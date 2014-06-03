
import java.util.ArrayList;
import java.util.HashMap;


public class ControlSystem {
	static ArrayList<Train> trains;
	static HashMap<Train, ArrayList<Node>> paths;
	static int currentTime;
	private static boolean newTrain;
	
	public static void startSimulation () {
		updateSystem();
	}
	
	public static Train buildTrain(Node start, Node destination) {
		ArrayList<Node> path = PathFinder.findPath(start, destination);
		Train t = new Train(path, 1);
		trains.add(t);
		newTrain = true;
		return t;
	}
	
	public static void initialize () {
		trains = new ArrayList<Train>();
		paths = new HashMap<Train, ArrayList<Node>>();
		currentTime = 0;
	}
	
	public static void updateSystem () {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			currentTime++;
			for (Train train : trains) {
				train.update();
			}
			
			if(newTrain) {
				for (Train train : trains) {
					ArrayList<Node> oldPath = train.getPath();
					Node source = train.getNodeFrom();
					Node destination = oldPath.get(oldPath.size() - 1);
					train.setPath(PathFinder.findPath(source, destination));
				}
				//PathFinder.optimize();
				newTrain = false;
			}
		}
	}
	
	public boolean isBlocked(ArrayList<Node> path) {
		for(int i = 0; i < path.size()-1; i++) {
			for(Train train : trains) {
				ArrayList<Node> checkPaths = paths.get(train);
				for(int j = 0; j < checkPaths.size()-1; j++) {
					if(train.isBetween(checkPaths.get(j), checkPaths.get(j+1)))
							return true;
				}
			}
		}
		return false;
	}
	
	public static void generatePath(Train train) {
		
	}
	
}

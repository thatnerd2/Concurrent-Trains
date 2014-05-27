
import java.util.ArrayList;
import java.util.HashMap;


public class ControlSystem {
	static ArrayList<Train> trains;
	static HashMap<Train, ArrayList<Node>> paths;
	private static boolean newTrain;
	
	public static void main (String[] args) {
		ControlSystem.initialize();
		TimeManager.initialize();
		TestAdministrator.essentialsTest();
	}
	
	public static void addTrain(Train train) {
		trains.add(train);
		newTrain = true;
	}
	
	public static void initialize () {
		trains = new ArrayList<Train>();
		paths = new HashMap<Train, ArrayList<Node>>();
	}
	
	public static void updateSystem () {
		TimeManager.updateTime();
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
			PathFinder.optimize();
			newTrain = false;
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

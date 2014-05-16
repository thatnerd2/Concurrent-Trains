
import java.util.ArrayList;
import java.util.HashMap;


public class ControlSystem {
	static ArrayList<Train> trains;
	static HashMap<Train, ArrayList<Node>> paths;
	
	public static void main (String[] args) {
		ControlSystem.initialize();
		TimeManager.initialize();
		TestAdministrator.essentialsTest();
	}
	
	public static void initialize () {
		trains = new ArrayList<Train>();
		paths = new HashMap<Train, ArrayList<Node>>();
	}
	
	public static void updateSystem () {
		TimeManager.updateTime();
		for (Train train : trains) {
			train.update();
			if (train.getNodeTo() == null) {
				System.out.println("Arrived");
			}
		}
		//needs to update paths of all trains
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

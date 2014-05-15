
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class ControlSystem {
	static ArrayList<Train> trains;
	static HashMap<Train, Path> paths;
	
	
	public static void main (String[] args) {
		ControlSystem.initialize();
		TimeManager.initialize();
		
		TestAdministrator.essentialsTest();
		
		
	}
	
	public static void initialize () {
		trains = new ArrayList<Train>();
		paths = new HashMap<Train, Path>();
	}
	
	public static void updateSystem () {
		TimeManager.updateTime();
		
		for (Train train : trains) {
			train.update();
		}
	}
	
}


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class ControlSystem {
	static ArrayList<Train> trains;
	static HashMap<Train, Path> paths;
	
	
	public static void main (String[] args) {
		initialize();
		TimeManager.initialize();
		/**
		 * Initialize, prompt, direct.
		 */
		
		
		
		//ArrayList<Path> testPath = new ArrayList<Path>();
		
		//Train t = new Train (testPath, 1, 4);
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

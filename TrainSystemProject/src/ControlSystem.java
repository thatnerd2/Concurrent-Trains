
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class ControlSystem {
	static ArrayList<Train> trains;
	static HashMap<Train, Path> paths;
	private static int time;
	
	public static void main (String[] args) {
		initialize();
		
		/**
		 * Initialize, prompt, direct.
		 */
		
		Timer Time = new Timer();
		Time.scheduleAtFixedRate(new TimerTask () {
			public void run () {
				ControlSystem.updateSystem();
			}
		}, 1000, 1000);
	}
	
	public static void initialize () {
		trains = new ArrayList<Train>();
		paths = new HashMap<Train, Path>();
	}
	
	public static void updateSystem () {
		updateTime();
		
		for (Train train : trains) {
			train.update();
		}
	}
	
	public static void updateTime () {
		time += 1;
	}
	
	public static int getTime(Node nodeFrom, Node nodeTo) {
		return 0;
		//idk
	}
}

import java.util.Timer;
import java.util.TimerTask;


public class TimeManager {
	private static int time;
	
	public static void initialize () {
		Timer Time = new Timer();
		Time.scheduleAtFixedRate(new TimerTask () {
			public void run () {
				ControlSystem.updateSystem();
			}
		}, 1000, 1000);
	}
	
	public static int getCurrentTime() {
		return time;
	}
	
	public static void updateTime () {
		time += 1;
	}
}

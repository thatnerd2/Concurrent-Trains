import java.util.ArrayList;


public class Optimizer {
	
	
	private static boolean isValid(ArrayList<ArrayList<Node>> list, Node from, Node to, ArrayList<Node> currentPath) {
		for (ArrayList<Node> l : list) {
			if (l.contains(to)) {
				return false;
			}
		}
		int myTimeArrivingOnPath = TimeManager.getCurrentTime() + getPathDistance(currentPath);
		int myTimeLeavingPath = myTimeArrivingOnPath + from.getDistance(to);
		for (Train train : ControlSystem.trains) 
		{
			if (train.getMyPath().contains(from)) 
			{
				int i = train.getMyPath().indexOf(from);
				if (train.getMyPath().get(i + 1).equals(to)) 
				{
					int timeGettingOnPath = TimeManager.getCurrentTime() + train.getArrivalTime() + getPathDistance(subPath(train.getMyPath(), i, i + 1));
					int timeLeavingPath = timeGettingOnPath + from.getDistance(to);
					if (overlaps(myTimeArrivingOnPath, timeGettingOnPath, timeLeavingPath)
							|| (overlaps(myTimeLeavingPath, timeGettingOnPath, timeLeavingPath))) 
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private static boolean overlaps(int check, int start, int end) {
		if (check > start && check < end) {
			return true;
		} else {
			return false;
		}
	}
}

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PathFinder {
	public static ArrayList<Node> findPath (Node start, Node end) {
		ArrayList<Integer> distances = new ArrayList<Integer>();
		ArrayList<ArrayList<Node>> paths = new ArrayList<ArrayList<Node>>();
		
		Node nextNode = start;
		Node prevNode = start;
		int pathContext = -1;
		ArrayList<Node> initialPath = new ArrayList<Node>();
		initialPath.add(prevNode);
		
		paths.add(initialPath);
		distances.add(0);
		
		while (!nextNode.equals(end)) {
			nextNode = null;
			prevNode = null;
			pathContext = -1;
			int minDistance = Integer.MAX_VALUE;
			for (int i = 0; i < paths.size(); i++) {
				ArrayList<Node> currentList = paths.get(i);
				for (int j = 0; j < currentList.size(); j++) {
					Node from = currentList.get(j);
					int cumulativeDistance = getPathDistance(subPath(currentList, 0, j));
					for (Node to : from.getAdjacents()) {
						int d = from.getDistance(to) + cumulativeDistance;
						if (isValid(currentList, from, to) && d < minDistance) {
							prevNode = from;
							nextNode = to;
							pathContext = i;
							minDistance = d;
						}
						else {
							//need to update like normal, but also add the wait time?
						}
					}
				}
			}
			ArrayList<Node> target = paths.get(pathContext);
			if (target.get(target.size() - 1).equals(prevNode)) {
				target.add(nextNode);
				distances.set(pathContext, distances.get(pathContext) + minDistance);
			}
			else {
				ArrayList<Node> newList = subPath(target, 0, target.indexOf(prevNode));
				newList.add(prevNode);
				newList.add(nextNode);
				paths.add(newList);
				distances.add(getPathDistance(newList));
			}
		}
		int record = Integer.MAX_VALUE;
		int index = -1;
		for (int i = 0; i < distances.size(); i++) {
			int candidate = distances.get(i);
			if (candidate < record && paths.get(i).contains(end)) {
				record = candidate;
				index = i;
			}
		}
		return paths.get(index);
	}
	
	private static ArrayList<Node> subPath (ArrayList<Node> source, int start, int to) {
		List<Node> listView = source.subList(start, to);
		ArrayList<Node> res = new ArrayList<Node>();
		res.addAll(listView);
		return res;
	}
	 
	private static int getPathDistance (ArrayList<Node> list) {
		int sum = 0;
		for (int i = 0; i < list.size() - 1; i++) {
			sum += list.get(i).getDistance(list.get(i + 1));
		}
		return sum;
	}
	
	private static int getWaitTimes (ArrayList<Integer> list) {
		int sum = 0;
		for (int i = 0; i < list.size() - 1; i++) {
			sum += list.get(i);
		}
		return sum;
	}
	
	private static boolean wasVisited (ArrayList<ArrayList<Node>> list, Node n) {
		for (ArrayList<Node> l : list) {
			if (l.contains(n)) {
				return true;
			}
		}
		return false;
	}

	private static boolean isValid(ArrayList<Node> currentPath, Node from, Node to) {
		int myTimeArrivingOnPath = TimeManager.getCurrentTime() + getPathDistance(currentPath); // + getWaitTimes(currentPath)?
		int myTimeLeavingPath = myTimeArrivingOnPath + from.getDistance(to);
		for (Train train : ControlSystem.trains) 
		{
			if (train.getPath().contains(from) && train.getPath().contains(to) 
					&& (Math.abs(train.getPath().indexOf(from))-train.getPath().indexOf(to) == 1)) 
			{
				int fromIndex = train.getPath().indexOf(from);
				if(fromIndex == (train.getPath().indexOf(to)+1)) 
				{
					int timeGettingOnPath = TimeManager.getCurrentTime() + train.getArrivalTime()
							+ getPathDistance(subPath(train.getPath(), 0, fromIndex - 1));
					int timeLeavingPath = timeGettingOnPath + from.getDistance(to);
					if (overlaps(myTimeArrivingOnPath, timeGettingOnPath, timeLeavingPath)
							|| (overlaps(myTimeLeavingPath, timeGettingOnPath, timeLeavingPath))) 
					{
						return false;
					}
				}
				else  
				{
					int timeGettingToFromNode = TimeManager.getCurrentTime() + train.getArrivalTime()
					+ getPathDistance(subPath(train.getPath(), 0, fromIndex - 1));
					if(timeGettingToFromNode == myTimeArrivingOnPath) 
					{
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private static void optimize () {
		int secondsToCheck = shortestPathTime(ControlSystem.trains);
		int currentTime = 0;
		
		while (currentTime < secondsToCheck) {
			/**
			 * The idea of the algorithm:
			 * At a certain point in time, who's AT a station?  Which trains have arrived?  Of these trains,
			 * store their current node place that they've gotten to in conflicts...wait, how do we account for wait time?
			 */
			
			HashMap<Node, Train> conflicts = new HashMap<Node, Train>();
			
			int myTimeArrivingOnPath = TimeManager.getCurrentTime() + getPathDistance(currentPath);
			int myTimeLeavingPath = myTimeArrivingOnPath + from.getDistance(to);
			for (Train train : ControlSystem.trains) 
			{
				if (train.getPath().contains(from)) 
				{
					int i = train.getPath().indexOf(from);
					if (train.getPath().get(i + 1).equals(to)) 
					{
						int timeGettingOnPath = TimeManager.getCurrentTime() + train.getArrivalTime() + getPathDistance(subPath(train.getPath(), i, i + 1));
						int timeLeavingPath = timeGettingOnPath + from.getDistance(to);
						if (overlaps(myTimeArrivingOnPath, timeGettingOnPath, timeLeavingPath)
								|| (overlaps(myTimeLeavingPath, timeGettingOnPath, timeLeavingPath))) 
						{
						}
					}
				}
			}
		}
	}
	
	private static int shortestPathTime (ArrayList<Train> trains) {
		int lowestTime = Integer.MAX_VALUE;
		for (Train t : trains) {
			lowestTime = Math.min(lowestTime, t.getArrivalTime() + getPathDistance(t.getPath()));
		}
		return lowestTime;
	}
	
	private static boolean overlaps(int check, int start, int end) {
		if (check > start && check < end) {
			return true;
		} else {
			return false;
		}
	}

}

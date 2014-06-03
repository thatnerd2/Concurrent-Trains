import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PathFinder {
	public static ArrayList<Node> findPath (Node start, Node end) {
		ArrayList<Integer> distances = new ArrayList<Integer>();
		ArrayList<ArrayList<Node>> paths = new ArrayList<ArrayList<Node>>();
		ArrayList<ArrayList<Integer>> waitTimes = new ArrayList<ArrayList<Integer>>();
		
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
			int minWaitTime = 0;
			for (int i = 0; i < paths.size(); i++) 
			{
				ArrayList<Node> currentList = paths.get(i);
				for (int j = 0; j < currentList.size(); j++) 
				{
					Node from = currentList.get(j);
					int cumulativeDistance = getPathDistance(subPath(currentList, 0, j));
					
					for (Node to : from.getAdjacents()) 
					{
						int d = from.getDistance(to) + cumulativeDistance;
						
						if (!wasVisited(paths, to) && d < minDistance && isValid(currentList, from, to)) {
							prevNode = from;
							nextNode = to;
							pathContext = i;
							minDistance = d;
						}
						else if (!wasVisited(paths, to) && d < minDistance && !isValid(currentList, from, to)) {
							int myTimeArrivingOnPath = ControlSystem.currentTime + getPathDistance(currentList);
							int timeGettingOnPath = ControlSystem.currentTime + train.getArrivalTime()
									+ getPathDistance(subPath(train.getPath(), 1, fromIndex));
							int timeLeavingPath = timeGettingOnPath + from.getDistance(to);
							minWaitTime = 
						}
					}
				}
			}
			ArrayList<Node> targetPath = paths.get(pathContext);
			ArrayList<Integer> targetTimes = waitTimes.get(pathContext);
			if (targetPath.get(targetPath.size() - 1).equals(prevNode)) {
				targetPath.add(nextNode);
				distances.set(pathContext, distances.get(pathContext) + minDistance);
			}
			else {
				int sectionCutOff = targetPath.indexOf(prevNode);
				ArrayList<Node> newList = subPath(targetPath, 0, sectionCutOff);
				newList.add(prevNode);
				newList.add(nextNode);
				ArrayList<Integer> newWaitTimes = subPath(targetTimes, 0, sectionCutOff);
				//add later
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
	
	private static <T> ArrayList<T> subPath (ArrayList<T> source, int start, int to) {
		List<T> listView = source.subList(start, to);
		ArrayList<T> res = new ArrayList<T>();
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
		
		int myTimeArrivingOnPath = ControlSystem.currentTime + getPathDistance(currentPath); // + getWaitTimes(currentPath)?
		int myTimeLeavingPath = myTimeArrivingOnPath + from.getDistance(to);
		for (Train train : ControlSystem.trains) 
		{
			if (train.getPath().contains(from) && train.getPath().contains(to) 
					&& (Math.abs(train.getPath().indexOf(from))-train.getPath().indexOf(to) == 1)) 
			{
				int fromIndex = train.getPath().indexOf(from);
				if(fromIndex == (train.getPath().indexOf(to)+1)) 
				{
					int timeGettingOnPath = ControlSystem.currentTime + train.getArrivalTime()
							+ getPathDistance(subPath(train.getPath(), 1, fromIndex));
					int timeLeavingPath = timeGettingOnPath + from.getDistance(to);
					if (overlaps(myTimeArrivingOnPath, timeGettingOnPath, timeLeavingPath)
							|| (overlaps(myTimeLeavingPath, timeGettingOnPath, timeLeavingPath))) 
					{
						return false;
					}
				}
				else  
				{
					int timeGettingToFromNode = ControlSystem.currentTime + train.getArrivalTime()
					+ getPathDistance(subPath(train.getPath(), 1, fromIndex));
					if(timeGettingToFromNode == myTimeArrivingOnPath) 
					{
						return false;
					}
				}
			}
		}
		System.out.println("Is valid returned true");
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

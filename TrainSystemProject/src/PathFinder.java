import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PathFinder {
	public static Path findPath (Node start, Node end) {
		ArrayList<Path> paths = new ArrayList<Path>();
		Node nextNode = start;
		Node prevNode = start;
		
		int pathContext = -1;
		ArrayList<Node> initNodes = new ArrayList<Node>();
		ArrayList<Integer> initWaitTimes = new ArrayList<Integer>();
		initNodes.add(prevNode);
		initWaitTimes.add(0);
		Path path = new Path(initNodes, initWaitTimes);
		
		paths.add(path);
		
		while (!nextNode.equals(end)) {
			nextNode = null;
			prevNode = null;
			pathContext = -1;
			int minDistance = Integer.MAX_VALUE;
			int minWaitTime = 0;
			for (int i = 0; i < paths.size(); i++) 
			{
				/**
				 * No calculations are done, just put
				 */
				ArrayList<Node> nodes = paths.get(i).getNodes();
				ArrayList<Integer> waitTimes = paths.get(i).getWaitTimes();
				
				for (int j = 0; j < nodes.size(); j++) 
				{
					Node from = nodes.get(j);
					int cumulativeDistance = paths.get(i).computePathSubsetTime(0, j);
					
					for (Node to : from.getAdjacents()) 
					{
						int d = from.getDistance(to) + cumulativeDistance;
						
						if (!wasVisited(paths, to) && d < minDistance && isValid(nodes, waitTimes, from, to)) {
							prevNode = from;
							nextNode = to;
							pathContext = i;
							minDistance = d; // do these pertain to minTravelTime now?
							minWaitTime = 0;
						}
						else if (!wasVisited(paths, to) && d < minDistance && !isValid(nodes, waitTimes, from, to)) {
							prevNode = from;
							nextNode = to;
							pathContext = i;
							minDistance = d;
							minWaitTime = minWaitTime(nodes, waitTimes, from, to);
						}
					}
				}
			}
			
			Path pathToEdit = paths.get(pathContext);
			ArrayList<Node> nodesToEdit = pathToEdit.getNodes();
			ArrayList<Integer> timesToEdit = pathToEdit.getWaitTimes();
			if (nodesToEdit.get(nodesToEdit.size() - 1).equals(prevNode)) {
				/**
				 * The node we've selected will be added to the end of this path.
				 * No new branching path will be created.
				 */
				nodesToEdit.add(nextNode);
				timesToEdit.add(minWaitTime);
				pathToEdit.computeDistance();
			}
			
			//ArrayList<Integer> targetTimes = waitTimes.get(pathContext);
			/*if (pathToEdit.get(pathToEdit.size() - 1).equals(prevNode)) {
				pathToEdit.add(nextNode);
				.set(pathContext, distances.get(pathContext) + minDistance);
			}
			else {
				int sectionCutOff = targetPath.indexOf(prevNode);
				ArrayList<Node> newList = subPath(targetPath, 0, sectionCutOff);
				newList.add(prevNode);
				newList.add(nextNode);
				//ArrayList<Integer> newWaitTimes = subPath(targetTimes, 0, sectionCutOff);
				//add later
				paths.add(newList);
				distances.add(getPathDistance(newList));
			}*/
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
	
	private static int getTotalTime(ArrayList<Node> path, ArrayList<Integer> waitTimes) {
		int total = 0;
		for(int i = 0; i < path.size() - 1; i++) {
			total += path.get(i).getDistance(path.get(i+1));
			total += waitTimes.get(i);
		}
		return total;
	}
	
	private static boolean wasVisited (ArrayList<Path> allPaths, Node n) {
		for (int i = 0; i < allPaths.size(); i++) {
			if (allPaths.get(i).getNodes().contains(n)) {
				return true;
			}
		}
		return false;
	}
	
	private static int minWaitTime(ArrayList<Node> nodes, ArrayList<Integer> waitTimes, Node from, Node to) {
		int minWaitTime = 0;
		int thisWaitTime = 0;
		int myTimeArrivingOnPath = ControlSystem.currentTime + getTotalTime(nodes, waitTimes); // + getWaitTimes(currentPath)?
		int myTimeLeavingPath = myTimeArrivingOnPath + from.getDistance(to);
		for (Train train : ControlSystem.trains) 
		{
			if (train.getPath().contains(from) && train.getPath().contains(to) 
					&& (Math.abs(train.getPath().indexOf(from))-train.getPath().indexOf(to) == 1)) 
			{
				thisWaitTime = 0;
				int fromIndex = train.getPath().indexOf(from);
				if(fromIndex == (train.getPath().indexOf(to)+1)) 
				{
					int timeGettingOnPath = ControlSystem.currentTime + train.getArrivalTime()
						 + getTotalTime(subPath(train.getPath(), 1, fromIndex), subPath(train.getWaitTimes(), 1, fromIndex));
					int timeLeavingPath = timeGettingOnPath + from.getDistance(to);
					if (overlaps(myTimeArrivingOnPath, timeGettingOnPath, timeLeavingPath)
							|| (overlaps(myTimeLeavingPath, timeGettingOnPath, timeLeavingPath))) 
					{
						if(myTimeArrivingOnPath < timeGettingOnPath) {
							thisWaitTime = 0; //ensures that the first train to arrive at a collision-path will be allowed to go first
											//i believe that if a train's path is modified after a previous train's path has already
											//been optimized, this may lead to crashes. I can explain it more clearly in person
						}
						else {
							thisWaitTime = timeLeavingPath - myTimeArrivingOnPath;
						}
					}
				}
				else  
				{
					int timeGettingToFromNode = ControlSystem.currentTime + train.getArrivalTime()
					+ getTotalTime(subPath(train.getPath(), 1, fromIndex), subPath(train.getWaitTimes(), 1, fromIndex));
					if(timeGettingToFromNode == myTimeArrivingOnPath) 
					{
						thisWaitTime = 1;
					}
				}
				if(minWaitTime < thisWaitTime) {
					minWaitTime = thisWaitTime;
				}
			}
		}
		return minWaitTime;
	}
	private static boolean isValid(ArrayList<Node> nodes, ArrayList<Integer> waitTimes, Node from, Node to) {
		
		int myTimeArrivingOnPath = ControlSystem.currentTime + getTotalTime(nodes, waitTimes); // + getWaitTimes(currentPath)?
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
						 + getTotalTime(subPath(train.getPath(), 1, fromIndex), subPath(train.getWaitTimes(), 1, fromIndex));
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
					+ getTotalTime(subPath(train.getPath(), 1, fromIndex), subPath(train.getWaitTimes(), 1, fromIndex));
					if(timeGettingToFromNode == myTimeArrivingOnPath) 
					{
						return false;
					}
				}
			}
		}
		//System.out.println("Is valid returned true");
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

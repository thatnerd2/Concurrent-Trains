import java.util.ArrayList;

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
			int recordPathMinTime = Integer.MAX_VALUE;
			int waitTimeForRecord = 0;
			for (int i = 0; i < paths.size(); i++) 
			{
				/**
				 * No calculations are done, just initialized
				 */
				ArrayList<Node> nodes = paths.get(i).getNodes();
				ArrayList<Integer> waitTimes = paths.get(i).getWaitTimes();
				
				for (int j = 0; j < nodes.size(); j++) 
				{
					/**
					 * Check a node.  Compute the path time all the way up to that node and then start
					 * checking it's neighbors to compare possible times with the recordTotalMinTime
					 */
					Node from = nodes.get(j);
					int cumulativeTime = paths.get(i).computePathSubsetTime(0, j);
					
					for (Node to : from.getAdjacents()) 
					{
						/**
						 * Calculate the total time required for this **prospective** branch, ignoring wait time.
						 */
						int thisTotalTime = from.getDistance(to) + cumulativeTime;
						
						if (!wasVisited(paths, to) && thisTotalTime < recordPathMinTime + waitTimeForRecord
								&& isValid(nodes, waitTimes, from, to)) {
							prevNode = from;
							nextNode = to;
							pathContext = i;
							recordPathMinTime = thisTotalTime;
							waitTimeForRecord = 0;
						}
						else if (!wasVisited(paths, to) && thisTotalTime < recordPathMinTime + waitTimeForRecord
								&& !isValid(nodes, waitTimes, from, to)) {
							prevNode = from;
							nextNode = to;
							pathContext = i;
							recordPathMinTime = thisTotalTime;
							waitTimeForRecord = minWaitTime(nodes, waitTimes, from, to);
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
				timesToEdit.add(waitTimeForRecord);
				pathToEdit.computeTotalTime();
			}
			else {
				/**
				 * Branch off, create a new path and add it to the paths list.
				 */
				int cutOff = nodesToEdit.indexOf(prevNode);
				Path branchingPath = pathToEdit.getPathSubset(0, cutOff);
				ArrayList<Node> branchNodes = branchingPath.getNodes();
				ArrayList<Integer> branchWaitTimes = branchingPath.getWaitTimes();
				branchNodes.add(nextNode);
				branchWaitTimes.add(timesToEdit.get(nodesToEdit.indexOf(prevNode)));
				branchWaitTimes.add(waitTimeForRecord);
				paths.add(branchingPath);
			}
		}
		
		return paths.get(pathContext);
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
			Path currentPath = train.getPath();
			ArrayList<Node> currentPathNodes = currentPath.getNodes();
			
			if (currentPathNodes.contains(from) && currentPathNodes.contains(to) 
					&& (Math.abs(currentPathNodes.indexOf(from)) - currentPathNodes.indexOf(to) == 1)) 
			{
				thisWaitTime = 0;
				int fromIndex = currentPathNodes.indexOf(from);
				Path subsetToFromIndex = currentPath.getPathSubset(1, fromIndex);
				
				if (fromIndex == currentPathNodes.indexOf(to) + 1) 
				{
					int timeGettingOnPath = ControlSystem.currentTime + train.getArrivalTime() + subsetToFromIndex.totalTime;
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
					int timeGettingToFromNode = ControlSystem.currentTime + 
												train.getArrivalTime() + 
												subsetToFromIndex.totalTime;
					
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
			Path currentPath = train.getPath();
			ArrayList<Node> currentPathNodes = currentPath.getNodes();
			
			if (currentPathNodes.contains(from) && currentPathNodes.contains(to) 
					&& (Math.abs(currentPathNodes.indexOf(from)) - currentPathNodes.indexOf(to) == 1)) 
			{
				int fromIndex = currentPathNodes.indexOf(from);
				Path subsetToFromIndex = currentPath.getPathSubset(1, fromIndex);
				
				if (fromIndex == currentPathNodes.indexOf(to) + 1) 
				{
					int timeGettingOnPath = ControlSystem.currentTime + 
											train.getArrivalTime() + 
											subsetToFromIndex.totalTime;
					
					int timeLeavingPath = timeGettingOnPath + from.getDistance(to);
					
					if (overlaps(myTimeArrivingOnPath, timeGettingOnPath, timeLeavingPath)
							|| (overlaps(myTimeLeavingPath, timeGettingOnPath, timeLeavingPath))) 
					{
						return false;
					}
				}
				else  
				{
					int timeGettingToFromNode = ControlSystem.currentTime + 
												train.getArrivalTime() + 
												subsetToFromIndex.totalTime;
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

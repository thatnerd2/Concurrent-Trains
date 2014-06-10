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
					Path subsetToFromNode = paths.get(i).getPathSubset(0, j);
					ArrayList<Node> subsetNodes = subsetToFromNode.getNodes();
					ArrayList<Integer> subsetWaitTimes = subsetToFromNode.getWaitTimes();
					int cumulativeTime = getTotalTime(subsetNodes, subsetWaitTimes);
					
					for (Node to : from.getAdjacents()) 
					{
						/**
						 * Calculate the total time required for this **prospective** branch, ignoring wait time.
						 */
						int prospectiveWaitTime = minWaitTimeTest(subsetNodes, waitTimes, from, to);
						int thisTotalTime = from.getDistance(to) + cumulativeTime + prospectiveWaitTime;
						
						if (!wasVisited(paths, to) && thisTotalTime < recordPathMinTime + waitTimeForRecord) {
							prevNode = from;
							nextNode = to;
							pathContext = i;
							recordPathMinTime = from.getDistance(to) + cumulativeTime;
							waitTimeForRecord = prospectiveWaitTime;
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
				timesToEdit.set(timesToEdit.size() -1, waitTimeForRecord);
				timesToEdit.add(0);
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
				branchWaitTimes.set(branchWaitTimes.size() - 1, waitTimeForRecord);
				branchWaitTimes.add(0);
				paths.add(branchingPath);
				pathContext = paths.size() - 1;
			}
			
		}
		
		return paths.get(pathContext);
	}
	
	private static int getTotalTime(ArrayList<Node> path, ArrayList<Integer> waitTimes) {
		int total = 0;
		for(int i = 0; i < path.size() - 1; i++) {
			total += path.get(i).getDistance(path.get(i+1));
			if(waitTimes.get(i).equals(null)) {
				
			}
			else {
				total += waitTimes.get(i);
			}
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
	
	private static int minWaitTimeTest (ArrayList<Node> nodes, ArrayList<Integer> waitTimes, Node from, Node to) {
		int myTimeOfArrivalAtTo = ControlSystem.currentTime + 
								  getTotalTime(nodes, waitTimes) + 
								  from.getDistance(to);
		
		int myTimeOfArrivalAtFrom = ControlSystem.currentTime +
									getTotalTime(nodes, waitTimes);
		
		for (Train train : ControlSystem.trains) {
			Path otherPath = train.getPath();
			ArrayList<Node> otherNodes = otherPath.getNodes();
			
			if (hasUndirectedConnection(otherNodes, from, to)) {
				int fromIndex = otherNodes.indexOf(from);
				int toIndex = otherNodes.indexOf(to);
				Path conflictingPath = otherPath.getPathSubset(0, toIndex);
				ArrayList<Node> conflictNodes = conflictingPath.getNodes();
				ArrayList<Integer> conflictTimes = conflictingPath.getWaitTimes();

				if (fromIndex < toIndex) {
					int otherTimeOfArrivalAtTo = ControlSystem.currentTime + getTotalTime(conflictNodes, conflictTimes);
					int otherTimeOfArrivalAtFrom = otherTimeOfArrivalAtTo - from.getDistance(to);
					
					if (otherTimeOfArrivalAtFrom == myTimeOfArrivalAtFrom) {
						/**
						 * We get there at the same time, and we're headed in the same direction.
						 * I'll be the gentleman.
						 */
						return 1;
					}
					else {
						/**
						 * He's already ahead of me or behind me.
						 */
						return 0;
					}
				}
				else {
					/**
					 * We're heading in the exact opposite directions.  If both of us step on the path
					 * at any point, we're going to crash.
					 */
					int otherTimeOfArrivalAtFrom = ControlSystem.currentTime + getTotalTime(conflictNodes, conflictTimes);
					int otherTimeOfArrivalAtTo = from.getDistance(to);
					
					/*System.out.println(otherTimeOfArrivalAtFrom);
					System.out.println(otherTimeOfArrivalAtTo);
					System.out.println(myTimeOfArrivalAtFrom);
					System.out.println(myTimeOfArrivalAtTo);*/
					
					if (overlaps(otherTimeOfArrivalAtFrom, myTimeOfArrivalAtFrom, myTimeOfArrivalAtTo) ||
						overlaps(otherTimeOfArrivalAtTo, myTimeOfArrivalAtFrom, myTimeOfArrivalAtTo))
					{
						/**
						 * It's as we feared.  We're going to crash unless we wait.
						 */
						return otherTimeOfArrivalAtTo - myTimeOfArrivalAtFrom;
					}
					else {
						/**
						 * No crash, we're not going to be there at the same time anyway.
						 */
						return 0;
					}
				}
			}
		}
		/**
		 * No trains actually have that from -> to connection.  We never need to wait!
		 */
		return 0;
	}
	
	private static boolean hasUndirectedConnection (ArrayList<Node> nodes, Node from, Node to) {
		return (nodes.contains(from) && nodes.contains(to) &&
				Math.abs(nodes.indexOf(from) - nodes.indexOf(to)) == 1);
	}
	
	private static boolean overlaps(int check, int start, int end) {
		if (check > start && check < end) {
			return true;
		} else {
			return false;
		}
	}

}

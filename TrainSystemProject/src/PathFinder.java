import java.util.ArrayList;
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
					int cumulativeDistance = getPathDistance(subPath(currentList, j));
					for (Node to : from.getAdjacents()) {
						int d = from.getDistance(to) + cumulativeDistance;
						//wasVisited should be changed to isValid
						if (!wasVisited(paths, to) && d < minDistance) {
							prevNode = from;
							nextNode = to;
							pathContext = i;
							minDistance = d;
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
				ArrayList<Node> newList = subPath(target, target.indexOf(prevNode));
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
	
	private static ArrayList<Node> subPath (ArrayList<Node> source, int end) {
		List<Node> listView = source.subList(0, end);
		ArrayList<Node> res = new ArrayList<Node>();
		res.addAll(listView);
		return res;
	}
	
	/* private static boolean ArrayList<Node> subPath(ArrayList<Node> source, int start, int end) {
	 * 		return source.subList(start, end);
	 * 	}
	 */
	
	private static int getPathDistance (ArrayList<Node> list) {
		int sum = 0;
		for (int i = 0; i < list.size() - 1; i++) {
			sum += list.get(i).getDistance(list.get(i + 1));
		}
		return sum;
	}
	
	/* private static boolean isValid(ArrayList<ArrayList<Node>> list, Node from, Node to, ArrayList<Node> currentPath) {
	 * 		for(ArrayList<Node> l : list) {
	 *			if(l.contains(to)) {
	 *				return false;
	 *			}
	 *		int myTimeArrivingOnPath = TimeManager.getCurrentTime() + getPathDistance(currentPath);  //figures out when the train the path is being built for arrives at the "from" node in
	 *		int myTimeLeavingPath = myTimeArrivingOnPath + from.getDistance(to); //figures out when the train the path is being built for arrives at the "to" location
	 *		ArrayList<Integer> timeInTrainPath = new ArrayList<Integer>();
	 *		for(Train train : ControlSystem.trains) {  
	 *			if(train.getMyPath().contains(from)) {  // sees if any of the train paths contains the "from" node
	 *				int i = train.getMyPath().indexOf(from);
	 *				if(train.getMyPath().get(i+1).equals(to)) { // sees if the "from" node is followed by the "to" node ... can .equals be used here?
	 * 					int timeGettingOnPath = time + train.getArrivalTime + getPathDistance(subPath(train.getMyPath(), train.getNodeTo(), to);
	 * 					int timeLeavingPath = timeGettingOnPath + from.getDistance(to); // finds the times that each train will be on the from-to path
	 * 					if(overlaps(myTimeArrivingOnPath, timeGettingOnPath, timeLeavingPath) || (overlaps(myTimeLeavingPath, timeGettingOnPath, timeLeavingPath)) {
	 * 						return false; returns false if the from-to path for any train would overlap the from-to path for the path of the train being built
	 * 					}
	 * 				}
	 * 
	 * 				// the above if loop should also have a condition for .get(i-1).equals(to). don't have time to do that ATM
	 * 			}
	 * 		}
	 * 	return true;
	 * 	}
	 */
	
	/* private static method overlaps(int check, int start, int end) { 
	 * 		if(check > start && check < end) {
	 * 			return true;
	 * 		}
	 * 		else {
	 * 			return false;
	 * 		}
	 * }
	 */
	private static boolean wasVisited (ArrayList<ArrayList<Node>> list, Node n) {
		for (ArrayList<Node> l : list) {
			if (l.contains(n)) {
				return true;
			}
		}
		return false;
	}
}

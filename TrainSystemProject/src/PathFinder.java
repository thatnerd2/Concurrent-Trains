import java.util.ArrayList;
import java.util.List;

public class PathFinder {
	private ArrayList<Node> trainPath;
	
	
	public static ArrayList<Node> findPath (Node start, Node end) {
		ArrayList<Integer> distances = new ArrayList<Integer>();
		ArrayList<ArrayList<Node>> paths = new ArrayList<ArrayList<Node>>();
		Node nextNode = start;
		ArrayList<Node> initialPath = new ArrayList<Node>();
		initialPath.add(nextNode);
		paths.add(initialPath);
		distances.add(0);
		int debugCount = 0;
		while (debugCount < 10 && !nextNode.equals(end)) {
			nextNode = null;
			Node prevNode = null;
			int index = -1;
			int minDistance = Integer.MAX_VALUE;
			for (int i = 0; i < paths.size(); i++) {
				for (ArrayList<Node> currentList : paths) {
					for (int j = 0; j < currentList.size(); j++) {
						Node from = currentList.get(j);
						int cumulativeDistance = getPathDistance(subPath(currentList, j));
						for (Node to : from.getAdjacents()) {
							int d = from.getDistance(to) + cumulativeDistance;
							if (!wasVisited(paths, to) && d < minDistance) {
								prevNode = from;
								nextNode = to;
								index = i;
								minDistance = d;
								System.out.println("now min distance is: "+minDistance+" going to node: "+nextNode);
								
							}
						}
					}
				}
			}
			System.out.println("Previous Node: "+prevNode);
			System.out.println("Next Node: "+nextNode);
			System.out.println("Min: "+minDistance);
			
			ArrayList<Node> target = paths.get(index);
			if (target.get(target.size() - 1).equals(prevNode)) {
				target.add(nextNode);
				distances.set(index, distances.get(index) + minDistance);
			}
			else {
				ArrayList<Node> newList = subPath(target, target.indexOf(prevNode));
				newList.add(prevNode);
				newList.add(nextNode);
				paths.add(newList);
				distances.add(distances.get(index) + minDistance);
			}
			System.out.println("------");
			debugCount++;
		}
		
		for (ArrayList<Node> path : paths) {
			System.out.println(path);
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
	
	private static int getPathDistance (ArrayList<Node> list) {
		int sum = 0;
		for (int i = 0; i < list.size() - 1; i++) {
			sum += list.get(i).getDistance(list.get(i + 1));
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
}

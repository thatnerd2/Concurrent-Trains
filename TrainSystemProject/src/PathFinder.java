import java.util.ArrayList;
/* 
 * not sure exactly how we'll implement this yet. probably give the ControlSystem
 * a pathfinder object and have it call "findPath" on each train concurrently.
 * new paths will be generated based off of optimizations of current paths
 * 
 * it could be different with multithreading
 */
public class PathFinder {
	private ArrayList<Node> trainPath;
	public PathFinder() {
		
	}
	//how to store each node's adjacentNode list?
	//trying to add each node's adjacentNode list and add it to an arraylist of all possible nodePaths,
	//which will then be trimmed down into non-loops and searched for one that ends at endNode
	public ArrayList<Node> buildPath(Node startNode, Node endNode) {
		ArrayList<ArrayList<Node>> possiblePaths = new ArrayList<ArrayList<Node>>();
		ArrayList<Node> start = new ArrayList<Node>();
		start.add(startNode);
		possiblePaths.add(start);
		for(int i = 0; i < possiblePaths.size(); i++) {
			if(!possiblePaths.get(i).contains(endNode) {
				ArrayList<Node> moreAdjacents = possiblePaths.get(i).get(possiblePaths.get(i).size()-1).getAdjacents();
			}
		}
		for(int j = 0; j < possiblePaths.size(); j++) {
			//while(possiblePaths.get(j).
		}
		return new ArrayList<Node>(); // just temp, ofc
	}
}

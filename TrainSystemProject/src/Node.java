import java.util.ArrayList;
import java.util.HashMap;


public class Node {
	private ArrayList<Node> adjacentNodes;
	private HashMap<Node, Integer> distances;
	
	public Node () {
		adjacentNodes = new ArrayList<Node>();
		distances = new HashMap<Node, Integer>();
	}
	
	public void addAdjacent (Node n, int time) {
		adjacentNodes.add(n);
		distances.put(n, time);
	}
	
	public void deleteAdjacent(Node n) {
		if (adjacentNodes.contains(n)) {
			adjacentNodes.remove(n);
			distances.remove(n);
		}
	}
	
	public int getDistance (Node to) {
		return distances.get(to);
	}
}

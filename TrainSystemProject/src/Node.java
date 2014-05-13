import java.util.ArrayList;


public class Node {
	private ArrayList<Node> adjacentNodes;
	
	public Node () {
		adjacentNodes = new ArrayList<Node>();
	}
	
	public void addAdjacent (Node n) {
		adjacentNodes.add(n);
	}
}

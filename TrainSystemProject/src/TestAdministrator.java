import java.util.ArrayList;


public class TestAdministrator {
	public static void essentialsTest () {
		Node A = new Node("A");
		Node B = new Node("B");
		MapManager.connectNodes(A, B, 5);
		ArrayList<Node> path = new ArrayList<Node>();
		path.add(A);
		path.add(B);
		Train testTrain = new Train (path, 1);
	}
	
	public static void pathfindingTest () {
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		Node E = new Node("E");
		MapManager.connectNodes(A, B, 2);
		MapManager.connectNodes(B, C, 1);
		MapManager.connectNodes(C, D, 2);
		MapManager.connectNodes(B, D, 6);
		MapManager.connectNodes(A, C, 7);
		MapManager.connectNodes(A, E, 3);
		System.out.println("RESULT: "+PathFinder.findPath(A, D));
	}
}

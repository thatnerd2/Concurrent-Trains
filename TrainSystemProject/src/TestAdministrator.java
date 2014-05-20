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
		Node F = new Node("F");
		Node G = new Node("G");
		MapManager.connectNodes(A, B, 2);
		MapManager.connectNodes(B, E, 2);
		MapManager.connectNodes(A, C, 1);
		MapManager.connectNodes(C, E, 3);
		MapManager.connectNodes(A, D, 3);
		MapManager.connectNodes(D, F, 6);
		MapManager.connectNodes(F, G, 2);
		System.out.println("RESULT: "+PathFinder.findPath(F, B));
	}
}

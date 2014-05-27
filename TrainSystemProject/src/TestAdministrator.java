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
		ControlSystem.trains.add(testTrain);
	}
	
	public static void pathfindingTest () {
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		Node E = new Node("E");
		Node F = new Node("F");
		Node G = new Node("G");
		MapManager.connectNodes(A, B, 1);
		MapManager.connectNodes(A, C, 2);
		MapManager.connectNodes(B, C, 2);
		MapManager.connectNodes(B, D, 4);
		MapManager.connectNodes(C, D, 2);
		MapManager.connectNodes(C, E, 3);
		MapManager.connectNodes(E, D, 1);
		System.out.println("RESULT: "+PathFinder.findPath(E, B));
	}
}

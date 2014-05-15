import java.util.ArrayList;


public class TestAdministrator {
	public static void essentialsTest () {
		Node A = new Node();
		Node B = new Node();
		MapManager.connectNodes(A, B, 5);
		ArrayList<Node> path = new ArrayList<Node>();
		path.add(A);
		path.add(B);
		Train testTrain = new Train (path, 1);
	}
}

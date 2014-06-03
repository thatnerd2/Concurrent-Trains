import java.util.ArrayList;


public class TestAdministrator {
	public static void main (String[] args) {
		ControlSystem.initialize();
		pathfindingTest();
		ControlSystem.startSimulation();
	}
	
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
		MapManager.connectNodes(A, B, 5);
		MapManager.connectNodes(B, C, 4);
		MapManager.connectNodes(B, E, 2);
		MapManager.connectNodes(C, D, 3);
		MapManager.connectNodes(E, D, 10);
		Train t1 = ControlSystem.buildTrain(A, D);
		System.out.println("T1: "+t1.getPath());
		Train t2 = ControlSystem.buildTrain(D, A);
		System.out.println("T2: "+t2.getPath());
	}
}

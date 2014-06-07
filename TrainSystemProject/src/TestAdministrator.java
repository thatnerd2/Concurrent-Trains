import java.util.ArrayList;


public class TestAdministrator {
	public static void main (String[] args) {
		ControlSystem.initialize();
		pathfindingTest();
		ControlSystem.startSimulation();
	}

	
	public static void pathfindingTest () {
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		MapManager.connectNodes(A, B, 2);
		MapManager.connectNodes(B, C, 1);
		MapManager.connectNodes(A, C, 7);
		MapManager.connectNodes(C, D, 2);
		MapManager.connectNodes(B, D, 3);
		Train t1 = ControlSystem.buildTrain(A, D);
		System.out.println("T1: "+t1.getPath());
		Train t2 = ControlSystem.buildTrain(D, A);
		System.out.println("T2: "+t2.getPath());
	}
}

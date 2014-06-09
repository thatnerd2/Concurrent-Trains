

public class TestAdministrator {
	public static void main (String[] args) {
		ControlSystem.initialize();
		pathfindingTest();
		
	}

	
	public static void pathfindingTest () {
		Node A = new Node("A");
		Node B = new Node("B");
		Node C = new Node("C");
		Node D = new Node("D");
		MapManager.connectNodes(A, C, 4);
		MapManager.connectNodes(B, C, 4);
		MapManager.connectNodes(C, D, 2);
		Train t1 = ControlSystem.buildTrain(A, D);
		System.out.println("T1: "+t1.getPath());
		Train t2 = ControlSystem.buildTrain(B, A);
		System.out.println("T2: "+t2.getPath());
		ControlSystem.startSimulation();
	}
}

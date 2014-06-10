

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
		Node E = new Node("E");
		Node F = new Node("F");
		MapManager.connectNodes(A, C, 5);
		MapManager.connectNodes(A, B, 3);
		MapManager.connectNodes(B, D, 4);
		MapManager.connectNodes(B, C, 2);
		MapManager.connectNodes(C, D, 1);
		MapManager.connectNodes(C, E, 3);
		MapManager.connectNodes(D, E, 4);
		MapManager.connectNodes(E, F, 6);
		Train t1 = ControlSystem.buildTrain(D, F);
		System.out.println("T1: "+t1.getPath());
		Train t2 = ControlSystem.buildTrain(D, A);
		System.out.println("T2: "+t2.getPath());
		Train t3 = ControlSystem.buildTrain(A, C);
		System.out.println("T3: "+t3.getPath());
		Train t4 = ControlSystem.buildTrain(F, D);
		System.out.println("T4: "+t4.getPath());
		Train t5 = ControlSystem.buildTrain(B, C);
		System.out.println("T5: "+t5.getPath());
		ControlSystem.startSimulation();
	}
}

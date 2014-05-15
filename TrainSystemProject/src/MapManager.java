
public class MapManager {
	public static void connectNodes (Node A, Node B, int distance) {
		A.addAdjacent(B, distance);
		B.addAdjacent(A, distance);
	}
}

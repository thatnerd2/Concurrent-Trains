import java.util.ArrayList;

//we decided not to use Path, right?
public class Path {
//	private ArrayList<Node> path;
//	//uses static aList "trains" from ControlSystem
//	public Path() {
//		path = new ArrayList<Node>();
//	}
//	public Path(ArrayList<Node> trainPath) {
//		path = trainPath;
//	}
//	public int size() {
//		return path.size();
//	}
//	public Node get(int index) {
//		return path.get(index);
//	}
//	public boolean isBlocked() {
//		for(int i = 0; i < path.size()-1; i++) {
//			for(Train train : ControlSystem.trains) {
//				Path checkPaths = ControlSystem.paths.get(train);
//				for(int j = 0; j < checkPaths.size()-1; j++) {
//					if(train.isBetween(checkPaths.get(j), checkPaths.get(j+1)))
//							return true;
//				}
//			}
//		}
//		return false;
//	}
}

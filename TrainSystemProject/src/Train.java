import java.util.ArrayList;


public class Train {
	private Node nodeFrom, nodeTo;
	private int arrivalTime, distanceFrom, speed;
	private ArrayList<Node> myPath;
	public Train () {}
	public Train(ArrayList<Node> path, int speed) {
		this.myPath = path;
		this.speed = speed;
		this.arrivalTime = TimeManager.getCurrentTime() + nodeFrom.getDistance(nodeTo);
		this.nodeFrom = myPath.get(0);
		myPath.remove(0);
		this.nodeTo = myPath.get(0);
		this.distanceFrom = nodeFrom.getDistance(nodeTo);
	}
	public void update() {
		if(nodeTo!=null) {
			distanceFrom -= speed;
			if(distanceFrom<=0) {
				if(myPath.size()>1) {
					updateDestination();
				}
				else {
					nodeFrom = myPath.get(0);
					nodeTo = null;
				}
			}
		}
		else {
			
		}
			
	}
	public void updateDestination() {
		nodeFrom = myPath.get(0);
		myPath.remove(0);
		nodeTo = myPath.get(0);
	}
	public ArrayList<Node> getPath() {
		return myPath;
	}
	public void setPath(ArrayList<Node> myPath) {
		this.myPath = myPath;
	}
	public int getDistanceFrom() {
		return distanceFrom;
	}
	public Node getNodeFrom() {
		return nodeFrom;
	}
	public void setNodeFrom(Node nodeFrom) {
		this.nodeFrom = nodeFrom;
	}
	public Node getNodeTo() {
		return nodeTo;
	}
	public void setNodeTo(Node nodeTo) {
		this.nodeTo = nodeTo;
	}
}

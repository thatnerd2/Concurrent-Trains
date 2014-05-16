import java.util.ArrayList;


public class Train {
	private Node nodeFrom, nodeTo;
	private int arrivalTime, distanceFrom, speed;
	private ArrayList<Node> myPath;
	public Train () {}
	public Train(ArrayList<Node> path, int speed) {
		this.myPath = path;
		this.speed = speed;
		updateDestination();
		this.arrivalTime = TimeManager.getCurrentTime() + nodeFrom.getDistance(nodeTo);		
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
	public boolean isBetween(Node node1, Node node2) {
		if((nodeFrom == node1 || nodeTo == node1) && (nodeFrom == nodeTo || nodeTo == node2))
			return true;
		else
			return false;
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
	public int getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public ArrayList<Node> getMyPath() {
		return myPath;
	}
	public void setMyPath(ArrayList<Node> myPath) {
		this.myPath = myPath;
	}
}

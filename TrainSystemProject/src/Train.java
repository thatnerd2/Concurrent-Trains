import java.util.ArrayList;

// remember to include whether or not things need to wait

public class Train {
	private Node nodeFrom, nodeTo;
	private int arrivalTime, distanceFrom;
	private ArrayList<Node> myPath;
	private ArrayList<Integer> myWaitTimes;
	
	public Train () {}
	
	public Train(ArrayList<Node> path, int speed) {
		this.myPath = path;
		this.myWaitTimes = null;
		nodeFrom = path.get(0);
		nodeTo = path.get(1);
		this.arrivalTime = ControlSystem.currentTime + nodeFrom.getDistance(nodeTo);
		this.distanceFrom = nodeFrom.getDistance(nodeTo);
	}
	//get proportion to samir, from node 
	public void update() {
		if(nodeTo!=null) {
			distanceFrom -= 1;
			if(distanceFrom<=0) {
				if(myPath.size()>2) {
					updateDestination();
				}
				else {
					myPath.remove(0);
					nodeFrom = myPath.get(0);
					nodeTo = null;
				}
			}
		}
	}
	
	public boolean isBetween(Node node1, Node node2) {
		if((nodeFrom == node1 || nodeTo == node1) && (nodeFrom == nodeTo || nodeTo == node2))
			return true;
		else
			return false;
	}
	
	public void updateDestination() {
		nodeFrom = myPath.get(1);
		myPath.remove(0);
		nodeTo = myPath.get(1);
		//myWaitTimes.remove(0);
	}
	
	public ArrayList<Integer> getWaitTimes() {
		return myWaitTimes;
	}
	public void setWaitTimes(ArrayList<Integer> myWaitTimes) {
		this.myWaitTimes = myWaitTimes;
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
}

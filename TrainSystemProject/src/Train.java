import java.util.ArrayList;

// remember to include whether or not things need to wait

public class Train {
	private Node nodeFrom, nodeTo;
	private int arrivalTime, distanceFrom;
	private Path myPath;
	
	public Train () {}
	
	public Train (Path path) {
		this.myPath = path;
		nodeFrom = path.getNodes().get(0);
		nodeTo = path.getNodes().get(1);
		this.arrivalTime = ControlSystem.currentTime + nodeFrom.getDistance(nodeTo);
		this.distanceFrom = nodeFrom.getDistance(nodeTo);
	}
	//get proportion to samir, from node 
	public void update() {
		if(nodeTo!=null) {
			distanceFrom -= 1;
			if(distanceFrom<=0) {
				if(myPath.getNodes().size()>2) {
					updateDestination();
				}
				else {
					myPath.getNodes().remove(0);
					nodeFrom = myPath.getNodes().get(0);
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
		ArrayList<Node> nodes = myPath.getNodes();
		ArrayList<Integer> waitTimes = myPath.getWaitTimes();
		nodeFrom = nodes.get(1);
		nodes.remove(0);
		nodeTo = nodes.get(1);
		waitTimes.remove(0);
	}
	
	public ArrayList<Integer> getWaitTimes() {
		return myPath.getWaitTimes();
	}
	
	public void setWaitTimes(ArrayList<Integer> myWaitTimes) {
		myPath.setWaitTimes(myWaitTimes);
	}
	
	public Path getPath() {
		return myPath;
	}
	
	public void setPath(Path myPath) {
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

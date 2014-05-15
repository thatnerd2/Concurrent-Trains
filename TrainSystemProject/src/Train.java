
public class Train {
	private Node nodeFrom, nodeTo;
	private int departureTime, arrivalTime;
	private int distanceFrom;
	public Train () {
		nodeFrom = new Node();
		nodeTo = new Node();
		departureTime = 0;
		arrivalTime = departureTime + ControlSystem.getTime(nodeFrom, nodeTo);
	}
	public Train(Node nodeFrom, Node nodeTo, int departureTime) {
		this.nodeFrom = nodeFrom;
		this.nodeTo = nodeTo;
		this.departureTime = departureTime;
		arrivalTime = departureTime + ControlSystem.getTime(this.nodeFrom, this.nodeTo);
	}
	//using update method for now
	public void update() {
		distanceFrom--;
	}
	public void setFromAndTo(Node nodeFrom, Node nodeTo) {
		this.nodeFrom = nodeFrom;
		this.nodeTo = nodeTo;
	}
	public int getDistanceFrom() {
		return distanceFrom;
	}
	public void setDistanceFrom(int distanceFrom) {
		this.distanceFrom = distanceFrom;
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
	public int getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(int departureTime) {
		this.departureTime = departureTime;
	}
}

import java.util.ArrayList;
import java.util.List;


public class Path {
	protected ArrayList<Node> nodes;
	protected ArrayList<Integer> waitTimes;
	public int totalTime;
	
	public Path() {
		nodes = new ArrayList<Node>();
		waitTimes = new ArrayList<Integer>();
		computeDistance();
	}
	
	public Path(ArrayList<Node> nodes, ArrayList<Integer> waitTimes) {
		super();
		this.nodes = nodes;
		this.waitTimes = waitTimes;
		computeDistance();
	}
	
	/**
	 * Computes the full amount of time
	 * @return
	 */
	public int computeDistance () {
		int sumTime = waitTimes.get(0); //Add first wait time.
		for (int i = 1; i < nodes.size(); i++) {
			Node chainFrom = nodes.get(i - 1);
			Node chainTo = nodes.get(i);
			sumTime += chainFrom.getDistance(chainTo) + waitTimes.get(i);
		}
		return sumTime;
	}
	
	public int computePathSubsetTime (int startIndex, int endIndex) {
		List<Integer> abstractSubsetWaitTimes = waitTimes.subList(startIndex, endIndex);
		List<Node> abstractSubsetNodes = nodes.subList(startIndex, endIndex);
		ArrayList<Integer> concreteWaitTimes = new ArrayList<Integer>();
		concreteWaitTimes.addAll(abstractSubsetWaitTimes);
		ArrayList<Node> concreteNodes = new ArrayList<Node>();
		concreteNodes.addAll(abstractSubsetNodes);
		
		int sumTime = waitTimes.get(0); //Add first wait time.
		for (int i = 1; i < concreteNodes.size(); i++) {
			Node chainFrom = concreteNodes.get(i - 1);
			Node chainTo = concreteNodes.get(i);
			sumTime += chainFrom.getDistance(chainTo) + waitTimes.get(i);
		}
		
		return sumTime;
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	
	public ArrayList<Integer> getWaitTimes() {
		return waitTimes;
	}
	
	public void setWaitTimes(ArrayList<Integer> waitTimes) {
		this.waitTimes = waitTimes;
	}
	
}

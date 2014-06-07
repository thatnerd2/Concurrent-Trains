import java.util.ArrayList;
import java.util.List;


public class Path {
	protected ArrayList<Node> nodes;
	protected ArrayList<Integer> waitTimes;
	public int totalTime;
	
	public Path() {
		nodes = new ArrayList<Node>();
		waitTimes = new ArrayList<Integer>();
		totalTime = computeTotalTime();
	}
	
	public Path(ArrayList<Node> nodes, ArrayList<Integer> waitTimes) {
		this.nodes = nodes;
		this.waitTimes = waitTimes;
		totalTime = computeTotalTime();
	}
	
	/**
	 * Computes the full amount of time required to make the trip
	 * @return
	 */
	public int computeTotalTime () {
		int sumTime = waitTimes.get(0); //Add first wait time.
		for (int i = 1; i < nodes.size(); i++) {
			Node chainFrom = nodes.get(i - 1);
			Node chainTo = nodes.get(i);
			sumTime += chainFrom.getDistance(chainTo) + waitTimes.get(i);
		}
		return sumTime;
	}
	
	public int computePathSubsetTime (int startIndex, int endIndex) {
		Path subset = getPathSubset(startIndex, endIndex);
		
		ArrayList<Node> nodes = subset.getNodes();
		ArrayList<Integer> waitTimes = subset.getWaitTimes();
		
		int sumTime = waitTimes.get(0); //Add first wait time.
		for (int i = 1; i < nodes.size(); i++) {
			Node chainFrom = nodes.get(i - 1);
			Node chainTo = nodes.get(i);
			sumTime += chainFrom.getDistance(chainTo) + waitTimes.get(i);
		}
		
		return sumTime;
	}
	
	/**
	 * Gets a subset of the path (nodes and waitTimes) from startIndex to endIndex INCLUSIVE
	 * @return
	 */
	public Path getPathSubset (int startIndex, int endIndex) {
		
		List<Integer> abstractSubsetWaitTimes = waitTimes.subList(startIndex, endIndex + 1);
		List<Node> abstractSubsetNodes = nodes.subList(startIndex, endIndex + 1);
		ArrayList<Integer> concreteWaitTimes = new ArrayList<Integer>();
		concreteWaitTimes.addAll(abstractSubsetWaitTimes);
		ArrayList<Node> concreteNodes = new ArrayList<Node>();
		concreteNodes.addAll(abstractSubsetNodes);
		
		Path res = new Path(concreteNodes, concreteWaitTimes);
		return res;
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
	
	public String toString () {
		return nodes.toString()+" "+waitTimes.toString();
	}
}

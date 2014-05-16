import java.util.ArrayList;
/*
 *  don't think we need this class lol
 */

public class Key {
	private ArrayList<Key> keyList;
	private Node keyNode;
	public Key() {
		keyList = new ArrayList<Key>();
		keyNode = new Node();
	}
	public Key(Node node) {
		keyNode = node;
		keyList = new ArrayList<Key>();
	}
	public ArrayList<Key> getKeyList() {
		return keyList;
	}
	public Node getKeyNode() {
		return keyNode;
	}
	public void add(Key key) {
		keyList.add(key);
	}
}

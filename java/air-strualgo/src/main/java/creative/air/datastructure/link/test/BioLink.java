package creative.air.datastructure.link.test;

import java.util.ArrayList;
/**
 * 02/05(May)/2012
 * Thomson Reuters
 * @author Administrator
 */
public class BioLink {
	private Node first;

	public void insert(Node e, Node n) {
		Node c = first;
		if (c == null) {
			first = n;
			return;
		}
		while (c != null) {
			if (c == e) {
				Node t = c.getNext();
				c.setNext(n);
				n.setPrevious(c);
				if (t != null) {
					n.setNext(t);
					t.setPrevious(n);
				}
				break;
			} else
				c = c.getNext();
		}
	}

	public void remove(Node r) {
		Node c = first;
		if (c == null) {
			return;
		}
		while (c != null) {
			if (c == r) {
				Node n = c.getNext();
				Node p = c.getPrevious();

				if (p != null) {
					p.setNext(n);
				}
				if (n != null) {
					n.setPrevious(p);
				}
				break;
			} else
				c = c.getNext();
		}
	}

	public void print() {
		Node c = first;
		if (c == null) {
			return;
		}
		while (c != null) {
			System.out.println(c.getData());
			c = c.getNext();
		}
	}

	public static void main(String[] args) {
		BioLink bioLinkTest = new BioLink();

		ArrayList<Node> temp = new ArrayList<Node>();
		for (int i = 0; i < 10; i++) {
			temp.add(new Node(i));
		}
		bioLinkTest.insert(null, temp.get(0));
		for (int i = 1; i < temp.size(); i++) {
			Node e = temp.get(i - 1);
			Node n = temp.get(i);
			bioLinkTest.insert(e, n);
		}
		bioLinkTest.print();
		bioLinkTest.remove(temp.get(3));
		bioLinkTest.remove(temp.get(6));
		bioLinkTest.print();
	}
}

class Node {
	public Node(Object data) {
		this.data = data;
	}

	private Node previous;
	private Node next;
	private Object data;

	public Node getPrevious() {
		return previous;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
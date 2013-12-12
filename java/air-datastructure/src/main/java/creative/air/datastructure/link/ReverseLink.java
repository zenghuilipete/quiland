package creative.air.datastructure.link;

/**
 * Created by erichan on 12/12/13.
 */
public class ReverseLink {
    public static void main(String[] args) {
        LinkNode a = new LinkNode("A");
        LinkNode b = new LinkNode("B");
        LinkNode c = new LinkNode("C");
        LinkNode d = new LinkNode("D");
        a.next = b;
        b.next = c;
        c.next = d;

        iter(a);
        reverse(a);
        iter(d);
        reverseMe(d);
        iter(a);
    }

    private static void iter(LinkNode current) {
        while (current != null) {
            System.out.println(current);
            current = current.next;
        }
        System.out.println("----");
    }

    public static LinkNode reverse(LinkNode current) {
        //initialization
        LinkNode previousNode = null;
        LinkNode nextNode = null;

        while (current != null) {
            //save the next node
            nextNode = current.next;
            //update the value of "next"
            current.next = previousNode;
            //shift the pointers
            previousNode = current;
            current = nextNode;
        }
        return previousNode;
    }

    public static LinkNode reverseMe(LinkNode current) {
        if (current == null || current.next == null) {
            return current;
        }
        LinkNode nextNode = current.next;
        current.next = null;
        LinkNode reverseNode = reverseMe(nextNode);
        nextNode.next = current;
        return reverseNode;
    }
}

class LinkNode {
    public String data;
    public LinkNode next;

    public LinkNode(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return data;
    }
}

package creative.air.datastructure.tree;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class SimpleTreeNode<T> {

    T data;
    SimpleTreeNode<T> parent;
    List<SimpleTreeNode<T>> children;

    public SimpleTreeNode(T data) {
        this.data = data;
        this.children = new LinkedList<SimpleTreeNode<T>>();
    }

    public SimpleTreeNode<T> addChild(T child) {
        SimpleTreeNode<T> childNode = new SimpleTreeNode<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }

    /*
     * http://en.wikipedia.org/wiki/File:Sorted_binary_tree_preorder.svg
     * Pre-order: F, B, A, D, C, E, G, I, H
     */
    public java.util.Queue<T> preOrder() {
        Queue<T> queue = new LinkedBlockingQueue<>();
        queue.add(data);
        preOrder0(queue, children);
        return queue;
    }

    private void preOrder0(Queue<T> queue, List<SimpleTreeNode<T>> children) {
        if (children == null || children.isEmpty()) {
            return;
        }
        for (SimpleTreeNode<T> node : children) {
            queue.add(node.data);
            preOrder0(queue, node.children);
        }
    }

    /*
     * http://en.wikipedia.org/wiki/File:Sorted_binary_tree_inorder.svg
     * In-order: A, B, C, D, E, F, G, H, I
     */
    public java.util.Queue<T> inOrder4BinaryTree() {
        Queue<T> queue = new LinkedBlockingQueue<>();
        return queue;
    }

    /*
     * http://en.wikipedia.org/wiki/File:Sorted_binary_tree_postorder.svg
     * Post-order: A, C, E, D, B, H, I, G, F
     */
    public java.util.Queue<T> postOrder4BinaryTree() {
        Queue<T> queue = new LinkedBlockingQueue<>();
        return queue;
    }

    /*
      F 
    B  G
    A D  I
    C E H
     */
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        SimpleTreeNode<String> root = new SimpleTreeNode<String>("F");
        {
            SimpleTreeNode<String> node0 = root.addChild("B");
            SimpleTreeNode<String> node1 = root.addChild("G");
            {
                SimpleTreeNode<String> node01 = node0.addChild("A");
                SimpleTreeNode<String> node02 = node0.addChild("D");
                SimpleTreeNode<String> node11 = node1.addChild("I");
                {
                    SimpleTreeNode<String> node021 = node02.addChild("C");
                    SimpleTreeNode<String> node022 = node02.addChild("E");
                    SimpleTreeNode<String> node111 = node11.addChild("H");
                }
            }
        }
        Queue<String> preOrderQueue = root.preOrder();
        for (String node : preOrderQueue) {
            System.out.println(node);
        }
    }
}

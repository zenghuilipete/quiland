package creative.priorityqueue;

import creative.air.datastructure.heap.AirPriorityQueue;

import java.util.PriorityQueue;

/**
 * Created by erichan on 1/7/14.
 */
public class PriorityQueueTest {
    /*
    堆：完全二叉樹
        6
       / \
      10 18
     / \
    23 44

    A[i]的左右兒子 A[2i] A[2i+1] 父結點是A[i/2]
    */
    public static void main(String[] args) {
        AirPriorityQueue<Integer> pq = new AirPriorityQueue<>(5);
        /*first time*/
        pq.offer(18);
        pq.offer(44); // see stack trace
        pq.offer(6);
        pq.offer(23);
        pq.offer(10);

        System.out.println();
        Integer[] es = pq.toArray(new Integer[]{});
        for (Integer e : es) {
            System.out.print(e + "\t");
        }
        System.out.println();

        while (pq.size() > 0) {
            pq.poll();    //see stack trace
        }
        /*second time*/
        //secondTime(pq);
    }

    private static void secondTime(AirPriorityQueue<Integer> pq) {
        Integer[] es;
        pq.offer(6);
        pq.offer(10);
        pq.offer(44);
        pq.offer(23);
        pq.offer(18);
        System.out.println();
        es = pq.toArray(new Integer[]{});
        for (Integer e : es) {
            System.out.print(e + "\t");
        }
        System.out.println();

        Integer e;
        while ((e = pq.poll()) != null) {
            System.out.print(e + "\t");
        }
    }

}

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
        pq.add(18);
        pq.add(44); // see stack trace
        pq.add(6);
        pq.add(23);
        pq.add(10);

        System.out.println();
        Integer[] es = pq.toArray(new Integer[]{});
        for (Integer e : es) {
            System.out.print(e + "\t");
        }
        System.out.println();

        Integer i;
        while ((i = pq.poll()) != null) { //see stack trace
            System.out.println(i);
        }
        pq.clear();

        pq.add(6);
        pq.add(10);
        pq.add(44);
        pq.add(23);
        pq.add(18);
        System.out.println();
        es = pq.toArray(new Integer[]{});
        for (Integer e : es) {
            System.out.print(e + "\t");
        }
        System.out.println();

        Integer e;
        while ((e = pq.poll()) != null) {
            System.out.println(e);
        }
    }

}

package creative.fire.concurrent.collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Producer_Consumer_Pattern {
	public static void main(String[] args) {
		BlockingQueue<String> q = new ArrayBlockingQueue<String>(10);
		Producer p = new Producer(q);
		Consumer c1 = new Consumer(q);
		Consumer c2 = new Consumer(q);
		new Thread(p).start();
		new Thread(c1).start();
		new Thread(c2).start();
	}
}

class Producer implements Runnable {
	private final BlockingQueue<String> queue;

	Producer(BlockingQueue<String> q) {
		queue = q;
	}

	public void run() {
		try {
			while (true) {
				queue.put(produce());
			}
		} catch (InterruptedException ex) {

		}
	}

	String produce() {
		return "1";
	}
}

class Consumer implements Runnable {
	private final BlockingQueue<String> queue;

	Consumer(BlockingQueue<String> q) {
		queue = q;
	}

	public void run() {
		try {
			while (true) {
				consume(queue.take());
			}
		} catch (InterruptedException ex) {
		}
	}

	void consume(String x) {
		System.out.println(Thread.currentThread().getName() + " " + x);
	}
}

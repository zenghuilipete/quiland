package creative.fire.concurrent.edu.cp;

import java.util.concurrent.*;

public class Three {
	private LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<Object>(10);
	private int MAX = 10;

	public Three() {
	}

	public void start() {
		new Producer().start();
		new Consumer().start();
	}

	public static void main(String[] args) throws Exception {
		Three s3 = new Three();
		s3.start();
	}

	class Producer extends Thread {
		public void run() {
			while (true) {
				// synchronized(this){
				try {
					if (queue.size() == MAX)
						System.out.println("warning: it's full!");
					Object o = new Object();
					queue.put(o);
					System.out.println("Producer: " + o);
				} catch (InterruptedException e) {
					System.out.println("producer is interrupted!");
				}
				// }
			}
		}
	}

	class Consumer extends Thread {
		public void run() {
			while (true) {
				// synchronized(this){
				try {
					if (queue.size() == 0)
						System.out.println("warning: it's empty!");
					Object o = queue.take();
					System.out.println("Consumer: " + o);
				} catch (InterruptedException e) {
					System.out.println("producer is interrupted!");
				}
				// }
			}
		}
	}

}

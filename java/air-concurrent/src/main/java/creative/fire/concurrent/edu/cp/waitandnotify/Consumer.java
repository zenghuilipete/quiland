package creative.fire.concurrent.edu.cp.waitandnotify;

import java.util.ArrayList;

/**
 * @author feuyeux@gmail.com 2011-3-12
 */
public class Consumer extends Thread {
	private ArrayList<Integer> list;

	public Consumer(ArrayList<Integer> list) {
		this.list = list;
	}

	public void run() {
		while (true) {
			synchronized (list) {
				try {
					while (list.size() == 0) {
						System.out.println("warning: it's empty!");
						list.wait();
					}
					Integer o = list.remove(0);
					System.out.println("Consumer: " + o);
					list.notify();
				} catch (InterruptedException ie) {
					System.out.println("consumer is interrupted!");
				}
			}
		}
	}
}
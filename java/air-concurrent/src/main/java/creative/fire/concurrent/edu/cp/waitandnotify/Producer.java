package creative.fire.concurrent.edu.cp.waitandnotify;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author feuyeux@gmail.com 2011-3-12
 */
class Producer extends Thread {
	private ArrayList<Integer> list;
	private int MAX = 10;

	public Producer(ArrayList<Integer> list) {
		this.list = list;
	}

	public void run() {
		while (true) {
			synchronized (list) {
				try {
					while (list.size() == MAX) {
						System.out.println("warning: it's full!");
						list.wait();
					}

					int a = produceNumber();
					if (list.add(a)) {
						System.out.println("Producer: " + a);
						list.notify();
					}
				} catch (InterruptedException ie) {
					System.out.println("producer is interrupted!");
				}
			}
		}
	}

	private final Random random = new Random();
	private final int min = 32;
	private final int max = 64;

	private int produceNumber() {
		return random.nextInt(max) % (max - min + 1) + min;
	}
}

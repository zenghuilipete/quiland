package creative.fire.concurrent.pool;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Delayed implementation that actually delays
 */
public class NanoDelay implements Delayed {
	long trigger;

	NanoDelay(long i) {
		trigger = System.nanoTime() + i;
	}

	@Override
	public int compareTo(Delayed o) {
		long i = trigger;
		long j = ((NanoDelay) o).trigger;
		if (i < j) {
			return -1;
		}
		if (i > j) {
			return 1;
		}
		return 0;
	}

	@Override
	public boolean equals(Object other) {
		return ((NanoDelay) other).trigger == trigger;
	}

	public boolean equals(NanoDelay other) {
		return other.trigger == trigger;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long n = trigger - System.nanoTime();
		return unit.convert(n, TimeUnit.NANOSECONDS);
	}

	public long getTriggerTime() {
		return trigger;
	}

	@Override
	public String toString() {
		return String.valueOf(trigger);
	}

	public static void main(String args[]) throws InterruptedException {
		Random random = new Random();
		DelayQueue<NanoDelay> queue = new DelayQueue<NanoDelay>();
		for (int i = 0; i < 5; i++) {
			queue.add(new NanoDelay(random.nextInt(1000)));
		}
		long last = 0;
		for (int i = 0; i < 5; i++) {
			NanoDelay delay = queue.take();
			long tt = delay.getTriggerTime();
			System.out.println("Trigger time: " + tt);
			if (i != 0) {
				System.out.println("Delta: " + (tt - last));
			}
			last = tt;
		}
	}
}

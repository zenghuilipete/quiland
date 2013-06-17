package creative.fire.aop;

import java.util.concurrent.TimeUnit;

public class Task implements ITask {
	@Override
	public long go() {
		try {
			TimeUnit.MICROSECONDS.sleep(100);
			System.out.println(this.getClass().getName() + " says: go.");
		} catch (InterruptedException e) {
			System.err.println(e);
		}
		return System.currentTimeMillis();
	}

	public static void main(String[] args) throws InterruptedException {
		Task t = new Task();
		t.go();
	}
}

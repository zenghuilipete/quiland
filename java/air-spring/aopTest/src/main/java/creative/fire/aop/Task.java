package creative.fire.aop;

import java.util.concurrent.TimeUnit;

public class Task implements ITask {
	public void go() {
		try {
			TimeUnit.MICROSECONDS.sleep(100);
		} catch (InterruptedException e) {
		}
		System.out.println("Done.");
	}

	public static void main(String[] args) throws InterruptedException {
		Task t = new Task();
		t.go();
	}
}

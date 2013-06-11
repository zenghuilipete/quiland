package creative.fire.multithread;

/**
 * @author feuyeux@gmail.com 2011-10-13
 * 
 */
public class DeadLockTest extends Thread {
	private final int timeout1 = 2 * 1000;
	private final int timeout2 = 4 * 1000;

	private static Object o1 = new Object();
	private static Object o2 = new Object();

	public void a(String name) throws InterruptedException {
		synchronized (o1) {
			System.out.println(name + " lock o1");
			Thread.sleep(timeout1);
			System.out.println(name + " try to lock o2...");
			synchronized (o2) {
				System.out.println(name + " lock o2");
			}
			System.out.println(name + " unlock o2");
		}
		System.out.println(name + " unlock o1");
	}

	public synchronized void b(String name) throws InterruptedException {
		synchronized (o2) {
			System.out.println(name + " lock o2");
			Thread.sleep(timeout2);
			System.out.println(name + " try to lock o1...");
			synchronized (o1) {
				System.out.println(name + " lock o1");
			}
			System.out.println(name + " unlock o1");
		}
		System.out.println(name + " unlock o2");
	}

	public static void main(String[] args) {
		final DeadLockTest thiz1 = new DeadLockTest();
		final DeadLockTest thiz2 = new DeadLockTest();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					thiz1.a("1");
					thiz1.b("1");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					thiz2.b("2");
					thiz2.a("2");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}

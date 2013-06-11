package creative.fire.multithread;

import java.util.concurrent.CountDownLatch;

/**
 * @author feuyeux@gmail.com 2011-10-13
 * 
 */

/*
 * synchronized static void foo() { ... } is the same as
 * 
 * static void foo() { synchronized(SomeClass.class) { ... } }
 * 
 * synchronized void foo() { ... } is the same as
 * 
 * void foo() { synchronized(this) { ... } }
 */
public class LockMethodTest {
	private static final int timeout = 3 * 1000;

	public synchronized void a(String name) throws InterruptedException {
		System.out.println(name + " enter a");
		Thread.sleep(timeout);
		System.out.println("quit a");
	}

	public synchronized void b() throws InterruptedException {
		System.out.println("enter b");
		Thread.sleep(timeout);
		System.out.println("quit b");
	}

	public synchronized static void c() throws InterruptedException {
		System.out.println("enter c");
		Thread.sleep(timeout);
		System.out.println("quit c");
	}

	public static void main(String[] args) throws InterruptedException {
		testInSameObject();
		System.out.println("--------");
		testInTwoObjects();
		System.out.println("--------");
		testClassObject();
	}

	private static void testClassObject() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);
		final LockMethodTest thiz = new LockMethodTest();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					LockMethodTest.c();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				latch.countDown();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					thiz.a("object");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				latch.countDown();
			}
		}).start();
		latch.await();
	}

	private static void testInTwoObjects() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);
		final LockMethodTest thiz1 = new LockMethodTest();
		final LockMethodTest thiz2 = new LockMethodTest();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					thiz1.a("object1");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				latch.countDown();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					thiz2.a("object2");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				latch.countDown();
			}
		}).start();
		latch.await();
	}

	private static void testInSameObject() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(2);

		final LockMethodTest thiz = new LockMethodTest();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					thiz.a("object");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				latch.countDown();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					thiz.b();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				latch.countDown();
			}
		}).start();
		latch.await();
	}
}

//package creative.fire.concurrent.synchronizer;
//
//import java.util.concurrent.Semaphore;
//import java.util.concurrent.TimeUnit;
//
//@ThreadSafe
//public class SemaphoreCollection<E> {
//	private final Semaphore availableItems, availableSpaces;
//	@GuardedBy("this")
//	private final E[] items;
//	@GuardedBy("this")
//	private int putPosition = 0, takePosition = 0;
//
//	public SemaphoreCollection(int capacity) {
//		if (capacity <= 0)
//			throw new IllegalArgumentException();
//		availableItems = new Semaphore(0);
//		availableSpaces = new Semaphore(capacity);
//		items = (E[]) new Object[capacity];
//	}
//
//	public boolean isEmpty() {
//		return availableItems.availablePermits() == 0;
//	}
//
//	public boolean isFull() {
//		return availableSpaces.availablePermits() == 0;
//	}
//
//	public void put(E x) throws InterruptedException {
//		if (availableSpaces.tryAcquire(2, TimeUnit.SECONDS)) {
//			System.out.println("availableSpaces acquire");
//			System.out.println("available Spaces="+availableSpaces.availablePermits());
//			doInsert(x);
//			availableItems.release();
//			System.out.println("availableItems release");
//			System.out.println("available Items="+availableItems.availablePermits());
//		} else {
//			System.out.println("time out.");
//		}
//	}
//
//	public E take() throws InterruptedException {
//		// availableItems.acquire();
//		if (availableItems.tryAcquire(2, TimeUnit.SECONDS)) {
//			System.out.println("availableItems acquire");
//			System.out.println("available Items="+availableItems.availablePermits());
//			E item = doExtract();
//			availableSpaces.release();
//			System.out.println("availableSpaces release");
//			System.out.println("available Spaces="+availableSpaces.availablePermits());
//			return item;
//		} else {
//			System.out.println("time out.");
//			return null;
//		}
//	}
//
//	private synchronized void doInsert(E x) {
//		int i = putPosition;
//		items[i] = x;
//		putPosition = (++i == items.length) ? 0 : i;
//		System.out.println("insert " + x);
//	}
//
//	private synchronized E doExtract() {
//		int i = takePosition;
//		E x = items[i];
//		items[i] = null;
//		takePosition = (++i == items.length) ? 0 : i;
//		System.out.println("extract " + x);
//		return x;
//	}
//
//	public static void main(String[] ss) throws InterruptedException {
//		final SemaphoreCollection<Integer> buffer = new SemaphoreCollection<Integer>(
//				2);
//		buffer.put(1);
//		System.out.println("---- ----");
//		buffer.put(2);
//		System.out.println("---- ----");
//		buffer.put(3);
//		System.out.println("---- ----");
//		buffer.take();
//		System.out.println("---- ----");
//		buffer.take();
//		System.out.println("---- ----");
//		buffer.take();
//	}
//}

package creative.fire.pattern.callback4.observer;

//un thread safe
public class Counter {
	private long count;

	public long getCount() {
		return count;
	}

	public void count() {
		count++;
	}
}

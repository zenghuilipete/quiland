package creative.fire.pattern.callback3.composite;

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

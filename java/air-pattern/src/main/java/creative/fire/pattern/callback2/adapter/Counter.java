package creative.fire.pattern.callback2.adapter;

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

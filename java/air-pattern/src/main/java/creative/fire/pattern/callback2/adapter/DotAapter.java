package creative.fire.pattern.callback2.adapter;

public class DotAapter implements ICallback {
	private final Counter counter;

	public DotAapter(Counter counter) {
		this.counter = counter;
	}

	@Override
	public void invoke(String info) {
		counter.count();
		System.out.println("Time(" + counter.getCount() + ") ...\t" + info);
	}
}

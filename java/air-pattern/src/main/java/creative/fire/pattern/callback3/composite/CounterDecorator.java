package creative.fire.pattern.callback3.composite;

//un thread safe
public class CounterDecorator extends Counter implements ICallback {

	@Override
	public void invoke(String info) {
		count();
		System.out.println("Time(" + getCount() + ") ...\t" + info);
	}
}

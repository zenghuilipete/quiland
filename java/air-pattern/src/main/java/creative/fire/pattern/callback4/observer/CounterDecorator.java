package creative.fire.pattern.callback4.observer;

//un thread safe
public class CounterDecorator extends Counter implements CallbackObserver {

	@Override
	public void notify(ExecuteObservable executor) {
		count();
		System.out.println("Time(" + getCount() + ") ...\t" + executor.getInfo());
	}
}

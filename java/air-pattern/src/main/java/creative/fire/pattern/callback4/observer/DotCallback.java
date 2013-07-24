package creative.fire.pattern.callback4.observer;

public class DotCallback implements CallbackObserver {

	@Override
	public void notify(ExecuteObservable executor) {
		System.out.println("...\t" + executor.getInfo());

	}
}

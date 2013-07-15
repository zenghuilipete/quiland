package creative.fire.pattern.callback4.observer;

public class Execute implements ExecuteObservable {
	private CallbackObserver observer;
	private String info;

	public void service(int callTime) {
		for (int i = 0; i < callTime; i++) {
			info = "processing " + i;
			observer.notify(this);
		}
	}

	@Override
	public void registerObserver(CallbackObserver observer) {
		this.observer = observer;
	}

	@Override
	public String getInfo() {
		return info;
	}
}

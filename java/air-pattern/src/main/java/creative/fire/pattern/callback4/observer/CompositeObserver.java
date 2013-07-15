package creative.fire.pattern.callback4.observer;

import java.util.ArrayList;
import java.util.List;

//CompositeCallback->
public class CompositeObserver implements CallbackObserver {
	private final List<CallbackObserver> parts;

	public CompositeObserver() {
		parts = new ArrayList<CallbackObserver>();
	}

	public void add(CallbackObserver part) {
		parts.add(part);
	}

	@Override
	public void notify(ExecuteObservable executor) {
		for (CallbackObserver part : parts) {
			part.notify(executor);
		}
	}
}

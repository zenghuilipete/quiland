package creative.fire.pattern.callback4.observer;

import org.junit.Test;

public class TestCallback4 {

	private int callTime = 3;

	@Test
	public void testCompositeCallback() {
		System.out.println("Test Dot Callback:");
		Execute callee = new Execute();
		CompositeObserver compositeCallback = new CompositeObserver();
		compositeCallback.add(new CounterDecorator());
		compositeCallback.add(new DotCallback());
		callee.registerObserver(compositeCallback);
		callee.service(callTime);
	}
}

package creative.fire.pattern.callback3.composite;

import org.junit.Test;

public class TestCallback3 {

	private int callTime = 3;

	@Test
	public void testCompositeCallback() {
		System.out.println("Test Dot Callback:");
		Execute callee = new Execute();
		CompositeCallback compositeCallback = new CompositeCallback();
		compositeCallback.register(new CounterDecorator());
		compositeCallback.register(new DotCallback());
		callee.service(compositeCallback, callTime);
	}
}

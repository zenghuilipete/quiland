package creative.fire.pattern.callback1.strategy;

import org.junit.Test;

public class TestCallback1 {

	private int callTime = 3;

	@Test
	public void testDotCallback() {
		System.out.println("Test Dot Callback:");
		Execute callee = new Execute();
		callee.service(new DotCallback(), callTime);
	}

	@Test
	public void testSlashCallback() {
		System.out.println("\nTest Slash Callback:");
		Execute callee = new Execute();
		callee.service(new SlashCallback(), callTime);
	}

	@Test
	public void testAnonymousCallback() {
		System.out.println("\nTest Anonymous Callback:");
		Execute callee = new Execute();
		callee.service(new ICallback() {
			@Override
			public void invoke(String info) {
				System.out.println("Anonymous invoke: " + info);

			}
		}, callTime);
	}

	class Adhoc implements ICallback {
		@Override
		public void invoke(String info) {
			System.out.println("Adhoc invoke: " + info);
		}
	}

	@Test
	public void testAdhocCallback() {
		System.out.println("\nTest Ad hoc Callback:");
		Execute callee = new Execute();
		callee.service(new Adhoc(), callTime);
	}
}

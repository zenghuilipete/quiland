package creative.fire.pattern.callback2.adapter;

import org.junit.Test;

public class TestCallback2 {

	private int callTime = 3;

	@Test
	public void testDotAapterCallback() {
		System.out.println("Test Dot Callback:");
		Execute callee = new Execute();
		callee.service(new DotAapter(new Counter()), callTime);
	}

}

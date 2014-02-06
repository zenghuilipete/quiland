package creative.fire.pattern.callback3.composite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class TestCallback3 {
    private Logger logger = LogManager.getLogger(TestCallback3.class);
	private int callTime = 3;

	@Test
	public void testCompositeCallback() {
        logger.info("Test Dot Callback:");
		Execute callee = new Execute();
		CompositeCallback compositeCallback = new CompositeCallback();
		compositeCallback.register(new CounterDecorator());
		compositeCallback.register(new DotCallback());
		callee.service(compositeCallback, callTime);
	}
}

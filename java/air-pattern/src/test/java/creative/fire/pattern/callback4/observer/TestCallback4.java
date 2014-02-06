package creative.fire.pattern.callback4.observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

public class TestCallback4 {
    private Logger logger = LogManager.getLogger(TestCallback4.class);
    private int callTime = 3;

    @Test
    public void testCompositeCallback() {
        logger.info("Test Dot Callback:");
        Execute callee = new Execute();
        callee.registerObserver(new CounterDecorator());
        callee.registerObserver(new DotCallback());
        callee.service(callTime);
    }
}

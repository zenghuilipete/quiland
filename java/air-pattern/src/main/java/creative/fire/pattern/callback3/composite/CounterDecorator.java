package creative.fire.pattern.callback3.composite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//un thread safe
public class CounterDecorator extends Counter implements ICallback {
    private Logger logger = LogManager.getLogger(CounterDecorator.class);

    @Override
    public void invoke(String info) {
        count();
        logger.info("Time({}) ...\t{}", getCount(), info);
    }
}

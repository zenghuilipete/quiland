package creative.fire.pattern.callback4.observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//un thread safe
public class CounterDecorator extends Counter implements CallbackObserver {
    private Logger logger = LogManager.getLogger(CounterDecorator.class);
	@Override
	public void update(ExecuteObservable executor) {
		count();
        logger.info("Time(" + getCount() + ") ...\t" + executor.getInfo());
	}
}

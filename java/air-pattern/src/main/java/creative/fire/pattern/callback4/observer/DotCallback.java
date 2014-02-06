package creative.fire.pattern.callback4.observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DotCallback implements CallbackObserver {
    private Logger logger = LogManager.getLogger(DotCallback.class);

    @Override
    public void update(ExecuteObservable executor) {
        logger.info("...\t" + executor.getInfo());
    }
}

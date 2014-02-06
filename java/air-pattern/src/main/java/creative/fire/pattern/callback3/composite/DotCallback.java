package creative.fire.pattern.callback3.composite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DotCallback implements ICallback {
    private Logger logger = LogManager.getLogger(DotCallback.class);

    @Override
    public void invoke(String info) {
        logger.info("...\t" + info);
    }
}

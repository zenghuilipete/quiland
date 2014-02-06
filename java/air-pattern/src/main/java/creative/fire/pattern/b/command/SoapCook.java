package creative.fire.pattern.b.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoapCook {
    private Logger logger = LogManager.getLogger(getClass());
    private static SoapCook instance = new SoapCook();

    public void action() {
        logger.info("soap cooking...");
    }

    public static SoapCook getInstance() {
        return instance;
    }
}

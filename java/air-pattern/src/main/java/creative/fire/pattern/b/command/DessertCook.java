package creative.fire.pattern.b.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DessertCook {
    private static DessertCook instance = new DessertCook();
    private Logger logger = LogManager.getLogger(getClass());

    public void action() {
        logger.info("dessert cooking...");
    }

    public static DessertCook getInstance() {
        return instance;
    }
}

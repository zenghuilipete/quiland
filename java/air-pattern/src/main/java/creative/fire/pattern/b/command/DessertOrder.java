package creative.fire.pattern.b.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DessertOrder implements Order {
    private Logger logger = LogManager.getLogger(getClass());
    private DessertCook cook=DessertCook.getInstance();

    @Override
    public void execute() {
        logger.info("Making dessert order...");
        cook.action();
    }
}

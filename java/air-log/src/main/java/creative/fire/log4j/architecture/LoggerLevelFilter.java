package creative.fire.log4j.architecture;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class LoggerLevelFilter {

	public static void main(String[] args) {
		BasicConfigurator.configure();

		Logger logger = Logger.getLogger("com.foo");

		logger.setLevel(Level.INFO);

		Logger barLogger = Logger.getLogger("com.foo.Bar");

		logger.warn("0");

		logger.debug("1");

		barLogger.info("2");

		barLogger.debug("3");
	}
}

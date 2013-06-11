package creative.fire.log4j.architecture;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerRepository;

public class HierarchyWideThreshold {
	public static void main(String[] args) {
		BasicConfigurator.configure();

		Logger x = Logger.getLogger("foo.bar");
		x.setLevel(Level.INFO);

		LoggerRepository repository = x.getLoggerRepository();

		repository.setThreshold(Level.WARN);

		x.info("0");

		repository.setThreshold(Level.OFF);

		x.fatal("1");

		repository.setThreshold(Level.ALL);

		x.info("2");

		x.debug("3");
	}
}

package creative.fire.log4j.configuration;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class DefaultConfiguration {
	final static Logger logger = Logger.getLogger(DefaultConfiguration.class);

	public static void main(String[] args) {
		BasicConfigurator.configure();
		logger.info("Entering application.");
		Foo foo = new Foo();
		foo.doIt();
		logger.info("Exiting application.");
	}
}

class Foo {
	final static Logger logger = Logger.getLogger(Foo.class);

	public void doIt() {
		logger.debug("...");
	}
}
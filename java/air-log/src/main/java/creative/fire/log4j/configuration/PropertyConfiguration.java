package creative.fire.log4j.configuration;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PropertyConfiguration {
	final static Logger logger = Logger.getLogger(PropertyConfiguration.class);

	public static void main(String[] args) {
		PropertyConfigurator.configure("1124.properties");
		logger.info("Entering application.");
		Foo foo = new Foo();
		foo.doIt();
		logger.info("Exiting application.");
	}
}

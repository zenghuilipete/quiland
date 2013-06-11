package creative.fire.log4j.configuration;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class DOMConfiguration {
	final static Logger logger = Logger.getLogger(DOMConfiguration.class);

	public static void main(String[] args) {
		DOMConfigurator.configure("1124.xml");
		logger.info("Entering application.");
		Foo foo = new Foo();
		foo.doIt();
		logger.info("Exiting application.");
	}
}

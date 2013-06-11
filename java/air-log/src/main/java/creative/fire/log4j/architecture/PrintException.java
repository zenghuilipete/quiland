package creative.fire.log4j.architecture;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class PrintException {
	public static void main(String[] args) {
		Logger log = Logger.getLogger("mars.feu");

		BasicConfigurator.configure();

		try {
			kickzero();
		} catch (Exception e) {
			log.error(e);
			log.debug("***");
			log.error("error!", e);
		}
	}

	private static void kickzero() {
		int x = 9527 / 0;
		System.out.println(x);
	}
}

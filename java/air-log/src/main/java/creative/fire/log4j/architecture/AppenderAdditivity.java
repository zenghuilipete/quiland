package creative.fire.log4j.architecture;

import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class AppenderAdditivity {

	public static void main(String[] args) throws IOException {
		FileAppender a0 = new FileAppender(new SimpleLayout(), "a0.log");
		FileAppender a1 = new FileAppender(new SimpleLayout(), "a1.log");
		FileAppender as = new FileAppender(new SimpleLayout(), "as.log");

		Logger root = Logger.getRootLogger();
		root.addAppender(a0);

		Logger x = Logger.getLogger("x");
		x.addAppender(a1);

		Logger xy = Logger.getLogger("x.y");

		Logger xyz = Logger.getLogger("x.y.z");
		xyz.addAppender(as);
		xyz.setAdditivity(false);

		xy.debug("0");
		xy.info("1");
		xyz.debug("s");
	}
}

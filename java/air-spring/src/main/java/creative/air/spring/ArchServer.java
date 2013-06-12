package creative.air.spring;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component(value="arch")
public class ArchServer implements VMDevice {
	private final Logger	logger	= Logger.getLogger(this.getClass());

	public ArchServer() {
		logger.info("ArchServer construct");
	}

	public void install() {
		logger.info("ArchServer install");
	}

	public void configure() {
		logger.info("ArchServer configure");
	}

	public void running() {
		logger.info("ArchServer running");
	}

	public Boolean playing() {
		logger.info("ArchServer playing");
		return Boolean.TRUE;
	}
}

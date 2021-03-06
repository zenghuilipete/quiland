package creative.air.spring.ioc;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component(value = "arch")
public class ArchServer implements VMDevice {
	private final Logger logger = Logger.getLogger(this.getClass());

	public ArchServer() {
		logger.info("ArchServer construct");
	}

	@Override
	public void install() {
		logger.info("ArchServer install");
	}

	@Override
	public void configure() {
		logger.info("ArchServer configure");
	}

	@Override
	public void running() {
		logger.info("ArchServer running");
	}

	@Override
	public Boolean playing() {
		logger.info("ArchServer playing");
		return Boolean.TRUE;
	}
}

package creative.air.spring.ioc;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component(value="ci")
public class CIServer implements VMDevice {
	private final Logger logger = Logger.getLogger(this.getClass());

	public CIServer() {
		logger.info("CIServer construct");
	}

	@Override
	public void install() {
		logger.info("CIServer install");
	}

	@Override
	public void configure() {
		logger.info("CIServer configure");
	}

	@Override
	public void running() {
		logger.info("CIServer running");
	}

	@Override
	public Boolean playing() {
		logger.info("CIServer playing");
		return Boolean.TRUE;
	}
}
package creative.air.spring;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class CIServer implements VMDevice {
	private final Logger logger = Logger.getLogger(this.getClass());

	public CIServer() {
		logger.debug("CIServer construct");
	}

	@Override
	public void install() {
		logger.debug("CIServer install");
	}

	@Override
	public void configure() {
		logger.debug("CIServer configure");
	}

	@Override
	public void running() {
		logger.debug("CIServer running");
	}

	@Override
	public Boolean playing() {
		logger.debug("CIServer playing");
		return Boolean.TRUE;
	}
}
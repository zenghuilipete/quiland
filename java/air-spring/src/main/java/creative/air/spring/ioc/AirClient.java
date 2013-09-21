package creative.air.spring.ioc;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * @author feuyeux@gmail.com
 * 21/09/2013
 */
@Component(value = "client")
public class AirClient {
    private final Logger logger = Logger.getLogger(this.getClass());
    private String serverHost;

    public AirClient() {
        logger.info("AirClient construct");
    }

    public AirClient(String serverHost) {
        this.serverHost = serverHost;
        logger.info("AirClient construct serverHost=" + serverHost);
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String playing() {
        logger.info("AirClient playing");
        return "PLAYING";
    }
}

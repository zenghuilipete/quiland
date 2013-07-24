package creative.fire.pattern.crazyif.strategy;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class TestDevice {
	Logger log = Logger.getLogger(getClass());

	@Test
	public void testDevice() {
		IDevice gateway = new Gateway105("10.11.58.184");
		IDevice tablet = new Tablet2("10.11.58.141");
		String communication1 = tablet.call("hello world");
		String communication2 = gateway.call("hello world");

		log.debug(communication1);
		Assert.assertEquals("Android-C-Linux--10.11.58.141--hello world", communication1);
		log.debug(communication2);
		Assert.assertEquals("openwrt::10.11.58.184::hello world", communication2);
	}
}

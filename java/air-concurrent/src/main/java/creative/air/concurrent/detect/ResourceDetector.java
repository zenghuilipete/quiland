package creative.air.concurrent.detect;

import java.util.Date;
import java.util.concurrent.Callable;

public class ResourceDetector implements Callable<DeviceDomain> {

	private DeviceDomain deviceDomain;

	public ResourceDetector(DeviceDomain deviceDomain) {
		this.deviceDomain = deviceDomain;
	}

	@Override
	public DeviceDomain call() throws Exception {
		System.out.println("");
		deviceDomain.setLastUpdate(new Date());
		return deviceDomain;
	}
}

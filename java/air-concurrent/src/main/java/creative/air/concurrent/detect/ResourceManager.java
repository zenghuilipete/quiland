package creative.air.concurrent.detect;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class ResourceManager {
	private List<Future<DeviceDomain>> resultList = new ArrayList<Future<DeviceDomain>>();

	List<DeviceDomain> getDevices() {
		List<DeviceDomain> devices = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			devices.add(new DeviceDomain("DEVICE_" + i));
		}
		return devices;
	}

	public void detectDevices() throws InterruptedException, ExecutionException {
		List<DeviceDomain> devices = getDevices();
		int count = devices.size();
		ExecutorService threadPool = Executors.newFixedThreadPool(count);
		for (int i = 0; i < count; ++i) {
			ResourceDetector detector = new ResourceDetector(devices.get(i));
			FutureTask<DeviceDomain> task = new FutureTask<DeviceDomain>(detector);
			resultList.add(task);
			if (!threadPool.isShutdown()) {
				threadPool.submit(task);
			}
		}

		for (Future<DeviceDomain> result : resultList) {
			DeviceDomain deviceDomain = result.get();
			System.out.println(deviceDomain);
		}

		threadPool.shutdown();
	}
}

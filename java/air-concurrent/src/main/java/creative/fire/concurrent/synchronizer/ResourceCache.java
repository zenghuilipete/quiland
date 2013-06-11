package creative.fire.concurrent.synchronizer;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ResourceCache {
	private static ConcurrentHashMap<String, FutureTask<Resource>> resourceMap = 
			new ConcurrentHashMap<String, FutureTask<Resource>>(1000, 0.7f);

	private Resource retrieveFromDB(String resId) {
		Resource res = null;
		System.out.println("retrieve " + resId + " from database.");
		return res;
	}

	public Resource get(final String resId) throws InterruptedException, ExecutionException {
		FutureTask<Resource> resTask = resourceMap.get(resId);
		if (resTask != null) {
			System.out.println("get " + resId + " from cache.");
			return resTask.get();
		}

		FutureTask<Resource> newTask = new FutureTask<Resource>(new Callable<Resource>() {
			public Resource call() throws Exception {
				return retrieveFromDB(resId);
			}
		});

		FutureTask<Resource> task = resourceMap.putIfAbsent(resId, newTask);
		if (task == null) {
			task = newTask;
			task.run();
		}
		return task.get();
	}

	interface Resource {

	}

	public static void main(String[] ss) throws InterruptedException, ExecutionException {
		ResourceCache cache = new ResourceCache();
		cache.get("Device_ABC");
		cache.get("Device_ABC");
	}
}

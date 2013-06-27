package creative.fire.concurrent.synchronizer;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
/**
 * FutureTask 未来的任务
 * 可取消的异步计算。利用开始和取消计算的方法、查询计算是否完成的方法和获取计算结果的方法，此类提供了对Future的基本实现。
 * 仅在计算完成时才能获取结果；如果计算尚未完成，则阻塞 get 方法。一旦计算完成，就不能再重新开始或取消计算。
 * 三态：
 * waiting to run
 * running
 * completed
 */
public class ResourceCache {
	private static ConcurrentHashMap<String, FutureTask<Resource>> resourceMap = new ConcurrentHashMap<String, FutureTask<Resource>>(1000, 0.7f);

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
			@Override
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

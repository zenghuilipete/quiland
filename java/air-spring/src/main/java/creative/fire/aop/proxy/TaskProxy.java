package creative.fire.aop.proxy;

import java.lang.reflect.Proxy;

import creative.fire.aop.ITask;
import creative.fire.aop.Task;

public class TaskProxy {
	public static void main(String[] args) throws Exception {
		ClassLoader loader = Task.class.getClassLoader();
		Class<?>[] interfaces = new Class[] { ITask.class };
		TaskHandler handler = new TaskHandler(new Task());
		ITask task = (ITask) Proxy.newProxyInstance(loader, interfaces, handler);
		task.go();
	}
}

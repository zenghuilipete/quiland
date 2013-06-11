package creative.fire.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import creative.fire.aop.ITask;
import creative.fire.aop.Task;

class DynamicTask extends Proxy {
	private static final long serialVersionUID = 3997427074722972707L;

	protected DynamicTask(InvocationHandler h) {
		super(h);
	}
}

class TaskHandler implements InvocationHandler {
	private ITask task;

	public TaskHandler(ITask task) {
		this.task = task;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before...");
		Object o = method.invoke(task, args);
		System.out.println("after...");
		return o;
	}

}

public class TaskProxy {
	public static void main(String[] args) throws Exception {
		TaskHandler handler = new TaskHandler(new Task());
		ClassLoader loader = Task.class.getClassLoader();
		Class<?>[] interfaces = new Class[] { ITask.class };
		ITask task = (ITask) DynamicTask.newProxyInstance(loader, interfaces, handler);
		task.go();
	}
}

package creative.fire.aop.dynamicproxy;

import java.lang.reflect.Proxy;

import creative.fire.aop.ITask;
import creative.fire.aop.Task;

public class TaskProxy {
	public static void main(String[] args) throws Exception {
<<<<<<< HEAD:java/air-spring/src/main/java/creative/fire/aop/proxy/TaskProxy.java
		ClassLoader loader = Task.class.getClassLoader();
		Class<?>[] interfaces = new Class[] { ITask.class };
		TaskHandler handler = new TaskHandler(new Task());
		ITask task = (ITask) Proxy.newProxyInstance(loader, interfaces, handler);
		task.go();
=======
		TaskHandler handler = new TaskHandler();
		ITask taskProxy = (ITask) handler.bind(new Task());
		taskProxy.go();
	}
}

class TaskHandler implements InvocationHandler {
	private ITask	task;

	public TaskHandler() {
	}

	public TaskHandler(ITask task) {
		this.task = task;
	}

	public ITask bind(ITask task) {
		this.task = task;
		return (ITask) Proxy.newProxyInstance(task.getClass().getClassLoader(), task.getClass().getInterfaces(), this);
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before...");
		Object o = method.invoke(task, args);
		System.out.println("after...");
		return o;
>>>>>>> ioo:java/air-spring/src/main/java/creative/fire/aop/dynamicproxy/TaskProxy.java
	}
}

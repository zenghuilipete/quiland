package creative.fire.aop.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import creative.fire.aop.ITask;

public class TaskHandler implements InvocationHandler {
	private ITask task;

	public TaskHandler(ITask task) {
		this.task = task;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(this.getClass().getName() + " says before aspect ->");
		Object o = method.invoke(task, args);
		System.out.println("method result=" + o);
		System.out.println(this.getClass().getName() + " says after aspect <-");
		return o;
	}
}

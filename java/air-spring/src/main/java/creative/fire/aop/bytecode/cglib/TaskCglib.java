package creative.fire.aop.bytecode.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import creative.fire.aop.Task;

public class TaskCglib implements MethodInterceptor {
	private Object	target;

	public Object getInstance(Object target) {
		this.target = target;
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(this.target.getClass());
		enhancer.setCallback(this);
		return enhancer.create();
	}

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("before");
		proxy.invokeSuper(obj, args);
		System.out.println("after");
		return null;
	}

	public static void main(String[] args) {
		TaskCglib cglib = new TaskCglib();
		Task task = (Task) cglib.getInstance(new Task()); //sub-class
		task.go();
	}
}
package creative.fire.aop.staticproxy;

import creative.fire.aop.ITask;
import creative.fire.aop.Task;

public class TaskProxy implements ITask {
	private Task task;

	public TaskProxy(Task task) {
		this.task = task;
	}

	@Override
	public long go() {
		System.out.println("before");
		long r = task.go();
		System.out.println("after");
		return r;
	}

	public static void main(String[] args) {
		Task task = new Task();
		TaskProxy taskProxy = new TaskProxy(task);
		taskProxy.go();
	}
}
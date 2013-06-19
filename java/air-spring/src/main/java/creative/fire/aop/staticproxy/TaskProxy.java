package creative.fire.aop.staticproxy;

import creative.fire.aop.ITask;
import creative.fire.aop.Task;

public class TaskProxy implements ITask {
	private Task	task;

	public TaskProxy(Task task) {
		this.task = task;
	}

	@Override
	public void go() {
		System.out.println("before");
		task.go();
		System.out.println("after");
	}

	public static void main(String[] args) {
		Task task = new Task();
		TaskProxy taskProxy = new TaskProxy(task);
		taskProxy.go();
	}
}
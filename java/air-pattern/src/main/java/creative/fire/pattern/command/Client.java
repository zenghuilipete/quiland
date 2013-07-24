package creative.fire.pattern.command;

public class Client {
	private Invoker invoker;

	public Client() {
		Command command = new ConcreteCommand(new Receiver());
		invoker = new Invoker(command);
	}

	public void action() {
		invoker.action();
	}
}

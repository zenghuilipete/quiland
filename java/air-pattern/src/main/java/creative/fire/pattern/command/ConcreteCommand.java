package creative.fire.pattern.command;

public class ConcreteCommand implements Command {
    private Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
		this.receiver = receiver;
	}

	public void execute() {
		System.out.println("do execute.");
		receiver.action();
	}
}

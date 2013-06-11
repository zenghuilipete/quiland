package creative.air.java7.exception;

public class MultipleException {
	private void method() throws AirLogicException, AirStreamException {

	}

	public void handle() {
		try {
			method();
		} catch (AirLogicException | AirStreamException e) {
			e.getLocalizedMessage();
		}
	}
}

class AirLogicException extends Exception {
	private static final long serialVersionUID = -2111973707134326875L;
}

class AirStreamException extends Exception {
	private static final long serialVersionUID = -2111973707134326875L;
}

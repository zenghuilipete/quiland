package creative.air.concurrent.detect;

import java.util.concurrent.ExecutionException;

public class TestSimulator {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ResourceManager rm = new ResourceManager();
		rm.detectDevices();
	}

}

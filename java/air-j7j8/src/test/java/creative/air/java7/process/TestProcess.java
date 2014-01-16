package creative.air.java7.process;

import java.io.IOException;

import org.junit.Test;

public class TestProcess {
	AirProcess process = new AirProcess();

	@Test
	public void testStartProcessNormal() throws IOException {
		process.startProcessNormal();
	}

	@Test
	public void dir() throws IOException {
		process.dir();
	}
}
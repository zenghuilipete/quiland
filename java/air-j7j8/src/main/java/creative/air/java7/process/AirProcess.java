package creative.air.java7.process;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class AirProcess {
	public void startProcessNormal() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "netstat", "-a");
		java.lang.Process process = pb.start();
		InputStream input = process.getInputStream();
		Files.copy(input, Paths.get("netstat.txt"), StandardCopyOption.REPLACE_EXISTING);
	}

	public void dir() throws IOException {
		ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "netstat", "-a");
		pb.redirectOutput(Redirect.INHERIT);
		pb.start();
	}
}
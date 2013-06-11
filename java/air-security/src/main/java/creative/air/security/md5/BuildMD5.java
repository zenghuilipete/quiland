package creative.air.security.md5;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class BuildMD5 {
	public static void main(String[] args) {
		final String md5Source = "V:\\iVIEW\\installation\\build\\md5_source.txt";
		final String md5Target = "V:\\iVIEW\\installation\\build\\md5_target.txt";
		final String md5FilePath = "D:\\Setup_suite\\";
		String cmd = null;
		try {
			String source0 = read(md5Source);
			String source = "D:\\Setup_suite\\" + source0 + ".exe";
			System.out.println("The iview file is: " + source);
			final String md5Tools = "V:\\iVIEW\\installation\\build\\HashMyFiles.exe";
			cmd = md5Tools + " /file \"" + source + "\" /stab " + md5Target;
		} catch (IOException e1) {
			System.out.println("read the file: " + md5Source + " failed.");
			return;
		}

		try {
			executeCommand(cmd);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		try {
			String content = read(md5Target);
			String[] tabs = content.split("\t");
			String filename = tabs[0].substring(0, tabs[0].lastIndexOf(".")) + ".md5";
			String fileContent = tabs[1];
			String md5File = md5FilePath + filename;
			System.out.println("MD5 File is: " + md5File);
			FileWriter writer = new FileWriter(md5File);
			writer.write(fileContent);
			writer.flush();
			writer.close();

			System.out.println("build successfully : " + fileContent);
		} catch (IOException e) {
			System.out.println("read the file: " + md5Target + " failed.");
		}
	}

	public static String read(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		StringBuilder sb = new StringBuilder();
		while ((s = in.readLine()) != null)
			sb.append(s);
		in.close();
		return sb.toString();
	}

	public static boolean executeCommand(String command) throws IOException, InterruptedException {
		System.out.println("Command: " + command);
		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(command);
		InputStream is = process.getInputStream();
		int r = 0;
		StringBuilder builder = new StringBuilder();
		while ((r = is.read()) != -1) {
			builder.append((char) r);
		}
		if (builder.length() > 0)
			System.out.println(builder.toString());
		builder = new StringBuilder();
		InputStream errin = process.getErrorStream();
		while ((r = errin.read()) != -1) {
			builder.append((char) r);
		}
		String output = builder.toString();
		if (builder.length() > 0)
			System.out.println(output);
		int ret = process.waitFor();

		if (output.length() > 0 && output.indexOf("Exception") != -1) {
			System.out.println("Execute command failed!");
			return false;
		} else {
			if (ret == 0) {
				System.out.println("Execute command successfully!");
				return true;
			} else {
				return false;
			}
		}
	}
}

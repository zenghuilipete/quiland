package creative.air.io.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BufferedInputFile {
	public static String read0(String filename) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		StringBuilder sb = new StringBuilder();
		while ((s = in.readLine()) != null) {
			sb.append(s + "\n");
		}
		in.close();
		return sb.toString();
	}

	public static String read1(String filename) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		StringBuilder sb = new StringBuilder();
		while ((bytesRead = in.read(buffer)) != -1) {
			String s = new String(buffer, 0, bytesRead);
			sb.append(s + "\n");
		}
		in.close();
		return sb.toString();
	}

	public static String read2(String filename) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename));
		BufferedReader in = new BufferedReader(new InputStreamReader(bis));
		String s;
		StringBuilder sb = new StringBuilder();
		while ((s = in.readLine()) != null) {
			sb.append(s + "\n");
		}
		in.close();
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		System.out.print(read2("D:\\-aries\\no202\\io\\src\\creative\\fire\\io\\file\\BufferedInputFile.java"));
	}
} /* (Execute to see output) */// :~

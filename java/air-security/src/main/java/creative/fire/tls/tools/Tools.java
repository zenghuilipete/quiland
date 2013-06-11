package creative.fire.tls.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import creative.fire.tls.TLSParameter;

/**
 * @author feuyeux@gmail.com 2011-5-21
 */
public class Tools {
	private static ExecutorService executor = Executors.newSingleThreadExecutor();

	public static boolean executeCmd(final String command) {
		Callable<Boolean> task = new Callable<Boolean>() {
			public Boolean call() {
				try {
					PConsole.print(command);
					Runtime runtime = Runtime.getRuntime();
					Process process = runtime.exec(command);

					if (print(process.getInputStream()) >= 0)
						if (print(process.getErrorStream()) == 0)
							return true;
					return false;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		};

		Future<Boolean> future = executor.submit(task);
		try {
			Thread.sleep(TLSParameter.SLEEPTIME);
			
			Boolean result = future.get(10, TimeUnit.SECONDS);
			return result.booleanValue();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		} catch (ExecutionException e) {
			e.printStackTrace();
			return false;
		} catch (TimeoutException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static int print(InputStream input) throws IOException {
		StringBuilder builder = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(input, "GB2312"));
		String s;
		while ((s = in.readLine()) != null)
			builder.append(s + "\r\n");

		String output = builder.toString();
		int result = builder.length();
		if (result > 0) {
			if (!TLSParameter.quiet)
				PConsole.print(output);
		} else {
		}

		return result;
	}
}

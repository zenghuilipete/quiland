package creative.fire.algorithm.recursive;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {
	public static int times = 0;

	/**
	 * dynamic programming
	 */
	private static int[] d;

	private static Map<Integer, Integer> map;

	private static boolean muted = true;

	static {
		map = new HashMap<Integer, Integer>();
		map.put(0, 1);
		map.put(1, 1);
	}

	public static int f(int n) {
		plusTime(n);
		if (n <= 1)
			return 1;
		return f(n - 1) + f(n - 2);
	}

	public static int f1(int n) {
		d = new int[n + 1];
		for (int j = 2; j <= n; j++)
			d[j] = -1;
		d[0] = 1;
		d[1] = 1;
		return f11(n);
	}

	public static int f11(int n) {
		plusTime(n);
		if (d[n - 1] < 0)
			d[n - 1] = f11(n - 1); // adds "memory"
		return (d[n - 1] + d[n - 2]);
	}

	public static int f2(int n) {
		plusTime(n);
		Integer y = map.get(n - 1);
		if (null == y) {
			map.put(n - 1, f2(n - 1));
		}

		return map.get(n - 1) + map.get(n - 2);
	}

	public static void main(String[] args) {
		int n = 11;

		times = 0;
		System.out.print(Fibonacci.f(n));
		timelog("f");

		times = 0;
		System.out.print(Fibonacci.f1(n));
		timelog("f1");

		times = 0;
		System.out.print(Fibonacci.f2(n));
		timelog("f2");

		times = 0;
		System.out.print(Fibonacci.f3(n));
		timelog("f3");
	}

	private static int f3(int n) {
		int b1 = 0, b2 = 1;
		return f33(b1, b2, n, 2);
	}

	private static int f33(int b1, int b2, int n, int i) {
		int b = b1 + b2;
		plusTime(b);
		if (n >= i) {
			b1 = b2;
			b2 = b;
			return f33(b1, b2, n, ++i);
		}
		return b;
	}

	private static void plusTime(int n) {
		times++;

		if (muted)
			return;

		System.out.println(n);
	}

	private static void timelog(String algorithmMethod) {
		System.out.println("\t" + algorithmMethod + " times: " + times);
	}
}

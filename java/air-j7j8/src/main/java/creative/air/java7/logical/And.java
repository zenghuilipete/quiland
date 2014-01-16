package creative.air.java7.logical;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 19/12/2012
 * @since  0.0.1
 * @version 0.0.1
 */
public class And {
	public static void main(String[] s) {
		if (conditionalVerify1() && conditionalVerify2()) {
			System.out.print("Never be executed.");
		}

		if (logicalVerify1() & logicalVerify2()) {
			System.out.print("Never be executed.");
		}
	}

	private static boolean logicalVerify1() {
		System.out.print("logicalVerify1 ");
		return false;
	}

	private static boolean logicalVerify2() {
		System.out.print("logicalVerify2 ");
		return false;
	}

	private static boolean conditionalVerify1() {
		System.out.print("conditionalVerify1 ");
		return false; //To change body of created methods use File | Settings | File Templates.
	}

	private static boolean conditionalVerify2() {
		System.out.print("conditionalVerify2 ");
		return false;
	}
}

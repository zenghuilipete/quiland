package creative.air.java7.bitwise;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 19/12/2012
 * @since  0.0.1
 * @version 0.0.1
 */
import static java.lang.Integer.toBinaryString;

public class AndOrNot {
	public static void main(String[] s) {
		int x1 = 0B0000_0000_0000_0000_0000_0000_1111_0000;
		int x2 = 0B1100_1111;
		System.out.println(toBinaryString(x1 & x2));
		System.out.println(toBinaryString(x1 | x2));
		System.out.println(toBinaryString(x1 ^ x2));
		System.out.println(toBinaryString(~x1));
	}
}

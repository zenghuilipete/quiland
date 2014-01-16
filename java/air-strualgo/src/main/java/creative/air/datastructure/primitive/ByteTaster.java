package creative.air.datastructure.primitive;

/**
 * @author feuyeux@gmail.com 2011-1-1
 */
public class ByteTaster {
	public static void main(String[] args) {
		byte a_byte = 10;
		String bits = byte2bits(a_byte);
		System.out.println(a_byte + "=" + bits);

		a_byte = -10;
		bits = byte2bits(a_byte);
		System.out.println(a_byte + "=" + bits);

		a_byte = Byte.MIN_VALUE;
		bits = byte2bits(a_byte);
		System.out.println(a_byte + "=" + bits);

	}

	private static String byte2bits(byte a_byte) {
		String bits = Integer.toBinaryString(a_byte & 0xFF);
		while (bits.length() < 8) {
			bits = "0" + bits;
		}
		return bits.substring(0, 4) + " " + bits.substring(4, 8);
	}
}

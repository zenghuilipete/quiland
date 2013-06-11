package creative.fire.algorithm.recursive;

/**
 * greatest common divisor(gsd) gcd(A,B)=gcd(A-B,B) Euclid's algorithm gcd(A,0)=A
 * 
 * gcd(A,B)=gcd(B,A mod B) gcd(70,25)=>gcd(25,20)=>gcd(20,5)=>gcd(5,0)=>5
 */
public class GCD {

	public static void main(String[] args) {
		System.out.println(gcd(70, 25));
	}

	public static long gcd(long a, long b) {
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}
}

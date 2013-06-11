package creative.fire.algorithm.regularexpression;

public class StringRegex {

	public static void main(String[] args) {

		String regex = "\\w+@.+\\..+";
		
		boolean b= "feuyeux@gmaill.com".matches(regex);
		
		System.out.println(b);
	}
}

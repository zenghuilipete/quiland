package creative.fire.algorithm.regularexpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternMatcher {
	private void testGroup() {
		String regex = "\\w(\\d\\d)(\\w+)";
		String candidate = "x99SuperJava";

		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(candidate);
		if (matcher.find()) {
			int gc = matcher.groupCount();
			for (int i = 0; i <= gc; i++)
				System.out.println("group " + i + " :" + matcher.group(i));
		}
	}

	private void testReplace() {
		StringBuffer sb = new StringBuffer();
		String replacement = "Smith";
		Pattern pattern = Pattern.compile("Bond");
		Matcher matcher = pattern.matcher("My name is Bond. James Bond. I would like a martini.");
		while (matcher.find()) {
			matcher.appendReplacement(sb, replacement);
		}
		matcher.appendTail(sb);
		System.out.println(sb.toString());
	}

	private void testFind() {
		String candidate = "A Matcher examines the results of applying a pattern.";
		String regex = "\\ba\\w*\\b";//"\\b[Aa]\\w*\\b";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(candidate);
		String val = null;
		System.out.println("INPUT: " + candidate);
		System.out.println("REGEX: " + regex + "\r\n");
		while (m.find()) {
			val = m.group();
			System.out.println("MATCH: " + val);
		}
		if (val == null) {
			System.out.println("NO MATCHES: ");
		}
	}

	public static void main(String[] args) {
		PatternMatcher pm = new PatternMatcher();
		pm.testGroup();
		pm.testReplace();
		pm.testFind();
	}
}

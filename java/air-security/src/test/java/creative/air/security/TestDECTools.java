package creative.air.security;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import creative.air.security.base64.DECTools;

public class TestDECTools {
	Logger log = Logger.getLogger(getClass());
	DECTools decTools;

	@Test
	public void testDEC() throws Exception {
		String s = "let's complete the ci and architect in 2013.";
		decTools = new DECTools();
		String e = decTools.encode(s);
		log.debug(e);
		String s1 = decTools.decode(e);
		log.debug(s1);
		Assert.assertEquals(s, s1);
	}
}

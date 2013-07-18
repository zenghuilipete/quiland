package creative.fire.pattern.lsp.equals;

import junit.framework.Assert;

import org.junit.Test;

public class TestEquals {
	@Test
	public void testFinal() {
		FinalBase thiz1 = new FinalBase(1, 2);
		FinalBase thiz2 = new FinalBase(2, 1);
		FinalBase thiz3 = new FinalBase(2, 1);
		Assert.assertFalse(thiz1.equals(null));
		Assert.assertFalse(thiz1.equals(thiz2));
		Assert.assertTrue(thiz3.equals(thiz2));
	}

	@Test
	public void testNormal() {
		Base thiz1 = new Base(1, 2);
		Base thiz2 = new Base(2, 1);
		Base thiz3 = new Base(2, 1);
		Son thiz4 = new Son(2, 1);
		Assert.assertFalse(thiz1.equals(null));
		Assert.assertFalse(thiz1.equals(thiz2));
		Assert.assertTrue(thiz3.equals(thiz2));
		Assert.assertFalse(thiz3.equals(thiz4));
	}
}

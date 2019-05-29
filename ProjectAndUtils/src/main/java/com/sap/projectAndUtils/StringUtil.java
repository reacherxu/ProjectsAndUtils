package com.sap.projectAndUtils;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

public class StringUtil {

	/**
	 * StringUtils.equals(null, null)   = true
     * StringUtils.equals(null, "abc")  = false
     * StringUtils.equals("abc", null)  = false
     * StringUtils.equals("abc", "abc") = true
     * StringUtils.equals("abc", "ABC") = false
	 */
	@Test
	public void testEquals() {
        Assert.assertTrue(StringUtils.equals(null, null));
        Assert.assertFalse(StringUtils.equals(null, "abc"));
        Assert.assertFalse(StringUtils.equals("abc", null));
        Assert.assertTrue(StringUtils.equals("abc", "abc"));
        Assert.assertFalse(StringUtils.equals("abc", "ABC"));
	}
	
	@Test
	public void testNull() {
        Assert.assertTrue(StringUtils.isNotEmpty(" "));
	}
	
	/**
	 * test StringFormat
	 */
	@Test
	public void testStringFormat() {
		String info = String.format("%s : %s","time",new Date());
		System.out.println(info);
	}
}

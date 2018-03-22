package com.sap.projectAndUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
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
		assertTrue(StringUtils.equals(null, null));
		assertFalse(StringUtils.equals(null, "abc"));
		assertFalse(StringUtils.equals("abc", null));
		assertTrue(StringUtils.equals("abc", "abc"));
		assertFalse(StringUtils.equals("abc", "ABC"));
	}
	
	@Test
	public void testNull() {
		assertTrue(StringUtils.isNotEmpty(" "));
		
	}
}

package com.sap.projectAndUtils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringUtil {

    /**
     * StringUtils.equals(null, null) = true StringUtils.equals(null, "abc") = false
     * StringUtils.equals("abc", null) = false StringUtils.equals("abc", "abc") = true
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

    @Test
    public void testA() {
        Map<List<String>, String> map = new HashMap<>();
        List<String> list1 = new ArrayList<>();
        map.put(list1, "a0");
        System.out.println(map);


        list1.add("list");
        map.put(list1, "b0");
        System.out.println(map);

        // map.replace("a", "a0", "modified");
        // map.put("a", "c0");
        // System.out.println(map);
    }

    /**
     * test StringFormat
     */
    @Test
    public void testStringFormat() {
        String info = String.format("%s : %s", "time", new Date());
        System.out.println(info);
    }
}

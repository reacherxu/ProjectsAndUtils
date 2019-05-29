/**
 * SAP Inc.
 * Copyright (c) 1972-2019 All Rights Reserved.
 */
package com.sap.projectAndUtils;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

/**
 *
 * @author richard.xu03@sap.com
 * @version $Id: CollectionUtilsTest.java, v 0.1 May 29, 2019 4:40:25 PM richard.xu Exp $
 */
public class CollectionUtilsTest {

    /**
     * 两个集合元素是否相同
     */
    @Test
    public void isEqualCollection() {
        String[] arrayA = new String[] {"A", "B", "C"};
        String[] arrayB = new String[] {"A", "B", "C"};
        String[] arrayC = new String[] {"B", "C", "A"};

        // [A, C, E, G, H, K]
        List<String> listA = Arrays.asList(arrayA);
        List<String> listB = Arrays.asList(arrayB);
        List<String> listC = Arrays.asList(arrayC);
        System.out.println(CollectionUtils.isEqualCollection(listA, listB));
        System.out.println(CollectionUtils.isEqualCollection(listA, listC));
    }

    @Test
    public void operation() {
        String[] arrayA = new String[] {"A", "B", "C", "D", "E", "F"};
        String[] arrayB = new String[] {"B", "D", "F", "G", "H", "K"};
        // [A, C, E, G, H, K]
        List<String> listA = Arrays.asList(arrayA);
        List<String> listB = Arrays.asList(arrayB);

        // 说通俗点就去掉重复元素剩下的元素
        System.out.println("补集:" + CollectionUtils.disjunction(listA, listB));

        System.out.println("并集:" + CollectionUtils.union(listA, listB));
        System.out.println("交集:" + CollectionUtils.intersection(listA, listB));

        // listA扣除listB,剩下的元素
        // 差集:返回只存在于listA左集合独有的数据, 结果【C】
        System.out.println("差集:" + CollectionUtils.subtract(listA, listB));
    }

}

/**
 * SAP Inc.
 * Copyright (c) 1972-2019 All Rights Reserved.
 */
package com.sap.projectAndUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 *
 * @author richard.xu03@sap.com
 * @version $Id: StreamUtils.java, v 0.1 Aug 11, 2019 6:24:50 PM richard.xu Exp $
 */
public class StreamUtils {

    @Test
    public void testMap() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        System.out.println("numbers : " + numbers);

        List<Integer> copyIntegers = numbers.stream().map(i -> i * i).collect(Collectors.toList());
        System.out.println("copyIntegers : " + copyIntegers);

        List<Integer> filterIntegers = numbers.stream().filter(i -> i >= 5).collect(Collectors.toList());
        System.out.println("filterIntegers : " + filterIntegers);
    }

    @Test
    public void test1() {
        // Stream 的基本使用
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        long count = strings.stream().filter(str -> StringUtils.isBlank(str)).count();
        System.out.println("blank string number is :" + count);
        long count1 = strings.stream().filter(str -> str.length() == 3).count();
        System.out.println("length = 3 number is :" + count1);

        List<String> myList = Arrays.asList("a1", "a2", "b1", "c2", "c1");
        myList.stream().filter(s -> s.startsWith("c")).map(String::toUpperCase).forEach(System.out::println);
        myList.stream().filter(s -> s.startsWith("c")).map(String::toUpperCase).forEach(s -> {
            System.out.println("Upper case " + s);
        });
        myList.stream().filter(s -> s.startsWith("c")).map(String::toUpperCase).forEach(s -> System.out.println("Upper case " + s));
    }

    @Test
    public void test2() {
        // Stream 的创建
        // 创建方式1
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        strings.stream().filter(str -> StringUtils.isNotBlank(str)).forEach(System.out::println);
        // 创建方式2
        Stream.of("abc", "", "bc", "efg", "abcd", "", "jkl").filter(str -> StringUtils.isNotBlank(str)).forEach(System.out::println);
    }
}

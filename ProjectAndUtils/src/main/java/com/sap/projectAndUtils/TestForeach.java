/**
 * SAP Inc. Copyright (c) 1972-2018 All Rights Reserved.
 */
package com.sap.projectAndUtils;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * 普通for循环在遍历集合时使用下标来定位集合中的元素，java在jdk1.5中开始支持foreach循环，foreach在一定程度上简化了对集合的遍历， 但是foreach不能完全代替for循环
 * 
 * 限制场景： 1、使用foreach来遍历集合时，集合必须实现Iterator接口，foreach就是使用Iterator接口来实现对集合的遍历的
 * 2、在用foreach循环遍历一个集合时不能向集合中增加元素，不能从集合中删除元素，否则会抛出ConcurrentModificationException异常。
 * 抛出该异常是因为在集合内部有一个modCount变量用于记录集合中元素的个数，当向集合中增加或删除元素时，modCount也会随之变化，在遍历开始时会记录modCount的值，每次遍历元素时都会判断该变量是否发生了变化，如果发生了变化则抛出ConcurrentModificationException异常
 * 
 * 
 * @author richard.xu03@sap.com
 * @version $Id: TestForeach.java, v 0.1 Dec 17, 2018 1:25:11 PM richard.xu Exp $
 */
public class TestForeach {

    /**
     * foreach ConcurrentModificationException异常
     */
    @Test
    public void testForeachThrowCME0() {
        List<String> list = new ArrayList<String>();
        list.add("0");
        list.add("1");
        list.add("2");
        for (String str : list) {
            System.out.println(str);
            list.add("3");
        }
    }



    /**
     * 特例： list集合中的元素只有两条，使用foreach循环移除元素。
     */
    @Test
    public void testForeachThrowCME1() {
        List<String> list = new ArrayList<String>();
        list.add("0");
        list.add("1");
        for (String str : list) {
            list.remove("0");
        }
        System.out.println("只有两个元素，不会抛出异常" + list);
    }

    @Test
    public void testForeachThrowCME2() {
        List<String> list = new ArrayList<String>();
        list.add("0");
        list.add("1");
        list.add("2");
        for (String str : list) {
            list.remove("0");
        }
        System.out.println("大于两个元素，会抛出异常" + list);
    }

    /**
     * foreach方式并不能修改list中的元素 当使用foreach循环基本类型时变量时不能修改集合中的元素的值，遍历对象时可以修改对象的属性的值，但是不能修改对象的引用
     */
    @Test
    public void testForeach() {
        List<String> list = new ArrayList<String>();
        list.add("0");
        list.add("1");
        list.add("2");
        for (String str : list) {
            str = str + "0";
        }
        System.out.println("test foreach" + list);
    }

    /**
     * 普通for循环成功将list集合中的每一个元素修改
     */
    @Test
    public void testFor() {
        List<String> list = new ArrayList<String>();
        list.add("0");
        list.add("1");
        list.add("2");
        for (int i = 0; i < list.size(); i++) {
            list.set(i, "a");
        }
        System.out.println("test for" + list);
    }
}

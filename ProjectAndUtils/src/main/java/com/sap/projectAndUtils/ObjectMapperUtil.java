/**
 * SAP Inc.
 * Copyright (c) 1972-2020 All Rights Reserved.
 */
package com.sap.projectAndUtils;

import java.util.List;
import org.junit.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * refer to https://juejin.im/post/5d9733a26fb9a04e38584387
 * 
 * @author richard.xu03@sap.com
 * @version $Id: ObjectMapperUtil.java, v 0.1 Mar 12, 2020 2:34:47 PM richard.xu Exp $
 */
public class ObjectMapperUtil {

    /**
     * 不使用范型
     * 
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void testRead() throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String stringArray = "[\"color\" , \"Black\", \"type\" , \"BMW\" ]";
        String intArray = "[1,2,3]";
        String booleanArray = "[true,false,true]";

        System.out.println(objectMapper.readValue(stringArray, java.util.List.class));
        System.out.println(objectMapper.readValue(intArray, java.util.List.class));
        System.out.println(objectMapper.readValue(booleanArray, java.util.List.class));
    }

    /**
     * 使用范型
     * 
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void testReadWithT() throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String stringArray = "[\"color\" , \"Black\", \"type\" , \"BMW\" ]";
        String intArray = "[1,2,3]";
        String booleanArray = "[true,false,true]";

        List<Object> aList = objectMapper.readValue(stringArray, new TypeReference<List<Object>>() {});
        System.out.println(aList);
        List<Object> bList = objectMapper.readValue(intArray, new TypeReference<List<Object>>() {});
        System.out.println(bList);
        List<Object> cList = objectMapper.readValue(booleanArray, new TypeReference<List<Object>>() {});
        System.out.println(cList);
    }

}

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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * refer to https://juejin.im/post/5d9733a26fb9a04e38584387
 * https://www.history-of-my-life.com/archives/547
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

        List aList = objectMapper.readValue(stringArray, java.util.List.class);
        System.out.println(objectMapper.writeValueAsString(aList));
        List bList = objectMapper.readValue(intArray, java.util.List.class);
        System.out.println(objectMapper.writeValueAsString(bList));
        List cList = objectMapper.readValue(booleanArray, java.util.List.class);
        System.out.println(objectMapper.writeValueAsString(cList));
        objectMapper.readTree(stringArray);
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

        List<String> aList = objectMapper.readValue(stringArray, new TypeReference<List<String>>() {});
        System.out.println(objectMapper.writeValueAsString(aList));
        List<Object> bList = objectMapper.readValue(intArray, new TypeReference<List<Object>>() {});
        System.out.println(bList);
        List<Object> cList = objectMapper.readValue(booleanArray, new TypeReference<List<Object>>() {});
        System.out.println(cList);
    }

    /**
     * JSON反序列化为Java对象
     * 
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void testReadJavaClass() throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"color\":\"Black\",\"type\":\"BMW\"}";
        Car car = objectMapper.readValue(json, Car.class);
        System.out.println(car);
    }

    /**
     * JSON反序列化为JasonNode对象
     * 
     * @throws JsonMappingException
     * @throws JsonProcessingException
     */
    @Test
    public void testReadJsonNode() throws JsonMappingException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{ \"color\" : \"Black\", \"type\" : \"FIAT\" }";
        JsonNode jsonNode = objectMapper.readTree(json);
        String color = jsonNode.get("color").asText();
        System.err.println(color);
    }
}


@Data
@AllArgsConstructor
@NoArgsConstructor
class Car {

    private String color;
    private String type;
}

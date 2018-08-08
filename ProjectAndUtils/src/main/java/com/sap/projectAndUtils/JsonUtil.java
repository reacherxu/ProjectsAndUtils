package com.sap.projectAndUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sap.cdt.domain.enums.ErrorCode;
import com.sap.cdt.domain.enums.MODRuntimeException;

public class JsonUtil {
    class Person {
        String  name;
        String  id;
        Date    birth;
        Boolean sex;

        public Person(String name, String id, Date birth, Boolean sex) {
            super();
            this.name = name;
            this.id = id;
            this.birth = birth;
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Date getBirth() {
            return birth;
        }

        public void setBirth(Date birth) {
            this.birth = birth;
        }

        public Boolean getSex() {
            return sex;
        }

        public void setSex(Boolean sex) {
            this.sex = sex;
        }

    }

    private final static Logger logger           = LoggerFactory.getLogger(JsonUtil.class);

    public static final String  EMPTY_JSON       = "{}";

    public static final String  DEFAULT_CONTENTS = "contents";

    /**
     * add a property for a json text
     * 
     * @param jsonText  source json text
     * @param content   root conent
     * @param properties    conditions
     * @return
     */
    public static JsonObject addExpireProperty(String jsonText, String content,
                                               Map<String, String> properties) {
        JsonObject target = null;
        try {

            JsonParser jsonParser = new JsonParser();
            // get root json object
            JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonText);
            if (StringUtils.isBlank(jsonText)
                || StringUtils.equals(EMPTY_JSON, jsonObject.toString())) {
                throw new MODRuntimeException("parse JSON error", ErrorCode.ERR_PARSE_CONTENT);
            }
            AssertUtil.assertTrue(properties.size() >= 1, ErrorCode.ERR_PARSE_CONTENT);

            // get json object key set
            JsonObject rootContent = null;
            if (StringUtils.isBlank(content)) {
                rootContent = jsonObject.get(DEFAULT_CONTENTS).getAsJsonObject();
            } else {
                rootContent = jsonObject.get(content).getAsJsonObject();
            }
            Set<String> set = rootContent.keySet();
            for (String key : set) {
                JsonObject subJsonObject = rootContent.getAsJsonObject(key);

                boolean flag = true;
                for (String propertyName : properties.keySet()) {
                    if (StringUtils.equals(properties.get(propertyName),
                        subJsonObject.get(propertyName).getAsString())) {
                        flag = flag && true;
                    } else {
                        flag = flag && false;
                        break;
                    }
                }
                if (flag) {
                    subJsonObject.addProperty("isExpire", true);
                }
            }
            target = jsonObject;

        } catch (MODRuntimeException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new MODRuntimeException("parse JSON error", ErrorCode.ERR_PARSE_CONTENT);
        }
        return target;
    }

    @Test
    public void generateJsonWithPrettyFormat() {
        Person richard = new Person("Richard", "I350644", new Date(), true);
        System.out.println(JSON.toJSONString(richard, true));
    }

    /**
     * 格式化用json array 表示
     */
    @Test
    public void testCreateJsonArray() {

        String jsonText = "{\"students\":[{\"name\":\"A\",\"age\":19},{\"name\":\"B\",\"age\":19},{\"name\":\"C\",\"age\":19}],\"num\":3}";

        //创建json解析器
        JsonParser jsonParser = new JsonParser();
        // 通过parse方法获取最外层的json对象。
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonText);

        // 获取JSON数组
        JsonArray jsonArray = jsonObject.get("students").getAsJsonArray();

        // 依次获取数组中的元素，遍历并添加属性
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject subJsonObject = jsonArray.get(i).getAsJsonObject();
            subJsonObject.addProperty("number", i);
            System.out.print("" + subJsonObject.get("name").getAsString() + "  ");
            System.out.println("" + subJsonObject.get("age").getAsInt());
        }

        System.out.println(jsonObject);
    }

    @Test
    public void testAddExpirePropertySuccess() {
        String validJsonText = "{\"contents\":{\"4ea85026-ff21-48c9-a7fc-0560c4e70730\":{\"classDefinition\":\"sap.modeling.cdt.msgFlow.Service\",\"name\":\"bikeAdd\",\"sId\":\"aef1fa35-a12c-42b6-8429-ddbf3006bc5f\",\"displayName\":\"bikeAdd\"},\"1a9b61ac-09d8-42b5-9b3a-eca6304d9f77\":{\"classDefinition\":\"sap.modeling.cdt.msgFlow.ui.ServiceSymbol\",\"x\":-7263,\"y\":-7531,\"width\":160,\"height\":30,\"object\":\"4ea85026-ff21-48c9-a7fc-0560c4e70730\"},\"272f1ad5-af56-44e1-85ee-076630bae401\":{\"classDefinition\":\"sap.modeling.cdt.msgFlow.Service\",\"name\":\"bikeDelete\",\"sId\":\"5026b789-db70-450e-984a-7aa2f4ad4ed7\",\"displayName\":\"bikeDelete\"},\"1508cc32-5332-4f9b-ab4a-98afd691f239\":{\"classDefinition\":\"sap.modeling.cdt.msgFlow.ui.ServiceSymbol\",\"x\":-7263,\"y\":-7491,\"width\":160,\"height\":30,\"object\":\"272f1ad5-af56-44e1-85ee-076630bae401\"}}}";

        Map<String, String> map = new HashMap<>();
        map.put("classDefinition", "sap.modeling.cdt.msgFlow.Service");
        map.put("sId", "aef1fa35-a12c-42b6-8429-ddbf3006bc5f");

        JsonObject object = JsonUtil.addExpireProperty(validJsonText, null, map);
        Assert.assertTrue(object.toString().contains("isExpire"));
    }

}

package com.sap.projectAndUtils;

import java.util.Date;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
	class Person {
		String name;
		String id;
		Date birth;
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
	
	public void generateJsonWithPrettyFormat() {
		Person richard = new Person("Richard", "I350644", new Date(), true);
		System.out.println(JSON.toJSONString(richard, false));
	}
	
	public static void main(String[] args) {
		new JsonUtil().generateJsonWithPrettyFormat();
	}
}

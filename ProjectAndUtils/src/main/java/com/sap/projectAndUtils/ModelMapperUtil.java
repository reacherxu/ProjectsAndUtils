/**
 * SAP Inc.
 * Copyright (c) 1972-2018 All Rights Reserved.
 */
package com.sap.projectAndUtils;

import java.util.Optional;

import org.junit.Test;
import org.modelmapper.ModelMapper;

/**
 * 
 * @author richard.xu03@sap.com
 * @version $Id: ModelMapperUtil.java, v 0.1 May 17, 2018 11:27:48 AM richard.xu Exp $
 */
public class ModelMapperUtil {

    /**
     * 简单类-类转换
     */
    @Test
    public void testModelToDTO() {
        User user = new User();
        System.out.println(user);
        user.setId(1L);
        user.setNickname("张三");
        user.setEmail("101@qq.com");
        user.setHonor("测试荣誉");
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        System.out.println(userDTO);
    }

    @Test
    public void testOptional() {
        User user = new User();
        user.setId(1L);
        user.setNickname("张三");
        user.setEmail("101@qq.com");
        user.setHonor("测试荣誉");

        Optional<User> oUser = Optional.ofNullable(user);
        oUser.ifPresent(u -> System.out.println("Username is: " + u.getNickname()));

        Optional<User> oUser2 = Optional.ofNullable(null);
        oUser2.ifPresent(u -> System.out.println("Username is: " + u.getNickname()));
    }

    class User {
        private Long   id;
        private String nickname;
        private String honor;
        private String email;
        private String password;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHonor() {
            return honor;
        }

        public void setHonor(String honor) {
            this.honor = honor;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "User [id=" + id + ", nickname=" + nickname + ", honor=" + honor + ", email="
                   + email + ", password=" + password + "]";
        }

    }

    class UserDTO {
        private Long   id;
        private String nickname;
        private String honor;
        private String email;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHonor() {
            return honor;
        }

        public void setHonor(String honor) {
            this.honor = honor;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "UserDTO [id=" + id + ", nickname=" + nickname + ", honor=" + honor + ", email="
                   + email + "]";
        }

    }

}

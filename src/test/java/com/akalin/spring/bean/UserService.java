package com.akalin.spring.bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService {

    private String uId;

    private String name;

    private Integer age;
    private UserDao userDao;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UserService() {
        log.info("UserService init, no arg constructor");
    }

    public UserService(String uId, UserDao userDao) {
        log.info("UserService init, two arg constructor");
        this.uId = uId;
        this.userDao = userDao;
    }

    public void queryUserInfo() {
        String s = userDao.queryById(uId);
        log.info("uid={},name={},age={}", uId,name,age);
    }

    public void queryById() {
        String s = userDao.queryById(uId);
        log.info("用户信息:{}",s);
    }

}

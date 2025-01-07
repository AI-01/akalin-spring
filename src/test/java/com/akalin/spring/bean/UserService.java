package com.akalin.spring.bean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService {

    private String uId;

    private UserDao userDao;

    public UserService() {
        log.info("UserService init, no arg constructor");
    }

    public UserService(String uId, UserDao userDao) {
        log.info("UserService init, two arg constructor");
        this.uId = uId;
        this.userDao = userDao;
    }

    public void queryUserInfo() {
        System.out.println("query user info");
    }

    public void queryById() {
        String s = userDao.queryById(uId);
        System.out.println("用户信息："+s);
    }

}

package com.akalin.spring.bean;

import com.akalin.spring.beans.factory.DisposableBean;
import com.akalin.spring.beans.factory.InitializingBean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService implements InitializingBean, DisposableBean {


    private String uId;
    private String company;
    private String location;
    private UserDao userDao;

    @Override
    public void destroy() throws Exception {
        System.out.println("执行：UserService.destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行：UserService.afterPropertiesSet");
    }

    public void queryUserInfo() {
        String s = userDao.queryUserName(uId);
        log.info("result={}", s);
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}

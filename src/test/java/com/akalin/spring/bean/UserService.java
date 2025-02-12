package com.akalin.spring.bean;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class UserService implements IUserService{

    private String uId;
    private String company;
    private String location;
    private IUserDao userDao;


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

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "akalin，1001，深圳";
    }

    public String register(String userName) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "注册用户：" + userName + " success！";
    }

    @Override
    public Class<?> getTargetClass() {
        return UserService.class;
    }

    @Override
    public boolean isStatic() {
        return IUserService.super.isStatic();
    }

    @Override
    public Object getTarget() throws Exception {
        return new UserService();
    }

    @Override
    public void releaseTarget(Object target) throws Exception {
        IUserService.super.releaseTarget(target);
    }
}

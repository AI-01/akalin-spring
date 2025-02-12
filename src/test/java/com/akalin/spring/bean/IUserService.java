package com.akalin.spring.bean;

import org.springframework.aop.TargetSource;

public interface IUserService extends TargetSource {

    public String queryUserInfo();

    public String register(String userName);

}

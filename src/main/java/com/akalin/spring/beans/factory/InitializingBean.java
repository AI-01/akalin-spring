package com.akalin.spring.beans.factory;

public interface InitializingBean {

    /**
     * bean属性填充后调用
     * @throws Exception 异常
     */
    void afterPropertiesSet() throws Exception;

}

package com.akalin.spring.beans.factory;

public interface DisposableBean {

    /**
     * 销毁
     * @throws Exception 异常
     */
    void destroy() throws Exception;

}

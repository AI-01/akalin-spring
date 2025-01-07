package com.akalin.spring.core.io;

public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 获取资源
     * @param location 资源位置
     * @return 资源
     */
    Resource getResource(String location);

}

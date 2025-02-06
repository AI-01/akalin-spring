package com.akalin.spring;

import cn.hutool.core.io.IoUtil;
import com.akalin.spring.bean.UserService;
import com.akalin.spring.beans.factory.support.ClassPathXmlApplicationContext;
import com.akalin.spring.core.io.DefaultResourceLoader;
import com.akalin.spring.core.io.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

//@SpringBootTest
class AkalinSpringApplicationTests {

    private DefaultResourceLoader resourceLoader;

    @BeforeEach
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void testClassPath() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:test.properties");
        InputStream is = resource.getInputStream();
        String content = IoUtil.readUtf8(is);
        System.out.println(content);
    }

    @Test
    public void testFile() throws Exception {
        Resource resource = resourceLoader.getResource("src/main/resources/spring.xml");
        InputStream is = resource.getInputStream();
        String content = IoUtil.readUtf8(is);
        System.out.println(content);
    }

    @Test
    public void testUrl() throws Exception {
        Resource resource = resourceLoader.getResource("http://www.baidu.com");
        InputStream is = resource.getInputStream();
        String content = IoUtil.readUtf8(is);
        System.out.println(content);
    }

    @Test
    void contextLoads() {

    }

    @Test
    public void testBeanFactory() {
        // 1.初始化applicationContext
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 2.获取bean并使用
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }

}

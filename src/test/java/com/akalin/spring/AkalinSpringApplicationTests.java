package com.akalin.spring;

import cn.hutool.core.io.IoUtil;
import com.akalin.spring.aop.AdvisedSupport;
import com.akalin.spring.aop.aspect.AspectJExpressionPointcut;
import com.akalin.spring.aop.framework.CglibAopProxy;
import com.akalin.spring.aop.framework.JdkDynamicAopProxy;
import com.akalin.spring.bean.IUserService;
import com.akalin.spring.bean.UserService;
import com.akalin.spring.bean.UserServiceInterceptor;
import com.akalin.spring.beans.factory.support.ClassPathXmlApplicationContext;
import com.akalin.spring.core.io.DefaultResourceLoader;
import com.akalin.spring.core.io.Resource;
import com.akalin.spring.event.CustomEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.TargetSource;

import java.io.InputStream;
import java.lang.reflect.Method;

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
    public void testApplicationContext() {
        // 1.初始化applicationContext
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 2.获取bean并使用
        UserService userService = applicationContext.getBean("userService", UserService.class);
        System.out.println("result=" + userService.queryUserInfo());
    }

    @Test
    public void test_event() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.publishEvent(new CustomEvent(applicationContext, 1001L, "akin"));

        applicationContext.registerShutdownHook();
    }

    @Test
    public void test_aop() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut("execution(* com.akalin.spring.bean.UserService.*(..))");
        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryUserInfo");

        System.out.println(pointcut.matches(clazz)); // true
        System.out.println(pointcut.matches(method, clazz)); // true
    }

    @Test
    public void test_dynamic() {
        // 目标对象
        IUserService userService = new UserService();

        // 组装代理信息
        AdvisedSupport advisedSupport = new AdvisedSupport();
        advisedSupport.setTargetSource(userService);
        advisedSupport.setMethodInterceptor(new UserServiceInterceptor());
        advisedSupport.setMethodMatcher(new AspectJExpressionPointcut("execution(*  com.akalin.spring.bean.IUserService.*(..))"));

        // 代理对象(CglibAopProxy)
        IUserService proxy_cglib = (IUserService) new CglibAopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_cglib.register("阿卡林"));

        // 代理对象(JdkDynamicAopProxy)
        IUserService proxy_jdk = (IUserService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        // 测试调用
        System.out.println("测试结果：" + proxy_jdk.queryUserInfo());

    }
}

package com.akalin.spring.aop;

public interface Pointcut {

    /**
     * 获取切点类过滤器
     */
    ClassFilter getClassFilter();

    /**
     * 获取切点方法匹配器
     */
    MethodMatcher getMethodMatcher();

}

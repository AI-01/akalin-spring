package com.akalin.spring.aop;

import org.aopalliance.intercept.MethodInvocation;

public interface ProxyMethodInvocation extends MethodInvocation {

    Object getProxy();

}

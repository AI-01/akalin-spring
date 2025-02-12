package com.akalin.spring.aop.framework;

import com.akalin.spring.aop.ProxyMethodInvocation;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

public class ReflectiveMethodInvocation implements ProxyMethodInvocation,Cloneable {

    protected final Object target;

    protected final Method method;

    protected Object[] arguments;


    protected ReflectiveMethodInvocation(@Nullable Object target, Method method, @Nullable Object[] arguments) {
        this.target = target;
        this.method = BridgeMethodResolver.findBridgedMethod(method);
        this.arguments = this.adaptArgumentsIfNecessary(method, arguments);
    }

    private Object[] adaptArgumentsIfNecessary(Method method, @Nullable Object[] arguments) {
        if (ObjectUtils.isEmpty(arguments)) {
            return new Object[0];
        }
        if (method.isVarArgs() && (method.getParameterCount() == arguments.length)) {
            Class<?>[] paramTypes = method.getParameterTypes();
            int varargIndex = paramTypes.length - 1;
            Class<?> varargType = paramTypes[varargIndex];
            if (varargType.isArray()) {
                Object varargArray = arguments[varargIndex];
                if (varargArray instanceof Object[] && !varargType.isInstance(varargArray)) {
                    Object[] newArguments = new Object[arguments.length];
                    System.arraycopy(arguments, 0, newArguments, 0, varargIndex);
                    Class<?> targetElementType = varargType.componentType();
                    int varargLength = Array.getLength(varargArray);
                    Object newVarargArray = Array.newInstance(targetElementType, varargLength);
                    System.arraycopy(varargArray, 0, newVarargArray, 0, varargLength);
                    newArguments[varargIndex] = newVarargArray;
                    return newArguments;
                }
            }
        }
        return arguments;
    }


    @Override
    public Object getProxy() {
        return null;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public Object[] getArguments() {
        return this.arguments;
    }

    @Override
    public Object proceed() throws Throwable {
        return null;
    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public AccessibleObject getStaticPart() {
        return this.method;
    }

}

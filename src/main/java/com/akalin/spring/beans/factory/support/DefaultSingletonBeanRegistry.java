package com.akalin.spring.beans.factory.support;


import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.factory.DisposableBean;
import com.akalin.spring.beans.factory.config.SingletonBeanRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;


import java.util.*;

@Slf4j
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    private final Map<String,Object> singletonObjects = new HashMap<>();  // 单例bean缓存

    private final Map<String, DisposableBean> disposableBeans = new LinkedHashMap<>();  // 可丢弃的bean实例

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    @Override
    public void registerSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new BeansException("Singleton bean name '" + beanName + "' has been already registered!");
            }
            addSingleton(beanName, singletonObject);
        }
    }

    /**
     * 添加单例bean
     * @param beanName bean名称
     * @param singletonObject 单例bean
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    /**
     * 注册可丢弃的bean
     * @param beanName bean名称
     * @param bean 一次性bean
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        synchronized (this.disposableBeans) {
            this.disposableBeans.put(beanName, bean);
        }
    }

    public void destroySingletons() {
        String[] disposableBeanNames;
        synchronized (this.disposableBeans) {
            disposableBeanNames = StringUtils.toStringArray(this.disposableBeans.keySet());
        }
        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            destroySingleton(disposableBeanNames[i]);
        }
        clearSingletonCache();
    }

    public void destroySingleton(String beanName) {
        // Remove a registered singleton of the given name, if any.
        removeSingleton(beanName);

        // Destroy the corresponding DisposableBean instance.
        DisposableBean disposableBean;
        synchronized (this.disposableBeans) {
            disposableBean = this.disposableBeans.remove(beanName);
        }
        destroyBean(beanName, disposableBean);
    }

    protected void clearSingletonCache() {
        synchronized (this.singletonObjects) {
            this.singletonObjects.clear();
        }
    }

    protected void removeSingleton(String beanName) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.remove(beanName);
        }
    }

    protected void destroyBean(String beanName, DisposableBean bean) {
        // Actually destroy the bean now...
        if (bean != null) {
            try {
                bean.destroy();
            }
            catch (Throwable ex) {
                log.warn("Destruction of bean with name '" + beanName + "' threw an exception", ex);
            }
        }
    }
}

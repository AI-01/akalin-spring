package com.akalin.spring.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.akalin.spring.beans.BeansException;
import com.akalin.spring.beans.PropertyValue;
import com.akalin.spring.beans.factory.config.BeanDefinition;
import com.akalin.spring.beans.factory.config.BeanReference;
import com.akalin.spring.beans.factory.support.AbstractBeanDefinitionReader;
import com.akalin.spring.beans.factory.support.BeanDefinitionRegistry;
import com.akalin.spring.core.io.Resource;
import com.akalin.spring.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader loader){
        super(registry,loader);
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream is = resource.getInputStream()) {
            doLoadBeanDefinitions(is);
        } catch (Exception e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    private void doLoadBeanDefinitions(InputStream is) throws ClassNotFoundException {
        Document doc = XmlUtil.readXML(is);
        Element root = doc.getDocumentElement();
        NodeList childNodes = root.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            // 判断元素
            if (!(childNodes.item(i) instanceof Element)) {
                continue;
            }
            // 判断对象
            if (!"bean".equals(childNodes.item(i).getNodeName())) {
                continue;
            }

            // 读取属性
            Element ele = (Element) childNodes.item(i);
            String id = ele.getAttribute("id");
            String name = ele.getAttribute("name");
            String className = ele.getAttribute("class");
            String initMethod = ele.getAttribute("init-method");
            String destroyMethod = ele.getAttribute("destroy-method");

            Class<?> clazz = Class.forName(className);
            String beanName = StrUtil.isNotBlank(id)?id:name;
            if (StrUtil.isBlank(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 定义bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethod);
            // 读取属性并注入
            for (int j = 0; j < ele.getChildNodes().getLength(); j++) {
                if (!(ele.getChildNodes().item(j) instanceof Element)) {
                    continue;
                }
                if (!"property".equals(ele.getChildNodes().item(j).getNodeName())) {
                    continue;
                }
                // 处理标签property, 包括 1. name 2. value 3. ref
                Element prop = (Element) ele.getChildNodes().item(j);
                String attrName = prop.getAttribute("name");
                String attrValue = prop.getAttribute("value");
                String ref = prop.getAttribute("ref");
                if (StrUtil.isBlank(attrName) && StrUtil.isBlank(ref)) {
                    continue;
                }
                if (StrUtil.isNotBlank(attrValue) && StrUtil.isNotBlank(ref)) {
                    throw new BeansException("Cannot define both 'value' and 'ref' in tag 'property'");
                }
                Object value = attrValue;
                if (StrUtil.isNotBlank(ref)) {
                    value = (BeanReference) () -> ref;
                }
                // 注入属性
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            // 注册bean
            getRegistry().registerBeanDefinition(beanName,beanDefinition);
        }

    }
}

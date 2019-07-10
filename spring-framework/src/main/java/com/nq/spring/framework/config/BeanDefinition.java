package com.nq.spring.framework.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 存放bean信息
 * @author niuqian
 */
public class BeanDefinition {

    private String beanName;

    private String beanClassName;

    private String initMethod;

    /**
     * bean的属性信息
     */
    private List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();

    public BeanDefinition(String beanName, String beanClassName) {
        this.beanName = beanName;
        this.beanClassName = beanClassName;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public String getInitMethod() {
        return initMethod;
    }

    public void setInitMethod(String initMethod) {
        this.initMethod = initMethod;
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValues;
    }

    public void addPropertyValues(PropertyValue propertyValue) {
        propertyValues.add(propertyValue);
    }
}

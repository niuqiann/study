package com.nq.spring.framework.config;

/**
 * 存放property value属性值
 * @author niuqian
 */
public class TypeStringValue {

    private String value;

    private Class<?> targetType;

    public TypeStringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Class<?> getTargetType() {
        return targetType;
    }

    public void setTargetType(Class<?> targetType) {
        this.targetType = targetType;
    }
}

package com.nq.spring.framework.convert;

/**
 * @author niuqian
 */
public class IntegerTypeConverter implements TypeConverter {
    @Override
    public Boolean isType(Class<?> clazz) {
        return clazz == Integer.class;
    }

    @Override
    public Object convert(String source) {
        return Integer.valueOf(source);
    }
}

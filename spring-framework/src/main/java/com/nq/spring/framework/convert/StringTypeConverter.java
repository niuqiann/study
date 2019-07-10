package com.nq.spring.framework.convert;

/**
 * @author niuqian
 */
public class StringTypeConverter implements TypeConverter {
    @Override
    public Boolean isType(Class<?> clazz) {
        return clazz == String.class;
    }

    @Override
    public Object convert(String source) {
        return source;
    }
}

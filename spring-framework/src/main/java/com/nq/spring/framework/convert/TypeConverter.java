package com.nq.spring.framework.convert;

/**
 * @author niuqian
 */
public interface TypeConverter {

    /**
     * 判断是否是此类型
     * @param clazz
     * @return
     */
    Boolean isType(Class<?> clazz);

    /**
     * 转换
     * @param source
     * @return
     */
    Object convert(String source);

}

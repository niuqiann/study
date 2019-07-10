package com.nq.spring.framework.factory;

/**
 * @author niuqian
 */
public interface BeanFactory {

    /**
     * 根据beanName获取对象
     * @param beanName
     * @return
     */
    Object getBean(String beanName);

    /**
     * 根据beanTypeClass获取对象
     * @param clazz
     * @return
     */
    Object getBean(Class<?> clazz);
}

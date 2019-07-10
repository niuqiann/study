package com.nq.spring.framework.factory;

/**
 * @author niuqian
 */
public abstract class AbstarctBeanFactory implements BeanFactory {

    @Override
    public Object getBean(String beanName) {
        return null;
    }

    @Override
    public Object getBean(Class<?> clazz) {
        return null;
    }
}

package com.nq.spring.framework.config;

/**
 * 引用bean信息
 * @author niuqian
 */
public class RuntimeBeanReference {

    private String ref;

    public RuntimeBeanReference(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}

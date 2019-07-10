package com.nq.spring.framework.config;

import java.io.InputStream;

/**
 * @author niuqian
 */
public interface Resource {

    /**
     * 判断是否可读
     * @param location
     * @return
     */
    boolean isCanRead(String location);

    /**
     * 获取字节流
     * @return
     */
    InputStream getInputStream();
}

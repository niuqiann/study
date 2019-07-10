package com.nq.spring.framework.config;

import java.io.InputStream;

/**
 * @author niuqian
 */
public class ClassPathResource implements Resource {

    private String location;

    @Override
    public boolean isCanRead(String location) {
        if(location == null || "".equals(location)){
            return false;
        }
        if(location.startsWith("classpath:")){
            this.location = location.replaceAll("classpath:","");
            return true;
        }
        return false;
    }

    @Override
    public InputStream getInputStream() {
        if(location == null || "".equals(location)){
            return null;
        }
        return this.getClass().getClassLoader().getResourceAsStream(location);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

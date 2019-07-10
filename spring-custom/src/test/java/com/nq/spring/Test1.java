package com.nq.spring;

import com.nq.spring.framework.factory.BeanFactory;
import com.nq.spring.framework.factory.DefaultListableBeanFactory;
import com.nq.spring.po.Student;
import org.junit.Test;

public class Test1 {

    @Test
    public void test(){
        String location = "classpath:beans.xml";

        BeanFactory beanFactory = new DefaultListableBeanFactory(location);

        Student student = (Student) beanFactory.getBean("student");
        System.out.println(student);
    }
}

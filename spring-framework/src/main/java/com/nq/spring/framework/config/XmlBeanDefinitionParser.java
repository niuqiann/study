package com.nq.spring.framework.config;

import com.nq.spring.framework.factory.DefaultListableBeanFactory;
import com.nq.spring.framework.utils.DocumentReader;
import org.dom4j.Document;

import java.io.InputStream;
import java.util.Map;

/**
 * xmlBean解析器
 * @author niuqian
 */
public class XmlBeanDefinitionParser {

    public void loadBeanDefinitions(DefaultListableBeanFactory factory, Resource resource){
        //获取配置文件的字节流
        InputStream inputStream = resource.getInputStream();
        //将字节流对象转为Document对象
        Document document = DocumentReader.createDocument(inputStream);
        //解析document对象，数据封装到factory的beanDefinitions中
        XmlBeanDefinitionDocumentParser parser = new XmlBeanDefinitionDocumentParser(factory);
        parser.loadBeanDefinitions(document.getRootElement());
    }
}

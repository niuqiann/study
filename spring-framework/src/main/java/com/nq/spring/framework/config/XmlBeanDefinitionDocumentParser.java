package com.nq.spring.framework.config;

import com.nq.spring.framework.factory.AbstarctBeanFactory;
import com.nq.spring.framework.factory.DefaultListableBeanFactory;
import com.nq.spring.framework.utils.ReflectUtils;
import org.dom4j.Element;

import java.util.List;

/**
 *根据spring的语义来解析document对象
 * @author niuqian
 */
public class XmlBeanDefinitionDocumentParser {

    private DefaultListableBeanFactory beanFactory;

    public XmlBeanDefinitionDocumentParser(DefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void loadBeanDefinitions(Element rootElement){
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            String name = element.getName();
            if("bean".equals(name)){
                parseBeanElement(element);
            }else{

            }
        }
    }

    private void parseBeanElement(Element beanElement) {
        if(beanElement == null){
            return;
        }

        try {
            // 获取id属性
            String id = beanElement.attributeValue("id");
            // 获取name属性
            String name = beanElement.attributeValue("name");
            // 获取class属性
            String clazz = beanElement.attributeValue("class");
            Class<?> clazzType = Class.forName(clazz);
            // 获取init-method属性
            String initMethod = beanElement.attributeValue("init-method");

            //处理beanName
            String beanName = id == null ? name : id;
            beanName = beanName == null ? clazzType.getSimpleName() : beanName;

            //创建beanDefinition
            BeanDefinition beanDefinition = new BeanDefinition(beanName,clazz);
            beanDefinition.setInitMethod(initMethod);

            //获取property字标签集合
            List<Element> propertyElements = beanElement.elements();
            if(propertyElements != null && propertyElements.size() > 0){
                for (Element propertyElement : propertyElements) {
                    parsePropertyElement(beanDefinition, propertyElement);
                }
            }

            //将解析出来的BeanDefinition放到factory的beanDefinitions中
            registerBeanDefinition(beanName,beanDefinition);
            // 2.将bean信息封装到BeanDefinition对象中
            // 对bean标签解析解析
            // class信息
            // id信息
            // init-method信息
            // property标签信息----PropertyValue对象（name和value）
            // name信息
            // value信息
            // value属性---属性值、属性类型（属性赋值的时候，需要进行类型转换）TypedStringValue
            // ref属性---RuntimeBeanReference(bean的名称)---根据bean的名称获取bean的实例，将获取到的实例赋值该对象
            // 3.再将BeanDefinition放入beanDefinations集合对象中


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void parsePropertyElement(BeanDefinition beanDefinition, Element propertyElement) {
        if(propertyElement == null){
            return;
        }

        //获取标签内属性值
        String name = propertyElement.attributeValue("name");
        String value = propertyElement.attributeValue("value");
        String ref = propertyElement.attributeValue("ref");

        //value和ref不能同时存在
        if (value != null && !value.equals("") && ref != null && !ref.equals("")) {
            return;
        }

        PropertyValue pv;
        if(value != null && !"".equals(value)){
            TypeStringValue typeStringValue = new TypeStringValue(value);

            //value值肯定是beanclass的字段，所以此处根据className和字段名反射得出字段class
            Class<?> targetType = ReflectUtils.getTypeByFieldName(beanDefinition.getBeanClassName(), name);
            typeStringValue.setTargetType(targetType);

            pv = new PropertyValue(name,typeStringValue);
        }else if(ref != null && !"".equals(ref)){
            //根据bean的名称获取bean的实例，将获取到的实例赋值该对象
            RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference(ref);
            pv = new PropertyValue(name,runtimeBeanReference);
        }else{
            return;
        }

        beanDefinition.addPropertyValues(pv);
    }

    private void registerBeanDefinition(String beanName, BeanDefinition bd) {
        this.beanFactory.registerBeanDefinition(beanName, bd);
    }
}

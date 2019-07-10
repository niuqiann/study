package com.nq.spring.framework.factory;

import com.nq.spring.framework.config.*;
import com.nq.spring.framework.convert.IntegerTypeConverter;
import com.nq.spring.framework.convert.StringTypeConverter;
import com.nq.spring.framework.convert.TypeConverter;
import com.nq.spring.framework.utils.ReflectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author niuqian
 */
public class DefaultListableBeanFactory extends AbstarctBeanFactory {

    /**
     * 读取xml之后获得的BeanDefinition信息
     */
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<String, BeanDefinition>();

    /**
     * 存放bean对象
     */
    private Map<String,Object> singletonObjects = new HashMap<String, Object>();

    /**
     * 存放resource解析器
     */
    private List<Resource> resources = new ArrayList<Resource>();

    /**
     * 存放类型转换器
     */
    private List<TypeConverter> converters = new ArrayList<>();


    public DefaultListableBeanFactory(String location) {

        //注册资源类
        registerResources();
        registerConverters();

        //获取资源包装类
        Resource resource = getResource(location);

        XmlBeanDefinitionParser xmlBeanDefinitionParser = new XmlBeanDefinitionParser();
        xmlBeanDefinitionParser.loadBeanDefinitions(this,resource);

    }

    @Override
    public Object getBean(String beanName) {
        // 优化方案
        // 给对象起个名，在xml配置文件中，建立名称和对象的映射关系
        // 1.如果singletonObjects中已经包含了我们要找的对象，就不需要再创建了。
        // 2.如果singletonObjects中已经没有包含我们要找的对象，那么根据传递过来的beanName参数去BeanDefinition集合中查找对应的BeanDefinition信息
        // 3.根据BeanDefinition中的信息去创建Bean的实例。
        // a)根据class信息包括里面的constructor-arg通过反射进行实例化
        // b)根据BeanDefinition中封装的属性信息集合去挨个给对象赋值。
        // 类型转换
        // 反射赋值
        // c)根据initMethod方法去调用对象的初始化操作

        Object instance = singletonObjects.get(beanName);
        if(instance != null){
            return instance;
        }

        //目前只考虑单例
        BeanDefinition beanDefinition = this.getBeanDefinitions().get(beanName);
        String beanClassName = beanDefinition.getBeanClassName();

        //创建实例对象
        instance = this.createInstance(beanClassName,null);

        //设置属性值
        setProperty(instance,beanDefinition);

        //执行初始化方法
        initBean(instance,beanDefinition);

        return instance;
    }

    private void initBean(Object instance, BeanDefinition beanDefinition) {
        String initMethod = beanDefinition.getInitMethod();
        if(initMethod == null || "".equals(initMethod)){
            return;
        }
        ReflectUtils.invokeMethod(instance,initMethod);
    }

    private void setProperty(Object instance, BeanDefinition beanDefinition) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            String name = propertyValue.getName();
            Object value = propertyValue.getValue();

            Object valueToUse = null;
            if(value instanceof TypeStringValue){
                TypeStringValue typeStringValue = (TypeStringValue) value;
                String stringValue = typeStringValue.getValue();
                Class<?> targetType = typeStringValue.getTargetType();

                for (TypeConverter converter : converters) {
                    if(converter.isType(targetType)){
                        valueToUse = converter.convert(stringValue);
                    }
                }
            }else if(value instanceof RuntimeBeanReference){
                RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) value;
                String ref = runtimeBeanReference.getRef();

                //递归获取bean对象  此处需要注意循环依赖
                valueToUse = getBean(ref);
            }
            ReflectUtils.setProperty(instance,name,valueToUse);
        }

    }

    @Override
    public Object getBean(Class<?> clazz) {
        return super.getBean(clazz);
    }

    private void registerResources(){
        resources.add(new ClassPathResource());
    }

    private Resource getResource(String location){
        for (Resource resource : resources) {
            if(resource.isCanRead(location)){
                return resource;
            }
        }
        return null;
    }

    private void registerConverters(){
        converters.add(new StringTypeConverter());
        converters.add(new IntegerTypeConverter());
    }

    private Object createInstance(String beanClassName,Object... args){
        return ReflectUtils.createObject(beanClassName,args);
    }

    public Map<String, BeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        this.beanDefinitions.put(beanName, beanDefinition);
    }
}

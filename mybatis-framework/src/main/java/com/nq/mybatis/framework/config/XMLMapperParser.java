package com.nq.mybatis.framework.config;

import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLMapperParser {

    private Configuration configuration;

    private String namespace;

    public XMLMapperParser(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(Element rootElement){
        namespace = rootElement.attributeValue("namespace");

        // 将解析出来的MappedStatement对象放入Configuration对象中的map集合
        parseStatements(rootElement.elements("select"));

        /*List<Element> elements = rootElement.elements();
        Map<String,String> statementMap = new HashMap<>();
        for (Element element : elements) {
            String id = element.attributeValue("id");
            String sql = element.getTextTrim();
            statementMap.put(id,sql);
        }
        MappedStatement mapperStatement = new MappedStatement();
        mapperStatement.setStatementMap(statementMap);
        configuration.getMapperStatementMap().put(namespace,mapperStatement);*/
    }

    private void parseStatements(List<Element> elements) {
        for (Element element : elements) {
            parseStatement(element);
        }
    }

    private void parseStatement(Element element) {
        String id = element.attributeValue("id");
        String statementId = namespace + "." + id;

        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterClass = getClassType(parameterType);

        String resultType = element.attributeValue("resultType");
        Class<?> resultClass = getClassType(resultType);

        String statementType = element.attributeValue("statementType");

        // 还包含#{id}占位符的SQL语句
        // 此时拿到未解析的SQL语句，还需要进行特殊解析
        // 使用面向对象思想，创建SqlSource对象（提供获取SQL语句和SQL语句中的参数这个功能）

        // 获取的SQL语句：select * from user where id = #{id}
        // 需要的SQL语句：select * from user where id = ?
        String sqlText = element.getTextTrim();
        SqlSource sqlSource = new SqlSource(sqlText);

        // 封装MappedStatement对象  源码是用构建者模式创建的
        MappedStatement mappedStatement = new MappedStatement(id,parameterClass,resultClass,statementType,sqlSource);
        configuration.addMappedStatement(statementId, mappedStatement);
    }

    private Class<?> getClassType(String className){
        if(className == null || "".equals(className)){
            return null;
        }

        Class<?> clazz = null;

        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return clazz;
    }
}

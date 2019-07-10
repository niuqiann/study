package com.nq.mybatis.framework.config;

import com.nq.mybatis.framework.sqlsession.DocumentReader;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigParse {

    private Configuration configuration;

    public XMLConfigParse(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration parseConfiguration(Element element){
        parseEnvironments(element.element("environments"));
        parseMappers(element.element("mappers"));

        return configuration;
    }

    private void parseMappers(Element element) {
        List<Element> mappers = element.elements("mapper");
        for (Element mapper : mappers) {
            parseMapper(mapper);
        }
    }

    private void parseMapper(Element mapper) {
        String resource = mapper.attributeValue("resource");
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        Document document = DocumentReader.read(inputStream);
        XMLMapperParser xmlMapperParser = new XMLMapperParser(configuration);
        xmlMapperParser.parse(document.getRootElement());
    }

    private void parseEnvironments(Element element) {
        String defaultId = element.attributeValue("default");
        List<Element> environments = element.elements("environment");
        for (Element environment : environments) {
            String envId = environment.attributeValue("id");
            if(envId != null && envId.equals(defaultId)){
                parseDataSource(environment.element("dataSource"));
            }
        }
    }

    private void parseDataSource(Element element) {
        String type = element.attributeValue("type");
        if(type == null || "".equals(type)){
            type = "DBCP";
        }
        List<Element> elements = element.elements("property");
        Properties properties = new Properties();
        for (Element property : elements) {
            String name = property.attributeValue("name");
            String value = property.attributeValue("value");
            properties.setProperty(name,value);
        }

        BasicDataSource dataSource = null;
        if("DBCP".equals(type)){
            dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
        }

        configuration.setDataSource(dataSource);
    }
}

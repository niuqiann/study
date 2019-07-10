package com.nq.mybatis.framework.sqlsession;

import com.nq.mybatis.framework.config.Configuration;
import com.nq.mybatis.framework.config.XMLConfigParse;
import org.dom4j.Document;

import java.io.InputStream;
import java.util.HashMap;

public class SqlSessionFactoryBuilder {

    //封装全局配置文件信息和所有映射文件信息
    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        this.configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream inputStream){
        //解析全局配置文件，封装为configuration对象
        //通过InputStream流对象，去创建Document对象（dom4j）
        //DocumentReader-- 加载InputStream流创建Document对象
        Document document = DocumentReader.read(inputStream);
        XMLConfigParse xmlConfigParse = new XMLConfigParse(configuration);
        xmlConfigParse.parseConfiguration(document.getRootElement());
        return build();
    }

    private SqlSessionFactory build(){
        return new DefaultSqlSessionFactory(configuration);
    }
}

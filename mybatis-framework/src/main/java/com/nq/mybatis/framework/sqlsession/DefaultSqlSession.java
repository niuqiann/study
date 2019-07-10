package com.nq.mybatis.framework.sqlsession;

import com.nq.mybatis.framework.config.Configuration;
import com.nq.mybatis.framework.config.MappedStatement;

import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statementId, Object param) {
        List<Object> objects = this.selectList(statementId, param);
        if(objects != null && objects.size() > 0){
            return (T) objects.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object param) {
        // 此处是真正和数据库交互的地方
        // 根据statementId获取MappedStatement对象，执行sql

        //执行时用执行器去执行的  执行器有基本执行器，缓存执行器等
        Executor executor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatements().get(statementId);

        return executor.query(configuration,mappedStatement,param);
    }
}

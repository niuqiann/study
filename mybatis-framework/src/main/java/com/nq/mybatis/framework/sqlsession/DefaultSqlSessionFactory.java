package com.nq.mybatis.framework.sqlsession;

import com.nq.mybatis.framework.config.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() throws SQLException {
        return new DefaultSqlSession(configuration);
    }
}

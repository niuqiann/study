package com.nq.mybatis.framework.sqlsession;

import com.nq.mybatis.framework.config.Configuration;
import com.nq.mybatis.framework.config.MappedStatement;

import java.util.List;

public interface Executor {

    <T> List<T> query(Configuration configuration, MappedStatement mappedStatement,Object param);
}

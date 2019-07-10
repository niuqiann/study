package com.nq.mybatis.framework.config;

import lombok.Data;

@Data
public class MappedStatement {

    private String id;

    private Class<?> parameterTypeClass;

    private Class<?> resultTypeClass;

    private String statementType;

    private SqlSource sqlSource;

    public MappedStatement(String id, Class<?> parameterTypeClass, Class<?> resultTypeClass, String statementType, SqlSource sqlSource) {
        this.id = id;
        this.parameterTypeClass = parameterTypeClass;
        this.resultTypeClass = resultTypeClass;
        this.statementType = statementType;
        this.sqlSource = sqlSource;
    }
}

package com.nq.mybatis.framework.config;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Data
public class Configuration {

    private DataSource dataSource;

    private Map<String, MappedStatement> mappedStatements = new HashMap<>();

    public void addMappedStatement(String statementId, MappedStatement mappedStatement){
        mappedStatements.put(statementId,mappedStatement);
    }

}

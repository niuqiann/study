package com.nq.mybatis.framework.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BoundSql {

    //存放解析出来的Sql语句
    private String sql;
    //存放解析出来的参数
    private List<ParameterMapping> parameterMappings = new ArrayList();

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    public void addParameterMapping(ParameterMapping parameterMapping){
        parameterMappings.add(parameterMapping);
    }

}

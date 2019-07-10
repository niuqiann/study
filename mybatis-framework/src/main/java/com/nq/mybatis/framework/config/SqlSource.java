package com.nq.mybatis.framework.config;

import com.nq.mybatis.framework.utils.GenericTokenParser;
import com.nq.mybatis.framework.utils.ParameterMappingTokenHandler;

public class SqlSource {

    private String sqlText;

    public SqlSource(String sqlText) {
        this.sqlText = sqlText;
    }

    public BoundSql getBoundSql(){
        //解析Sql文本
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{","}",tokenHandler);
        String sql = genericTokenParser.parse(sqlText);

        return new BoundSql(sql,tokenHandler.getParameterMappings());

    }
}

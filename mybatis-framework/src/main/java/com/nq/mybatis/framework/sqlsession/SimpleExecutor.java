package com.nq.mybatis.framework.sqlsession;

import com.nq.mybatis.framework.config.*;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    @Override
    public <T> List<T> query(Configuration configuration, MappedStatement mappedStatement, Object param) {
        /*
		 * a).获取连接 //读取配置文件，获取数据源对象，根据数据源获取连接 通过Configuration对象获取DataSource对象
		 * 通过DataSource对象，获取Connection
		 */

        Connection connection = null;
        List<T> resultList = new ArrayList();
        try {
            DataSource dataSource = configuration.getDataSource();
            connection = dataSource.getConnection();

            SqlSource sqlSource = mappedStatement.getSqlSource();
            BoundSql boundSql = sqlSource.getBoundSql();
            String sql = boundSql.getSql();

            String statementType = mappedStatement.getStatementType();
            if("prepared".equals(statementType)){
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                //获取参数名
                List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();

                Class<?> parameterTypeClass = mappedStatement.getParameterTypeClass();
                //如果是基本数据类型 直接设置参数
                if(param != null){
                    if(parameterTypeClass == Integer.class){
                        preparedStatement.setObject(1,param);
                    }else{
                        //TODO 此处只处理POJO类型的参数

                        for (int i = 0; i < parameterMappings.size(); i++) {
                            ParameterMapping parameterMapping = parameterMappings.get(i);

                            String name = parameterMapping.getName();

                            Field field = parameterTypeClass.getDeclaredField(name);
                            field.setAccessible(true);
                            Object value = field.get(param);
                            preparedStatement.setObject(i + 1 , value);
                        }
                    }
                }


                ResultSet resultSet = preparedStatement.executeQuery();
                Class<?> resultTypeClass = mappedStatement.getResultTypeClass();

                while(resultSet.next()){
                    Object obj = resultTypeClass.newInstance();
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 0; i < columnCount; i++) {
                        String columnName = metaData.getColumnName(i + 1);
                        Field field = resultTypeClass.getDeclaredField(columnName);
                        field.setAccessible(true);
                        field.set(obj,resultSet.getObject(columnName));
                    }
                    resultList.add((T) obj);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultList;
    }
}

package com.nq.mybatis.framework.sqlsession;

import java.sql.SQLException;

public interface SqlSessionFactory {

    SqlSession openSession() throws SQLException;

}

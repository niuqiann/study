package com.nq.test;

import com.nq.mybatis.framework.sqlsession.SqlSession;
import com.nq.mybatis.framework.sqlsession.SqlSessionFactory;
import com.nq.mybatis.framework.sqlsession.SqlSessionFactoryBuilder;
import com.nq.mybatis.po.User;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class Test1 {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init(){
        String resource = "SqlMapConfig.xml";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test() throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<Object> objects = sqlSession.selectList("test.findUsers", null);
        System.out.println(objects);
    }

}

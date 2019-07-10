package com.nq.mybatis.dao;

import com.nq.mybatis.po.User;

public interface IUserDao {

    User queryUserById(Integer id);

}

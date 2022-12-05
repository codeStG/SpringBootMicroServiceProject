package com.stgcodes.dao;

import org.springframework.stereotype.Component;

import com.stgcodes.entity.UserEntity;

@Component("userDao")
public class UserDaoImpl extends DaoImpl<UserEntity> implements UserDao {
}

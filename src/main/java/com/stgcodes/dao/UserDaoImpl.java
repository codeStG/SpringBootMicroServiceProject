package com.stgcodes.dao;

import com.stgcodes.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component("userDao")
public class UserDaoImpl extends DaoImpl<UserEntity> implements UserDao {
}

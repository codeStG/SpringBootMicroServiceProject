package com.stgcodes.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.stgcodes.entity.UserEntity;
import com.stgcodes.model.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userToUserEntity(User user);

    User userEntityToUser(UserEntity userEntity);
}

package com.stgcodes.mappers;

import com.stgcodes.entity.UserEntity;
import com.stgcodes.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = UserAgeMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity userToUserEntity(User user);

    @Mapping(target = "age", source = "dateOfBirth")
    User userEntityToUser(UserEntity userEntity);
}

package com.stgcodes.mappers;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.entity.UserEntity;
import com.stgcodes.model.User;
import com.stgcodes.validation.enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class UserMapperTests {

    @Test
    void testMapModelToEntity() {
        User user = User.builder()
                .firstName("Bryan")
                .lastName("Byard")
                .username("brbyard")
                .dateOfBirth(LocalDate.of(1978, 7, 11))
                .socialSecurityNumber("123-45-6777")
                .gender(Gender.MALE)
                .email("brbyard@gmail.com")
                .phones(new ArrayList<>(List.of(new PhoneEntity())))
                .build();

        UserEntity userEntity = UserMapper.INSTANCE.userToUserEntity(user);

        Assertions.assertEquals(user.getFirstName(), userEntity.getFirstName());
        Assertions.assertEquals(user.getLastName(), userEntity.getLastName());
        Assertions.assertEquals(user.getUsername(), userEntity.getUsername());
        Assertions.assertEquals(user.getDateOfBirth(), userEntity.getDateOfBirth());
        Assertions.assertEquals(user.getSocialSecurityNumber(), userEntity.getSocialSecurityNumber());
        Assertions.assertEquals(user.getGender(), userEntity.getGender());
        Assertions.assertEquals(user.getEmail(), userEntity.getEmail());
        Assertions.assertEquals(user.getPhones(), userEntity.getPhones());
    }

    @Test
    void testMapEntityToModel() {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName("User");
        userEntity.setLastName("Name");
        userEntity.setUsername("username");
        userEntity.setDateOfBirth(LocalDate.of(1973, 01,01));
        userEntity.setSocialSecurityNumber("123-45-6789");
        userEntity.setGender(Gender.REFUSE);
        userEntity.setEmail("email@address.com");

        User user = UserMapper.INSTANCE.userEntityToUser(userEntity);

        Assertions.assertEquals(userEntity.getFirstName(), user.getFirstName());
        Assertions.assertEquals(userEntity.getLastName(), user.getLastName());
        Assertions.assertEquals(userEntity.getUsername(), user.getUsername());
        Assertions.assertEquals(userEntity.getDateOfBirth(), user.getDateOfBirth());
        Assertions.assertEquals(userEntity.getSocialSecurityNumber(), user.getSocialSecurityNumber());
        Assertions.assertEquals(userEntity.getGender(), user.getGender());
        Assertions.assertEquals(userEntity.getEmail(), user.getEmail());

        int expectedAge = LocalDate.now().getYear() - userEntity.getDateOfBirth().getYear();
        Assertions.assertEquals(expectedAge, user.getAge());
    }

    @Test
    void testMapNullModelToNullEntity() {
        UserEntity userEntity = UserMapper.INSTANCE.userToUserEntity(null);
        Assertions.assertNull(userEntity);
    }

    @Test
    void testMapNullEntityToNullModel() {
        User user = UserMapper.INSTANCE.userEntityToUser(null);
        Assertions.assertNull(user);
    }
}

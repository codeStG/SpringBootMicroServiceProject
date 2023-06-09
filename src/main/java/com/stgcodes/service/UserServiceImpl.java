package com.stgcodes.service;

import com.stgcodes.criteria.UserCriteria;
import com.stgcodes.dao.UserDao;
import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.entity.UserEntity;
import com.stgcodes.mappers.UserMapper;
import com.stgcodes.model.User;
import com.stgcodes.specifications.user.UserSpecifications;
import com.stgcodes.utils.sorting.UserComparator;
import com.stgcodes.validation.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Component("userService")
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final  UserDao dao;
    private final  UserSpecifications specs;
    private final  UserValidator validator;

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        dao.findAll().forEach(e -> users.add(mapToModel(e)));
        users.sort(new UserComparator());

        return users;
    }

    @Override
    public List<User> findByCriteria(UserCriteria searchCriteria) {
        List<User> result = new ArrayList<>();
                
        dao.findAll(specs.whereMatches(searchCriteria))
                .forEach(entity -> result.add(mapToModel(entity)));

        result.sort(new UserComparator());

        return result;
    }

    @Override
    public User findById(Long userId) {
        UserEntity userEntity;
        userEntity = dao.findById(userId);

        return mapToModel(userEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = DataAccessException.class)
    public User save(User user) {
        validator.validate(user);
        
        UserEntity userEntity = mapToEntity(user);
        user.getPhones().forEach(phone -> phone.setUserEntity(userEntity));
        user.setAge(calculateAge(user.getDateOfBirth()));
        
        UserEntity result = dao.save(userEntity);
        
        return mapToModel(result);
    }

    @Override
    public User update(User user, Long userId) {
        UserEntity existingUser = dao.findById(userId);
        List<PhoneEntity> phones = existingUser.getPhones();
        validator.validate(user);

        UserEntity userEntity = mapToEntity(user);
        userEntity.setPhones(phones);
        userEntity.setUserId(userId);

        UserEntity result = dao.update(userEntity);

        return mapToModel(result);
    }

    @Override
    public void delete(Long userId) {
        UserEntity userEntity = dao.findById(userId);

        dao.delete(userEntity);
    }

    private int calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    private UserEntity mapToEntity(User user) {
        return UserMapper.INSTANCE.userToUserEntity(user);
    }

    private User mapToModel(UserEntity userEntity) {
        return UserMapper.INSTANCE.userEntityToUser(userEntity);
    }
}

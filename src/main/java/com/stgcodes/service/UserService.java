package com.stgcodes.service;

import java.util.List;

import com.stgcodes.criteria.UserCriteria;
import com.stgcodes.model.User;

public interface UserService {
    List<User> findAll();
    List<User> findByCriteria(UserCriteria criteria);
    User findById(Long userId);
    User save(User user);
    User update(User user, Long userId);
    void delete(Long userId);
}
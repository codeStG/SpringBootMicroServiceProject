package com.stgcodes.service;

import com.stgcodes.criteria.UserCriteria;
import com.stgcodes.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    List<User> findByCriteria(UserCriteria criteria);
    User findById(Long userId);
    User save(User user);
    User update(User user, Long userId);
    void delete(Long userId);
}
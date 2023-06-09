package com.stgcodes.service;


import com.stgcodes.entity.UserEntity;
import com.stgcodes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserServiceJpa {

    private final UserRepository repository;

    public List<Optional<UserEntity>> findByLastName(String lastName) {
        return repository.findByLastName(lastName);
    }
}

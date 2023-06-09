package com.stgcodes.repository;

import com.stgcodes.entity.UserEntity;
import com.stgcodes.validation.enums.Gender;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    List<Optional<UserEntity>> findByLastName(String lastName);

    List<Optional<UserEntity>> findByGender(Gender gender, Sort sort);
}

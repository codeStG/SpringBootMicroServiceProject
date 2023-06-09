package com.stgcodes.config;

import com.stgcodes.entity.UserEntity;
import com.stgcodes.repository.UserRepository;
import com.stgcodes.service.UserServiceJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class SampleInsert implements ApplicationListener<ApplicationStartedEvent> {

        private final UserRepository repo;
        private final  UserServiceJpa service;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        findAll();
    }

    private void findById() {
        Optional<UserEntity> optional = repo.findById(14L);
        log.info("***********************************************");
        log.info(optional.get().toString());
        log.info("***********************************************");
    }

    private void findByLastName() {
        List<Optional<UserEntity>> list = service.findByLastName("Brady");
        log.info("***********************************************");
        for(Optional each : list) {
            if(each.isPresent()) {
                log.info(each.get().toString());
            }
            else {
                log.info("Nothing returned ");
            }
        }
        log.info("***********************************************");
    }

    private void findAll() {
        Page<UserEntity> pages = repo.findAll(PageRequest.of(1,3));
        log.info("***********************************************");
        pages.forEach(each -> log.info(each.toString()));


        pages = repo.findAll(PageRequest.of(2,5));

        pages.forEach(each -> {
            log.info(each.toString());
        });
        log.info("***********************************************");
    }

}
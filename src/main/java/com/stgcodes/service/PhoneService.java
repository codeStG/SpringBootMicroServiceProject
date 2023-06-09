package com.stgcodes.service;

import com.stgcodes.model.Phone;

import java.util.List;

public interface PhoneService {
    List<Phone> findAll();
    Phone findById(Long phoneId);
    Phone save(Phone phone, Long phoneId);
    Phone update(Phone phone, Long phoneId);
    void delete(Long phoneId);
}


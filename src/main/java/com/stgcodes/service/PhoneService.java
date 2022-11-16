package com.stgcodes.service;

import java.util.List;

import com.stgcodes.model.Phone;

public interface PhoneService {
    List<Phone> findAll();
    Phone findById(Long phoneId);
    Phone save(Phone phone, Long phoneId);
    Phone update(Phone phone, Long phoneId);
    void delete(Long phoneId);
}


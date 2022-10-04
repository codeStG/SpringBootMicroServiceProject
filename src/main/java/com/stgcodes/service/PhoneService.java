package com.stgcodes.service;

import com.stgcodes.model.Phone;

import java.util.List;

public interface PhoneService {

    List<Phone> getAllPhones();
    Phone getPhoneById(Long phoneId);
    void addPhone(Phone phone);
    void deletePhone(Long phoneId);
}


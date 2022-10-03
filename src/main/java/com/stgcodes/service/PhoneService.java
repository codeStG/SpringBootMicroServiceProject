package com.stgcodes.service;

import com.stgcodes.model.Phone;

import java.util.List;

public interface PhoneService {

    List<Phone> getAllPhones();
    Phone getPhoneById(Long phoneId);
    Phone addPhone(Phone phone);
    Phone deletePhone(Long phoneId);
}


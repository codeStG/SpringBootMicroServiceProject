package com.stgcodes.service;

import com.stgcodes.model.Phone;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public interface PhoneService {

    List<Phone> getAllPhones();
    Phone getPhoneById(Long phoneId);
    Phone addPhone(Phone phone);
    void deletePhone(Long phoneId);
}


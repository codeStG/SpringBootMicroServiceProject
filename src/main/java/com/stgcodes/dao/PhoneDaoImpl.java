package com.stgcodes.dao;

import org.springframework.stereotype.Component;

import com.stgcodes.entity.PhoneEntity;

@Component("phoneDao")
public class PhoneDaoImpl extends DaoImpl<PhoneEntity> implements PhoneDao {

}
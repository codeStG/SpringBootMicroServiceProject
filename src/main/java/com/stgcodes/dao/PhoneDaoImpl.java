package com.stgcodes.dao;

import org.springframework.stereotype.Component;

import com.stgcodes.entity.PhoneEntity;

import lombok.extern.slf4j.Slf4j;

@Component("phoneDao")
@Slf4j
public class PhoneDaoImpl extends DaoImpl<PhoneEntity> implements PhoneDao {

}
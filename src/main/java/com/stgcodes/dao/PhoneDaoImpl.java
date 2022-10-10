package com.stgcodes.dao;

import com.stgcodes.entity.PhoneEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("phoneDao")
@Slf4j
public class PhoneDaoImpl extends DaoImpl<PhoneEntity> implements PhoneDao {

}
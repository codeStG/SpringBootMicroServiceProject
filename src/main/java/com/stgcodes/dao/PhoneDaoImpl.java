package com.stgcodes.dao;

import com.stgcodes.entity.PhoneEntity;
import com.stgcodes.mappers.PhoneMapper;
import com.stgcodes.model.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("phoneDao")
@Slf4j
public class PhoneDaoImpl extends DaoImpl<PhoneEntity> implements PhoneDao {

}
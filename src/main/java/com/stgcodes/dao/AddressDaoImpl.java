package com.stgcodes.dao;

import com.stgcodes.entity.AddressEntity;
import com.stgcodes.mappers.AddressMapper;
import com.stgcodes.model.Address;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("addressDao")
public class AddressDaoImpl extends DaoImpl<AddressEntity> implements AddressDao {

}
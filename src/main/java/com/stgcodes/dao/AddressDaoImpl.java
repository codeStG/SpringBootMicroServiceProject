package com.stgcodes.dao;

import org.springframework.stereotype.Component;

import com.stgcodes.entity.AddressEntity;

@Component("addressDao")
public class AddressDaoImpl extends DaoImpl<AddressEntity> implements AddressDao {

}
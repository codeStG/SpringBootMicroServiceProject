package com.stgcodes.dao;

import com.stgcodes.entity.AddressEntity;
import org.springframework.stereotype.Component;

@Component("addressDao")
public class AddressDaoImpl extends DaoImpl<AddressEntity> implements AddressDao {

}
package com.stgcodes;

import com.stgcodes.dao.PersonDao;
import com.stgcodes.entity.PersonEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
public class FirstmicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstmicroserviceApplication.class, args);
	}
}
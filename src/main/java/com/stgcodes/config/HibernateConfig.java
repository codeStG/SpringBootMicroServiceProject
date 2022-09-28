package com.stgcodes.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Slf4j
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        Properties props = new Properties();

        props.setProperty("hibernate.dialect", env.getProperty("spring.jpa.database-platform"));
        props.setProperty("hibernate.hbm2dll.auto", "create-drop");
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");

        localSessionFactoryBean.setDataSource(dataSource());

        localSessionFactoryBean.setHibernateProperties(props);

        localSessionFactoryBean.setPackagesToScan("com.stgcodes.entity");

        return localSessionFactoryBean;
    }

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username(env.getProperty("spring.datasource.username"))
                .password(env.getProperty("spring.datasource.password"))
                .url(env.getProperty("spring.datasource.url"))
                .driverClassName(env.getProperty("spring.datasource.driverClassName"))
                .build();
    }

    @PostConstruct
    public void onPostConstruct() {
        log.info("h2 database up");
    }
}
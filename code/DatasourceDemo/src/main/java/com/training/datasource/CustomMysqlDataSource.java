package com.training.datasource;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//@Profile("mysql")
@EnableJpaRepositories(basePackages = {"com.training.mysql", "com.training.data"})
@Configuration
public class CustomMysqlDataSource {

    @Primary
    @Bean("mysqlProps")
    @ConfigurationProperties("mysql.spring.datasource")
    public DataSourceProperties mysqlDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource mysqlDataSource(@Qualifier("mysqlProps") DataSourceProperties mysqlDataSourceProps) {
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.driverClassName(mysqlDataSourceProps.getDriverClassName());
        dataSource.url(mysqlDataSourceProps.getUrl());
        dataSource.username(mysqlDataSourceProps.getUsername());
        dataSource.password(mysqlDataSourceProps.getPassword());

        return dataSource.build();
    }

}
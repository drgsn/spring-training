package com.training.config;

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
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Profile("prod")
@Component
public class CustomMysqlDataSource {

    @Bean(name = "mysqlDataSourceProps")
    @ConfigurationProperties("mysql.spring.datasource")
    public DataSourceProperties mysqlDataSourceProps() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource mysqlDataSource(@Qualifier("mysqlDataSourceProps") DataSourceProperties mysqlDataSourceProps) {


        /** // works for mysql doesn't work for h2
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(mysqlDataSourceProps.getDriverClassName());
        dataSource.setUrl(mysqlDataSourceProps.getUrl());
        dataSource.setUsername(mysqlDataSourceProps.getUsername());
        dataSource.setPassword(mysqlDataSourceProps.getPassword());

        return dataSource;*/

        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.driverClassName(mysqlDataSourceProps.getDriverClassName());
        dataSource.url(mysqlDataSourceProps.getUrl());
        dataSource.username(mysqlDataSourceProps.getUsername());
        dataSource.password(mysqlDataSourceProps.getPassword());

        return dataSource.build();
    }

}

package com.training.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//@Profile("h2")
@EnableJpaRepositories(basePackages = "com.training.h2")
@Configuration
public class CustomH2DataSource {

    @Bean("h2Props")
    @ConfigurationProperties("h2.spring.datasource")
    public DataSourceProperties h2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource h2DataSource(@Qualifier("h2Props") DataSourceProperties h2DataSourceProperties){
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.driverClassName(h2DataSourceProperties.getDriverClassName());
        dataSource.url(h2DataSourceProperties.getUrl());
        dataSource.username(h2DataSourceProperties.getUsername());
        dataSource.password(h2DataSourceProperties.getPassword());

        return dataSource.build();
    }

}

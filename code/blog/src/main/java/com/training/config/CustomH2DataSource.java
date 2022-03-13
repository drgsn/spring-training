package com.training.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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


@Profile("!prod")
@Component
public class CustomH2DataSource {

    @Bean(name = "h2DataSourceProperties")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties h2DataSourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource h2DataSource(@Qualifier("h2DataSourceProperties") DataSourceProperties h2DataSourceProperties){
        DataSourceBuilder dataSource = DataSourceBuilder.create();
        dataSource.driverClassName(h2DataSourceProperties.getDriverClassName());
        dataSource.url(h2DataSourceProperties.getUrl());
        dataSource.username(h2DataSourceProperties.getUsername());
        dataSource.password(h2DataSourceProperties.getPassword());

        return dataSource.build();
    }

}

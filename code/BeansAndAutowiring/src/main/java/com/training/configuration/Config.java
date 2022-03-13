package com.training.configuration;

import com.training.beans.Organization;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {

    @Bean(autowire = Autowire.BY_NAME)
//    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Organization organization(){
        return new Organization("Test Name 2");
    }
}

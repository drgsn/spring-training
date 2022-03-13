package com.training;

import com.training.beans.Organization;
import com.training.configuration.Config;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Stream;

public class AnnotationApplication {

    public static void main(String[] args) {
        /**
         * load using package scanning -> if we change it to com.training then the config class will be loaded!!
         */
        ApplicationContext ctx = new AnnotationConfigApplicationContext("com.training.beans");
        Stream.of(ctx.getBeanDefinitionNames()).forEach(bean -> System.out.println(bean));

        Organization org = ctx.getBean(Organization.class);
        System.out.println(org.getName());

        /**
         * load using config class
         */
        ctx = new AnnotationConfigApplicationContext(Config.class);
        Stream.of(ctx.getBeanDefinitionNames()).forEach(bean -> System.out.println(bean));

        org = ctx.getBean(Organization.class);
        System.out.println(org.getName());
    }
}

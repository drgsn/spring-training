package com.training;

import com.training.beans.Organization;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.stream.Stream;

public class XmlApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring/bean-scan.xml");
        Stream.of(ctx.getBeanDefinitionNames()).forEach(bean -> System.out.println(bean));

        Organization org = ctx.getBean(Organization.class);
        System.out.println(org.getName());

        ctx = new ClassPathXmlApplicationContext("spring/bean-declaration.xml");
        Stream.of(ctx.getBeanDefinitionNames()).forEach(bean -> System.out.println(bean));

        org = ctx.getBean("organization", Organization.class);
        System.out.println(org.getName());

        Organization org2 = ctx.getBean("organization2", Organization.class);
        System.out.println(org2.getName());

    }
}

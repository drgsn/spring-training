package com.training.service;


import com.training.data.rest.model.User;
import com.training.data.rest.repository.UserRepository;
import com.training.h2.model.H2Employee;
import com.training.h2.repository.H2EmployeeRepository;
import com.training.mongo.model.MongoEmployee;
import com.training.mongo.repository.MongoEmployeeRepository;
import com.training.mysql.model.MysqlEmployee;
import com.training.mysql.repository.MysqlEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DataService {

    @Autowired
    private MongoRepository mongoRepository;

    @Autowired
    private MongoEmployeeRepository mongoEmployeeRepository;

    @Autowired
    private MysqlEmployeeRepository mysqlRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private H2EmployeeRepository h2Repository;

    @PostConstruct
    public void testDataSources() {

        System.out.println("================== Multiple Data Sources ==================");
        H2Employee h2Employee = new H2Employee();
        MongoEmployee mongoEmployee = new MongoEmployee();
        MysqlEmployee mysqlEmployee = new MysqlEmployee();

        h2Repository.save(h2Employee);
        System.out.println(h2Employee);

        // use the mongo repository interface
        // mongoRepository.insert(mongoEmployee);
        mongoEmployeeRepository.save(mongoEmployee);
        System.out.println(mongoEmployee);

        mysqlRepository.save(mysqlEmployee);
        System.out.println(mysqlEmployee);

        System.out.println("============================ DATA REST ======================");

        User user = new User();
        userRepository.save(user);
        System.out.println(user);

    }

}

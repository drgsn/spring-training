package com.training.mongo.repository;

import com.training.mongo.model.MongoEmployee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoEmployeeRepository extends MongoRepository<MongoEmployee, String> {
}

package com.training.mysql.repository;

import com.training.mysql.model.MysqlEmployee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MysqlEmployeeRepository extends CrudRepository<MysqlEmployee, Long> {
}

package com.training.h2.repository;

import com.training.h2.model.H2Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface H2EmployeeRepository extends CrudRepository<H2Employee, Long> {

    H2Employee findByFirstName(String firstName);
}

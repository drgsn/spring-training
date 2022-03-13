package com.training.service;

import com.training.model.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    public Employee createEmployee(String name, String empId) {

        Employee emp = new Employee();
        emp.setId(empId);
        emp.setName(name);
        return emp;
    }

    public void deleteEmployee(String empId) {

    }
}

package com.training.controller;

import com.training.model.Employee;
import com.training.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // http://localhost:8080/add/employee?name=Test&id=10
    @RequestMapping(value = "/add/employee", method = RequestMethod.GET)
    public Employee addEmployee(@RequestParam("name") String name, @RequestParam("id") String empId) {
        return employeeService.createEmployee(name, empId);
    }

    // http://localhost:8080/remove/employee?id=10
    @RequestMapping(value = "/remove/employee", method = RequestMethod.GET)
    public String removeEmployee(@RequestParam("id") String empId) {
        employeeService.deleteEmployee(empId);
        return "Employee removed";
    }

}

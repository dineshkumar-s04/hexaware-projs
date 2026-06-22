package com.hexaware.springboot.mvc.service;

import java.util.List;

import com.hexaware.springboot.mvc.model.Employee;
import com.hexaware.springboot.mvc.exception.EmployeeNotFoundException;

public interface IEmployeeService {

    int addEmployee(Employee emp);

    int updateEmployee(Employee emp);

    int deleteEmployee(int eid);

    List<Employee> getAllEmployees();

    List<Employee> getBySalaryGT(double sal);

    Employee getByEid(int eid) throws EmployeeNotFoundException;
}
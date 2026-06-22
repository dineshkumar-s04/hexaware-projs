package com.hexaware.springboot.mvc.dao;

import java.util.List;

import com.hexaware.springboot.mvc.exception.EmployeeNotFoundException;
import com.hexaware.springboot.mvc.model.Employee;

public interface IEmployeeDao {

    int addEmployee(Employee emp);

    int updateEmployee(Employee emp);

    int deleteEmployee(int eid);

    List<Employee> getAllEmployees();

    List<Employee> getBySalaryGT(double sal);

    Employee getByEid(int eid) throws EmployeeNotFoundException;
}
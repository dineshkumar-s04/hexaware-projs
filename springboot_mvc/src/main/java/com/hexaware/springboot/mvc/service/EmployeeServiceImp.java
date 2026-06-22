package com.hexaware.springboot.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.springboot.mvc.dao.IEmployeeDao;
import com.hexaware.springboot.mvc.model.Employee;
import com.hexaware.springboot.mvc.exception.EmployeeNotFoundException;

@Service
public class EmployeeServiceImp implements IEmployeeService {

    @Autowired
    private IEmployeeDao dao;

    @Override
    public int addEmployee(Employee emp) {
        return dao.addEmployee(emp);
    }

    @Override
    public int updateEmployee(Employee emp) {
        return dao.updateEmployee(emp);
    }

    @Override
    public int deleteEmployee(int eid) {
        return dao.deleteEmployee(eid);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return dao.getAllEmployees();
    }

    @Override
    public List<Employee> getBySalaryGT(double sal) {
        return dao.getBySalaryGT(sal);
    }

    @Override
    public Employee getByEid(int eid) throws EmployeeNotFoundException {
        return dao.getByEid(eid);
    }

    public static boolean validateData(Employee emp) {

        return emp.getEid() > 99
                && emp.getEname().length() > 3
                && emp.getSalary() > 5000;
    }
}
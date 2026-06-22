package com.hexaware.springweb.mvc.dao;

import java.util.List;

import com.hexaware.springweb.mvc.model.Employee;

public interface IEmployeeDao {
	
	
	public int  addEmp(Employee emp);
	
	public List<Employee> getAll();

}

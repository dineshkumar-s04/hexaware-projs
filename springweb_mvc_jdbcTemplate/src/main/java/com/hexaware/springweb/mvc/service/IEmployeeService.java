package com.hexaware.springweb.mvc.service;

import java.util.List;

import com.hexaware.springweb.mvc.model.Employee;

public interface IEmployeeService {
	
	
		public int  addEmp(Employee emp);
		
		public List<Employee> getAll();

}
 
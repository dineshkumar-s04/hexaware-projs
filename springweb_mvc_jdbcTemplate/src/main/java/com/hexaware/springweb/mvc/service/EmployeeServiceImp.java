package com.hexaware.springweb.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.springweb.mvc.dao.IEmployeeDao;
import com.hexaware.springweb.mvc.model.Employee;

@Service
public class EmployeeServiceImp implements IEmployeeService {
	
		@Autowired
		IEmployeeDao  dao;
		
	

	@Override
	public int addEmp(Employee emp) {
		
		
		return  dao.addEmp(emp);
	}



	@Override
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return  dao.getAll();
	}

}

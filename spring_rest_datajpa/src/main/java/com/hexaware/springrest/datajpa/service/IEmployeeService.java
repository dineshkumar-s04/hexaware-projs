package com.hexaware.springrest.datajpa.service;

import java.util.List;

import com.hexaware.springrest.datajpa.EmployeeDTO;
import com.hexaware.springrest.datajpa.entity.Employee;

public interface IEmployeeService {

	public Employee addEmployee(EmployeeDTO dto);

	public Employee updateEmployee(EmployeeDTO dto);

	public EmployeeDTO getByEid(int eid);

	public void deleteByEid(int eid);

	public List<Employee> getAllEmployees();

	public Employee getByEname(String ename);

	public List<Employee> getBySalaryGreaterThan(double sal);
	
	public List<Employee>  getAllSortedBySal(double sal);
	
	public void    updateEmployeeSal(int eid, double salary);
	
	public  List<Employee>  getAllSalaryLT(double salary);
	
	public  List<Employee>  getAllSQL();

}

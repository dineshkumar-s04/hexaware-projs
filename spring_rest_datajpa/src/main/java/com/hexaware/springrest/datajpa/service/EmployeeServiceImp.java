package com.hexaware.springrest.datajpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.hexaware.springrest.datajpa.EmployeeDTO;
import com.hexaware.springrest.datajpa.entity.Employee;
import com.hexaware.springrest.datajpa.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class EmployeeServiceImp   implements  IEmployeeService{

	
		@Autowired
		EmployeeRepository repo;
	
	
	@Override
	public Employee addEmployee(EmployeeDTO dto) {

		Employee  emp = new Employee();
		
		emp.setEid(dto.getEid());
		emp.setEname(dto.getEname());
		emp.setSalary(dto.getSalary());
		
		 
		
		
		
		return repo.save(emp);
	}

	@Override
	public Employee updateEmployee(EmployeeDTO dto) {
		
		
		
		Employee  emp = new Employee();
		
		emp.setEid(dto.getEid());
		emp.setEname(dto.getEname());
		emp.setSalary(dto.getSalary());
		
		return repo.save(emp);
	}

	@Override
	public EmployeeDTO getByEid(int eid) {


		Employee emp = repo.findById(eid).orElse(null);
		
		
			EmployeeDTO dto = new EmployeeDTO();
					dto.setEid(emp.getEid());
					dto.setEname(emp.getEname());
					dto.setSalary(emp.getSalary());
					
					
			return dto;		
		
	}

	@Override
	public void deleteByEid(int eid) {

				repo.deleteById(eid);
		
	}

	@Override
	public List<Employee> getAllEmployees() {

		// repo.findAll(Sort.by(Direction.DESC, "ename"));
		
		return repo.findAll();
	}

	@Override
	public Employee getByEname(String ename) {
		
		return repo.findByEname(ename);
	}

	@Override
	public List<Employee> getBySalaryGreaterThan(double sal) {


		
		return  repo.findBySalaryGreaterThan(sal);
	}

	@Override
	public List<Employee> getAllSortedBySal(double sal) {
		
		return repo.getAllSortedBySal(sal);
	}

	@Override
	public void updateEmployeeSal(int eid, double salary) {

			
				repo.updateEmployeeSal(eid, salary);
		
		
	}

	@Override
	public List<Employee> getAllSalaryLT(double salary) {
		
		return repo.getAllSalaryLT(salary);
	}

	@Override
	public List<Employee> getAllSQL() {
		// TODO Auto-generated method stub
		return  repo.getAllSQL();
	}

}

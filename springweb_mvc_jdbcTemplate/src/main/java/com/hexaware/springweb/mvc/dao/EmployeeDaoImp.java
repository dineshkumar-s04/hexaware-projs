package com.hexaware.springweb.mvc.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hexaware.springweb.mvc.model.Employee;

@Repository
public class EmployeeDaoImp implements IEmployeeDao {


				@Autowired
				JdbcTemplate jdbcTemplate;
	
	@Override
	public int addEmp(Employee emp) {

			
				String    insertQuery = "insert into Emp_Table   values(?,?,?)";
				
			int count =		jdbcTemplate.update(insertQuery , emp.getEid(), emp.getEname() , emp.getSalary());
		
		
		return count;
	}

	@Override
	public List<Employee> getAll() {

				String  selectAll = "select * from Emp_Table";
				
				List<Employee>		 list  =jdbcTemplate.query(selectAll, new BeanPropertyRowMapper(Employee.class));
		
		
		return list;
	}

}

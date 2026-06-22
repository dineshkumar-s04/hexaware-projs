package com.hexaware.springrest.datajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hexaware.springrest.datajpa.entity.Employee;

import jakarta.persistence.NamedQuery;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	public Employee findByEname(String ename);

	public List<Employee> findBySalaryGreaterThan(double sal);
	
	
	// findByProperty1OrProperty2(p1,p2)

	// findByPropertyNameLike()
	// findByPropertyBetween(d1,d2);
	// findByPropertyIN()
	
	//deleteByEname(String ename);

	
				@Query("select  e from Employee e  where e.salary > ?1 order by e.salary  ")
				public List<Employee>  getAllSortedBySal(double sal);
	
			
			//	public  void   deleteByEname();
				
				@Modifying
				@Query("update  Employee  e   set e.salary = ?2 where e.eid = ?1")
				public void    updateEmployeeSal(int eid, double salary);
				
	
				
				public  List<Employee>  getAllSalaryLT(double salary);
				
				
				//@Query(value = "select * from Emp_Table" , nativeQuery =  true)
				
				
				@NativeQuery("select * from Emp_Table")
				public  List<Employee>  getAllSQL();
				
				
					
				
}

package com.hexaware.springrest.datajpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity
@Table(name="Emp_Table")
@NamedQueries(@NamedQuery(name =  "Employee.getAllSalaryLT" , query = "select  e from Employee e  where e.salary < ?1"))
public class Employee {
	
	
		@Id
		private int eid;
		private String ename;
		private double salary;
		
	
		
		public Employee() {
			
			
		}
		
		
		public Employee(int eid, String ename, double salary) {
			super();
			this.eid = eid;
			this.ename = ename;
			this.salary = salary;
		}


		public int getEid() {
			return eid;
		}


		public void setEid(int eid) {
			this.eid = eid;
		}


		public String getEname() {
			return ename;
		}


		public void setEname(String ename) {
			this.ename = ename;
		}


		public double getSalary() {
			return salary;
		}


		public void setSalary(double salary) {
			this.salary = salary;
		}


		@Override
		public String toString() {
			return "Employee [eid=" + eid + ", ename=" + ename + ", salary=" + salary + "]";
		}
		
		
		
	
	

}

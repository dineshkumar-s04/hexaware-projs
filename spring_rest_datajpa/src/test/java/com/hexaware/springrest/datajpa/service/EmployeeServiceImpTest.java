package com.hexaware.springrest.datajpa.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.springrest.datajpa.EmployeeDTO;
import com.hexaware.springrest.datajpa.entity.Employee;

@SpringBootTest
class EmployeeServiceImpTest {

	@Autowired
	IEmployeeService service;

	@Autowired
	EmployeeDTO empDTO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testAddEmployee() {

		empDTO.setEid(191);
		empDTO.setEname("Rahul Kumar");
		empDTO.setSalary(75000);

		Employee emp = service.addEmployee(empDTO);

		assertEquals(75000, emp.getSalary());

	}

	@Test
	void testGetByEid() {

		EmployeeDTO emp = null;
		try {
			emp = service.getByEid(101);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(emp);

		assertTrue(emp.getSalary() > 0);

	}

	@Test
	void testDeleteByEid() {

		service.deleteByEid(102);

	

		assertThrows(NullPointerException.class, ()->{  service.getByEid(102); });

	}

	@Test
	void testGetAllEmployees() {
		
		List<Employee> list =	service.getAllEmployees();
		
				assertTrue(list.size() > 0);
		
		
		

	}

}

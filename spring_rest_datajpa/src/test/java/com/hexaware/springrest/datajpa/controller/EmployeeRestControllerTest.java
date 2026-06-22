package com.hexaware.springrest.datajpa.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.hexaware.springrest.datajpa.EmployeeDTO;
import com.hexaware.springrest.datajpa.entity.Employee;
import com.hexaware.springrest.datajpa.service.IEmployeeService;

@SpringBootTest
class EmployeeRestControllerTest {
	
	
	
	@Autowired
	RestTemplate restTemplate;
	
	

	@Autowired
	IEmployeeService service;
	

	@Test
	
	void testCreateEmployee() {
		
		
		EmployeeDTO empDTO = new EmployeeDTO(192, "Steve", 55000);
			
	//Employee emp =	restTemplate.postForObject("http://localhost:8080/api/employees/add",empDTO , Employee.class);
					
			ResponseEntity<Employee>	response =	restTemplate.postForEntity("http://localhost:8080/api/employees/add",empDTO, Employee.class);
			
			
					
			
		Employee emp=		response.getBody();
	
			assertEquals(55000, emp.getSalary());
	
	}

	@Test
	@Disabled
	void testUpdateEmployee() throws Exception {


		EmployeeDTO empDTO = new EmployeeDTO(190, "Abul Kalam", 99000);
		
			restTemplate.put("http://localhost:8080/api/employees/update",empDTO);

		
		EmployeeDTO emp =		service.getByEid(190);
			
				assertEquals(99000, emp.getSalary());
		
		
	}

	@Test
	void testGetEmployeeByEid() {
		
		int eid = 101;
		
		
			EmployeeDTO dto =	restTemplate.getForObject("http://localhost:8080/api/employees/getbyeid/"+eid, EmployeeDTO.class);
		

				assertEquals("javeed khan", dto.getEname());

	}

	@Test
	void testDeleteByEid() {

		
			int eid = 103;
		
		restTemplate.delete("http://localhost:8080/api/employees/deletebyeid/"+eid);
		
			
		
			
				assertThrows(Exception.class, ()->{  restTemplate.getForObject("http://localhost:8080/api/employees/getbyeid/"+eid, EmployeeDTO.class); });

	}

}

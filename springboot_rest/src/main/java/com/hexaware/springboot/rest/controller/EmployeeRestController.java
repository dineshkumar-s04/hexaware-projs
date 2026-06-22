package com.hexaware.springboot.rest.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.springboot.rest.entity.Employee;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

	static List<Employee> list = new ArrayList<Employee>();

	static {

		list.add(new Employee(111, "tom", 8000));
		list.add(new Employee(112, "satish", 7000));
		list.add(new Employee(113, "jerry", 3000));
		list.add(new Employee(114, "ravi", 5000));

	}

	@GetMapping("/hello")
	public String hello() {

		return "Hello World";

	}

	@PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
	public Employee addEmployee(@RequestBody Employee emp) {

		list.add(emp);

		return emp;

	}

	@PutMapping(value = "/update", consumes = "application/json")
	public Employee updateEmployee(@RequestBody Employee emp) {

		list.add(emp);

		return emp;

	}

	@GetMapping("/getall")
	public List<Employee> getAll() {

		return list;

	}

	@GetMapping("/getbyid/{eid}")
	public List<Employee> getById(@PathVariable int eid) {

		return list.stream().filter((emp) -> {
			return emp.getEid() == eid;
		}).toList();

	}

	@DeleteMapping("/deletebyid/{eid}")
	public String deleteById(@PathVariable int eid) {

		Iterator<Employee> it = list.iterator();

		int i = 0;

		while (it.hasNext()) {

			Employee emp = it.next();

			if (emp.getEid() == eid) {

				list.remove(i);

				break;

			}

			i++;

		}
	
		return "record deleted...";
	
	}

}

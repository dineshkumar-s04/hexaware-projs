package com.hexaware.springweb.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hexaware.springweb.mvc.model.Employee;
import com.hexaware.springweb.mvc.service.IEmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	
		@Autowired
		IEmployeeService service;
			
	
		@RequestMapping("/hello")
		@ResponseBody
		public  String  hello(HttpServletRequest request , HttpServletResponse  response ,HttpSession session) {
			
		
				return "hello friends";
			
			
		}
	
	

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addEmp(@ModelAttribute  Employee emp , HttpSession session) {
		
		System.out.println("addEmp()   "+emp);
		
						session.setAttribute("emp", emp);
						
				int count =		service.addEmp(emp);
				
				System.out.println(count +" record effected");
		
				return  "success";   //  views/success.jsp
		
		

	}
	
	
		@RequestMapping("/getall")
		public String  getAllEmployees(HttpSession session) {
			
				List<Employee>  empList =	service.getAll();
			
					session.setAttribute("empList", empList);
					
				
				return  "displayAll";  // views/displayAll.jsp
			
			
		}
	
	
	
	
	

}

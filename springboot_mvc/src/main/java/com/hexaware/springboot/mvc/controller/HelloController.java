package com.hexaware.springboot.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hexaware.springboot.mvc.exception.EmployeeNotFoundException;
import com.hexaware.springboot.mvc.model.Employee;
import com.hexaware.springboot.mvc.service.IEmployeeService;

@Controller
@RequestMapping("/app")
public class HelloController {

    @Autowired
    private IEmployeeService service;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(@RequestParam String name) {

        return "Welcome to Spring Boot Controller " + name;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @ResponseBody
    public String add(@RequestParam String a,
                      @RequestParam String b) {

        int n1 = Integer.parseInt(a);
        int n2 = Integer.parseInt(b);

        return "Addition is : " + (n1 + n2);
    }

    @RequestMapping(value = "/addEmp", method = RequestMethod.POST)
    @ResponseBody
    public String addEmp(@RequestParam String eid,
                         @RequestParam String ename,
                         @RequestParam String salary) {

        Employee emp = new Employee();

        emp.setEid(Integer.parseInt(eid));
        emp.setEname(ename);
        emp.setSalary(Double.parseDouble(salary));

        int count = service.addEmployee(emp);

        if (count > 0) {
            return "Employee inserted successfully : " + emp;
        } else {
            return "Employee insertion failed";
        }
    }

    @RequestMapping("/all")
    @ResponseBody
    public List<Employee> getAllEmployees() {

        return service.getAllEmployees();
    }

    @RequestMapping("/find")
    @ResponseBody
    public Employee getEmployeeById(@RequestParam int eid)
            throws EmployeeNotFoundException {

        return service.getByEid(eid);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public String deleteEmployee(@RequestParam int eid) {

        int count = service.deleteEmployee(eid);

        return count > 0
                ? "Employee Deleted Successfully"
                : "Employee Not Found";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updateEmployee(@RequestParam int eid,
                                 @RequestParam String ename,
                                 @RequestParam double salary) {

        Employee emp = new Employee(eid, ename, salary);

        int count = service.updateEmployee(emp);

        return count > 0
                ? "Employee Updated Successfully"
                : "Employee Update Failed";
    }
}
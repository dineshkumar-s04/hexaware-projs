<%@ page import="java.util.List"%>
<%@ page import="com.hexaware.mvc.pojo.Employee"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee Details</title>
</head>
<body>

    <h1>UPDATE EMPLOYEES</h1>

    <form action="/MVC_Demo/UpdateController" method="post">

    EID:
    <input type="text" name="eid"><br><br>

    ENAME:
    <input type="text" name="ename"><br><br>

    SALARY:
    <input type="text" name="salary"><br><br>

    <button type="submit">Update Employee</button>

</form>

    <hr>

    <%
    List<Employee> list = (List<Employee>) session.getAttribute("empList");

    if (list != null && !list.isEmpty()) {
        for (Employee emp : list) {
    %>
            <p>
                ID: <%= emp.getEid() %>,
                Name: <%= emp.getEname() %>,
                Salary: <%= emp.getSalary() %>
            </p>
    <%
        }
    } else {
    %>
        <p>No employee records found.</p>
    <%
    }
    %>

</body>
</html>
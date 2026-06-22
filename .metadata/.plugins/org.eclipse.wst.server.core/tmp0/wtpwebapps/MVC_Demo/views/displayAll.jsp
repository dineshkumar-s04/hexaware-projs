<%@ page import="java.util.List,com.hexaware.mvc.pojo.Employee"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Display All Employees</title>
</head>
<body>

<h1>DISPLAY ALL EMPLOYEES</h1>

<form action="/MVC_Demo/DisplayController" method="get">
    <button type="submit">Display All</button>
</form>

<hr>

<%
List<Employee> list = (List<Employee>) session.getAttribute("empList");

if (list != null && !list.isEmpty()) {
    for (Employee emp : list) {
%>

<p>
    ID: <%= emp.getEid() %> |
    Name: <%= emp.getEname() %> |
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
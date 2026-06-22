<%@ page language="java"  import="com.hexaware.actions.beans.Employee"  contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<h1>Success JSP Page</h1>

<jsp:useBean id="emp"  class="com.hexaware.actions.beans.Employee"  scope="request"></jsp:useBean>

<jsp:getProperty property="eid" name="emp"/>

<jsp:getProperty property="ename" name="emp"/>

<jsp:getProperty property="salary" name="emp"/>


<br>
		JSP Scriptlet
		<br>
		
		<%
		
			Employee emp1 = (Employee)	request.getAttribute("emp");
				
			out.print(emp1);
		
		%>
		
		
		<br>
		
		JSP Exp:  <%=  request.getAttribute("emp") %>
		
		<br>
		
		JSP EL Exp:
		${ emp }
		
		


</body>
</html>
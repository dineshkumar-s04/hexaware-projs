<%@ page language="java"   import="com.hexaware.actions.beans.*"  isELIgnored="false"  contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
			<h1>Welcome to ADD EMPLOYEE JSP</h1>
	
		<jsp:useBean id="emp"  class="com.hexaware.actions.beans.Employee"  scope="request"></jsp:useBean>
		
		<%-- <jsp:setProperty property="eid" name="emp"  param="eid" />
		<jsp:setProperty property="ename" name="emp" param="ename"/>
		<jsp:setProperty property="salary" name="emp" param="salary"/> --%>
		
		
		<jsp:setProperty property="*" name="emp"/>
		
		<jsp:include page="/views/success.jsp" ></jsp:include>
		
		<%-- <jsp:forward page="/views/success.jsp"></jsp:forward> --%>
		
		
		
		
		
		
		
		
		
		
		
		
		
	

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Database Demo</title>
</head>
<body>
	<form action="MainServlet" method=get>
<p style="color:black"> Department:
<select name="department" id="department">
<option value="Marketing" selected> Marketing </option>
<option value="Finance" selected> Finance </option>
<option value="Human Resources" selected> Human Resources </option>
<option value="Production" selected> Production </option>
<option value="Development" selected> Development </option>
<option value="Quality Management" selected> Quality Management </option>
<option value="Sales" selected>Sales  </option>
<option value="Research" selected>Research </option>
<option value="Customer Service" selected> Customer Service </option>

 </select>
<input type="submit" name="action" style="height:50px">
</form>
</body>
</html>
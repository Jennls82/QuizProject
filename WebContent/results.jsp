<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- display results of quiz taken -->
	<h1>Your Results</h1>



	<form action="DisplayQuestions.do" method="post">
		<table border="1" class="left">
		<tr><th>Question</th><th>Answer</th><th>Response</th></tr>
			<c:forEach var="r" items="${results}">
				<tr>
				<td>${r[0]}</td>
				<td>${r[1]}</td>
				<td>${r[2]}</td>
				</tr>
				<br/>	
			</c:forEach>
		</table>	
	</form>

	<form action="quiz.do" method="post">
		<input type="submit" value="play again" />
	</form>
</body>
</html>
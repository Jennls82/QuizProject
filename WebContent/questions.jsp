<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<body>

	<h1 align="center">QUIZTIME</h1>

	<p>Choose your answer:</p>

	<form action="DisplayQuestions.do" method="post">
		<p>
			
			${question.value}
			<c:forEach var="a" items="${question.answers}">
				<br/><input type="radio" name="answers" value="${a.value}"/>${a.value}<br/>
			</c:forEach>
		</p>
		<input type = "submit"/> 
	
	</form>

${count}
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="quiz.css">
<link href='https://fonts.googleapis.com/css?family=Bangers' rel='stylesheet' type='text/css'>
<title>Insert title here</title>
</head>
<body>
<body>

	<h1>QUIZTIME</h1>

	<p>Choose your answer:</p>
<div>
	<form action="DisplayQuestions.do" method="post">
		<p>
			
			${question.text}
			<c:forEach var="a" items="${question.answers}"><br/>
				<br/><input type="radio" name="answers" value="${a.text}"/>${a.text}<br/>
			</c:forEach>
		</p>
		<input class="button" type = "submit"/> 
	</form>
</div>
<%-- ${count} debugger to track ? count --%>
</body>
</html>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="quiz.css">
<link href='https://fonts.googleapis.com/css?family=Bangers' rel='stylesheet' type='text/css'>
<title>Welcome!</title>
</meta>
</head>

<body>
	<h1>Welcome Back!</h1>
		<!--Post an msg for the user based on login  -->
		
		
  <!-- 	<form action="DisplayName.do">
		<input type="submit" value="Show me the name of the quiz" />
	</form> -->
	<!-- <form action="DisplayNumber.do">
		<input type="submit" value="Show me the number of questions" />
	</form>  --> 
	<div id="inContent">
			<h2>Choose a Quiz</h2>
		<form action="quizstart1.jsp">
			<input id="HF" class="button" type="submit" name="quiz" value="Horror Film Quiz"/>
		</form>
		
		 <form action="quizstart1.jsp">
			<input id="CQ" class="button" type="submit" name="quiz" value="Creature Quiz"/>
		</form> 
		
		
	</div>
</body>
</html>
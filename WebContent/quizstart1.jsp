<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="quiz.css">
<link href='https://fonts.googleapis.com/css?family=Bangers'
	rel='stylesheet' type='text/css'>
<title>Quiz Program</title>
<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script>
	$(document).ready(
					function() {//page load
						function getRequestParam(name){//function to display the correct quiz name for each quiz
							   if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
							      return decodeURIComponent(name[1]);
							}//this could be replaced by a simple JQuery statement ************************************
						var quizname = getRequestParam("quiz");
						var quizId = 1;
						
						if(quizname==="Horror+Film+Quiz"){//check the url to see which quiz was picked
						//	alert("what quiz am i"); debugger 
							quizId=1;}
						else{ quizId=2;
									}
						

						var questionIds = [];
						var quiz = null;

						(function initQuiz() {
							$.ajax(
											{//call to the entity to instaniate a new quiz object
												url : "/SDQuizProject/rest/quiz/" // url path mapping for request
														+ quizId, //requested data
												method : "get", // request method type
												datatype : "json",//call expects back a json object
												success : function(quizObj) {//if call is sucessful run a fuction to
													quiz = quizObj;// declare a quiz object...
													questionIds = quiz.questionIds;//declare the questions assciated with the quiz...
													$("#title").html(quizObj.name);//display the name associated with the quiz in the html...
													loadQuestion(quizId, questionIds[0]);//load the questions assciated with the quiz, function defined on line 53
												},
											}).fail(function(data) {//if the call fails log the event
												console.log("failure of ajax/json during initQuiz");
											});
						})();

						function loadQuestion(quizId, quesId) {
							$.ajax(
									{//call to question data
										url : "/SDQuizProject/rest/quiz/"
												+ quizId + "/question/"
												+ quesId,
										method : "get",
										datatype : "json",
										success : displayQuestion,//function defined on line 64
									}).fail(function(data) {
								console.log("failure of ajax/json");
							});
							
							function displayQuestion(question) {
								if (question) {//what to do if there is a next question 
									console.log(question);//log of returned data
									$("#questionText").html(question.text)//display the question to html...
									$("#answers").html("");//display the answers
									for ( var i in question.answers) {//loop through the data 
										var answerText = question.answers[i].text;//declare the text of the answer...
										var answerId = question.answers[i].id;//assign an id to each answer...
										var answerItem = document.createElement("li");//create an ol in the html and display the answers in it...
										var answerHTML = "<input type='radio' name='answers' value='" + answerId + "'/>"//create radio buttons 
												+ answerText;
										//console.log(answerHTML) debugger
										answerItem.innerHTML = answerHTML;//assign the radio buttons to answers
										$("#answers").append(answerItem);//and add it to the doc
									}
								} else {//if there are no more questions
									console.log("displayQuestions(): no 'question'")
									displayResults(quizId);//display the results, function defined on line 132
								}
							}
						}

						$('#submit').click(function() {//method to track user answers
											var answerId = $("input[name=answers]:checked","#qform").val();//assign an id to user selected answer
											console.log(answerId);
											if (questionIds.length > 0) {//if there are questions...
												console.log("button click: questionIds.length is "+questionIds.length)
												$.ajax(
														{//make a call to update the object
															url : "/SDQuizProject/rest/quiz/"
																			+ quizId
																			+ "/question/"
																			+ questionIds[0]
																			+ "/answer/"
																			+ answerId,
																	method : "put",//method to add to info to object
																	datatype : "json",
																	success : processAnswer,//function defined on line 112
																}).fail(function(data) {
															alert("Please select an answer.");//if no answer selected, annoy the user
																	console.log("fail in button click");
																}); //fail
											} else {
												// Do we ever get here?  Nope.**************************
												console.log("button click: questionIds.length is 0.")
												displayResults(quizId); //if there are no more questions to answer, display the results
											}
											function processAnswer(json) {//add the user answers to the quiz object
												console.log("processAnswer: typeof json is "+typeof json)
												console.log(json)
												if (json == null) {//if there is an object
													// we never get here.************
													// Possible TODO: Have server return next question, or null if no more questions?**************
													console.log("processAnswer(): Ajax returned null")
													displayResults(quizId);//display the quiz
												} else {  // Proceed or not?
													questionIds.shift();  // Remove first questionId (the one just submitted) from list
													if (questionIds.length > 0) {  //Any more questions?
														console.log("processAnswer(): new current question: "+questionIds[0])
														loadQuestion(quizId, questionIds[0]);
													}  else { //Nope, no more questions, lets see results
														console.log("processAnswer(): questionIds is empty.")
														displayResults(quizId)
													}
												}
											}
										});

						function displayResults(quizId) {
							$.ajax(
									{//call to display the updated quiz object 
												url : "/SDQuizProject/rest/quiz/"
														+ quizId + "/results",
												method : "get",
												datatype : "json",
												success : function(questionList) {
													console.log(questionList);
													//	 Show results.
													 //Create Table and Heading Rows 
													var newTable = document.createElement('table');
													newTable.className = "jsTable";
													for (var r = 0; r < questionList.length; r++) {
														//create new row
														var row = newTable.insertRow(r);
														var qcell = row.insertCell(0);
														var ccell = row.insertCell(1);
														var gcell = row.insertCell(2);
														qcell.innerHTML = questionList[r].text;
														ccell.innerHTML = questionList[r].correctAnswer.text;
														gcell.innerHTML = questionList[r].givenAnswer.text;	
													}
													var tHeadings = ["Question", "Correct Answer", "Your Answer"];
													var tableHeadingRow = newTable.insertRow(0);
													for (var i=0;i<3;i++) {
													//	var h = tableHeadingRow.insertCell(i);
													var h = document.createElement("th");	
													h.innerHTML = tHeadings[i];
														h.className = 'jsTableRowHead'
														tableHeadingRow.appendChild(h);
													}

													// Add classes to elements
													//Append table to DOM
													var mainContent = document.getElementById("mainContent");
													mainContent.innerHTML=""; // reset the HTML to display results
													mainContent.appendChild(newTable);
													$(".jsTable tr:first").addClass("jsTableHead");
													$(".jsTable th").addClass("jsTableRowHead");	
													
													//add replay button
													var indexButton = document.createElement("input");
													indexButton.id = "indexButton";
													indexButton.className = "button";
													indexButton.type = "button";													
													indexButton.value = "take another quiz";
													//newButton = document.getElementById("button");
													mainContent.appendChild(indexButton);
													// "old" way:
													//	indexButton.onClick = "window.location.href='index.jsp'";
													// Proper way to add listeners:
													//indexButton.addEventListener("click", function(evt){
													//  window.location.href = 'index.jsp';
													//})
													// jQuery way:
													$('#indexButton').click(function(evt){
														console.log("FML");
														window.location.href = 'ResetQuiz.do';
  													   //$('#indexButton').attr('action', "ResetQuiz.do");
													});
													/* $("#indexButton").click(function(evt){
													  window.location.href = 'index.jsp';
													}); */
														
													
												}, //success
											}) //ajax
									.fail(
											function(data) {
												console.log("failure of ajax/json during displayResults");
											});
						} //displayResults

					}); //document.ready
</script>

</head>

<body>

<div id="mainContent">
	<h1 id="title">HORROR FILM QUIZ</h1>
	<div id="resultContent">
		<h2 id="quizName"></h2>
		<p id="p1">Choose your answer:</p>
	</div>
	<div id="questionAjax">
		<div id="questionText"></div>
		<form id="qform">
			<ol id="answers">
			</ol>
			<input id="submit" class="button" type="button" value="answer" />
		</form>
	</div>
</div>


	<%-- <div>
	<form action="DisplayQuestions.do" method="post">
		<p>
			
			${question.text}
			<c:forEach var="a" items="${question.answers}"><br/>
				<br/><input type="radio" name="answers" value="${a.text}"/>${a.text}<br/>
			</c:forEach>
		</p>
		<input class="button" type="submit"/> 
	</form>
</div> --%>
	<%-- ${count} debugger to track ? count --%>
</body>
</html>
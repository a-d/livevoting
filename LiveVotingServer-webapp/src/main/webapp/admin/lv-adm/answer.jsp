<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LiveVoting - Answers</title>
<s:head />
<link type="text/css" href="/LiveVoting/style.css" rel="stylesheet" />
</head>
<body>

<s:push value="answer">
	<s:url id="allCourses" namespace="/adm/course" action="list" />
	<s:url id="editCourse" namespace="/adm/course" action="edit">
		<s:param name="id" value="%{getParameterQuestion().getCourse().id}" />
	</s:url>
	<s:url id="allQuestions" namespace="/adm/question" action="list">
		<s:param name="cId" value="%{getParameterQuestion().getCourse().id}" />
	</s:url>
	<s:url id="editQuestion" namespace="/adm/question" action="edit">
		<s:param name="id" value="%{getParameterQuestion().id}" />
	</s:url>
	<s:url id="allAnswers" namespace="/adm/answer" action="list">
		<s:param name="qId" value="%{getParameterQuestion().id}" />
	</s:url>
	<s:url id="logout" namespace="/" action="logout" />
	
	<div class="navi">
		<span style="float:right"><s:a href="%{logout}">Logout</s:a></span>
		
		<s:a href="%{allCourses}">All Courses</s:a>
		&gt;
		<s:a href="%{editCourse}"><i>
			<s:property value="getParameterQuestion().getCourse().name" />
		</i></s:a>
		&gt;
		<s:a href="%{allQuestions}">Questions</s:a>
		&gt;
		<s:a href="%{editQuestion}"><i>
			<s:property value="getParameterQuestion().text" />
		</i></s:a>
		&gt;
		<s:a href="%{allAnswers}">Answers</s:a>
		
	</div>
</s:push>

<hr />

<s:form action="save" validate="true">
	<s:hidden name="qId" value="%{getParameterQuestion().id}" />

	<s:push value="answer">
		<s:hidden name="id" />
		<s:label value="%{getParameterQuestion().text}" label="Question" />
		<s:label value="%{getParameterQuestion().multipleChoice}" label="Multiple Choice" />
		<s:label value="%{getParameterQuestion().customAnswer}" label="Custom Answer" />
		
		<s:textfield name="title" label="Title" required="true" />
		<s:textarea name="text" rows="3" cols="25" label="Text" required="true" />
		<s:textarea name="picture" rows="3" cols="25" label="Picture" />
		
		<s:submit />
	</s:push>
</s:form>

<s:if test="list.size() > 0">
	<div class="content">
	<table class="answerTable" cellpadding="5px">
		<tr class="even">
			<th>Title</th>
			<th>Text</th>
			<th>Picture</th>
			<th>#Votes</th>
		</tr>
		<s:iterator value="list" status="answerStatus">
			<tr class="<s:if test="#answerStatus.odd == true ">odd</s:if><s:else>even</s:else>">
				<td><s:property value="title" /></td>
				<td><s:property value="text" /></td>
				<td>
					<s:url id="pic" namespace="/download" action="png"><s:param name="aId" value="%{id}" /></s:url>
					<a href="<s:property value="pic" />">
						<img class="ansPic" src="<s:property value="pic" />" width="100" border="0" />
					</a>
				</td>
				<td><s:property value="votes.size" /></td>
				<td><s:url id="editURL" action="edit">
					<s:param name="id" value="%{id}" />
				</s:url> <s:a href="%{editURL}">Edit</s:a></td>
				<td><s:url id="deleteURL" action="delete">
					<s:param name="id" value="%{id}"></s:param>
				</s:url> <s:a href="%{deleteURL}">Delete</s:a></td>
			</tr>
		</s:iterator>
	</table>
	</div>
</s:if>
</body>
</html>
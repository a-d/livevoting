<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
%><!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LiveVoting - Questions</title>
<s:head />
<link type="text/css" href="/LiveVoting/style.css" rel="stylesheet" />
</head>
<body>

<s:push value="question">
	<s:url id="allCourses" namespace="/adm/course" action="list" />
	<s:url id="editCourse" namespace="/adm/course" action="edit">
		<s:param name="id" value="%{getParameterCourse().id}" />
	</s:url>
	<s:url id="allQuestions" namespace="/adm/question" action="list">
		<s:param name="cId" value="%{getParameterCourse().id}" />
	</s:url>
	
	<s:url id="logout" namespace="/" action="logout" />
	
	<div class="navi">
		<span style="float:right"><s:a href="%{logout}">Logout</s:a></span>
		
		<s:a href="%{allCourses}">All Courses</s:a>
		&gt;
		<s:a href="%{editCourse}"><i>
			<s:property value="getParameterCourse().name" />
		</i></s:a>
		&gt;
		<s:a href="%{allQuestions}">Questions</s:a>
	</div>
</s:push>

<hr />

<s:form action="save" validate="true">
	<s:hidden name="cId" value="%{getParameterCourse().id}" />

	<s:push value="question">
		<s:hidden name="id" />
		
		<s:label label="Course" value="%{getParameterCourse().name}" />
		
		<s:textarea name="text" rows="3" cols="25" label="Text" required="true" />
		<s:textarea name="picture" rows="3" cols="25" label="Picture" />
		
		<s:checkbox name="multipleChoice" label="Allow Multiple Choice" />
		<s:checkbox name="customAnswer" label="Allow Custom Answers" />

		<s:label label="Date Format" value="%{getText('format.date.plain')}" />
		<s:textfield name="dateFrom" label="Date From" maxlength="10" required="true">
			<jsp:attribute name="value"><s:date name="dateFrom" format="dd.MM.yyyy" /></jsp:attribute>
		</s:textfield>
		<s:textfield name="dateTo" label="Date To" maxlength="10" required="true">
			<jsp:attribute name="value"><s:date name="dateTo" format="dd.MM.yyyy" /></jsp:attribute>
		</s:textfield>
			
		<s:submit />
		
	</s:push>
</s:form>

<s:if test="list.size() > 0">
	<div class="content">
	<table class="questionTable" cellpadding="5px">
		<tr class="even">
			<th>Text</th>
			<th>Picture</th>
			<th colspan="3">Date</th>
		</tr>
		<s:iterator value="list" status="questionStatus">
			<tr class="<s:if test="#questionStatus.odd == true ">odd</s:if><s:else>even</s:else>">
				<td><s:property value="text" /></td>
				<td>
					<s:if test="picture!=null && picture!=\"\"">
						<s:url id="pic" namespace="/download" action="png"><s:param name="qId" value="%{id}" /></s:url>
						<a href="<s:property value="pic" />">
							<img class="ansPic" src="<s:property value="pic" />" width="100" border="0" />
						</a>
					</s:if>
				</td>
				<td><s:date name="dateFrom" format="dd.MM.yyyy" /></td>
				<td>-</td>
				<td><s:date name="dateTo" format="dd.MM.yyyy" /></td>
				<td>
					<s:url id="editURL" action="edit">
						<s:param name="id" value="%{id}" />
					</s:url>
					<s:a href="%{editURL}">Edit</s:a>
				</td>
				<td>
					<s:url id="deleteURL" action="delete">
						<s:param name="id" value="%{id}" />
					</s:url>
					<s:a href="%{deleteURL}">Delete</s:a></td>
				<td>
					<s:url id="answersURL" namespace="/adm/answer" action="list">
						<s:param name="qId" value="%{id}" />
					</s:url>
					<s:a href="%{answersURL}">Answers</s:a>
				</td>
				<td>
					<s:url id="participateURL" namespace="/adm/participate" action="show"><s:param name="qId" value="%{id}"></s:param></s:url>
					<s:a href="%{participateURL}"><i>Participate</i></s:a>
				</td>
				<td>
					<s:url id="resultURL" namespace="/adm/participate" action="result"><s:param name="qId" value="%{id}"></s:param></s:url>
					<s:a href="%{resultURL}"><i>Result</i></s:a>
				</td>
			</tr>
		</s:iterator>
	</table>
	</div>
</s:if>
</body>
</html>
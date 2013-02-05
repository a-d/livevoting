<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LiveVoting - Courses</title>
<s:head />
<link type="text/css" href="/LiveVoting/style.css" rel="stylesheet" />
</head>
<body>

<s:url id="allCourses" namespace="/adm/course" action="list" />
<s:url id="allTeachers" namespace="/adm/teacher" action="list" />
<s:url id="allUsers" namespace="/adm/user" action="list" />

<s:url id="logout" namespace="/" action="logout" />
	
	<div class="navi">
	<span style="float:right"><s:a href="%{logout}">Logout</s:a></span>
	<s:a href="%{allCourses}">All Courses</s:a>
	|
	<s:a href="%{allTeachers}">All Teachers</s:a>
	|
	<s:a href="%{allUsers}">All Users</s:a>
</div>

<hr />


<s:form action="save">
	<s:push value="course">
		<s:hidden name="id" />
		<s:select name="teacherId" label="Teacher" required="true" list="teacherList" listValue="name" listKey="id"/>
		<s:textfield key="name" name="name" label="Course Name" required="true" />
		<s:label name="dateFormatString" label="Date Format" />
		<s:textfield name="dateFrom" label="Date From" required="true">
			<jsp:attribute name="value"><s:date name="dateFrom" format="dd.MM.yyyy" /></jsp:attribute>
		</s:textfield>
		<s:textfield name="dateTo" label="Date To" required="true">
			<jsp:attribute name="value"><s:date name="dateTo" format="dd.MM.yyyy" /></jsp:attribute>
		</s:textfield>
		<s:submit />
	</s:push>
</s:form>


<s:if test="list!=null && list.size() > 0">
	<div class="content">
	<table class="courseTable" cellpadding="5px">
		<tr class="even">
			<th>Name</th>
			<th>Teacher</th>
			<th colspan="3">Date</th>
			<th colspan="4"> </th>
		</tr>
		<s:iterator value="list" status="courseStatus">
			<tr class="<s:if test="#courseStatus.odd == true ">odd</s:if><s:else>even</s:else>">
				<td><s:property value="name" /></td>
				<td><s:property value="teacher.name" /></td>
				<td><s:date name="dateFrom" format="dd.MM.yyyy" /></td>
				<td>-</td>
				<td><s:date name="dateTo" format="dd.MM.yyyy" /></td>
				<td><s:url id="editURL" action="edit">
					<s:param name="id" value="%{id}" />
				</s:url> <s:a href="%{editURL}">Edit</s:a></td>
				<td><s:url id="deleteURL" action="delete">
					<s:param name="id" value="%{id}" />
				</s:url> <s:a href="%{deleteURL}">Delete</s:a></td>
				<td><s:url id="questionURL" namespace="/adm/question" action="list">
					<s:param name="cId" value="%{id}" />
				</s:url> <s:a href="%{questionURL}">Questions</s:a></td>
				<td>|
					<s:url id="clientProperties" namespace="/download" action="properties">
						<s:param name="cId" value="%{id}" />
					</s:url>
					<s:a href="%{clientProperties}">Download Configuration</s:a>
				</td>
			</tr>
		</s:iterator>
	</table>
	</div>
</s:if>
</body>
</html>
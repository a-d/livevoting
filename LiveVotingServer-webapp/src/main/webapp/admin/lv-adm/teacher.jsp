<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LiveVoting - Teachers</title>
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
	<s:push value="teacher">
		<s:hidden name="id" />
		<s:textfield name="name" label="Teacher Name" />
		<s:textfield name="hashedPassword" label="Password" value="" />
		<s:submit />
	</s:push>
</s:form>

<s:if test="list.size() > 0">
	<div class="content">
	<table class="teacherTable" cellpadding="5px">
		<tr class="even">
			<th>Name</th>
			<th>Password</th>
		</tr>
		<s:iterator value="list" status="teacherStatus">
			<tr
				class="<s:if test="#teacherStatus.odd == true ">odd</s:if><s:else>even</s:else>">
				<td><s:property value="name" /></td>
				<td><s:property value="hashedPassword" /></td>
				<td><s:url id="editURL" action="edit">
					<s:param name="id" value="%{id}"></s:param>
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
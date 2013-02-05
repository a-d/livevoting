<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LiveVoting - Users</title>
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

<s:if test="list.size() > 0">
	<div class="content">
	<table class="teacherTable" cellpadding="5px">
		<tr class="even">
			<th>Id</th>
			<th>Ip</th>
			<th>Browser-Hash</th>
			<th>Browser-Agent</th>
			<th>Date</th>
		</tr>
		<s:iterator value="list" status="userStatus">
			<tr
				class="<s:if test="#userStatus.odd == true ">odd</s:if><s:else>even</s:else>">
				<td><s:property value="id" /></td>
				<td><s:property value="ip" /></td>
				<td><s:property value="browserHash" /></td>
				<td><s:property value="browserAgent" /></td>
				<td><s:property value="date" /></td>
			</tr>
		</s:iterator>
	</table>
	</div>
</s:if>
</body>
</html>
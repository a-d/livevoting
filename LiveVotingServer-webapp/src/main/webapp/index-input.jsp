<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Active Courses</title>
<s:head />
<style type="text/css">
@import url(style.css);
</style>
</head>
<body>

<s:if test="courseList.size() > 0">
	<div class="content">
	<table class="courseTable" cellpadding="5px">
		<tr class="even">
			<th>Name</th>
			<th>Teacher</th>
		</tr>
		<s:iterator value="courseList" status="courseStatus">
			<tr class="<s:if test="#courseStatus.odd == true ">odd</s:if><s:else>even</s:else>">
				<td><s:property value="name" /></td>
				<td><s:property value="teacher.name" /></td>
				<td>
				<jsp:element name="a">
					<jsp:attribute name="href">course_<s:property value="id" /></jsp:attribute>
					<jsp:body>Enter</jsp:body>
				</jsp:element>
				</td>
			</tr>
		</s:iterator>
	</table>
	</div>
</s:if>
</body>
</html>
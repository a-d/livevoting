<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:element name="meta">
	<jsp:attribute name="http-equiv">refresh</jsp:attribute>
	<jsp:attribute name="content">0;URL='course_<s:property value="model.course.id" />'</jsp:attribute>
</jsp:element>
<title>Course: <s:property value="model.course.name"/></title>
<s:head />
<style type="text/css">
@import url(style.css);
</style>
</head>
<body>

<h1>LiveVoting: <s:property value="model.course.name" /></h1>
<h2>Success</h2>

<p>
	Vote for Question has been saved.
</p>
<p>
	<jsp:element name="a">
		<jsp:attribute name="href">course_<s:property value="model.course.id" /></jsp:attribute>
		<jsp:body>Back</jsp:body>
	</jsp:element>
</p>
</body>
</html>
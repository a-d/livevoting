<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="/struts-tags" prefix="s" %><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
 
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:jsp="http://java.sun.com/JSP/Page" xml:lang="en" lang="de">

<head>
	<title><s:text name="global.document.title" /></title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<s:head />
	<style type="text/css">
	@import url(style.css);
	</style>
</head>
<body>

<s:if test="hasActionErrors()">
 <div class="errors"><s:actionerror /></div>
</s:if>
<s:if test="hasActionMessages()">
 <div class="welcome"><s:actionmessage /></div>
</s:if>



<s:form action="login" namespace="/admin">
 <s:hidden name="url" value="%{ url!=null ? url : #request['javax.servlet.forward.servlet_path'] + (#request['javax.servlet.forward.query_string']!=null ? '?' + #request['javax.servlet.forward.query_string'] : '') }" />
 <s:if test="availableLogins.size > 0">
 	<s:select label="Name" name="name" list="availableLogins" value="model.name" />
 </s:if>
 <s:else>
 	<s:textfield label="Name" name="name" />
 </s:else>
 <s:password label="Password" name="password"/>
 <s:submit label="Login" name="submit" />
</s:form>

</body>
</html>
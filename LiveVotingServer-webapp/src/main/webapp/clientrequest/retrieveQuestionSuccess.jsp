<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
%>
<%@taglib uri="/struts-tags" prefix="s"%>
questionId=<s:property value="question.id"/>

<s:iterator value="question.answers">
<s:property value="title" />=<s:property value="votes.size" />
</s:iterator>
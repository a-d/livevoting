<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
%>
<%@taglib uri="/struts-tags" prefix="s"%>
questionId=<s:property value="question.id"/>
courseId=<s:property value="question.course.id" />
numAnswers=<s:property value="question.answers.size" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LiveVoting - Results</title>
<s:head />
<link type="text/css" href="/LiveVoting/style.css" rel="stylesheet" />
</head>
<body>

<s:push value="parameterQuestion">
	<s:url id="allCourses" namespace="/adm/course" action="list" />
	<s:url id="editCourse" namespace="/adm/course" action="edit">
		<s:param name="id" value="course.id"></s:param>
	</s:url>
	<s:url id="allQuestions" namespace="/adm/question" action="list">
		<s:param name="cId" value="course.id"></s:param>
	</s:url>
	<s:url id="editQuestion" namespace="/adm/question" action="edit">
		<s:param name="id" value="id"></s:param>
	</s:url>
	<s:url id="participateQuestions" namespace="/adm/participate" action="show">
		<s:param name="qId" value="id"></s:param>
	</s:url>
	
	<s:url id="logout" namespace="/" action="logout" />
	
	<div class="navi">
		<span style="float:right"><s:a href="%{logout}">Logout</s:a></span>
		
		<s:a href="%{allCourses}">All Courses</s:a>
		&gt;
		<s:a href="%{editCourse}"><i>
			<s:property value="course.name" />
		</i></s:a>
		&gt;
		<s:a href="%{allQuestions}">Questions</s:a>
		&gt;
		<s:a href="%{editQuestion}"><i>
			<s:property value="text" />
		</i></s:a>
		&gt;
		<s:a href="%{participateQuestions}"><i>Participate</i></s:a>
	</div>
</s:push>

<hr />

<s:if test="#session['question.answered.'+parameterQuestion.id] != null">
	<h3>You have answered the questionnaire.</h3>
</s:if>

<div class="content">
	<ul>
	<s:push value="parameterQuestion">
		<li><s:label name="course.name" label="Course" /></li>
		<s:if test="text != ''">
			<li><s:label name="text" label="Text" /></li>
		</s:if>
		<li>
			<s:label name="dateFrom" label="From">
				<jsp:attribute name="value"><s:date name="dateFrom" format="dd.MM.yyyy" /></jsp:attribute>
			</s:label>
		</li>
		<li>
			<s:label name="dateTo" label="To">
				<jsp:attribute name="value"><s:date name="dateTo" format="dd.MM.yyyy" /></jsp:attribute>
			</s:label>
		</li>
	</s:push>

	<li><s:label name="numVotes" label="Number of Votes" /></li>
	<s:if test="picture != ''"><li><s:label label="Picture" /></li></s:if>
	</ul>

<div style="margin:10px; margin-left:30px">
	<s:property value="parameterQuestion.picture" escape="false" />
</div>

	<table class="courseTable" cellpadding="5px">
		<tr class="even">
			<th>Id</th>
			<th>Title</th>
			<th>Text</th>
			<th>Picture</th>
			<th>#Votes</th>
		</tr>
		<s:iterator value="parameterQuestion.answers" status="answerStatus">
			<tr class="<s:if test="#answerStatus.odd == true ">odd</s:if><s:else>even</s:else>">
				<td><s:property value="id" /></td>
				<td><s:property value="title" /></td>
				<s:if test="user == null">
					<td><s:property value="text" /></td>
					<td>
					<s:url id="pic" namespace="/download" action="png"><s:param name="aId" value="%{id}" /></s:url>
					<a href="<s:property value="pic" />">	
						<img class="ansPic" src="<s:property value="pic" />" width="100" border="0" />
					</a>
					</td>
				</s:if>
				<s:else>
					<td colspan="2" align="center"><i>user-created</i></td>
				</s:else>
				<td><s:property value="votes.size" /></td>
			</tr>
		</s:iterator>
	</table>
</div>

</body>
</html>
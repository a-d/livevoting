<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LiveVoting - Participate</title>
<s:head />
<link type="text/css" href="/LiveVoting/style.css" rel="stylesheet" />
</head>
<body>

<s:push value="parameterQuestion">
	<s:url id="allCourses" namespace="/adm/course" action="list" />
	<s:url id="editCourse" namespace="/adm/course" action="edit">
		<s:param name="id" value="%{course.id}"></s:param>
	</s:url>
	<s:url id="allQuestions" namespace="/adm/question" action="list">
		<s:param name="cId" value="%{course.id}"></s:param>
	</s:url>
	<s:url id="editQuestion" namespace="/adm/question" action="edit">
		<s:param name="id" value="%{id}"></s:param>
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
	</div>
</s:push>

<hr />


<s:form disabled="true">
	<s:push value="parameterQuestion">
		<s:label name="course.name" label="Course" />
		<s:if test="text != null && text != \"\"">
			<s:label name="text" label="Text" />
		</s:if>
		
		<s:label name="dateFrom" label="From" value="%{dateFrom!=null ? getText('format.date', '', {dateFrom}) : ''}" />
		<s:label name="dateTo" label="To" value="%{dateTo!=null ? getText('format.date', '', {dateTo}) : ''}" />
		
		<s:if test="picture != null && picture != \"\"">
			<s:label label="Picture" />
		</s:if>
	</s:push>
</s:form>


<s:if test="parameterQuestion.picture!=null && parameterQuestion.picture!=\"\"">
	<div style="margin:10px; margin-left:30px">
		<s:url id="pic" namespace="/download" action="svg"><s:param name="qId" value="%{parameterQuestion.id}" /></s:url>
		<object data="<s:property value="pic" />" type="image/svg+xml">
			<embed src="<s:property value="pic" />" type="image/svg+xml" />
		</object>
	</div>
</s:if>

<s:form action="save" validate="true">
	<s:hidden name="qId" value="%{parameterQuestion.id}"/>
	
	<s:if test="answerList.size() >  0 || parameterQuestion.customAnswer == true">
		<div class="content">
		
			<s:if test="answerList.size() >  0">
				<s:iterator value="answerList">
					<div style="margin:10px 20px">
						<input
							id="givenAnswer-<s:property value="id" />"
							<s:if test="parameterQuestion.multipleChoice == true ">
							type="checkbox"
							</s:if>
							<s:else>
							type="radio"
							</s:else>
							name="givenAnswer"
							value="<s:property value="id" />"
						/>
						<label class="checkboxLabel" for="givenAnswer-<s:property value="id" />">
						
							<s:url id="pic" namespace="/download" action="png"><s:param name="aId" value="%{id}" /></s:url>
							<a href="<s:property value="pic" />">	
								<img class="ansPic" src="<s:property value="pic" />" width="100" border="0" />
							</a>
						</label>
					</div>
				</s:iterator>
			</s:if>
				
			<s:if test="parameterQuestion.customAnswer == true ">
				<s:textfield name="customAnswer" label="Custom Answer" />
			</s:if>
			
			<s:submit />
		</div>
	</s:if>
</s:form>

</body>
</html>
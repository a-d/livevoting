<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/struts-tags" prefix="s"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Course: <s:property value="model.name"/></title>
<s:head />
<style type="text/css">
@import url(style.css);
</style>
</head>
<body>

<h1>LiveVoting: <s:property value="model.name" /></h1>

<s:if test="questionList.size() > 0">
	<div class="content">
		<s:iterator value="questionList" status="questionStatus" var="question">
			<div style="margin:10px">

				<p class="qMeta">
					<s:if test="picture==null || picture==\"\"">
						<b>
							<s:property value="text" />
						</b>
						<s:if test="dateFrom!=null && dateTo!=null">
							<br />
							<i> 
								From: <s:date name="dateTo" format="dd.MM.yyyy" />
								To: <s:date name="dateTo" format="dd.MM.yyyy" />
							</i>
						</s:if>
			 		</s:if>
				</p>
				<s:if test="picture!=null && picture!=\"\"">
					<div class="qPic">
					<s:url id="pic" namespace="/download" action="png"><s:param name="questionId" value="%{id}" /></s:url>
					<img class="svgPic" src="<s:property value="pic" />" width="<s:property value="%{getPictureWidth()}" />" height="<s:property value="%{getPictureHeight()}" />" />
					</div>
				</s:if>
				<s:if test="#session['question.answered.'+id] != null">
				You have answered this question.</s:if>
				<s:elseif test="isOld()">The question has expired.</s:elseif>
				<s:else>
				<p class="qAns">
					<s:form action="question_%{id}" validate="true">
						<s:hidden name="qId" value="%{id}"/>
						
						<s:if test="answers.size() >  0 || customAnswer == true">
								<s:if test="nonCustomAnswers.size() >  0">
									<s:iterator value="nonCustomAnswers">
										<s:if test="user == null">
										<div class="aPic">
											<input
												id="givenAnswer-<s:property value="id" />"
												<s:if test="question.multipleChoice == true ">
												type="checkbox"
												</s:if>
												<s:else>
												type="radio"
												</s:else>
												name="givenAnswer"
												value="<s:property value="id" />"
												style="position:relative; top:10px"
											/>
											
											<label
											 for="givenAnswer-<s:property value="id" />">
												<span class="svgPic" style="width:<s:property value="maxAnswerPictureWidth" />px">
													<s:url id="pic" namespace="/download" action="png"><s:param name="answerId" value="%{id}" /></s:url>
													<img class="ansPic" src="<s:property value="pic" />" height="<s:property value="pictureHeight"/>" />
												</span>
											 </label>

										</div>
										</s:if>
									</s:iterator>
								</s:if>
									
								<s:if test="customAnswer == true ">
									<s:textfield name="customAnswer" label="Custom Answer" value="" />
								</s:if>
								
								<s:submit />
						</s:if>
					</s:form>
				</p>
				</s:else>
			</div>
		</s:iterator>
	</div>
</s:if>
<s:else>
	No Questions available.
</s:else>
</body>
</html>
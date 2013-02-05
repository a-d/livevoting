<%@ taglib prefix="s" uri="/struts-tags"%>
<s:select
	name="answerChoice"
	list="availableAnswers"
	listKey="%{id}"
	listValue="%{title} - %{text}"
	theme="simple"
	value="defaultAnswerId"/>
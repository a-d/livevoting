<%@ taglib prefix="s" uri="/struts-tags"%>
<s:select
	name="questionChoice"
	list="availableQuestions"
	listKey="%{id}"
	listValue="%{text}"
	theme="simple"
	value="defaultQuestionId"
/>
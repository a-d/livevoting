<%@ taglib prefix="s" uri="/struts-tags"%>
<s:select
	name="userChoice"
	list="availableUsers"
	listKey="%{id}"
	listValue="%{ip}"
	theme="simple"
	emptyOption="true"
	value="defaultUserId"
/>
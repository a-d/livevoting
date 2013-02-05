<%@ taglib prefix="s" uri="/struts-tags"%>
<s:select
	name="teacherChoice"
	list="availableTeachers"
	listKey="%{id}"
	listValue="%{name}"
	theme="simple"
	emptyOption="true"
	value="defaultTeacherId"
/>
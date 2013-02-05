<%@ taglib prefix="s" uri="/struts-tags"%>
<s:select
	name="courseChoice"
	list="availableCourses"
	listKey="%{id}"
	listValue="%{name}"
	theme="simple"
	value="defaultCourseId"
/>
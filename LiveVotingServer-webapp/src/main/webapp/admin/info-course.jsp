<%@ taglib prefix="s" uri="/struts-tags"%>

<table>
	<tr>
		<td>Id : </td>
		<td><s:property value="model.id"/></td>
	</tr>
	<tr>
		<td>Name : </td>
		<td><s:property value="model.name"/></td>
	</tr>
	<tr>
		<td>Teacher: </td>
		<td><s:property value="model.teacher.name"/></td>
	</tr>
</table>

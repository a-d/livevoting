<%@ taglib prefix="s" uri="/struts-tags"%>

<table style="width:90%">
	<tr>
		<td width="150">Id : </td>
		<td colspan="2"><s:property value="model.id"/></td>
	</tr>
	<tr>
		<td>Course : </td>
		<td colspan="2"><s:property value="model.course.name"/></td>
	</tr>
	<tr>
		<td>Teacher: </td>
		<td colspan="2"><s:property value="model.course.teacher.name"/></td>
	</tr>
	<tr>
		<td>Picture: </td>
		<td colspan="2"><img src='../download/png?questionId=<s:property value="model.id"/>' /></td>
	</tr>
	<tr>
		<td>Text: </td>
		<td colspan="2"><s:property value="model.text"/></td>
	</tr>
	<tr>
		<td>1+ Answers: </td>
		<td colspan="2"><s:property value="model.multipleChoice"/></td>
	</tr>
	<tr>
		<td>Custom Answers: </td>
		<td colspan="2"><s:property value="model.customAnswer"/></td>
	</tr>
	<tr>
		<td colspan="3">Current Result: </td>
	</tr>
	<s:iterator value="stats">
		<tr>
		  <td></td>
		  <td width="210"><img src='../download/png?answerId=<s:property value="key.id" />' style="max-width:200px; max-height:150px" /></td>
		  <td><div style="width:<s:property value="100*(1f*value /numberOfAllVotes)" />%; background-color:#000000; color:#fff; padding:10px; min-width:20px; ">
		  <s:property value="value" />
		  </div></td>		  
		</tr>
	</s:iterator>
</table>

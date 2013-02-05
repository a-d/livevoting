<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>


	<s:url id="remoteurl" action="json-table-user">
		<s:param name="forceId" value="#parameters['forceId']" />
	</s:url>
    <s:url id="editurl" action="edit-user" />
    
	<sj:dialog
		id="user_details"
		title="User Details"
		autoOpen="false"
		modal="true"
		width="600"
	>

	</sj:dialog>
	<h2>
	<s:if test="#parameters['forceId']!=null">
		User [#<s:property value="#parameters['forceId']" />]
	</s:if>
	<s:else>
		Users
	</s:else>
	</h2>
    <p>
		<strong>Every step immediately effects the database.</strong>
    </p>
    <sjg:grid
    	id="userstable"
    	caption="Administration - User"
    	dataType="json"
    	href="%{remoteurl}"
    	pager="true"
    	navigator="false"
    	navigatorEdit="false"
    	navigatorView="false"
    	navigatorDelete="false"
    	gridModel="gridModel"
    	rowList="10,15,20,50"
    	rowNum="15"
    	width="800"
    	editurl="%{editurl}"
    	multiselect="true"
    	viewrecords="true"
    	viewsortcols="[true, 'horizontal', true]"
    >

    	<sjg:gridColumn name="id"
    		index="userId"
    		key="true"
    		title="ID"
    		width="50"
    		formatter="integer"
    		sortable="true"
    		search="true"
    		searchoptions="{sopt:['eq','ne','lt','gt']}"
    	/>
    	
    	<sjg:gridColumn
    		name="browserAgent"
    		index="browserAgent"
    		title="Browser Agent"
    		sortable="true"
    		editable="false"
    		edittype="text"
    		/>
    		
    	<sjg:gridColumn
    		name="ip"
    		index="ip"
    		title="IP"
    		sortable="true"
    		editable="false"
    		edittype="text"
    		/>
    		
    	<sjg:gridColumn
    		name="date"
    		index="date"
    		title="Visit Date"
    		width="70"
    		formatter="date"
    		sortable="true"
    		search="true"
    		searchoptions="{sopt:['eq','ne','lt','gt']}"
    		formatoptions="{
              srcformat: 'ISO8601Long',
              newformat: 'd.m.Y',
              defaultValue: '01.01.1970'
          	}"
          	defval="01.01.2010"
			editable="false"
          	edittype="text"
          	required="false"
          	editrules="{date: true}"
    	/>
    		

    </sjg:grid>

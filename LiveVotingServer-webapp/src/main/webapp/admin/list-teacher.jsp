<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>


	<s:url id="remoteurl" action="json-table-teacher">
		<s:param name="forceId" value="#parameters['forceId']" />
	</s:url>
    <s:url id="editurl" action="edit-teacher" />
    
	<sj:dialog
		id="teacher_details"
		title="Teacher Details"
		autoOpen="false"
		modal="true"
		width="600"
	>

	</sj:dialog>
	<h2>
	<s:if test="#parameters['forceId']!=null">
		Teacher [#<s:property value="#parameters['forceId']" />]
	</s:if>
	<s:else>
		Teachers
	</s:else>
	</h2>
    <p>
		<strong>Every step immediately effects the database.</strong>
    </p>
    <sjg:grid
    	id="teacherstable"
    	caption="Administration - Teachers"
    	dataType="json"
    	href="%{remoteurl}"
    	pager="true"
    	navigator="true"
    	navigatorAddOptions="{
    		height:280,
    		reloadAfterSubmit:true,
			afterSubmit:function(response, postdata) {
							return isError(response.responseText);
                         }
		}"
    	navigatorEdit="true"
    	navigatorEditOptions="{
    		height:280,
    		reloadAfterSubmit:true,
			afterSubmit:function(response, postdata) {
							return isError(response.responseText);
                         }
		}"
    	navigatorView="true"
    	navigatorDelete="true"
    	navigatorDeleteOptions="{
    		height:280,
    		reloadAfterSubmit:true,
			afterSubmit:function(response, postdata) {
							return isError(response.responseText);
                         }
		}"
    	gridModel="gridModel"
    	rowList="10,15,20,50"
    	rowNum="15"
    	editurl="%{editurl}"
    	editinline="false"
    	multiselect="true"
    	viewrecords="true"
    	viewsortcols="[true, 'horizontal', true]"
    >

    	<sjg:gridColumn name="id"
    		index="voteId"
    		key="true"
    		title="ID"
    		width="50"
    		formatter="integer"
    		sortable="true"
    		search="true"
    		searchoptions="{sopt:['eq','ne','lt','gt']}"
    	/>
    	

    	<sjg:gridColumn
    		name="name"
    		index="name"
    		title="Name"
    		width="300"
    		sortable="true"
    		editable="true"
    		edittype="text"
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
    		name="dateRegistered"
    		index="dateRegistered"
    		title="Registered"
    		sortable="true"
    		editable="false"
    		edittype="text"
    		/>    	
    	
    	<sjg:gridColumn
    		name="hashedPassword"
    		index="hashedPassword"
    		title="Hashed Password"
    		sortable="true"
    		editable="false"
    		edittype="text"
    		/>
    	<sjg:gridColumn
    		name="password"
    		index="password"
    		title="New Password"
    		sortable="true"
			editrules="{ edithidden : true } "
    		hidden="true"
    		editable="true"
    		edittype="text"
    		/>
    		
    </sjg:grid>

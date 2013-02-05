<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

	<s:url id="remoteurl" action="json-table-answer" escapeAmp="false">
	    <s:param name="forceId" value="#parameters['forceId']" />
    </s:url>
    
    <s:url id="editurl" action="edit-answer" />
    
    <s:url id="selectquestionurl" action="selection-question">
    </s:url>
    <s:url id="selectuserurl" action="selection-user">
    </s:url>

	<sj:dialog
		id="answer_details"
		title="Answer Details"
		autoOpen="false"
		modal="true"
		width="600"
	>

	</sj:dialog>
	<h2>
	<s:if test="#parameters['forceId']!=null">
		Answer [#<s:property value="#parameters['forceId']" />]
	</s:if>
	<s:else>
		Answers
	</s:else>
	</h2>
    <p>
		<strong>Every step immediately effects the database.</strong>
    </p>
    <sjg:grid
    	id="answerstable"
    	caption="Administration - Answers"
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
		navigatorExtraButtons="{
				seperator: { 
						title : 'seperator'  
				},
				votes : { 
						title : 'Votes', 
						icon : 'ui-icon-document',
						onclick: function(e){ window.goSubTableWithIds('answerId', e); }
				}
		}"
    	gridModel="gridModel"
    	rowList="5,10,15,20,50"
    	rowNum="5"
    	editurl="%{editurl}"
    	editinline="false"
    	multiselect="true"
    	viewrecords="true"
    	viewsortcols="[true, 'horizontal', true]"
    >
    	<sjg:gridColumn name="id"
    		index="answerId"
    		key="true"
    		title="ID"
    		width="50"
    		formatter="integer"
    		sortable="true"
    		search="true"
    		searchoptions="{sopt:['eq','ne','lt','gt']}"
    	/>
    	
    	
		<!-- question -->
    	<sjg:gridColumn
    		name="question.id"
    		index="questionId"
    		title="Question"
			align="left"
    		formatter="formatLink"
    		width="150"
			cssClass="link"
    		sortable="true"
    		search="true"
    		searchoptions="{sopt:['eq','ne'], dataUrl : '%{selectquestionurl}'}"
    		searchtype="select"
    		editable="true"
    		edittype="select"
    		required="true"
    		editoptions="{ dataUrl : '%{selectquestionurl}' }"
    		formoptions="{ label:'Question', target: 'answer_details' }"
    		/>
    		
		<!-- picture -->
    	<sjg:gridColumn
    		name="picture"
    		index="picture"
    		title="Picture"
			align="left"
    		formatter="formatLink"
    		width="300"
			cssClass="link"
    		sortable="false"
    		search="false"
    		editable="false"
    		required="false"
    		formoptions="{ label:'AnswerPicture', target: 'answer_details' }"
    		/>

    		
    	<sjg:gridColumn
    		name="title"
    		index="title"
    		title="Title"
    		sortable="true"
    		editable="true"
    		edittype="text"
    	/>
    	
    	<sjg:gridColumn
    		name="text"
    		index="text"
    		title="Text"

    		sortable="true"
    		editable="true"
    		edittype="text"
    	/>
    	
<!-- relations -->

    		
		<!-- user -->
    	<sjg:gridColumn
    		name="user.id"
    		index="userId"
    		title="User"
			align="left"
    		formatter="formatLink"
			cssClass="link"
    		sortable="true"
    		search="true"
    		searchoptions="{sopt:['eq','ne'], dataUrl : '%{selectuserurl}'}"
    		searchtype="select"
    		editable="true"
    		required="false"
    		edittype="select"
    		editoptions="{ dataUrl : '%{selectuserurl}' }"
    		formoptions="{label:'User', target: 'answer_details'}"
    		/>
    		
    </sjg:grid>

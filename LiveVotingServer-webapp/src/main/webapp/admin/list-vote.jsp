<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>


	<s:url id="remoteurl" action="json-table-vote">
		<s:param name="forceId" value="#parameters['forceId']" />
	</s:url>
    <s:url id="editurl" action="edit-vote" />
    
	<s:url id="selectanswerurl" action="selection-answer">
	</s:url>
	<s:url id="selectuserurl" action="selection-user">
	</s:url>
	
	<sj:dialog
		id="vote_details"
		title="Vote Details"
		autoOpen="false"
		modal="true"
		width="600"
	>

	</sj:dialog>
    <h2>Votes</h2>
    <p>
		<strong>Every step immediately effects the database.</strong>
    </p>
    <sjg:grid
    	id="votestable"
    	caption="Administration - Votes"
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
    	width="800"
    	editurl="%{editurl}"
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
    		name="date"
    		index="date"
    		title="Date"
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
			editable="true"
          	edittype="text"
          	required="false"
          	editoptions="{
              size: 12,
              value: '01.01.1970',
              maxlengh: 12,
              dataInit: function (element) {
                  $(element).datepicker({ dateFormat: 'dd.mm.yy' })
              }
          	}"
          	editrules="{date: true}"
    	/>
    	
<!-- relations -->

		<!-- user -->
    	<sjg:gridColumn
    		name="user.id"
    		index="userId"
    		title="User"
			align="left"
    		width="100"
    		formatter="formatLink"
			cssClass="link"
    		sortable="true"
    		search="true"
    		searchoptions="{sopt:['eq','ne'], dataUrl : '%{selectuserurl}'}"
    		searchtype="select"
    		editable="true"
    		edittype="select"
    		required="true"
    		editoptions="{ dataUrl : '%{selectuserurl}' }"
    		formoptions="{ label:'User', target: 'user_details' }"
    		/>
    		
		<!-- answer -->
    	<sjg:gridColumn
    		name="answer.id"
    		index="answerId"
    		title="Answer"
			align="left"
    		width="100"
    		formatter="formatLink"
			cssClass="link"
    		sortable="true"
    		search="true"
    		searchoptions="{sopt:['eq','ne'], dataUrl : '%{selectanswerurl}'}"
    		searchtype="select"
    		editable="true"
    		edittype="select"
    		required="true"
    		editoptions="{ dataUrl : '%{selectanswerurl}' }"
    		formoptions="{ label:'Answer', target: 'answer_details' }"
    		/>
    </sjg:grid>

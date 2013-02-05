<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

	<s:url id="remoteurl" action="json-table-question" escapeAmp="true">
		<s:param name="forceId" value="#parameters['forceId']" />
	</s:url>

	<s:url id="editurl" action="edit-question" />
	<s:url id="selectcourseurl" action="selection-course">
	</s:url>
	
	<sj:dialog
		id="question_details"
		title="Question Details"
		autoOpen="false"
		modal="true"
		width="600"
	>

	</sj:dialog>
	
	
	<h2>
	<s:if test="#parameters['forceId']!=null">
		Question [#<s:property value="#parameters['forceId']" />]
	</s:if>
	<s:else>
		Questions
	</s:else>
	</h2>
    <p>
		<strong>Every step immediately effects the database.</strong>
    </p>
	<sjg:grid
		id="questionstable"
		caption="Administration - Questions"
		dataType="json"
		href="%{remoteurl}"
		
		pager="true"
		navigator="true"
		navigatorSearch="#parameters['forceId']==null"
		navigatorAddOptions="{
			height:400,
			reloadAfterSubmit:true,
			afterSubmit:function(response, postdata) {
							return isError(response.responseText);
						 }
		}"
		navigatorEdit="true"
		navigatorEditOptions="{
			height:400,
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
				answers : { 
						title : 'Answers', 
						icon : 'ui-icon-document',
						onclick: function(e){ window.goSubTableWithIds('questionId', e); }
				}
		}"
		gridModel="gridModel"
		rowList="10,15,20,50"
		rowNum="15"
		width="900"
		editurl="%{editurl}"
		editinline="false"
		multiselect="true"
		viewrecords="true"
		viewsortcols="[true, 'horizontal', true]"
	>
		<sjg:gridColumn name="id"
			index="questionId"
			key="true"
			title="ID"
			width="50"
			formatter="integer"
			sortable="true"
			search="true"
			searchoptions="{sopt:['eq','ne','lt','gt']}"
		/>
		
		<!-- picture -->
    	<sjg:gridColumn
    		name="picture"
    		index="picture"
    		title="Picture"
			align="left"
    		formatter="formatLink"
			cssClass="link"
    		sortable="false"
    		search="false"
    		editable="false"
    		required="false"
    		formoptions="{ label:'QuestionPicture', target: 'question_details' }"
    		/>
    		
    	<sjg:gridColumn
    		name="multipleChoice"
    		index="multipleChoice"
    		title="Multiple Votes"
    		formatter="checkbox"
    		editoptions="{ value:'true:false'}"
    		width="25"
    		sortable="true"
    		edittype="checkbox"
    		editable="true"
    		search="true"
    		searchoptions="{sopt:['eq']}"
    		/>
    	<sjg:gridColumn
    		name="customAnswer"
    		index="customAnswer"
    		title="Custom Answers"
    		formatter="checkbox"
    		editoptions="{ value:'true:false'}"
    		width="25"
    		sortable="true"
    		edittype="checkbox"
    		editable="true"
    		search="true"
    		searchoptions="{sopt:['eq']}"
    		/>
		<sjg:gridColumn
			name="text"
			index="text"
			title="Text"
			sortable="true"
			width="100"
			editable="true"
			edittype="text"
		/>
		
    	<sjg:gridColumn
    		name="dateFrom"
    		index="dateFrom"
    		title="Date From"
    		width="80"
    		formatter="date"
    		sortable="true"
    		search="true"
    		searchoptions="{sopt:['eq','ne','lt','gt']}"
    		formatoptions="{
              srcformat: 'ISO8601Long',
              newformat: 'd.m.Y H:i',
              defaultValue: '01.01.1970 00:00'
          	}"
          	defval="01.01.2010"
			editable="true"
          	edittype="text"
          	required="false"
          	editoptions="{
              size: 16,
              maxlengh: 16,
              dataInit: function (element) {
                  $(element).datetimepicker({
                  	dateFormat: 'dd.mm.yy',
                  	timeFormat: 'HH:mm'
                  });
              }
          	}"
    	/>
    	<sjg:gridColumn
    		name="dateTo"
    		index="dateTo"
    		title="Date To"
    		width="80"
    		formatter="date"
    		sortable="true"
    		search="true"
    		searchoptions="{sopt:['eq','ne','lt','gt']}"
    		formatoptions="{
              srcformat: 'ISO8601Long',
              newformat: 'd.m.Y H:i',
              defaultValue: '01.01.1970 00:00'
          	}"
          	defval="01.01.2010"
			editable="true"
          	edittype="text"
          	required="false"
          	editoptions="{
              size: 16,
              maxlengh: 16,
              dataInit: function (element) {
                  $(element).datetimepicker({
                  	dateFormat: 'dd.mm.yy',
                  	timeFormat: 'HH:mm'
                  });
              }
          	}"
    	/>

<!-- relations -->

		<!-- course -->
    	<sjg:gridColumn
    		name="course.id"
    		index="courseId"
    		title="Course"
			align="left"
    		width="100"
    		formatter="formatLink"
			cssClass="link"
			hidden="false"
    		sortable="true"
    		search="true"
    		searchoptions="{sopt:['eq','ne'], dataUrl : '%{selectcourseurl}'}"
    		searchtype="select"
    		editable="true"
    		edittype="select"
    		required="true"
			editrules="{ edithidden : true } "
    		editoptions="{ dataUrl : '%{selectcourseurl}' }"
    		formoptions="{ label:'Course', target: 'course_details' }"
    		/>
	</sjg:grid>

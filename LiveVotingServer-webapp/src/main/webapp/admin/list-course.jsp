<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>

	<s:url id="remoteurl" action="json-table-course" escapeAmp="true">
		<s:param name="forceId" value="#parameters['forceId']" />
	</s:url>

	<s:url id="editurl" action="edit-course" />
	<s:url id="selectteacherurl" action="selection-teacher">
	</s:url>
	
	<sj:dialog
		id="course_details"
		title="Course Details"
		autoOpen="false"
		modal="true"
		width="600"
	>

	</sj:dialog>
	
	
	<h2>
	<s:if test="#parameters['forceId']!=null">
		Course [#<s:property value="#parameters['forceId']" />]
	</s:if>
	<s:else>
		Courses
	</s:else>
	</h2>
    <p>
		<strong>Every step immediately effects the database.</strong>
    </p>
	<sjg:grid
		id="coursestable"
		caption="Administration - Courses"
		dataType="json"
		href="%{remoteurl}"
		
		pager="true"
		navigator="true"
		navigatorSearch="#parameters['forceId']==null"
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
				questions : { 
						title : 'Questions', 
						icon : 'ui-icon-document',
						onclick: function(e){ window.goSubTableWithIds('courseId', e); }
				},
				properties : { 
						title : 'Download Properties', 
						icon : 'ui-icon-comment',
						onclick: function(e){
							var elm = $('div.ui-jqgrid-bdiv tr.ui-state-highlight').get(0);
							if(elm) {
								var id = elm.getAttribute('id');
								window.open('../download/properties?id='+id);
							}
						}
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
			index="courseId"
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
			title="Course"
			sortable="true"
			editable="true"
			edittype="text"
		/>
		<sjg:gridColumn
			name="token"
			index="token"
			title="Token"
			sortable="true"
			editable="true"
			edittype="text"
		/>
		<sjg:gridColumn
			name="url"
			index="url"
			title="Website"
			sortable="true"
			hidden="true"
			editable="true"
			edittype="text"
			editrules="{ edithidden : true } "
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
    		name="teacher.id"
    		index="teacherId"
    		title="Teacher"
			align="left"
    		width="100"
    		formatter="formatLink"
			cssClass="link"
			hidden="false"
    		sortable="true"
    		search="true"
    		searchoptions="{sopt:['eq','ne'], dataUrl : '%{selectteacherurl}'}"
    		searchtype="select"
    		editable="true"
    		edittype="select"
    		required="true"
			editrules="{ edithidden : true } "
    		editoptions="{ dataUrl : '%{selectteacherurl}' }"
    		formoptions="{ label:'Teacher', target: 'teacher_details' }"
    		/>
	</sjg:grid>

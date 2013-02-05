<?xml version="1.0" encoding="utf-8"?>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<s:url id="urlgrid" action="list-question"/>
<s:set var="escapedVarId">__id__</s:set>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title><s:text name="global.document.title" /> - <s:text name="LiveVoting.version"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Content-Style-Type" content="text/css" />
	<meta http-equiv="pragma" content="no-cache" />
	<meta http-equiv="cache-control" content="no-cache" />
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="keywords" content="struts2, jquery, hibernate, plugin, LiveVoting, grid" />
	<meta http-equiv="description" content="<s:text name='global.document.title' />" />
	<link href="../styles/layout.css" rel="stylesheet" type="text/css" />
	<link href="style.css" rel="stylesheet" type="text/css" />
	<!--[if lte IE 7]>
	<link href="../styles/patch_layout.css" rel="stylesheet" type="text/css" />
	<![endif]-->


    <sj:head
    	loadAtOnce="true"
    	compressed="false"
    	jquerytheme="showcase"
    	customBasepath="themes"
    	loadFromGoogle="false"
    	debug="true"
    />
    
    <s:url id="urlInfoCourse" action="info-course" />
    
   	<s:url id="urlCourse" action="list-course" />
   	<s:url id="urlQuestion" action="list-question" />
   	<s:url id="urlAnswer" action="list-answer" />
   	<s:url id="urlTeacher" action="list-teacher" />
   	<s:url id="urlVote" action="list-vote" />
   	<s:url id="urlUser" action="list-user" />
   	<s:url id="urlLogout" action="logout" />

	<script type="text/javascript">
		var myDialogUrls = {
				"Course" : '<s:property value="urlInfoCourse"/>',
				"Question" : null,
				"Answer" : null,
				"Vote" : null,
				"Teacher" : null,
				"User" : null
		};


		var foreignKeyColumns = {
				"Course" : {
					"caption" : function(entity) { return entity["name"]; },
					"info" : function(entity) { return '<s:url action="info-course"><s:param name="forceId" value="escapedVarId" /></s:url>'.replace('<s:property value="escapedVarId"/>', entity["id"]); },
					"list" : function(entity) { return '<s:url action="list-course"><s:param name="forceId" value="escapedVarId" /></s:url>'.replace('<s:property value="escapedVarId"/>', entity["id"]); }
				},
				"Question" : {
					"caption" : function(entity) { return '<img src="../<s:url action="png" namespace="download" />?questionId='+entity["id"]+'" />'+entity["text"]; },
					"info" : function(entity) { return '<s:url action="info-question" />?id='+entity["id"];; },
					"list" : function(entity) { return '<s:url action="list-question"><s:param name="forceId" value="escapedVarId" /></s:url>'.replace('<s:property value="escapedVarId"/>', entity["id"]); }
				},
				"QuestionPicture" : {
					"caption" : function(entity, that) { return '<img src="../<s:url action="png" namespace="download" />?questionId='+that["id"]+'" />'; },
					"info" : function(entity, that) { return '<s:url action="info-question" />?id=' + (entity["id"] || that["id"]); },
					"list" : function(entity) { return null; }
				},
				"Answer" : {
					"caption" : function(entity) { return entity["title"] ? entity["title"] : entity["text"]; },
					"info" : function(entity) { return null; },
					"list" : function(entity) { return '<s:url action="list-answer"><s:param name="forceId" value="escapedVarId" /></s:url>'.replace('<s:property value="escapedVarId"/>', entity["id"]); }
				},
				"AnswerPicture" : {
					"caption" : function(entity, that) { return '<img src="../<s:url action="png" namespace="download" />?answerId='+that["id"]+'" />'; },
					"info" : function(entity) { return null; },
					"list" : function(entity) { return null; }
				},
				"Vote" : {
					"caption" : function(entity) { return entity["id"]; },
					"info" : function(entity) { return null; },
					"list" : function(entity) { return null; }
				},
				"Teacher" : {
					"caption" : function(entity) { return entity["name"]; },
					"info" : function(entity) { return null; },
					"list" : function(entity) { return '<s:url action="list-teacher"><s:param name="forceId" value="escapedVarId" /></s:url>'.replace('<s:property value="escapedVarId"/>', entity["id"]); }
				},
				"User" : {
					"caption" : function(entity) { return entity["ip"]; },
					"info" : function(entity) { return null; },
					"list" : function(entity) { return '<s:url action="list-user"><s:param name="forceId" value="escapedVarId" /></s:url>'.replace('<s:property value="escapedVarId"/>', entity["id"]); }
				}
		};
		
		var myListUrls = {
				"Courses" : '<s:property value="urlCourse"/>',
				"Questions" : '<s:property value="urlQuestion"/>',
				"Answers" : '<s:property value="urlAnswer"/>',
				"Votes" : '<s:property value="urlVote"/>',
				"Teachers" : '<s:property value="urlTeacher"/>',
				"Users" : '<s:property value="urlUser"/>',
		}
	</script>
	
  <script type="text/javascript" src="js/admin.js"></script>
  <script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
  <link rel="stylesheet" type="text/css" href="themes/showcase/jquery-ui.timepicker-addon.css" />
</head>
<body>
  <div class="page_margins">
    <div class="page">
      <div id="header" class="ui-widget-header">
        <div id="headline">
	        <h1 class="ui-state-default" style="background: none; border: none;width: 700px">Database-Administration for LiveVoting</h1>
	        <h4 class="ui-state-default" style="background: none; border: none;width: 700px">CMS - Version <s:text name="LiveVoting.version"/> / <s:text name="LiveVoting.year"/></h4>
        </div>
      </div>
      <div id="nav">
        <div class="hlist ui-widget-header">
          <ul>
            <li class="ui-widget-header ui-state-active">
            	<sj:a id="linkCourse" href="%{urlCourse}" targets="main">Courses</sj:a>
            </li>
            <li class="ui-widget-header ui-state-active">
            	<sj:a id="linkQuestion" href="%{urlQuestion}" targets="main">Questions</sj:a>
            </li>
            <li class="ui-widget-header">
            	<sj:a id="linkAnswer" href="%{urlAnswer}" targets="main">Answers</sj:a>
            </li>
            <li class="ui-widget-header">
            	<sj:a id="linkVote" href="%{urlVote}" targets="main">Votes</sj:a>
            </li>
          </ul>
          <ul style="float:right; margin-left:5em;">
            <li class="ui-widget-header client">
				<a href="../files/LiveVotingClient.jar">Download Client</a>
			</li>
            <li class="ui-widget-header logout">
            	<jsp:element name="a">
            		<jsp:attribute name="href"><s:property value="urlLogout" /></jsp:attribute>
            		<jsp:body>Logout</jsp:body>
            	</jsp:element>
            </li>
            </ul>
          <ul style="float:right">
            <li class="ui-widget-header">
            	<sj:a id="linkUser" href="%{urlUser}" targets="main">Users</sj:a>
            </li>
            <li class="ui-widget-header">
            	<sj:a id="linkTeacher" href="%{urlTeacher}" targets="main">Teachers</sj:a>
            </li>
          </ul>
        </div>
      </div>
      <sj:div id="main" href="%{urlgrid}">
        <img id="indicator" src="../images/indicator.gif" alt="Loading..."/>
      </sj:div>
      
      <!-- begin: #footer -->
      <div id="footer">
        Written by <a href="mailto:alexander_duemont@web.de">Alexander D&uuml;mont</a>
        <br /> 
        <small>
        	CMS is an enhanved version of the work "Struts2 jQuery Grid Plugin Showcase" by
        	Johannes Geppert and Jos&eacute; Yoshiriro
        </small>
      </div>
    </div>
  </div>
</body>
</html>

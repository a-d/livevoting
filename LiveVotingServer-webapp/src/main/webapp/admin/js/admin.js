/*
 * Format a Column as Link
 */
function formatLink(cellvalue, options, row) {
	window.qvcValue = cellvalue;
	window.qvcOptions = options;
	window.qvcRow = row;

	var html = "";

	var trgObject = options.colModel.name;
	if(cellvalue!=null && trgObject!=null) {
		var trgDialog = options.colModel.formoptions.target;

		var trgEntity = row;
		var urlKey = options.colModel.formoptions.label; // options.colModel.title
		var dotPos = trgObject.indexOf(".");
		
		if(dotPos>0) {
			trgObject = trgObject.substring(0, dotPos);
		}
		trgObject = trgEntity[trgObject];
		
		var trgColumn = foreignKeyColumns[urlKey];
		if(trgColumn!=null && trgObject!=null) {
			var trgCaption = trgColumn.caption(trgObject, trgEntity);
			var trgInfoUrl = trgColumn.info(trgObject, trgEntity);
			var trgListUrl = trgColumn.list(trgObject, trgEntity);
			var lbl = trgCaption!=null ? trgCaption : (cellvalue=="null" ? "[" + cellvalue + "]" : "-");
			
			if(trgInfoUrl!=null) {
				html = "<a href=\"#\" onClick=\"javascript:openDialog("
							+ "'" + trgDialog + "',"
							+ "'" + trgInfoUrl + "'"
						+ ")\">"+lbl+"</a>";
			}
			else {
				html = lbl;
			}
		
			if(trgListUrl!=null) {
				html = "<a href=\"#\" onClick=\"javascript:openDialog("
					+ "'main',"
					+ "'" + trgListUrl + "'"
				+ ")\">[+]</a>"+html;
			}
		}
		else {
			html = cellvalue+"(?)";
			window.qvcBla = urlKey;
		}
	}
	else {
		html = "-";
	}
	return html;
}

/*
 * Open Dialog with Employee Details
 */
function openDialog(trgDialog, trgUrl) {
	$("#"+trgDialog).load(trgUrl);
	$("#"+trgDialog).dialog('open');
}

/*
 * Call action by parameter and grid ids
 */
function goSubTableWithIds(foreignKey, event, addParameter) {
	var eventName = $(event.currentTarget).attr("title").replace(/\W+/g, "");
	
	
	var url = myListUrls[eventName];
	if(!url) {
		alert("Could not load url from list: \""+eventName+"\"");
		return false;
	}
	
	var key = foreignKey;
	if(!key) {
		alert("Could not load key from event.");
		return false;
	}
	
	var idString = jQuery
		.map(
			$('div.ui-jqgrid-bdiv tr.ui-state-highlight'),
			function (a) { return a.getAttribute('id');
		})
		.join(',');
	url = url+'?'+key+'='+idString;
	
	if(addParameter && addParameter.length)
	for(i=0; i<addParameter.length; i++) {
		url += "&"+addParameter[i];
	}
	$('#main').load(url);
}


/*
 * Handle Error Messages for Form Editing
 */
function isError(text) {

	if (text.indexOf('ERROR') >= 0) {
		return [ false, text ];
	}

	return [ true, '' ];
}



function dndCommit(url, dataObj, top, bottom) {
	url = url.replace("&amp;", "&");
	
	var data = {
		'entitiesAdd' : '',
		'entitiesRemove' : ''
	}
	
	var strAdd = '';
	if(dataObj[top])
	for(var i=0; i<dataObj[top].length; i++) {
		var entId = dataObj[top][i];
		if(entId) {
			strAdd += (strAdd ? ',' : '') + entId;
		}
	}
	data.entitiesAdd = strAdd;
	
	var strRemove = '';
	if(dataObj[bottom])
	for(var i=0; i<dataObj[bottom].length; i++) {
		var entId = dataObj[bottom][i];
		if(entId) {
			strRemove += (strRemove ? ',' : '') + entId;
		}
	}
	data.entitiesRemove = strRemove;
	
	$.ajax({
	  type: 'POST',
	  url: url,
	  data: data,
	  success: function(data, textStatus, jqXHR) {
		$("#dndmessages").text("changes have been sent. \n"+(data ? data +", " : "")+" ("+textStatus+")");
	  },
	  dataType: 'json'
	});
}



$(document).ready(
		function() {

			/*
			 * Topic for Drag and Drop Example.
			 */
			$.subscribe('ondrop', function(event, data) {
				var dragElem = $(event.originalEvent.ui.draggable);
				var dropElem = $(event.originalEvent.event.target);
				var id = $("td", dragElem).first().text();
				var targetGrid = dropElem.attr("id");
				$("#dndmessages").html('Moved ' + id + ' to grid ' + targetGrid);
				
				
				
				/*
				 * move element in draggedEntities map
				 */
				var dragMap = window.draggedEntities;
				if(dragMap) {
					for(var gridName in dragMap) {
						var gridChanges = dragMap[gridName];
						for(var x=0; x<gridChanges.length; x++) {
							if(gridChanges[x]==id) {
								dragMap[gridName][x] = null;
							}
						}
					}
					if(!dragMap[targetGrid]) {
						dragMap[targetGrid] = [];
					}
					dragMap[targetGrid][dragMap[targetGrid].length] = id; 
				}
			});

			/*
			 * Menu Highlight
			 */
			$('div.ui-widget-header > ul > li').click(
					function() {
						$('div.ui-widget-header > ul > li').removeClass('ui-state-active');
						$(this).addClass('ui-state-active');
					}, function() {
					});
			$('div.ui-widget-header > ul > li').hover(function() {
				$(this).addClass('ui-state-hover');
			}, function() {
				$(this).removeClass('ui-state-hover');
			});

		});

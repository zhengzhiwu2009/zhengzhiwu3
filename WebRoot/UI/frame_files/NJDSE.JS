
function openwindow(URL){
	window.open(URL,"name","width=780, height=500, toolbars=no, scrollbars=yes resizable=yes");
}

function selectall(thisform){
	for (i=0; i<thisform.elements["checkbox"].length; i++){
		thisform.checkbox[i].checked = true;
	}
}

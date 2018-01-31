var menuStr = "menu";

var preMenu = "0";

function registMenu(obj)
{
	if(preMenu!="")	
	{
		offMenu((typeof(preMenu)=="string")?$$$(menuStr+""+preMenu):preMenu);
	}
	if(obj.getAttribute("status")=="off")
	{
		obj.setAttribute("status","on");
		preMenu = obj;
	}
}
function overMenu(obj)
{
	setClass(obj,"on");
}
function outMenu(obj)
{
	if(obj.getAttribute("status")=="off")
	{
		setClass(obj,"off");
	}
	else
	{
		setClass(obj,"on");
	}
}

function onMenu(obj)
{
	obj.setAttribute("status","on");
	setClass(obj,"on");
}
function offMenu(obj)
{
	obj.setAttribute("status","off");
	setClass(obj,"off");
}
function setClass(obj,status)
{
	if(obj.className.length>=2)
	{
		if(obj.className.lastIndexOf("-")>=0)
		{
			obj.className = obj.className.substring(0,obj.className.lastIndexOf("-")+1)+status;
			
		}
	}
}
function $$$(id)
{
	return document.getElementById(id);
}
window.onload =function()
{
	if($$$(menuStr+""+preMenu))
	{
		onMenu($$$(menuStr+""+preMenu));
		preMenu = $$$(menuStr+""+preMenu);
	}
}





function clearAllChild()
{
	for(var i=1;i<9;i++)
	{
		document.getElementById("child"+i).style.display="none";
	}
}

function show(DivId)
{
	if(document.all[DivId].style.display=='none')
	  { document.all[DivId].style.display='' }
	else
	  { document.all[DivId].style.display='none'}

	parent.resizea();parent.parent.resize();
		return 0;
}




function resize()
{
	document.getElementById("frm1").height=0;
	document.getElementById("frm1").height=document.getElementById("frm1").contentWindow.document.body.scrollHeight;
}
function resizea()
{
	document.getElementById("frm2").height=0;
	document.getElementById("frm2").height=document.getElementById("frm2").contentWindow.document.body.scrollHeight;
}
function resizea()
{
	document.getElementById("frm3").height=0;
	document.getElementById("frm3").height=document.getElementById("frm3").contentWindow.document.body.scrollHeight;
}
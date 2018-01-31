// JavaScript Document
//系列菜单的特征标记。后面可跟上序列数字
var menuStr = "menu";
//默认标记为on的菜单项序号
var preMenu = "0";
////////////////////////////////
////////////////////////////////
function registMenu(obj)
{
	if(preMenu!="")	
	{
		offMenu((typeof(preMenu)=="string")?$(menuStr+""+preMenu):preMenu);
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
function $(id)
{
	return document.getElementById(id);
}
window.onload =function()
{
	if($(menuStr+""+preMenu))
	{
		onMenu($(menuStr+""+preMenu));
		preMenu = $(menuStr+""+preMenu);
	}
}



function clearAllChild()
{
	for(var i=1;i<10;i++)
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

	parent.resize();
		return 0;
}




function resize()
{
	document.getElementById("main").height=0;
	document.getElementById("main").height=document.getElementById("main").contentWindow.document.body.scrollHeight;
	
}
function resizea()
{
	document.getElementById("jxmain").height=0;
	document.getElementById("jxmain").height=document.getElementById("jxmain").contentWindow.document.body.scrollHeight;
}
function resizea()
{
	document.getElementById("zjmain").height=0;
	document.getElementById("zjmain").height=document.getElementById("zjmain").contentWindow.document.body.scrollHeight;
}
// JavaScript Document 
//系列菜单的特征标记。后面可跟上序列数字
//var menuStr = "menu";
//默认标记为on的菜单项序号
//var preMenu = "0";
////////////////////////////////
////////////////////////////////
var menuStr = "menu";
//默认标记为on的菜单项序号
olddiv=top.document.location+"";
var preMenu = 0;
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
	if(olddiv.split("m=").length==2){
	
		preMenu = olddiv.split("m=")[1].substring(0,10);
		
	}else{
	
		preMenu = 0;
	
	}
	
	
	if($(menuStr+""+preMenu))
	{
		onMenu($(menuStr+""+preMenu));
		preMenu = $(menuStr+""+preMenu);
	}
}





function clearAllChild()
{
	for(var i=1;i<5;i++)
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





function    locking(){

document.all.Layer2.style.display='block';
}
function    Lock_CheckForm(theForm){
document.all.Layer2.style.display='none';
return   false;
}



 // JavaScript Document
 var myCount = 0;
var treedata	= new Array();
var path		= "";
var tempMenu;
function treemenu(tree_path,tree_name,tree_ico)
{
	path	= tree_path;
	name	= tree_name;
	ico	= tree_ico;
	
	document.write("<div style='font-size:15px;font-weight:bold'><img src='"+path+ico+"' align=top border=0>"+name+"</div>");

}//end function treemenu
//                 0 id       1 ??id     2?       3????      4????     5??    6??
function add_item(tree_id,tree_prarent,tree_name,tree_close_ico,tree_open_ico,tree_url,tree_target,page_url)
{
	if(page_url==undefined) page_url="";
	if(tree_close_ico=="")
		tree_close_ico="close.gif";
	if(tree_open_ico=="")
		tree_open_ico="open.gif";
	//???? 4? 6???
	treedata[treedata.length]	= new Array(tree_id,tree_prarent,tree_name,tree_url,tree_close_ico,tree_open_ico,tree_target,page_url);
}//end function add_item

function print_arr()
{
	var i;
	var j;
	var n	= treedata.length;
	var m	= treedata[0].length;
	
	for(var i=0; i<n; i++)
	{
		for(j=0; j<m;j++)
		{
			//"  treedata["+i+"]["+j+"]="
			document.write(" "+j+":"+treedata[i][j]);
		}
		document.write(" <br>\n");
	}
}//end function print_arr

function menu(id)
{
	var currdata	= new Array();
	var i			= 0;
	var printstr	= "";	//zlb	"<table border='0' cellspacing='0' cellpadding='0'>\n";
	var listtype	= "";
	var menutype	= "";
	var onmouseup   = "";
	
	for(; i<treedata.length; i++)
	{
		if(treedata[i][1]==id)	currdata[currdata.length] = treedata[i];
	}//end for	
	
	
	for(var i=0; i<currdata.length; i++)
	{
		var clickFunction;
		if(itemExists(currdata[i][0]))
		{
			if(i==currdata.length-1)
			{
				menutype	= "menu3";
				listtype	= "list1";
			}
			else
			{
				menutype	= "menu1";
				listtype	= "list";
			}//end if
			onmouseup	= "chengstate("+currdata[i][0]+")";
			menuname	= currdata[i][2];
			clickFunction = "";
		}
		else
		{
			if(i==currdata.length-1)
			{
				menutype	= "file1";
			}
			else
			{
				menutype	= "file";
			}//end if
			var tmpN;
			if(currdata[i][2].length>=13){
				tmpN = currdata[i][2].slice(0,11)+"?";
			}else{
				tmpN = currdata[i][2]
			}
			if(currdata[i][3] == "")
			{
				currdata[i][3] = "./menu.htm";
			}
//zlb			menuname	= "<a href='"+currdata[i][3]+"' onmouseup='top.openPage(\""+currdata[i][7]+"\",\"objID\")' target='"+currdata[i][6]+"' title='"+currdata[i][2]+"' style='height:14px;padding-top:5px;padding-bottom:2px;margin-top:-4px;margin-left:-2px;'>"+tmpN+"</a>";
			menuname = tmpN;
			
			if(currdata[i][3] == "./menu.htm")
			{
				onmouseup	= "top.modelD()";
			}
			else
			{
				onmouseup	= "top.showMenu()";
			}
			clickFunction = "registerObj(this)";
		}//end if
//zlb		ico			= "<img src='"+path+currdata[i][4]+"' id='ico"+currdata[i][0]+"' align=bottom border=0>";
//zlb		printstr	+= "<tr><td id='pr"+currdata[i][0]+"' valign=middle class="+menutype+" onMouseUp="+onmouseup+">"+ico+"<span valign='bottom' onMouseDown='this.focus()' onMouseOver='over_str(this)' onMouseOut='out_str(this)' class='item' style='margin-left:2px;padding-left:5px;padding-right:3px' thisUrl="+ currdata[i][3] +" thisName=" + currdata[i][2] + " onMouseUp='"+ clickFunction +"'>"+menuname+"</span> </td></tr>\n";
		if(!itemExists(currdata[i][0]))
			printstr += "<a href='"+currdata[i][3]+"' onmouseup='top.openPage(\""+currdata[i][7]+"\",\"objID\")' target='"+currdata[i][6]+"' title='"+currdata[i][2]+"'>";
			
		printstr += "<div id='pr"+currdata[i][0]+"' class="+menutype+" onMouseUp='"+onmouseup+"' onClick='"+clickFunction+"' onMouseDown='this.focus()' thisUrl="+ currdata[i][3] +" thisName=" + currdata[i][2] + " onMouseOver='over_str(this)' onMouseOut='out_str(this)'>"+menuname+"</div>";
		
		if(!itemExists(currdata[i][0]))
			printstr += "</a>";
		printstr += "<div id='item"+currdata[i][0]+"' style='display:none;padding-left:10px;' class='menu_2_content'>"+menu(currdata[i][0])+"</div>";

	}//end for
//zlb	printstr	+= "</table>\n";
	
	return printstr;
}//end function menu

function itemExists(id)
{
	for(var i=0;i<treedata.length;i++)
	{
		if(treedata[i][1]==id)return true;
	}//end for
	return false;
}//end function itemExists

function closeAll()
{
	var len	= treedata.length;
	for(var i=0; i<len; i++)
	{
		obj		= eval("pr"+treedata[i][0]);
		if(obj.className == "menu2" || obj.className == "menu4")
		{
			chengstate(treedata[i][0]);
		}//end if
	}//end for
	
}//end function closeAll

function openAll()
{
	var len	= treedata.length;
	for(var i=0; i<len; i++)
	{
		obj		= eval("pr"+treedata[i][0]);
		if(obj.className == "menu1" || obj.className == "menu3")
		{
			chengstate(treedata[i][0]);
		}//end if
	}//end for
}//end function openAll

function registerObj(obj)
{
	top.clearItv();
	if(tempMenu && obj!=tempMenu)
	{
//zlb		tempMenu.style.background	= "";
//zlb		tempMenu.style.border		= "0px";
		tempMenu.className = "file";
	}
	tempMenu = obj;
	tempMenu.className = "file_focus";
}

function over_str(obj)
{
//	obj.style.background	= "#F5FDFE";
//	obj.style.border		= "1px solid #0099CC";
	if (obj.className == "file")
		obj.className = "file_focus";

}//end function over

function out_str(obj)
{
	if((tempMenu && obj != tempMenu)||(!tempMenu))
	{
//zlb		obj.style.background	= "";
		//obj.style.border		= "1px solid #FFFFFF";
//zlb		obj.style.border		= "0px";
		if (obj.className == "file_focus")
			obj.className = "file";
	}
}//end function out

function chengstate(menuid,save)
{
	//return;	//zlb
	//alert('execute chengstate method...' + menuid);
	//alert("menuid:"+menuid+"save:"+save);
	menuobj	= eval("item"+menuid);
	obj		= eval("pr"+menuid);
//	ico		= eval("ico"+menuid);
	var len	= treedata.length;
	//alert('treedata.length=' + treedata.length);
	for(var i=0; i<len; i++)
	{
		//alert('i in chengstate' + i); 
		if(treedata[i][0]==menuid)
		{
			break;
		}
	}
	
	if(menuobj.style.display == '')
	{
		menuobj.style.display	= 'none';
//		ico.src					= path+treedata[i][4];
	}else{
		menuobj.style.display	= '';
//		ico.src					= path+treedata[i][5];
	}
	switch (obj.className)
	{
		case "menu1":
			obj.className	= "menu2";
			break;
		case "menu2":
			obj.className	= "menu1";
			break;
		case "menu3":
			obj.className	= "menu4";
			break;
		case "menu4":
			obj.className	= "menu3";
			break;
	}//end switch
	
	if(save!=false)
	{
		setupcookie(menuid);
	}//end if
}//end funciton chengstaut

function setupcookie(menuid)
{										//??cookie  ??????
	var menu	= new Array();
	var menustr	= new String();
	menuOpen	= false;
	if(checkCookieExist("menu"))
	{									//????????????cookie
		menustr		= getCookie("menu");
		//alert(menustr);
		if(menustr.length>0)
		{								//??menu??????????????
			menu	= menustr.split(",");
			for(var i=0;i<menu.length;i++)
			{
				if(menu[i]==menuid)
				{						//??????????????
					menu[i]='';
					menuOpen	= true;
				}//end if
			}//end for
			if(menuOpen==false)menu[i] = menuid;
		}else{
			menu[0]	= menuid;
		}//end if
	}else{
		menu[0]	= menuid;
	}//end if
	menu.sort();
	menustr	= menu.join(",");
	menustr	= menustr.replace(",,",",");
	if(menustr.substr(menustr.length-1,1)==',')menustr = menustr.substr(0,menustr.length-1);		//????? ","
	if(menustr.substr(0,1)==',')menustr = menustr.substr(1,menustr.length-1);		//????? ","
	saveCookie("menu",menustr,1000);
	//alert(menustr);
	//deleteCookie("menu");
}//end function setupcookie

function initialize()
{	
	//alert('initialize......');	
										//??cookie  ???????,,???????
	var menu	= new Array();
	var menustr	= new String();
	
	if(checkCookieExist("menu"))
	{									//????????????cookie
		menustr	= getCookie("menu");
		//alert(menustr);
		if(menustr.length>0)
		{	
			menu	= menustr.split(",");
			//alert('menu.length=' + menu.length);
			for(var i=0;i<menu.length;i++)
			{	
				myCount++;
				//alert('menu['+i+']='	+	menu[i] + 'menu.length in for = ' + menu.length);
				if(objExists(menu[i]))			
				{						//????????
					chengstate(menu[i],false);
				}//end if
			}//end for
			objExists(99);
		}//end if
	}//end if
	
	//alert('myCount=' + myCount);
}//end funciton setupstate

function objExists(objid)
{											//????????
	try
	{
		obj = eval("item"+objid);
	}//end try
	catch(obj)
	{
		return false;
	}//end catch
	
	if(typeof(obj)=="object")
	{
		return true;
	}//end if
	return false;
}//end function objExists
//--------------------------------------------------????????????  ??Cookie ??
function saveCookie(name, value, expires, path, domain, secure)
{											// ??Cookie
  var strCookie = name + "=" + value;
  if (expires)
  {											// ??Cookie???, ?????
     var curTime = new Date();
     curTime.setTime(curTime.getTime() + expires*24*60*60*1000);
     strCookie += "; expires=" + curTime.toGMTString();
  }//end if
  // Cookie???
  strCookie +=  (path) ? "; path=" + path : ""; 
  // Cookie?Domain
  strCookie +=  (domain) ? "; domain=" + domain : "";
  // ????????,??????
  strCookie +=  (secure) ? "; secure" : "";
  document.cookie = strCookie;
}//end funciton saveCookie

function getCookie(name)
{											// ????????Cookie?, null??Cookie???
  var strCookies = document.cookie;
  var cookieName = name + "=";  // Cookie??
  var valueBegin, valueEnd, value;
  // ??????Cookie??
  valueBegin = strCookies.indexOf(cookieName);
  if (valueBegin == -1) return null;  // ???Cookie
  // ????????
  valueEnd = strCookies.indexOf(";", valueBegin);
  if (valueEnd == -1)
      valueEnd = strCookies.length;  // ????Cookie
  // ??Cookie?
  value = strCookies.substring(valueBegin+cookieName.length,valueEnd);
  return value;
}//end function getCookie

function checkCookieExist(name)
{											// ??Cookie????
  if (getCookie(name))
      return true;
  else
      return false;
}//end function checkCookieExist

function deleteCookie(name, path, domain)
{											// ??Cookie
  var strCookie;
  // ??Cookie????
  if (checkCookieExist(name))
  {										    // ??Cookie???????
    strCookie = name + "="; 
    strCookie += (path) ? "; path=" + path : "";
    strCookie += (domain) ? "; domain=" + domain : "";
    strCookie += "; expires=Thu, 01-Jan-70 00:00:01 GMT";
    document.cookie = strCookie;
  }//end if
}//end function deleteCookie
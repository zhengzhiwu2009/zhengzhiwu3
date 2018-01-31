function SH(o)
{
if($("content_"+o)){HAll(o);
if($("content_"+o).style.display=="none"){
$("content_"+o).style.display = "";
//$("title_"+o).style.backgroundColor = "#EDF2FC";
}
else
{
$("content_"+o).style.display="none";
//$("title_"+o).style.backgroundColor = "#F1F2F1";
}
};
}
function HAll(o){for(var i=0;i<50;i++){if($("content_"+i)&&i!=o){$("content_"+i).style.display="none";/*$("title_"+i).style.backgroundColor = "#F1F2F1";*/}}}
function $(o)
{
	if(document.getElementById(o))
		return document.getElementById(o);
	else
		return false;
}

function hidden(o)
{
	for(var i=0;i<50;i++)
	{
		if($("content_"+i)&&i!=o)
		{
			$("content_"+i).style.display="none";/*$("title_"+i).style.backgroundColor = "#F1F2F1";*/
		}
	}
}
//
function setTab(name,cursel,n){
for(i=1;i<=n;i++){
var menu=document.getElementById(name+i);
var con=document.getElementById("con_"+name+"_"+i);
menu.className=i==cursel?"hover":"";
//document.getElementByClassName("hover").a.img.src='../../images/icon_' + cursel + ".jpg"
con.style.display=i==cursel?"block":"none";
}
}

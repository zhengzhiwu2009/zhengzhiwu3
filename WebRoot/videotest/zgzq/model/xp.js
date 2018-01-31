var g_animEffectTimings = null;

function init_ticker(){
	//Cache "messages" element of xml file
	if (xmlDoc == null) return;
		courseslides=xmlDoc.getElementsByTagName("mouse")[0];
	//REMOVE white spaces in XML file. Intended mainly for NS6/Mozilla
	//alert(courseslides.childNodes.length);
	
	if (courseslides == null) 
	{
		g_animEffectTimings = new Array();
		return;
	}
	for (i=0;i<courseslides.childNodes.length;i++){
		if ((courseslides.childNodes[i].nodeType == 3)&&(!notWhitespace.test(courseslides.childNodes[i].nodeValue))) {
			courseslides.removeChild(courseslides.childNodes[i])
			i--
		}
		xmlLoadStatus=true;
	}
	
	msglength=courseslides.childNodes.length;
	var strPoint,iLength,inTime,LastTime=0,iDur=0;
	
	for (i=0;i<msglength;i++){
		strPoint=courseslides.childNodes[i].firstChild.nodeValue;
		iLength = strPoint.length;
		
		if (strPoint.indexOf("2")==0||strPoint.indexOf("3")==0)
		{	
			inTime = parseFloat(strPoint.substr(12, iLength - 9));
			if (starttime>inTime)iDur=0.05;
			else iDur=(inTime-LastTime)/1000;
			strAnimation=strAnimation + iDur +","
			LastTime=inTime;
		}	
	}
	
	strAnimation=strAnimation.substr(0,strAnimation.length-1);
	if (strAnimation.length<1) strAnimation+="0.01,0.01";
	else if (strAnimation.indexOf(",")<0) strAnimation+=",0.01";

	g_animEffectTimings = strAnimation.split(',');

} 

var fetchCount = 0;
function fetchxml(){
	if(fetchCount >= 100)
	{
		g_animEffectTimings = new Array();
		return;
	}
	if (xmlDoc.readyState==4)
	{
		init_ticker()
	}
	else
	{
		fetchCount++;
		setTimeout("fetchxml()",1);
	}
}

var courseslides;
//Regular expression used to match any non-whitespace character
var notWhitespace = /\S/
var xmlsource;
var starttime;
var strAnimation="";

if (g_animItemsToHide){

	try { 
		xmlsource=parent.PPTOtl.getXMLData()+".cmd.xml";
		starttime=parent.PPTOtl.getStartTime();	
	} 
	catch(er) {	
	   xmlsource="1.cmd.xml";
	   starttime=0;
	} 
	
	////No need to edit beyond here////////////
	//load xml file
	if (window.ActiveXObject)
		var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
	else if (document.implementation && document.implementation.createDocument)
		var xmlDoc= document.implementation.createDocument("","doc",null);
	if (typeof xmlDoc!="undefined"){
		xmlDoc.load(xmlsource);
	}
	
	if (window.ActiveXObject)
		fetchxml();
	else if (typeof xmlDoc!="undefined")
		xmlDoc.onload=init_ticker;
	
	writeAnimHtml();
}
else
{
	g_animEffectTimings = new Array();
}

function writeAnimHtml()
{
	var attachSWFHtml="<script>";
	attachSWFHtml+="if (g_animItemsToHide) {";
	attachSWFHtml+="g_animEffectTimings=new Array(";
	attachSWFHtml+=strAnimation;
	attachSWFHtml+=");";
	attachSWFHtml+="g_animSlideTime=11;";
	attachSWFHtml+="}<\/script>";

	//document.write(attachSWFHtml);
}

var spans = document.getElementsByTagName("span");
for (var i=0;i< spans.length;i++)
{
	if(spans[i].innerHTML=="¡±"||spans[i].innerHTML=="¡°") {
		spans[i].lang="EN-US";
		}
	else if(spans[i].style.cssText=="mso-spacerun: yes"){
		spans[i].style.cssText="mso-spacerun: yes;font-family:garamond;";
		}	
}

function LoadSld()
{
	document.body.style.display="none";
	if(g_animEffectTimings == null)
	{
		setTimeout("LoadSld()",1);
		return;
	}
	document.body.style.display="block";
	if(parent!=window&&parent.PPTOtl&&parent.PPTOtl.getDuration)
		g_animSlideTime = parent.PPTOtl.getDuration();
	else
		g_animSlideTime = 10;
	
	var sld=GetObj("SlideObj")
	if( !g_supportsPPTHTML ) {		
		sld.style.visibility="visible"
		return
	}

	if( MakeNotesVis() ) return

	runAnimations = _InitAnimations();
	
	if( IsWin("PPTSld") )
		parent.SldUpdated(GetSldId())
	g_origSz=parseInt(SlideObj.style.fontSize)
	g_origH=sld.style.posHeight
	g_origW=sld.style.posWidth
	g_scaleHyperlinks=(document.all.tags("AREA").length>0)
	if( g_scaleHyperlinks )
		InitHLinkArray()
	if( g_scaleInFrame||(IsWin("PPTSld") && parent.IsFullScrMode() ) )
		document.body.scroll="no"
	_RSW()
	if( IsWin("PPTSld") && parent.IsFullScrMode() )
		FullScrInit();
	
	MakeSldVis();
	ChkAutoAdv()

	if( runAnimations )
	{
		if( document.all("NSPlay") )
			document.all("NSPlay").autoStart = false;
		
		if( sld.filters && sld.filters.revealtrans )
			setTimeout( "document.body.start()", sld.filters.revealtrans.duration * 1000 );
		else
			document.body.start();
	}
}

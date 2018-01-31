var FlashDebug=false;
//1
var CurSldNum=1,mysel=0;
var videoLocalPath="";//针对whatyviewer设计
var currentTitle ="";
var thisNewPaperName="";
var IsMousePressed=false;
var IsDebugging = false;
var IsAudioOnly = false;
var InitLeftSize="224";

var CurColorStyleId;
var CurColorStylePath="";
var DefaultColorStyleId;

var VideoURL="";
var AudioURL="";

var isSetQuiz = false;
var isInitQuiz = false;
var InitLeftSize="224";
var QuizSlides = new Array();
var QuizScores = new Array();
var quizAry = new Array();
var QuizPassScores = new Array();
var quizString = new Array();

var iDivVideoPosBarLeft=0;
var xModelXml=new ActiveXObject("MSXML.DOMDocument");
var oTmrShowCourseInfo;
var IsShowTrademark=true;

var IsShowAudioOnlyMenu=false;

var LastFlashPage=-1;
var LastPage=-1;

var gCurrentPage=0;

var bBuffering =	true;

//---------------------------------------------------------------
//  0 Stopped  1 Contacting  2 Buffering 3 Playing 4 Paused 5 Seeking 
//var mpStopped=0, mpPaused=1, mpPlaying=2, mpWaiting=3,
//mpScanForward=4, mpScanReverse=5, mpSkipForward=6,
//mpSkipReverse=7, mpMediaEnded=8;
//var SlidesCount = -1
 
//==================================================================

/* 字幕支持 start */
var isINIT=false;
var captionAry = new Array();
var curSec=null;
function setSrt()
{
	var curT = Math.ceil(MsnGetPosition());
	if(curSec!=curT)	curSec = curT;
	else	return;
	//比对选择字幕
	try{
		for(var mm=0;mm<captionAry.length;mm++)
		{
			if(curT>captionAry[mm][0] && curT<captionAry[mm][1])
			{
				//srtBox.innerText = captionAry[mm][1]+":"+curT;
				parent.PPTNav.textBox.innerText = captionAry[mm][2];
				break;
			}
			else
			{
				parent.PPTNav.textBox.innerText = "";
			}
		}
	}
	catch(e){}
}

function format2Sec(str)
{
	var sec=0;
	if(str.indexOf(":")>=0 && str.indexOf(".")>=0)
	{
		abc = str.substring(0,str.indexOf(".")).split(":");
		sec = Number(abc[0])*3600+Number(abc[1])*60+Number(abc[2]);
	}
	return sec;
}
var XMLCaption=new ActiveXObject("MSXML.DOMDocument");
XMLCaption.async=false;
var captionURL,lessonLocation = parent.document.location.href;
captionURL = lessonLocation.substring(0,lessonLocation.indexOf("contents.files"))+"whatyCaption.xml";
XMLCaption.load(captionURL);



function startSrt()
{
	try
	{
		var num = XMLCaption.selectNodes("tt/body/div/p").length;
		for(var cpt=0;cpt<num;cpt++)
		{
			var xmlData = XMLCaption.selectNodes("tt/body/div/p").item(cpt);
			captionAry.push([format2Sec(xmlData.getAttribute("begin")),format2Sec(xmlData.getAttribute("end")),xmlData.nodeTypedValue]);
		}
		setInterval("setSrt()",300);
	}
	catch(err)
	{
		setTimeout("startSrt()",1000);
	}
}
startSrt();
/* 字幕支持 end */

function Trim(strOrig){
//	alert(strOrig);
	var ret=strOrig;
	var Err;
	try{
		ret=ret.replace(/^\s+/,"");
		ret=ret.replace(/\s+$/,"");
	}catch(Err){
		return strOrig;
	}
	return ret;
}

function player_DoFSCommand(cmdString, args) {  //这个函数是暴露给n.cmd.xml里的6命令的,函数名没改/
	switch (cmdString) {
	case "quit":
		close();
		break;
	case "quit":
		break;
	case "PlayVideoOrSound":
		if (parseInt(args)==1) { //播放音频文件/
			//playSoundOnly();
		}else{//播放视频文件/
			//playVideoFile();
		}
		break;
	case "Seek":
		VideoJumpTo(parseFloat(args));
		break;
	case "SetMute":
		if (args>0) {
			MsnSetMute(false);
		}else{
			MsnSetMute(true);
		}
		break;
	case "SetVideoFullScreen":
		if (args>0) {
			MsnSetFullSize();
		}else{
			MsnSetOrigSize();
		}
		break;

	default :
		debug("Failed to run \"player_DoFSCommand(" + cmdString + ", " + args +")\"");
	}
}


if (
	navigator.appName &&
	navigator.appName.indexOf("Microsoft") != -1 &&
	navigator.userAgent.indexOf("Windows") != -1 && 
	navigator.userAgent.indexOf("Windows 3.1") == -1) {

	document.write('<SCRIPT LANGUAGE=VBScript\> \n');
	document.write('on error resume next \n');
	document.write('Sub player_FSCommand(ByVal command, ByVal args)\n');
	document.write(' call player_DoFSCommand(command, args)\n');
	document.write('end sub\n');
	document.write('</SCRIPT\> \n');
} 

function setOverflow(flag){
	parent.PPTSld.FlashMovie.style.overflow=flag;
}

/*
function zGetCurSldNum(){
	return CurSldNum;
}
*/

function GetSlideByTime(iSec){	//返回指定时间所在的Slide的序号/
	var i;
	for (i=0; i<=SlidesCount-1; i++) {
		if (i<SlidesCount-1) {
			if (iSec >= parseInt(xmlDoc.selectNodes("courseslides/slide/duration").item(i).nodeTypedValue) &&
				iSec < parseInt(xmlDoc.selectNodes("courseslides/slide/duration").item(i+1).nodeTypedValue)){
				break;
			}
		}else{
			break;
		}
	}
	return i;
}

function GetSlidesCount() {	//返回幻灯片数量/
	return xmlDoc.selectNodes("courseslides/slide").length; 
}

function getXMLData() { //Version4.1  12.24  取鼠标文件的文件名,根据全局变量CurSldNum/
	var MouseData;
	MouseData= xmlDoc.selectNodes("courseslides/slide/sequence").item(CurSldNum-1).nodeTypedValue;
	return MouseData;
}

function setinvisible() {	//让鼠标层消失,由Flash层自己调用,未改函数名/
return;
	if (parent.PPTSld.tome) {
		parent.PPTSld.tome.style.zIndex=-1;
	}
}


function divResize(){
	if (document.body.clientWidth<105){
		//document.body.clientWidth=105;
		return;
	}
	scaleMovie();//调整视频大小	
}

function InitFlashScreen() {

	var attachSWFHtml;
	var iFlashHeight=0,iFlashWidth=0;

	if (parent.PPTSld.FlashMovie && (parent.PPTSld.FlashMovie.innerHTML.length < 30))
	{
		iFlashHeight=isNaN(xmlDoc.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashheight"))?600:parseInt(xmlDoc.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashheight"));
		iFlashWidth=isNaN(xmlDoc.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashwidth"))?800:parseInt(xmlDoc.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashwidth"));
		attachSWFHtml = "";
		attachSWFHtml += "<OBJECT classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\"";
		attachSWFHtml += "codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\"";
		attachSWFHtml += " WIDTH=\"" + iFlashWidth + "\" HEIGHT=\"" + iFlashHeight + "\" id=\"player_clip\" ALIGN=\"bottom\">";
		attachSWFHtml += " <PARAM NAME=movie VALUE=\"../../model/player_clip.swf\"> <param NAME=\"FlashVars\" VALUE=\"FlashDebug=" + FlashDebug + "\">  <param NAME=\"SAlign\" VALUE=\"LT\"> <param NAME=\"Play\" VALUE=\"true\"> <PARAM NAME=menu VALUE=false> <PARAM NAME=scale VALUE=noscale>  <PARAM NAME=quality VALUE=best> <PARAM NAME=wmode VALUE=Opaque> <PARAM NAME=bgcolor VALUE=#FFFFFF> <EMBED src=\"../../model/player_clip.swf\" menu=false quality=best wmode=Opaque bgcolor=#FFFFFF  WIDTH=\""+iFlashWidth+"\" scale=\"noscale\" HEIGHT=\""+iFlashHeight+"\" NAME=\"player_clip\" salign=\"LT\" ALIGN=\"bottom\" FlashVars=\"FlashDebug=" + FlashDebug + "\"";
		attachSWFHtml += " TYPE=\"application/x-shockwave-flash\" PLUGINSPAGE=\"http://www.macromedia.com/go/getflashplayer\"></EMBED>";
		attachSWFHtml += "</OBJECT>";
		parent.PPTSld.FlashMovie.innerHTML = attachSWFHtml;
		parent.PPTSld.FlashMovie.style.zIndex = 10;
	}
}

/* add for fullscreen model start */
/* call by frame.htm */
function resizeFlashScreen(){
	try
	{
		if(parent.PPTSld.player_clip)
		{
			//判断宽度，如果显示区域宽度大于flash的录制宽度，则设置flash播放的宽度为录制宽度；否则为区域宽度
			if(parent.PPTSld.document.body.clientWidth>parseInt(xmlDoc.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashwidth")))
			{
				parent.PPTSld.player_clip.SetVariable('_root.clientWidth',parseInt(xmlDoc.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashwidth")));
			}
			else
			{
				parent.PPTSld.player_clip.SetVariable('_root.clientWidth',parent.PPTSld.document.body.clientWidth);
			}
			//判断高度，如果显示区高度度大于flash的录制高度，则设置flash播放的高度为录制高度；否则为区域高度
			if(parent.PPTSld.document.body.clientHeight>parseInt(xmlDoc.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashheight")))
			{
				parent.PPTSld.player_clip.SetVariable('_root.clientHeight',parseInt(xmlDoc.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashheight")));
			}
			else
			{
				parent.PPTSld.player_clip.SetVariable('_root.clientHeight',parent.PPTSld.document.body.clientHeight);
			}
			parent.PPTSld.player_clip.TCallLabel('_root.ScriptLib','Resize');
		}
	}
	catch(E)
	{	
	}
}
/* add for fullscreen model end */


function InitMouseTrack() {
	var attachSWFHtml;

	if (parent.PPTSld.tome) {
		attachSWFHtml="";
		attachSWFHtml+="<center>";
		attachSWFHtml+="<OBJECT id=\"myFlash\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\";";
		attachSWFHtml+=" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0\" WIDTH=100% HEIGHT=100%>";
		attachSWFHtml+=" <PARAM NAME=movie VALUE=\"../../model/MouseTrace.swf\">";
		attachSWFHtml+="<PARAM NAME=quality VALUE=best><PARAM NAME=menu VALUE=false> ";
		attachSWFHtml+="  <PARAM NAME=wmode VALUE=transparent> ";
		attachSWFHtml+="  <PARAM NAME=bgcolor VALUE=#FFFFFF> ";
		attachSWFHtml+="  <EMBED name=\"myFlash\" src=\"../../model/MouseTrace.swf\" quality=best menu=false wmode=transparent bgcolor=#FFFFFF  WIDTH=100% HEIGHT=100% TYPE=\"application/x-shockwave-flash\" PLUGINSPAGE=\"http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash\">  </EMBED>";
		attachSWFHtml+="</OBJECT></center>";
		if (parent.PPTSld.tome.innerHTML.length<30) {
			parent.PPTSld.tome.innerHTML=attachSWFHtml;
			parent.PPTSld.tome.style.zIndex=1;
		}
	}            
}


function getFlashXMLDate(){
	var tPath;
	if(parent.PPTSld.player_clip) {
		tPath=xmlDoc.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashpath");
		if (tPath!=null) {
			parent.PPTSld.player_clip.SetVariable('datePath',tPath);//设置数据文件名称/
		}

	}
}


function getCurrentPaperStartTime(){
	var StartTime;
	var MouseData;
	if (MsnGetPlayState() == mpPlaying) {
		StartTime=MsnGetPosition()*1000-xmlDoc.selectNodes("courseslides/slide/duration").item(CurSldNum-1).nodeTypedValue*1000;
	}else{
		StartTime=0;
	}

	if (parent.PPTSld.myFlash) {
		MouseData= xmlDoc.selectNodes("courseslides/slide/sequence").item(CurSldNum-1).nodeTypedValue+".xml";
		parent.PPTSld.myFlash.SetVariable('DataURL',MouseData);//设置数据文件名称/
		parent.PPTSld.myFlash.SetVariable('starttime', StartTime);
	}
}

function getStartTime(){
	var StartTime;
	var MouseData;
	if (MsnGetPlayState() == mpPlaying) {
		StartTime=MsnGetPosition()*1000-xmlDoc.selectNodes("courseslides/slide/duration").item(CurSldNum-1).nodeTypedValue*1000;
	}else{
		StartTime=0;
	}
	return StartTime;
}

function getDuration()
{
	var StartTime;
	var EndTime;
	var Duration;
	if (MsnGetPlayState() == mpPlaying) {
		StartTime=xmlDoc.selectNodes("courseslides/slide/duration").item(CurSldNum-1).nodeTypedValue;
		if(CurSldNum == xmlDoc.selectNodes("courseslides/slide/duration").length)
		{
			EndTime = Math.ceil(MsnGetDuration());
		}
		else
		{
			EndTime = xmlDoc.selectNodes("courseslides/slide/duration").item(CurSldNum).nodeTypedValue;
		}
		Duration = EndTime - StartTime;
	}else{
		Duration=10;
	}
	return Duration;
}

function getCurrentTitle(){
	return currentTitle;
}

function LoadVideo() {

	var CurPath;
	var iPos;
	var VideoPathFile;
	var VideoFileName;
	var LocalVideoFile=xmlDoc.selectSingleNode("courseslides/LocalVideo-filename").nodeTypedValue.replace(/\\/g,"/");
	var RemoteVideoFile=xmlDoc.selectSingleNode("courseslides/RemoteVideo-filename").nodeTypedValue;
	var Err;

	iPos=parent.location.href.lastIndexOf("/");
	if (parent.location.href.lastIndexOf("\\")>iPos) {
		iPos=parent.location.href.lastIndexOf("\\");
	}
	CurPath=parent.location.href.substr(0,iPos);
	VideoFileName=LocalVideoFile.substring(LocalVideoFile.lastIndexOf("/"));

	if(parent.location.protocol == "file:") {
		VideoURL = GetAbsolutePath(CurPath + "/" + LocalVideoFile);
	}else if(parent.location.hostname=="localhost"||parent.location.hostname=="127.0.0.1"){
		//add for whatyviewer
		if (videoLocalPath=="")
		{
			VideoURL = GetAbsolutePath(CurPath + "/" + LocalVideoFile);
		}else {
			videoLocalPath="file:///"+videoLocalPath.replace(/\\/g,"/");
			var coursePath=parent.location.href;
			var tstring  = coursePath.split( "/" ); 
			for(var i=3;i<tstring.length-1;i++) videoLocalPath=videoLocalPath+tstring[i]+"/";
			VideoURL = GetAbsolutePath(videoLocalPath +  LocalVideoFile);
		}
		
	}else{
		if (Trim(RemoteVideoFile) != "") {
			VideoURL = RemoteVideoFile;
		}else{
			var xModelVideoPath=xModelXml.selectSingleNode("Model/Settings/ModelVideoPath");
			if (xModelVideoPath != null && xModelVideoPath.nodeTypedValue.length > 6 && xModelVideoPath.nodeTypedValue.substring(0,6)=="mms://") {
				VideoURL = xModelXml.selectSingleNode("Model/Settings/ModelVideoPath").nodeTypedValue + "/" + VideoFileName;
				VideoURL = VideoURL.substring(0,6) + VideoURL.substring(6).replace(/\/+/g,"/");
				//alert(VideoURL);
			}else{
				VideoURL = GetAbsolutePath(CurPath + "/" + LocalVideoFile);
			}
			
		}
	}
	//VideoURL="mms://sss";
	if(VideoURL.substring(0,6)!="mms://")//不是mms视频
	{
		if(document.getElementById("imgSetBandWidth")!=null)
			document.getElementById("imgSetBandWidth").style.display="none";
	}
	else
		bBuffering = false;

	AudioURL=unescape(VideoURL.substr(0,VideoURL.length-3)+"wma");
	VideoURL=unescape(VideoURL);

	try{
		MsnSetFileName(VideoURL);
		MsnMediaPlay();
	}catch(Err){
		setTimeout("MsnSetFileName(VideoURL);",1000);
	}

	if (SlidesCount < 0) SlidesCount=xmlDoc.selectNodes("courseslides/slide").length;
} 

function GetAbsolutePath(strOrigPath){
	var iPos;
	var ret=strOrigPath;

	while ((iPos=ret.indexOf("../"))>=0){
		ret = ret.slice(0,ret.slice(0,iPos-1).lastIndexOf("/")+1) + ret.substring(iPos+3);
	}
  return ret;
}

function SetSubFilename() {  //似乎没用/
	var PaperName;
	str=parent.location.href;
	idx=str.indexOf('#');
	if(idx>=0) {
		str=str.substr(idx+1);
		var i = SlidesCount;
		do{
			i--;
			PaperName=xmlDoc.selectNodes("courseslides/slide/file").item(i).nodeTypedValue;
		}while (i>0 && PaperName.indexOf(str)<0);
		alert("SetSubFilename："+str);
		jumpURL(i);
	}
		
}

function setTimer() {
//	timerID = setTimeout( "newState()", 20000);
	window.setInterval("ChangeNewLeft()", 1000); 
//	window.setInterval("Simula()", 1000); 
                  
}

function getVolume() {
	return (MsnGetVolume()+10000)/100;
}

function setVolume(vol) {
	MsnSetVolume((vol-56)*2.2)
	//MsnSetVolume(vol*100-10000);
}

function VideoJumpTo(VideoPosSec){
	if (MsnGetPlayState()==mpStopped) {
		MsnMediaPlay();
	}
	if(MsnGetPlayState()==mpWaiting){
		setTimeout("VideoJumpTo("+VideoPosSec+")", 1000);
	}else{
		if(null == VideoPosSec || "" == VideoPosSec || "null" == VideoPosSec)
			VideoPosSec = 0;
		MsnSetPosition(VideoPosSec);
	}
}

function jumpTo(SlideNum,tSec){
	var CurrentTime;
	CurrentTime=parseFloat(tSec)+parseFloat(xmlDoc.selectNodes("courseslides/slide/duration").item(SlideNum-1).nodeTypedValue);
	currentTitle=xmlDoc.selectNodes("courseslides/slide/title").item(SlideNum-1).nodeTypedValue;
	if (MsnGetPlayState()==mpPlaying || MsnGetPlayState()==mpPaused) {
		MsnSetPosition(CurrentTime);
		if(parent.PPTSld.location.href.indexOf("WhatyYF_Slide_Video.htm?videoname")>=0)
			MsnMediaPlay();
	}
}

function jump(node){
    mysel=node-1;
	{
        jumpURL(mysel);
		return document.MM_returnValue;	
	}
}

function jumpURL(pagenum){
	try{
		if (pagenum<0){
			mysel=0;
		}else if (pagenum>=xmlDoc.selectNodes("courseslides/slide").length){
			mysel=xmlDoc.selectNodes("courseslides/slide").length-1;
		}else mysel=pagenum;

		CurSldNum=mysel+1; 
		curSec=parseInt(xmlDoc.selectNodes("courseslides/slide/duration").item(mysel).nodeTypedValue);

		if(MsnGetPlayState() == mpPlaying){
			MsnSetPosition(curSec+1);
		}else{
			if(MsnGetPlayState() != mpStopped && MsnGetPlayState()!= mpMediaEnded) MsnSetPosition(curSec+1);
			NewPaperName=xmlDoc.selectNodes("courseslides/slide/file").item(mysel).nodeTypedValue;

			if (parent.PPTSld.location.href.indexOf(NewPaperName) <0 ){
				SelectLink(mysel+1);
				parent.PPTSld.location.href=zMHTMLPrefix+NewPaperName;
				currentTitle=xmlDoc.selectNodes("courseslides/slide/title").item(mysel).nodeTypedValue;
			}
			else if(parent.PPTSld.location.href.indexOf(NewPaperName) >=0)
			{
				if(parent.PPTSld.location.href.indexOf("WhatyYF_Slide_Video.htm?videoname")>=0)
					MsnMediaPlay();
			}
		}
	}catch(err){
		if(parent.PPTSld.location.href.indexOf("WhatyYF_Slide_Video.htm?videoname")>=0)
				MsnMediaPlay();
		debug(err);
	}
	return document.MM_returnValue;
}

function startCourse(isVideo,isContinue)
{
	if(MediaPlayer.URL=="")
	{
		setTimeout("startCourse()",200);
		return;
	}
	//设置音视频版本
	if(isVideo==false)
	{
		AudioURL=unescape(VideoURL.substr(0,VideoURL.length-9)+"audio"+VideoURL.substr(VideoURL.length-4,VideoURL.length));
		try{
			MsnSetFileName(AudioURL);
		}catch(Err){
			setTimeout("MsnSetFileName(AudioURL);",1000);
		}
		//
		divVideoPhoto.style.width="200px";
		divVideoPhoto.style.height="150px";
		divVideoPhoto.style.display="block";
		MediaPlayerBox.style.display="none";
	}
	if(isContinue || multSCO)
	{
		//获取上次学习位置
		parent.loadBookMark();
	}
	//
	if(parent.initPos && Number(parent.initPos)>0)
	{
		VideoJumpTo(parent.initPos);
	}
	else if(parent.initPage && parent.initPage.length>=13)
	{
		JumpSlideAndVideo(parent.initPage);
	}
	else
	{
		MsnMediaPlay();
	}
	//拉帘儿，开幕
	parent.ControlLoadingPage.rows='0,100%';
	parent.document.getElementById("loading").src = "../../course/copyRight.html";
}

function JumpSlideAndVideo(SlideName){
	var SlideNum=GetSlideNum(SlideName)-1;
	jumpURL(SlideNum);
	MsnMediaPlay();
	//setTimeout("SelectLink("+String(SlideNum+1)+")",5000);
}

function GetSlideNum(SlideName){
	try{
		var iSlideCount=xmlDoc.selectNodes("courseslides/slide/file").length;
		for (var iCircle=0;iCircle<iSlideCount;iCircle++){
			if (xmlDoc.selectNodes("courseslides/slide/file").item(iCircle).nodeTypedValue==SlideName){
				return iCircle+1;
			}
		}
	}catch(err){}
}

function doMouseDown(Obj) {
	IsMousePressed=true;
	switch(Obj.id){
	case "divVideoPosBar":
		if(event.srcElement.id!="divVideoPosBar")	break;
		var ClickTime=(parseFloat(event.offsetX) / parseFloat(divVideoPosBar.style.width)) * MsnGetDuration();
		VideoJumpTo(ClickTime);
		SetController(parseInt(ClickTime));
		event.returnValue = false;
		event.cancelBubble = true;
		break;
	}

}

	
function doMouseMove(Obj) {
	
	switch(Obj.id) {
	case "imgVideoPosBtn":
		if (!event.button==1) return;
		//由于取不到相对坐标,PixelInLeft最后减去的那个数只能写死,其实就是divVideoPosBar的左边缘坐标.注意divVideoPosBar不能允许水平移动/
		var PixelInLeft=parseInt(event.clientX) - iDivVideoPosBarLeft - parseInt(imgVideoPosBtn.offsetWidth/2);
		debug("PixelInLeft="+PixelInLeft);
		if (PixelInLeft>=0 && parseInt(divVideoPosBar.style.width)-(PixelInLeft + imgVideoPosBtn.width) >= 0){
			imgVideoPosBtn.style.pixelLeft=PixelInLeft;
		}
		event.returnValue = false;
		event.cancelBubble = true;
		break;
	}
	
}
	
function doMouseUp(Obj) {
	if(!IsMousePressed) return;
	
	switch(Obj.id){
	case "imgVideoPosBtn":
		if (MsnGetPlayState()==mpPlaying){
			var PercentPos=parseFloat(imgVideoPosBtn.style.pixelLeft) / (parseFloat(divVideoPosBar.style.width)-parseFloat(imgVideoPosBtn.width));
			VideoJumpTo(MsnGetDuration() * PercentPos);
		}
		break;
	case "divVideoPosBar":
		break;
	}

	IsMousePressed=false;
}


function SecsToHhMmSs(sec,noSs) {
	var Hh, Mm, Ss, str;
	Hh = Math.round(sec/3600 - 0.5);
	Mm = Math.round((sec % 3600)/60 - 0.5);
	Ss = Math.round(sec % 60 - 0.5);
	if(Hh=="0")
	{
		if(noSs)
		str="00:";
		else
		str=""
	}
	else
		str = (Hh >= 10 ? Hh : "0"+Hh)+":";
	str += (Mm >= 10 ? Mm : "0"+Mm);
	if(noSs)
	return str;
	str += ":" + (Ss >= 10 ? Ss : "0"+Ss);
	return str;
}


function zGoToNextSld(){   
	jumpURL(CurSldNum);	
}
function zGoToPrevSld(){
	jumpURL(CurSldNum-2);
}


var zMHTMLPrefix = parent.zCalculateMHTMLPrefix(); 
var IsSimulaFailed = 0;

function Simula()//同步Flash/
{

	var flashMovie=parent.PPTSld.player_clip
	var mediaTime;
	var tslidebegin;
	//parent.document.title = "Debug :" + MsnGetPlayState() + " mpPlaying" + mpPlaying;
//if (MsnGetPlayState()!=mpPlaying) alert("PPP"+MsnGetPlayState());
//	var IsTryFailed = 1;

//	while(IsTryFailed){
		try{

			if(parent.PPTSld.FlashMovie && MsnGetPlayState()==mpPlaying && (!bBuffering || MsnGetBufferingProgress()==100 || parent.location.protocol == "file:")){
//			alert("FlashSync");
				tslidebegin=xmlDoc.selectNodes("courseslides/slide/duration").item(CurSldNum-1).nodeTypedValue;
				//ttestflash=xmlDoc.selectSingleNode(("courseslides/slide/file").attributes.getNamedItem("flashpath");

				mediaTime=Math.abs(MsnGetPosition()-tslidebegin-parseFloat(flashMovie.GetVariable("_root.player.nbseco")));

				if (mediaTime> 1){
					flashMovie.SetVariable("_root.ScriptLib.timeid",MsnGetPosition());
					flashMovie.TCallLabel("_root.ScriptLib","Play");

				}
			}else if (parent.PPTSld.FlashMovie && MsnGetPlayState()==mpPaused){
				flashMovie.SetVariable("_root.ScriptLib.timeid",0);
				flashMovie.TCallLabel("_root.ScriptLib","Pause");
			}else if (parent.PPTSld.FlashMovie && MsnGetPlayState()==mpStopped){
				flashMovie.SetVariable("_root.ScriptLib.timeid",0);
				flashMovie.TCallLabel("_root.ScriptLib","Pause");
			}else if (parent.PPTSld.FlashMovie && MsnGetPlayState()==10){
				flashMovie.SetVariable("_root.ScriptLib.timeid",0);
				flashMovie.TCallLabel("_root.ScriptLib","Pause");
			}			
//			IsTryFailed = 0;
//			alert("succeed!");

		}catch (err)
		{
//			mediaTime=0;
			window.setTimeout("Simula()", 200);
//			alert("settimeout");
//			IsTryFailed = 1;
		//}
	}
//	alert("out of try!");
}

//add by wangyuan 20100915 start
var thisWin = new Array();
var unableSetting = false;
function setUnable(win,page)
{
	if(win && typeof win=="object")
	{
		thisWin.push(win);
		if(unableSetting)	return;
		else	unableSetting = true;
	}
	if(thisWin.length>=1 && thisWin[0].document && thisWin[0].document.body && thisWin[0].document.title!="")
	{
		if(page && thisWin[0].document.location.href.indexOf(page)<0)
		{
			setTimeout("setUnable(null,'"+ page +"')",50);
			return;
		}
		thisWin[0].document.body.onselectstart = function(){return false};
		thisWin[0].document.body.oncontextmenu = function(){return false};
		thisWin[0].document.body.onscroll = function(){window.scroll(0)};
		thisWin[0].document.body.style.cursor="default";
		unableSetting = false;
		thisWin.splice(0,1);
		if(thisWin.length>0)
			setUnable();
	}
	else
	{
		setTimeout("setUnable()",50);
	}
}
//add by wangyuan 20100915 end

var preVisibleNode = "index";
var courseTotalDuration = null;

function GetQuizSlide()
{
	var err;
	try{
		if (SlidesCount < 0) SlidesCount = xmlDoc.selectNodes("courseslides/slide").length;
	}catch(err){return;}
	var courseslides,courseURL;
	var xmlDoc1=new ActiveXObject("MSXML.DOMDocument");
	xmlDoc1.async=false;
	iPos=parent.location.href.lastIndexOf("/");
	if (parent.location.href.lastIndexOf("\\")>iPos) {
		iPos=parent.location.href.lastIndexOf("\\");
	}
	courseURL = parent.location.href.substr(0,iPos + 1);

	var i = 0;
	var j = 0;
	for(i = 0; i < SlidesCount; i++)
	{
		PaperName=xmlDoc.selectNodes("courseslides/slide").item(i).selectSingleNode("file").nodeTypedValue;
		if(PaperName.indexOf("quiz/q") >= 0)
		{
			QuizSlides[j] = i;
			xmlPath = courseURL + PaperName;
			iPos = xmlPath.lastIndexOf("/"); 
			xmlPath = xmlPath.substr(0,iPos + 1) + "quiz.xml";
			xmlDoc1.load(xmlPath);
			QuizPassScores[j] = xmlDoc1.selectSingleNode("root/passscore").nodeTypedValue;
			j++;
		}
	}
		for(i = 0; i < QuizSlides.length; i++)
		{
			QuizScores[i] = -1;
		}
		
	isSetQuiz=true;
}

function ChangeNewLeft() {
//	debug("PlayState:"+MsnGetPlayState());
	if (MsnGetPlayState()!=mpPlaying) {
		do{
			if(MsnGetPlayState()==mpPaused)
			{
				var obj = parent.PPTSld.SlideMediaPlayer;
				if(obj!=null)
				{
					if(obj.playState==parent.PPTSld.mpStopped || obj.playState== parent.PPTSld.mpReady)
					{
						if(gCurrentPage<xmlDoc.selectNodes("courseslides/slide").length-1)
						{										
							MsnSetPosition(xmlDoc.selectNodes("courseslides/slide").item(gCurrentPage + 1).selectSingleNode("duration").nodeTypedValue);
							MsnMediaPlay();
							break;
						}
						else
						{
							MsnMediaStop();
						}
					}
				}
			}
			Simula();
			SetController(0);
			return;
		}while(false);
	}
	
	if(document.getElementById("StatusTime")!=null)
		StatusTime.innerHTML=SecsToHhMmSs(MsnGetPosition())+"/"+SecsToHhMmSs(MsnGetDuration());

	var err;
	try{
		if (SlidesCount < 0) SlidesCount = xmlDoc.selectNodes("courseslides/slide").length;
	}catch(err){return;}

	if((MsnGetDuration() - MsnGetPosition() <3) && MsnGetDuration()!= 0)
	{
		setTimeout("EndCourse()", 4000);
	}

	var NewLeft, OldPaperName, NewPaperName, Dir, CurrentPage, CurrentTime, Num;
	var CPosition,CLength;
	var CurrentPage;
	InitMouseTrack();
	InitFlashScreen();
	
	if (MsnGetPlayState()==mpPlaying){
		if(courseTotalDuration==null && MsnGetDuration()>1)
		{
			courseTotalDuration = MsnGetDuration();
		}
		CPosition=MsnGetPosition()/1000;
		CLength=MsnGetDuration()/1000;
		NewLeft=( CPosition/CLength)*(265-12)+12;

		//if (NewLeft>=12 && NewLeft<=265)
		//{btnScroll.style.pixelLeft = NewLeft;}
		//else btnScroll.style.pixelLeft = 12;

		SetController(MsnGetPosition()/MsnGetDuration()*1000);

		//Sync with the Right Frame content
		Dir = new String(location);
		Dir = Dir.substring(0, Dir.length - 15);
		CurrentTime=MsnGetPosition();
		//alert(CurrentPaper);
		//alert(xmlDoc.documentElement.childNodes.item(5+myNum+1).selectSingleNode("duration").nodeTypedValue);



		if (SlidesCount == 1 || (CurrentTime >= 0 && CurrentTime < xmlDoc.selectNodes("courseslides/slide").item(1).selectSingleNode("duration").nodeTypedValue)){
			CurrentPage = 0;
		}
		
		else if(CurrentTime >= 0 && CurrentTime >= xmlDoc.selectNodes("courseslides/slide").item(SlidesCount - 1).selectSingleNode("duration").nodeTypedValue){
			CurrentPage = SlidesCount - 1;
		}else{
			CurrentPage = 1;
			while (!(CurrentTime >= xmlDoc.selectNodes("courseslides/slide").item(CurrentPage).selectSingleNode("duration").nodeTypedValue &&
					CurrentTime < xmlDoc.selectNodes("courseslides/slide").item(CurrentPage + 1).selectSingleNode("duration").nodeTypedValue)){
				if (CurrentPage >= SlidesCount - 1) break;
				CurrentPage = CurrentPage + 1;
			}
		}
		
		var thisURL = xmlDoc.selectNodes("courseslides/slide/file").item(CurrentPage).nodeTypedValue;
		if(thisURL.indexOf("WhatyYF_Slide_Video.htm?videoname")>=0)//是一个视频页面
		{
			MsnMediaPause();
			MsnSetPosition(xmlDoc.selectNodes("courseslides/slide/duration").item(CurrentPage).nodeTypedValue );
			gCurrentPage=CurrentPage;
		}
		
		var IsShowAlert = 0;
		if (xmlDoc.selectNodes("courseslides/slide").item(CurrentPage).selectSingleNode("file").getAttribute("flashpath") != null){
			Simula();
			IsShowAlert=1;
		}
		
		//quiz test
		if(isSetQuiz == false){
			GetQuizSlide();
			isSetQuiz = true;
		}
		
		if(isInitQuiz == false)
		{
			quizAry = parent.getQuiz();
			if(quizAry.length == 0)
			{
				quizAry = quizString;
			}
			else
			{
				quizString = quizAry;
			}
		
			isInitQuiz = true;
		}
	
		for(var l = 0; l < quizString.length; l++)
		{
			QuizScores[l] = quizString[l].value;
		}

		var i;
	//	alert(QuizSlides.length+", "+CurrentPage);
		for(i = 0; i < QuizSlides.length; i++)
		{
			if(CurrentPage == SlidesCount-1)
			{
				if(CurrentPage < QuizSlides[i]) break;
			}
			else
			{
				if(CurrentPage < QuizSlides[i]+1) break;
			}
		//	alert(QuizScores[i] + ", "+ QuizPassScores[i]);
			if(QuizScores[i] < QuizPassScores[i])
			{
				var j,k=0;
				for(j=0; j<quizString.length; j++)
				{
					if(quizString[j].value >= QuizPassScores[j])
					{
						k=1;
						quizString[j].status = "completed";
					}
				}
				if(k == 1)
				{
					parent.setQuiz(quizString);
					k = 0;
				}
				MediaPlayer.CurrentPosition=xmlDoc.selectNodes("courseslides/slide/duration").item(QuizSlides[i]).nodeTypedValue + 1;
				CurrentPage = GetSlideByTime(xmlDoc.selectNodes("courseslides/slide/duration").item(QuizSlides[i]).nodeTypedValue + 1);
				//if(CurrentPage == QuizSlides[i])
				{
					var thisURL = xmlDoc.selectNodes("courseslides/slide/file").item(QuizSlides[i]).nodeTypedValue;
					if(thisURL.indexOf("quiz/")>=0)
					{
						parent.curQuizID = thisURL.split("/")[1];
						//alert(parent.curQuizID)
						//debugger;
					}
				}
				MsnMediaPause();
				alert("测验提示：所选内容之前存在未完成的测试，请通过测试后再继续学习。");
				break;
				
			}
			else
			{
				var j,k=0;
				for(j=0; j<quizString.length; j++)
				{
					if(quizString[j].value >= QuizPassScores[j])
					{
						if(quizString[j].status != "completed")
						{
							k=1;
							quizString[j].status = "completed";
						}
					}
				}
				if(k == 1)
				{
					alert(quizString);
					parent.setQuiz(quizString);
					k = 0;
				}

				if(quizString.length == QuizPassScores.length)
				{
					var ii=0;
					for(; ii<quizString.length; ii++)
					{
						if(quizString[ii].status != "completed") 
							break;
					}
					if(ii == quizString.length)
					{
						parent.finishSco=true;
						parent.closeSco();
					}
				}
			}
		}
		
		CurSldNum = CurrentPage + 1;
		
		NewPaperName=xmlDoc.selectNodes("courseslides/slide").item(CurrentPage).selectSingleNode("file").nodeTypedValue;
		thisNewPaperName = NewPaperName;
		var l1_N=l2_N=l3_N=l4_N=l5_N=p_N=0;
		var sections = xmlDoc.selectNodes("courseslides/slide");
		var thisVisibleNode = getVisibleNode(CurSldNum);
		var nextVisibleNode = getNextVisibleNode(thisVisibleNode);

		if (parent.PPTSld.location.href.indexOf(NewPaperName) <0 || parent.PPTSld.location.href.indexOf("WhatyYF_Slide_Video.htm?videoname")>=0)
		{
			SelectLink(CurrentPage+1);
			if((parent.multSCO || !(parent.completedRule=="duration")) && thisVisibleNode != preVisibleNode)
			{
				if(preVisibleNode!="index")
				{
					parent.closeSco();
				}
				else
				{
					initSCO(sections);
				}
				//
				preVisibleNode = thisVisibleNode;
				//
				var thisVisibleNodeFile = xmlDoc.selectNodes("courseslides/slide/file").item(thisVisibleNode-1).nodeTypedValue;
				var thisVisibleSCOID = getScoreFolder(zMHTMLPrefix) + "_" +  thisVisibleNodeFile;
				//MM_swapImages();
				parent.enterSco(thisVisibleSCOID);
			}

			parent.PPTSld.location.href=zMHTMLPrefix+NewPaperName;
			currentTitle=xmlDoc.selectNodes("courseslides/slide/title").item(CurrentPage).nodeTypedValue;
			//
		};
		var FlashTimeLine, CurrentFlashPage, CFlashLength, CurrentPageTime;
		FlashTimeLine=xmlDoc.selectNodes("courseslides/slide").item(CurrentPage).selectSingleNode("TimeLine");
		CurrentFlashPage=0;
		CFlashLength=0;		
		if(FlashTimeLine){
			CurrentPageTime=xmlDoc.selectNodes("courseslides/slide").item(CurrentPage).selectSingleNode("duration").nodeTypedValue
			CFlashLength=FlashTimeLine.selectNodes("item").length
			if(CFlashLength>1){
				if(CurrentTime>=(Number(FlashTimeLine.selectNodes("item").item(0).getAttribute("time"))+CurrentPageTime) && CurrentTime<(Number(FlashTimeLine.selectNodes("item").item(CFlashLength-1).getAttribute("time"))+CurrentPageTime)){
					while(!(CurrentTime>=(Number(FlashTimeLine.selectNodes("item").item(CurrentFlashPage).getAttribute("time"))+CurrentPageTime) && CurrentTime<(Number(FlashTimeLine.selectNodes("item").item(CurrentFlashPage+1).getAttribute("time"))+CurrentPageTime))){						
						if(CurrentFlashPage>=CFlashLength-1) break;
						CurrentFlashPage=CurrentFlashPage+1;
					}
				}
				if(CurrentTime>=Number(FlashTimeLine.selectNodes("item").item(CFlashLength-1).getAttribute("time"))+CurrentPageTime ){
					CurrentFlashPage=CFlashLength-1;
				}
				SelectLinkFlash(CurrentPage+1,CurrentFlashPage+1);
				if(LastFlashPage!=CurrentFlashPage || LastPage!=CurrentPage)
				{
					SetFlashScorm(CurrentPage, CurrentFlashPage);
				}
			}
			if(CFlashLength==1){
				if(LastFlashPage!=CurrentFlashPage || LastPage!=CurrentPage)
				{					
					SetFlashScorm(CurrentPage, CurrentFlashPage);
				}
				SelectLinkFlash(CurrentPage+1,CurrentFlashPage+1);
			}
		}	
		
//		if(SlidesCount==0){
//			if(CFlashLength==0){
//			}
//			else{
//				if(CFlashLength==CurrentFlashPage+1){
//					parent.finishSco = true;
////					parent.learned();
//				}
//			}
//		}
//		else{
//			if(CFlashLength==0){
//				if(SlidesCount==CurrentPage+1){
//					parent.finishSco = true;
////					parent.learned();
//				}
//			}
//			else{
//				if(CFlashLength+SlidesCount==CurrentPage+CurrentFlashPage+2){
//	//				alert("both");
//					parent.finishSco = true;
//	//				parent.learned();
//				}
//			}
//		}		
		
	}
	//安比例调节MouseTrack的大小/
	var iTomeWidth;
	var iTomeHeight;
	var iTomeTop;
	var iTomeLeft;
	if (parent.PPTSld.tome != null) {
		if (parseInt(parent.PPTSld.document.body.clientHeight) * 4 / 3 < parseInt(parent.PPTSld.document.body.clientWidth)) {
			//太宽/
			iTomeWidth=parseInt(parent.PPTSld.document.body.clientHeight) * 4 / 3 - 4;
			iTomeHeight=parseInt(parent.PPTSld.document.body.clientHeight) - 4;
			iTomeTop=2;
			iTomeLeft=parseInt((parseInt(parent.PPTSld.document.body.clientWidth)-iTomeWidth)/2);
		}else{
			//太高/
			iTomeWidth=parseInt(parent.PPTSld.document.body.clientWidth)-4;
			iTomeHeight=parseInt(parent.PPTSld.document.body.clientWidth) * 3 / 4 - 4;
			iTomeTop=parseInt((parseInt(parent.PPTSld.document.body.clientHeight)-iTomeHeight)/2);
			iTomeLeft=2;
		}

		//alert(iTomeWidth+ ", " +iTomeHeight);
		if (iTomeWidth > 0 && iTomeHeight > 0 && iTomeLeft > 0 && iTomeTop > 0){
			parent.PPTSld.tome.style.width=iTomeWidth + "px";
			parent.PPTSld.tome.style.height=iTomeHeight + "px";
			parent.PPTSld.tome.style.left=iTomeLeft + "px";
			parent.PPTSld.tome.style.top=iTomeTop + "px";
		}
		
	}
	
}//end of ChangeNewLeft()

function SetFlashScorm(slideId, flashId)
{
	if(parent.multSCO)
	{
		parent.unloadPage('completed');
		parent.enterSco(getScoreFolder(zMHTMLPrefix) + "_" +  slideId + "_" + flashId);
		parent.loadPage();
		LastFlashPage=flashId;
		LastPage=slideId;
	}
}

function getScoreFolder(strFullPath)
{
	var strTemp;
	strTemp = strFullPath.substring(0, strFullPath.lastIndexOf("/"));
	strTemp = strTemp.substring(0, strTemp.lastIndexOf("/"));
	strTemp = strTemp.substring(strTemp.lastIndexOf("/") + 1, strTemp.length);
	return strTemp;
}

function format2Sec(str)
{
	var sec=0;
	if(str.indexOf(":")>=0)
	{
		abc = str.split(":");
		sec = Number(abc[0])*3600+Number(abc[1])*60+Number(abc[2]);
	}
	return sec;
}

function getContentDur(thisVisibleNode,nextVisibleNode)
{
	if(nextVisibleNode==false)
	{
		var contentDur = Number(courseTotalDuration==null?0:courseTotalDuration) - Number(xmlDoc.selectNodes("courseslides/slide").item(thisVisibleNode-1).selectSingleNode("duration").nodeTypedValue);
		return contentDur;
	}
	else
	{
		var contentDur = Number(xmlDoc.selectNodes("courseslides/slide").item(nextVisibleNode-1).selectSingleNode("duration").nodeTypedValue) - Number(xmlDoc.selectNodes("courseslides/slide").item(thisVisibleNode-1).selectSingleNode("duration").nodeTypedValue);
		return contentDur;
	}
}


function Init(){
	
		try{
	currentTitle=xmlDoc.selectSingleNode("courseslides/slide/title").nodeTypedValue;
	//初始化Frame/
	document.getElementsByName("PPTSld").src=xmlDoc.selectNodes("courseslides/slide/file").item(0).nodeTypedValue;
	document.getElementsByName("PPTNts").src=xmlDoc.selectNodes("courseslides/slide/file").item(xmlDoc.selectNodes("courseslides/slide").length-1).nodeTypedValue;

	}catch(Err){
		setTimeout("Init();",200);
		return;
	}
	
	//读model.xml/
	xModelXml.async=false;
	xModelXml.load("model.xml");


	var xTitle=xmlDoc.selectSingleNode("courseslides/coursetitle");
	iDivVideoPosBarLeft=GetAbsoluteLeft(divVideoPosBar);

	InitScormConfig();
	setTimer();
	PicView.innerHTML=generatePicView();
	if (PicView.innerHTML=="") {
		imgChangeView.style.visibility="hidden";
	}
	
	//====AudioOnlyMenu/
	//var IsShowAudioOnlyMenu=false;
	IsShowAudioOnlyMenu=false;
	if (xmlDoc.selectSingleNode("courseslides/CourseSettings/ShowAudioOnlyMenu")) {
		IsShowAudioOnlyMenu=(xmlDoc.selectSingleNode("courseslides/CourseSettings/ShowAudioOnlyMenu").nodeTypedValue.toLowerCase()=="true");
	}else{
	/*
		if (xModelXml.selectSingleNode("Model/Settings/ModelIsShowAudioOnlyMenu")) {
			IsShowAudioOnlyMenu=(xModelXml.selectSingleNode("Model/Settings/ModelIsShowAudioOnlyMenu").nodeTypedValue.toLowerCase()=="true");
		}
	*/
		ShowAudioOnlyMenu=false;
	}
	if (!IsShowAudioOnlyMenu) {
		tdAudioOnlyMenu.style.display="none";
	}

	//====ColorStyleMenu/
	var IsShowColorStyleMenu;
	if (xmlDoc.selectSingleNode("courseslides/CourseSettings/ShowColorStyleMenu")) {
		IsShowColorStyleMenu=(xmlDoc.selectSingleNode("courseslides/CourseSettings/ShowColorStyleMenu").nodeTypedValue.toLowerCase()=="true");
	}else{
		if (xModelXml.selectSingleNode("Model/Settings/ModelIsShowColorStyleMenu")) {
			IsShowColorStyleMenu=(xModelXml.selectSingleNode("Model/Settings/ModelIsShowColorStyleMenu").nodeTypedValue.toLowerCase()=="true");
		}
	}
	if (!IsShowColorStyleMenu) {
		tdColorStyleMenu.style.display="none";
	}

	//====InitLeftSize/
	if (xmlDoc.selectSingleNode("courseslides/CourseSettings/InitLeftSize")) {
		InitLeftSize=xmlDoc.selectSingleNode("courseslides/CourseSettings/InitLeftSize").nodeTypedValue;
	}else{
		if (xModelXml.selectSingleNode("Model/Settings/ModelInitLeftSize") && Trim(xModelXml.selectSingleNode("Model/Settings/ModelInitLeftSize").nodeTypedValue) != "") {
			InitLeftSize=xModelXml.selectSingleNode("Model/Settings/ModelInitLeftSize").nodeTypedValue;
		}
	}

	//====RightDown/
	var InitIsShowRightDown=true;
	if (xmlDoc.selectSingleNode("courseslides/CourseSettings/InitIsShowRightDown")) {
		InitIsShowRightDown=xmlDoc.selectSingleNode("courseslides/CourseSettings/InitIsShowRightDown").nodeTypedValue;
	}else{
		if (xModelXml.selectSingleNode("Model/Settings/ModelInitIsShowRightDown")) {
			InitIsShowRightDown=(xModelXml.selectSingleNode("Model/Settings/ModelInitIsShowRightDown").nodeTypedValue.toLowerCase()=="true");
		}
	}

	//====IsShowTrademark/
	if (xModelXml.selectSingleNode("Model/Settings/ModelIsShowTrademark")) {
		IsShowTrademark=(xModelXml.selectSingleNode("Model/Settings/ModelIsShowTrademark").nodeTypedValue.toLowerCase()=="true");
	}

	InitColorStyleMenu();
	InitColorStyle();
	InitBandSettingMenu();

	parent.InitFrmSet(InitLeftSize,InitIsShowRightDown);
	InitVideoPhoto();
	LoadVideo();
	
	if(xmlDoc.selectSingleNode("courseslides").getAttribute("model")=="tree"){
		SlideTree.innerHTML=generateTree();
	}else{
		SlideTree.innerHTML=generateFlatList();
		imgCollapse.style.visibility="hidden";
		imgExpand.style.visibility="hidden";
	}

	divResize();
	SelectLink(1);
	ExpandTree();

}

function InitVideoPhoto(){
	var PhotoFile="";
	var CurPath="";

	CurPath=parent.location.href.substr(0, parent.location.href.lastIndexOf("/"));
	if (xmlDoc.selectSingleNode("courseslides").getAttribute("photo") && Trim(xmlDoc.selectSingleNode("courseslides").getAttribute("photo")).length > 0){
		PhotoFile=Trim(xmlDoc.selectSingleNode("courseslides").getAttribute("photo"));
		PhotoFile=CurPath + "/" + PhotoFile;
	}else{
//		PhotoFile="photo.jpg";
		if (Trim(xModelXml.selectSingleNode("Model/Settings/ModelPhotoFile").nodeTypedValue).length>0) {
			PhotoFile="" + xModelXml.selectSingleNode("Model/Settings/ModelPhotoFile").nodeTypedValue;
		}
	}

	if (PhotoFile.length>0) {
		var err;
		try{
			objswfVideoPhoto.SetVariable("_root.ScriptLib.timeid",PhotoFile);
			objswfVideoPhoto.TCallLabel("_root.ScriptLib","loadPhoto");
		}catch(err){
			//return;
		}
	}
}

function generatePicView()
{	
	var returnResult="";
	var i,j;
	var PicDoc=xmlDoc.selectNodes("courseslides/picview/item");
	
	if (PicDoc.length<=0) {
//		imgChangeView.style.display="none";
		returnResult = "";
	}else{

		returnResult += "<table align=\"center\">";
		for (i=0;i<PicDoc.length;i++) {
			citem=PicDoc.item(i);		
			if((i%2)==0) returnResult+="<tr>";
//			returnResult+="<td><a href=\"#\" target=\"_self\" onclick=\"VideoJumpTo("+citem.getAttribute('time')+");return false;\" ><img src=\"pic/"+citem.getAttribute('pic')+"\" width=\"40\" height=\"30\" border=\"0\" title=\""+citem.nodeTypedValue+"  "+SecsToHhMmSs(citem.getAttribute('time'))+"\" ondrag=\"return false;\" ></a></td>";
			returnResult+='<td><img style="cursor:hand" src="'+ courseURL +'/pic/'+citem.getAttribute('pic')+'" width="80" height="60" border="0" onclick="VideoJumpTo('+citem.getAttribute('time')+');" title="'+citem.nodeTypedValue+'  '+SecsToHhMmSs(citem.getAttribute('time'))+'" ondrag="return false;" ></td>';
			if((i%2)==1) returnResult+="</tr>";
		}
		returnResult += "</table>";
	}
	
	return returnResult;
}

function generateFlatList(){
	var returnResult="";
	var i,j;
	var citem,cTimeLine;
	var SlideDoc=xmlDoc.selectNodes("courseslides/slide");
	var iTimeLineAbsoTime=0;
	
	returnResult += '<TABLE cellSpacing="0" cellPadding="0" width="100%" border="0"><TBODY>\n';

	for (i=0; i<SlideDoc.length; i++){
		citem=SlideDoc.item(i);
		
		if (citem.selectSingleNode("hidden")){
			if (citem.selectSingleNode("hidden").nodeTypedValue=="False"){
				returnResult += '  <TR height="18" class="link" onClick="jump(' + (i+parseInt(1)) + ');return false;" title="' + SecsToHhMmSs(citem.selectSingleNode("duration").nodeTypedValue) + '">\n';
				returnResult += '    <TD align="center" width="20">' + getNodeICON(i) + '</TD>\n';
				returnResult += '    <TD class="ListDefault" id="ListItem_' + (i+1) + '" onMouseOver="if (this.className != \'ListHighLight\') this.className=\'ListMouseOver\';" onMouseOut="if (this.className != \'ListHighLight\') this.className=\'ListDefault\';">' + citem.selectSingleNode("title").nodeTypedValue + '</TD>\n';
				returnResult += '    <TD class="ListDefault" align="center" width="42">' + SecsToHhMmSs(citem.selectSingleNode("duration").nodeTypedValue) + '</TD>\n';
				returnResult += '  </TR>\n';
		
				if((citem.selectSingleNode('TimeLine') != null) && (citem.selectSingleNode('TimeLine').hasChildNodes())){
		
					cTimeLine=citem.selectNodes("TimeLine/item");			
					for (j=0; j<cTimeLine.length; j++) {
						iTimeLineAbsoTime=parseInt(cTimeLine.item(j).getAttribute('time')) + parseInt(citem.selectSingleNode("duration").nodeTypedValue);
						returnResult += '  <TR height="18" class="link" onClick="VideoJumpTo(' + iTimeLineAbsoTime + ');return false;" title="' + SecsToHhMmSs(iTimeLineAbsoTime) + '">\n';
						returnResult += '    <TD align="center" width="20">' + getNodeICON(i) + '</TD>\n';
						returnResult += '    <TD class="ListDefault" id="ListItem_' + (i+1) + '_' + (j+1) + '" onMouseOver="if (this.className != \'ListHighLight\') this.className=\'ListMouseOver\';" onMouseOut="if (this.className != \'ListHighLight\') this.className=\'ListDefault\';">' + cTimeLine.item(j).nodeTypedValue + '</TD>\n';
						returnResult += '    <TD class="ListDefault" align="center" width="42">' + SecsToHhMmSs(iTimeLineAbsoTime) + '</TD>\n';
						returnResult += '  </TR>\n';
					}
				}
			}
		}
		else{
			returnResult += '  <TR height="18" class="link" onClick="jump(' + (i+parseInt(1)) + ');return false;" title="' + SecsToHhMmSs(citem.selectSingleNode("duration").nodeTypedValue) + '">\n';
				returnResult += '    <TD align="center" width="20">' + getNodeICON(i) + '</TD>\n';
				returnResult += '    <TD class="ListDefault" id="ListItem_' + (i+1) + '" onMouseOver="if (this.className != \'ListHighLight\') this.className=\'ListMouseOver\';" onMouseOut="if (this.className != \'ListHighLight\') this.className=\'ListDefault\';">' + citem.selectSingleNode("title").nodeTypedValue + '</TD>\n';
				returnResult += '    <TD class="ListDefault" align="center" width="42">' + SecsToHhMmSs(citem.selectSingleNode("duration").nodeTypedValue) + '</TD>\n';
				returnResult += '  </TR>\n';
		
				if((citem.selectSingleNode('TimeLine') != null) && (citem.selectSingleNode('TimeLine').hasChildNodes())){
		
					cTimeLine=citem.selectNodes("TimeLine/item");			
					for (j=0; j<cTimeLine.length; j++) {
						iTimeLineAbsoTime=parseInt(cTimeLine.item(j).getAttribute('time')) + parseInt(citem.selectSingleNode("duration").nodeTypedValue);
						returnResult += '  <TR height="18" class="link" onClick="VideoJumpTo(' + iTimeLineAbsoTime + ');return false;" title="' + SecsToHhMmSs(iTimeLineAbsoTime) + '">\n';
						returnResult += '    <TD align="center" width="20">' + getNodeICON(i) + '</TD>\n';
						returnResult += '    <TD class="ListDefault" id="ListItem_' + (i+1) + '_' + (j+1) + '" onMouseOver="if (this.className != \'ListHighLight\') this.className=\'ListMouseOver\';" onMouseOut="if (this.className != \'ListHighLight\') this.className=\'ListDefault\';">' + cTimeLine.item(j).nodeTypedValue + '</TD>\n';
						returnResult += '    <TD class="ListDefault" align="center" width="42">' + SecsToHhMmSs(iTimeLineAbsoTime) + '</TD>\n';
						returnResult += '  </TR>\n';
					}
				}	
		}
		

	}
	returnResult += '</TBODY></TABLE>\n';
	return returnResult;
}

function generateTree() {
	var returnResult="";
	var i,j;
	var citem,cTimeLine;
	var SlideDoc=xmlDoc.selectNodes("courseslides/slide");
	var DivNum=0,n;
	
	
	for (i=0;i<SlideDoc.length;i++){
	
		citem=SlideDoc.item(i);
		
		if (citem.selectSingleNode("hidden")){
			if (citem.selectSingleNode("hidden").nodeTypedValue=="False"){

				if (i>0) {
					for (n= getCurrentLevel(citem);n<DivNum;n++){
						returnResult+="</div>";
					}
					DivNum=getCurrentLevel(citem);
				}
		
				if (hasInnerTree(citem)) {
					returnResult += '<span ID="TreeMotherNode_' + (i+1) + '" onClick="Toggle(this);return false;">'+getCloseICON(i+1)+'</span>\n';
					returnResult += '<span class="ListDefault" onclick="jump('+(i+1)+'); return false;" title="' + SecsToHhMmSs(citem.selectSingleNode("duration").nodeTypedValue)+'" ID="ListItem_' + (i+1) + '" onMouseOver="if (this.className != \'ListHighLight\') this.className=\'ListMouseOver\';" onMouseOut="if (this.className != \'ListHighLight\') this.className=\'ListDefault\';">'+citem.selectSingleNode("title").nodeTypedValue+'</span><BR />\n';
						
					returnResult += '<div ID="divTreeChildNodes_' + (i+1) + '" style="display:none; margin-left:2em;">\n';
					DivNum+=1;
		
					if(citem.selectSingleNode('TimeLine') && citem.selectSingleNode('TimeLine').hasChildNodes()){
					
						cTimeLine=citem.selectNodes("TimeLine/item");
		
						for (j=0;j<cTimeLine.length;j++) {
							returnResult+='<span target="_self" class="ListDefault" id="ListItem_' + (i+1) + '_' + (j+1) + '" onClick="SelectLink(\'' + (i+1) + '_' + (j+1) + '\'); jumpTo(' + (i+1) + ','+cTimeLine.item(j).getAttribute("time")+'); return false;" title="'+SecsToHhMmSs(parseFloat(cTimeLine.item(j).getAttribute("time")) + parseFloat(cTimeLine.item(j).parentNode.parentNode.selectSingleNode("duration").nodeTypedValue))+'" onMouseOver="if (this.className != \'ListHighLight\') this.className=\'ListMouseOver\';" onMouseOut="if (this.className != \'ListHighLight\') this.className=\'ListDefault\';">' + getNodeICON(0) + cTimeLine.item(j).nodeTypedValue + '</span><BR/>\n';
						}
					}
			
				
				}else{
					returnResult+='<span class="ListDefault" onClick="jump(' + (i+1) + '); return false;" title="' + SecsToHhMmSs(citem.selectSingleNode("duration").nodeTypedValue) + '" ID="ListItem_' + (i+1) + '" onMouseOver="if (this.className != \'ListHighLight\') this.className=\'ListMouseOver\';" onMouseOut="if (this.className != \'ListHighLight\') this.className=\'ListDefault\';">'+getNodeICON(i+1)+citem.selectSingleNode("title").nodeTypedValue+'</span><BR/>';
				}
			}
		}else{
			if (i>0) {
				for (n= getCurrentLevel(citem);n<DivNum;n++){
					returnResult+="</div>";
				}
				DivNum=getCurrentLevel(citem);
			}
	
			if (hasInnerTree(citem)) {
				returnResult += '<span ID="TreeMotherNode_' + (i+1) + '" onClick="Toggle(this);return false;">'+getCloseICON(i+1)+'</span>\n';
				returnResult += '<span class="ListDefault" onclick="jump('+(i+1)+'); return false;" title="' + SecsToHhMmSs(citem.selectSingleNode("duration").nodeTypedValue)+'" ID="ListItem_' + (i+1) + '" onMouseOver="if (this.className != \'ListHighLight\') this.className=\'ListMouseOver\';" onMouseOut="if (this.className != \'ListHighLight\') this.className=\'ListDefault\';">'+citem.selectSingleNode("title").nodeTypedValue+'</span><BR />\n';
					
				returnResult += '<div ID="divTreeChildNodes_' + (i+1) + '" style="display:none; margin-left:2em;">\n';
				DivNum+=1;
	
				if(citem.selectSingleNode('TimeLine') && citem.selectSingleNode('TimeLine').hasChildNodes()){
				
					cTimeLine=citem.selectNodes("TimeLine/item");
	
					for (j=0;j<cTimeLine.length;j++) {
						returnResult+='<span target="_self" class="ListDefault" id="ListItem_' + (i+1) + '_' + (j+1) + '" onClick="SelectLink(\'' + (i+1) + '_' + (j+1) + '\'); jumpTo(' + (i+1) + ','+cTimeLine.item(j).getAttribute("time")+'); return false;" title="'+SecsToHhMmSs(parseFloat(cTimeLine.item(j).getAttribute("time")) + parseFloat(cTimeLine.item(j).parentNode.parentNode.selectSingleNode("duration").nodeTypedValue))+'" onMouseOver="if (this.className != \'ListHighLight\') this.className=\'ListMouseOver\';" onMouseOut="if (this.className != \'ListHighLight\') this.className=\'ListDefault\';">' + getNodeICON(0) + cTimeLine.item(j).nodeTypedValue + '</span><BR/>\n';
					}
				}
				
			}else{
				returnResult+='<span class="ListDefault" onClick="jump(' + (i+1) + '); return false;" title="' + SecsToHhMmSs(citem.selectSingleNode("duration").nodeTypedValue) + '" ID="ListItem_' + (i+1) + '" onMouseOver="if (this.className != \'ListHighLight\') this.className=\'ListMouseOver\';" onMouseOut="if (this.className != \'ListHighLight\') this.className=\'ListDefault\';">'+getNodeICON(i+1)+citem.selectSingleNode("title").nodeTypedValue+'</span><BR/>';
			}
		}
	}
	
	for (n= 0;n<DivNum;n++){
		returnResult+="</div>";
	}
	//alert(returnResult);
	return returnResult;
}

var openICONHTML="";

var nodeICONHTML="";
function getOpenICON(thisNodeNum){
	if(openICONHTML=="")
	{
		var openICON="";
		var SrcFile = (CurColorStylePath.length==0 ? "aboutblank.gif" : "" + CurColorStylePath + "/icon/open.gif");
		openICONHTML='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="open.gif" src="' + SrcFile + '" border="0" ondrag="return false;">';
		
		if (thisNodeNum>0) {
			if (xmlDoc.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("openICON")){
				openICON = xmlDoc.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("openICON");
				
			}else if (xmlDoc.selectSingleNode("courseslides").getAttribute("openICON")){
					openICON = xmlDoc.selectSingleNode("courseslides").getAttribute("openICON");
			}
		}
		
		if (openICON.indexOf(".gif")>0 || openICON.indexOf(".jpg")>0){
			openICONHTML='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="' + openICON + '" src="' + SrcFile + '" border="0" ondrag="return false;">';
		}
	}
	return openICONHTML;
}

var closeICONHTML="";
function getCloseICON(thisNodeNum){
	if(closeICONHTML=="")
	{
		var closeICON="";
		var SrcFile = (CurColorStylePath.length==0 ? "aboutblank.gif" : "" + CurColorStylePath + "/icon/close.gif");
		closeICONHTML='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="close.gif" src="' + SrcFile + '" border="0" ondrag="return false;">';
	
		if (thisNodeNum>0) {
			if (xmlDoc.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("closeICON")){
				closeICON = xmlDoc.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("closeICON");
			}else if (xmlDoc.selectSingleNode("courseslides").getAttribute("closeICON")){
				closeICON = xmlDoc.selectSingleNode("courseslides").getAttribute("closeICON");
			}
		}
		
		if (closeICON.indexOf(".gif")>0 || closeICON.indexOf(".jpg")>0){
			closeICONHTML='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="' + closeICON + '" src="' + SrcFile + '" border="0" ondrag="return false;">';
		}
	}
	return closeICONHTML;

}

function getNodeICON(thisNodeNum){
	if(nodeICONHTML=="")
	{
		var nodeICON="";
		var SrcFile = (CurColorStylePath.length==0 ? "aboutblank.gif" : "" + CurColorStylePath + "/icon/node.gif");
		nodeICONHTML='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="node.gif" src="' + SrcFile + '" border="0" ondrag="return false;">';
		
		if (thisNodeNum>0) {
			if (xmlDoc.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("nodeICON")){
				nodeICON = xmlDoc.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("nodeICON");
				
			}else if (xmlDoc.selectSingleNode("courseslides").getAttribute("nodeICON")){
				nodeICON = xmlDoc.selectSingleNode("courseslides").getAttribute("nodeICON");
			}
		}
		
		if (nodeICON.indexOf(".gif")>0 || nodeICON.indexOf(".jpg")>0){
			nodeICONHTML='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="' + nodeICON + '" src="' + SrcFile + '" border="0" ondrag="return false;">';
		}
	}
	return nodeICONHTML;
}
	 
function getCurrentLevel(thisNode){
	var thisTreeLevel=0;
	if (thisNode.getAttributeNode('index')!=null){
		thisTreeLevel=parseInt(thisNode.getAttribute('index'));
	}
	return thisTreeLevel;
} 

function isCloseDIV(thisNode){
	var thisTreeLevel=0,preTreeLevel=0;

	if (thisNode.getAttributeNode('index')!=null){
		thisTreeLevel=parseInt(thisNode.getAttribute('index'));
	}
	if (thisNode.previousSibling!=null){
		if (thisNode.previousSibling.getAttributeNode('index')!=null){
			preTreeLevel = parseInt(thisNode.previousSibling.getAttribute('index'));
		}
	}

	alert("preTreeLevel:"+preTreeLevel+"thisTreeLevel:"+thisTreeLevel);
	if (preTreeLevel>thisTreeLevel){
		return 1;
	}else if (preTreeLevel==thisTreeLevel){
		if (hasInnerTree(thisNode.previousSibling)){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
}


function hasInnerTree(thisNode){
	var thisTreeLevel=0,nextTreeLevel=0;
	if (thisNode.selectSingleNode('TimeLine')){
		if(thisNode.selectSingleNode('TimeLine').hasChildNodes()){
			return true;
		}
	}
	if (thisNode.getAttributeNode('index')!=null){
		thisTreeLevel=parseInt(thisNode.getAttribute('index'));
	}
		
	if (thisNode.nextSibling!=null){
		if (thisNode.nextSibling.getAttributeNode('index')!=null){
			nextTreeLevel = parseInt(thisNode.nextSibling.getAttribute('index'));
		}
	}

	if (nextTreeLevel>thisTreeLevel) {
		return true;
	}else{
		return false;
	}

}

function SetController(sec){
	if (IsMousePressed) return;
	if (MsnGetPlayState()==mpPlaying||MsnGetPlayState()==mpPaused||MsnGetPlayState()==mpWaiting){
		var PercentPos=parseFloat(MsnGetPosition() * 100 / MsnGetDuration());
		var AbsoPos = parseInt((parseInt(divVideoPosBar.style.width)-parseInt(imgVideoPosBtn.width)) * (PercentPos / 100) + 0.5);
		imgVideoPosBtn.style.pixelLeft=AbsoPos;
	}else {
		imgVideoPosBtn.style.pixelLeft = 0;
	}
	debug("Pecent="+PercentPos+" Width="+(parseInt(divVideoPosBar.style.width)-parseInt(imgVideoPosBtn.width))+" left="+imgVideoPosBtn.style.pixelLeft);
}

function debug(DebugString){

	if (!IsDebugging || !parent.fraTop.divDebug) return;
	parent.fraTop.divDebug.innerHTML=DebugString;
} 

function selectVideoOrAudio(selectIndex){return;}

function doControlClick(Obj){
	
	switch (Obj.id){
	case "imgFB":
		if (MsnCanScan()) {
			MsnFastReverse();
		}else{
			zGoToPrevSld();
		}
		break;
	case "imgPlay":
		if (MsnGetPlayState() != mpPlaying) {
			MsnMediaPlay();
		}
		break;
	case "imgFF":
		if (MsnCanScan()) {
			MsnFastForward();
		}else{ 
			zGoToNextSld();
		}
		break;
	case "imgPause":
		if (MsnGetPlayState() == mpPlaying) {
			MsnMediaPause();
		}
		break;
	case "imgStop":
		if (MsnGetPlayState() != mpStopped) {
			MsnMediaStop();
			MsnSetPosition(0);
			SetController(0);
		}
		break;
	case "imgVolSwitch":
		MsnSetMute( !MsnGetMute());
		break;
	case "imgVideoFullScreen":
		if(MsnGetPlayState()==mpPlaying)
			MsnSetFullSize();
		break;
	default:
	
	}
	return false;
}

//Dreamweaver生成的按钮换图片命令/
/*<script language="JavaScript" type="text/JavaScript">
<!--
*/
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;

}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function SwapImage(imgObj){
	var MouseOverFile;
	var OrigFile=imgObj.src;
	MouseOverFile = OrigFile.substr(0, OrigFile.lastIndexOf(".")) + "_mouseover." + OrigFile.substring(OrigFile.lastIndexOf(".")+1);
	imgObj.src=MouseOverFile;
}
function SwapImgRestore(imgObj){
	var OrigFile;
	var OrigFile=imgObj.src;
	OrigFile = OrigFile.substr(0, OrigFile.lastIndexOf("_mouseover.")) + OrigFile.substring(OrigFile.lastIndexOf("."));
//	alert(OrigFile);
	imgObj.src=OrigFile;
}
/*
//-->
</script>
*/
//<!--Dreamweaver生成的按钮换图片命令-->

function SetAudioOnly(IsSetAudioOnly){
	var CurPlayerPosition=0;
	var CurPlayState;
	if (!IsSetAudioOnly) {//视频音频/
		IsAudioOnly=false;
		CurPlayState=MsnGetPlayState();

		if (CurPlayState==mpPlaying || CurPlayState==mpPaused) {
			CurPlayerPosition=MsnGetPosition();
			MsnMediaStop();
		}

		divVideoPhoto.style.display="none";
		MsnSetFileName(VideoURL);
		MsnMediaPlay();
		
		if (CurPlayState==mpPlaying || CurPlayState==mpPaused) {
			MsnMediaPlay();
			//MediaPlayer.CurrentPosition=CurPlayerPosition;
			VideoJumpTo(CurPlayerPosition);
			if (CurPlayState==mpPaused) MsnMediaPause();
		}

	}else{//仅音频/
		IsAudioOnly=true;
		CurPlayState=MsnGetPlayState();

		if (CurPlayState==mpPlaying || CurPlayState==mpPaused) {
			CurPlayerPosition=MsnGetPosition();
			MsnMediaStop();
		}
		MsnSetFileName(AudioURL);
		MediaPlayer.height=24;
		divVideoPhoto.style.display="block";
		MsnMediaPlay();

		if (CurPlayState==mpPlaying || CurPlayState==mpPaused) {
			MsnMediaPlay();
			//MediaPlayer.CurrentPosition=parseFloat(CurPlayerPosition);
			VideoJumpTo(CurPlayerPosition);
			if (CurPlayState==mpPaused) MsnMediaPause();
		}
	}
	divResize();
}
var dut=1020*Math.random();
var StrForeColor,StrBackColor,StrDarkColor;
function SetColorStyle(_ColorStyleId){
	var objs;
	var i;
	var xColorStyleNode;
	var strColorStylePath;
	var strBackColor;
	var strForeColor;
	var IsIdValid=false;

	for (i=0; i<xModelXml.selectNodes("Model/ColorStyles/ColorStyle").length; i++) {
		xColorStyleNode=xModelXml.selectNodes("Model/ColorStyles/ColorStyle").item(i);
		if (xColorStyleNode.selectSingleNode("ColorStyleId").nodeTypedValue==_ColorStyleId) {
			strColorStylePath=xColorStyleNode.selectSingleNode("Path").nodeTypedValue;
			strBackColor=xColorStyleNode.selectSingleNode("BackColor").nodeTypedValue;

			if (xColorStyleNode.selectSingleNode("ForeColor")) {
				strForeColor=xColorStyleNode.selectSingleNode("ForeColor").nodeTypedValue;
			}

			CurColorStyleId=_ColorStyleId;
			CurColorStylePath=strColorStylePath;
			IsIdValid=true;
			break;
		}
	}

	if (!IsIdValid) {
		return false;
	}
	
	var IsPlayerPlaying=false;
	if (MsnGetPlayState()==mpPlaying) {
		IsPlayerPlaying=true;
		MsnMediaPause();
	}

	parent.fraTop.location.href="" + strColorStylePath + "/top.htm";
	parent.PPTNav.location.href="" + strColorStylePath + "/right2.htm";
	parent.fraBottom.location.href="" + strColorStylePath + "/down.htm";

	//=====ShowTrademark
	//setTimeout('if (parent.fraTop.imgTrademark != null) {parent.fraTop.imgTrademark.style.visibility=(IsShowTrademark ? "visible" : "hidden");}',1000);

	//=====ShowCourseInfo
	oTmrShowCourseInfo=setInterval("if (ShowCourseInfo()) clearInterval(oTmrShowCourseInfo);", 1000);

	lnkCssLink.href="" + strColorStylePath + "/css.css"

	objs=document.all;
	//alert(objs.length);
	for (i=0; i<objs.length; i++) {
		if (objs[i].ColorStyleTag) {
			switch (objs[i].ColorStyleTag) {
			case "objColorStyleImg":
				objs[i].src="" + strColorStylePath + "/images/" + objs[i].imgFileName;
				break;
			case "objColorStyleImgIcon":
				objs[i].src="" + strColorStylePath + "/icon/" + objs[i].imgFileName;
				break;
			case "objColorStyleBackColor":
				objs[i].style.backgroundColor=strBackColor;
				break;
			case "objColorStyleBackImg":
				objs[i].style.backgroundImage="url(" + strColorStylePath + "/images/" + objs[i].backimgFileName + ")";
				break;
			default:
			}
		}
	}

	tdControlSeparator.style.color=strBackColor;
	PicView.style.scrollbarFaceColor=strBackColor;
	PicView.style.scrollbarShadowColor=strBackColor;
	PicView.style.scrollbarBaseColor=strBackColor;
	PicView.style.scrollbarDarkShadowColor=strBackColor;
	
	SlideTree.style.scrollbarFaceColor=strBackColor;
	SlideTree.style.scrollbarShadowColor=strBackColor;
	SlideTree.style.scrollbarBaseColor=strBackColor;
	SlideTree.style.scrollbarDarkShadowColor=strBackColor;

	if (IsPlayerPlaying) {
		MsnMediaPlay();
	}
}

function ShowCourseInfo(){
	//var CourseTitle=(xmlDoc.selectSingleNode("courseslides/coursetitle") ? "课程: " + xmlDoc.selectSingleNode("courseslides/coursetitle").nodeTypedValue : "网梯课件制作系统");
	var CourseTitle=(Trim(xmlDoc.selectSingleNode("courseslides/coursetitle").nodeTypedValue).length > 0 ? unescape("%u8BFE%u7A0B%3A%20") + xmlDoc.selectSingleNode("courseslides/coursetitle").nodeTypedValue : unescape(""));
	var CourseTeacher=((xmlDoc.selectSingleNode("courseslides/Teacher") && Trim(xmlDoc.selectSingleNode("courseslides/Teacher").nodeTypedValue).length > 0) ? "&nbsp;(" + xmlDoc.selectSingleNode("courseslides/Teacher").nodeTypedValue + ")" : "");
	//var CourseDescription=(xmlDoc.selectSingleNode("courseslides/description") ? "课程内容: " + xmlDoc.selectSingleNode("courseslides/description").nodeTypedValue : "");
	var CourseDescription=(xmlDoc.selectSingleNode("courseslides/description") ? unescape("%u8BFE%u7A0B%u5185%u5BB9%3A%20") + xmlDoc.selectSingleNode("courseslides/description").nodeTypedValue : "");
	//var ModelUserName=(xModelXml.selectSingleNode("Model/Settings/ModelUserName") ? xModelXml.selectSingleNode("Model/Settings/ModelUserName").nodeTypedValue : "北京网梯信息技术有限公司");
	var ModelUserName=(xModelXml.selectSingleNode("Model/Settings/ModelUserName") ? xModelXml.selectSingleNode("Model/Settings/ModelUserName").nodeTypedValue : unescape(""));
	var ret=false;

	if (parent.PPTNav.document.getElementById("divCourseInfo")) {
		parent.PPTNav.divCourseInfo.innerHTML=CourseTitle + CourseTeacher + "<br>" + CourseDescription + "<br>" + ModelUserName;
		parent.PPTNav.doResize();
		ret=true;
	}
	return ret;
}

function InitColorStyleMenu(){
	var HtmlString='';
	var i;
	var xColorStyleNode;
	var strColorStyleId;
	var strColorStyleName;
	var xDisplayColor;

	DefaultColorStyleId="";
	HtmlString += '<TABLE id="tableColorStyleList" width="100%" align="center" onMouseOver="divColorStyleMenu.style.display=\'block\';">';
	
	for (i=0; i<xModelXml.selectNodes("Model/ColorStyles/ColorStyle").length; i++) {
		xColorStyleNode=xModelXml.selectNodes("Model/ColorStyles/ColorStyle").item(i);
		
		strColorStyleId=xColorStyleNode.selectSingleNode("ColorStyleId").nodeTypedValue;
		strColorStyleName=xColorStyleNode.selectSingleNode("ColorStyleFriendlyName").nodeTypedValue;
		xDisplayColor=xColorStyleNode.selectSingleNode("DisplayColor");
		if (!xDisplayColor) {
			xDisplayColor=xColorStyleNode.selectSingleNode("BackColor");
		}
		if (xColorStyleNode.selectSingleNode("Default")) {
			DefaultColorStyleId=strColorStyleId;
		}
		HtmlString += '<TR><TD height="20" ColorStyleId="' + strColorStyleId + '" style="background-color:' + xDisplayColor.nodeTypedValue + '; cursor:hand; border-style:solid; border-width:1; border-color:#999999" onClick="doMenuItemClick(\'divColorStyleMenu\', this);"><SPAN title="' + strColorStyleName + '">' + unescape("%u3000") + '</SPAN></TD></TR>';
	}

	HtmlString += '</TABLE>';
	divColorStyleMenu.innerHTML=HtmlString;

	if (DefaultColorStyleId=="") {
		DefaultColorStyleId=xModelXml.selectNodes("Model/ColorStyles/ColorStyle/ColorStyleId").item(0).nodeTypedValue;
	}
}

function PopMenu(obj){
	debug(IsAudioOnly);
	var iMenuLeft=0;
	switch (obj.id) {
	case "imgAudioOnlyMenu":
		divAudioOnlyMenu.style.top=parseInt(GetAbsoluteTop(obj))+parseInt(obj.height);
		iMenuLeft=parseInt(GetAbsoluteLeft(obj))-parseInt(divAudioOnlyMenu.style.width)+parseInt(obj.style.width);
		divAudioOnlyMenu.style.left=(iMenuLeft > 5 ? iMenuLeft : 5);
		
		if (IsAudioOnly) {
			//tdSelACheck.innerHTML="●";
			tdSelACheck.innerHTML=unescape("%u25CF");	//Win2000的IE5的js文件用ascii方式时不能有中文,不用unicode是为了在98下也能看/
			//tdSelAVCheck.innerHTML="　";
			tdSelAVCheck.innerHTML=unescape("%u3000");	//Win2000的IE5的js文件用ascii方式时不能有中文,不用unicode是为了在98下也能看/

		}else{
			//tdSelACheck.innerHTML="　";
			tdSelACheck.innerHTML=unescape("%u3000");	//Win2000的IE5的js文件用ascii方式时不能有中文,不用unicode是为了在98下也能看/
			//tdSelAVCheck.innerHTML="●";
			tdSelAVCheck.innerHTML=unescape("%u25CF");	//Win2000的IE5的js文件用ascii方式时不能有中文,不用unicode是为了在98下也能看/
		}
		(divAudioOnlyMenu.style.display=="block") ? (divAudioOnlyMenu.style.display="none") : (divAudioOnlyMenu.style.display="block");
		break;
	case "imgColorStyleMenu":
		divColorStyleMenu.style.top=parseInt(GetAbsoluteTop(obj))+parseInt(obj.height);
		iMenuLeft=parseInt(GetAbsoluteLeft(obj))-parseInt(divColorStyleMenu.style.width)+parseInt(obj.style.width)+parseInt(divColorStyleMenu.style.width)/2;
		debug(parseInt(obj.style.width));
		divColorStyleMenu.style.left=(iMenuLeft > 5 ? iMenuLeft : 5);
		(divColorStyleMenu.style.display=="block") ? (divColorStyleMenu.style.display="none") : (divColorStyleMenu.style.display="block");
		break;
	case "imgVolSwitch":
		volumeContral.style.top=parseInt(GetAbsoluteTop(obj))+parseInt(obj.height);
		iMenuLeft=parseInt(GetAbsoluteLeft(obj))-parseInt(volumeContral.style.width)+parseInt(obj.style.width)+10;
		debug(parseInt(obj.style.width));
		volumeContral.style.left=(iMenuLeft > 5 ? iMenuLeft : 5);
		(volumeContral.style.display=="block") ? (volumeContral.style.display="none") : (volumeContral.style.display="block");
		break;
	case "imgSetBandWidth":
		divBandStyleMenu.style.top=parseInt(GetAbsoluteTop(obj))+parseInt(obj.height)-25;
		iMenuLeft=parseInt(GetAbsoluteLeft(obj))-parseInt(divBandStyleMenu.style.width)+parseInt(obj.style.width)+parseInt(divBandStyleMenu.style.width)/2;
		debug(parseInt(obj.style.width));
		divBandStyleMenu.style.left=(iMenuLeft > 5 ? iMenuLeft : 5);
		(divBandStyleMenu.style.display=="block") ? (divBandStyleMenu.style.display="none") : (divBandStyleMenu.style.display="block");
		break;
	default:
	}

}

function doMenuItemClick(MenuName, MenuItem){
	if (MenuName=="divAudioOnlyMenu") {
		if (MenuItem.id=="tdSelAV" && IsAudioOnly) {
			SetAudioOnly(false);
		}else if (MenuItem.id=="tdSelA" && !IsAudioOnly) {
			SetAudioOnly(true);
		}
		divAudioOnlyMenu.style.display="none";

	}else if (MenuName=="divColorStyleMenu") {
		CurColorStylePath=MenuItem.ColorStyleId;
		CurColorStyleBgColor=MenuItem.style.backgroundColor;
		SetColorStyle(CurColorStylePath);
		divColorStyleMenu.style.display="none";
		//add 07.01.24 by wangyuan
		setTimeout("scaleMovie('fix')",100);
		//add 07.01.24 by wangyuan
	}
	else if(MenuName=="divBandStyleMenu"){
		if(MenuItem.id=="audioonly")
		{
			SetAudioOnly(true);
		}
		else
			{
			SetPlayStatus("正在切换到"+MenuItem.id+"K码率下，请稍候...");
			divBandStyleMenu.style.display="none";
			SetPlayBandWidth(MenuItem.id,VideoURL);
			}
	}
}

function GetAbsoluteLeft(obj){

	var iTop=obj.offsetTop;
	var iLeft=obj.offsetLeft;
	var ParentObj=obj;

	while(ParentObj=ParentObj.offsetParent){ 
		iTop += ParentObj.offsetTop; 
		iLeft += ParentObj.offsetLeft;
	}
	return iLeft;
} 

function GetAbsoluteTop(obj){

	var iTop=obj.offsetTop;
	var iLeft=obj.offsetLeft;
	var ParentObj=obj;

	while(ParentObj=ParentObj.offsetParent){ 
		iTop += ParentObj.offsetTop; 
		iLeft += ParentObj.offsetLeft;
	}
	return iTop;
}

function InitColorStyle(){
	var ColorStyleSetting="";
	if (xmlDoc.selectSingleNode("courseslides/CourseSettings/ColorStyleId")) {
		ColorStyleSetting=xmlDoc.selectSingleNode("courseslides/CourseSettings/ColorStyleId").nodeTypedValue;
	}else{
		if (xModelXml.selectSingleNode("Model/Settings/ModelColorStyleId")) {
			ColorStyleSetting=xModelXml.selectSingleNode("Model/Settings/ModelColorStyleId").nodeTypedValue;
		}
	}

	if (ColorStyleSetting=="__DEFAULT__" || Trim(ColorStyleSetting)=="") {
		CurColorStyleId=DefaultColorStyleId;
	}else if (ColorStyleSetting=="__RANDOM__"){
		CurColorStyleId=GetRandomColorStyle();
	}else{
		CurColorStyleId=ColorStyleSetting;
	}

	SetColorStyle(CurColorStyleId);

}

function GetRandomColorStyle(){
	var ColorStylesUpperBound;
	var RndNum;
	var RndColorStyleId;
	ColorStylesUpperBound=parseInt(xModelXml.selectNodes("Model/ColorStyles/ColorStyle").length)-1;
	RndNum=parseInt((ColorStylesUpperBound - 0 + 1) * Math.random() + 0)
	RndColorStyleId=xModelXml.selectNodes("Model/ColorStyles/ColorStyle").item(RndNum).selectSingleNode("ColorStyleId").nodeTypedValue;
	return(RndColorStyleId);
}

function getlocalhostvideo()//此功能使得在whatyviewer中视频文件可以被拖拽
{
	if (parent.location.hostname=="localhost"||parent.location.hostname=="127.0.0.1"){
	document.write("<iframe src='setlocathostvideo.asp' width=0 height=0></iframe>");
	}
}

function getScoreFolder(strFullPath)
{
	var strTemp;
	strTemp = strFullPath.substring(0, strFullPath.lastIndexOf("/"));
	strTemp = strTemp.substring(0, strTemp.lastIndexOf("/"));
	strTemp = strTemp.substring(strTemp.lastIndexOf("/") + 1, strTemp.length);
	return strTemp;
}

//add by wyj for tsinghua scorm which is used to jump to a sldie
function getTimeFromAddress(strFilePath)
{
	for(var i = 0; i < xmlDoc.selectNodes("courseslides/slide").length; i++)
	{
		if(xmlDoc.selectNodes("courseslides/slide").item(i).selectSingleNode("file").nodeTypedValue == strFilePath)
		{
			return xmlDoc.selectNodes("courseslides/slide").item(i).selectSingleNode("duration").nodeTypedValue;
		}
	}
}

function InitBandSettingMenu(){
	var HtmlString='';
	var i;
	var xColorStyleNode;
	var strColorStyleId;
	var strColorStyleName;
	var xDisplayColor;

	DefaultColorStyleId="";
	HtmlString += '<TABLE id="tableBandList" width="100%" align="center" onMouseOver="divBandStyleMenu.style.display=\'block\';">';
	if(IsShowAudioOnlyMenu)
		HtmlString += '<TR><TD id="audioonly" height="20" ColorStyleTag="objColorStyleBackColor"  style="background-color:#E2EAF8; cursor:hand; border-style:solid; border-width:1; border-color:#999999" onClick="doMenuItemClick(\'divBandStyleMenu\', this);"><SPAN title="仅播放音频">仅音频</SPAN></TD></TR>';
	HtmlString += '<TR><TD id="56" height="20" ColorStyleTag="objColorStyleBackColor" style="background-color:#E2EAF8; cursor:hand; border-style:solid; border-width:1; border-color:#999999" onClick="doMenuItemClick(\'divBandStyleMenu\', this);"><SPAN title="设置播放器最高码率为56K">视频56K</SPAN></TD></TR>';
	HtmlString += '<TR><TD id="65" height="20" ColorStyleTag="objColorStyleBackColor"  style="background-color:#E2EAF8; cursor:hand; border-style:solid; border-width:1; border-color:#999999" onClick="doMenuItemClick(\'divBandStyleMenu\', this);"><SPAN title="设置播放器最高码率为65K">视频65K</SPAN></TD></TR>';
	HtmlString += '<TR><TD id="128" height="20" ColorStyleTag="objColorStyleBackColor"  style="background-color:#E2EAF8; cursor:hand; border-style:solid; border-width:1; border-color:#999999" onClick="doMenuItemClick(\'divBandStyleMenu\', this);"><SPAN title="设置播放器最高码率为128K">视频128K</SPAN></TD></TR>';
	HtmlString += '<TR><TD id="256" height="20" ColorStyleTag="objColorStyleBackColor"  style="background-color:#E2EAF8; cursor:hand; border-style:solid; border-width:1; border-color:#999999" onClick="doMenuItemClick(\'divBandStyleMenu\', this);"><SPAN title="设置播放器最高码率为256K">视频256K</SPAN></TD></TR>';
	HtmlString += '<TR><TD id="512" height="20" ColorStyleTag="objColorStyleBackColor"  style="background-color:#E2EAF8; cursor:hand; border-style:solid; border-width:1; border-color:#999999" onClick="doMenuItemClick(\'divBandStyleMenu\', this);"><SPAN title="设置播放器最高码率为512K">视频512K</SPAN></TD></TR>';
	HtmlString += '<TR><TD id="0" height="20" ColorStyleTag="objColorStyleBackColor"  style="background-color:#E2EAF8; cursor:hand; border-style:solid; border-width:1; border-color:#999999" onClick="doMenuItemClick(\'divBandStyleMenu\', this);"><SPAN title="系统自动检测带宽">自适应带宽</SPAN></TD></TR>';

	HtmlString += '</TABLE>';
	
	if(document.getElementById("divBandStyleMenu")!=null)
		divBandStyleMenu.innerHTML=HtmlString;
}

function getScoreFolder(strFullPath)
{
	var strTemp;
	strTemp = strFullPath.substring(0, strFullPath.lastIndexOf("/"));
	strTemp = strTemp.substring(0, strTemp.lastIndexOf("/"));
	strTemp = strTemp.substring(strTemp.lastIndexOf("/") + 1, strTemp.length);
	return strTemp;
}

function GetJumpCourseName(courseName, offset)
{
	var NavigateXml=new ActiveXObject("MSXML.DOMDocument");
	NavigateXml.async=false;
	NavigateXml.load("../navigate/navigate.xml");
	var Navigates = NavigateXml.selectNodes("navigates/navigate");
	if(Navigates.length == 0)
		return null;
	for(var i = 0; i < Navigates.length; i++)
	{
		if(courseName == Navigates[i].selectSingleNode("href").nodeTypedValue)
		{
			break;
		}
	}
	var jumpIndex = i + offset;
	if(jumpIndex < 0)
		jumpIndex = 0;
	else if(jumpIndex >= Navigates.length)
		jumpIndex = Navigates.length - 1;

	return Navigates[jumpIndex].selectSingleNode("href").nodeTypedValue;
}

function JumpToNextCourse()
{
	var courseName= parent.GetCoursePath(parent.location.href);
	var jumpCourseName = GetJumpCourseName(courseName, 1);
	if(jumpCourseName == null || courseName == jumpCourseName)
	{
	}
	else
	{
		parent.location.href = "../"+ jumpCourseName +"/contents.files/frame.htm";
	}
}

function EndCourse()
{
//	parent.CommitTestStr();
	if(parent.multSCO || parent.completedRule=="duration")
		parent.unloadPage("completed");
	parent.closeSco();
	JumpToNextCourse();
}

//if (navigator.appName && navigator.appName.indexOf("Microsoft") != -1 && 
//navigator.userAgent.indexOf("Windows") != -1 && navigator.userAgent.indexOf("Windows 3.1") == -1) {
//document.write('<script language="javascript" FOR="MediaPlayer" EVENT="PlayStateChange(NewState)">\n');
//document.write('  switch(NewState) {');
//document.write('  case 8: ');
//document.write(' 	parent.CommitTestStr();');
//document.write(' 	parent.unloadPage("completed");');
//document.write(' 	JumpToNextCourse();');
//document.write('  	break;');
//document.write('   }');
//document.write('</script\> \n');
//} 


//准备sco数组
function initSCO(sections)
{
	if(parent.multSCO || parent.completedRule=="duration")
		return;
	for(var i=0;i<sections.length;i++)
	{
		if(sections.item(i).selectSingleNode("hidden").nodeTypedValue=="False")
		{
			var thisVisibleNode = getVisibleNode(i+1);
			var nextVisibleNode = getNextVisibleNode(thisVisibleNode);
			var totalDur = getContentDur(thisVisibleNode,nextVisibleNode);
			var scoID = getScoreFolder(zMHTMLPrefix) + "_" +  sections.item(i).selectSingleNode("file").nodeTypedValue;
			//
			parent.scoAry.push([scoID,0,totalDur]);
		}
	}
}

function setStatus(idx)
{
	if(parent.multSCO || !(parent.completedRule=="duration"))
	{
		var preNextVisibleNode = getNextVisibleNode(preVisibleNode);
		if(preVisibleNode=="index")	return;//未初始化的情况会出现溢出
		var thisContentDur = getContentDur(preVisibleNode,preNextVisibleNode);
	}
	if(parent.multSCO)
	{
		var isPptType = false;
		if(xmlDoc.selectSingleNode("courseslides/CourseType")==null)
		{
			isPptType = "flase";
		}
		else
		{
			isPptType = xmlDoc.selectSingleNode("courseslides/CourseType").nodeTypedValue.toLowerCase();
		}
		if(isPptType == "ppt")
		{
			var totalTime= format2Sec(parent.doLMSGetValue("cmi.core.total_time")) + parent.getThisDur()-parent.notStudyDur;
			if(thisContentDur>0 && (totalTime>=thisContentDur*parent.blockPassK || thisContentDur<parent.easyPassTime))
				parent.finishSco = true;
		}
		else
		{
			parent.finishSco = true;
		}
	}
	else
	{
		if(parent.completedRule=="duration")
		{
			var preStudyDur = format2Sec(parent.doLMSGetValue("cmi.core.total_time"));
			var curStudyDur = parent.getThisDur()-parent.notStudyDur;
			//alert("duration:"+parent.notStudyDur)
			if(courseTotalDuration!=null && (preStudyDur+curStudyDur)>=courseTotalDuration*parent.totalPassK)
			{
				parent.finishSco = true;
			}
		}
		else if(parent.completedRule=="block")
		{
			parent.scoAry[idx][1] = Number(parent.scoAry[idx][1]) + Math.round(parent.getThisDur()) -parent.notStudyDur;
			//alert("block:"+parent.notStudyDur)
			var passedSCO = 0;
			var scoData="";
			for(var i=0;i<parent.scoAry.length;i++)
			{
				if(Number(parent.scoAry[i][1])>=Number(parent.scoAry[i][2])*parent.blockPassK || Number(parent.scoAry[i][2])<parent.easyPassTime)
				{
					passedSCO++;
				}
				//
				scoData+=parent.scoAry[i].join(parent.link1);
				if(i!=parent.scoAry.length-1)
					scoData+=parent.link2;
			}
			//数据传平台
			//doLMSSetValue("cmi.core.lesson_progress",Math.round(100*passedSCO/scoAry.length));
			parent.doLMSSetValue("cmi.suspend_data",scoData);
			//alert(scoData)
			//alert(passedSCO+ "?" +scoAry.length)
			parent.history = scoData;
			/*//debug
			var ts = "";
			for(var ii=0;ii<scoAry.length;ii++)
			{
				if(scoAry[ii][1]>0)
					ts+=scoAry[ii][0]+" : "+ scoAry[ii][1] +"\n";
				
			}
			alert(ts)*/
			if(passedSCO==parent.scoAry.length && parent.passedSCO!=0)
				parent.finishSco=true;
		}
	}
}

function InitScormConfig()
{
	var ScormXml=new ActiveXObject("MSXML.DOMDocument");
	ScormXml.async=false;
	ScormXml.load("../navigate/scormconfig.xml");

	var ScormNode= ScormXml.selectSingleNode("scorm");
	if(ScormNode == null)
		return;

	var NodeValue =	GetNodeValue(ScormNode, "type");
	if(NodeValue != null)
	{
		if(NodeValue == "multi")
			parent.multSCO = true;
		else
			parent.multSCO = false;
	}

	NodeValue = GetNodeValue(ScormNode, "blockpassk"); 
	if(NodeValue!= null)
		parent.blockPassK = parseFloat(NodeValue);

	NodeValue = GetNodeValue(ScormNode, "goonstudywith"); 
	if(NodeValue!= null)
		parent.goonStudyWith = NodeValue;

	NodeValue = GetNodeValue(ScormNode, "completedrule"); 
	if(NodeValue!= null)
		parent.completedRule = NodeValue;

	NodeValue = GetNodeValue(ScormNode, "easypasstime"); 
	if(NodeValue!= null)
		parent.easyPassTime = parseFloat(NodeValue);

	NodeValue = GetNodeValue(ScormNode, "totalpassk"); 
	if(NodeValue!= null)
		parent.totalPassK = parseFloat(NodeValue);

	NodeValue = GetNodeValue(ScormNode, "totalpassk"); 
	if(NodeValue!= null)
		parent.totalPassK = parseFloat(NodeValue);

	NodeValue = GetNodeValue(ScormNode, "committime"); 
	if(NodeValue!= null)
		parent.commitTime = parseFloat(NodeValue);
}

function GetNodeValue(node, name)
{
	if(node.selectSingleNode(name) != null)
		return node.selectSingleNode(name).nodeTypedValue;
	else
		return null;
}
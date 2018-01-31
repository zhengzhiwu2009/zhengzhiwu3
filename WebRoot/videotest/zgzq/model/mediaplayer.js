var FlashDebug=false;
var xModelXml=new ActiveXObject("MSXML.DOMDocument");
//var IfJumpFlash=false;
//var newCurrentPaperNum=0;
//var oldCurrentPaperNum=0;
var Timer;
var MediaPlayer = parent.PPTVideo.WindowsMediaPlayer;

var CurSldNum=1,mysel=0;
//var JumpSldNum=1;
var videoLocalPath="";//针对whatyviewer设计

//for shanghai
var currentTitle="";

var IsMousePressed=false;
var IsDebugging = false;
var IsAudioOnly = false;
var InitLeftSize="224";

var isSetQuiz = false;
var isInitQuiz = false;
var QuizSlides = new Array();
var QuizScores = new Array();
var quizAry = new Array();
var QuizPassScores = new Array();
var quizString = new Array();

var CurColorStyleId;
var CurColorStylePath="";
var DefaultColorStyleId;

var VideoURL="";
var AudioURL="";

var iDivVideoPosBarLeft=0;

var oTmrShowCourseInfo;
var IsShowTrademark=true;
var ISBigVideo=false;

var gCurrentPage=0;

//---------------------------------------------------------------
//  0 Stopped  1 Contacting  2 Buffering 3 Playing 4 Paused 5 Seeking 
var mpStopped=0, mpPaused=1, mpPlaying=2, mpWaiting=3,
mpScanForward=4, mpScanReverse=5, mpSkipForward=6,
mpSkipReverse=7, mpMediaEnded=8;
var SlidesCount = -1
 
//
function MsnMediaPlay() {
	MediaPlayer.Play();
}

function ChangeRight() {
//alert("ok");	
//alert("changeright");
ISBigVideo=!ISBigVideo;
SetToBigVideo(ISBigVideo);

if ((parent.PPTVideo.document.body.clientWidth-parent.PPTVideo.WindowsMediaPlayer.width)>0){
      		parent.PPTVideo.WindowsMediaPlayerDiv.style.left=(parent.PPTVideo.document.body.clientWidth-parent.PPTVideo.WindowsMediaPlayer.width)/2;
      	}
      if ((parent.PPTVideo.document.body.clientHeight-parent.PPTVideo.WindowsMediaPlayer.height)>0){
			parent.PPTVideo.WindowsMediaPlayerDiv.style.top=(parent.PPTVideo.document.body.clientHeight-parent.PPTVideo.WindowsMediaPlayer.height)/2;
		}
}

function SetToBigVideo(isVideo)
{
var frmset=parent.document.all("PPTVertAdjust")
	if (isVideo==false)
	{
		frmset.rows="100%,0%,*";
		
		//alert("OK");
		//parent.PPTNav.imgNext.style.display="block";
		//parent.PPTNav.imgPre.style.display="block";
		//imgExpand.style.display="none";
	
	}
	else 
	{
		frmset.rows="0%,100%,*";
		parent.PPTNav.imgNext.style.display="none";
		parent.PPTNav.imgPre.style.display="none";

	}

}
function SetToBigVideo1(isVideo)
{
var frmset=parent.document.all("PPTVertAdjust")
	if (isVideo==false)
	{
		frmset.rows="100%,0%,*";
		//setTimeout("parent.PPTNav.imgScreenFullScreen.style.display='none';",2000);
		//alert("OK");
		//parent.PPTNav.imgNext.style.display="block";
		//parent.PPTNav.imgPre.style.display="block";
		//imgExpand.style.display="none";
	
	}
	else 
	{
		frmset.rows="0%,100%,*";
		//parent.PPTNav.imgNext.style.display="none";
		//parent.PPTNav.imgPre.style.display="none";

	}

}
//==================================================================

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
			MediaPlayer.Mute=false;
		}else{
			MediaPlayer.Mute=true;
		}
		break;
	case "SetVideoFullScreen":
		if (args>0) {
			MediaPlayer.DisplaySize=3;
		}else{
			MediaPlayer.DisplaySize=0;
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
	//if (document.body.clientWidth<205) return;
	//if (document.body.clientWidth>800) return;
	
	//alert(parent.PPTVideo.document.body.clientWidth);
	
	//调节视频div大小/
	if (document.body.clientWidth<33){
		divMediaPlayer.style.width=1
	}
	else{
		divMediaPlayer.style.width=document.body.clientWidth-33;
	}
	//divMediaPlayer.style.height=parseInt(parseInt(divMediaPlayer.style.width) * 3 / 4 + 24);
	divMediaPlayer.style.height=70;
	//tdVideoBackground.style.height=parseInt(divMediaPlayer.style.height) + 2;
  	tdVideoBackground.style.height=70;
  	
  	if (parent.PPTVideo.document.body.clientWidth>2){
  		
  		if (parent.PPTVideo.document.body.clientWidth<2){
  			parent.PPTVideo.WindowsMediaPlayer.width=1
  		}else{
  			parent.PPTVideo.WindowsMediaPlayer.width=parent.PPTVideo.document.body.clientWidth-2;
  		}
  		
  		parent.PPTVideo.WindowsMediaPlayer.height=(parent.PPTVideo.WindowsMediaPlayer.width)*3/4+24;
  		parent.PPTVideo.WindowsMediaPlayerDiv.style.top=(parent.PPTVideo.document.body.clientHeight-parent.PPTVideo.WindowsMediaPlayer.height)/2;
	}
  	
	//调节MediaPlayer大小/
	//if (!IsAudioOnly) {	//视频音频/
		//高度加24是为了保持MediaPlayer画面为4:3,因为还有个状态条/
		//MediaPlayer.width=divMediaPlayer.style.width;
		//MediaPlayer.height=1;
	//}else{	//仅音频/
	//	MediaPlayer.width=divMediaPlayer.style.width;
	//	if (divMediaPlayer.PhotoType.toUpperCase()=="IMG") {
			divVideoPhotoImg.style.height=68;
			
	//	}else{
	//		divVideoPhotoSwf.style.height=parseInt(divMediaPlayer.style.height)-parseInt(MediaPlayer.height)-3;
	//	}
	//}
	
	//调整进度条长度/
	if (parseInt(document.body.clientWidth)<37){
		divVideoPosBar.style.width=1;
	}else{
		divVideoPosBar.style.width=parseInt(document.body.clientWidth)-37;
	}

	var ret=0;
	//菜单
	ret=parseInt(document.body.clientHeight) - (parseInt(divMediaPlayer.style.height)+parseInt(divMediaPlayer.style.top));
//	debug(ret);
	if (SlideTree.style.display != "none"){
		SlideTree.style.height = ret>0 ? ret : 1;
		SlideTree.style.width=divMediaPlayer.style.width;
	}

	if (PicView.style.display != "none"){
		PicView.style.height = ret>0 ? ret : 1;
		PicView.style.width=divMediaPlayer.style.width;
	}
	//debug("clientWidth="+tdVideoPos.clientWidth + " width="+divVideoPosBar.style.width);
	//debug("SlideTree: " + tdListView.clientHeight);
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
			if(parent.PPTSld.document.body.clientWidth>parseInt(xmlDoc.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashwidth")))
			{
				parent.PPTSld.player_clip.SetVariable('_root.clientWidth',parseInt(xmlDoc.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashwidth")));
			}
			else
			{
				parent.PPTSld.player_clip.SetVariable('_root.clientWidth',parent.PPTSld.document.body.clientWidth);
			}
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
	
	if (MediaPlayer.PlayState == mpPlaying) {
		StartTime=MediaPlayer.CurrentPosition*1000-xmlDoc.selectNodes("courseslides/slide/duration").item(CurSldNum-1).nodeTypedValue*1000;
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
	if (MediaPlayer.PlayState == mpPlaying) {
		StartTime=MediaPlayer.CurrentPosition*1000-xmlDoc.selectNodes("courseslides/slide/duration").item(CurSldNum-1).nodeTypedValue*1000;
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
	if (MediaPlayer.PlayState == mpPlaying) {
		StartTime=xmlDoc.selectNodes("courseslides/slide/duration").item(CurSldNum-1).nodeTypedValue;
		if(CurSldNum == xmlDoc.selectNodes("courseslides/slide/duration").length)
		{
			EndTime = Math.ceil(MediaPlayer.Duration);
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


/*
function MM_swapImgRestore() { //v3.0
  var i,x;
  var a=document.MM_sr;
  for (i=0; a && i<a.length && (x=a[i]) && x.oSrc; i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  alert("MM_preloadImages");
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
    
}

function MM_findObj(n, d) { //v3.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document); return x;
}

function MM_swapImage() { //v3.0
	var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
	if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

*/

function LoadVideo() {

	var CurPath;
	var iPos;
	var VideoPathFile;
	var VideoFileName;
	var LocalVideoFile=xmlDoc.selectSingleNode("courseslides/LocalVideo-filename").nodeTypedValue.replace(/\\/g,"/");
	var RemoteVideoFile=xmlDoc.selectSingleNode("courseslides/RemoteVideo-filename").nodeTypedValue;
	var Err;
	RemoteVideoFile = "";
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
		
	}
	else{
		if (Trim(RemoteVideoFile) != "") {
			VideoURL = RemoteVideoFile;
		}
		else{
			var xModelVideoPath=xModelXml.selectSingleNode("Model/Settings/ModelVideoPath");
			if (xModelVideoPath != null && xModelVideoPath.nodeTypedValue.length > 6 && xModelVideoPath.nodeTypedValue.substring(0,6)=="mms://") {
				VideoURL = xModelXml.selectSingleNode("Model/Settings/ModelVideoPath").nodeTypedValue + "/" + VideoFileName;
				VideoURL = VideoURL.substring(0,6) + VideoURL.substring(6).replace(/\/+/g,"/");
			}else{
				VideoURL = GetAbsolutePath(CurPath + "/" + LocalVideoFile);
			}
			
		}
	}
	
	
	AudioURL=unescape(VideoURL.substr(0,VideoURL.length-3)+"wma");
	VideoURL=unescape(VideoURL);


	//if (parseInt(getCookie())==1){
	if (0==0){
		//AudioURL=unescape(VideoURL.substr(0,VideoURL.length-3)+"wma");
		AudioURL=unescape(VideoURL);
		try{
			MediaPlayer.FileName=AudioURL;
		}catch(Err){
			setTimeout("MediaPlayer.FileName=VideoURL;",1000);
		}
		
		parent.PPTVideo.divVideoPhoto.style.width=570
		parent.PPTVideo.divVideoPhoto.style.height=450;
		parent.PPTVideo.divVideoPhoto.style.display="block";
		//parent.PPTVideo.WindowsMediaPlayer.style.height=20
		//MediaPlayer.style.height=20;
		SetToBigVideo1(false);
		
	}
	else{
		try{
			MediaPlayer.FileName=VideoURL;
		}catch(Err){
			setTimeout("MediaPlayer.FileName=VideoURL;",1000);
		}
	}
	
/*
	alert(VideoURL);
	
	try{
		MediaPlayer.FileName=VideoURL;
	}catch(Err){
		setTimeout("MediaPlayer.FileName=VideoURL;",1000);
	}
*/

//	var err;
//	try{
	if (SlidesCount < 0) SlidesCount=xmlDoc.selectNodes("courseslides/slide").length;
//	}catch(err){}
//	SetSubFilename();
	
} 

function getCookie (){
	
	var CookieName="IsOnlyAudio="
	var start = 0;
	var end = 0;
	var CookieString = document.cookie;
	var i = 0;
	
	while (i <= CookieString.length) {
		start = i ;
		end = start + CookieName.length;
		if (CookieString.substring(start, end) == CookieName){
			CookieFound = true;
			break; 
		}
		i++;
	}
	return CookieString.substring(end,end+1);
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
		i = SlidesCount;
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
	Timer=Window.setInterval("ChangeNewLeft()", 1000); 
//	window.setInterval("Simula()", 1000); 
                  
}

function getVolume() {
	return (MediaPlayer.Volume+10000)/100;
}

function setVolume(vol) {
	MediaPlayer.Volume=vol*100-10000;
}

function VideoJumpTo(VideoPosSec){
	if (MediaPlayer.PlayState==mpStopped) {
		MediaPlayer.Play();
	}
	if(MediaPlayer.PlayState==mpWaiting){
		setTimeout("VideoJumpTo("+VideoPosSec+")", 1000);
	}else{
		MediaPlayer.CurrentPosition=VideoPosSec;
	}
}

function jumpTo(SlideNum,tSec){
	var CurrentTime;
	CurrentTime=parseFloat(tSec)+parseFloat(xmlDoc.selectNodes("courseslides/slide/duration").item(SlideNum-1).nodeTypedValue);
	currentTitle=xmlDoc.selectNodes("courseslides/slide/title").item(SlideNum-1).nodeTypedValue;
	if (MediaPlayer.PlayState==mpPlaying || MediaPlayer.PlayState==mpPaused) {
		MediaPlayer.CurrentPosition=CurrentTime;
	}
}

function jump(node){
	//alert("ok");
    mysel=node-1;
	{
        jumpURL(mysel);
		return document.MM_returnValue;	
	}
}

function jumpURL(pagenum){
	setTimer();
	try{
		if (pagenum<0){
			mysel=0;
		}else if (pagenum>=xmlDoc.selectNodes("courseslides/slide").length){
			mysel=xmlDoc.selectNodes("courseslides/slide").length-1;
		}else mysel=pagenum;

		CurSldNum=mysel+1; 
		curSec=parseInt(xmlDoc.selectNodes("courseslides/slide/duration").item(mysel).nodeTypedValue);

		if(MediaPlayer.PlayState == mpPlaying){
			MediaPlayer.CurrentPosition=curSec+1;
			//IfJumpFlash=true;
		}else{
			if(MediaPlayer.PlayState != mpStopped && MediaPlayer.PlayState!= mpMediaEnded) MediaPlayer.CurrentPosition=curSec+1;
			NewPaperName=xmlDoc.selectNodes("courseslides/slide/file").item(mysel).nodeTypedValue;

			if (parent.PPTSld.location.href.indexOf(NewPaperName) <0 && xmlDoc.selectNodes("courseslides/slide/hidden").item(CurrentPage).nodeTypedValue=="False"){
				SelectLink(mysel+1);
				parent.PPTSld.location.href=zMHTMLPrefix+NewPaperName;
				currentTitle=xmlDoc.selectNodes("courseslides/slide/title").item(mysel).nodeTypedValue;
				//caption.innerHTML=unescape("%u7F51%u68AF%u73B0%u4EE3%u8FDC%u7A0B%u6559%u80B2%20%20%u5F53%u524D%u64AD%u653E%uFF1A")+currentTitle;
			}
			else if(parent.PPTSld.location.href.indexOf(NewPaperName) >=0)
			{
				if(parent.PPTSld.location.href.indexOf("WhatyYF_Slide_Video.htm?videoname")>=0)
					MediaPlayer.Play();
			}
		}
	}catch(err){
		if(parent.PPTSld.location.href.indexOf("WhatyYF_Slide_Video.htm?videoname")>=0)
				MediaPlayer.Play();
		debug(err);
	}
	return document.MM_returnValue;
}

function zGoToNextSldOnly(){  
	//window.setInterval("ChangeNewLeft()", 1000000); 
	//alert("ok");
	window.clearInterval(Timer);
	//JumpSldNum=CurSldNum;
	jumpURLSlide(CurSldNum);	
}
function zGoToPrevSldOnly(){
	//alert("ok");
	window.clearInterval(Timer);
	jumpURLSlide(CurSldNum-2);
}

function jumpURLSlide(pagenum){

	try{
		if (pagenum<0){
			mysel=0;
		}else if (pagenum>=xmlDoc.selectNodes("courseslides/slide").length){
			mysel=xmlDoc.selectNodes("courseslides/slide").length-1;
		}else mysel=pagenum;

		CurSldNum=mysel+1; 
		//curSec=parseInt(xmlDoc.selectNodes("courseslides/slide/duration").item(mysel).nodeTypedValue);

		//if(MediaPlayer.PlayState == mpPlaying){
		//	MediaPlayer.CurrentPosition=curSec+1;
			//IfJumpFlash=true;
		//}else{
			//if(MediaPlayer.PlayState != mpStopped && MediaPlayer.PlayState!= mpMediaEnded) MediaPlayer.CurrentPosition=curSec+1;
			NewPaperName=xmlDoc.selectNodes("courseslides/slide/file").item(mysel).nodeTypedValue;

			if (parent.PPTSld.location.href.indexOf(NewPaperName) <0 && xmlDoc.selectNodes("courseslides/slide/hidden").item(CurrentPage).nodeTypedValue=="False"){
				//SelectLink(mysel+1);
				parent.PPTSld.location.href=zMHTMLPrefix+NewPaperName;
				currentTitle=xmlDoc.selectNodes("courseslides/slide/title").item(mysel).nodeTypedValue;
				//caption.innerHTML=unescape("%u7F51%u68AF%u73B0%u4EE3%u8FDC%u7A0B%u6559%u80B2%20%20%u5F53%u524D%u64AD%u653E%uFF1A")+currentTitle;
			}
			else if(parent.PPTSld.location.href.indexOf(NewPaperName) >=0)
			{
				if(parent.PPTSld.location.href.indexOf("WhatyYF_Slide_Video.htm?videoname")>=0)
					MediaPlayer.Play();
			}
		//}
	}catch(err){
		if(parent.PPTSld.location.href.indexOf("WhatyYF_Slide_Video.htm?videoname")>=0)
				MediaPlayer.Play();
		debug(err);
	}
	return document.MM_returnValue;
}

function doMouseDown(Obj) {
	IsMousePressed=true;
	switch(Obj.id){
	case "divVideoPosBar":
		if(event.srcElement.id!="divVideoPosBar")	break;
		var ClickTime=(parseFloat(event.offsetX) / parseFloat(divVideoPosBar.style.width)) * MediaPlayer.Duration;
		VideoJumpTo(ClickTime);
		SetController(parseInt(ClickTime));
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
		if (MediaPlayer.PlayState==mpPlaying){
			var PercentPos=parseFloat(imgVideoPosBtn.style.pixelLeft) / (parseFloat(divVideoPosBar.style.width)-parseFloat(imgVideoPosBtn.width));
			VideoJumpTo(MediaPlayer.Duration * PercentPos);
		}
		break;
	case "divVideoPosBar":
		break;
	}

	IsMousePressed=false;
}


function SecsToHhMmSs(sec) {
	var Hh, Mm, Ss, str;
	Hh = Math.round(sec/3600 - 0.5);
	Mm = Math.round((sec % 3600)/60 - 0.5);
	Ss = Math.round(sec % 60 - 0.5);
	str = Hh;
	str += ":" + (Mm >= 10 ? Mm : "0"+Mm);
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
//	var IsTryFailed = 1;

//	while(IsTryFailed){
		try{

			if(parent.PPTSld.FlashMovie && MediaPlayer.PlayState==mpPlaying && (MediaPlayer.BufferingProgress==100 || parent.location.protocol == "file:")){
//			alert("FlashSync");
				tslidebegin=xmlDoc.selectNodes("courseslides/slide/duration").item(CurSldNum-1).nodeTypedValue;
				//ttestflash=xmlDoc.selectSingleNode(("courseslides/slide/file").attributes.getNamedItem("flashpath");

				mediaTime=Math.abs(MediaPlayer.CurrentPosition-tslidebegin-parseFloat(flashMovie.GetVariable("_root.player.nbseco")));

				if (mediaTime> 1){
//					alert("FlashSync: " + mediaTime);

//					alert(IfJumpFlash);
/*					if (IfJumpFlash==true){
						flashMovie.SetVariable("_root.ScriptLib.timeid",tslidebegin);
						flashMovie.TCallLabel("_root.ScriptLib","Play");
						IfJumpFlash=false	    	
					}else if (IfJumpFlash==false){
						//alert(newCurrentPaperNum);
						//alert(oldCurrentPaperNum);
						if(newCurrentPaperNum>oldCurrentPaperNum){	
							flashMovie.SetVariable("_root.ScriptLib.timeid",tslidebegin);
							flashMovie.TCallLabel("_root.ScriptLib","Play");
							oldCurrentPaperNum=newCurrentPaperNum
						}else{
							flashMovie.SetVariable("_root.ScriptLib.timeid",MediaPlayer.CurrentPosition);
							flashMovie.TCallLabel("_root.ScriptLib","Play");
						}
					}
*/
					flashMovie.SetVariable("_root.ScriptLib.timeid",MediaPlayer.CurrentPosition);
					flashMovie.TCallLabel("_root.ScriptLib","Play");

				}
			}else if (parent.PPTSld.FlashMovie && MediaPlayer.PlayState==mpPaused){
				flashMovie.SetVariable("_root.ScriptLib.timeid",0);
				flashMovie.TCallLabel("_root.ScriptLib","Pause");
			}else if (parent.PPTSld.FlashMovie&&MediaPlayer.PlayState==mpStopped){
//				alert("mpstopped");
				flashMovie.SetVariable("_root.ScriptLib.timeid",0);
				flashMovie.TCallLabel("_root.ScriptLib","Pause");
			}
//			IsTryFailed = 0;
//			alert("succeed!");

		}catch (err){
//			alert("catched err");
//			mediaTime=0;
			window.setTimeout("Simula()", 200);
//			alert("settimeout");
//			IsTryFailed = 1;
		}
//	}
//	alert("out of try!");
}


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
//	debug("PlayState:"+MediaPlayer.PlayState);
	
	showStatusAndTime();
	
	if (xmlDoc.selectNodes("courseslides/slide").item(CurrentPage).selectSingleNode("file").getAttribute("flashpath") != null){
		Simula();
	}
		
	if (MediaPlayer.PlayState!=mpPlaying) {
		do{
			if(MediaPlayer.PlayState==mpPaused)
			{
				var obj = parent.PPTSld.SlideMediaPlayer;
				if(obj!=null)
				{
					if(obj.playState==parent.PPTSld.mpStopped || obj.playState== parent.PPTSld.mpReady)
					{
						if(gCurrentPage<xmlDoc.selectNodes("courseslides/slide").length-1)
						{										
							MediaPlayer.CurrentPosition = xmlDoc.selectNodes("courseslides/slide").item(gCurrentPage + 1).selectSingleNode("duration").nodeTypedValue;
							MediaPlayer.Play();
							break;
						}
						else
						{
							MediaPlayer.Stop();
						}
					}
				}
			}
			SetController(0);
			return;
		}while(false);
	}

	var err;
	try{
		if (SlidesCount < 0) SlidesCount = xmlDoc.selectNodes("courseslides/slide").length;
	}catch(err){return;}

	var NewLeft, OldPaperName, NewPaperName, Dir, CurrentPage, CurrentTime, Num;
	var CPosition,CLength;
	var CurrentPage;
	InitMouseTrack();
	InitFlashScreen();
	

	if (MediaPlayer.PlayState==mpPlaying){

		CPosition=MediaPlayer.CurrentPosition/1000;
		CLength=MediaPlayer.Duration/1000;
		NewLeft=( CPosition/CLength)*(265-12)+12;

		//if (NewLeft>=12 && NewLeft<=265)
		//{btnScroll.style.pixelLeft = NewLeft;}
		//else btnScroll.style.pixelLeft = 12;

		SetController(MediaPlayer.CurrentPosition/MediaPlayer.Duration*1000);

		//Sync with the Right Frame content
		Dir = new String(location);
		Dir = Dir.substring(0, Dir.length - 15);
		CurrentTime=MediaPlayer.CurrentPosition;
		//alert(CurrentPaper);
		//alert(xmlDoc.documentElement.childNodes.item(5+myNum+1).selectSingleNode("duration").nodeTypedValue);


/*		while (CurrentPage<SlidesCount && xmlDoc.documentElement.childNodes.item(5+CurrentPage+2).selectSingleNode("duration").nodeTypedValue < CurrentTime){
		while (CurrentPage < SlidesCount && xmlDoc.selectNodes("courseslides/slide").item(CurrentPage).selectSingleNode("duration").nodeTypedValue < CurrentTime){
			CurrentPage = CurrentPage+1;
			IfJumpFlash=true;
			//alert(IfJumpFlash);
		}
*/


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
			MediaPlayer.Pause();
			MediaPlayer.CurrentPosition = xmlDoc.selectNodes("courseslides/slide/duration").item(CurrentPage).nodeTypedValue;
			gCurrentPage=CurrentPage;
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
				MediaPlayer.Pause();
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

		var IsHiddenExist = true, HiddenPage;
		HiddenPage = xmlDoc.selectNodes("courseslides/slide").item(CurrentPage).selectSingleNode("hidden");
		if(HiddenPage)
			IsHiddenExist = (HiddenPage.nodeTypedValue == "False");

		if (parent.PPTSld.location.href.indexOf(NewPaperName) <0 || parent.PPTSld.location.href.indexOf("WhatyYF_Slide_Video.htm?videoname")>=0){
			if(IsHiddenExist)
				SelectLink(CurrentPage+1);

			parent.PPTSld.location.href=zMHTMLPrefix+NewPaperName;
			currentTitle=xmlDoc.selectNodes("courseslides/slide/title").item(CurrentPage).nodeTypedValue;
			//caption.innerHTML=unescape("%u7F51%u68AF%u73B0%u4EE3%u8FDC%u7A0B%u6559%u80B2%20%20%u5F53%u524D%u64AD%u653E%uFF1A")+currentTitle; 
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
			}
			if(CFlashLength==1){
				SelectLinkFlash(CurrentPage+1,CurrentFlashPage+1);
			}
		}	

		if(SlidesCount==0){
			if(CFlashLength==0){
			}
			else{
				if(CFlashLength==CurrentFlashPage+1){
					parent.finishSco = true;
//					parent.learned();
				}
			}
		}
		else{
			if(CFlashLength==0){
				if(SlidesCount==CurrentPage+1){
					parent.finishSco = true;
//					parent.learned();
				}
			}
			else{
				if(CFlashLength+SlidesCount==CurrentPage+CurrentFlashPage+2){
	//				alert("both");
					parent.finishSco = true;
	//				parent.learned();
				}
			}
		}		
		
	}
	//安比例调节MouseTrack的大小/
	var iTomeWidth;
	var iTomeHeight;
	var iTomeTop;
	var iTomeLeft;
	if (parent.PPTSld.tome != null) {
		if (parseInt(parent.PPTSld.document.body.clientHeight) * 4 / 3 < parseInt(parent.PPTSld.document.body.clientWidth)) {
			//太宽/
			iTomeWidth=parseInt(parent.PPTSld.document.body.clientHeight) * 4 / 3;
			iTomeHeight=parseInt(parent.PPTSld.document.body.clientHeight);
			iTomeTop=5;
			iTomeLeft=parseInt((parseInt(parent.PPTSld.document.body.clientWidth)-iTomeWidth)/2)+5;
		}else{
			//太高/
			iTomeWidth=parseInt(parent.PPTSld.document.body.clientWidth)-4;
			iTomeHeight=parseInt(parent.PPTSld.document.body.clientWidth) * 3 / 4 - 4;
			iTomeTop=parseInt((parseInt(parent.PPTSld.document.body.clientHeight)-iTomeHeight)/2)+5;
			iTomeLeft=5;
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

function Init(){
	//读model.xml/
	xModelXml.async=false;
	xModelXml.load("model.xml");

	try
	{
	//初始化Frame/
	document.getElementsByName("PPTSld").src=xmlDoc.selectNodes("courseslides/slide/file").item(0).nodeTypedValue;
	document.getElementsByName("PPTNts").src=xmlDoc.selectNodes("courseslides/slide/file").item(xmlDoc.selectNodes("courseslides/slide").length-1).nodeTypedValue;
	currentTitle=xmlDoc.selectSingleNode("courseslides/slide/title").nodeTypedValue;
	var xTitle=xmlDoc.selectSingleNode("courseslides/coursetitle");
	}
	catch(Err)
	{
		setTimeout("Init()",300);
		return;
	}
	//parent.document.title=(xTitle != null ? xTitle.nodeTypedValue : unescape("网梯流媒体课件 - 北京网梯信息技术有限公司"));
	parent.document.title=(xTitle != null ? xTitle.nodeTypedValue : unescape(""));
	iDivVideoPosBarLeft=GetAbsoluteLeft(divVideoPosBar);

	if(xmlDoc.selectSingleNode("courseslides").getAttribute("model")=="tree"){
		SlideTree.innerHTML=generateTree();
		ExpandTree();
	}else{
		SlideTree.innerHTML=generateFlatList();
		//imgCollapse.style.visibility="hidden";
		//imgExpand.style.visibility="hidden";
	}

	setTimer();
	Window.setInterval("showStatusAndTime()", 1000);
	//showStatusAndTime();
	PicView.innerHTML=generatePicView();
	if (PicView.innerHTML=="") {
		//imgChangeView.style.visibility="hidden";
	}
	
	//====AudioOnlyMenu/
	var IsShowAudioOnlyMenu=false;
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
	SetColorStyle("colorstyle_blue");
	//parent.InitFrmSet(InitLeftSize,InitIsShowRightDown);
	
	//InitVideoPhoto();
	//LoadVideo();
	divVideoPhotoImg.style.display="block";
	divVideoPhotoSwf.style.display="block";
	//alert("ok");
	

	divResize();
	
	SelectLink(1);
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
		if (PhotoFile.substring(PhotoFile.length-4).toLowerCase()==".gif" || PhotoFile.substring(PhotoFile.length-4).toLowerCase()==".jpg") {
			imgVideoPhotoImg.src=PhotoFile;
			divMediaPlayer.PhotoType="IMG";
		}else if(PhotoFile.substring(PhotoFile.length-4).toLowerCase()==".swf") {
			var err;
			try{
				objswfVideoPhotoSwf.SetVariable("_root.ScriptLib.timeid",PhotoFile);
				objswfVideoPhotoSwf.TCallLabel("_root.ScriptLib","loadPhoto");
				divMediaPlayer.PhotoType="SWF";
			}catch(err){
				divMediaPlayer.PhotoType="";
				//alert("LoadPhotoError");
				//return;
			}
		}else{
			divMediaPlayer.PhotoType="";
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
//			returnResult+="<td><a href=\"#\" target=\"_self\" onclick=\"VideoJumpTo("+citem.getAttribute('time')+");return false;\" ><img src=\"pic/"+citem.getAttribute('pic')+"\" width=\"40\" height=\"30\" border=\"0\" title=\""+citem.nodeTypedValue+"  "+SecsToHhMmSs(citem.getAttribute('time'))+"\" ></a></td>";
			returnResult+='<td><img style="cursor:hand" src="'+courseURL+'/pic/'+citem.getAttribute('pic')+'" width="80" height="60" border="0" onclick="VideoJumpTo('+citem.getAttribute('time')+');" title='+citem.nodeTypedValue+'  '+SecsToHhMmSs(citem.getAttribute('time'))+'\' ></td>';
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

function getOpenICON(thisNodeNum){
	var openICON="";
	var SrcFile = (CurColorStylePath.length==0 ? "aboutblank.gif" : "" + CurColorStylePath + "/icon/open.gif");
	var ret='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="open.gif" src="' + SrcFile + '" border="0" ondrag="return false">';
	
	if (thisNodeNum>0) {
		if (xmlDoc.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("openICON")){
			openICON = xmlDoc.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("openICON");
			
		}else if (xmlDoc.selectSingleNode("courseslides").getAttribute("openICON")){
				openICON = xmlDoc.selectSingleNode("courseslides").getAttribute("openICON");
		}
	}
	
	if (openICON.indexOf(".gif")>0 || openICON.indexOf(".jpg")>0){
		ret='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="' + openICON + '" src="' + SrcFile + '" border="0" ondrag="return false">';
	}
	
	return ret;
}

function getCloseICON(thisNodeNum){
	var closeICON;
	var SrcFile = (CurColorStylePath.length==0 ? "aboutblank.gif" : "" + CurColorStylePath + "/icon/close.gif");
	var ret='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="close.gif" src="' + SrcFile + '" border="0" ondrag="return false">';

	if (thisNodeNum>0) {
		if (xmlDoc.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("closeICON")){
			closeICON = xmlDoc.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("closeICON");
			
		}else if (xmlDoc.selectSingleNode("courseslides").getAttribute("closeICON")){
				closeICON = xmlDoc.selectSingleNode("courseslides").getAttribute("closeICON");
		}
	}
	
	if (closeICON.indexOf(".gif")>0 || closeICON.indexOf(".jpg")>0){
		ret='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="' + closeICON + '" src="' + SrcFile + '" border="0" ondrag="return false">';
	}
	
	return ret;
}

function getNodeICON(thisNodeNum){
	var nodeICON="";
	var SrcFile = (CurColorStylePath.length==0 ? "aboutblank.gif" : "" + CurColorStylePath + "/icon/node.gif");
	var ret='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="node.gif" src="' + SrcFile + '" border="0" ondrag="return false">';
	
	if (thisNodeNum>0) {
		if (xmlDoc.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("nodeICON")){
			nodeICON = xmlDoc.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("nodeICON");
			
		}else if (xmlDoc.selectSingleNode("courseslides").getAttribute("nodeICON")){
			nodeICON = xmlDoc.selectSingleNode("courseslides").getAttribute("nodeICON");
		}
	}
	
	if (nodeICON.indexOf(".gif")>0 || nodeICON.indexOf(".jpg")>0){
		ret='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="' + nodeICON + '" src="' + SrcFile + '" border="0" ondrag="return false">';
	}
	
	return ret;
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

/*
if (navigator.appName && navigator.appName.indexOf("Microsoft") != -1 && 
navigator.userAgent.indexOf("Windows") != -1 && navigator.userAgent.indexOf("Windows 3.1") == -1) {
document.write('<script language="javascript" FOR="MediaPlayer" EVENT="PlayStateChange(OldState, NewState)">\n');
document.write('  switch(NewState) {');
document.write('  case mpMediaEnded: ');
document.write(' 	SetController(0);');
document.write('  	break;');
document.write('   }');
document.write('</script\> \n');
} 
*/
function SetController(sec){
	if (IsMousePressed) return;
	if (MediaPlayer.PlayState==mpPlaying||MediaPlayer.PlayState==mpPaused||MediaPlayer.PlayState==mpWaiting){
		var PercentPos=parseFloat(MediaPlayer.CurrentPosition * 100 / MediaPlayer.Duration);
		var AbsoPos = parseInt((parseInt(divVideoPosBar.style.width)-parseInt(imgVideoPosBtn.width)) * (PercentPos / 100) + 0.5);
		imgVideoPosBtn.style.pixelLeft=AbsoPos;
	}else {
		imgVideoPosBtn.style.pixelLeft = 0;
	}
	//debug("Pecent="+PercentPos+" Width="+(parseInt(divVideoPosBar.style.width)-parseInt(imgVideoPosBtn.width))+" left="+imgVideoPosBtn.style.pixelLeft);
}

function debug(DebugString){

	if (!IsDebugging || !parent.fraTop.divDebug) return;
	parent.fraTop.divDebug.innerHTML=DebugString;
} 

function selectVideoOrAudio(selectIndex){return;}

function doControlClick(Obj){
	switch (Obj.id){
	case "imgFB":
		//if (MediaPlayer.CanScan) {
		//	MediaPlayer.FastReverse();
		//}else{
			zGoToPrevSld();
		//}
		break;
	case "imgPlay":
		if (MediaPlayer.PlayState != mpPlaying) {
			MediaPlayer.Play();
			setTimer();
		}
		break;
	case "imgFF":
		//if (MediaPlayer.CanScan) {
		//	MediaPlayer.FastForward();
		//}else{ 
			zGoToNextSld();
		//}
		break;
	case "imgPause":
		if (MediaPlayer.PlayState == mpPlaying) {
			MediaPlayer.Pause();
		}
		break;
	case "imgStop":
		if (MediaPlayer.PlayState != mpStopped) {
			MediaPlayer.Stop();
			MediaPlayer.CurrentPosition=0;
			SetController(0);
		}
		break;
	case "imgVolSwitch":
		MediaPlayer.Mute = !MediaPlayer.Mute;
		break;
	case "imgVideoFullScreen":
		MediaPlayer.DisplaySize=3;
		break;
	default:
	}
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
		CurPlayState=MediaPlayer.PlayState;

		if (CurPlayState==mpPlaying || CurPlayState==mpPaused) {
			CurPlayerPosition=MediaPlayer.CurrentPosition;
			MediaPlayer.Stop();
		}

		//divVideoPhotoSwf.style.display="none";
		//divVideoPhotoImg.style.display="none";

		MediaPlayer.FileName=VideoURL;
		
		if (CurPlayState==mpPlaying || CurPlayState==mpPaused) {
			MediaPlayer.Play();
			//MediaPlayer.CurrentPosition=CurPlayerPosition;
			VideoJumpTo(CurPlayerPosition);
			if (CurPlayState==mpPaused) MediaPlayer.Pause();
		}

	}else{//仅音频/
		IsAudioOnly=true;
		CurPlayState=MediaPlayer.PlayState;

		if (CurPlayState==mpPlaying || CurPlayState==mpPaused) {
			CurPlayerPosition=MediaPlayer.CurrentPosition;
			MediaPlayer.Stop();
		}
		MediaPlayer.FileName=AudioURL;
//		MediaPlayer.height=1;

		ISBigVideo=false;
		SetToBigVideo(ISBigVideo);
		if (divMediaPlayer.PhotoType.toUpperCase()=="IMG") {
			divVideoPhotoImg.style.display="block";
		}else{
			divVideoPhotoSwf.style.display="block";
		}

		if (CurPlayState==mpPlaying || CurPlayState==mpPaused) {
			MediaPlayer.Play();
			//MediaPlayer.CurrentPosition=parseFloat(CurPlayerPosition);
			VideoJumpTo(CurPlayerPosition);
			if (CurPlayState==mpPaused) MediaPlayer.Pause();
		}
	}
//	divResize();
}

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
	if (MediaPlayer.PlayState==mpPlaying) {
		IsPlayerPlaying=true;
		MediaPlayer.Pause();
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
	PicView.style.scrollbarShadowColor=strForeColor;
	PicView.style.scrollbarBaseColor=strBackColor;
	PicView.style.scrollbarDarkShadowColor=strBackColor;
	PicView.style.scrollbarArrowColor=strForeColor;
	
	SlideTree.style.scrollbarFaceColor=strBackColor;
	SlideTree.style.scrollbarShadowColor=strForeColor;
	SlideTree.style.scrollbarBaseColor=strBackColor;
	SlideTree.style.scrollbarDarkShadowColor=strBackColor;
	SlideTree.style.scrollbarArrowColor=strForeColor;

	if (IsPlayerPlaying) {
		MediaPlayer.Play();
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
		parent.PPTNav.divCourseInfo.innerHTML=CourseTitle + CourseTeacher + "<br>" + CourseDescription + "<br>" +ModelUserName;
		//parent.PPTNav.divCourseInfo.innerHTML=CourseDescription + "<br>" ;
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
		divAudioOnlyMenu.style.top=parseInt(GetAbsoluteTop(obj))+parseInt(obj.height)-40;
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
		divColorStyleMenu.style.top=parseInt(GetAbsoluteTop(obj))+parseInt(obj.height)-160;//此处用于定位菜单位置
		iMenuLeft=parseInt(GetAbsoluteLeft(obj))-parseInt(divColorStyleMenu.style.width)+parseInt(obj.style.width);
		debug(parseInt(obj.style.width));
		divColorStyleMenu.style.left=(iMenuLeft > 5 ? iMenuLeft : 5);
		(divColorStyleMenu.style.display=="block") ? (divColorStyleMenu.style.display="none") : (divColorStyleMenu.style.display="block");
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

function GetAbsoluteHeight(obj){

	var ret=obj.offsetHeight;
	var ParentObj=obj;

	while(ParentObj=ParentObj.offsetParent){ 
		ret += ParentObj.offsetHeight;
	}
	return ret;
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
var CPosition,CLength;

function showStatusAndTime()
{

		CPosition=MediaPlayer.CurrentPosition;
		CLength=MediaPlayer.Duration;
		if(CPosition<0)CPosition = 0;
                //CPosition=MediaPlayer.GetTime()/MediaPlayer.GetTimeScale();
                //CLength=MediaPlayer.GetDuration()/MediaPlayer.GetTimeScale();
    if(parent.PPTNav.StatusTime)
    {
			setTimeout("parent.PPTNav.StatusTime.innerHTML=SecsToHhMmSs(" + CPosition + ")+\"&nbsp;/&nbsp;\"+SecsToHhMmSs(CLength);",200);
		}
}
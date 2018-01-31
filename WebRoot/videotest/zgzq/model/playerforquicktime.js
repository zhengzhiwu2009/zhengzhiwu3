var FlashDebug=false;
//var IfJumpFlash=false;
//var newCurrentPaperNum=0;
//var oldCurrentPaperNum=0;

var CurSldNum=1,mysel=0;
var currentTitle=document.XMLDocument.selectSingleNode("courseslides/slide/title").nodeTypedValue;

var IsMousePressed=false;
var IsDebugging = false;
var IsAudioOnly = false;
var InitLeftSize="224";

var CurColorStyleId;
var CurColorStylePath="";
var DefaultColorStyleId;

var VideoURL="";
var AudioURL="";

var iDivVideoPosBarLeft=0;
var xModelXml=new ActiveXObject("MSXML.DOMDocument");
var oTmrShowCourseInfo;
var IsShowTrademark=true;

//---------------------------------------------------------------
//  0 Stopped  1 Contacting  2 Buffering 3 Playing 4 Paused 5 Seeking 
//var mpStopped=0, mpPaused=1, mpPlaying=2, mpWaiting=3,
//mpScanForward=4, mpScanReverse=5, mpSkipForward=6,
//mpSkipReverse=7, mpMediaEnded=8;
//var SlidesCount = -1
 
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
			if (iSec >= parseInt(document.XMLDocument.selectNodes("courseslides/slide/duration").item(i).nodeTypedValue) &&
				iSec < parseInt(document.XMLDocument.selectNodes("courseslides/slide/duration").item(i+1).nodeTypedValue)){
				break;
			}
		}else{
			break;
		}
	}
	return i;
}

function GetSlidesCount() {	//返回幻灯片数量/
	return document.XMLDocument.selectNodes("courseslides/slide").length; 
}

function getXMLData() { //Version4.1  12.24  取鼠标文件的文件名,根据全局变量CurSldNum/
	var MouseData;
	MouseData= document.XMLDocument.selectNodes("courseslides/slide/sequence").item(CurSldNum-1).nodeTypedValue;
	return MouseData;
}

function setinvisible() {	//让鼠标层消失,由Flash层自己调用,未改函数名/
return;
	if (parent.PPTSld.tome) {
		parent.PPTSld.tome.style.zIndex=-1;
	}
}


function divResize(){
	if (document.body.clientWidth<205){
//document.body.clientWidth=205;
 return;
  }
	//调节视频div大小/
	divMediaPlayer.style.width=document.body.clientWidth-33;
	divMediaPlayer.style.height=parseInt(parseInt(divMediaPlayer.style.width) * 5 / 8 + 24);
	tdVideoBackground.style.height=parseInt(divMediaPlayer.style.height) + 2;

	//调节MediaPlayer大小/
	if (!IsAudioOnly) {	//视频音频/
		//高度加24是为了保持MediaPlayer画面为4:3,因为还有个状态条/
		MediaPlayer.width=divMediaPlayer.style.width;
		MediaPlayer.height=divMediaPlayer.style.height;
		MediaPlayer.SetRectangle("0,0," + MediaPlayer.width + "," + MediaPlayer.height);
	}else{	//仅音频/
		MediaPlayer.width=divMediaPlayer.style.width;
		divVideoPhoto.style.height=parseInt(divMediaPlayer.style.height)-parseInt(MediaPlayer.height);
	}
	
	
	//调整进度条长度/
	divVideoPosBar.style.width=parseInt(document.body.clientWidth)-37;

	var ret=0;
	ret=parseInt(document.body.clientHeight) - (parseInt(divMediaPlayer.style.height)+parseInt(divMediaPlayer.style.top)+101)-24;
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
}

function InitFlashScreen() {

	var attachSWFHtml;
	var iFlashHeight=0,iFlashWidth=0;

	if (parent.PPTSld.FlashMovie && (parent.PPTSld.FlashMovie.innerHTML.length < 30))
	{
		iFlashHeight=isNaN(document.XMLDocument.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashheight"))?600:parseInt(document.XMLDocument.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashheight"));
		iFlashWidth=isNaN(document.XMLDocument.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashwidth"))?800:parseInt(document.XMLDocument.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashwidth"));
		attachSWFHtml = "";
		attachSWFHtml += "<OBJECT classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\"";
		attachSWFHtml += "codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\"";
		attachSWFHtml += " WIDTH=\"" + iFlashWidth + "\" HEIGHT=\"" + iFlashHeight + "\" id=\"player_clip\" ALIGN=\"bottom\">";
		attachSWFHtml += " <PARAM NAME=movie VALUE=\"../../model/player_clip.swf\"> <param NAME=\"FlashVars\" VALUE=\"FlashDebug=" + FlashDebug + "\">  <param NAME=\"SAlign\" VALUE=\"LT\"> <param NAME=\"Play\" VALUE=\"true\"> <PARAM NAME=menu VALUE=false> <PARAM NAME=scale VALUE=noscale>  <PARAM NAME=quality VALUE=high> <PARAM NAME=wmode VALUE=Opaque> <PARAM NAME=bgcolor VALUE=#FFFFFF> <EMBED src=\"../model/player_clip.swf\" menu=false quality=high wmode=Opaque bgcolor=#FFFFFF  WIDTH=\""+iFlashWidth+"\" scale=\"noscale\" HEIGHT=\""+iFlashHeight+"\" NAME=\"player_clip\" salign=\"LT\" ALIGN=\"bottom\" FlashVars=\"FlashDebug=" + FlashDebug + "\"";
		attachSWFHtml += " TYPE=\"application/x-shockwave-flash\" PLUGINSPAGE=\"http://www.macromedia.com/go/getflashplayer\"></EMBED>";
		attachSWFHtml += "</OBJECT>";
		parent.PPTSld.FlashMovie.innerHTML = attachSWFHtml;
		parent.PPTSld.FlashMovie.style.zIndex = 10;
	}
}

function InitMouseTrack() {
	var attachSWFHtml;

	if (parent.PPTSld.tome) {
		attachSWFHtml="";
		attachSWFHtml+="<center>";
		attachSWFHtml+="<OBJECT id=\"myFlash\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\";";
		attachSWFHtml+=" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0\" WIDTH=100% HEIGHT=100%>";
		attachSWFHtml+=" <PARAM NAME=movie VALUE=\"../../model/MouseTrace.swf\">";
		attachSWFHtml+="<PARAM NAME=quality VALUE=high><PARAM NAME=menu VALUE=false> ";
		attachSWFHtml+="  <PARAM NAME=wmode VALUE=transparent> ";
		attachSWFHtml+="  <PARAM NAME=bgcolor VALUE=#FFFFFF> ";
		attachSWFHtml+="  <EMBED name=\"myFlash\" src=\"../../model/MouseTrace.swf\" quality=high menu=false wmode=transparent bgcolor=#FFFFFF  WIDTH=100% HEIGHT=100% TYPE=\"application/x-shockwave-flash\" PLUGINSPAGE=\"http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash\">  </EMBED>";
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
		tPath=document.XMLDocument.selectNodes("courseslides/slide/file").item(CurSldNum-1).getAttribute("flashpath");
		if (tPath!=null) {
			parent.PPTSld.player_clip.SetVariable('datePath',tPath);//设置数据文件名称/
		}

	}
}


function getCurrentPaperStartTime(){
	var StartTime;
	var MouseData;
	
	if (MsnGetPlayState() == mpPlaying) {
		StartTime=MsnGetPosition*1000-document.XMLDocument.selectNodes("courseslides/slide/duration").item(CurSldNum-1).nodeTypedValue*1000;
	}else{
		StartTime=0;
	}
	if (parent.PPTSld.myFlash) {
		MouseData= document.XMLDocument.selectNodes("courseslides/slide/sequence").item(CurSldNum-1).nodeTypedValue+".xml";
		parent.PPTSld.myFlash.SetVariable('DataURL',MouseData);//设置数据文件名称/
		parent.PPTSld.myFlash.SetVariable('starttime', StartTime);
	}
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
	var LocalVideoFile=document.XMLDocument.selectSingleNode("courseslides/LocalVideo-filename").nodeTypedValue.replace(/\\/g,"/");
	var RemoteVideoFile=document.XMLDocument.selectSingleNode("courseslides/RemoteVideo-filename").nodeTypedValue;
	var Err;

	iPos=document.location.href.lastIndexOf("/");
	if (document.location.href.lastIndexOf("\\")>iPos) {
		iPos=document.location.href.lastIndexOf("\\");
	}
	CurPath=document.location.href.substr(0,iPos);
	VideoFileName=LocalVideoFile.substring(LocalVideoFile.lastIndexOf("/"));

	if(document.location.protocol == "file:") {
		VideoURL = GetAbsolutePath(CurPath + "/" + LocalVideoFile);
	}else{
		if (Trim(RemoteVideoFile) != "") {
			VideoURL = RemoteVideoFile;
		}else{
			var xModelVideoPath=xModelXml.selectSingleNode("Model/Settings/ModelVideoPath");
			if (xModelVideoPath != null && xModelVideoPath.nodeTypedValue.length > 6 && xModelVideoPath.nodeTypedValue.substring(0,6)=="mms://") {
				VideoURL = xModelXml.selectSingleNode("Model/Settings/ModelVideoPath").nodeTypedValue + "/" + VideoFileName;
				VideoURL = VideoURL.substring(0,6) + VideoURL.substring(6).replace(/\/+/g,"/");
				alert(VideoURL);
			}else{
				VideoURL = GetAbsolutePath(CurPath + "/" + LocalVideoFile);
			}
			
		}
	}

	AudioURL=unescape(VideoURL.substr(0,VideoURL.length-3)+"wma");
	VideoURL=unescape(VideoURL);

	try{
		MsnSetFileName(VideoURL);
		 MsnMediaPlay();
	}catch(Err){
		setTimeout("MsnSetFileName(VideoURL);",1000);
	}
	
//	var err;
//	try{
	if (SlidesCount < 0) SlidesCount=document.XMLDocument.selectNodes("courseslides/slide").length;
//	}catch(err){}
//	SetSubFilename();
	
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
			PaperName=document.XMLDocument.selectNodes("courseslides/slide/file").item(i).nodeTypedValue;
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
	MsnSetVolume(vol*100-10000);
}

function VideoJumpTo(VideoPosSec){
	if (MsnGetPlayState()==mpStopped) {
		MsnMediaPlay();
	}
	if(MsnGetPlayState()==mpWaiting){
		setTimeout("VideoJumpTo("+VideoPosSec+")", 1000);
	}else{
		MsnSetPosition(VideoPosSec);
	}
}

function jumpTo(SlideNum,tSec){
	var CurrentTime;
	CurrentTime=parseFloat(tSec)+parseFloat(document.XMLDocument.selectNodes("courseslides/slide/duration").item(SlideNum-1).nodeTypedValue);
	currentTitle=document.XMLDocument.selectNodes("courseslides/slide/title").item(SlideNum-1).nodeTypedValue;
	if (MsnGetPlayState()==mpPlaying || MsnGetPlayState()==mpPaused) {
		MsnSetPosition(CurrentTime);
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
		}else if (pagenum>=document.XMLDocument.selectNodes("courseslides/slide").length){
			mysel=document.XMLDocument.selectNodes("courseslides/slide").length-1;
		}else mysel=pagenum;

		CurSldNum=mysel+1; 
		curSec=parseInt(document.XMLDocument.selectNodes("courseslides/slide/duration").item(mysel).nodeTypedValue);

		if(MsnGetPlayState() == mpPlaying){
			MsnSetPosition(curSec+1);
			//IfJumpFlash=true;
		}else{
			if(MsnGetPlayState() != mpStopped && MsnGetPlayState()!= mpMediaEnded) MsnSetPosition(curSec+1);
			NewPaperName=document.XMLDocument.selectNodes("courseslides/slide/file").item(mysel).nodeTypedValue;

			if (parent.PPTSld.location.href.indexOf(NewPaperName) <0){
				SelectLink(mysel+1);
				parent.PPTSld.location.href=zMHTMLPrefix+NewPaperName;
				currentTitle=document.XMLDocument.selectNodes("courseslides/slide/title").item(mysel).nodeTypedValue;
				//caption.innerHTML=unescape("%u7F51%u68AF%u73B0%u4EE3%u8FDC%u7A0B%u6559%u80B2%20%20%u5F53%u524D%u64AD%u653E%uFF1A")+currentTitle;
			}
		}
	}catch(err){
		debug(err);
	}
	return document.MM_returnValue;
}


function doMouseDown(Obj) {
	IsMousePressed=true;
	switch(Obj.id){
	case "divVideoPosBar":
		var ClickTime=(parseFloat(event.offsetX) / parseFloat(divVideoPosBar.style.width)) * MsnGetDuration();
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

			if(parent.PPTSld.FlashMovie && MsnGetPlayState()==mpPlaying && (MsnGetBufferingProgress()==100 || document.location.protocol == "file:")){
//			alert("FlashSync");
				tslidebegin=document.XMLDocument.selectNodes("courseslides/slide/duration").item(CurSldNum-1).nodeTypedValue;
				//ttestflash=document.XMLDocument.selectSingleNode(("courseslides/slide/file").attributes.getNamedItem("flashpath");

				mediaTime=Math.abs(MsnGetPosition()-tslidebegin-parseFloat(flashMovie.GetVariable("_root.player.nbseco")));

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
							flashMovie.SetVariable("_root.ScriptLib.timeid",MsnGetPosition());
							flashMovie.TCallLabel("_root.ScriptLib","Play");
						}
					}
*/
					flashMovie.SetVariable("_root.ScriptLib.timeid",MsnGetPosition());
					flashMovie.TCallLabel("_root.ScriptLib","Play");

				}
			}else if (parent.PPTSld.FlashMovie && MsnGetPlayState()==mpPaused){
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

function ChangeNewLeft() {
	
	QuicktimePlayer_StateChange();
//	debug("PlayState:"+MsnGetPlayState());
	if (MsnGetPlayState()!=mpPlaying) {
		
		showStatusAndTime();
		SetController(0);
		return;
	}

	var err;
	try{
		if (SlidesCount < 0) SlidesCount = document.XMLDocument.selectNodes("courseslides/slide").length;
	}catch(err){return;}

	var NewLeft, OldPaperName, NewPaperName, Dir, CurrentPage, CurrentTime, Num;
	var CPosition,CLength;
	var CurrentPage;
	InitMouseTrack();
	InitFlashScreen();
	
	if (MsnGetPlayState()==mpPlaying){
		
		showStatusAndTime();
		
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
		//alert(document.XMLDocument.documentElement.childNodes.item(5+myNum+1).selectSingleNode("duration").nodeTypedValue);


/*		while (CurrentPage<SlidesCount && document.XMLDocument.documentElement.childNodes.item(5+CurrentPage+2).selectSingleNode("duration").nodeTypedValue < CurrentTime){
		while (CurrentPage < SlidesCount && document.XMLDocument.selectNodes("courseslides/slide").item(CurrentPage).selectSingleNode("duration").nodeTypedValue < CurrentTime){
			CurrentPage = CurrentPage+1;
			IfJumpFlash=true;
			//alert(IfJumpFlash);
		}
*/


		if (SlidesCount == 1 || (CurrentTime >= 0 && CurrentTime < document.XMLDocument.selectNodes("courseslides/slide").item(1).selectSingleNode("duration").nodeTypedValue)){
			CurrentPage = 0;
		}
		
		else if(CurrentTime >= 0 && CurrentTime >= document.XMLDocument.selectNodes("courseslides/slide").item(SlidesCount - 1).selectSingleNode("duration").nodeTypedValue){
			CurrentPage = SlidesCount - 1;
		}else{
			CurrentPage = 1;
			while (!(CurrentTime >= document.XMLDocument.selectNodes("courseslides/slide").item(CurrentPage).selectSingleNode("duration").nodeTypedValue &&
					CurrentTime < document.XMLDocument.selectNodes("courseslides/slide").item(CurrentPage + 1).selectSingleNode("duration").nodeTypedValue)){
				if (CurrentPage >= SlidesCount - 1) break;
				CurrentPage = CurrentPage + 1;
			}
		}

		var IsShowAlert = 0;
		if (document.XMLDocument.selectNodes("courseslides/slide").item(CurrentPage).selectSingleNode("file").getAttribute("flashpath") != null){
			Simula();
			IsShowAlert=1;
		}
		//CurrentPage = CurrentPage+1;
//		IfJumpFlash=true;
		//alert(IfJumpFlash);

//		CurrentPaper=myNum;

		CurSldNum = CurrentPage + 1;
		
		NewPaperName=document.XMLDocument.selectNodes("courseslides/slide").item(CurrentPage).selectSingleNode("file").nodeTypedValue;

		if (parent.PPTSld.location.href.indexOf(NewPaperName) <0){
			SelectLink(CurrentPage+1);

			parent.PPTSld.location.href=zMHTMLPrefix+NewPaperName;
			currentTitle=document.XMLDocument.selectNodes("courseslides/slide/title").item(CurrentPage).nodeTypedValue;
			//caption.innerHTML=unescape("%u7F51%u68AF%u73B0%u4EE3%u8FDC%u7A0B%u6559%u80B2%20%20%u5F53%u524D%u64AD%u653E%uFF1A")+currentTitle; 
		};
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

function Init(){
	//读model.xml/
	xModelXml.async=false;
	xModelXml.load("../../model/model.xml");

	//初始化Frame/
	document.getElementsByName("PPTSld").src=document.XMLDocument.selectNodes("courseslides/slide/file").item(0).nodeTypedValue;
	document.getElementsByName("PPTNts").src=document.XMLDocument.selectNodes("courseslides/slide/file").item(document.XMLDocument.selectNodes("courseslides/slide").length-1).nodeTypedValue;
	
	var xTitle=document.XMLDocument.selectSingleNode("courseslides/coursetitle");
	//parent.document.title=(xTitle != null ? xTitle.nodeTypedValue : unescape("网梯流媒体课件 - 北京网梯信息技术有限公司"));
	parent.document.title=(xTitle != null ? xTitle.nodeTypedValue : unescape("%u7F51%u68AF%u6D41%u5A92%u4F53%u8BFE%u4EF6%20-%20%u5317%u4EAC%u7F51%u68AF%u4FE1%u606F%u6280%u672F%u6709%u9650%u516C%u53F8"));
	iDivVideoPosBarLeft=GetAbsoluteLeft(divVideoPosBar);

	if(document.XMLDocument.selectSingleNode("courseslides").getAttribute("model")=="tree"){
		SlideTree.innerHTML=generateTree();
	}else{
		SlideTree.innerHTML=generateFlatList();
		imgCollapse.style.visibility="hidden";
		imgExpand.style.visibility="hidden";
	}

	setTimer();
	PicView.innerHTML=generatePicView();
	if (PicView.innerHTML=="") {
		imgChangeView.style.visibility="hidden";
	}
	
	//====AudioOnlyMenu/
	var IsShowAudioOnlyMenu=false;
	if (document.XMLDocument.selectSingleNode("courseslides/CourseSettings/ShowAudioOnlyMenu")) {
		IsShowAudioOnlyMenu=(document.XMLDocument.selectSingleNode("courseslides/CourseSettings/ShowAudioOnlyMenu").nodeTypedValue.toLowerCase()=="true");
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
	if (document.XMLDocument.selectSingleNode("courseslides/CourseSettings/ShowColorStyleMenu")) {
		IsShowColorStyleMenu=(document.XMLDocument.selectSingleNode("courseslides/CourseSettings/ShowColorStyleMenu").nodeTypedValue.toLowerCase()=="true");
	}else{
		if (xModelXml.selectSingleNode("Model/Settings/ModelIsShowColorStyleMenu")) {
			IsShowColorStyleMenu=(xModelXml.selectSingleNode("Model/Settings/ModelIsShowColorStyleMenu").nodeTypedValue.toLowerCase()=="true");
		}
	}
	if (!IsShowColorStyleMenu) {
		tdColorStyleMenu.style.display="none";
	}

	//====InitLeftSize/
	if (document.XMLDocument.selectSingleNode("courseslides/CourseSettings/InitLeftSize")) {
		InitLeftSize=document.XMLDocument.selectSingleNode("courseslides/CourseSettings/InitLeftSize").nodeTypedValue;
	}else{
		if (xModelXml.selectSingleNode("Model/Settings/ModelInitLeftSize") && Trim(xModelXml.selectSingleNode("Model/Settings/ModelInitLeftSize").nodeTypedValue) != "") {
			InitLeftSize=xModelXml.selectSingleNode("Model/Settings/ModelInitLeftSize").nodeTypedValue;
		}
	}

	//====RightDown/
	var InitIsShowRightDown=true;
	if (document.XMLDocument.selectSingleNode("courseslides/CourseSettings/InitIsShowRightDown")) {
		InitIsShowRightDown=document.XMLDocument.selectSingleNode("courseslides/CourseSettings/InitIsShowRightDown").nodeTypedValue;
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

	parent.InitFrmSet(InitLeftSize,InitIsShowRightDown);
	InitVideoPhoto();
	LoadVideo();

	divResize();
	SelectLink(1);
}

function InitVideoPhoto(){
	var PhotoFile="";
	var CurPath="";

	CurPath=document.location.href.substr(0, document.location.href.lastIndexOf("/"));
	if (document.XMLDocument.selectSingleNode("courseslides").getAttribute("photo") && Trim(document.XMLDocument.selectSingleNode("courseslides").getAttribute("photo")).length > 0){
		PhotoFile=Trim(document.XMLDocument.selectSingleNode("courseslides").getAttribute("photo"));
		PhotoFile=CurPath + "/" + PhotoFile;
	}else{
//		PhotoFile="../../model/photo.jpg";
		if (Trim(xModelXml.selectSingleNode("Model/Settings/ModelPhotoFile").nodeTypedValue).length>0) {
			PhotoFile="../../model/" + xModelXml.selectSingleNode("Model/Settings/ModelPhotoFile").nodeTypedValue;
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
	var PicDoc=document.XMLDocument.selectNodes("courseslides/picview/item");
	
	if (PicDoc.length<=0) {
//		imgChangeView.style.display="none";
		returnResult = "";
	}else{

		returnResult += "<table align=\"center\">";
		for (i=0;i<PicDoc.length;i++) {
			citem=PicDoc.item(i);		
			if((i%2)==0) returnResult+="<tr>";
//			returnResult+="<td><a href=\"#\" target=\"_self\" onclick=\"VideoJumpTo("+citem.getAttribute('time')+");return false;\" ><img src=\"pic/"+citem.getAttribute('pic')+"\" width=\"40\" height=\"30\" border=\"0\" title=\""+citem.nodeTypedValue+"  "+SecsToHhMmSs(citem.getAttribute('time'))+"\" ></a></td>";
			returnResult+='<td><img style="cursor:hand" src="pic/'+citem.getAttribute('pic')+'" width="80" height="60" border="0" onclick="VideoJumpTo('+citem.getAttribute('time')+');" title='+citem.nodeTypedValue+'  '+SecsToHhMmSs(citem.getAttribute('time'))+'\' ></td>';
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
	var SlideDoc=document.XMLDocument.selectNodes("courseslides/slide");
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
	var SlideDoc=document.XMLDocument.selectNodes("courseslides/slide");
	var DivNum=0,n;
	
	
	for (i=0;i<SlideDoc.length;i++){
		citem=SlideDoc.item(i);
		
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
	}
	
	for (n= 0;n<DivNum;n++){
		returnResult+="</div>";
	}
	//alert(returnResult);
	return returnResult;
}

function getOpenICON(thisNodeNum){
	var openICON="";
	var SrcFile = (CurColorStylePath.length==0 ? "../../model/aboutblank.gif" : "../../model/" + CurColorStylePath + "/icon/open.gif");
	var ret='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="open.gif" src="' + SrcFile + '" border="0">';
	
	if (thisNodeNum>0) {
		if (document.XMLDocument.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("openICON")){
			openICON = document.XMLDocument.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("openICON");
			
		}else if (document.XMLDocument.selectSingleNode("courseslides").getAttribute("openICON")){
				openICON = document.XMLDocument.selectSingleNode("courseslides").getAttribute("openICON");
		}
	}
	
	if (openICON.indexOf(".gif")>0 || openICON.indexOf(".jpg")>0){
		ret='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="' + openICON + '" src="' + SrcFile + '" border="0">';
	}
	
	return ret;
}

function getCloseICON(thisNodeNum){
	var closeICON;
	var SrcFile = (CurColorStylePath.length==0 ? "../../model/aboutblank.gif" : "../../model/" + CurColorStylePath + "/icon/close.gif");
	var ret='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="close.gif" src="' + SrcFile + '" border="0">';

	if (thisNodeNum>0) {
		if (document.XMLDocument.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("closeICON")){
			closeICON = document.XMLDocument.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("closeICON");
			
		}else if (document.XMLDocument.selectSingleNode("courseslides").getAttribute("closeICON")){
				closeICON = document.XMLDocument.selectSingleNode("courseslides").getAttribute("closeICON");
		}
	}
	
	if (closeICON.indexOf(".gif")>0 || closeICON.indexOf(".jpg")>0){
		ret='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="' + closeICON + '" src="' + SrcFile + '" border="0">';
	}
	
	return ret;
}

function getNodeICON(thisNodeNum){
	var nodeICON="";
	var SrcFile = (CurColorStylePath.length==0 ? "../../model/aboutblank.gif" : "../../model/" + CurColorStylePath + "/icon/node.gif");
	var ret='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="node.gif" src="' + SrcFile + '" border="0">';
	
	if (thisNodeNum>0) {
		if (document.XMLDocument.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("nodeICON")){
			nodeICON = document.XMLDocument.selectNodes("courseslides/slide").item(thisNodeNum-1).getAttribute("nodeICON");
			
		}else if (document.XMLDocument.selectSingleNode("courseslides").getAttribute("nodeICON")){
			nodeICON = document.XMLDocument.selectSingleNode("courseslides").getAttribute("nodeICON");
		}
	}
	
	if (nodeICON.indexOf(".gif")>0 || nodeICON.indexOf(".jpg")>0){
		ret='<img ColorStyleTag="objColorStyleImgIcon" imgFileName="' + nodeICON + '" src="' + SrcFile + '" border="0">';
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
	if (MsnGetPlayState()==mpPlaying||MsnGetPlayState()==mpPaused){
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
		MsnSetFullSize();
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
		CurPlayState=MsnGetPlayState();

		if (CurPlayState==mpPlaying || CurPlayState==mpPaused) {
			CurPlayerPosition=MsnGetPosition();
			MsnMediaStop();
		}

		divVideoPhoto.style.display="none";
		MsnSetFileName(VideoURL);
		
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

		if (CurPlayState==mpPlaying || CurPlayState==mpPaused) {
			MsnMediaPlay();
			//MediaPlayer.CurrentPosition=parseFloat(CurPlayerPosition);
			VideoJumpTo(CurPlayerPosition);
			if (CurPlayState==mpPaused) MsnMediaPause();
		}
	}
	divResize();
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
	if (MsnGetPlayState()==mpPlaying) {
		IsPlayerPlaying=true;
		MsnMediaPause();
	}

	parent.fraTop.location.href="../../model/" + strColorStylePath + "/top.htm";
	parent.PPTNav.location.href="../../model/" + strColorStylePath + "/right2.htm";
	parent.fraBottom.location.href="../../model/" + strColorStylePath + "/down.htm";

	//=====ShowTrademark
	//setTimeout('if (parent.fraTop.imgTrademark != null) {parent.fraTop.imgTrademark.style.visibility=(IsShowTrademark ? "visible" : "hidden");}',1000);

	//=====ShowCourseInfo
	oTmrShowCourseInfo=setInterval("if (ShowCourseInfo()) clearInterval(oTmrShowCourseInfo);", 1000);

	lnkCssLink.href="../../model/" + strColorStylePath + "/css.css"

	objs=document.all;
	//alert(objs.length);
	for (i=0; i<objs.length; i++) {
		if (objs[i].ColorStyleTag) {
			switch (objs[i].ColorStyleTag) {
			case "objColorStyleImg":
				objs[i].src="../../model/" + strColorStylePath + "/images/" + objs[i].imgFileName;
				break;
			case "objColorStyleImgIcon":
				objs[i].src="../../model/" + strColorStylePath + "/icon/" + objs[i].imgFileName;
				break;
			case "objColorStyleBackColor":
				objs[i].style.backgroundColor=strBackColor;
				break;
			case "objColorStyleBackImg":
				objs[i].style.backgroundImage="url(../../model/" + strColorStylePath + "/images/" + objs[i].backimgFileName + ")";
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
	//var CourseTitle=(document.XMLDocument.selectSingleNode("courseslides/coursetitle") ? "课程: " + document.XMLDocument.selectSingleNode("courseslides/coursetitle").nodeTypedValue : "网梯课件制作系统");
	var CourseTitle=(Trim(document.XMLDocument.selectSingleNode("courseslides/coursetitle").nodeTypedValue).length > 0 ? unescape("%u8BFE%u7A0B%3A%20") + document.XMLDocument.selectSingleNode("courseslides/coursetitle").nodeTypedValue : unescape("%u7F51%u68AF%u8BFE%u4EF6%u5236%u4F5C%u7CFB%u7EDF"));
	var CourseTeacher=((document.XMLDocument.selectSingleNode("courseslides/Teacher") && Trim(document.XMLDocument.selectSingleNode("courseslides/Teacher").nodeTypedValue).length > 0) ? "&nbsp;(" + document.XMLDocument.selectSingleNode("courseslides/Teacher").nodeTypedValue + ")" : "");
	//var CourseDescription=(document.XMLDocument.selectSingleNode("courseslides/description") ? "课程内容: " + document.XMLDocument.selectSingleNode("courseslides/description").nodeTypedValue : "");
	var CourseDescription=(document.XMLDocument.selectSingleNode("courseslides/description") ? unescape("%u8BFE%u7A0B%u5185%u5BB9%3A%20") + document.XMLDocument.selectSingleNode("courseslides/description").nodeTypedValue : "");
	//var ModelUserName=(xModelXml.selectSingleNode("Model/Settings/ModelUserName") ? xModelXml.selectSingleNode("Model/Settings/ModelUserName").nodeTypedValue : "北京网梯信息技术有限公司");
	var ModelUserName=(xModelXml.selectSingleNode("Model/Settings/ModelUserName") ? xModelXml.selectSingleNode("Model/Settings/ModelUserName").nodeTypedValue : unescape("%u5317%u4EAC%u7F51%u68AF%u4FE1%u606F%u6280%u672F%u6709%u9650%u516C%u53F8"));
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

function InitColorStyle(){
	var ColorStyleSetting="";
	if (document.XMLDocument.selectSingleNode("courseslides/CourseSettings/ColorStyleId")) {
		ColorStyleSetting=document.XMLDocument.selectSingleNode("courseslides/CourseSettings/ColorStyleId").nodeTypedValue;
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

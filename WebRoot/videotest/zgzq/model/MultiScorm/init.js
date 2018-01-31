var multSCO = 		true;
//继续学习依据，按页：page；按时间position
var goonStudyWith = "page";

//多节点每个节点完成时间比例
var blockPassK = 0.75;

//完成准则(只针对单节点)，按累计时长：duration；按内容块：block；
var completedRule = "duration";
//单节点完成页数占总页数的比例
var easyPassTime = 0.8;
//单节点按总时间的完成时间比例
var totalPassK = 0.8;
var commitTime = 5;

var curScoId = "";
//是否为多节点课程

var link1 = "*";
var link2 = "-";
var thisIDX= 0;
//从平台获取suspend_data
var history = "";
var initHisSco = false;
var scoAry = new Array();
var hisScoAry = new Array();
//多节点完成标志
var finishSco = false;
//排除计时
var notStudyDur = null;
//
function enterSco(scoID)
{
	if(notStudyDur == null)
		setInterval("notStudyTimer()",1000);

	if(!multSCO && completedRule=="duration")
		return;
	//
	setSCOID(scoID);
	loadPage();
	//
	if(multSCO)
	{
		finishSco = false;
	}
	else
	{
		if(initHisSco)	return;
		//获取最新学习记录
		history = (doLMSGetValue("cmi.suspend_data").length>0)?doLMSGetValue("cmi.suspend_data"):history;
		//alert("history:"+history)
		if(history.indexOf(link2)>=0 && history.indexOf(link1)>=0)
		{
			hisScoAry= parseScoAry(history);
		}
		if(scoAry.length>0 && hisScoAry.length==scoAry.length && initHisSco==false)
		{
			scoAry = hisScoAry;
			//alert("get FRom plant");
		}
		initHisSco = true;
	}
}
//
function closeSco(exitIE)
{
	if(exitPageStatus)	return;
	PPTOtl.setStatus(thisIDX);
	//
	if(goonStudyWith=="page")
		setBookMark(PPTOtl.thisNewPaperName);
	else if(goonStudyWith=="position")
		setBookMark(Number(PPTOtl.MsnGetPosition()));
	//alert("exitPageStatus exitIE:"+exitIE);
	//获取
	var status = doLMSGetValue( "cmi.core.lesson_status" );
	//alert("status:"+status+" finishSco:"+finishSco);
	if(status=="completed")
		return unloadPage('completed');
	else if(status=="passed")
		return unloadPage('passed');
	else
	{
		if( finishSco==false)
		{
			return unloadPage('incomplete');
		}
		else if(finishSco == true)
		{
			return unloadPage('completed');
		}
	}
}

function loadBookMark()
{
	loadPage();
	var bm = doLMSGetValue("cmi.core.lesson_location");
	if(goonStudyWith=="page")
	{
		if(bm.length==13&&bm.indexOf(".htm")>0)
		{
			initPage = bm;
		}
	}
	else if(goonStudyWith=="position")
	{
		if(typeof Number(bm) =="number" && Number(bm)>0)
		{
			initPos = Math.round(bm);
		}
	}
	//alert("loadBookMark init: "+initPage +"  & pos:"+ initPos)
}
function setBookMark(bookMark)
{
	if(goonStudyWith=="page")
	{
		if(bookMark.length==13&&bookMark.indexOf(".htm")>0)
		{
			doLMSSetValue("cmi.core.lesson_location",bookMark);
		}
	}
	else if(goonStudyWith=="position")
	{
		if(typeof bookMark =="number" && Number(bookMark)>0)
		{
			doLMSSetValue("cmi.core.lesson_location",bookMark);
		}
	}
}

function parseScoAry(str)
{
	var tmpAry1 = str.split(link2);
	var tmpAry2 = new Array();
	for(var i=0;i<tmpAry1.length;i++)
	{
		tmpAry2.push(tmpAry1[i].split(link1));
	}
	return tmpAry2;
}
function setSCOID(scoID)
{
	if(multSCO)
	{
		doLMSSetSCOID(scoID);
		curScoId = scoID;
	}
	else
	{
		for(var i=0;i<scoAry.length;i++)
		{
			if(scoAry[i][0]==scoID)
			{
				thisIDX = i;
				break;
			}
		}	
	}
}

function notStudyTimer()
{
	try{
		if(PPTOtl.MsnGetPlayState() != PPTOtl.mpPlaying){
			notStudyDur++;
		}
	}catch(err){
//		alert("not study timer error, name:"+err.name+", message:"+err.message);
	}
}

var gNavLoaded = gOtlNavLoaded = gOtlLoaded = false;
var gOtlOpen2 =true,gOtlFull=false;
var IsLeftHidden=false;
var IsRightDownHidden=false;

//edit 07.07.19 by wangyuan for scorm Bookmark start
//在player.js中用来确定书签是否已被读取过
var isBookMarkRead = false;
function Load()
{
	if(!checkBrowser()) return;
	str=document.location.hash,idx=str.indexOf('#')
	if(idx>=0) str=str.substr(1);
	bookMark = readBookMark();
	if(str != "")
	{
		PPTOtl.location.href="../../model/media.htm";		
		linkBookMark(str);
	}
	else if(bookMark!=null&&bookMark!="" && bookMark!=false)
	{
		str = bookMark;
		isBookMarkRead = true;
		//PPTSld.location.replace(str);
		PPTOtl.location.href="../../model/media.htm";		
		linkBookMark(str);
	}
	else
	{
		
		if (PPTOtl.location.href.substr(0,11) == "about:blank")
		{
		  PPTOtl.location.href="../../model/media.htm";
		}
	}
}

function linkBookMark(str)
{
    if(PPTOtl.InitLeftSize && PPTOtl.MediaPlayer != undefined && PPTOtl.MediaPlayer != null)
    {
    	if(str.substring(str.length - 4) == ".htm")
    		PPTOtl.VideoJumpTo(PPTOtl.getTimeFromAddress(str));
    	else
    		PPTOtl.VideoJumpTo(str);
    }
    else
    {
    	window.setTimeout("linkBookMark(str)", 100);
    }
}
//edit 07.07.19 by wangyuan for scorm Bookmark end

function linkCurSlide(str)
{
    if(PPTOtl.InitLeftSize && PPTOtl.MediaPlayer != null)
    {
    	//window.setTimeout("PPTOtl.startFromBookMark(str)", 100);
    	//PPTOtl.startFromBookMark(str);
    	//alert(str);
    	//alert(PPTOtl.getTimeFromAddress(str));
    	PPTOtl.VideoJumpTo(PPTOtl.getTimeFromAddress(str));
    }
    else
    {
    	window.setTimeout("linkCurSlide(str)", 100);
    }
}

function InitFrmSet(InitLeftSize, IsShowRightDown){

  frmset=document.all("PPTHorizAdjust")
  if (parseInt(InitLeftSize)>0){
    frmset.cols=InitLeftSize+",*"
  }

  if (!IsShowRightDown && !IsRightDownHidden){
    zToggleOtlPane2();
  }
}

function zToggleOtlPane()
{
  
  frmset=document.all("PPTHorizAdjust")
  frm=document.all("PPTOtl")

  if( gOtlOpen ){
      frmset.cols="*,100%"
      IsLeftHidden=true
  }else{
      frmset.cols=PPTOtl.InitLeftSize+",*"
      IsLeftHidden=false
  }
//add by zhangzhen
  gOtlOpen=!gOtlOpen
  frm.noResize=!frm.noResize
  UpdOtNavPane()
}


function zToggleOtlPane2()
{	
	
	frmset=document.all("PPTVert")
	frm=document.all("PPTOtl")

	if( gOtlOpen2 ){
   
		frmset.rows="*,26"
		if ((PPTVideo.document.body.clientHeight-PPTVideo.WindowsMediaPlayer.height)>0){
			PPTVideo.WindowsMediaPlayerDiv.style.top=(PPTVideo.document.body.clientHeight-PPTVideo.WindowsMediaPlayer.height)/2;
		}
		 if ((PPTVideo.document.body.clientWidth-PPTVideo.WindowsMediaPlayer.width)>0){
      		PPTVideo.WindowsMediaPlayerDiv.style.left=(PPTVideo.document.body.clientWidth-PPTVideo.WindowsMediaPlayer.width)/2;
      	}
    		IsRightDownHidden=true
	}else{
		frmset.rows="*,82"
		if ((PPTVideo.document.body.clientHeight-PPTVideo.WindowsMediaPlayer.height)>0){
			PPTVideo.WindowsMediaPlayerDiv.style.top=(PPTVideo.document.body.clientHeight-PPTVideo.WindowsMediaPlayer.height)/2;
		}
		 if ((PPTVideo.document.body.clientWidth-PPTVideo.WindowsMediaPlayer.width)>0){
      		PPTVideo.WindowsMediaPlayerDiv.style.left=(PPTVideo.document.body.clientWidth-PPTVideo.WindowsMediaPlayer.width)/2;
      	}
    		IsRightDownHidden=false
  }
//add by zhangzhen
	gOtlOpen2=!gOtlOpen2
//	frm.noResize=!frm.noResize
	UpdOtNavPane()
	PPTOtl.resizeFlashScreen();
	PPTOtl.resizeFlashScreen();
	PPTOtl.resizeFlashScreen();
}


function zFullScreenPPT(){ 
  //alert( MHTMLPrefix+FULLSCR_HREF);
  window.open( zCalculateMHTMLPrefix()+"fullscreen.htm",null,"fullscreen=yes" ) }

function zFullScreen(){
  if(IsLeftHidden && IsRightDownHidden){
  	
    zToggleOtlPane()
    zToggleOtlPane2()
  }else{
    if( !IsLeftHidden ){
      zToggleOtlPane()
    }
    if( !IsRightDownHidden ){
      zToggleOtlPane2()
    }
  }
}


function zCalculateMHTMLPrefix()
{
  //if ( (document.location.protocol == 'mhtml:') || (document.location.protocol == 'file:')|| (document.location.protocol == 'http:')) 
  { //add by Zhang Zhen
      href=new String(document.location.href) 
      Start=href.indexOf('!')+1 
      End=href.lastIndexOf('/')+1 
      if (End < Start) 
          return href.substring(0, Start) 
      else 
      return href.substring(0, End) 
  }
  //return '';//rem by Zhang Zhen
}
function GoToPrevSld1()
{
alert("Ok!,GoToPrevSld1");
//document.all().test();
alert(PPTOtl.location);
PPTOtl.test();
}
//-->
function GetCoursePath(path)
{
	var courseName;
	var iPos=path.lastIndexOf("/");
	path = path.substring(0, iPos);
	iPos=path.lastIndexOf("/");
	path = path.substring(0, iPos);
	iPos=path.lastIndexOf("/");
	courseName = path.substring(iPos + 1);
	return courseName;
}

function GetNavigateScoId(courseName, offset)
{
	var NavigateXml=new ActiveXObject("MSXML.DOMDocument");
	NavigateXml.async=false;
	NavigateXml.load("../../navigate/navigate.xml");
	var Navigates = NavigateXml.selectNodes("navigates/navigate");
	for(var i = 0; i < Navigates.length; i++)
	{
		if(courseName == Navigates[i].selectSingleNode("href").nodeTypedValue)
		{
			break;
		}
	}
	if(i == Navigates.length)
		return "";
	else
		return Navigates[i].selectSingleNode("scoid").nodeTypedValue;
}

function checkBrowser()
{
	if(getBrowserVersion().substring(0, 4) != "msie")
	{
		alert("请您使用IE或以IE为核心的浏览器如360、遨游等打开课件！");
		window.close();	
		return false;
	}
	return true;
}

function getBrowserVersion() {  
    var browser = {};  
    var userAgent = navigator.userAgent.toLowerCase();  
    var s;  
    (s = userAgent.match(/msie ([\d.]+)/))  
            ? browser.ie = s[1]  
            : (s = userAgent.match(/firefox\/([\d.]+)/))  
                    ? browser.firefox = s[1]  
                    : (s = userAgent.match(/chrome\/([\d.]+)/))  
                            ? browser.chrome = s[1]  
                            : (s = userAgent.match(/opera.([\d.]+)/))  
                                    ? browser.opera = s[1]  
                                    : (s = userAgent  
                                            .match(/version\/([\d.]+).*safari/))  
                                            ? browser.safari = s[1]  
                                            : 0;  
    var version = "";  
    if (browser.ie) {  
        version = 'msie ' + browser.ie;  
    } else if (browser.firefox) {  
        version = 'firefox ' + browser.firefox;  
    } else if (browser.chrome) {  
        version = 'chrome ' + browser.chrome;  
    } else if (browser.opera) {  
        version = 'opera ' + browser.opera;  
    } else if (browser.safari) {  
        version = 'safari ' + browser.safari;  
    } else {  
        version = '未知浏览器';  
    }  
    return version;  
}  

function beforeLoadPage()
{
	var courseName= GetCoursePath(location.href);
	var scoId = GetNavigateScoId(courseName, -1);
	if(scoId != "")
	{
		setSCOID(scoId);
	}
}
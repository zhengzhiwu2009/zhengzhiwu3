function learned()
{
	doQuit("completed");
	
}
function closeSco()
{
	var status = doLMSGetValue( "cmi.core.lesson_status" );
//	alert("status:"+status+" finishSco:"+finishSco);

	if(status=="completed")
		return unloadPage('completed');
	else
	{
		if( finishSco==false)
		{
	//	alert("aa");
			return unloadPage('incomplete');
		}
		else if(finishSco == true)
		{
	//	alert("bb");
			return unloadPage('completed');
		}
	}
}

var gNavLoaded = gOtlNavLoaded = gOtlLoaded = false;
var gOtlOpen2 =true,gOtlFull=false;
var IsLeftHidden=false;
var IsRightDownHidden=false;

function Load()
{
  str=document.location.hash,idx=str.indexOf('#')
  if(idx>=0) str=str.substr(1);
  if(str) {
    PPTSld.location.replace(str);
    fraMain.rows="0,*,0";
    PPTHorizAdjust.cols="0,*";
    PPTVert.rows="*,0";
    PPTHorizAdjust.noResize=true;
  }else{
    if (PPTOtl.location.href.substr(0,11) == "about:blank"){
      PPTOtl.location.href="../../model/media.htm";
    }
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
  	//alert("ok");
      frmset.cols="*,100%"
      //alert(PPTVideo.document.body.clientWidth);
      
      if ((PPTVideo.document.body.clientWidth-PPTVideo.WindowsMediaPlayer.width)>0){
      		PPTVideo.WindowsMediaPlayerDiv.style.left=(PPTVideo.document.body.clientWidth-PPTVideo.WindowsMediaPlayer.width)/2;
      	}
      if ((PPTVideo.document.body.clientHeight-PPTVideo.WindowsMediaPlayer.height)>0){
			PPTVideo.WindowsMediaPlayerDiv.style.top=(PPTVideo.document.body.clientHeight-PPTVideo.WindowsMediaPlayer.height)/2;
		}
		
     //alert(PPTVideo.WindowsMediaPlayer.width);
      IsLeftHidden=true
  }else{
      frmset.cols=PPTOtl.InitLeftSize+",*"
      
      if ((PPTVideo.document.body.clientWidth-PPTVideo.WindowsMediaPlayer.width)>0){
      		PPTVideo.WindowsMediaPlayerDiv.style.left=(PPTVideo.document.body.clientWidth-PPTVideo.WindowsMediaPlayer.width)/2;
      	}
      if ((PPTVideo.document.body.clientHeight-PPTVideo.WindowsMediaPlayer.height)>0){
			PPTVideo.WindowsMediaPlayerDiv.style.top=(PPTVideo.document.body.clientHeight-PPTVideo.WindowsMediaPlayer.height)/2;
		}
		
      IsLeftHidden=false
  }
//add by zhangzhen
  gOtlOpen=!gOtlOpen
  frm.noResize=!frm.noResize
  UpdOtNavPane()
  PPTOtl.resizeFlashScreen();
	PPTOtl.resizeFlashScreen();
	PPTOtl.resizeFlashScreen();
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
    if( !IsLeftHidden )
      zToggleOtlPane()
    if( !IsRightDownHidden )
      zToggleOtlPane2()
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

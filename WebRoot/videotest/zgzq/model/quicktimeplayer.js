var mpStopped=0, mpPaused=1, mpPlaying=2, mpWaiting=3,	
    mpScanForward=4, mpScanReverse=5, mpSkipForward=6,
    mpSkipReverse=7, mpClosed=8;
	
var SlidesCount = -1

function MsnSetMute(MuteState)
{
//	if(MeteState){
//		MediaPlayer.SetMute(1);	
//	}
//	else{
//		MediaPlayer.SetMute(0);
//	}
	MediaPlayer.SetMute(MuteState);
}

function MsnGetMute()
{
//	if(MediaPlayer.GetMute()==1){
//		return true;
//	}
//	else{
//	 	return false;
//	 }
	return MediaPlayer.GetMute();
}

function MsnSetFullSize()
{
	//MediaPlayer.
	//SetFullScreen();
}

function MsnSetOrigSize()
{
	//MediaPlayer.SetOriginalSize();
}

function MsnGetPlayState()
{
	return QuicktimePlayer_PlayState(); 
	//MediaPlayer.GetPlayState();
}



function MsnSetPosition(Position)
{
	 MediaPlayer.SetTime (Position*MediaPlayer.GetTimeScale());
}


function MsnGetPosition()
{
	return MediaPlayer.GetTime()/ MediaPlayer.GetTimeScale();	
}

function MsnSetFileName(FileName)
{
	MediaPlayer.SetURL(FileName);
}

function MsnSetVolume(Volume)
{
	 MediaPlayer.SetVolume(Volume*100);
}

function MsnGetVolume()
{
	return (MediaPlayer.GetVolume()+10000)/100;
}

function MsnMediaPlay()
{
	divResize();
	MediaPlayer.Play();
}

function MsnMediaPause()
{
	MediaPlayer.Stop();
}

function MsnMediaStop()
{	
	MediaPlayer.Rewind();
	MediaPlayer.Stop();
}


function MsnGetDuration()
{
	return MediaPlayer.GetDuration()/MediaPlayer.GetTimeScale();
}

function MsnGetBufferingProgress()
{
	return MediaPlayer.BufferingProgress;
}

function MsnCanScan()
{
	//return MediaPlayer.CanScan;
	return false;
}

function MsnFastReverse()
{
	MediaPlayer.FastReverse();
}

function MsnFastForward()
{
	MediaPlayer.FastForward();
}

function SetFullScreen() 
{
	var videoFile=getFilename();
	alert("进入全屏播放，\r\n按Alt+F4退出全屏模式");
	MediaPlayer.Play();
	window.open("../../model/quicktimeFull.html","","statusbar=no,scrollbars=no,fullscreen=yes");
}

function QuicktimePlayer_PlayState()
{
var mpStopped=0, mpPaused=1, mpPlaying=2, mpWaiting=3,	mpScanForward=4, mpScanReverse=5, mpSkipForward=6,mpSkipReverse=7, mpClosed=8;
try{
VideoStatus=MediaPlayer.GetRate();
if (VideoStatus==1) return mpPlaying;
else if (VideoStatus==0) 
{
	if(MediaPlayer.GetTime()>0) return mpPaused; 
	else return mpStopped;
	}
}catch ( err)
	{
		debug(err);
		return 0;
	}-1
}

// after add
function showStatus(msg)
{
      if (StatusBar) StatusBar.innerHTML =msg;           
}

function QuicktimePlayer_StateChange()
{
	
var NewState=QuicktimePlayer_PlayState();
 switch(NewState) {
  case mpPlaying: 
 	showStatus("视频播放中");
  	break;
  case mpPaused:
 	showStatus("暂停");
	break;
  case mpStopped:
 	showStatus("停止");
	break;
  case mpScanForward:
 	showStatus("快进");
	break;
  case mpScanReverse:
 	showStatus("快退");
	break;
   } 	  
}   

function showStatusAndTime()
{
	var CPosition,CLength;	
                CPosition=MediaPlayer.GetTime()/MediaPlayer.GetTimeScale();
                CLength=MediaPlayer.GetDuration()/MediaPlayer.GetTimeScale();
		StatusTime.innerHTML=SecsToHhMmSs(CPosition)+"/"+SecsToHhMmSs(CLength);
	
}
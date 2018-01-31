 var mpStopped=0, mpPaused=4, mpPlaying=3, mpWaiting=1,
	mpScanForward=2, mpScanReverse=5, mpSkipForward=6,
	mpSkipReverse=7, mpMediaEnded=8;
	
var SlidesCount = -1

function MsnSetMute(MuteState)
{
	MediaPlayer.SetMute(MuteState);	
}

function MsnGetMute(MuteState)
{
	return MediaPlayer.GetMute();	
}

function MsnSetFullSize()
{
	MediaPlayer.SetFullScreen();
}

function MsnSetOrigSize()
{
	MediaPlayer.SetOriginalSize();
}

function MsnGetPlayState()
{
	return MediaPlayer.GetPlayState();
}


function MsnSetPosition(Position)
{
	MediaPlayer.SetPosition(Position*1000);
}

function MsnGetPosition()
{
	return MediaPlayer.GetPosition()/1000;	
}

function MsnSetFileName(FileName)
{
	MediaPlayer.SetSource(FileName);
}

function MsnSetVolume(Volume)
{
	 MediaPlayer.SetVolume(Volume);
}

function MsnGetVolume()
{
	return MediaPlayer.GetVolume();
}

function MsnMediaPlay()
{
	MediaPlayer.DoPlay();
}

function MsnMediaPause()
{
	MediaPlayer.DoPause();
}

function MsnMediaStop()
{
	MediaPlayer.DoStop();
}


function MsnGetDuration()
{
	return MediaPlayer.GetLength()/1000;
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
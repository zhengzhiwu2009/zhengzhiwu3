var interval = 1000;
//
var ifOver = false;
var timeOut = null;
function getO(objID)
{
	if(document.getElementById(objID)) return document.getElementById(objID)
	else return false;
}
function hide(obj)
{
	var tempO = getO(obj);
	if(ifOver)	return;
	tempO.style.display = "none";
}
function leave(obj)
{
	var tempO = getO(obj);
	if(tempO!=false)
	{
		timeOut = setTimeout("hide('"+ obj +"')",interval);
		ifOver = false;
	}
}
function over()
{
	ifOver = true;
	clearTimeout(timeOut);
}
function showHideDiv(obj)
{
	clearTimeout(timeOut);
	var tempO = getO(obj);
	if(tempO!=false && tempO.style.display == "none")
	{
		tempO.style.display = "block";
	}
	else if(tempO!=false && tempO.style.display == "block")
		tempO.style.display = "none";
}
function trace(str)
{
	str = String(str);
	getO("showBox").innerText = str;
}
///////
function domousemoves(objID)
{
	var tempO = getO(objID);
	var tempB = getO("volumeContral");
	if(tempO!=false && tempO.onOver=="true")
	{
		
		var tempL = parseInt(event.clientX) - GetAbsoluteLeft(tempB) - parseInt(tempO.offsetWidth/2);
		var tempV = parseInt(tempL*100/(parseInt(tempB.style.width)-parseInt(tempO.offsetWidth)))
		if(tempL>4 && tempL<parseInt(tempB.style.width)-parseInt(tempO.offsetWidth)-4)
		{
			tempO.style.pixelLeft = tempL;
			if(tempV>=50)
			{
				setVolume(75+tempV/4);
			}
			else if(tempV<50 && tempV>25)
			{
				setVolume(63+tempV/2)	
			}
			else if(tempV<=25 && tempV>5)
			{
				setVolume(50+tempV);	
			}
			else
			{
				setVolume(tempV);	
			}
			//trace(75+parseInt(tempL*100/(parseInt(tempB.style.width)-parseInt(tempO.offsetWidth)))/4);
		}
		event.returnValue = false;
		event.cancelBubble = true;
	}
}

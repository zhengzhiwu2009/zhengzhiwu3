/*******************************************************************************
** 
** Filename: SCOFunctions.js
**
** File Description: This file contains several JavaScript functions that are 
**                   used by the Sample SCOs contained in the Sample Course.
**                   These functions encapsulate actions that are taken when the
**                   user navigates between SCOs, or exits the Lesson.
**
** Author: ADL Technical Team
**
** Contract Number:
** Company Name: CTC
**
** Design Issues:
**
** Implementation Issues:
** Known Problems:
** Side Effects:
**
** References: ADL SCORM
**
/*******************************************************************************
**
** Concurrent Technologies Corporation (CTC) grants you ("Licensee") a non-
** exclusive, royalty free, license to use, modify and redistribute this
** software in source and binary code form, provided that i) this copyright
** notice and license appear on all copies of the software; and ii) Licensee
** does not utilize the software in a manner which is disparaging to CTC.
**
** This software is provided "AS IS," without a warranty of any kind.  ALL
** EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
** IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-
** INFRINGEMENT, ARE HEREBY EXCLUDED.  CTC AND ITS LICENSORS SHALL NOT BE LIABLE
** FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
** DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES.  IN NO EVENT WILL CTC  OR ITS
** LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
** INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
** CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
** OR INABILITY TO USE SOFTWARE, EVEN IF CTC  HAS BEEN ADVISED OF THE
** POSSIBILITY OF SUCH DAMAGES. 
**
*******************************************************************************/
var startDate = 0;
var exitPageStatus;
var completePercent = 0.8;	//学时完成标准
var commitHandle = null;

function loadPage()
{
	var result = doLMSInitialize();
	//if(result.toString()=="false")	return;
	var status = doLMSGetValue( "cmi.core.lesson_status" );

	if (status == "not attempted")
	{
	  // the student is now attempting the lesson
	  doLMSSetValue( "cmi.core.lesson_status", "incomplete" );
	}
	 
	exitPageStatus = false;
	startTimer();

	if(commitHandle != null)
	{
		clearTimeout(commitHandle);
		commitHandle=null;
	}	
	//commitTime 初始化后，隔设置时间提交一次数据，并重新初始化
	commitHandle = setTimeout("startCommitTimer()", commitTime * 60 * 1000);
}

function startCommitTimer()
{
	closeSco();
	commitHandle = null;
	loadPage();
	//setTimeout("loadPage()",1000);
}

//add 07.07.19 by wangyuan for bookmark
var bookMark="";
function readBookMark()
{
   //var result = doLMSInitialize();

   bookMark = doLMSGetValue( "cmi.core.lesson_location" );
   //bookMark = "slide0004.htm";
   //alert("readBookMark:" + bookMark)
   if(bookMark!="")	
   	return bookMark;
   else	
   	return false;
   //
   var frameURL = parent.document.location.href;
   //alert("frameURL="+frameURL)
   if(frameURL.indexOf("frame.htm#")>=0 && frameURL.indexOf(bookMark)<0)
   {
   		 var tempNum = frameURL.indexOf("frame.htm#");
   		 var tempStr1 = frameURL.substring(0,tempNum);
   		 var tempStr2 = frameURL.substring(tempNum+23,frameURL.length);
		 parent.document.location.href = tempStr1 + bookMark + tempStr2;
	 }
}
function setBookMark(time)
{
	if(typeof Number(time)=="number" && exitPageStatus==false && PPTOtl.MsnGetPlayState()==PPTOtl.mpPlaying)
	{
		if(PPTOtl.MsnGetDuration()-time<5)
			doLMSSetValue("cmi.core.lesson_location",PPTOtl.MsnGetDuration()-5);
		else
			doLMSSetValue("cmi.core.lesson_location",time);
		//alert("setBookMark:"+markURL)
	}
	else
	{
		//alert("bookMark update failed!")
	}
	
}
//add 07.07.19 by wangyuan for bookmark
//add 08.11.11 by wangyuan for comments
function setCom(comStr)
{
	doLMSSetValue("cmi.comments",comStr);
}
function getCom()
{
	return doLMSGetValue("cmi.comments");
}
//add 08.11.11 by wangyuan for comments
/*******************************************************************************
** 调用APIWrapper.js中的doLMSSetSCOID方法设置SCOID
*******************************************************************************/
function setSCOID(scoID)
{
	doLMSSetSCOID(scoID);
}

//通过计时器计时
function startTimer()
{
	startDate = 0;
}

function startAddTime()
{
	startDate++;
}
setInterval("startAddTime()", 1000);

function getThisDur()
{
//	var currentDate = new Date().getTime();
	return startDate;
}


function computeTime()
{
   if ( startDate != 0 )
   {
      //var currentDate = new Date().getTime();
      var elapsedSeconds = startDate;
      var formattedTime = convertTotalSeconds( elapsedSeconds );
		notStudyDur = 0;
   }
   else
   {
      formattedTime = "00:00:00.0";
   }
   doLMSSetValue( "cmi.core.session_time", formattedTime );
}

function doBack()
{
   doLMSSetValue( "cmi.core.exit", "suspend" );

   computeTime();
   exitPageStatus = true;
   
   var result;

   result = doLMSCommit();

	// NOTE: LMSFinish will unload the current SCO.  All processing
	//       relative to the current page must be performed prior
	//		 to calling LMSFinish.   
   
   result = doLMSFinish();

}

function doContinue( status )
{
   // Reinitialize Exit to blank
   doLMSSetValue( "cmi.core.exit", "" );

   var mode = doLMSGetValue( "cmi.core.lesson_mode" );

   if ( mode != "review"  &&  mode != "browse" )
   {
      doLMSSetValue( "cmi.core.lesson_status", status );
   }
 
   computeTime();
   exitPageStatus = true;
   
   var result;
   result = doLMSCommit();
	// NOTE: LMSFinish will unload the current SCO.  All processing
	//       relative to the current page must be performed prior
	//		 to calling LMSFinish.   

   result = doLMSFinish();

}

function addTimespan  (a, b) 
{
	if ((a == null || String(a) == "null" || a == "" || a.indexOf("NaN")!= -1) || (b == null || String(b) == "null" || b =="" || b.indexOf("NaN")!= -1)) 
	{
		if((a == null || String(a) == "null" || a == "") || a.indexOf("NaN")!= -1){
			a = "00:00:00.0";
		}
		if((b == null || String(b) == "null" || b =="") || b.indexOf("NaN")!= -1){
			b = "00:00:00.0";
		}
	}
	
	var aElements = a.split(":");
	var bElements = b.split(":");

	for (var i=0; i < aElements.length; i++) 
	{
		while (aElements[i].length > 1 && aElements[i].charAt(0) == '0') 
		{
			aElements[i] = aElements[i].substr(1);
		}
	}
	
	for (var i=0; i < bElements.length; i++) 
	{
		while (bElements[i].length > 1 && bElements[i].charAt(0) == '0') 
		{
			bElements[i] = bElements[i].substr(1);
		}
	}
	var seconds = new Number(aElements[2]) + new Number(bElements[2]);
	var minutes = new Number(aElements[1]) + new Number(bElements[1]) + Math.floor(seconds / 60);
	var hours = new Number(aElements[0]) + new Number(bElements[0]) + Math.floor(minutes / 60);
	seconds = seconds - Math.floor(seconds / 60) * 60; 
	minutes = minutes % 60;

	seconds = seconds.toString();
	minutes = minutes.toString();
	hours = hours.toString();
	var p = seconds.indexOf(".");
	
	if (p != -1 && seconds.length > p + 2) 
	{
		seconds = seconds.substring(0, p + 3);	
	}
	
	var result = ((hours.length < 2) ? "0" : "") + hours 
		+ ((minutes.length < 2) ? ":0" : ":") + minutes 
		+ ((p == -1 && seconds.length < 2 || p == 1) ? ":0" : ":") + seconds;
  
	return result;
}

function doQuit( status )
{
   computeTime();
   exitPageStatus = true;

	if(doLMSGetValue( "cmi.core.lesson_status" )!="completed")
	{
		result = doLMSSetValue("cmi.core.lesson_status", status);
	}
   var result;
   result = doLMSCommit();

   
	// NOTE: LMSFinish will unload the current SCO.  All processing
	//       relative to the current page must be performed prior
	//		 to calling LMSFinish.   

   result = doLMSFinish();
}

/*******************************************************************************
** The purpose of this function is to handle cases where the current SCO may be 
** unloaded via some user action other than using the navigation controls 
** embedded in the content.   This function will be called every time an SCO
** is unloaded.  If the user has caused the page to be unloaded through the
** preferred SCO control mechanisms, the value of the "exitPageStatus" var
** will be true so we'll just allow the page to be unloaded.   If the value
** of "exitPageStatus" is false, we know the user caused to the page to be
** unloaded through use of some other mechanism... most likely the back
** button on the browser.  We'll handle this situation the same way we 
** would handle a "quit" - as in the user pressing the SCO's quit button.
*******************************************************************************/
function unloadPage( status )
{
	if (exitPageStatus != true)
	{
		doQuit( status );
	}

	// NOTE:  don't return anything that resembles a javascript
	//		  string from this function or IE will take the
	//		  liberty of displaying a confirm message box.
	
}

/*******************************************************************************
** this function will convert seconds into hours, minutes, and seconds in
** CMITimespan type format - HHHH:MM:SS.SS (Hours has a max of 4 digits &
** Min of 2 digits
*******************************************************************************/
function convertTotalSeconds(ts)
{
   var sec = (ts % 60);

   ts -= sec;
   var tmp = (ts % 3600);  //# of seconds in the total # of minutes
   ts -= tmp;              //# of seconds in the total # of hours

   // convert seconds to conform to CMITimespan type (e.g. SS.00)
   sec = Math.round(sec*100)/100;
   
   var strSec = new String(sec);
   var strWholeSec = strSec;
   var strFractionSec = "";

   if (strSec.indexOf(".") != -1)
   {
      strWholeSec =  strSec.substring(0, strSec.indexOf("."));
      strFractionSec = strSec.substring(strSec.indexOf(".")+1, strSec.length);
   }
   
   if (strWholeSec.length < 2)
   {
      strWholeSec = "0" + strWholeSec;
   }
   strSec = strWholeSec;
   
   if (strFractionSec.length)
   {
      strSec = strSec+ "." + strFractionSec;
   }


   if ((ts % 3600) != 0 )
      var hour = 0;
   else var hour = (ts / 3600);
   if ( (tmp % 60) != 0 )
      var min = 0;
   else var min = (tmp / 60);

   if ((new String(hour)).length < 2)
      hour = "0"+hour;
   if ((new String(min)).length < 2)
      min = "0"+min;

   var rtnVal = hour+":"+min+":"+strSec;

   return rtnVal;
}

//读取测试分数
function getQuiz()
{
	var commentsString = getCom();
	//commentsString = '{"quiz":[{"name":"q1","value":5,"status":"completed"},{"name":"q2","value":6,"status":"completed"},{"value":5,"status":"completed"},{"name":"q3","value":6,"status":"completed"}]}';
	if(commentsString == null || commentsString == "" || commentsString == "null")
		return new Array();
	var temp = commentsString.parseJSON();
	if(typeof(temp.quiz) != "undefined")
		return QuizFilter(temp.quiz);
	else
		return new Array();
}

//add 08.11.11 by wangyuan for comments
function setCom(comStr)
{
//	alert("before encode: "+comStr);
	comStr = encodeURI(comStr);
//	alert("afert encode: "+comStr);
	doLMSSetValue("cmi.suspend_data",comStr);
//	doLMSSetValue("cmi.comments",comStr);
}

function getCom()
{
	return decodeURI( doLMSGetValue("cmi.suspend_data"));
//	return doLMSGetValue("cmi.comments");
}

//设置测试分数
function setQuiz(quizValue)
{
	quizValue = QuizFilter(quizValue);
	var commentsString = getCom();
	var temp;
	if(commentsString == null || commentsString == "" || commentsString == "null")
	{
		temp = new Object();
	}
	else
	{
		temp = commentsString.parseJSON();
	}
	temp.quiz = quizValue;
	//alert(temp.toJSONString());
	setCom(temp.toJSONString());
}


function QuizFilter(ary)
{
	var newAry = new Array();
	var j = 0;
	for(var i = 0; i < ary.length; i++)
	{
		if(typeof(ary[i].name) != "undefined")
		{
			newAry[j] = ary[i];
			j++;
		}
	}
	return newAry;
}

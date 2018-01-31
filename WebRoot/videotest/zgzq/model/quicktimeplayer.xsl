<?xml version="1.0" encoding="GB2312"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl">
<xsl:template match="/">

<HTML>
<HEAD>
<TITLE>网梯课件</TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312" />
<link id="lnkCssLink" href="" rel="stylesheet" type="text/css" /><!--css的link，在InitColorStyle()中设置-->

<!-- 调用库开始 -->
<base target="PPTSld"/>
<!--
<script type="text/javascript" language="JavaScript" src="../../model/poptext.js" charset="gb2312"></script>
-->
<script type="text/javascript" language="JavaScript" src="../../model/list.js" charset="gb2312"></script>
<script type="text/javascript" language="JavaScript" src="../../model/playerforquicktime.js" charset="gb2312"></script>
<script type="text/javascript" language="JavaScript" src="../../model/quicktimeplayer.js" charset="gb2312"></script>

<!-- 调用库结束 -->
</HEAD>
<BODY BGCOLOR="#FFFFFF" LEFTMARGIN="0" TOPMARGIN="0" MARGINWIDTH="0" MARGINHEIGHT="0" OnResize="divResize()" OnLoad="Init();" onContextMenu="return false;"><!-- onContextMenu="return false;"-->
<TABLE WIDTH="100%" height="100%" BORDER="0" CELLPADDING="0" CELLSPACING="0">
  <TR>
    <TD width="15" valign="top" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_02.gif" style="background-image:url(../../model/aboutblank.gif)">
      <!--<IMG SRC="../../model/aboutblank.gif" WIDTH="15" HEIGHT="21"/>-->
    </TD>
    <TD width="100%" height="21" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_03.gif" style="background-image:url(../../model/aboutblank.gif)"></TD>
    <TD width="16" valign="top" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_04.gif" style="background-image:url(../../model/aboutblank.gif)">
      <!--<IMG SRC="../../model/aboutblank.gif" WIDTH="16" HEIGHT="21"/>-->
    </TD>
  </TR>
  <TR>
    <TD width="18" ROWSPAN="3" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_06.gif" style="background-image:url(../../model/aboutblank.gif)"></TD>
    <TD height="169" style="background-color:#003300" ID="tdVideoBackground">
    
    
    
      <!-- 视频播放窗口开始 -->
        
      <div id="divMediaPlayer" class="VideoWindow" style="position:absolute; z-index:1; left: 16px; top: 21px;"> 
      
		<OBJECT name="MediaPlayer" id="MediaPlayer" CLASSID="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" 
		CODEBASE="http://www.apple.com/qtactivex/qtplugin.cab"  WIDTH="192" height="155" 
		standby="Loading Applet Quicktime Player components..." >
		
		<PARAM name="CONTROLLER" VALUE="false" />
		<PARAM name="scale" VALUE="tofit" />
		<!-- <PARAM name="scale" VALUE="showall" /> -->
		<!-- <PARAM name="scale" VALUE="aspect" /> -->
		<!-- <param name="TYPE" value="video/quicktime" /> -->
		<!-- ＜PARAM NAME="_cx" VALUE="16669" /> -->
		<!-- ＜PARAM NAME="_cy" VALUE="2514" />  -->
		 ＜PARAM NAME="Quality" VALUE="High" /> 
		 ＜PARAM NAME="Menu" VALUE="-1" > 
		
		<PARAM name="src" VALUE="" />
		</OBJECT>
		
      </div>
      <!-- 视频播放窗口结束 -->
    </TD>
    <TD ROWSPAN="3" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_08.gif" style="background-image:url(../../model/aboutblank.gif)"></TD>
  </TR>
    
  <TR>
    <TD><TABLE ColorStyleTag="objColorStyleBackColor">
    
	<tr>
	   <td>	       
	      <table>
		 <tr>     
		    <td style="COLOR: rgb(250,0,0)">
		    	<div id="StatusBar" class="StatusBar" style="StatusBar">网梯播放器</div>
		    </td>
		    <td width="23%" id="StatusTime" style="COLOR: rgb(0,0,250)">00:00:00/00:00:00</td>
		 </tr>
	      </table>	       
	   </td>
	</tr>
	
    <TR>
	    
	    
          <TD style="border-width:0" height="16" align="left" id="tdVideoPos"> 

            <!--进度条-->
            <div id="divVideoPosBar" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_09.gif" style="left:0; top:0; height:20; background-image: url(../../model/aboutblank.gif)" OnMouseDown='doMouseDown(this);' OnMouseUp='doMouseUp(this);' ><div>
              <IMG ColorStyleTag="objColorStyleImg" imgFileName="i.gif" title="进度调节" height="9" width="9" id="imgVideoPosBtn" src="../../model/aboutblank.gif" style="position:relative; cursor:hand" OnMouseDown='doMouseDown(this);' OnMouseMove='doMouseMove(this);' OnMouseUp='doMouseUp(this);' />     
            </div></div>
            <!--进度条-->
          </TD>
          
  </TR></TABLE></TD>
  </TR>
  <TR> 
    <TD height="22" align="center" valign="bottom" ColorStyleTag="objColorStyleBackColor" style="background-color:#DEE6F5"> <table width="100%" border="0" cellpadding="0" cellspacing="0" id="tbVideoControlButtons">
        <tr valign="top">
          <td align="center" width="19"><img ColorStyleTag="objColorStyleImg" imgFileName="left_12.gif" src="../../model/aboutblank.gif" width="19" height="18" title="上一张" id="imgFB" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" /></td>

          <td align="center" width="19"><img ColorStyleTag="objColorStyleImg" imgFileName="left_14.gif" src="../../model/aboutblank.gif" width="19" height="18" title="播放" id="imgPlay" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" /></td>
          
          <td align="center" width="19"><img ColorStyleTag="objColorStyleImg" imgFileName="left_16.gif" src="../../model/aboutblank.gif" width="19" height="18" title="下一张" id="imgFF" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" /></td>
          
          <td align="center" width="19"><img ColorStyleTag="objColorStyleImg" imgFileName="left_18.gif" src="../../model/aboutblank.gif" width="19" height="18" title="暂停" id="imgPause" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" /></td>
          
          <td align="center" width="19"><img ColorStyleTag="objColorStyleImg" imgFileName="left_20.gif" src="../../model/aboutblank.gif" width="19" height="18" title="停止" id="imgStop" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" /></td>
          
          <TD align="center" id="tdControlSeparator">.</TD>
          <!--仅播放音频菜单-->
          <TD align="center" width="19" valign="bottom" id="tdAudioOnlyMenu">
          <div onMouseOut="divAudioOnlyMenu.style.display='none';">
            <img id="imgAudioOnlyMenu" ColorStyleTag="objColorStyleImg" src="../../model/aboutblank.gif" imgFileName="left_40.gif" onClick="PopMenu(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" style="width:19" title="视频音频选择" />
            
            <div id="divAudioOnlyMenu" onMouseOver="divAudioOnlyMenu.style.display='block';" style="position:absolute; width:80; background-color:#FFFFFF; display:none; z-index=3; border-style:solid; border-width:1; border-color:#999999">
              <TABLE width="100%" onMouseOver="divAudioOnlyMenu.style.display='block';">
                <TR>
                  <TD id="tdSelAVCheck" width="10" align="center" style="color:#777777">　</TD><TD id="tdSelAV" align="left" class="ListDefault" onMouseOver="this.className='ListMouseOver'" onMouseOut="this.className='ListDefault'" onClick="doMenuItemClick('divAudioOnlyMenu', this);">视频音频</TD>
                </TR>
                <TR>
                  <TD id="tdSelACheck" width="10" align="center" style="color:#777777">　</TD><TD id="tdSelA" align="left" class="ListDefault" onMouseOver="this.className='ListMouseOver'" onMouseOut="this.className='ListDefault'" onClick="doMenuItemClick('divAudioOnlyMenu', this);">仅音频</TD>
                </TR>
              </TABLE>
            </div>
          </div>
          </TD>
          <!--/仅播放音频菜单/-->

          <!--配色方案菜单-->
          <TD align="right" width="19" valign="bottom" id="tdColorStyleMenu">
          <div onMouseOut="divColorStyleMenu.style.display='none';">
            <img id="imgColorStyleMenu" ColorStyleTag="objColorStyleImg" src="../../model/aboutblank.gif" imgFileName="left_41.gif" onClick="PopMenu(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" style="width:19" title="颜色选择" />

            <div id="divColorStyleMenu" onMouseOver="divColorStyleMenu.style.display='block';" align="center" style="position:absolute; width:70; background-color:#FFFFFF; display:none; z-index=3; border-style:solid; border-width:1; border-color:#999999; overflow:auto">
              
            </div>
          </div>
          </TD>
          <!--/配色方案菜单/-->

          <td align="right" width="19"><img ColorStyleTag="objColorStyleImg" imgFileName="left_22.gif" src="../../model/aboutblank.gif" width="19" height="18" title="音量开关" id="imgVolSwitch" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" /></td>
          
          <td align="right" width="19"><img ColorStyleTag="objColorStyleImg" imgFileName="left_24.gif" src="../../model/aboutblank.gif" width="19" height="18" title="全屏幕视频" id="imgVideoFullScreen" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" /></td>

        </tr>
      </table></TD>
  </TR>
  <TR> 
    <TD valign="top" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_29.gif" style="background-image:url(../../model/aboutblank.gif)"> <IMG ColorStyleTag="objColorStyleImg" imgFileName="left_26.gif" SRC="../../model/aboutblank.gif" WIDTH="15" HEIGHT="7" title="" /></TD>
    <TD height="7" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_01.gif" style="background-image:url(../../model/aboutblank.gif)"><img ColorStyleTag="objColorStyleImg" imgFileName="spacer.gif" src="../../model/aboutblank.gif" width="1" height="1" /></TD>
    <TD height="7" valign="top" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_33.gif" style="background-image:url(../../model/aboutblank.gif)"> <div align="right"><IMG ColorStyleTag="objColorStyleImg" imgFileName="left_28.gif" SRC="../../model/aboutblank.gif" WIDTH="16" HEIGHT="7" title="" /></div></TD>
  </TR>
  <TR> 
    <TD ROWSPAN="4" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_29.gif" style="background-image:url(../../model/aboutblank.gif)"> </TD>
    <TD height="14" valign="top" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_30.gif" style="background-image:url(../../model/aboutblank.gif)"> <div align="center"><IMG ColorStyleTag="objColorStyleImg" imgFileName="left_31.gif" SRC="../../model/aboutblank.gif" WIDTH="96" HEIGHT="15" title="" /></div></TD>
    <TD ROWSPAN="4" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_33.gif" style="background-image:url(../../model/aboutblank.gif)"> <div align="right"></div></TD>
  </TR>
  <TR> 
    <TD valign="top" height="50%" style="border-width:1px; border-style:solid; border-color:#828282">
    <!-- 目录树开始 -->
    <div ID="PicView" style="overflow:auto;"></div>
    <div ID="SlideTree" style="border-width:5px;overflow:auto;"></div>
    <!--    <xsl:apply-templates match="courseslides"/> -->
    <!-- 目录数结束 -->
	  </TD>
  </TR>

  <TR> 
    <TD align="center" ColorStyleTag="objColorStyleBackColor" style="background-color:#E2EAF8" height="18">
      <table width="100%" border="0" cellspacing="4" cellpadding="0">
        <tr align="center">
          <td width="33">

              <img id="imgExpand" src="../../model/aboutblank.gif" ColorStyleTag="objColorStyleImg" imgFileName="zk_01.gif" width="33" height="17" border="0" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" onClick="ExpandTree(); return false;"></img>
          </td>
          <td width="33">
              <img id="imgCollapse" src="../../model/aboutblank.gif" ColorStyleTag="objColorStyleImg" imgFileName="ss_01.gif" width="33" height="17" border="0"  onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" onClick="CollapseTree(); return false;"></img>
          </td>

          <td align="right">
              <img id="imgChangeView" src="../../model/aboutblank.gif" ColorStyleTag="objColorStyleImg" imgFileName="qh_01.gif" width="59" height="17" border="0" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" onClick="ChangePicView(); return false;"></img>
          </td>

        </tr>
      </table> 
    </TD>
  </TR>
  <SCRIPT LANGUAGE="JavaScript">
      InitList(false);
  </SCRIPT>

</TABLE>

</BODY>
</HTML>

</xsl:template>

</xsl:stylesheet>
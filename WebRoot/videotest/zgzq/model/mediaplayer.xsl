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
<script type="text/javascript" language="JavaScript" src="../../model/mediaplayer.js" charset="gb2312"></script>
<script type="text/javascript" language="JavaScript" src="../../model/setVolume.js" charset="gb2312"></script>

<!-- 调用库结束 -->
</HEAD>
<BODY BGCOLOR="#FFFFFF" LEFTMARGIN="0" TOPMARGIN="0" MARGINWIDTH="0" MARGINHEIGHT="0" OnResize="divResize()" OnLoad="Init()" onContextMenu="return false;"><!-- onContextMenu="return false;"-->
<TABLE WIDTH="160" height="100%" BORDER="0" CELLPADDING="0" CELLSPACING="0">
  <TR>
    <TD width="15" valign="top" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_02.gif" style="background-image:url(../../model/aboutblank.gif)">
      <!--<IMG SRC="../../model/aboutblank.gif" WIDTH="15" HEIGHT="21"/>-->
    </TD>
    <TD width="100%" height="21" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_03.gif" style="background-image:url(../../model/aboutblank.gif)"></TD>
    <TD width="16" valign="top" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_04.gif" style="background-image:url(../../model/aboutblank.gif)">
      <!--<IMG SRC="../../model/aboutblank.gif" WIDTH="16" HEIGHT="21"/>-->
    </TD>
  </TR>
  
  
  
  <TR style="display:none">
    <TD height="0" style="background-color:#ffffff" ID="tdVideoBackground">
      <!-- 图片窗口 -->
      <!-- 视频播放窗口开始 -->
      <div id="divMediaPlayer" style="position:absolute; z-index:1; left:16px; height=0px; top:22px;" PhotoType="">
        <div id="divVideoPhotoImg" style="display:none; height:0;">
          <IMG id="imgVideoPhotoImg" src="../../model/aboutblank.gif" width="100%" height="0" ondrag="return false" />
        </div>
        <div id="divVideoPhotoSwf" style="display:none; height:0;"></div>
      </div>
      <!-- 视频播放窗口结束 -->
    </TD>
  </TR>
  
  
  
  

  <TR> 
    <TD ROWSPAN="4" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_29.gif" style="background-image:url(../../model/aboutblank.gif)"> </TD>
    <TD height="14" valign="top" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_30.gif" style="background-image:url(../../model/aboutblank.gif)"> <div align="center"><IMG ColorStyleTag="objColorStyleImg" imgFileName="left_31.gif" SRC="../../model/aboutblank.gif" WIDTH="96" HEIGHT="15" title="" ondrag="return false" /></div></TD>
    <TD ROWSPAN="4" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_33.gif" style="background-image:url(../../model/aboutblank.gif)"> <div align="right"></div></TD>
  </TR>
  <TR> 
    <!--<TD valign="top" height="50%" style="border-width:1px; border-style:solid; border-color:#828282">-->
    <TD valign="top" id="tdListView" style="border-width:1px; border-style:solid; border-color:#828282">
    <!-- 目录树开始 -->
    <div ID="PicView" style="overflow:auto;"></div>
    <div ID="SlideTree" style="border-width:5px;overflow:auto;"></div>
    <!--    <xsl:apply-templates match="courseslides"/> -->
    <!-- 目录数结束 -->
	  </TD>
  </TR> 
  <TR>
    <TD><TABLE ColorStyleTag="objColorStyleBackColor"><TR>
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
          <td align="center" width="19"><img ColorStyleTag="objColorStyleImg" imgFileName="left_12.gif" src="../../model/aboutblank.gif" width="19" height="18" title="快退" id="imgFB" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" ondrag="return false" /></td>

          <td align="center" width="19"><img ColorStyleTag="objColorStyleImg" imgFileName="left_14.gif" src="../../model/aboutblank.gif" width="19" height="18" title="播放" id="imgPlay" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" ondrag="return false" /></td>
          
          <td align="center" width="19"><img ColorStyleTag="objColorStyleImg" imgFileName="left_16.gif" src="../../model/aboutblank.gif" width="19" height="18" title="快进" id="imgFF" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" ondrag="return false" /></td>
          
          <td align="center" width="19"><img ColorStyleTag="objColorStyleImg" imgFileName="left_18.gif" src="../../model/aboutblank.gif" width="19" height="18" title="暂停" id="imgPause" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" ondrag="return false" /></td>
          
          <td align="center" width="19"><img ColorStyleTag="objColorStyleImg" imgFileName="left_20.gif" src="../../model/aboutblank.gif" width="19" height="18" title="停止" id="imgStop" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" ondrag="return false" /></td>
          
          <TD align="center" id="tdControlSeparator">.</TD>
          <!--仅播放音频菜单-->
          <TD align="left" width="1" valign="bottom" id="tdAudioOnlyMenu">
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
          <TD align="left" width="1" valign="bottom" id="tdColorStyleMenu">
          <div onMouseOut="divColorStyleMenu.style.display='none';">
        <!--  <img id="imgColorStyleMenu" ColorStyleTag="objColorStyleImg" src="../../model/aboutblank.gif" imgFileName="left_41.gif" onClick="PopMenu(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" style="width:19" title="颜色选择" />  -->

            <div id="divColorStyleMenu" onMouseOver="divColorStyleMenu.style.display='block';" align="center" style="position:absolute; width:70; background-color:#FFFFFF; display:none; z-index=3; border-style:solid; border-width:1; border-color:#999999; overflow:auto">
              
            </div>
          </div>
          </TD>
          <!--/配色方案菜单/-->

          <td align="left" width="19">
          	<img ColorStyleTag="objColorStyleImg" imgFileName="left_22.gif" src="../../model/aboutblank.gif" width="19" height="18" title="音量调节" id="imgVolSwitch" onClick="showHideDiv('volumeContral')" onMouseOut="SwapImgRestore(this);leave('volumeContral')" onMouseOver="SwapImage(this);over()" ondrag="return false" />
						<div id="volumeContral" ColorStyleTag="objColorStyleBackImg"  backimgFileName="volumeContral.gif" style="position:absolute;padding-top:0px;left:112px;bottom:26px;width:80px;height:18px;border:0px solid #003366;background-image:url(../../model/aboutblank.gif);display:none" onmouseover="over()" onmouseout="leave('volumeContral')" onselectstart="return false">
							<img id="volumeBt" onOver = "false" ColorStyleTag="objColorStyleImg" imgFileName="volumeBt.gif" src="../../model/aboutblank.gif" style="position:absolute;left:64px;top:4px;cursor:hand;width:6px;height:10px;border:0px solid #ffffff;background-color:#003366;font-size:1px;" onmousemove="domousemoves(this.id)" onmouseup="this.onOver = 'false';leave('volumeContral')" onmousedown="this.onOver = 'true'" />
						</div>
          </td>
          
          <td align="left" width="19" style="display:none"><img ColorStyleTag="objColorStyleImg" imgFileName="left_24.gif" src="../../model/aboutblank.gif" width="19" height="18" title="全屏幕视频" id="imgVideoFullScreen" onClick="doControlClick(this);" onMouseOut="SwapImgRestore(this)" onMouseOver="SwapImage(this)" ondrag="return false" /></td>

        </tr>
      </table></TD>
  </TR>
  <TR> 
    <TD valign="top" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_29.gif" style="background-image:url(../../model/aboutblank.gif)"> <IMG ColorStyleTag="objColorStyleImg" imgFileName="left_26.gif" SRC="../../model/aboutblank.gif" WIDTH="15" HEIGHT="7" title="" ondrag="return false" /></TD>
    <TD height="7" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_01.gif" style="background-image:url(../../model/aboutblank.gif)"><img ColorStyleTag="objColorStyleImg" imgFileName="spacer.gif" src="../../model/aboutblank.gif" width="1" height="1" ondrag="return false" /></TD>
    <TD height="7" valign="top" ColorStyleTag="objColorStyleBackImg" backimgFileName="left_33.gif" style="background-image:url(../../model/aboutblank.gif)"> <div align="right"><IMG ColorStyleTag="objColorStyleImg" imgFileName="left_28.gif" SRC="../../model/aboutblank.gif" WIDTH="16" HEIGHT="7" title="" ondrag="return false" /></div></TD>
  </TR>
</TABLE>
  <SCRIPT LANGUAGE="JavaScript">
      InitList(false);
  </SCRIPT>
</BODY>
</HTML>

</xsl:template>

</xsl:stylesheet>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%
String d=String.valueOf(Math.random());
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<o:SmartTagType namespaceuri="urn:schemas-microsoft-com:office:smarttags"
 name="chmetcnv" downloadurl=""/>
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>acer</o:Author>
  <o:LastAuthor>MC SYSTEM</o:LastAuthor>
  <o:Revision>2</o:Revision>
  <o:TotalTime>21</o:TotalTime>
  <o:LastPrinted>2011-03-30T06:22:00Z</o:LastPrinted>
  <o:Created>2011-04-01T11:00:00Z</o:Created>
  <o:LastSaved>2011-04-01T11:00:00Z</o:LastSaved>
  <o:Pages>1</o:Pages>
  <o:Words>23</o:Words>
  <o:Characters>135</o:Characters>
  <o:Lines>1</o:Lines>
  <o:Paragraphs>1</o:Paragraphs>
  <o:CharactersWithSpaces>157</o:CharactersWithSpaces>
  <o:Version>11.5606</o:Version>
 </o:DocumentProperties>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:SpellingState>Clean</w:SpellingState>
  <w:GrammarState>Clean</w:GrammarState>
  <w:PunctuationKerning/>
  <w:DrawingGridVerticalSpacing>7.8 磅</w:DrawingGridVerticalSpacing>
  <w:DisplayHorizontalDrawingGridEvery>0</w:DisplayHorizontalDrawingGridEvery>
  <w:DisplayVerticalDrawingGridEvery>2</w:DisplayVerticalDrawingGridEvery>
  <w:ValidateAgainstSchemas/>
  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>
  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>
  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>
  <w:Compatibility>
   <w:SpaceForUL/>
   <w:BalanceSingleByteDoubleByteWidth/>
   <w:DoNotLeaveBackslashAlone/>
   <w:ULTrailSpace/>
   <w:DoNotExpandShiftReturn/>
   <w:AdjustLineHeightInTable/>
   <w:BreakWrappedTables/>
   <w:SnapToGridInCell/>
   <w:WrapTextWithPunct/>
   <w:UseAsianBreakRules/>
   <w:DontGrowAutofit/>
   <w:UseFELayout/>
  </w:Compatibility>
  <w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel>
 </w:WordDocument>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:LatentStyles DefLockedState="false" LatentStyleCount="156">
 </w:LatentStyles>
</xml><![endif]--><!--[if !mso]><object
 classid="clsid:38481807-CA0E-42D2-BF39-B33AF135CC4D" id=ieooui></object>
<style>
st1\:*{behavior:url(#ieooui) }
</style>
<![endif]-->
<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:宋体;
	panose-1:2 1 6 0 3 1 1 1 1 1;
	mso-font-alt:SimSun;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:3 135135232 16 0 262145 0;}
@font-face
	{font-family:"\@宋体";
	panose-1:2 1 6 0 3 1 1 1 1 1;
	mso-font-charset:134;
	mso-generic-font-family:auto;
	mso-font-pitch:variable;
	mso-font-signature:3 135135232 16 0 262145 0;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{mso-style-parent:"";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:10.5pt;
	mso-bidi-font-size:12.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
p.MsoAcetate, li.MsoAcetate, div.MsoAcetate
	{mso-style-noshow:yes;
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	mso-pagination:none;
	font-size:9.0pt;
	font-family:"Times New Roman";
	mso-fareast-font-family:宋体;
	mso-font-kerning:1.0pt;}
 /* Page Definitions */
 @page
	{mso-page-border-surround-header:no;
	mso-page-border-surround-footer:no;}
@page Section1
	{size:595.3pt 841.9pt;
	margin:72.0pt 90.0pt 72.0pt 90.0pt;
	mso-header-margin:42.55pt;
	mso-footer-margin:49.6pt;
	mso-paper-source:0;
	layout-grid:15.6pt;}
div.Section1
	{page:Section1;}
 /* List Definitions */
 @list l0
	{mso-list-id:960693258;
	mso-list-type:hybrid;
	mso-list-template-ids:1980501470 391646966 67698713 67698715 67698703 67698713 67698715 67698703 67698713 67698715;}
@list l0:level1
	{mso-level-text:%1、;
	mso-level-tab-stop:18.0pt;
	mso-level-number-position:left;
	margin-left:18.0pt;
	text-indent:-18.0pt;}
ol
	{margin-bottom:0cm;}
ul
	{margin-bottom:0cm;}
-->
</style>
<!--[if gte mso 10]>
<style>
 /* Style Definitions */
 table.MsoNormalTable
	{mso-style-name:普通表格;
	mso-tstyle-rowband-size:0;
	mso-tstyle-colband-size:0;
	mso-style-noshow:yes;
	mso-style-parent:"";
	mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
	mso-para-margin:0cm;
	mso-para-margin-bottom:.0001pt;
	mso-pagination:widow-orphan;
	font-size:10.0pt;
	font-family:"Times New Roman";
	mso-ansi-language:#0400;
	mso-fareast-language:#0400;
	mso-bidi-language:#0400;}
</style>
<![endif]--><!--[if gte mso 9]><xml>
 <o:shapedefaults v:ext="edit" spidmax="2050"/>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <o:shapelayout v:ext="edit">
  <o:idmap v:ext="edit" data="1"/>
 </o:shapelayout></xml><![endif]-->
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>中国证劵业协会培训平台</title>
<link href="/entity/bzz-students/css1.css" rel="stylesheet" type="text/css" />
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2k-cold-1.css" title="win2k-cold-1"/>
<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<script language="javascript" src="/js/calender.js"></script>	
<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
.STYLE1 {font-size: 12px; color: #0041A1; font-weight: bold; }
.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}
.kctx_zi1 {
	font-size: 12px;
	color: #0041A1;
}
.kctx_zi2 {
	font-size: 12px;
	color: #54575B;
	line-height: 24px;
	padding-top:2px;
}
-->
</style>
<script language="javascript" >
	function checknull(){
		var Phone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,8}(\-\d{1,5})?$/;
		var Mobile = /^((\(\d{2,3}\))|(\d{3}\-))?1[3|5|8]\d{9}$/;
		var Email = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
		var  elem = document.getElementsByTagName("input");
		for(k =0 ;k<elem.length;k++){
			if(elem[k].type=='text'&&elem[k].value.length<1){
				alert("至少有一项信息未填写,请您完善您的资料！");
				return false;
			}else{
				if(elem[k].name=="peBzzStudent.mobilePhone"&&!Mobile.test(elem[k].value)){
						alert("移动电话格式不正确!");
						return false;
			    }
				if(elem[k].name=="peBzzStudent.phone"&&!Phone.test(elem[k].value)){
						alert("办公电话格式不正确!");
						return false;
				}
				if(elem[k].name=="peBzzStudent.email"&&!Email.test(elem[k].value)){
						alert("电子邮件格式不正确!");
						return false;
				}
				if(elem[k].type=='file' && elem[k].value.length > 5) {
					var Extlist = ".gif.jpg.bmp.png.GIF.JPG.BMP.PNG";
				    
					var arrList = elem[k].value.split(".");
					var ext = arrList[arrList.length-1];
					
					if(Extlist.indexOf(ext)==-1) {
						alert("相片格式不正确，请上传gif,jpg,bmp或png格式图片!");
						return false;
					}
					
				}
				if(elem[k].type=='file' && elem[k].value.length ==0) {
						alert("请上传标准照片!");
						return false;
					
				}
			}
		}
	}
</script>
</head>
<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <IFRAME NAME="top" width=100% height=200 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/top.jsp" scrolling=no allowTransparency="true"></IFRAME>
  <tr>
    <td height="13"></td>
  </tr>
</table>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="237" valign="top"><IFRAME NAME="leftA" width=237 height=580 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/left.jsp?d=<%=d %>" scrolling=no allowTransparency="true"></IFRAME></td>
   <td width="752" valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr align="center" valign="top">
        <td height="17" align="left" class="twocentop">	<div class="content_title"><img src="/entity/bzz-students/images/two/1.jpg" width="11" height="12" />当前位置：个人信息</div>
	</td>
      </tr>
      <tr>
      	<td>&nbsp;</td>
      </tr>
   <tr align="center">
        <td height="29" background="/entity/bzz-students/images/two/two2_r15_c5.jpg"><table width="96%" border="0" cellpadding="0" cellspacing="0" class="twocencetop">
          <tr>
          <td width="5%" align="center"></td>
            <td width="45%" align="left">&lt;&lt; <font color="red"><s:property value="peBzzStudent.trueName"/></font>&gt;&gt; 的个人信息: </td>
          </tr>
        </table></td>
      </tr>
       <table width="85%" border="0" align="center" cellpadding="0" cellspacing="0" style="border:solid 2px #9FC3FF; padding:5px; margin:15px 0px 15px 0px;">
  <tr>
    <td>
    <form action="/entity/workspaceStudent/bzzstudent_modifyPhoto.action" method="post" name="infoform" enctype="multipart/form-data" onsubmit="return checknull();" >
    <input type="hidden" name="peBzzStudent.id" value="<s:property value="peBzzStudent.id"/>"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
     <tr>
        <td align="center" bgcolor="#E9F4FF" class="STYLE1" > &nbsp;个人照片：</td>
        <td align="left" bgcolor="#FAFAFA" class="kctx_zi2" colspan="2"> &nbsp; <input type="file" name="photo"/>
        &nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="上 &nbsp;传" stype="cursor:hand"/>
        &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="返 &nbsp;回" stype="cursor:hand" onclick="javascript:window.navigate('/entity/workspaceStudent/bzzstudent_StudentInfo.action')"/>
         </td>
     </tr>
     </table>
         </form>
      <br/><br/><br/><br/>
      <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#D7D7D7">
      <tr>
      	<p class=MsoNormal align=center style='text-align:center'><b style='mso-bidi-font-weight:
			normal'><span style='font-size:15.0pt;font-family:宋体;mso-ascii-font-family:
			"Times New Roman";mso-hansi-font-family:"Times New Roman"'>中国证券业协会远程培训系统</span></b><b
			style='mso-bidi-font-weight:normal'><span lang=EN-US style='font-size:15.0pt'><o:p></o:p></span></b></p>
			
			<p class=MsoNormal style='margin-left:17.85pt;text-indent:-17.85pt;line-height:
			150%;mso-list:l0 level1 lfo1;tab-stops:list 18.0pt'><![if !supportLists]><span
			lang=EN-US style='font-size:12.0pt;line-height:150%;mso-fareast-font-family:
			"Times New Roman"'><span style='mso-list:Ignore'>1、</span></span><![endif]><span
			style='font-size:12.0pt;line-height:150%;font-family:宋体;mso-ascii-font-family:
			"Times New Roman";mso-hansi-font-family:"Times New Roman"'>照片尺寸：</span><st1:chmetcnv
			TCSC="0" NumberType="1" Negative="False" HasSpace="False" SourceValue="4.8"
			UnitName="cm" w:st="on"><span lang=EN-US style='font-size:12.0pt;line-height:
			 150%'>4.8cm</span></st1:chmetcnv><span lang=EN-US style='font-size:12.0pt;
			line-height:150%'>*<st1:chmetcnv TCSC="0" NumberType="1" Negative="False"
			HasSpace="False" SourceValue="3.3" UnitName="cm" w:st="on">3.3cm</st1:chmetcnv><o:p></o:p></span></p>
			
			<p class=MsoNormal style='margin-left:17.85pt;text-indent:-17.85pt;line-height:
			150%;mso-list:l0 level1 lfo1;tab-stops:list 18.0pt'><![if !supportLists]><span
			lang=EN-US style='font-size:12.0pt;line-height:150%;mso-fareast-font-family:
			"Times New Roman"'><span style='mso-list:Ignore'>2、</span></span><![endif]><span
			style='font-size:12.0pt;line-height:150%;font-family:宋体;mso-ascii-font-family:
			"Times New Roman";mso-hansi-font-family:"Times New Roman"'>照片像素不低于：</span><span
			lang=EN-US style='font-size:12.0pt;line-height:150%'>567*390<o:p></o:p></span></p>
			
			<p class=MsoNormal style='margin-left:17.85pt;text-indent:-17.85pt;line-height:
			150%;mso-list:l0 level1 lfo1;tab-stops:list 18.0pt'><![if !supportLists]><span
			lang=EN-US style='font-size:12.0pt;line-height:150%;mso-fareast-font-family:
			"Times New Roman"'><span style='mso-list:Ignore'>3、</span></span><![endif]><span
			style='font-size:12.0pt;line-height:150%;font-family:宋体;mso-ascii-font-family:
			"Times New Roman";mso-hansi-font-family:"Times New Roman"'>样片</span><span
			lang=EN-US style='font-size:12.0pt;line-height:150%;mso-no-proof:yes'>.</span><span
			lang=EN-US style='font-size:12.0pt;line-height:150%'><o:p></o:p></span></p>
			
			<p class=MsoNormal style='text-indent:68.25pt;mso-char-indent-count:6.5'><span
			lang=EN-US style='mso-no-proof:yes'><img width=119 height=176
			src="/entity/bzz-students/images/semple1.jpg" alt=11.jpg v:shapes="图片_x0020_0"><o:p></o:p></span></p>
			
			<p class=MsoNormal style='margin-left:18.0pt;text-indent:-18.0pt;mso-list:l0 level1 lfo1;
			tab-stops:list 18.0pt'><![if !supportLists]><span lang=EN-US style='font-size:
			15.0pt;mso-fareast-font-family:"Times New Roman"'><span style='mso-list:Ignore'>4、<span
			style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</span></span></span><![endif]><span style='font-size:15.0pt;font-family:宋体;
			mso-ascii-font-family:"Times New Roman";mso-hansi-font-family:"Times New Roman"'>不合格照片举例：</span><span
			lang=EN-US style='font-size:15.0pt'><o:p></o:p></span></p>
			
			<p class=MsoNormal><span lang=EN-US style='font-size:15.0pt'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img width=133 height=193
			src="/entity/bzz-students/images/semple2.jpg" v:shapes="_x0000_i1026"><span
			style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img width=132 height=192
			src="/entity/bzz-students/images/semple3.jpg" v:shapes="_x0000_i1027"><span
			style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img width=127 height=184
			src="/entity/bzz-students/images/semple4.jpg" v:shapes="_x0000_i1028"><o:p></o:p></span></p>
			
			<p class=MsoNormal><span style='font-size:15.0pt;font-family:宋体;mso-ascii-font-family:
			"Times New Roman";mso-hansi-font-family:"Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;身体倾斜</span><span
			lang=EN-US style='font-size:15.0pt'><span
			style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</span></span><span style='font-size:15.0pt;font-family:宋体;mso-ascii-font-family:
			"Times New Roman";mso-hansi-font-family:"Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;照片比例失调</span><span
			lang=EN-US style='font-size:15.0pt'><span
			style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span
			style='font-size:15.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
			mso-hansi-font-family:"Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;头像过低</span><span lang=EN-US
			style='font-size:15.0pt'><o:p></o:p></span></p>
			
			<p class=MsoNormal><span lang=EN-US style='font-size:15.0pt'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img width=132 height=192
			src="/entity/bzz-students/images/semple5.jpg" v:shapes="_x0000_i1029"><span
			style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img width=128 height=188
			src="/entity/bzz-students/images/semple6.jpg" v:shapes="_x0000_i1030"><span
			style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;</span><o:p></o:p></span></p>
			
			<p class=MsoNormal style='text-indent:15.0pt;mso-char-indent-count:1.0'><span
			style='font-size:15.0pt;font-family:宋体;mso-ascii-font-family:"Times New Roman";
			mso-hansi-font-family:"Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;照片模糊</span><span lang=EN-US
			style='font-size:15.0pt'><span
			style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</span></span><span style='font-size:15.0pt;font-family:宋体;mso-ascii-font-family:
			"Times New Roman";mso-hansi-font-family:"Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位置不正</span><span
			lang=EN-US style='font-size:15.0pt'><o:p></o:p></span></p>
			
			<p class=MsoNormal><span lang=EN-US style='mso-no-proof:yes'><o:p>&nbsp;</o:p></span></p>

      </tr>
    </table></td>
  </tr>
</table>
    </td>
    <td width="13">&nbsp;</td>
  </tr>
</table>
<IFRAME NAME="top" width=1002 height=73 frameborder=0 marginwidth=0 marginheight=0 SRC="/entity/bzz-students/pub/bottom.jsp" scrolling=no allowTransparency="true" align=center></IFRAME>
</td>
</tr>
</table>
</body>
</html>
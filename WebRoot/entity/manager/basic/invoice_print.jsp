<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=dd.doc"); %>
<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:w="urn:schemas-microsoft-com:office:word" xmlns="http://www.w3.org/TR/REC-html40">
<head>
<meta http-equiv=Content-Type  content="text/html; charset=utf-8" >
<title>1</title><!--[if gte mso 9]><xml><w:WordDocument><w:BrowserLevel>MicrosoftInternetExplorer4</w:BrowserLevel><w:DisplayHorizontalDrawingGridEvery>0</w:DisplayHorizontalDrawingGridEvery><w:DisplayVerticalDrawingGridEvery>2</w:DisplayVerticalDrawingGridEvery><w:DocumentKind>DocumentNotSpecified</w:DocumentKind><w:DrawingGridVerticalSpacing>7.8</w:DrawingGridVerticalSpacing><w:View>Web</w:View><w:Compatibility></w:Compatibility><w:Zoom>0</w:Zoom></w:WordDocument></xml><![endif]--><style>
@font-face{
font-family:"Times New Roman";
}

@font-face{
font-family:"&#23435;&#20307;";
}

@font-face{
font-family:"Symbol";
}

@font-face{
font-family:"Arial";
}

@font-face{
font-family:"&#40657;&#20307;";
}

@font-face{
font-family:"Courier New";
}

@font-face{
font-family:"Wingdings";
}

p.p0{
margin:0pt;
margin-bottom:0.0001pt;
margin-bottom:0pt;
margin-top:0pt;
text-align:justify;
font-size:10.5000pt; font-family:'Times New Roman'; }

span.10{
font-size:10.0000pt; font-family:'Times New Roman'; }

p.p15{
margin-bottom:0pt;
margin-top:0pt;
text-align:justify;
font-size:9.0000pt; font-family:'Times New Roman'; }
@page{mso-page-border-surround-header:no;
	mso-page-border-surround-footer:no;}@page Section0{
margin-top:28.3500pt;
margin-bottom:56.7000pt;
margin-left:23.8000pt;
margin-right:72.0000pt;
size:567.0000pt 283.5000pt;
layout-grid:15.6000pt;
mso-page-orientation:landscape;
}
div.Section0{page:Section0;}</style>
</head>
<body style="tab-interval:21pt; " ><!--StartFragment-->
<s:if test="getPrintInvoiceInfo()!=null">
<s:iterator value="getPrintInvoiceInfo()" status="i">
<s:if test="#i.index!=0">
<br clear=all style='page-break-before:always'>
</s:if>
<div class="Section0"  style="layout-grid:15.6000pt;
" ><p class=p0  style="margin-bottom:0pt; margin-top:0pt; " >
<span style="mso-spacerun:'yes'; font-size:10.5000pt; font-family:'&#23435;&#20307;'; " >
<s:property value="zipCode" /></span>
<span style="mso-spacerun:'yes'; font-size:10.5000pt; font-family:'&#23435;&#20307;'; " >
<o:p></o:p></span>
</p>
<p class=p0  style="margin-bottom:0pt; margin-top:0pt; " >
<span style="mso-spacerun:'yes'; font-size:10.5000pt; font-family:'&#23435;&#20307;'; " >
<o:p></o:p></span></p>
<p class=p0  style="margin-bottom:0pt; margin-top:0pt; " >
<span style="mso-spacerun:'yes'; font-size:10.5000pt; font-family:'&#23435;&#20307;'; " >
<o:p></o:p></span></p>
<p class=p0  style="margin-bottom:0pt; margin-top:0pt; " >
<span style="mso-spacerun:'yes'; font-size:10.5000pt; font-family:'&#23435;&#20307;'; " >
<o:p></o:p></span></p>
<p class=p0  style="margin-left:450.0000pt; margin-bottom:0pt; margin-top:0pt; " >
<span style="mso-spacerun:'yes'; font-size:10.5000pt; font-family:'&#23435;&#20307;'; " >
<o:p></o:p></span></p>
<p class=p0  style="margin-bottom:0pt; margin-top:0pt; " >
<span style="mso-spacerun:'yes'; font-size:10.5000pt; font-family:'&#23435;&#20307;'; " >
<o:p></o:p></span></p>
<p class=p0  style="margin-left:21.0000pt; text-indent:21.0000pt; margin-bottom:0pt; margin-top:0pt; " >
<span style="mso-spacerun:'yes'; font-size:12.0000pt; font-family:'&#23435;&#20307;'; " >
<s:property value="address" /></span>
<span style="mso-spacerun:'yes'; font-size:12.0000pt; font-family:'&#23435;&#20307;'; " >
<o:p></o:p></span></p>
<p class=p0  style="text-indent:21.0000pt; margin-bottom:0pt; margin-top:0pt; " >
<span style="mso-spacerun:'yes'; font-size:12.0000pt; font-family:'&#23435;&#20307;'; " >
&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="city" /></span>
<span style="mso-spacerun:'yes'; font-size:12.0000pt; font-family:'&#23435;&#20307;'; " >
<o:p></o:p></span></p>
<p class=p0  style="text-indent:21.0000pt; margin-bottom:0pt; margin-top:0pt; " >
<span style="mso-spacerun:'yes'; font-size:12.0000pt; font-family:'&#23435;&#20307;'; " >
<o:p></o:p></span></p>
<p class=p0  style="margin-left:42.0000pt; text-indent:24.0000pt; margin-bottom:0pt; margin-top:0pt; " >
<span style="mso-spacerun:'yes'; font-size:12.0000pt; font-family:'&#23435;&#20307;'; " >
<s:property value="addressee" />(收)</span><span style="mso-spacerun:'yes'; font-size:12.0000pt; font-family:'&#23435;&#20307;'; " >
<o:p></o:p></span></p>
<p class=p0  style="margin-left:42.0000pt; text-indent:24.0000pt; margin-bottom:0pt; margin-top:0pt; " >
<span style="mso-spacerun:'yes'; font-size:12.0000pt; font-family:'&#23435;&#20307;'; " >
<o:p></o:p></span></p>
<p class=p0  style="margin-left:42.0000pt; text-indent:24.0000pt; margin-bottom:0pt; margin-top:0pt; " >
<span style="mso-spacerun:'yes'; font-style:italic; font-size:12.0000pt; font-family:'&#23435;&#20307;'; " >
发票金额</span>
<span style="mso-spacerun:'yes'; font-size:12.0000pt; font-family:'&#23435;&#20307;'; " >
:<s:property value="peBzzOrderInfo.amount" /></span><span style="mso-spacerun:'yes'; font-size:12.0000pt; font-family:'&#23435;&#20307;'; " >
<o:p></o:p></span>
</p></div>
<!--EndFragment-->
</s:iterator>
</s:if>
</body>
</html>
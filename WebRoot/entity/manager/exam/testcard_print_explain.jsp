<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<title>准考证打印说明</title>
<link href="/entity/function/css/css.css" rel="stylesheet" type="text/css">
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
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" ></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="/entity/function/images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>准考证打印</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="/entity/function/images/table_02.gif" >
				  <table width="90%" border="0" align="center" cellpadding="0" cellspacing="1" class="bg4" bgcolor="#D7D7D7">
                          <tr bgcolor="#ECECEC">
                          <td bgcolor="#FAFAFA"><br/><font color="red" size="3">打印注意事项：</font>
                          <br/>
                          1、只打印考试报名审核通过学生的准考证；<br/>
                          2、点击“选择打印准考证”或“打印当前批次全部准考证”后进入准考证打印页面；<br/>
                          3、在打印页面，点击“页面设置”；<br/>
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/entity/function/images/testcard1.jpg" alt=""/><br/>
                          4、将其中的页眉和页脚的设置都置为“空”，并且将左右边距设为19.05，上下边距设为5；<br/>
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/entity/function/images/testcard2.jpg" alt=""/><br/>
                          5、点击“确定”后再点击浏览器的“打印”按钮即可打印。<br/><br/>
                          </td>
					       </tr>
					       <tr bgcolor="#ECECEC">
                          <td bgcolor="#FAFAFA" align="center">
                          <input type="button" value="关闭"  onClick="javascript:window.close()" />
                          </td>
					       </tr>
				  </table>
				</td>
			  </tr>
              <tr>
		         <td><img src="/entity/function/images/table_03.gif" width="765" height="21"></td>
              </tr>
          </table>
         </td>
        </tr>
</table>
</body>
</html>
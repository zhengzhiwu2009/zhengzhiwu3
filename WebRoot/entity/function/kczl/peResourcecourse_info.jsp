<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/htm清华大学继续教育学院harset=utf-8">
<title>清华大学继续教育学院</title>
<link href="../css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="/entity/function/images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="/entity/function/images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="/entity/function/images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="/entity/function/images/xb3.gif" width="17" height="15"> 
                              <strong>参考资料</strong>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                    <input type=hidden name=coursename value=<s:property value="#request.coursename" />>
                      <td background="/entity/function/images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td>
                           <s:iterator value='knowledeListxq' id='knowledge'>	
	                        <tr>
	                        	 <td class="neirong" width=20%>
									<font size=2>题目：</font>
								</td>
								<td class="neirong" valign=top>
									<font size=2> <s:property value="#knowledge[0]" />
								</td>
							 </tr>
                          	<tr>
                          		<td class="neirong" valign=top width=20%>
									<font size=2>资料描述：</font>
								</td>
							<td class="neirong" valign=top>
								<font size=2><s:property value="#knowledge[1]" />
							</td>
						  </tr>
						   	<tr>
                          		<td class="neirong" valign=top width=20%>
									<font size=2>资料内容：</font>
								</td>
							<td class="neirong" valign=top>
							<font size=2>	<s:property value="#knowledge[2]" escape="false"/>
							</td>
						  </tr>
						</s:iterator>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="/entity/function/images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><input type=button value="返回" onclick="history.go(-1)"></td>
		</tr>
                  </table> </td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>

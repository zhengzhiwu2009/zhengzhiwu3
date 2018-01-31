<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.test.*,com.whaty.platform.test.question.*,com.whaty.platform.test.lore.*"%>
<%@ page import="com.whaty.platform.test.TestManage" %>
<%@ include file="../pub/priv.jsp"%>
<%
	String loreId = request.getParameter("loreId");
	String loreDirId = request.getParameter("loreDirId");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>知识点题库添加</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
<script>
	function onSelect(idVal) {
		var obj = document.getElementById(idVal);
		//alert(document.ctcontent.location);
		if(obj.value == "danxuan") {
			parent.ctcontent.location = "store_question_single.jsp?loreId=<%=loreId%>&loreDirId=<%=loreDirId%>&type="
					+ obj.value;
		}
		if(obj.value == "duoxuan") {
			parent.ctcontent.location = "store_question_multi.jsp?loreId=<%=loreId%>&loreDirId=<%=loreDirId%>&type="
					+ obj.value;
		}
		if(obj.value == "panduan") {
			parent.ctcontent.location = "store_question_judge.jsp?loreId=<%=loreId%>&loreDirId=<%=loreDirId%>&type="
					+ obj.value;
		}
		if(obj.value == "tiankong") {
			parent.ctcontent.location = "store_question_blank.jsp?loreId=<%=loreId%>&loreDirId=<%=loreDirId%>&type="
					+ obj.value;
		}
		if(obj.value == "wenda") {
			parent.ctcontent.location = "store_question_answer.jsp?loreId=<%=loreId%>&loreDirId=<%=loreDirId%>&type="
					+ obj.value;
		}
		if(obj.value == "yuedu") {
			parent.ctcontent.location = "store_question_comprehension.jsp?loreId=<%=loreId%>&loreDirId=<%=loreDirId%>&type="
					+ obj.value;
		}
	}
</script>
</head>

<body leftmargin="0" topmargin="0"  background="images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
              <tr>
                
          <td align="center" valign="top"><table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" align="center" >
              <tr> 
                <td align="center" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" align="center" >
                    <tr> 
                      
                      <td style="padding-left:25px"><table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                      	<td colspan=15 height=20></td>
                      </tr>
                          <tr> 
                            <td class="text4">客观题:</td>
                            <td><input type="radio" id="single" name="radiobutton" value="danxuan" checked onclick="onSelect(this.id)">&nbsp;</td>
                            <td class="text4">单项选择题</td>
                            <td><input type="radio" id="multi" name="radiobutton" value="duoxuan" onclick="onSelect(this.id)">&nbsp;</td>
                            <td class="text4">多项选择题</td>
                            <td><input type="radio" id="judge" name="radiobutton" value="panduan" onclick="onSelect(this.id)">&nbsp;</td>
                            <td class="text4">判断题</td>
                            <td width=100>&nbsp;</td>
                            <!--
                            <td class="text4">主观题:</td>
                            
                            <td><input type="radio" id="blank" name="radiobutton" value="tiankong" onclick="onSelect(this.id)">&nbsp;</td>
                            <td class="text4">填空题</td>
                             
                            <td><input type="radio" id="answer" name="radiobutton" value="wenda" onclick="onSelect(this.id)">&nbsp;</td>
                            <td class="text4">问答题</td>
                            
                            <td><input type="radio" id="comprehesion" name="radiobutton" value="yuedu" onclick="onSelect(this.id)">&nbsp;</td>
                            <td class="text4">案例分析题</td>
                             -->
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>

            </table></td>
              </tr>
            </table></td>
        </tr>
      </table>
</body>
</html>

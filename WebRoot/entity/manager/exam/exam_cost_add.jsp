<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>tableType_2</title>
<link href="<%=request.getContextPath()%>/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
function check(){
	var cost = document.getElementById("cost").value;
	if(cost.length < 1){
		alert("请输入补考费用！");
		document.getElementById("cost").focus();
		return false;
	}
	var reg = /^\d{1,8}(\.\d)?$/;
	if(!reg.test(cost)){
		alert("费用格式1-8位整数0-1位小数！"); 
		document.getElementById("cost").select();
		return false;
	}
	return true;
}
</script>
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<s:form name = "batch" action="/entity/exam/peBzzExamAgain_saceCost.action" method="POST" onsubmit ="return check();">
<table width="80%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">添加补考费用</div></td>
  </tr>
    
            
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb" align="center"> 
        <div class="cntent_k" id="zlb_content_start">
          <p><FONT color="red"><s:property value="msg" escape="false" /></FONT></p>
          <table width="68%" border="0" cellpadding="0" cellspacing="0">
            
            <tr valign="bottom" class="postFormBox"> 
              <td width="23%"  valign="bottom"><span class="name">所选学生:</span></td>
              <td width="77%"><s:property value="studentList"/></td>
            </tr>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span class="name">补考费用(元):</span></td>
              <td valign="bottom"><input type="text" name="cost" maxLength="10" id="cost" /></td>
            </tr>
                      
            
            <tr class="postFormBox">
              <td ><input type="hidden" name="ids" value="<s:property value="ids"/>"/></td>
             <td><input type=submit name="submit1" value = "确定" /> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <input type="button" value="返回" onclick="window.location.href='/entity/exam/peBzzExamAgain.action'" ></td>
            </tr>
         </table>
      </div>

    </td>
  </tr>
 
</table>
</s:form>
</body>
</html>
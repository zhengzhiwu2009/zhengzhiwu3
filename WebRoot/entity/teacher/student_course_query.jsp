<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
<title>学员-课程</title>
<link rel="stylesheet" type="text/css" href="./js/extjs/resources/css/ext-all.css">
<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
<script language="JavaScript" src="js/shared.js"></script>
<script type="text/javascript" src="/web/bzz_index/index_new/js/jquery-1.6.1.min.js"></script>
<script type="text/javascript">
	function ajaxForData(){
		var firstSiteId = document.getElementById('firstSiteSelect').value;
		if(firstSiteId == ''){
			alert('请选择一级机构');
			return;
		}
		$.ajax({
	        url: "/entity/teaching/studentCourse_getSubEnterpriseList.action",
	        type: "post",
	        data: {'firstSiteId':firstSiteId},
	        success: function(response, textStatus, jqXHR){
	        		var jsonObj = eval(response);
					var myselect = $('#secondSiteId')
				    .find('option')
				    .remove().end().append('<option value="all">&nbsp;&nbsp;&nbsp;&nbsp;--全部--</option>');	
				    for(var i=0;i<jsonObj.length;i++){
				    	myselect.append('<option value="'+ jsonObj[i].id +'">'+ jsonObj[i].name  +'</option>');
				    }        	
	        }
	    });
	}
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="101.5%" border="0" cellspacing="0" cellpadding="4" align="center">
    <tr> 
      <td><div class="content_title">学员-课程</div></td>
    </tr>
</table>
<div id="tab">
<div id="tab_user2course" style="width:100.73%">
<form name="form1" method="post" action="/entity/teaching/studentCourse.action" id="form1">
<div class="cntent_k">
   <table width="98%" border="0" align="center" cellpadding="2" cellspacing="3" class="tdbg2">
    <tr >
	    <td width="50%" nowrap class="tdbg2" align="center"><font color="red">*</font>
	    	一级机构
			<select name="firstSiteId" style="width:250px;" onchange="ajaxForData();" id="firstSiteSelect">
			   <s:iterator value="firstSiteList" id="stat">
			      <option value="<s:property value="#stat[0]"/>"><s:property value="#stat[1]"/></option>
			   </s:iterator>
			</select></br><font color="red">&nbsp;&nbsp;</font>
		</td>
	</tr>
	<tr>	
		<td width="50%" nowrap class="tdbg2" align="center" ><font color="red">&nbsp;&nbsp;</font>	
	    	二级机构
			<select name="secondSiteId" style="width:250px;padding-top:10px;margin-top:10xp;" id="secondSiteId">
			   <s:iterator value="secondSiteList" id="stat">
			      <option value="<s:property value="#stat[0]"/>"><s:property value="#stat[1]"/></option>
			   </s:iterator>
			</select>
			</td>
	    </td>
    </tr>
	<tr>	
		<td width="50%" nowrap class="tdbg2" align="center"><font color="red">&nbsp;&nbsp;</font>	
			<INPUT class=button style="margin-left:50px;" onclick="doSubmit();" type="button" value="&nbsp;&nbsp;搜&nbsp;&nbsp;索&nbsp;&nbsp;">
	    </td>
    </tr>
    <tr>
    	<td colspan="4" align="center"><font color="red"></font></td>
    </tr>
  </table>
  </div>
  </form>
</div>
</div>
</body>
<script type="text/javascript">
	function doSubmit() {
		var parentId = document.getElementById('firstSiteSelect').value;
		if(parentId == ''){
			alert('请选择一级机构');
			return false;
		}
		var secondSiteId = document.getElementById('secondSiteId').value;
		var url = '/entity/teaching/studentCourse.action?firstSiteId='+parentId+'&secondSiteId='+secondSiteId;
		document.getElementById('form1').action = url;
		document.getElementById('form1').submit();
	}
	
</script>
</html>

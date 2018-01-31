<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>中国证券业协会远程培训系统</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css"/>
		<style type="text/css">
			input::-moz-focus-inner{ border: 0;padding: 0;}/*针对Firefox*/
			.comment_btn{
				height:26px;
				line-height:22px;/*针对IE*/
				width:50px;
			}		
		</style>
	</head>
		<script type="text/javascript">
Date.prototype.format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}
function searSubmit(){
	document.forms.form.startIndex.value= 0;
	document.forms.form.submit();
}
</script>
	<body leftmargin="0" topmargin="0" class="scllbar">
			
 			<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td height="28">
					<div class="content_title"  id="zlb_title_start">二级集体学时分配记录</div id="zlb_title_end">
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder" style="background:none;">
					<!-- start:内容区域 -->
					<div class="border">
						<!-- start:内容区－列表区域 -->
					<form action="/entity/basic/prepaidAccountSearch_toViewEnterpriseAdd.action" name="form" method="post">
								<input type="hidden" name="startIndex"	value="<s:property value="startIndex" />" />
								<input type="hidden" name="pageSize"	value="<s:property value="pageSize" />" />
						<table width="98%">
							<tr>                         
	                           <td align="left" >二级集体：:<input type=text style="width: 150px" id="enterpriseName"
								name="enterpriseName"
								value="<s:property value="#session.enterpriseName" />" />
								<input class="comment_btn" type="button" value="查  询" onclick="searSubmit();" >
								</td>
	                           <input type="hidden" style="width: 150px" id="recordId"
								name="recordId" 
								value="<s:property value="recordId"/>" />&nbsp;&nbsp;
	                         </tr>
                          </table>
							<table width='98%' align="center" cellpadding='1' cellspacing='0'
								class='list'>
								<tr>
									<th width='30%' style='white-space: nowrap;'>
										<span class="link">分配时间</span>
									</th>
									<th width='20%' style='white-space: nowrap;'>
										<span class="link">二级集体</span>
									</th>
									<th width='20%' style='white-space: nowrap;'>
										<span class="link">分配学时</span>
									</th>
									
								</tr>
							<s:if test="lists != null">
							<s:iterator value="lists" id="cp" status="st">
									<tr class='oddrowbg'>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#cp[1]" />
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#cp[2]" />
									</td>
									<td style='white-space: nowrap; text-align: center;'>
										<s:property value="#cp[3]" />
									</td>
									
								</tr>
							</s:iterator>
						</s:if>
							</table>
						<table  width='98%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
							<tr>
								<td height="30" align="center" style="font-size:larger">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">分配机构总数：<s:property value="allotStudent" />
								</font>
									&nbsp;&nbsp;&nbsp;
									<font color="red">分配学时总计：<s:property value="allotAmount" />
								</font>
								</td>
							</tr>
						</table>
							<br/>
      					<table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
   							 <tr>
          						<td align="center" valign="middle">
          	 							<span  style="width:46px;height:15px;padding-top:3px" ><input class="comment_btn" type="button" value="关闭" onclick="javascript:window.self.close()"></span>
          						</td>
        					</tr>
      					</table>
						</form>
						<!-- end:内容区－列表区域 -->
					</div>
					<!-- 内容区域结束 -->
				</td>
			</tr>
		</table>
	</body>
</html>
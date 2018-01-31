<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券协会远程培训系统</title>
		<!--  <link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">-->
		<style type="text/css"><!--<%@ include file="/entity/manager/css/admincss.css"%>--></style>
		<script type="text/javascript">
		function resetContent(){
			var content=document.getElementsByTagName("input");
			document.forms.batch.reset();
			for(var i=0;i<content.length;i++){
				if(content[i].type=="text"){
					content[i].value="";
				}
			}
		}
		</script>
	</head>
	<body leftmargin="0" topmargin="0" class="scllbar">
		<s:form name="batch" id="batch" action="/entity/basic/signStudentCourse_toConfirm.action" method="POST">
			<input type='hidden' name='signUpId' value="<s:property value="signUpId"/>">
			<input type='hidden' name='currSignUpOrderId' value="<s:property value="signUpId"/>">
 			<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
			<tr>
				<td height="28">
					<div class="content_title"  id="zlb_title_start">在线报名单信息确认</div id="zlb_title_end">
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder" style="background:none;">
					<div class="border">
						<table  width='98%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
							<tr>
								<td height="25" align="left">系统编号<input type="text" name="regno" value="<s:property value="regno"/>" ></td>
								<td height="25" align="left">学员姓名<input type="text" name="stuname" value="<s:property value="stuname"/>" ></td>
								<td height="25" align="left">课程编号<input type="text" name="classno" value="<s:property value="classno"/>" ></td>
								<td height="25" align="left">课程名称<input type="text" name="classname" value="<s:property value="classname"/>" ></td>
							</tr>
							<tr>
								<td align="center" colspan="4">
										<input type="submit" value="&nbsp;查&nbsp;找&nbsp;"/>
										<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="resetContent()" />
								</td>
							</tr>
						
							<tr>
								<td height="30" align="left">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;合计金额：
									<font color="red"><s:property value="totalfee" /></font>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报名人数：
									<font color="red"><s:property value="totalbm" /></font>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;报名学时数： 
									<font color="red"><s:property value="totalhour" /></font>
								</td>
							</tr>
						</table> 
						<form name='userform' action='#' method='post' class='nomargin' onsubmit="">
							<table width='98%' align="center" cellpadding='1' cellspacing='0' class='list'>
								<tr>
									<th width='5%' style='white-space: nowrap;'>
										<span class="link">序号</span>
									</th>
									<th width='5%' style='white-space: nowrap;'>
										<span class="link">系统编号</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">学员姓名</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">证件号</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">部门</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">课程编号</span>
									</th>
									<th width='15%' style='white-space: nowrap;'>
										<span class="link">课程名称</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">金额</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">学时</span>
									</th>
								</tr>
								<s:iterator value="stucoulist" id="stucour"  status="st">
									<tr class='oddrowbg'>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#st.index+1"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#stucour[1]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#stucour[2]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#stucour[3]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#stucour[4]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#stucour[5]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#stucour[6]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#stucour[8]"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#stucour[7]"/>
										</td>
										
									</tr>
								</s:iterator>
							</table>
							<br/>
      					<table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
   							 <tr>
          						<td align="center" valign="middle">

          	 						<span id="submitSpan"   style="width: 70px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_4.jpg');" ><a href="#" onclick="invalideButton();">确认报名</a></span>	

          	 							&nbsp;&nbsp;&nbsp;
          	 							<span   style="width: 40px; height: 22px;line-height:22px;background-image:url('/entity/manager/images/button_2.jpg');" ><a href="###"  onclick="goBack();" >关闭</a></span>
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
		</s:form>
	</body>
	<script type="text/javascript">
		var sum = 0;
		function goBack() {
			//history.back();
			window.close();
		}
		
		function invalideButton() {
			if(sum==0) { 
				document.forms.batch.submit();
				document.getElementById('batch').submited=false;
				sum = 1;
			}
			document.getElementById('submitSpan').style.display = 'none';
		}
	</script>
</html>
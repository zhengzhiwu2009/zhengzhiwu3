<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券业协会远程培训系统</title>
		<link href="../entity/function/css/css.css" rel="stylesheet"
			type="text/css">
	</head>
	<Script>
function listSelect(){
  var flag=document.all.listSelectAll.checked;
  var ids=del_form.all.idss
  if(ids.length==undefined){
   ids.checked=flag;
   }else{
    for(var i=0;i<ids.length;i++){
       ids[i].checked=flag;
    }
   }
 
}
function sub(){
if(del_form.all.idss==null){
  alert("无收件人");
  return;
}
var flag=false;
 var ids=del_form.all.idss
 if(ids.length==undefined){
   flag=ids.checked;
 }else{
    for(var i=0;i<ids.length;i++){
      if(ids[i].checked){
        flag=true;
         break;
      }
      }
    }
    if(flag==true){
      del_form.submit();
    }else{
      alert("请选择要发送的人");
    }
 
}
function queryInfo(){
del_form.action='/siteEmail/editEmail_addStudent.action';
 del_form.submit();
}

function return1() {
	del_form.action='/siteEmail/editEmail_addEnd.action?flag=1';
	del_form.submit();
}

function qingkong() {
	document.getElementById('roleType').value = '';
	document.getElementById('zgId').value = '';
	document.getElementById('enterpriseId').value = '';
	document.getElementById('sendName').value = '';
	document.getElementById('sendId').value = '';
}
</Script>
	<body leftmargin="0" topmargin="0"
		background="../entity/function/homeworkpaper/images/bg2.gif">
		<form action="/siteEmail/editEmail_addEnd.action" method="post"
			name="del_form">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<input type="hidden" name=title
					value='<s:property value='getTitle()'/>'>
				<input type="hidden" name=content
					value='<s:property value='getContent()'/>'>
				<input type="hidden" name=sendIds
					value='<s:property value='getSendIds()'/>'>
				<tr>
					<td valign="top" class="bg3">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td valign="top"
									background="../entity/function/homeworkpaper/images/top_01.gif">
									<table width="100%" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td width="217" height="104" rowspan="2" valign="top"></td>
											<td height="46">
												&nbsp;
											</td>
										</tr>
										<tr>
											<td align="left" valign="top">
												<table width="70%" border="0" cellpadding="0"
													cellspacing="0">
													<tr>
														<td align="left">
															<img src="../entity/function/homeworkpaper/images/xb.gif"
																width="48" height="32">
														</td>
														<td align="center" class="mc1"></td>
														<td align="center"></td>
														<td align="center"></td>
														<td class="mc1">
															<!--<a href="#" ><img src="../entity/function/homeworkpaper/images/Return.gif" onClick="javascript:return1();"/ border="0"></a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a href="#" onclick='sub()'><IMG src="../entity/function/images/OK.gif" border="0"/></a>-->
														</td>
													</tr>
													<tr>
														<td nowrap="true" align="left" colspan="2" nowrap="true">
															系统编号:&nbsp;
															<input type="text" style="width: 180" name="sendId"
																id="sendId" value="<s:property value='sendId' />">
														</td>
														<td align="left" colspan="2">
															&nbsp;&nbsp;&nbsp;&nbsp;姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:&nbsp;
															<input type="text" name="sendName" id="sendName" style="width: 180"
																value="<s:property value='sendName' />">
														</td>
														<s:if test="#session.user_session.userLoginType!=4">
															<td align="left" colspan="2" nowrap="nowrap">
																&nbsp;&nbsp;&nbsp;所在机构:
																<select name="enterpriseId" id="enterpriseId"
																	style="width: 200px">
																	<option value="">
																		全部
																	</option>
																	<s:iterator id="pelist" value="peEnterpriseList">
																		<option value="<s:property value="#pelist[0]"/>"
																			<s:if test="#request.enterpriseId==#pelist[0]">selected</s:if>>
																			<s:property value="#pelist[1]" />
																		</option>
																	</s:iterator>
																</select>
															</td>
														</s:if>
														<!-- 
                            <td align="left" colspan="2" nowrap="nowrap">&nbsp;&nbsp;&nbsp;角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色:
                            	<select name="roleType" style="width:180px">
                            		<option value="">全部</option>
                            		<s:iterator id="typeList" value="roleTypeList">
                            			<option value="<s:property value="#typeList[1]"/>" <s:if test="roleType==#typeList[1]">selected</s:if>><s:property value="#typeList[1]"/></option>
                            		</s:iterator>
                            	</select>
                            </td>
                             -->
													</tr>
													<tr>
														<!--  <td align="left" colspan="2">姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名:<input type="text" name="sendName"  value="<s:property value='sendName' />"></td>
                             -->

														<td align="left" colspan="2" nowrap="nowrap">
															资格类型:&nbsp;
															<select name="zgId" id="zgId" style="width: 180px"
																value="">
																<option value="">
																	全部
																</option>
																<s:iterator id="zglist" value="zgList">
																	<option value="<s:property value="#zglist[0]"/>"
																		<s:if test="#zglist[0]==zgId">selected</s:if>>
																		<s:property value="#zglist[1]" />
																	</option>
																</s:iterator>
															</select>
														</td>
														<s:if test="#session.user_session.userLoginType!=4">
															<td align="left" colspan="2" nowrap="nowrap">
																&nbsp;&nbsp;&nbsp;&nbsp;账号类型:&nbsp;
																<select name="roleType" id="roleType"
																	style="width: 180px">
																	<option value="">
																		全部
																	</option>
																	<s:iterator id="typeList" value="roleTypeList">
																		<option value="<s:property value="#typeList[1]"/>"
																			<s:if test="roleType==#typeList[1]">selected</s:if>>
																			<s:property value="#typeList[1]" />
																		</option>
																	</s:iterator>
																</select>
															</td>
														</s:if>
														<td class="mc1" align="center">
															<input type="button" value="&nbsp;查&nbsp;找&nbsp;" Onclick=queryInfo();>
														</td>
														<td class="mc1" align="center">
															<input type="button" value="&nbsp;清&nbsp;空&nbsp;" onclick="qingkong();">
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
								<td height="46">
									&nbsp;
								</td>
							</tr>
							<tr>
								<td align="center">
									<table width="812" border="0" cellspacing="0" cellpadding="0"
										style="margin-top: 20px;">
										<tr>
											<td height="26"
												background="../entity/function/homeworkpaper/images/tabletop.gif">
												<table width="100%" border="0" align="center"
													cellpadding="0" cellspacing="0">
													<tr>
														<td width="1%" align="center" valign="bottom">
															&nbsp;
														</td>
														<td width="6%" align="center" class="title">
															<input type='checkbox' class='checkbox'
																name='listSelectAll' value='true' onClick="listSelect()">
														</td>
														<td width="1%" align="center" valign="bottom">
															<img
																src="../entity/function/homeworkpaper/images/topxb.gif">
														</td>
														<td width="15%" align="center" class="title">
															系统编号
														</td>
														<td width="1%" align="center" valign="bottom">
															<img
																src="../entity/function/homeworkpaper/images/topxb.gif">
														</td>
														<td width="10%" align="center" class="title">
															姓&nbsp;&nbsp;&nbsp;&nbsp;名
														</td>
														<td width="1%" align="center" valign="bottom">
															<img
																src="../entity/function/homeworkpaper/images/topxb.gif">
														</td>
														<td width="29%" align="center" class="title">
															所在机构
														</td>
														<td width="1%" align="center" valign="bottom">
															<img
																src="../entity/function/homeworkpaper/images/topxb.gif">
														</td>
														<td width=13% " align="center" class="title">
															账号类型
														</td>
														<td width="1%" align="center" valign="bottom">
															<img
																src="../entity/function/homeworkpaper/images/topxb.gif">
														</td>
														<td width="17%" align="center" class="title">
															资格类型
														</td>
														<!--  <td width="6%" align="center" class="title"><input type='checkbox' class='checkbox' name='listSelectAll' value='true'  onClick="listSelect()"></td>
                     -->
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td align="center"
												background="../entity/function/homeworkpaper/images/tablebian.gif">
												<table width="99%" border="0" cellspacing="0"
													cellpadding="0" class="mc1">
													<s:iterator id="stu" value="studentList">
														<tr>
															<td width="7%" align="center" class="td1">
																<input type='checkbox' class='checkbox' name='idss'
																	value='<s:property value='#stu[0]'/>'>
															</td>
															<td width="5%" align="center" valign="middle" class="td1">
																&nbsp;
															</td>
															<!--   <td width="5%" align="center" valign="middle" class="td1"><img src="../entity/function/homeworkpaper/images/xb2.gif" width="8" height="8"></td>
                             -->
															<td width="15%" class="td1">
																<s:property value="#stu[0]" />
															</td>
															<td width="11%" align="left" class="td1">
																<s:property value="#stu[1]" default="--" />
															</td>
															<td width="30%" align="center" class="td1">
																<s:property value="#stu[2]" />
																&nbsp;
															</td>
															<td width="15%" align="center" class="td1">
																<s:property value="#stu[3]" />
																&nbsp;
															</td>
															<td width="19%" align="center" class="td1">
																<s:property value="#stu[4]" />
																&nbsp;
															</td>
															<!-- <td width="7%"  align="center" class="td1"><input type='checkbox' class='checkbox' name='idss' value='<s:property value='#stu[0]'/>'></td>
                          	 -->
														</tr>
													</s:iterator>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												<img src="../entity/function/homeworkpaper/images/tablebottom.gif" width="812" height="4" />
											</td>
										</tr>
									</table>
									<br/>
									<table width="70%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td class="mc1" align="center">
												<a href="#"><img
														src="../entity/function/homeworkpaper/images/Return.gif"
														onClick="javascript:return1();" / border="0">
												</a>&nbsp;&nbsp;&nbsp;&nbsp;
												<a href="#" onclick='sub()'><IMG
														src="../entity/function/images/OK.gif" border="0" />
												</a>
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<tr>
								</form>
								<td align="center">
									<table width="806" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td>
												<img src="../entity/function/homeworkpaper/images/bottomtop.gif" width="806" height="7" />
											</td>
										</tr>
										<tr>
											<td background="../entity/function/homeworkpaper/images/bottom02.gif">
												<%@ include file="dividepage.jsp"%>
											</td>
										</tr>
										<tr>
											<td>
												<img
													src="../entity/function/homeworkpaper/images/bottom03.gif"
													width="806" height="7">
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
	</body>
</html>

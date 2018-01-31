<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>中国证券业协会远程培训系统</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<style type="text/css">
			input::-moz-focus-inner{ border: 0;padding: 0;}/*针对Firefox*/
			.comment_btn{
				height:26px;
				line-height:22px;/*针对IE*/
				width:50px;
			}		
		</style>
	</head>
	<Script>
		//操作中的删除
		function deleteStudentInfo(stuid) {
			var perid=document.getElementById("p_id").value;
			if (confirm("确定要删除吗？")) {
				window.location.href = "/entity/basic/peElectiveCoursePeriod_toDeleteStudent.action?id="+perid+"&stuid="+stuid;
			} else {
				return ;
			}
		}
		function queryInfo(){
			document.userform.action="/entity/basic/peElectiveCoursePeriod_viewStudentInfo.action"; 
			document.userform.submit();	
		}	
		//删除选中项
		function deleteAll(){
			var roomids = document.getElementsByName("stu_check");
			var perid=document.getElementById("p_id").value;
			var ids="";
			for (var j = 0; j < roomids.length; j++) {
				if (roomids.item(j).checked == true) {
					ids+=roomids.item(j).value+",";
				}
			}
			if (ids!="" && ids!="," && confirm("确定要删除吗？")) {
				window.location.href = "/entity/basic/peElectiveCoursePeriod_toDeleteStudentAll.action?id="+perid+"&delIds="+ids;
			}
		}
		//全选或反选
		function selectAll(){ 
			var ch_v=document.getElementById("select_all_id").value;
			var roomids = document.getElementsByName("stu_check");
			if(ch_v=='1'){
		        for ( var j = 0; j < roomids.length; j++) {
		            if (roomids.item(j).checked == false) {
		                roomids.item(j).checked = true;
		            }
		        }
		        document.getElementById("select_all_id").value='0';
			}else{
				for ( var j = 0; j < roomids.length; j++) {
		            if (roomids.item(j).checked == true) {
		                roomids.item(j).checked = false;
		            }
		        }
		        document.getElementById("select_all_id").value='1'
			}
			    
		}  
		
	</Script>
	<body leftmargin="0" topmargin="0" class="scllbar">
			
 			<table width="100%" border="0" align="left" cellpadding="0"
			cellspacing="0">
			<tr>
				<td height="28">
					<div class="content_title"  id="zlb_title_start">选课期选课列表</div id="zlb_title_end">
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder" style="background:none;">
					<!-- start:内容区域 -->
					<div class="border">
						<!-- start:内容区－列表区域 -->
						<form name="userform" action="" method="post" class='nomargin' onsubmit="">
						<input type="hidden" name="id" id="p_id" value="<s:property value='id' />">
							<table width="100%">
							<tr>                         
	                            <td align="left" >系统编号:<input type="text" name="sysno" value="<s:property value='sysno' />"></td>
	                            <td align="left" >姓名:<input type="text" name="username"  value="<s:property value='username' />"></td>
	                            <td align="left">资格类型:
		                            <select name="zige" class="sub">
										<option value="" selected>全部</option>
										<s:iterator value="zigelist" id="zigeB">
											<s:if test="#zigeB.id == zige">
												<option value="<s:property value="#zigeB.id" />" selected>
													<s:property value="#zigeB.name" />
												</option>
											</s:if>
											<s:else>
												<option value="<s:property value="#zigeB.id" />">
													<s:property value="#zigeB.name" />
												</option>
											</s:else>
										</s:iterator>
									</select>
	                            </td>
	                            <td align="left">移动电话:<input type="text" name="ydphone"  value="<s:property value='ydphone' />"></td>
	                         </tr>
							<tr>                         
	                            <td align="left" >身份证号:<input type="text" name="idcard" value="<s:property value='idcard' />"></td> 
	                            <td  class="mc1" align="center"><input class="comment_btn" type="button" value="&nbsp;查&nbsp;找&nbsp;" Onclick="queryInfo();"></td>	                 
	                         </tr>
                          </table>
							<table width='100%' align="center" cellpadding='1' cellspacing='0' class='list'>
								<tr>
									<th width='2%' style='white-space: nowrap;'>
										<input type="checkbox" id='select_all_id' name="select_all" value="1" onclick="selectAll()"> 
									</th>
									<th width='5%' style='white-space: nowrap;'>
										<span class="link">系统编号</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">姓名</span>
									</th>
									<!--th width='10%' style='white-space: nowrap;'>
										<span class="link">具有从业资格</span>
									</th-->
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">资格类型</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">移动电话</span>
									</th>
									<th width='15%' style='white-space: nowrap;'>
										<span class="link">组别</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">国籍</span>
									</th>
									<th width='15%' style='white-space: nowrap;'>
										<span class="link">证件号码</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">必修学时</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">选修学时</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">总学时</span>
									</th>
									<th width='15%' style='white-space: nowrap;'>
										<span class="link">操作</span>
									</th>
								</tr>
								<s:if test = 'studentlist!=null && studentlist.size()>0'>
									<s:iterator value="studentlist" id="elective">
										<tr class='oddrowbg'>
											<td><input type="checkbox" name="stu_check" value="<s:property value="#elective[13]"/>"></td>
											<td style='white-space: nowrap; text-align: center;'>
												<s:property value="#elective[1]"/>
											</td>
											<td style='white-space: nowrap; text-align: center;'>
												<s:property value="#elective[0]"/>
											</td>
											<!--td style='white-space: nowrap; text-align: center;'>
												<s:property value="#elective[5]"/>
											</td-->
											<td style='white-space: nowrap; text-align: center;'>
												<s:property value="#elective[3]"/>
											</td>
											<td style='white-space: nowrap; text-align: center;'>
												<s:property value="#elective[6]"/>
											</td>
											<td style='white-space: nowrap; text-align: center;'>
												<s:property value="#elective[7]"/>
											</td>
											<td style='white-space: nowrap; text-align: center;'>
												<s:property value="#elective[8]"/>
											</td>
											<td style='white-space: nowrap; text-align: center;'>
												<s:property value="#elective[12]"/>
											</td>
											<td style='white-space: nowrap; text-align: center;'>
												<s:property value="#elective[9]"/>
											</td>
											<td style='white-space: nowrap; text-align: center;'>
												<s:property value="#elective[10]"/>
											</td>
											<td style='white-space: nowrap; text-align: center;'>
												<s:property value="#elective[11]"/>
											</td>
											<td style='white-space: nowrap; text-align: center;'>
												<input type=button onclick=deleteStudentInfo("<s:property value="#elective[13]"/>") value="删除"></input><br />
											</td>
										</tr>
									</s:iterator>
								</s:if>
							</table>
							<br/>
      					<table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0">
   							 <tr>
          						<td align="center" valign="middle">
          	 							<span  style="width:66px;height:15px;padding-top:3px" ><input class="comment_btn" style="width:76px" type="button" value="删除选中项" onclick="deleteAll()"></span>
          	 							<span  style="width:46px;height:15px;padding-top:3px" ><input class="comment_btn" type="button" value="返 回" onclick="javascript:window.location.href='/entity/basic/peElectiveCoursePeriod.action'"></span>
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
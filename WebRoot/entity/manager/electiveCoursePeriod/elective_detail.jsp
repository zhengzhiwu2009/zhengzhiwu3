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
		function checkDel(id,beanId) {
			var answer = confirm("确定要删除吗？");
			if (answer) {
				window.location.href = "/entity/basic/peElectiveCoursePeriod_deleteElectiveCoursePeriodBack.action?id="+id+"&bean.id="+beanId;
			} else {
				return ;
			}
		}
		function queryInfo(){
			document.userform.action="/entity/basic/peElectiveCoursePeriod_viewDetail.action"; 
			document.userform.submit();	
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
						<input type="hidden" name="bean.id" value="<s:property value='bean.id' />">
							<table width="98%">
							<tr>                         
	                            <td align="left">系统编号:<input type="text" name="regNo" value="<s:property value='regNo' />"></td>
	                            <td align="left">学员姓名:<input type="text" name="stuName"  value="<s:property value='stuName' />"></td>
	                            <td align="left">课程编号:<input type="text" name="courseCode"  value="<s:property value='courseCode' />"></td>
	                            <td align="left">课程名称:<input type="text" name="courseName"  value="<s:property value='courseName' />"></td>
	                            <td align="left">
										课程性质：
										<select name="courseType" class="sub">
											<option value="" selected>全部课程</option>
											<s:iterator value="courseTypeList" id="type">
												<s:if test="#type.id == courseType">
													<option value="<s:property value="#type.id" />" selected>
														<s:property value="#type.name" />
													</option>
												</s:if>
												<s:else>
													<option value="<s:property value="#type.id" />">
														<s:property value="#type.name" />
													</option>
												</s:else>
											</s:iterator>
										</select>
									</td>
	                            <td class="mc1" align="center"><input class="comment_btn" type="button" value="&nbsp;查&nbsp;找&nbsp;" Onclick="queryInfo();"></td>	                 
	                         </tr>
                          </table>
							<table width='98%' align="center" cellpadding='1' cellspacing='0'
								class='list'>
								<tr>
									<th width='20%' style='white-space: nowrap;'>
										<span class="link">系统编号</span>
									</th>
									<th width='15%' style='white-space: nowrap;'>
										<span class="link">学员姓名</span>
									</th>
									<th width='15%' style='white-space: nowrap;'>
										<span class="link">课程编号</span>
									</th>
									<th width='20%' style='white-space: nowrap;'>
										<span class="link">课程名称</span>
									</th>
									<th width='10%' style='white-space: nowrap;'>
										<span class="link">课程性质</span>
									</th>
									<th width='5%' style='white-space: nowrap;'>
										<span class="link">学时</span>
									</th>
									<th width='15%' style='white-space: nowrap;'>
										<span class="link">价格</span>
									</th>
									<th width='15%' style='white-space: nowrap;'>
										<s:if test="!isFormedOrder && (electiveList==null || electiveList.size()==0)">
											<span class="link">操作</span>
										</s:if>
										<s:else>
											<span class="link">支付状态</span>
										</s:else>
									</th>
								</tr>
								<s:if test = 'electiveList!=null && electiveList.size()>0'>
									<s:iterator value="electiveList" id="elective">
										<tr class='oddrowbg'>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#elective.peBzzStudent.regNo"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#elective.peBzzStudent.trueName"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#elective.prBzzTchOpencourse.peBzzTchCourse.code"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#elective.prBzzTchOpencourse.peBzzTchCourse.name"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#elective.prBzzTchOpencourse.peBzzTchCourse.enumConstByFlagCourseType.name"/>
										<br/></td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#elective.prBzzTchOpencourse.peBzzTchCourse.time"/>
										<br/></td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#elective.prBzzTchOpencourse.peBzzTchCourse.price"/>
										<br/></td>
										<td style='white-space: nowrap; text-align: center;'>
												已支付
										</td>
									</tr>
									</s:iterator>
								</s:if>
								<s:else>
									<s:iterator value="electiveBackList" id="electiveBack">
										<tr class='oddrowbg'>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#electiveBack.peBzzStudent.regNo"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#electiveBack.peBzzStudent.trueName"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#electiveBack.prBzzTchOpencourse.peBzzTchCourse.code"/>
										</td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#electiveBack.prBzzTchOpencourse.peBzzTchCourse.name"/>
										<br/></td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#electiveBack.prBzzTchOpencourse.peBzzTchCourse.enumConstByFlagCourseType.name"/>
										<br/></td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#electiveBack.prBzzTchOpencourse.peBzzTchCourse.time"/>
										<br/></td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:property value="#electiveBack.prBzzTchOpencourse.peBzzTchCourse.price"/>
										<br/></td>
										<td style='white-space: nowrap; text-align: center;'>
											<s:if test="!isFormedOrder">
												<a href="#" onclick="checkDel('<s:property value="#electiveBack.id"/>','<s:property value="#electiveBack.peElectiveCoursePeriod.id"/>');">删除</a>				
											</s:if>
											<s:else>
												未支付
											</s:else>
										</td>
									</tr>
									</s:iterator>
								</s:else>
							</table>
						<table  width='98%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
							<tr>
								<td height="30" align="center" style="font-size:larger">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总人数：
									<font color="red"><s:property value="num" /></font>
									&nbsp;人&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学时数：
									<font color="red"><s:property value="allTime" /></font>&nbsp;
									学时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									必修学时数：
									<font color="red"><s:property value="bTimes" /></font>&nbsp;
									学时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									选修学时数：
									<font color="red"><s:property value="xTimes" /></font>&nbsp;
									学时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总金额：
									<font color="red"><s:property value="allAmount" /></font>&nbsp;元
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
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
					<link rel="stylesheet" type="text/css" media="all"	href="/js/calendar/calendar-win2000.css">

		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<script language="JavaScript" src="js/shared.js"></script>
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
				<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<style type="text/css">
		.tdbg0		{background-color: #ffffff; color:#444444}
		</style>
		<script language="javascript">
			function chs(){
				//var changeStatues = document.getElementById("status").value;
				form1.action="/entity/teaching/searchAnyStudent_electivedBatchCourse.action";
				form1.submit();
			}
			
			function resetAll() {
				/**
				var paymentDateStart = document.getElementById('paymentDateStart').value;   //支付开始时间
				var paymentDateEnd = document.getElementById('paymentDateEnd').value;   //支付结束时间
				var studyDateStart = document.getElementById('studyDateStart').value;   //开始学习时间
				var studyDateEnd = document.getElementById('studyDateEnd').value;      //学习结束时间
				**/
//				document.getElementById('studyDateStart').value = '';
//				document.getElementById('studyDateEnd').value = '';
                document.getElementById('courseNo').value = '';
				document.getElementById('courseName').value = '';
				document.getElementById('status').value = 'all';
				document.getElementById('examStatus').value = 'all';
				
			}
		</script>
	</head>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
		<form name="form1" method="post" action="">
		<input type="hidden" name="flag" value="<s:property value="flag"/>"/>
		<input type="hidden" name="stuId" value="<s:property value="stuId"/>"/>
			<table width="100%" border="0" cellspacing="0" cellpadding="4"
				align="center">
				<tr>
					<td class="tdsectionbar">
					<div class="content_title">
						专项学习信息
						</div>
					</td>
				</tr>
			</table>
			<div class="cntent_k">
			<table width="100%" border="0" align="center" cellpadding="2" cellspacing="3" >
				<tr>
				<td  style="font-size:12px;" colspan="4">
				<table width="100%" border="0" align="center" cellpadding="0"
				cellspacing="0" >
				<tr>
					<td  class="tdbg2" style="font-size:12px;">
						<font><b>系统编号：</b><s:property value="peBzzStudent.regNo"/></font><input type="hidden"  name="peBzzStudent.regNo" value="<s:property value="peBzzStudent.regNo"/>"/>
					</td>
					<td>
						<font><b>学员姓名：</b><s:property value="peBzzStudent.trueName"/></font><input type="hidden"  name="peBzzStudent.trueName" value="<s:property value="peBzzStudent.trueName"/>"/>
					</td>
					<!-- <td  nowrap class="tdbg2">
						<font size="4"><b>学员姓名：</b><s:property value="peBzzStudent.trueName"/></font><input type="hidden"  name="peBzzStudent.trueName" value="<s:property value="peBzzStudent.trueName"/>"/>
					</td> -->
					<td  class="tdbg2">
						<font><b>身份证号：</b><s:property value="peBzzStudent.cardNo"/></font><input type="hidden"  name="peBzzStudent.cardNo" value="<s:property value="peBzzStudent.cardNo"/>"/>
					</td>
					<td  class="tdbg2">
						<font ><b>所属机构：</b><s:property value="peBzzStudent.peEnterprise.name"/></font><input type="hidden"  name="peBzzStudent.peEnterprise.name" value="<s:property value="peBzzStudent.peEnterprise.name"/>"/>
					</td>
					</tr>
					</table>
					</td>
				</tr>
   				<tr><td colspan="4" style="border-top:1px solid #CCC; " height="10"></td></tr>
   				<tr>
						<td align="left" class="tdbg2" >
						    <font><b>课程编号:</b> </font><input type="text" align="left" id="courseNo" name="courseNo"/>
						</td>
					    <td align="left" class="tdbg2" >
					      <font><b>课程名称:</b> </font><input type="text" align="left" id="courseName" name="courseName"/>
					    </td>
					    <td align="left" class="tdbg2" colspan="2">
					  		 <font><b>课程性质:&nbsp;&nbsp;&nbsp;</b> </font>
						  		<select name="courseNature" id="courseNature">
						             <option value="all">全部</option>
						             <option value="402880f32200c249012200c780c40001">必修</option>
						             <option value="402880f32200c249012200c7f8b30002">选修</option>
						        </select>
						</td>
						<td align="right">
						&nbsp;
						</td>
					</tr>
				<tr>	
					 <td  align="center" class="tdbg2" >
						<font><b>学习状态：</b>
						<select name="status" id="status" >
					 
							<option value="all" selected>全部</option>
							<s:if test="status=='INCOMPLETE'">
								<option value="INCOMPLETE" selected <s:if test="status=='INCOMPLETE'"></s:if>> 正在学习课程</option>							
							</s:if>
							<s:else>
								<option value="INCOMPLETE" <s:if test="status=='INCOMPLETE'"></s:if>> 正在学习课程</option>
							</s:else>
							<s:if test="status=='COMPLETED'">
								<option value="COMPLETED" selected <s:if test="status=='COMPLETED'"></s:if>>已完成学习课程</option>
							</s:if>
							<s:else>
								<option value="COMPLETED"  <s:if test="status=='COMPLETED'"></s:if>>已完成学习课程</option>
							</s:else>
							<s:if test="status=='UNCOMPLETE'">
								<option value="UNCOMPLETE" selected <s:if test="status=='UNCOMPLETE'"></s:if>>未开始学习课程</option>							
							</s:if>
							<s:else>
								<option value="UNCOMPLETE" <s:if test="status=='UNCOMPLETE'"></s:if>>未开始学习课程</option>		
							</s:else>
						</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
					</td>
					<td  align="left" class="tdbg2" >
						<font><b>考&nbsp;试&nbsp;结&nbsp;果：</b>
						
						&nbsp;&nbsp;&nbsp;
						<select name="examStatus" id="examStatus" >
							<option value="all" selected>全部</option>
							<s:if test="examStatus=='PASSED'">
								<option value="PASSED" selected <s:if test="examStatus=='PASSED'"></s:if>>通过考试</option>								
							</s:if>
							<s:else>
								<option value="PASSED" <s:if test="examStatus=='PASSED'"></s:if>>通过考试</option>	
							</s:else>
							<s:if test="examStatus=='FAILED'">
								<option value="FAILED" selected <s:if test="examStatus=='FAILED'"></s:if>>未通过考试</option>
							</s:if>
							<s:else>							
								<option value="FAILED" <s:if test="examStatus=='FAILED'"></s:if>>未通过考试</option>
							</s:else>
							<!-- wuhao  -->
							<s:if test="examStatus=='UNFINISHED'">
								<option value="UNFINISHED" selected <s:if test="examStatus=='UNFINISHED'"></s:if>>未完成学习</option>
							</s:if>
							<s:else>							
								<option value="UNFINISHED" <s:if test="examStatus=='UNFINISHED'"></s:if>>未完成学习</option>
							</s:else>
								<!-- end
								<option value="UNEXAM"  >未完成学习</option>
								  -->
						</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						
					</td>
					<td  align="center" class="tdbg2">
						<input type="submit" value="搜索"/>&nbsp;&nbsp;
						<input type="button" onclick="resetAll();"  value="清空"/>
					</td>
					<td align="left">
						&nbsp;
					</td>
				</tr>
	
			</table>
			<div style="border-top:1px solid #CCC; margin:10px;"></div>
			<table width="100%" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<td class="tdsectionbar">
						<font><b>&nbsp;专项学习信息</b></font>
					</td>
				</tr>
			</table>
			<table width="98%" border="0" align="center" cellpadding="0"
				cellspacing="0" class="content_title"  style="padding:0px;">
				<style type="text/css">
                .td_inf{ border-bottom:1px solid #ccc;
					}.td_r{
					border-right:none;
					}
					
                </style>
				<tr align="center" >
					<td style="display:none;" class="td_inf">课程Id</td>
					<td width="10%" class="td_inf">
						序号
					</td>
					<td width="10%" class="td_inf">
						课程编号
					</td>
					<td width="20%" class="td_inf">
						课程名称
					</td>
					<td width="10%" class="td_inf">
						课程学时
					</td>
					<td width="10%" class="td_inf">
						课程性质
					</td>
					<td width="10%" class="td_inf">
						支付时间
					</td>
				<!-- 	<td width="10%" class="td_inf" style="display:none;">
						开始学习时间
					</td> -->
					<td width="10%" class="td_inf">
						学习状态
					</td>
					<td width="10%" class="td_inf">
						考试结果
					</td>
					<td width="10%" class="td_inf td_r">
						操作
					</td>
				</tr>
				<s:if test="list.size()==0">
					<tr align="center" class="tdbg0">
						<td colspan=9 bgcolor="rgb(227, 241, 246)" height="6" >
							<font color="red" size="4">未查找到对应的结果</font>
						</td>
					</tr>
				</s:if>
				<s:else>
				<s:iterator value="list" id="course" status="item">
				<tr align="center" class="tdbg0" >
					<td style="display:none;" class="td_inf">
						<s:property value="#course[0]"/>
					</td>
					<td  class="td_inf">
						<s:property value="#item.index+1"/>
					</td>
					<td class="td_inf">
						<s:property value="#course[1]"/>
					</td>
					<td class="td_inf" align="center">
						<s:property value="#course[2]"/>
					</td>
					<td class="td_inf">
						<s:property value="#course[3]"/>
					</td>
					<td class="td_inf">
						<s:property value="#course[4]"/>
					</td>
					<td class="td_inf">
						<s:property value="#course[5]" default="--"/>
					</td>
				<!-- 	<td class="td_inf" style="display:none;">
						<s:property value="#course[6]" default="--"/>
					</td> -->
					<td class="td_inf">
						<s:if test="#course[7]=='COMPLETED'">已完成学习</s:if>
						<s:elseif test="#course[7]=='INCOMPLETE'">正在学习</s:elseif>
						<s:else>未开始学习</s:else>
					</td>
					<td class="td_inf">
					<s:if test="#course[8]=='PASSED'">通过考试</s:if>
					<s:elseif test="#course[8]=='FAILED'">未通过考试</s:elseif>
					<s:else>未完成学习</s:else>
					</td>
					<td class="td_inf td_r">
						<a href="/entity/teaching/searchAnyStudent_courseInfo.action?courseId=<s:property value="#course[0]"/>&opencourseId=<s:property value="#course[9]"/>&sid=<s:property value="peBzzStudent.id"/>">查看</a>
					</td>
				</tr>
				</s:iterator>
				</s:else>
				
		<!-- 	<tr>
					<td colspan="1" align="center"><font size="3"><b>获得此专项学时合计</b></font></td>
					<td colspan="4" align="center" ><b><font size="3"><s:property value="sumCourse"/></font></b></td>
					<td colspan="4">&nbsp;</td>
				</tr>  -->	
				
			</table>
		</form>
		<table width="98%" border="0" cellspacing="3" cellpadding="0"
			align="center">
			<tr class="tdbg2">
				<td align="center">
					<s:if test="#session.flag_x=='stu'">
						<td align="center"><a href="/entity/basic/peBzzStudent.action?backParam=true"  title="返回" class="buttonn"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class="text">返回</span></span></a></td>
					</s:if>
					<s:elseif test="#session.flag_x=='subEn'">
						<td align="center"><a href="/entity/basic/peDetail.action?id=<s:property value="#session.id"/>&type=<s:property value="type"/>&method=<s:property value="method"/>"  title="返回" class="buttonn"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class="text">返回</span></span></a></td>
					</s:elseif>
					<s:else>
						<td align="center"><a href="/entity/teacher/search_any_student.jsp"  title="返回" class="buttonn"><span unselectable="on" class="norm"  style="width:46px;height:15px;padding-top:3px" onMouseOver="className='over'" onMouseOut="className='norm'" onMouseDown="className='push'" onMouseUp="className='over'"><span class="text">返回</span></span></a></td>
					</s:else>
				</td>
			</tr>
		</table>
	</body>
</html>


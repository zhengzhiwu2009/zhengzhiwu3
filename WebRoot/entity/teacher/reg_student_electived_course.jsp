<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" media="all" href="/js/calendar/calendar-win2000.css" />

		<script type="text/javascript" src="/js/calendar/calendar.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-zh_utf8.js"></script>
		<script type="text/javascript" src="/js/calendar/calendar-setup.js"></script>
		<script language="JavaScript" src="js/shared.js"></script>
		<script language="javascript" src="/entity/bzz-students/js/pro.js"></script>
		<script src="<%=request.getContextPath()%>/FCKeditor/fckeditor.js"></script>
		<style type="text/css">
.tdbg0 {
	background-color: #ffffff;
	color: #444444
}
</style>
		<script language="javascript">
			function chs(){
				form1.action="/entity/teaching/searchAnyStudent_electivedCourse.action";
				form1.submit();
			}
			
			function resetAll() {
				document.getElementById('tmDateStart').value = '';
				document.getElementById('tmDateEnd').value = '';
				document.getElementById('courseNo').value = '';
				document.getElementById('courseName').value = '';
				document.getElementById('eleDateStart').value = '';
				document.getElementById('eleDateEnd').value = '';
				document.getElementById('status').value = 'all';
				document.getElementById('examStatus').value = 'all';
				document.getElementById('courseRegion').value = '';
				document.getElementById('courseNature').value = '';
				
			}
		</script>
	</head>
	<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" onload="setValue()">
		<form name="form1" method="post" action="">
			<input type="hidden" name="flag" value="<s:property value="flag"/>" />
			<input type="hidden" name="stuId" value="<s:property value="stuId"/>" />
			<table width="100%" border="0" cellspacing="0" cellpadding="4" align="center">
				<tr>
					<td class="tdsectionbar">
						<div class="content_title">
							学习详情
						</div>
					</td>
				</tr>
			</table>
			<div class="cntent_k">
				<table width="100%" border="0" align="center" cellpadding="2" cellspacing="3">
					<tr>
						<td style="font-size: 12px;" colspan="4">
							<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
								<tr>
									<td class="tdbg2" style="font-size: 12px;">
										<font><b>系统编号：</b> <s:property value="peBzzStudent.regNo" /> </font>
										<input type="hidden" name="peBzzStudent.regNo" value="<s:property value="peBzzStudent.regNo"/>" />
									</td>
									<td>
										<font><b>学员姓名：</b> <s:property value="peBzzStudent.trueName" /> </font>
										<input type="hidden" name="peBzzStudent.trueName" value="<s:property value="peBzzStudent.trueName"/>" />
									</td>
									<td class="tdbg2">
										<font><b>证件号码：</b> <s:property value="peBzzStudent.cardNo" /> </font>
										<input type="hidden" name="peBzzStudent.cardNo" value="<s:property value="peBzzStudent.cardNo"/>" />
									</td>
									<td class="tdbg2">
										<font><b>所属机构：</b> <s:property value="peBzzStudent.peEnterprise.name" /> </font>
										<input type="hidden" name="peBzzStudent.peEnterprise.name" value="<s:property value="peBzzStudent.peEnterprise.name"/>" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="4" style="border-top: 1px solid #CCC;" height="10"></td>
					</tr>
					<tr>
						<td nowrap align="left" class="tdbg2" width="11%" colspan="1">
							<font><b>报&nbsp;名&nbsp;时&nbsp;间：</b> </font>
							<font><b>从</b> </font>
							<input type="text" align="left" id="eleDateStart" name="eleDateStart" size="9" readonly value="<s:property value="eleDateStart"/>" />
							<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_a" style="cursor: pointer;" title="选择日期" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />
							<font><b>到</b> </font>
							<input type="text" id="eleDateEnd" name="eleDateEnd" size="9" readonly value="<s:property value="eleDateEnd"/>" />
							<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_b" style="cursor: pointer;" title="选择日期" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />

						</td>
						<td nowrap align="left" class="tdbg2" width="11%" colspan="1">
							<font><b>获得学时时间：</b> </font>
							<font><b>从</b> </font>
							<input type="text" align="left" id="tmDateStart" name="tmDateStart" size="9" readonly value="<s:property value="tmDateStart"/>" />
							<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_c" style="cursor: pointer;" title="选择日期" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />
							<font><b>到</b> </font>
							<input type="text" id="tmDateEnd" name="tmDateEnd" size="9" readonly value="<s:property value="tmDateEnd"/>" />
							<img src="/js/calendar/img.gif" width="20" height="14" id="f_trigger_d" style="cursor: pointer;" title="选择日期" onmouseover="this.style.background='white';" onmouseout="this.style.background=''" />

						</td>
						<td align="center" class="tdbg2" colspan="2">
							<font><b>学习状态：</b> <select name="status" id="status">
									<option value="all" selected>
										全部
									</option>
									<s:if test="status=='INCOMPLETE'">
										<option value="INCOMPLETE" selected <s:if test="status=='INCOMPLETE'"></s:if>>
											正在学习课程
										</option>
									</s:if>
									<s:else>
										<option value="INCOMPLETE" <s:if test="status=='INCOMPLETE'"></s:if>>
											正在学习课程
										</option>
									</s:else>
									<s:if test="status=='COMPLETED'">
										<option value="COMPLETED" selected <s:if test="status=='COMPLETED'"></s:if>>
											已完成学习课程
										</option>
									</s:if>
									<s:else>
										<option value="COMPLETED" <s:if test="status=='COMPLETED'"></s:if>>
											已完成学习课程
										</option>
									</s:else>
									<s:if test="status=='UNCOMPLETE'">
										<option value="UNCOMPLETE" selected <s:if test="status=='UNCOMPLETE'"></s:if>>
											未开始学习课程
										</option>
									</s:if>
									<s:else>
										<option value="UNCOMPLETE" <s:if test="status=='UNCOMPLETE'"></s:if>>
											未开始学习课程
										</option>
									</s:else>
								</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</font>
						</td>
						<td align="left" class="tdbg2" colspan="2">
							<font><b>考&nbsp;试&nbsp;结&nbsp;果：</b> &nbsp;&nbsp;&nbsp; <select name="examStatus" id="examStatus">
									<option value="all" selected>
										全部
									</option>
									<s:if test="examStatus=='PASSED'">
										<option value="PASSED" selected <s:if test="examStatus=='PASSED'"></s:if>>
											通过考试
										</option>
									</s:if>
									<s:else>
										<option value="PASSED" <s:if test="examStatus=='PASSED'"></s:if>>
											通过考试
										</option>
									</s:else>
									<s:if test="examStatus=='FAILED'">
										<option value="FAILED" selected <s:if test="examStatus=='FAILED'"></s:if>>
											未通过考试
										</option>
									</s:if>
									<s:else>
										<option value="FAILED" <s:if test="examStatus=='FAILED'"></s:if>>
											未通过考试
										</option>
									</s:else>
									<s:if test="examStatus=='NOEXAMLEE'">
										<option value="NOEXAMLEE" selected <s:if test="examStatus=='NOEXAMLEE'"></s:if>>
											不考试
										</option>
									</s:if>
									<s:else>
										<option value="NOEXAMLEE" <s:if test="examStatus=='NOEXAMLEE'"></s:if>>
											不考试
										</option>
									</s:else>
								</select>
						</td>
					</tr>
					<tr>
						<td align="left" class="tdbg2">
							<font><b>课程编号:</b> </font>
							<input type="text" align="left" value="<s:property value="courseNo"/>" id="courseNo" name="courseNo" />
						</td>
						<td align="left" class="tdbg2">
							<font><b>课程名称:</b> </font>
							<input type="text" align="left" value="<s:property value="courseName"/>" id="courseName" name="courseName" />
						</td>
						<td align="left" class="tdbg2">
							<font><b>所属区域:</b> </font>
							<select name="courseRegion" id="courseRegion">
								<option value="" selected>
									全部
								</option>
								<s:if test="courseRegion=='Coursearea1'">
									<option value="Coursearea1" selected <s:if test="courseRegion=='Coursearea1'"></s:if>>
										公共区课程
									</option>
								</s:if>
								<s:else>
									<option value="Coursearea1" <s:if test="courseRegion=='Coursearea1'"></s:if>>
										公共区课程
									</option>
								</s:else>
								<s:if test="courseRegion=='Coursearea2'">
									<option value="Coursearea2" selected <s:if test="courseRegion=='Coursearea2'"></s:if>>
										专项课程
									</option>
								</s:if>
								<s:else>
									<option value="Coursearea2" <s:if test="courseRegion=='Coursearea2'"></s:if>>
										专项课程
									</option>
								</s:else>
								<s:if test="courseRegion=='Coursearea0'">
									<option value="Coursearea0" selected <s:if test="courseRegion=='Coursearea0'"></s:if>>
										在线直播课程
									</option>
								</s:if>
								<s:else>
									<option value="Coursearea0" <s:if test="courseRegion=='Coursearea0'"></s:if>>
										在线直播课程
									</option>
								</s:else>
								<s:if test="courseRegion=='Coursearea5'">
									<option value="Coursearea5" selected <s:if test="courseRegion=='Coursearea5'"></s:if>>
										监管内训课程
									</option>
								</s:if>
								<s:else>
									<option value="Coursearea5" <s:if test="courseRegion=='Coursearea5'"></s:if>>
										监管内训课程
									</option>
								</s:else>
							</select>
						</td>
						<td align="left" class="tdbg2">
							<font><b>课程性质</b> </font>
							<select name="courseNature" id="courseNature">
								<option value="" selected>
									全部
								</option>
								<s:if test="courseNature=='402880f32200c249012200c780c40001'">
									<option value="402880f32200c249012200c780c40001" selected <s:if test="courseNature=='402880f32200c249012200c780c40001'"></s:if>>
										必修
									</option>
								</s:if>
								<s:else>
									<option value="402880f32200c249012200c780c40001" <s:if test="courseNature=='402880f32200c249012200c780c40001'"></s:if>>
										必修
									</option>
								</s:else>
								<s:if test="courseNature=='402880f32200c249012200c7f8b30002'">
									<option value="402880f32200c249012200c7f8b30002" selected <s:if test="courseNature=='402880f32200c249012200c7f8b30002'"></s:if>>
										选修
									</option>
								</s:if>
								<s:else>
									<option value="402880f32200c249012200c7f8b30002" <s:if test="courseNature=='402880f32200c249012200c7f8b30002'"></s:if>>
										选修
									</option>
								</s:else>
							</select>
						</td>
						<td align="center" class="tdbg2">
							<input type="submit" value="搜索" />
							&nbsp;&nbsp;
							<input type="button" onclick="resetAll();" value="清空" />
						</td>
					</tr>
				</table>
				<div style="border-top: 1px solid #CCC; margin: 10px;"></div>
				<s:if test="list.size()>0">
					<table width="100%" border="0" cellspacing="1" cellpadding="0">
						<tr>
							<td colspan="9" align="left">
								<font size="3"><b>课程共计:<span id="kcgj1"></span>门 ,共计<span id="gjxs1"></span>学时，已获得<span id="hdxs1"></span>学时 
								</font>
							</td>
						</tr>
					</table>
				</s:if>
				<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="content_title" style="padding: 0px;">
					<style type="text/css">
.td_inf {
	border-bottom: 1px solid #ccc;
}

.td_r {
	border-right: none;
}
</style>
					<tr align="center">
						<td style="display: none;" class="td_inf">
							课程Id
						</td>
						<td width="5%" class="td_inf">
							序号
						</td>
						<td width="5%" class="td_inf">
							课程编号
						</td>
						<td width="20%" class="td_inf">
							课程名称
						</td>
						<td width="5%" class="td_inf">
							课程学时
						</td>
						<td width="5%" class="td_inf">
							是否必修
						</td>
						<td width="10%" class="td_inf">
							报名时间
						</td>
						<td width="10%" class="td_inf">
							所属区域
						</td>
						<td width="10%" class="td_inf">
							学习状态
						</td>
						<td width="10%" class="td_inf">
							考试结果
						</td>

						<td width="5%" class="td_inf">
							获得学时时间
						</td>
						<td width="10%" class="td_inf td_r">
							操作
						</td>
					</tr>
					<s:if test="list.size()==0">
						<tr align="center" class="tdbg0">
							<td colspan=9 bgcolor="rgb(227, 241, 246)" height="6">
								<font color="red" size="4">未查找到对应的结果</font>
							</td>
						</tr>
					</s:if>
					<s:else>
						<s:set name="courseSum" value="0"></s:set>
						<s:set name="courseTime" value="0"></s:set>
						<s:set name="completeTime" value="0"></s:set>
						<s:set name="hasCourse" value="0"></s:set>
						<s:set name="hasTime" value="0"></s:set>
						<s:set name="chooseCourse" value="0"></s:set>
						<s:set name="chooseTime" value="0"></s:set>
						<s:iterator value="list" id="course" status="item">
							<s:set name="courseSum" value="#courseSum + 1" />
							<s:bean name="com.whaty.platform.entity.web.action.teaching.elective.SearchAnyStudentAction" id="sas" />
							<s:set name="courseTime" value="#sas.CalcTime(#courseTime,#course[3])" />
							<s:if test="#course[14]=='1' || #course[14] == 1">
								<s:set name="completeTime" value="#sas.CalcTime(#completeTime,#course[3])" />
								<s:if test="#course[4]=='必修'">
									<s:set name="hasCourse" value="#hasCourse + 1" />
									<s:set name="hasTime" value="#sas.CalcTime(#hasTime,#course[3])" />
								</s:if>
								<s:else>
									<s:set name="chooseCourse" value="#chooseCourse + 1" />
									<s:set name="chooseTime" value="#sas.CalcTime(#chooseTime,#course[3])" />
								</s:else>
							</s:if>
							<tr align="center" class="tdbg0">
								<td style="display: none;" class="td_inf">
									<s:property value="#course[0]" />
								</td>
								<td class="td_inf">
									<s:property value="#item.index+1" />
								</td>
								<td class="td_inf">
									<s:property value="#course[1]" />
								</td>
								<td class="td_inf" align="center">
									<s:property value="#course[2]" />
								</td>
								<td class="td_inf">
									<s:property value="#course[3]" />
								</td>
								<td class="td_inf">
									<s:property value="#course[4]" />
								</td>
								<td class="td_inf">
									<s:property value="#course[17]" default="--" />
								</td>
								<td class="td_inf">
									<s:property value="#course[10]" default="--" />
								</td>
								<td class="td_inf">
									<s:if test="#course[7]=='COMPLETED'">已完成学习</s:if>
									<s:elseif test="#course[7]=='INCOMPLETE'">正在学习</s:elseif>
									<s:else>未开始学习</s:else>
								</td>
								<td class="td_inf">
									<s:if test="#course[8]=='PASSED'">通过考试</s:if>
									<s:elseif test="#course[8]=='FAILED'">未通过考试</s:elseif>
									<!-- Lee start 2014年2月26日 -->
									<s:elseif test="#course[8]=='NOEXAMLEE'">不考试</s:elseif>
									<!-- Lee end -->
									<s:else>--</s:else>
								</td>
								<td width="5%" class="td_inf">
									<s:property value="#course[16]" default="--" />
								</td>

								<td class="td_inf td_r">
									<a target="_blank" href="/entity/teaching/searchAnyStudent_courseInfo.action?courseId=<s:property value="#course[0]"/>&opencourseId=<s:property value="#course[9]"/>&sid=<s:property value="peBzzStudent.id"/>&courseCode=<s:property value="#course[1]" />">查看</a>
								</td>
							</tr>
						</s:iterator>
					</s:else>
					<s:if test="list.size()>0">
						<tr>
							<td colspan="9" align="left">
								<font size="3"><b>课程共计:<span id="kcgj"><s:property value="#courseSum" /></span>门 ,共计<span id="gjxs"><s:property value="#courseTime" /></span>学时，获得<span id="hdxs"><s:property value="#completeTime" /></span>学时 其中，必修：<s:property value="#hasCourse" />门，<s:property value="#hasTime" />学时；选修: <s:property value="#chooseCourse" />门，<s:property value="#chooseTime" />学时 </b>
								</font>
							</td>
						</tr>
					</s:if>
					<tr>
						<td colspan="20" align="center">
							<input type="button" value="&nbsp;&nbsp;关 &nbsp;&nbsp;闭&nbsp;&nbsp;" onclick="javascript:window.close();"></input>
						</td>
					</tr>
				</table>
		</form>
	</body>
</html>
<script type="text/javascript">
	function setValue(){
		var kcgj = document.getElementById("kcgj").innerText;
		var gjxs = document.getElementById("gjxs").innerText;
		var hdxs = document.getElementById("hdxs").innerText;
		document.getElementById("kcgj1").innerText = kcgj;
		document.getElementById("gjxs1").innerText = gjxs;
		document.getElementById("hdxs1").innerText = hdxs;
	}
    Calendar.setup({
        inputField     :    "eleDateStart",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_a",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
    Calendar.setup({
        inputField     :    "eleDateEnd",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_b",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
    Calendar.setup({
        inputField     :    "tmDateStart",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_c",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
    Calendar.setup({
        inputField     :    "tmDateEnd",     // id of the input field
        ifFormat       :    "%Y-%m-%d",       // format of the input field
        button         :    "f_trigger_d",  // trigger for the calendar (button ID)
        align          :    "Tl",           // alignment (defaults to "Bl")
        singleClick    :    true
    });
</script>

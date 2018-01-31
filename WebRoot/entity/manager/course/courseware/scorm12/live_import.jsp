<%@page import="com.whaty.platform.standard.scorm.util.ScormConstant"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.io.File"%>
<%
	/*---------------------------------------------
	 Function Description:	
	 Editing Time:	
	 Editor: chenjian
	 Target File:	
	 	 
	 Revise Log
	 Revise Time:  Revise Content:   Reviser:
	 -----------------------------------------------*/
%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.util.*,com.whaty.platform.training.*"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@ include file="./readerror.jsp"%>
<%!//判断字符串为空的话，赋值为""
	String fixnull(Object str) {
		if (str == null || str.equals("null") || str.equals(""))
			return "";
		else
			return str.toString();

	}%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>培训平台－后台管理</title>
		<link href="<%=request.getContextPath()%>/work/manager/css/style.css" rel="stylesheet" type="text/css">
		<script language="JavaScript" src="../../../js/frame.js"></script>
		<style type="text/css">
<!--
.w1 {
	font-family: "宋体";
	font-size: 12px;
	line-height: 24px;
	color: #333333;
	text-decoration: none;
}

.w2 {
	font-family: "宋体";
	font-size: 14px;
	line-height: 24px;
	font-weight: bold;
	color: #265A8E;
	text-decoration: none;
}
-->
</style>
		<script>
function checkValues(){
      if ( courseInfo.title.value == "" || 
           courseInfo.coursezipfile.value == "" || courseInfo.scorm_index.value== ""){
         alert( "请输入必填项" );
         return false;
      }
      courseInfo.theZipFile.value = courseInfo.coursezipfile.value;
      return checkScormType();
   }
   function checkScormType() {
   	if(document.getElementById("scorm_type").value=="") {
   		alert("请您选择课件类型");
   		return false;
   	}
   	return true;
   }
</script>
	</head>
	<%
		String courseware_code = fixnull(request.getAttribute("courseware_code"));
		String courseware_id = fixnull(request.getAttribute("courseware_id"));
		String courseware_name = fixnull(request.getAttribute("courseware_name"));
		/***whatyutil的fileManage使用解压方法时多层次结构目录会失败，所以先创建目录***/
		/*创建课件目录start huze*/
		try {
			String theWebPath = getServletConfig().getServletContext().getRealPath("/");
			new File(theWebPath + "CourseImports" + File.separator + courseware_code).mkdirs();
		} catch (Exception e) {
		}
		/*创建课件目录end huze*/
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select name from pe_bzz_tch_courseware where code='" + courseware_code + "'";
		rs = db.executeQuery(sql);
		if (rs != null && rs.next()) {
			courseware_name = fixnull(rs.getString("name"));
		}
		db.close(rs);
		/*scorm课件多表现形式start edit by huze*/
		List<String[]> typeList = new ArrayList<String[]>();
		StringBuilder sb = new StringBuilder();
		sb.append("select a.code, a.name                      ");
		sb.append("  from scorm_type a                    ");
		sb.append(" where not exists (select 1            ");
		sb.append("          from scorm_sco_launch b      ");
		sb.append("         where a.code = b.scorm_type   ");
		sb.append("           and b.course_id = '" + courseware_id + "') order by a.code");
		rs = db.executeQuery(sb.toString());
		if (rs != null && rs.next()) {
			String[] type1 = { StringUtils.defaultIfEmpty(rs.getString("code"), ""), StringUtils.defaultIfEmpty(rs.getString("name"), "") };
			typeList.add(type1);
			while (rs.next()) {
				String[] type2 = { StringUtils.defaultIfEmpty(rs.getString("code"), ""), StringUtils.defaultIfEmpty(rs.getString("name"), "") };
				typeList.add(type2);
			}
		} else {
			String[] type1 = { "", "您没有剩余类型可选择了" };
			typeList.add(type1);
		}
		db.close(rs);
		/*scorm课件多表现形式end*/
	%>
	<body leftmargin="0" rightmargin="0" style="background-color: transparent;" class="scllbar">
		<!-- start:图标区域 -->
		<!-- end:图标区域 -->
		<form name='courseInfo' action='/entity/manager/course/courseware/scorm12/courseImport.jsp' method='post' class='nomargin' onSubmit="return checkValues()" enctype="multipart/form-data">
			<!-- start:内容区域1 -->
			<div unselectable="on" class="border">
				<table width=60% border="0" align="center" cellpadding="2" cellspacing="1" bgcolor="3F6C61">
					<tr bgcolor="#FFFFFF">
						<td align='center' bgColor=#E6EFF9 class="w2" colspan="3">
							导入SCORM1.2版课件
						</td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td align='left' bgColor=#ffffff class="w1">
							<span>课件编号：</span>
						</td>
						<td bgColor=#ffffff class="w1">
							<input class="xmlInput textInput" name="course_id" id="course_id" value="<%=courseware_code%>" readonly="readonly">
						</td>
						<td bgColor=#ffffff class="w1">
							*必填
						</td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td align='left' bgColor=#ffffff class="w1">
							<span>课件名称：</span>
						</td>
						<td bgColor=#ffffff class="w1">
							<input class="xmlInput textInput" name="course_title" id="course_title" value="<%=courseware_name%>" readonly="readonly">
						</td>
						<td bgColor=#ffffff class="w1">
							*必填
						</td>
					</tr>
					<%
						/*scorm课件多表现形式start edit by huze*/
					%>
					<tr bgcolor="#FFFFFF" style="display: none;">
						<td align='left' bgColor=#ffffff class="w1">
							<span>课件类型：</span>
						</td>
						<td bgColor=#ffffff class="w1">
							<select name="scorm_type" id="scorm_type">
								<%
									for (String[] strs : typeList) {
								%>
								<option value="<%=strs[0]%>"><%=strs[1]%></option>
								<%
									}
								%>
							</select>
						</td>
						<td bgColor=#ffffff class="w1">
							*必填
						</td>
					</tr>
					<tr bgcolor="#FFFFFF">
						<td align='left' bgColor=#ffffff class="w1">
							<span>课件首页：</span>
						</td>
						<td bgColor=#ffffff class="w1">
							<input type='text' class="xmlInput textInput" name="scorm_index" id="scorm_index" value="record.html" />
						</td>
						<td bgColor=#ffffff class="w1">
							*必填
						</td>
					</tr>
					<%
						/*scorm课件多表现形式end*/
					%>
					<tr bgcolor="#FFFFFF">
						<td align='left' bgColor=#ffffff class="w1">
							<span>要导入的SCORM压缩包：</span>
						</td>
						<td bgColor=#ffffff class="w1">
							<input type="File" name="coursezipfile" size="20" value="100">
						</td>
						<td bgColor=#ffffff class="w1">
							*必填
						</td>
					</tr>
					<tr bgcolor="#FFFFFF" style="display: none">
						<td align='left' bgColor=#ffffff class="w1">
							<span>课程的控制方式：</span>
						</td>
						<td bgColor=#ffffff class="w1">
							<!-- 顺序访问&nbsp;&nbsp;&nbsp;&nbsp;
		            <input type="radio" name="controltype" value="flow" checked><br/> -->
							<input type="radio" name="controltype" value="choice" checked>
							&nbsp;&nbsp;自由访问
						</td>
						<td bgColor=#ffffff class="w1">
							*必填
						</td>
					</tr>
					<%
						String sqlNavigate = "select navigate from scorm_course_info where id='" + courseware_id + "'";
						rs = db.executeQuery(sqlNavigate);
						String naviagteStr = "";
						if (rs != null && rs.next()) {
							naviagteStr = fixnull(rs.getString("navigate"));
						}
						db.close(rs);
						if (naviagteStr.trim().length() > 0) {
					%>
					<tr bgcolor="#FFFFFF" style="display: none">
						<td align='left' bgColor=#ffffff class="w1">
							<span>课件导航方式：</span>
						</td>
						<td bgColor=#ffffff class="w1"><%=ScormConstant.SCORM_NAVIGATE_COURSEWARE.equalsIgnoreCase(naviagteStr) ? "课件导航" : "课件导航"%>
						<td bgColor=#ffffff class="w1">
							(
						</td>
					</tr>
					<%
						} else {
					%>
					<tr bgcolor="#FFFFFF" style="display: none">
						<td align='left' bgColor=#ffffff class="w1">
							<span>课件导航方式：</span>
						</td>
						<td bgColor=#ffffff class="w1">
							<input type="radio" name="navigate" value="<%=ScormConstant.SCORM_NAVIGATE_PLATFORM%>">
							&nbsp;&nbsp;平台导航&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="navigate" value="<%=ScormConstant.SCORM_NAVIGATE_COURSEWARE%>" checked>
							课件导航&nbsp;&nbsp;
						</td>

						<td bgColor=#ffffff class="w1">
							*必填
						</td>
					</tr>
					<%
						}
					%>
					<%
						if (error != null) {
					%>
					<tr bgcolor="#FFFFFF">
						<td align='left' colspan='4' bgColor=#ffffff class="w1">
							<span style="color: red">导入课程出错：<%=error%></span>
						</td>

					</tr>
					<%
						}
					%>
					<tr bgcolor="#FFFFFF">
						<td colspan="3" align="center" bgColor=#ffffff class="w1">
							<input name="ok" value="提 交" type="submit" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">

							<input name="cancel" type="button" value="关 闭" onclick="window.close()" class="dialogbutton" onmouseover="this.className='dialogbuttonOver'" onmouseout="this.className='dialogbutton'">
						</td>
					</tr>
				</table>
			</div>
			<!-- end:内容区域1 -->
			<input type=hidden name="theManifest">
			<input type=hidden name="theZipFile">
			<!-- button row start -->
			<div class="dialogbuttons" unselectable="on">
			</div>
			<!-- button row end -->
		</form>
		<!-- 内容区域结束 -->
	</body>
</html>
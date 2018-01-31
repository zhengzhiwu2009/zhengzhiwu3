<%@ page language="java" import="com.whaty.util.*,java.util.*"
	pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>

<html>
	<head>

		<title></title>
		<meta http-equiv="expires" content="0">
		<link href="./css/css.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet" type="text/css">
		<%
			String tagEnt = fixnull(request.getParameter("tagEnt"));
			String actionUrl = "";
			dbpool db = new dbpool();
			MyResultSet rs = null;
			if(tagEnt == null || tagEnt.equals("1") || tagEnt.equals("2")) {
				actionUrl = "/entity/manager/statistics/stat_study_export_enterprise_excel.jsp";
			} else  if(tagEnt.equals("3")) {
				actionUrl = "/entity/manager/statistics/stat_study_export_course_appraisal_excel.jsp";
			}
			else  if(tagEnt.equals("7")) {
				actionUrl = "/entity/manager/statistics/stat_study_export_course_appraisal_excel1.jsp";
			}
			 else if(tagEnt.equals("4")) {
			%>
				<script type="text/javascript">
				function exportZY()
				{
					var form1 = document.getElementById("form1");
					form1.action="/entity/manager/statistics/stat_study_export_zyzc_excel.jsp?type=zy";
					form1.submit();
				}
				
				function exportZC()
				{
					var form1 = document.getElementById("form1");
					form1.action="/entity/manager/statistics/stat_study_export_zyzc_excel.jsp?type=zc";
					form1.submit();
				}
				</script>
			<% 
			} else if(tagEnt.equals("5")) {
				actionUrl = "/entity/manager/statistics/stat_study_export_progress_excel.jsp";
				%>
				<script type="text/javascript">
					function isNumber(e) 
				   { 
				   	  var oNum = e.value;
					  if(!oNum) return false; 
					  var strP=/^\d+(\.\d+)?$/; 
					  if(!strP.test(oNum))
					  {
					  	alert("请输入数字！");
					  	e.value="0";
					  	e.focus();
					  	return;
					  } 
					  try{ 
					  if(parseFloat(oNum)!=oNum)
					   {
					  	alert("请输入数字！");
					  	e.value="0";
					  	e.focus();
					  	return;
					  } 
					  } 
					  catch(ex) 
					  {
					  	alert("请输入数字！");
					  	e.value="0";
					  	e.focus();
					  } 
					  return true; 
				   }
				   
				   var strArea = new Array(); 
				   strArea[0] = ['所有一级企业','all','C'];//['标签','本身的ID','上级菜单编号'] 
				   <%
				   String sql_t = "select id,name,fk_parent_id from pe_enterprise";
				   rs = db.executeQuery(sql_t);
					int i = 1;
					while (rs != null && rs.next()) {
						String id = fixnull(rs.getString("id"));
						String ent_name = fixnull(rs.getString("name"));
						String fk_parent_id = fixnull(rs.getString("fk_parent_id"));
						if(fk_parent_id == null || fk_parent_id.equals(""))	{
							fk_parent_id = "C";
						}
					%>
					strArea[<%=i%>] = ['<%=ent_name%>','<%=id%>','<%=fk_parent_id%>'];
					<%
						i++;
					}
					db.close(rs);
				%>

// 清空列表 
function ddl_Clear(ddl_name){ 
	  
    var obj = document.getElementById(ddl_name); 
      
    for(var i = obj.options.length - 1;i >= 0;i--){ 
        obj.options[i] = null; 
    } 
    
} 

// 选中匹配项 
function ddl_selected(ddl_name,match_val,isValue){ 
    var obj = document.getElementById(ddl_name); 
    for( var i = 0; i < obj.options.length; i++ ){ 
        var matchobj = obj.options[i].value; 
        if(!isValue){ 
            matchobj = obj.options[i].Text; 
        } 
        if(match_val == matchobj){ 
            obj.options[i].selected = "selected"; 
        } 
    } 
} 

// 去掉所有空格 
function delspace(findstr){ 
    // 先去掉空格 
    var myfind = findstr; 
    // 因为这里的.replace 只替换当前找到的第一个。所以这里用循环 
    for(var i = 0; i < findstr.length; i++){ 
        var myfind = myfind.replace(" ",""); 
    } 
    return myfind; 
} 

// 初始化第一个下拉列表内容 
function ddl_Bind(ddl_name,bindData,keyword){ 
    // 获得一类下拉列表对象 
    var obj = document.getElementById(ddl_name); 
     
    for( var i = 0;i < bindData.length; i++ ){ 
        if(bindData[i][2] == keyword){ 
              if(i == 0){ 
                  if(bindData[i][0] != ""){ 
                      obj.add(new Option(bindData[i][0],bindData[i][1])); 
                  } 
              } 
              else{ 
                  obj.add(new Option(bindData[i][0],bindData[i][1])); 
              } 
        }     
    } 
    var num = 2; 
    var subValue = obj.options[num].value; 
     
    ddl_selected(ddl_name, num, true) 
    ddl_changed('section', subValue, strArea); 
} 

// 列表内容变化时，实现其子列表动态加载 
function ddl_changed(ddl_name,keywords,ddl_data){     
    // 获得一类下拉列表对象 
    
    var obj = document.getElementById(ddl_name); 
    // 添加下一级分类的计数器 
    var m = 1; 
     
    ddl_Clear(ddl_name);
    
    // 先添加一个'标题' 
    // 添加下一级分类 
    obj.options[0] = ( new Option( '请选择...','' ) ); 
    for( var i = 0; i < ddl_data.length; i++ ){         
        if( ddl_data[i][2] == keywords ){ 
            obj.options[m] = ( new Option( ddl_data[i][0],ddl_data[i][1] ) ); 
            m = m + 1; 
        }         
    }
   
    obj.fireEvent("onchange"); 
} 
				   
				</script>
				<% 
			} else if(tagEnt.equals("6")) {
				actionUrl = "/entity/manager/statistics/stat_account_nologin_excel.jsp";
			}

			
%>
		
	</head>

	<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA" <%if(tagEnt.equals("5")) {%>onload="ddl_Bind('enterprise_id',strArea,strArea[0][2]);"<%} %>>

		<div id="main_content">
			<div class="content_title">
				学习相关统计
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<form id="form1"
						action="<%=actionUrl%>"
						method="post">
						<input type="hidden" name="tagEnt" value="<%=tagEnt%>">
						<table width="554" border="0" align="center" cellpadding="0"
							cellspacing="0">

							<tr>
								<td height="26" align="center" valign="middle" colspan="2">
									请选择导出学期<% if(tagEnt.equals("5")){%>和学时段<% }%>:
								</td>
							</tr>
							<tr>
								<td height="10">
								</td>
							</tr>

							<tr valign="middle">
								<td width="140" height="30" align="left" class="postFormBox">
									<span class="name">学期：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<select name="batch_id">
										<option value="">
											所有学期
										</option>
										<%
											String sql_t = "select id,name from pe_bzz_batch order by id";
											rs = db.executeQuery(sql_t);
											while (rs != null && rs.next()) {
												String id = fixnull(rs.getString("id"));
												String batch_name = fixnull(rs.getString("name"));
										%>
										<option value=<%=id%>><%=batch_name%></option>
										<%
											}
											db.close(rs);
										%>
									</select>
								</td>
							</tr>
							
							<% if(tagEnt.equals("5")) { %>
							<tr valign="middle">
								<td width="140" height="30" align="left" class="postFormBox">
									<span class="name">所在集团：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
								<select name="enterprise_id" id='enterprise_id' onChange="ddl_changed('sub_enterprise_id',this.value,strArea)">
								</select>
								</td>
							</tr>
							<tr valign="middle">
								<td width="140" height="30" align="left" class="postFormBox">
									<span class="name">所在企业：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
								<select name="sub_enterprise_id" id='sub_enterprise_id'>
								<option value="">请选择...</option>
									</select>
								</td>
							</tr>
							<tr valign="middle">
								<td width="140" height="30" align="left" class="postFormBox">
									<span class="name">开始课时段：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<input type="text" id="sStime" name="sStime" size ="12" value="0" onchange="isNumber(this)">
								</td>
							</tr>
							<tr valign="middle">
								<td width="140" height="30" align="left" class="postFormBox">
									<span class="name">结束课时段：</span>
								</td>
								<td class="postFormBox" style="padding-left: 18px">
									<input type="text" id="eStime" name="eStime" size ="12" value="0"  onchange="isNumber(this)">
								</td>
							</tr>
							<%} %>
							
							<tr>
								<td height="10">
								</td>
							</tr>
							
							<tr valign="middle">
								<td align="center">
									<input type="button" value="返回" onclick="history.back();">
								</td>
								<% if(tagEnt!=null && tagEnt.equals("4")) {%>
								<td align="center">
									<input type="button" onclick="exportZY();" value="作业报表导出">
									<input type="button" onclick="exportZC();" value="自测报表导出">
								</td>
								<%} else { %>
								<td align="center">
									<input type="submit" value="导出报表">
								</td>
								<%} %>
							</tr>


							<tr>
								<td height="10">
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>

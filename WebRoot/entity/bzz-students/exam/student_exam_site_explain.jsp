<%@ page language="java" import="java.net.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>

<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="com.whaty.platform.entity.*,com.whaty.platform.entity.user.*,com.whaty.platform.interaction.*"%>
<%@ page import="com.whaty.platform.entity.activity.*"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool" />
<jsp:directive.page
	import="com.whaty.platform.database.oracle.MyResultSet" />



<%!String fixnull1(String str) {
		if (str == null || str.equals("null") || str.equals("")) {
			str = "";
			return str;
		}
		return str;
	}%>
<%!String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals("")) {
			str = "";
			return str;
		}
		return str;
	}%>
<%
	dbpool db = new dbpool();
	String sql = "";
	MyResultSet rs = null;
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title></title>
		<link href="/entity/function/css/css.css" rel="stylesheet"
			type="text/css">

		<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}

.STYLE1 {
	font-size: 12px;
	color: #0041A1;
	font-weight: bold;
}

.STYLE2 {
	color: #FF5F01;
	font-weight: bold;
}

.kctx_zi1 {
	font-size: 12px;
	color: #0041A1;
}

.kctx_zi2 {
	font-size: 12px;
	color: #54575B;
	line-height: 24px;
	padding-top: 2px;
}
-->
</style>
		<script language="JavaScript">
function redirect(x)
	{
		//alert(document.search.siteShi.options.value);
		var i ,cnt;
		i=0;
		cnt = 0;
		for (var m=document.search.siteShi.options.length-1;m>0;m--)
			{
				document.search.siteShi.options[m]=null;
			}
		<%
				String sql_sheng = "select distinct s.sheng\n" +
							   "  from pe_bzz_exambatch_site bs, pe_bzz_exambatch b, pe_bzz_examsite s\n" + 
								" where bs.exambatch_id = b.id and  bs.site_id = s.id\n" + 
								"   and b.selected ='402880a91dadc115011dadfcf26b00aa'";
				MyResultSet rs_sheng = db.executeQuery(sql_sheng);
				while(rs_sheng!=null&&rs_sheng.next())
				{
		%>
					sheng = '<%=rs_sheng.getString("sheng")%>';
					if(sheng==x) 
					{
					   <%
					     String sql_shi = 
							"select distinct s.shi\n" +
							"  from pe_bzz_exambatch_site bs, pe_bzz_exambatch b, pe_bzz_examsite s\n" + 
							" where bs.exambatch_id = b.id and  bs.site_id = s.id\n" + 
							"   and b.selected ='402880a91dadc115011dadfcf26b00aa' and s.sheng like '%"+rs_sheng.getString("sheng")+"%'";
						 //System.out.println(sql_shi);
					     MyResultSet rs_shi = db.executeQuery(sql_shi);
					     while(rs_shi!= null && rs_shi.next())
					     {
					   %>
						    shi = '<%=rs_shi.getString("shi")%>';
							cnt = cnt+1;
							document.search.siteShi.options[cnt]=new Option(shi,shi);
						
					   <%
			   				}
						db.close(rs_shi);
			 %>
					}
			  <%	
		  		 } db.close(rs_sheng);
			  %>
	}
	
</script>
		<script type="text/javascript">
function check(){
    var siteSheng = document.getElementById("siteSheng").value;
    var siteShi = document.getElementById("siteShi").value;
	if(siteSheng == "")
	{
		alert("请选择考点所在省份！");
		return false;
	}
	if(siteShi == "")
	{
		alert("请选择考点所在城市！");
		return false;
	}
	return true;
	
}
</script>
	</head>
	<body leftmargin="0" topmargin="0"
		background="/entity/function/images/bg2.gif">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td valign="top" class="bg3">

					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="45" valign="top"></td>
						</tr>
						<tr>
							<td align="center" valign="top">
								<form name=search
									action="/entity/workspaceStudent/bzzstudent_examSi.action"
									method="post" onsubmit="return check();">
									<table width="765" border="0" cellspacing="0" cellpadding="0">
										<tr>
											<td height="65"
												background="/entity/function/images/table_01.gif">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td align="center" class="text1">
															<img src="/entity/function/images/xb3.gif" width="17"
																height="15">
															<strong>考点说明</strong>
														</td>
														<td width="300">
															&nbsp;
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td background="/entity/function/images/table_02.gif">
												<table width="95%" border="0" align="center" cellpadding="0"
													cellspacing="0" class="bg4">
													<tr>
														<td class="text2">
															<table border="0" align="center" cellpadding="0"
																cellspacing="0" width=77%>
																<tr>
																	<td>
																		学员您好：请先下载查看考试地点明细表，以便您选择方便的考试地点参加考试。
																		<br/>
																		下面是考点明细表的下载链接：
																		<a href="/web/bzz_index/files/kdmxb_2011.xls"
																			target="_blank"><font color="red"><b>点击下载</b>
																		</font> </a>
																		<br/>
																	</td>
																</tr>
															</table>
															<table width="70%" border="0" align="center"
																cellpadding="0" cellspacing="1" class="bg4"
																bgcolor="#D7D7D7">
																<tr>
																	<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
																		<strong>选择考点所在省：</strong>
																	</td>
																	<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
																		width=300>
																		<select name="siteSheng"
																			onchange="redirect(this.options.value)"
																			id="siteSheng">
																			<option value="">
																				请选择...
																			</option>
																			<%
																				sql = "select distinct s.sheng\n"
																						+ "  from pe_bzz_exambatch_site bs, pe_bzz_exambatch b, pe_bzz_examsite s\n"
																						+ " where bs.exambatch_id = b.id and  bs.site_id = s.id\n"
																						+ "   and b.selected ='402880a91dadc115011dadfcf26b00aa' ";

																				rs = db.executeQuery(sql);
																				while (rs != null && rs.next()) {
																					String sh = rs.getString("sheng");
																			%>
																			<option value="<%=fixnull(rs.getString("sheng"))%>"><%=fixnull(rs.getString("sheng"))%></option>
																			<%
																				}
																				db.close(rs);
																			%>
																		</select>
																	</td>
																</tr>
																<tr>
																	<td align="center" bgcolor="#E9F4FF" class="kctx_zi1">
																		<strong>选择考点所在市：</strong>
																	</td>
																	<td align="left" bgcolor="#FAFAFA" class="kctx_zi2"
																		width=300>
																		<select id="siteShi" name="siteShi" id="siteShi">
																			<option value="">
																				请选择考点所在省...
																			</option>

																		</select>

																	</td>
																</tr>
																<tr>
																	<td>
																		<table border="0" align="center" cellpadding="0"
																			cellspacing="0">
																			<tr>
																				<td>
																					&nbsp;
																				</td>
																			</tr>
																			<tr>
																				<td align="center" width="600">
																					<input type="submit" value="下一步" />
																					<!-- <input type="button" value="下一步"  onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_latestudentInfo.action'"/> -->
																					&nbsp;&nbsp;&nbsp;&nbsp;
																					<input type="button" value="返回"
																						onClick="javascript:window.location = '/entity/workspaceStudent/bzzstudent_toModifyInfoN.action'" />
																				</td>
																			</tr>

																		</table>
																	</td>
																</tr>
																<tr>
																	<td>
																		<img src="/entity/function/images/table_03.gif"
																			width="765" height="21">
																	</td>
																</tr>
															</table>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>
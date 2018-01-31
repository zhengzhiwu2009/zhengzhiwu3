<%@ page language="java" import="com.whaty.util.*" pageEncoding="UTF-8"%>
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

<%
		String batch_id = fixnull(request.getParameter("batch_id"));
		
		String sql_rec = "";
		String sql_fax = "";
		String sql_s = "";
		String sql_mail = "";
		String sql_zk = "";
		if(batch_id != null && !batch_id.equals("")) {
			sql_rec = " and rec.fk_batch_id='" + batch_id + "'";
			sql_fax = " and fax.fk_batch_id='" + batch_id + "'";
			sql_s = " and s.fk_batch_id='" + batch_id + "'";
			sql_mail = " and mail.fk_batch_id='" + batch_id + "'";
			sql_zk = " and zk.fk_batch_id='" + batch_id + "'";
		}
		String sql = "select a.*,b.*,c.*,d.*, c.bmnum-d.jfnum as njfnum, e.*, f.*,g.*, h.*,i.*,j.* from \n"
			+ "--一级企业报名数 只在一recruit表中有记录的企业数 \n"
			+ "(select count(distinct ent1.id) as ent1num \n"
			+ "from pe_enterprise ent1,pe_enterprise ent2,pe_bzz_recruit rec \n"
			+ "where ent2.fk_parent_id=ent1.id and ent1.fk_parent_id is null \n"
			+ "      and rec.fk_enterprise_id=ent2.id \n"
			+ "     " + sql_rec + " \n"
			+ "    ) a, \n"
			+ "--二级企业报名数 只在一recruit表中有记录的企业数 \n"
			+ "(select count(distinct ent2.id) as ent2num \n"
			+ "from pe_enterprise ent2,pe_bzz_recruit rec \n"
			+ "where ent2.fk_parent_id is not null \n"
			+ "      and rec.fk_enterprise_id=ent2.id \n"
			+ "     " + sql_rec + " \n"
			+ "    )b, \n"
			+ "--报名总人数 pe_bzz_recruit某学期下的人数 \n"
			+ "(select count(rec.id) as bmnum \n"
			+ "from pe_bzz_recruit rec \n"
			+ "where 1=1 \n" 
			+ "     " + sql_rec + " \n"
			+ "     ) c, \n"
			+ "--缴费总人数 在底联表中，某学期下，（发票状态为已开和未开）的 人数（num的总合） \n"
			+ "(select sum(fax.num) as jfnum \n"
			+ "from pe_bzz_fax_info fax \n"
			+ "where 1=1 \n" 
			+ "     " + sql_fax + " \n"
			+ "     ) d, \n"
			+ "--未缴费总人数 报名总人数-缴费总人数 \n"
			+ "--已开发票人数  \n"
			+ "(select sum(fax.num) as fpnum \n"
			+ "from pe_bzz_fax_info fax,enum_const const \n"
			+ "where fax.flag_fp_open_state = const.id  and const.name='已开' \n"
			+ "      " + sql_fax + " \n"
			+ "      ) e, \n"
			+ "--注册总人数 某学期下，在pe_bzz_student表中已生成学号的数量。 \n"
			+ "(select count(s.reg_no) as zcnum \n"
			+ "from pe_bzz_student s \n"
			+ "       ,sso_user su \n"
			+ "where 1=1 \n"
			+ "      " + sql_s + " \n"
			+ "      and s.fk_sso_user_id=su.id and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_recruit_id is not null\n"
			+ "      ) f, \n"
			+ "--导出总人数 是某报名学期下已导出人数. \n"
			+ "(select count(s.reg_no) as dcnum \n"
			+ "from pe_bzz_student s \n"
			+ "       ,sso_user su \n"
			+ "where s.export_state='1' \n"
			+ "      " + sql_s + " \n"
			+ "      and s.fk_sso_user_id=su.id and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006'\n"
			+ "       ) g, \n"
			+ "--制卡总人数 某报名学期中制卡信息表中收到人数之和。 \n"
			+ "(select sum(zk.received_person_num) as zknum \n"
			+ "from pe_bzz_zk_info zk \n"
			+ "--     ,sso_user su \n"
			+ "where 1=1 \n"
			+ "      " + sql_zk + " \n"
			+ "   -- and s.fk_sso_user_id=su.id and su.flag_isvalid='2' \n"
			+ "       ) h,    \n"
			+ "--邮寄总人数 某报名学期中邮寄信息表中件数之和。 \n"
			+ "(select sum(mail.num) as mailnum \n"
			+ "from pe_bzz_mail_info mail \n"
			+ "--     ,sso_user su \n"
			+ "where 1=1 \n"
			+ "      " + sql_mail + " \n"
			+ "   -- and s.fk_sso_user_id=su.id and su.flag_isvalid='2' \n"
			+ "       ) i, \n"
			+ "--收到人数 某报名学期中邮寄信息表中已收件数之和。 \n"
			+ "(select sum(mail.num) as sdnum \n"
			+ "from pe_bzz_mail_info mail,enum_const const \n"
			+ "--     ,sso_user su \n"
			+ "where mail.flag_mail_send_state=const.id and const.name='已收' \n"
			+ "      " + sql_mail + " \n"
			+ "   -- and s.fk_sso_user_id=su.id and su.flag_isvalid='2' \n"
			+ "       ) j";
	//System.out.println(sql);
	dbpool db = new dbpool();
	MyResultSet rs = db.executeQuery(sql);
	int ent1num = 0;
	int ent2num = 0;
	int bmnum = 0;
	int jfnum = 0;
	int njfnum = 0;
	int fpnum = 0;
	int zcnum = 0;
	int dcnum = 0;
	int zknum = 0;
	int mailnum = 0;
	int sdnum = 0;

	if (rs.next()) {
		ent1num = rs.getInt("ent1num");
		ent2num = rs.getInt("ent2num");
		bmnum = rs.getInt("bmnum");
		jfnum = rs.getInt("jfnum");
		njfnum = rs.getInt("njfnum");
		fpnum = rs.getInt("fpnum");
		zcnum = rs.getInt("zcnum");
		dcnum = rs.getInt("dcnum");
		zknum = rs.getInt("zknum");
		mailnum = rs.getInt("mailnum");
		sdnum = rs.getInt("sdnum");
	}
	db.close(rs);
%>
<html>
	<head>

		<title></title>
		<meta http-equiv="expires" content="0">
		<link href="./css/css.css" rel="stylesheet" type="text/css">
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
			
		<script type="text/javascript">
			function changeSelect(e)
			{
				e.form.submit();
			}
		</script>
	</head>

	<body topmargin="0" leftmargin="0" bgcolor="#FAFAFA">

		<div id="main_content">
			<div class="content_title">
				报名信息统计
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<table width="554" border="0" align="center" cellpadding="0"
						cellspacing="0">

						<tr>
							<td height="26" align="center" valign="middle" colspan="2">
								报名信息统计
							</td>
						</tr>
						<tr>
							<td height="8">
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">请选择学期：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
							<form action="/entity/manager/statistics/stat_recruit_status.jsp" method="post">
							<select id="batch_id" name="batch_id" onchange="changeSelect(this)">
								<option value="">所有学期</option>
								<%
									String t_sql = "select id,name from pe_bzz_batch order by name";
									rs = db.executeQuery(t_sql);
									while (rs != null && rs.next()) {
										String t_id = fixnull(rs.getString("id"));
										String t_name = fixnull(rs.getString("name"));
										String selected = "";
										//System.out.println(search);
										if (batch_id.equals(t_id))
											selected = "selected";
										//System.out.println(selected);
								%>
								<option value="<%=t_id%>" <%=selected%>><%=t_name%></option>
								<%
									}
									db.close(rs);
								%>
							</select>
							</form>
							</td>
						</tr>
						<tr>
							<td height="8">
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">报名一级企业数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=ent1num%>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">报名二级企业数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=ent2num%>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">报名总人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=bmnum%>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">缴费总人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=jfnum%>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">未缴费总人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px">
								<%if(njfnum < 0) {%>
									<font color="red"><%=njfnum%></font>
								<%} else { %>
									<%=njfnum%>
								<%}%>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">已开发票人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=fpnum%>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">注册总人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=zcnum%>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">导出总人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=dcnum%>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">制卡总人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=zknum%>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">邮寄总人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=mailnum%>
							</td>
						</tr>
						<tr valign="middle">
							<td width="140" height="30" align="left" class="postFormBox">
								<span class="name">收到总人数：</span>
							</td>
							<td class="postFormBox" style="padding-left: 18px"><%=sdnum%>
							</td>
						</tr>
						<tr>
						<tr>
							<td height="10">
							</td>
						</tr>
					</table>

				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>

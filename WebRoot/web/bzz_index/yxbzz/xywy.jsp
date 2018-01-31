<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.dbpool"/>
<jsp:directive.page import="com.whaty.platform.database.oracle.MyResultSet"/>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
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
String path1 = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path1+"/";

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<base href="<%=basePath%>">
<title>中国证劵业协会培训平台</title>
<link href="/web/bzz_index/style/index.css" rel="stylesheet" type="text/css">
<link href="/web/bzz_index/style/msfc.css" rel="stylesheet" type="text/css">
<script language="javascript" src="/web/bzz_index/js/wsMenu.js"></script>
<script language="javascript">
function resize()
{
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
	document.getElementById("xytd").height=document.getElementById("xytd").contentWindow.document.body.scrollHeight;
}
</script>
</head>

<body>
<table width="1002" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
  <tr>
    <td height="209"><iframe id="top" name="top" frameborder="0" width="1002" height="209" scrolling="no" src="/web/bzz_index/top.jsp"></iframe></td>
  </tr>
  <tr>
    <td align="center">
		<table width="932" align="center" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="30" height="30"><img src="/web/bzz_index/yxbzz/images/msfc_11.jpg" border="0"></td>
			<td width="80" class="ms_title">学员文苑</td>
			<td background="/web/bzz_index/yxbzz/images/msfc_12.jpg">&nbsp;</td>
		  </tr>
		</table>
		<table width="932" align="center" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td height="5" background="/web/bzz_index/yxbzz/images/box_top.jpg"></td>
		  </tr>
		  <tr>
			<td background="/web/bzz_index/yxbzz/images/box_bg.jpg"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="240" valign="top"><br/>
                  <table width="184" border="0" align="center" cellpadding="0" cellspacing="0">
                  <tr>
                    <td align="center"><img src="/web/bzz_index/yxbzz/images/xywy.jpg" width="171" height="122" vspace="10"></td>
                  </tr>
                  <tr>
                    <td class="msfc_left_t"> 　　<b>征文通知：</b>征文采取择优推荐的方法。首先由各企业的管理员组织参训班组长投稿，题材不限，内容可以是班组建设、个人发展、岗位技能等经验分享，也可以是学习心得和感悟；然后由各企业管理员审核后择优推荐到清华大学。
<br/>征文联系人：孙老师
<br/>联系电话：010-62796133
<br/>所推荐作品需标明“作者姓名+学号+工作单位+管理员姓名+央企班组长”，另附上作者电子版生活照，以电子版形式发送至<br/>Email: bzzzg@mail.itsinghua.com ，邮件标题表明“稿件”二字，长期投稿有效。
<br/>欢迎各企业管理员积极组织学员投稿，学员踊跃投稿，录用文章将及时更新在“学员文苑”专栏，敬请关注。
</td>
                  </tr>
                </table></td>
                <td align="center" valign="top" style="padding-top:20px"><table width="650" border="0" align="center" cellpadding="0" cellspacing="0" class="msfc_title">
                  <tr>
                    <td height="19"><table width="113" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
                      <tr>
                        <td align="center" class="ms_title_t"> 学员文苑 </td>
                      </tr>
                    </table></td>
                  </tr>
                </table>
                  <br/>
                  <%
                  
					dbpool db = new dbpool();
					MyResultSet rs = null;
					String sql="select title,id,to_char(report_date,'yyyy-mm-dd') as report_date,summary from pe_info_news a where a.fk_news_type_id='_xywy' and a.flag_news_status='402880a91e4f1248011e4f1c0ab40004' and a.flag_isactive='402880a91e4f1248011e4f1a2b360002'  order by a.report_date desc,a.id";
					int totalItems = 0;
					totalItems = db.countselect(sql);
					//----------分页开始---------------
						String spagesize = (String) session.getValue("pagesize");
						String spageInt = request.getParameter("pageInt");
						Page pageover = new Page();
						pageover.setTotalnum(totalItems);
						pageover.setPageSize(spagesize);
						pageover.setPageInt(spageInt);
						int pageNext = pageover.getPageNext();
						int pageLast = pageover.getPagePre();
						int maxPage = pageover.getMaxPage();
						int pageInt = pageover.getPageInt();
						int pagesize = pageover.getPageSize();
						String link = "";
					rs = db.execute_oracle_page(sql,pageInt,pagesize);
					while(rs!=null&&rs.next())
					{
						String title=fixnull(rs.getString("title"));
						String id=fixnull(rs.getString("id"));
						String report_date=fixnull(rs.getString("report_date"));
						String summary=fixnull(rs.getString("summary"));
						if(title.length()>15)
						{
							title=title.substring(0,15)+"...";
						}
						if(summary.length()>20)
						{
							summary=summary.substring(0,20)+"...";
						}
                   	%>
                  <table width="620" border="0" align="center" cellpadding="0" cellspacing="0" class="list_box">
					 <tr>
                    <td width="250" align="left"><span class="msfc_name2"><a href="/entity/first/firstInfoNews_toXywyDetail.action?bean.id=<%=id%>" target="_blank">&nbsp;&nbsp;<%=title %></a></span></td>  
                      <td colspan=1 width="278"> <span class="msfc_name1"><%=summary %></span></td>
                    </tr>
                    </tr>
                    <tr>
                      <td colspan="3" align="center" height="5px"></td>
                    </tr>
                  </table>
                   <%
	                 }
	                 db.close(rs);
	                  %>
                  <table width="90%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td width="90%" valign="middle" class="page"><%@ include file="/web/bzz_index/pub/dividepage.jsp" %>
                      </td>
                    </tr>
                  </table></td>
              </tr>
            </table>
			  </td>
		  </tr>
		  <tr>
			<td height="20" background="/web/bzz_index/yxbzz/images/msfc_18.jpg"></td>
		  </tr>
		</table>
	</td>
  </tr>
  <tr>
    <td><iframe id="foot" name="foot" frameborder="0" width="1002" height="147" scrolling="no" src="/web/bzz_index/bottom1.jsp"></iframe></td>
  </tr>
</table>


</body>
</html>
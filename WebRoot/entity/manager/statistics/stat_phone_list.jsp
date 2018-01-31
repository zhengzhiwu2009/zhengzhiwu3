<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@ include file="./pub/priv.jsp"%>
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
	String sql = "";
	MyResultSet rs = null;
	dbpool db = new dbpool();
	sql = "select a.id,a.code,a.name,b.num09,c.num10,d.signnum,e.paynum,f.m_name,f.phone,f.mobile_phone,f.login_num from\n" +
		"(select e.id,e.code,e.name from pe_enterprise e where e.fk_parent_id is null order by e.code) a,\n" + 
		"(select e1.id,count(s.id) as num09 from pe_bzz_student s,pe_enterprise e,pe_enterprise e1,sso_user u\n" + 
		"where s.fk_enterprise_id=e.id and e.fk_parent_id=e1.id and u.id=s.fk_sso_user_id and u.flag_isvalid='2'\n" + 
		"and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_batch_id='ff8080812253f04f0122540a58000004' group by e1.id) b,\n" + 
		"(select e1.id,count(s.id) as num10 from pe_bzz_student s,pe_enterprise e,pe_enterprise e1,sso_user u\n" + 
		"where s.fk_enterprise_id=e.id and e.fk_parent_id=e1.id and u.id=s.fk_sso_user_id and u.flag_isvalid='2'\n" + 
		"and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and s.fk_batch_id='ff8080812824ae6f012824b0a89e0008' group by e1.id) c,\n" + 
		"(select e1.id,count(s.id) as signnum from pe_bzz_recruit s,pe_enterprise e,pe_enterprise e1\n" + 
		"where s.fk_enterprise_id=e.id and e.fk_parent_id=e1.id  and s.fk_batch_id='ff8080812fce7858012fd2d27c1d1527' group by e1.id) d,\n" + 
		"(select e1.id,sum(s.num) as paynum from pe_bzz_fax_info s,pe_enterprise e,pe_enterprise e1\n" + 
		"where s.fk_enterprise_id=e.id and e.fk_parent_id=e1.id  and s.fk_batch_id='ff8080812fce7858012fd2d27c1d1527' group by e1.id) e,\n" + 
		"(select a.id,a.code,a.name,a.m_name,a.phone,a.mobile_phone,a.login_num from\n" + 
		"(select e.id,e.code,e.name,m.name as m_name,m.phone,m.mobile_phone,u.login_num\n" + 
		"from pe_enterprise_manager m,sso_user u,pe_enterprise e\n" + 
		"where m.fk_enterprise_id=e.id and m.fk_sso_user_id=u.id and u.fk_role_id in('402880a92137be1c012137db62100006','2') and e.fk_parent_id is null) a,\n" + 
		"(select e.id,e.code,e.name,max(u.login_num) as login_num\n" + 
		"from pe_enterprise_manager m,sso_user u,pe_enterprise e\n" + 
		"where m.fk_enterprise_id=e.id and m.fk_sso_user_id=u.id and u.fk_role_id in('402880a92137be1c012137db62100006','2') and e.fk_parent_id is null\n" + 
		"group by e.id,e.code,e.name) b\n" + 
		"where a.id=b.id and a.code=b.code and a.name=b.name and a.login_num=b.login_num) f\n" + 
		"where a.id=b.id(+) and a.id=c.id(+) and a.id=d.id(+) and a.id=e.id(+) and a.id=f.id(+)\n" + 
		"and (num09 is not null or num10 is not null or signnum is not null)";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tableType_1</title>
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0" class="scllbar">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
 <tr> 
    <td height="28"><table width="100%" height="28" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="12"><img src="/entity/manager/statistics/images/page_titleLeft.gif" width="12" height="28"></td>
          <td align="right" background="/entity/manager/statistics/images/page_titleM.gif"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="112"><img src="/entity/manager/statistics/images/page_titleMidle.gif" width="112" height="28"></td>
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看学员统计信息</td>
              </tr>
            </table></td>
          <td width="8"><img src="/entity/manager/statistics/images/page_titleRight.gif" width="8" height="28"></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder">
<!-- start:内容区域 -->
<table width="4470" align="center" cellpadding='1' cellspacing='1' class='list'>
            <tr> 
              <th style='white-space: nowrap;' width="34"> <span class="link">序号</span></th>
              <th style='white-space: nowrap;' width="59"> <span class="link">企业编号</span></th>
              <th style='white-space: nowrap;' width="262"> <span class="link">一级企业名称</span> </th>
              <th style='white-space: nowrap;' width="85"><span class="link">09学习总人数</span> </th>
              <th style='white-space: nowrap;' width="85"><span class="link">10学习总人数</span> </th>
              <th style='white-space: nowrap;' width="124"> <span class="link">11年动态报名总人数</span></th>
              <th style='white-space: nowrap;' width="124"> <span class="link">11年动态交费总人数</span></th>
              <th style='white-space: nowrap;' width="199"> <span class="link">一级管理员</span></th>
              <th style='white-space: nowrap;' width="125"> <span class="link">一级管理员固定电话</span> </th>
              <th style='white-space: nowrap;' width="99"> <span class="link">一级管理员手机</span></th>
              <th style='white-space: nowrap;' width="33"> <span class="link">序号</span></th>
              <th style='white-space: nowrap;' width="417"> <span class="link">二级企业名称</span></th>
              <th style='white-space: nowrap;' width="72"> <span class="link">09学习人数</span></th>
              <th style='white-space: nowrap;' width="85"> <span class="link">09年考试人数</span></th>
              <th style='white-space: nowrap;' width="85"> <span class="link">09年缓考人员</span></th>
              <th style='white-space: nowrap;' width="89"> <span class="link">通过率/优秀率</span></th>
              <th style='white-space: nowrap;' width="85"> <span class="link">10年学习人数</span></th>
              <th style='white-space: nowrap;' width="125"> <span class="link">已开学期学习总人数</span></th>
              <th style='white-space: nowrap;' width="111"> <span class="link">11年动态报名人数</span></th>
              <th style='white-space: nowrap;' width="141"> <span class="link">11年动态交费人数动态</span></th>
              <th style='white-space: nowrap;' width="163"> <span class="link">本级管理员姓名</span></th>
              <th style='white-space: nowrap;' width="133"> <span class="link">本级管理员固定电话</span></th>
              <th style='white-space: nowrap;' width="99"> <span class="link">本级管理员手机</span></th>
              <th style='white-space: nowrap;' width="99"> <span class="link">本级负责人姓名</span></th>
              <th style='white-space: nowrap;' width="150"> <span class="link">本级负责人固定电话</span></th>
              <th style='white-space: nowrap;' width="142"> <span class="link">本级负责人手机</span></th>
              <th style='white-space: nowrap;' width="103"> <span class="link">本级联系人姓名</span></th>
              <th style='white-space: nowrap;' width="150"> <span class="link">本级联系人固定电话</span></th>
              <th style='white-space: nowrap;' width="151"> <span class="link">本级联系人手机</span></th>
              <th style='white-space: nowrap;' width="115"> <span class="link">上级负责人姓名</span></th>
              <th style='white-space: nowrap;' width="125"> <span class="link">上级负责人固定电话</span></th>
              <th style='white-space: nowrap;' width="99"> <span class="link">上级负责人手机</span></th>
              <th style='white-space: nowrap;' width="154"> <span class="link">09证书邮寄快递单号</span></th>
              <th style='white-space: nowrap;' width="85"> <span class="link">10年学习详情</span></th>
              <th style='white-space: nowrap;' width="85"> <span class="link">11年预报人数</span></th>
              <th style='white-space: nowrap;' width="59"> <span class="link">沟通记录</span></th>
              <th style='white-space: nowrap;' width="34"> <span class="link">备注</span></th>
              <th style='white-space: nowrap;' width="70"> <span class="link">&nbsp;</span></th>
            </tr>
            </table>
</td></tr>
  <tr> 
    <td valign="top" class="pageBodyBorder">
<!-- start:内容区域 -->
<iframe id="footer" name="footer" frameborder="0" scrolling="auto" src="/entity/manager/statistics/stat_phone_list1.jsp" width="4420" height="600"></iframe>
</td></tr>
  
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"> 
      <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="4"><img src="../images/page_bottomSlip.gif" width="100%" height="2"></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
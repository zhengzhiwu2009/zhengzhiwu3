package com.whaty.encrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.whaty.util.StandardStatus;
/*******************************************************************************
 * jsdx=656101 fsdx=656102 qfdx=656103 pbdx=656104 xtgly=656201 bmgly=656202
 * ejgly=656203 bzr=656204 rkjs=656205 xy=656206
 ******************************************************************************/
public class ReceiveAndSend {
//	public String getSend(HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		//SsoUser userInfo = (com.whaty.dls.UserInfo) session.getAttribute("managerInfo");
//		String[] str = new String[] { "0", "0", "0", "0", "0", "0", "0", "0",
//				"0", "0" };
//		String[] pra = new String[] { "jsdx", "fsdx", "qfdx", "pbdx", "xtgly",
//				"bmgly", "ejgly", "bzr", "rkjs", "xy" };
//		String strId = "";
//		String mySQL = "select right_id from mng_group_right g, lrn_manager_info r "
//				+ "where g.group_id = r.group_id and r.id='"
//				+ userInfo.getId()
//				+ "'";
//		dbpool db = new dbpool();
//		MyResultSet rs = db.executeQuery(mySQL);
//		try {
//			while (rs != null && rs.next()) {
//				String id = rs.getString("right_id");
//				for (int i = 0; i < pra.length; i++) {
//					if (str[i].equals("1"))
//						continue;
//					str[i] = this.replice(pra[i], id);
//				}
//
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			db.close(rs);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (int i = 0; i < str.length; i++) {
//			strId = strId + str[i];
//		}
//		return strId;
//	}
//
//	public String replice(String pra, String id) {
//		String qxid = parameter.getsetup(pra);
//		String[] ids = null;
//		if (qxid != null && qxid.length() > 0) {
//			ids = qxid.split(",");
//			for (int i = 0; i < ids.length; i++) {
//				if (ids[i].equals(id)) {
//					return "1";
//				}
//			}
//		}
//		return "0";
//	}
//
//	public void printSelect(HttpServletRequest request,
//			HttpServletResponse response) {
//		PrintWriter out = null;
//		try {
//			out = response.getWriter();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		String type = "";
//		type = (String) request.getSession().getAttribute(
//				StandardStatus.USER_LOGIN_TYPE);
//
//		out.print(this.add(type).toString());// ��ӡ��Ϣ���ͷ�Χ�Ĳ˵�
//	}
//
//	public StringBuffer add(String type) {// �ж���ݣ������Ӧ�ķ�����Ϣ�ķ�Χ
//		StringBuffer sb = new StringBuffer("");
//		if (type.equals("admin")) {
//			//sb.append("<option   value=''>��ѡ��</option>");
//			sb.append("<option   value='111111'>ȫ��Ա��</option>");
//			sb.append("<option value='000001'>ѧ��</option>");
//			sb.append("<option value='000010'>��ʦ</option>");
//			sb.append("<option value='000100'>������</option>");
//			sb.append("<option value='001000'>��Դ����Ա</option>");
//			sb.append("<option value='010000'>��������Ա</option>");
//			sb.append("<option value='100000'>һ������Ա</option>");
//			return sb.append("</select>");
//		}
//		if (type != null && type.length() > 0
//				&& String.valueOf(type.charAt(0)).endsWith("1")) {
//			sb.append("<option value='000001'>ѧ��</option>");
//			sb.append("<option value='000010'>��ʦ</option>");
//			sb.append("<option value='000100'>������</option>");
//			sb.append("<option value='001000'>��Դ����Ա</option>");
//			sb.append("<option value='010000'>��������Ա</option>");
//			return sb.append("</select>");
//		}
//		if (type != null && type.length() > 0
//				&& String.valueOf(type.charAt(1)).endsWith("1")) {
//			sb.append("<option value='000001'>ѧ��</option>");
//			sb.append("<option value='000010'>��ʦ</option>");
//			sb.append("<option value='000100'>������</option>");
//			sb.append("<option value='001000'>��Դ����Ա</option>");
//			return sb.append("</select>");
//		}
//		if (type != null && type.length() > 0
//				&& String.valueOf(type.charAt(2)).endsWith("1")) {
//			sb.append("<option value='000001'>ѧ��</option>");
//			sb.append("<option value='000010'>��ʦ</option>");
//			sb.append("<option value='000100'>������</option>");
//			return sb.append("</select>");
//		}
//		if (type != null && type.length() > 0
//				&& String.valueOf(type.charAt(3)).endsWith("1")) {
//			sb.append("<option value='000001'>ѧ��</option>");
//			sb.append("<option value='000010'>��ʦ</option>");
//			return sb.append("</select>");
//		}
//		if (type != null && type.length() > 0
//				&& String.valueOf(type.charAt(4)).endsWith("1")) {
//			sb.append("<option value='000001'>ѧ��</option>");
//			return sb.append("</select>");
//		}
//		return sb.append("</select>");
//	}
///**
// * @author zlw
// * ����������4��ʾ������Ϣ�ľ�����Ա��ͬ����ı���NAME����Ҳ��һ��������
// * **/
//	public List getUsrList(String type,HttpServletRequest request) {//�õ�ѡ��ķ�����Ϣ��Χ
//		String sql = "";
//		String divSql="";
//		String id_card = request.getParameter("id_card");//���֤��name=id_card
//		String name =  request.getParameter("name");//���� name = name 
//		String ygdw =  request.getParameter("ygdw");//���� name = name 
//		String fg =  request.getParameter("flag");//1 ��λ��0 ���� 
//		String syglx= request.getParameter("syglx");
//		String ygbh=  request.getParameter("ygbh");
//		
//		com.whaty.dls.UserInfo teacher = (com.whaty.dls.UserInfo) request.getSession().getAttribute(StandardStatus.SSO_USER);
//		String site_idd2  = "";
//		if(teacher!=null){
//			site_idd2 = teacher.getSite_id();
//		}
//
//		if(id_card!=null&&!"".equals(id_card)&&!"null".equals(id_card)){
//			divSql=divSql+" and a.card_no like '%"+id_card+"%'";
//		   }
//		if(name!=null&&!"".equals(name)&&!"null".equals(name)){
//			divSql=divSql+" and a.name like '%"+name+"%'";
//		   }
//		if(ygdw!=null&&!"".equals(ygdw)&&!"null".equals(ygdw)){
//			if("1".equals(fg))
//				divSql=divSql+" and a.senworkplaceid= '"+ygdw+"'";
//			else if("0".equals(fg)){
//				divSql=divSql+" and a.thirworkplaceid= '"+ygdw+"'";
//			}
//		}
//		if("0".equals(fg)){
//			divSql=divSql+" and a.senworkplaceid= '"+site_idd2+"'";
//		}
//		
//		if(syglx!=null&&!"".equals(syglx)&&!"null".equals(syglx)){
//			divSql=divSql+" and a.gwlx= '"+syglx+"'";
//		   }
//		if(ygbh!=null&&!"".equals(ygbh)&&!"null".equals(ygbh)){
//			divSql=divSql+" and a.id like'%"+ygbh+"%'";
//		   }
//		dbpool db = new dbpool();
//		int totalItems=0;//��ݲ�ͬ����ݲ��ҳ�ͬ������
//		//���Ŀǰ��½�˵���ݲ�����Ӧ��Χ����Ϣ������
//		String loginType = "";
//		loginType = (String) request.getSession().getAttribute(
//				StandardStatus.USER_LOGIN_TYPE);
//		
//		String user_id ="";
//		user_id = ((com.whaty.dls.UserInfo) request.getSession().getAttribute(StandardStatus.SSO_USER)).getId();
//		
//		if(loginType.equals("admin")){
//			if ("111111".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,a.thirworkplacename as thirworkplacename from  lrn_sso_user a where 1=1 "+divSql+") order by senworkplacename ";
//			}
//			if ("000001".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,a.thirworkplacename as thirworkplacename from  lrn_sso_user a where substr( a.login_type,6,1) ='1'"+divSql+") order by senworkplacename";
//			}
//			if ("000010".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,a.thirworkplacename as thirworkplacename from  lrn_sso_user a where substr(a.login_type,5,1) ='1' "+divSql+") order by senworkplacename";
//			}
//			if ("000100".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,a.thirworkplacename as thirworkplacename from  lrn_sso_user a where substr(a.login_type,4,1) ='1' "+divSql+") order by senworkplacename";
//			}
//			if ("001000".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,a.thirworkplacename as thirworkplacename from  lrn_sso_user a where substr(a.login_type,3,1) ='1' "+divSql+") order by senworkplacename";
//			}
//			if ("010000".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,a.thirworkplacename as thirworkplacename from  lrn_sso_user a where substr(a.login_type,2,1) ='1' "+divSql+") order by senworkplacename";
//			}
//			if ("100000".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,a.thirworkplacename as thirworkplacename from  lrn_sso_user a where substr(a.login_type,1,1) ='1' "+divSql+") order by senworkplacename";
//			}
//		}else if(loginType != null && loginType.length() > 0
//				&& String.valueOf(loginType.charAt(3)).endsWith("1")){
//			if ("000001".equals(type)) {
//				sql = "select id, name, gender,senworkplacename,thirworkplacename from (select distinct a.id, a.name, a.gender,senworkplacename,a.thirworkplacename as thirworkplacename from lrn_sso_user a,lrn_register_info b,lrn_classmanager_relation c where a.id=b.user_id and b.semester_id=c.semester_id and c.classmanager_id='"+user_id+"' and substr(a.login_type, 6, 1) = '1'"+divSql+") order by senworkplacename";
//			}
//			if ("000010".equals(type)) {
//				sql = "select id, name, gender,senworkplacename,thirworkplacename  from (select distinct a.id, a.name, a.gender,senworkplacename,a.thirworkplacename as thirworkplacename  from lrn_sso_user a, lrn_course_active b,lrn_classmanager_relation c,lrn_teach_course d where a.id=d.teacher_id and b.id=d.open_course_id and b.semester_id=c.semester_id and c.classmanager_id='"+user_id+"' and substr(a.login_type, 6, 1) = '1'"+divSql+") order by senworkplacename";
//			}
//			if ("111111".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select distinct a.senworkplacename,a.id, a.name, a.gender,a.thirworkplacename as thirworkplacename  from lrn_sso_user a, lrn_course_active b,lrn_classmanager_relation c,lrn_teach_course d where a.id=d.teacher_id and b.id=d.open_course_id and b.semester_id=c.semester_id and c.classmanager_id='"+user_id+"' and substr(a.login_type, 6, 1) = '1'"+divSql+")"
//					+ " union select id,name,gender from (select distinct a.senworkplacename,a.id, a.name, a.gender,a.thirworkplacename as thirworkplacename from lrn_sso_user a,lrn_register_info b,lrn_classmanager_relation c where a.id=b.user_id and b.semester_id=c.semester_id and c.classmanager_id='"+user_id+"' and substr(a.login_type, 6, 1) = '1'"+divSql+") order by senworkplacename";
//			}
//		}else if(loginType != null && loginType.length() > 0
//				&& String.valueOf(loginType.charAt(4)).endsWith("1")){//��ʦ������
//			String site_id = request.getParameter("site_id");
//			String whereSite = "";
//			if(!"0000".equals(site_id)){
//				whereSite = " and senworkplaceid = " +site_id ;
//			}
//			
//			divSql = divSql + "  ";
//			if ("000001".equals(type)) {
//				sql = "select distinct id ,name ,gender,senworkplacename,thirworkplacename from (" 
//						+" select a.id as id,a.name as name,a.gender as gender,a.senworkplacename as senworkplacename,a.thirworkplacename as thirworkplacename "
//						+" from lrn_course_active ca,lrn_course_info c,lrn_elective e,lrn_teach_course t,lrn_semester_info s,lrn_sso_user a " 
//						+" where t.teacher_id='"+ user_id+"' and ca.id=t.open_course_id and ca.course_id=c.id and e.open_course_id=ca.id "
//						+" and s.id=ca.semester_id and a.id=e.student_id and t.teacher_id='"+ user_id+"' and substr(a.login_type,6,1) ='1' "+divSql+") order by senworkplacename";
//
//			}
//			if ("111111".equals(type)) {
//				sql = "select distinct id,name,gender,senworkplacename,thirworkplacename from (" 
//					+" select a.id as id,a.name as name,a.gender as gender,a.senworkplacename as senworkplacename,a.thirworkplacename as thirworkplacename "
//					+" from lrn_course_active ca,lrn_course_info c,lrn_elective e,lrn_teach_course t,lrn_semester_info s,lrn_sso_user a " 
//					+" where t.teacher_id='"+ user_id+"' and ca.id=t.open_course_id and ca.course_id=c.id and e.open_course_id=ca.id "
//					+" and s.id=ca.semester_id and a.id=e.student_id and t.teacher_id='"+ user_id+"' and substr(a.login_type,6,1) ='1' "+divSql+") order by senworkplacename";
//
//			}
//		}else if(loginType != null && loginType.length() > 0
//				&& String.valueOf(loginType.charAt(2)).endsWith("1")){
//			if ("000001".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,thirworkplacename from  lrn_sso_user a where substr( a.login_type,6,1) ='1'"+divSql+") order by senworkplacename";
//			}
//			if ("000010".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,thirworkplacename from  lrn_sso_user a where substr(a.login_type,5,1) ='1' "+divSql+") order by senworkplacename";
//			}
//			if ("000100".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,thirworkplacename from  lrn_sso_user a where substr(a.login_type,4,1) ='1' "+divSql+") order by senworkplacename";
//			}
//			if ("111111".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,thirworkplacename from  lrn_sso_user a where 1 =1 "+divSql+") order by senworkplacename";
//			}	
//		}else if(loginType != null && loginType.length() > 0
//				&& (String.valueOf(loginType.charAt(0)).endsWith("1")||String.valueOf(loginType.charAt(1)).endsWith("1") )){//��վ����Ա
//			/*
//			 *  loginType->100011
//				sql11-->0->1
//				sql11-->1->0
//				sql11-->2->0
//				sql11-->3->0
//				sql11-->4->1
//				sql11-->5->1
//			 */		
//			
//			//String site_id = request.getParameter("site_id");
//			String site_id = "";
//			 HttpSession session = request.getSession();
//			 UserInfo UserInfo_temp = (com.whaty.dls.UserInfo)session.getAttribute(StandardStatus.SSO_USER);
//			 if(UserInfo_temp!=null){
//				 site_id = UserInfo_temp.getSite_id();
//			 }
//			if("null".equals(site_id)||site_id==null){
//				site_id = "";
//			}
//			
//			String whereSite = "";
//			divSql = divSql + "  "; 
//			if(!StandardStatus.SITE_ID_CHIEF.equals(site_id)){
//				//whereSite = " and senworkplaceid = " +site_id ;
//				divSql = divSql +" and senworkplaceid= '" + site_id +"' ";
//			}
//		
//			//sql = "select  id,name,gender from (select id,name,gender from  lrn_sso_user a where 1 =1 "+divSql+"  " +whereSite +")";
//			
//			if ("000001".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,thirworkplacename from  lrn_sso_user a where substr( a.login_type,6,1) ='1'"+divSql+") order by senworkplacename";
//			}
//			if ("000010".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,thirworkplacename from  lrn_sso_user a where substr(a.login_type,5,1) ='1' "+divSql+") order by senworkplacename";
//			}
//			if ("000100".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,thirworkplacename from  lrn_sso_user a where substr(a.login_type,4,1) ='1' "+divSql+") order by senworkplacename";
//			}
//			if ("001000".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,thirworkplacename from  lrn_sso_user a where substr(a.login_type,3,1) ='1' "+divSql+") order by senworkplacename";
//			}
//			if ("010000".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,thirworkplacename from  lrn_sso_user a where substr(a.login_type,2,1) ='1' "+divSql+") order by senworkplacename";
//			}
//			if ("100000".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,thirworkplacename from  lrn_sso_user a where substr(a.login_type,1,1) ='1' "+divSql+") order by senworkplacename";
//			}
//			if ("111111".equals(type)) {
//				sql = "select  id,name,gender,senworkplacename,thirworkplacename from (select id,name,gender,senworkplacename,thirworkplacename from  lrn_sso_user a where 1 =1 "+divSql+") order by senworkplacename";
//			}
//			
//		}
//		 
//		totalItems=db.countselect(sql);
//      String spageInt = request.getParameter("pageInt");
//		if(null == spageInt || spageInt.equals(""))
//			spageInt = "1";
//		int pageInt = Integer.parseInt(spageInt);
//		int pageSize=10;
//		HttpSession session = request.getSession();
//		String temp_pagesize = (String)session.getAttribute("pagesize");
//		if (temp_pagesize == null || temp_pagesize.length() == 0 || temp_pagesize.equals("null"))
//		{
//			pageSize = 10;
//		}
//		else
//		{
//			pageSize = Integer.parseInt(temp_pagesize);
//		}
//		
//		if (totalItems <= (pageInt - 1) * pageSize)
//		{
//			pageInt = pageInt - 1;
//			if(pageInt < 1)
//			{
//				pageInt = 1;
//			}
//		}
//		int maxPage = (totalItems + pageSize - 1) / pageSize;
//		if(pageInt > maxPage)
//			pageInt = maxPage;
//		int pageNext = pageInt + 1;
//		int pageLast = pageInt - 1;
//		request.setAttribute("maxPage", maxPage);
//		request.setAttribute("pageNext", pageNext);
//		request.setAttribute("pageLast", pageLast);
//		request.setAttribute("pageInt", pageInt);
//		request.setAttribute("totalItems", totalItems);
//		request.setAttribute("pageSize", pageSize);
//		List userList = new ArrayList();
//		MyResultSet rs = db.execute_oracle_page(sql, pageInt, pageSize);
//		try {
//			while(rs!=null&&rs.next()){
//				List list = new ArrayList();
//				list.add(rs.getString("id"));
//				list.add(rs.getString("name"));
//				list.add(rs.getString("gender"));
//				list.add(rs.getString("senworkplacename"));
//				list.add(rs.getString("thirworkplacename"));
//				userList.add(list);
//			}
//			db.close(rs);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return userList;
//	}
// /***
//  * @author zlw
//  * ʹ�ø÷�����ʱ��Ҫע�⣬�ϸ�ҳ���е��ı������NAME������һ�������ơ�
//  * 
//  * ***/
//	public int insertMessage(HttpServletRequest request){
//		dbpool db = new dbpool();
//		 List sqlList = new ArrayList();
//		 HttpSession session = request.getSession();
//		 String[] ids = request.getParameterValues("sid"); //��ѡ��name��Ϊsid valueΪstudentId
//		 String title=request.getParameter("title");//�����ı���name ����Ϊtitle
//		 String content=request.getParameter("body");//����nameΪbody
//		 String type=request.getParameter("type");//type ѡ���͵���ݷ�Χ
//		 String ygdw=request.getParameter("ygdw");
//		 String name=request.getParameter("name");
//		 String id_card=request.getParameter("id_card");
//		 String ygbh = request.getParameter("ygbh");
//		 String syglx = request.getParameter("syglx");
//		 String fg = request.getParameter("flag");//0 Ϊ��������Ա��ֻ�ܸ��ŷ� 1Ϊһ������Ա 
//		 UserInfo userInfo=(UserInfo)session.getAttribute(StandardStatus.SSO_USER);
//		 String site_idd2 = userInfo.getSite_id();
//		 String user_id="";//�û� ��ID
//		 String loginType = "";
//			loginType = (String) request.getSession().getAttribute(StandardStatus.USER_LOGIN_TYPE);
//		 if(userInfo!=null){
//		 user_id= userInfo.getId();
//		 }
//		 String sql="";
//		 String sql1="";
//		 
//		 String infoIdSql = "select max(to_number(id)) as info from lrn_message_info  ";
//		 String infoid = "";
//	
//		 
//		 sql = "insert into lrn_message_info a (id,title,content,type,user_id,SENDDATE) values(to_char(lrn_message_info_id.nextval),'"+title+"',?,'"+type+"','"+user_id+"',sysdate)";
// 		
//		 if( db.executeUpdate(sql, content)>0){
//			 MyResultSet rs1 = null;
//    		 rs1 = db.executeQuery(infoIdSql);
//    		 try {
//    			while(rs1!=null&&rs1.next()){
//    				infoid = rs1.getString("info") ;
//    			 }
//    		} catch (SQLException e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    		}  try {
//				db.close(rs1);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//                 if(ids==null){
//                	
//                	     sql1="insert into lrn_message_user (message_id,user_id,send_user,status)select "+infoid+",id,'"+userInfo.getId()+"','0' from lrn_sso_user a  where 1=1";
//                	   if("111111".equals(type)){
//                        }
//                        if("000001".equals(type)){
//                        	sql1=sql1+"and substr( a.login_type,6,1) ='1'";
//                        }
//                        if("000010".equals(type)){
//                        	sql1=sql1+" and substr( a.login_type,5,1) ='1'" ;
//                        }
//                        if("000100".equals(type)){
//                        	sql1=sql1+" and substr( a.login_type,4,1) ='1'";
//                        }
//                        if("001000".equals(type)){
//                        	sql1=sql1+" and substr( a.login_type,3,1) ='1'";
//                        }
//                        if("010000".equals(type)){
//                        	sql1=sql1+" and substr( a.login_type,2,1) ='1'";
//                        } 
//                        if("100000".equals(type)){
//                        	sql1=sql1+" and substr( a.login_type,1,1) ='1'";
//                        }
//                        
//                        if(!"".equals(ygbh)&&!"null".equals(ygbh)&&ygbh!=null){
//                        	sql1=sql1+"and a.id like '%"+ygbh+"%' ";
//                        }
//                        if(!"".equals(syglx)&&!"null".equals(syglx)&&syglx!=null){
//                        	sql1=sql1+"and a.gwlx = '"+syglx+"' ";
//                        }
//                        
//                		if(ygdw!=null&&!"".equals(ygdw)&&!"null".equals(ygdw)){
//                			if("1".equals(fg))
//                				sql1=sql1+" and a.senworkplaceid= '"+ygdw+"'";
//                			else if("0".equals(fg)){
//                				sql1=sql1+" and a.thirworkplaceid= '"+ygdw+"'";
//                			}
//                		}
//                		if("0".equals(fg)){
//                			sql1=sql1+" and a.senworkplaceid= '"+site_idd2+"'";
//                		}
//
//                        if(!"".equals(name)&&!"null".equals(name)&&name!=null){
//                        	sql1=sql1+"and a.name like'%"+name+"%'";
//                        }
//                        if(!"".equals(id_card)&&!"null".equals(id_card)&&id_card!=null){
//                        	sql1=sql1+"and a.card_no like'%"+id_card+"%'";
//                        }
//                        if(loginType != null && loginType.length() > 0
//                				&& String.valueOf(loginType.charAt(4)).endsWith("1")){//��ʦ������
//                			String site_id = request.getParameter("site_id");
//                			String whereSite = "";
//                			if(!"0000".equals(site_id)){
//                				whereSite = " and senworkplaceid = " +site_id ;
//                			}
//                			
//                			if ("000001".equals(type)) {
//                				sql1 = sql1+ " and a.id in(select distinct id  from (" 
//                						+" select a.id as id,a.name as name,a.gender as gender,a.senworkplacename as senworkplacename "
//                						+" from lrn_course_active ca,lrn_course_info c,lrn_elective e,lrn_teach_course t,lrn_semester_info s,lrn_sso_user a " 
//                						+" where t.teacher_id='"+ user_id+"' and ca.id=t.open_course_id and ca.course_id=c.id and e.open_course_id=ca.id "
//                						+" and s.id=ca.semester_id and a.id=e.student_id and t.teacher_id='"+ user_id+"' and substr(a.login_type,6,1) ='1' "+"))";
//
//                			}
//                			if ("111111".equals(type)) {
//                				sql1 =sql1+ "and a.id in(select distinct id from (" 
//                					+" select a.id as id,a.name as name,a.gender as gender,a.senworkplacename as senworkplacename "
//                					+" from lrn_course_active ca,lrn_course_info c,lrn_elective e,lrn_teach_course t,lrn_semester_info s,lrn_sso_user a " 
//                					+" where t.teacher_id='"+ user_id+"' and ca.id=t.open_course_id and ca.course_id=c.id and e.open_course_id=ca.id "
//                					+" and s.id=ca.semester_id and a.id=e.student_id and t.teacher_id='"+ user_id+"' and substr(a.login_type,6,1) ='1' "+"))";
//
//                			}
//                		}
//                        if(loginType != null && loginType.length() > 0
//                				&& String.valueOf(loginType.charAt(3)).endsWith("1")){
//                			if ("000001".equals(type)) {
//                				sql1 =sql1+ "and a.id in(select id from (select distinct a.id, a.name, a.gender,senworkplacename from lrn_sso_user a,lrn_register_info b,lrn_classmanager_relation c where a.id=b.user_id and b.semester_id=c.semester_id and c.classmanager_id='"+user_id+"' and substr(a.login_type, 6, 1) = '1'"+"))";
//                			}
//                			if ("000010".equals(type)) {
//                				sql1 =sql1+ "and a.id in(select id  from (select distinct a.id, a.name, a.gender,senworkplacename  from lrn_sso_user a, lrn_course_active b,lrn_classmanager_relation c,lrn_teach_course d where a.id=d.teacher_id and b.id=d.open_course_id and b.semester_id=c.semester_id and c.classmanager_id='"+user_id+"' and substr(a.login_type, 6, 1) = '1'"+"))";
//                			}
//                			if ("111111".equals(type)) {
//                				sql1 =sql1+ "and a.id in(select  id from (select distinct a.senworkplacenamea.id, a.name, a.gender  from lrn_sso_user a, lrn_course_active b,lrn_classmanager_relation c,lrn_teach_course d where a.id=d.teacher_id and b.id=d.open_course_id and b.semester_id=c.semester_id and c.classmanager_id='"+user_id+"' and substr(a.login_type, 6, 1) = '1'"+"))"
//                					+ " union select id,name,gender from (select distinct a.senworkplacename,a.id, a.name, a.gender from lrn_sso_user a,lrn_register_info b,lrn_classmanager_relation c where a.id=b.user_id and b.semester_id=c.semester_id and c.classmanager_id='"+user_id+"' and substr(a.login_type, 6, 1) = '1'"+"))";
//                			}
//                		}
//                        sqlList.add(sql1);
//                         
//                        
//                 }else{
//		 for(int i=0;i<ids.length;i++){
//			   sql1="insert into lrn_message_user (id,message_id,user_id,status,send_user) values(lrn_message_user_id.nextval,to_char("+infoid+"),'"+ids[i]+"','0','"+userInfo.getId()+"')";
// 		   
//				  sqlList.add(sql1);
//		  }
//		 }
//		 }
//		 int i=db.executeUpdateBatch(sqlList);
//		 return i;
//	}
//
//	//ǩ����Ϣ
//	public int checkMessage(HttpServletRequest request){
//		String message_id = request.getParameter("message_id");
//		UserInfo userInfo=(UserInfo)request.getSession().getAttribute(StandardStatus.SSO_USER);
//		String user_id="";
//		if(userInfo!=null){
//			user_id= userInfo.getId();
//		}
//		String sql = "update lrn_message_user set status='1' where user_id='"+user_id+"' and message_id='"+message_id+"'";
//		dbpool db = new dbpool();
//		return db.executeUpdate(sql);
//	}
//	public String getImg(String type){
//		if("��ʦ".equals(type)){
//			return "../images/14041416.png";
//		}
//		if("������".equals(type)){
//			return "../images/14041421.png";
//		}
//		if("��Դ����Ա".equals(type)){
//			return "../images/14041424.png";
//		}
//		if("��������Ա".equals(type)){
//			return "../images/14041414.png";
//		}
//		if("һ������Ա".equals(type)){
//			return "../images/14041413.png";
//		}
//		return "../images/6.bmp";
//	}
//	public List getUserName(String id){
//		String sql = "select a.id ,a.name from lrn_sso_user a where a.login_id='"+id+"'";
//		dbpool  db = new dbpool();
//		List arrayList = new ArrayList();
//		     MyResultSet rs = db.executeQuery(sql);
//		     try {
//				while(rs!=null&&rs.next()){
//					List list = new ArrayList();
//					     list.add(rs.getString("id"));
//					     list.add(rs.getString("name"));
//					     arrayList.add(list);
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			try {
//				db.close(rs);
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		return arrayList;
//	}
}

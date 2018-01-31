/*
 * OracleUserList.java
 *
 * Created on 2005年5月24日, 上午10:58
 */

package com.whaty.platform.database.oracle.standard.entity.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitBatch;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitStudent;
import com.whaty.platform.entity.recruit.RecruitBatch;
import com.whaty.platform.entity.recruit.RecruitEduInfo;
import com.whaty.platform.entity.recruit.RecruitStudent;
import com.whaty.platform.entity.user.BasicUserList;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.entity.user.HumanPlatformInfo;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.entity.user.StudentEduInfo;
import com.whaty.platform.entity.user.TeacherEduInfo;
import com.whaty.platform.util.Address;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.RedundanceData;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

/**
 * 
 * @author Administrator
 */
public class OracleBasicUserList implements BasicUserList {
	// private String condition = "";

	// private String SQLTEACHER = "select
	// id,name,password,email,teach_time,gender,teach_level,teach_time,phone,address,zip_code,workplace,position,note,title
	// from entity_teacher_info ";

	//	private String SQLTEACHER = " select id, name, gh, password, email, phone, address, zip_code, gender, "
	//			+ "workplace, position, title,status, dep_id, login_num, last_login_time, last_login_ip, teach_level,"
	//			+ "site,teach_time, work_kind, note, re0, re1, re2, re3, re4, re5, re6, re7,re8,re9,id_card,"
	//			+ "mobilephone,research_direction,type from entity_teacher_info ";

//	private String SQLTEACHER = " select id, name, gh, password, email, phone, address, zip_code, gender, "
//			+ "workplace, position, title,status, dep_id, login_num, last_login_time, last_login_ip, teach_level,"
//			+ "site,teach_time, work_kind, note, re0, re1, re2, re3, re4, re5, re6, re7,re8,re9,id_card,"
//			+ "mobilephone,research_direction,type,d_name from (select t.id  , t.name  , t.gh   , t.password, t.email, t.phone, t.address, t.zip_code, t.gender, t.workplace, t.position, t.title, t.status, t.dep_id, t.login_num, t.last_login_time, t.last_login_ip, t.teach_level, t.site, t.teach_time, t.work_kind, t.note, t.re0, t.re1, t.re2, t.re3, t.re4, t.re5, t.re6, t.re7, t.re8, t.re9, t.id_card, t.mobilephone, t.research_direction, t.type,d.name as d_name from entity_teacher_info t,entity_dep_info d where t.dep_id = d.id(+))";
	
	//bjsy2 
	private String SQLTEACHER = "select id ,name ,login_id,gender,mobilephone,teach_level,status from (select t.id,t.true_name as name,u.login_id,t.gender,t.mobilephone,ec.name as teach_level,en.name as status from pe_teacher t,enum_const en,sso_user u, enum_const ec where t.flag_active=en.id and u.id = t.id and ec.id = t.flag_paper ) ";
	
	private String SQLSITETEACHER = " select id, site_id, isconfirm, name, gh, password, email, phone, address,research_direction, "
			+ "zip_code, gender, workplace, position, title, status, dep_id, login_num, last_login_time, "
			+ "last_login_ip, teach_level, site, teach_time, work_kind, note, re0, re1, re2, "
			+ "re3, re4, re5, re6, re7,re8,re9, mobilephone,confirm_note,photo_link,type from entity_siteteacher_info ";
	
	//查找已指定学生的分站教师; 
//	private String  SQL_APPOINT_SITE_TEACHER ="select distinct f.id,f.site_id,f.isconfirm,f.name,f.gh,f.password,f.email,f.phone,f.address," +
//			"f.zip_code,f.gender,f.workplace,f.position,f.title,f.status,f.dep_id,f.login_num,f.last_login_time," +
//			"f.last_login_ip,f.teach_level,f.site,f.teach_time,f.work_kind,f.note,f.re0,f.re1,f.re2," +
//			"f.re3,f.re4,f.re5,f.re6,f.re7,f.re8,f.re9,f.mobilephone,f.research_direction,f.confirm_note," +
//			"f.photo_link,f.type from entity_graduate_siteteachlimit t ,entity_siteteacher_info f entity_elective      ee,entity_teach_class   etc, entity_course_active eca, entity_course_info   eci, entity_semester_info esis, entity_student_info  esi where ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = esi.id and eci.id in ('000001','000011') and f.type = '1' and t.siteteacher_id = f.id and esi.id = t.student_id";
	
	private String  SQL_APPOINT_SITE_TEACHER="select distinct f.id,f.site_id,f.isconfirm,f.name,f.gh,f.password,f.email,f.phone,f.address,f.zip_code,f.gender,f.workplace,f.position,f.title,f.status,f.dep_id,f.login_num,f.last_login_time,f.last_login_ip,f.teach_level,f.site,f.teach_time,f.work_kind,f.note,f.re0,f.re1,f.re2,f.re3,f.re4,f.re5,f.re6,f.re7,f.re8,f.re9,f.mobilephone,f.research_direction,f.confirm_note,f.photo_link,f.type,f.semester_id from (select distinct ff.id,ff.site_id,ff.isconfirm,ff.name,ff.gh,ff.password,ff.email,ff.phone,ff.address,ff.zip_code,ff.gender,ff.workplace,ff.position,ff.title,ff.status,ff.dep_id,ff.login_num,ff.last_login_time,ff.last_login_ip,ff.teach_level,ff.site,ff.teach_time,ff.work_kind,ff.note,ff.re0,ff.re1,ff.re2,ff.re3,ff.re4,ff.re5,ff.re6,ff.re7,ff.re8,ff.re9,ff.mobilephone,ff.research_direction,ff.confirm_note,ff.photo_link,ff.type,esis.id as semester_id from entity_graduate_siteteachlimit t,entity_siteteacher_info        ff,entity_elective      ee,entity_teach_class   etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis, entity_student_info  esi where ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = esi.id and eci.id in ('000001','000011') and ff.type = '1' and t.siteteacher_id = ff.id and esi.id = t.student_id) f ";
	
	// private String SQLTEACHER_ORDERBY = " order by id";
	/*
	 	private String SQLPASSTSTUDENT = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
			+ "	postalcode,address,mobilephone,phone,considertype,considertype_status,batch_id,status,testcard_id,append_score,score,site_name,major_name,edutype_name,register_status,grade_id,grade_name,reg_no,score_1,score_2,score_3,score_4,photo_status,idcard_status,graduatecard_status"
			+ " from (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,"
			+ "	rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.register_status,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,esi.name as site_name,emi.name as major_name,eet.name as edutype_name,nvl(egi.id,'') as grade_id,nvl(egi.name,'') as grade_name,rsi.reg_no,rsi.score_1 as score_1,rsi.score_2 as score_2,rsi.score_3 as score_3,rsi.score_4 as score_4,rsi.photo_status,rsi.idcard_status,rsi.graduatecard_status  from recruit_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet,entity_grade_info egi,entity_student_info esti where rsi.status='2' and rsi.site_id=esi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id and rsi.reg_no=esti.reg_no(+) and esti.grade_id=egi.id(+))";

	 */
	
	private String SQLPASSTSTUDENT = "select id, name, password, gender, folk, birthday, zzmm, edu, site_id, edutype_id, major_id, card_type, card_no, hometown, email, postalcode, address, mobilephone, phone, considertype, considertype_status, batch_id, status, testcard_id, append_score, score, site_name, major_name, edutype_name, register_status, grade_id, grade_name, reg_no, score_1, score_2, score_3, score_4, photo_status, idcard_status, graduatecard_status from (select rsi.id, rsi.name, rsi.password, rsi.gender, rsi.folk, to_char(rsi.birthday, 'yyyy-mm-dd') as birthday, rsi.zzmm, edu, rsi.site_id, rsi.edutype_id, rsi.major_id, rsi.card_type, rsi.card_no, rsi.hometown, rsi.email, rsi.postalcode, rsi.address, rsi.mobilephone, rsi.phone, rsi.considertype, rsi.considertype_status, rsi.register_status, rsi.batch_id, rsi.status, rsi.testcard_id, rsi.append_score, rsi.score, esi.name as site_name, emi.name as major_name, eet.name as edutype_name, nvl(egi.id, '') as grade_id, nvl(egi.name, '') as grade_name, rsi.reg_no, rsi.score_1 as score_1, rsi.score_2 as score_2, rsi.score_3 as score_3, rsi.score_4 as score_4, rsi.photo_status, rsi.idcard_status, rsi.graduatecard_status from recruit_student_info rsi, entity_site_info esi, entity_major_info emi, entity_edu_type eet, entity_grade_info egi where rsi.status = '2' and rsi.site_id = esi.id and rsi.major_id = emi.id and rsi.edutype_id = eet.id and rsi.grade_id = egi.id(+)) ";

	private String sql_site = "(select id,name from entity_site_info) s";

	private String sql_grade = "(select id,name from entity_grade_info) g";

	private String sql_edu_type = "(select id,name from entity_edu_type) e";

	private String sql_major = "(select id,name from entity_major_info where id<>'0') m";

	/*
	 * private String SQLSTUDENT = "select
	 * id,u_name,id_card,m_name,g_name,e_name,s_name,s_id,reg_no,address,company,degree,edu,gender,phone,title,work_place,zip_address,m_id,g_id,s_id,e_id
	 * from" +" (select u.id as id,u.name as u_name,u.grade_id as
	 * g_id,u.major_id as m_id,u.edu_type_id as e_id,u.site as s_id,u.id_card as
	 * id_card,u.reg_no as reg_no,u.address as address,u.company as company,"
	 * +"u.degree as degree,u.edu as edu,u.gender as gender,u.phone as
	 * phone,u.title as title,u.work_place as work_place," +"u.zip_address as
	 * zip_address,m.name as m_name,g.name as g_name,e.name as e_name,s.name as
	 * s_name from " +"(select
	 * id,name,major_id,grade_id,site,edu_type_id,id_card,reg_no,address,company,degree,edu,gender,id_card,phone,title,work_place,zip_address "
	 * +"from lrn_user_info where isgraduated = '0' and status = '0000' "+
	 * condition +") u," + sql_major + "," + sql_grade + "," + sql_edu_type +
	 * "," + sql_site + " where " +"u.major_id = m.id and u.grade_id = g.id and
	 * u.site = s.id and u.edu_type_id = e.id) " ;
	 */

	// private String SQLSTUDENT_ORDERBY = "order by id asc";
//	private String SQLMANAGER = "select id,login_id,name,nickname,group_id,group_name,last_login_time,last_login_ip,"
//			+ "login_num,status,site_id,mobilephone,gender from (select m.id as id,m.login_id as login_id,m.name as name,m.nickname as nickname,"
//			+ "m.group_id as group_id,r.name as group_name, m.last_login_time as last_login_time, "
//			+ "m.last_login_ip as last_login_ip,m.login_num as login_num, m.status as status ,"
//			+ "m.site as site_id,m.mobilephone as mobilephone,m.re1 as gender from entity_manager_info m,entity_rightgroup_info r where  m.group_id = r.id(+))";

	//JBSY2
	private String SQLMANAGER = "select id,name,login_id,gender,mobilephone from (select m.id,m.name,u.login_id,m.gender,m.mobile_phone as mobilephone from pe_manager m,sso_user u where u.id=m.id) ";

//	private String SQLSITEMANAGER = "select id,login_id,name,s_name,site_id,status,login_num,last_login_ip,"
//			+ "last_login_time,mobilephone,gender from (select m.id as id,m.login_id as login_id,m.name as name,"
//			+ "s.name as s_name,s.id as site_id,m.status as status,m.login_num as login_num,"
//			+ "m.last_login_ip as last_login_ip,m.last_login_time as last_login_time,m.mobilephone as mobilephone"
//			+ ",m.re1 as gender from entity_sitemanager_info m,entity_site_info s where  m.site_id = s.id(+))";

	//bjsy2
	private String SQLSITEMANAGER = "select id,name,login_id,gender,mobilephone,site_id,site_name from (select m.id,m.name,u.login_id,m.gender,m.mobile_phone as mobilephone,s.id as site_id,s.name as site_name from pe_sitemanager m,sso_user u,pe_site s where u.id=m.id and m.fk_site_id=s.id) ";
	
	
	// private String SQLMANAGER_ORDERBY = " order by id";

	/** Creates a new instance of OracleUserList */
	public OracleBasicUserList() {
	}

	public String getStudentSql(String con, String orderby) {
		String sql = "";
		if (con == null)
			con = "";
//		if (con.indexOf("ame") > 0) {
//			con = con.substring(0, con.indexOf("name")) + "eu."
//					+ con.substring(con.indexOf("name"));
//		}
		if (orderby == null)
			orderby = "";
//		sql = "select id,name,idcard,card_type,major_name,grade_name,edutype_name,site_name,site_id,reg_no,address"
//				+ ",degree,edu,gender,phone,title,workplace,zip_address,major_id,grade_id,edutype_id,password"
//				+ ",birthday,email,fax,folk,graduated_major,graduated_sch,graduated_time,home_address,home_postalcode"
//				+ ",work_address,work_postalcode,hometown,mobilephone,note,position,zzmm,class_id,entrance_date"
//				+ ",photo_link,status,study_form,study_status,degree_status,GRADUATE_OPERATE_TIME"
//				+ ",GRADUATE_OPERATOR,SCHOOL_NAME,SCHOOL_CODE,GRADUATE_YEAR,GRADUATE_CARDNO,ISGRADUATED"
//				+ ",ISDEGREED,expend_score_status,graduate_status,graduate_no"
//				+ ",degree_operate_time,degree_operator,degree_score,degree_release from "
//				+ "(select u.id as id,u.name as name,u.grade_id as grade_id,u.major_id as major_id,u.edutype_id as edutype_id,u.site_id as site_id,u.id_card as idcard,eu.card_type,u.reg_no as reg_no,eu.home_address as address,eu.degree as degree,eu.edu as edu,eu.gender as gender,eu.phone as phone,eu.title as title,eu.workplace as workplace,eu.work_postalcode as zip_address,m.name as major_name,g.name as grade_name,e.name as edutype_name,s.name as site_name,su.password,eu.birthday,su.email,eu.fax,eu.folk,eu.graduated_major,eu.graduated_sch,eu.graduated_time,eu.home_address,eu.home_postalcode,eu.work_address,eu.work_postalcode,eu.hometown,eu.mobilephone,eu.note,eu.position,eu.zzmm,u.class_id,u.graduate_operator,to_char(u.GRADUATE_OPERATE_TIME,'yyyy-mm-dd') as GRADUATE_OPERATE_TIME,u.entrance_date,u.photo_link,u.status as status,u.study_form,u.study_status,case when u.degree_status='0' then '否' else '是' end as degree_status,u.SCHOOL_NAME,u.SCHOOL_CODE,u.GRADUATE_YEAR,u.GRADUATE_CARDNO,case when u.ISGRADUATED='0' then '否' else '是' end as ISGRADUATED,case when u.ISDEGREED='0' then '否' else '是' end as ISDEGREED,case when u.expend_score_status='0' then '否' else '是' end as expend_score_status,case when u.graduate_status='0' then '否' else '是' end as graduate_status,u.graduate_no,u.degree_operate_time,u.degree_operator,u.degree_score,case when u.degree_release='0' then '否' else '是' end as degree_release from (select u.* from entity_student_info u,entity_usernormal_info eu where u.id=eu.id and  u.status = '0' "
//				+ con
//				+ ") u,entity_usernormal_info eu,sso_user su,"
//				+ sql_major
//				+ ","
//				+ sql_grade
//				+ ","
//				+ sql_edu_type
//				+ ","
//				+ sql_site
//				+ " where u.major_id = m.id and u.grade_id = g.id and u.site_id = s.id and u.edutype_id = e.id and u.id=eu.id and u.id=su.id) "
//				+ orderby;
		sql = "select id,reg_no,name,gender,mobilephone,sitename,gradename,majorname,eduname,site_id,grade_id,major_id,edutype_id from (select pest.id,pest.reg_no,pest.true_name as name,prst.gender,prst.mobilephone,si.name as sitename,gr.name as gradename,ma.name as majorname,edu.name as eduname,si.id as site_id,gr.id as grade_id,ma.id as major_id,edu.id as edutype_id  from pr_student_info prst,pe_student pest,pe_site si,pe_edutype edu,pe_major ma,pe_grade gr where prst.id=pest.id and pest.fk_site_id=si.id and pest.fk_edutype_id=edu.id and pest.fk_major_id= ma.id and pest.fk_grade_id=gr.id  order by pest.reg_no) "+con;
		return sql;
	}
	
	public String getStudentFeeSql(String con, String orderby) {
		String sql = "";
		if (con == null)
			con = "";
		if (con.indexOf("ame") > 0) {
			con = con.substring(0, con.indexOf("name")) + "eu."
					+ con.substring(con.indexOf("name"));
		}
		if (orderby == null)
			orderby = "";
		sql = "select id,name,idcard,card_type,major_name,grade_name,edutype_name,site_name,site_id,reg_no,address"
				+ ",degree,edu,gender,phone,title,workplace,zip_address,major_id,grade_id,edutype_id,password"
				+ ",birthday,email,fax,folk,graduated_major,graduated_sch,graduated_time,home_address,home_postalcode"
				+ ",work_address,work_postalcode,hometown,mobilephone,note,position,zzmm,class_id,entrance_date"
				+ ",photo_link,status,study_form,study_status,degree_status,GRADUATE_OPERATE_TIME"
				+ ",GRADUATE_OPERATOR,SCHOOL_NAME,SCHOOL_CODE,GRADUATE_YEAR,GRADUATE_CARDNO,ISGRADUATED"
				+ ",ISDEGREED,expend_score_status,graduate_status,graduate_no"
				+ ",degree_operate_time,degree_operator,degree_score,degree_release,nvl(fee_value,'0') as fee_value,nvl(type3_value,'0') as type3_value  from "
				+ "(select u.id as id,u.name as name,u.grade_id as grade_id,u.major_id as major_id,u.edutype_id as edutype_id,u.site_id as site_id,u.id_card as idcard,eu.card_type,u.reg_no as reg_no,eu.home_address as address,eu.degree as degree,eu.edu as edu,eu.gender as gender,eu.phone as phone,eu.title as title,eu.workplace as workplace,eu.work_postalcode as zip_address,m.name as major_name,g.name as grade_name,e.name as edutype_name,s.name as site_name,su.password,eu.birthday,su.email,eu.fax,eu.folk,eu.graduated_major,eu.graduated_sch,eu.graduated_time,eu.home_address,eu.home_postalcode,eu.work_address,eu.work_postalcode,eu.hometown,eu.mobilephone,eu.note,eu.position,eu.zzmm,u.class_id,u.graduate_operator,to_char(u.GRADUATE_OPERATE_TIME,'yyyy-mm-dd') as GRADUATE_OPERATE_TIME,u.entrance_date,u.photo_link,u.status as status,u.study_form,u.study_status,case when u.degree_status='0' then '否' else '是' end as degree_status,u.SCHOOL_NAME,u.SCHOOL_CODE,u.GRADUATE_YEAR,u.GRADUATE_CARDNO,case when u.ISGRADUATED='0' then '否' else '是' end as ISGRADUATED,case when u.ISDEGREED='0' then '否' else '是' end as ISDEGREED,case when u.expend_score_status='0' then '否' else '是' end as expend_score_status,case when u.graduate_status='0' then '否' else '是' end as graduate_status,u.graduate_no,u.degree_operate_time,u.degree_operator,u.degree_score,case when u.degree_release='0' then '否' else '是' end as degree_release, k.fee_value,l.type3_value  from (select u.* from entity_student_info u,entity_usernormal_info eu where u.id=eu.id and  u.status = '0' "
				+ con
				+ ") u,entity_usernormal_info eu,sso_user su,"
				+ sql_major
				+ ","
				+ sql_grade
				+ ","
				+ sql_edu_type
				+ ","
				+ sql_site
				+ " ,(select user_id, sum(fee_value) as fee_value from entity_userfee_record where   checked='1' and payout_type='DEPOSIT' and fee_type ='CREDIT' group by user_id) k , entity_userfee_level l where u.major_id = m.id and u.grade_id = g.id and u.site_id = s.id and u.edutype_id = e.id and u.id=eu.id and u.id=su.id and u.id = k.user_id(+) and u.id = l.user_id(+)) "
				+ orderby;
		return sql;
	}

	private String getAllStudentSql(String con, String orderby) {
		String sql = "";
		if (con == null)
			con = "";
		if (con.indexOf("ame") > 0) {
			con = con.substring(0, con.indexOf("name")) + "eu."
					+ con.substring(con.indexOf("name"));
		}
		if (orderby == null)
			orderby = "";
		sql = "select id,name,idcard,major_name,grade_name,edutype_name,site_name,site_id,reg_no,address,degree,"
				+ "edu,gender,phone,title,workplace,zip_address,major_id,grade_id,edutype_id,password,birthday,"
				+ "email,fax,folk,graduated_major,graduated_sch,graduated_time,home_address,home_postalcode,"
				+ "work_address,work_postalcode,hometown,mobilephone,note,position,zzmm,class_id,entrance_date,"
				+ "photo_link,isgraduated,status,study_form,study_status,isdegreed,graduate_status,degree_status,"
				+ "graduate_no,graduate_operate_time,graduate_operator,degree_operate_time,degree_operator,degree_score,degree_release from (select u.id as id,u.name as name,"
				+ "u.grade_id as grade_id,u.major_id as major_id,u.edutype_id as edutype_id,u.site_id as site_id,"
				+ "u.id_card as idcard,u.reg_no as reg_no,eu.home_address as address,eu.degree as degree,"
				+ "eu.edu as edu,eu.gender as gender,eu.phone as phone,eu.title as title,eu.workplace as workplace,"
				+ "eu.work_postalcode as zip_address,m.name as major_name,g.name as grade_name,"
				+ "e.name as edutype_name,s.name as site_name,su.password,eu.birthday,su.email,eu.fax,eu.folk,"
				+ "eu.graduated_major,eu.graduated_sch,eu.graduated_time,eu.home_address,eu.home_postalcode,"
				+ "eu.work_address,eu.work_postalcode,eu.hometown,eu.mobilephone,eu.note,eu.position,eu.zzmm,"
				+ "u.class_id,u.entrance_date,u.photo_link,u.isgraduated as isgraduated,u.status as status,"
				+ "u.study_form,u.study_status,u.isdegreed,u.graduate_status,u.degree_status,u.graduate_no,"
				+ "to_char(u.graduate_operate_time,'yyyy-mm-dd') as graduate_operate_time,u.graduate_operator,to_char(u.degree_operate_time,'yyyy-mm-dd') as degree_operate_time,u.degree_operator,u.degree_score,u.degree_release from "
				+ "(select u.* from entity_student_info u,entity_usernormal_info eu where u.id=eu.id"
				+ con
				+ ") u,entity_usernormal_info eu,sso_user su,"
				+ sql_major
				+ ","
				+ sql_grade
				+ ","
				+ sql_edu_type
				+ ","
				+ sql_site
				+ " where u.major_id = m.id and u.grade_id = g.id and u.site_id = s.id and u.edutype_id = e.id and u.id=eu.id and u.id=su.id) "
				+ orderby;
		return sql;
	}

	/*
	 * public List getManagers(Page page) { ArrayList managerlist = new
	 * ArrayList(); dbpool db = new dbpool(); MyResultSet rs = null; String sql =
	 * ""; try { sql = SQLMANAGER + condition; rs =
	 * db.execute_oracle_page(sql,page.getPageInt(),page.getPageSize());
	 * while(rs!=null && rs.next()) { OracleManager oraclemanager = new
	 * OracleManager(); oraclemanager.setId(rs.getString("id"));
	 * oraclemanager.setName(rs.getString("name"));
	 * oraclemanager.setNickname(rs.getString("nickname"));
	 * oraclemanager.setPassword(rs.getString("password"));
	 * oraclemanager.setEmail(rs.getString("email"));
	 * oraclemanager.setSite_id(rs.getString("site_id"));
	 * oraclemanager.setGroup_id(rs.getString("group_id"));
	 * oraclemanager.setLast_login_ip(rs.getString("last_login_ip"));
	 * oraclemanager.setLast_login_time(rs.getString("last_login_time"));
	 * oraclemanager.setStatus(rs.getString("status"));
	 * oraclemanager.setLogin_num(rs.getInt("login_num"));
	 * managerlist.add(oraclemanager); } } catch (java.sql.SQLException e) {
	 * Log.setError("TeacherList.getTeachers() error" + sql); } finally {
	 * db.close(rs); db = null; } return managerlist; }
	 * 
	 * public List getManagersByGroup(Page page, String group_id) { condition = "
	 * and m.group_id = '"+group_id+"'"; return getManagers(page); }
	 * 
	 * public int getManagersByGroupNum(String group_id) { condition = " and
	 * m.group_id = '"+group_id+"'"; return getManagersNum(); }
	 * 
	 * public List getManagersBySite(Page page, Site site) { condition = " and
	 * m.site_id = '"+site.getId()+"'"; return getManagers(page); }
	 * 
	 * public int getManagersBySiteNum(Site site) { condition = " and m.site_id =
	 * '"+site.getId()+"'"; return getManagersNum(); }
	 * 
	 * public int getManagersNum() { dbpool db = new dbpool(); String sql = "";
	 * sql = SQLMANAGER + condition; int i = db.countselect(sql); return i; }
	 * 
	 * public List getStudents(Page page) { ArrayList studentlist=new
	 * ArrayList(); dbpool db=new dbpool(); String sql=""; MyResultSet rs=null;
	 * try { sql=SQLSTUDENT+SQLSTUDENT_ORDERBY;
	 * rs=db.execute_oracle_page(sql,page.getPageInt(),page.getPageSize());
	 * while(rs!=null && rs.next()) { OracleSimpleStudent student=new
	 * OracleSimpleStudent(); student.setId(rs.getString("id"));
	 * student.setName(rs.getString("u_name"));
	 * student.setReg_no(rs.getString("reg_no"));
	 * student.setEdutype_id(rs.getString("e_id"));
	 * student.setGender(rs.getString("gender"));
	 * student.setGrade_id(rs.getString("g_id"));
	 * student.setIdCardNo(rs.getString("id_card"));
	 * student.setMajor_id(rs.getString("m_id"));
	 * student.setSite_id(rs.getString("s_id"));
	 * student.setSite_name(rs.getString("s_name"));
	 * student.setMajor_name(rs.getString("m_name"));
	 * student.setGrade_name(rs.getString("g_name"));
	 * student.setEdutype_name(rs.getString("e_name")); } }
	 * catch(java.sql.SQLException e) { Log.setError("StudentList.getStudents()
	 * error! "+sql); } finally { db.close(rs); db=null; } return studentlist; }
	 * 
	 * public List getStudents(Page page, String site_id, String edutype_id,
	 * String major_id, String grade_id, Student student) { boolean is_first =
	 * true; if(site_id!=null && !site_id.equals("")) { sql_site = "(select
	 * id,name from lrn_site_info where id = '"+site_id+"') s"; }
	 * if(edutype_id!=null && !edutype_id.equals("")) { sql_edu_type = "(select
	 * id,name from lrn_edu_type where id = '"+edutype_id+"') e"; }
	 * if(major_id!=null && !major_id.equals("")) { sql_major = "(select id,name
	 * from lrn_major_info where id = '"+major_id+"') m"; } if(grade_id!=null &&
	 * !grade_id.equals("")) { sql_grade = "(select id,name from lrn_grade_info
	 * where id = '"+grade_id+"') g"; } if(student.getName() != null &&
	 * !student.getName().equals("")) { condition = "and name like '%"
	 * +student.getName().replaceAll(" ","") + "%'"; is_first = false; }
	 * 
	 * if (student.getStudentInfo().getReg_no() != null &&
	 * !student.getStudentInfo().getReg_no().equals("")) { if (is_first) {
	 * condition = "and reg_no like '%" +
	 * student.getStudentInfo().getReg_no().replaceAll(" ","") + "%'"; } else {
	 * condition = condition + " and reg_no like '%" +
	 * student.getStudentInfo().getReg_no().replaceAll(" ","") + "%'"; }
	 * is_first = false; }
	 * 
	 * if (student.getNormalInfo().getIdcard() != null &&
	 * !student.getNormalInfo().getIdcard().equals("")) { if (is_first) {
	 * condition = "and id_card like '%" +
	 * student.getNormalInfo().getIdcard().replaceAll(" ","") + "%'"; } else {
	 * condition = condition + " and id_card like '%" +
	 * student.getNormalInfo().getIdcard().replaceAll(" ","") + "%'"; } is_first =
	 * false; }
	 * 
	 * if (student.getNormalInfo().getPhones() != null &&
	 * !student.getNormalInfo().getPhones().equals("")) { if (is_first) {
	 * condition = "and phone like '%" +
	 * student.getNormalInfo().getPhones().replaceAll(" ","") + "%'"; } else {
	 * condition = condition + " and phone like '%" +
	 * student.getNormalInfo().getPhones().replaceAll(" ","") + "%'"; } is_first =
	 * false; } return getStudents(page); }
	 * 
	 * public int getStudentsNum() { dbpool db = new dbpool(); String sql = "";
	 * sql = SQLSTUDENT + SQLSTUDENT_ORDERBY; int i = db.countselect(sql);
	 * return i; }
	 * 
	 * public int getStudentsNum(String site_id, String edutype_id, String
	 * major_id, String grade_id, Student student) { boolean is_first = true;
	 * if(site_id!=null && !site_id.equals("")) { sql_site = "(select id,name
	 * from lrn_site_info where id = '"+site_id+"') s"; } if(edutype_id!=null &&
	 * !edutype_id.equals("")) { sql_edu_type = "(select id,name from
	 * lrn_edu_type where id = '"+edutype_id+"') e"; } if(major_id!=null &&
	 * !major_id.equals("")) { sql_major = "(select id,name from lrn_major_info
	 * where id = '"+major_id+"') m"; } if(grade_id!=null &&
	 * !grade_id.equals("")) { sql_grade = "(select id,name from lrn_grade_info
	 * where id = '"+grade_id+"') g"; } if(student.getName() != null &&
	 * !student.getName().equals("")) { condition = "and name like '%"
	 * +student.getName().replaceAll(" ","") + "%'"; is_first = false; }
	 * 
	 * if (student.getStudentInfo().getReg_no() != null &&
	 * !student.getStudentInfo().getReg_no().equals("")) { if (is_first) {
	 * condition = "and reg_no like '%" +
	 * student.getStudentInfo().getReg_no().replaceAll(" ","") + "%'"; } else {
	 * condition = condition + " and reg_no like '%" +
	 * student.getStudentInfo().getReg_no().replaceAll(" ","") + "%'"; }
	 * is_first = false; }
	 * 
	 * if (student.getNormalInfo().getIdcard() != null &&
	 * !student.getNormalInfo().getIdcard().equals("")) { if (is_first) {
	 * condition = "and id_card like '%" +
	 * student.getNormalInfo().getIdcard().replaceAll(" ","") + "%'"; } else {
	 * condition = condition + " and id_card like '%" +
	 * student.getNormalInfo().getIdcard().replaceAll(" ","") + "%'"; } is_first =
	 * false; }
	 * 
	 * if (student.getNormalInfo().getPhones() != null &&
	 * !student.getNormalInfo().getPhones().equals("")) { if (is_first) {
	 * condition = "and phone like '%" +
	 * student.getNormalInfo().getPhones().replaceAll(" ","") + "%'"; } else {
	 * condition = condition + " and phone like '%" +
	 * student.getNormalInfo().getPhones().replaceAll(" ","") + "%'"; } is_first =
	 * false; } return getStudentsNum(); }
	 * 
	 * public List getTeachers(Page page) { ArrayList teacherlist = new
	 * ArrayList(); dbpool db = new dbpool(); MyResultSet rs = null; String sql =
	 * ""; try { sql = SQLTEACHER + condition + SQLTEACHER_ORDERBY; rs =
	 * db.execute_oracle_page(sql,page.getPageInt(),page.getPageSize());
	 * while(rs!=null && rs.next()) { OracleTeacher oracleteacher = new
	 * OracleTeacher(); oracleteacher.setId(rs.getString("id"));
	 * oracleteacher.setName(rs.getString("name"));
	 * oracleteacher.setNickname(rs.getString("nickname"));
	 * oracleteacher.setPassword(rs.getString("password"));
	 * oracleteacher.setEmail(rs.getString("email"));
	 * oracleteacher.setGender(rs.getString("gender"));
	 * oracleteacher.setTeach_level(rs.getString("teach_level"));
	 * oracleteacher.setTeach_time(rs.getString("teach_time"));
	 * oracleteacher.setPhone(rs.getString("phone"));
	 * oracleteacher.setAddress(rs.getString("address"));
	 * oracleteacher.setZipcode(rs.getString("zip_code"));
	 * oracleteacher.setWorkplace(rs.getString("work_place"));
	 * oracleteacher.setPosition(rs.getString("position"));
	 * oracleteacher.setNote(rs.getString("note"));
	 * oracleteacher.setTitle(rs.getString("title"));
	 * teacherlist.add(oracleteacher); } } catch (java.sql.SQLException e) {
	 * Log.setError("TeacherList.getTeachers() error" + SQLTEACHER); } finally {
	 * db.close(rs); db = null; } return teacherlist; }
	 * 
	 * public int getTeachersNum() { dbpool db=new dbpool(); int
	 * i=db.countselect(SQLTEACHER+condition); return i; }
	 * 
	 * public List getTeachers(Page page, String search_type, String
	 * search_value, String teach_level) { boolean isFirst = true;
	 * if(search_value != null && !search_value.equals("") &&
	 * search_value.equals("null")) { if(isFirst) { condition = " where
	 * '"+search_type+"' like '%"+search_value+"%'"; } else { condition =
	 * condition + " and '"+search_type+"' like '%"+search_value+"%'"; } isFirst =
	 * false; } if(teach_level!=null && !teach_level.equals("") &&
	 * !teach_level.equals("null")) { if(isFirst) { condition = " where
	 * teach_level = '"+teach_level+"'"; } else { condition = condition + " and
	 * teach_level = '"+teach_level+"'"; } isFirst = false; } return
	 * getTeachers(page); }
	 * 
	 * public int getTeachersNum(String search_type, String search_value, String
	 * teach_level) { boolean isFirst = true; if(search_value != null &&
	 * !search_value.equals("") && search_value.equals("null")) { if(isFirst) {
	 * condition = " where '"+search_type+"' like '%"+search_value+"%'"; } else {
	 * condition = condition + " and '"+search_type+"' like
	 * '%"+search_value+"%'"; } isFirst = false; } if(teach_level!=null &&
	 * !teach_level.equals("") && !teach_level.equals("null")) { if(isFirst) {
	 * condition = " where teach_level = '"+teach_level+"'"; } else { condition =
	 * condition + " and teach_level = '"+teach_level+"'"; } isFirst = false; }
	 * return getTeachersNum(); }
	 */

	public int getTeachersNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLTEACHER
				+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public int getSiteTeachersNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLSITETEACHER
				+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}
	
	/**
	 * 得到已指定学生的分站老师的数量
	 */
	public int getAppointStudentSiteTeachersNum(List searchproperty) {
		dbpool db = new dbpool();
//		String sql = SQL_APPOINT_SITE_TEACHER
//				+ Conditions.convertToCondition(searchproperty, null) +" and t.siteteacher_id = f.id ";
		String sql = SQL_APPOINT_SITE_TEACHER
		+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}
	public int getAppointStudentSiteTeachersNum2(List searchproperty) {
		dbpool db = new dbpool();
//		String sql = SQL_APPOINT_SITE_TEACHER
//				+ Conditions.convertToCondition(searchproperty, null) +" and t.siteteacher_id = f.id ";
		String sql = SQLSITETEACHER 
		+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getTeachers(Page page, List searchproperty, List orderproperty) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		ArrayList teacherlist = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = SQLTEACHER + condition;
			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			while (rs != null && rs.next()) {
				OracleTeacher oracleteacher = new OracleTeacher();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				oracleteacher.setId(rs.getString("id"));
				oracleteacher.setName(rs.getString("name"));
				oracleteacher.setLoginId(rs.getString("login_id"));
				// this.setNickname(rs.getString("nickname"));
//				oracleteacher.setPassword(rs.getString("password"));
//				oracleteacher.setEmail(rs.getString("email"));
//				eduInfo
//						.setResearchDirection(rs
//								.getString("research_direction"));
//				eduInfo.setGh(rs.getString("gh"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setTeach_level(rs.getString("teach_level"));
//				eduInfo.setTeach_time(rs.getString("teach_time"));
//				eduInfo.setDep_id(rs.getString("dep_id"));
//				eduInfo.setDep_name(rs.getString("d_name"));
//				eduInfo.setWork_kind(rs.getString("work_kind"));
//				normalInfo.setIdcard(rs.getString("id_card"));
//				normalInfo.setNote(rs.getString("note"));
//				normalInfo.setWorkplace(rs.getString("workplace"));
//				normalInfo.setPosition(rs.getString("position"));
//				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setGender(rs.getString("gender"));
//				if (rs.getString("phone") != null
//						&& rs.getString("phone").length() > 0)
//					normalInfo.setPhone(rs.getString("phone").split(","));
//				else
//					normalInfo.setPhone(new String[0]);

				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(new String[0]);

//				Address home_address = new Address();
//				Address work_address = new Address();
//				work_address.setAddress(rs.getString("address"));
//				work_address.setPostalcode(rs.getString("zip_code"));
//				normalInfo.setWork_address(work_address);
//				platformInfo.setLastlogin_ip(rs.getString("last_login_ip"));
//				platformInfo.setLastlogin_time(rs.getString("last_login_time"));
//				redundance.setRe1(rs.getString("re1"));
//				redundance.setRe2(rs.getString("re2"));
//				redundance.setRe3(rs.getString("re3"));
//				redundance.setRe4(rs.getString("re4"));
//				redundance.setRe5(rs.getString("re5"));
//				redundance.setRe6(rs.getString("re6"));
//				redundance.setRe7(rs.getString("re7"));
//				redundance.setRe8(rs.getString("re8"));
//				redundance.setRe9(rs.getString("re9"));
				oracleteacher.setNormalInfo(normalInfo);
				oracleteacher.setTeacherInfo(eduInfo);
				oracleteacher.setPlatformInfo(platformInfo);
				oracleteacher.setRedundace(redundance);

				teacherlist.add(oracleteacher);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("TeacherList.getTeachers() error" + SQLTEACHER);
		} finally {
			db.close(rs);
			db = null;
		}
		return teacherlist;
	}
	
	public List getSiteTeachers2(Page page, List searchproperty,
			List orderproperty) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		ArrayList teacherlist = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = " select name,email,gender,type, position, title, note, mobilephone,research_direction,site_id,majors,site_name from (select a.name,a.email,a.gender, a.position, a.title, a.note,a.type, a.mobilephone,a.research_direction,c.name as site_name,a.site_id,nvl(b.majors,'') as majors from entity_siteteacher_info a, entity_site_info c,(select siteteacher_id,max(substr(majors,2)) as majors from ( select siteteacher_id, sys_connect_by_path(major_name, ',') as majors from (select siteteacher_id, major_name, siteteacher_id || '_' || node nchild, siteteacher_id || '_' || (node - 1) nfather from (select siteteacher_id, major_name, row_number() over(partition by siteteacher_id order by siteteacher_id) as node from (select distinct a.siteteacher_id, c.name as major_name from entity_graduate_siteteachlimit a, entity_student_info b, entity_major_info c where a.student_id = b.id and b.major_id = c.id))) connect by prior nchild = nfather start with nfather like '%_0' ) group by siteteacher_id) b where a.id = b.siteteacher_id(+) and a.site_id = c.id) " + condition;
			Log.setDebug("siteTeachers: " + sql);
			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			while (rs != null && rs.next()) {
				List tmplist = new ArrayList();
				tmplist.add(rs.getString("site_name"));
				tmplist.add(rs.getString("name"));
				tmplist.add(rs.getString("gender"));
				tmplist.add(rs.getString("title"));
				tmplist.add(rs.getString("research_direction"));
				tmplist.add(rs.getString("position"));
				tmplist.add(rs.getString("note"));
				tmplist.add(rs.getString("mobilephone"));
				tmplist.add(rs.getString("majors"));
				
				teacherlist.add(tmplist);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("SiteTeacherList.getTeachers() error" + SQLTEACHER);
		} finally {
			db.close(rs);
			db = null;
		}

		return teacherlist;
	}
	public List getSiteTeachers(Page page, List searchproperty,
			List orderproperty) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		ArrayList teacherlist = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = SQLSITETEACHER + condition;
			Log.setDebug("siteTeachers: " + sql);
			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			while (rs != null && rs.next()) {
				OracleSiteTeacher oracleSiteTeacher = new OracleSiteTeacher();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				oracleSiteTeacher.setId(rs.getString("id"));
				oracleSiteTeacher.setSiteId(rs.getString("site_id"));
				oracleSiteTeacher.setConfirmStatus(rs.getString("isconfirm"));
				oracleSiteTeacher.setName(rs.getString("name"));
				oracleSiteTeacher.setConfirmNote(rs.getString("confirm_note"));
				oracleSiteTeacher.setPassword(rs.getString("password"));
				oracleSiteTeacher.setEmail(rs.getString("email"));
				eduInfo.setGh(rs.getString("gh"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setTeach_level(rs.getString("teach_level"));
				eduInfo.setTeach_time(rs.getString("teach_time"));
				eduInfo.setWork_kind(rs.getString("work_kind"));
				eduInfo.setResearchDirection(rs.getString("research_direction"));
				normalInfo.setNote(rs.getString("note"));
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setGender(rs.getString("gender"));
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(new String[0]);

				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(new String[0]);

				Address home_address = new Address();
				Address work_address = new Address();
				work_address.setAddress(rs.getString("address"));
				work_address.setPostalcode(rs.getString("zip_code"));
				normalInfo.setWork_address(work_address);
				platformInfo.setLastlogin_ip(rs.getString("last_login_ip"));
				platformInfo.setLastlogin_time(rs.getString("last_login_time"));
				redundance.setRe1(rs.getString("re1"));
				redundance.setRe2(rs.getString("re2"));
				redundance.setRe3(rs.getString("re3"));
				redundance.setRe4(rs.getString("re4"));
				redundance.setRe5(rs.getString("re5"));
				redundance.setRe6(rs.getString("re6"));
				redundance.setRe7(rs.getString("re7"));
				redundance.setRe8(rs.getString("re8"));
				redundance.setRe9(rs.getString("re9"));
				oracleSiteTeacher.setNormalInfo(normalInfo);
				oracleSiteTeacher.setTeacherInfo(eduInfo);
				oracleSiteTeacher.setPlatformInfo(platformInfo);
				oracleSiteTeacher.setRedundace(redundance);

				teacherlist.add(oracleSiteTeacher);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("SiteTeacherList.getTeachers() error" + SQLTEACHER);
		} finally {
			db.close(rs);
			db = null;
		}

		return teacherlist;
	}
	
	/**
	 * 	得到已指定学生的分站教师;
	 * @param searchproperty
	 * @param orderproperty
	 * @return
	 */
	public List getAppointStudentSiteTeachers(Page page, List searchproperty,
			List orderproperty) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		ArrayList teacherlist = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
//			sql = SQL_APPOINT_SITE_TEACHER + condition +" and t.siteteacher_id=f.id order by f.isconfirm";
			sql = SQL_APPOINT_SITE_TEACHER + condition +" order by f.isconfirm";
			Log.setDebug("siteTeachers: " + sql);
			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			while (rs != null && rs.next()) {
				OracleSiteTeacher oracleSiteTeacher = new OracleSiteTeacher();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				oracleSiteTeacher.setId(rs.getString("id"));
				oracleSiteTeacher.setSiteId(rs.getString("site_id"));
				oracleSiteTeacher.setConfirmStatus(rs.getString("isconfirm"));
				oracleSiteTeacher.setName(rs.getString("name"));
				oracleSiteTeacher.setConfirmNote(rs.getString("confirm_note"));
				oracleSiteTeacher.setPassword(rs.getString("password"));
				oracleSiteTeacher.setEmail(rs.getString("email"));
				eduInfo.setGh(rs.getString("gh"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setTeach_level(rs.getString("teach_level"));
				eduInfo.setTeach_time(rs.getString("teach_time"));
				eduInfo.setWork_kind(rs.getString("work_kind"));
				normalInfo.setNote(rs.getString("note"));
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setGender(rs.getString("gender"));
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(new String[0]);

				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(new String[0]);

				Address home_address = new Address();
				Address work_address = new Address();
				work_address.setAddress(rs.getString("address"));
				work_address.setPostalcode(rs.getString("zip_code"));
				normalInfo.setWork_address(work_address);
				platformInfo.setLastlogin_ip(rs.getString("last_login_ip"));
				platformInfo.setLastlogin_time(rs.getString("last_login_time"));
				redundance.setRe1(rs.getString("re1"));
				redundance.setRe2(rs.getString("re2"));
				redundance.setRe3(rs.getString("re3"));
				redundance.setRe4(rs.getString("re4"));
				redundance.setRe5(rs.getString("re5"));
				redundance.setRe6(rs.getString("re6"));
				redundance.setRe7(rs.getString("re7"));
				redundance.setRe8(rs.getString("re8"));
				redundance.setRe9(rs.getString("re9"));
				oracleSiteTeacher.setNormalInfo(normalInfo);
				oracleSiteTeacher.setTeacherInfo(eduInfo);
				oracleSiteTeacher.setPlatformInfo(platformInfo);
				oracleSiteTeacher.setRedundace(redundance);

				teacherlist.add(oracleSiteTeacher);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("SiteTeacherList.getTeachers() error" + SQLTEACHER);
		} finally {
			db.close(rs);
			db = null;
		}

		return teacherlist;
	}
	public List getAppointStudentSiteTeachers2(Page page, List searchproperty,
			List orderproperty) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		ArrayList teacherlist = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
//			sql = SQL_APPOINT_SITE_TEACHER + condition +" and t.siteteacher_id=f.id order by f.isconfirm";
			sql = SQLSITETEACHER + condition +" order by isconfirm";
			Log.setDebug("siteTeachers: " + sql);
			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			while (rs != null && rs.next()) {
				OracleSiteTeacher oracleSiteTeacher = new OracleSiteTeacher();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				oracleSiteTeacher.setId(rs.getString("id"));
				oracleSiteTeacher.setSiteId(rs.getString("site_id"));
				oracleSiteTeacher.setConfirmStatus(rs.getString("isconfirm"));
				oracleSiteTeacher.setName(rs.getString("name"));
				oracleSiteTeacher.setConfirmNote(rs.getString("confirm_note"));
				oracleSiteTeacher.setPassword(rs.getString("password"));
				oracleSiteTeacher.setEmail(rs.getString("email"));
				eduInfo.setGh(rs.getString("gh"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setTeach_level(rs.getString("teach_level"));
				eduInfo.setTeach_time(rs.getString("teach_time"));
				eduInfo.setWork_kind(rs.getString("work_kind"));
				normalInfo.setNote(rs.getString("note"));
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setGender(rs.getString("gender"));
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(new String[0]);

				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(new String[0]);

				Address home_address = new Address();
				Address work_address = new Address();
				work_address.setAddress(rs.getString("address"));
				work_address.setPostalcode(rs.getString("zip_code"));
				normalInfo.setWork_address(work_address);
				platformInfo.setLastlogin_ip(rs.getString("last_login_ip"));
				platformInfo.setLastlogin_time(rs.getString("last_login_time"));
				redundance.setRe1(rs.getString("re1"));
				redundance.setRe2(rs.getString("re2"));
				redundance.setRe3(rs.getString("re3"));
				redundance.setRe4(rs.getString("re4"));
				redundance.setRe5(rs.getString("re5"));
				redundance.setRe6(rs.getString("re6"));
				redundance.setRe7(rs.getString("re7"));
				redundance.setRe8(rs.getString("re8"));
				redundance.setRe9(rs.getString("re9"));
				oracleSiteTeacher.setNormalInfo(normalInfo);
				oracleSiteTeacher.setTeacherInfo(eduInfo);
				oracleSiteTeacher.setPlatformInfo(platformInfo);
				oracleSiteTeacher.setRedundace(redundance);

				teacherlist.add(oracleSiteTeacher);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("SiteTeacherList.getTeachers() error" + SQLTEACHER);
		} finally {
			db.close(rs);
			db = null;
		}

		return teacherlist;
	}

	public List getTeachers(List searchproperty, List orderproperty) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		ArrayList teacherlist = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = SQLTEACHER + condition;
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleTeacher oracleteacher = new OracleTeacher();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				oracleteacher.setId(rs.getString("id"));
				oracleteacher.setName(rs.getString("name"));
				oracleteacher.setLoginId(rs.getString("login_id"));
				// this.setNickname(rs.getString("nickname"));
//				oracleteacher.setPassword(rs.getString("password"));
//				oracleteacher.setEmail(rs.getString("email"));
//				eduInfo.setGh(rs.getString("gh"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setTeach_level(rs.getString("teach_level"));
//				eduInfo.setTeach_time(rs.getString("teach_time"));
//				eduInfo.setWork_kind(rs.getString("work_kind"));
//				normalInfo.setNote(rs.getString("note"));
//				normalInfo.setWorkplace(rs.getString("workplace"));
//				normalInfo.setPosition(rs.getString("position"));
//				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setGender(rs.getString("gender"));
//				if (rs.getString("phone") != null
//						&& rs.getString("phone").length() > 0)
//					normalInfo.setPhone(rs.getString("phone").split(","));
//				else
//					normalInfo.setPhone(new String[0]);
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone").split(","));
				else
					normalInfo.setMobilePhone(new String[0]);
						
//				Address home_address = new Address();
//				Address work_address = new Address();
//				work_address.setAddress(rs.getString("address"));
//				work_address.setPostalcode(rs.getString("zip_code"));
//				normalInfo.setWork_address(work_address);
//				platformInfo.setLastlogin_ip(rs.getString("last_login_ip"));
//				platformInfo.setLastlogin_time(rs.getString("last_login_time"));
//				redundance.setRe1(rs.getString("re1"));
//				redundance.setRe2(rs.getString("re2"));
//				redundance.setRe3(rs.getString("re3"));
//				redundance.setRe4(rs.getString("re4"));
//				redundance.setRe5(rs.getString("re5"));
//				redundance.setRe6(rs.getString("re6"));
//				redundance.setRe7(rs.getString("re7"));
//				redundance.setRe8(rs.getString("re8"));
//				redundance.setRe9(rs.getString("re9"));
				oracleteacher.setNormalInfo(normalInfo);
				oracleteacher.setTeacherInfo(eduInfo);
				oracleteacher.setPlatformInfo(platformInfo);
				oracleteacher.setRedundace(redundance);

				teacherlist.add(oracleteacher);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("TeacherList.getTeachers() error" + SQLTEACHER);
		} finally {
			db.close(rs);
			db = null;
		}
		return teacherlist;
	}

	public List getSiteTeachers(List searchproperty, List orderproperty) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		ArrayList teacherlist = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = SQLSITETEACHER + condition;
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleSiteTeacher oracleSiteTeacher = new OracleSiteTeacher();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				oracleSiteTeacher.setId(rs.getString("id"));
				oracleSiteTeacher.setSiteId(rs.getString("site_id"));
				oracleSiteTeacher.setConfirmStatus(rs.getString("isconfirm"));
				oracleSiteTeacher.setName(rs.getString("name"));
				// this.setNickname(rs.getString("nickname"));
				oracleSiteTeacher.setPassword(rs.getString("password"));
				oracleSiteTeacher.setEmail(rs.getString("email"));
				eduInfo.setGh(rs.getString("gh"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setTeach_level(rs.getString("teach_level"));
				eduInfo.setTeach_time(rs.getString("teach_time"));
				eduInfo.setWork_kind(rs.getString("work_kind"));
				normalInfo.setNote(rs.getString("note"));
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setGender(rs.getString("gender"));
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(new String[0]);
				Address home_address = new Address();
				Address work_address = new Address();
				work_address.setAddress(rs.getString("address"));
				work_address.setPostalcode(rs.getString("zip_code"));
				normalInfo.setWork_address(work_address);
				platformInfo.setLastlogin_ip(rs.getString("last_login_ip"));
				platformInfo.setLastlogin_time(rs.getString("last_login_time"));
				redundance.setRe1(rs.getString("re1"));
				redundance.setRe2(rs.getString("re2"));
				redundance.setRe3(rs.getString("re3"));
				redundance.setRe4(rs.getString("re4"));
				redundance.setRe5(rs.getString("re5"));
				redundance.setRe6(rs.getString("re6"));
				redundance.setRe7(rs.getString("re7"));
				redundance.setRe8(rs.getString("re8"));
				redundance.setRe9(rs.getString("re9"));
				oracleSiteTeacher.setNormalInfo(normalInfo);
				oracleSiteTeacher.setTeacherInfo(eduInfo);
				oracleSiteTeacher.setPlatformInfo(platformInfo);
				oracleSiteTeacher.setRedundace(redundance);

				teacherlist.add(oracleSiteTeacher);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("TeacherList.getTeachers() error" + SQLTEACHER);
		} finally {
			db.close(rs);
			db = null;
		}
		return teacherlist;
	}

	public int getStudentsNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = getStudentSql(Conditions
//				.convertToAndCondition(searchproperty), null);
		.convertToCondition(searchproperty,null), null);
		int i = db.countselect(sql);
		return i;
	}

	public List getStudents(List searchproperty, List orderproperty) {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		MyResultSet recruitinfo = null;
		try {
			sql = getStudentSql(Conditions
					.convertToCondition(searchproperty, null), Conditions
					.convertToCondition(null, orderproperty));
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				String reg_no = rs.getString("reg_no");
				// student.setNickname(rs.getString("nickname"));
//				student.setPassword(rs.getString("password"));
//				normalInfo.setBirthday(rs.getString("birthday"));
//				normalInfo.setDegree(rs.getString("degree"));
//				normalInfo.setEdu(rs.getString("edu"));
//				normalInfo.setCardType(rs.getString("card_type"));
//				if (rs.getString("email") != null
//						&& rs.getString("email").length() > 0)
//					normalInfo.setEmail(rs.getString("email").split(","));
//				else
//					normalInfo.setEmail(null);
//				if (rs.getString("fax") != null
//						&& rs.getString("fax").length() > 0)
//					normalInfo.setFax(rs.getString("fax").split(","));
//				else
//					normalInfo.setFax(null);
//				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
//				normalInfo.setGraduated_major(rs.getString("graduated_major"));
//				normalInfo.setGraduated_sch(rs.getString("graduated_sch"));
//				normalInfo.setGraduated_time(rs.getString("graduated_time"));
//				Address homeaddress = new Address();
//				homeaddress.setAddress(rs.getString("home_address"));
//				homeaddress.setPostalcode(rs.getString("home_postalcode"));
//				Address workaddress = new Address();
//				workaddress.setAddress(rs.getString("work_address"));
//				workaddress.setPostalcode(rs.getString("work_postalcode"));
//				normalInfo.setHome_address(homeaddress);
//				normalInfo.setHometown(rs.getString("hometown"));
//				normalInfo.setIdcard(rs.getString("idcard"));
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
//				normalInfo.setNote(rs.getString("note"));
//				if (rs.getString("phone") != null
//						&& rs.getString("phone").length() > 0)
//					normalInfo.setPhone(rs.getString("phone").split(","));
//				else
//					normalInfo.setPhone(null);
//				normalInfo.setPosition(rs.getString("position"));
//				normalInfo.setTitle(rs.getString("title"));
//				normalInfo.setWork_address(workaddress);
//				normalInfo.setWorkplace(rs.getString("workplace"));
//				normalInfo.setZzmm(rs.getString("zzmm"));

//				eduInfo.setClass_id(rs.getString("class_id"));
				// eduInfo.setClass_name(rs.getString("class_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("eduname"));
//				eduInfo.setEntrance_date(rs.getString("entrance_date"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("gradename"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("majorname"));
//				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("sitename"));
//				eduInfo.setStatus(rs.getString("status"));
//				eduInfo.setIsgraduated(rs.getString("isgraduated"));
//				eduInfo.setStudy_form(rs.getString("study_form"));
//				eduInfo.setStudy_status(rs.getString("study_status"));
//				eduInfo.setGraduate_status(rs.getString("graduate_status"));
//				eduInfo.setGraduate_no(rs.getString("graduate_no"));
//				eduInfo.setGraduate_operate_time(rs
//						.getString("graduate_operate_time"));
//				eduInfo.setGraduate_operator(rs.getString("graduate_operator"));
//				eduInfo.setIsdegreed(rs.getString("isdegreed"));
//				eduInfo.setDegree_status(rs.getString("degree_status"));
//				eduInfo.setDegree_operate_time(rs
//						.getString("degree_operate_time"));
//				eduInfo.setDegree_operator(rs.getString("degree_operator"));
//				eduInfo.setDegree_score(rs.getString("degree_score"));
//				eduInfo.setDegree_release(rs.getString("degree_release"));
//				eduInfo.setExpend_score_status(rs
//						.getString("expend_score_status"));
//				eduInfo.setSchool_name(rs.getString("SCHOOL_NAME"));
//				eduInfo.setSchool_code(rs.getString("SCHOOL_CODE"));
//				eduInfo.setGraduate_year(rs.getString("GRADUATE_YEAR"));
//				eduInfo.setGraduate_cardno(rs.getString("GRADUATE_CARDNO"));

//				if (reg_no != null && !reg_no.equals("")
//						&& !reg_no.equals("null")) {
//					RecruitStudent recruitStu = new OracleRecruitStudent();
//					String recruitsql = "select s.study_no as study_no,s.reg_no,s.testcard_id,case when CONSIDERTYPE='0' or considertype='无' or considertype is null then '无加分项' when considertype='1' then '有加分项' else '免试' end as considertype,CONSIDERTYPE_STATUS,s.batch_id,r.title as batch_name,case when register_status='1' then '已注册' else '未注册' end as register_status from recruit_student_info s,recruit_batch_info r where s.card_no='"
//							+ rs.getString("idcard")
//							+ "' and s.batch_id=r.id(+)";
//					recruitinfo = db.executeQuery(recruitsql);
//					String study_no = "";
//					while (recruitinfo != null && recruitinfo.next()) {
//						study_no = recruitinfo.getString("study_no");
//						recruitStu.setReg_no(recruitinfo.getString("reg_no"));
//						RecruitEduInfo reduInfo = new RecruitEduInfo();
//						reduInfo.setConsidertype_status(recruitinfo
//								.getString("CONSIDERTYPE_STATUS"));
//						reduInfo.setConsiderType(recruitinfo
//								.getString("CONSIDERTYPE"));
//						reduInfo.setRegister_status(recruitinfo
//								.getString("register_status"));
//						RecruitBatch batch = new OracleRecruitBatch();
//						batch.setId(recruitinfo.getString("batch_id"));
//						batch.setTitle(recruitinfo.getString("batch_name"));
//						reduInfo.setBatch(batch);
//						reduInfo.setTestCardId(recruitinfo
//								.getString("testcard_id"));
//						recruitStu.setEduInfo(reduInfo);
//					}
//					db.close(recruitinfo);
//					normalInfo.setStudy_no(study_no);
//					student.setRecruitStudent(recruitStu);
//				} else {
//					normalInfo.setStudy_no("");
//				}

				student.setNormalInfo(normalInfo);
				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db.close(recruitinfo);
			db = null;

		}
		return studentlist;
	}

	// 未注册学生个数
	public int getUnRegStudentsNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = SQLPASSTSTUDENT
				+ Conditions.convertToCondition(searchproperty, null);
		int i = db.countselect(sql);
		return i;
	}

	// 未注册学生列表
	public List getUnRegStudents(Page page, List searchproperty,
			List orderproperty) {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = SQLPASSTSTUDENT
				+ Conditions.convertToCondition(searchproperty, null);
		MyResultSet rs = null;
		try {
			db = new dbpool();
			studentlist = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setPassword(rs.getString("password"));
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setCardType(rs.getString("card_type"));
				normalInfo.setIdcard(rs.getString("card_no"));
				normalInfo.setEdu(rs.getString("edu"));
				normalInfo.setHometown(rs.getString("hometown"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("address"));
				homeaddress.setPostalcode(rs.getString("postalcode"));
				normalInfo.setHome_address(homeaddress);
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				student.setNormalInfo(normalInfo);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setRegister_status(rs.getString("register_status"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				eduInfo.setPhoto_status(rs.getString("photo_status"));
				eduInfo.setGraduatecard_status(rs
						.getString("graduatecard_status"));
				eduInfo.setIdcard_status(rs.getString("idcard_status"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				student.setStudentInfo(eduInfo);

				/*
				 * testStudent.setAppendScore(rs.getString("append_score"));
				 * testStudent.setScore(rs.getString("score")); List
				 * searchProperties2 = new ArrayList();
				 * searchProperties2.add(new SearchProperty("student_id",
				 * student .getId(), "=")); List studentScoreList =
				 * this.getStudentScores(null, searchProperties2,
				 * orderProperty);
				 * testStudent.setStudentScoreList(studentScoreList);
				 */
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return studentlist;
	}

	// add by wq
	public List getAllStudents(List searchproperty, List orderproperty) {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = getAllStudentSql(Conditions
					.convertToAndCondition(searchproperty), Conditions
					.convertToCondition(null, orderproperty));
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				// student.setNickname(rs.getString("nickname"));
				student.setPassword(rs.getString("password"));
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setDegree(rs.getString("degree"));
				normalInfo.setEdu(rs.getString("edu"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				if (rs.getString("fax") != null
						&& rs.getString("fax").length() > 0)
					normalInfo.setFax(rs.getString("fax").split(","));
				else
					normalInfo.setFax(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setGraduated_major(rs.getString("graduated_major"));
				normalInfo.setGraduated_sch(rs.getString("graduated_sch"));
				normalInfo.setGraduated_time(rs.getString("graduated_time"));
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("home_address"));
				homeaddress.setPostalcode(rs.getString("home_postalcode"));
				Address workaddress = new Address();
				workaddress.setAddress(rs.getString("work_address"));
				workaddress.setPostalcode(rs.getString("work_postalcode"));
				normalInfo.setHome_address(homeaddress);
				normalInfo.setHometown(rs.getString("hometown"));
				normalInfo.setIdcard(rs.getString("idcard"));
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				normalInfo.setNote(rs.getString("note"));
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setWork_address(workaddress);
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				student.setNormalInfo(normalInfo);
				eduInfo.setClass_id(rs.getString("class_id"));
				// eduInfo.setClass_name(rs.getString("class_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setEntrance_date(rs.getString("entrance_date"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setIsgraduated(rs.getString("isgraduated"));
				eduInfo.setStudy_form(rs.getString("study_form"));
				eduInfo.setStudy_status(rs.getString("study_status"));
				eduInfo.setGraduate_status(rs.getString("graduate_status"));
				eduInfo.setGraduate_no(rs.getString("graduate_no"));
				eduInfo.setGraduate_operate_time(rs
						.getString("graduate_operate_time"));
				eduInfo.setGraduate_operator(rs.getString("graduate_operator"));
				eduInfo.setIsdegreed(rs.getString("isdegreed"));
				eduInfo.setDegree_status(rs.getString("degree_status"));
				eduInfo.setDegree_operate_time(rs
						.getString("degree_operate_time"));
				eduInfo.setDegree_operator(rs.getString("degree_operator"));
				eduInfo.setDegree_score(rs.getString("degree_score"));
				eduInfo.setDegree_release(rs.getString("degree_release"));
				eduInfo.setExpend_score_status(rs
						.getString("expend_score_status"));
				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db = null;

		}
		return studentlist;
	}

	//修改 lwx 
	public List getStudents(Page page, List searchproperty, List orderproperty) {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		MyResultSet recruitinfo = null;
		if (orderproperty == null) {
			orderproperty = new ArrayList();
			orderproperty.add(new OrderProperty("reg_no"));
		}
		try {
			sql = getStudentSql(Conditions
					.convertToCondition(searchproperty,null), Conditions
					.convertToCondition(null, orderproperty));
			rs = db.execute_oracle_page(sql, page.getPageInt(), page
					.getPageSize());
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				String reg_no = rs.getString("reg_no");
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
//				eduInfo.setStudy_status(rs.getString("study_status"));
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(reg_no);
				// student.setNickname(rs.getString("nickname"));
//				student.setPassword(rs.getString("password"));
//				normalInfo.setBirthday(rs.getString("birthday"));
//				normalInfo.setDegree(rs.getString("degree"));
//				normalInfo.setEdu(rs.getString("edu"));
//				normalInfo.setCardType(rs.getString("card_type"));
//				if (rs.getString("email") != null
//						&& rs.getString("email").length() > 0)
//					normalInfo.setEmail(rs.getString("email").split(","));
//				else
//					normalInfo.setEmail(null);
//				if (rs.getString("fax") != null
//						&& rs.getString("fax").length() > 0)
//					normalInfo.setFax(rs.getString("fax").split(","));
//				else
//					normalInfo.setFax(null);
//				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
//				normalInfo.setGraduated_major(rs.getString("graduated_major"));
//				normalInfo.setGraduated_sch(rs.getString("graduated_sch"));
//				normalInfo.setGraduated_time(rs.getString("graduated_time"));
//				Address homeaddress = new Address();
//				homeaddress.setAddress(rs.getString("home_address"));
//				homeaddress.setPostalcode(rs.getString("home_postalcode"));
//				Address workaddress = new Address();
//				workaddress.setAddress(rs.getString("work_address"));
//				workaddress.setPostalcode(rs.getString("work_postalcode"));
//				normalInfo.setHome_address(homeaddress);
//				normalInfo.setHometown(rs.getString("hometown"));
//				normalInfo.setIdcard(rs.getString("idcard"));
//				normalInfo.setCardType(rs.getString("card_type"));
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
//				normalInfo.setNote(rs.getString("note"));
//				if (rs.getString("phone") != null
//						&& rs.getString("phone").length() > 0)
//					normalInfo.setPhone(rs.getString("phone").split(","));
//				else
//					normalInfo.setPhone(null);
//				normalInfo.setPosition(rs.getString("position"));
//				normalInfo.setTitle(rs.getString("title"));
//				normalInfo.setWork_address(workaddress);
//				normalInfo.setWorkplace(rs.getString("workplace"));
//				normalInfo.setZzmm(rs.getString("zzmm"));
//				student.setNormalInfo(normalInfo);
//				eduInfo.setClass_id(rs.getString("class_id"));
				// eduInfo.setClass_name(rs.getString("class_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("eduname"));
//				eduInfo.setEntrance_date(rs.getString("entrance_date"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("gradename"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("majorname"));
//				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("sitename"));
//				eduInfo.setStatus(rs.getString("status"));
//				eduInfo.setIsgraduated(rs.getString("isgraduated"));
//				eduInfo.setStudy_form(rs.getString("study_form"));
//				eduInfo.setStudy_status(rs.getString("study_status"));
//				eduInfo.setGraduate_status(rs.getString("graduate_status"));
//				eduInfo.setGraduate_no(rs.getString("graduate_no"));
//				eduInfo.setGraduate_operate_time(rs
//						.getString("graduate_operate_time"));
//				eduInfo.setGraduate_operator(rs.getString("graduate_operator"));
//				eduInfo.setIsdegreed(rs.getString("isdegreed"));
//				eduInfo.setDegree_status(rs.getString("degree_status"));
//				eduInfo.setDegree_operate_time(rs
//						.getString("degree_operate_time"));
//				eduInfo.setDegree_operator(rs.getString("degree_operator"));
//				eduInfo.setDegree_score(rs.getString("degree_score"));
//				eduInfo.setDegree_release(rs.getString("degree_release"));
//				eduInfo.setExpend_score_status(rs
//						.getString("expend_score_status"));
//				eduInfo.setSchool_name(rs.getString("SCHOOL_NAME"));
//				eduInfo.setSchool_code(rs.getString("SCHOOL_CODE"));
//				eduInfo.setGraduate_year(rs.getString("GRADUATE_YEAR"));
//				eduInfo.setGraduate_cardno(rs.getString("GRADUATE_CARDNO"));
//				if (reg_no != null && !reg_no.equals("")
//						&& !reg_no.equals("null")) {
//					RecruitStudent recruitStu = new OracleRecruitStudent();
//					String recruitsql = "select s.study_no as study_no,s.reg_no,s.testcard_id,case when CONSIDERTYPE='0' or considertype='无' or considertype is null then '无加分项' when considertype='1' then '有加分项' else '免试' end as considertype,CONSIDERTYPE_STATUS,s.batch_id,r.title as batch_name,case when register_status='1' then '已注册' else '未注册' end as register_status from recruit_student_info s,recruit_batch_info r where s.card_no='"
//							+ rs.getString("idcard")
//							+ "' and s.batch_id=r.id(+)";
//					recruitinfo = db.executeQuery(recruitsql);
//					String study_no = "";
//					while (recruitinfo != null && recruitinfo.next()) {
//						study_no = recruitinfo.getString("study_no");
//						recruitStu.setReg_no(recruitinfo.getString("reg_no"));
//						RecruitEduInfo reduInfo = new RecruitEduInfo();
//						reduInfo.setConsidertype_status(recruitinfo
//								.getString("CONSIDERTYPE_STATUS"));
//						reduInfo.setConsiderType(recruitinfo
//								.getString("CONSIDERTYPE"));
//						reduInfo.setRegister_status(recruitinfo
//								.getString("register_status"));
//						RecruitBatch batch = new OracleRecruitBatch();
//						batch.setId(recruitinfo.getString("batch_id"));
//						batch.setTitle(recruitinfo.getString("batch_name"));
//						reduInfo.setBatch(batch);
//						reduInfo.setTestCardId(recruitinfo
//								.getString("testcard_id"));
//						recruitStu.setEduInfo(reduInfo);
//					}
//					db.close(recruitinfo);
//					normalInfo.setStudy_no(study_no);
//					student.setRecruitStudent(recruitStu);
//				} else {
//					normalInfo.setStudy_no("");
//				}
				student.setNormalInfo(normalInfo);
				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db = null;
			return studentlist;
		}

	}
	
	public List getStudentsFee(Page page, List searchproperty, List orderproperty) {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		MyResultSet recruitinfo = null;
		if (orderproperty == null) {
			orderproperty = new ArrayList();
			orderproperty.add(new OrderProperty("reg_no"));
		}
		try {
			sql = getStudentFeeSql(Conditions
					.convertToAndCondition(searchproperty), Conditions
					.convertToCondition(null, orderproperty));
			rs = db.execute_oracle_page(sql, page.getPageInt(), page
					.getPageSize());
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				
				String reg_no = rs.getString("reg_no");
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				eduInfo.setStudy_status(rs.getString("study_status"));
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(reg_no);
				// student.setNickname(rs.getString("nickname"));
				student.setPassword(rs.getString("password"));
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setDegree(rs.getString("degree"));
				normalInfo.setEdu(rs.getString("edu"));
				normalInfo.setCardType(rs.getString("card_type"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				if (rs.getString("fax") != null
						&& rs.getString("fax").length() > 0)
					normalInfo.setFax(rs.getString("fax").split(","));
				else
					normalInfo.setFax(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setGraduated_major(rs.getString("graduated_major"));
				normalInfo.setGraduated_sch(rs.getString("graduated_sch"));
				normalInfo.setGraduated_time(rs.getString("graduated_time"));
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("home_address"));
				homeaddress.setPostalcode(rs.getString("home_postalcode"));
				Address workaddress = new Address();
				workaddress.setAddress(rs.getString("work_address"));
				workaddress.setPostalcode(rs.getString("work_postalcode"));
				normalInfo.setHome_address(homeaddress);
				normalInfo.setHometown(rs.getString("hometown"));
				normalInfo.setIdcard(rs.getString("idcard"));
				normalInfo.setCardType(rs.getString("card_type"));
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				normalInfo.setNote(rs.getString("note"));
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setWork_address(workaddress);
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				student.setNormalInfo(normalInfo);
				eduInfo.setClass_id(rs.getString("class_id"));
				// eduInfo.setClass_name(rs.getString("class_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setEntrance_date(rs.getString("entrance_date"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setIsgraduated(rs.getString("isgraduated"));
				eduInfo.setStudy_form(rs.getString("study_form"));
				eduInfo.setStudy_status(rs.getString("study_status"));
				eduInfo.setGraduate_status(rs.getString("graduate_status"));
				eduInfo.setGraduate_no(rs.getString("graduate_no"));
				eduInfo.setGraduate_operate_time(rs
						.getString("graduate_operate_time"));
				eduInfo.setGraduate_operator(rs.getString("graduate_operator"));
				eduInfo.setIsdegreed(rs.getString("isdegreed"));
				eduInfo.setDegree_status(rs.getString("degree_status"));
				eduInfo.setDegree_operate_time(rs
						.getString("degree_operate_time"));
				eduInfo.setDegree_operator(rs.getString("degree_operator"));
				eduInfo.setDegree_score(rs.getString("degree_score"));
				eduInfo.setDegree_release(rs.getString("degree_release"));
				eduInfo.setExpend_score_status(rs
						.getString("expend_score_status"));
				eduInfo.setSchool_name(rs.getString("SCHOOL_NAME"));
				eduInfo.setSchool_code(rs.getString("SCHOOL_CODE"));
				eduInfo.setGraduate_year(rs.getString("GRADUATE_YEAR"));
				eduInfo.setGraduate_cardno(rs.getString("GRADUATE_CARDNO"));
				if (reg_no != null && !reg_no.equals("")
						&& !reg_no.equals("null")) {
					RecruitStudent recruitStu = new OracleRecruitStudent();
					String recruitsql = "select s.study_no as study_no,s.reg_no,s.testcard_id,case when CONSIDERTYPE='0' or considertype='无' or considertype is null then '无加分项' when considertype='1' then '有加分项' else '免试' end as considertype,CONSIDERTYPE_STATUS,s.batch_id,r.title as batch_name,case when register_status='1' then '已注册' else '未注册' end as register_status from recruit_student_info s,recruit_batch_info r where s.card_no='"
							+ rs.getString("idcard")
							+ "' and s.batch_id=r.id(+)";
					recruitinfo = db.executeQuery(recruitsql);
					String study_no = "";
					while (recruitinfo != null && recruitinfo.next()) {
						study_no = recruitinfo.getString("study_no");
						recruitStu.setReg_no(recruitinfo.getString("reg_no"));
						RecruitEduInfo reduInfo = new RecruitEduInfo();
						reduInfo.setConsidertype_status(recruitinfo
								.getString("CONSIDERTYPE_STATUS"));
						reduInfo.setConsiderType(recruitinfo
								.getString("CONSIDERTYPE"));
						reduInfo.setRegister_status(recruitinfo
								.getString("register_status"));
						RecruitBatch batch = new OracleRecruitBatch();
						batch.setId(recruitinfo.getString("batch_id"));
						batch.setTitle(recruitinfo.getString("batch_name"));
						reduInfo.setBatch(batch);
						reduInfo.setTestCardId(recruitinfo
								.getString("testcard_id"));
						recruitStu.setEduInfo(reduInfo);
					}
					db.close(recruitinfo);
					normalInfo.setStudy_no(study_no);
					student.setRecruitStudent(recruitStu);
				} else {
					normalInfo.setStudy_no("");
				}
				RedundanceData redundanceData = new RedundanceData();
//				暂时当临时参数,用于存放已交学费
				redundanceData.setRe1(rs.getString("fee_value"));
				//暂时当临时参数，用于存放学费标准
				redundanceData.setRe2(rs.getString("type3_value"));
				student.setRedundace(redundanceData);
				
				student.setNormalInfo(normalInfo);
				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			
			Log.setError("StudentList.getStudentsFee() error! " + sql);
		} finally {
			db.close(rs);
			db = null;
			return studentlist;
		}

	}
	public int getStudentsFeeNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = getStudentFeeSql(Conditions
				.convertToAndCondition(searchproperty), null);
		int i = db.countselect(sql);
		return i;
	}
	// add by wq
	public int getAllStudentsNum(List searchproperty) {
		dbpool db = new dbpool();
		String sql = getAllStudentSql(Conditions
				.convertToAndCondition(searchproperty), null);
		int i = db.countselect(sql);
		return i;
	}

	public List getAllStudents(Page page, List searchproperty,
			List orderproperty) {
		ArrayList studentlist = new ArrayList();
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		try {
			sql = getAllStudentSql(Conditions
					.convertToAndCondition(searchproperty), Conditions
					.convertToCondition(null, orderproperty));
			rs = db.execute_oracle_page(sql, page.getPageInt(), page
					.getPageSize());
			while (rs != null && rs.next()) {
				OracleStudent student = new OracleStudent();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				// student.setNickname(rs.getString("nickname"));
				student.setPassword(rs.getString("password"));
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setDegree(rs.getString("degree"));
				normalInfo.setEdu(rs.getString("edu"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				if (rs.getString("fax") != null
						&& rs.getString("fax").length() > 0)
					normalInfo.setFax(rs.getString("fax").split(","));
				else
					normalInfo.setFax(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setGraduated_major(rs.getString("graduated_major"));
				normalInfo.setGraduated_sch(rs.getString("graduated_sch"));
				normalInfo.setGraduated_time(rs.getString("graduated_time"));
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("home_address"));
				homeaddress.setPostalcode(rs.getString("home_postalcode"));
				Address workaddress = new Address();
				workaddress.setAddress(rs.getString("work_address"));
				workaddress.setPostalcode(rs.getString("work_postalcode"));
				normalInfo.setHome_address(homeaddress);
				normalInfo.setHometown(rs.getString("hometown"));
				normalInfo.setIdcard(rs.getString("idcard"));
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				normalInfo.setNote(rs.getString("note"));
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setWork_address(workaddress);
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				student.setNormalInfo(normalInfo);
				eduInfo.setClass_id(rs.getString("class_id"));
				// eduInfo.setClass_name(rs.getString("class_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setEntrance_date(rs.getString("entrance_date"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setIsgraduated(rs.getString("isgraduated"));
				eduInfo.setStudy_form(rs.getString("study_form"));
				eduInfo.setStudy_status(rs.getString("study_status"));
				eduInfo.setGraduate_status(rs.getString("graduate_status"));
				eduInfo.setGraduate_no(rs.getString("graduate_no"));
				eduInfo.setGraduate_operate_time(rs
						.getString("graduate_operate_time"));
				eduInfo.setGraduate_operator(rs.getString("graduate_operator"));
				eduInfo.setIsdegreed(rs.getString("isdegreed"));
				eduInfo.setDegree_status(rs.getString("degree_status"));
				eduInfo.setDegree_operate_time(rs
						.getString("degree_operate_time"));
				eduInfo.setDegree_operator(rs.getString("degree_operator"));
				eduInfo.setDegree_score(rs.getString("degree_score"));
				eduInfo.setDegree_release(rs.getString("degree_release"));
				student.setStudentInfo(eduInfo);
				studentlist.add(student);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("StudentList.getStudents() error! " + sql);
		} finally {
			db.close(rs);
			db = null;
			return studentlist;
		}

	}

	public int getManagersNum(List searchproperty) {
		dbpool db = new dbpool();
		int i = db.countselect(SQLMANAGER
				+ Conditions.convertToCondition(searchproperty, null));
		return i;
	}

	public List getManagers(Page page, List searchproperty, List orderproperty) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		ArrayList managerlist = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = SQLMANAGER + condition;
			rs = db.execute_oracle_page(sql, page.getPageInt(), page
					.getPageSize());
			while (rs != null && rs.next()) {
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				OracleManager oraclemanager = new OracleManager();
				redundance.setRe1(rs.getString("gender"));
				oraclemanager.setId(rs.getString("id"));
				oraclemanager.setName(rs.getString("name"));
				oraclemanager.setLoginId(rs.getString("login_id"));
//				oraclemanager.setNickname(rs.getString("nickname"));
				// oraclemanager.setPassword(rs.getString("password"));
				// oraclemanager.setEmail(rs.getString("email"));
//				oraclemanager.setSite_id(rs.getString("site_id"));
//				oraclemanager.setGroup_id(rs.getString("group_id"));
//				platformInfo.setLastlogin_ip(rs.getString("last_login_ip"));
//				platformInfo.setLastlogin_time(rs.getString("last_login_time"));
//				oraclemanager.setStatus(rs.getString("status"));
//				oraclemanager.setLogin_num(rs.getInt("login_num"));
				oraclemanager.setMobilePhone(rs.getString("mobilephone"));
				oraclemanager.setPlatformInfo(platformInfo);
				oraclemanager.setRedundace(redundance);
				managerlist.add(oraclemanager);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("BasicUserList.getManagers() error:" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return managerlist;
	}

	public int getSiteManagersNum(List searchproperty) {
		dbpool db = new dbpool();
		int i = db.countselect(SQLSITEMANAGER
				+ Conditions.convertToCondition(searchproperty, null));
		return i;
	}

	public List getSiteManagers(Page page, List searchproperty,
			List orderproperty) {
		String condition = Conditions.convertToCondition(searchproperty,
				orderproperty);
		ArrayList sitemanagerlist = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = SQLSITEMANAGER + condition;
			rs = db.execute_oracle_page(sql, page.getPageInt(), page
					.getPageSize());
			while (rs != null && rs.next()) {
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				OracleSiteManager oraclesitemanager = new OracleSiteManager();
				RedundanceData redundance = new RedundanceData();
				redundance.setRe1(rs.getString("gender"));
				oraclesitemanager.setId(rs.getString("id"));
				oraclesitemanager.setLoginId(rs.getString("login_id"));
				oraclesitemanager.setName(rs.getString("name"));
				oraclesitemanager.setSite_id(rs.getString("site_id"));
				oraclesitemanager.setSite_name(rs.getString("site_name"));
//				platformInfo.setLastlogin_ip(rs.getString("last_login_ip"));
//				platformInfo.setLastlogin_time(rs.getString("last_login_time"));
//				oraclesitemanager.setStatus(rs.getString("status"));
//				oraclesitemanager.setLogin_num(rs.getInt("login_num"));
				oraclesitemanager.setMobilePhone(rs.getString("mobilephone"));
				oraclesitemanager.setPlatformInfo(platformInfo);
				oraclesitemanager.setRedundace(redundance);
				sitemanagerlist.add(oraclesitemanager);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("BasicUserList.getSiteManagers() error:" + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return sitemanagerlist;
	}

	public HashMap getStudentStatByEduType(String siteId, String gradeId)
			throws PlatformException {
		dbpool db = new dbpool();
		HashMap nums = new HashMap();
		MyResultSet rs_edutype = null;
		MyResultSet rs_major = null;
		MyResultSet rs = null;
		String sql_edutype = "";
		String sql_major = "";
		String sql = "";
		int count_edutype = 0;
		int count_major = 0;
		String edutype_id_str = "";
		if (siteId.equals("")) {
			if (gradeId.equals(""))
				sql_edutype = "select distinct b.id,b.name from entity_student_info a,entity_edu_type b where a.edutype_id=b.id order by id";
			else
				sql_edutype = "select distinct b.id,b.name from entity_student_info a,entity_edu_type b where a.edutype_id=b.id and a.grade_id='"
						+ gradeId + "' order by id";
		} else {
			if (gradeId.equals(""))
				sql_edutype = "select distinct b.id,b.name from entity_student_info a,entity_edu_type b where a.edutype_id=b.id and a.site_id='"
						+ siteId + "' order by id";
			else
				sql_edutype = "select distinct b.id,b.name from entity_student_info a,entity_edu_type b where a.edutype_id=b.id and a.site_id='"
						+ siteId
						+ "' and a.grade_id='"
						+ gradeId
						+ "' order by id";
		}
		rs_edutype = db.executeQuery(sql_edutype);
		try {
			String[] num = new String[db.countselect(sql_edutype) + 5];
			num[count_edutype] = "专业代号";
			num[++count_edutype] = "专业名称";
			while (rs_edutype != null && rs_edutype.next()) {
				count_edutype++;
				num[count_edutype] = rs_edutype.getString("name");
				edutype_id_str = edutype_id_str + ",'"
						+ rs_edutype.getString("id") + "'";
			}
			if (count_edutype > 1)
				edutype_id_str = edutype_id_str.substring(1);
			num[++count_edutype] = "专业人数";
			num[++count_edutype] = "退学人数";
			num[++count_edutype] = "毕业人数";
			nums.put("title", num);
		} catch (SQLException e) {
		}
		db.close(rs_edutype);

		if (siteId.equals("")) {
			if (gradeId.equals(""))
				sql_major = "select distinct b.id,b.name from entity_student_info a,entity_major_info b where a.major_id=b.id order by to_number(b.id)";
			else
				sql_major = "select distinct b.id,b.name from entity_student_info a,entity_major_info b where a.major_id=b.id and a.grade_id='"
						+ gradeId + "' order by to_number(b.id)";
		} else {
			if (gradeId.equals(""))
				sql_major = "select distinct b.id,b.name from entity_student_info a,entity_major_info b where a.major_id=b.id and a.site_id='"
						+ siteId + "' order by to_number(b.id)";
			else
				sql_major = "select distinct b.id,b.name from entity_student_info a,entity_major_info b where a.major_id=b.id and a.site_id='"
						+ siteId
						+ "' and a.grade_id='"
						+ gradeId
						+ "' order by to_number(b.id)";
		}
		String[][] major = new String[db.countselect(sql_major)][2];
		rs_major = db.executeQuery(sql_major);
		try {
			while (rs_major != null && rs_major.next()) {
				major[count_major][0] = rs_major.getString("id");
				major[count_major][1] = rs_major.getString("name");
				count_major++;
			}
		} catch (SQLException e) {
		}
		db.close(rs_major);
		for (int i = 0; i < count_major; i++) {
			int count = 0;
			String[] num = new String[count_edutype + 1];
			num[count] = major[i][0];
			num[++count] = major[i][1];
			if (siteId.equals("")) {
				if (gradeId.equals(""))
					sql = "select nvl(num,0) as num,b.id as edutype_id from(select count(id) as num,edutype_id from entity_student_info where major_id='"
							+ major[i][0]
							+ "' and isgraduated='0' and status='0' group by edutype_id) a,entity_edu_type b where b.id in("
							+ edutype_id_str
							+ ") and b.id=a.edutype_id(+) order by edutype_id";
				else
					sql = "select nvl(num,0) as num,b.id as edutype_id from(select count(id) as num,edutype_id from entity_student_info where grade_id='"
							+ gradeId
							+ "' and major_id='"
							+ major[i][0]
							+ "' and isgraduated='0' and status='0' group by edutype_id) a,entity_edu_type b where b.id in("
							+ edutype_id_str
							+ ") and b.id=a.edutype_id(+) order by edutype_id";
			} else {
				if (gradeId.equals(""))
					sql = "select nvl(num,0) as num,b.id as edutype_id from(select count(id) as num,edutype_id from entity_student_info where site_id='"
							+ siteId
							+ "' and major_id='"
							+ major[i][0]
							+ "' and isgraduated='0' and status='0' group by edutype_id) a,entity_edu_type b where b.id in("
							+ edutype_id_str
							+ ") and b.id=a.edutype_id(+) order by edutype_id";
				else
					sql = "select nvl(num,0) as num,b.id as edutype_id from(select count(id) as num,edutype_id from entity_student_info where grade_id='"
							+ gradeId
							+ "' and site_id='"
							+ siteId
							+ "' and major_id='"
							+ major[i][0]
							+ "' and isgraduated='0' and status='0' group by edutype_id) a,entity_edu_type b where b.id in("
							+ edutype_id_str
							+ ") and b.id=a.edutype_id(+) order by edutype_id";
			}
			rs = db.executeQuery(sql);
			try {
				while (rs != null && rs.next()) {
					count++;
					num[count] = rs.getString("num");
				}
			} catch (SQLException e) {
			}
			db.close(rs);
			if (siteId.equals("")) {
				if (gradeId.equals(""))
					sql = "select a.num,b.num as abort_num,c.num as graduate_num from (select count(id) as num from entity_student_info where major_id='"
							+ major[i][0]
							+ "' and isgraduated='0' and status='0') a,(select count(id) as num from entity_student_info where major_id='"
							+ major[i][0]
							+ "' and status='1111') b,(select count(id) as num from entity_student_info where major_id='"
							+ major[i][0] + "' and isgraduated='1') c";
				else
					sql = "select a.num,b.num as abort_num,c.num as graduate_num from (select count(id) as num from entity_student_info where grade_id='"
							+ gradeId
							+ "' and major_id='"
							+ major[i][0]
							+ "' and isgraduated='0' and status='0') a,(select count(id) as num from entity_student_info where grade_id='"
							+ gradeId
							+ "' and major_id='"
							+ major[i][0]
							+ "' and status='1111') b,(select count(id) as num from entity_student_info where grade_id='"
							+ gradeId
							+ "' and major_id='"
							+ major[i][0]
							+ "' and isgraduated='1') c";
			} else {
				if (gradeId.equals(""))
					sql = "select a.num,b.num as abort_num,c.num as graduate_num from (select count(id) as num from entity_student_info where site_id='"
							+ siteId
							+ "' and major_id='"
							+ major[i][0]
							+ "' and isgraduated='0' and status='0') a,(select count(id) as num from entity_student_info where site_id='"
							+ siteId
							+ "' and major_id='"
							+ major[i][0]
							+ "' and status='1111') b,(select count(id) as num from entity_student_info where site_id='"
							+ siteId
							+ "' and major_id='"
							+ major[i][0]
							+ "' and isgraduated='1') c";
				else
					sql = "select a.num,b.num as abort_num,c.num as graduate_num from (select count(id) as num from entity_student_info where grade_id='"
							+ gradeId
							+ "' and site_id='"
							+ siteId
							+ "' and major_id='"
							+ major[i][0]
							+ "' and isgraduated='0' and status='0') a,(select count(id) as num from entity_student_info where grade_id='"
							+ gradeId
							+ "' and site_id='"
							+ siteId
							+ "' and major_id='"
							+ major[i][0]
							+ "' and status='1111') b,(select count(id) as num from entity_student_info where grade_id='"
							+ gradeId
							+ "' and site_id='"
							+ siteId
							+ "' and major_id='"
							+ major[i][0]
							+ "' and isgraduated='1') c";
			}
			rs = db.executeQuery(sql);
			try {
				while (rs != null && rs.next()) {
					num[++count] = rs.getString("num");
					num[++count] = rs.getString("abort_num");
					num[++count] = rs.getString("graduate_num");
				}
			} catch (SQLException e) {
			}
			db.close(rs);
			nums.put(Integer.toString(i), num);
		}
		return nums;
	}

	public HashMap getStudentStatByGrade(String siteId, String gradeId)
			throws PlatformException {
		dbpool db = new dbpool();
		HashMap nums = new HashMap();
		MyResultSet rs = null;
		String sql = "";
		int count = 0;
		if (siteId.equals(""))
			sql = "select major_name,nvl(a.num,0) as num,nvl(b.num,0) as abort_num,nvl(c.num,0) as graduate_num from (select count(id) as num,major_id from entity_student_info where grade_id='"
					+ gradeId
					+ "' and isgraduated='0' and status='0' group by major_id) a,(select count(id) as num,major_id from entity_student_info where grade_id='"
					+ gradeId
					+ "' and status='1111' group by major_id) b,(select count(id) as num,major_id from entity_student_info where grade_id='"
					+ gradeId
					+ "' and isgraduated='1' group by major_id) c,(select distinct major_id,b.name as major_name from entity_student_info a,entity_major_info b where grade_id='"
					+ gradeId
					+ "' and a.major_id=b.id) d where d.major_id=a.major_id(+) and d.major_id=b.major_id(+) and d.major_id=c.major_id(+) order by d.major_id";
		else
			sql = "select major_name,nvl(a.num,0) as num,nvl(b.num,0) as abort_num,nvl(c.num,0) as graduate_num from (select count(id) as num,major_id from entity_student_info where site_id='"
					+ siteId
					+ "' and grade_id='"
					+ gradeId
					+ "' and isgraduated='0' and status='0' group by major_id) a,(select count(id) as num,major_id from entity_student_info where site_id='"
					+ siteId
					+ "' and grade_id='"
					+ gradeId
					+ "' and status='1111' group by major_id) b,(select count(id) as num,major_id from entity_student_info where site_id='"
					+ siteId
					+ "' and grade_id='"
					+ gradeId
					+ "' and isgraduated='1' group by major_id) c,(select distinct major_id,b.name as major_name from entity_student_info a,entity_major_info b where site_id='"
					+ siteId
					+ "' and grade_id='"
					+ gradeId
					+ "' and a.major_id=b.id) d where d.major_id=a.major_id(+) and d.major_id=b.major_id(+) and d.major_id=c.major_id(+) order by d.major_id";
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				String[] num = new String[4];
				num[0] = rs.getString("major_name");
				num[1] = rs.getString("num");
				num[2] = rs.getString("abort_num");
				num[3] = rs.getString("graduate_num");
				nums.put(Integer.toString(count++), num);
			}
		} catch (SQLException e) {
		}
		db.close(rs);
		return nums;
	}

	public List getAssistanceTeachers(Page page, String teachclass_id) {
		ArrayList teacherlist = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = SQLTEACHER
					+ " where id in (select teacher_id from entity_teacher_course where teachclass_id='"
					+ teachclass_id + "' and teach_level='助教')";
			if (page == null)
				rs = db.executeQuery(sql);
			else
				rs = db.execute_oracle_page(sql, page.getPageInt(), page
						.getPageSize());
			while (rs != null && rs.next()) {
				OracleTeacher oracleteacher = new OracleTeacher();
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				TeacherEduInfo eduInfo = new TeacherEduInfo();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				oracleteacher.setId(rs.getString("id"));
				oracleteacher.setName(rs.getString("name"));
				// this.setNickname(rs.getString("nickname"));
				oracleteacher.setPassword(rs.getString("password"));
				oracleteacher.setEmail(rs.getString("email"));
				eduInfo.setGh(rs.getString("gh"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setTeach_level(rs.getString("teach_level"));
				eduInfo.setTeach_time(rs.getString("teach_time"));
				eduInfo.setWork_kind(rs.getString("work_kind"));
				normalInfo.setNote(rs.getString("note"));
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setGender(rs.getString("gender"));
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(new String[0]);
				Address home_address = new Address();
				Address work_address = new Address();
				work_address.setAddress(rs.getString("address"));
				work_address.setPostalcode(rs.getString("zip_code"));
				normalInfo.setWork_address(work_address);
				platformInfo.setLastlogin_ip(rs.getString("last_login_ip"));
				platformInfo.setLastlogin_time(rs.getString("last_login_time"));
				redundance.setRe1(rs.getString("re1"));
				redundance.setRe2(rs.getString("re2"));
				redundance.setRe3(rs.getString("re3"));
				redundance.setRe4(rs.getString("re4"));
				redundance.setRe5(rs.getString("re5"));
				redundance.setRe6(rs.getString("re6"));
				redundance.setRe7(rs.getString("re7"));
				redundance.setRe8(rs.getString("re8"));
				redundance.setRe9(rs.getString("re9"));
				oracleteacher.setNormalInfo(normalInfo);
				oracleteacher.setTeacherInfo(eduInfo);
				oracleteacher.setPlatformInfo(platformInfo);
				oracleteacher.setRedundace(redundance);

				teacherlist.add(oracleteacher);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("TeacherList.getTeachers() error" + SQLTEACHER);
		} finally {
			db.close(rs);
			db = null;
		}
		// System.out.println("teacherlist.size()=" + teacherlist.size());
		return teacherlist;
	}

	public int getAssistanceTeachersNum(String teachclass_id) {
		ArrayList teacherlist = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = SQLTEACHER
				+ " where id in (select teacher_id from entity_teacher_course where teachclass_id='"
				+ teachclass_id + "' and teach_level='助教')";
		return db.countselect(sql);
	}

	public void confirmSiteTeachers(String[] selectedIds,String[] notes)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql2 = "";
		List sqlList = new ArrayList();
		if (selectedIds != null && selectedIds.length > 0) {
			for (int i = 0; i < selectedIds.length; i++) {
				sql2 = "update entity_siteteacher_info set isconfirm='1',confirm_note='" + notes[i] +"' where id = '" + selectedIds[i] + "'";
				sqlList.add(sql2);
				UserUpdateLog.setDebug("OracleBasicUserList.confirmSiteTeachers(String[] selectedIds) SQL=" + sql2 + " DATE=" + new Date());
			}
		}
		db.executeUpdateBatch(sqlList);
	}

	public void confirmSiteTeachers(String[] selectedIds)
		throws PlatformException {
		dbpool db = new dbpool();
		String sql2 = "";
		List sqlList = new ArrayList();
		if (selectedIds != null && selectedIds.length > 0) {
			for (int i = 0; i < selectedIds.length; i++) {
				sql2 = "update entity_siteteacher_info set isconfirm='1' where id = '" + selectedIds[i] + "'";
				sqlList.add(sql2);
				UserUpdateLog.setDebug("OracleBasicUserList.confirmSiteTeachers(String[] selectedIds) SQL=" + sql2 + " DATE=" + new Date());
			}
		}
		db.executeUpdateBatch(sqlList);
	}

	
	public void deleteSiteTeachers(String[] selectedIds)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql2 = "";
		String sql1 = "";
		String sql3 = "";
		ArrayList sqlList = new ArrayList();
		if (selectedIds != null && selectedIds.length > 0) {
			for (int i = 0; i < selectedIds.length; i++) {
				sql2 = "delete from entity_siteteacher_info where id ='"
						+ selectedIds[i] + "'";
				sql1 = "delete from sso_user where id = '" + selectedIds[i]
						+ "'";
				sql3 = "delete from entity_siteteacher_course where teacher_id = '"
						+ selectedIds[i] + "'";
				sqlList.add(sql1);
				sqlList.add(sql2);
				sqlList.add(sql3);
				UserUpdateLog
						.setDebug("OracleBasicUserList.deleteSiteTeachers(String[] selectedIds) SQL1="
								+ sql1
								+ " SQL2="
								+ sql2
								+ " SQL3="
								+ sql3
								+ " DATE=" + new Date());
			}
		}
		db.executeUpdateBatch(sqlList);
	}

	public void rejectSiteTeachers(String[] selectedIds, String[] notes)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql2 = "";
		ArrayList sqlList = new ArrayList();
		if (selectedIds != null && selectedIds.length > 0) {
			for (int i = 0; i < selectedIds.length; i++) {
				sql2 = "update entity_siteteacher_info set isconfirm='2',confirm_note = '"
						+ notes[i] + "' where id ='" + selectedIds[i] + "'";
				sqlList.add(sql2);
				UserUpdateLog
						.setDebug("OracleBasicUserList.rejectSiteTeachers(String[] selectedIds,String[] notes) SQL="
								+ sql2 + " DATE=" + new Date());
			}
		}
		db.executeUpdateBatch(sqlList);
	}

	public List getRegStudentIds(String semesterId) throws PlatformException {
		ArrayList list = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select distinct(user_id) from entity_register_info where semester_id = '"
					+ semesterId + "'";
			Log.setDebug("getRegStudentIds(String semesterId): " + sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				list.add(rs.getString("user_id"));
			}
		} catch (java.sql.SQLException e) {
			Log.setError("getRegStudentIds(String semesterId) error: " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public List getRegStat() throws PlatformException {
		ArrayList list = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		try {
			sql = "select count(a.user_id) as num, b.name from entity_register_info a,"
					+ " entity_semester_info b where a.semester_id = b.id group by b.name order by b.name";
			Log.setDebug("getRegStat: " + sql);
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				List semester = new ArrayList();
				semester.add(rs.getString("name"));
				semester.add(rs.getString("num"));
				list.add(semester);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("getRegStat error: " + sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public Student getStudentByRegNo(String regNo) {
		OracleStudent student = new OracleStudent();
		dbpool dbStu = new dbpool();
		String sql = "select so.password,s.name as name,u.birthday,u.degree,u.edu,u.email,u.fax,u.folk,u.gender,u.graduated_major,"
				+ "u.graduated_sch,u.graduated_time,u.work_address,u.work_postalcode,u.home_address,u.home_postalcode,"
				+ "u.hometown,u.idcard,u.card_type,u.mobilephone,u.phone,u.note,u.position,u.title,u.workplace,u.zzmm,s.class_id,"
				+ "s.edutype_id,s.grade_id ,s.site_id,s.major_id,s.study_form,s.study_status,s.status,s.entrance_date,s.isgraduated,"
				+ "s.photo_link,s.id,s.reg_no,s.login_num,s.last_login_time,s.last_login_ip,"
				+ "c.name as class_name,g.name as grade_name,si.name as site_name,m.name as major_name,e.name as edutype_name "
				+ "from sso_user so,entity_student_info s,entity_usernormal_info u,entity_site_info si,entity_grade_info g,entity_major_info m,"
				+ "entity_edu_type e,entity_class_info c where so.id = s.id and s.reg_no = '"
				+ regNo
				+ "' and s.id=u.id(+) and s.grade_id = g.id(+) and "
				+ "s.major_id = m.id(+) and s.site_id = si.id(+) and s.edutype_id = e.id(+) and s.class_id = c.id(+)";
		EntityLog.setDebug("OracleStudent: " + sql);
		MyResultSet rs = dbStu.executeQuery(sql);
		try {
			if (rs != null && rs.next()) {
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				StudentEduInfo eduInfo = new StudentEduInfo();
				HumanPlatformInfo platformInfo = new HumanPlatformInfo();
				RedundanceData redundance = new RedundanceData();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setLoginId(rs.getString("reg_no"));
				// this.setNickname(rs.getString("nickname"));
				student.setPassword(rs.getString("password"));
				normalInfo.setBirthday(rs.getString("birthday"));
				normalInfo.setDegree(rs.getString("degree"));
				normalInfo.setEdu(rs.getString("edu"));
				if (rs.getString("email") != null
						&& rs.getString("email").length() > 0)
					normalInfo.setEmail(rs.getString("email").split(","));
				else
					normalInfo.setEmail(null);
				if (rs.getString("fax") != null
						&& rs.getString("fax").length() > 0)
					normalInfo.setFax(rs.getString("fax").split(","));
				else
					normalInfo.setFax(null);
				normalInfo.setFolk(rs.getString("folk"));
				normalInfo.setGender(rs.getString("gender"));
				normalInfo.setGraduated_major(rs.getString("graduated_major"));
				normalInfo.setGraduated_sch(rs.getString("graduated_sch"));
				normalInfo.setGraduated_time(rs.getString("graduated_time"));
				Address homeaddress = new Address();
				homeaddress.setAddress(rs.getString("home_address"));
				homeaddress.setPostalcode(rs.getString("home_postalcode"));
				Address workaddress = new Address();
				workaddress.setAddress(rs.getString("work_address"));
				workaddress.setPostalcode(rs.getString("work_postalcode"));
				normalInfo.setHome_address(homeaddress);
				normalInfo.setHometown(rs.getString("hometown"));
				normalInfo.setIdcard(rs.getString("idcard"));
				normalInfo.setCardType(rs.getString("card_type"));
				if (rs.getString("mobilephone") != null
						&& rs.getString("mobilephone").length() > 0)
					normalInfo.setMobilePhone(rs.getString("mobilephone")
							.split(","));
				else
					normalInfo.setMobilePhone(null);
				normalInfo.setNote(rs.getString("note"));
				if (rs.getString("phone") != null
						&& rs.getString("phone").length() > 0)
					normalInfo.setPhone(rs.getString("phone").split(","));
				else
					normalInfo.setPhone(null);
				normalInfo.setPosition(rs.getString("position"));
				normalInfo.setTitle(rs.getString("title"));
				normalInfo.setWork_address(workaddress);
				normalInfo.setWorkplace(rs.getString("workplace"));
				normalInfo.setZzmm(rs.getString("zzmm"));
				student.setNormalInfo(normalInfo);
				eduInfo.setClass_id(rs.getString("class_id"));
				eduInfo.setClass_name(rs.getString("class_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setEntrance_date(rs.getString("entrance_date"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setIsgraduated(rs.getString("isgraduated"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setStudy_form(rs.getString("study_form"));
				eduInfo.setStudy_status(rs.getString("study_status"));
				student.setStudentInfo(eduInfo);
				platformInfo.setLastlogin_ip(rs.getString("last_login_ip"));
				platformInfo.setLastlogin_time(rs.getString("last_login_time"));
				student.setPlatformInfo(platformInfo);
				student.setRedundace(redundance);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleStudent(String aid) error" + sql);
		} finally {
			dbStu.close(rs);
			dbStu = null;
		}

		return student;
	}

	public boolean isNewStudent(String ssoId) {
		boolean result = true;
		dbpool dbStu = new dbpool();
		String sql = "select id,reg_no,name from entity_student_info where id='"
				+ ssoId + "'";
		if (dbStu.countselect(sql) > 0)
			result = false;

		return result;
	}

	public String getStudentList(String teacherId) {
		ArrayList list = new ArrayList();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		String args = "";
		try {
			sql = "select id,siteteacher_id,student_id from entity_graduate_siteteachlimit where siteteacher_id = '"
					+ teacherId + "'";
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				String studentId = rs.getString("student_id");
				args += studentId + ",";
			}
			if (args.length() > 0 && args.lastIndexOf(",") != -1) {
				args = args.substring(0, args.lastIndexOf(","));
			} else {
				args = "''";
			}
		} catch (java.sql.SQLException e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return args;
	}

	public Map getTeacherStat(Page page) {

		dbpool db = new dbpool();
		MyResultSet depRs = null;
		MyResultSet rs = null;
		MyResultSet rss = null;
		
		Map map = new HashMap();
		try {
			String sql = "select id ,name from entity_dep_info ";
			depRs = db.executeQuery(sql);
			while (depRs != null && depRs.next()) {
				String id = depRs.getString("id");
				String name = depRs.getString("name");

				String xlSql = "select t.id as dep_id,t.name as dep_name,d.title as title,d.num_01,c.total_01 ,Round(d.num_01/c.total_01,2)*100 as point from entity_dep_info t,   (select dep_id, count(*) as total_01 from entity_teacher_info group by dep_id) c left join (select dep_id, title, count(*) as num_01 from entity_teacher_info  group by dep_id, title) d on c.dep_id = d.dep_id where  t.id = c.dep_id ";
				String zcSql = "select t.id as dep_id,t.name as dep_name,b.position as position ,b.num,a.total,Round(b.num/a.total,2)*100 as point from entity_dep_info t, (select dep_id, count(*) as total from entity_teacher_info group by dep_id) a left join (select dep_id, position, count(*) as num from entity_teacher_info group by dep_id, position) b on a.dep_id = b.dep_id where  t.id = a.dep_id ";

				xlSql = xlSql + "	and t.id = '" + id + "' order by t.id asc";
				zcSql = zcSql + "	and t.id = '" + id + "'	order by t.id asc";
				
				List list = new ArrayList();
				Map hashMapNum = new HashMap();
				Map hashPoint = new HashMap();
				Map depMap = new HashMap();

				depMap.put("1", id);
				depMap.put("2", name);

				rs = db.executeQuery(zcSql);
				int cont = 0;
				int contXL = 0;
				while (rs != null && rs.next()) {
					String zcName = rs.getString("position");
					String zcNum = rs.getString("num");
					String zcTotal = rs.getString("total");
					String zcPoint = rs.getString("point");
					
					

					if (zcName != null) {
						if (zcName.trim().equals("博导")) {
							hashMapNum.put("1", zcNum);
							hashPoint.put("1", zcPoint);
							cont++;
						} else if (zcName.trim().equals("教授")) {
							hashMapNum.put("2", zcNum);
							hashPoint.put("2", zcPoint);
							cont++;
						} else if (zcName.trim().equals("副教授")) {
							hashMapNum.put("3", zcNum);
							hashPoint.put("3", zcPoint);
							cont++;
						} else if (zcName.trim().equals("讲师")) {
							hashMapNum.put("4", zcNum);
							hashPoint.put("4", zcPoint);
							cont++;
						} 
						
							
					}
					hashMapNum
					.put("5", String.valueOf(Integer.valueOf(zcTotal) - cont));
			hashPoint.put("5",
					String.valueOf((Integer.valueOf(zcTotal) - cont)*100/Integer.valueOf(zcTotal)));
		
					hashMapNum.put("6", zcTotal);
					hashPoint.put("6", "100%");
				}
				db.close(rs);

				rss = db.executeQuery(xlSql);
				while (rss != null && rss.next()) {
					String xlName = rss.getString("title");
					String xlNum = rss.getString("num_01");
					String xlTotal = rss.getString("total_01");
					String xlPoint = rss.getString("point");

				
					
					if (xlName != null) {
						if (xlName.trim().equals("博士")) {
							hashMapNum.put("7", xlNum);
							hashPoint.put("7", xlPoint);
							contXL ++;
						} else if (xlName.trim().equals("硕士")) {
							hashMapNum.put("8", xlNum);
							hashPoint.put("8", xlPoint);
							contXL ++;
						} else if (xlName.trim().equals("学士")) {
							hashMapNum.put("9", xlNum);
							hashPoint.put("9", xlPoint);
							contXL ++;
						} 
					}
					hashMapNum.put("10", String.valueOf(Integer.valueOf(xlTotal)- contXL));
					hashPoint.put("10",
							String.valueOf((Integer.valueOf(xlTotal) - contXL)*100
									/ Integer.valueOf(xlTotal)));
				
					hashMapNum.put("11", xlTotal);
					hashPoint.put("11", "100%");
				}
				 db.close(rss);
				list.add(depMap);
				list.add(hashMapNum);
				list.add(hashPoint);
				
				map.put(id,list);
			}

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.close(depRs);
		}
		return map;
	}

}

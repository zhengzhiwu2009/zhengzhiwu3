package com.whaty.platform.database.oracle.standard.entity.recruit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleGrade;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleSite;
import com.whaty.platform.entity.basic.Site;
import com.whaty.platform.entity.recruit.BasicRecruitList;
import com.whaty.platform.entity.recruit.RecruitBatch;
import com.whaty.platform.entity.recruit.RecruitEduInfo;
import com.whaty.platform.entity.recruit.RecruitLimit;
import com.whaty.platform.entity.recruit.RecruitMatricaluteCondition;
import com.whaty.platform.entity.recruit.RecruitMatricaluteCourse;
import com.whaty.platform.entity.recruit.TimeDef;
import com.whaty.platform.entity.user.HumanNormalInfo;
import com.whaty.platform.util.Address;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.EntityLog;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;
import com.whaty.util.string.WhatyStrManage;

public class OracleBasicRecruitList implements BasicRecruitList {

	private String SQLTESTCOURSE = "select id,course_name,title,startdate,enddate,note,testsequence_id,course_id,batch_title from (select a.id,b.name as course_name,a.title,to_char(a.startdate,'yyyy-mm-dd hh24:mi:ss') as startdate,to_char(a.enddate,'yyyy-mm-dd hh24:mi:ss') as enddate,a.note,testsequence_id,course_id,c.title as batch_title from recruit_test_course a,recruit_course_info b,recruit_batch_info c where a.course_id=b.id and a.testsequence_id=c.id)";

	private String SQLTESTSTUDENTSCORE = "select id,recruitstudent_id,testcourse_id,score,status,course_name,student_id from (select a.id,recruitstudent_id,testcourse_id,score,status,c.name as course_name,a.recruitstudent_id as student_id from recruit_test_desk a,recruit_test_course b,recruit_course_info c where a.testcourse_id=b.id and b.course_id=c.id) ";

	private String SQLTESTROOM = "select id,title,note,testsite_id,num from recruit_test_room ";

	private String SQLRECRUITPLANSITE = "select id,name,linkman,phone,address,email,fax,found_date,manager,note,recruit_status,status,zip_code,URL from entity_site_info where id in (select distinct b.id from recruit_plan_info a,entity_site_info b where a.site_id=b.id ";

	private String SQLTESTDESK = "select id,recruitstudent_id,testroom_id,testcourse_id,numbyroom,numbycourse from recruit_test_desk ";

	private String SQLTESTBATCH = "select id,title,startdate,enddate,active,note,batch_id from (select id,title,to_char(startdate,'yyyy-mm-dd') as startdate,to_char(enddate,'yyyy-mm-dd') as enddate,active,note,batch_id from recruit_test_batch) ";

	private String SQLSTUDENT = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,grade_id,card_type,card_no,hometown,email,"
			+ "postalcode,address,mobilephone,phone,considertype,batch_id,status,testcard_id,append_score,score,site_name,major_name,edutype_name,grade_name,considertype_status,considertype_note,photo_link,study_no,photo_status,idcard_link,idcard_status,graduatecard_link,graduatecard_status,reg_no,school_name,school_code,graduate_year,graduate_cardno"
			+ " from (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,"
			+ "rsi.major_id,rsi.grade_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,esi.name as site_name,emi.name as major_name,egi.name as grade_name,eet.name as edutype_name,rsi.considertype_note,rsi.considertype_status as considertype_status,rsi.photo_link,rsi.study_no,rsi.photo_status,rsi.idcard_link,rsi.idcard_status,rsi.graduatecard_link,rsi.graduatecard_status,rsi.reg_no,rsi.school_name,rsi.school_code,rsi.graduate_year,rsi.graduate_cardno from recruit_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet,entity_grade_info egi where rsi.site_id=esi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id and rsi.grade_id=egi.id(+))";

	/*
	 * private String SQLTESTSTUDENT = "select
	 * id,name,password,gender,folk,birthday,zzmm,edu,card_type,card_no,hometown,email," +
	 * "postalcode,address,mobilephone,phone,considertype,considertype_status,batch_id,status,testcard_id,append_score,score,site_name,site_id,major_name,major_id,edutype_name,edutype_id,score_1,score_2,score_3,score_4" + "
	 * from (select
	 * rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd')
	 * as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id," +
	 * "rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,esi.name
	 * as site_name,emi.name as major_name,eet.name as
	 * edutype_name,rsi.score_1,rsi.score_2,rsi.score_3,rsi.score_4 from
	 * recruit_student_info rsi,entity_site_info esi,entity_major_info
	 * emi,entity_edu_type eet where rsi.status<>'0' and rsi.considertype<>'2'
	 * and rsi.considertype_status<>'2' and rsi.site_id=esi.id and
	 * rsi.major_id=emi.id and rsi.edutype_id=eet.id)";
	 */
	private String SQLTESTSTUDENT = "select id,name,password,gender,folk,birthday,zzmm,edu,card_type,card_no,hometown,email,"
			+ "postalcode,address,mobilephone,phone,considertype,considertype_status,batch_id,status,testcard_id,append_score,score,site_name,site_id,major_name,major_id,edutype_name,edutype_id,score_1,score_2,score_3,score_4,study_no,score_status,pass_status,photo_link"
			+ " from (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,"
			+ "rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,esi.name as site_name,emi.name as major_name,eet.name as edutype_name,rsi.score_1,rsi.score_2,rsi.score_3,rsi.score_4,rsi.study_no,rsi.score_status,rsi.pass_status,rsi.photo_link from recruit_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet,recruit_test_desk rtd where rsi.status<>'0'  and rsi.site_id=esi.id and rtd.recruitstudent_id = rsi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id)";

	private String SQLHANDSINGLESTUDENT = "select id"
			+ " from (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,"
			+ "rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,esi.name as site_name,emi.name as major_name,eet.name as edutype_name from recruit_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet where rsi.status<>'0' and rsi.considertype_status<>'2' and rsi.site_id=esi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id)";

	private String SQLFREESTUDENT = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
			+ "postalcode,address,mobilephone,phone,considertype,considertype_status,considertype_note,batch_id,status,testcard_id,append_score,score,site_name,major_name,edutype_name"
			+ " from (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,"
			+ "rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.considertype_note,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,esi.name as site_name,emi.name as major_name,eet.name as edutype_name from recruit_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet where rsi.status<>'0' and rsi.considertype_status='2' and rsi.site_id=esi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id)";

	private String SQLFREESTUDENTSTATUS = "select id"
			+ " from (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,"
			+ "rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,esi.name as site_name,emi.name as major_name,eet.name as edutype_name from recruit_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet where rsi.status<>'0' and rsi.considertype_status='2' and rsi.site_id=esi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id)";

	private String SQLPASSTSTUDENT = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
			+ "	postalcode,address,mobilephone,phone,considertype,considertype_status,batch_id,status,testcard_id,append_score,score,site_name,major_name,edutype_name,register_status,grade_id,grade_name,reg_no,score_1,score_2,score_3,score_4,photo_status,idcard_status,graduatecard_status,school_name,school_code,graduate_year,graduate_cardno,REGNO_STATUS,study_status "
			+ " from (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,"
			+ "	rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.school_name,rsi.school_code,rsi.graduate_year,rsi.graduate_cardno,rsi.register_status,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,rsi.study_status,esi.name as site_name,emi.name as major_name,eet.name as edutype_name,nvl(egi.id,'') as grade_id,nvl(egi.name,'') as grade_name,rsi.reg_no,rsi.score_1 as score_1,rsi.score_2 as score_2,rsi.score_3 as score_3,rsi.score_4 as score_4,rsi.photo_status,rsi.idcard_status,rsi.graduatecard_status,rsi.REGNO_STATUS  from recruit_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet,entity_grade_info egi,entity_student_info esti where rsi.status='2' and rsi.site_id=esi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id and rsi.reg_no=esti.reg_no(+) and esti.grade_id=egi.id(+))";

	private String SQLUNPASSTSTUDENT = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
			+ "	postalcode,address,mobilephone,phone,considertype,considertype_status,batch_id,status,testcard_id,append_score,score,site_name,major_name,edutype_name,register_status,grade_id,grade_name,reg_no,score_1,score_2,score_3,score_4,school_name,school_code,graduate_year,graduate_cardno,photo_status,idcard_status,graduatecard_status,study_status"
			+ " from (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,"
			+ "	rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.register_status,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,rsi.study_status,esi.name as site_name,emi.name as major_name,eet.name as edutype_name,nvl(egi.id,'') as grade_id,nvl(egi.name,'') as grade_name,rsi.reg_no,rsi.score_1 as score_1,rsi.score_2 as score_2,rsi.score_3 as score_3,rsi.score_4 as score_4,rsi.school_name,rsi.school_code,rsi.graduate_year,rsi.graduate_cardno,rsi.photo_status,rsi.idcard_status,rsi.graduatecard_status  from recruit_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet,entity_grade_info egi,entity_student_info esti where rsi.status='1' and rsi.site_id=esi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id and rsi.reg_no=esti.reg_no(+) and esti.grade_id=egi.id(+))";

	private String SQLSTUDYSTATUSSTUDENT = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
			+ "	postalcode,address,mobilephone,phone,considertype,considertype_status,batch_id,status,testcard_id,append_score,score,study_status,site_name,major_name,edutype_name,register_status,grade_id,grade_name,reg_no,score_1,score_2,score_3,score_4,school_name,school_code,graduate_year,graduate_cardno,photo_status,idcard_status,graduatecard_status"
			+ " from (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,"
			+ "	rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.register_status,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,rsi.study_status,esi.name as site_name,emi.name as major_name,eet.name as edutype_name,nvl(egi.id,'') as grade_id,nvl(egi.name,'') as grade_name,rsi.reg_no,rsi.score_1 as score_1,rsi.score_2 as score_2,rsi.score_3 as score_3,rsi.score_4 as score_4,rsi.school_name,rsi.school_code,rsi.graduate_year,rsi.graduate_cardno,rsi.photo_status,rsi.idcard_status,rsi.graduatecard_status  from recruit_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet,entity_grade_info egi,entity_student_info esti where rsi.site_id=esi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id and rsi.reg_no=esti.reg_no(+) and esti.grade_id=egi.id(+))";

	private String SQLRELEASESTUDENTS = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
			+ "	postalcode,address,mobilephone,phone,considertype,considertype_status,batch_id,status,testcard_id,append_score,score,study_status,site_name,major_name,edutype_name,register_status,grade_id,grade_name,reg_no,score_1,score_2,score_3,score_4,school_name,school_code,graduate_year,graduate_cardno,photo_status,idcard_status,graduatecard_status,pass_status "
			+ " from (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,"
			+ "	rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.register_status,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,rsi.study_status,rsi.pass_status,esi.name as site_name,emi.name as major_name,eet.name as edutype_name,nvl(egi.id,'') as grade_id,nvl(egi.name,'') as grade_name,rsi.reg_no,rsi.score_1 as score_1,rsi.score_2 as score_2,rsi.score_3 as score_3,rsi.score_4 as score_4,rsi.school_name,rsi.school_code,rsi.graduate_year,rsi.graduate_cardno,rsi.photo_status,rsi.idcard_status,rsi.graduatecard_status  from recruit_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet,entity_grade_info egi,entity_student_info esti where rsi.status='2' and rsi.site_id=esi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id and rsi.reg_no=esti.reg_no(+) and esti.grade_id=egi.id(+))";

	private String SQLPLAN = "select id,title,batch_id,edutype_id,major_id,site_id,recruitnum,recruitnumvalid,signnum,signnumvalid,startdate,enddate,signtimevalid,status,active,reject_note from (select rpi.id,rpi.title,rpi.batch_id,rpi.edutype_id,rpi.major_id,rpi.site_id,rpi.recruitnum,rpi.recruitnumvalid,rpi.signnum,rpi.signnumvalid,to_char(rpi.startdate,'yyyy-mm-dd') as startdate,to_char(rpi.enddate,'yyyy-mm-dd') as enddate,rpi.signtimevalid,rpi.status,rbi.active as active,rpi.reject_note from recruit_plan_info rpi,recruit_batch_info rbi where rpi.batch_id=rbi.id)";

	private String SQLBATCH = "select id,title,plan_startdate,plan_enddate,startdate,enddate,exam_startdate,exam_enddate,grade_id,"
			+ "note,creattime,active,jianzhang,reg_startdate,reg_enddate from "
			+ "(select id,title,to_char(plan_startdate,'yyyy-mm-dd') as plan_startdate,"
			+ "to_char(reg_startdate,'yyyy-mm-dd') as reg_startdate,to_char(reg_enddate,'yyyy-mm-dd') as reg_enddate,to_char(plan_enddate,'yyyy-mm-dd') as plan_enddate,to_char(startdate,'yyyy-mm-dd') as startdate,"
			+ "to_char(enddate,'yyyy-mm-dd') as enddate,to_char(exam_startdate,'yyyy-mm-dd') as exam_startdate,"
			+ "to_char(exam_enddate,'yyyy-mm-dd') as exam_enddate,grade_id,note,jianzhang,"
			+ " to_char(creattime,'yyyy-mm-dd') as creattime,active from recruit_batch_info)";

	private String SQLACTIVEBATCH = "select id,title,plan_startdate,plan_enddate,startdate,enddate,exam_startdate,exam_enddate,grade_id,"
			+ "note,creattime,active,isSignTime,reg_startdate,reg_enddate from "
			+ "(select id,title,to_char(plan_startdate,'yyyy-mm-dd') as plan_startdate,"
			+ "to_char(reg_startdate,'yyyy-mm-dd') as reg_startdate,to_char(reg_enddate,'yyyy-mm-dd') as reg_enddate,to_char(plan_enddate,'yyyy-mm-dd') as plan_enddate,to_char(startdate,'yyyy-mm-dd') as startdate,"
			+ "to_char(enddate,'yyyy-mm-dd') as enddate,to_char(exam_startdate,'yyyy-mm-dd') as exam_startdate,"
			+ "to_char(exam_enddate,'yyyy-mm-dd') as exam_enddate,grade_id,note,"
			+ " to_char(creattime,'yyyy-mm-dd') as creattime,active,'1' as isSignTime from recruit_batch_info where sysdate between startdate and enddate union select id,title,to_char(plan_startdate,'yyyy-mm-dd') as plan_startdate,to_char(reg_startdate,'yyyy-mm-dd') as reg_startdate,to_char(reg_enddate,'yyyy-mm-dd') as reg_enddate,"
			+ "to_char(plan_enddate,'yyyy-mm-dd') as plan_enddate,to_char(startdate,'yyyy-mm-dd') as startdate,"
			+ "to_char(enddate,'yyyy-mm-dd') as enddate,to_char(exam_startdate,'yyyy-mm-dd') as exam_startdate,"
			+ "to_char(exam_enddate,'yyyy-mm-dd') as exam_enddate,grade_id,note,"
			+ " to_char(creattime,'yyyy-mm-dd') as creattime,active,'0' as isSignTime from recruit_batch_info where sysdate not between startdate and enddate)";

	private String SQLTESTSEQUENCE = "select id,title,startdate,enddate,note,testbatch_id from (select id,title,to_char(startdate,'yyyy-mm-dd') as startdate,to_char(enddate,'yyyy-mm-dd') as enddate,note,testbatch_id from recruit_test_sequence)";

	private String SQLTESTSITE = "select id,name,note,site_id from recruit_test_site";

	private String SQLCOURSE = "select id,name,note from recruit_course_info where id<>'0' ";

	private String SQLSORT = "select id,edutype_id,edutype_name,name,note from (select a.id,a.edutype_id,b.name as edutype_name,a.name,a.note from recruit_sort_info a,entity_edu_type b where a.edutype_id=b.id)";

	private String SQLNOEXAMCONDITION = "select id,name,note from recruit_noexam_condition ";

	private String SQLSTUDENTTESTCOURSE = "select id,name,site_id,card_no,major_id,major_name,status,edutype_id,edutype_name,considertype,course_id,course_name,sort_name,startdate,enddate from (select rsi.id,rsi.name,rsi.site_id,rsi.card_no,rsi.major_id,rsi.status,emi.name as major_name,rsi.edutype_id,eet.name as edutype_name,rsi.considertype,rsr.course_id,rec.name  as course_name,rsoi.name as sort_name,to_char(rtc.startdate,'yyyy-mm-dd hh24:mi:ss') startdate,to_char(rtc.enddate,'yyyy-mm-dd hh24:mi:ss') enddate from recruit_test_desk rtd,recruit_student_info rsi,recruit_batch_info rbi,entity_major_info emi,entity_edu_type eet,recruit_majorsort_relation rmr,recruit_sort_info rsoi,recruit_sortcourse_relation rsr,recruit_test_course rtc ,recruit_course_info rec where rtd.recruitstudent_id=rsi.id and rtd.testcourse_id=rtc.id and rsi.batch_id=rbi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id and rbi.id=rtc.testsequence_id and emi.id=rmr.major_id and eet.id=rsoi.edutype_id and rmr.sort_id=rsoi.id and rsoi.id=rsr.sort_id and rsr.course_id=rec.id and rtc.course_id=rec.id and rtc.testsequence_id in(select id from recruit_batch_info where active='1'))";

	private String SQLSIGNSTATISTICNUM = "select * from recruit_student_info";

	private String SQLSIGNSTATISTIC = "select countId,edutype_id,edutype_name, major_id,major_name from (select count(a.id) as countId,edutype_id,c.name as edutype_name,major_id,b.name as major_name,a.batch_id,a.status,a.site_id from recruit_student_info a,entity_major_info b,entity_edu_type c where a.major_id=b.id and a.edutype_id=c.id group by major_id,c.name,edutype_id,b.name)";

	// private String SQLSIGNSTATISTIC = "select edutype_id,edutype_name,
	// major_id,major_name,site_id,site_name,countId from (select distinct
	// edutype_id,c.name as edutype_name,major_id,b.name as
	// major_name,a.batch_id,a.status,a.site_id as site_id,d.name as
	// site_name,count(a.id) as countId from recruit_student_info
	// a,entity_major_info b,entity_edu_type c,entity_site_info d where
	// a.major_id=b.id and a.edutype_id=c.id and a.site_id=d.id group by
	// edutype_id,b.name,major_id,c.name,a.site_id,d.name) order by site_id";
	private String SQLSIGNSTATISTIC2 = "select countId,edutype_id,edutype_name, site_id,site_name from (select count(a.id) as countId,edutype_id,c.name as edutype_name,site_id,b.name as site_name,a.batch_id,a.status,a.major_id from recruit_student_info a,entity_site_info b,entity_edu_type c where a.site_id=b.id and a.edutype_id=c.id group by site_id,c.name,edutype_id,b.name)";

	private String SQLTOTALTESTROOM = "select edutype_id,major_id,edutype_name,major_name,roomnums,nums from (select rem.edutype_id,rem.major_id,eet.name as edutype_name,emi.name as major_name,rem.roomnums,rem.nums from (select a.edutype_id,a.major_id,a.roomnums,b.nums from (select edutype_id,major_id,count(*) as roomnums  from (select distinct rtd.testroom_id,rsi.edutype_id,rsi.major_id from recruit_test_desk rtd,recruit_student_info rsi where rtd.recruitstudent_id=rsi.id) group by edutype_id,major_id) a left join (select edutype_id,major_id ,count(*) as nums from (select distinct rsi.id,rsi.edutype_id,rsi.major_id from recruit_test_desk rtd,recruit_student_info rsi where rtd.recruitstudent_id=rsi.id) group by edutype_id,major_id) b on a.edutype_id=b.edutype_id and a.major_id=b.major_id ) rem,entity_edu_type eet,entity_major_info emi where rem.edutype_id=eet.id and rem.major_id=emi.id)";

	private String SQLSTUDENTTESTDESK = "select id,name,gender,card_no,testcard_id,address,major_id,major_name,edutype_id,edutype_name,photo_link,batch_id,testroom_id,numbyroom,startdate,enddate,site_id,room_no,testsite_name,course_id,course_name from (select distinct rsi.id,rsi.name,rsi.gender,rsi.card_no,rsi.testcard_id,rsi.address,rsi.major_id,emi.name as major_name,eet.name as edutype_name,rsi.edutype_id,rsi.photo_link,rsi.batch_id,rtd.testroom_id,rtd.numbyroom,rtc.startdate,rtc.enddate,rsi.site_id,rtr.room_no,rtr.title as testsite_name,rec.id as course_id,rec.name as course_name from recruit_test_desk rtd,recruit_student_info rsi,entity_major_info emi,entity_edu_type eet,recruit_test_room rtr,recruit_test_course rtc,recruit_course_info rec where rsi.considertype_status<>'2' and rsi.status='1' and rec.id=rtc.course_id and rtd.recruitstudent_id=rsi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id and rsi.site_id=rtr.testsite_id and rtc.id=rtd.testcourse_id and rtd.testroom_id = rtr.id and rtd.testroom_id is not null)";

	private String SQLTATOLSTU = "select countId,site_id,edutype_id, major_id from (select count(id) as countId,site_id,edutype_id, major_id,batch_id,status from recruit_student_info group by site_id, major_id,edutype_id)";

	private String SQLTATOLSTUPASS = "select count(id) as countId from recruit_student_info ";

	private String sql_condition = "select b.id,a.major_id as major_id,a.edutype_id,b.score,b.batch_id from (select distinct major_id,edutype_id,rsi.batch_id from recruit_test_desk rtd,recruit_student_info rsi where rtd.recruitstudent_id=rsi.id) a left join recruit_matriculate_condition b on a.major_id=b.major_id and b.batch_id=a.batch_id and a.edutype_id=b.edutype_id";

	public List getCourses(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLCOURSE
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty);
		MyResultSet rs = null;
		ArrayList recruitCourse = null;

		try {
			db = new dbpool();
			recruitCourse = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitCourse orclrecruitCourse = new OracleRecruitCourse();
				orclrecruitCourse.setId(rs.getString("id"));
				orclrecruitCourse.setName(rs.getString("name"));
				orclrecruitCourse.setNote(rs.getString("note"));
				recruitCourse.add(orclrecruitCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return recruitCourse;
	}

	public int getCoursesNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLCOURSE + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getMajorsBySort(Page page, List searchProperty,
			List orderProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMajorsNumBySort(List searchProperty) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getSorts(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLSORT
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty);
		MyResultSet rs = null;
		ArrayList sortList = null;
		try {
			db = new dbpool();
			sortList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitSort recruitSort = new OracleRecruitSort();
				recruitSort.setId(rs.getString("id"));
				OracleEduType edutype = new OracleEduType();
				edutype.setId(rs.getString("edutype_id"));
				edutype.setName(rs.getString("edutype_name"));
				recruitSort.setEdutype(edutype);
				recruitSort.setName(rs.getString("name"));
				recruitSort.setNote(rs.getString("note"));
				sortList.add(recruitSort);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return sortList;
	}

	public int getSortsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLSORT + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getTestCourses(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLTESTCOURSE
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList testCourseList = null;
		try {
			db = new dbpool();
			testCourseList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestCourse testCourse = new OracleRecruitTestCourse();
				testCourse.setId(rs.getString("id"));
				testCourse.setTitle(rs.getString("title"));
				TimeDef time = new TimeDef(rs.getString("startdate"), rs
						.getString("enddate"));
				testCourse.setTime(time);
				testCourse.setNote(rs.getString("note"));
				OracleRecruitTestSequence testSequence = new OracleRecruitTestSequence();
				OracleRecruitTestBatch testBatch = new OracleRecruitTestBatch();
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("testsequence_id"));
				batch.setTitle(rs.getString("batch_title"));
				testBatch.setBatch(batch);
				testSequence.setTestBatch(testBatch);
				testCourse.setTestSequence(testSequence);
				OracleRecruitCourse course = new OracleRecruitCourse();
				course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				testCourse.setCourse(course);
				testCourseList.add(testCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testCourseList;
	}

	public int getTestCoursesNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLTESTCOURSE
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getTestSequences(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLTESTSEQUENCE
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty);
		MyResultSet rs = null;
		ArrayList testSequenceList = null;
		try {
			db = new dbpool();
			testSequenceList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestSequence testSequence = new OracleRecruitTestSequence();
				testSequence.setId(rs.getString("id"));
				testSequence.setTitle(rs.getString("title"));
				TimeDef time = new TimeDef();
				time.setStartTime(rs.getString("startdate"));
				time.setEndTime(rs.getString("enddate"));
				testSequence.setTime(time);
				testSequence.setNote(rs.getString("note"));
				OracleRecruitTestBatch recruitTestBatch = new OracleRecruitTestBatch();
				recruitTestBatch.setId(rs.getString("testbatch_id"));
				testSequence.setTestBatch(recruitTestBatch);
				testSequenceList.add(testSequence);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testSequenceList;
	}

	public int getTestSequencesNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLTESTSEQUENCE
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getActiveBatchs(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLACTIVEBATCH
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		boolean active = false;
		boolean isSignTime = false;
		ArrayList batchList = null;
		try {
			db = new dbpool();
			batchList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}

			while (rs != null && rs.next()) {
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("id"));
				batch.setTitle(rs.getString("title"));
				batch.setReg_startdate("reg_startdate");
				batch.setReg_enddate(rs.getString("reg_enddate"));

				TimeDef planDate = new TimeDef();
				planDate.setStartTime(rs.getString("plan_startdate"));
				planDate.setEndTime(rs.getString("plan_enddate"));
				batch.setPlanTime(planDate);

				TimeDef signDate = new TimeDef();
				signDate.setStartTime(rs.getString("startdate"));
				signDate.setEndTime(rs.getString("enddate"));
				batch.setSignTime(signDate);

				TimeDef examDate = new TimeDef();
				examDate.setStartTime(rs.getString("exam_startdate"));
				examDate.setEndTime(rs.getString("exam_enddate"));
				batch.setExamTime(examDate);

				WhatyStrManage strManage1 = new WhatyStrManage(rs
						.getString("note"));

				OracleGrade grade = new OracleGrade();
				grade.setId(rs.getString("grade_id"));
				batch.setNote(strManage1.htmlDecode());
				batch.setCreatTime(rs.getString("creattime"));
				if (rs.getString("active").equals("1"))
					active = true;
				else
					active = false;
				batch.setActive(active);
				if (rs.getString("isSignTime").equals("1"))
					isSignTime = true;
				else
					isSignTime = false;
				batch.setSignTime(isSignTime);
				batchList.add(batch);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return batchList;
	}

	public List getBatchs(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLBATCH
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		boolean active = false;
		ArrayList batchList = null;
		try {
			db = new dbpool();
			batchList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setSimpleNote(rs.getString("jianzhang"));
				batch.setId(rs.getString("id"));
				batch.setTitle(rs.getString("title"));
				batch.setReg_startdate(rs.getString("reg_startdate"));
				batch.setReg_enddate(rs.getString("reg_enddate"));

				TimeDef planDate = new TimeDef();
				planDate.setStartTime(rs.getString("plan_startdate"));
				planDate.setEndTime(rs.getString("plan_enddate"));
				batch.setPlanTime(planDate);

				TimeDef signDate = new TimeDef();
				signDate.setStartTime(rs.getString("startdate"));
				signDate.setEndTime(rs.getString("enddate"));
				batch.setSignTime(signDate);

				TimeDef examDate = new TimeDef();
				examDate.setStartTime(rs.getString("exam_startdate"));
				examDate.setEndTime(rs.getString("exam_enddate"));
				batch.setExamTime(examDate);

				OracleGrade grade = new OracleGrade();
				grade.setId(rs.getString("grade_id"));
				batch.setNote(rs.getString("note"));
				batch.setCreatTime(rs.getString("creattime"));
				if (rs.getString("active").equals("1"))
					active = true;
				else
					active = false;
				batch.setActive(active);
				batchList.add(batch);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return batchList;
	}

	public int getBatchsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLBATCH + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getPlans(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLPLAN
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		boolean recruitNumValid = false;
		boolean signNumValid = false;
		boolean signTimeValid = false;
		ArrayList planList = null;
		try {
			db = new dbpool();
			planList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitPlan plan = new OracleRecruitPlan();
				plan.setId(rs.getString("id"));
				plan.setTitle(rs.getString("title"));
				OracleRecruitBatch recruitBatch = new OracleRecruitBatch();
				recruitBatch.setId(rs.getString("batch_id"));
				OracleEduType edutype = new OracleEduType(rs
						.getString("edutype_id"));
				// edutype.setId(rs.getString("edutype_id"));
				plan.setEdutype(edutype);
				OracleMajor major = new OracleMajor(rs.getString("major_id"));
				// major.setId(rs.getString("major_id"));
				plan.setMajor(major);
				OracleSite site = new OracleSite(rs.getString("site_id"));
				// site.setId(rs.getString("site_id"));
				plan.setSite(site);
				RecruitLimit limit = new RecruitLimit();
				limit.setRecruitNum(rs.getInt("recruitnum"));
				if (rs.getString("recruitnumvalid").equals("1"))
					recruitNumValid = true;
				else
					recruitNumValid = false;
				limit.setRecruitNumValid(recruitNumValid);
				limit.setSignNum(rs.getInt("signnum"));
				if (rs.getString("signnumvalid").equals("1"))
					signNumValid = true;
				else
					signNumValid = false;
				limit.setSignNumValid(signNumValid);
				TimeDef time = new TimeDef();
				time.setStartTime(rs.getString("startdate"));
				time.setEndTime(rs.getString("enddate"));
				limit.setSignTime(time);
				if (rs.getString("signtimevalid").equals("1"))
					signTimeValid = true;
				else
					signTimeValid = false;
				limit.setSignTimeValid(signTimeValid);
				plan.setLimit(limit);
				plan.setStatus(rs.getString("status"));
				plan.setReject_note(rs.getString("reject_note"));
				planList.add(plan);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return planList;
	}

	public int getPlansNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLPLAN + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getTestBatchs(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLTESTBATCH
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty);
		MyResultSet rs = null;
		boolean active = false;
		ArrayList testBatchList = null;
		try {
			db = new dbpool();
			testBatchList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestBatch testBatch = new OracleRecruitTestBatch();
				testBatch.setId(rs.getString("id"));
				testBatch.setTitle(rs.getString("title"));
				TimeDef time = new TimeDef();
				time.setStartTime(rs.getString("startdate"));
				time.setEndTime(rs.getString("enddate"));
				testBatch.setTime(time);
				if (rs.getString("active").equals("1"))
					active = true;
				else
					active = false;
				testBatch.setActive(active);
				testBatch.setNote(rs.getString("note"));
				OracleRecruitBatch recruitBatch = new OracleRecruitBatch();
				recruitBatch.setId(rs.getString("batch_id"));
				testBatch.setBatch(recruitBatch);
				testBatchList.add(testBatch);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testBatchList;
	}

	public int getTestBatchsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLTESTBATCH
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getTestSites(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLTESTSITE
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty);
		MyResultSet rs = null;
		MyResultSet rs_sub = null;
		ArrayList testSiteList = null;
		try {
			db = new dbpool();
			testSiteList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestSite testSite = new OracleRecruitTestSite();
				testSite.setId(rs.getString("id"));
				testSite.setName(rs.getString("name"));
				testSite.setNote(rs.getString("note"));
				OracleSite site = new OracleSite();
				site.setId(rs.getString("site_id"));
				testSite.setSite(site);
				sql = "select si.id as site_id,si.name as site_name from recruit_test_subsite ts,entity_site_info si where ts.subsite_id=si.id and ts.testsite_id='"
						+ testSite.getId() + "'";
				rs_sub = db.executeQuery(sql);
				ArrayList subSiteList = new ArrayList();
				while (rs_sub != null && rs_sub.next()) {
					OracleSite subsite = new OracleSite();
					subsite.setId(rs_sub.getString("site_id"));
					subsite.setName(rs.getString("site_name"));
					subSiteList.add(subsite);
				}
				rs_sub = null;
				testSite.setSubSiteList(subSiteList);
				testSiteList.add(testSite);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db.close(rs_sub);
			db = null;
		}
		return testSiteList;
	}

	public int getTestSitesNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLTESTSITE + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getTestRooms(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLTESTROOM
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty);
		MyResultSet rs = null;
		ArrayList testRoomList = null;
		try {
			db = new dbpool();
			testRoomList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestRoom testRoom = new OracleRecruitTestRoom();
				testRoom.setId(rs.getString("id"));
				testRoom.setTitle(rs.getString("title"));
				testRoom.setNote(rs.getString("note"));
				OracleRecruitTestSite testSite = new OracleRecruitTestSite();
				testSite.setId(rs.getString("testsite_id"));
				testRoom.setTestSite(testSite);
				testRoom.setNum(rs.getInt("num"));
				testRoomList.add(testRoom);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testRoomList;
	}

	public int getTestRoomsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLTESTROOM + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getStudents(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLSTUDENT
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList studentList = null;
		try {
			db = new dbpool();
			studentList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitStudent student = new OracleRecruitStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setStudy_no(rs.getString("study_no"));
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
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				eduInfo.setConsidertype_status(rs
						.getString("considertype_status"));
				eduInfo.setConsidertype_note(rs.getString("considertype_note"));
				eduInfo.setTestCardId(rs.getString("testcard_id"));
				eduInfo.setSchool_name(rs.getString("school_name"));
				eduInfo.setSchool_code(rs.getString("school_code"));
				eduInfo.setGraduate_year(rs.getString("graduate_year"));
				eduInfo.setGraduate_cardno(rs.getString("graduate_cardno"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setPhoto_link(rs.getString("photo_link"));
				eduInfo.setPhoto_status(rs.getString("photo_status"));
				eduInfo.setIdcard_link(rs.getString("idcard_link"));
				eduInfo.setIdcard_status(rs.getString("idcard_status"));
				eduInfo.setGraduatecard_link(rs.getString("graduatecard_link"));
				eduInfo.setGraduatecard_status(rs
						.getString("graduatecard_status"));
				student.setEduInfo(eduInfo);
				studentList.add(student);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return studentList;
	}

	public int getStudentsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLSTUDENT + Conditions.convertToCondition(searchProperty, null);
		EntityLog.setDebug("getStudentsNum=" + sql);
		int i = db.countselect(sql);

		return i;
	}

	public List getTestStudents(Page page, List searchProperty,
			List orderProperty, String signDate) {
		dbpool db = new dbpool();
		String sql = "";
		if (signDate == null || signDate.length() < 4
				|| signDate.equals("unmatriculate"))
			sql = SQLTESTSTUDENT
					+ Conditions.convertToCondition(searchProperty,
							orderProperty);
		else {
			String year = signDate.substring(0, 4);
			String month = signDate.substring(5, 7);
			if (Integer.parseInt(month) <= 6) {
				year = Integer.toString(Integer.parseInt(year) - 1);
				signDate = year + "-12-31";
			} else {
				signDate = year + "-06-30";
			}
			sql = "select id,name,password,gender,folk,birthday,zzmm,edu,card_type,card_no,hometown,email,"
					+ "postalcode,address,mobilephone,phone,considertype,considertype_status,batch_id,status,testcard_id,append_score,score,site_name,site_id,major_name,major_id,edutype_name,edutype_id,score_1,score_2,score_3,score_4,study_no,score_status,photo_link"
					+ " from (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.batch_id,rsi.status,rsi.testcard_id,30 as append_score,rsi.score,rsi.photo_link,esi.name as site_name,emi.name as major_name,eet.name as edutype_name,rsi.score_1,rsi.score_2,rsi.score_3,rsi.score_4,rsi.study_no,rsi.score_status from recruit_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet ,recruit_test_desk rtd where rtd.recruitstudent_id = rsi.id and add_months(rsi.birthday,300)<=to_date('"
					+ signDate
					+ "','yyyy-mm-dd') and rsi.status<>'0' and rsi.considertype_status<>'2' and rsi.considertype_status<>'2' and rsi.site_id=esi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id"
					+ " union select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.batch_id,rsi.status,rsi.testcard_id,0 as append_score,rsi.score,rsi.photo_link,esi.name as site_name,emi.name as major_name,eet.name as edutype_name,rsi.score_1,rsi.score_2,rsi.score_3,rsi.score_4,rsi.study_no,rsi.score_status from recruit_student_info rsi,entity_site_info esi,entity_major_info emi,entity_edu_type eet ,recruit_test_desk rtd where rtd.recruitstudent_id = rsi.id and add_months(rsi.birthday,300)>to_date('"
					+ signDate
					+ "','yyyy-mm-dd') and rsi.status<>'0' and rsi.considertype_status<>'2' and rsi.considertype_status<>'2' and rsi.site_id=esi.id and rsi.major_id=emi.id and rsi.edutype_id=eet.id)"
					+ Conditions.convertToCondition(searchProperty,
							orderProperty);
		}
		EntityLog.setDebug(sql);
		int length = 0;
		MyResultSet rs = null;
		ArrayList testStudentList = null;
		try {
			db = new dbpool();
			testStudentList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestStudent testStudent = new OracleRecruitTestStudent();
				OracleRecruitStudent student = new OracleRecruitStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setStudy_no(rs.getString("study_no"));
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
				eduInfo.setSite_id(rs.getString("site_id"));

				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				eduInfo.setPhoto_link(rs.getString("photo_link"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setScore_status(rs.getString("score_status"));
				if (signDate != null && signDate.equals("1")) {
					String no1 = rs.getString("batch_id")
							+ rs.getString("site_id").substring(
									rs.getString("site_id").length() - 2);
					String no = "0001";
					sql = "select nvl(max(notice_no),0) as notice_no from recruit_student_info where notice_no like '"
							+ no1 + "____'";
					MyResultSet rss = null;
					try {
						rss = db.executeQuery(sql);
						if (rss != null && rss.next()) {
							no = rss.getString("notice_no");
							no = no.substring(no.length() - 4);
						}
					} catch (Exception e) {
					}
					db.close(rss);
					int nos = 0;
					nos = Integer.parseInt(no);
					no = "0000" + (++nos);
					no = no.substring(no.length() - 4);
					no = no1 + no;
					eduInfo.setNotice_no(no);
					sql = "update recruit_student_info set notice_no='" + no
							+ "',notice_status='1' where id='"
							+ rs.getString("id") + "'";
					db.executeUpdate(sql);
				}
				student.setEduInfo(eduInfo);
				testStudent.setStudent(student);
				List searchProperties = new ArrayList();
				searchProperties.add(new SearchProperty("recruitstudent_id",
						student.getId(), "="));
				List testDeskList = this.getTestDesks(null, searchProperties,
						null);
				testStudent.setTestDeskList(testDeskList);
				testStudent.setTestCardId(rs.getString("testcard_id"));
				testStudent.setAppendScore(rs.getString("append_score"));
				testStudent.setScore(rs.getString("score"));
				List scoreList = new ArrayList();
				scoreList.add(rs.getString("score_1"));
				scoreList.add(rs.getString("score_2"));
				scoreList.add(rs.getString("score_3"));
				scoreList.add(rs.getString("score_4"));
				testStudent.setScoreList(scoreList);

				List searchProperties2 = new ArrayList();
				searchProperties2.add(new SearchProperty("student_id", student
						.getId(), "="));
				List studentScoreList = this.getStudentScores(null,
						searchProperties2, null);
				testStudent.setStudentScoreList(studentScoreList);

				testStudentList.add(testStudent);
			}

		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testStudentList;
	}

	public int getPassStudentsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLPASSTSTUDENT
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);

		return i;
	}

	public int updateStudentSiteAndMajor(String[] ids, String majorId,
			String siteId) {
		dbpool db = new dbpool();
		String idStr = "";
		for (int i = 0; i < ids.length; i++) {
			idStr += "'" + ids[i] + "',";
		}
		if (idStr.length() > 0)
			idStr = idStr.substring(0, idStr.length() - 1);
		String sql = "";
		sql = "update recruit_student_info set major_id = '" + majorId
				+ "' , site_id = '" + siteId + "' where id in (" + idStr + ")";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleBasicRecruitList.updateStudentSiteAndMajor(String[] ids, String majorId,String siteId) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getPassStudents(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLPASSTSTUDENT
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList testStudentList = null;
		try {
			db = new dbpool();
			testStudentList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestStudent testStudent = new OracleRecruitTestStudent();
				OracleRecruitStudent student = new OracleRecruitStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setPassword(rs.getString("password"));
				student.setStudyStatus(rs.getString("study_status"));
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
				eduInfo.setSchool_name(rs.getString("school_name"));
				eduInfo.setSchool_code(rs.getString("school_code"));
				eduInfo.setGraduate_year(rs.getString("graduate_year"));
				eduInfo.setGraduate_cardno(rs.getString("graduate_cardno"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				student.setEduInfo(eduInfo);
				testStudent.setStudent(student);
				List searchProperties = new ArrayList();
				searchProperties.add(new SearchProperty("recruitstudent_id",
						student.getId(), "="));
				List testDeskList = this.getTestDesks(null, searchProperties,
						null);
				testStudent.setTestDeskList(testDeskList);
				testStudent.setTestCardId(rs.getString("testcard_id"));
				testStudent.setAppendScore(rs.getString("append_score"));
				testStudent.setScore(rs.getString("score"));
				List scoreList = new ArrayList();
				scoreList.add(rs.getString("score_1"));
				scoreList.add(rs.getString("score_2"));
				scoreList.add(rs.getString("score_3"));
				scoreList.add(rs.getString("score_4"));
				testStudent.setScoreList(scoreList);
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
				testStudentList.add(testStudent);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testStudentList;
	}

	public int getTestStudentsNum(List searchProperty, String signDate) {
		dbpool db = new dbpool();
		String sql = "";
		// if (signDate == null)
		sql = SQLTESTSTUDENT
				+ Conditions.convertToCondition(searchProperty, null);
		EntityLog.setDebug(sql);
		// else
		// sql = SQLTESTSTUDENT2
		// + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getTotalStudents(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
				+ "	postalcode,address,mobilephone,phone,considertype,considertype_status,batch_id,status,testcard_id,append_score,score,major_name,edutype_name,register_status,reg_no,score_1,score_2,score_3,score_4,pass_status,school_name,school_code,graduate_year,graduate_cardno,study_no"
				+ " from  (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,"
				+ "	rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.register_status,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,emi.name as major_name,eet.name as edutype_name,rsi.reg_no,rsi.score_1 as score_1,rsi.score_2 as score_2,rsi.score_3 as score_3,rsi.score_4 as score_4,rsi.pass_status as pass_status,rsi.school_name,rsi.school_code,rsi.graduate_year,rsi.graduate_cardno,rsi.study_no  from recruit_student_info rsi,entity_major_info emi,entity_edu_type eet where  rsi.major_id=emi.id and rsi.edutype_id=eet.id)";

		sql = sql + Conditions.convertToCondition(searchProperty, null);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList testStudentList = null;
		try {
			db = new dbpool();
			testStudentList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitStudent student = new OracleRecruitStudent();
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
				student.setStudy_no(rs.getString("study_no"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				eduInfo.setSchool_name(rs.getString("school_name"));
				eduInfo.setSchool_code(rs.getString("school_code"));
				eduInfo.setGraduate_year(rs.getString("graduate_year"));
				eduInfo.setGraduate_cardno(rs.getString("graduate_cardno"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setPassStatus(rs.getString("pass_status"));
				student.setEduInfo(eduInfo);
				testStudentList.add(student);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testStudentList;
	}

	public int getTotalStudentsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "select id,name,password,gender,folk,birthday,zzmm,edu,site_id,edutype_id,major_id,card_type,card_no,hometown,email,"
				+ "	postalcode,address,mobilephone,phone,considertype,considertype_status,batch_id,status,testcard_id,append_score,score,major_name,edutype_name,register_status,reg_no,score_1,score_2,score_3,score_4,pass_status"
				+ " from  (select rsi.id,rsi.name,rsi.password,rsi.gender,rsi.folk,to_char(rsi.birthday,'yyyy-mm-dd') as birthday,rsi.zzmm,edu,rsi.site_id,rsi.edutype_id,"
				+ "	rsi.major_id,rsi.card_type,rsi.card_no,rsi.hometown,rsi.email,rsi.postalcode,rsi.address,rsi.mobilephone,rsi.phone,rsi.considertype,rsi.considertype_status,rsi.register_status,rsi.batch_id,rsi.status,rsi.testcard_id,rsi.append_score,rsi.score,emi.name as major_name,eet.name as edutype_name,rsi.reg_no,rsi.score_1 as score_1,rsi.score_2 as score_2,rsi.score_3 as score_3,rsi.score_4 as score_4,rsi.pass_status as pass_status  from recruit_student_info rsi,entity_major_info emi,entity_edu_type eet where  rsi.major_id=emi.id and rsi.edutype_id=eet.id)";

		sql = sql + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getFreeStudents(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLFREESTUDENT
				+ Conditions.convertToCondition(searchProperty, null);
		MyResultSet rs = null;
		ArrayList testStudentList = null;
		try {
			db = new dbpool();
			testStudentList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestStudent testStudent = new OracleRecruitTestStudent();
				OracleRecruitStudent student = new OracleRecruitStudent();
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
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				eduInfo.setConsidertype_status(rs
						.getString("considertype_status"));
				eduInfo.setConsidertype_note(rs.getString("considertype_note"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				student.setEduInfo(eduInfo);
				testStudent.setStudent(student);
				List searchProperties = new ArrayList();
				searchProperties.add(new SearchProperty("recruitstudent_id",
						student.getId(), "="));
				List testDeskList = this.getTestDesks(null, searchProperties,
						null);
				testStudent.setTestDeskList(testDeskList);
				testStudent.setTestCardId(rs.getString("testcard_id"));
				testStudent.setAppendScore(rs.getString("append_score"));
				testStudent.setScore(rs.getString("score"));
				List searchProperties2 = new ArrayList();
				searchProperties2.add(new SearchProperty("student_id", student
						.getId(), "="));
				List studentScoreList = this.getStudentScores(null,
						searchProperties2, orderProperty);
				testStudent.setStudentScoreList(studentScoreList);
				testStudentList.add(testStudent);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testStudentList;
	}

	public int getFreeStudentsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLFREESTUDENT
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getTestDesks(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLTESTDESK
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList testDeskList = null;
		try {
			db = new dbpool();
			testDeskList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestDesk testDesk = new OracleRecruitTestDesk();
				testDesk.setId(rs.getString("id"));
				OracleRecruitTestRoom testRoom = new OracleRecruitTestRoom();
				testRoom.setId(rs.getString("testroom_id"));
				testDesk.setTestRoom(testRoom);
				OracleRecruitTestCourse testCourse = new OracleRecruitTestCourse();
				testCourse.setId(rs.getString("testcourse_id"));
				testDesk.setTestCourse(testCourse);
				testDesk.setNumByRoom(rs.getInt("numbyroom"));
				testDesk.setNumByCourse(rs.getInt("numbycourse"));
				OracleRecruitStudent student = new OracleRecruitStudent(rs
						.getString("recruitstudent_id"));
				testDesk.setStudent(student);
				testDeskList.add(testDesk);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testDeskList;
	}

	public int getTestDesksNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLTESTDESK + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getStudentScores(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLTESTSTUDENTSCORE
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList recruitStudentScoreList = null;
		try {
			db = new dbpool();
			recruitStudentScoreList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitStudentScore recruitStudentScore = new OracleRecruitStudentScore();
				recruitStudentScore.setId(rs.getString("id"));
				OracleRecruitStudent student = new OracleRecruitStudent();
				student.setId(rs.getString("recruitstudent_id"));
				recruitStudentScore.setStudent(student);
				OracleRecruitTestCourse testCourse = new OracleRecruitTestCourse();
				testCourse.setId(rs.getString("testcourse_id"));
				OracleRecruitCourse course = new OracleRecruitCourse();
				course.setName(rs.getString("course_name"));
				testCourse.setCourse(course);
				recruitStudentScore.setTestCourse(testCourse);
				recruitStudentScore.setScore(rs.getString("score"));
				recruitStudentScore.setStatus(rs.getString("status"));
				recruitStudentScoreList.add(recruitStudentScore);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return recruitStudentScoreList;
	}

	public int getStudentScoresNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLTESTSTUDENTSCORE
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public RecruitBatch getBatch(List searchproperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLBATCH + Conditions.convertToCondition(searchproperty, null);
		MyResultSet rs = null;
		RecruitBatch batch = new OracleRecruitBatch();
		try {
			rs = db.executeQuery(sql);
			if (rs != null && rs.next()) {
				batch.setId(rs.getString("id"));
				batch.setTitle(rs.getString("title"));
				TimeDef planDate = new TimeDef();
				planDate.setStartTime(rs.getString("plan_startdate"));
				planDate.setEndTime(rs.getString("plan_enddate"));
				batch.setPlanTime(planDate);

				TimeDef signDate = new TimeDef();
				signDate.setStartTime(rs.getString("startdate"));
				signDate.setEndTime(rs.getString("enddate"));
				batch.setSignTime(signDate);

				TimeDef examDate = new TimeDef();
				examDate.setStartTime(rs.getString("exam_startdate"));
				examDate.setEndTime(rs.getString("exam_enddate"));
				batch.setExamTime(examDate);

				batch.setActive(true);
				WhatyStrManage strManage2 = new WhatyStrManage(rs
						.getString("note"));
				batch.setNote(strManage2.htmlDecode());
				WhatyStrManage strManage1 = new WhatyStrManage(rs
						.getString("jianzhang"));
				batch.setSimpleNote(strManage1.htmlDecode());
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return batch;
	}

	public List getUnTestCourses(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLCOURSE
				+ " and id not in (select course_id from recruit_test_course"
				+ Conditions.convertToCondition(searchProperty, null)
				+ ") order by id";
		MyResultSet rs = null;
		ArrayList recruitCourse = null;

		try {
			db = new dbpool();
			recruitCourse = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitCourse orclrecruitCourse = new OracleRecruitCourse();
				orclrecruitCourse.setId(rs.getString("id"));
				orclrecruitCourse.setName(rs.getString("name"));
				orclrecruitCourse.setNote(rs.getString("note"));
				recruitCourse.add(orclrecruitCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return recruitCourse;
	}

	public int getUnTestCoursesNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = SQLCOURSE
				+ " and id not in (select course_id from recruit_test_course"
				+ Conditions.convertToCondition(searchProperty, null) + ")";
		int i = db.countselect(sql);
		return i;
	}

	public List getStudentTestCourse(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLSTUDENTTESTCOURSE
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		Log.setDebug(sql);
		ArrayList testCourseList = null;
		try {
			db = new dbpool();
			testCourseList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestDesk testDesk = new OracleRecruitTestDesk();
				OracleRecruitStudent student = new OracleRecruitStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				normalInfo.setIdcard(rs.getString("card_no"));
				student.setNormalInfo(normalInfo);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				OracleRecruitSort sort = new OracleRecruitSort();
				sort.setName(rs.getString("sort_name"));
				eduInfo.setSort(sort);
				student.setEduInfo(eduInfo);

				testDesk.setStudent(student);

				OracleRecruitTestCourse testCourse = new OracleRecruitTestCourse();
				testCourse.setId(rs.getString("course_id"));
				testCourse.setTitle(rs.getString("course_name"));

				TimeDef timeDef = new TimeDef(rs.getString("startdate"), rs
						.getString("enddate"));
				testCourse.setTime(timeDef);
				testDesk.setTestCourse(testCourse);

				testCourseList.add(testDesk);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testCourseList;
	}

	public int getStudentTestCoursesNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = SQLSTUDENTTESTCOURSE
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	/** ϴѧƬ */
	public int uploadImage(String card_no, String filename, String type)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "update recruit_student_info set " + type + "_link='"
				+ filename + "' , " + type + "_status='1' where card_no='"
				+ card_no + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleBasicRecruitList.uploadImage(String card_no, String filename, String type) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (type.equals("photo")) {
			sql = "update recruit_photo_record set photo_status='1' where user_id=(select id from recruit_student_info where card_no='"
					+ card_no + "')";
			db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleBasicRecruitList.uploadImage(String card_no, String filename, String type) SQL=" + sql + " DATE=" + new Date());
		}
		return i;
	}

	public List getRecruitScoreStudents(Page page, List searchProperty,
			List orderProperty, String[] sec) {
		dbpool db = new dbpool();
		String sql = "select site_id,site_name,edutype_id,edutype_name,num,num_1,num_2,num_3,num_4,num_5 from (select a.site_id,b.name as site_name,a.edutype_id,c.name as edutype_name,num,nvl(num_1,0) as num_1,nvl(num_2,0) as num_2,nvl(num_3,0) as num_3,nvl(num_4,0) as num_4,nvl(num_5,0) as num_5 from (select site_id,edutype_id,count(distinct a.id) as num from recruit_student_info a,recruit_test_desk b where a.id=b.recruitstudent_id and a.status<>'0' and considertype_status<>'2' "
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty)
				+ "group by batch_id,site_id,edutype_id order by site_id,edutype_id) a,(select site_id,edutype_id,count(distinct a.id) as num_1 from recruit_student_info a,recruit_test_desk b where a.id=b.recruitstudent_id and a.status<>'0' and considertype_status<>'2' "
				
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty)
				+" and to_number(a.score)>="  
				+ sec[0] + " "
				+ "group by batch_id,site_id,edutype_id order by site_id,edutype_id) a1,(select site_id,edutype_id,count(distinct a.id) as num_2 from recruit_student_info a,recruit_test_desk b where a.id=b.recruitstudent_id and a.status<>'0' and considertype_status<>'2' and (to_number(a.score)>="
				+ sec[1]
				+ " and to_number(a.score)<"
				+ sec[0]
				+ ")"
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty)
				+ "group by batch_id,site_id,edutype_id order by site_id,edutype_id) a2,(select site_id,edutype_id,count(distinct a.id) as num_3 from recruit_student_info a,recruit_test_desk b where a.id=b.recruitstudent_id and a.status<>'0' and considertype_status<>'2' and (to_number(a.score)>="
				+ sec[2]
				+ " and to_number(a.score)<"
				+ sec[1]
				+ ")"
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty)
				+ "group by batch_id,site_id,edutype_id order by site_id,edutype_id) a3,(select site_id,edutype_id,count(distinct a.id) as num_4 from recruit_student_info a,recruit_test_desk b where a.id=b.recruitstudent_id and a.status<>'0' and considertype_status<>'2' and (to_number(a.score)>="
				+ sec[3]
				+ " and to_number(a.score)<"
				+ sec[2]
				+ ")"
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty)
				+ "group by batch_id,site_id,edutype_id order by site_id,edutype_id) a4,(select site_id,edutype_id,count(distinct a.id) as num_5 from recruit_student_info a,recruit_test_desk b where a.id=b.recruitstudent_id and a.status<>'0' and considertype_status<>'2' and (to_number(a.score)>="
				+ sec[4]
				+ " and to_number(a.score)<"
				+ sec[3]
				+ ")"
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty)
				+ "group by batch_id,site_id,edutype_id order by site_id,edutype_id) a5,entity_site_info b,entity_edu_type c where a.site_id=a1.site_id(+) and a.edutype_id=a1.edutype_id(+) and a.site_id=a2.site_id(+) and a.edutype_id=a2.edutype_id(+) and a.site_id=a3.site_id(+) and a.edutype_id=a3.edutype_id(+) and a.site_id=a4.site_id(+) and a.edutype_id=a4.edutype_id(+) and a.site_id=a5.site_id(+) and a.edutype_id=a5.edutype_id(+) and a.site_id=b.id and a.edutype_id=c.id  order by site_id,edutype_id)";
		MyResultSet rs = null;
		List scoreList = null;
		try {
			db = new dbpool();
			scoreList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				List list = new ArrayList();
				list.add("(" + rs.getString("site_id") + ")"
						+ rs.getString("site_name"));
				list.add("(" + rs.getString("edutype_id") + ")"
						+ rs.getString("edutype_name"));
				list.add(rs.getString("num"));
				list.add(rs.getString("num_1"));
				list.add(rs.getString("num_2"));
				list.add(rs.getString("num_3"));
				list.add(rs.getString("num_4"));
				list.add(rs.getString("num_5"));
				scoreList.add(list);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return scoreList;
	}

	public int getRecruitScoreStudentsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "select site_id,edutype_id,major_id,num from (select site_id,edutype_id,major_id,count(id) as num from recruit_student_info "
				+ "where status<>'0' and considertype_status<>'2' "
				+ Conditions.convertToAndCondition(searchProperty, null)
				+ " group by batch_id,site_id,edutype_id,major_id order by site_id,edutype_id,major_id)";
		int i = db.countselect(sql);
		return i;
	}

	public int getSignStatisticNum(String batch_id, String site_id,
			String major_id) {
		dbpool db = new dbpool();
		List conditions = new ArrayList();
		String sql = "";
		if (site_id == null) {
			SearchProperty searchProperty1 = new SearchProperty("major_id",
					major_id, "=");
			conditions.add(searchProperty1);
			SearchProperty searchProperty2 = new SearchProperty("batch_id",
					batch_id, "=");
			conditions.add(searchProperty2);
			sql = SQLSIGNSTATISTIC2
					+ Conditions.convertToCondition(conditions, null);
		} else if (major_id == null) {
			SearchProperty searchProperty1 = new SearchProperty("site_id",
					site_id, "=");
			conditions.add(searchProperty1);
			SearchProperty searchProperty2 = new SearchProperty("batch_id",
					batch_id, "=");
			conditions.add(searchProperty2);
			sql = SQLSIGNSTATISTIC
					+ Conditions.convertToCondition(conditions, null);
		}
		int i = db.countselect(sql);
		return i;
	}

	public List getSignStatistic(Page page, String batch_id, String site_id,
			String major_id) {
		dbpool db = new dbpool();
		List conditions = new ArrayList();
		ArrayList studentStatistic = new ArrayList();
		String sql = "";
		if (site_id == null) {
			SearchProperty searchProperty1 = new SearchProperty("major_id",
					major_id, "=");
			conditions.add(searchProperty1);
			SearchProperty searchProperty2 = new SearchProperty("batch_id",
					batch_id, "=");
			conditions.add(searchProperty2);

			sql = SQLSIGNSTATISTIC2
					+ Conditions.convertToCondition(conditions, null);

			MyResultSet rs = null;
			try {
				db = new dbpool();

				if (page != null) {
					int pageint = page.getPageInt();
					int pagesize = page.getPageSize();
					rs = db.execute_oracle_page(sql, pageint, pagesize);
				} else {
					rs = db.executeQuery(sql);
				}
				while (rs != null && rs.next()) {
					RecruitEduInfo eduInfo = new RecruitEduInfo();

					eduInfo.setTestCardId(rs.getString("countId"));
					eduInfo.setSite_id(rs.getString("site_id"));
					eduInfo.setSite_name(rs.getString("site_name"));
					eduInfo.setEdutype_id(rs.getString("edutype_id"));
					eduInfo.setEdutype_name(rs.getString("edutype_name"));
					studentStatistic.add(eduInfo);
				}
			} catch (Exception e) {
				
			} finally {
				db.close(rs);
				db = null;

			}
		} else if (major_id == null) {
			SearchProperty searchProperty1 = new SearchProperty("site_id",
					site_id, "=");
			conditions.add(searchProperty1);
			SearchProperty searchProperty2 = new SearchProperty("batch_id",
					batch_id, "=");
			conditions.add(searchProperty2);

			sql = SQLSIGNSTATISTIC
					+ Conditions.convertToCondition(conditions, null);

			MyResultSet rs = null;
			try {
				db = new dbpool();

				if (page != null) {
					int pageint = page.getPageInt();
					int pagesize = page.getPageSize();
					rs = db.execute_oracle_page(sql, pageint, pagesize);
				} else {
					rs = db.executeQuery(sql);
				}
				while (rs != null && rs.next()) {
					RecruitEduInfo eduInfo = new RecruitEduInfo();

					eduInfo.setTestCardId(rs.getString("countId"));
					eduInfo.setMajor_id(rs.getString("major_id"));
					eduInfo.setMajor_name(rs.getString("major_name"));
					eduInfo.setEdutype_id(rs.getString("edutype_id"));
					eduInfo.setEdutype_name(rs.getString("edutype_name"));
					studentStatistic.add(eduInfo);
				}
			} catch (Exception e) {
				
			} finally {
				db.close(rs);
				db = null;

			}
		}
		EntityLog.setDebug(sql);
		return studentStatistic;
	}

	public List getSignStatistic(Page page, String batch_id, String site_id) {
		dbpool db = new dbpool();
		ArrayList studentStatistic = new ArrayList();
		String sql = "";
		StringBuffer conditionbuffer = new StringBuffer();
		String condition = "";

		if (site_id == null || site_id.equals("") || site_id.equals("null")) {
		} else {

			conditionbuffer.append("  and stu.site_id= '").append(site_id)
					.append("' ");
		}

		if (batch_id == null || batch_id.equals("") || batch_id.equals("null")) {
		} else {

			conditionbuffer.append("  and stu.batch_id= '").append(batch_id)
					.append("' ");
		}

		if (batch_id == null || batch_id.equals("") || batch_id.equals("null")) {
		} else {

			conditionbuffer.append("  and stu.batch_id= '").append(batch_id)
					.append("' ");
		}

		condition = conditionbuffer.toString();

		sql = "select a.edutype_id as edutype_id, a.major_id as major_id,a.edutype_name as edutype_name,a.major_name as major_name,nvl(b.baoming,0) as baoming,"
				+ " nvl(c.baoming_confer,0) as baoming_confer,nvl(d.baoming_confer_free,0) as baoming_confer_free "
				+ " from (select distinct stu.edutype_id as edutype_id, stu.major_id as major_id,edutype.name as edutype_name,"
				+ " major.name as major_name from recruit_student_info  stu,entity_edu_type edutype,entity_major_info major where stu.edutype_id=edutype.id "
				+ " and stu.major_id=major.id  "
				+ condition
				+ ") a,"
				+ " (select  stu.edutype_id as edutype_id,stu.major_id as major_id, nvl(count(stu.id),0) as baoming "
				+ " from recruit_student_info  stu,entity_edu_type edutype,entity_major_info major where"
				+ " stu.edutype_id=edutype.id and stu.major_id=major.id  "
				+ condition
				+ " group by stu.edutype_id, stu.major_id) b,"
				+ "(select  stu.edutype_id as edutype_id,stu.major_id as major_id, "
				+ " nvl(count(stu.id),0) as baoming_confer from recruit_student_info  stu,entity_edu_type edutype,"
				+ " entity_major_info major where stu.edutype_id=edutype.id and stu.major_id=major.id and "
				+ " stu.status='1' "
				+ condition
				+ "  group by stu.edutype_id, stu.major_id) c,"
				+ " (select  stu.edutype_id as edutype_id,stu.major_id as major_id,  nvl(count(stu.id),0) as baoming_confer_free"
				+ " from recruit_student_info  stu,entity_edu_type edutype,entity_major_info major where "
				+ "	stu.edutype_id=edutype.id and stu.major_id=major.id and stu.considertype_status='2' "
				+ condition
				+ "  group by stu.edutype_id, stu.major_id) d  "
				+ " where (a.edutype_id=b.edutype_id(+) and a.major_id=b.major_id(+)) and "
				+ "(a.edutype_id=c.edutype_id(+) and a.major_id=c.major_id(+)) and (a.edutype_id=d.edutype_id(+) and a.major_id=d.major_id(+))";

		MyResultSet rs = null;
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("edutype_id", rs.getString("edutype_id"));
				hash.put("major_id", rs.getString("major_id"));
				hash.put("edutype_name", rs.getString("edutype_name"));
				hash.put("major_name", rs.getString("major_name"));
				hash.put("baoming", rs.getString("baoming"));
				hash.put("baoming_confer", rs.getString("baoming_confer"));
				hash.put("baoming_free_confer", rs
						.getString("baoming_confer_free"));
				studentStatistic.add(hash);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		EntityLog.setDebug(sql);
		return studentStatistic;
	}

	public int getSignStatisticNum(String batch_id, String site_id) {
		dbpool db = new dbpool();
		String sql = "";
		StringBuffer conditionbuffer = new StringBuffer();
		String condition = "";
		int count = 0;
		if (site_id == null || site_id.equals("") || site_id.equals("null")) {
		} else {

			conditionbuffer.append("  and stu.site_id= '").append(site_id)
					.append("' ");
		}

		if (batch_id == null || batch_id.equals("") || batch_id.equals("null")) {
		} else {

			conditionbuffer.append("  and stu.batch_id= '").append(batch_id)
					.append("' ");
		}

		condition = conditionbuffer.toString();

		sql = "select a.edutype_id as edutype_id, a.major_id as major_id,a.edutype_name as edutype_name,a.major_name as major_name,nvl(b.baoming,0) as baoming,"
				+ " nvl(c.baoming_confer,0) as baoming_confer,nvl(d.baoming_confer_free,0) as baoming_confer_free "
				+ " from (select distinct stu.edutype_id as edutype_id, stu.major_id as major_id,edutype.name as edutype_name,"
				+ " major.name as major_name from recruit_student_info  stu,entity_edu_type edutype,entity_major_info major where stu.edutype_id=edutype.id "
				+ " and stu.major_id=major.id  "
				+ condition
				+ ") a,"
				+ " (select  stu.edutype_id as edutype_id,stu.major_id as major_id, nvl(count(stu.id),0) as baoming "
				+ " from recruit_student_info  stu,entity_edu_type edutype,entity_major_info major where"
				+ " stu.edutype_id=edutype.id and stu.major_id=major.id  "
				+ condition
				+ " group by stu.edutype_id, stu.major_id) b,"
				+ "(select  stu.edutype_id as edutype_id,stu.major_id as major_id, "
				+ " nvl(count(stu.id),0) as baoming_confer from recruit_student_info  stu,entity_edu_type edutype,"
				+ " entity_major_info major where stu.edutype_id=edutype.id and stu.major_id=major.id and "
				+ " stu.status='1' "
				+ condition
				+ "  group by stu.edutype_id, stu.major_id) c,"
				+ " (select  stu.edutype_id as edutype_id,stu.major_id as major_id,  nvl(count(stu.id),0) as baoming_confer_free"
				+ " from recruit_student_info  stu,entity_edu_type edutype,entity_major_info major where "
				+ "	stu.edutype_id=edutype.id and stu.major_id=major.id and stu.considertype_status='2'  "
				+ condition
				+ "  group by stu.edutype_id, stu.major_id) d  "
				+ " where (a.edutype_id=b.edutype_id(+) and a.major_id=b.major_id(+)) and "
				+ "(a.edutype_id=c.edutype_id(+) and a.major_id=c.major_id(+)) and (a.edutype_id=d.edutype_id(+) and a.major_id=d.major_id(+))";

		count = db.countselect(sql);
		EntityLog.setDebug(sql);
		return count;
	}

	public List getTotalTestRoom(Page page, String batchId, String edutype_id,
			String major_id, String site_id) {
		dbpool db = new dbpool();
		String sql = "";
		List conditions = new ArrayList();
		if (batchId != null && !batchId.equals(""))
			conditions.add(new SearchProperty("batch_id", batchId, "="));
		if (edutype_id != null && !edutype_id.equals(""))
			conditions.add(new SearchProperty("edutype_id", edutype_id, "="));
		if (major_id != null && !major_id.equals(""))
			conditions.add(new SearchProperty("major_id", major_id, "="));
		if (site_id != null && !site_id.equals("")) {
			sql = "select edutype_id,major_id,batch_id,edutype_name,major_name,roomnums,nums "
					+ "from (select rem.edutype_id,rem.major_id,rem.batch_id,eet.name as edutype_name,emi.name as major_name,"
					+ "rem.roomnums,rem.nums from (select a.edutype_id,a.major_id,a.batch_id,a.roomnums,b.nums "
					+ "from (select edutype_id,major_id,batch_id,count(*) as roomnums  "
					+ "from (select distinct rtd.testroom_id,rsi.edutype_id,rsi.major_id,rsi.batch_id "
					+ "from recruit_test_desk rtd,(select si.id,si.edutype_id,si.major_id,si.site_id ,si.batch_id from recruit_student_info si where si.considertype_status<>'2' and si.status='1' and si.batch_id='"
					+ batchId
					+ "' ) rsi where rsi.site_id='"
					+ site_id
					+ "' and rtd.recruitstudent_id=rsi.id  and rtd.testroom_id is not null) group by edutype_id,major_id,batch_id) a "
					+ "left join (select edutype_id,major_id ,batch_id,count(*) as nums from (select distinct rsi.id,rsi.edutype_id,rsi.major_id,rsi.batch_id "
					+ "from recruit_test_desk rtd,(select si.id,si.edutype_id,si.major_id,si.site_id,si.batch_id from recruit_student_info si where si.considertype_status<>'2' and si.status='1' and si.batch_id='"
					+ batchId
					+ "') rsi where rsi.site_id='"
					+ site_id
					+ "' and rtd.recruitstudent_id=rsi.id  and rtd.testroom_id is not null) group by edutype_id,major_id,batch_id) b "
					+ "on a.edutype_id=b.edutype_id and a.major_id=b.major_id ) rem,entity_edu_type eet,entity_major_info emi "
					+ "where rem.edutype_id=eet.id and rem.major_id=emi.id)"
					+ Conditions.convertToCondition(conditions, null);
		} else {
			sql = "select edutype_id,major_id,batch_id,edutype_name,major_name,roomnums,nums "
					+ "from (select rem.edutype_id,rem.major_id,rem.batch_id,eet.name as edutype_name,emi.name as major_name,"
					+ "rem.roomnums,rem.nums from (select a.edutype_id,a.major_id,a.batch_id,a.roomnums,b.nums "
					+ "from (select edutype_id,major_id,batch_id,count(*) as roomnums  "
					+ "from (select distinct rtd.testroom_id,rsi.edutype_id,rsi.major_id,rsi.batch_id "
					+ "from recruit_test_desk rtd,(select si.id,si.edutype_id,si.major_id,si.site_id ,si.batch_id from recruit_student_info si where si.considertype_status<>'2' and si.status='1' and si.batch_id='"
					+ batchId
					+ "' ) rsi where rtd.recruitstudent_id=rsi.id  and rtd.testroom_id is not null) group by edutype_id,major_id,batch_id) a "
					+ "left join (select edutype_id,major_id ,batch_id,count(*) as nums from (select distinct rsi.id,rsi.edutype_id,rsi.major_id,rsi.batch_id "
					+ "from recruit_test_desk rtd,(select si.id,si.edutype_id,si.major_id,si.site_id,si.batch_id from recruit_student_info si where si.considertype_status<>'2' and si.status='1' and si.batch_id='"
					+ batchId
					+ "') rsi where rtd.recruitstudent_id=rsi.id  and rtd.testroom_id is not null) group by edutype_id,major_id,batch_id) b "
					+ "on a.edutype_id=b.edutype_id and a.major_id=b.major_id ) rem,entity_edu_type eet,entity_major_info emi "
					+ "where rem.edutype_id=eet.id and rem.major_id=emi.id)"
					+ Conditions.convertToCondition(conditions, null);
		}
		// System.out.println(sql);
		MyResultSet rs = null;
		ArrayList testRooms = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("edutype_id"));
				list.add(rs.getString("edutype_name"));
				list.add(rs.getString("major_id"));
				list.add(rs.getString("major_name"));
				list.add(rs.getString("roomnums"));
				list.add(rs.getString("nums"));
				OracleSite site = new OracleSite(site_id);
				list.add(site_id);
				list.add(site.getName());
				testRooms.add(list);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testRooms;
	}

	public int getTotalTestRoomNums(String batchId, String edutype_id,
			String major_id, String site_id) {
		dbpool db = new dbpool();
		String sql = "";
		List conditions = new ArrayList();
		if (batchId != null && !batchId.equals(""))
			conditions.add(new SearchProperty("batch_id", batchId, "="));
		if (edutype_id != null && !edutype_id.equals(""))
			conditions.add(new SearchProperty("edutype_id", edutype_id, "="));
		if (major_id != null && !major_id.equals(""))
			conditions.add(new SearchProperty("major_id", major_id, "="));
		if (site_id != null && !site_id.equals("")) {
			sql = "select edutype_id,major_id,batch_id,edutype_name,major_name,roomnums,nums "
					+ "from (select rem.edutype_id,rem.major_id,rem.batch_id,eet.name as edutype_name,emi.name as major_name,"
					+ "rem.roomnums,rem.nums from (select a.edutype_id,a.major_id,a.batch_id,a.roomnums,b.nums "
					+ "from (select edutype_id,major_id,batch_id,count(*) as roomnums  "
					+ "from (select distinct rtd.testroom_id,rsi.edutype_id,rsi.major_id,rsi.batch_id "
					+ "from recruit_test_desk rtd,(select si.id,si.edutype_id,si.major_id,si.site_id ,si.batch_id from recruit_student_info si where si.considertype_status='2' and si.status='1' and si.batch_id='"
					+ batchId
					+ "' union select si.id,si.edutype_id,si.major_id,si.site_id ,si.batch_id from recruit_student_info si where si.considertype_status<>'2' and si.status='1' and si.batch_id='"
					+ batchId
					+ "' ) rsi where rsi.site_id='"
					+ site_id
					+ "' and rtd.recruitstudent_id=rsi.id  and rtd.testroom_id is not null) group by edutype_id,major_id,batch_id) a "
					+ "left join (select edutype_id,major_id ,batch_id,count(*) as nums from (select distinct rsi.id,rsi.edutype_id,rsi.major_id,rsi.batch_id "
					+ "from recruit_test_desk rtd,(select si.id,si.edutype_id,si.major_id,si.site_id,si.batch_id from recruit_student_info si where si.considertype_status<>'2' and si.status='1' and si.batch_id='"
					+ batchId
					+ "') rsi where rsi.site_id='"
					+ site_id
					+ "' and rtd.recruitstudent_id=rsi.id  and rtd.testroom_id is not null) group by edutype_id,major_id,batch_id) b "
					+ "on a.edutype_id=b.edutype_id and a.major_id=b.major_id ) rem,entity_edu_type eet,entity_major_info emi "
					+ "where rem.edutype_id=eet.id and rem.major_id=emi.id)"
					+ Conditions.convertToCondition(conditions, null);
		} else {
			sql = "select edutype_id,major_id,batch_id,edutype_name,major_name,roomnums,nums "
					+ "from (select rem.edutype_id,rem.major_id,rem.batch_id,eet.name as edutype_name,emi.name as major_name,"
					+ "rem.roomnums,rem.nums from (select a.edutype_id,a.major_id,a.batch_id,a.roomnums,b.nums "
					+ "from (select edutype_id,major_id,batch_id,count(*) as roomnums  "
					+ "from (select distinct rtd.testroom_id,rsi.edutype_id,rsi.major_id,rsi.batch_id "
					+ "from recruit_test_desk rtd,(select si.id,si.edutype_id,si.major_id,si.site_id ,si.batch_id from recruit_student_info si where si.considertype_status<>'2' and si.status='1' and si.batch_id='"
					+ batchId
					+ "' ) rsi where rtd.recruitstudent_id=rsi.id  and rtd.testroom_id is not null) group by edutype_id,major_id,batch_id) a "
					+ "left join (select edutype_id,major_id ,batch_id,count(*) as nums from (select distinct rsi.id,rsi.edutype_id,rsi.major_id,rsi.batch_id "
					+ "from recruit_test_desk rtd,(select si.id,si.edutype_id,si.major_id,si.site_id,si.batch_id from recruit_student_info si where si.considertype_status<>'2' and si.status='1' and si.batch_id='"
					+ batchId
					+ "') rsi where rtd.recruitstudent_id=rsi.id  and rtd.testroom_id is not null) group by edutype_id,major_id,batch_id) b "
					+ "on a.edutype_id=b.edutype_id and a.major_id=b.major_id ) rem,entity_edu_type eet,entity_major_info emi "
					+ "where rem.edutype_id=eet.id and rem.major_id=emi.id)"
					+ Conditions.convertToCondition(conditions, null);
		}
		int i = db.countselect(sql);

		return i;
	}

	public List getTotalTestRoom(Page page, List con) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select site_id,site_name,batch_id,num,roomnum from (select aa.site_id,cc.name as site_name,aa.batch_id,aa.num,nvl(bb.roomnum,'0') as roomnum from (select site_id,batch_id,count(a.id) as num from (select id,site_id ,batch_id from recruit_student_info where considertype_status<>'2' and status='1' "
				+ Conditions.convertToAndCondition(con, null)
				+ ") a,(select distinct recruitstudent_id from recruit_test_desk) b where a.id=b.recruitstudent_id group by site_id,batch_id) aa,(select b1.testsite_id as site_id,b1.batch_id,count(b1.id) as roomnum from (select distinct site_id ,batch_id from recruit_student_info where considertype_status<>'2' and status='1' "
				+ Conditions.convertToAndCondition(con, null)
				+ ") a1,recruit_test_room b1 where a1.site_id=b1.testsite_id and a1.batch_id=b1.batch_id group by b1.testsite_id,b1.batch_id) bb,entity_site_info cc where aa.site_id=bb.site_id(+) and aa.batch_id=bb.batch_id(+) and aa.site_id=cc.id) order by site_id";
		MyResultSet rs = null;
		ArrayList testRooms = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("site_id"));
				list.add(rs.getString("site_name"));
				list.add(rs.getString("batch_id"));
				list.add(rs.getString("num"));
				list.add(rs.getString("roomnum"));
				testRooms.add(list);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testRooms;
	}

	public List getTestRooms(Page page, List con) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select room_id,room_no,edutype_name,sort_name from (select distinct b.id as room_id,b.room_no,e.name as edutype_name,d.name as sort_name from (select id,site_id ,batch_id,edutype_id from recruit_student_info where considertype_status<>'2' and status='1' "
				+ Conditions.convertToAndCondition(con, null)
				+ ") a,recruit_test_room b,recruit_test_desk c,recruit_sort_info d,entity_edu_type e where a.site_id=b.testsite_id and a.batch_id=b.batch_id and a.id=c.recruitstudent_id and c.testroom_id=b.id and a.edutype_id=e.id and b.sort_id=d.id) order by room_no";
		MyResultSet rs = null;
		ArrayList testRooms = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("room_id"));
				list.add(rs.getString("room_no"));
				list.add(rs.getString("edutype_name"));
				list.add(rs.getString("sort_name"));
				testRooms.add(list);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testRooms;
	}

	public List getTestDesks(Page page, List con) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select numbyroom,testcard_id,name from (select distinct a.numbyroom,b.testcard_id,b.name from recruit_test_desk a,recruit_student_info b where a.recruitstudent_id=b.id "
				+ Conditions.convertToAndCondition(con, null)
				+ ") order by to_number(numbyroom)";
		MyResultSet rs = null;
		ArrayList testDesks = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("numbyroom"));
				list.add(rs.getString("testcard_id"));
				list.add(rs.getString("name"));
				testDesks.add(list);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testDesks;
	}

	public int getTotalTestRoomNums(List con) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select site_id,site_name,batch_id,num,roomnum from (select aa.site_id,cc.name as site_name,aa.batch_id,aa.num,nvl(bb.roomnum,'0') as roomnum from (select site_id,batch_id,count(a.id) as num from (select id,site_id ,batch_id from recruit_student_info where considertype_status<>'2' and status='1' "
				+ Conditions.convertToAndCondition(con, null)
				+ ") a,(select distinct recruitstudent_id from recruit_test_desk) b where a.id=b.recruitstudent_id group by site_id,batch_id) aa,(select b1.testsite_id as site_id,b1.batch_id,count(b1.id) as roomnum from (select distinct site_id ,batch_id from recruit_student_info where considertype_status<>'2' and status='1' "
				+ Conditions.convertToAndCondition(con, null)
				+ ") a1,recruit_test_room b1 where a1.site_id=b1.testsite_id and a1.batch_id=b1.batch_id group by b1.testsite_id,b1.batch_id) bb,entity_site_info cc where aa.site_id=bb.site_id(+) and aa.batch_id=bb.batch_id(+) and aa.site_id=cc.id) order by site_id";
		int i = db.countselect(sql);
		return i;
	}

	public List getSortTestCourses(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = "select course_id,course_name,testcourse_id,sort_id,sort_name,testsequence_id "
				+ "from (select a.id as course_id,a.name as course_name,c.id as testcourse_id,b.sort_id,"
				+ "d.name as sort_name,c.testsequence_id from recruit_course_info a,recruit_sortcourse_relation b,"
				+ "recruit_test_course c,recruit_sort_info d where a.id=b.course_id and a.id=c.course_id and b.sort_id=d.id)"
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList testCourseList = null;
		try {
			db = new dbpool();
			testCourseList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestCourse testCourse = new OracleRecruitTestCourse();
				OracleRecruitCourse course = new OracleRecruitCourse();
				/*
				 * OracleRecruitSort sort = new OracleRecruitSort();
				 * sort.setId(rs.getString("sort_id"));
				 * sort.setName(rs.getString("sort_name"));
				 */course.setId(rs.getString("course_id"));
				course.setName(rs.getString("course_name"));
				testCourse.setId(rs.getString("testcourse_id"));
				OracleRecruitTestSequence testSequence = new OracleRecruitTestSequence();
				OracleRecruitTestBatch testBatch = new OracleRecruitTestBatch();
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("testsequence_id"));
				testBatch.setBatch(batch);
				testSequence.setTestBatch(testBatch);
				testCourse.setTestSequence(testSequence);
				testCourse.setCourse(course);
				testCourseList.add(testCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testCourseList;
	}

	public List getStudentTestDesks(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLSTUDENTTESTDESK
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList testDeskList = null;
		try {
			db = new dbpool();
			testDeskList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestDesk testDesk = new OracleRecruitTestDesk();
				OracleRecruitTestRoom testRoom = new OracleRecruitTestRoom();
				testRoom.setId(rs.getString("testroom_id"));
				testRoom.setRoomNo(rs.getString("room_no"));
				OracleRecruitTestSite testSite = new OracleRecruitTestSite();
				testSite.setName(rs.getString("testsite_name"));
				testRoom.setTestSite(testSite);
				testDesk.setTestRoom(testRoom);
				OracleRecruitTestCourse testCourse = new OracleRecruitTestCourse();
				testCourse.setId(rs.getString("course_id"));
				testCourse.setTitle(rs.getString("course_name"));
				testCourse.setTime(new TimeDef(rs.getString("startdate"), rs
						.getString("enddate")));
				testDesk.setTestCourse(testCourse);
				testDesk.setNumByRoom(rs.getInt("numbyroom"));

				OracleRecruitStudent student = new OracleRecruitStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				HumanNormalInfo normalInfo = new HumanNormalInfo();
				normalInfo.setIdcard(rs.getString("card_no"));
				normalInfo.setGender(rs.getString("gender"));
				Address work_address = new Address();
				work_address.setAddress(rs.getString("address"));
				normalInfo.setWork_address(work_address);
				student.setNormalInfo(normalInfo);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setTestCardId(rs.getString("testcard_id"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setPhoto_link(rs.getString("photo_link"));
				student.setEduInfo(eduInfo);
				testDesk.setStudent(student);
				testDeskList.add(testDesk);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return testDeskList;
	}

	public List getRecruitCourseScoreStandard(Page page, List searchProperty,
			List orderProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getPassStatistic(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();

		String sql = "select batch_id,edutype_id,edutype_name, major_id,major_name,site_id,site_name,pass_status,countId from (select distinct edutype_id,c.name as edutype_name,major_id,b.name as major_name,a.batch_id,a.status,a.site_id as site_id,d.name as site_name,a.pass_status,count(a.id) as countId from recruit_student_info a,entity_major_info b,entity_edu_type c,entity_site_info d where a.major_id=b.id and a.edutype_id=c.id and a.site_id=d.id and a.status='2'   group by a.batch_id,edutype_id,b.name,major_id,c.name,a.site_id,d.name,a.pass_status) "
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList studentStatistic = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				RecruitEduInfo eduInfo = new RecruitEduInfo();

				eduInfo.setTestCardId(rs.getString("countId"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setPassStatus(rs.getString("pass_status"));
				studentStatistic.add(eduInfo);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return studentStatistic;
	}

	public int getPassStatisticNum(List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = "select batch_id,edutype_id,edutype_name, major_id,major_name,site_id,site_name,pass_status,countId from (select distinct edutype_id,c.name as edutype_name,major_id,b.name as major_name,a.batch_id,a.status,a.site_id as site_id,d.name as site_name,a.pass_status,count(a.id) as countId from recruit_student_info a,entity_major_info b,entity_edu_type c,entity_site_info d where a.major_id=b.id and a.edutype_id=c.id and a.site_id=d.id and a.status='2'   group by a.batch_id,edutype_id,b.name,major_id,c.name,a.site_id,d.name,a.pass_status) "
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		EntityLog.setDebug(sql);
		int i = db.countselect(sql);
		return i;
	}

	public List getPassStuId(List searchProperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List stuId = new ArrayList();
		String sql = "";
		sql = SQLSIGNSTATISTICNUM
				+ Conditions.convertToCondition(searchProperty, null);
		EntityLog.setDebug(sql);
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				stuId.add(rs.getString("id"));

			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}

		return stuId;
	}

	public int updateRecruitStudentPassstatus(String stuId, String passstatus) {
		dbpool db = new dbpool();

		String sql = "";
		sql = "update recruit_student_info set pass_status='" + passstatus
				+ "' where id='" + stuId + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleBasicRecruitList.updateRecruitStudentPassstatus(String stuId, String passstatus) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getTotalStu(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();

		String sql = SQLTATOLSTU
				+ Conditions.convertToCondition(searchProperty, null);
		MyResultSet rs = null;
		ArrayList studentStatistic = new ArrayList();
		try {
			db = new dbpool();

			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				RecruitEduInfo eduInfo = new RecruitEduInfo();

				eduInfo.setClass_id(rs.getString("countId"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setSite_id(rs.getString("site_id"));
				studentStatistic.add(eduInfo);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return studentStatistic;
	}

	public int getTotalStuNum(List searchProperty) {
		dbpool db = new dbpool();

		String sql = "";
		sql = SQLTATOLSTU + Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getStatStu(String batch_id, String site_id, String edutype_id,
			String major_id) {

		StringBuffer conditions = new StringBuffer();
		if (batch_id != null && !batch_id.equals(""))
			conditions.append(" and batch_id='").append(batch_id).append("'");

		if (edutype_id != null && !edutype_id.equals(""))
			conditions.append(" and edutype_id='").append(edutype_id).append(
					"'");
		if (major_id != null && !major_id.equals(""))
			conditions.append(" and major_id='").append(major_id).append("'");
		if (site_id != null && !site_id.equals(""))
			conditions.append(" and site_id='").append(site_id).append("'");

		String condition = conditions.toString();

		dbpool db = new dbpool();
		String sql = "";
		sql = "select a.site_name as site_name,a.edutype_name as edutype_name,a.major_name as major_name,"
				+ "nvl(a.total,0) as total,nvl(b.pass,0) as pass,nvl(c.nopass,0) as nopass from "
				+ "(select edutype_id,edutype_name, major_id,major_name,site_id,site_name,total from "
				+ "(select distinct edutype_id,c.name as edutype_name,major_id,b.name as major_name,a.batch_id as batch_id,a.status,"
				+ "a.site_id as site_id,d.name as site_name,count(a.id) as total from "
				+ "recruit_student_info a,entity_major_info b,entity_edu_type c,entity_site_info d where a.major_id=b.id "
				+ "and a.edutype_id=c.id and a.site_id=d.id "
				+ condition
				+ " group by edutype_id,b.name,major_id,c.name,a.site_id,d.name) ) a ,"
				+ "(select edutype_id,edutype_name, major_id,major_name,site_id,site_name,pass from "
				+ "(select distinct edutype_id,c.name as edutype_name,major_id,b.name as major_name,a.batch_id,"
				+ "a.status,a.site_id as site_id,d.name as site_name,count(a.id) as pass from "
				+ "recruit_student_info a,entity_major_info b,entity_edu_type c,entity_site_info d "
				+ "where a.major_id=b.id and a.edutype_id=c.id and a.site_id=d.id and a.status='2'  "
				+ condition
				+ " group by edutype_id,b.name,major_id,c.name,a.site_id,d.name)  ) b,"
				+ "(select edutype_id,edutype_name, major_id,major_name,site_id,site_name,nopass from"
				+ " (select distinct edutype_id,c.name as edutype_name,major_id,b.name as major_name,a.batch_id,a.status,"
				+ "a.site_id as site_id,d.name as site_name,count(a.id) as nopass from "
				+ "recruit_student_info a,entity_major_info b,entity_edu_type c,entity_site_info d "
				+ "where a.major_id=b.id and a.edutype_id=c.id and a.site_id=d.id and a.status<>'2' "
				+ condition
				+ "group by edutype_id,b.name,major_id,c.name,a.site_id,d.name) ) c "
				+ "where (a.site_name=b.site_name(+) and a.edutype_name=b.edutype_name(+) and a.major_name=b.major_name(+)) "
				+ "and (a.site_name=c.site_name(+) and a.edutype_name=c.edutype_name(+) and a.major_name=c.major_name(+) )";
		MyResultSet rs = null;
		ArrayList studentStatistic = new ArrayList();
		try {
			db = new dbpool();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				HashMap hash = new HashMap();
				hash.put("site_name", rs.getString("site_name"));
				hash.put("edutype_name", rs.getString("edutype_name"));
				hash.put("major_name", rs.getString("major_name"));
				hash.put("total", rs.getString("total"));
				hash.put("pass", rs.getString("pass"));
				hash.put("nopass", rs.getString("nopass"));
				studentStatistic.add(hash);

			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;

		}
		return studentStatistic;
	}

	public HashMap getRecruitStat(String batchId) throws SQLException {
		HashMap stat = new HashMap();
		dbpool db = new dbpool();
		String sql = "";
		String sqlEduType = "select id,name from entity_edu_type "
				+ "where id in(select distinct edutype_id from recruit_student_info where batch_id='"
				+ batchId + "') order by id";
		String sqlMajor = "select edutype_id,edutype_name,major_id,major_name from "
				+ "(select distinct a.id as edutype_id,a.name as edutype_name,b.id as major_id,b.name as major_name "
				+ "from entity_edu_type a,entity_major_info b,recruit_student_info c "
				+ "where a.id=c.edutype_id and b.id=c.major_id and c.batch_id='"
				+ batchId + "') order by edutype_id,major_id";
		String sqlSite = "select id,name from entity_site_info "
				+ "where id in(select distinct site_id from recruit_student_info where batch_id='"
				+ batchId + "') order by id";
		MyResultSet rs = null;
		MyResultSet rsEduType = db.executeQuery(sqlEduType);
		MyResultSet rsMajor = db.executeQuery(sqlMajor);
		MyResultSet rsSite = null;
		int countEduType = db.countselect(sqlEduType);
		int countMajor = db.countselect(sqlMajor);
		if (countEduType > 0 && countMajor > 0) {
			String[][] eduType = new String[countEduType][3];
			String[][] major = new String[countMajor + countEduType][3];
			int totalCount = 0;
			int width = 0;
			int count = 0;
			String eduTypeId = "";
			String eduTypeName = "";
			String majorId = "";
			String siteId = "";
			String siteName = "";

			while (rsEduType != null && rsEduType.next()) {
				eduTypeId = rsEduType.getString("id");
				eduTypeName = rsEduType.getString("name");
				while (rsMajor != null && rsMajor.next()) {
					if (eduTypeId.equals(rsMajor.getString("edutype_id"))) {
						major[totalCount][0] = rsMajor.getString("edutype_id");
						major[totalCount][1] = rsMajor.getString("major_id");
						major[totalCount][2] = rsMajor.getString("major_name");
						width++;
						totalCount++;
					} else {
						eduType[count][0] = eduTypeId;
						eduType[count][1] = eduTypeName;
						eduType[count][2] = Integer.toString(width + 1);
						major[totalCount][0] = eduTypeId;
						major[totalCount][1] = "total";
						major[totalCount][2] = eduTypeName + "С";
						totalCount++;
						major[totalCount][0] = rsMajor.getString("edutype_id");
						major[totalCount][1] = rsMajor.getString("major_id");
						major[totalCount][2] = rsMajor.getString("major_name");
						totalCount++;
						eduTypeId = rsMajor.getString("edutype_id");
						eduTypeName = rsMajor.getString("edutype_name");
						width = 1;
						count++;
					}
				}
			}
			db.close(rsMajor);
			db.close(rsEduType);
			eduType[count][0] = eduTypeId;
			eduType[count][1] = eduTypeName;
			eduType[count][2] = Integer.toString(width + 1);
			major[totalCount][0] = eduTypeId;
			major[totalCount][1] = "total";
			major[totalCount][2] = eduTypeName + "С";
			count++;
			totalCount++;
			stat.put("eduType", eduType);
			stat.put("eduTypeNum", Integer.toString(count));
			stat.put("major", major);
			stat.put("majorNum", Integer.toString(totalCount));
			int maxSite = db.countselect(sqlSite);
			String[][] studentsNum = new String[maxSite + 1][totalCount + 2];
			for (int j = 0; j < maxSite + 1; j++) {
				for (int i = 0; i < totalCount + 2; i++) {
					studentsNum[j][i] = "0";
				}
			}
			int countSite = 0;
			int num = 0;
			int totalNum = 0;
			rsSite = db.executeQuery(sqlSite);
			while (rsSite != null && rsSite.next()) {
				siteId = rsSite.getString("id");
				siteName = rsSite.getString("name");
				studentsNum[countSite][0] = siteName;
				sql = "select num,edutype_id,major_id from (select count(a.id) as num,edutype_id,major_id from recruit_student_info a,entity_edu_type b,entity_major_info c where site_id='"
						+ siteId
						+ "' and  batch_id='"
						+ batchId
						+ "' and a.status='2' and a.edutype_id=b.id and a.major_id=c.id group by edutype_id,major_id order by edutype_id,major_id)";
				rs = db.executeQuery(sql);
				int i = 0;
				totalNum = 0;
				while (rs != null && rs.next()) {
					eduTypeId = rs.getString("edutype_id");
					majorId = rs.getString("major_id");
					for (; i < totalCount; i++) {
						if (eduTypeId.equals(major[i][0])
								&& majorId.equals(major[i][1])) {
							studentsNum[countSite][i + 1] = rs.getString("num");
							num += Integer.parseInt(rs.getString("num"));
							totalNum += Integer.parseInt(rs.getString("num"));
							studentsNum[maxSite][i + 1] = Integer
									.toString(Integer
											.parseInt(studentsNum[maxSite][i + 1])
											+ Integer.parseInt(rs
													.getString("num")));
							i++;
							break;
						} else if (major[i][1].equals("total")) {
							studentsNum[countSite][i + 1] = Integer
									.toString(num);
							studentsNum[maxSite][i + 1] = Integer
									.toString(Integer
											.parseInt(studentsNum[maxSite][i + 1])
											+ num);
							num = 0;
						}
					}
				}
				db.close(rs);
				for (; i < totalCount; i++) {
					if (major[i][1].equals("total")) {
						studentsNum[countSite][i + 1] = Integer.toString(num);
						studentsNum[maxSite][i + 1] = Integer.toString(Integer
								.parseInt(studentsNum[maxSite][i + 1])
								+ num);
						num = 0;
					}
				}
				/*
				 * studentsNum[countSite][totalCount] = Integer.toString(num);
				 * studentsNum[maxSite][totalCount] = Integer.toString(Integer
				 * .parseInt(studentsNum[maxSite][totalCount]) + num);
				 */studentsNum[countSite][totalCount + 1] = Integer
						.toString(totalNum);
				studentsNum[maxSite][totalCount + 1] = Integer.toString(Integer
						.parseInt(studentsNum[maxSite][totalCount + 1])
						+ totalNum);
				countSite++;
			}
			db.close(rsSite);
			countSite++;
			stat.put("studentsNum", studentsNum);
			stat.put("siteNum", Integer.toString(countSite));
		}
		return stat;
	}

	public List getMatricaluteCondition(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List recruitmat = null;

		String sql = "select id,major_id,edutype_id,score,batch_id from (select b.id,a.major_id as major_id,a.edutype_id,b.score,b.batch_id from (select distinct rsi.major_id,rsi.edutype_id,rsi.batch_id from recruit_student_info rsi,recruit_sort_info s,recruit_majorsort_relation m where s.edutype_id=rsi.edutype_id and s.id=m.sort_id and m.major_id=rsi.major_id "
				+ Conditions.convertToAndCondition(searchProperty, null)
				+ " ) a left join (select rsi.* from recruit_matriculate_condition rsi,recruit_sort_info s,recruit_majorsort_relation m where s.edutype_id=rsi.edutype_id and s.id=m.sort_id and m.major_id=rsi.major_id "
				+ Conditions.convertToAndCondition(searchProperty, null)
				+ " ) b on a.major_id=b.major_id and b.batch_id=a.batch_id and a.edutype_id=b.edutype_id ) ";
		try {

			recruitmat = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}

			while (rs != null && rs.next()) {
				RecruitMatricaluteCondition recruitMatcon = new OracleRecruitMatricaluteCondition();

				recruitMatcon.setId(rs.getString("id"));
				OracleRecruitBatch recruitBatch = new OracleRecruitBatch();
				recruitBatch.setId(rs.getString("batch_id"));
				recruitMatcon.setBatch(recruitBatch);
				OracleMajor major = new OracleMajor(rs.getString("major_id"));
				// major.setId(rs.getString("major_id"));
				recruitMatcon.setMajor(major);
				OracleEduType eduType = new OracleEduType(rs
						.getString("edutype_id"));
				// eduType.setId(rs.getString("edutype_id"));
				recruitMatcon.setEduType(eduType);
				recruitMatcon.setScore(rs.getString("score"));

				recruitmat.add(recruitMatcon);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleRecruitMatricaluteCondition error"
					+ sql_condition);
		} finally {
			db.close(rs);
			db = null;
		}
		return recruitmat;
	}

	public int getMatricaluteConditionNum(List searchProperty) {

		dbpool db = new dbpool();

		String sql = "select id,major_id,edutype_id,score,batch_id from (select b.id,a.major_id as major_id,a.edutype_id,b.score,b.batch_id from (select distinct rsi.major_id,rsi.edutype_id,rsi.batch_id from recruit_student_info rsi,recruit_sort_info s,recruit_majorsort_relation m where s.edutype_id=rsi.edutype_id and s.id=m.sort_id and m.major_id=rsi.major_id "
				+ Conditions.convertToAndCondition(searchProperty, null)
				+ " ) a left join (select rsi.* from recruit_matriculate_condition rsi,recruit_sort_info s,recruit_majorsort_relation m where s.edutype_id=rsi.edutype_id and s.id=m.sort_id and m.major_id=rsi.major_id "
				+ Conditions.convertToAndCondition(searchProperty, null)
				+ " ) b on a.major_id=b.major_id and b.batch_id=a.batch_id and a.edutype_id=b.edutype_id ) ";

		int i = db.countselect(sql);
		return i;

	}

	public List getMatricaluteConditionTotalScore(List searchProperty) {

		dbpool db = new dbpool();
		MyResultSet rs = null;
		List recruitmat = null;

		String sql = "select id,score,score1,photo_status,idcard_status,graduatecard_status from recruit_matriculate_condition "
				+ Conditions.convertToCondition(searchProperty, null);

		rs = db.executeQuery(sql);
		try {

			recruitmat = new ArrayList();
			while (rs != null && rs.next()) {
				RecruitMatricaluteCondition recruitMatcon = new OracleRecruitMatricaluteCondition();
				recruitMatcon.setId(rs.getString("id"));
				recruitMatcon.setScore(rs.getString("score"));
				recruitMatcon.setScore1(rs.getString("score1"));
				recruitMatcon.setPhoto_status(rs.getString("photo_status"));
				recruitMatcon.setIdcard_status(rs.getString("idcard_status"));
				recruitMatcon.setGraduatecard_status(rs
						.getString("graduatecard_status"));
				recruitmat.add(recruitMatcon);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleRecruitMatricaluteCondition error"
					+ sql_condition);
		} finally {
			db.close(rs);
			db = null;
		}
		return recruitmat;
	}

	public List getMatricaluteCourseScore(String mc_id) {

		dbpool db = new dbpool();
		MyResultSet rs = null;
		List recruitmat = null;

		String sql = "select  b.id as id ,a.name as course_name,b.score as score,b.mc_id as mc_id from recruit_course_info a,recruit_matriculate_course b where a.id=b.course_id and b.mc_id='"
				+ mc_id + "'";

		rs = db.executeQuery(sql);
		try {

			recruitmat = new ArrayList();
			while (rs != null && rs.next()) {

				RecruitMatricaluteCourse recruitCourse = new OracleRecruitMatricaluteCourse();

				recruitCourse.setId(rs.getString("id"));
				recruitCourse.setCoureName(rs.getString("course_name"));
				recruitCourse.setScore(rs.getString("score"));

				recruitmat.add(recruitCourse);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("getMatricaluteCourseScore error" + sql_condition);
		} finally {
			db.close(rs);
			db = null;
		}
		return recruitmat;
	}

	public int updateStudentStatus(String status, String student_id) {
		dbpool db = new dbpool();
		String sql = "";
		if (status != null && status.equals("1"))
			sql = "update recruit_student_info set status='" + status
					+ "',reg_no = '',pass_status='0' where id ='" + student_id + "'";
		else
			sql = "update recruit_student_info set status='" + status
					+ "' where id ='" + student_id + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleBasicRecruitList.updateStudentStatus(String status, String student_id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateStudentStatus(String status, String student_id,
			String grade_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info set status='" + status
				+ "',grade_id='" + grade_id + "' where id ='" + student_id
				+ "'";

		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleBasicRecruitList.updateStudentStatus(String status, String student_id,String grade_id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int recruitStudent(String student_id, String grade_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info set status='2',password = card_no,grade_id='"
				+ grade_id + "' where id ='" + student_id + "'";
		EntityLog.setDebug("OracleBasicRecruitList@Method:recruitStudent()="
				+ sql);
		int i = db.executeUpdate(sql);
		return i;
	}

	public List getMatricaluteCourse(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List recruitmat = null;

		String sql_condition = "select distinct course_id,course_name,major_id,batch_id from(select a.id as course_id,a.name as course_name,b.major_id as major_id,d.testsequence_id as batch_id  from recruit_course_info a,recruit_majorsort_relation b,recruit_sortcourse_relation c,recruit_test_course d where a.id=c.course_id and b.sort_id=c.sort_id and a.id=d.course_id  )";
		sql_condition = sql_condition
				+ Conditions.convertToCondition(searchProperty, null);
		try {

			recruitmat = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql_condition, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql_condition);
			}

			while (rs != null && rs.next()) {
				RecruitMatricaluteCourse recruitCourse = new OracleRecruitMatricaluteCourse();

				recruitCourse.setId(rs.getString("course_id"));
				recruitCourse.setScore(rs.getString("course_name"));

				recruitmat.add(recruitCourse);
			}
		} catch (java.sql.SQLException e) {
			Log.setError("OracleRecruitMatricaluteCondition error"
					+ sql_condition);
		} finally {
			db.close(rs);
			db = null;
		}
		return recruitmat;
	}

	public String getIdByMajorandBatch(String batch_id, String major_id,
			String edutype_id) {
		dbpool db = new dbpool();
		String sql = "";
		MyResultSet rs = null;
		String id = "";
		sql = "select id  from recruit_matriculate_condition where batch_id='"
				+ batch_id + "' and major_id='" + major_id
				+ "' and  edutype_id='" + edutype_id + "'";
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				id = rs.getString("id");
			}
		} catch (Exception e) {
			
		}
		return id;
	}

	public int deleteByMajorandBatch(String batch_id, String major_id,
			String edutype_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_matriculate_condition where batch_id='"
				+ batch_id + "' and major_id='" + major_id
				+ "' and edutype_id='" + edutype_id + "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleBasicRecruitList.deleteByMajorandBatch(String batch_id, String major_id,String edutype_id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int deleteByMajor(String mc_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "delete from recruit_matriculate_course where mc_id='" + mc_id
				+ "'";
		int i = db.executeUpdate(sql);
		UserDeleteLog.setDebug("OracleBasicRecruitList.deleteByMajor(String mc_id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int updateMatricaluteCondition(String batch_id, String major_id,
			String edutype_id, String score, String score1,
			String photo_status, String idcard_status,
			String graduatecard_status) {
		dbpool db = new dbpool();
		String sql = "";

		sql = "insert into recruit_matriculate_condition (id,batch_id,major_id,edutype_id,score,score1,photo_status,idcard_status,graduatecard_status) values(to_char(s_matriculate_condition_id.nextval),'"
				+ batch_id
				+ "','"
				+ major_id
				+ "','"
				+ edutype_id
				+ "','"
				+ score
				+ "','"
				+ score1
				+ "','"
				+ photo_status
				+ "','"
				+ idcard_status + "','" + graduatecard_status + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleBasicRecruitList.updateMatricaluteCondition(String batch_id, String major_id,String edutype_id, String score, String score1,String photo_status, String idcard_status,String graduatecard_status) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int setMatricaluteCondition(String mc_id, String course_id,
			String score) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "insert into recruit_matriculate_course (id,mc_id,course_id,score) values(to_char(S_MATRICULATE_COURSE_ID.nextval),'"
				+ mc_id + "','" + course_id + "','" + score + "')";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleBasicRecruitList.setMatricaluteCondition(String mc_id, String course_id,String score) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int IsExsit(String mc_id) {
		dbpool db = new dbpool();
		int i = 0;
		String sql = "";
		sql = "select * from recruit_matriculate_course where mc_id='" + mc_id
				+ "'";
		i = db.countselect(sql);
		return i;

	}

	public int getTotalStuPassNum(List searchProperty) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		sql = SQLTATOLSTUPASS
				+ Conditions.convertToCondition(searchProperty, null);
		int count = 0;
		rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				count = rs.getInt("countId");
			}
		} catch (Exception e) {
			
		}
		return count;
	}

	public int updateStudentStatusBatch(String batch_id, String major_id,
			String edutype_id, String grade_id) {
		dbpool db = new dbpool();
		String sql = "";
		int i = 0;
		sql = "select s.id from recruit_student_info s,recruit_matriculate_condition m "
				+ "where s.edutype_id=m.edutype_id and s.photo_status>=m.photo_status and s.idcard_status>=m.idcard_status and s.graduatecard_status>=m.graduatecard_status and s.considertype_status<>'2' and s.status='1' and s.batch_id='"
				+ batch_id
				+ "'  and s.major_id='"
				+ major_id
				+ "' and s.edutype_id='"
				+ edutype_id
				+ "' and s.major_id=m.major_id"
				+ " and to_number(s.score)>= to_number(m.score) "
				+ " and to_number(s.score_1)>=to_number(m.score1) and to_number(s.score_2)>=to_number(m.score1) and to_number(s.score_3)>=to_number(m.score1) ";
		EntityLog.setDebug(sql);
		MyResultSet rs = db.executeQuery(sql);
		try {
			while (rs != null && rs.next()) {
				OracleRecruitStudent student = new OracleRecruitStudent(rs
						.getString("id"));
				// student.updateRegNo();
				OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
				i += basicdatalist.recruitStudent(rs.getString("id"), grade_id);

				/***************************************************************
				 * Ƕ֪ͨ
				 **************************************************************/
				/*
				 * 
				 * String regNo = student.getReg_no(); String psw =
				 * student.getPassword(); String mobilePhone =
				 * student.getNormalInfo().getMobilePhones(); SmsSendThread
				 * sendThread = new SmsSendThread(mobilePhone,
				 * "ϲѾйʯʹѧԶѧԺ¼ȡ!¼û:" + regNo + " :" +
				 * psw); // sendThread.start();
				 * 
				 */
			}
		} catch (SQLException e) {
		} finally {
			db.close(rs);
			db = null;
		}

		return i;
	}

	public int updateHandSingleStudentStatus(List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = "";

		sql = "update recruit_student_info  set status='1' where status='2' and id in ("
				+ SQLHANDSINGLESTUDENT
				+ Conditions.convertToCondition(searchProperty, null) + ")";
			int i = db.executeUpdate(sql);
			UserUpdateLog.setDebug("OracleBasicRecruitList.updateHandSingleStudentStatus(List searchProperty,List orderProperty) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
			return i;
	}

	public int updateFreeStudentStatus(List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info  set status='1' where status='2' and id in ("
				+ SQLFREESTUDENTSTATUS
				+ Conditions.convertToCondition(searchProperty, null) + ")";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleBasicRecruitList.updateFreeStudentStatus(List searchProperty, List orderProperty) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public List getRegisterStat(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = "select totalnum,r_num,site_id,site_name,major_id,major_name,edutype_id,edutype_name from "
				+ "(select a.totalnum,a.r_num,a.site_id,b.name as site_name,a.major_id,c.name as major_name,"
				+ "a.edutype_id,d.name as edutype_name from (select a.num as totalnum,nvl(b.num,0) as r_num,"
				+ "a.site_id,a.major_id,a.edutype_id from (select count(id) as num,site_id,major_id,edutype_id from "
				+ "(select id,site_id,major_id,edutype_id from recruit_student_info where status='2'"
				+ Conditions.convertToAndCondition(searchProperty, null)
				+ ") group by site_id,major_id,edutype_id) a,"
				+ "(select count(id) as num,site_id,major_id,edutype_id from (select id,site_id,major_id,edutype_id "
				+ "from recruit_student_info where status='2' and register_status='1' "
				+ Conditions.convertToAndCondition(searchProperty, null)
				+ ") group by site_id,major_id,edutype_id) b where a.site_id=b.site_id(+) "
				+ "and a.major_id=b.major_id(+) and a.edutype_id=b.edutype_id(+)) a,"
				+ "entity_site_info b,entity_major_info c,entity_edu_type d where a.site_id=b.id and "
				+ "a.major_id=c.id and a.edutype_id=d.id)";
		MyResultSet rs = null;
		ArrayList numList = null;
		String siteId = "";
		String siteName = "";
		String majorId = "";
		String majorName = "";
		String eduTypeId = "";
		String eduTypeName = "";
		int countA = 0;// registered num
		int countB = 0;// unregistered num
		int countTotal = 0;// all student num
		try {
			db = new dbpool();
			numList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				siteId = rs.getString("site_id");
				siteName = rs.getString("site_name");
				majorId = rs.getString("major_id");
				majorName = rs.getString("major_name");
				eduTypeId = rs.getString("edutype_id");
				eduTypeName = rs.getString("edutype_name");
				countTotal = rs.getInt("totalnum");
				countA = rs.getInt("r_num");
				countB = countTotal - countA;
				HashMap num = new HashMap();
				num.put("siteName", siteName);
				num.put("majorName", majorName);
				num.put("eduTypeName", eduTypeName);
				num.put("countTotal", Integer.toString(countTotal));
				num.put("countA", Integer.toString(countA));
				num.put("countB", Integer.toString(countB));
				numList.add(num);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return numList;
	}

	public int getRegisterStatNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select totalnum,r_num,site_id,site_name,major_id,major_name,edutype_id,edutype_name from "
				+ "(select a.totalnum,a.r_num,a.site_id,b.name as site_name,a.major_id,c.name as major_name,"
				+ "a.edutype_id,d.name as edutype_name from (select a.num as totalnum,nvl(b.num,0) as r_num,"
				+ "a.site_id,a.major_id,a.edutype_id from (select count(id) as num,site_id,major_id,edutype_id from "
				+ "(select id,site_id,major_id,edutype_id from recruit_student_info where status='2'"
				+ Conditions.convertToAndCondition(searchProperty, null)
				+ ") group by site_id,major_id,edutype_id) a,"
				+ "(select count(id) as num,site_id,major_id,edutype_id from (select id,site_id,major_id,edutype_id "
				+ "from recruit_student_info where status='2' and register_status='1' "
				+ Conditions.convertToAndCondition(searchProperty, null)
				+ ") group by site_id,major_id,edutype_id) b where a.site_id=b.site_id(+) "
				+ "and a.major_id=b.major_id(+) and a.edutype_id=b.edutype_id(+)) a,"
				+ "entity_site_info b,entity_major_info c,entity_edu_type d where a.site_id=b.id and "
				+ "a.major_id=c.id and a.edutype_id=d.id)";
		int i = db.countselect(sql);
		return i;
	}

	public List getRecruitPlanSites(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		List OrderProperties = new ArrayList();
		if (orderProperty == null) {
			OrderProperties.add(new OrderProperty("id"));
		} else {
			OrderProperties = orderProperty;
		}
		String sql = SQLRECRUITPLANSITE;
		String condition = Conditions.convertToAndCondition(searchProperty,
				null);
		sql = sql + condition + ") "
				+ Conditions.convertToCondition(null, OrderProperties);
		MyResultSet rs = null;
		List sites = new ArrayList();
		try {
			db = new dbpool();
			sites = new ArrayList();
			if (page != null) {
				int pageInt = page.getPageInt();
				int pageSize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageInt, pageSize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				Site site = new OracleSite();
				site.setId(rs.getString("id"));
				site.setName(rs.getString("name"));
				site.setAddress(rs.getString("address"));
				site.setEmail(rs.getString("email"));
				site.setFax(rs.getString("fax"));
				site.setLinkman(rs.getString("linkman"));
				site.setManager(rs.getString("manager"));
				site.setNote(rs.getString("note"));
				site.setPhone(rs.getString("phone"));
				site.setRecruit_status(rs.getString("recruit_status"));
				site.setStatus(rs.getString("status"));
				site.setZip_code(rs.getString("zip_code"));
				site.setURL(rs.getString("URL"));
				sites.add(site);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return sites;
	}

	public List getSignStatisticBySite(String batch_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sqledu = "select distinct b.id as edutype_id,b.name as edutype_name from recruit_batch_edu_major a,entity_edu_type b where a.batch_id='"
				+ batch_id + "' and a.edu_type_id=b.id order by b.id";
		String edutype_id = "";
		String sql1 = "select si.id as site_id,si.name as site_name,nvl(num,0) as num,nvl(num_c,0) as num_c,nvl(num_n,0) as num_n";
		String sql2 = " from entity_site_info si,(select count(id) as num,site_id from recruit_student_info where batch_id='"
				+ batch_id
				+ "' group by site_id) a,"
				+ "(select count(id) as num_c,site_id from recruit_student_info where batch_id='"
				+ batch_id
				+ "' and status='1' group by site_id) ac,"
				+ "(select count(id) as num_n,site_id from recruit_student_info where batch_id='"
				+ batch_id
				+ "' and considertype_status='2' group by site_id) an";
		String sql3 = " where a.site_id=si.id and a.site_id=ac.site_id(+) and a.site_id=an.site_id(+)";
		MyResultSet rs = null;
		List list = new ArrayList();
		List listedu = new ArrayList();
		try {
			List edutypeList = new ArrayList();
			rs = db.executeQuery(sqledu);
			while (rs != null && rs.next()) {
				edutype_id = rs.getString("edutype_id");
				edutypeList.add(rs.getString("edutype_name"));
				listedu.add(edutype_id);
				sql1 += ",nvl(num_" + edutype_id + ",0) as num_" + edutype_id
						+ ",nvl(num_" + edutype_id + "_c,0) as num_"
						+ edutype_id + "_c,nvl(num_" + edutype_id
						+ "_n,0) as num_" + edutype_id + "_n";
				sql2 += ",(select count(id) as num_"
						+ edutype_id
						+ ",site_id from recruit_student_info where batch_id='"
						+ batch_id
						+ "' and edutype_id='"
						+ edutype_id
						+ "' group by site_id) a"
						+ edutype_id
						+ ","
						+ "(select count(id) as num_"
						+ edutype_id
						+ "_c,site_id from recruit_student_info where batch_id='"
						+ batch_id
						+ "' and edutype_id='"
						+ edutype_id
						+ "' and status='1' group by site_id) a"
						+ edutype_id
						+ "c,"
						+ "(select count(id) as num_"
						+ edutype_id
						+ "_n,site_id from recruit_student_info where batch_id='"
						+ batch_id + "' and edutype_id='" + edutype_id
						+ "' and considertype_status='2' group by site_id) a"
						+ edutype_id + "n";
				sql3 += "and a.site_id=a" + edutype_id
						+ ".site_id(+) and a.site_id=a" + edutype_id
						+ "c.site_id(+) and a.site_id=a" + edutype_id
						+ "n.site_id(+)";
			}
			list.add(edutypeList);
		} catch (Exception e) {
		} finally {
			db.close(rs);
		}
		int total[] = new int[listedu.size() * 3 + 3];
		String sql = sql1 + sql2 + sql3;
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				List item = new ArrayList();
				item.add(rs.getString("site_name"));
				int j = 0;
				for (int i = 0; i < listedu.size(); i++) {
					edutype_id = (String) listedu.get(i);
					total[j++] += rs.getInt("num_" + edutype_id);
					item.add(rs.getString("num_" + edutype_id));
					total[j++] += rs.getInt("num_" + edutype_id + "_c");
					item.add(rs.getString("num_" + edutype_id + "_c"));
					total[j++] += rs.getInt("num_" + edutype_id + "_n");
					item.add(rs.getString("num_" + edutype_id + "_n"));
				}
				total[j++] += rs.getInt("num");
				item.add(rs.getString("num"));
				total[j++] += rs.getInt("num_c");
				item.add(rs.getString("num_c"));
				total[j++] += rs.getInt("num_n");
				item.add(rs.getString("num_n"));
				list.add(item);
			}
			List item = new ArrayList();
			item.add("ϼ");
			for (int i = 0; i < total.length; i++) {
				item.add(Integer.toString(total[i]));
			}
			list.add(item);
		} catch (Exception e) {
		} finally {
			db.close(rs);
		}
		return list;
	}

	public List getSignStatisticByMajor(String batch_id)
			throws PlatformException {
		dbpool db = new dbpool();
		String sqledu = "select distinct b.id as edutype_id,b.name as edutype_name from recruit_batch_edu_major a,entity_edu_type b where a.batch_id='"
				+ batch_id + "' and a.edu_type_id=b.id order by b.id";
		String edutype_id = "";
		String sql1 = "select si.id as major_id,si.name as major_name,nvl(num,0) as num,nvl(num_c,0) as num_c,nvl(num_n,0) as num_n";
		String sql2 = " from entity_major_info si,(select count(id) as num,major_id from recruit_student_info where batch_id='"
				+ batch_id
				+ "' group by major_id) a,"
				+ "(select count(id) as num_c,major_id from recruit_student_info where batch_id='"
				+ batch_id
				+ "' and status='1' group by major_id) ac,"
				+ "(select count(id) as num_n,major_id from recruit_student_info where batch_id='"
				+ batch_id
				+ "' and considertype_status='2' group by major_id) an";
		String sql3 = " where a.major_id=si.id and a.major_id=ac.major_id(+) and a.major_id=an.major_id(+)";
		MyResultSet rs = null;
		List list = new ArrayList();
		List listedu = new ArrayList();
		try {
			List edutypeList = new ArrayList();
			rs = db.executeQuery(sqledu);
			while (rs != null && rs.next()) {
				edutype_id = rs.getString("edutype_id");
				edutypeList.add(rs.getString("edutype_name"));
				listedu.add(edutype_id);
				sql1 += ",nvl(num_" + edutype_id + ",0) as num_" + edutype_id
						+ ",nvl(num_" + edutype_id + "_c,0) as num_"
						+ edutype_id + "_c,nvl(num_" + edutype_id
						+ "_n,0) as num_" + edutype_id + "_n";
				sql2 += ",(select count(id) as num_"
						+ edutype_id
						+ ",major_id from recruit_student_info where batch_id='"
						+ batch_id
						+ "' and edutype_id='"
						+ edutype_id
						+ "' group by major_id) a"
						+ edutype_id
						+ ","
						+ "(select count(id) as num_"
						+ edutype_id
						+ "_c,major_id from recruit_student_info where batch_id='"
						+ batch_id
						+ "' and edutype_id='"
						+ edutype_id
						+ "' and status='1' group by major_id) a"
						+ edutype_id
						+ "c,"
						+ "(select count(id) as num_"
						+ edutype_id
						+ "_n,major_id from recruit_student_info where batch_id='"
						+ batch_id + "' and edutype_id='" + edutype_id
						+ "' and considertype_status='2' group by major_id) a"
						+ edutype_id + "n";
				sql3 += "and a.major_id=a" + edutype_id
						+ ".major_id(+) and a.major_id=a" + edutype_id
						+ "c.major_id(+) and a.major_id=a" + edutype_id
						+ "n.major_id(+)";
			}
			list.add(edutypeList);
		} catch (Exception e) {
		} finally {
			db.close(rs);
		}
		int total[] = new int[listedu.size() * 3 + 3];
		String sql = sql1 + sql2 + sql3;
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				List item = new ArrayList();
				item.add(rs.getString("major_name"));
				int j = 0;
				for (int i = 0; i < listedu.size(); i++) {
					edutype_id = (String) listedu.get(i);
					total[j++] += rs.getInt("num_" + edutype_id);
					item.add(rs.getString("num_" + edutype_id));
					total[j++] += rs.getInt("num_" + edutype_id + "_c");
					item.add(rs.getString("num_" + edutype_id + "_c"));
					total[j++] += rs.getInt("num_" + edutype_id + "_n");
					item.add(rs.getString("num_" + edutype_id + "_n"));
				}
				total[j++] += rs.getInt("num");
				item.add(rs.getString("num"));
				total[j++] += rs.getInt("num_c");
				item.add(rs.getString("num_c"));
				total[j++] += rs.getInt("num_n");
				item.add(rs.getString("num_n"));
				list.add(item);
			}
			List item = new ArrayList();
			item.add("ϼ");
			for (int i = 0; i < total.length; i++) {
				item.add(Integer.toString(total[i]));
			}
			list.add(item);
		} catch (Exception e) {
		} finally {
			db.close(rs);
		}
		return list;
	}

	public List getRecruitNoExamConditions(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLNOEXAMCONDITION
				+ Conditions.convertToAndCondition(searchProperty,
						orderProperty);
		MyResultSet rs = null;
		List list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitNoExamCondition recruitNoExamCondition = new OracleRecruitNoExamCondition();
				recruitNoExamCondition.setId(rs.getString("id"));
				recruitNoExamCondition.setName(rs.getString("name"));
				recruitNoExamCondition.setNote(rs.getString("note"));
				list.add(recruitNoExamCondition);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getRecruitNoExamConditionsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLNOEXAMCONDITION
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public int getUnPassStudentsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLUNPASSTSTUDENT
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getUnPassStudents(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLUNPASSTSTUDENT
				+ Conditions.convertToCondition(searchProperty, null);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList testStudentList = null;
		try {
			db = new dbpool();
			testStudentList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestStudent testStudent = new OracleRecruitTestStudent();
				OracleRecruitStudent student = new OracleRecruitStudent();
				student.setId(rs.getString("id"));
				student.setName(rs.getString("name"));
				student.setPassword(rs.getString("password"));
				student.setStudyStatus(rs.getString("study_status"));
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
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				eduInfo.setSchool_name(rs.getString("school_name"));
				eduInfo.setSchool_code(rs.getString("school_code"));
				eduInfo.setGraduate_year(rs.getString("graduate_year"));
				eduInfo.setGraduate_cardno(rs.getString("graduate_cardno"));
				eduInfo.setPhoto_status(rs.getString("photo_status"));
				eduInfo.setGraduatecard_status(rs
						.getString("graduatecard_status"));
				eduInfo.setIdcard_status(rs.getString("idcard_status"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				student.setEduInfo(eduInfo);
				testStudent.setStudent(student);
				List searchProperties = new ArrayList();
				searchProperties.add(new SearchProperty("recruitstudent_id",
						student.getId(), "="));
				List testDeskList = this.getTestDesks(null, searchProperties,
						null);
				testStudent.setTestDeskList(testDeskList);
				testStudent.setTestCardId(rs.getString("testcard_id"));
				testStudent.setAppendScore(rs.getString("append_score"));
				testStudent.setScore(rs.getString("score"));
				List scoreList = new ArrayList();
				scoreList.add(rs.getString("score_1"));
				scoreList.add(rs.getString("score_2"));
				scoreList.add(rs.getString("score_3"));
				scoreList.add(rs.getString("score_4"));
				testStudent.setScoreList(scoreList);
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
				testStudentList.add(testStudent);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testStudentList;
	}

	public int deleteImage(String user_id, String note, String type)
			throws PlatformException {
		dbpool db = new dbpool();
		String sql = "insert into recruit_photo_record (user_id," + type
				+ "_link,note) select id," + type + "_link,'" + note
				+ "' from recruit_student_info where id='" + user_id + "'";
		int i = db.executeUpdate(sql);
		UserAddLog.setDebug("OracleBasicRecruitList.deleteImage(String user_id, String note, String type) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		sql = "update recruit_student_info set " + type + "_link='' , " + type
				+ "_status='0' where id='" + user_id + "'";
	
		i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleBasicRecruitList.deleteImage(String user_id, String note, String type) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		if (type.equals("photo")) {
			sql = "update recruit_photo_record set photo_status='0' where user_id='"+user_id+"'";
			UserAddLog.setDebug("OracleBasicRecruitList.deleteImage(String user_id, String note, String type) SQL=" + sql + " DATE=" + new Date());
			db.executeUpdate(sql);
		}
		return i;
	}

	public List getRejectStudentPhotos(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = "select user_id,photo_link,photo_status,note from (select a.user_id,a.photo_link,a.photo_status,a.note from recruit_photo_record a,(select id from recruit_student_info "
				+ Conditions.convertToCondition(searchProperty, orderProperty)
				+ ") b where a.user_id=b.id)";
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		List list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitPhotoRecord record = new OracleRecruitPhotoRecord();
				record.setUser_id(rs.getString("user_id"));
				record.setPhoto_link(rs.getString("photo_link"));
				record.setPhoto_status(rs.getString("photo_status"));
				record.setNote(rs.getString("note"));
				list.add(record);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getRejectStudentPhotosNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select user_id,photo_link,photo_status,note from (select a.user_id,a.photo_link,a.photo_status,a.note from recruit_photo_record a,(select id from recruit_student_info "
				+ Conditions.convertToCondition(searchProperty, null)
				+ ") b where a.user_id=b.id)";
		int i = db.countselect(sql);
		return i;
	}

	public List getReleaseSites(String batchId) throws PlatformException {
		dbpool db = new dbpool();
		String sql = "select site_id from (select distinct site_id from recruit_student_info where score_status='1' and batch_id='"
				+ batchId
				+ "' minus select distinct site_id from recruit_student_info where score_status='0' and batch_id='"
				+ batchId + "')";
		;
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		List list = null;
		try {
			db = new dbpool();
			list = new ArrayList();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				list.add(rs.getString("site_id"));
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getHaveMajorAnEduNum(String change_siteId, String batchId,
			String stu_Id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select count(*) from recruit_plan_info info,(select s.edutype_id as edutype,s.major_id as major from recruit_student_info s where s.id = '"
				+ stu_Id
				+ "') stu  where (stu.edutype,stu.major) in (select to_char(t.edutype_id) ,to_char(t.major_id) from recruit_plan_info t where t.batch_id = '"
				+ batchId
				+ "' and t.site_id ='"
				+ change_siteId
				+ "') and info.batch_id = '"
				+ batchId
				+ "' and info.site_id ='" + change_siteId + "'";
		MyResultSet rs = null;
		int i = 0;
		try {
			db = new dbpool();
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				i = Integer.parseInt(rs.getString(1));
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return i;
	}

	public int getRecruitStudentsStudyStatusNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLSTUDYSTATUSSTUDENT
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getRecruitStudentsStudyStatus(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLSTUDYSTATUSSTUDENT
				+ Conditions.convertToCondition(searchProperty, null);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList testStudentList = null;
		try {
			db = new dbpool();
			testStudentList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestStudent testStudent = new OracleRecruitTestStudent();
				OracleRecruitStudent student = new OracleRecruitStudent();
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
				student.setStudyStatus(rs.getString("study_status"));
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				eduInfo.setSchool_name(rs.getString("school_name"));
				eduInfo.setSchool_code(rs.getString("school_code"));
				eduInfo.setGraduate_year(rs.getString("graduate_year"));
				eduInfo.setGraduate_cardno(rs.getString("graduate_cardno"));
				eduInfo.setPhoto_status(rs.getString("photo_status"));
				eduInfo.setGraduatecard_status(rs
						.getString("graduatecard_status"));
				eduInfo.setIdcard_status(rs.getString("idcard_status"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				student.setEduInfo(eduInfo);
				testStudent.setStudent(student);
				List searchProperties = new ArrayList();
				searchProperties.add(new SearchProperty("recruitstudent_id",
						student.getId(), "="));
				List testDeskList = this.getTestDesks(null, searchProperties,
						null);
				testStudent.setTestDeskList(testDeskList);
				testStudent.setTestCardId(rs.getString("testcard_id"));
				testStudent.setAppendScore(rs.getString("append_score"));
				testStudent.setScore(rs.getString("score"));
				List scoreList = new ArrayList();
				scoreList.add(rs.getString("score_1"));
				scoreList.add(rs.getString("score_2"));
				scoreList.add(rs.getString("score_3"));
				scoreList.add(rs.getString("score_4"));
				testStudent.setScoreList(scoreList);
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
				testStudentList.add(testStudent);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testStudentList;
	}

	public int updateStudentStudyStatus(String status, String student_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "update recruit_student_info set study_status='" + status
				+ "' where id ='" + student_id + "'";
		int i = db.executeUpdate(sql);
		UserUpdateLog.setDebug("OracleBasicRecruitList.updateStudentStudyStatus(String status, String student_id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
		return i;
	}

	public int getReleaseStudentsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLRELEASESTUDENTS
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getReleaseStudents(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLRELEASESTUDENTS
				+ Conditions.convertToCondition(searchProperty, null);
		EntityLog.setDebug(sql);
		MyResultSet rs = null;
		ArrayList testStudentList = null;
		try {
			db = new dbpool();
			testStudentList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleRecruitTestStudent testStudent = new OracleRecruitTestStudent();
				OracleRecruitStudent student = new OracleRecruitStudent();
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
				student.setStudyStatus(rs.getString("study_status"));
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setReg_no(rs.getString("reg_no"));
				eduInfo.setSite_id(rs.getString("site_id"));
				eduInfo.setSite_name(rs.getString("site_name"));
				eduInfo.setGrade_id(rs.getString("grade_id"));
				eduInfo.setGrade_name(rs.getString("grade_name"));
				eduInfo.setEdutype_id(rs.getString("edutype_id"));
				eduInfo.setEdutype_name(rs.getString("edutype_name"));
				eduInfo.setMajor_id(rs.getString("major_id"));
				eduInfo.setMajor_name(rs.getString("major_name"));
				eduInfo.setConsiderType(rs.getString("considertype"));
				eduInfo.setSchool_name(rs.getString("school_name"));
				eduInfo.setSchool_code(rs.getString("school_code"));
				eduInfo.setGraduate_year(rs.getString("graduate_year"));
				eduInfo.setGraduate_cardno(rs.getString("graduate_cardno"));
				eduInfo.setPhoto_status(rs.getString("photo_status"));
				eduInfo.setGraduatecard_status(rs
						.getString("graduatecard_status"));
				eduInfo.setIdcard_status(rs.getString("idcard_status"));
				OracleRecruitBatch batch = new OracleRecruitBatch();
				batch.setId(rs.getString("batch_id"));
				eduInfo.setBatch(batch);
				eduInfo.setStatus(rs.getString("status"));
				eduInfo.setPassStatus(rs.getString("pass_status"));
				student.setEduInfo(eduInfo);
				testStudent.setStudent(student);
				List searchProperties = new ArrayList();
				searchProperties.add(new SearchProperty("recruitstudent_id",
						student.getId(), "="));
				List testDeskList = this.getTestDesks(null, searchProperties,
						null);
				testStudent.setTestDeskList(testDeskList);
				testStudent.setTestCardId(rs.getString("testcard_id"));
				testStudent.setAppendScore(rs.getString("append_score"));
				testStudent.setScore(rs.getString("score"));
				List scoreList = new ArrayList();
				scoreList.add(rs.getString("score_1"));
				scoreList.add(rs.getString("score_2"));
				scoreList.add(rs.getString("score_3"));
				scoreList.add(rs.getString("score_4"));
				testStudent.setScoreList(scoreList);
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
				testStudentList.add(testStudent);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return testStudentList;
	}
}

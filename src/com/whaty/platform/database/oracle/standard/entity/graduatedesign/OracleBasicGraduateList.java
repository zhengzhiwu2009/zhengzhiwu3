package com.whaty.platform.database.oracle.standard.entity.graduatedesign;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleEduType;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleGrade;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleMajor;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleSiteTeacherLimit;
import com.whaty.platform.database.oracle.standard.entity.user.OracleStudent;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacher;
import com.whaty.platform.database.oracle.standard.entity.user.OracleTeacherLimit;
import com.whaty.platform.entity.basic.Major;
import com.whaty.platform.entity.graduatedesign.BasicGraduateList;
import com.whaty.platform.entity.graduatedesign.FreeApply;
import com.whaty.platform.entity.graduatedesign.MetaphaseCheck;
import com.whaty.platform.entity.graduatedesign.Pici;
import com.whaty.platform.entity.graduatedesign.RegBgCourse;
import com.whaty.platform.entity.graduatedesign.SubjectQuestionary;
import com.whaty.platform.entity.recruit.TimeDef;
import com.whaty.platform.entity.user.SiteTeacher;
import com.whaty.platform.entity.user.SiteTeacherLimit;
import com.whaty.platform.entity.user.Student;
import com.whaty.platform.util.Conditions;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.log.EntityLog;

public class OracleBasicGraduateList implements BasicGraduateList {
	

	//private String SQLSTUDENTAPPLY = "select id,student_id,production_name,link,status,teacher_note,apply_time from (select t.id,t.student_id,t.production_name,t.link,t.status,t.teacher_note,to_char(t.apply_time,'yyyy-mm-dd') as apply_time,r.grade_id,r.site_id,r.edutype_id,r.major_id,r.name as student_name,r.reg_no as reg_no from ENTITY_GRADUATE_FREE_APPLY t ,entity_student_info r where r.id = t.student_id)";
	//private String SQLSTUDENTAPPLY = "select distinct id, student_id, production_name, link, status, teacher_note, apply_time from (select distinct t.id, t.student_id, t.production_name, t.link, t.status, t.teacher_note, to_char(t.apply_time, 'yyyy-mm-dd') as apply_time, r.grade_id, r.site_id, r.edutype_id, r.major_id, r.name as student_name, r.reg_no as reg_no, eci.id as course_id from ENTITY_GRADUATE_FREE_APPLY t, entity_student_info r, entity_elective ee, entity_course_info eci, entity_course_active eca, entity_teach_class etc, entity_graduate_pici egp, entity_graduate_picilimit egpl, entity_semester_info esi where r.id = t.student_id and esi.selected = '1' and esi.id = eca.semester_id and egp.status = '1' and egp.id = egpl.pici_id and r.grade_id||r.major_id||r.edutype_id=egpl.grade_id||egpl.major_id||egpl.edutype_id and ee.student_id = t.student_id and eca.course_id = eci.id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eci.id in ('000001','000011') )";
	

	//private String SQLSTUDENTAPPLY = "select id,student_id,production_name,link,status,teacher_note,apply_time from (select t.id,t.student_id,t.production_name,t.link,t.status,t.teacher_note,to_char(t.apply_time,'yyyy-mm-dd') as apply_time,r.grade_id,r.site_id,r.edutype_id,r.major_id,r.name as student_name,r.reg_no as reg_no from ENTITY_GRADUATE_FREE_APPLY t ,entity_student_info r where r.id = t.student_id)";
	//2008-05-28 private String SQLSTUDENTAPPLY = "select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time,free_type,free_score ,grade_id, site_id, edutype_id, major_id, student_name, reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id,teacher_id from (select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time, free_type, free_score,grade_id, site_id, edutype_id, major_id, name as student_name, reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id,teacher_id from (select t.id as free_id, t.student_id, t.production_name, t.link, t.status as freestatus, t.teacher_note, to_char(t.apply_time, 'yyyy-mm-dd') as apply_time, t.type as free_type,t.score_status as free_score ,r.grade_id, r.site_id, r.edutype_id, r.major_id, r.name, r.reg_no as reg_no, eci.id as course_id, eci.name as course_name, esi.id as semester_id, esi.name as semester_name from ENTITY_GRADUATE_FREE_APPLY t, entity_student_info r, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esi where t.student_id = r.id and t.student_id = ee.student_id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.semester_id = esi.id and eca.course_id = eci.id and eci.id in ('000011','000001') and esi.selected = 1) t, (select id as pici_id from entity_graduate_pici) egrp,(select teacher_id,grade_id as grade_id1,edutype_id as edutype_id1,major_id as major_id1 from entity_graduate_teacherlimit) teach where egrp.pici_id || t.grade_id || t.edutype_id || t.major_id in (select egp.pici_id || egp.grade_id || egp.edutype_id || egp.major_id from entity_graduate_picilimit egp) and  t.grade_id || t.edutype_id || t.major_id in (teach.grade_id1||teach.edutype_id1||teach.major_id1))";
	//
	//ñҵ
	//private String SQLSTUDENTAPPLY = "select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time,free_type,free_score ,grade_id,site_id, edutype_id, major_id, student_name, reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id,teacher_id from (select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time, free_type, free_score,grade_id, site_id, edutype_id, major_id, name as student_name, reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id,teacher_id from (select distinct t.id as free_id, t.student_id, t.production_name, t.link, t.status as freestatus, t.teacher_note, to_char(t.apply_time, 'yyyy-mm-dd') as apply_time, t.type as free_type,t.score_status as free_score ,r.grade_id, r.site_id, r.edutype_id, r.major_id, r.name, r.reg_no as reg_no, eci.id as course_id, eci.name as course_name, esi.id as semester_id, pi.id as pici_id,esi.name as semester_name from ENTITY_GRADUATE_FREE_APPLY t, entity_student_info r, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esi ,entity_graduate_pici pi where t.type='1' and t.student_id = r.id and t.student_id = ee.student_id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.semester_id = esi.id and eca.course_id = eci.id and eci.id in ('000001') and pi.id in(select p.id from entity_graduate_pici p,entity_semester_info se  where p.starttime>se.start_date and p.endtime<se.end_date and p.status='1' and se.selected=1)) t, (select teacher_id,grade_id as grade_id1,edutype_id as edutype_id1,major_id as major_id1 from entity_graduate_teacherlimit) teach where t.grade_id || t.edutype_id || t.major_id in (teach.grade_id1||teach.edutype_id1||teach.major_id1))";
	private String SQLSTUDENTAPPLY = "select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time, free_type, free_score, grade_id, site_id, edutype_id, major_id, name, reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id from (select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time, free_type, free_score, grade_id, site_id, edutype_id, major_id, name, reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id from (select distinct t.id as free_id, t.student_id, t.production_name, t.link, t.status as freestatus, t.teacher_note, to_char(t.apply_time, 'yyyy-mm-dd') as apply_time, t.type as free_type, t.score_status as free_score, r.grade_id, r.site_id, r.edutype_id, r.major_id, r.name, r.reg_no as reg_no, eci.id as course_id, eci.name as course_name, esi.id as semester_id, pi.id as pici_id, esi.name as semester_name from ENTITY_GRADUATE_FREE_APPLY t, entity_student_info r, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esi, entity_graduate_pici pi where t.type = '1' and t.student_id = r.id and t.student_id = ee.student_id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.semester_id = esi.id and eca.course_id = eci.id and eca.semester_id = pi.semester_id and eci.id in ('000001')) t) ";
	
	private String SQLSTUDENTAPPLY_TUTOR ="select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time,free_type,free_score ,grade_id,site_id, edutype_id, major_id,  name, reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id,teacher_id from (select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time, free_type, free_score,grade_id, site_id, edutype_id, major_id, name , reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id,teacher_id from (select distinct t.id as free_id, t.student_id, t.production_name, t.link, t.status as freestatus, t.teacher_note, to_char(t.apply_time, 'yyyy-mm-dd') as apply_time, t.type as free_type,t.score_status as free_score ,r.grade_id, r.site_id, r.edutype_id, r.major_id, r.name, r.reg_no as reg_no, eci.id as course_id, eci.name as course_name, esi.id as semester_id, pi.id as pici_id,esi.name as semester_name from ENTITY_GRADUATE_FREE_APPLY t, entity_student_info r, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esi ,entity_graduate_pici pi where t.type='1' and t.student_id = r.id and t.student_id = ee.student_id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.semester_id = esi.id and eca.course_id = eci.id and eci.id in ('000001') and pi.status='1') t, (select teacher_id,grade_id as grade_id1,edutype_id as edutype_id1,major_id as major_id1 from entity_graduate_teacherlimit) teach where t.grade_id || t.edutype_id || t.major_id in (teach.grade_id1||teach.edutype_id1||teach.major_id1)) ";
	//ñҵҵ
	//private String SQLSTUDENTAPPLY_EXEC = "select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time,free_type,free_score ,grade_id,site_id, edutype_id, major_id, student_name, reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id,teacher_id from (select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time, free_type, free_score,grade_id, site_id, edutype_id, major_id, name as student_name, reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id,teacher_id from (select distinct t.id as free_id, t.student_id, t.production_name, t.link, t.status as freestatus, t.teacher_note, to_char(t.apply_time, 'yyyy-mm-dd') as apply_time, t.type as free_type,t.score_status as free_score ,r.grade_id, r.site_id, r.edutype_id, r.major_id, r.name, r.reg_no as reg_no, eci.id as course_id, eci.name as course_name, esi.id as semester_id, pi.id as pici_id,esi.name as semester_name from ENTITY_GRADUATE_FREE_APPLY t, entity_student_info r, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esi ,entity_graduate_exec_pici pi where t.type='0' and t.student_id = r.id and t.student_id = ee.student_id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.semester_id = esi.id and eca.course_id = eci.id and eci.id in ('000011') and pi.id in(select p.id from entity_graduate_exec_pici p,entity_semester_info se  where p.starttime>se.start_date and p.endtime<se.end_date and p.status='1' and se.selected=1)) t, (select teacher_id,grade_id as grade_id1,edutype_id as edutype_id1,major_id as major_id1 from entity_graduate_teacherlimit) teach where t.grade_id || t.edutype_id || t.major_id in (teach.grade_id1||teach.edutype_id1||teach.major_id1))";
	private String SQLSTUDENTAPPLY_EXEC = "select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time, free_type, free_score, grade_id, site_id, edutype_id, major_id, name, reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id from (select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time, free_type, free_score, grade_id, site_id, edutype_id, major_id, name, reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id from (select distinct t.id as free_id, t.student_id, t.production_name, t.link, t.status as freestatus, t.teacher_note, to_char(t.apply_time, 'yyyy-mm-dd') as apply_time, t.type as free_type, t.score_status as free_score, r.grade_id, r.site_id, r.edutype_id, r.major_id, r.name, r.reg_no as reg_no, eci.id as course_id, eci.name as course_name, esi.id as semester_id, pi.id as pici_id, esi.name as semester_name from ENTITY_GRADUATE_FREE_APPLY t, entity_student_info r, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esi, entity_graduate_exec_pici pi where t.type = '0' and t.student_id = r.id and t.student_id = ee.student_id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.semester_id = esi.id and eca.course_id = eci.id and pi.semester_id = eca.semester_id and eci.id in ('000011')) t) ";
  
	private String SQLSTUDENTAPPLY_EXEC_TUTOR ="select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time,free_type,free_score ,grade_id,site_id, edutype_id, major_id, name, reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id,teacher_id from (select distinct free_id, student_id, production_name, link, freestatus, teacher_note, apply_time, free_type, free_score,grade_id, site_id, edutype_id, major_id, name , reg_no as reg_no, course_id, course_name, semester_id, semester_name, pici_id,teacher_id from (select distinct t.id as free_id, t.student_id, t.production_name, t.link, t.status as freestatus, t.teacher_note, to_char(t.apply_time, 'yyyy-mm-dd') as apply_time, t.type as free_type,t.score_status as free_score ,r.grade_id, r.site_id, r.edutype_id, r.major_id, r.name, r.reg_no as reg_no, eci.id as course_id, eci.name as course_name, esi.id as semester_id, pi.id as pici_id,esi.name as semester_name from ENTITY_GRADUATE_FREE_APPLY t, entity_student_info r, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esi ,entity_graduate_exec_pici pi where t.type='0' and t.student_id = r.id and t.student_id = ee.student_id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.semester_id = esi.id and eca.course_id = eci.id and eci.id in ('000011') and pi.status='1') t, (select teacher_id,grade_id as grade_id1,edutype_id as edutype_id1,major_id as major_id1 from entity_graduate_teacherlimit) teach where t.grade_id || t.edutype_id || t.major_id in (teach.grade_id1||teach.edutype_id1||teach.major_id1)) ";
	//entity_graduate_picilimit
	//2008-05-28private String SQLSTUDENTMAJORS ="select distinct major_id from (select i.major_id, t.siteteacher_id as teacher_id from entity_graduate_siteteachlimit t, entity_student_info i, entity_graduate_pici egp,entity_graduate_picilimit egpl where t.student_id = i.id and egp.status = '1' and egp.id = egpl.pici_id and i.grade_id||i.edutype_id||i.major_id = egpl.grade_id||egpl.edutype_id||egpl.major_id ) ";
	//
//	private String SQLSTUDENTMAJORS ="select distinct major_id from (select i.major_id, t.siteteacher_id as teacher_id from entity_graduate_siteteachlimit t, entity_student_info i, entity_graduate_pici egp ,entity_elective      ee,entity_teach_class   etc,entity_course_active eca,entity_course_info   eci, entity_semester_info esis where t.student_id = i.id and egp.status = '1' and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = i.id and eci.id = '000001' and egp.starttime>esis.start_date and egp.endtime<esis.end_date and egp.status='1') ";
	private String SQLSTUDENTMAJORS ="select distinct major_id from (select i.major_id, t.siteteacher_id as teacher_id from entity_graduate_siteteachlimit t, entity_student_info i where t.student_id = i.id ) ";
	
	private String SQLSUBJECTSEARCHSTATUSCHANGEDSTUDENT = "select id from entity_graduate_subjectsearch t where t.status = 3";

	private String SQLREGBEGINCOURSESTATUSCHANGEDSTUDENT = "select id from entity_graduate_regbegincourse t where t.status = 3";

	private String SQLMETAPHASECHECKSTATUSCHANGEDSTUDENT = "select id from entity_graduate_metaphasecheck t where t.status = 3";

	private String SQLREJOINREQUESSTATUSCHANGEDSTUDENT = "select id from entity_graduate_rejoinreques t where t.status = 3";

	private String SQLDISCOURSESTATUSCHANGEDSTUDENT = "select id from entity_graduate_discourse t where t.status = 3";
	
	private String SQLDISCOURSESCORESTATUSCHANGEDSTUDENT = "select id from entity_graduate_discourse t where t.score_status = '1'";
	
	private String SQLEXERCISESCORESTATUSCHANGEDSTUDENT = "select id from entity_graduate_subjectsearch t where t.score_status = '1'";
	
	//2008-05-28 private String SQLACTIVEDESCOURSECHECKSTUDENTID = " select student_id from ( select t.student_id,t2.reg_no, t2.major_id, t2.edutype_id, t2.site_id, t2.grade_id, t2.name, t3.status from entity_graduate_discourse t, entity_student_info t2, entity_graduate_rejoinreques t3, entity_graduate_pici t4, entity_graduate_picilimit t5 where t.student_id = t2.id and t.student_id = t3.student_id(+) and t4.status = '1' and t4.id = t5.pici_id and t2.grade_id || t2.major_id || t2.edutype_id = t5.grade_id || t5.major_id || t5.edutype_id ) " ;
	//
	//private String SQLACTIVEDESCOURSECHECKSTUDENTID = " select student_id from ( select distinct t.student_id,t2.reg_no, t2.major_id, t2.edutype_id, t2.site_id, t2.grade_id, t2.name, t3.status from entity_graduate_discourse t, entity_student_info t2, entity_graduate_rejoinreques t3, entity_graduate_pici t4 ,entity_elective      ee,entity_teach_class   etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis where t.student_id = t2.id and t.student_id = t3.student_id(+) and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = t2.id and eci.id ='000001' and t4.starttime>esis.start_date and t4.endtime<esis.end_date and t4.status = '1' ) " ;
	private String SQLACTIVEDESCOURSECHECKSTUDENTID = " select student_id from ( select distinct t.student_id,t2.reg_no, t2.major_id, t2.edutype_id, t2.site_id, t2.grade_id, t2.name, t3.status from entity_graduate_discourse t, entity_student_info t2, entity_graduate_rejoinreques t3, entity_graduate_pici t4 ,entity_elective      ee,entity_teach_class   etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis where t.student_id = t2.id and t.student_id = t3.student_id(+) and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = t2.id and eci.id ='000001' ) " ;
	
	private String SQLACTIVESUBJECTSEARCHSTUDENTID = "((select student_id from entity_graduate_subjectsearch) minus (select student_id from entity_graduate_regbegincourse where student_id in (select student_id from entity_graduate_subjectsearch))) union (select student_id from entity_graduate_regbegincourse where student_id in (select student_id from entity_graduate_subjectsearch) and status not in (3, 1))" ;
	
	//2008-05-28private String SQLACTIVEREGBEGINCOURSESTUDENTID = "select student_id from ( select t.student_id,t2.reg_no, t2.major_id, t2.edutype_id, t2.site_id, t2.grade_id, t2.name, t3.status from entity_graduate_regbegincourse t, entity_student_info t2, entity_graduate_metaphasecheck t3, entity_graduate_pici t4, entity_graduate_picilimit t5 where t.student_id = t2.id and t.student_id = t3.student_id(+) and t4.status = '1' and t4.id = t5.pici_id and t2.grade_id || t2.major_id || t2.edutype_id = t5.grade_id || t5.major_id || t5.edutype_id ) " ;
	private String SQLACTIVEREGBEGINCOURSESTUDENTID = "select student_id from ( select t.student_id,t2.reg_no, t2.major_id, t2.edutype_id, t2.site_id, t2.grade_id, t2.name, t3.status from entity_graduate_regbegincourse t, entity_student_info t2, entity_graduate_metaphasecheck t3, entity_graduate_pici t4 where t.student_id = t2.id and t.student_id = t3.student_id(+) and t4.status = '1' ) " ;	
	
//	2008-05-28 private String SQLACTIVEMETAPHASECHECKSTUDENTID = " select student_id from ( select t.student_id,t2.reg_no, t2.major_id, t2.edutype_id, t2.site_id, t2.grade_id, t2.name, t3.status from entity_graduate_metaphasecheck t, entity_student_info t2, entity_graduate_discourse t3, entity_graduate_pici t4, entity_graduate_picilimit t5 where t.student_id = t2.id and t.student_id = t3.student_id(+) and t4.status = '1' and t4.id = t5.pici_id and t2.grade_id || t2.major_id || t2.edutype_id = t5.grade_id || t5.major_id || t5.edutype_id ) " ;
	private String SQLACTIVEMETAPHASECHECKSTUDENTID = " select student_id from ( select t.student_id,t2.reg_no, t2.major_id, t2.edutype_id, t2.site_id, t2.grade_id, t2.name, t3.status from entity_graduate_metaphasecheck t, entity_student_info t2, entity_graduate_discourse t3, entity_graduate_pici t4 where t.student_id = t2.id and t.student_id = t3.student_id(+) and t4.status = '1' ) " ;
	
	private String SQLGRADUATEBATCH = "select id, name, starttime, endtime, subjectstime, subjectetime, opensubstime, endsubetime, subsubstime, subsubetime, rejoinstime, rejoinetime, reportgradestime, reportgradeetime, status, semester_id, semester_name from (select a.id, a.name, to_char(starttime, 'yyyy-mm-dd') as starttime, to_char(endtime, 'yyyy-mm-dd') as endtime, to_char(subjectstime, 'yyyy-mm-dd') as subjectstime, to_char(subjectetime, 'yyyy-mm-dd') as subjectetime, to_char(opensubstime, 'yyyy-mm-dd') as opensubstime, to_char(endsubetime, 'yyyy-mm-dd') as endsubetime, to_char(subsubstime, 'yyyy-mm-dd') as subsubstime, to_char(subsubetime, 'yyyy-mm-dd') as subsubetime, to_char(rejoinstime, 'yyyy-mm-dd') as rejoinstime, to_char(rejoinetime, 'yyyy-mm-dd') as rejoinetime, to_char(reportgradestime, 'yyyy-mm-dd') as reportgradestime, to_char(reportgradeetime, 'yyyy-mm-dd') as reportgradeetime, status, b.name as semester_name, a.semester_id from entity_graduate_pici a ,entity_semester_info b where a.semester_id = b.id) ";

	//ҵҵѯ;
	private String SQLGRADUATE_EXEC_BATCH = "select id, name, starttime, endtime, subjectstime, subjectetime, opensubstime, endsubetime, subsubstime, subsubetime, rejoinstime, rejoinetime, reportgradestime, reportgradeetime, status, semester_id, semester_name,WRITEDISCOURSESTIME,WRITEDISCOURSEETIME from (select a.id, a.name, to_char(starttime, 'yyyy-mm-dd') as starttime, to_char(endtime, 'yyyy-mm-dd') as endtime, to_char(subjectstime, 'yyyy-mm-dd') as subjectstime, to_char(subjectetime, 'yyyy-mm-dd') as subjectetime, to_char(opensubstime, 'yyyy-mm-dd') as opensubstime, to_char(endsubetime, 'yyyy-mm-dd') as endsubetime, to_char(subsubstime, 'yyyy-mm-dd') as subsubstime, to_char(subsubetime, 'yyyy-mm-dd') as subsubetime, to_char(rejoinstime, 'yyyy-mm-dd') as rejoinstime, to_char(rejoinetime, 'yyyy-mm-dd') as rejoinetime, to_char(reportgradestime, 'yyyy-mm-dd') as reportgradestime, to_char(reportgradeetime, 'yyyy-mm-dd') as reportgradeetime, status, a.semester_id, b.name as semester_name,to_char(WRITEDISCOURSESTIME,'yyyy-mm-dd') as WRITEDISCOURSESTIME,to_char(WRITEDISCOURSEETIME,'yyyy-mm-dd') as WRITEDISCOURSEETIME from entity_graduate_exec_pici a,entity_semester_info b where a.semester_id = b.id) ";

	
//	2008-05-28private String SQLGRADEEDUMAJORS = "select pici_id,gradeid,gradename,edutypeid,edutypename,majorid,majorname from (select egi.id as gradeid,egi.name as gradename,eet.id as edutypeid ,eet.name as edutypename,emi.id as majorid,emi.name as majorname,egp.id as pici_id from entity_grade_info egi,entity_edu_type eet,entity_major_info emi,entity_graduate_pici egp  where egp.id || egi.id || eet.id || emi.id not in (select pici_id || grade_id || edutype_id || major_id from entity_graduate_picilimit))";
	//
	private String SQLGRADEEDUMAJORS = "select pici_id,gradeid,gradename,edutypeid,edutypename,majorid,majorname from (select egi.id as gradeid,egi.name as gradename,eet.id as edutypeid ,eet.name as edutypename,emi.id as majorid,emi.name as majorname,egp.id as pici_id from entity_grade_info egi,entity_edu_type eet,entity_major_info emi,entity_graduate_pici egp  )";

	//δ޸,entity_graduate_picilimit
	//
	//private String SQLGRADEEDUMAJORFORPICI = "select pici_id,grade_id,edutype_id,major_id from (select  distinct pi.id as pici_id,esi.grade_id as grade_id,esi.edutype_id as edutype_id,esi.major_id as major_id  from entity_elective      ee,entity_teach_class   etc,entity_course_active eca,entity_course_info   eci, entity_semester_info esis,entity_student_info  esi, entity_graduate_pici pi where ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = esi.id and eci.id='000001' and esis.start_date<pi.starttime and esis.end_date>pi.endtime and pi.status='1')";
	private String SQLGRADEEDUMAJORFORPICI = "select pici_id,grade_id,edutype_id,major_id from (select  distinct pi.id as pici_id,esi.grade_id as grade_id,esi.edutype_id as edutype_id,esi.major_id as major_id  from entity_elective      ee,entity_teach_class   etc,entity_course_active eca,entity_course_info   eci, entity_semester_info esis,entity_student_info  esi, entity_graduate_pici pi where ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = esi.id and eci.id='000001'  and pi.status='1')";

//	private String SQLSUBJECTQUESTIONARY = "select distinct id,score,score_status,score_note,student_id,main_job,link,subjectname,subjectcontent,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,gender,folk,home_address,work_address,phone,mobilephone,home_postalcode,pici_id  from (select t.id,t.score,t.score_status,t.score_note,t.student_id,t.main_job,t.link,t.subjectname,t.subjectcontent,t.remark,t.teacher_note,t.siteteacher_note,t.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id,eui.gender,eui.folk,eui.home_address,eui.work_address,eui.phone,eui.mobilephone,eui.home_postalcode,egrp.id as pici_id          from entity_graduate_subjectsearch t,entity_student_info   esi,entity_usernormal_info eui,entity_graduate_pici  egrp   where t.student_id = esi.id  and t.student_id = eui.id  and egrp.id||esi.grade_id || esi.edutype_id || esi.major_id in(select egp.pici_id||egp.grade_id || egp.edutype_id || egp.major_id   from entity_graduate_picilimit egp))";

	//2008-05-28 private String SQLSUBJECTQUESTIONARY = "select distinct id,student_id, main_job, link, subjectname, subjectcontent, remark, teacher_note, siteteacher_note, status, reg_no, name, site_id, edutype_id, major_id, grade_id, gender, folk, home_address, work_address, phone, mobilephone, home_postalcode, pici_id, course_name, course_id, semester_id, semester_name,score, score_status, score_note from (select t.id, t.student_id, t.main_job, t.link, t.subjectname, t.subjectcontent, t.remark, t.teacher_note, t.siteteacher_note, t.status, t.score, t.score_status, t.score_note,esi.reg_no, esi.name, esi.site_id, esi.edutype_id, esi.major_id, esi.grade_id, eui.gender, eui.folk, eui.home_address, eui.work_address, eui.phone, eui.mobilephone, eui.home_postalcode, egrp.id as pici_id, t.course_name, t.course_id, t.semester_id, t.semester_name from (select distinct egs.*, eci.name as course_name,eci.id as course_id,esi.id as semester_id,esi.name as semester_name from entity_graduate_subjectsearch egs, entity_elective  ee, entity_teach_class  etc, entity_course_active  eca, entity_course_info  eci, entity_semester_info   esi where egs.student_id = ee.student_id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.semester_id = esi.id and eca.course_id = eci.id ) t, entity_student_info esi, entity_usernormal_info eui, entity_graduate_pici egrp where t.student_id = esi.id and t.student_id = eui.id and egrp.id || esi.grade_id || esi.edutype_id || esi.major_id in (select egp.pici_id || egp.grade_id || egp.edutype_id || egp.major_id from entity_graduate_picilimit egp))";
	//
	//private String SQLSUBJECTQUESTIONARY = "select distinct id,student_id, main_job, link, subjectname, subjectcontent, remark, teacher_note, siteteacher_note, status, reg_no, name, site_id, edutype_id, major_id, grade_id, gender, folk, home_address, work_address, phone, mobilephone, home_postalcode, pici_id, course_name, course_id, semester_id, semester_name,score, score_status, score_note from (select distinct t.id, t.student_id, t.main_job, t.link, t.subjectname, t.subjectcontent, t.remark, t.teacher_note, t.siteteacher_note, t.status, t.score,t.score_status, t.score_note,esi.reg_no, esi.name, esi.site_id, esi.edutype_id, esi.major_id, esi.grade_id, eui.gender, eui.folk, eui.home_address, eui.work_address, eui.phone, eui.mobilephone, eui.home_postalcode, egrp.id as pici_id, t.course_name, t.course_id, t.semester_id, t.semester_name from (select distinct egs.*, eci.name as course_name,eci.id as course_id,esi.id as semester_id,esi.name as semester_name from entity_graduate_subjectsearch egs, entity_elective  ee, entity_teach_class  etc,entity_course_active  eca, entity_course_info  eci,entity_semester_info   esi where egs.student_id = ee.student_id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.semester_id = esi.id and eca.course_id = eci.id ) t, entity_student_info esi, entity_usernormal_info eui , entity_graduate_exec_pici egrp where t.student_id = esi.id and t.student_id = eui.id and t.course_id ='000011' and t.semester_id in (select distinct seme.id from entity_semester_info seme,entity_graduate_exec_pici pic where pic.starttime>seme.start_date and pic.endtime<seme.end_date and pic.status ='1') and egrp.status ='1')";
	private String SQLSUBJECTQUESTIONARY = "select distinct id, student_id, main_job, link, subjectname, subjectcontent, remark, teacher_note, siteteacher_note, status, reg_no, name, site_id, edutype_id, major_id, grade_id, gender, folk, home_address, work_address, phone, mobilephone, home_postalcode, pici_id, course_name, course_id, semester_id, semester_name, score, score_status, score_note from (select distinct t.id, t.student_id, t.main_job, t.link, t.subjectname, t.subjectcontent, t.remark, t.teacher_note, t.siteteacher_note, t.status, esi.graduate_grade as score, t.score_status, t.score_note, esi.reg_no, esi.name, esi.site_id, esi.edutype_id, esi.major_id, esi.grade_id, eui.gender, eui.folk, eui.home_address, eui.work_address, eui.phone, eui.mobilephone, eui.home_postalcode, egrp.id as pici_id, t.course_name, t.course_id, t.semester_id, t.semester_name from (select distinct egs.*, eci.name as course_name, eci.id as course_id, esi.id as semester_id, esi.name as semester_name from entity_graduate_subjectsearch egs, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esi, entity_graduate_exec_pici pi where egs.student_id = ee.student_id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.semester_id = pi.semester_id and eca.semester_id = esi.id and eca.course_id = eci.id) t, entity_student_info esi, entity_usernormal_info eui, entity_graduate_exec_pici egrp where t.student_id = esi.id and t.student_id = eui.id and t.course_id = '000011' and egrp.status = '1') ";
	
//	2008-05-28private String SQLSUBJECTQUESTIONARY2 = " select subject_id, student_id, subjectname, subjectcontent, link, remark, teacher_note, siteteacher_note, score_status, score, score_note, status, main_job from (select egs.id as subject_id, egs.student_id, egs.link, egs.subjectname, egs.subjectcontent, egs.remark, egs.teacher_note, egs.status, egs.siteteacher_note, egs.score_note, egs.score_status, egs.score, egs.main_job, esi.name as student_name, esi.reg_no, esi.major_id, esi.site_id, esi.edutype_id, esi.grade_id from entity_graduate_subjectsearch egs, entity_student_info esi ,entity_graduate_pici egp,entity_graduate_picilimit egpl where egs.student_id = esi.id and egp.status = '1' and egpl.pici_id = egp.id and egpl.grade_id||egpl.major_id||egpl.edutype_id = esi.grade_id||esi.major_id||esi.edutype_id) ";
	//
	//private String SQLSUBJECTQUESTIONARY2 = " select subject_id, student_id, subjectname, subjectcontent, link, remark, teacher_note, siteteacher_note, score_status, score, score_note, status, main_job ,pici_id from (select egs.id as subject_id, egs.student_id, egs.link, egs.subjectname, egs.subjectcontent, egs.remark, egs.teacher_note, egs.status, egs.siteteacher_note, egs.score_note, egs.score_status, egs.score, egs.main_job, esi.name as student_name, esi.reg_no, esi.major_id, esi.site_id, esi.edutype_id, esi.grade_id ,egp.id as pici_id from entity_graduate_subjectsearch egs, entity_student_info esi ,entity_graduate_exec_pici egp,entity_elective      ee,entity_teach_class   etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis where egs.student_id = esi.id and egp.status = '1' and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = esi.id and esis.start_date<egp.starttime and esis.end_date>egp.endtime and eci.id='000011')";
	private String SQLSUBJECTQUESTIONARY2 = " select subject_id, student_id, subjectname, subjectcontent, link, remark, teacher_note, siteteacher_note, score_status, score, score_note, status, main_job ,pici_id,semester_id from (select egs.id as subject_id, egs.student_id, egs.link, egs.subjectname, egs.subjectcontent, egs.remark, egs.teacher_note, egs.status, egs.siteteacher_note, egs.score_note, egs.score_status, egs.score, egs.main_job, esi.name as student_name, esi.reg_no, esi.major_id, esi.site_id, esi.edutype_id, esi.grade_id ,egp.id as pici_id,esis.id as semester_id from entity_graduate_subjectsearch egs, entity_student_info esi ,entity_graduate_exec_pici egp,entity_elective      ee,entity_teach_class   etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis where egs.student_id = esi.id and egp.status = '1' and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = esi.id  and eci.id='000011')";
	
	//private String SQLREGGEGINCOURSE = "select id,student_id,link,discoursename,discoursecontent,discourseplan,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id from (select egr.id,egr.student_id,egr.link,egr.discoursename,egr.discoursecontent,egr.discourseplan,egr.remark,egr.teacher_note,egr.siteteacher_note,egr.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id   from entity_graduate_regbegincourse egr,entity_student_info    esi,entity_usernormal_info eui  where egr.student_id = esi.id    and egr.student_id = eui.id)";
    //2008-05-28private String SQLREGGEGINCOURSE ="select id,student_id,link,discoursename,discoursecontent,discourseplan,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,pici_id from (select egr.id,egr.student_id,egr.link,egr.discoursename,egr.discoursecontent,egr.discourseplan,egr.remark,egr.teacher_note,egr.siteteacher_note,egr.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id,egrp.id as pici_id  from entity_graduate_regbegincourse egr,entity_student_info    esi,entity_usernormal_info eui,entity_graduate_pici egrp  where egr.student_id = esi.id    and egr.student_id = eui.id and egrp.id||esi.grade_id || esi.edutype_id || esi.major_id in (select egp.pici_id||egp.grade_id || egp.edutype_id || egp.major_id   from entity_graduate_picilimit egp))";
    //
	//private String SQLREGGEGINCOURSE ="select distinct id,student_id,link,discoursename,discoursecontent,discourseplan,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id ,pici_id from (select distinct egr.id,egr.student_id,egr.link,egr.discoursename,egr.discoursecontent,egr.discourseplan,egr.remark,egr.teacher_note,egr.siteteacher_note, egr.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id ,pi.id as pici_id from entity_graduate_regbegincourse egr,entity_student_info  esi,entity_usernormal_info eui ,entity_elective      ee,entity_teach_class   etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis, entity_graduate_pici pi where egr.student_id = esi.id and egr.student_id = eui.id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and  eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = esi.id and esis.start_date<pi.starttime and esis.end_date>pi.endtime and eci.id = '000001' and pi.status ='1') ";
	private String SQLREGGEGINCOURSE ="select distinct id, student_id, link, discoursename, discoursecontent, discourseplan, remark, teacher_note, siteteacher_note, status, reg_no, name, site_id, edutype_id, major_id, grade_id, semester_id from (select distinct egr.id, egr.student_id, egr.link, egr.discoursename, egr.discoursecontent, egr.discourseplan, egr.remark, egr.teacher_note, egr.siteteacher_note, egr.status, esi.reg_no, esi.name, esi.site_id, esi.edutype_id, esi.major_id, esi.grade_id, esis.id as semester_id, pi.id as pici_id from entity_graduate_regbegincourse egr, entity_student_info esi, entity_usernormal_info eui, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esis, entity_graduate_pici pi where egr.student_id = esi.id and egr.student_id = eui.id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and eca.semester_id = pi.semester_id and ee.student_id = esi.id and eci.id = '000001') ";
	//վʦ
	//private String SQLREGGEGINCOURSE1 ="select distinct id, student_id, link, discoursename, discoursecontent, discourseplan, remark, teacher_note, siteteacher_note, status, reg_no, name, site_id, edutype_id, major_id, grade_id, semester_id from (select distinct egr.id, egr.student_id, egr.link, egr.discoursename, egr.discoursecontent, egr.discourseplan, egr.remark, egr.teacher_note, egr.siteteacher_note, egr.status, esi.reg_no, esi.name, esi.site_id, esi.edutype_id, esi.major_id, esi.grade_id, esis.id as semester_id, pi.id as pici_id from entity_graduate_regbegincourse egr, entity_student_info esi, entity_usernormal_info eui, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esis, entity_graduate_pici pi where egr.student_id = esi.id and egr.student_id = eui.id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and eca.semester_id = pi.semester_id and ee.student_id = esi.id and eci.id = '000001') ";
	//ڷվʦ
	//private String SQLREGGEGINCOURSE2 ="select distinct id, student_id, link, discoursename, discoursecontent, discourseplan, remark, teacher_note, siteteacher_note, status, reg_no, name, site_id, edutype_id, major_id, grade_id, semester_id from (select distinct egr.id, egr.student_id, egr.link, egr.discoursename, egr.discoursecontent, egr.discourseplan, egr.remark, egr.teacher_note, egr.siteteacher_note, egr.status, esi.reg_no, esi.name, esi.site_id, esi.edutype_id, esi.major_id, esi.grade_id, esis.id as semester_id, pi.id as pici_id from entity_graduate_regbegincourse egr, entity_student_info esi, entity_usernormal_info eui, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esis, entity_graduate_pici pi where egr.student_id = esi.id and egr.student_id = eui.id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and eca.semester_id = pi.semester_id and ee.student_id = esi.id and eci.id = '000001') ";
    private String SQLSITETUTORTEACHER = "";

	//private String SQLMETAPHASECHECK = "select id ,student_id,link,discoursename,completetask,anaphaseplan,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id  from (select egm.id ,egm.student_id,link,egm.discoursename,egm.completetask,egm.anaphaseplan,egm.remark,egm.teacher_note,egm.siteteacher_note,egm.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id from entity_graduate_metaphasecheck egm,entity_student_info  esi,entity_usernormal_info   eui where egm.student_id = esi.id and egm.student_id = eui.id)";
   //2008-05-28 private String SQLMETAPHASECHECK = "select id ,student_id,link,discoursename,completetask,anaphaseplan,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,pici_id  from (select egm.id ,egm.student_id,link,egm.discoursename,egm.completetask,egm.anaphaseplan,egm.remark,egm.teacher_note,egm.siteteacher_note,egm.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id,egrp.id as pici_id from entity_graduate_metaphasecheck egm,entity_student_info  esi,entity_usernormal_info   eui ,entity_graduate_pici egrp where egm.student_id = esi.id and egm.student_id = eui.id and egrp.id||esi.grade_id || esi.edutype_id || esi.major_id in (select egp.pici_id||egp.grade_id || egp.edutype_id || egp.major_id   from entity_graduate_picilimit egp))";
    //
   // private String SQLMETAPHASECHECK = "select distinct id ,student_id,link,discoursename,completetask,anaphaseplan,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,pici_id  from (select egm.id ,egm.student_id,link,egm.discoursename,egm.completetask,egm.anaphaseplan,egm.remark,egm.teacher_note,egm.siteteacher_note,egm.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id,egrp.id as pici_id from entity_graduate_metaphasecheck egm,entity_student_info  esi,entity_usernormal_info   eui ,entity_graduate_pici egrp ,entity_elective      ee,entity_teach_class   etc, entity_course_active eca, entity_course_info   eci, entity_semester_info esis where egm.student_id = esi.id and egm.student_id = eui.id and egrp.status='1' and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = esi.id and eci.id = '000001' and egrp.starttime>esis.start_date and egrp.endtime<esis.end_date)";
    private String SQLMETAPHASECHECK = "select distinct id ,student_id,link,discoursename,completetask,anaphaseplan,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,pici_id ,semester_id from (select egm.id ,egm.student_id,link,egm.discoursename,egm.completetask,egm.anaphaseplan,egm.remark,egm.teacher_note,egm.siteteacher_note,egm.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id,egrp.id as pici_id,esis.id as semester_id from entity_graduate_metaphasecheck egm,entity_student_info  esi,entity_usernormal_info   eui ,entity_graduate_pici egrp ,entity_elective      ee,entity_teach_class   etc, entity_course_active eca, entity_course_info   eci, entity_semester_info esis where egm.student_id = esi.id and egm.student_id = eui.id and egrp.status='1' and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = esi.id and eci.id = '000001' and egrp.status='1') ";
    
    //private String SQLREJOIN ="select id,student_id,link,discoursename,completestatus,requistiontype,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id from (select egr.id,egr.student_id,egr.link,egr.discoursename,egr.completestatus,egr.requistiontype,egr.remark,egr.teacher_note,egr.siteteacher_note,egr.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id  from entity_graduate_rejoinreques egr,entity_student_info  esi,entity_usernormal_info   eui where egr.student_id = esi.id and egr.student_id = eui.id)";
    
    //2008-05-28 private String SQLREJOIN ="select id,student_id,link,discoursename,completestatus,requistiontype,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,pici_id from (select egr.id,egr.student_id,egr.link,egr.discoursename,egr.completestatus,egr.requistiontype,egr.remark,egr.teacher_note,egr.siteteacher_note,egr.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id ,egrp.id as pici_id from entity_graduate_rejoinreques egr,entity_student_info  esi,entity_usernormal_info   eui ,entity_graduate_pici egrp where egr.student_id = esi.id and egr.student_id = eui.id and egrp.id||esi.grade_id || esi.edutype_id || esi.major_id in (select egp.pici_id||egp.grade_id || egp.edutype_id || egp.major_id   from entity_graduate_picilimit egp))";
    //
    //private String SQLREJOIN ="select id,student_id,link,discoursename,completestatus,requistiontype,remark,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,pici_id from (select distinct egr.id,egr.student_id,egr.link,egr.discoursename,egr.completestatus,egr.requistiontype,egr.remark,egr.teacher_note,egr.siteteacher_note,egr.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id ,egrp.id as pici_id from entity_graduate_rejoinreques egr,entity_student_info  esi,entity_usernormal_info  eui ,entity_graduate_pici egrp ,entity_elective      ee,entity_teach_class   etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis where egr.student_id = esi.id  and egr.student_id = eui.id  and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = esi.id and eci.id= '000001' and esis.start_date<egrp.starttime and esis.end_date>egrp.endtime and egrp.status='1')";
    private String SQLREJOIN ="select id, student_id, link, discoursename, completestatus, requistiontype, remark, teacher_note, siteteacher_note, status, reg_no, name, site_id, edutype_id, major_id, grade_id, pici_id, semester_id from (select distinct egr.id, egr.student_id, egr.link, egr.discoursename, egr.completestatus, egr.requistiontype, egr.remark, egr.teacher_note, egr.siteteacher_note, egr.status, esi.reg_no, esi.name, esi.site_id, esi.edutype_id, esi.major_id, esi.grade_id, egrp.id as pici_id, esis.id as semester_id from entity_graduate_rejoinreques egr, entity_student_info esi, entity_usernormal_info eui, entity_graduate_pici egrp, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esis where egr.student_id = esi.id and egr.student_id = eui.id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = esi.id and eca.semester_id = egrp.semester_id and eci.id = '000001' and egrp.status = '1') ";
    
	//private String SQLDISCOURSE ="select id,student_id,link,discoursename,discoursecontent,remark,requisitiontype,requisitiongrade,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id  from (select egd.id,egd.student_id,egd.link,egd.discoursename,egd.discoursecontent,egd.remark,egd.requisitiontype,egd.requisitiongrade,egd.teacher_note,egd.siteteacher_note,egd.status,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id from entity_graduate_discourse egd ,entity_student_info  esi,entity_usernormal_info   eui where egd.student_id = esi.id and egd.student_id = eui.id) ";
    //2008-05-28 private String SQLDISCOURSE ="select id,student_id,link,discoursename,discoursecontent,remark,requisitiontype,requisitiongrade,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,pici_id,score_status, score_note  from (select egd.id,egd.student_id,egd.link,egd.discoursename,egd.discoursecontent,egd.remark,egd.requisitiontype,egd.requisitiongrade,egd.teacher_note,egd.siteteacher_note,egd.status, egd.score_status,egd.score_note,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id ,egrp.id as pici_id from entity_graduate_discourse egd ,entity_student_info  esi,entity_usernormal_info   eui,entity_graduate_pici egrp where egd.student_id = esi.id and egd.student_id = eui.id and egrp.id||esi.grade_id || esi.edutype_id || esi.major_id in (select egp.pici_id||egp.grade_id || egp.edutype_id || egp.major_id   from entity_graduate_picilimit egp))";
   
    //entity_graduate_subjectsearch,ʵִͲѯ
    //2008-05-28 private String SQLDISCOURSE ="select id,student_id,link,discoursename,discoursecontent,remark,requistiontype,requisitiongrade,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,pici_id,score_status, score_note  from (select egd.id,egd.student_id,egd.link,egd.discoursename,egd.discoursecontent,egd.remark,subj.requistiontype,egd.requisitiongrade,egd.teacher_note,egd.siteteacher_note,egd.status, egd.score_status,egd.score_note,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id ,egrp.id as pici_id from entity_graduate_discourse egd, entity_graduate_rejoinreques subj ,entity_student_info  esi,entity_usernormal_info   eui,entity_graduate_pici egrp where egd.student_id=subj.student_id(+) and egd.student_id = esi.id and egd.student_id = eui.id and egrp.id||esi.grade_id || esi.edutype_id || esi.major_id in (select egp.pici_id||egp.grade_id || egp.edutype_id || egp.major_id   from entity_graduate_picilimit egp)) ";
    //
    //private String SQLDISCOURSE ="select id,student_id,link,discoursename,discoursecontent,remark,requistiontype,requisitiongrade,teacher_note,siteteacher_note,status,reg_no,name,site_id,edutype_id,major_id,grade_id,pici_id,score_status, score_note  from (select distinct egd.id,egd.student_id,egd.link,egd.discoursename,egd.discoursecontent,egd.remark,subj.requistiontype,egd.requisitiongrade,egd.teacher_note,egd.siteteacher_note,egd.status, egd.score_status,egd.score_note,esi.reg_no,esi.name,esi.site_id,esi.edutype_id,esi.major_id,esi.grade_id , egrp.id as pici_id  from entity_graduate_discourse egd, entity_graduate_rejoinreques subj ,entity_student_info  esi,entity_usernormal_info   eui,entity_graduate_pici egrp ,entity_elective      ee,entity_teach_class   etc,entity_course_active eca,entity_course_info   eci,entity_semester_info esis where egd.student_id=subj.student_id(+) and egd.student_id = esi.id and egrp.status='1' and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and ee.student_id = esi.id and eci.id in ('000001','000011') and egrp.starttime>esis.start_date and egrp.endtime<esis.end_date ) ";
    private String SQLDISCOURSE ="select id, student_id, link, discoursename, discoursecontent, remark, requistiontype, requisitiongrade, teacher_note, siteteacher_note, status, reg_no, name, site_id, edutype_id, major_id, grade_id, pici_id, score_status, score_note, semester_id from (select distinct egd.id, egd.student_id, egd.link, egd.discoursename, egd.discoursecontent, egd.remark, subj.requistiontype, esi.graduate_grade as requisitiongrade, egd.teacher_note, egd.siteteacher_note, egd.status, egd.score_status, egd.score_note, esi.reg_no, esi.name, esi.site_id, esi.edutype_id, esi.major_id, esi.grade_id, egrp.id as pici_id, esis.id as semester_id from entity_graduate_discourse egd, entity_graduate_rejoinreques subj, entity_student_info esi, entity_usernormal_info eui, entity_graduate_pici egrp, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esis where egd.student_id = subj.student_id(+) and egd.student_id = esi.id and egrp.status = '1' and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and egrp.semester_id = eca.semester_id and ee.student_id = esi.id and eci.id in ('000001', '000011')) ";
    
	//2008-05028 private String SQLNONEGRADEEDUMAJORSFORTEACHER = "select teacherid,gradeid,gradename,edutypeid,edutypename,majorid,majorname  from (select egi.id   as gradeid,egi.name as gradename,eet.id   as edutypeid,eet.name as edutypename,emi.id   as majorid,emi.name as majorname,eti.id as teacherid  from entity_grade_info  egi,entity_edu_type eet,entity_major_info emi,entity_graduate_picilimit egp,entity_graduate_pici egp2,entity_teacher_info  eti where egp2.id = egp.pici_id  and egp2.status = '1' and  eti.type ='1' and egp.grade_id = egi.id  and egp.edutype_id =eet.id  and egp.major_id =  emi.id  and eti.id || egi.id || eet.id || emi.id not in(select teacher_id || grade_id || edutype_id || major_id from entity_graduate_teacherlimit))"; 
	//
    private String SQLNONEGRADEEDUMAJORSFORTEACHER = "select teacherid,gradeid,gradename,edutypeid,edutypename,majorid,majorname from (select egi.id   as gradeid,egi.name as gradename,eet.id   as edutypeid,eet.name as edutypename,emi.id   as majorid,emi.name as majorname,eti.id   as teacherid from entity_grade_info         egi, entity_edu_type           eet, entity_major_info         emi, entity_teacher_info       eti  where  eti.type = '1' and eti.id || egi.id || eet.id || emi.id not in (select teacher_id || grade_id || edutype_id || major_id from entity_graduate_teacherlimit))";
    
    private String SQLGRADEEDUMAJORSFORTEACHER ="select id,teacherid,gradeid,edutypeid,majorid from (select egt.id, egt.teacher_id as teacherid, egt.grade_id as gradeid, egt.edutype_id as edutypeid, egt.major_id as majorid from entity_graduate_teacherlimit egt)";
	
    //private String SQLNONESELECTSTUDENT ="select studentid,siteteacherid,gradeid,edutypeid,majorid  from (select esi.id as studentid,esi.grade_id as gradeid,esi.edutype_id as edutypeid,esi.major_id as majorid, esis.id as siteteacherid from entity_student_info esi, entity_siteteacher_info esis where esi.grade_id || esi.edutype_id || esi.major_id in (select egp1.grade_id || egp1.edutype_id || egp1.major_id from entity_graduate_pici egp, entity_graduate_picilimit egp1 where egp.id = egp1.pici_id  and egp.status = '1')  and esis.type ='1' and esis.id || esi.id not in   (select egs.siteteacher_id || egs.student_id from entity_graduate_siteteachlimit egs))";
    //private String SQLNONESELECTSTUDENT ="select studentid,siteteacherid,siteid,gradeid,edutypeid,majorid,reg_no,name  from (select esi.id as studentid,esi.site_id as siteid,esi.grade_id as gradeid,esi.edutype_id as edutypeid,esi.major_id as majorid,esi.reg_no as reg_no,esi.name as name, esis.id as siteteacherid from entity_student_info esi, entity_siteteacher_info esis where esi.grade_id || esi.edutype_id || esi.major_id in (select egp1.grade_id || egp1.edutype_id || egp1.major_id from entity_graduate_pici egp, entity_graduate_picilimit egp1 where egp.id = egp1.pici_id  and egp.status = '1')  and esis.type ='1' and esis.id || esi.id not in   (select egs.siteteacher_id || egs.student_id from entity_graduate_siteteachlimit egs))";
    //private String SQLNONESELECTSTUDENT ="select studentid,siteteacherid,siteid,gradeid,edutypeid,majorid,reg_no,name  from (select esi.id as studentid,esi.site_id as siteid,esi.grade_id as gradeid,esi.edutype_id as edutypeid,esi.major_id as majorid,esi.reg_no as reg_no,esi.name as name, esis.id as siteteacherid from entity_student_info esi, entity_siteteacher_info esis where esi.grade_id || esi.edutype_id || esi.major_id in (select egp1.grade_id || egp1.edutype_id || egp1.major_id from entity_graduate_pici egp, entity_graduate_picilimit egp1 where egp.id = egp1.pici_id  and egp.status = '1')  and esis.type ='1' and esi.id not in   (select egs.student_id from entity_graduate_siteteachlimit egs))";
    
    //2008-05-28 private String SQLNONESELECTSTUDENT ="select studentid,siteteacherid,siteid,gradeid, edutypeid,majorid,reg_no,name,course_name,course_id,semester_id,semester_name from (select distinct a.studentid, a.siteteacherid, a.siteid, a.gradeid,  a.edutypeid, a.majorid, a.reg_no, a.name, b.course_name, b.course_id,  b.semester_id, b.semester_name from (select esi.id         as studentid,  esi.site_id    as siteid, esi.grade_id   as gradeid, esi.edutype_id as  edutypeid, esi.major_id   as majorid, esi.reg_no     as reg_no, esi.name as  name, esis.id        as siteteacherid from entity_student_info esi,  entity_siteteacher_info esis where esi.grade_id || esi.edutype_id ||  esi.major_id in (select egp1.grade_id || egp1.edutype_id || egp1.major_id from  entity_graduate_pici      egp, entity_graduate_picilimit egp1 where egp.id =  egp1.pici_id and egp.status = '1') and esis.type = '1' and esi.id not in (select  egs.student_id from entity_graduate_siteteachlimit egs)) a, (select distinct  ee.student_id, eci.name as course_name, eci.id as course_id, esi.id as  semester_id, esi.name as semester_name from entity_elective      ee,  entity_teach_class   etc, entity_course_active eca, entity_course_info   eci,  entity_semester_info esi where ee.teachclass_id = etc.id and etc.open_course_id  = eca.id and eca.semester_id = esi.id and eca.course_id = eci.id) b where  a.studentid = b.student_id   and (b.course_id ='000001' or b.course_id ='000011'))  ";
    private String SQLNONESELECTSTUDENT ="select distinct studentid,siteteacherid,siteid,gradeid, edutypeid,majorid,reg_no,name,course_name,course_id,semester_id,semester_name from (select distinct a.studentid, a.siteteacherid, a.siteid, a.gradeid,  a.edutypeid, a.majorid, a.reg_no, a.name, b.course_name, b.course_id,  b.semester_id, b.semester_name from (select esi.id         as studentid,  esi.site_id    as siteid, esi.grade_id   as gradeid, esi.edutype_id as  edutypeid, esi.major_id   as majorid, esi.reg_no     as reg_no, esi.name as  name, esis.id        as siteteacherid from entity_student_info esi,  entity_siteteacher_info esis where  esis.type = '1' and esi.id not in (select  egs.student_id from entity_graduate_siteteachlimit egs)) a, (select distinct  ee.student_id, eci.name as course_name, eci.id as course_id, esi.id as  semester_id, esi.name as semester_name from entity_elective      ee,  entity_teach_class   etc, entity_course_active eca, entity_course_info   eci, entity_semester_info esi where ee.teachclass_id = etc.id and etc.open_course_id  = eca.id and eca.semester_id = esi.id and eca.course_id = eci.id) b where  a.studentid = b.student_id   and (b.course_id ='000001' or b.course_id ='000011'))  ";
    
    //private String SQLSELECTSTUDENT ="select id,studentid,studentName,siteid,gradeid,edutypeid,majorid ,siteteacherid from ( select egs.id,egs.siteteacher_id as siteteacherid,egs.student_id as studentid,esi.name as studentname, esi.site_id as siteid,esi.grade_id as gradeid,esi.edutype_id as edutypeid,esi.major_id as majorid from entity_graduate_siteteachlimit egs,entity_student_info esi where egs.student_id = esi.id)";	
   
    private String SQLSELECTSTUDENT ="select distinct id, studentid, studentName, siteid, gradeid, edutypeid, majorid, siteteacherid, course_name, course_id, semester_id, semester_name from (select a.id, a.studentid, a.studentName, a.siteid, a.gradeid, a.edutypeid, a.majorid, a.siteteacherid, b.course_name, b.course_id, b.semester_id, b.semester_name from (select egs.id, egs.siteteacher_id as siteteacherid, egs.student_id as studentid, esi.name as studentname, esi.site_id as siteid, esi.grade_id as gradeid, esi.edutype_id as edutypeid, esi.major_id as majorid from entity_graduate_siteteachlimit egs, entity_student_info esi where egs.student_id = esi.id) a, (select distinct ee.student_id, eci.name as course_name, eci.id as course_id, esi.id as semester_id, esi.name as semester_name from entity_elective ee, entity_teach_class   etc, entity_course_active eca, entity_course_info eci, entity_semester_info esi where ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.semester_id = esi.id and eca.course_id = eci.id) b where a.studentid = b.student_id and (b.course_id ='000001' or b.course_id ='000011'))";
    
    private String SQLSELECTSTUDENT2 = "select id,studentid,reg_no,studentName,siteid,gradeid,edutypeid,majorid ,siteteacherid from ( select egs.id,egs.siteteacher_id as siteteacherid,egs.student_id as studentid,esi.name as studentname, esi.site_id as siteid,esi.grade_id as gradeid,esi.edutype_id as edutypeid,esi.major_id as majorid,esi.reg_no as reg_no from entity_graduate_siteteachlimit egs,entity_student_info esi where egs.student_id = esi.id)" ;
 
    public List getGraduateDesignBatch(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLGRADUATEBATCH
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		boolean active = false;
		ArrayList graduateList = null;
		try {
			db = new dbpool();
			graduateList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleGraduatePici graduate = new OracleGraduatePici();
				graduate.setId(rs.getString("id"));
				graduate.setName(rs.getString("name"));

				TimeDef graduateDesignTime = new TimeDef();
				graduateDesignTime.setStartTime(rs.getString("starttime"));
				graduateDesignTime.setEndTime(rs.getString("endtime"));
				graduate.setGraduateDesignTime(graduateDesignTime);

				TimeDef subjectTime = new TimeDef();
				subjectTime.setStartTime(rs.getString("subjectstime"));
				subjectTime.setEndTime(rs.getString("subjectetime"));
				graduate.setSubjectTime(subjectTime);

				TimeDef openSubTime = new TimeDef();
				openSubTime.setStartTime(rs.getString("opensubstime"));
				openSubTime.setEndTime(rs.getString("endsubetime"));
				graduate.setOpenSubTime(openSubTime);

				TimeDef subSubTime = new TimeDef();
				subSubTime.setStartTime(rs.getString("subsubstime"));
				subSubTime.setEndTime(rs.getString("subsubetime"));
				graduate.setSubSubTime(subSubTime);

				TimeDef rejoinTime = new TimeDef();
				rejoinTime.setStartTime(rs.getString("rejoinstime"));
				rejoinTime.setEndTime(rs.getString("rejoinetime"));
				graduate.setReJoinTime(rejoinTime);

				TimeDef reportGradeTime = new TimeDef();
				reportGradeTime.setStartTime(rs.getString("reportgradestime"));
				reportGradeTime.setEndTime(rs.getString("reportgradeetime"));
				graduate.setReportGradeTime(reportGradeTime);

				graduate.setStatus(rs.getString("status"));
				if (rs.getString("status").equals("1"))
					active = true;
				else
					active = false;
				graduate.setSemester_id(rs.getString("semester_id"));
				graduate.setSemester_name(rs.getString("semester_name"));
				graduate.setActive(active);
				graduateList.add(graduate);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return graduateList;
	}
    
    /**
     * ôҵб
     */
    public List getGraduateExecDesignBatch(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLGRADUATE_EXEC_BATCH
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		boolean active = false;
		ArrayList graduateList = null;
		try {
			db = new dbpool();
			graduateList = new ArrayList();
			if (page != null) {
				int pageint = page.getPageInt();
				int pagesize = page.getPageSize();
				rs = db.execute_oracle_page(sql, pageint, pagesize);
			} else {
				rs = db.executeQuery(sql);
			}
			while (rs != null && rs.next()) {
				OracleGraduateExecPici graduate = new OracleGraduateExecPici();
				graduate.setId(rs.getString("id"));
				graduate.setName(rs.getString("name"));

				TimeDef graduateDesignTime = new TimeDef();
				graduateDesignTime.setStartTime(rs.getString("starttime"));
				graduateDesignTime.setEndTime(rs.getString("endtime"));
				graduate.setGraduateDesignTime(graduateDesignTime);

				TimeDef subjectTime = new TimeDef();
				subjectTime.setStartTime(rs.getString("subjectstime"));
				subjectTime.setEndTime(rs.getString("subjectetime"));
				graduate.setSubjectTime(subjectTime);

				TimeDef openSubTime = new TimeDef();
				openSubTime.setStartTime(rs.getString("opensubstime"));
				openSubTime.setEndTime(rs.getString("endsubetime"));
				graduate.setOpenSubTime(openSubTime);

				TimeDef subSubTime = new TimeDef();
				subSubTime.setStartTime(rs.getString("subsubstime"));
				subSubTime.setEndTime(rs.getString("subsubetime"));
				graduate.setSubSubTime(subSubTime);

				TimeDef rejoinTime = new TimeDef();
				rejoinTime.setStartTime(rs.getString("rejoinstime"));
				rejoinTime.setEndTime(rs.getString("rejoinetime"));
				graduate.setReJoinTime(rejoinTime);

				TimeDef writeDiscourseTime = new TimeDef();
				writeDiscourseTime.setStartTime(rs.getString("WRITEDISCOURSESTIME"));
				writeDiscourseTime.setEndTime(rs.getString("WRITEDISCOURSEETIME"));
				graduate.setWriteDiscourseTiem(writeDiscourseTime);

				TimeDef reportGradeTime = new TimeDef();
				reportGradeTime.setStartTime(rs.getString("reportgradestime"));
				reportGradeTime.setEndTime(rs.getString("reportgradeetime"));
				graduate.setReportGradeTime(reportGradeTime);
				graduate.setSemester_id(rs.getString("semester_id"));
				graduate.setSemester_name(rs.getString("semester_name"));

				graduate.setStatus(rs.getString("status"));
				if (rs.getString("status").equals("1"))
					active = true;
				else
					active = false;
				graduate.setActive(active);
				graduateList.add(graduate);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return graduateList;
	}
    
    public String getActiveDiscourseCheckStudentIdString(List searchProperty) {
    	String str = "";
    	String sql = SQLACTIVEDESCOURSECHECKSTUDENTID + Conditions.convertToCondition(searchProperty, null);
    	dbpool db = new dbpool();
    	MyResultSet rs = db.executeQuery(sql);
    	if(rs!=null) {
    		try {
				while(rs.next()) {
					str += rs.getString("student_id") + "|";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
			finally {
				db.close(rs);
				db = null;
			}
    		
    	}
    	return str;
    }

    
    public String getActiveSubjectSearchStudentIdString() {
    	String str = "";
    	String sql = SQLACTIVESUBJECTSEARCHSTUDENTID;
    	dbpool db = new dbpool();
    	MyResultSet rs = db.executeQuery(sql);
    	if(rs!=null) {
    		try {
				while(rs.next()) {
					str += rs.getString("student_id") + "|";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
			finally {
				db.close(rs);
				db = null;
			}
   		
    	}
    	return str;
    }
    
    public String getActiveRegBeginCourseStudentIdString(List searchProperty) {
    	String str = "";
    	String sql = SQLACTIVEREGBEGINCOURSESTUDENTID + Conditions.convertToCondition(searchProperty, null);
    	dbpool db = new dbpool();
    	MyResultSet rs = db.executeQuery(sql);
    	if(rs!=null) {
    		try {
				while(rs.next()) {
					str += rs.getString("student_id") + "|";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
			finally {
				db.close(rs);
				db = null;
			}
    		
    	}
    	return str;
    }
    
    public String getActiveMetaphaseCheckStudentIdString(List searchProperty) {
    	String str = "";
    	String sql = SQLACTIVEMETAPHASECHECKSTUDENTID + Conditions.convertToCondition(searchProperty, null);
    	dbpool db = new dbpool();
    	MyResultSet rs = db.executeQuery(sql);
    	if(rs!=null) {
    		try {
				while(rs.next()) {
					str += rs.getString("student_id") + "|";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
			finally {
				db.close(rs);
				db = null;
			}
    		
    	}
    	return str;
    }
    
    
	public int getGraduateDesignBatchNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLGRADUATEBATCH
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}
	
	/**
	 * ñҵҵ
	 */
	public int getGraduateExecDesignBatchNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLGRADUATE_EXEC_BATCH
		+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public Pici getGraduateDesignBatch(List searchProperty) {
		dbpool db = new dbpool();
		String sql = SQLGRADUATEBATCH
				+ Conditions.convertToCondition(searchProperty, null);
		MyResultSet rs = null;
		boolean active = false;
		OracleGraduatePici graduate = null;
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				graduate = new OracleGraduatePici();
				graduate.setId(rs.getString("id"));
				graduate.setName(rs.getString("name"));

				TimeDef graduateDesignTime = new TimeDef();
				graduateDesignTime.setStartTime(rs.getString("starttime"));
				graduateDesignTime.setEndTime(rs.getString("endtime"));
				graduate.setGraduateDesignTime(graduateDesignTime);

				TimeDef subjectTime = new TimeDef();
				subjectTime.setStartTime(rs.getString("subjectstime"));
				subjectTime.setEndTime(rs.getString("subjectetime"));
				graduate.setSubjectTime(subjectTime);

				TimeDef openSubTime = new TimeDef();
				openSubTime.setStartTime(rs.getString("opensubstime"));
				openSubTime.setEndTime(rs.getString("endsubetime"));
				graduate.setOpenSubTime(openSubTime);

				TimeDef subSubTime = new TimeDef();
				subSubTime.setStartTime(rs.getString("subsubstime"));
				subSubTime.setEndTime(rs.getString("subsubetime"));
				graduate.setSubSubTime(subSubTime);

				TimeDef rejoinTime = new TimeDef();
				rejoinTime.setStartTime(rs.getString("rejoinstime"));
				rejoinTime.setEndTime(rs.getString("rejoinetime"));
				graduate.setReJoinTime(rejoinTime);

				TimeDef reportGradeTime = new TimeDef();
				reportGradeTime.setStartTime(rs.getString("reportgradestime"));
				reportGradeTime.setEndTime(rs.getString("reportgradeetime"));
				graduate.setReportGradeTime(reportGradeTime);
				graduate.setSemester_id(rs.getString("semester_id"));
				graduate.setSemester_name(rs.getString("semester_name"));

				graduate.setStatus(rs.getString("status"));
				if (rs.getString("status").equals("1"))
					active = true;
				else
					active = false;
				graduate.setActive(active);
			}
		} catch (SQLException es) {
			EntityLog
					.setError("OracleBasicGraduateList@Method:getGraduateDesignBatch  error!!!    sql="
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return graduate;
	}
	
	public Map getGraduateTypes(String teacher_id) {
		dbpool db = new dbpool();
		String sql = "select distinct d.id from entity_graduate_siteteachlimit a,entity_elective b,entity_teach_class e,entity_course_active c,entity_course_info d where a.student_id = b.student_id and b.teachclass_id = e.id and e.open_course_id=c.id and c.course_id = d.id and d.id in ('000011','000001') and a.siteteacher_id='" + teacher_id + "'" ;
		MyResultSet rs = null;
		Map tmpList = new HashMap() ;
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				String id = rs.getString("id") ;
				tmpList.put(id,"");
			}
		} catch (SQLException es) {
			EntityLog
					.setError("OracleBasicGraduateList@Method:getGraduateTypes  error!!!    sql="
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return tmpList;
	}

	
	/**
	 * ôҵб;
	 */
	public Pici getGraduateExecDesignBatch(List searchProperty) {
		dbpool db = new dbpool();
		String sql = SQLGRADUATE_EXEC_BATCH
				+ Conditions.convertToCondition(searchProperty, null);
		MyResultSet rs = null;
		boolean active = false;
		OracleGraduateExecPici graduate = null;
		try {
			rs = db.executeQuery(sql);
			while (rs != null && rs.next()) {
				graduate = new OracleGraduateExecPici();
				graduate.setId(rs.getString("id"));
				graduate.setName(rs.getString("name"));

				TimeDef graduateDesignTime = new TimeDef();
				graduateDesignTime.setStartTime(rs.getString("starttime"));
				graduateDesignTime.setEndTime(rs.getString("endtime"));
				graduate.setGraduateDesignTime(graduateDesignTime);

				TimeDef subjectTime = new TimeDef();
				subjectTime.setStartTime(rs.getString("subjectstime"));
				subjectTime.setEndTime(rs.getString("subjectetime"));
				graduate.setSubjectTime(subjectTime);

				TimeDef openSubTime = new TimeDef();
				openSubTime.setStartTime(rs.getString("opensubstime"));
				openSubTime.setEndTime(rs.getString("endsubetime"));
				graduate.setOpenSubTime(openSubTime);

				TimeDef subSubTime = new TimeDef();
				subSubTime.setStartTime(rs.getString("subsubstime"));
				subSubTime.setEndTime(rs.getString("subsubetime"));
				graduate.setSubSubTime(subSubTime);

				TimeDef rejoinTime = new TimeDef();
				rejoinTime.setStartTime(rs.getString("rejoinstime"));
				rejoinTime.setEndTime(rs.getString("rejoinetime"));
				graduate.setReJoinTime(rejoinTime);

				TimeDef writeDiscourseTime = new TimeDef();
				writeDiscourseTime.setStartTime(rs.getString("WRITEDISCOURSESTIME"));
				writeDiscourseTime.setEndTime(rs.getString("WRITEDISCOURSEETIME"));
				graduate.setWriteDiscourseTiem(writeDiscourseTime);

				TimeDef reportGradeTime = new TimeDef();
				reportGradeTime.setStartTime(rs.getString("reportgradestime"));
				reportGradeTime.setEndTime(rs.getString("reportgradeetime"));
				graduate.setReportGradeTime(reportGradeTime);
				graduate.setSemester_id(rs.getString("semester_id"));
				graduate.setSemester_name(rs.getString("semester_name"));

				graduate.setStatus(rs.getString("status"));
				if (rs.getString("status").equals("1"))
					active = true;
				else
					active = false;
				graduate.setActive(active);
			}
		} catch (SQLException es) {
			EntityLog
					.setError("OracleBasicGraduateList@Method:getGraduateDesignBatch  error!!!    sql="
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return graduate;
	}

	public List getGradeEduTypeMajors(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLGRADEEDUMAJORS
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				OracleGrade oracleGrade = new OracleGrade();
				oracleGrade.setId(rs.getString("gradeid"));
				oracleGrade.setName(rs.getString("gradename"));

				OracleEduType oracleEduType = new OracleEduType();
				oracleEduType.setId(rs.getString("edutypeid"));
				oracleEduType.setName(rs.getString("edutypename"));

				OracleMajor oracleMajor = new OracleMajor();
				oracleMajor.setId(rs.getString("majorid"));
				oracleMajor.setName(rs.getString("majorName"));

				OraclePiciLimit oraclePiciLimit = new OraclePiciLimit();
				oraclePiciLimit.setGrade(oracleGrade);
				oraclePiciLimit.setEduType(oracleEduType);
				oraclePiciLimit.setMajor(oracleMajor);

				list.add(oraclePiciLimit);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getGradeEduTypeMajorsNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLGRADEEDUMAJORS
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getGradeEduTypeMajorsForPici(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLGRADEEDUMAJORFORPICI
				+ Conditions.convertToCondition(searchProperty, orderProperty);
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
				OracleGraduatePici oracleGraduatePici = new OracleGraduatePici(
						rs.getString("pici_id"));
				OracleGrade oracleGrade = new OracleGrade(rs
						.getString("grade_id"));
				OracleEduType oracleEduType = new OracleEduType(rs
						.getString("edutype_id"));
				OracleMajor oracleMajor = new OracleMajor(rs
						.getString("major_id"));

				OraclePiciLimit oraclePiciLimit = new OraclePiciLimit();
				oraclePiciLimit.setId(rs.getString("id"));
				oraclePiciLimit.setPiCi(oracleGraduatePici);
				oraclePiciLimit.setEduType(oracleEduType);
				oraclePiciLimit.setGrade(oracleGrade);
				oraclePiciLimit.setMajor(oracleMajor);

				list.add(oraclePiciLimit);
			}
		} catch (java.sql.SQLException es) {
			EntityLog
					.setError("OracleBasicGraduateList@Method:getGradeEduTypeMajorsForPici() error !!!   sql="
							+ sql);
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getGradeEduTypeMajorsForPiciNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLGRADEEDUMAJORFORPICI
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getSubjectQuestionaryList(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLSUBJECTQUESTIONARY
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary();
				OracleSiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(rs.getString("student_id"),"STUDENT");
				subjectQuestionary.setSiteTeacher(oracleSiteTeacherLimit.getSiteTeacher());
				subjectQuestionary.setId(rs.getString("id"));
               
				Student student = new OracleStudent(rs.getString("student_id"));
				subjectQuestionary.setScore(rs.getString("score"));
				subjectQuestionary.setScore_note(rs.getString("score_note"));
				subjectQuestionary.setScore_status(rs.getString("score_status"));
				subjectQuestionary.setLink(rs.getString("link"));
				subjectQuestionary.setMain_job(rs.getString("main_job"));
				subjectQuestionary.setSubjectName(rs.getString("subjectname"));
				subjectQuestionary.setSubjectContent(rs
						.getString("subjectcontent"));
				subjectQuestionary.setRemark(rs.getString("remark"));
				subjectQuestionary
						.setTeacher_note(rs.getString("teacher_note"));
				subjectQuestionary.setSiteTeacher_note(rs
						.getString("siteteacher_note"));
				subjectQuestionary.setStatus(rs.getString("status"));

				subjectQuestionary.setStudent(student);

				list.add(subjectQuestionary);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getSubjectQuestionaryListNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLSUBJECTQUESTIONARY
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getSubjectQuestionaryList2(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLSUBJECTQUESTIONARY2
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				SubjectQuestionary subjectQuestionary = new OracleSubjectQuestionary();
				OracleSiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(rs.getString("student_id"),"STUDENT");
				subjectQuestionary.setSiteTeacher(oracleSiteTeacherLimit.getSiteTeacher());
				subjectQuestionary.setId(rs.getString("subject_id"));
               
				Student student = new OracleStudent(rs.getString("student_id"));
				subjectQuestionary.setScore(rs.getString("score"));
				subjectQuestionary.setScore_note(rs.getString("score_note"));
				subjectQuestionary.setScore_status(rs.getString("score_status"));
				subjectQuestionary.setLink(rs.getString("link"));
				subjectQuestionary.setMain_job(rs.getString("main_job"));
				subjectQuestionary.setSubjectName(rs.getString("subjectname"));
				subjectQuestionary.setSubjectContent(rs
						.getString("subjectcontent"));
				subjectQuestionary.setRemark(rs.getString("remark"));
				subjectQuestionary
						.setTeacher_note(rs.getString("teacher_note"));
				subjectQuestionary.setSiteTeacher_note(rs
						.getString("siteteacher_note"));
				subjectQuestionary.setStatus(rs.getString("status"));

				subjectQuestionary.setStudent(student);

				list.add(subjectQuestionary);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getSubjectQuestionaryListNum2(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLSUBJECTQUESTIONARY2
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	
	public List getRegBeginCourseList(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLREGGEGINCOURSE
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				RegBgCourse oracleRegBgCourse = new OracleRegBgCourse();
				oracleRegBgCourse.setId(rs.getString("id"));
                
				OracleSiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(rs.getString("student_id"),"STUDENT");
				oracleRegBgCourse.setSiteTeacher(oracleSiteTeacherLimit.getSiteTeacher());
				
				
				Student student = new OracleStudent(rs.getString("student_id"));
				oracleRegBgCourse.setStudent(student);

				oracleRegBgCourse.setLink(rs.getString("link"));
				oracleRegBgCourse.setDiscourseName(rs
						.getString("discoursename"));
				oracleRegBgCourse.setDiscourseContent(rs
						.getString("discoursecontent"));
				oracleRegBgCourse.setDiscoursePlan(rs
						.getString("discourseplan"));
				oracleRegBgCourse.setRemark(rs.getString("remark"));
				oracleRegBgCourse.setTeacher_note(rs.getString("teacher_note"));
				oracleRegBgCourse.setSiteteacher_note(rs
						.getString("siteteacher_note"));
				oracleRegBgCourse.setStatus(rs.getString("status"));

				list.add(oracleRegBgCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
	
	//ڽʦ
	public List getRegBeginCourseList1(Page page, List searchProperty,
			List orderProperty,String teacher_id) {
		dbpool db = new dbpool();
		String sql = "select distinct id, student_id, link, discoursename, discoursecontent, discourseplan, remark, teacher_note, siteteacher_note, status, reg_no, name, site_id, edutype_id, major_id, grade_id, semester_id from (select distinct egr.id, egr.student_id, egr.link, egr.discoursename, egr.discoursecontent, egr.discourseplan, egr.remark, egr.teacher_note, egr.siteteacher_note, egr.status, esi.reg_no, esi.name, esi.site_id, esi.edutype_id, esi.major_id, esi.grade_id, esis.id as semester_id, pi.id as pici_id from entity_graduate_regbegincourse egr, (select a.* from entity_student_info a,entity_graduate_teacherlimit b where a.grade_id||a.major_id||a.edutype_id in b.grade_id||b.major_id||b.edutype_id and b.teacher_id = '"
				+ teacher_id
				+ "') esi, entity_usernormal_info eui, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esis, entity_graduate_pici pi where egr.student_id = esi.id and egr.student_id = eui.id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and eca.semester_id = pi.semester_id and ee.student_id = esi.id and eci.id = '000001')"
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				RegBgCourse oracleRegBgCourse = new OracleRegBgCourse();
				oracleRegBgCourse.setId(rs.getString("id"));
                
				OracleSiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(rs.getString("student_id"),"STUDENT");
				oracleRegBgCourse.setSiteTeacher(oracleSiteTeacherLimit.getSiteTeacher());
				
				
				Student student = new OracleStudent(rs.getString("student_id"));
				oracleRegBgCourse.setStudent(student);

				oracleRegBgCourse.setLink(rs.getString("link"));
				oracleRegBgCourse.setDiscourseName(rs
						.getString("discoursename"));
				oracleRegBgCourse.setDiscourseContent(rs
						.getString("discoursecontent"));
				oracleRegBgCourse.setDiscoursePlan(rs
						.getString("discourseplan"));
				oracleRegBgCourse.setRemark(rs.getString("remark"));
				oracleRegBgCourse.setTeacher_note(rs.getString("teacher_note"));
				oracleRegBgCourse.setSiteteacher_note(rs
						.getString("siteteacher_note"));
				oracleRegBgCourse.setStatus(rs.getString("status"));

				list.add(oracleRegBgCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
	//***********************************************************
	//ڸʦ
	public List getRegBeginCourseList2(Page page, List searchProperty,
			List orderProperty,String teacher_id) {
		dbpool db = new dbpool();
		String sql = "select distinct id, student_id, link, discoursename, discoursecontent, discourseplan, remark, teacher_note, siteteacher_note, status, reg_no, name, site_id, edutype_id, major_id, grade_id, semester_id from (select distinct egr.id, egr.student_id, egr.link, egr.discoursename, egr.discoursecontent, egr.discourseplan, egr.remark, egr.teacher_note, egr.siteteacher_note, egr.status, esi.reg_no, esi.name, esi.site_id, esi.edutype_id, esi.major_id, esi.grade_id, esis.id as semester_id, pi.id as pici_id from entity_graduate_regbegincourse egr, (select a.* from entity_student_info a,entity_graduate_siteteachlimit b where a.id in b.student_id and b.siteteacher_id = '"
				+ teacher_id
				+ "') esi, entity_usernormal_info eui, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esis, entity_graduate_pici pi where egr.student_id = esi.id and egr.student_id = eui.id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and eca.semester_id = pi.semester_id and ee.student_id = esi.id and eci.id = '000001')"
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				RegBgCourse oracleRegBgCourse = new OracleRegBgCourse();
				oracleRegBgCourse.setId(rs.getString("id"));
                
				OracleSiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(rs.getString("student_id"),"STUDENT");
				oracleRegBgCourse.setSiteTeacher(oracleSiteTeacherLimit.getSiteTeacher());
				
				
				Student student = new OracleStudent(rs.getString("student_id"));
				oracleRegBgCourse.setStudent(student);

				oracleRegBgCourse.setLink(rs.getString("link"));
				oracleRegBgCourse.setDiscourseName(rs
						.getString("discoursename"));
				oracleRegBgCourse.setDiscourseContent(rs
						.getString("discoursecontent"));
				oracleRegBgCourse.setDiscoursePlan(rs
						.getString("discourseplan"));
				oracleRegBgCourse.setRemark(rs.getString("remark"));
				oracleRegBgCourse.setTeacher_note(rs.getString("teacher_note"));
				oracleRegBgCourse.setSiteteacher_note(rs
						.getString("siteteacher_note"));
				oracleRegBgCourse.setStatus(rs.getString("status"));

				list.add(oracleRegBgCourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}
	//*************************************************************************
	public int getRegBeginCourseListNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLREGGEGINCOURSE
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}
	public int getRegBeginCourseListNum1(List searchProperty,String teacher_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select distinct id, student_id, link, discoursename, discoursecontent, discourseplan, remark, teacher_note, siteteacher_note, status, reg_no, name, site_id, edutype_id, major_id, grade_id, semester_id from (select distinct egr.id, egr.student_id, egr.link, egr.discoursename, egr.discoursecontent, egr.discourseplan, egr.remark, egr.teacher_note, egr.siteteacher_note, egr.status, esi.reg_no, esi.name, esi.site_id, esi.edutype_id, esi.major_id, esi.grade_id, esis.id as semester_id, pi.id as pici_id from entity_graduate_regbegincourse egr, (select a.* from entity_student_info a,entity_graduate_teacherlimit b where a.grade_id||a.major_id||a.edutype_id in b.grade_id||b.major_id||b.edutype_id and b.teacher_id = '"
			+ teacher_id
			+ "') esi, entity_usernormal_info eui, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esis, entity_graduate_pici pi where egr.student_id = esi.id and egr.student_id = eui.id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and eca.semester_id = pi.semester_id and ee.student_id = esi.id and eci.id = '000001')"
			+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}
	
	public int getRegBeginCourseListNum2(List searchProperty,String teacher_id) {
		dbpool db = new dbpool();
		String sql = "";
		sql = "select distinct id, student_id, link, discoursename, discoursecontent, discourseplan, remark, teacher_note, siteteacher_note, status, reg_no, name, site_id, edutype_id, major_id, grade_id, semester_id from (select distinct egr.id, egr.student_id, egr.link, egr.discoursename, egr.discoursecontent, egr.discourseplan, egr.remark, egr.teacher_note, egr.siteteacher_note, egr.status, esi.reg_no, esi.name, esi.site_id, esi.edutype_id, esi.major_id, esi.grade_id, esis.id as semester_id, pi.id as pici_id from entity_graduate_regbegincourse egr, (select a.* from entity_student_info a,entity_graduate_siteteachlimit b where a.id in b.student_id and b.siteteacher_id = '"
			+ teacher_id
			+ "') esi, entity_usernormal_info eui, entity_elective ee, entity_teach_class etc, entity_course_active eca, entity_course_info eci, entity_semester_info esis, entity_graduate_pici pi where egr.student_id = esi.id and egr.student_id = eui.id and ee.teachclass_id = etc.id and etc.open_course_id = eca.id and eca.course_id = eci.id and eca.semester_id = esis.id and eca.semester_id = pi.semester_id and ee.student_id = esi.id and eci.id = '000001')"
			+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getSiteTutorTeacherList(Page page, List searchProperty,
			List orderProperty) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getSiteTutorTeacherListNum(List searchProperty) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List getMetaphaseCheckList(Page page, List searchProperty,
			List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLMETAPHASECHECK
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				MetaphaseCheck oracleMetaphaseCheck = new OracleMetaphaseCheck();
				oracleMetaphaseCheck.setId(rs.getString("id"));
				OracleSiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(rs.getString("student_id"),"STUDENT");
				oracleMetaphaseCheck.setSiteTeacher(oracleSiteTeacherLimit.getSiteTeacher());
				
				Student student = new OracleStudent(rs.getString("student_id"));
                 
				oracleMetaphaseCheck.setStudent(student);

				oracleMetaphaseCheck.setLink(rs.getString("link"));
				oracleMetaphaseCheck.setDiscourseName(rs
						.getString("discoursename"));
				oracleMetaphaseCheck.setCompletetask(rs
						.getString("completetask"));
				oracleMetaphaseCheck.setAnaphasePlan(rs
						.getString("anaphaseplan"));
				oracleMetaphaseCheck.setRemark(rs.getString("remark"));
				oracleMetaphaseCheck.setTeacher_note(rs
						.getString("teacher_note"));
				oracleMetaphaseCheck.setSiteteacher_note(rs
						.getString("siteteacher_note"));
				oracleMetaphaseCheck.setStatus(rs.getString("status"));

				list.add(oracleMetaphaseCheck);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getMetaphaseCheckListNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLMETAPHASECHECK
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getRejoinRequesList(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLREJOIN
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				OracleRejoinRequisition oracleRejoinRequisition = new OracleRejoinRequisition();
				OracleSiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(rs.getString("student_id"),"STUDENT");
				oracleRejoinRequisition.setSiteTeacher(oracleSiteTeacherLimit.getSiteTeacher());
				
				//id,student_id,link,discoursename,completestatus,requistiontype,remark,teacher_note,siteteacher_note,status
				oracleRejoinRequisition.setId(rs.getString("id"));
				OracleStudent oracleStudent = new OracleStudent(rs.getString("student_id")); 
				oracleRejoinRequisition.setStudent(oracleStudent);
				
				oracleRejoinRequisition.setLink(rs.getString("link"));
				oracleRejoinRequisition.setDiscourseName(rs.getString("discoursename"));
				oracleRejoinRequisition.setCompleteStatus(rs.getString("completestatus"));
				oracleRejoinRequisition.setRequisitionType(rs.getString("requistiontype"));
				oracleRejoinRequisition.setRemark(rs.getString("remark"));
				oracleRejoinRequisition.setTeacher_note(rs.getString("teacher_note"));
				oracleRejoinRequisition.setSiteteacher_note(rs.getString("siteteacher_note"));
				oracleRejoinRequisition.setStatus(rs.getString("status"));

				list.add(oracleRejoinRequisition);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getRejoinRequesListNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLREJOIN
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getDisCourseList(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		String sql = SQLDISCOURSE
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				OracleDiscourse oracleDiscourse = new OracleDiscourse();
				OracleSiteTeacherLimit oracleSiteTeacherLimit = new OracleSiteTeacherLimit(rs.getString("student_id"),"STUDENT");
				oracleDiscourse.setSiteTeacher(oracleSiteTeacherLimit.getSiteTeacher());
				
				oracleDiscourse.setId(rs.getString("id"));
				Student student = new OracleStudent(rs.getString("student_id"));
				oracleDiscourse.setStudent(student);
				oracleDiscourse.setLink(rs.getString("link"));
				oracleDiscourse.setDiscourseName(rs.getString("discoursename"));
				oracleDiscourse.setDiscourseContent(rs.getString("discoursecontent"));
				oracleDiscourse.setRemark(rs.getString("remark"));
				oracleDiscourse.setRequisitionType(rs.getString("requistiontype"));
				oracleDiscourse.setRequisitionGrade(rs.getString("requisitiongrade"));
				oracleDiscourse.setTeacher_note(rs.getString("teacher_note"));
				oracleDiscourse.setSiteTeacher_note(rs.getString("siteteacher_note"));
				oracleDiscourse.setStatus(rs.getString("status"));
				oracleDiscourse.setScore_note(rs.getString("score_note"));
				oracleDiscourse.setScore_status(rs.getString("score_status"));
				list.add(oracleDiscourse);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getDisCourseListNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLDISCOURSE
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getNoneGradeEduMajorsForTeacher(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		//private String SQLGRADEEDUMAJORSFORTEACHER = "select teacherid,gradeid,gradename,edutypeid,edutypename,majorid,majorname  from (select egi.id   as gradeid,egi.name as gradename,eet.id   as edutypeid,eet.name as edutypename,emi.id   as majorid,emi.name as majorname,eti.id as teacherid  from entity_grade_info  egi,entity_edu_type eet,entity_major_info emi,entity_graduate_picilimit egp,entity_graduate_pici egp2,entity_teacher_info  eti where egp2.id = egp.pici_id  and egp2.status = '1' and  eti.type ='1' and egp.grade_id = egi.id  and egp.edutype_id =eet.id  and egp.major_id =  emi.id           and eti.id || egi.id || eet.id || emi.id not in(select teacher_id || grade_id || edutype_id || major_id from entity_graduate_teacherlimit))";
		String sql = SQLNONEGRADEEDUMAJORSFORTEACHER
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				OracleTeacherLimit oracleTeacherLimit = new OracleTeacherLimit();
				OracleTeacher oracleTeacher = new OracleTeacher(rs.getString("teacherid"));
				oracleTeacherLimit.setTeacher(oracleTeacher);
				
				OracleGrade oracleGrade = new OracleGrade(rs
						.getString("gradeid"));
				OracleEduType oracleEduType = new OracleEduType(rs
						.getString("edutypeid"));
				OracleMajor oracleMajor = new OracleMajor(rs
						.getString("majorid"));

				oracleTeacherLimit.setEduType(oracleEduType);
				oracleTeacherLimit.setGrade(oracleGrade);
				oracleTeacherLimit.setMajor(oracleMajor);
				
				list.add(oracleTeacherLimit);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getNoneGradeEduMajorsForTeacherNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLNONEGRADEEDUMAJORSFORTEACHER
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getGradeEduMajorsForTeacher(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		//private String SQLGRADEEDUMAJORSFORTEACHER = "select teacherid,gradeid,gradename,edutypeid,edutypename,majorid,majorname  from (select egi.id   as gradeid,egi.name as gradename,eet.id   as edutypeid,eet.name as edutypename,emi.id   as majorid,emi.name as majorname,eti.id as teacherid  from entity_grade_info  egi,entity_edu_type eet,entity_major_info emi,entity_graduate_picilimit egp,entity_graduate_pici egp2,entity_teacher_info  eti where egp2.id = egp.pici_id  and egp2.status = '1' and  eti.type ='1' and egp.grade_id = egi.id  and egp.edutype_id =eet.id  and egp.major_id =  emi.id           and eti.id || egi.id || eet.id || emi.id not in(select teacher_id || grade_id || edutype_id || major_id from entity_graduate_teacherlimit))";
		String sql = SQLGRADEEDUMAJORSFORTEACHER
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				OracleTeacherLimit oracleTeacherLimit = new OracleTeacherLimit();
				OracleTeacher oracleTeacher = new OracleTeacher(rs.getString("teacherid"));
				oracleTeacherLimit.setTeacher(oracleTeacher);
				
				OracleGrade oracleGrade = new OracleGrade(rs
						.getString("gradeid"));
				OracleEduType oracleEduType = new OracleEduType(rs
						.getString("edutypeid"));
				OracleMajor oracleMajor = new OracleMajor(rs
						.getString("majorid"));

				oracleTeacherLimit.setId(rs.getString("id"));
				oracleTeacherLimit.setEduType(oracleEduType);
				oracleTeacherLimit.setGrade(oracleGrade);
				oracleTeacherLimit.setMajor(oracleMajor);
				
				list.add(oracleTeacherLimit);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getGradeEduMajorsForTeacherNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLGRADEEDUMAJORSFORTEACHER
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getSelectStudentAtCurrentPici(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		//private String SQLGRADEEDUMAJORSFORTEACHER = "select teacherid,gradeid,gradename,edutypeid,edutypename,majorid,majorname  from (select egi.id   as gradeid,egi.name as gradename,eet.id   as edutypeid,eet.name as edutypename,emi.id   as majorid,emi.name as majorname,eti.id as teacherid  from entity_grade_info  egi,entity_edu_type eet,entity_major_info emi,entity_graduate_picilimit egp,entity_graduate_pici egp2,entity_teacher_info  eti where egp2.id = egp.pici_id  and egp2.status = '1' and  eti.type ='1' and egp.grade_id = egi.id  and egp.edutype_id =eet.id  and egp.major_id =  emi.id           and eti.id || egi.id || eet.id || emi.id not in(select teacher_id || grade_id || edutype_id || major_id from entity_graduate_teacherlimit))";
		String sql = SQLSELECTSTUDENT
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				SiteTeacherLimit siteTeacherLimit= new  OracleSiteTeacherLimit();
				Student oracleStudent = new OracleStudent(rs.getString("studentid"));
				SiteTeacher oracleTeacher = new OracleSiteTeacher(rs.getString("siteteacherid"));
				
				siteTeacherLimit.setStudent(oracleStudent);
				siteTeacherLimit.setSiteTeacher(oracleTeacher);
				siteTeacherLimit.setId(rs.getString("id"));
				list.add(siteTeacherLimit);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getSelectStudentAtCurrentPiciNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLSELECTSTUDENT
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getSelectGraduateStudentAtCurrentPici(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		//private String SQLGRADEEDUMAJORSFORTEACHER = "select teacherid,gradeid,gradename,edutypeid,edutypename,majorid,majorname  from (select egi.id   as gradeid,egi.name as gradename,eet.id   as edutypeid,eet.name as edutypename,emi.id   as majorid,emi.name as majorname,eti.id as teacherid  from entity_grade_info  egi,entity_edu_type eet,entity_major_info emi,entity_graduate_picilimit egp,entity_graduate_pici egp2,entity_teacher_info  eti where egp2.id = egp.pici_id  and egp2.status = '1' and  eti.type ='1' and egp.grade_id = egi.id  and egp.edutype_id =eet.id  and egp.major_id =  emi.id           and eti.id || egi.id || eet.id || emi.id not in(select teacher_id || grade_id || edutype_id || major_id from entity_graduate_teacherlimit))";
		String sql = SQLSELECTSTUDENT
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
		
		String tempId =""; 
		
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
				
				String id = rs.getString("id");
				if(!tempId.equals(id)){//Ƚٽid.,list,,ülist,ȥظֵ.
					tempId =id;
					
					SiteTeacherLimit siteTeacherLimit= new  OracleSiteTeacherLimit();
					Student oracleStudent = new OracleStudent(rs.getString("studentid"));
					SiteTeacher oracleTeacher = new OracleSiteTeacher(rs.getString("siteteacherid"));
					
					
					siteTeacherLimit.setStudent(oracleStudent);
					siteTeacherLimit.setSiteTeacher(oracleTeacher);
					siteTeacherLimit.setId(rs.getString("id"));
					list.add(siteTeacherLimit);
				}else{
					continue;
				}
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getSelectGraduateStudentAtCurrentPiciNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLSELECTSTUDENT
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}

	public List getNotSelectStudentAtCurrentPici(Page page, List searchProperty, List orderProperty) {
		dbpool db = new dbpool();
		//private String SQLNONESELECTSTUDENT ="select studentid,siteteacherid,gradeid,edutypeid,majorid from (select esi.id as studentid,esi.grade_id as gradeid,esi.edutype_id as edutypeid,esi.major_id as majorid, esis.id as siteteacherid from entity_student_info esi, entity_siteteacher_info esis where esi.grade_id || esi.edutype_id || esi.major_id in (select egp1.grade_id || egp1.edutype_id || egp1.major_id from entity_graduate_pici egp, entity_graduate_picilimit egp1 where egp.id = egp1.pici_id  and egp.status = '1')  and esis.type ='1' and esis.id || esi.id not in   (select egs.siteteacher_id || egs.student_id from entity_graduate_siteteachlimit egs))";
		String sql = SQLNONESELECTSTUDENT
				+ Conditions.convertToCondition(searchProperty, orderProperty);
		MyResultSet rs = null;
		ArrayList list = null;
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
				SiteTeacherLimit siteTeacherLimit= new  OracleSiteTeacherLimit();
				Student oracleStudent = new OracleStudent(rs.getString("studentid"));
				SiteTeacher oracleTeacher = new OracleSiteTeacher(rs.getString("siteteacherid"));
				
				siteTeacherLimit.setStudent(oracleStudent);
				siteTeacherLimit.setSiteTeacher(oracleTeacher);
				list.add(siteTeacherLimit);
			}
		} catch (Exception e) {
			
		} finally {
			db.close(rs);
			db = null;
		}
		return list;
	}

	public int getNotSelectStudentAtCurrentPiciNum(List searchProperty) {
		dbpool db = new dbpool();
		String sql = "";
		sql = SQLNONESELECTSTUDENT
				+ Conditions.convertToCondition(searchProperty, null);
		int i = db.countselect(sql);
		return i;
	}
	
	public int isGraduate(String studentId) {
		dbpool db = new dbpool();
		String sql = "select * from entity_student_info esi where esi.grade_id||esi.major_id||esi.edutype_id in (select egp1.grade_id||egp1.major_id||egp1.edutype_id from entity_graduate_picilimit egp1,entity_graduate_pici egp where egp1.pici_id = egp.id and egp.status ='1') and esi.id ='"+studentId+"'";
		int i = db.countselect(sql);
		return i;
	}
	
	public List getSubjectSearchStatusChangedStudent(String[] ids) {
		String sql = SQLSUBJECTSEARCHSTATUSCHANGEDSTUDENT + " and (";
		List studentList = new ArrayList();
		for(int i = 0 ; i < ids.length ; i++) {
			String id = ids[i];
			if(id != null) {
				sql += " id='" + id + "' or";
			}
		}
		sql = sql.substring(0,sql.length()-2);
		sql += ")";
		if(sql.length() < SQLSUBJECTSEARCHSTATUSCHANGEDSTUDENT.length() + 6)
			return studentList;
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		if(rs!=null) {
			try {
				while(rs.next()) {
					studentList.add(rs.getString("id"));	
				}
			}
			catch(Exception e) {
				
			
			}	
			finally {
				db.close(rs);
				db = null;
			}
		}
		return studentList;
	}

	public List getRegBeginCourseStatusChangedStudent(String[] ids) {
		String sql = SQLREGBEGINCOURSESTATUSCHANGEDSTUDENT + " and (";
		List studentList = new ArrayList();
		for(int i = 0 ; i < ids.length ; i++) {
			String id = ids[i];
			if(id != null) {
				sql += " id='" + id + "' or";
			}
		}
		sql = sql.substring(0,sql.length()-2);
		sql += ")";
		if(sql.length() < SQLREGBEGINCOURSESTATUSCHANGEDSTUDENT.length() + 6)
			return studentList;
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		if(rs!=null) {
			try {
				while(rs.next()) {
					studentList.add(rs.getString("id"));	
				}
			}
			catch(Exception e) {
				
			
			}	
			finally {
				db.close(rs);
				db = null;
			}
		}
		return studentList;
	}

	public List getMetaphaseCheckStatusChangedStudent(String[] ids) {
		String sql = SQLMETAPHASECHECKSTATUSCHANGEDSTUDENT + " and (";
		List studentList = new ArrayList();
		for(int i = 0 ; i < ids.length ; i++) {
			String id = ids[i];
			if(id != null) {
				sql += " id='" + id + "' or";
			}
		}
		sql = sql.substring(0,sql.length()-2);
		sql += ")";
		if(sql.length() < SQLMETAPHASECHECKSTATUSCHANGEDSTUDENT.length() + 6)
			return studentList;
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		if(rs!=null) {
			try {
				while(rs.next()) {
					studentList.add(rs.getString("id"));	
				}
			}
			catch(Exception e) {
				
			
			}	
			finally {
				db.close(rs);
				db = null;
			}
		}
		return studentList;
	}

	public List getRejoinRequesStatusChangedStudent(String[] ids) {
		String sql = SQLREJOINREQUESSTATUSCHANGEDSTUDENT + " and (";
		List studentList = new ArrayList();
		for(int i = 0 ; i < ids.length ; i++) {
			String id = ids[i];
			if(id != null) {
				sql += " id='" + id + "' or";
			}
		}
		sql = sql.substring(0,sql.length()-2);
		sql += ")";
		if(sql.length() < SQLREJOINREQUESSTATUSCHANGEDSTUDENT.length() + 6)
			return studentList;
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		if(rs!=null) {
			try {
				while(rs.next()) {
					studentList.add(rs.getString("id"));	
				}
			}
			catch(Exception e) {
				
			
			}	
			finally {
				db.close(rs);
				db = null;
			}
		}
		return studentList;
	}

	public List getDiscourseStatusChangedStudent(String[] ids) {
		String sql = SQLDISCOURSESTATUSCHANGEDSTUDENT + " and (";
		List studentList = new ArrayList();
		for(int i = 0 ; i < ids.length ; i++) {
			String id = ids[i];
			if(id != null) {
				sql += " id='" + id + "' or";
			}
		}
		sql = sql.substring(0,sql.length()-2);
		sql += ")";
		if(sql.length() < SQLDISCOURSESTATUSCHANGEDSTUDENT.length() + 6)
			return studentList;
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		if(rs!=null) {
			try {
				while(rs.next()) {
					studentList.add(rs.getString("id"));	
				}
			}
			catch(Exception e) {
				
			
			}	
			finally {
				db.close(rs);
				db = null;
			}
		}
		return studentList;
	}

	public List getStudentMajors(String teacherId) {
		List majors = new ArrayList();
		String sql = SQLSTUDENTMAJORS + " where teacher_id ='" + teacherId + "'";
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		if(rs!=null) {
			try {
				while(rs.next()) {
					String major_id = rs.getString("major_id");
					Major major = new OracleMajor(major_id);
					majors.add(major);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				
			}
			finally {
				db.close(rs);
				db = null;
			}
		}
		return majors;
	}

	public List getDiscourseScoreSubmitStatusChangedStudent(String[] ids) {
		String sql = SQLDISCOURSESCORESTATUSCHANGEDSTUDENT + " and (";
		List studentList = new ArrayList();
		for(int i = 0 ; i < ids.length ; i++) {
			String id = ids[i];
			if(id != null) {
				sql += " id='" + id + "' or";
			}
		}
		sql = sql.substring(0,sql.length()-2);
		sql += ")";
		if(sql.length() < SQLDISCOURSESCORESTATUSCHANGEDSTUDENT.length() + 6)
			return studentList;
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		if(rs!=null) {
			try {
				while(rs.next()) {
					studentList.add(rs.getString("id"));	
				}
			}
			catch(Exception e) {
				
			
			}	
			finally {
				db.close(rs);
				db = null;
			}
		}
		return studentList;
	}

	public List getExerciseScoreSubmitStatusChangedStudent(String[] ids) {
		String sql = SQLEXERCISESCORESTATUSCHANGEDSTUDENT + " and (";
		List studentList = new ArrayList();
		for(int i = 0 ; i < ids.length ; i++) {
			String id = ids[i];
			if(id != null) {
				sql += " id='" + id + "' or";
			}
		}
		sql = sql.substring(0,sql.length()-2);
		sql += ")";
		if(sql.length() < SQLEXERCISESCORESTATUSCHANGEDSTUDENT.length() + 6)
			return studentList;
		dbpool db = new dbpool();
		MyResultSet rs = db.executeQuery(sql);
		if(rs!=null) {
			try {
				while(rs.next()) {
					studentList.add(rs.getString("id"));	
				}
			}
			catch(Exception e) {
				
			
			}	
			finally {
				db.close(rs);
				db = null;
			}
		}
		return studentList;
	}

	public int getStudentFreeApplyNum(List searchproperty, List orderproperty) {
		String sql = SQLSTUDENTAPPLY + Conditions.convertToCondition(searchproperty,orderproperty);
		dbpool db = new dbpool ();
		int count = db.countselect(sql);
		db = null;
		return count;
	}

	//޸sql䣬typeͣ
	public List getStudentFreeApply(Page page, List searchproperty, List orderproperty) {
		String sql = SQLSTUDENTAPPLY + Conditions.convertToCondition(searchproperty,orderproperty);
		dbpool db = new dbpool ();
		MyResultSet rs = null;
		List applyList = new ArrayList();
		String tempFreeId="" ; //ȥظ;
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		try {
			while(rs != null && rs.next()) {
				// free_id, student_id, production_name, link, freestatus,teacher_note, apply_time, grade_id, site_id, edutype_id, major_id,   student_name, reg_no as reg_no, course_id, course_name, semester_id,  semester_name, pici_id 
				String id = rs.getString("free_id");
				String studentId = rs.getString("student_id");
				String productionName = rs.getString("production_name");
				String link = rs.getString("link");
				String status = rs.getString("freestatus");
				String teacherNote = rs.getString("teacher_note");
				String applyTime = rs.getString("apply_time");
				String applyType = rs.getString("free_type");//
				String score = rs.getString("free_score");//ӳɼ
				
				if(tempFreeId.equals(id)){
					continue;
				}else{
					tempFreeId = id;
				}
				FreeApply apply = new OracleFreeApply();
				apply.setId(id);
				apply.setStudentId(studentId);
				Student student = new OracleStudent(apply.getStudentId());
				apply.setStudent(student);
				apply.setLink(link);
				apply.setStatus(status);
				apply.setProductionName(productionName);
				apply.setApplyTime(applyTime);
				apply.setTeacher_note(teacherNote);
				apply.setType(applyType);
				apply.setScore_status(score);
				applyList.add(apply);
			}
		} catch (SQLException e) {
			
		}
		finally {
			db.close(rs);
			db = null;
		}
		return applyList;
	}
	
	//--------------------------------ҵҵ liwx 2008-05-28----------------
	
	/**
	 * ñҵҵ
	 */
	public List getStudentFreeApplyExec(Page page, List searchproperty, List orderproperty) {
		String sql = SQLSTUDENTAPPLY_EXEC + Conditions.convertToCondition(searchproperty,orderproperty);
		dbpool db = new dbpool ();
		MyResultSet rs = null;
		List applyList = new ArrayList();
		String tempFreeId="" ; //ȥظ;
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		try {
			while(rs != null && rs.next()) {
				// free_id, student_id, production_name, link, freestatus,teacher_note, apply_time, grade_id, site_id, edutype_id, major_id,   student_name, reg_no as reg_no, course_id, course_name, semester_id,  semester_name, pici_id 
				String id = rs.getString("free_id");
				String studentId = rs.getString("student_id");
				String productionName = rs.getString("production_name");
				String link = rs.getString("link");
				String status = rs.getString("freestatus");
				String teacherNote = rs.getString("teacher_note");
				String applyTime = rs.getString("apply_time");
				String applyType = rs.getString("free_type");//
				String score = rs.getString("free_score");//ӳɼ
				
				if(tempFreeId.equals(id)){
					continue;
				}else{
					tempFreeId = id;
				}
				FreeApply apply = new OracleFreeApply();
				apply.setId(id);
				apply.setStudentId(studentId);
				Student student = new OracleStudent(apply.getStudentId());
				apply.setStudent(student);
				apply.setLink(link);
				apply.setStatus(status);
				apply.setProductionName(productionName);
				apply.setApplyTime(applyTime);
				apply.setTeacher_note(teacherNote);
				apply.setType(applyType);
				apply.setScore_status(score);
				applyList.add(apply);
			}
		} catch (SQLException e) {
			
		}
		finally {
			db.close(rs);
			db = null;
		}
		return applyList;
	}
	
	public int getStudentFreeApplyExecNum(List searchproperty, List orderproperty) {
		String sql = SQLSTUDENTAPPLY_EXEC + Conditions.convertToCondition(searchproperty,orderproperty);
		dbpool db = new dbpool ();
		int count = db.countselect(sql);
		db = null;
		return count;
	}
	//-----------------------------------end----------------------------------
	 
	
	//----վʦ--
	public int getStudentFreeApplyTutorNum(List searchproperty, List orderproperty) {
		String sql = SQLSTUDENTAPPLY_TUTOR + Conditions.convertToCondition(searchproperty,orderproperty);
		dbpool db = new dbpool ();
		int count = db.countselect(sql);
		db = null;
		return count;
	}

	//޸sql䣬typeͣ
	public List getStudentFreeApplyTutor(Page page, List searchproperty, List orderproperty) {
		String sql = SQLSTUDENTAPPLY_TUTOR + Conditions.convertToCondition(searchproperty,orderproperty);
		dbpool db = new dbpool ();
		MyResultSet rs = null;
		List applyList = new ArrayList();
		String tempFreeId="" ; //ȥظ;
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		try {
			while(rs != null && rs.next()) {
				// free_id, student_id, production_name, link, freestatus,teacher_note, apply_time, grade_id, site_id, edutype_id, major_id,   student_name, reg_no as reg_no, course_id, course_name, semester_id,  semester_name, pici_id 
				String id = rs.getString("free_id");
				String studentId = rs.getString("student_id");
				String productionName = rs.getString("production_name");
				String link = rs.getString("link");
				String status = rs.getString("freestatus");
				String teacherNote = rs.getString("teacher_note");
				String applyTime = rs.getString("apply_time");
				String applyType = rs.getString("free_type");//
				String score = rs.getString("free_score");//ӳɼ
				
				if(tempFreeId.equals(id)){
					continue;
				}else{
					tempFreeId = id;
				}
				FreeApply apply = new OracleFreeApply();
				apply.setId(id);
				apply.setStudentId(studentId);
				Student student = new OracleStudent(apply.getStudentId());
				apply.setStudent(student);
				apply.setLink(link);
				apply.setStatus(status);
				apply.setProductionName(productionName);
				apply.setApplyTime(applyTime);
				apply.setTeacher_note(teacherNote);
				apply.setType(applyType);
				apply.setScore_status(score);
				applyList.add(apply);
			}
		} catch (SQLException e) {
			
		}
		finally {
			db.close(rs);
			db = null;
		}
		return applyList;
	}
	
	//--------------------------------ҵҵ liwx 2008-05-28----------------
	
	/**
	 * ñҵҵ
	 */
	public List getStudentFreeApplyExecTutor(Page page, List searchproperty, List orderproperty) {
		String sql = SQLSTUDENTAPPLY_EXEC_TUTOR + Conditions.convertToCondition(searchproperty,orderproperty);
		dbpool db = new dbpool ();
		MyResultSet rs = null;
		List applyList = new ArrayList();
		String tempFreeId="" ; //ȥظ;
		if (page != null) {
			int pageInt = page.getPageInt();
			int pageSize = page.getPageSize();
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
		} else {
			rs = db.executeQuery(sql);
		}
		try {
			while(rs != null && rs.next()) {
				// free_id, student_id, production_name, link, freestatus,teacher_note, apply_time, grade_id, site_id, edutype_id, major_id,   student_name, reg_no as reg_no, course_id, course_name, semester_id,  semester_name, pici_id 
				String id = rs.getString("free_id");
				String studentId = rs.getString("student_id");
				String productionName = rs.getString("production_name");
				String link = rs.getString("link");
				String status = rs.getString("freestatus");
				String teacherNote = rs.getString("teacher_note");
				String applyTime = rs.getString("apply_time");
				String applyType = rs.getString("free_type");//
				String score = rs.getString("free_score");//ӳɼ
				
				if(tempFreeId.equals(id)){
					continue;
				}else{
					tempFreeId = id;
				}
				FreeApply apply = new OracleFreeApply();
				apply.setId(id);
				apply.setStudentId(studentId);
				Student student = new OracleStudent(apply.getStudentId());
				apply.setStudent(student);
				apply.setLink(link);
				apply.setStatus(status);
				apply.setProductionName(productionName);
				apply.setApplyTime(applyTime);
				apply.setTeacher_note(teacherNote);
				apply.setType(applyType);
				apply.setScore_status(score);
				applyList.add(apply);
			}
		} catch (SQLException e) {
			
		}
		finally {
			db.close(rs);
			db = null;
		}
		return applyList;
	}
	
	public int getStudentFreeApplyExecTutorNum(List searchproperty, List orderproperty) {
		String sql = SQLSTUDENTAPPLY_EXEC_TUTOR + Conditions.convertToCondition(searchproperty,orderproperty);
		dbpool db = new dbpool ();
		int count = db.countselect(sql);
		db = null;
		return count;
	}

}

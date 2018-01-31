package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleBasicScoreList;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleElective;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleElectiveScore;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleTeachClass;
import com.whaty.platform.database.oracle.standard.entity.activity.score.OracleScoreCalculateType;
import com.whaty.platform.database.oracle.standard.entity.activity.score.OracleScoreModify;
import com.whaty.platform.database.oracle.standard.entity.activity.score.OracleScoreSetTime;
import com.whaty.platform.database.oracle.standard.entity.user.OracleManagerPriv;
import com.whaty.platform.entity.BasicScoreManage;
import com.whaty.platform.entity.activity.Elective;
import com.whaty.platform.entity.activity.score.ElectiveScoreType;
import com.whaty.platform.entity.activity.score.ScoreDef;
import com.whaty.platform.entity.activity.score.ScoreModify;
import com.whaty.platform.entity.activity.score.ScoreSetTime;
import com.whaty.platform.entity.user.ManagerPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;

public class OracleBasicScoreManage extends BasicScoreManage {
	private ManagerPriv basicManagePriv;

	public OracleBasicScoreManage() {
		this.basicManagePriv = new OracleManagerPriv();
	}

	public OracleBasicScoreManage(ManagerPriv managerPriv) {
		this.basicManagePriv = managerPriv;
	}

	public List getElectiveScores(Page pageover, String open_course_id,
			String major_id, String site_id, String edu_type_id, String grade_id)
			throws PlatformException {
		// if (basicManagePriv.getElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}

		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScores(pageover, searchProperties,
				null);
		// } else {
		// throw new PlatformException("��û�в鿴ѡ�γɼ���Ȩ��");
		// }
	}

	public List getElectiveScores(Page pageover, String open_course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id) throws PlatformException {
		// /if (basicManagePriv.getElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScores(pageover, searchProperties,
				null);
		// } else {
		// throw new PlatformException("��û�в鿴ѡ�γɼ���Ȩ��");
		// }
	}

	public List getElectiveScores(Page pageover, String open_course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String cardNo, String course_id)
			throws PlatformException {
		// if (basicManagePriv.getElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (cardNo != null && !cardNo.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", cardNo.trim(),
					">="));
		}
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("reg_no"));
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv .getMajorList(), "in"));
		// searchProperties.add(new SearchProperty("edutype_id",
		// basicManagePriv.getEduTypeList(), "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScores(pageover, searchProperties,
				orderProperty);
		// } else {
		// throw new PlatformException("��û�в鿴ѡ�γɼ���Ȩ��");
		// }
	}

	public List getElectiveScores(Page pageover, String open_course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String cardNo,
			String course_id, String free_status) throws PlatformException {
		// if (basicManagePriv.getElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (cardNo != null && !cardNo.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", cardNo.trim(),
					">="));
		}
		if (free_status != null && !free_status.equals("")) {
			searchProperties.add(new SearchProperty("free_total_score_status",
					free_status, "="));
		}
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("reg_no"));
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv .getMajorList(), "in"));
		// searchProperties.add(new SearchProperty("edutype_id",
		// basicManagePriv.getEduTypeList(), "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScores(pageover, searchProperties,
				orderProperty);
		// } else {
		// throw new PlatformException("��û�в鿴ѡ�γɼ���Ȩ��");
		// }
	}

	public List getElectiveScores(Page pageover, String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, int num, String order)
			throws PlatformException {
		// if (basicManagePriv.getElectiveScores == 1) {
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (num != 0) {
			searchProperties.add(new SearchProperty("rownum", Integer
					.toString(num), "<="));
		}
		if (order != null && !order.equals("")) {
			orderProperties.add(new OrderProperty("total_score", order));
		}
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScores(pageover, searchProperties,
				orderProperties);
		// } else {
		// throw new PlatformException("��û�в鿴ѡ�γɼ���Ȩ��");
		// }
	}

	public List getElectiveScores(String reg_no) throws PlatformException {
		List searchProperties = new ArrayList();
		if (reg_no != null && !reg_no.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", reg_no, "="));
		}
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList
				.searchElectiveScores(null, searchProperties, null);
	}

	public String getTestScore(String studentId, String teachClassId)
			throws PlatformException {
		OracleElectiveScore electiveScore = new OracleElectiveScore();
		return electiveScore.getTestScore(studentId, teachClassId);
	}

	public HashMap getScoreStatus(String open_course_id)
			throws PlatformException {
		OracleElectiveScore scorestatus = new OracleElectiveScore();
		return scorestatus.getScoreStatus(open_course_id);
	}

	public List getTotalElectiveScores(Page pageover, String open_course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line)
			throws PlatformException {
		// if (basicManagePriv.getTotalElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (total_line != null && !total_line.equals("")) {
			searchProperties.add(new SearchProperty("total_score", total_line,
					"<"));
		}
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(), "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));
		return basicScoreList.searchElectiveScores(pageover, searchProperties,
				null);
		// } else {
		// throw new PlatformException("��û�в鿴��ѡ�γɼ���Ȩ��");
		// }
	}

	public List getTotalElectiveScores(Page pageover, String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line,
			String reg_no, String name) throws PlatformException {
		// if (basicManagePriv.getTotalElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (total_line != null && !total_line.equals("")) {
			searchProperties.add(new SearchProperty("total_score", total_line,
					"<"));
		}
		if (reg_no != null && !reg_no.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", reg_no, "like"));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("student_name", name,
					"like"));
		}
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("semester_id", OrderProperty.DESC));
		orderProperty.add(new OrderProperty("grade_id"));
		orderProperty.add(new OrderProperty("edutype_id"));
		orderProperty.add(new OrderProperty("major_id"));
		orderProperty.add(new OrderProperty("reg_no"));
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScores(pageover, searchProperties,
				orderProperty);
		// } else {
		// throw new PlatformException("��û�в鿴��ѡ�γɼ���Ȩ��");
		// }
	}
	
	public List getPassTotalElectiveScores(String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line,
			String reg_no, String name) throws PlatformException {
		// if (basicManagePriv.getTotalElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (total_line != null && !total_line.equals("")) {
			searchProperties.add(new SearchProperty("total_score", total_line,
					">="));
		}
		if (reg_no != null && !reg_no.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", reg_no, "like"));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("student_name", name,
					"like"));
		}
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("grade_id"));
		orderProperty.add(new OrderProperty("edutype_id"));
		orderProperty.add(new OrderProperty("major_id"));
		orderProperty.add(new OrderProperty("reg_no"));
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScores(null, searchProperties,
				orderProperty);
		// } else {
		// throw new PlatformException("��û�в鿴��ѡ�γɼ���Ȩ��");
		// }
	}
	
	public List getTotalElectiveScores(Page pageover, String course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line,
			String reg_no, String name, String student_expend_score_status)
			throws PlatformException {
		// if (basicManagePriv.getTotalElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (total_line != null && !total_line.equals("")) {
			searchProperties.add(new SearchProperty("total_score", total_line,
					"<"));
		}
		if (reg_no != null && !reg_no.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", reg_no, "like"));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("student_name", name,
					"like"));
		}

		if (student_expend_score_status != null
				&& !student_expend_score_status.equals("")) {
			searchProperties.add(new SearchProperty(
					"expend_score_student_status", student_expend_score_status,
					"="));
		}

		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("reg_no"));
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScores(pageover, searchProperties,
				orderProperty);
		// } else {
		// throw new PlatformException("��û�в鿴��ѡ�γɼ���Ȩ��");
		// }
	}

	public int getElectiveScoresNum(String open_course_id, String major_id,
			String site_id, String edu_type_id, String grade_id)
			throws PlatformException {
		// if (basicManagePriv.getElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresNum(searchProperties, null);
		// } else {
		// throw new PlatformException("��û�в鿴ѡ�γɼ���Ȩ��");
		// }
	}

	public int getElectiveScoresNum(String open_course_id, String major_id,
			String site_id, String edu_type_id, String grade_id,
			String semester_id) throws PlatformException {
		// if (basicManagePriv.getElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresNum(searchProperties, null);
		// } else {
		// throw new PlatformException("��û�в鿴ѡ�γɼ���Ȩ��");
		// }
	}

	public int getElectiveScoresNum(String course_id, String major_id,
			String site_id, String edu_type_id, String grade_id,
			String semester_id, int num) throws PlatformException {
		// if (basicManagePriv.getElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (num != 0) {
			searchProperties.add(new SearchProperty("rownum", Integer
					.toString(num), "<="));
		}
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(), "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresNum(searchProperties, null);
		// } else {
		// throw new PlatformException("��û�в鿴ѡ�γɼ���Ȩ��");
		// }
	}

	public int getElectiveScoresNum(String open_course_id, String major_id,
			String site_id, String edu_type_id, String grade_id,
			String semester_id, String cardNo, String course_id)
			throws PlatformException {
		// System.out.println("basicManagePriv.getElectiveScores:
		// "+basicManagePriv.getElectiveScores);
		// if (basicManagePriv.getElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (cardNo != null && !cardNo.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", cardNo.trim(),
					">="));
		}
		// System.out.println(searchProperties.size());
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresNum(searchProperties, null);
		// } else {
		// throw new PlatformException("��û�в鿴ѡ�γɼ���Ȩ��");
		// }
	}

	public int getElectiveScoresNum(String open_course_id, String major_id,
			String site_id, String edu_type_id, String grade_id,
			String semester_id, String cardNo, String course_id,
			String free_status) throws PlatformException {
		// System.out.println("basicManagePriv.getElectiveScores:
		// "+basicManagePriv.getElectiveScores);
		// if (basicManagePriv.getElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (cardNo != null && !cardNo.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", cardNo.trim(),
					">="));
		}
		if (free_status != null && !free_status.equals("")) {
			searchProperties.add(new SearchProperty("free_total_score_status",
					free_status, "="));
		}
		// System.out.println(searchProperties.size());
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresNum(searchProperties, null);
		// } else {
		// throw new PlatformException("��û�в鿴ѡ�γɼ���Ȩ��");
		// }
	}

	public int getElectiveScoresNum(String reg_no) throws PlatformException {
		List searchProperties = new ArrayList();
		if (reg_no != null && !reg_no.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", reg_no.trim(),
					"="));
		}
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresNum(searchProperties, null);
	}

	public int getTotalElectiveScoresNum(String open_course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String total_line)
			throws PlatformException {
		// if (basicManagePriv.getTotalElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (total_line != null && !total_line.equals("")) {
			searchProperties.add(new SearchProperty("total_score", total_line,
					"<"));
		}
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresNum(searchProperties, null);
		// } else {
		// throw new PlatformException("��û�в鿴��ѡ�γɼ���Ȩ��");
		// }
	}

	public int getTotalElectiveScoresNum(String course_id, String major_id,
			String site_id, String edu_type_id, String grade_id,
			String semester_id, String total_line, String reg_no, String name)
			throws PlatformException {
		// if (basicManagePriv.getTotalElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (total_line != null && !total_line.equals("")) {
			searchProperties.add(new SearchProperty("total_score", total_line,
					"<"));
		}
		if (reg_no != null && !reg_no.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", reg_no, "like"));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("student_name", name,
					"like"));
		}
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresNum(searchProperties, null);
		// } else {
		// throw new PlatformException("��û�в鿴��ѡ�γɼ���Ȩ��");
		// }
	}

	public int getTotalElectiveScoresNum(String course_id, String major_id,
			String site_id, String edu_type_id, String grade_id,
			String semester_id, String total_line, String reg_no, String name,
			String expend_score_student_status) throws PlatformException {
		// if (basicManagePriv.getTotalElectiveScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (total_line != null && !total_line.equals("")) {
			searchProperties.add(new SearchProperty("total_score", total_line,
					"<"));
		}
		if (reg_no != null && !reg_no.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", reg_no, "like"));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("student_name", name,
					"like"));
		}

		if (expend_score_student_status != null
				&& !expend_score_student_status.equals("")) {
			searchProperties.add(new SearchProperty(
					"expend_score_student_status", expend_score_student_status,
					"="));
		}
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresNum(searchProperties, null);
		// } else {
		// throw new PlatformException("��û�в鿴��ѡ�γɼ���Ȩ��");
		// }
	}

	public int updateScoreBatch(String[] total_score, String[] usual_score,
			String[] exam_score, String[] expend_score, String[] renew_score,
			String[] score_status, String[] id) throws PlatformException {
		List dataList = new ArrayList();
		List searchProperties = new ArrayList();
		for (int i = 0; i < id.length; i++) {
			HashMap data = new HashMap();
			List searchProperty = new ArrayList();
			data.put("tableName", "pr_tch_stu_elective");
			if (total_score != null)
				data.put("total_score", total_score[i]);
			if (usual_score != null)
				data.put("score_usual", usual_score[i]);
			if (exam_score != null)
				data.put("exam_score", exam_score[i]);
			if (expend_score != null)
				data.put("expend_score", expend_score[i]);
			if (renew_score != null)
				data.put("renew_score", renew_score[i]);
			if (score_status != null)
				data.put("score_status", score_status[i]);
			dataList.add(data);
			if (id[i] != null && !id[i].equals("")) {
				searchProperty.add(new SearchProperty("id", id[i], "="));
			}
			searchProperties.add(searchProperty);
		}
		OracleElectiveScore electivescore = new OracleElectiveScore();
		int suc = electivescore.updateScoreBatch(dataList, searchProperties);
		// UserLog.setInfo("<whaty>USERID$|$"+this.basicManagePriv.getSso_id()+"</whaty><whaty>BEHAVIOR$|$updateScoreBatch</whaty><whaty>STATUS$|$"+suc+"</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"+LogType.MANAGER+"</whaty><whaty>PRIORITY$|$"+LogPriority.INFO+"</whaty>",new
		// Date());
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScoreBatch</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int updateScore(String total_score, String usual_score,
			String exam_score, String expend_score, String renew_score,
			String elective_id) throws PlatformException {
		OracleElectiveScore electivescore = new OracleElectiveScore();
		OracleElective elective = new OracleElective();
		elective.setId(elective_id);
		electivescore.setElective(elective);
		List newScoreList = new ArrayList();
		if (total_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.TOTAL);
			score.setScore(Float.parseFloat(total_score));
			newScoreList.add(score);
		}
		if (usual_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.USUAL);
			score.setScore(Float.parseFloat(usual_score));
			newScoreList.add(score);
		}
		if (exam_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.EXAM);
			score.setScore(Float.parseFloat(exam_score));
			newScoreList.add(score);
		}
		if (expend_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.EXPEND);
			score.setScore(Float.parseFloat(expend_score));
			newScoreList.add(score);
		}
		if (renew_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.RENEW);
			score.setScore(Float.parseFloat(renew_score));
			newScoreList.add(score);
		}
		int count = 1;
		try {
			electivescore.updateCoreScoresById(newScoreList, elective_id);
		} catch (PlatformException e) {
			count = 0;
		}
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScore</whaty><whaty>STATUS$|$"
								+ count
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		// UserLog.setInfo("<whaty>USERID$|$"+this.basicManagePriv.getSso_id()+"</whaty><whaty>BEHAVIOR$|$updateScore</whaty><whaty>STATUS$|$"+count+"</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"+LogType.MANAGER+"</whaty><whaty>PRIORITY$|$"+LogPriority.INFO+"</whaty>",new
		// Date());
		return count;
	}

	public int updateScore(String total_score, String usual_score,
			String exam_score, String expend_score, String experiment_score,
			String renew_score, String elective_id) throws PlatformException {
		OracleElectiveScore electivescore = new OracleElectiveScore();
		OracleElective elective = new OracleElective();
		elective.setId(elective_id);
		electivescore.setElective(elective);
		List newScoreList = new ArrayList();
		if (total_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.TOTAL);
			score.setScore(Float.parseFloat(total_score));
			newScoreList.add(score);
		}
		if (usual_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.USUAL);
			score.setScore(Float.parseFloat(usual_score));
			newScoreList.add(score);
		}
		if (exam_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.EXAM);
			score.setScore(Float.parseFloat(exam_score));
			newScoreList.add(score);
		}
		if (expend_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.EXPEND);
			score.setScore(Float.parseFloat(expend_score));
			newScoreList.add(score);
		}
		if (experiment_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.EXPERIMENT);
			score.setScore(Float.parseFloat(experiment_score));
			newScoreList.add(score);
		}
		if (renew_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.RENEW);
			score.setScore(Float.parseFloat(renew_score));
			newScoreList.add(score);
		}
		int count = 1;
		try {
			electivescore.updateCoreScoresById(newScoreList, elective_id);
		} catch (PlatformException e) {
			count = 0;
		}
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScore</whaty><whaty>STATUS$|$"
								+ count
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		// UserLog.setInfo("<whaty>USERID$|$"+this.basicManagePriv.getSso_id()+"</whaty><whaty>BEHAVIOR$|$updateScore</whaty><whaty>STATUS$|$"+count+"</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"+LogType.MANAGER+"</whaty><whaty>PRIORITY$|$"+LogPriority.INFO+"</whaty>",new
		// Date());
		return count;
	}

	public int updateScore(String total_score, String usual_score,
			String exam_score, String expend_score, String experiment_score,
			String test_score, String total_expend_score, String renew_score,
			String elective_id) throws PlatformException {
		OracleElectiveScore electivescore = new OracleElectiveScore();
		OracleElective elective = new OracleElective();
		elective.setId(elective_id);
		electivescore.setElective(elective);
		List newScoreList = new ArrayList();
		if (total_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.TOTAL);
			score.setScore(Float.parseFloat(total_score));
			newScoreList.add(score);
		}
		if (usual_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.USUAL);
			score.setScore(Float.parseFloat(usual_score));
			newScoreList.add(score);
		}
		if (exam_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.EXAM);
			score.setScore(Float.parseFloat(exam_score));
			newScoreList.add(score);
		}
		if (expend_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.EXPEND);
			score.setScore(Float.parseFloat(expend_score));
			newScoreList.add(score);
		}
		if (experiment_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.EXPERIMENT);
			score.setScore(Float.parseFloat(experiment_score));
			newScoreList.add(score);
		}
		if (renew_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.RENEW);
			score.setScore(Float.parseFloat(renew_score));
			newScoreList.add(score);
		}
		if (test_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.SELFTEST);
			score.setScore(Float.parseFloat(test_score));
			newScoreList.add(score);
		}
		if (total_expend_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.TOTAL_EXPEND_SCORE);
			score.setScore(Float.parseFloat(total_expend_score));
			newScoreList.add(score);
		}
		int count = 1;
		try {
			electivescore.updateCoreScoresById(newScoreList, elective_id);
		} catch (PlatformException e) {
			count = 0;
		}
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScore</whaty><whaty>STATUS$|$"
								+ count
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		// UserLog.setInfo("<whaty>USERID$|$"+this.basicManagePriv.getSso_id()+"</whaty><whaty>BEHAVIOR$|$updateScore</whaty><whaty>STATUS$|$"+count+"</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"+LogType.MANAGER+"</whaty><whaty>PRIORITY$|$"+LogPriority.INFO+"</whaty>",new
		// Date());
		return count;
	}

	public void updateScoreBatch(List total_scoreList, List usual_scoreList,
			List exam_scoreList, List expend_scoreList, List experiment_score,
			List renew_scoreList, List idList, List reg_noList, List nameList,
			String open_course_id) throws PlatformException {
		List dataList = new ArrayList();
		for (int i = 0; i < reg_noList.size(); i++) {
			HashMap data = new HashMap();

			data.put("tableName", "entity_elective");
			if (total_scoreList != null)
				data.put("total_score", total_scoreList.get(i));
			if (usual_scoreList != null)
				data.put("usual_score", usual_scoreList.get(i));
			if (exam_scoreList != null)
				data.put("exam_score", exam_scoreList.get(i));
			if (expend_scoreList != null)
				data.put("expend_score", expend_scoreList.get(i));
			if (experiment_score != null)
				data.put("experiment_score", experiment_score.get(i));
			if (renew_scoreList != null)
				data.put("renew_score", renew_scoreList.get(i));
			dataList.add(data);
		}
		OracleElectiveScore electivescore = new OracleElectiveScore();
		electivescore.updateScoreBatch(dataList, idList, reg_noList, nameList,
				open_course_id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScoreBatch</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		// UserLog.setInfo("<whaty>USERID$|$"+this.basicManagePriv.getSso_id()+"</whaty><whaty>BEHAVIOR$|$updateScoreBatch</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"+LogType.MANAGER+"</whaty><whaty>PRIORITY$|$"+LogPriority.INFO+"</whaty>",new
		// Date());

	}

	public void updateScoreBatch(List total_scoreList, List usual_scoreList,
			List exam_scoreList, List expend_scoreList, List renew_scoreList,
			List idList, List reg_noList, List nameList, String open_course_id)
			throws PlatformException {
		List dataList = new ArrayList();
		List searchProperties = new ArrayList();
		boolean checkExpend = false;
		if (expend_scoreList != null)
			checkExpend = true;
		for (int i = 0; i < reg_noList.size(); i++) {
			HashMap data = new HashMap();
			List searchProperty = new ArrayList();
			data.put("tableName", "entity_elective");
			if (total_scoreList != null)
				data.put("total_score", total_scoreList.get(i));
			if (usual_scoreList != null)
				data.put("usual_score", usual_scoreList.get(i));
			if (exam_scoreList != null)
				data.put("exam_score", exam_scoreList.get(i));
			if (expend_scoreList != null)
				data.put("expend_score", expend_scoreList.get(i));
			if (renew_scoreList != null)
				data.put("renew_score", renew_scoreList.get(i));
			dataList.add(data);
		}
		OracleElectiveScore electivescore = new OracleElectiveScore();
		electivescore.updateScoreBatch(dataList, idList, reg_noList, nameList,
				open_course_id, checkExpend);

		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScoreBatch</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		// UserLog.setInfo("<whaty>USERID$|$"+this.basicManagePriv.getSso_id()+"</whaty><whaty>BEHAVIOR$|$updateScoreBatch</whaty><whaty>STATUS$|$</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"+LogType.MANAGER+"</whaty><whaty>PRIORITY$|$"+LogPriority.INFO+"</whaty>",new
		// Date());

	}

	public void updateScoreBatch(List total_scoreList, List usual_scoreList,
			List exam_scoreList, List expend_scoreList, List renew_scoreList,
			List idList, List reg_noList, List nameList, String open_course_id,
			String free_total_score_status) throws PlatformException {
		List dataList = new ArrayList();
		List searchProperties = new ArrayList();
		for (int i = 0; i < reg_noList.size(); i++) {
			HashMap data = new HashMap();
			List searchProperty = new ArrayList();
			data.put("tableName", "entity_elective");
			if (total_scoreList != null)
				data.put("total_score", total_scoreList.get(i));
			if (usual_scoreList != null)
				data.put("usual_score", usual_scoreList.get(i));
			if (exam_scoreList != null)
				data.put("exam_score", exam_scoreList.get(i));
			if (expend_scoreList != null)
				data.put("expend_score", expend_scoreList.get(i));
			if (renew_scoreList != null)
				data.put("renew_score", renew_scoreList.get(i));
			dataList.add(data);
		}
		OracleElectiveScore electivescore = new OracleElectiveScore();
		electivescore.updateScoreBatch(dataList, idList, reg_noList, nameList,
				open_course_id, free_total_score_status);

		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScoreBatch</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());

	}

	public List getElectiveMaxScores(String scoretype, List searchproperties,
			List orderproperties) throws PlatformException {
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchMaxElectiveScores(scoretype,
				searchproperties, orderproperties);
	}

	public List getElectiveMaxUsualScores(String semester_id)
			throws PlatformException {
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();

		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		orderProperties.add(new OrderProperty("course_id"));
		return this.getElectiveMaxScores(ElectiveScoreType.USUAL,
				searchProperties, orderProperties);
	}

	public int getElectiveMaxScoresNum(String scoretype, List searchproperties,
			List orderproperties) throws PlatformException {
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchMaxElectiveScoresNum(scoretype,
				searchproperties, orderproperties);
	}

	public int getElectiveMaxUsualScoresNum(String semester_id)
			throws PlatformException {
		List searchProperties = new ArrayList();
		List orderProperties = new ArrayList();

		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		orderProperties.add(new OrderProperty("course_id"));
		return this.getElectiveMaxScoresNum(ElectiveScoreType.USUAL,
				searchProperties, orderProperties);

	}

	public void updateMaxScore(String total_score, String usual_score,
			String exam_score, String expend_score, String renew_score,
			String open_course_id) throws PlatformException {
		OracleElectiveScore electivescore = new OracleElectiveScore();
		OracleElective elective = new OracleElective();
		OracleTeachClass teachclass = new OracleTeachClass();
		OracleOpenCourse opencourse = new OracleOpenCourse();
		opencourse.setId(open_course_id);
		teachclass.setOpenCourse(opencourse);
		elective.setTeachClass(teachclass);
		electivescore.setElective(elective);
		List newScoreList = new ArrayList();
		if (total_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.TOTAL);
			score.setScore(Float.parseFloat(total_score));
			newScoreList.add(score);
		}
		if (usual_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.USUAL);
			score.setScore(Float.parseFloat(usual_score));
			newScoreList.add(score);
		}
		if (exam_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.EXAM);
			score.setScore(Float.parseFloat(exam_score));
			newScoreList.add(score);
		}
		if (expend_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.EXPEND);
			score.setScore(Float.parseFloat(expend_score));
			newScoreList.add(score);
		}
		if (renew_score != null) {
			ScoreDef score = new ScoreDef();
			score.setType(ElectiveScoreType.RENEW);
			score.setScore(Float.parseFloat(renew_score));
			newScoreList.add(score);
		}
		electivescore.updateMaxScores(newScoreList);

		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateMaxScore</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public void makeTotalScore(String usual_score, String percent,
			String open_course_id) throws PlatformException {
		OracleElectiveScore electivescore = new OracleElectiveScore();
		OracleElective elective = new OracleElective();
		OracleTeachClass teachclass = new OracleTeachClass();
		OracleOpenCourse opencourse = new OracleOpenCourse();
		opencourse.setId(open_course_id);
		teachclass.setOpenCourse(opencourse);
		elective.setTeachClass(teachclass);
		electivescore.setElective(elective);
		electivescore.makeTotalScore(usual_score, percent);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$makeTotalScore</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public void makeExpendList(String open_course_id, String major_id,
			String site_id, String edu_type_id, String grade_id,
			String semester_id, String total_line) throws PlatformException {

		// if (basicManagePriv.makeExpendList == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleElectiveScore electivescore = new OracleElectiveScore();
		electivescore.makeExpendList(searchProperties, total_line);
		// } else {
		// throw new PlatformException("��û�л�ȡ�����ɼ�Ȩ��");
		// }
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$makeExpendList</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
	}

	public Elective getElective(String elective_id) throws PlatformException {
		OracleElectiveScore electivescore = new OracleElectiveScore();
		return electivescore.getCoreScores(elective_id);
	}

	public int generateTotalScore(String elective_id, String CalculateType,
			String percent, String percent1, String percent2, String percent3,
			String[] calculate) throws PlatformException {

		String total_score = "0";
		int conunt = 0;
		OracleScoreCalculateType generateTotalScore = new OracleScoreCalculateType();
		OracleElectiveScore generate = new OracleElectiveScore();
		total_score = generateTotalScore.getTotalScore(elective_id,
				CalculateType, percent, percent1, percent2, percent3, null);
		conunt = generate.genarateTotalScore(elective_id, total_score, percent,
				percent1, percent2, percent3);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$generateTotalScore</whaty><whaty>STATUS$|$"
								+ conunt
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return conunt;
	}

	public int generateTotalScore(String techclass_id, String percent,
			String percent1, String percent2, String percent3)
			throws PlatformException {

		int conunt = 0;
		OracleScoreCalculateType generateTotalScore = new OracleScoreCalculateType();
		OracleElectiveScore generate = new OracleElectiveScore();
		conunt = generate.genarateTotalScore(techclass_id, percent, percent1,
				percent2, percent3);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$generateTotalScore</whaty><whaty>STATUS$|$"
								+ conunt
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return conunt;
	}

	public int generateExpendScore(String techclass_id, String percent,
			String percent1, String percent2, String percent3)
			throws PlatformException {

		int conunt = 0;
		OracleScoreCalculateType generateTotalScore = new OracleScoreCalculateType();
		OracleElectiveScore generate = new OracleElectiveScore();
		conunt = generate.genarateExpendScore(techclass_id, percent, percent1,
				percent2, percent3);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$generateExpendScore</whaty><whaty>STATUS$|$"
								+ conunt
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return conunt;
	}

	public int generateModifyTotalScore(String elective_id,
			String CalculateType, String[] calculate) throws PlatformException {

		String total_score = "0.0";
		OracleScoreCalculateType generateTotalScore = new OracleScoreCalculateType();
		OracleElectiveScore generate = new OracleElectiveScore();
		total_score = generateTotalScore.getTotalScore(elective_id,
				CalculateType, null, null, null, null, calculate);
		int i = generate.genarateModifyTotalScore(elective_id, total_score,
				calculate);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$generateModifyTotalScore</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return i;
	}

	public int modifyTotalScore(String teachclass_id, String[] ctype,
			String[] calculate, String total_score, String low_score,
			String heigh_score) throws PlatformException {
		OracleElectiveScore generate = new OracleElectiveScore();
		int suc = generate.modifyTotalScore(teachclass_id, ctype, calculate,
				total_score, low_score, heigh_score);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$modifyTotalScore</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int modifyTotalExpendScore(String teachclass_id, String[] ctype,
			String[] calculate, String total_score, String low_score,
			String heigh_score) throws PlatformException {
		OracleElectiveScore generate = new OracleElectiveScore();
		int suc = generate.modifyTotalExpendScore(teachclass_id, ctype,
				calculate, total_score, low_score, heigh_score);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$modifyTotalExpendScore</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int modifyTotalExpendScore1(String teachclass_id, String[] ctype,
			String[] calculate, String total_score, String low_score,
			String heigh_score) throws PlatformException {
		OracleElectiveScore generate = new OracleElectiveScore();
		int suc = generate.modifyTotalExpendScore(teachclass_id, ctype,
				calculate, total_score, low_score, heigh_score);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$modifyTotalExpendScore1</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int updateScoreStatus(String elective_id, String usual_score_status,
			String exam_score_status, String total_score_status,
			String expend_score_status) throws PlatformException {
		OracleElectiveScore generate = new OracleElectiveScore();
		int i = 0;
		i = generate.updateScoreStatus(elective_id, usual_score_status,
				exam_score_status, total_score_status, expend_score_status);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScoreStatus</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return i;
	}

	public int updateScoreStatus(String elective_id, String strsql)
			throws PlatformException {
		OracleElectiveScore generate = new OracleElectiveScore();
		int i = 0;
		i = generate.updateScoreStatus(elective_id, strsql);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScoreStatus</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return i;
	}

	public int updateStudentExpendScoreStatus(String elective_id,
			String expend_score_status) throws PlatformException {
		OracleElectiveScore generate = new OracleElectiveScore();

		int i = 0;
		i = generate.updateStudentExpendScoreStatus(elective_id,
				expend_score_status);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateStudentExpendScoreStatus</whaty><whaty>STATUS$|$"
								+ i
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return i;
	}

	public List getElectiveExpendScores(Page pageover, String open_course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String cardNo,
			String course_id, String expend_score_student_status)
			throws PlatformException {
		// if (basicManagePriv.getElectiveExpendScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (cardNo != null && !cardNo.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", cardNo.trim(),
					">="));
		}
		if (expend_score_student_status != null
				&& !expend_score_student_status.equals("")) {
			searchProperties.add(new SearchProperty(
					"expend_score_student_status", expend_score_student_status,
					"="));
		}
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("reg_no"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();

		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		return basicScoreList.searchElectiveScores(pageover, searchProperties,
				orderProperty);
		// } else {
		// throw new PlatformException("��û�л�ȡ�����ɼ�Ȩ��");
		// }
	}

	public int getElectiveExpendScoresNum(String open_course_id,
			String major_id, String site_id, String edu_type_id,
			String grade_id, String semester_id, String cardNo,
			String course_id, String expend_score_student_status)
			throws PlatformException {
		// if (basicManagePriv.getElectiveExpendScores == 1) {
		List searchProperties = new ArrayList();
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (open_course_id != null && !open_course_id.equals("")) {
			searchProperties.add(new SearchProperty("open_course_id",
					open_course_id, "="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (cardNo != null && !cardNo.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", cardNo.trim(),
					">="));
		}
		if (expend_score_student_status != null
				&& !expend_score_student_status.equals("")) {
			searchProperties.add(new SearchProperty(
					"expend_score_student_status", expend_score_student_status,
					"="));
		}
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresNum(searchProperties, null);
		// } else {
		// throw new PlatformException("��û�л�ȡ�����ɼ�Ȩ��");
		// }
	}

	public int modifyTotalScore(String elective_id, String total_score,
			String low_score, String heigh_score) throws PlatformException {
		OracleElectiveScore electivescore = new OracleElectiveScore();

		int count = 0;
		try {
			count = electivescore.modifyTotalScore(elective_id, total_score,
					low_score, heigh_score);
		} catch (PlatformException e) {
			
		}
		return count;
	}

	public int modifyScoreReason(String elective_id, String new_score,
			String student_id, String which_score, String old_score,
			String status, String techer_name, String reason)
			throws PlatformException {
		OracleScoreModify modifyscore = new OracleScoreModify();

		int count = 0;
		try {
			count = modifyscore.addScoreModify(elective_id, old_score,
					which_score, new_score, status, techer_name, reason,
					student_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getBasicManagePriv().getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$modifyScoreReason</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} catch (Exception e) {
			
		}
		return count;
	}

	public int modifyScoreReason(String elective_id, String new_score,
			String student_id, String which_score, String old_score,
			String status, String techer_name, String reason, String teacher_id)
			throws PlatformException {
		OracleScoreModify modifyscore = new OracleScoreModify();

		int count = 0;
		try {
			count = modifyscore.addScoreModify(elective_id, old_score,
					which_score, new_score, status, techer_name, reason,
					student_id, teacher_id);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.getBasicManagePriv().getSso_id()
									+ "</whaty><whaty>BEHAVIOR$|$modifyScoreReason</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.MANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} catch (Exception e) {
			
		}
		return count;
	}

	public ScoreModify getScoreModifyStatus(String elective_id,
			String which_score, String student_id) throws PlatformException {
		OracleScoreModify modifyscore = new OracleScoreModify();

		return modifyscore.getScoreModifyStatus(elective_id, which_score,
				student_id);
	}

	public int deleteScoreRecord(String modi_id) throws PlatformException {
		OracleScoreModify modifyscore = new OracleScoreModify();
		int suc = modifyscore.deleteScoreModify(modi_id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$deleteScoreRecord</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public ScoreModify getScoreModifyInfo(String id) throws PlatformException {
		OracleScoreModify modifyscore = new OracleScoreModify();

		return modifyscore.getScoreModify(id);
	}

	public int ConfirmScoreRecord(String elective_id, String usual_score,
			String exam_score, String total_score, String expend_score,
			String renew_score, String modi_id, String modi_status)
			throws PlatformException {

		OracleScoreModify modifyscore = new OracleScoreModify();
		modifyscore.updateScoreModifyStatus(modi_id, modi_status);

		int suc = this.updateScore(total_score, usual_score, exam_score,
				expend_score, renew_score, elective_id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$ConfirmScoreRecord</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int ConfirmScoreRecord(String elective_id, String usual_score,
			String exam_score, String total_score, String expend_score,
			String experiment_score, String renew_score, String modi_id,
			String modi_status) throws PlatformException {
		OracleScoreModify modifyscore = new OracleScoreModify();
		modifyscore.updateScoreModifyStatus(modi_id, modi_status);

		int suc = this.updateScore(total_score, usual_score, exam_score,
				expend_score, experiment_score, renew_score, elective_id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$ConfirmScoreRecord</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int ConfirmScoreRecord(String elective_id, String usual_score,
			String exam_score, String total_score, String expend_score,
			String experiment_score, String test_score,
			String total_expend_score, String renew_score, String modi_id,
			String modi_status) throws PlatformException {
		OracleScoreModify modifyscore = new OracleScoreModify();
		modifyscore.updateScoreModifyStatus(modi_id, modi_status);

		int suc = this.updateScore(total_score, usual_score, exam_score,
				expend_score, experiment_score, test_score, total_expend_score,
				renew_score, elective_id);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$ConfirmScoreRecord</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public List getElectiveScoresStaticsTotalScore(Page pageover,
			String semester_id, String course_id, String site_id,
			String grade_id, String edu_type_id, String major_id, String status)
			throws PlatformException {
		// if (basicManagePriv.getElectiveScoresStaticsTotalScore == 1) {
		List searchProperties = new ArrayList();
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}

		if (status != null && !status.equals("")) {
			searchProperties.add(new SearchProperty("status", status, "="));
		}
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresStatics(pageover,
				searchProperties, null);
		// } else {
		// throw new PlatformException("��û�л�ȡ���3ɼ�Ȩ��");
		// }
	}

	public List getElectiveScoresStaticsScore(String open_course_id,
			String grade_id, String edutype_id, String major_id,
			String scoretype) throws PlatformException {

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresStatics1(open_course_id,
				grade_id, edutype_id, major_id, scoretype);
	}

	public List getElectiveScoresStaticsScore(String open_course_id,
			String grade_id, String edutype_id, String major_id,
			String scoretype, String[] sec) throws PlatformException {

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresStatics1(open_course_id,
				grade_id, edutype_id, major_id, scoretype, sec);
	}

	public List getElectiveScoresStaticsScore(String open_course_id,
			String grade_id, String edutype_id, String major_id,
			String scoretype, String[] sec, String status)
			throws PlatformException {

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresStatics1(open_course_id,
				grade_id, edutype_id, major_id, scoretype, sec, status);
	}

	public List getElectiveScoresStaticsExamScore(Page pageover,
			String semester_id, String course_id, String site_id,
			String grade_id, String edu_type_id, String major_id, String status)
			throws PlatformException {
		// if (basicManagePriv.getElectiveScoresStaticsExamScore == 1) {
		List searchProperties = new ArrayList();
		if (semester_id != null && !semester_id.equals("")) {
			searchProperties.add(new SearchProperty("semester_id", semester_id,
					"="));
		}
		if (course_id != null && !course_id.equals("")) {
			searchProperties
					.add(new SearchProperty("course_id", course_id, "="));
		}
		if (site_id != null && !site_id.equals("")) {
			searchProperties.add(new SearchProperty("site_id", site_id, "="));
		}
		if (grade_id != null && !grade_id.equals("")) {
			searchProperties.add(new SearchProperty("grade_id", grade_id, "="));
		}
		if (major_id != null && !major_id.equals("")) {
			searchProperties.add(new SearchProperty("major_id", major_id, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}

		if (status != null && !status.equals("")) {
			searchProperties.add(new SearchProperty("status", status, "="));
		}
		// searchProperties.add(new SearchProperty("major_id",
		// basicManagePriv.getMajorList(), "in"));

		// searchProperties.add(new
		// SearchProperty("edutype_id",basicManagePriv.getEduTypeList(),
		// "in"));
		// searchProperties.add(new SearchProperty("grade_id",
		// basicManagePriv.getGradeList(), "in"));
		// searchProperties.add(new SearchProperty("site_id",
		// basicManagePriv.getSiteList(), "in"));

		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.searchElectiveScoresStaticsExamScore(pageover,
				searchProperties, null);
		// } else {
		// throw new PlatformException("��û�л�ȡ���Գɼ�Ȩ��");
		// }
	}

	public int updateScoreBatch(String[] total_score, String[] usual_score,
			String[] exam_score, String[] expend_score,
			String[] experiment_score, String[] renew_score,
			String[] score_status, String[] id) throws PlatformException {
		List dataList = new ArrayList();
		List searchProperties = new ArrayList();
		for (int i = 0; i < id.length; i++) {
			HashMap data = new HashMap();
			List searchProperty = new ArrayList();
			data.put("tableName", "entity_elective");
			if (total_score != null)
				data.put("total_score", total_score[i]);
			if (usual_score != null)
				data.put("usual_score", usual_score[i]);
			if (exam_score != null)
				data.put("exam_score", exam_score[i]);
			if (expend_score != null)
				data.put("expend_score", expend_score[i]);
			if (experiment_score != null)
				data.put("experiment_score", experiment_score[i]);
			if (renew_score != null)
				data.put("renew_score", renew_score[i]);
			if (score_status != null)
				data.put("score_status", score_status[i]);
			dataList.add(data);
			if (id[i] != null && !id[i].equals("")) {
				searchProperty.add(new SearchProperty("id", id[i], "="));
			}
			searchProperties.add(searchProperty);
		}
		OracleElectiveScore electivescore = new OracleElectiveScore();
		int suc = electivescore.updateScoreBatch(dataList, searchProperties);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScoreBatch</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public ScoreSetTime getScoreSetTime(String semester_id)
			throws PlatformException {
		OracleScoreSetTime scoretime = new OracleScoreSetTime();
		scoretime.setSemester_id(semester_id);

		return scoretime.getScoreSetTime(semester_id);
	}

	public int updateScoreSetTime(String semester_id, String start_usual_time,
			String end_usual_time, String start_experiment_time,
			String end_experiment_time, String start_exam_time,
			String end_exam_time, String status_usual_time,
			String status_experiment_time, String status_exam_time)
			throws PlatformException {
		OracleScoreSetTime scoretime = new OracleScoreSetTime();
		scoretime.setSemester_id(semester_id);
		scoretime.setStart_usual_time(start_usual_time);
		scoretime.setEnd_usual_time(end_usual_time);
		scoretime.setStart_experiment_time(start_experiment_time);
		scoretime.setEnd_experiment_time(end_experiment_time);
		scoretime.setStart_exam_time(start_exam_time);
		scoretime.setEnd_exam_time(end_exam_time);
		scoretime.setStatus_usual_time(status_usual_time);
		scoretime.setStatus_experiment_time(status_experiment_time);
		scoretime.setStatus_exam_time(status_exam_time);
		int suc = scoretime.updateScoreTime();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScoreSetTime</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int addScoreSetTime(String semester_id, String start_usual_time,
			String end_usual_time, String start_experiment_time,
			String end_experiment_time, String start_exam_time,
			String end_exam_time, String status_usual_time,
			String status_experiment_time, String status_exam_time)
			throws PlatformException {
		OracleScoreSetTime scoretime = new OracleScoreSetTime();
		scoretime.setSemester_id(semester_id);
		scoretime.setStart_usual_time(start_usual_time);
		scoretime.setEnd_usual_time(end_usual_time);
		scoretime.setStart_experiment_time(start_experiment_time);
		scoretime.setEnd_experiment_time(end_experiment_time);
		scoretime.setStart_exam_time(start_exam_time);
		scoretime.setEnd_exam_time(end_exam_time);
		scoretime.setStatus_usual_time(status_usual_time);
		scoretime.setStatus_experiment_time(status_experiment_time);
		scoretime.setStatus_exam_time(status_exam_time);
		int suc = scoretime.addScoreSetTime();
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$addScoreSetTime</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int updateScoreSetTimeStatus(String semester_id, String status,
			String which_time) throws PlatformException {
		OracleScoreSetTime scoretime = new OracleScoreSetTime();
		int suc = scoretime.updateScoreTimeStatus(semester_id, status,
				which_time);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScoreSetTimeStatus</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public List getStudentScoreReport(String student_id, String major_id,
			String edutype_id) throws PlatformException {
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.getStudentScoreReport(student_id, major_id,
				edutype_id);
	}

	public List getStudentScoreReport(String student_id)
			throws PlatformException {
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.getStudentScoreReport(student_id);
	}

	public List getStudentScoreReport1(String student_id)
			throws PlatformException {
		OracleBasicScoreList basicScoreList = new OracleBasicScoreList();
		return basicScoreList.getStudentScoreReport1(student_id);
	}

	public int updateFreeScoreBatch(String elect_id, String free_total_score,
			String free_total_score_status) throws PlatformException {
		OracleElectiveScore elect = new OracleElectiveScore();
		int suc = elect.updateFreeTotalScore(elect_id, free_total_score,
				free_total_score_status);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateFreeScoreBatch</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int updateScoreBatch(String[] total_score, String[] usual_score,
			String[] exam_score, String[] expend_score,
			String[] experiment_score, String[] test_score,
			String[] renew_score, String[] score_status, String[] id)
			throws PlatformException {
		List dataList = new ArrayList();
		List searchProperties = new ArrayList();
		for (int i = 0; i < id.length; i++) {
			HashMap data = new HashMap();
			List searchProperty = new ArrayList();
			data.put("tableName", "entity_elective");
			if (total_score != null)
				data.put("total_score", total_score[i]);
			if (usual_score != null)
				data.put("usual_score", usual_score[i]);
			if (exam_score != null)
				data.put("exam_score", exam_score[i]);
			if (expend_score != null)
				data.put("expend_score", expend_score[i]);
			if (experiment_score != null)
				data.put("experiment_score", experiment_score[i]);
			if (test_score != null)
				data.put("test_score", test_score[i]);
			if (renew_score != null)
				data.put("renew_score", renew_score[i]);
			if (score_status != null)
				data.put("score_status", score_status[i]);
			dataList.add(data);
			if (id[i] != null && !id[i].equals("")) {
				searchProperty.add(new SearchProperty("id", id[i], "="));
			}
			searchProperties.add(searchProperty);
		}
		OracleElectiveScore electivescore = new OracleElectiveScore();
		int suc = electivescore.updateScoreBatch(dataList, searchProperties);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScoreBatch</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public int updateScoreBatch(String[] total_score, String[] usual_score,
			String[] exam_score, String[] expend_score,
			String[] experiment_score, String[] test_score,
			String[] total_expend_score, String[] renew_score,
			String[] score_status, String[] id) throws PlatformException {
		List dataList = new ArrayList();
		List searchProperties = new ArrayList();
		for (int i = 0; i < id.length; i++) {
			HashMap data = new HashMap();
			List searchProperty = new ArrayList();
			data.put("tableName", "entity_elective");
			if (total_score != null)
				data.put("total_score", total_score[i]);
			if (usual_score != null)
				data.put("usual_score", usual_score[i]);
			if (exam_score != null)
				data.put("exam_score", exam_score[i]);
			if (expend_score != null)
				data.put("expend_score", expend_score[i]);
			if (experiment_score != null)
				data.put("experiment_score", experiment_score[i]);
			if (test_score != null)
				data.put("test_score", test_score[i]);
			if (total_expend_score != null)
				data.put("total_expend_score", total_expend_score[i]);
			if (renew_score != null)
				data.put("renew_score", renew_score[i]);
			if (score_status != null)
				data.put("score_status", score_status[i]);
			dataList.add(data);
			if (id[i] != null && !id[i].equals("")) {
				searchProperty.add(new SearchProperty("id", id[i], "="));
			}
			searchProperties.add(searchProperty);
		}
		OracleElectiveScore electivescore = new OracleElectiveScore();
		int suc = electivescore.updateScoreBatch(dataList, searchProperties);
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScoreBatch</whaty><whaty>STATUS$|$"
								+ suc
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());
		return suc;
	}

	public void updateScoreBatch(List total_scoreList, List usual_scoreList,
			List exam_scoreList, List expend_scoreList, List experiment_score,
			List test_scoreList, List renew_scoreList, List idList,
			List reg_noList, List nameList, String open_course_id)
			throws PlatformException {
		List dataList = new ArrayList();
		Iterator it = dataList.iterator();
		if (reg_noList != null && reg_noList.size() > 0)
			it = reg_noList.iterator();
		else if (idList != null && idList.size() > 0)
			it = idList.iterator();
		else
			return;
		for (int i = 0; it.hasNext(); i++) {
			it.next();
			HashMap data = new HashMap();
			data.put("tableName", "entity_elective");
			if (total_scoreList != null)
				data.put("total_score", total_scoreList.get(i));
			if (usual_scoreList != null)
				data.put("usual_score", usual_scoreList.get(i));
			if (exam_scoreList != null)
				data.put("exam_score", exam_scoreList.get(i));
			if (expend_scoreList != null)
				data.put("expend_score", expend_scoreList.get(i));
			if (experiment_score != null)
				data.put("experiment_score", experiment_score.get(i));
			if (test_scoreList != null)
				data.put("test_score", test_scoreList.get(i));
			if (renew_scoreList != null)
				data.put("renew_score", renew_scoreList.get(i));
			dataList.add(data);
		}
		OracleElectiveScore electivescore = new OracleElectiveScore();
		electivescore.updateScoreBatch(dataList, idList, reg_noList, nameList,
				open_course_id, "0");
		UserLog
				.setInfo(
						"<whaty>USERID$|$"
								+ this.getBasicManagePriv().getSso_id()
								+ "</whaty><whaty>BEHAVIOR$|$updateScoreBatch</whaty><whaty>STATUS$|$"
								+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
								+ LogType.MANAGER
								+ "</whaty><whaty>PRIORITY$|$"
								+ LogPriority.INFO + "</whaty>", new Date());

	}

	public ManagerPriv getBasicManagePriv() {
		return basicManagePriv;
	}

	public List<ScoreModify> getScoreModifyRecords(Page page, String siteId,
			String gradeId, String majorId, String eduTypeId, String semesterId,
			String courseId, String scoreType, String scoreStatus, String regNo, String cardNo)
			throws PlatformException {
		OracleBasicScoreList osl = new OracleBasicScoreList();
		List<SearchProperty> searchList = new ArrayList<SearchProperty>();
		if(siteId != null && !"".equals(siteId) && !"null".equalsIgnoreCase(siteId)) {
			searchList.add(new SearchProperty("esi.site_id", siteId, "="));
		}
		if(gradeId != null && !"".equals(gradeId) && !"null".equalsIgnoreCase(gradeId)) {
			searchList.add(new SearchProperty("esi.grade_id", gradeId, "="));
		}
		if(majorId != null && !"".equals(majorId) && !"null".equalsIgnoreCase(majorId)) {
			searchList.add(new SearchProperty("esi.major_id", majorId, "="));
		}
		if(eduTypeId != null && !"".equals(eduTypeId) && !"null".equalsIgnoreCase(eduTypeId)) {
			searchList.add(new SearchProperty("esi.edutype_id", eduTypeId, "="));
		}
		if(semesterId != null && !"".equals(semesterId) && !"null".equalsIgnoreCase(semesterId)) {
			searchList.add(new SearchProperty("es.id", semesterId, "="));
		}
		if(courseId != null && !"".equals(courseId) && !"null".equalsIgnoreCase(courseId)) {
			searchList.add(new SearchProperty("eci.id", courseId, "="));
		}
		if(scoreType != null && !"".equals(scoreType) && !"null".equalsIgnoreCase(scoreType)) {
			searchList.add(new SearchProperty("esm.which_score", scoreType, "="));
		}
		if(scoreStatus != null && !"".equals(scoreStatus) && !"null".equalsIgnoreCase(scoreStatus)) {
			searchList.add(new SearchProperty("esm.status", scoreStatus, "="));
		}
		if(regNo != null && !"".equals(regNo) && !"null".equalsIgnoreCase(regNo)) {
			searchList.add(new SearchProperty("esi.reg_no", regNo, "="));
		}
		if(cardNo != null && !"".equals(cardNo) && !"null".equalsIgnoreCase(cardNo)) {
			searchList.add(new SearchProperty("esi.id_card", cardNo, "="));
		}
		
		List<OrderProperty> orderList = new ArrayList<OrderProperty>();
		orderList.add(new OrderProperty("reg_no"));
		
		return osl.getScoreModifyRecords(page, searchList, orderList);
	}

	public int getScoreModifyRecordsNum(String siteId, String gradeId, String majorId,
			String eduTypeId, String semesterId, String courseId,
			String scoreType, String scoreStatus, String regNo, String cardNo) throws PlatformException {
		OracleBasicScoreList osl = new OracleBasicScoreList();
		List<SearchProperty> searchList = new ArrayList<SearchProperty>();
		if(siteId != null && !"".equals(siteId) && !"null".equalsIgnoreCase(siteId)) {
			searchList.add(new SearchProperty("esi.site_id", siteId, "="));
		}
		if(gradeId != null && !"".equals(gradeId) && !"null".equalsIgnoreCase(gradeId)) {
			searchList.add(new SearchProperty("esi.grade_id", gradeId, "="));
		}
		if(majorId != null && !"".equals(majorId) && !"null".equalsIgnoreCase(majorId)) {
			searchList.add(new SearchProperty("esi.major_id", majorId, "="));
		}
		if(eduTypeId != null && !"".equals(eduTypeId) && !"null".equalsIgnoreCase(eduTypeId)) {
			searchList.add(new SearchProperty("esi.edutype_id", eduTypeId, "="));
		}
		if(semesterId != null && !"".equals(semesterId) && !"null".equalsIgnoreCase(semesterId)) {
			searchList.add(new SearchProperty("es.id", semesterId, "="));
		}
		if(courseId != null && !"".equals(courseId) && !"null".equalsIgnoreCase(courseId)) {
			searchList.add(new SearchProperty("eci.id", courseId, "="));
		}
		if(scoreType != null && !"".equals(scoreType) && !"null".equalsIgnoreCase(scoreType)) {
			searchList.add(new SearchProperty("esm.which_score", scoreType, "="));
		}
		if(scoreStatus != null && !"".equals(scoreStatus) && !"null".equalsIgnoreCase(scoreStatus)) {
			searchList.add(new SearchProperty("esm.status", scoreStatus, "="));
		}
		if(regNo != null && !"".equals(regNo) && !"null".equalsIgnoreCase(regNo)) {
			searchList.add(new SearchProperty("esi.reg_no", regNo, "="));
		}
		if(cardNo != null && !"".equals(cardNo) && !"null".equalsIgnoreCase(cardNo)) {
			searchList.add(new SearchProperty("esi.id_card", cardNo, "="));
		}
		
		return osl.getScoreModifyRecordsNum(searchList);
	}

	@Override
	public int updateElectiveScore(String studentId, String opencourseId,
			String score) throws PlatformException {
		OracleElectiveScore electivescore = new OracleElectiveScore();
		return electivescore.updateScore(studentId, opencourseId, score);
	}

	@Override
	public String getElectiveScore(String studentId, String opencourseId)
			throws PlatformException {
		OracleElectiveScore electivescore = new OracleElectiveScore();
		return electivescore.getElectiveScore(studentId, opencourseId);
	}

	@Override
	public Map getOnlineExamScore(String studentId, String opencourseId)
			throws PlatformException {
		// TODO Auto-generated method stub
		OracleElectiveScore electivescore = new OracleElectiveScore();
		return electivescore.getOnlineExamScore(studentId, opencourseId);
	}

	@Override
	public int updateOnlineExamScore(String studentId, String opencourseId,
			String score,String ispass) throws PlatformException {
		OracleElectiveScore electivescore = new OracleElectiveScore();
		return electivescore.updateOnlineExamScore(studentId, opencourseId, score,ispass);
	}
}
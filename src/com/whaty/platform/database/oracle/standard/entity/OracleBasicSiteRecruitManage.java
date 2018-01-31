package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleBasicRecruitList;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitActivity;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitBatch;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitBatchEduMajor;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitPlan;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitStudent;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitTestRoom;
import com.whaty.platform.database.oracle.standard.entity.recruit.OracleRecruitTestStudent;
import com.whaty.platform.entity.BasicSiteRecruitManage;
import com.whaty.platform.entity.recruit.RecruitActivity;
import com.whaty.platform.entity.recruit.RecruitBatch;
import com.whaty.platform.entity.recruit.RecruitEduInfo;
import com.whaty.platform.entity.recruit.RecruitLimit;
import com.whaty.platform.entity.recruit.RecruitStudent;
import com.whaty.platform.entity.recruit.RecruitTestRoom;
import com.whaty.platform.entity.recruit.RecruitTestStudent;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.OrderProperty;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.log.UserLog;

public class OracleBasicSiteRecruitManage extends BasicSiteRecruitManage {
	SiteManagerPriv basicManagePriv;

	public OracleBasicSiteRecruitManage(SiteManagerPriv samanagerpriv) {
		this.basicManagePriv = samanagerpriv;
	}

	public List getSitePlans(Page page, String site_id, String batchId)
			throws NoRightException {
		if (basicManagePriv.getRecruitPlan == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			searchProperty.add(new SearchProperty("site_id", site_id, "="));
			searchProperty.add(new SearchProperty("batch_id", batchId, "="));
			orderProperty.add(new OrderProperty("edutype_id"));
			orderProperty.add(new OrderProperty("major_id"));
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			return basicdatalist.getPlans(page, searchProperty, orderProperty);
		} else {
			throw new NoRightException("您没有浏览招生计划的权限！");
		}
	}

	public int getPlansNum(String site_id, String batchId) {
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("site_id", site_id, "="));
		searchProperty.add(new SearchProperty("batch_id", batchId, "="));
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		return basicdatalist.getPlansNum(searchProperty);
	}

	// 获取当前招生批次
	public List getActiveBatch() throws NoRightException {
		if (basicManagePriv.getRecruitBatch == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperty = new ArrayList();
			searchProperty.add(new SearchProperty("active", "1", "="));
			return basicdatalist.getActiveBatchs(null, searchProperty, null);
		} else {
			throw new NoRightException("您没有浏览批次信息的权限！");
		}
	}

	public List getBatches() throws NoRightException {
		if (basicManagePriv.getRecruitBatch == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("id", OrderProperty.DESC));
			return basicdatalist.getBatchs(null, searchProperty, orderProperty);
		} else {
			throw new NoRightException("您没有浏览批次信息的权限！");
		}
	}

	// 获取学生信息
	public List getStudents(Page page, String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status) throws NoRightException {
		if (basicManagePriv.getRecruitStudent == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = null;
			if (status != null && !status.equals("null") && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			if (site_id != null && !site_id.equals("null")
					&& !site_id.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site_id, "="));
			}
			if (batchId != null && !batchId.equals("null")
					&& !batchId.equals("")) {
				searchProperty
						.add(new SearchProperty("batch_id", batchId, "="));
			}
			if (name != null && !name.equals("null") && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (card_no != null && !card_no.equals("null")
					&& !card_no.equals("")) {
				searchProperty.add(new SearchProperty("card_no", card_no,
						"like"));
			}
			if (zglx != null && !zglx.equals("null") && !zglx.equals("")) {
				searchProperty
						.add(new SearchProperty("considertype", zglx, "="));
			}
			if (edu_type_id != null && !edu_type_id.equals("null")
					&& !edu_type_id.equals("")) {
				searchProperty.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("null")
					&& !major_id.equals("")) {
				searchProperty
						.add(new SearchProperty("major_id", major_id, "="));
			}
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			return basicdatalist.getStudents(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有浏览报名信息的权限！");
		}

	}

	public List getStudents(Page page, String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status, String photo)
			throws NoRightException {
		if (basicManagePriv.getRecruitStudent == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = null;
			if (status != null && !status.equals("null") && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			if (site_id != null && !site_id.equals("null")
					&& !site_id.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site_id, "="));
			}
			if (batchId != null && !batchId.equals("null")
					&& !batchId.equals("")) {
				searchProperty
						.add(new SearchProperty("batch_id", batchId, "="));
			}
			if (name != null && !name.equals("null") && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (card_no != null && !card_no.equals("null")
					&& !card_no.equals("")) {
				searchProperty.add(new SearchProperty("card_no", card_no,
						"like"));
			}
			if (zglx != null && !zglx.equals("null") && !zglx.equals("")) {
				searchProperty
						.add(new SearchProperty("considertype", zglx, "="));
			}
			if (edu_type_id != null && !edu_type_id.equals("null")
					&& !edu_type_id.equals("")) {
				searchProperty.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("null")
					&& !major_id.equals("")) {
				searchProperty
						.add(new SearchProperty("major_id", major_id, "="));
			}
			if (photo != null && !photo.equals("null") && !photo.equals("")) {
				if (photo.equals("0"))
					searchProperty.add(new SearchProperty("photo_link", photo,
							"isNull"));
				else
					searchProperty.add(new SearchProperty("photo_link", photo,
							"isNotNull"));

			}
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			return basicdatalist.getStudents(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有浏览学生信息的权限！");
		}

	}

	// 获取免试学生信息
	public List getFreeStudents(Page page, String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String considertype_status) throws NoRightException {
		if (basicManagePriv.getFreeRecruitStudent == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = null;

			searchProperty.add(new SearchProperty("site_id", site_id, "="));
			if (batchId != null && !batchId.equals("null")
					&& !batchId.equals("")) {
				searchProperty
						.add(new SearchProperty("batch_id", batchId, "="));
			}
			if (name != null && !name.equals("null") && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (card_no != null && !card_no.equals("null")
					&& !card_no.equals("")) {
				searchProperty.add(new SearchProperty("card_no", card_no,
						"like"));
			}
			if (zglx != null && !zglx.equals("null") && !zglx.equals("")) {
				searchProperty
						.add(new SearchProperty("considertype", zglx, "="));
			}
			if (edu_type_id != null && !edu_type_id.equals("null")
					&& !edu_type_id.equals("")) {
				searchProperty.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("null")
					&& !major_id.equals("")) {
				searchProperty
						.add(new SearchProperty("major_id", major_id, "="));
			}

			if (considertype_status != null
					&& !considertype_status.equals("null")
					&& !considertype_status.equals("")) {
				searchProperty.add(new SearchProperty("considertype_status",
						considertype_status, "in"));
			}
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			return basicdatalist.getStudents(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有浏览免试学生信息的权限！");
		}

	}

	public int getStudentsNum(String batchId, String name, String card_no,
			String zglx, String edu_type_id, String major_id, String site_id,
			String status) {
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("status", status, "="));
		searchProperty.add(new SearchProperty("site_id", site_id, "="));
		if (batchId != null && !batchId.equals("null") && !batchId.equals("")) {
			searchProperty.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (name != null && !name.equals("null") && !name.equals("")) {
			searchProperty.add(new SearchProperty("name", name, "like"));
		}
		if (card_no != null && !card_no.equals("null") && !card_no.equals("")) {
			searchProperty.add(new SearchProperty("card_no", card_no, "like"));
		}
		if (zglx != null && !zglx.equals("null") && !zglx.equals("")) {
			searchProperty.add(new SearchProperty("considertype", zglx, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("null")
				&& !edu_type_id.equals("")) {
			searchProperty.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (major_id != null && !major_id.equals("null")
				&& !major_id.equals("")) {
			searchProperty.add(new SearchProperty("major_id", major_id, "="));
		}
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		return basicdatalist.getStudentsNum(searchProperty);
	}

	public int getStudentsNum(String batchId, String name, String card_no,
			String zglx, String edu_type_id, String major_id, String site_id,
			String status, String photo) {
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("status", status, "="));
		searchProperty.add(new SearchProperty("site_id", site_id, "="));
		if (batchId != null && !batchId.equals("null") && !batchId.equals("")) {
			searchProperty.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (name != null && !name.equals("null") && !name.equals("")) {
			searchProperty.add(new SearchProperty("name", name, "like"));
		}
		if (card_no != null && !card_no.equals("null") && !card_no.equals("")) {
			searchProperty.add(new SearchProperty("card_no", card_no, "like"));
		}
		if (zglx != null && !zglx.equals("null") && !zglx.equals("")) {
			searchProperty.add(new SearchProperty("considertype", zglx, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("null")
				&& !edu_type_id.equals("")) {
			searchProperty.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (major_id != null && !major_id.equals("null")
				&& !major_id.equals("")) {
			searchProperty.add(new SearchProperty("major_id", major_id, "="));
		}
		if (photo != null && !photo.equals("null") && !photo.equals("")) {
			if (photo.equals("0"))
				searchProperty.add(new SearchProperty("photo_link", photo,
						"isNull"));
			else
				searchProperty.add(new SearchProperty("photo_link", photo,
						"isNotNull"));

		}
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		return basicdatalist.getStudentsNum(searchProperty);
	}

	public int getFreeStudentsNum(String batchId, String name, String card_no,
			String zglx, String edu_type_id, String major_id, String site_id,
			String considertype_status) {
		List searchProperty = new ArrayList();

		searchProperty.add(new SearchProperty("site_id", site_id, "="));
		if (batchId != null && !batchId.equals("null") && !batchId.equals("")) {
			searchProperty.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (name != null && !name.equals("null") && !name.equals("")) {
			searchProperty.add(new SearchProperty("name", name, "like"));
		}
		if (card_no != null && !card_no.equals("null") && !card_no.equals("")) {
			searchProperty.add(new SearchProperty("card_no", card_no, "like"));
		}
		if (zglx != null && !zglx.equals("null") && !zglx.equals("")) {
			searchProperty.add(new SearchProperty("considertype", zglx, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("null")
				&& !edu_type_id.equals("")) {
			searchProperty.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (major_id != null && !major_id.equals("null")
				&& !major_id.equals("")) {
			searchProperty.add(new SearchProperty("major_id", major_id, "="));
		}

		if (considertype_status != null && !considertype_status.equals("null")
				&& !considertype_status.equals("")) {
			searchProperty.add(new SearchProperty("considertype_status",
					considertype_status, "in"));
		}
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		return basicdatalist.getStudentsNum(searchProperty);
	}

	// 确认学生信息
	public int confirmStudent(String[] studentIds) throws PlatformException,
			NoRightException {
		if (basicManagePriv.getConfirmStudent == 1) {
			OracleRecruitStudent student = new OracleRecruitStudent();
			int count = 0;
			for (int i = 0; i < studentIds.length; i++) {
				student.setId(studentIds[i]);
				count += student.confirm();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$confirmStudent</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new NoRightException("您没有确认学生信息的权限！");
		}

	}

	// 取消确认学生信息
	public int unConfirmStudent(String[] studentIds) throws PlatformException,
			NoRightException {

		if (basicManagePriv.getUnConfirmStudent == 1) {
			OracleRecruitStudent student = new OracleRecruitStudent();
			int count = 0;
			for (int i = 0; i < studentIds.length; i++) {
				student.setId(studentIds[i]);
				count += student.unConfirm();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$unConfirmStudent</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new NoRightException("您没有取消确认学生信息的权限！");
		}

	}

	// 上传学生照片
	public int uploadImage(String card_no, String filename)
			throws PlatformException, NoRightException {
		if (basicManagePriv.uploadImage == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			int suc = basicdatalist.uploadImage(card_no, filename, "photo");
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$uploadImage</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			if(suc>1){
				suc = 1;
			}
			return suc;

		} else {
			throw new NoRightException("您没有上传照片的权限！");
		}
 
	}

	public int uploadBatchImage(String card_no, String filename)
			throws PlatformException, NoRightException {
		if (basicManagePriv.uploadImage == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			int suc = basicdatalist.uploadImage(card_no, filename, "photo");
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$uploadBatchImage</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("您没有批量上传照片的权限！");
		}

	}

	public int uploadIdCard(String card_no, String filename)
			throws PlatformException, NoRightException {
		if (basicManagePriv.uploadIdCard == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			int suc = basicdatalist.uploadImage(card_no, filename, "idcard");
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$uploadIdCard</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		    if(suc>1){
		    	suc = 1;
		    }
			return suc;

		} else {
			throw new NoRightException("您没有上传身份证的权限！");
		}
	}

	public int uploadGraduateCard(String card_no, String filename)
			throws PlatformException, NoRightException {
		if (basicManagePriv.uploadGraduatedCard == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			int suc = basicdatalist.uploadImage(card_no, filename,
					"graduatecard");
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$uploadGraduateCard</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			if(suc>1){
				suc =1;
			}
			return suc;

		} else {
			throw new NoRightException("您没有上传毕业证的权限！");
		}
	}

	public List getStudentTestCourse(Page page, String name, String card_no,
			String zglx, String edu_type_id, String major_id, String site_id)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getStudentTestCourse == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			// orderProperty.add(new OrderProperty("card_no"));
			orderProperty.add(new OrderProperty("edutype_id"));
			orderProperty.add(new OrderProperty("major_id"));
			orderProperty.add(new OrderProperty("course_id"));
			searchProperty.add(new SearchProperty("status", "0", "<>"));
			searchProperty.add(new SearchProperty("site_id", site_id, "="));
			if (name != null && !name.equals("null") && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (card_no != null && !card_no.equals("null")
					&& !card_no.equals("")) {
				searchProperty.add(new SearchProperty("card_no", card_no,
						"like"));
			}
			if (zglx != null && !zglx.equals("null") && !zglx.equals("")) {
				// searchProperty.add(new SearchProperty("considertype",
				// zglx,"<>"));
			}
			if (edu_type_id != null && !edu_type_id.equals("null")
					&& !edu_type_id.equals("")) {
				searchProperty.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("null")
					&& !major_id.equals("")) {
				searchProperty
						.add(new SearchProperty("major_id", major_id, "="));
			}
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			return basicdatalist.getStudentTestCourse(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有浏览学生考试课程的权限！");
		}

	}

	public List getSignStatistic(Page page, String batch_id, String site_id)
			throws NoRightException {
		if (basicManagePriv.getSignTongji == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			return basicdatalist.getSignStatistic(page, batch_id, site_id);
		} else {
			throw new NoRightException("您没有浏览学生报名人数统计的权限！");
		}
	}

	public int getSignStatisticNum(String batch_id, String site_id)
			throws NoRightException {
		if (basicManagePriv.getSignTongji == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			return basicdatalist.getSignStatisticNum(batch_id, site_id);
		} else {
			throw new NoRightException("您没有浏览学生报名人数统计的权限！");
		}
	}

	public int getStudentTestCoursesNum(String name, String card_no,
			String zglx, String edu_type_id, String major_id, String site_id) {
		List searchProperty = new ArrayList();
		searchProperty.add(new SearchProperty("status", "0", "<>"));
		searchProperty.add(new SearchProperty("site_id", site_id, "="));
		if (name != null && !name.equals("null") && !name.equals("")) {
			searchProperty.add(new SearchProperty("name", name, "like"));
		}
		if (card_no != null && !card_no.equals("null") && !card_no.equals("")) {
			searchProperty.add(new SearchProperty("card_no", card_no, "like"));
		}
		if (zglx != null && !zglx.equals("null") && !zglx.equals("")) {
			// searchProperty.add(new SearchProperty("considertype", zglx,
			// "<>"));
		}
		if (edu_type_id != null && !edu_type_id.equals("null")
				&& !edu_type_id.equals("")) {
			searchProperty.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (major_id != null && !major_id.equals("null")
				&& !major_id.equals("")) {
			searchProperty.add(new SearchProperty("major_id", major_id, "="));
		}
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		return basicdatalist.getStudentTestCoursesNum(searchProperty);
	}

	public List getTotalTestRoom(Page page, String batchId, String edutype_id,
			String major_id, String site_id) throws PlatformException,
			NoRightException {
		if (basicManagePriv.getTestRoomTongji == 1) {
			OracleBasicRecruitList data = new OracleBasicRecruitList();
			return data.getTotalTestRoom(page, batchId, edutype_id, major_id,
					site_id);
		} else {
			throw new NoRightException("您没有浏览层次专业下考场统计信息的权限！");
		}
	}

	public int getTotalTestRoomNums(String batchId, String edutype_id,
			String major_id, String site_id) throws PlatformException,
			NoRightException {
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		return basicdatalist.getTotalTestRoomNums(batchId, edutype_id,
				major_id, site_id);
	}

	public List getEdutypeMajorTestDesk(String batchId, String edutype_id,
			String major_id, String card_no, String site_id, String testroom_id)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getEdutypeMajorTestDesk == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			if (batchId != null && !batchId.equals("")
					&& !batchId.equals("null"))
				searchProperty
						.add(new SearchProperty("batch_id", batchId, "="));
			searchProperty.add(new SearchProperty("site_id", site_id, "="));
			if (edutype_id != null && !edutype_id.equals("")
					&& !edutype_id.equals("null"))
				searchProperty.add(new SearchProperty("edutype_id", edutype_id,
						"="));
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchProperty
						.add(new SearchProperty("major_id", major_id, "="));
			if (card_no != null && !card_no.equals("")
					&& !card_no.equals("null"))
				searchProperty.add(new SearchProperty("card_no", card_no, "="));
			if (testroom_id != null && !testroom_id.equals("")
					&& !testroom_id.equals("null"))
				searchProperty.add(new SearchProperty("testroom_id",
						testroom_id, "="));
			orderProperty.add(new OrderProperty("testroom_id",
					OrderProperty.ASC));
			orderProperty.add(new OrderProperty("numbyroom"));

			List list = basicdatalist.getStudentTestDesks(null, searchProperty,
					orderProperty);
			return list;
		} else {
			throw new NoRightException("您没有打印准考证的权限！");
		}

	}

	public List getEdutypeMajorTestDesk(String batchId, String edutype_id,
			String major_id, String card_no, String site_id,
			String testroom_id, String reg_no) throws PlatformException,
			NoRightException {
		if (basicManagePriv.getEdutypeMajorTestDesk == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			if (batchId != null && !batchId.equals("")
					&& !batchId.equals("null"))
				searchProperty
						.add(new SearchProperty("batch_id", batchId, "="));
			searchProperty.add(new SearchProperty("site_id", site_id, "="));
			if (edutype_id != null && !edutype_id.equals("")
					&& !edutype_id.equals("null"))
				searchProperty.add(new SearchProperty("edutype_id", edutype_id,
						"="));
			if (major_id != null && !major_id.equals("")
					&& !major_id.equals("null"))
				searchProperty
						.add(new SearchProperty("major_id", major_id, "="));
			if (card_no != null && !card_no.equals("")
					&& !card_no.equals("null"))
				searchProperty.add(new SearchProperty("card_no", card_no, "="));

			if (reg_no != null && !reg_no.equals("") && !reg_no.equals("null"))
				searchProperty.add(new SearchProperty("testcard_id", reg_no,
						"="));
			if (testroom_id != null && !testroom_id.equals("")
					&& !testroom_id.equals("null"))
				searchProperty.add(new SearchProperty("testroom_id",
						testroom_id, "="));
			orderProperty.add(new OrderProperty("testroom_id",
					OrderProperty.ASC));
			orderProperty.add(new OrderProperty("numbyroom"));

			List list = basicdatalist.getStudentTestDesks(null, searchProperty,
					orderProperty);
			return list;
		} else {
			throw new NoRightException("您没有打印准考证的权限！");
		}

	}

	public String[][] batchDeletePlan(String[] planIds)
			throws NoRightException, PlatformException {
		if (basicManagePriv.deletePlan == 1) {
			if (planIds != null && planIds.length != 0) {
				String str[][] = new String[planIds.length][2];
				for (int i = 0; i < planIds.length; i++) {
					OracleRecruitPlan plan = new OracleRecruitPlan();
					plan.setId(planIds[i]);
					str[i][0] = planIds[i];
					str[i][1] = String.valueOf(plan.delete());
				}
				UserLog
						.setInfo(
								"<whaty>USERID$|$"
										+ this.basicManagePriv.getManagerId()
										+ "</whaty><whaty>BEHAVIOR$|$batchDeletePlan</whaty><whaty>STATUS$|$"
										+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
										+ LogType.SITEMANAGER
										+ "</whaty><whaty>PRIORITY$|$"
										+ LogPriority.INFO + "</whaty>",
								new Date());
				return str;
			} else
				throw new PlatformException("没有招生计划删除!");
		} else {
			throw new NoRightException("您没有批量删除招生计划的权限！");
		}
	}

	public int deletePlan(String planId) throws NoRightException,
			PlatformException {
		if (basicManagePriv.deletePlan == 1) {
			OracleRecruitPlan plan = new OracleRecruitPlan();
			plan.setId(planId) ;
			int suc = plan.delete();
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$deletePlan</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("您没有删除招生计划的权限！");
		}
	}

	public RecruitStudent getStudent(String id) throws NoRightException {

		if (basicManagePriv.getRecruitStudent == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = null;
			searchProperty.add(new SearchProperty("id", id, "="));
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List list = basicdatalist.getStudents(null, searchProperty,
					orderProperty);
			return (RecruitStudent) list.get(0);
		} else {
			throw new NoRightException("您没有浏览学生信息的权限！");
		}
	}

	public List getStudentsByMajorEdutype(Page page, String batchId,
			String siteId, String majorId, String eduTypeId)
			throws NoRightException {

		if (basicManagePriv.getRecruitStudent == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			searchProperty.add(new SearchProperty("batch_id", batchId, "="));
			searchProperty.add(new SearchProperty("site_id", siteId, "="));
			searchProperty.add(new SearchProperty("major_id", majorId, "="));
			searchProperty
					.add(new SearchProperty("edutype_id", eduTypeId, "="));
			orderProperty.add(new OrderProperty("card_no"));
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List list = basicdatalist.getStudents(page, searchProperty,
					orderProperty);
			return list;
		} else {
			throw new NoRightException("您没有浏览学生信息的权限！");
		}
	}

	public int getStudentsByMajorEdutypeNum(String batchId, String siteId,
			String majorId, String eduTypeId) throws NoRightException {

		if (basicManagePriv.getRecruitStudent == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			searchProperty.add(new SearchProperty("batch_id", batchId, "="));
			searchProperty.add(new SearchProperty("site_id", siteId, "="));
			searchProperty.add(new SearchProperty("major_id", majorId, "="));
			searchProperty
					.add(new SearchProperty("edutype_id", eduTypeId, "="));
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			return basicdatalist.getStudentsNum(searchProperty);
		} else {
			throw new NoRightException("您没有浏览学生信息的权限！");
		}
	}

	public List getConferStudentsByMajorEdutype(Page page, String batchId,
			String siteId, String majorId, String eduTypeId)
			throws NoRightException {

		if (basicManagePriv.getRecruitStudent == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			String status = "1";
			String considertype = "2";
			searchProperty.add(new SearchProperty("batch_id", batchId, "="));
			searchProperty.add(new SearchProperty("site_id", siteId, "="));
			searchProperty.add(new SearchProperty("major_id", majorId, "="));
			searchProperty
					.add(new SearchProperty("edutype_id", eduTypeId, "="));
			searchProperty.add(new SearchProperty("status", status, "="));
			orderProperty.add(new OrderProperty("card_no"));
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List list = basicdatalist.getStudents(page, searchProperty,
					orderProperty);
			return list;
		} else {
			throw new NoRightException("您没有浏览学生信息的权限！");
		}
	}

	public int getConferStudentsByMajorEdutypeNum(String batchId,
			String siteId, String majorId, String eduTypeId)
			throws NoRightException {

		if (basicManagePriv.getRecruitStudent == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			String status = "1";
			String considertype = "2";
			searchProperty.add(new SearchProperty("batch_id", batchId, "="));
			searchProperty.add(new SearchProperty("site_id", siteId, "="));
			searchProperty.add(new SearchProperty("major_id", majorId, "="));
			searchProperty
					.add(new SearchProperty("edutype_id", eduTypeId, "="));
			searchProperty.add(new SearchProperty("status", status, "="));
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			return basicdatalist.getStudentsNum(searchProperty);
		} else {
			throw new NoRightException("您没有浏览学生信息的权限！");
		}
	}

	public List getConferFreeStudentsByMajorEdutype(Page page, String batchId,
			String siteId, String majorId, String eduTypeId)
			throws NoRightException {

		if (basicManagePriv.getRecruitStudent == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			String considertype_status = "2";
			String considertype = "2";
			searchProperty.add(new SearchProperty("batch_id", batchId, "="));
			searchProperty.add(new SearchProperty("site_id", siteId, "="));
			searchProperty.add(new SearchProperty("major_id", majorId, "="));
			searchProperty
					.add(new SearchProperty("edutype_id", eduTypeId, "="));
			searchProperty.add(new SearchProperty("considertype_status",
					considertype_status, "="));
			searchProperty.add(new SearchProperty("considertype", considertype,
					"="));
			orderProperty.add(new OrderProperty("card_no"));
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List list = basicdatalist.getStudents(page, searchProperty,
					orderProperty);
			return list;
		} else {
			throw new NoRightException("您没有浏览学生信息的权限！");
		}
	}

	public int getConferFreeStudentsByMajorEdutypeNum(String batchId,
			String siteId, String majorId, String eduTypeId)
			throws NoRightException {

		if (basicManagePriv.getRecruitStudent == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = new ArrayList();
			String considertype_status = "2";
			String considertype = "2";
			searchProperty.add(new SearchProperty("batch_id", batchId, "="));
			searchProperty.add(new SearchProperty("site_id", siteId, "="));
			searchProperty.add(new SearchProperty("major_id", majorId, "="));
			searchProperty
					.add(new SearchProperty("edutype_id", eduTypeId, "="));
			searchProperty.add(new SearchProperty("considertype_status",
					considertype_status, "="));
			searchProperty.add(new SearchProperty("considertype", considertype,
					"="));
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			return basicdatalist.getStudentsNum(searchProperty);
		} else {
			throw new NoRightException("您没有浏览学生信息的权限！");
		}
	}

	/*
	 * 确认面试学生
	 * 
	 * @see com.whaty.platform.entity.BasicSiteRecruitManage#confirmFreeStudent(java.lang.String)
	 */
	public int confirmFreeStudent(String[] studentIds, String[] cons)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getConfirmFreeStudent == 1) {
			OracleRecruitStudent student = new OracleRecruitStudent();
			int count = 0;
			for (int i = 0; i < studentIds.length; i++) {
				student.setId(studentIds[i]);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setConsidertype_status("1");
				eduInfo.setConsidertype_note(cons[i]);
				student.setEduInfo(eduInfo);
				count += student.confirmFree();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$confirmFreeStudent</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new NoRightException("您没有确认免试学生信息的权限！");
		}
	}

	/*
	 * 取消确认面试学生
	 * 
	 * @see com.whaty.platform.entity.BasicSiteRecruitManage#unConfirmFreeStudent(java.lang.String)
	 */
	public int unConfirmFreeStudent(String[] studentIds, String[] cons)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getUnConfirmFreeStudent == 1) {
			OracleRecruitStudent student = new OracleRecruitStudent();
			int count = 0;
			for (int i = 0; i < studentIds.length; i++) {
				student.setId(studentIds[i]);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setConsidertype_status("0");
				eduInfo.setConsidertype_note(cons[i]);
				student.setEduInfo(eduInfo);
				count += student.unConfirmFree();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$unConfirmFreeStudent</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new NoRightException("您没有取消确认免试学生信息的权限！");
		}
	}

	public int rejectFreeStudent(String[] studentIds, String[] cons)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getUnConfirmFreeStudent == 1) {
			OracleRecruitStudent student = new OracleRecruitStudent();
			int count = 0;
			for (int i = 0; i < studentIds.length; i++) {
				student.setId(studentIds[i]);
				RecruitEduInfo eduInfo = new RecruitEduInfo();
				eduInfo.setConsidertype_status("0");
				eduInfo.setConsidertype_note(cons[i]);
				student.setEduInfo(eduInfo);
				count += student.rejectFree();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$rejectFreeStudent</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new NoRightException("您没有取消确认免试学生信息的权限！");
		}
	}

	public void allotStudents(String batch_id, String site_id, String numroom)
			throws PlatformException, NoRightException {
		if (basicManagePriv.generateTestRoom == 1) {
			OracleRecruitActivity recruitActivity = new OracleRecruitActivity();
			recruitActivity.allotStudents(batch_id, site_id, numroom);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$allotStudents</whaty><whaty>STATUS$|$"
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
		} else {
			throw new NoRightException("您没有给学生分配考场的的权限！");
		}
	}

	public List getBatchEdutypes(String batchId) throws PlatformException,
			NoRightException {
		if (basicManagePriv.getBatchEdutype == 1) {
			OracleRecruitBatchEduMajor batchedu = new OracleRecruitBatchEduMajor();
			return batchedu.getBatchEduTypes(batchId);
		} else {
			throw new NoRightException("您没有浏览招生批次层次的的权限！");
		}
	}

	public List getBatchMajors(String batchId, String edu_type_id)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getBatchEdutype == 1) {
			OracleRecruitBatchEduMajor batchedu = new OracleRecruitBatchEduMajor();
			return batchedu.getBatchMajors(batchId, edu_type_id);
		} else {
			throw new NoRightException("您没有浏览招生计划专业的的权限！");
		}
	}

	public List getBatchMajors1(String batchId, String edu_type_id,
			String site_id) throws PlatformException, NoRightException {
		if (basicManagePriv.getBatchEdutype == 1) {
			OracleRecruitBatchEduMajor batchedu = new OracleRecruitBatchEduMajor();
			return batchedu.getBatchMajors1(batchId, edu_type_id, site_id);
		} else {
			throw new NoRightException("您没有浏览招生计划专业的的权限！");
		}
	}

	public List getBatchEduTypeMajors(String batchId, String edu_type_id)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getBatchEdutype == 1) {
			OracleRecruitBatchEduMajor batchedu = new OracleRecruitBatchEduMajor();
			return batchedu.getBatchEduTypeMajors(batchId, edu_type_id);
		} else {
			throw new NoRightException("您没有浏览招生批次专业的的权限！");
		}
	}

	public List getMajors(String batch_id, String edu_type_id)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getRecruitMajors == 1) {
			OracleRecruitBatchEduMajor edumajor = new OracleRecruitBatchEduMajor();
			return edumajor.getMajors(batch_id, edu_type_id);
		} else {
			throw new NoRightException("您没有获取批次招生专业权限！");
		}
	}

	public List getRecruitNoExamConditions(Page page) throws NoRightException {
		if (basicManagePriv.getRecruitNoExamCondition == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("id"));
			return basicdatalist.getRecruitNoExamConditions(page, null,
					orderProperty);
		} else {
			throw new NoRightException("您没有浏览免试条件的权限！");
		}
	}

	public int updateRecruitPlanBatch(String[] id, String[] num)
			throws PlatformException, NoRightException {
		if (basicManagePriv.updateRecruitPlan == 1) {
			int count = 0;
			for (int i = 0; i < id.length; i++) {
				OracleRecruitPlan plan = new OracleRecruitPlan();
				plan.setId(id[i]);
				RecruitLimit limit = new RecruitLimit();
				int nums = 0;
				try {
					nums = Integer.parseInt(num[i]);
				} catch (NumberFormatException e) {
					nums = 0;
				}
				limit.setRecruitNum(nums);
				plan.setLimit(limit);
				count += plan.updateNum();
			}
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateRecruitPlanBatch</whaty><whaty>STATUS$|$"
									+ count
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return count;
		} else {
			throw new NoRightException("您没有修改招生计划的权限！");
		}
	}

	public List getRecruitSorts() throws PlatformException {

		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("id"));
		return basicdatalist.getSorts(null, null, orderProperty);

	}

	public List getExamroomDisplay(Page page, String siteId, String batchId,
			String sortId) throws PlatformException, NoRightException {
		if (basicManagePriv.getExamroomDisplay == 1) {
			OracleRecruitActivity basicdatalist = new OracleRecruitActivity();
			return basicdatalist.getExamroomDisply(page, siteId, batchId,
					sortId);
		} else {
			throw new NoRightException("您没有查看考场分布的权限！");
		}
	}

	public int getExamroomDisplayNum2(String siteId, String batchId,
			String sortId,String edutype_id) throws PlatformException, NoRightException {
		if (basicManagePriv.getExamroomDisplay == 1) {
			OracleRecruitActivity basicdatalist = new OracleRecruitActivity();
			return basicdatalist.getExamroomDisplyNum2(siteId, batchId, sortId,edutype_id);
		} else {
			throw new NoRightException("您没有查看考场分布的权限！");
		}
	}
	public List getExamroomDisplay2(Page page, String siteId, String batchId,
			String sortId,String edutype_id) throws PlatformException, NoRightException {
		if (basicManagePriv.getExamroomDisplay == 1) {
			OracleRecruitActivity basicdatalist = new OracleRecruitActivity();
			return basicdatalist.getExamroomDisply2(page, siteId, batchId,
					sortId,edutype_id);
		} else {
			throw new NoRightException("您没有查看考场分布的权限！");
		}
	}

	public int getExamroomDisplayNum(String siteId, String batchId,
			String sortId) throws PlatformException, NoRightException {
		if (basicManagePriv.getExamroomDisplay == 1) {
			OracleRecruitActivity basicdatalist = new OracleRecruitActivity();
			return basicdatalist.getExamroomDisplyNum(siteId, batchId, sortId);
		} else {
			throw new NoRightException("您没有查看考场分布的权限！");
		}
	}
	
	public List getExamroomDisplay2(Page page, String siteId, String batchId,
			String sortId) throws PlatformException, NoRightException {
		if (basicManagePriv.getExamroomDisplay == 1) {
			RecruitActivity basicdatalist = new OracleRecruitActivity();
			return basicdatalist.getExamroomDisply2(page, siteId, batchId,
					sortId,null);
		} else {
			throw new NoRightException("您没有查看考场分布的权限！");
		}
	}

	public int getExamroomDisplayNum2(String siteId, String batchId,
			String sortId) throws PlatformException, NoRightException {
		if (basicManagePriv.getExamroomDisplay == 1) {
			RecruitActivity basicdatalist = new OracleRecruitActivity();
			return basicdatalist.getExamroomDisplyNum2(siteId, batchId, sortId,null);
		} else {
			throw new NoRightException("您没有查看考场分布的权限！");
		}
	}

	public List getExamroomDisplay(Page page, String siteId, String batchId,
			String sortId,String edutype_id) throws PlatformException, NoRightException {
		if (basicManagePriv.getExamroomDisplay == 1) {
			OracleRecruitActivity basicdatalist = new OracleRecruitActivity();
			return basicdatalist.getExamroomDisply(page, siteId, batchId,
					sortId,edutype_id);
		} else {
			throw new NoRightException("您没有查看考场分布的权限！");
		}
	}

	public int getExamroomDisplayNum(String siteId, String batchId,
			String sortId,String edutype_id) throws PlatformException, NoRightException {
		if (basicManagePriv.getExamroomDisplay == 1) {
			OracleRecruitActivity basicdatalist = new OracleRecruitActivity();
			return basicdatalist.getExamroomDisplyNum(siteId, batchId, sortId,edutype_id);
		} else {
			throw new NoRightException("您没有查看考场分布的权限！");
		}
	}
	
	public int updateSite(String id, String site) throws PlatformException,
			NoRightException {
		if (basicManagePriv.getRecruitTestRoom == 1) {
			OracleRecruitTestRoom basicdata = new OracleRecruitTestRoom();
			int suc = basicdata.updateSite(id, site);
			UserLog
					.setInfo(
							"<whaty>USERID$|$"
									+ this.basicManagePriv.getManagerId()
									+ "</whaty><whaty>BEHAVIOR$|$updateSite</whaty><whaty>STATUS$|$"
									+ suc
									+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
									+ LogType.SITEMANAGER
									+ "</whaty><whaty>PRIORITY$|$"
									+ LogPriority.INFO + "</whaty>", new Date());
			return suc;
		} else {
			throw new NoRightException("您没有浏览考场分布表的权限！");
		}
	}

	public List getExamroomDisplayInfo(String testroomId)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getExamroomDisplay == 1) {
			OracleRecruitActivity basicdatalist = new OracleRecruitActivity();
			return basicdatalist.getExamroomDisplyInfo(testroomId);
		} else {
			throw new NoRightException("您没有浏览考场分布表的权限！");
		}

	}

	public List getExamroomDisplayInfo1(String testroomId)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getExamroomDisplay == 1) {
			OracleRecruitActivity basicdatalist = new OracleRecruitActivity();
			return basicdatalist.getExamroomDisplyInfo1(testroomId);
		} else {
			throw new NoRightException("您没有浏览考场分布的权限！");
		}

	}

	public List getExamroomDisplayInfo2(String testroomId)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getExamroomDisplay == 1) {
			OracleRecruitActivity basicdatalist = new OracleRecruitActivity();
			return basicdatalist.getExamroomDisplyInfo2(testroomId);
		} else {
			throw new NoRightException("您没有浏览考场分布的权限！");
		}

	}

	public RecruitTestRoom getRecruitTestroom(String testroomId)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getExamroomDisplay == 1) {
			OracleRecruitTestRoom basicdata = new OracleRecruitTestRoom();
			return basicdata.getRecruitTestRoom(testroomId);
		} else {
			throw new NoRightException("您没有浏览考场分布表的权限！");
		}
	}

	public List getTotalRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException, NoRightException {
		if (basicManagePriv.getTotalRecruitStudents == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperties = new ArrayList();
			if (siteId != null && !siteId.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			}
			if (eduTypeId != null && !eduTypeId.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						eduTypeId, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			if (batchId != null && !batchId.equals("")) {
				searchProperties.add(new SearchProperty("batch_id", batchId,
						"="));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardNo != null && !cardNo.equals("")) {
				searchProperties.add(new SearchProperty("card_no", cardNo,
						"like"));
			}
			searchProperties.add(new SearchProperty("status", "0", "<>"));

			List orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("reg_no"));
			return basicdatalist.getTotalStudents(page, searchProperties,
					orderProperty);
		} else {
			throw new NoRightException("您没有查询全部学生的权限！");
		}
	}

	public int getTotalRecruitStudentsNum(String siteId, String batchId,
			String majorId, String eduTypeId, String name, String cardNo)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getTotalRecruitStudents == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperties = new ArrayList();
			if (siteId != null && !siteId.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			}
			if (eduTypeId != null && !eduTypeId.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						eduTypeId, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			if (batchId != null && !batchId.equals("")) {
				searchProperties.add(new SearchProperty("batch_id", batchId,
						"="));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardNo != null && !cardNo.equals("")) {
				searchProperties.add(new SearchProperty("card_no", cardNo,
						"like"));
			}
			searchProperties.add(new SearchProperty("status", "0", "<>"));
			return basicdatalist.getTotalStudentsNum(searchProperties);
		} else {
			throw new NoRightException("您没有查询录取学生的权限！");
		}
	}

	public List getPassRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String status, String pass_status)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getPassRecruitStudents == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperties = new ArrayList();
			if (siteId != null && !siteId.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			}
			if (eduTypeId != null && !eduTypeId.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						eduTypeId, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			if (batchId != null && !batchId.equals("")) {
				searchProperties.add(new SearchProperty("batch_id", batchId,
						"="));
			}

			if (status != null && !status.equals("")) {
				searchProperties.add(new SearchProperty("status", status, "="));
			}

			if (pass_status != null && !pass_status.equals("")) {
				searchProperties.add(new SearchProperty("pass_status",
						pass_status, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}

			if (cardNo != null && !cardNo.equals("")) {
				searchProperties.add(new SearchProperty("card_no", cardNo,
						"like"));
			}

			List orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("reg_no"));
			return basicdatalist.getTotalStudents(page, searchProperties,
					orderProperty);
		} else {
			throw new NoRightException("您没有查询录取学生的权限！");
		}
	}

	public List getUnPassRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String status, String pass_status)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getUnPassRecruitStudents == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperties = new ArrayList();
			if (siteId != null && !siteId.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			}
			if (eduTypeId != null && !eduTypeId.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						eduTypeId, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			if (batchId != null && !batchId.equals("")) {
				searchProperties.add(new SearchProperty("batch_id", batchId,
						"="));
			}

			if (status != null && !status.equals("")) {
				searchProperties.add(new SearchProperty("status", status, "="));
			}

			if (pass_status != null && !pass_status.equals("")) {
				searchProperties.add(new SearchProperty("pass_status",
						pass_status, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}

			if (cardNo != null && !cardNo.equals("")) {
				searchProperties.add(new SearchProperty("card_no", cardNo,
						"like"));
			}

			List orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("reg_no"));
			return basicdatalist.getTotalStudents(page, searchProperties,
					orderProperty);
		} else {
			throw new NoRightException("您没有查询录取学生的权限！");
		}
	}

	public List getUnPassRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo) throws PlatformException {
		if (basicManagePriv.getTotalRecruitStudents == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperties = new ArrayList();
			if (siteId != null && !siteId.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			}
			if (eduTypeId != null && !eduTypeId.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						eduTypeId, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			if (batchId != null && !batchId.equals("")) {
				searchProperties.add(new SearchProperty("batch_id", batchId,
						"="));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardNo != null && !cardNo.equals("")) {
				searchProperties.add(new SearchProperty("card_no", cardNo,
						"like"));
			}
			List orderProperty = new ArrayList();
			orderProperty.add(new OrderProperty("course_name"));
			return basicdatalist.getUnPassStudents(page, searchProperties,
					orderProperty);
		} else {
			throw new PlatformException("您没有查询录取学生的权限！");
		}
	}

	public int getUnPassRecruitStudentsNum(String siteId, String batchId,
			String majorId, String eduTypeId, String name, String cardNo)
			throws PlatformException {
		if (basicManagePriv.getTotalRecruitStudents == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperties = new ArrayList();
			if (siteId != null && !siteId.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			}
			if (eduTypeId != null && !eduTypeId.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						eduTypeId, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			if (batchId != null && !batchId.equals("")) {
				searchProperties.add(new SearchProperty("batch_id", batchId,
						"="));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardNo != null && !cardNo.equals("")) {
				searchProperties.add(new SearchProperty("card_no", cardNo,
						"like"));
			}
			return basicdatalist.getUnPassStudentsNum(searchProperties);
		} else {
			throw new PlatformException("您没有查询录取学生的权限！");
		}
	}

	public int getPassRecruitStudentsNum(String siteId, String batchId,
			String majorId, String eduTypeId, String name, String cardNo,
			String status, String pass_status) throws PlatformException,
			NoRightException {
		if (basicManagePriv.getTotalRecruitStudents == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperties = new ArrayList();
			if (siteId != null && !siteId.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			}
			if (eduTypeId != null && !eduTypeId.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						eduTypeId, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			if (batchId != null && !batchId.equals("")) {
				searchProperties.add(new SearchProperty("batch_id", batchId,
						"="));
			}

			if (status != null && !status.equals("")) {
				searchProperties.add(new SearchProperty("status", status, "="));
			}

			if (pass_status != null && !pass_status.equals("")) {
				searchProperties.add(new SearchProperty("pass_status",
						pass_status, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}

			if (cardNo != null && !cardNo.equals("")) {
				searchProperties.add(new SearchProperty("card_no", cardNo,
						"like"));
			}

			return basicdatalist.getTotalStudentsNum(searchProperties);
		} else {
			throw new NoRightException("您没有查询录取学生的权限！");
		}
	}

	public int getUnPassRecruitStudentsNum(String siteId, String batchId,
			String majorId, String eduTypeId, String name, String cardNo,
			String status, String pass_status) throws PlatformException,
			NoRightException {
		if (basicManagePriv.getTotalRecruitStudents == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperties = new ArrayList();
			if (siteId != null && !siteId.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			}
			if (eduTypeId != null && !eduTypeId.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						eduTypeId, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			if (batchId != null && !batchId.equals("")) {
				searchProperties.add(new SearchProperty("batch_id", batchId,
						"="));
			}

			if (status != null && !status.equals("")) {
				searchProperties.add(new SearchProperty("status", status, "="));
			}

			if (pass_status != null && !pass_status.equals("")) {
				searchProperties.add(new SearchProperty("pass_status",
						pass_status, "="));
			}
			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}

			if (cardNo != null && !cardNo.equals("")) {
				searchProperties.add(new SearchProperty("card_no", cardNo,
						"like"));
			}

			return basicdatalist.getTotalStudentsNum(searchProperties);
		} else {
			throw new NoRightException("您没有查询录取学生的权限！");
		}
	}

	public int getRecruitStudentScoresNum(String siteId, String batchId,
			String majorId, String eduTypeId, String name, String cardNo,
			String signDate, String score_status) throws PlatformException,
			NoRightException {
		if (basicManagePriv.getRecruitScoreStudents == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperties = new ArrayList();
			List orderProperty = new ArrayList();
			if (signDate != null && !signDate.equals("")
					&& signDate.equals("unmatriculate"))
				searchProperties.add(new SearchProperty("status", "1", "="));
			if (siteId != null && !siteId.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			}
			if (eduTypeId != null && !eduTypeId.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						eduTypeId, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			if (batchId != null && !batchId.equals("")) {
				searchProperties.add(new SearchProperty("batch_id", batchId,
						"="));
			}

			if (score_status != null && !score_status.equals("")) {
				searchProperties.add(new SearchProperty("score_status",
						score_status, "="));
			}

			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardNo != null && !cardNo.equals("")) {
				searchProperties.add(new SearchProperty("card_no", cardNo,
						"like"));
			}
			if (signDate != null && !signDate.equals("")) {
				if (signDate.equals("1a"))
					orderProperty.add(new OrderProperty("to_number(score_1)"));
				else if (signDate.equals("1b"))
					orderProperty.add(new OrderProperty("to_number(score_1)",
							"1"));
				else if (signDate.equals("2a"))
					orderProperty.add(new OrderProperty("to_number(score_2)"));
				else if (signDate.equals("2b"))
					orderProperty.add(new OrderProperty("to_number(score_2)",
							"1"));
				else if (signDate.equals("3a"))
					orderProperty.add(new OrderProperty("to_number(score_3)"));
				else if (signDate.equals("3b"))
					orderProperty.add(new OrderProperty("to_number(score_3)",
							"1"));
				else if (signDate.equals("4a"))
					orderProperty.add(new OrderProperty("to_number(score)"));
				else if (signDate.equals("4b"))
					orderProperty
							.add(new OrderProperty("to_number(score)", "1"));
				else if (signDate.equals("5a"))
					orderProperty.add(new OrderProperty(
							"to_number(append_score)"));
				else if (signDate.equals("5b"))
					orderProperty.add(new OrderProperty(
							"to_number(append_score)", "1"));
				else if (signDate.equals("6a"))
					orderProperty.add(new OrderProperty("to_number(score_4)"));
				else if (signDate.equals("6b"))
					orderProperty.add(new OrderProperty("to_number(score_4)",
							"1"));
			}
			orderProperty.add(new OrderProperty("site_id"));
			orderProperty.add(new OrderProperty("major_id"));
			orderProperty.add(new OrderProperty("edutype_id"));
			orderProperty.add(new OrderProperty("name"));
			return basicdatalist.getTestStudentsNum(searchProperties, signDate);
		} else {
			throw new NoRightException("您没有查询成绩的权限！");
		}
	}

	public List getRecruitStudentScores(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String cardNo, String signDate, String score_status)
			throws PlatformException, NoRightException {
		if (basicManagePriv.getRecruitScoreStudents == 1) {
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			List searchProperties = new ArrayList();
			List orderProperty = new ArrayList();
			if (signDate != null && !signDate.equals("")
					&& signDate.equals("unmatriculate"))
				searchProperties.add(new SearchProperty("status", "1", "="));
			if (siteId != null && !siteId.equals("")) {
				searchProperties
						.add(new SearchProperty("site_id", siteId, "="));
			}
			if (eduTypeId != null && !eduTypeId.equals("")) {
				searchProperties.add(new SearchProperty("edutype_id",
						eduTypeId, "="));
			}
			if (majorId != null && !majorId.equals("")) {
				searchProperties.add(new SearchProperty("major_id", majorId,
						"="));
			}
			if (batchId != null && !batchId.equals("")) {
				searchProperties.add(new SearchProperty("batch_id", batchId,
						"="));
			}

			if (score_status != null && !score_status.equals("")) {
				searchProperties.add(new SearchProperty("score_status",
						score_status, "="));
			}

			if (name != null && !name.equals("")) {
				searchProperties.add(new SearchProperty("name", name, "like"));
			}
			if (cardNo != null && !cardNo.equals("")) {
				searchProperties.add(new SearchProperty("card_no", cardNo,
						"like"));
			}
			if (signDate != null && !signDate.equals("")) {
				if (signDate.equals("1a"))
					orderProperty.add(new OrderProperty("to_number(score_1)"));
				else if (signDate.equals("1b"))
					orderProperty.add(new OrderProperty("to_number(score_1)",
							"1"));
				else if (signDate.equals("2a"))
					orderProperty.add(new OrderProperty("to_number(score_2)"));
				else if (signDate.equals("2b"))
					orderProperty.add(new OrderProperty("to_number(score_2)",
							"1"));
				else if (signDate.equals("3a"))
					orderProperty.add(new OrderProperty("to_number(score_3)"));
				else if (signDate.equals("3b"))
					orderProperty.add(new OrderProperty("to_number(score_3)",
							"1"));
				else if (signDate.equals("4a"))
					orderProperty.add(new OrderProperty("to_number(score)"));
				else if (signDate.equals("4b"))
					orderProperty
							.add(new OrderProperty("to_number(score)", "1"));
				else if (signDate.equals("5a"))
					orderProperty.add(new OrderProperty(
							"to_number(append_score)"));
				else if (signDate.equals("5b"))
					orderProperty.add(new OrderProperty(
							"to_number(append_score)", "1"));
				else if (signDate.equals("6a"))
					orderProperty.add(new OrderProperty("to_number(score_4)"));
				else if (signDate.equals("6b"))
					orderProperty.add(new OrderProperty("to_number(score_4)",
							"1"));
			}
			orderProperty.add(new OrderProperty("site_id"));
			orderProperty.add(new OrderProperty("major_id"));
			orderProperty.add(new OrderProperty("edutype_id"));
			orderProperty.add(new OrderProperty("name"));

			return basicdatalist.getTestStudents(page, searchProperties,
					orderProperty, signDate);
		} else {
			throw new NoRightException("您没有查询成绩的权限！");
		}
	}

	public List getRejectStudentPhotos(Page page, String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status) throws NoRightException {
		if (basicManagePriv.checkImage == 1) {
			List searchProperty = new ArrayList();
			List orderProperty = null;
			if (status != null && !status.equals("null") && !status.equals("")) {
				searchProperty.add(new SearchProperty("status", status, "="));
			}
			if (site_id != null && !site_id.equals("null")
					&& !site_id.equals("")) {
				searchProperty.add(new SearchProperty("site_id", site_id, "="));
			}
			if (batchId != null && !batchId.equals("null")
					&& !batchId.equals("")) {
				searchProperty
						.add(new SearchProperty("batch_id", batchId, "="));
			}
			if (name != null && !name.equals("null") && !name.equals("")) {
				searchProperty.add(new SearchProperty("name", name, "like"));
			}
			if (card_no != null && !card_no.equals("null")
					&& !card_no.equals("")) {
				searchProperty.add(new SearchProperty("card_no", card_no,
						"like"));
			}
			if (zglx != null && !zglx.equals("null") && !zglx.equals("")) {
				searchProperty
						.add(new SearchProperty("considertype", zglx, "="));
			}
			if (edu_type_id != null && !edu_type_id.equals("null")
					&& !edu_type_id.equals("")) {
				searchProperty.add(new SearchProperty("edutype_id",
						edu_type_id, "="));
			}
			if (major_id != null && !major_id.equals("null")
					&& !major_id.equals("")) {
				searchProperty
						.add(new SearchProperty("major_id", major_id, "="));
			}
			OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
			return basicdatalist.getRejectStudentPhotos(page, searchProperty,
					orderProperty);
		} else {
			throw new NoRightException("您没有审核照片的权限！");
		}
	}

	public int getRejectStudentPhotosNum(String batchId, String name,
			String card_no, String zglx, String edu_type_id, String major_id,
			String site_id, String status) {
		List searchProperty = new ArrayList();
		if (status != null && !status.equals("null") && !status.equals("")) {
			searchProperty.add(new SearchProperty("status", status, "="));
		}
		if (site_id != null && !site_id.equals("null") && !site_id.equals("")) {
			searchProperty.add(new SearchProperty("site_id", site_id, "="));
		}
		if (batchId != null && !batchId.equals("null") && !batchId.equals("")) {
			searchProperty.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (name != null && !name.equals("null") && !name.equals("")) {
			searchProperty.add(new SearchProperty("name", name, "like"));
		}
		if (card_no != null && !card_no.equals("null") && !card_no.equals("")) {
			searchProperty.add(new SearchProperty("card_no", card_no, "like"));
		}
		if (zglx != null && !zglx.equals("null") && !zglx.equals("")) {
			searchProperty.add(new SearchProperty("considertype", zglx, "="));
		}
		if (edu_type_id != null && !edu_type_id.equals("null")
				&& !edu_type_id.equals("")) {
			searchProperty.add(new SearchProperty("edutype_id", edu_type_id,
					"="));
		}
		if (major_id != null && !major_id.equals("null")
				&& !major_id.equals("")) {
			searchProperty.add(new SearchProperty("major_id", major_id, "="));
		}
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		return basicdatalist.getRejectStudentPhotosNum(searchProperty);
	}

	public RecruitBatch getRecruitBatch(String aid) throws NoRightException {
		if (basicManagePriv.getRecruitBatch == 1) {
			return new OracleRecruitBatch(aid);
		} else {
			throw new NoRightException("您没有查看招生批次信息的权限！");
		}
	}

	public List getRecruitBatches(Page page) throws PlatformException {
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("id", OrderProperty.DESC));
		return basicdatalist.getBatchs(page, null, orderProperty);
	}

	public int getUnRegisterPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String regNo) throws PlatformException {
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		List searchProperties = new ArrayList();
		if (siteId != null && !siteId.equals("")) {
			searchProperties.add(new SearchProperty("site_id", siteId, "="));
		}
		if (eduTypeId != null && !eduTypeId.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", eduTypeId,
					"="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperties.add(new SearchProperty("major_id", majorId, "="));
		}
		if (batchId != null && !batchId.equals("")) {
			searchProperties.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (regNo != null && !regNo.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", regNo, "like"));
		}
		
		searchProperties.add(new SearchProperty("register_status", "0", "="));
		searchProperties.add(new SearchProperty("REGNO_STATUS", "1", "="));
		return basicdatalist.getPassStudentsNum(searchProperties);
	}
	public int getUnRegisterPassRecruitStudentsNum(String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String regNo,String gender) throws PlatformException {
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		List searchProperties = new ArrayList();
		if (siteId != null && !siteId.equals("")) {
			searchProperties.add(new SearchProperty("site_id", siteId, "="));
		}
		if (eduTypeId != null && !eduTypeId.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", eduTypeId,
					"="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperties.add(new SearchProperty("major_id", majorId, "="));
		}
		if (batchId != null && !batchId.equals("")) {
			searchProperties.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (regNo != null && !regNo.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", regNo, "like"));
		}
		if (gender != null && !gender.equals("")) {
			searchProperties.add(new SearchProperty("gender", gender, "like"));
		}
		searchProperties.add(new SearchProperty("register_status", "0", "="));
		searchProperties.add(new SearchProperty("REGNO_STATUS", "1", "="));
		return basicdatalist.getPassStudentsNum(searchProperties);
	}

	public List getUnRegisterPassRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String regNo) throws PlatformException, PlatformException {
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		List searchProperties = new ArrayList();
		if (siteId != null && !siteId.equals("")) {
			searchProperties.add(new SearchProperty("site_id", siteId, "="));
		}
		if (eduTypeId != null && !eduTypeId.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", eduTypeId,
					"="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperties.add(new SearchProperty("major_id", majorId, "="));
		}
		if (batchId != null && !batchId.equals("")) {
			searchProperties.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (regNo != null && !regNo.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", regNo, "like"));
		}
		searchProperties.add(new SearchProperty("register_status", "0", "="));
		searchProperties.add(new SearchProperty("REGNO_STATUS", "1", "="));

		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("site_id"));
		orderProperty.add(new OrderProperty("major_id"));
		orderProperty.add(new OrderProperty("edutype_id"));
		orderProperty.add(new OrderProperty("reg_no"));
		return basicdatalist.getPassStudents(page, searchProperties,
				orderProperty);
	}
	public List getUnRegisterPassRecruitStudents(Page page, String siteId,
			String batchId, String majorId, String eduTypeId, String name,
			String regNo,String gender) throws PlatformException, PlatformException {
		OracleBasicRecruitList basicdatalist = new OracleBasicRecruitList();
		List searchProperties = new ArrayList();
		if (siteId != null && !siteId.equals("")) {
			searchProperties.add(new SearchProperty("site_id", siteId, "="));
		}
		if (eduTypeId != null && !eduTypeId.equals("")) {
			searchProperties.add(new SearchProperty("edutype_id", eduTypeId,
					"="));
		}
		if (majorId != null && !majorId.equals("")) {
			searchProperties.add(new SearchProperty("major_id", majorId, "="));
		}
		if (batchId != null && !batchId.equals("")) {
			searchProperties.add(new SearchProperty("batch_id", batchId, "="));
		}
		if (name != null && !name.equals("")) {
			searchProperties.add(new SearchProperty("name", name, "like"));
		}
		if (regNo != null && !regNo.equals("")) {
			searchProperties.add(new SearchProperty("reg_no", regNo, "like"));
		}
		if (gender != null && !gender.equals("")) {
			searchProperties.add(new SearchProperty("gender", gender, "like"));
		}
		searchProperties.add(new SearchProperty("register_status", "0", "="));
		searchProperties.add(new SearchProperty("REGNO_STATUS", "1", "="));

		List orderProperty = new ArrayList();
		orderProperty.add(new OrderProperty("site_id"));
		orderProperty.add(new OrderProperty("major_id"));
		orderProperty.add(new OrderProperty("edutype_id"));
		orderProperty.add(new OrderProperty("reg_no"));
		return basicdatalist.getPassStudents(page, searchProperties,
				orderProperty);
	}

	public RecruitTestStudent getTestStudent(String aid)
			throws PlatformException {
		return new OracleRecruitTestStudent(aid);
	}
}

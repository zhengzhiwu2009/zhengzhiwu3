package com.whaty.platform.entity.web.action.teaching.elective;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.teaching.elective.PrBzzTchSelectCoursesSerivce;
import com.whaty.platform.entity.web.action.MyBaseAction;

import com.whaty.platform.util.JsonUtil;

public class PrBzzTchSelectCoursesAction extends
		MyBaseAction<PrBzzTchOpencourse> {

	private String id;
	private String message;
	private PrBzzTchSelectCoursesSerivce prBzzTchSelectCoursesSerivce;
	private String returnUrl;
	private String status;

	private String opTag; // 操作标志，选课/删除选课

	private int persons;
	private int courses;

	public PrBzzTchSelectCoursesSerivce getPrBzzTchSelectCoursesSerivce() {
		return prBzzTchSelectCoursesSerivce;
	}

	public void setPrBzzTchSelectCoursesSerivce(
			PrBzzTchSelectCoursesSerivce prBzzTchSelectCoursesSerivce) {
		this.prBzzTchSelectCoursesSerivce = prBzzTchSelectCoursesSerivce;
	}

	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false);

		this.getGridConfig().setTitle("课程列表");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("课程名称"),
				"peBzzTchCourse.name");
		this.getGridConfig().addColumn(this.getText("课程编号"),
				"peBzzTchCourse.code");
		this.getGridConfig().addColumn(this.getText("学时"),
				"peBzzTchCourse.time");
		this.getGridConfig().addColumn(this.getText("教师"),
				"peBzzTchCourse.teacher");
		// this.getGridConfig().addColumn(this.getText("评估结束时间"),"evalEndDate");

		// this.getGridConfig().addColumn(this.getText("评估结束时间"),"peBzzBatch.id",false,false,false,
		// "");

		if(this.getOpTag()!= null && this.getOpTag().equals("delete")) {
			this.getGridConfig().addMenuScript(this.getText("上一步"),"{history.go(-2)}");
			
			this.getGridConfig().addMenuFunction(this.getText("下一步"),
					"/entity/teaching/prBzzTchSelectCourses_delSelectCourse.action", false, false);
			
		} else {
			this.getGridConfig().addMenuFunction(this.getText("下一步"),
					"/entity/teaching/prBzzTchSelectStudent.action?batchId="+id, false, false);
	
			this.getGridConfig().addMenuScript(this.getText("返回"),
					"{window.location.href='/entity/teaching/prBzzTchOpenCourse.action?backParam=true'}");
		}
		this.getGridConfig()
				.addMenuScript(this.getText("取消"),
						"{window.navigate('/entity/teaching/prBzzTchOpenCourse.action?backParam=true');}");

	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		if (this.getId() == null || this.getId().equals("")) {
			this.id = (String) ServletActionContext.getRequest().getSession()
					.getAttribute("PrBzzTchBatchId");
		}
		DetachedCriteria dc = DetachedCriteria
				.forClass(PrBzzTchOpencourse.class);
		dc.createCriteria("peBzzTchCourse", "peBzzTchCourse");
		dc.createCriteria("peBzzBatch", "peBzzBatch");

		dc.add(Restrictions.eq("peBzzBatch.id", this.getId()));

		if (this.getOpTag() != null && this.getOpTag().equals("delete")) {
			this.setIds((String)ServletActionContext.getRequest().getSession().getAttribute(
					"PrBzzTchStudentIds"));
			DetachedCriteria tdc = DetachedCriteria
					.forClass(PeBzzStudent.class);
			tdc.createCriteria("ssoUser");
			tdc.setProjection(Property.forName("ssoUser.id"));
			List list = Arrays.asList(this.getIds().split(","));
			tdc.add(Restrictions.in("id", list));

			try {
				list = this.getGeneralService().getList(tdc);

				tdc = DetachedCriteria.forClass(TrainingCourseStudent.class);
				tdc.createCriteria("ssoUser");
				tdc.createCriteria("prBzzTchOpencourse");
				tdc.setProjection(Property.forName("prBzzTchOpencourse.id"));
				// tdc.setProjection(Projections.distinct(Projections.property("ssoUser.id")));
				tdc.add(Restrictions.in("ssoUser.id", list));

				list = this.getGeneralService().getList(tdc);

				if (list != null && list.size() != 0) {
					dc.add(Restrictions.in("id", list));
				} else {
					dc.add(Expression.sql("1=2"));
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}

		return dc;
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PrBzzTchOpencourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/prBzzTchSelectCourses";
	}

	/**
	 * action 批量选课
	 * 
	 * @return
	 */
	public String SelectCourses() {
		try {
			if (status != null && status.equals("returnurl")) {
				message = "/entity/teaching/prBzzTchSelectCourses_SelectCourses.action?backParam=true";
				returnUrl = "ajaxsubmit";
				return returnUrl;
			} else if (status != null && status.equals("submit")) {
				int num = 1;
				Map map = new HashMap();
				// 当前学期下的所有已开的课程
				DetachedCriteria courseDc = DetachedCriteria
						.forClass(PrBzzTchOpencourse.class);
				courseDc.setProjection(Property.forName("id"));
				courseDc.add(Restrictions.eq("peBzzBatch.id", id));
				List<String> courseIdList = this.getGeneralService().getList(
						courseDc);
				int n = 0;
				// 选课
				for (String cid : courseIdList) {

					String insertTrainingSql = "insert into training_course_student \n"
							+ "--=本学期下没选该课的学生 \n"
							+ "  select to_char(s_training_id.nextval), \n"
							+ "         b.student_id, \n"
							+ "         '"
							+ cid
							+ "', \n"
							+ "         '', \n"
							+ "         '', \n"
							+ "         '0.00', \n"
							+ "         'NOTATTEMPTED', \n"
							+ "         '' \n"
							+ "    from ( \n"
							+ "          --本学期下所有学生 \n"
							+ "          select s.fk_sso_user_id as student_id \n"
							+ "            from pe_bzz_student s \n"
							+ "           where s.fk_batch_id = '"
							+ this.getId()
							+ "' \n"
							+ "          minus \n"
							+ "          --本学期下已选该课的学生 \n"
							+ "          select cs.student_id \n"
							+ "            from training_course_student cs \n"
							+ "           where cs.course_id = '"
							+ cid
							+ "') b";
					n += this.getGeneralService().executeBySQL(
							insertTrainingSql);
					// System.out.println(insertTrainingSql);
					// System.out.println(n);
				}

				String insertElectiveSql = "insert into pr_bzz_tch_stu_elective \n"
						+ "--查出所有新增（上面接入）的记录 \n"
						+ "  select to_char(s_training_id.nextval), bs.id as stu_id, d.course_id,'','','',sysdate,null,'',d.id as training_id \n"
						+ "    from ( \n"
						+ "         --本学期下所有在training_course_student下的课程 \n"
						+ "         select cs.id \n"
						+ "            from training_course_student cs, pr_bzz_tch_opencourse tc \n"
						+ "           where tc.id = cs.course_id \n"
						+ "             and tc.fk_batch_id = '"
						+ this.getId()
						+ "' \n"
						+ "          minus \n"
						+ "          --所有在选课表下的课程 \n"
						+ "          select e.fk_training_id as id \n"
						+ "            from pr_bzz_tch_stu_elective e, pr_bzz_tch_opencourse tc \n"
						+ "           where tc.id = e.fk_tch_opencourse_id \n"
						+ "             and tc.fk_batch_id = '"
						+ this.getId()
						+ "') cc, \n"
						+ "         training_course_student d, \n"
						+ "         pe_bzz_student bs \n"
						+ "   where cc.id = d.id \n"
						+ "     and bs.fk_sso_user_id = d.student_id";
				int m = this.getGeneralService()
						.executeBySQL(insertElectiveSql);
				// System.out.println(m + ":" + n);

				map.put("success", "true");
				map.put("msg", "开课成功");
				this.setJsonString(JsonUtil.toJSONString(map));
				return json();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * action 单个选课选择课程
	 * 
	 * @return
	 */
	public String singleSelectCourses() {
		ServletActionContext.getRequest().getSession().setAttribute(
				"PrBzzTchBatchId", this.getId());
		return super.execute();
	}

	/**
	 * 单个选课
	 * 
	 * @return
	 */
	public String startSingleSelectCourses() {

		HttpSession session = ServletActionContext.getRequest().getSession();

		String batchId = (String) session.getAttribute("PrBzzTchBatchId");
		String courseIdsStr = (String) session
				.getAttribute("PrBzzTchCourseIds");

		List<String> courseIds = Arrays.asList(courseIdsStr.split(","));
		String stuIds = processIds(this.getIds());
		try {
			int n = 0;
			for (String cid : courseIds) {
				String insertTrainingSql = "insert into training_course_student \n"
						+ "--=本学期下没选该课的学生 \n"
						+ "  select to_char(s_training_id.nextval), \n"
						+ "         b.student_id, \n" + "'"
						+ cid
						+ "', \n"
						+ "         '', \n"
						+ "         '', \n"
						+ "         '0.00', \n"
						+ "         'NOTATTEMPTED', \n"
						+ "         '' \n"
						+ "    from ( \n"
						+ "          --本学期下所选学生 \n"
						+ "          select s.fk_sso_user_id as student_id \n"
						+ "            from pe_bzz_student s \n"
						+ "           where s.fk_batch_id = '"
						+ batchId
						+ "' and s.id in "
						+ stuIds
						+ " \n"
						+ "          minus \n"
						+ "          --本学期下已选该课的学生select cs.student_id \n"
						+ "          select cs.student_id \n"
						+ "            from training_course_student cs,pe_bzz_student ps \n"
						+ "           where cs.course_id = '" + cid
						+ "' and cs.student_id=ps.fk_sso_user_id \n"
						+ "                 and ps.id in " + stuIds
						+ " \n"
						+ "          ) b";
				n += this.getGeneralService().executeBySQL(insertTrainingSql);
				// System.out.println(insertTrainingSql);
			}

			String insertElectiveSql = "insert into pr_bzz_tch_stu_elective \n"
					+ "--查出所有新增（上面接入）的记录 \n"
					+ "  select to_char(s_training_id.nextval), bs.id as stu_id, d.course_id,'','','',sysdate,null,'',d.id as training_id \n"
					+ "    from ( \n"
					+ "         --本学期下所有在training_course_student下的课程 \n"
					+ "         select cs.id \n"
					+ "            from training_course_student cs, pr_bzz_tch_opencourse tc \n"
					+ "           where tc.id = cs.course_id \n"
					+ "             and tc.fk_batch_id = '"
					+ batchId
					+ "' \n"
					+ "          minus \n"
					+ "          --所有在选课表下的课程 \n"
					+ "          select e.fk_training_id as id \n"
					+ "            from pr_bzz_tch_stu_elective e, pr_bzz_tch_opencourse tc \n"
					+ "           where tc.id = e.fk_tch_opencourse_id \n"
					+ "             and tc.fk_batch_id = '" + batchId
					+ "') cc, \n" + "         training_course_student d, \n"
					+ "         pe_bzz_student bs \n"
					+ "   where cc.id = d.id \n"
					+ "     and bs.fk_sso_user_id = d.student_id";
			int m = this.getGeneralService().executeBySQL(insertElectiveSql);
			// System.out.println(m + ":" + n);
			this.setPersons(Arrays.asList(this.getIds().split(",")).size());
			this.setCourses(courseIds.size());
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return "singleSelCourseSuccess";
	}

	/**
	 * Action 为删除选课，选择要删除的课程
	 * 
	 * @return
	 */
	public String selectCourseForDel() {

		ServletActionContext.getRequest().getSession().setAttribute(
				"PrBzzTchStudentIds", this.getIds());
		return super.execute();
	}
	
	/**
	 * Action 删除选课
	 * @return
	 */
	public String delSelectCourse() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		String batchId = (String) session.getAttribute("PrBzzTchBatchId");
		String stuIds = this.processIds((String) session.getAttribute("PrBzzTchStudentIds"));
		String courseIds = this.getIds();
		List<String> courseIdList = Arrays.asList(courseIds.split(","));
		try {
			int n = 0;
			for(String cid : courseIdList) {
				//已选该课的学生(学生信息包括学期) pr_bzz_tch_stu_elective
				String delSql = "delete from pr_bzz_tch_stu_elective se \n"
						+ "where  se.fk_tch_opencourse_id='" + cid + "' \n"
						+ "      and se.fk_stu_id in " + stuIds;
//				System.out.println(delSql);
				
				n += this.getGeneralService().executeBySQL(delSql);
				
			}
			//--已从elective表中删除的学生(学生信息包括学期) training_course_student
			String delSql = "delete from training_course_student \n"
					+ "where id in( \n"
					+ "select cs.id from training_course_student cs, pr_bzz_tch_opencourse oc \n"
					+ "where cs.course_id=oc.id \n"
					+ "      and oc.fk_batch_id='" + batchId + "' \n"
					+ "minus \n"      
					+ "select se.fk_training_id from pr_bzz_tch_stu_elective se,pr_bzz_tch_opencourse oc \n"
					+ "where se.fk_tch_opencourse_id=oc.id \n"
					+ "      and oc.fk_batch_id='" + batchId + "' \n"
					+ ")";
//			System.out.println(delSql);
			int m = this.getGeneralService().executeBySQL(delSql);
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.courses = courseIdList.size();
		this.persons = stuIds.split(",").length;
		
		return "delSelectCourseSuccess";
	}

	private String processIds(String ids) {

		ids = ids.replace(",", "','");

		if (ids.endsWith(",'")) {
			ids = ids.substring(0, ids.length() - 2);
		}

		ids = "('" + ids + ")";

		return ids;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPersons() {
		return persons;
	}

	public void setPersons(int persons) {
		this.persons = persons;
	}

	public int getCourses() {
		return courses;
	}

	public void setCourses(int courses) {
		this.courses = courses;
	}

	public String getOpTag() {
		return opTag;
	}

	public void setOpTag(String opTag) {
		this.opTag = opTag;
	}
}

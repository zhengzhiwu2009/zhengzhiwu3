package com.whaty.platform.entity.web.action.basic;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.service.basic.PeDetailService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeBatchDetailEnterAction extends MyBaseAction<StudentBatch> {

	private PeDetailService peDetailService;

	private String id;
	private String method;
	private String batchid;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setCapability(false, false, false);
		Set capabilitySet = us.getUserPriority().keySet();
		this.getGridConfig().setTitle(this.getText("学员列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		if ("del".equalsIgnoreCase(method) || "list".equalsIgnoreCase(method))
			this.getGridConfig().addColumn(this.getText("学员ID"), "STU_ID", false, false, false, "textField");
		this.getGridConfig().addColumn(this.getText("系统编号"), "regno", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("姓名"), "truename");
		this.getGridConfig().addColumn(this.getText("具有从业资格"), "enumConstByIsEmployee.name", true);
		ColumnConfig cczige = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name", true, false, true, "TextField", false, 100, "");
		String sqlzige = "SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagQualificationsType' and t.code not in('9','90','91')";
		cczige.setComboSQL(sqlzige);
		this.getGridConfig().addColumn(cczige);

		this.getGridConfig().addColumn(this.getText("从事业务"), "work", true, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("职务"), "position", true, false, true, "textField");

		if ("del".equalsIgnoreCase(method) || "list".equalsIgnoreCase(method))
			this.getGridConfig().addColumn(this.getText("预计学时数"), "estimatetime", false, false, true, null);
		this.getGridConfig().addColumn(this.getText("所在机构"), "pename", true);
		this.getGridConfig().addColumn(this.getText("是否有效"), "ssoUser.enumConstByFlagIsvalid.name");
		this.getGridConfig().addColumn(this.getText("国籍"), "cardtype");
		this.getGridConfig().addColumn(this.getText("证件号码"), "cardno");
		this.getGridConfig().addColumn(this.getText("组别"), "groups");
		if ("del".equalsIgnoreCase(method) || "list".equalsIgnoreCase(method)) {
			this.getGridConfig().addColumn(this.getText("专项ID"), "BATCH_ID", false, false, false, "");
			ColumnConfig cczhifu = new ColumnConfig(this.getText("是否支付"), "enumConstByFlagBatchPayState.name", true, false, true, "TextField", false, 100, "");
			String sqlzhifu = "SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagBatchPayState' and t.code not in('2') UNION SELECT * FROM ENUM_CONST WHERE NAMESPACE = 'FLagJoinState'";
			cczhifu.setComboSQL(sqlzhifu);
			this.getGridConfig().addColumn(cczhifu);
		}
		if ("add".equalsIgnoreCase(method))
			this.getGridConfig().addMenuFunction(this.getText("添加学员"), "StuAdd_" + this.getBatchid());
		if ("del".equalsIgnoreCase(method)) {
			this.getGridConfig().addMenuFunction(this.getText("删除学员"), "StuDel_" + this.getBatchid());
			// 直播价格 如果免费则走免费报名方式
			String livep = ServletActionContext.getRequest().getParameter("livep");
			double livePirce = 0;
			if (null != livep && !"".equals(livep))
				livePirce = Double.parseDouble(livep);
			if (null != livep && livePirce > 0) {// 收费直播
				this.getGridConfig()
						.addMenuScript(
								this.getText("为选中学员报名"),
								"{var m = grid.getSelections();  "
										+ "if(m.length > 0){	         "
										+ " Ext.MessageBox.alert('提示','报名后请提醒学员准时学习！',function(){ "
										+ "	var jsonData = '';       "
										+ "	var bId = '';       "
										+ "	for(var i = 0, len = m.length; i < len; i++){"
										+ "		var ss =  m[i].get('STU_ID');"
										+ "		var bId =  m[i].get('BATCH_ID');"
										+ "		if(i==0)	jsonData = jsonData + ss ;"
										+ "		else	jsonData = jsonData + ',' + ss;"
										+ "	}                        "
										+ "	document.getElementById('user-defined-content').style.display='none'; "
										+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/paymentLive_toConfirmStu.action' method='post' name='formx1' style='display:none'><input type=hidden name=ids value='\"+jsonData+\"' ><input type=hidden name=id value='\"+bId+\"' ></form>\";"
										+ "	document.formx1.submit();" + "	document.getElementById('user-defined-content').innerHTML=\"\";" + "});} else {                    "
										+ "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}} ");
			} else {// 免费直播
				this.getGridConfig()
						.addMenuScript(
								this.getText("为选中学员报名"),
								"{var m = grid.getSelections();  "
										+ "if(m.length > 0){	         "
										+ " Ext.MessageBox.alert('提示','报名后请提醒学员准时学习！',function(){ "
										+ "	var jsonData = '';       "
										+ "	var bId = '';       "
										+ "	for(var i = 0, len = m.length; i < len; i++){"
										+ "		var ss =  m[i].get('STU_ID');"
										+ "		var bId =  m[i].get('BATCH_ID');"
										+ "		if(i==0)	jsonData = jsonData + ss ;"
										+ "		else	jsonData = jsonData + ',' + ss;"
										+ "	}                        "
										+ "	document.getElementById('user-defined-content').style.display='none'; "
										+ "	document.getElementById('user-defined-content').innerHTML=\"<form action='/entity/basic/paymentLive_toConfirmStuFree.action' method='post' name='formx1' style='display:none'><input type=hidden name=ids value='\"+jsonData+\"' ><input type=hidden name=id value='\"+bId+\"' ></form>\";"
										+ "	document.formx1.submit();" + "	document.getElementById('user-defined-content').innerHTML=\"\";" + "});} else {                    "
										+ "Ext.MessageBox.alert('错误', '请至少选择一条数据');  " + "}} ");
			}
		}
		if ("list".equalsIgnoreCase(method))
			this.getGridConfig().addMenuScript(this.getText("返回"), "{location.href='/entity/teaching/peBzzCourseLiveSearch.action'};");
		else
			this.getGridConfig().addMenuScript(this.getText("返回"), "{location.href='/entity/basic/paymentLive.action'};");
		ActionContext.getContext().getSession().put("id", id);
	}

	public void setEntityClass() {
		this.entityClass = StudentBatch.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peBatchDetailEnter";
	}

	public Map updateColumn() {
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();
		String id;
		String ap[] = action.split("_");
		id = ap[1];
		Date dt = new Date();
		if (action.indexOf("StuAdd") >= 0) {
			try {
				// 直播收费还是免费
				String liveSqlString = "SELECT 1 FROM PE_BZZ_TCH_COURSE WHERE ID IN (SELECT FK_COURSE_ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_BATCH_ID = '" + id
						+ "') AND PRICE > 0";
				List liveList = this.getGeneralService().getBySQL(liveSqlString);
				String sql1 = "";
				if (null != liveList && liveList.size() > 0) {// 收费直播
					// 已报人数
					sql1 = "SELECT NVL(COUNT(PBTSE.ID),0) FROM PR_BZZ_TCH_OPENCOURSE PBTO INNER JOIN PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSE ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID AND PBTO.FK_BATCH_ID = '"
							+ id + "' AND PBTSE.FK_ORDER_ID IS NOT NULL GROUP BY PBTO.FK_BATCH_ID";
				} else {// 免费直播
					sql1 = "SELECT NVL(COUNT(ID),0) FROM STU_BATCH WHERE BATCH_ID = '" + id + "'";
				}
				Object nowStudents = 0;
				List list1 = this.getGeneralService().getBySQL(sql1);
				if (null != list1 && list1.size() > 0)
					nowStudents = list1.get(0);
				// 允许人数
				sql1 = "SELECT NVL(PBTC.MAXSTUS,0) FROM PE_BZZ_TCH_COURSE PBTC,PR_BZZ_TCH_OPENCOURSE PBTO,PE_BZZ_BATCH PBB WHERE PBB.ID = PBTO.FK_BATCH_ID AND PBB.ID = '" + id
						+ "' AND PBTO.FK_COURSE_ID = PBTC.ID";
				Object maxStudents = this.getGeneralService().getBySQL(sql1).get(0);
				BigDecimal b1 = new BigDecimal(nowStudents.toString());
				BigDecimal b2 = new BigDecimal(maxStudents.toString());
				if (b1.subtract(b2).doubleValue() + this.getIds().split(",").length > 0) {
					map.clear();
					map.put("success", "false");
					map.put("info", "报名人数上限为：" + maxStudents + "，还可以添加" + (b2.subtract(b1).compareTo(BigDecimal.ZERO) == -1 ? 0 : b2.subtract(b1)) + "名学员，请重新选择！");
					return map;
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "添加失败：" + e.getMessage() + "，请重新选择学员！");
				return map;
			}
		}
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			try {
				DetachedCriteria rodc = DetachedCriteria.forClass(PeBzzBatch.class);
				rodc.add(Restrictions.eq("id", id));
				List<PeBzzBatch> peBzzBatchlist = this.getGeneralService().getList(rodc);
				DetachedCriteria peprdc = DetachedCriteria.forClass(PeBzzStudent.class);
				peprdc.add(Restrictions.in("id", ids));
				List<PeBzzStudent> studentlist = this.getGeneralService().getList(peprdc);
				PeBzzStudent peBzzStudent;
				if (studentlist.size() > 0) {
					EnumConst enumConstByFlagBatchPayState = this.getMyListService().getEnumConstByNamespaceCode("FlagBatchPayState", "0");
					DetachedCriteria opdc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
					opdc.createCriteria("enumConstByFlagChoose", "enumConstByFlagChoose", DetachedCriteria.LEFT_JOIN);
					opdc.add(Restrictions.eq("peBzzBatch.id", id));
					opdc.add(Restrictions.eq("enumConstByFlagChoose.code", "1"));
					List<PrBzzTchOpencourse> oplist = this.getGeneralService().getList(opdc);
					List eleBacklist = new ArrayList();
					List<StudentBatch> stuBatchList = new ArrayList<StudentBatch>();
					if (action.indexOf("StuAdd") >= 0) {
						if (peBzzBatchlist.get(0).getEndDate().before(new Date())) {
							map.put("success", "false");
							map.put("info", "报名时间已结束,不能报名！");
							return map;
						}
						for (int n = 0; n < studentlist.size(); n++) {
							peBzzStudent = studentlist.get(n);
							StudentBatch studentBatch = new StudentBatch();
							studentBatch.setPeStudent(peBzzStudent);
							studentBatch.setPeBzzBatch(peBzzBatchlist.get(0));
							studentBatch.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);
							// this.getGeneralService().save(studentBatch);
							stuBatchList.add(studentBatch);
							for (PrBzzTchOpencourse opencourse : oplist) {
								/*
								 * TrainingCourseStudent trainingCourseStudent =
								 * new TrainingCourseStudent();
								 * trainingCourseStudent
								 * .setPrBzzTchOpencourse(opencourse);
								 * trainingCourseStudent
								 * .setSsoUser(peBzzStudent.getSsoUser());
								 * trainingCourseStudent.setPercent(0.00);
								 * trainingCourseStudent
								 * .setLearnStatus(StudyProgress.UNCOMPLETE);
								 */
								DetachedCriteria dceletived = DetachedCriteria.forClass(PrBzzTchStuElective.class);
								dceletived.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
								dceletived.createAlias("peBzzStudent", "peBzzStudent");
								dceletived.add(Restrictions.eq("peBzzStudent", peBzzStudent));
								// Lee 注释↓查询选课记录方式：因为公共课程课程对应多个开课ID所以修改查询方式
								// dceletived.add(Restrictions.eq("prBzzTchOpencourse",
								// opencourse));
								// Lee↓新查询选课方式
								dceletived.add(Restrictions.eq("peBzzTchCourse.id", opencourse.getPeBzzTchCourse().getId()));
								DetachedCriteria dceletiveback = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
								dceletiveback.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
								dceletiveback.createAlias("peBzzStudent", "peBzzStudent");
								dceletiveback.add(Restrictions.eq("peBzzStudent", peBzzStudent));
								// Lee 注释↓查询选课记录方式：因为公共课程课程对应多个开课ID所以修改查询方式
								// dceletiveback.add(Restrictions.eq("prBzzTchOpencourse",
								// opencourse));
								// Lee↓新查询选课方式
								dceletiveback.add(Restrictions.eq("peBzzTchCourse.id", opencourse.getPeBzzTchCourse().getId()));
								try {
									List tempList = this.getGeneralService().getList(dceletived);
									List tempBackList = this.getGeneralService().getList(dceletiveback);
									if ((tempList != null && tempList.size() > 0) || (tempBackList != null && tempBackList.size() > 0)) {
										map.put("success", "false");
										map.put("info", "学员：" + peBzzStudent.getRegNo() + "已选课程-" + opencourse.getPeBzzTchCourse().getCode());
										return map;
									}
								} catch (EntityException e) {
									e.printStackTrace();
									map.put("success", "false");
									map.put("info", "选课验证失败！");
									return map;
								}
								PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
								eleBack.setPeBzzStudent(peBzzStudent);
								eleBack.setPrBzzTchOpencourse(opencourse);
								// stuEle.setTrainingCourseStudent(trainingCourseStudent);
								eleBacklist.add(eleBack);
							}
						}
						this.getPeDetailService().saveStuBatchBack(eleBacklist, stuBatchList);
					}
					if (action.indexOf("StuDel") >= 0) {
						if (peBzzBatchlist.get(0).getEndDate().before(new Date())) {
							map.clear();
							map.put("success", "false");
							map.put("info", "报名已经结束，无法删除学生");
							return map;
						}
						if (peBzzBatchlist.get(0).getStartDate().before(new Date())) {
							map.clear();
							map.put("success", "false");
							map.put("info", "报名已经开始，无法删除学生");
							return map;
						}
						// 获取学生专项id列表
						DetachedCriteria dcStuBatch = DetachedCriteria.forClass(StudentBatch.class);
						dcStuBatch.add(Restrictions.eq("peBzzBatch.id", id));
						dcStuBatch.add(Restrictions.in("peStudent.id", ids));
						dcStuBatch.createCriteria("enumConstByFlagBatchPayState");
						List<StudentBatch> sbList = this.getGeneralService().getList(dcStuBatch);
						dcStuBatch.setProjection(Projections.distinct(Projections.property("id")));
						List stuBatchIdList = this.getGeneralService().getList(dcStuBatch);
						/*
						 * 之前逻辑： 学员已经购买此专项，不能删除 ； 现在逻辑： 专项开始就不能删除 ； for(int i=0;
						 * i<sbList.size(); i++) { StudentBatch sb =
						 * (StudentBatch)sbList.get(i);
						 * if(!sb.getEnumConstByFlagBatchPayState
						 * ().getCode().equals("0")) { map.clear();
						 * map.put("success", "false"); map.put("info",
						 * sb.getPeStudent().getName()+"学员已经购买此专项，不能删除"); return
						 * map; } }
						 */// 获取学生在该专项中选课id列表
						DetachedCriteria dcEletive = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
						dcEletive.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzBatch", "peBzzBatch");
						dcEletive.add(Restrictions.eq("peBzzBatch.id", id));
						dcEletive.add(Restrictions.in("peBzzStudent.id", ids));
						dcEletive.setProjection(Projections.distinct(Projections.property("id")));
						List electiveIdList = this.getGeneralService().getList(dcEletive);
						// 执行删除
						// this.getPeDetailService().deleteStuBatch(stuBatchIdList,
						// electiveIdList);
						// 改为删除back数据
						this.getPeDetailService().deleteStuBatchBack(stuBatchIdList, electiveIdList);
					}
					map.clear();
					map.put("success", "true");
					map.put("info", msg + ids.length + "条记录操作成功，请返回上一级页面，点击查看学员并为选中学员报名。");
				}

			} catch (Exception e) {
				map.clear();
				map.put("success", "false");
				map.put("info", "操作失败");
				return map;
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}

	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		batchid = this.getBatchid();
		StringBuffer sqlBuffer = new StringBuffer();
		if ("del".equalsIgnoreCase(method) || "list".equalsIgnoreCase(method)) {
			sqlBuffer.append(" SELECT * ");
			sqlBuffer.append("   FROM (SELECT PBS.ID, ");
			sqlBuffer.append("                SB.STU_ID, ");
			sqlBuffer.append("                PBS.REG_NO REGNO, ");
			sqlBuffer.append("                PBS.TRUE_NAME TRUENAME, ");
			sqlBuffer.append("                ECEMPLOYEE.NAME EMPNAME, ");
			sqlBuffer.append("                ECZIGE.NAME ZIGENAME, ");
			sqlBuffer.append("                PBS.WORK, ");
			sqlBuffer.append("                PBS.POSITION, ");
			sqlBuffer.append("                PBTC.ESTIMATETIME, ");
			sqlBuffer.append("                PE.NAME PENAME, ");
			sqlBuffer.append("                ECVALID.NAME VALIDNAME, ");
			sqlBuffer.append("                PBS.CARD_TYPE CARDTYPE, ");
			sqlBuffer.append("                PBS.CARD_NO CARDNO, ");
			sqlBuffer.append("                PBS.GROUPS, ");
			sqlBuffer.append("                SB.BATCH_ID, ");
			sqlBuffer.append("                DECODE(PBTC.PRICE, ");
			sqlBuffer.append("                       0, ");
			sqlBuffer.append("                       DECODE(PBTSE.ID, NULL, '未报名', '已报名'), ");
			sqlBuffer.append("                       ECPAY.NAME) PAYNAME ");
			sqlBuffer.append("           FROM PE_BZZ_STUDENT PBS ");
			sqlBuffer.append("          INNER JOIN PE_ENTERPRISE PE ");
			sqlBuffer.append("             ON PBS.FK_ENTERPRISE_ID = PE.ID ");
			sqlBuffer.append("            AND (PE.ID IN ");
			sqlBuffer.append("                (SELECT FK_ENTERPRISE_ID ");
			sqlBuffer.append("                    FROM PE_ENTERPRISE_MANAGER ");
			sqlBuffer.append("                   WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "') OR ");
			sqlBuffer.append("                PE.FK_PARENT_ID IN ");
			sqlBuffer.append("                (SELECT FK_ENTERPRISE_ID ");
			sqlBuffer.append("                    FROM PE_ENTERPRISE_MANAGER ");
			sqlBuffer.append("                   WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "')) ");
			sqlBuffer.append("          INNER JOIN SSO_USER SU ");
			sqlBuffer.append("             ON PBS.FK_SSO_USER_ID = SU.ID ");
			sqlBuffer.append("            AND SU.FLAG_ISVALID = '2' ");
			sqlBuffer.append("          INNER JOIN STU_BATCH SB ");
			sqlBuffer.append("             ON SB.BATCH_ID = '" + batchid + "' ");
			sqlBuffer.append("            AND PBS.ID = SB.STU_ID ");
			sqlBuffer.append("          INNER JOIN PE_BZZ_BATCH PBB ");
			sqlBuffer.append("             ON SB.BATCH_ID = PBB.ID ");
			sqlBuffer.append("          INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
			sqlBuffer.append("             ON PBB.ID = PBTO.FK_BATCH_ID ");
			sqlBuffer.append("          INNER JOIN PE_BZZ_TCH_COURSE PBTC ");
			sqlBuffer.append("             ON PBTO.FK_COURSE_ID = PBTC.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST ECEMPLOYEE ");
			sqlBuffer.append("             ON PBS.IS_EMPLOYEE = ECEMPLOYEE.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST ECZIGE ");
			sqlBuffer.append("             ON PBS.ZIGE = ECZIGE.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST ECVALID ");
			sqlBuffer.append("             ON SU.FLAG_ISVALID = ECVALID.ID ");
			sqlBuffer.append("          INNER JOIN ENUM_CONST ECPAY ");
			sqlBuffer.append("             ON SB.FLAG_BATCHPAYSTATE = ECPAY.ID ");
			sqlBuffer.append("           LEFT JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sqlBuffer.append("             ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
			sqlBuffer.append("            AND PBS.ID = PBTSE.FK_STU_ID ");
			sqlBuffer.append("          GROUP BY PBS.ID, ");
			sqlBuffer.append("                   SB.STU_ID, ");
			sqlBuffer.append("                   PBS.REG_NO, ");
			sqlBuffer.append("                   PBS.TRUE_NAME, ");
			sqlBuffer.append("                   ECEMPLOYEE.NAME, ");
			sqlBuffer.append("                   ECZIGE.NAME, ");
			sqlBuffer.append("                   PBTC.ESTIMATETIME, ");
			sqlBuffer.append("                   PE.NAME, ");
			sqlBuffer.append("                   ECVALID.NAME, ");
			sqlBuffer.append("                   PBS.CARD_TYPE, ");
			sqlBuffer.append("                   PBS.CARD_NO, ");
			sqlBuffer.append("                   PBS.GROUPS, ");
			sqlBuffer.append("                   SB.BATCH_ID, ");
			sqlBuffer.append("                   ECPAY.NAME, ");
			sqlBuffer.append("                   PBTC.PRICE, ");
			sqlBuffer.append("                   PBS.WORK, ");
			sqlBuffer.append("                   PBS.POSITION, ");
			sqlBuffer.append("                   PBTSE.ID) ");
			sqlBuffer.append("  WHERE 1 = 1 ");
		}
		if ("add".equalsIgnoreCase(method)) {
			sqlBuffer.append(" SELECT * ");
			sqlBuffer.append("   FROM (SELECT PBS.ID, ");
			sqlBuffer.append("                PBS.REG_NO      REGNO, ");
			sqlBuffer.append("                PBS.TRUE_NAME   TRUENAME, ");
			sqlBuffer.append("                ECEMPLOYEE.NAME EMPNAME, ");
			sqlBuffer.append("                ECZIGE.NAME     ZIGENAME, ");
			sqlBuffer.append("                PBS.WORK, ");
			sqlBuffer.append("                PBS.POSITION, ");
			sqlBuffer.append("                PE.NAME         PENAME, ");
			sqlBuffer.append("                ECVALID.NAME    VALIDNAME, ");
			sqlBuffer.append("                PBS.CARD_TYPE   CARDTYPE, ");
			sqlBuffer.append("                PBS.CARD_NO     CARDNO, ");
			sqlBuffer.append("                PBS.GROUPS ");
			sqlBuffer.append("           FROM PE_BZZ_STUDENT PBS, ");
			sqlBuffer.append("                PE_ENTERPRISE  PE, ");
			sqlBuffer.append("                SSO_USER       SU, ");
			sqlBuffer.append("                ENUM_CONST     ECEMPLOYEE, ");
			sqlBuffer.append("                ENUM_CONST     ECZIGE, ");
			sqlBuffer.append("                ENUM_CONST     ECVALID ");
			sqlBuffer.append("          WHERE PBS.FK_ENTERPRISE_ID = PE.ID ");
			sqlBuffer.append("            AND PBS.IS_EMPLOYEE = ECEMPLOYEE.ID ");
			sqlBuffer.append("            AND PBS.ZIGE = ECZIGE.ID ");
			sqlBuffer.append("            AND PBS.FK_SSO_USER_ID = SU.ID ");
			sqlBuffer.append("            AND SU.FLAG_ISVALID = ECVALID.ID ");
			sqlBuffer.append("            AND ECVALID.CODE = '1' ");// 有效账号
			sqlBuffer.append("            AND PBS.FK_ENTERPRISE_ID IN ");
			sqlBuffer.append("                ((SELECT FK_ENTERPRISE_ID ");
			sqlBuffer.append("                    FROM PE_ENTERPRISE_MANAGER ");
			sqlBuffer.append("                   WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "') ");
			sqlBuffer.append("                 UNION ALL  SELECT ID ");
			sqlBuffer.append("                    FROM PE_ENTERPRISE ");
			sqlBuffer.append("                   WHERE FK_PARENT_ID IN ");
			sqlBuffer.append("                         (SELECT FK_ENTERPRISE_ID ");
			sqlBuffer.append("                            FROM PE_ENTERPRISE_MANAGER ");
			sqlBuffer.append("                           WHERE FK_SSO_USER_ID = ");
			sqlBuffer.append("                                 '" + us.getSsoUser().getId() + "') ");
			sqlBuffer.append("                 ) ");
			sqlBuffer.append("            AND PBS.ID NOT IN ");
			sqlBuffer.append("                (SELECT SB.STU_ID ");
			sqlBuffer.append("                   FROM STU_BATCH SB ");
			sqlBuffer.append("                  WHERE SB.BATCH_ID = '" + batchid + "') ");
			sqlBuffer.append("          GROUP BY PBS.ID, ");
			sqlBuffer.append("                   PBS.REG_NO, ");
			sqlBuffer.append("                   PBS.TRUE_NAME, ");
			sqlBuffer.append("                   ECEMPLOYEE.NAME, ");
			sqlBuffer.append("                   ECZIGE.NAME, ");
			sqlBuffer.append("                   PE.NAME, ");
			sqlBuffer.append("                   ECVALID.NAME, ");
			sqlBuffer.append("                   PBS.CARD_TYPE, ");
			sqlBuffer.append("                   PBS.CARD_NO, ");
			sqlBuffer.append("                   PBS.WORK, ");
			sqlBuffer.append("                   PBS.POSITION, ");
			sqlBuffer.append("                   PBS.GROUPS) ");
			sqlBuffer.append("  WHERE 1 = 1 ");
		}
		try {
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}
				if (name.equals("enumConstByFlagIsvalid.name"))
					name = "VALIDNAME";
				if (name.equals("enumConstByFlagQualificationsType.name"))
					name = "ZIGENAME";
				if (name.endsWith("enumConstByFlagBatchPayState.name"))
					name = "PAYNAME";
				if (name.equals("enumConstByIsEmployee.name"))
					name = "EMPNAME";
				if ("VALIDNAME".equalsIgnoreCase(name) || "ZIGENAME".equalsIgnoreCase(name) || "PAYNAME".equalsIgnoreCase(name) || "EMPNAME".equalsIgnoreCase(name))
					sqlBuffer.append(" and UPPER(" + name + ") = '" + value.toUpperCase() + "'");
				else
					sqlBuffer.append(" and UPPER(" + name + ") like '%" + value.toUpperCase() + "%'");
			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("id"))
					temp = "regNo";
				if (temp.equals("cardType"))
					temp = "cardtype";
				if (temp.equals("cardNo"))
					temp = "cardno";
				if (temp.equals("enumConstByFlagIsvalid.name"))
					temp = "VALIDNAME";
				if (temp.equals("enumConstByFlagQualificationsType.name"))
					temp = "ZIGENAME";
				if (temp.equals("enumConstByIsEmployee.name"))
					temp = "EMPNAME";
				if (temp.equals("enumConstByFlagBatchPayState.name"))
					temp = "PAYNAME";
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {
					if (temp.equals("time")) {
						sqlBuffer.append(" order by to_number(" + temp + ") desc ");
					} else {
						sqlBuffer.append(" order by " + temp + " desc ");
					}

				} else {
					if (temp.equals("time")) {
						sqlBuffer.append(" order by to_number(" + temp + ") asc ");
					} else {
						sqlBuffer.append(" order by " + temp + " asc ");
					}
				}
			} else {
				sqlBuffer.append(" order by regNo desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return page;
	}

	public PeDetailService getPeDetailService() {
		return peDetailService;
	}

	public void setPeDetailService(PeDetailService peDetailService) {
		this.peDetailService = peDetailService;
	}

	private PeBzzstudentbacthService peBzzstudentbacthService;
	private int count;

	public String fixStudent() {
		String cardNo = this.getIds();
		if (cardNo == null || "".equals(cardNo)) {
			cardNo = "1";
		}
		String m = this.peBzzstudentbacthService.updatestu(cardNo, "2", this.getCount());
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		out.print(m);
		this.setMsg(m);
		return "msg";
	}

	public PeBzzstudentbacthService getPeBzzstudentbacthService() {
		return peBzzstudentbacthService;
	}

	public void setPeBzzstudentbacthService(PeBzzstudentbacthService peBzzstudentbacthService) {
		this.peBzzstudentbacthService = peBzzstudentbacthService;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}
}

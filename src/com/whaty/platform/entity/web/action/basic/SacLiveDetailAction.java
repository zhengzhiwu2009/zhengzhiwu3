package com.whaty.platform.entity.web.action.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

/**
 * @param
 * @version 创建时间：2014年6月4日09:28:18
 * @return
 * @throws PlatformException
 *             类说明
 */
public class SacLiveDetailAction extends MyBaseAction {

	private String id;
	private String freeze;// 直播是否封版1封版0未封版

	public String getFreeze() {
		return freeze;
	}

	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}

	/**
	 * 初始化列表
	 * 
	 * @author Lee
	 * @return
	 */
	@Override
	public void initGrid() {
		this.getGridConfig().setCapability(false, false, false, true, false);
		this.getGridConfig().setTitle(this.getText("分配学时"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("直播ID"), "liveid", false, false, false, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("开课ID"), "pbtoid", false, false, false, "TextField", false, 200, "");
		this.getGridConfig().addColumn(this.getText("系统编号"), "reg_no", true, false, true, "TextField", false, 100, true);
		this.getGridConfig().addColumn(this.getText("学员姓名"), "true_name", true, false, true, "TextField", false, 100, true);
		this.getGridConfig().addColumn(this.getText("机构编号"), "code", false, false, true, "TextField", false, 100, true);
		ColumnConfig ccPeEnterprise = new ColumnConfig(this.getText("机构名称"), "combobox_name", true, false, true, "TextField", false, 50, "");
		String sqlPeEnterprise = "SELECT A.ID ,A.NAME FROM PE_ENTERPRISE A ORDER BY A.NAME ASC";
		ccPeEnterprise.setComboSQL(sqlPeEnterprise);
		this.getGridConfig().addColumn(ccPeEnterprise);
		this.getGridConfig().addColumn(this.getText("累计学习时间(分钟)"), "studytime", false, false, true, "TextField", false, 100, true);
		this.getGridConfig().addColumn(this.getText("占课程时长百分比(%)"), "perc", false, false, true, "TextField", false, 100, true);
		this.getGridConfig().addColumn(this.getText("实际获得学时"), "time", true, false, true, "TextField", false, 100, true);
		this.getGridConfig().addColumn(this.getText("课程ID"), "courseid", false, false, false, "TextField", false, 100, true);
		this.getGridConfig().addColumn(this.getText("开课ID"), "opencourseid", false, false, false, "TextField", false, 100, true);
		this.getGridConfig().addColumn(this.getText("课程CODE"), "coursecode", false, false, false, "TextField", false, 100, true);
		// this
		// .getGridConfig()
		// .addRenderScript(
		// this.getText("查看学习记录"),
		// "{return '<a
		// href=\"/entity/teaching/searchAnyStudent_courseInfo.action?courseId='+record.data['courseid']+'&opencourseId='+record.data['opencourseid']+'&sid='+record.data['id']+'&courseCode='+record.data['coursecode']+'\"
		// target=\"_blank\">查看学习记录</a>';}",
		// "id");
		this
				.getGridConfig()
				.addRenderScript(
						this.getText("查看学习记录"),
						"{return '<a href=\"/entity/basic/sacLiveDetail_showDetail.action?liveId='+record.data['liveid']+'&stuId='+record.data['id']+'\" target=\"_blank\">查看学习记录</a>';}",
						"id");
		if (!"1".equals(freeze))
			this.getGridConfig().addMenuFunction(this.getText("分配学时"), "fpxs_" + this.getId());
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location.href='/entity/teaching/peBzzCourseLiveManager.action'}");
	}

	public void setEntityClass() {
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/sacLiveDetail";
	}

	public Page list() {
		Page page = null;
		StringBuffer sb = new StringBuffer();

		sb.append(" SELECT ID, ");
		sb.append("        LIVEID, ");
		sb.append("        PBTOID, ");
		sb.append("        REG_NO, ");
		sb.append("        TRUE_NAME, ");
		sb.append("        CODE, ");
		sb.append("        COMBOBOX_NAME, ");
		sb.append("        NVL(STUDYTIME, 0) STUDYTIME, ");
		sb.append("        TRUNC(DECODE(COURSE_LEN, NULL, '0', NVL(STUDYTIME, 0) / COURSE_LEN * 100), 2) PERC, ");
		sb.append("        TIME, ");
		sb.append("        COURSEID, ");
		sb.append("        OPENCOURSEID, ");
		sb.append("        COURSECODE ");
		sb.append("   FROM (SELECT PBS.ID, ");
		sb.append("                PBTC.LIVEID, ");
		sb.append("                PBTO.ID PBTOID, ");
		sb.append("                PBS.REG_NO, ");
		sb.append("                PBS.TRUE_NAME, ");
		sb.append("                PE.CODE, ");
		sb.append("                PE.NAME COMBOBOX_NAME, ");
		sb.append("                TRUNC(ROUND(SUM((CASE ");
		// 进入<开始 离开>开始 离开<结束 = 离开-开始
		sb.append("                                  WHEN WH_JOINTIME < (PBTC.ESTIMATESTARTDATE - ");
		sb.append("                                       TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                       8 / 24) * 86400000 AND ");
		sb.append("                                       WH_LEAVETIME > (PBTC.ESTIMATESTARTDATE - ");
		sb.append("                                       TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                       8 / 24) * 86400000 AND ");
		sb.append("                                       WH_LEAVETIME < (PBTC.ESTIMATEENDDATE - ");
		sb.append("                                       TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                       8 / 24) * 86400000 THEN ");
		sb.append("                                   WH_LEAVETIME - ");
		sb.append("                                   (PBTC.ESTIMATESTARTDATE - ");
		sb.append("                                   TO_DATE('1970-01-01', 'yyyy-mm-dd') - 8 / 24) * 86400000 ");
		// 进入<开始 离开>结束 = 结束-开始
		sb.append("                                  WHEN WH_JOINTIME < (PBTC.ESTIMATESTARTDATE - ");
		sb.append("                                       TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                       8 / 24) * 86400000 AND ");
		sb.append("                                       WH_LEAVETIME > (PBTC.ESTIMATEENDDATE - ");
		sb.append("                                       TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                       8 / 24) * 86400000 THEN ");
		sb.append("                                   (PBTC.ESTIMATEENDDATE - TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                   8 / 24) * 86400000 - ");
		sb.append("                                   (PBTC.ESTIMATESTARTDATE - ");
		sb.append("                                   TO_DATE('1970-01-01', 'yyyy-mm-dd') - 8 / 24) * 86400000 ");
		// 进入>开始 离开<结束 = 离开-进入
		sb.append("                                  WHEN WH_JOINTIME > (PBTC.ESTIMATESTARTDATE - ");
		sb.append("                                       TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                       8 / 24) * 86400000 AND ");
		sb.append("                                       WH_LEAVETIME < (PBTC.ESTIMATEENDDATE - ");
		sb.append("                                       TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                       8 / 24) * 86400000 THEN ");
		sb.append("                                   WH_LEAVETIME - WH_JOINTIME ");
		// 进入>开始 进入<结束 离开>结束 = 结束-进入
		sb.append("                                  WHEN WH_JOINTIME > (PBTC.ESTIMATESTARTDATE - ");
		sb.append("                                       TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                       8 / 24) * 86400000 AND ");
		sb.append("                                       WH_JOINTIME < (PBTC.ESTIMATEENDDATE - ");
		sb.append("                                       TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                       8 / 24) * 86400000 AND ");
		sb.append("                                       WH_LEAVETIME > (PBTC.ESTIMATEENDDATE - ");
		sb.append("                                       TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                       8 / 24) * 86400000 THEN ");
		sb.append("                                   (PBTC.ESTIMATEENDDATE - TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                   8 / 24) * 86400000 - WH_JOINTIME ");
		// 进入>结束 = 0
		sb.append("                                  WHEN WH_JOINTIME > (PBTC.ESTIMATEENDDATE - ");
		sb.append("                                       TO_DATE('1970-01-01', 'yyyy-mm-dd') - ");
		sb.append("                                       8 / 24) * 86400000 THEN ");
		sb.append("                                   0 ");
		sb.append("                                END) / 60 / 1000)), ");
		sb.append("                      2) STUDYTIME, ");
		sb.append("                PBTC.COURSE_LEN, ");
		sb.append("                DECODE(PBTSE.COMPLETED_TIME, NULL, '0', PBTC.TIME) TIME, ");
		sb.append("                PBTC.ID COURSEID, ");
		sb.append("                PBTO.ID OPENCOURSEID, ");
		sb.append("                PBTC.CODE COURSECODE ");
		sb.append("           FROM PE_BZZ_STUDENT          PBS, ");
		sb.append("                STU_BATCH               SB, ");
		sb.append("                PE_ENTERPRISE           PE, ");
		sb.append("                WE_HISTORY              WH, ");
		sb.append("                PR_BZZ_TCH_STU_ELECTIVE PBTSE, ");
		sb.append("                PR_BZZ_TCH_OPENCOURSE   PBTO, ");
		sb.append("                PE_BZZ_TCH_COURSE       PBTC, ");
		sb.append("                LIVE_SEQ_STU            LSS ");
		sb.append("          WHERE PBTC.ID = '" + this.id + "' ");
		sb.append("            AND PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("            AND PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		sb.append("            AND PBTSE.FK_STU_ID = PBS.ID ");
		sb.append("            AND PBS.FK_ENTERPRISE_ID = PE.ID ");
		sb.append("            AND PBS.FK_SSO_USER_ID = LSS.LOGIN_ID ");
		sb.append("            AND WH.WH_UID = LSS.ID ");
		sb.append("            AND PBTSE.FK_STU_ID = SB.STU_ID ");
		sb.append("            AND PBTO.FK_BATCH_ID = SB.BATCH_ID ");
		sb.append("            AND PBTC.LIVEID = WH.WH_WEBCASTID ");
		sb.append("          GROUP BY PBS.ID, ");
		sb.append("                   PBS.REG_NO, ");
		sb.append("                   PBS.TRUE_NAME, ");
		sb.append("                   PE.CODE, ");
		sb.append("                   PE.NAME, ");
		sb.append("                   PBTSE.COMPLETED_TIME, ");
		sb.append("                   PBTC.TIME, ");
		sb.append("                   PBTC.COURSE_LEN, ");
		sb.append("                   PBTC.LIVEID, ");
		sb.append("                   PBTO.ID, ");
		sb.append("                   PBTC.ID, ");
		sb.append("                   PBTC.CODE) ");
		sb.append("  WHERE 1 = 1 ");

		// sb.append(" SELECT * FROM ( ");
		// sb.append(" SELECT PBS.ID,PBTC.LIVEID,PBTO.ID PBTOID, ");
		// sb.append(" PBS.REG_NO, ");
		// sb.append(" PBS.TRUE_NAME, ");
		// sb.append(" PE.CODE, ");
		// sb.append(" PE.NAME COMBOBOX_NAME, ");
		// sb.append(" TRUNC(SUM((WH_LEAVETIME-WH_JOINTIME) / 60 / 1000),2)
		// STUDYTIME, ");
		// sb.append(" TRUNC(DECODE(PBTC.COURSE_LEN, ");
		// sb.append(" NULL, ");
		// sb.append(" '0', ");
		// sb.append(" ROUND(SUM((WH_LEAVETIME-WH_JOINTIME) / 60 / 1000) * 100 /
		// ");
		// sb.append(" PBTC.COURSE_LEN, ");
		// sb.append(" 2)),2) PERC, ");
		// sb.append(" DECODE(PBTSE.COMPLETED_TIME, NULL, '0', PBTC.TIME)
		// TIME,PBTC.ID COURSEID,PBTO.ID OPENCOURSEID, PBTC.CODE COURSECODE ");
		// sb.append(" FROM PE_BZZ_STUDENT PBS, ");
		// sb.append(" STU_BATCH SB, ");
		// sb.append(" PE_ENTERPRISE PE, ");
		// sb.append(" WE_HISTORY WH, ");
		// sb.append(" PR_BZZ_TCH_STU_ELECTIVE PBTSE, ");
		// sb.append(" PR_BZZ_TCH_OPENCOURSE PBTO, ");
		// sb.append(" PE_BZZ_TCH_COURSE PBTC, ");
		// sb.append(" LIVE_SEQ_STU LSS ");
		// sb.append(" WHERE PBTC.ID = '" + this.id + "' ");
		// sb.append(" AND PBTC.ID = PBTO.FK_COURSE_ID ");
		// sb.append(" AND PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID ");
		// sb.append(" AND PBTSE.FK_STU_ID = PBS.ID ");
		// sb.append(" AND PBS.FK_ENTERPRISE_ID = PE.ID ");
		// sb.append(" AND PBS.FK_SSO_USER_ID = LSS.LOGIN_ID ");
		// sb.append(" AND WH.WH_UID = LSS.ID ");
		// sb.append(" AND PBTSE.FK_STU_ID = SB.STU_ID ");
		// sb.append(" AND PBTO.FK_BATCH_ID = SB.BATCH_ID ");
		// sb.append(" AND PBTC.LIVEID = WH.WH_WEBCASTID ");
		// sb.append(" GROUP BY PBS.ID, ");
		// sb.append(" PBS.REG_NO, ");
		// sb.append(" PBS.TRUE_NAME, ");
		// sb.append(" PE.CODE, ");
		// sb.append(" PE.NAME, ");
		// sb.append(" PBTSE.COMPLETED_TIME, ");
		// sb.append(" PBTC.TIME, ");
		// sb.append(" PBTC.COURSE_LEN,PBTC.LIVEID,PBTO.ID,PBTC.ID,PBTC.CODE)
		// WHERE 1 = 1 ");
		try {
			this.setSqlCondition(sb);
			page = this.getGeneralService().getByPageSQL(sb.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

	public String showDetail() {
		String liveId = ServletActionContext.getRequest().getParameter("liveId");
		String stuId = ServletActionContext.getRequest().getParameter("stuId");
		StringBuffer sbPbtc = new StringBuffer();
		// 课程信息
		sbPbtc.append("SELECT CODE, NAME FROM PE_BZZ_TCH_COURSE WHERE LIVEID = '" + liveId + "'");
		// 登陆登出记录
		StringBuffer sbWeHistory = new StringBuffer();
		sbWeHistory.append(" SELECT DISTINCT * FROM ( ");
		sbWeHistory.append(" SELECT TO_CHAR(TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sbWeHistory.append("        (WH_JOINTIME + 8 * 1000 * 3600) / 1000 / 24 / 60 / 60,'yyyy-MM-dd hh24:mi:ss') D1, ");
		sbWeHistory.append("        TO_CHAR(TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sbWeHistory.append("        (WH_LEAVETIME + 8 * 1000 * 3600) / 1000 / 24 / 60 / 60,'yyyy-MM-dd hh24:mi:ss') D2 ");
		sbWeHistory.append("   FROM WE_HISTORY WH, LIVE_SEQ_STU LSS ");
		sbWeHistory.append("  WHERE WH.WH_UID = LSS.ID ");
		sbWeHistory.append("    AND WH.WH_WEBCASTID = '" + liveId + "' ");
		sbWeHistory.append("    AND LSS.LOGIN_ID = (SELECT FK_SSO_USER_ID FROM PE_BZZ_STUDENT WHERE ID = '" + stuId + "') ");
		sbWeHistory.append("    ) ORDER BY TO_DATE(D1,'yyyy-MM-dd hh24:mi:ss') ASC ");
		// 点名记录
		StringBuffer sbWeRollCall = new StringBuffer();
		sbWeRollCall.append(" SELECT DISTINCT * FROM ( ");
		sbWeRollCall.append(" SELECT TO_CHAR(TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sbWeRollCall.append("        (WR_STARTTIME + 8 * 1000 * 3600) / 1000 / 24 / 60 / 60,'yyyy-MM-dd hh24:mi:ss') D1, ");
		sbWeRollCall.append("        TO_CHAR(TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sbWeRollCall.append("        (WR_ENDTIME + 8 * 1000 * 3600) / 1000 / 24 / 60 / 60,'yyyy-MM-dd hh24:mi:ss') D2, ");
		sbWeRollCall.append("        DECODE(WRU.WRU_PRESENT,'true',1,0) ");
		sbWeRollCall.append("   FROM WE_ROLLCALL WR, WE_ROLLCALL_USERS WRU, LIVE_SEQ_STU LSS ");
		sbWeRollCall.append("  WHERE WR.WR_ID = WRU.WRU_WR_ID ");
		sbWeRollCall.append("    AND WRU.WRU_UID = LSS.ID ");
		sbWeRollCall.append("    AND WR.WR_WEBCASTID = '" + liveId + "' ");
		sbWeRollCall.append("    AND LSS.LOGIN_ID = ");
		sbWeRollCall.append("        (SELECT FK_SSO_USER_ID FROM PE_BZZ_STUDENT WHERE ID = '" + stuId + "') ");
		sbWeRollCall.append("    ORDER BY TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') +  ");
		sbWeRollCall.append("        WR_STARTTIME / 1000 / 24 / 60 / 60 ASC) ");
		// 答疑记录
		StringBuffer sbWeQa = new StringBuffer();
		sbWeQa.append(" SELECT DISTINCT * FROM ( ");
		sbWeQa.append(" SELECT TO_CHAR(TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sbWeQa.append("        (WQ.WQ_SUBMITTIME + 8 * 1000 * 3600) / 1000 / 24 / 60 / 60,'yyyy-MM-dd hh24:mi:ss') D1, ");
		sbWeQa.append("        WQ.WQ_QUESTION, ");
		sbWeQa.append("        DECODE(WQ.WQ_RESPONSE,null,'-',WQ.WQ_RESPONSE) WQ_RESPONSE, ");
		sbWeQa.append("        WQ.WQ_NAME ");
		sbWeQa.append("   FROM WE_QA WQ, LIVE_SEQ_STU LSS ");
		sbWeQa.append("  WHERE WQ.WQ_SUBMITTER = LSS.ID ");
		sbWeQa.append("    AND WQ.WQ_WEBCASTID = '" + liveId + "' ");
		sbWeQa.append("    AND LSS.LOGIN_ID = (SELECT FK_SSO_USER_ID FROM PE_BZZ_STUDENT WHERE ID = '" + stuId + "') ");
		sbWeQa.append("    ORDER BY TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sbWeQa.append("    WQ.WQ_SUBMITTIME / 1000 / 24 / 60 / 60 ASC) ");
		String minSumSql = "SELECT TRUNC(SUM((WH.WH_LEAVETIME - WH.WH_JOINTIME) / 60 / 1000), 2) MINSUM,PBS.TRUE_NAME FROM WE_HISTORY WH, PE_BZZ_STUDENT PBS, LIVE_SEQ_STU LSS WHERE WH.WH_UID = LSS.ID AND LSS.LOGIN_ID = PBS.FK_SSO_USER_ID AND PBS.ID = '"
				+ stuId + "' AND WH.WH_WEBCASTID = '" + liveId + "' GROUP BY WH.WH_NICKNAME, WH.WH_WEBCASTID,PBS.TRUE_NAME";
		try {
			// 小统计
			List minSumList = this.getGeneralService().getBySQL(minSumSql);
			if (null == minSumList || minSumList.size() <= 0) {
				this.setMsg("暂无直播数据");
				this.setTogo("window.close();");
				return "msg";
			}
			// 互动调查记录
			StringBuffer sbWeVote = new StringBuffer();
			sbWeVote.append(" SELECT WVV_ID,WVV_SUBJECT FROM WE_VOTE_VOTELIST WHERE WVV_WEBCASTID = '" + liveId + "' ");
			// 互动调查记录
			List listVote = this.getGeneralService().getBySQL(sbWeVote.toString());
			// 问题内容
			Map<Object, List> mapVoteQuestion = new HashMap<Object, List>();
			// 问题选项
			Map<Object, List> mapVoteOption = new HashMap<Object, List>();
			// 问题回答
			Map<Object, List> mapVoteResult = new HashMap<Object, List>();
			// 循环调查内容
			for (int i = 0; i < listVote.size(); i++) {
				Object voteId = ((Object[]) listVote.get(i))[0];
				StringBuffer sbWeVoteQuestion = new StringBuffer();
				sbWeVoteQuestion.append(" SELECT WVQ_ID,WVQ_CONTENT FROM WE_VOTE_QUESTIONS WHERE WVQ_WVV_ID = '" + voteId + "'");
				List listVoteQuestion = this.getGeneralService().getBySQL(sbWeVoteQuestion.toString());
				mapVoteQuestion.put(voteId, listVoteQuestion);
				/* 说明：如果内容选项value为空，调查回答的value不为空，则为问题题，其他为选择题 */
				// 循环调查内容选项
				for (int ii = 0; ii < listVoteQuestion.size(); ii++) {
					Object voteQuestionId = ((Object[]) listVoteQuestion.get(ii))[0];
					StringBuffer sbWeVoteOption = new StringBuffer();
					sbWeVoteOption.append(" SELECT WVO_ID,WVO_VALUE FROM WE_VOTE_OPTIONS WHERE WVO_WVQ_ID = '" + voteQuestionId + "' ");
					List listVoteOption = this.getGeneralService().getBySQL(sbWeVoteOption.toString());
					mapVoteOption.put(voteQuestionId, listVoteOption);
					// 循环调查回答
					for (int iii = 0; iii < listVoteOption.size(); iii++) {
						Object voteOptionId = ((Object[]) listVoteOption.get(iii))[0];
						StringBuffer sbWeVoteResult = new StringBuffer();
						sbWeVoteResult
								.append(" SELECT WVR_WVO_ID WWI1,WVR_ANSWER, WVR_WVO_ID WWI2 FROM WE_VOTE_RESULTS WVR,LIVE_SEQ_STU LSS WHERE LSS.LOGIN_ID = (SELECT FK_SSO_USER_ID FROM PE_BZZ_STUDENT WHERE ID = '"
										+ stuId + "') AND WVR.WVR_UID = LSS.ID AND WVR_WVO_ID = '" + voteOptionId + "'");
						List listVoteResult = this.getGeneralService().getBySQL(sbWeVoteResult.toString());
						mapVoteResult.put(voteOptionId, listVoteResult);
					}
				}
			}
			// 课程信息
			List listPbtc = this.getGeneralService().getBySQL(sbPbtc.toString());
			// 登陆登出记录
			List listWeHistory = this.getGeneralService().getBySQL(sbWeHistory.toString());
			// 点名记录
			List listWeRollCall = this.getGeneralService().getBySQL(sbWeRollCall.toString());
			// 答疑记录
			List listWeQa = this.getGeneralService().getBySQL(sbWeQa.toString());
			// 课程信息
			ServletActionContext.getRequest().setAttribute("listPbtc", listPbtc);
			// 登陆登出记录
			ServletActionContext.getRequest().setAttribute("listWeHistory", listWeHistory);
			// 点名记录
			ServletActionContext.getRequest().setAttribute("listWeRollCall", listWeRollCall);
			// 问卷调查记录
			ServletActionContext.getRequest().setAttribute("listVote", listVote);
			// 问卷调查问题内容
			ServletActionContext.getRequest().setAttribute("mapVoteQuestion", mapVoteQuestion);
			// 问卷调查问题选项
			ServletActionContext.getRequest().setAttribute("mapVoteOption", mapVoteOption);
			// 问卷调查问题回答
			ServletActionContext.getRequest().setAttribute("mapVoteResult", mapVoteResult);
			// 答疑记录
			ServletActionContext.getRequest().setAttribute("listWeQa", listWeQa);
			ServletActionContext.getRequest().setAttribute("minSum", ((Object[]) minSumList.get(0))[0]);
			ServletActionContext.getRequest().setAttribute("stuName", ((Object[]) minSumList.get(0))[1]);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "showDetail";
	}

	public Map updateColumn() {
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();
		String id;
		String ap[] = action.split("_");
		id = ap[1];
		Object[] idsObjects = this.getIds().split(",");
		if (action.indexOf("fpxs") >= 0) {
			String stuIdString = "";
			for (int i = 0; i < idsObjects.length; i++) {
				stuIdString += "'" + idsObjects[i] + "',";
			}
			stuIdString = stuIdString.substring(0, stuIdString.length() - 1);
			List<StringBuffer> sbList = new ArrayList<StringBuffer>();
			StringBuffer sb = new StringBuffer();
			for (Object stuId : idsObjects) {
				sb = new StringBuffer();
				sb.append(" UPDATE TRAINING_COURSE_STUDENT TCS ");
				sb
						.append("    SET TCS.GET_DATE      = (SELECT TO_DATE('1970-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss') + (MIN(WH_JOINTIME) + 8 * 1000 * 3600)/1000/24/60/60 FROM WE_HISTORY WH, PE_BZZ_TCH_COURSE PBTC, LIVE_SEQ_STU LSS WHERE WH.WH_WEBCASTID = PBTC.LIVEID AND WH.WH_UID = LSS.ID AND PBTC.ID = '"
								+ id + "' AND LSS.LOGIN_ID = (SELECT FK_SSO_USER_ID FROM PE_BZZ_STUDENT WHERE ID = '" + stuId + "')), ");
				sb.append("        TCS.PERCENT       = '100', ");
				sb.append("        TCS.LEARN_STATUS  = 'COMPLETED', ");
				sb
						.append("        TCS.COMPLETE_DATE = (SELECT TO_DATE('1970-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss') + (MAX(WH_LEAVETIME) + 8 * 1000 * 3600)/1000/24/60/60 FROM WE_HISTORY WH, PE_BZZ_TCH_COURSE PBTC, LIVE_SEQ_STU LSS WHERE WH.WH_WEBCASTID = PBTC.LIVEID AND WH.WH_UID = LSS.ID AND PBTC.ID = '"
								+ id + "' AND LSS.LOGIN_ID = (SELECT FK_SSO_USER_ID FROM PE_BZZ_STUDENT WHERE ID = '" + stuId + "')) ");
				sb.append("  WHERE TCS.STUDENT_ID IN (SELECT FK_SSO_USER_ID FROM PE_BZZ_STUDENT WHERE ID = '" + stuId + "') ");
				sb.append("    AND TCS.COURSE_ID IN ");
				sb.append("        (SELECT ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_COURSE_ID = '" + id + "') ");
				sbList.add(sb);
			}
			StringBuffer sb2 = new StringBuffer();
			sb2.append(" UPDATE PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
			sb2.append("    SET PBTSE.COMPLETED_TIME = SYSDATE,ISPASS='1' ");
			sb2.append("  WHERE PBTSE.FK_STU_ID IN (" + stuIdString + ") ");
			sb2.append("    AND PBTSE.FK_TCH_OPENCOURSE_ID IN ");
			sb2.append("        (SELECT ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_COURSE_ID = '" + id + "') ");
			try {
				// 1.验证选中学员是否有人已经分配过学时
				String sql = "SELECT DISTINCT PBS.REG_NO FROM PE_BZZ_STUDENT PBS INNER JOIN PR_BZZ_TCH_STU_ELECTIVE PBTSE ON PBS.ID = PBTSE.FK_STU_ID AND PBS.ID IN ("
						+ stuIdString
						+ ") AND PBTSE.COMPLETED_TIME IS NOT NULL INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID AND PBTO.FK_COURSE_ID = '"
						+ id + "'";
				List hasStusList = this.getGeneralService().getBySQL(sql);
				if (null != hasStusList && hasStusList.size() > 0) {
					for (int i = 0; i < hasStusList.size(); i++) {
						Object regNo = hasStusList.get(i);
						msg += regNo + ",";
					}
					if (null != msg && !"".equals(msg)) {
						msg = "以下学员：" + msg.substring(0, msg.length() - 1) + " 已经分配学时，请重新选择学员！";
						map.clear();
						map.put("success", "false");
						map.put("info", msg);
						return map;
					}
				} else {
					// 2.选中学员全部未分配过学时则执行分配操作
					int res = this.getGeneralService().saveLiveTime(sbList, sb2.toString());
					if (res > 0) {
						map.clear();
						map.put("success", "true");
						map.put("info", "分配成功");
						return map;
					} else {
						map.clear();
						map.put("success", "false");
						map.put("info", "分配失败");
						return map;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", "分配出错，请联系管理员");
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

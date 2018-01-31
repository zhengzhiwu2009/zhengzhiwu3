package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.information.PeVotePaperService;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

public class AddSatisfactionPaperAction extends MyBaseAction<PeBzzTchCourse> {

	private String courseId;
	private String flag;
	private String operate;
	private PeVotePaperService peVotePaperService;
	/* 记录来自哪个课程的action */
	private String area;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		if (flag != null) {
			ServletActionContext.getRequest().getSession().setAttribute("flag", flag);
		} else {
			flag = (String) ServletActionContext.getRequest().getSession().getAttribute("flag");
		}
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("课程添加满意度调查");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("调查问卷名称"), "title");
		// this.getGridConfig().addColumn(this.getText("是否可用"),
		// "enumConstByFlagIsvalid.name");
		// this.getGridConfig().addColumn(this.getText("创建人"), "creatuser",
		// false);
		this.getGridConfig().addColumn(this.getText("创建日期"), "startDate");
		// this.getGridConfig().addMenuFunction(this.getText("加入课程"), "add");
		this.getGridConfig().addMenuFunction(this.getText("添加到对应课程"), "/entity/teaching/addSatisfactionPaper_dealSatisPaper.action?operate=add", true, true);
		/* 获取上一个action名字用于返回对应的课程列表 */
		String act = this.getArea();
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location.href=\"/entity/teaching/" + act + ".action\"}");
		if (courseId != null) {
			ServletActionContext.getRequest().getSession().setAttribute("courseId", courseId);
		}
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		// this.entityClass = SatisfactionSurveyPaperInfo.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/addSatisfactionPaper";
	}

	@Override
	public Page list() {
		Page page = null;

		if (courseId == null) {
			courseId = (String) ServletActionContext.getRequest().getSession().getAttribute("courseId");
		}
		if (flag.equalsIgnoreCase("addPaper")) {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from ");
			sql.append("(select pvp.id,pvp.title as title, pvp.start_Date as startDate");
			sql.append("  from pe_vote_paper pvp\n");
			sql.append(" where pvp.type = 'course'\n");
			sql.append("   and pvp.courseid is null\n");
			sql.append("   and pvp.flag_isvalid='2'\n");
			sql.append("   and pvp.id not in (select pv.FK_PARENT_ID\n");
			sql.append("                        from pe_bzz_tch_course p, pe_vote_paper pv\n");
			sql.append("                       where p.id = '" + courseId + "'\n");
			sql.append("                         and pv.id = p.satisfaction_id))t");
			sql.append(" where 1=1");
			try {
				this.setSqlCondition(sql);
				page = this.getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return page;
	}

	/**
	 * 将调查问卷添加到对应的课程中去
	 */
	public String dealSatisPaper() {

		Map map = new HashMap();
		String[] ids = getIds().split(",");
		if (ids.length != 1) {
			map.clear();
			map.put("success", "false");
			map.put("info", "请不要选择多个满意度问卷");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
		String courseId = (String) ServletActionContext.getRequest().getSession().getAttribute("courseId");
		ServletActionContext.getRequest().getSession().removeAttribute("courseId");
		if ("".equals(courseId) || null == courseId) {
			map.clear();
			map.put("success", "false");
			map.put("info", "课程ID不存在");
			this.setJsonString(JsonUtil.toJSONString(map));
			return json();
		}
		DetachedCriteria pbdc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		pbdc.add(Restrictions.eq("id", courseId));
		List<PeBzzTchCourse> courseList = null;
		try {
			courseList = this.getGeneralService().getList(pbdc);
			if (null == courseList || courseList.size() == 0) {
				map.clear();
				map.put("success", "false");
				map.put("info", "课程ID不存在");
				this.setJsonString(JsonUtil.toJSONString(map));
				return json();
			}
			PeBzzTchCourse course = courseList.get(0);
			//boolean flag =this.peVotePaperService.getFlag(ids[0], courseId);
//			if(flag != true){
//				map.put("success", "false");
//				map.put("info", "已填写调查问卷不能更改");
//				
//			}else{
			int i = this.peVotePaperService.addCourseVote(ids[0], courseId);
			map.put("success", "true");
			map.put("info", "操作成功");
			//}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.clear();
			map.put("success", "false");
			map.put("info", "操作失败");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		// JsonUtil.setDateformat("yyyy-MM-dd");
		return json();
	}

	public PeVotePaperService getPeVotePaperService() {
		return peVotePaperService;
	}

	public void setPeVotePaperService(PeVotePaperService peVotePaperService) {
		this.peVotePaperService = peVotePaperService;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

}

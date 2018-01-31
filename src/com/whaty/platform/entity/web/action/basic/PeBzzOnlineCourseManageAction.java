package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBulletin;
import com.whaty.platform.entity.bean.PeBzzOnlineCourse;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.AnalyseClassType;
import com.whaty.platform.entity.util.ExpressionParse;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

public class PeBzzOnlineCourseManageAction extends MyBaseAction<PeBzzOnlineCourse> {
	private File upload;
	
	private String course_id;
	
	private String indexUrl;

	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		this.getGridConfig().setTitle(this.getText("在线课堂列表"));
		boolean capability = true;
		boolean isAdmin = true;
		if(!us.getUserLoginType().equals("3")) {
			isAdmin = false;
			capability = false;
		}
		this.getGridConfig().setCapability(capability,capability, capability, true);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		
		this.getGridConfig().addColumn(this.getText("编号"), "code");
		this.getGridConfig().addColumn(this.getText("讲座主题"), "subject",true, true, true,"TextField", false,100);
		this.getGridConfig().addColumn(this.getText("日期"), "courseDate");
		this.getGridConfig().addColumn(this.getText("开始时间"), "startTime", false, true, true, "TimeField", false, 25);
		this.getGridConfig().addColumn(this.getText("结束时间"), "endTime", false, true, true, "TimeField", false, 25);
		this.getGridConfig().addColumn(this.getText("图片提前天数"), "precedeDays", capability, capability, capability, "precedeDays", false, 1,Const.number_for_extjs);
		this.getGridConfig().addColumn(this.getText("主持人"), "emcee", true, true, true, "TextField", true, 25);
		this.getGridConfig().addColumn(this.getText("备注"), "bz", true, true, true, "TextArea", true, 200);
		
		String op = "<a href=\"/entity/bzz-students/jumpOnlineCourse.jsp?course_id=${value}\" target=\"_blank\">查看课件</a>";
		if(isAdmin)
		{
			this.getGridConfig().addRenderFunction(this.getText("查看问题列表"), "<a href=\"/entity/manager/basic/onlineCourseTwy.jsp?id=${value}\" target=\"_blank\">查看</a>", "id");
			
			this.getGridConfig().addRenderFunction(this.getText("浮动图片"), "<a href=\"/entity/manager/basic/image.jsp?id=${value}\" target=\"_blank\">查看</a>", "id");
			op = "<a href=\"/entity/manager/basic/import.jsp?courseId=${value}\" target=\"_blank\">导入课件</a>|" +
				"<a href=\"/entity/basic/onlineCourseMutiDisplay.action?code=${value}\" target=\"_blank\">查看课件</a>";
		}
		this.getGridConfig().addRenderFunction(this.getText("课件操作"), op, "id");
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzOnlineCourse.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peBzzOnlineCourseManage";
	}

	public void setBean(PeBzzOnlineCourse instance) {
		super.superSetBean(instance);
	}

	public PeBzzOnlineCourse getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOnlineCourse.class);
		return dc;
	}
	
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria enterdc = DetachedCriteria
				.forClass(PeBzzOnlineCourse.class);
		enterdc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(enterdc,
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
			
			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this
						.getGridConfig().getListColumnConfig()));
				Container o = new Container();
				o.setBean(page.getItems().get(0));
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount",1);
				map.put("models", list);
			}
			this.setJsonString(JsonUtil.toJSONString(map));
			JsonUtil.setDateformat("yyyy-MM-dd");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}
	
	public String abstractList() {
		ActionContext context = ActionContext.getContext();
		Map map1 = context.getParameters();
		Iterator it  = map1.entrySet().iterator();
		int k=0;
		int n=0;
		String tempsql = "select * from( select e.id,e.code,e.subject,to_char(e.start_date,'yyyy-mm-dd') as courseDate,to_char(e.start_date, 'hh24:mi:ss') as startTime,to_char(e.end_date, 'hh24:mi:ss') as endTime,e.precede_days as precedeDays,e.emcee,e.bz " 
				+ "from pe_bzz_onlinecourse e )"
				+ "where 1=1" ;
		StringBuffer buffer = new StringBuffer(tempsql);
		while(it.hasNext()){
			java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
			String name = entry.getKey().toString();
			if(name.startsWith("search__")){
				n++;
				String value = ((String[])entry.getValue())[0];
				if(value==""||"".equals(value)){
					k++;
				}else{
					name = name.substring(8);
					buffer.append(" and " + name + " like '%"+value+"%'");
				}
			}
		}
		
		String temp = this.getSort();
		//截掉前缀 Combobox_PeXxxxx.
		if(temp != null && temp.indexOf(".") > 1){
			if(temp.toLowerCase().startsWith("combobox_")){
				temp = temp.substring(temp.indexOf(".") + 1);
			}
		}
		if (this.getSort() != null && temp != null) {

				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc"))
					{buffer.append(" order by " +temp+ " desc ");
					}
				
				else{
					buffer.append(" order by " +temp+ " asc ");
				}
				
		} else {
			buffer.append(" order by id desc");
		}
		
		String js =null;
		if(k-n==0?true:false){
			js = super.abstractList();
		}else{
			initGrid();
			Page page = null;
			String sql = buffer.toString();
			try {
				page = this.getGeneralService().getByPageSQL(sql, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
				List jsonObjects = JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig());
				Map map = new HashMap();
				if (page != null) {
					map.put("totalCount", page.getTotalCount());
					map.put("models", jsonObjects);
				}
				this.setJsonString(JsonUtil.toJSONString(map));
				JsonUtil.setDateformat("yyyy-MM-dd");
				js = this.json();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		return js;
	}
	

	public Page list() {
		
		Page page = null;
		String sql = "select * from( select e.id,e.code,e.subject,to_char(e.start_date,'yyyy-mm-dd') as courseDate,to_char(e.start_date, 'hh24:mi:ss') as startTime,to_char(e.end_date, 'hh24:mi:ss') as endTime,e.precede_days as precedeDays,e.emcee,e.bz " 
			+ "from pe_bzz_onlinecourse e )"
			+ "where 1=1" ;

		try {
			StringBuffer sql_temp = new StringBuffer(sql);
			//this.setSort("code");
			
			String temp = this.getSort();
			//截掉前缀 Combobox_PeXxxxx.
			if(temp != null && temp.indexOf(".") > 1){
				if(temp.toLowerCase().startsWith("combobox_")){
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {

					if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc"))
						{sql_temp.append(" order by " +temp+ " desc ");
						}
					
					else{
						sql_temp.append(" order by " +temp+ " asc ");
					}
					
			} else {
				sql_temp.append(" order by id desc");
			}
			
			page = this.getGeneralService().getByPageSQL(sql_temp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	
	
	@Override
	public void checkBeforeAdd() throws EntityException {
		
		if (this.getBean().getStartDate().after(this.getBean().getEndDate())) {
			throw new EntityException("开始时间应在结束时间之前！");
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOnlineCourse.class);
		
		dc.add(Restrictions.eq("subject", this.getBean().getSubject()));
		List list = this.getGeneralService().getList(dc);
		if (list.size() > 0) {
			throw new EntityException("在线课程名已存在，请重新填写！");
		}
		dc = DetachedCriteria.forClass(PeBzzOnlineCourse.class);
		dc.add(Restrictions.eq("code", this.getBean().getCode()));
		list = this.getGeneralService().getList(dc);
		if (list.size() > 0) {
			throw new EntityException("在线课程编号已存在，请重新填写！");
		}
		
		this.getBean().setFlashImage("default.jpg");
	}
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
		if (this.getBean().getStartDate().after(this.getBean().getEndDate())) {
			throw new EntityException("开始时间应在结束时间之前！");
		}
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOnlineCourse.class);
		
		dc.add(Restrictions.eq("subject", this.getBean().getSubject()));
		dc.add(Restrictions.ne("id", this.getBean().getId()));
		List list = this.getGeneralService().getList(dc);
		if (list.size() > 0) {
			throw new EntityException("在线课程名已存在，请重新填写！");
		}
		dc = DetachedCriteria.forClass(PeBzzOnlineCourse.class);
		dc.add(Restrictions.eq("code", this.getBean().getCode()));
		dc.add(Restrictions.ne("id", this.getBean().getId()));
		list = this.getGeneralService().getList(dc);
		if (list.size() > 0) {
			throw new EntityException("在线课程编号已存在，请重新填写！");
		}
		
	}
	
	@Override
	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				String idStr = "";
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
					idStr += "'" + ids[i] + "',";
				}
				if(idStr.endsWith(",")) {
					idStr = idStr.substring(0,idStr.length()-1);
				}
				
				try{
					String sql = "delete from scorm_sco_launch ssl where ssl.course_id in("+idStr+")";
					int n = this.getGeneralService().executeBySQL(sql);
					//System.out.println(n);
					
					DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOnlineCourse.class);
					dc.setProjection(Property.forName("code"));
					dc.add(Restrictions.in("id", idList));
					List codeList = this.getGeneralService().getList(dc);
//					this.getGeneralService().deleteByIds(idList);
					UserSession us = (UserSession) ServletActionContext.getRequest()
					.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
					List<PeEnterprise> priList = us.getPriEnterprises();

					List siteList=new ArrayList();
					
					for(int i=0;i<priList.size();i++)
					{
						siteList.add(priList.get(i).getId());
					}
					
					int i = this.getGeneralService().deleteByIds(this.getEntityClass(), siteList, idList);
					if(i!=0){
						deleteCourseSrc(codeList);
					}
					map.put("success", "true");
					map.put("info", "删除成功");
					if(0 == i){
						map.clear();
						map.put("success", "false");
						map.put("info", "对不起，您的没有删除的权限");
					}
				}catch(RuntimeException e){
					return this.checkForeignKey(e);
				}catch(Exception e1){
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}
	
	private void deleteCourseSrc(List<String> codeList) {
		String path;
		path = ServletActionContext.getServletContext().getRealPath("/CourseImports/OnlineCourse/");
		//System.out.println(path);
		
		for(String code : codeList) {
			File file = new File(path + code);
			if(file.isDirectory()) {
				//System.out.println("delete folder " + file.getAbsolutePath());
				deleteFolder(file);
			}
		}
		
	}
	
	private void deleteFolder(File dir) {
		File filelist[] = dir.listFiles();
		int listlen = filelist.length;
		for (int i = 0; i < listlen; i++) {
			if (filelist[i].isDirectory()) {
				deleteFolder(filelist[i]);
			} else {
				filelist[i].delete();
			}
		}
		dir.delete();// 删除当前目录
	}
	
	
	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getIndexUrl() {
		return indexUrl;
	}

	public void setIndexUrl(String indexUrl) {
		this.indexUrl = indexUrl;
	}
}

package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzCommInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeBzzCommInfoBatchService;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.util.AnalyseClassType;
import com.whaty.platform.entity.util.ExpressionParse;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

import jxl.Sheet;
import jxl.Workbook;

public class PeBzzCommInfoAction extends MyBaseAction<PeBzzCommInfo> {
	
	private File upload;
	private String uploadFileName; // 文件名属性
	private String uploadContentType; // 文件类型属性
	private String _uploadContentType; // 文件类型属性
	private String savePath; // 文件存储位置
	private String course_id;
	
	private String indexUrl;
	
	private PeBzzCommInfoBatchService peBzzCommInfoBatchService;

	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		this.getGridConfig().setTitle(this.getText("沟通记录列表"));
	
		this.getGridConfig().setCapability(true,true, true, true);

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("姓名"),"name",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("学号"), "regNo");
		this.getGridConfig().addColumn(this.getText("手机号码"),"phone",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("所在企业"),"ename",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("沟通时间"), "commDate");
		this.getGridConfig().addColumn(this.getText("沟通时已完成学时"), "finishTime");
		this.getGridConfig().addColumn(this.getText("当前已完成学时"),"curfinishTime",false,false,true,"");
		this.getGridConfig().addColumn(this.getText("备注"), "bz", true, true, true, "TextArea", true, 200);
		this.getGridConfig().addColumn(this.getText("录入时间"), "luruDate",true,false,true,"");
		this.getGridConfig().addColumn(this.getText("录入人"), "luruPeople",true,false,true,"");
		
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzCommInfo.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/peBzzCommInfo";
	}

	public void setBean(PeBzzCommInfo instance) {
		super.superSetBean(instance);
	}

	public PeBzzCommInfo getBean() {
		return super.superGetBean();
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzCommInfo.class);
		return dc;
	}
	
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria enterdc = DetachedCriteria
				.forClass(PeBzzCommInfo.class);
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
	
	public String getSavePath() {
		//return ServletActionContext.getServletContext().getRealPath(savePath);
		return ServletActionContext.getServletContext().getRealPath("/incoming/Excel");
	}
	
	public String batch(){
		return "batch";
	}
	public String uploadExcel(){
		int count =0;
		try {
			FileInputStream fis = new FileInputStream(getUpload());
			File file = new File(getSavePath().replaceAll("\\\\", "/"));
			 if (!file.exists()) {
				    file.mkdirs();
				   }
			 	String filepath = getSavePath().replaceAll("\\\\", "/") + "/" + getUploadFileName();
			 FileOutputStream fos = new FileOutputStream(getSavePath().replaceAll("\\\\", "/") + "/" + getUploadFileName());
			  int i = 0;
			   byte[] buf = new byte[1024];
			   while ((i = fis.read(buf)) != -1) {
			    fos.write(buf, 0, i);
			   }
			   File filetest = new File(filepath);
			  // System.out.println("filepath>>>>>>>>>:"+filepath);
			   count = this.peBzzCommInfoBatchService.Bacthsave(filetest);
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "uploadComm_result";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		this.setTogo("back");
		return "uploadComm_result";
	}
	
	
	public String abstractList() {
		ActionContext context = ActionContext.getContext();
		Map map1 = context.getParameters();
		Iterator it  = map1.entrySet().iterator();
		int k=0;
		int n=0;
		String tempsql = "select * from ("+
		"select c.id as id,\n" +
		"       s.true_name as name,\n" + 
		"       c.reg_no as regNo,\n" + 
		"       s.mobile_phone as phone,\n" + 
		"       e.name as ename,\n" + 
		"       to_char(c.comm_date,'yyyy-mm-dd') as commDate,\n" + 
		"       c.finish_time as finishTime,\n" + 
		"       sum(cs.percent * tc.time / 100) as curfinishTime,\n" + 
		"       c.bz as bz,\n" + 
		"       to_char(c.luru_date,'yyyy-mm-dd') as luruDate,\n" + 
		"       c.luru_people as luruPeople\n" + 
		"  from pe_bzz_comm_info        c,\n" + 
		"       pe_bzz_student          s,\n" + 
		"       training_course_student cs,\n" + 
		"       pe_bzz_tch_course       tc,\n" + 
		"       pr_bzz_tch_opencourse   btc,\n" + 
		"       pe_enterprise   e\n" + 
		" where c.reg_no = s.reg_no\n" + 
		"   and cs.student_id = s.fk_sso_user_id\n" + 
		"   and cs.course_id = btc.id\n" + 
		"   and btc.fk_course_id = tc.id\n" + 
		"   and e.id=s.fk_enterprise_id"+
		" group by c.id, s.true_name, c.reg_no,s.mobile_phone,e.name, c.comm_date, c.finish_time, c.bz,c.luru_date,c.luru_people )where 1=1";
		
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
		String sql = "select * from ("+
					"select c.id as id,\n" +
					"       s.true_name as name,\n" + 
					"       c.reg_no as regNo,\n" + 
					"       s.mobile_phone as phone,\n" + 
					"       e.name as ename,\n" + 
					"       to_char(c.comm_date,'yyyy-mm-dd') as commDate,\n" + 
					"       c.finish_time as finishTime,\n" + 
					"       sum(cs.percent * tc.time / 100) as curfinishTime,\n" + 
					"       c.bz as bz,\n" + 
					"       to_char(c.luru_date,'yyyy-mm-dd') as luruDate,\n" + 
					"       c.luru_people as luruPeople\n" + 
					"  from pe_bzz_comm_info        c,\n" + 
					"       pe_bzz_student          s,\n" + 
					"       training_course_student cs,\n" + 
					"       pe_bzz_tch_course       tc,\n" + 
					"       pr_bzz_tch_opencourse   btc,\n" + 
					"       pe_enterprise   e\n" + 
					" where c.reg_no = s.reg_no\n" + 
					"   and cs.student_id = s.fk_sso_user_id\n" + 
					"   and cs.course_id = btc.id\n" + 
					"   and btc.fk_course_id = tc.id\n" + 
					"   and e.id=s.fk_enterprise_id"+
					" group by c.id, s.true_name, c.reg_no,s.mobile_phone,e.name, c.comm_date, c.finish_time, c.bz,c.luru_date,c.luru_people) where 1=1";

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
		DetachedCriteria testdc = DetachedCriteria.forClass(PeBzzStudent.class);
		testdc.add(Restrictions.eq("regNo", this.getBean().getRegNo()));
		List pList=null;
		pList=this.getGeneralService().getList(testdc);
		if(pList.size()==0||pList.get(0)==null){
			throw new EntityException("该学号在系统没有对应学员，请修改后重新添加");
		}
		
		UserSession us = (UserSession)ServletActionContext
		.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us == null) {
			throw new EntityException("无操作权限");
		}
		String luru_people=us.getLoginId();
		this.getBean().setLuruDate(getCurSqlDate());
		this.getBean().setLuruPeople(luru_people);
	}
	
	@Override
	public void checkBeforeUpdate() throws EntityException {
		DetachedCriteria testdc = DetachedCriteria.forClass(PeBzzStudent.class);
		testdc.add(Restrictions.eq("regNo", this.getBean().getRegNo()));
		List pList=null;
		pList=this.getGeneralService().getList(testdc);
		if(pList.size()==0||pList.get(0)==null){
			throw new EntityException("该学号在系统没有对应学员，请修改后重新添加");
		}
		
		UserSession us = (UserSession)ServletActionContext
		.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us == null) {
			throw new EntityException("无操作权限");
		}
		String luru_people=us.getLoginId();
		this.getBean().setLuruDate(getCurSqlDate());
		this.getBean().setLuruPeople(luru_people);
	}
	
	public java.sql.Date getCurSqlDate()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		Date currentTime_2 = null;
		try {
			currentTime_2 = formatter.parse(dateString);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date d2 = new java.sql.Date(currentTime_2.getTime());
		return d2;  
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

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String get_uploadContentType() {
		return _uploadContentType;
	}

	public void set_uploadContentType(String contentType) {
		_uploadContentType = contentType;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public PeBzzCommInfoBatchService getPeBzzCommInfoBatchService() {
		return peBzzCommInfoBatchService;
	}

	public void setPeBzzCommInfoBatchService(
			PeBzzCommInfoBatchService peBzzCommInfoBatchService) {
		this.peBzzCommInfoBatchService = peBzzCommInfoBatchService;
	}

}

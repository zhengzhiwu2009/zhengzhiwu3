package com.whaty.platform.entity.web.action.recruit.baoming;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.sun.org.apache.bcel.internal.generic.DCMPG;
import com.whaty.platform.entity.service.recruit.PeBzzRecruitbatchService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzRecruit;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.exception.MessageException;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeBzzRecruitAction extends MyBaseAction<PeBzzRecruit> {
	
	//	 上传所用字段
	private File upload; // 文件
	private String uploadFileName; // 文件名属性
	private String uploadContentType; // 文件类型属性
	private String savePath; // 文件存储位置
	private String batchid ;
	private List<PeBzzBatch> peBzzBatch;
	
	private String eId;
	
	private String isSfe;//是二级企业？
	
	private String select_batch_id;	//所选择学期的id号
	
	private PeBzzBatch curBatch = null;
	
	private PeBzzRecruitbatchService peBzzRecruitbacthService;
	
	public File getUpload() {
		return upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public String getSavePath() {
		//return ServletActionContext.getServletContext().getRealPath(savePath);
		return ServletActionContext.getServletContext().getRealPath("/incoming/Excel");
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		boolean tagOp = true;
		if(this.getEId() != null && this.getIsSfe() != null && "0".equals(this.getIsSfe())) {
			tagOp = false;
		}
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us.getUserLoginType().equals("3"))
		{
			this.getGridConfig().setCapability(tagOp, tagOp, tagOp);
		}
		else
		{
			this.getGridConfig().setCapability(false, false, false);
		}
		
		
		this.getGridConfig().setTitle(this.getText("学员列表"));
		
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		
		this.getGridConfig().addColumn(this.getText("姓名"),"name");
		this.getGridConfig().addColumn(this.getText("性别"),"gender",false,true,true,Const.sex_for_extjs);
		this.getGridConfig().addColumn(this.getText("民族"),"folk",false,true,false,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("学历"),"education",false,true,true,Const.edu_for_extjs);
		this.getGridConfig().addColumn(this.getText("出生日期"),"birthdayDate",false);
		this.getGridConfig().addColumn(this.getText("手机"),"mobilePhone",true,true,true,"TextField",false,50,"");
//		this.getGridConfig().addColumn(this.getText("办公电话"),"phone",false,true,false,"TextField",true,50,Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("办公电话"),"phone",false,true,false,"TextField",true,20,"");
		this.getGridConfig().addColumn(this.getText("电子邮件"),"email",false,true,false,"TextField",true,150,Const.email_for_extjs);
		
//		this.getGridConfig().addColumn(this.getText("所在学期"),"peBzzBatch.name", false,false,true,"");	
		
		this.getGridConfig().addColumn(this.getText("注册状态"),"enumConstByFlagRegistState.name", true,false,true,"");
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		if(this.getEId() != null){
			
			this.getGridConfig().addMenuScript(this.getText("返回"),
			"{window.location='/entity/recruit/peRecEnterprise.action?backParam=true'}");
			session.setAttribute("eId", this.getEId());
		}
		
		
		ColumnConfig c_name1 = new ColumnConfig(this.getText("所在企业"),"peEnterprise.name");
		c_name1.setComboSQL("select t.id,t.name as p_name from pe_enterprise t where t.fk_parent_id is not null");
		this.getGridConfig().addColumn(c_name1);

		boolean showBatch = true;
		//增加学期处理
		if(null != this.getSelect_batch_id() && !"".equals(this.getSelect_batch_id())){
			session.setAttribute("select_batch_id", this.getSelect_batch_id());
			showBatch = false;
		}
		ColumnConfig c_name2 = new ColumnConfig(this.getText("学期"), "peBzzBatch.name", showBatch,true, true,"TextField",false,25,"");
		c_name2.setComboSQL("select b.id,b.name from pe_bzz_batch b");
		this.getGridConfig().addColumn(c_name2);
		this.getGridConfig().addColumn(this.getText("操作时间"),"luruDate",
				true,false,true,"TextField",false,5);
		if (us.getUserLoginType().equals("3"))
		{
		this.getGridConfig().addMenuScript(this.getText("导出重复报名学员"),
				"{" + "var searchData = s_formPanel.getForm().getValues(true);" +
				"window.location='/entity/manager/recruit/recruit_repeat_stu_excel.jsp?'+searchData;}");
		}
		
		//this.getGridConfig().addRenderFunction(this.getText("详细信息"), "<a href=\"peDetail_stuviewDetail.action?id=${value}\">查看详细信息</a>", "id");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass=PeBzzRecruit.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath="/entity/recruit/peBzzRecruit";
	}
	
	public void setBean(PeBzzRecruit instance){
		super.superSetBean(instance);
	}
	
	public PeBzzRecruit getBean() {
		return super.superGetBean();
	}
	
	public String batch(){
		/**
		 * 取得管理员类型.总站管理员不限制
		 */
		UserSession us = (UserSession)ServletActionContext
			.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";//保存管理员类型
		if (us!=null) {
			userLoginType = us.getUserLoginType();
		}
		if(userLoginType.equals("3")||userLoginType.equals("2")){
			DetachedCriteria bathdc = DetachedCriteria.forClass(PeBzzBatch.class);
			bathdc.add(Restrictions.eq("recruitSelected","1"));
			bathdc.addOrder(Order.asc("startDate"));
			try {
			this.setPeBzzBatch(this.getGeneralService().getList(bathdc));
			} catch (EntityException e) {
				e.printStackTrace();
			}
			return "batch";
		}else{
			this.setMsg("您的权限不够，无法进行此项操作!!!!");
		}
		return "msg";
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
			   count = this.peBzzRecruitbacthService.Bacthsave(filetest,batchid);
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
			return "uploadRecruit_result";
		} catch (MessageException e) {
			this.setMsg(e.getMessage());
			this.setTogo("back");
			return "uploadRecruit_result";
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "uploadRecruit_result";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		this.setTogo("back");
		return "uploadRecruit_result";
	}
	
	public List<PeBzzBatch> getPeBzzBatch() {
		return peBzzBatch;
	}

	public void setPeBzzBatch(List<PeBzzBatch> peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public PeBzzRecruitbatchService getPeBzzRecruitbacthService() {
		return peBzzRecruitbacthService;
	}

	public void setPeBzzRecruitbacthService(
			PeBzzRecruitbatchService peBzzRecruitbacthService) {
		this.peBzzRecruitbacthService = peBzzRecruitbacthService;
	}
	
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRecruit.class);
		dc.createCriteria("peEnterprise", "peEnterprise");
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.createCriteria("enumConstByFlagRegistState","enumConstByFlagRegistState", DetachedCriteria.LEFT_JOIN);
//		dc.add(Restrictions.eq("peBzzBatch.recruitSelected", "1"));
		if(this.getEId() != null) {
			if(this.getIsSfe() != null && this.getIsSfe().trim().equals("1")) {//是二级企业
				dc.add(Restrictions.eq("peEnterprise.id", this.getEId()));
			} else {
				dc.add(Restrictions.or(Restrictions.eq("peEnterprise.peEnterprise.id", this.getEId()), 
										Restrictions.eq("peEnterprise.id", this.getEId())));
			}
		}
		else
		{
			UserSession us = (UserSession) ServletActionContext.getRequest()
			.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			if(!us.getUserLoginType().equals("3")) {
				List<String> priEntIds = new ArrayList<String>();
				for(PeEnterprise pe : us.getPriEnterprises()) {
					priEntIds.add(pe.getId());
				}
				if(priEntIds.size() != 0) {
					dc.add(Restrictions.in("peEnterprise.id", priEntIds));
				} else {
					dc.add(Expression.eq("2", "1"));
				}
			}
		}
		//增加学期处理
		HttpSession session = ServletActionContext.getRequest().getSession();
		this.select_batch_id = (String) session.getAttribute("select_batch_id");
		if(null != this.getSelect_batch_id() && !"".equals(this.getSelect_batch_id())){
			dc.add(Restrictions.eq("peBzzBatch.id", this.getSelect_batch_id()));
		}
		return dc;
	}

	@Override
	public void checkBeforeAdd() throws EntityException {
		// TODO Auto-generated method stub
		//判断在同一企业下是否存在相同姓名和手机号组合
		UserSession us = (UserSession)ServletActionContext
		.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us == null) {
			throw new EntityException("无操作权限");
		}
		String luru_people=us.getLoginId();
		
		if(this.getBean().getPeEnterprise() == null || this.getBean().getPeEnterprise().getId() == null) {
			HttpSession session = ServletActionContext
			.getRequest().getSession();
			String teId = (String) session.getAttribute("eId");
			DetachedCriteria dc1 = DetachedCriteria.forClass(PeEnterprise.class);
			dc1.add(Restrictions.eq("id", teId));
			List l = this.getGeneralService().getList(dc1);
			PeEnterprise peEnterprise = (PeEnterprise) l.get(0);
			this.getBean().setPeEnterprise(peEnterprise);
			
		}
		
		
//		this.getBean().setPeBzzBatch(this.getCurBatch());
		this.getBean().setLuruDate(getCurSqlDate());
		this.getBean().setLuruPeople(luru_people);
		
		
		if (this.getBean().getId() == null || "".equals(this.getBean().getId())) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRecruit.class);
			dc.setProjection(Projections.property("id"));
			dc.add(Restrictions.eq("name", this.getBean().getName()));
			dc.add(Restrictions.eq("mobilePhone", this.getBean().getMobilePhone()));
			dc.createCriteria("peEnterprise", "peEnterprise");
			dc.add(Restrictions.eq("peEnterprise.id", this.getBean().getPeEnterprise().getId()));
			dc.createCriteria("peBzzBatch", "peBzzBatch");
			dc.add(Restrictions.eq("peBzzBatch.id", this.getBean().getPeBzzBatch().getId()));
			
			List idList = new ArrayList();
			try {
				idList = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if(idList!=null && idList.size()>0)
			{
				throw new EntityException("该企业下已经存在相同姓名与手机号组合。");
			}
		}
		
		this.getBean().setEnumConstByFlagRegistState(this.getDefaultFlagRegistState());
	}

	private EnumConst getDefaultFlagRegistState() throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
		dc.add(Restrictions.eq("namespace", "FlagRegistState"));
		dc.add(Restrictions.eq("isDefault", "1"));
		
		EnumConst ec = null;
		
		ec = (EnumConst) this.getGeneralService().getList(dc).get(0);
		return ec;
	}

	@Override
	public void checkBeforeUpdate() throws EntityException {
		
		UserSession us = (UserSession)ServletActionContext
		.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (us == null) {
			throw new EntityException("无操作权限");
		}
		String luru_people=us.getLoginId();
		
		if(this.getBean().getPeEnterprise() == null || this.getBean().getPeEnterprise().getId() == null) {
			DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzRecruit.class);
			dc1.createCriteria("peEnterprise", "peEnterprise");
			dc1.add(Restrictions.eq("id", this.getBean().getId()));
			List l = this.getGeneralService().getList(dc1);
			PeBzzRecruit peBzzRecruit = (PeBzzRecruit) l.get(0);
			this.getBean().setPeEnterprise(getEnterprise(this.getBean().getPeEnterprise()));
		}
		
		this.getBean().setPeBzzBatch(this.getCurBatch());
		this.getBean().setLuruDate(getCurSqlDate());
		this.getBean().setLuruPeople(luru_people);
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRecruit.class);
		dc.setProjection(Projections.property("id"));
		dc.add(Restrictions.eq("name", this.getBean().getName()));
		dc.add(Restrictions.eq("mobilePhone", this.getBean().getMobilePhone()));
		dc.createCriteria("peEnterprise", "peEnterprise");
		dc.add(Restrictions.eq("peEnterprise.id", this.getBean().getPeEnterprise().getId()));
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.add(Restrictions.eq("peBzzBatch.id", this.getBean().getPeBzzBatch().getId()));
		
		List pList=null;
		try {
			pList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String id="";
		if(pList!=null && pList.size()>0)
			id = pList.get(0).toString();
		if (pList!=null && id!=null && pList.size()>0 && !id.equals(this.getBean().getId())) {
			throw new EntityException("该企业下已经存在相同姓名与手机号组合。");
		}
	}
	
	private PeEnterprise getEnterprise(PeEnterprise peEnterprise) {
		if(peEnterprise == null) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeEnterprise.class);
		dc.add(Restrictions.eq("name", peEnterprise.getName()));
		try {
			List<PeEnterprise> list = this.getGeneralService().getList(dc);
			if(list != null && list.size()>0) {
				return list.get(0);
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void checkBeforeDelete(List idList) throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.add(Restrictions.in("peBzzRecruit.id", idList));
		
		List list = this.getGeneralService().getList(dc);
		
		if(list != null && list.size()!=0) {
			throw new EntityException("该学员报名信息已关联至正式信息表中，不能删除!");
		}
	}
	
	public String viewStudent() {
		
		return null;
	}

	public String getEId() {
		
		return eId;
	}

	public void setEId(String id) {
		eId = id;
	}

	public String getIsSfe() {
		return isSfe;
	}

	public void setIsSfe(String isSfe) {
		this.isSfe = isSfe.trim();
	}
	
	private PeBzzBatch getCurBatch() throws EntityException {
		if(this.curBatch== null) {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzBatch.class);
			dc.add(Restrictions.eq("recruitSelected","1"));
			
			List<PeBzzBatch> list = this.getGeneralService().getList(dc);
			if(list == null || list.size() == 0) {
				throw new EntityException("无当前报名学期");
			} else {
				this.curBatch = list.get(0);
			}
		}
		
		return this.curBatch;
	}
	
	private java.sql.Date getCurSqlDate()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		Date currentTime_2 = null;
		try {
			currentTime_2 = formatter.parse(dateString);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date d2 = new java.sql.Date(currentTime_2.getTime());
		return d2;  
	}

	public String getSelect_batch_id() {
		return select_batch_id;
	}

	public void setSelect_batch_id(String select_batch_id) {
		this.select_batch_id = select_batch_id;
	}
}

package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzCertificate;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.exception.MessageException;
import com.whaty.platform.entity.service.basic.PeBzzCertificateService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeBzzCertificateAction extends MyBaseAction<PeBzzCertificate>{
     
	private File upload; // 文件
	private String uploadFileName; // 文件名属性
	private String uploadContentType; // 文件类型属性
	private String savePath; // 文件存储位置
	private PeBzzCertificateService peBzzCertificateService;
	
	private List peBzzCertificates;
	
	/**
	 * 
	 */
     
	@Override
	public void initGrid() {
		
		this.setCanProjections(true);
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if(us.getUserLoginType().equals("3"))
		{
			this.getGridConfig().setCapability(false,true,true);
		}
		else
		{
			this.getGridConfig().setCapability(false,false,false);
		}
		
		this.getGridConfig().setTitle("证书信息");
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		//this.getGridConfig().addColumn(this.getText("学号"),"peBzzStudent.regNo",true,false,true,"TextField");
		this.getGridConfig().addColumn(this.getText("学号"),"peBzzStudent.regNo",true,true, true, "");
		this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.trueName",true,true, true, "");
		this.getGridConfig().addColumn(this.getText("移动电话"),"peBzzStudent.mobilePhone",true,false, true, "");
		//this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.trueName",true,false,true,"TextField",true,100);
		//this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.name",false,true,false,"TextField",false,100);
		this.getGridConfig().addColumn(this.getText("学期"),"peBzzStudent.peBzzBatch.name",true,
				false, true, "");
		this.getGridConfig().addColumn(this.getText("考试批次"),"peBzzExamBatch.name",true,
				false, false, "");
		//this.getGridConfig().addColumn(this.getText("学期"),"peBzzStudent.peBzzBatch.name",true,true,true,"TextField",false,50);
		ColumnConfig c_name1 = new ColumnConfig(this.getText("所在企业"),"peBzzStudent.peEnterprise.name");
		c_name1.setComboSQL("select t.id,t.name as p_name from pe_enterprise t where t.fk_parent_id is not null");
		this.getGridConfig().addColumn(c_name1);
		this.getGridConfig().addColumn(this.getText("证书号"),"certificate",true,true, true, "");
		
		if(us.getUserLoginType().equals("3"))
		{
			this.getGridConfig().addColumn(this.getText("证书打印时间"),"printDate",false,false, true, "");
			
			this.getGridConfig().addMenuFunction(this.getText("打印证书"),
					"/entity/basic/peBzzCertificate_certificatePrint.action?",
					false, false);
			
			this.getGridConfig().addMenuFunction(this.getText("打印特殊证书"),
					"/entity/basic/peBzzCertificate_certificatePrintSpecial.action?",
					false, false);
			
			this.getGridConfig().addMenuFunction(this.getText("打印成绩单"),
					"/entity/basic/peBzzCertificate_scorePrint.action?",
					false, false);
			
			this.getGridConfig().addMenuScript(this.getText("批量打印当前考试批次下证书"),
					"{" + "window.open('/entity/manager/basic/certificate_batch_print.jsp?')}");
			
			this.getGridConfig().addMenuScript(this.getText("批量打印当前考试批次下成绩单"),
					"{" + "window.open('/entity/manager/basic/score_batch_print.jsp?')}");
		}
	}
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzCertificate.class);
		dc.createCriteria("peBzzExamBatch", "peBzzExamBatch", DetachedCriteria.LEFT_JOIN);
		//dc.createCriteria("peBzzStudent", "peBzzStudent", DetachedCriteria.INNER_JOIN);
		DetachedCriteria pebzz =dc.createCriteria("peBzzStudent","peBzzStudent", DetachedCriteria.INNER_JOIN);
		pebzz.createCriteria("peBzzBatch", "peBzzBatch", DetachedCriteria.INNER_JOIN);
		pebzz.createCriteria("peEnterprise", "peEnterprise", DetachedCriteria.INNER_JOIN);
		
		DetachedCriteria dct = DetachedCriteria.forClass(PeBzzStudent.class);
		dct.createAlias("peEnterprise", "peEnterprise",DetachedCriteria.LEFT_JOIN);
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		if(!us.getUserLoginType().equals("3")) {
			List<String> priEntIds = new ArrayList<String>();
			for(PeEnterprise pe : us.getPriEnterprises()) {
				priEntIds.add(pe.getId());
			}
			if(priEntIds.size() != 0) {
				dc.add(Restrictions.in("peBzzStudent.peEnterprise.id", priEntIds));
			} else {
				dc.add(Expression.eq("2", "1"));
			}
		}
		return dc;
	}
	public String ups(){
		UserSession us = (UserSession)ServletActionContext
		.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";//保存管理员类型
		if (us!=null) {
			userLoginType = us.getUserLoginType();
		}
		if(userLoginType.equals("3")){
			return "ups";
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
			   //count = this.getPeBzzExamScoreService().Bacthsave(filetest, batchid);
			   count = this.getPeBzzCertificateService().Bacthsave(filetest);
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
			return "uploadCertificate_result";
		} catch (MessageException e) {
			this.setMsg(e.getMessage());
			this.setTogo("back");
			return "uploadCertificate_result";
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "uploadCertificate_result";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		this.setTogo("back");
		return "uploadCertificate_result";
	}
	
	public String certificatePrint()
	{	
		List<PeBzzCertificate> list = new ArrayList <PeBzzCertificate>(); 
		Map session =  ActionContext.getContext().getSession();
		for(int i=0;i<this.getIds().split(",").length;i++){
			DetachedCriteria detachedCriteria = DetachedCriteria
			.forClass(PeBzzCertificate.class);
			DetachedCriteria student = detachedCriteria.createCriteria("peBzzStudent", "peBzzStudent", DetachedCriteria.INNER_JOIN);
			student.createCriteria("peEnterprise", "peEnterprise", DetachedCriteria.INNER_JOIN);
			student.createCriteria("peBzzBatch", "peBzzBatch");
			detachedCriteria.add(Restrictions.eq("id", this.getIds().split(",")[i]));
			PeBzzCertificate peBzzCertificate = null;
			try {
				peBzzCertificate = this.getGeneralService().getCertificate(detachedCriteria);
				if(peBzzCertificate!=null)
				{
					list.add(peBzzCertificate);
				}
				
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
		}
		this.setPeBzzCertificates(list);
		session.put("peBzzCertificates", peBzzCertificates);
		return "certificatePrint";
	}
	public String certificatePrintSpecial()
	{	
		List<PeBzzCertificate> list = new ArrayList <PeBzzCertificate>(); 
		Map session =  ActionContext.getContext().getSession();
		for(int i=0;i<this.getIds().split(",").length;i++){
			DetachedCriteria detachedCriteria = DetachedCriteria
			.forClass(PeBzzCertificate.class);
			DetachedCriteria student = detachedCriteria.createCriteria("peBzzStudent", "peBzzStudent", DetachedCriteria.INNER_JOIN);
			student.createCriteria("peEnterprise", "peEnterprise", DetachedCriteria.INNER_JOIN);
			student.createCriteria("peBzzBatch", "peBzzBatch");
			detachedCriteria.add(Restrictions.eq("id", this.getIds().split(",")[i]));
			PeBzzCertificate peBzzCertificate = null;
			try {
				peBzzCertificate = this.getGeneralService().getCertificate(detachedCriteria);
				if(peBzzCertificate!=null)
				{
					list.add(peBzzCertificate);
				}
				
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
		}
		this.setPeBzzCertificates(list);
		session.put("peBzzCertificates", peBzzCertificates);
		return "certificatePrintSpecial";
	}
	public String scorePrint()
	{	
		List<PeBzzCertificate> list = new ArrayList <PeBzzCertificate>(); 
		Map session =  ActionContext.getContext().getSession();
		for(int i=0;i<this.getIds().split(",").length;i++){
			DetachedCriteria detachedCriteria = DetachedCriteria
			.forClass(PeBzzCertificate.class);
			DetachedCriteria student = detachedCriteria.createCriteria("peBzzStudent", "peBzzStudent", DetachedCriteria.INNER_JOIN);
			student.createCriteria("peEnterprise", "peEnterprise");
			student.createCriteria("peBzzBatch", "peBzzBatch");
			detachedCriteria.add(Restrictions.eq("id", this.getIds().split(",")[i]));
			PeBzzCertificate peBzzCertificate = null;
			try {
				peBzzCertificate = this.getGeneralService().getCertificate(detachedCriteria);
				if(peBzzCertificate!=null)
				{
					list.add(peBzzCertificate);
				}
				
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
		}
		this.setPeBzzCertificates(list);
		session.put("peBzzCertificates", peBzzCertificates);
		return "scorePrint";
	}
	
	public void setEntityClass() {
		this.entityClass = PeBzzCertificate.class;
	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peBzzCertificate";
	}

	public void setBean(PeBzzCertificate instance) {
		super.superSetBean(instance);
	}

	public PeBzzCertificate getBean() {
		return super.superGetBean();
	}
	
	//add校验
	
	public void checkBeforeAdd() throws EntityException {
		
	}
	
	//update校验
	
	public void checkBeforeUpdate() throws EntityException {
		
	}
	public String getSavePath() {
		return ServletActionContext.getServletContext().getRealPath("/incoming/Excel");
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
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
	public PeBzzCertificateService getPeBzzCertificateService() {
		return peBzzCertificateService;
	}
	public void setPeBzzCertificateService(
			PeBzzCertificateService peBzzCertificateService) {
		this.peBzzCertificateService = peBzzCertificateService;
	}
	public List getPeBzzCertificates() {
		return peBzzCertificates;
	}
	public void setPeBzzCertificates(List peBzzCertificates) {
		this.peBzzCertificates = peBzzCertificates;
	}
	
}
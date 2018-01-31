package com.whaty.platform.entity.web.action.recruit.baoming;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.whaty.platform.entity.bean.PeBzzEditStudent;
import com.whaty.platform.entity.bean.PeBzzExamLate;
import com.whaty.platform.entity.bean.PeBzzRecruit;
import com.whaty.platform.entity.bean.PeBzzRecruitBefore;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.exception.MessageException;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeBzzRecruitBeforeAction extends MyBaseAction<PeBzzRecruitBefore> {
	
	//	 上传所用字段
	private File upload; // 文件
	private String uploadFileName; // 文件名属性
	private String uploadContentType; // 文件类型属性
	private String savePath; // 文件存储位置
	private String batchid ;
	private List<PeBzzBatch> peBzzBatch;
	private PeBzzBatch curBatch = null;
	private String beforeId;
	private PeBzzRecruitbatchService peBzzRecruitbacthService;
	private PeBzzRecruitBefore peBzzRecruitBefore;
	private String operateresult;
	private String sign_up_num;
	private String communicateRecord;
	private String note;
	

	public String getSign_up_num() {
		return sign_up_num;
	}

	public void setSign_up_num(String sign_up_num) {
		this.sign_up_num = sign_up_num;
	}

	public String getCommunicateRecord() {
		return communicateRecord;
	}

	public void setCommunicateRecord(String communicateRecord) {
		this.communicateRecord = communicateRecord;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	public PeBzzRecruitBefore getPeBzzRecruitBefore() {
		return peBzzRecruitBefore;
	}

	public void setPeBzzRecruitBefore(PeBzzRecruitBefore peBzzRecruitBefore) {
		this.peBzzRecruitBefore = peBzzRecruitBefore;
	}

	public PeBzzRecruitbatchService getPeBzzRecruitbacthService() {
		return peBzzRecruitbacthService;
	}

	public void setPeBzzRecruitbacthService(
			PeBzzRecruitbatchService peBzzRecruitbacthService) {
		this.peBzzRecruitbacthService = peBzzRecruitbacthService;
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass=PeBzzRecruitBefore.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath="/entity/recruit/peBzzRecruitBefore";
	}
	
	public void setBean(PeBzzRecruitBefore instance){
		super.superSetBean(instance);
	}
	
	public PeBzzRecruitBefore getBean() {
		return super.superGetBean();
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
			   count = this.peBzzRecruitbacthService.BacthBeforesave(filetest,this.getCurBatch());
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
			return "uploadRecruit_result";
		} catch (MessageException e) {
			this.setMsg(e.getMessage());
			this.setTogo("back");
			return "uploadRecruit_result";
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			this.setMsg("Excel表格读取异常！批量添加失败！");
			return "uploadRecruit_result";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		this.setTogo("back");
		return "uploadRecruit_result";
	}
	public String info() {
		DetachedCriteria before = DetachedCriteria.forClass(PeBzzRecruitBefore.class);
		before.add(Restrictions.eq("id",this.getBeforeId()));
		
		PeBzzRecruitBefore peBzzRecruitBefore = null;

		try {
			peBzzRecruitBefore = (PeBzzRecruitBefore) this.getGeneralService().getPeBzzRecruitBefore(before);
			
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setPeBzzRecruitBefore(peBzzRecruitBefore);
		return "before_info";
	}
	public String edit() {
		DetachedCriteria before = DetachedCriteria.forClass(PeBzzRecruitBefore.class);
		before.add(Restrictions.eq("id",this.getBeforeId()));
		
		PeBzzRecruitBefore peBzzRecruitBefore = null;
        
		try {
			peBzzRecruitBefore = (PeBzzRecruitBefore) this.getGeneralService().getPeBzzRecruitBefore(before);
			peBzzRecruitBefore.setSignUpNum(this.getSign_up_num());
			peBzzRecruitBefore.setCommunicateRecord(this.getCommunicateRecord());
			peBzzRecruitBefore.setNote(this.getNote());
			
			PeBzzRecruitBefore beforesign = (PeBzzRecruitBefore) this.getGeneralService().save(peBzzRecruitBefore);
			if (beforesign != null) {
				operateresult = "1";
			} else {
				operateresult = "2";
			}
			} catch (Exception e) {
				e.printStackTrace();
				operateresult = "2";
				return "edit";
			}
			return "edit";
	}
	 public boolean isNumeric1(String str)
		{
			Pattern pattern = Pattern.compile("^-?[0-9]+");//允许负数
			Matcher isNum = pattern.matcher(str);
			if(!isNum.matches())
			{
				return false;
			}
				return true;
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

	public String getSavePath() {
		return ServletActionContext.getServletContext().getRealPath("/incoming/Excel");
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public List<PeBzzBatch> getPeBzzBatch() {
		return peBzzBatch;
	}

	public void setPeBzzBatch(List<PeBzzBatch> peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}
	
	public void setCurBatch(PeBzzBatch curBatch) {
		this.curBatch = curBatch;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

	public String getBeforeId() {
		return beforeId;
	}

	public void setBeforeId(String beforeId) {
		this.beforeId = beforeId;
	}

	
}

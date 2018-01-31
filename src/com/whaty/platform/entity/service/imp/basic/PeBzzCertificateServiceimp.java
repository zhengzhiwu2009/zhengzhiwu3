package com.whaty.platform.entity.service.imp.basic;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzCertificate;
import com.whaty.platform.entity.bean.PeBzzExamBatch;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzRecruit;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.exception.MessageException;
import com.whaty.platform.entity.service.basic.PeBzzCertificateService;
import com.whaty.platform.entity.service.exam.PeBzzExamScoreService;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.entity.service.recruit.PeBzzRecruitbatchService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.util.RandomString;

public class PeBzzCertificateServiceimp extends GeneralServiceImp implements PeBzzCertificateService {

public int Bacthsave(File file) throws EntityException, MessageException {
		
		StringBuffer msg = new StringBuffer();
		StringBuffer msgStu = new StringBuffer();
		
		int count = 0;
		int jsnum=0;
		Workbook work = null;
		boolean check = false;
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			try {
				throw new EntityException(msg.toString());
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		
		Set<PeBzzCertificate> certificateSet = new HashSet<PeBzzCertificate>();
		
		//开始导入成绩信息
		for(int i=2;i<rows;i++)
		{
			String reg_no=fixnull(sheet.getCell(0, i).getContents().trim()); //学号 
			//String name=fixnull(sheet.getCell(1, i).getContents().trim()); //姓名
			//name=name.replace(" ", "");
			//name=name.replace("　", "");
			//name = name.replace(" ", "");
			String certificate=fixnull(sheet.getCell(2, i).getContents().trim()); //证书号
			certificate=certificate.replace(" ", "");
			certificate=certificate.replace("　", "");
			certificate =certificate.replace(" ", "");
			
			if(isNumeric(reg_no)&&!reg_no.equals(""))
			{
				//判断该学员是否存在
				DetachedCriteria studc = DetachedCriteria
				.forClass(PeBzzStudent.class);
				studc.add(Restrictions.eq("regNo", reg_no));
				//studc.add(Restrictions.eq("trueName", name));
				
				PeBzzStudent pebzzstudent = this.getGeneralDao().getStudentInfo(studc);
				//System.out.println("pebzzstudent==="+pebzzstudent);
				//System.out.println("pebzzstudent.id==="+pebzzstudent.getId());
				if(pebzzstudent==null)
				{
					msg.append("第" + (i + 1) + "行数据，该学员不存在！<br/>");
					continue;
				}
				DetachedCriteria dc = DetachedCriteria.forClass(PeBzzExamBatch.class);
				dc.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
				dc.add(Restrictions.eq("enumConstByFlagExamBatch.id", "402880a91dadc115011dadfcf26b00aa"));
				PeBzzExamBatch peBzzExamBatch = null;
				peBzzExamBatch = (PeBzzExamBatch) this.getGeneralDao().getList(dc).get(0);
				PeBzzCertificate pebzzcertificate = new PeBzzCertificate();	
				//判断该学员是否已经录入过证书号
				DetachedCriteria baodc = DetachedCriteria.forClass(PeBzzCertificate.class);
				baodc.createCriteria("peBzzStudent", "peBzzStudent");
				baodc.add(Restrictions.eq("peBzzStudent.regNo",reg_no));
				
				pebzzcertificate = this.getGeneralDao().getCertificate(baodc);
				if(pebzzcertificate==null || pebzzcertificate.equals(""))
				{
					pebzzcertificate = new PeBzzCertificate();	
					pebzzcertificate.setCertificate(certificate);
					pebzzcertificate.setPeBzzStudent(pebzzstudent);
					pebzzcertificate.setPeBzzExamBatch(peBzzExamBatch);
					//certificateSet.add(pebzzcertificate);
					
					this.getGeneralDao().save(pebzzcertificate);
					count++;
				}
				else
				{
					pebzzcertificate.setCertificate(certificate);
					//certificateSet.add(pebzzcertificate);
					
					this.getGeneralDao().update(pebzzcertificate);
					count++;
				}
				
				//PeBzzCertificate pebzzcertificate=this.getGeneralDao().getCertificate(baodc);
				
				//this.getGeneralDao().save(pebzzrecruit);
			}
		}
		
		if (msg.length() > 0) {
			msg.append("学生证书信息批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}
		
		if(msgStu.length() > 0) {
			throw new MessageException("共" + count + "条数据上传成功！<br/>"+msgStu.toString());
		}
		return count;
	}

	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("")) {
			str = "";
	    } else {
	    	str = str.replace("　", "").trim();
	    	str = str.replace("\n", "").trim();
	    	str = str.replace("\r", "").trim();
	    }
			return str;
	}
	String fixNull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("")) {
			str = "0";
	    } 
		return str;
	}
	
	public boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if(!isNum.matches())
		{
			return false;
		}
			return true;
	}
	
}
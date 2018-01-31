package com.whaty.platform.entity.service.imp.exam;

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
import com.whaty.platform.entity.service.exam.PeBzzExamScoreService;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.entity.service.recruit.PeBzzRecruitbatchService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.util.RandomString;

public class PeBzzExamScoreServiceimp extends GeneralServiceImp implements PeBzzExamScoreService {
	
	public int Bacthsave(File file, String id) throws EntityException, MessageException {
		
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
		
		Set<PeBzzExamScore> scoreSet = new HashSet<PeBzzExamScore>();
		
		//开始导入成绩信息
		for(int i=2;i<rows;i++)
		{
			String reg_no=fixnull(sheet.getCell(0, i).getContents().trim()); //学号 
			/*String name=fixnull(sheet.getCell(1, i).getContents().trim()); //姓名
			name=name.replace(" ", "");
			name=name.replace("　", "");
			name = name.replace(" ", "");*/
			String subscore=fixNull(sheet.getCell(1, i).getContents().trim()); //主观题成绩
			subscore=subscore.replace(" ", "");
			subscore=subscore.replace("　", "");
			subscore =subscore.replace(" ", "");
			
			String objscore=fixNull(sheet.getCell(2, i).getContents().trim()); //客观题成绩
			objscore=objscore.replace(" ", "");
			objscore=objscore.replace("　", "");
			objscore =objscore.replace(" ", "");
			
			String examStatus=fixnull(sheet.getCell(3, i).getContents().trim()); //考试状态
			examStatus=examStatus.replace(" ", "");
			examStatus=examStatus.replace("　", "");
			examStatus =examStatus.replace(" ", "");
			
			
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
				
				//判断该学员该批次下是否已经报名
				DetachedCriteria baodc = DetachedCriteria
				.forClass(PeBzzExamScore.class);
				
				baodc.createCriteria("peBzzExamBatch", "peBzzExamBatch");
				baodc.createCriteria("peBzzStudent", "peBzzStudent");
				baodc.add(Restrictions.eq("peBzzExamBatch.id", id));
				baodc.add(Restrictions.eq("peBzzStudent.regNo",reg_no));
				
				List<PeBzzExamScore> s_list = this.getGeneralDao().getList(baodc);
				
				if(s_list==null||s_list.size()==0)
				{
					msg.append("第" + (i + 1) + "行数据，该学员没有报名！<br/>");
					continue;
				}
				
				//PeBzzExamScore pebzzexamscore=new PeBzzExamScore();
				PeBzzExamScore pebzzexamscore=this.getGeneralDao().getExamScore(baodc);
				pebzzexamscore.setObjScore(objscore);
				pebzzexamscore.setSubScore(subscore);
				String examscore=Double.toString((Double.parseDouble(objscore)+Double.parseDouble(subscore)));
				pebzzexamscore.setScore(examscore);
				
				EnumConst enumConstByFlagExamStatus = this.getGeneralDao().getEnumConstByNamespaceName("FlagExamStatus", examStatus) ;
				
				pebzzexamscore.setEnumConstByFlagExamStatus(enumConstByFlagExamStatus);
				pebzzexamscore.setPeBzzStudent(pebzzstudent);
				
				scoreSet.add(pebzzexamscore);
				//this.getGeneralDao().save(pebzzrecruit);
			}
		}
		
		if (msg.length() > 0) {
			msg.append("学生成绩信息批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}
		
		for (PeBzzExamScore score : scoreSet) {
			try {
				this.getGeneralDao().update(score);
				count++;
			} catch (Exception e) {
				e.printStackTrace();
					throw new EntityException("批量上传学生成绩失败");

			}
		}
		
		if(msgStu.length() > 0) {
			throw new MessageException("共" + count + "条数据上传成功！<br/>"+msgStu.toString());
		}
		return count;
	}
public int Bacthsave(File file, String id,String score_type) throws EntityException, MessageException {
		
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
		
		Set<PeBzzExamScore> scoreSet = new HashSet<PeBzzExamScore>();
		
		//开始导入成绩信息
		for(int i=2;i<rows;i++)
		{
			String objscore="";
			String subscore="";
			String reg_no=fixnull(sheet.getCell(0, i).getContents().trim()); //学号 
			String name=fixnull(sheet.getCell(1, i).getContents().trim()); //姓名
			name=name.replace(" ", "");
			name=name.replace("　", "");
			name = name.replace(" ", "");
			if(score_type.equals("obj_score"))
			{
				objscore=fixnull(sheet.getCell(2, i).getContents().trim()); //客观题成绩
				objscore=objscore.replace(" ", "");
				objscore=objscore.replace("　", "");
				objscore =objscore.replace(" ", "");
			}
			else if(score_type.equals("sub_score"))
			{
				subscore=fixnull(sheet.getCell(2, i).getContents().trim()); //主观题成绩
				subscore=subscore.replace(" ", "");
				subscore=subscore.replace("　", "");
				subscore =subscore.replace(" ", "");
			}
			
			
			
			if(isNumeric(reg_no)&&!reg_no.equals(""))
			{
				//判断该学员是否存在
				DetachedCriteria studc = DetachedCriteria
				.forClass(PeBzzStudent.class);
				studc.add(Restrictions.eq("regNo", reg_no));
				studc.add(Restrictions.eq("trueName", name));
				
				PeBzzStudent pebzzstudent = this.getGeneralDao().getStudentInfo(studc);
				//System.out.println("pebzzstudent==="+pebzzstudent);
				//System.out.println("pebzzstudent.id==="+pebzzstudent.getId());
				if(pebzzstudent==null)
				{
					msg.append("第" + (i + 1) + "行数据，该学员不存在！<br/>");
					continue;
				}
				
				//判断该学员该批次下是否已经报名
				DetachedCriteria baodc = DetachedCriteria
				.forClass(PeBzzExamScore.class);
				
				baodc.createCriteria("peBzzExamBatch", "peBzzExamBatch");
				baodc.createCriteria("peBzzStudent", "peBzzStudent");
				baodc.add(Restrictions.eq("peBzzExamBatch.id", id));
				baodc.add(Restrictions.eq("peBzzStudent.regNo",reg_no));
				
				List<PeBzzExamScore> s_list = this.getGeneralDao().getList(baodc);
				
				if(s_list==null||s_list.size()==0)
				{
					msg.append("第" + (i + 1) + "行数据，该学员没有报名！<br/>");
					continue;
				}
				
				//PeBzzExamScore pebzzexamscore=new PeBzzExamScore();
				PeBzzExamScore pebzzexamscore=this.getGeneralDao().getExamScore(baodc);
				
				if(score_type.equals("obj_score"))
				{
					pebzzexamscore.setObjScore(objscore);
					subscore=fixNull(pebzzexamscore.getSubScore());
				}
				else if(score_type.equals("sub_score"))
				{
					pebzzexamscore.setSubScore(subscore);
					objscore=fixNull(pebzzexamscore.getObjScore());
				}
				//pebzzexamscore.setObjScore(objscore);
				//pebzzexamscore.setSubScore(subscore);
				String examscore=Integer.toString((Integer.parseInt(objscore)+Integer.parseInt(subscore)));
				pebzzexamscore.setScore(examscore);
				pebzzexamscore.setPeBzzStudent(pebzzstudent);
				
				scoreSet.add(pebzzexamscore);
				//this.getGeneralDao().save(pebzzrecruit);
			}
		}
		
		if (msg.length() > 0) {
			msg.append("学生成绩信息批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}
		
		for (PeBzzExamScore score : scoreSet) {
			try {
				this.getGeneralDao().update(score);
				count++;
			} catch (Exception e) {
				e.printStackTrace();
					throw new EntityException("批量上传学生成绩失败");

			}
		}
		
		if(msgStu.length() > 0) {
			throw new MessageException("共" + count + "条数据上传成功！<br/>"+msgStu.toString());
		}
		return count;
	}
	public int BacthExamsave(File file, String id) throws EntityException, MessageException {
		
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
		
		Set<PeBzzExamScore> scoreSet = new HashSet<PeBzzExamScore>();
		
		//开始导入考试信息——第一行开始
		for(int i=1;i<rows;i++)
		{
			
			String reg_no=fixnull(sheet.getCell(0, i).getContents().trim()); //学号 
			reg_no=reg_no.replace(" ", "");
			reg_no=reg_no.replace("　", "");
			reg_no = reg_no.replace(" ", "");
			
			String testcard_id=fixnull(sheet.getCell(1, i).getContents().trim()); //准考证号
			testcard_id=testcard_id.replace(" ", "");
			testcard_id=testcard_id.replace("　", "");
			testcard_id = testcard_id.replace(" ", "");
			
			String exam_time=fixnull(sheet.getCell(3, i).getContents().trim()); //考试时间
			//由于考试时间中日期与时间之间有一个空格必须存在，取消下列转换操作2011-11-19mhy
//			exam_time=exam_time.replace(" ", "");
//			exam_time=exam_time.replace("　", "");
//			exam_time = exam_time.replace(" ", "");
			
			String room_id=fixnull(sheet.getCell(4, i).getContents().trim()); //考场名称
			room_id=room_id.replace(" ", "");
			room_id=room_id.replace("　", "");
			room_id = room_id.replace(" ", "");
			
			String desk_no=fixnull(sheet.getCell(5, i).getContents().trim()); //座位号
			desk_no=desk_no.replace(" ", "");
			desk_no=desk_no.replace("　", "");
			desk_no = desk_no.replace(" ", "");
			
			String address=fixnull(sheet.getCell(6, i).getContents().trim()); //考场地址
			address=address.replace(" ", "");
			address=address.replace("　", "");
			address = address.replace(" ", "");
			
			String note=fixnull(sheet.getCell(7, i).getContents().trim()); //乘车路线
			note=note.replace(" ", "");
			note=note.replace("　", "");
			note = note.replace(" ", "");
			
			String phone=fixnull(sheet.getCell(8, i).getContents().trim()); //联系电话
			phone=phone.replace(" ", "");
			phone=phone.replace("　", "");
			phone = phone.replace(" ", "");
			
			
			if(isNumeric(reg_no)&&!reg_no.equals(""))
			{
				//判断该学员是否存在
				DetachedCriteria studc = DetachedCriteria
				.forClass(PeBzzStudent.class);
				studc.add(Restrictions.eq("regNo", reg_no));
				
				PeBzzStudent pebzzstudent = this.getGeneralDao().getStudentInfo(studc);
				if(pebzzstudent==null)
				{
					msg.append("第" + (i + 1) + "行数据，该学员不存在！<br/>");
					continue;
				}
				
				//判断该学员该批次下是否已经报名
				DetachedCriteria baodc = DetachedCriteria
				.forClass(PeBzzExamScore.class);
				
				baodc.createCriteria("peBzzExamBatch", "peBzzExamBatch");
				baodc.createCriteria("peBzzStudent", "peBzzStudent");
				baodc.add(Restrictions.eq("peBzzExamBatch.id", id));
				baodc.add(Restrictions.eq("peBzzStudent.regNo",reg_no));
				
				List<PeBzzExamScore> s_list = this.getGeneralDao().getList(baodc);
				
				if(s_list==null||s_list.size()==0)
				{
					msg.append("第" + (i + 1) + "行数据，该学员没有报名！<br/>");
					continue;
				}
				
				//PeBzzExamScore pebzzexamscore=new PeBzzExamScore();
				PeBzzExamScore pebzzexamscore=this.getGeneralDao().getExamScore(baodc);
				pebzzexamscore.setAddress(address);
				pebzzexamscore.setDesk_no(desk_no);
//				pebzzexamscore.setNote(note);
				pebzzexamscore.setPhone(phone);
				pebzzexamscore.setRoom_id(room_id);
				pebzzexamscore.setTestcard_id(testcard_id);
				pebzzexamscore.setExam_time(exam_time);
				pebzzexamscore.setPeBzzStudent(pebzzstudent);
				
				scoreSet.add(pebzzexamscore);
				//this.getGeneralDao().save(pebzzrecruit);
			}
		}
		
		if (msg.length() > 0) {
			msg.append("学生考试信息批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}
		
		for (PeBzzExamScore score : scoreSet) {
			try {
				this.getGeneralDao().update(score);
				count++;
			} catch (Exception e) {
				e.printStackTrace();
					throw new EntityException("批量上传学生考试信息失败");

			}
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

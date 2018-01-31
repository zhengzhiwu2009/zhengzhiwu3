package com.whaty.platform.entity.service.imp.basic;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.whaty.platform.entity.bean.PeBzzCommInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeBzzCommInfoBatchService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.util.RandomString;
import com.whaty.util.string.AttributeManage;
import com.whaty.util.string.WhatyAttributeManage;

public class PeBzzCommInfoBacthServiceImpl implements PeBzzCommInfoBatchService {

	private GeneralDao<PeBzzCommInfo> generalDao;

	public int Bacthsave(File filetest)throws EntityException {
		StringBuffer msg = new StringBuffer();
		int count = 0;

		Workbook work = null;
		try {
			work = Workbook.getWorkbook(filetest);
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
		String temp = "";
		Set<PeBzzCommInfo> commInfoSet = new HashSet();
		for (int i = 1; i < rows; i++) {
			
			PeBzzCommInfo pebzzcommInfo=new PeBzzCommInfo();
			
			temp = sheet.getCell(0, i).getContents().trim();   //学号
			if(temp==null ||temp.equals(""))
			{
				msg.append("第" + (i + 1) + "行数据，学号不能为空！<br/>");
				continue;
			}
			else{
				if(temp.length()!=12)
				{
					msg.append("第" + (i + 1) + "行数据，学号长度不正确，必须为12位！<br/>");
					continue;
				}
				DetachedCriteria testdc = DetachedCriteria.forClass(PeBzzStudent.class);
				testdc.add(Restrictions.eq("regNo", temp));
				List<PeBzzStudent> list = this.generalDao.getList(testdc);
				if(list.size()==0||list.get(0)==null){
					msg.append("第" + (i + 1) + "行数据，学号在系统中不存在对应学员！<br/>");
					continue;
				}
				pebzzcommInfo.setRegNo(temp);
			}
			temp = sheet.getCell(1, i).getContents().trim();  //沟通时间，要求yyyymmdd格式
			if(temp==null ||temp.equals(""))
			{
				msg.append("第" + (i + 1) + "行数据，沟通时间不能为空！<br/>");
				continue;
			}
			else{
				if(temp.length()!=8)
				{
					msg.append("第" + (i + 1) + "行数据，沟通时间长度不正确，必须为8位！<br/>");
					continue;
				}
				String year = temp.substring(0, 4);
				String month = temp.substring(4, 6);
				String day = temp.substring(6, 8);
				if (!isNumeric(year) || !isNumeric(month) || !isNumeric(day)) {
					msg.append("第" + (i + 1) + "行数据，沟通时间不为整数！<br/>");
					continue;
				}
				temp = year + "-" + month + "-" + day;
				Date commDate = this.StringtoDate(temp, "yyyy-MM-dd");
				pebzzcommInfo.setCommDate(commDate);
			}
			
			temp = sheet.getCell(2, i).getContents().trim();   //沟通时已完成学时
			pebzzcommInfo.setFinishTime(temp);
			
			temp = sheet.getCell(3, i).getContents().trim();   //备注
			if(temp!=null && temp.length()>200)
			{
				msg.append("第" + (i + 1) + "行数据，备注长度不能超过200个字！<br/>");
				continue;
			}
			pebzzcommInfo.setBz(temp);
			
			UserSession us = (UserSession)ServletActionContext
			.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			if (us == null) {
				throw new EntityException("无操作权限");
			}
			String luru_people=us.getLoginId();
			
			pebzzcommInfo.setLuruDate(this.getCurSqlDate());
			pebzzcommInfo.setLuruPeople(luru_people);
			
			if (!commInfoSet.add(pebzzcommInfo)) {
				msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
				continue;
			}
		}
		
		if (msg.length() > 0) {
			msg.append("批量上传失败，请修改以上错误之后重新上传！<br/>");
				throw new EntityException(msg.toString());
		}
		
		for (PeBzzCommInfo commInfo : commInfoSet) {
			try {
				this.getGeneralDao().save(commInfo);
				count++;
			} catch (Exception e) {
				e.printStackTrace();

				throw new EntityException("批量上传沟通记录信息失败");

			}
		}
		return count;
	}
	
	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	private Date StringtoDate(String temp, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date tempDate = null;
		try {
			tempDate = df.parse(temp);
		} catch (ParseException e) {
			System.out.println("格式错误");
			e.printStackTrace();
		}
		return tempDate;
	}

	/**
	 * 验证日期
	 * 
	 * @param date
	 * @return
	 */
	private boolean isDate(String date) {
		AttributeManage manage = new WhatyAttributeManage();
		try {
			String[] strings = date.split("-");
			if (strings.length != 3)
				return false;
			return manage.isValidDate(strings[0], strings[1], strings[2]);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public GeneralDao<PeBzzCommInfo> getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao<PeBzzCommInfo> generalDao) {
		this.generalDao = generalDao;
	}
	
	public PeBzzCommInfo save(PeBzzCommInfo bean) {
		return this.generalDao.save(bean);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.sql.Date d2 = new java.sql.Date(currentTime_2.getTime());
		return d2;  
	}

}

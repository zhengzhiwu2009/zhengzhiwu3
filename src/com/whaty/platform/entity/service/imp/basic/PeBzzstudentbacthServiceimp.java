package com.whaty.platform.entity.service.imp.basic;

import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.AssignRecord;
import com.whaty.platform.entity.bean.AssignRecordStudent;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzEditStudent;
import com.whaty.platform.entity.bean.PeBzzPici;
import com.whaty.platform.entity.bean.PeBzzPiciStudent;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.EnumConstDAO;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.util.RandomString;

public class PeBzzstudentbacthServiceimp implements PeBzzstudentbacthService {

	private GeneralDao<PeBzzStudent> generalDao;
	private Set checkcomnum = new HashSet();
	private Vector vector = new Vector();

	public void updateFlaggoodStu(PeBzzStudent student, EnumConst enumConst) {
		student.setEnumConstByFlagIsGoodStu(enumConst);
		generalDao.update(student);
	}

	public int Bacthsave(File file, String id) throws EntityException {
		StringBuffer msg = new StringBuffer();

		int count = 0;
		int jsnum = 0;
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
		if (rows < 4) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		String twoname = "";
		String onename = "";
		Set<PeBzzStudent> studentSet = new HashSet<PeBzzStudent>();
		checkcomnum.clear();
		vector.removeAllElements();

		for (int i = 3; i < rows; i++) {
			PeBzzStudent student = new PeBzzStudent();
			SsoUser suser = new SsoUser();
			int n = i - 2;
			// *姓名
			temp = sheet.getCell(1, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，姓名为空！<br/>");
				continue;
			} else if (temp.length() > 50) {
				msg.append("第" + (i + 1) + "行数据，姓名长度不能超过50！<br/>");
				continue;
			}
			student.setTrueName(temp);

			// 性别
			temp = sheet.getCell(2, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				msg.append("第" + (i + 1) + "行数据，性别为空！<br/>");
				continue;
			} else {
				if (temp.equals("男") || temp.equals("女")) {
					student.setGender(temp);
				} else {
					msg.append("第" + (i + 1) + "行数据，性别只能为男/女！<br/>");
					continue;
				}
			}

			// 民族
			temp = sheet.getCell(3, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				// temp="--";
				msg.append("第" + (i + 1) + "行数据，民族为空！<br/>");
				continue;
			} else if (temp.length() > 50) {
				msg.append("第" + (i + 1) + "行数据，民族长度不能超过50！<br/>");
				continue;
			}
			student.setFolk(temp);

			// 学历
			temp = sheet.getCell(4, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				// temp="--";
				msg.append("第" + (i + 1) + "行数据，学历为空！<br/>");
				continue;
			} else {
				// String test = "[初中]、[高中]、[职高]、[中专]、[技校]、[大专]、[本科]、[硕士]、[博士]";
				if (temp.equals("初中") || temp.equals("高中") || temp.equals("职高")
						|| temp.equals("中专") || temp.equals("技校")
						|| temp.equals("大专") || temp.equals("本科")
						|| temp.equals("硕士") || temp.equals("博士")) {
					// student.setEducation(temp);
				} else {
					msg.append("第" + (i + 1) + "行数据，学历不在范围之内空！<br/>");
				}
			}
			student.setEducation(temp);

			// 出生日期
			temp = sheet.getCell(5, i).getContents().trim();
			if (temp.length() != 8) {
				msg.append("第" + (i + 1) + "行数据，出生日期格式错误！<br/>");
				continue;
			}
			String year = temp.substring(0, 4);
			String month = temp.substring(4, 6);
			String day = temp.substring(6, 8);
			if (!isNumeric(year) || !isNumeric(month) || !isNumeric(day)) {
				msg.append("第" + (i + 1) + "行数据，出生日期不为整数！<br/>");
				continue;
			}
			int imonth = Integer.parseInt("1" + month);
			int iday = Integer.parseInt("1" + day);
			if (imonth > 112 || iday > 132) {
				msg.append("第" + (i + 1) + "行数据，出生日期错误！<br/>");
				continue;
			}

			temp = year + "-" + month + "-" + day;
			Date birthday = this.StringtoDate(temp, "yyyy-MM-dd");
			student.setBirthdayDate(birthday);

			// *所在批次
			DetachedCriteria tempbatchdc = DetachedCriteria
					.forClass(PeBzzBatch.class);
			tempbatchdc.add(Restrictions.eq("id", id));
			PeBzzBatch bzzBatch = this.getGeneralDao().getPeBzzBatch(
					tempbatchdc);
			student.setPeBzzBatch(bzzBatch);

			// *所在集团
			onename = sheet.getCell(6, i).getContents().trim();
			twoname = sheet.getCell(7, i).getContents().trim();
			if ((onename.length() > 1 && twoname.length() > 1)) {
				temp = twoname;
			} else if ((onename.length() > 1 && twoname.length() < 1)) {
				temp = onename;
			}

			DetachedCriteria criteria = DetachedCriteria
					.forClass(PeEnterprise.class);
			criteria.add(Restrictions.eq("name", temp));
			List<PeEnterprise> list = this.generalDao.getList(criteria);
			if (list.size() == 0) {
				msg.append("第" + (i + 1) + "行数据，当前工作单位信息不存在！<br/>");
				continue;
			}
			student.setPeEnterprise(list.get(0));
			String comnum = "";

			DetachedCriteria teria = DetachedCriteria
					.forClass(PeEnterprise.class);
			teria.add(Restrictions.eq("name", temp));
			List<PeEnterprise> enlist = this.generalDao.getList(teria);
			String tempcom = "";
			int start = 0;
			int end = 0;
			List<PeEnterprise> blist = null;
			// 如果是2级企业
			if (enlist.get(0).getPeEnterprise() != null) {
				DetachedCriteria adc = DetachedCriteria
						.forClass(PeEnterprise.class);
				adc.createCriteria("peEnterprise", "peEnterprise");
				adc.add(Restrictions.eq("id", list.get(0).getId()));
				blist = this.generalDao.getList(adc);
				tempcom = blist.get(0).getPeEnterprise().getCode();
				start = tempcom.length() - 3;
				end = tempcom.length();
				comnum = tempcom.substring(start, end);
			} else {
				start = enlist.get(0).getCode().length() - 3;
				end = enlist.get(0).getCode().length();
				comnum = enlist.get(0).getCode().substring(start, end);
			}

			String Loginid = this.getCardsNo(comnum, n, true);

			student.setRegNo(Loginid);
			SsoUser sso = new SsoUser();
			sso.setLoginId(Loginid);
			String pwsd = RandomString.getString(8);
			sso.setPassword(pwsd);
			sso.setEnumConstByFlagIsvalid(this.getGeneralDao()
					.getEnumConstByNamespaceCode("FlagIsvalid", "1"));
			DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
			dc.add(Restrictions.eq("name",
					SsoConstant.SSO_DEFAULT_STUDENT_ROLE_NAME));
			List rl = this.getGeneralDao().getList(dc);
			if (rl != null) {
				PePriRole role = (PePriRole) rl.get(0);
				sso.setPePriRole(role);
			}

			student.setName(Loginid + "/" + student.getTrueName());
			student.setSsoUser(sso);

			// 移动电话

			int k = 0;
			temp = sheet.getCell(8, i).getContents().trim();
			if (temp == null || "".equals(temp)) {
				// msg.append("第" + (i + 1) + "行数据，手机为空！<br/>");
				// check = true;
				k += 1;
				// continue;
			} else if (temp.length() > 20) {
				msg.append("第" + (i + 1) + "行数据， 移动电话长度不能超过20！<br/>");
				continue;
			}
			// if (temp!=null && !temp.equals("")&& !this.isMobile(temp)) {
			// msg.append("第" + (i + 1) + "行数据，手机格式错误！<br/>");
			// continue;
			// }
			student.setMobilePhone(temp);
			// 办公电话

			temp = sheet.getCell(9, i).getContents().trim();
			/*
			 * if (temp!=null && !temp.equals("")&&!isTelephone(temp)) {
			 * msg.append("第" + (i + 1) + "行数据，办公电话格式错误！."+temp+".<br/>");
			 * continue; }
			 */
			if (temp == null || "".equals(temp)) {
				k += 1;
			} else if (temp.length() > 50) {
				msg.append("第" + (i + 1) + "行数据， 办公电话长度不能超过50！<br/>");
				continue;
			}
			if (k == 2) {
				msg.append("第" + (i + 1) + "行数据，联系电话至少填一项！<br/>");
				continue;
			}

			student.setPhone(temp);

			// 电子邮件
			temp = sheet.getCell(10, i).getContents().trim();
			/*
			 * if (temp == null || "".equals(temp)) { msg.append("第" + (i + 1) +
			 * "行数据，E-mail为空！<br/>"); continue; }
			 */
			/*
			 * if (temp!=null && !temp.equals("")&& !isEmail(temp)) {
			 * msg.append("第" + (i + 1) + "行数据，E-mail格式错误！<br/>"); continue; }
			 */
			if (temp.length() > 50) {
				msg.append("第" + (i + 1) + "行数据， 电子邮件长度不能超过50！<br/>");
				continue;
			}

			student.setEmail(temp);

			// 年龄
			Date now = new Date();
			int tage = this.getDaysOfTowDiffDate(birthday, now);
			if (tage > 18 && tage < 100) {
				student.setAge((tage + "").trim());
			}
			// 具体职业

			String tempc = "---";

			student.setPosition(tempc);

			student.setTitle(tempc);

			student.setDepartment(tempc);

			// 地址

			student.setAddress(tempc);

			// 邮编

			student.setZipcode(tempc);

			if (!studentSet.add(student)) {
				msg.append("第" + (i + 1) + "行数据与文件中前面的数据重复！<br/>");
				continue;
			}
		}

		if (msg.length() > 0) {
			msg.append("学生报名信息批量上传失败，请修改以上错误之后重新上传！<br/>");
			throw new EntityException(msg.toString());
		}

		for (PeBzzStudent student : studentSet) {
			try {
				this.getGeneralDao().save(student);
				count++;
			} catch (Exception e) {
				e.printStackTrace();

				throw new EntityException("批量上传学生报名信息失败");

			}
		}
		checkcomnum.clear();
		vector.removeAllElements();
		return count;
	}

	/**
	 * 自动计算年龄
	 * 
	 */

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

	private int getDaysOfTowDiffDate(Date sdate, Date edate) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdate);
		Long begintime = calendar.getTimeInMillis();
		calendar.setTime(edate);
		Long endtime = calendar.getTimeInMillis();
		int betweenDays = (int) ((endtime - begintime) / (1000 * 60 * 60 * 24 * 365));
		return betweenDays;
	}

	/***************************************************************************
	 * 自动生成学号 根据企业重新生成
	 * 
	 * @param comnum
	 * @param i
	 */
	private String getCardsNo(String comnum,int i,boolean flag){
			GregorianCalendar gc = new GregorianCalendar();
			int nowYear = gc.get(Calendar.YEAR);
			
			String newRrgNO = "";
			String testno =nowYear+comnum;
			DetachedCriteria chdc = DetachedCriteria.forClass(PeBzzStudent.class);
			chdc.setProjection(Projections.max("regNo"));
			chdc.add(Restrictions.like("regNo",testno,MatchMode.START));
			List mlist = this.generalDao.getList(chdc);
			
			String tempno="";
			if(flag){
			    i = this.getcount(comnum);
			}
			// 如果学号为空
			int n = 0;
			while(true) {
				if(mlist.size()==0||mlist.get(0)==null||mlist.get(0).equals("null")||mlist.get(0).equals("")){
				    if(i<10){
					newRrgNO=testno+"0000"+ i;
				    }
				    if(10 <= i&&i <100){
					newRrgNO=testno+"000"+i;
				    }
				    if(100 <= i&&i <1000){
					newRrgNO=testno+"00"+i;
				    }
				    if(1000 <= i&&i <10000){
					newRrgNO=testno+"0"+i;
				    }
				    if(10000 <= i&&i <100000){
					newRrgNO=testno+i;
				    }
				}else{
					/*
					 * 如果学号不为空 则先取得匹配当前年份当前企业的最大学号
					 */
					String stem ="1"+mlist.get(0).toString().substring(7, 12);
					int tem = Integer.parseInt(stem)+i;
					String testRrgNO = (tem+"");
					newRrgNO = (testno+testRrgNO.trim().substring(1, testRrgNO.length())).trim();
					
				}
				
				//判断用户表中的登陆ID不与学号重复,如果不重复就退出死循环，否则增量加1后重试，直至学号不存在于用户表中
				chdc = DetachedCriteria.forClass(SsoUser.class);
				chdc.add(Restrictions.eq("loginId", newRrgNO));
				chdc.setProjection(Property.forName("id"));
				List uList = this.generalDao.getList(chdc);
				
				if(uList == null || uList.size() == 0) {
					break;
				} else {
					i++;
				}
			}
//			System.out.println(newRrgNO);
			return newRrgNO;
		}

	private int getcount(String comnum) {
		String tempnum = "";
		int result = 0;
		if (checkcomnum.add(comnum)) {
			vector.addElement(comnum + "1");
			result = 1;
		} else {
			for (int m = 0; m < vector.size(); m++) {
				if ((vector.get(m).toString()).contains(comnum)) {
					tempnum = vector.get(m).toString().substring(3);
					int k = Integer.parseInt(tempnum) + 1;
					vector.setElementAt(comnum + k, m);
					result = k;
				}
			}
		}

		return result;
	}

	/**
	 * 验证日期
	 * 
	 * @param date
	 * @return
	 */

	private static boolean isDate(String brithday) {
		if (brithday == null)
			return false;
		else
			return brithday.matches(Const.checkdate);
	}

	/**
	 * 校验手机号码
	 * 
	 * @param mobile
	 * @return
	 */
	public boolean isMobile(String mobile) {
		if (mobile == null)
			return false;
		else
			return mobile.matches(Const.mobile);
	}

	/**
	 * 校验email格式
	 * 
	 * @param email
	 *            email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null)
			return false;
		else
			return email.matches(Const.email);
	}

	/**
	 * 校验邮编格式
	 * 
	 * @param zip
	 *            zip
	 * @return
	 */
	public static boolean isZIP(String zip) {
		if (zip == null)
			return false;
		else
			return zip.matches(Const.zip);
	}

	public static boolean isTelephone(String telephone) {
		if (telephone == null)
			return false;
		else
			return telephone.matches(Const.telephone);
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public PeBzzStudent save(PeBzzStudent transientInstance)
			throws EntityException {
		PeBzzStudent instance = null;
		boolean flag = false;
		String comnum = "";
		comnum = transientInstance.getPeEnterprise().getName();
		DetachedCriteria comdc = DetachedCriteria.forClass(PeEnterprise.class);
		comdc.add(Restrictions.eq("name", comnum));
		List<PeEnterprise> list = this.getGeneralDao().getList(comdc);
		String tempcom = "";
		int start = 0;
		int end = 0;
		List<PeEnterprise> blist = null;
		if (list.get(0).getPeEnterprise() != null) {
			DetachedCriteria adc = DetachedCriteria
					.forClass(PeEnterprise.class);
			adc.createCriteria("peEnterprise", "peEnterprise");
			adc.add(Restrictions.eq("id", list.get(0).getId()));
			blist = this.generalDao.getList(adc);
			tempcom = blist.get(0).getPeEnterprise().getCode();
			start = tempcom.length() - 3;
			end = tempcom.length();
			comnum = tempcom.substring(start, end);
		} else {
			start = list.get(0).getCode().length() - 3;
			end = list.get(0).getCode().length();
			comnum = list.get(0).getCode().substring(start, end);
		}
		String loginid = this.getCardsNo(comnum, 1, false);
		SsoUser ssoUser = new SsoUser();
		ssoUser.setLoginId(loginid);
//		String pwsd = RandomString.generatePassStr();
//		ssoUser.setPassword(pwsd);
		//添加学员时，默认密码为随机8位数字
		String password = MyUtil.getRandomNum(8);
		ssoUser.setPassword(password);
		ssoUser.setPasswordMd5(MyUtil.md5(password));
		ssoUser.setEnumConstByFlagIsvalid(this.getGeneralDao()
				.getEnumConstByNamespaceCode("FlagIsvalid", "1"));
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(PePriRole.class);
			dc.add(Restrictions.eq("name",
					SsoConstant.SSO_DEFAULT_STUDENT_ROLE_NAME));
			List rl = this.getGeneralDao().getList(dc);
			if (rl != null) {
				PePriRole role = (PePriRole) rl.get(0);
				ssoUser.setPePriRole(role);
			}
			this.getGeneralDao().saveSsoUser(ssoUser);
			transientInstance.setRegNo(loginid);
			transientInstance.setName(loginid + "/"
					+ transientInstance.getTrueName());
			transientInstance.setSsoUser(ssoUser);
			transientInstance.setExportState("0");
			instance = (PeBzzStudent) this.generalDao.save(transientInstance);
			if (instance.getId() != null) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		checkcomnum.clear();
		vector.removeAllElements();

		return instance;
	}

	public int deleteByIds(List idList) throws EntityException {
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			PeBzzStudent peBzzStudent = (PeBzzStudent) this.getGeneralDao()
					.getById(id);
			SsoUser ssoUser = peBzzStudent.getSsoUser();
			try {
				String sql = "";
				//删除学员开课信息
				sql = "delete from pr_bzz_tch_stu_elective pbtse where pbtse.fk_stu_id='"+peBzzStudent.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//删除学员证书
				sql = "delete from pe_bzz_certificate pbc where pbc.student_id='"+peBzzStudent.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//删除学员反馈start
				sql = "delete from pe_bzz_feedback_info pbfi where pbfi.fk_sso_user_id='"+ssoUser.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//删除学员补考信息
				sql = "delete from pe_bzz_examagain pbe where pbe.student_id='"+peBzzStudent.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//删除学员缓考信息
				sql = "delete from pe_bzz_examlate pbe where pbe.student_id='"+peBzzStudent.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//删除学员考试信息
				sql = "delete from pe_bzz_examscore pbe where pbe.student_id='"+peBzzStudent.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//删除优秀信息
				sql = "delete from pe_bzz_goodstu pbg where pbg.fk_studentid='"+peBzzStudent.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//删除学习情况汇总
				sql = "delete from stat_study_summary sss where sss.student_id='"+peBzzStudent.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//这张表具体功能暂时不清楚，
				sql = "delete from pe_bzz_editstudent pbe where pbe.student_id='"+peBzzStudent.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//删除学员课程评估
				sql = "delete from pe_bzz_assess pba where pba.fk_student_id='"+ssoUser.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//删除学员课程评估建议
				sql = "delete from pe_bzz_suggestion pbs where pbs.fk_user_id='"+ssoUser.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//这张表具体功能暂时不清楚
				sql = "delete from stuttime s where s.fk_ssouser_id='"+ssoUser.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				
				//删除学员在线学习信息
				//学员详细学习记录
				sql = "delete from scorm_stu_sco sss where sss.student_id='"+ssoUser.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//学员学习汇总
				sql = "delete from scorm_stu_course ssc where ssc.student_id='"+ssoUser.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//学习总体情况
				sql = "delete from training_course_student tcs where tcs.student_id='"+ssoUser.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//在线作业
				sql = "delete from test_homeworkpaper_history thh where thh.t_user_id='"+ssoUser.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				//在线自测
				sql = "delete from test_testpaper_history tth where tth.t_user_id='"+ssoUser.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				
				//在线课程提问
				sql = "delete from pe_bzz_onlinecourse_twy pbot where pbot.fk_sso_user_id='"+ssoUser.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				
				//课程答疑
				//学员课程疑问的回复
				sql = "delete from interaction_answer_info iai where iai.question_id in (select id from interaction_question_info iqi where iqi.submituser_id='"+peBzzStudent.getId()+"')";
				this.getGeneralDao().executeBySQL(sql);
				//学员课程疑问
				sql = "delete from interaction_question_info iqi where iqi.submituser_id='"+peBzzStudent.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				
				//辅导答疑
				//学员考试疑问的回复
				sql = "delete from exam_answer_info eai where eai.question_id in (select id from exam_question_info eqi where eqi.submituser_id='"+peBzzStudent.getId()+"')";
				this.getGeneralDao().executeBySQL(sql);
				//学员考试疑问
				sql = "delete from exam_question_info eqi where eqi.submituser_id='"+peBzzStudent.getId()+"'";
				this.getGeneralDao().executeBySQL(sql);
				
				this.getGeneralDao().delete(peBzzStudent);
				this.getGeneralDao().delete(ssoUser);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return idList.size();
	}

	public void updateSsoUser(SsoUser ssoUser) {
		this.getGeneralDao().updateSsoUser(ssoUser);
	}

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	//@Override
	public int deleteByIds(Class clazz, List siteIds, List ids)
			throws EntityException {
		// TODO Auto-generated method stub
		List<SsoUser> ssoUserList = new ArrayList<SsoUser>();
		for (Iterator iter = ids.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			PeBzzStudent peBzzStudent = (PeBzzStudent) this.getGeneralDao().getById(id);
			ssoUserList.add(peBzzStudent.getSsoUser());
		}
		int i = 0;
		try {
			if(hasMethod(clazz,"getPeSite")){
				i = this.generalDao.deleteByIds(clazz,siteIds,ids);
			}else{
				i = this.generalDao.deleteByIds(clazz,ids);
			}
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		if(i > 0){	//删除登录信息
			for(int temp = 0; temp < ssoUserList.size(); temp++){
				this.getGeneralDao().delete(ssoUserList.get(temp));
			}
		}
		return i;
	}

	private boolean hasMethod(Class clazz, String methodName) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(methodName))
			return false;
		Method[] methods = clazz.getDeclaredMethods();
		for(Method m : methods){
			if(methodName.equals(m.getName())){
				return true;
			}
		}
		return false;
	}
	
	public PeBzzStudent getById(Class entityClass,List siteIds,String id) throws EntityException{
		PeBzzStudent instance = null;
		try {
			instance = this.generalDao.getById(entityClass,siteIds,id);
		} catch (RuntimeException e) {
			throw new EntityException(e);
		}
		return instance;
	}

	
	public String updatestu(String cardNo,String flag,int pageSize) {
		//第一步 查出重复身份证号的身份证
		if(flag==null || "".equals(flag) || "2".equals(flag)){
			flag=" su.flag_isvalid='2' ";
		}else{
			flag=" 1=1";
		}
		String sql="";
		String msg = "";
		String preSql="update  pe_bzz_student pbs set pbs.copy_carid=trim(pbs.card_no) where pbs.copy_carid is null";
		this.getGeneralDao().executeBySQL(preSql);
		if("1".equals(cardNo)){
			sql="select copy_carid  from pe_bzz_student  b,sso_user su  where su.id=b.fk_sso_user_id and "+flag+" and b.copy_carid not in (select pbs.copy_carid "+
		        "  from pe_bzz_student pbs "+
		        " where pbs.fk_sso_user_id in "+
		        "       (select su.id from sso_user su where su.flag_isvalid = '2') "+
		        "       and pbs.is_employee <> '40288ccf3ac50e95013ac51504430004' "+
		        " group by pbs.copy_carid "+
		        " having count(pbs.copy_carid) =2) group by b.copy_carid having count(b.copy_carid)=2";
		}else{
			sql="select copy_carid  from pe_bzz_student b,sso_user su where b.copy_carid='"+cardNo+"' and  su.id=b.fk_sso_user_id and "+flag+" group by b.copy_carid having count(b.copy_carid)=2";
		}
		int m=0;
		List cardList = this.getGeneralDao().getBySQL(sql);
		if(pageSize > cardList.size()){
			pageSize = cardList.size();
		}
		
	    //然后根据身份证循环
		for(int i=0;i<pageSize;i++){
			String card=cardList.get(i).toString();
			if(card==null || "".equals(card)){
				continue;
			}
			//分别查出从业和非从业的人员,
			String sqlC = //从业
				"select p.id, p.fk_sso_user_id, s.amount, s.sum_amount,p.true_name\n" +
				"  from pe_bzz_student p, sso_user s\n" + 
				" where p.is_employee = '40288ccf3ac50e95013ac51504430004'\n" + 
				"   and p.fk_sso_user_id = s.id and p.copy_carid='"+card+"'";
			String sqlF = //非从业
				"select p.id, p.fk_sso_user_id, s.amount, s.sum_amount,p.true_name\n" +
				"  from pe_bzz_student p, sso_user s\n" + 
				" where p.is_employee = '40288ccf3ac50e95013ac5148fde0003'\n" + 
				"   and p.fk_sso_user_id = s.id and p.copy_carid='"+card+"'";
			List cList = this.getGeneralDao().getBySQL(sqlC);
			List fList = this.getGeneralDao().getBySQL(sqlF);
			try{
				if(cList!=null && fList!= null &&cList.size()==1&&fList.size()==1){//异常账号
					String cid = ((Object[])cList.get(0))[0].toString();	//从业学生ID
					String fid = ((Object[])fList.get(0))[0].toString();	//非从业学生ID
					String cssoid=((Object[])cList.get(0))[1].toString();	//从业ssoID
					String fssoid=((Object[])fList.get(0))[1].toString();	//非从业ssoID
					String cAmount=((Object[])cList.get(0))[2].toString();	//从业消费
					String fAmount=((Object[])fList.get(0))[2].toString();	//非从业消费
					String csum=((Object[])cList.get(0))[3].toString();		//从业缴费
					String fsum=((Object[])fList.get(0))[3].toString();		//非从业缴费
					String cname = ((Object[])fList.get(0))[4].toString();		//从业姓名
					String fname = ((Object[])fList.get(0))[4].toString();		//非从业姓名
					if(cname==null || fname==null||!cname.trim().equals(fname.trim())){
						msg+="<br>姓名不同："+card+"--"+cname+"-"+fname;
						continue;
					}
					double amount=Double.parseDouble(cAmount)+Double.parseDouble(fAmount);	//合并后消费
					double sum=Double.parseDouble(csum)+Double.parseDouble(fsum);			//合并后缴费
					//pr_bzz_tch_stu_elective--选课 --学生ID
					//备份+删除冲突历史数据
					String sql1_bak =
						"insert into elective_history\n" +
						"  (id,\n" + 
						"   fk_stu_id,\n" + 
						"   fk_tch_opencourse_id,\n" + 
						"   fk_operator_id,\n" + 
						"   flag_score_status,\n" + 
						"   flag_score_pub,\n" + 
						"   elective_date,\n" + 
						"   score_exam,\n" + 
						"   online_time,\n" + 
						"   fk_training_id,\n" + 
						"   fk_order_id,\n" + 
						"   flag_elective_pay_status,\n" + 
						"   fk_ele_course_period_id,\n" + 
						"   start_time,\n" + 
						"   tempid,\n" + 
						"   exam_times,\n" + 
						"   completed_time,\n" + 
						"   ispass)\n" + 
						"  select id,\n" + 
						"         fk_stu_id,\n" + 
						"         fk_tch_opencourse_id,\n" + 
						"         fk_operator_id,\n" + 
						"         flag_score_status,\n" + 
						"         flag_score_pub,\n" + 
						"         elective_date,\n" + 
						"         score_exam,\n" + 
						"         online_time,\n" + 
						"         fk_training_id,\n" + 
						"         fk_order_id,\n" + 
						"         flag_elective_pay_status,\n" + 
						"         fk_ele_course_period_id,\n" + 
						"         start_time,\n" + 
						"         tempid,\n" + 
						"         exam_times,\n" + 
						"         completed_time,\n" + 
						"         ispass\n" + 
						"    from pr_bzz_tch_stu_elective ele\n" + 
						"   where ele.fk_stu_id = '"+cid+"'\n" + 
						"     and ele.fk_tch_opencourse_id in\n" + 
						"         (select e.fk_tch_opencourse_id from pr_bzz_tch_stu_elective e where e.fk_stu_id = '"+fid+"')";
					this.getGeneralDao().executeBySQL(sql1_bak);
					
					//删除冲突数据
					String sql1_del = 
						"delete from pr_bzz_tch_stu_elective ele\n" +
						" where ele.fk_stu_id = '"+cid+"'\n" + 
						"   and ele.fk_tch_opencourse_id in\n" + 
						"       (select e.fk_tch_opencourse_id\n" + 
						"          from pr_bzz_tch_stu_elective e\n" + 
						"         where e.fk_stu_id = '"+fid+"')";
					this.getGeneralDao().executeBySQL(sql1_del);
					
					String sql1="update pr_bzz_tch_stu_elective ele set ele.fk_stu_id='"+cid+"',ele.completed_time=ele.completed_time where ele.fk_stu_id='"+fid+"'";

					this.getGeneralDao().executeBySQL(sql1);
					
					//elective_history---选课删除历史 --学生ID
					String sql2="update elective_history eh set eh.fk_stu_id='"+cid+"' where eh.fk_stu_id ='"+fid+"'";		
					this.getGeneralDao().executeBySQL(sql2);
					
					
					//pr_bzz_tch_stu_elective_back--课程报名
					//备份报名历史
					String sql3_bak = 
						"insert into elective_back_history\n" +
						"  (id,\n" + 
						"   fk_operator_id,\n" + 
						"   fk_tch_opencourse_id,\n" + 
						"   fk_stu_id,\n" + 
						"   fk_order_id,\n" + 
						"   fk_ele_course_period_id)\n" + 
						"  select id,\n" + 
						"         fk_operator_id,\n" + 
						"         fk_tch_opencourse_id,\n" + 
						"         fk_stu_id,\n" + 
						"         fk_order_id,\n" + 
						"         fk_ele_course_period_id\n" + 
						"    from pr_bzz_tch_stu_elective_back bak\n" + 
						"   where bak.fk_stu_id = '"+cid+"'\n" + 
						"     and bak.fk_tch_opencourse_id in\n" + 
						"         (select b.fk_tch_opencourse_id\n" + 
						"            from pr_bzz_tch_stu_elective_back b\n" + 
						"           where b.fk_stu_id = '"+fid+"')";
					this.getGeneralDao().executeBySQL(sql3_bak);
					//删除报名历史
					String sql3_del = 
						"delete from pr_bzz_tch_stu_elective_back bak\n" +
						" where bak.fk_stu_id = '" + cid + "'\n" + 
						"   and bak.fk_tch_opencourse_id in\n" + 
						"       (select b.fk_tch_opencourse_id\n" + 
						"          from pr_bzz_tch_stu_elective_back b\n" + 
						"         where b.fk_stu_id = '" + fid + "')";

					this.getGeneralDao().executeBySQL(sql3_del);
					String sql3="update pr_bzz_tch_stu_elective_back back set back.fk_stu_id= '"+cid+"' where back.fk_stu_id= '"+fid+"'";
					this.getGeneralDao().executeBySQL(sql3);
					//--sso_userID
					String sql4="update pr_bzz_tch_stu_elective_back back set back.FK_OPERATOR_ID= '"+cssoid+"' where back.FK_OPERATOR_ID= '"+fssoid+"'";
					this.getGeneralDao().executeBySQL(sql4);
					//elective_back_history---报名删除历史
					String sql5="update elective_back_history ebh set ebh.fk_stu_id = '"+cid+"' where ebh.fk_stu_id = '"+fid+"'" ;
					this.getGeneralDao().executeBySQL(sql5);
					//--sso_userID
					String sql6="update elective_back_history ebh set ebh.FK_OPERATOR_ID = '"+cssoid+"' where ebh.FK_OPERATOR_ID = '"+fssoid+"'";
					this.getGeneralDao().executeBySQL(sql6);
					
					//备份冲突学习记录
					String sql7_bak=
						"insert into tcs_history\n" +
						"  (id,\n" + 
						"   student_id,\n" + 
						"   course_id,\n" + 
						"   status,\n" + 
						"   get_date,\n" + 
						"   percent,\n" + 
						"   learn_status,\n" + 
						"   score,\n" + 
						"   complete_date)\n" + 
						"\n" + 
						"  select id,\n" + 
						"         student_id,\n" + 
						"         course_id,\n" + 
						"         status,\n" + 
						"         get_date,\n" + 
						"         percent,\n" + 
						"         learn_status,\n" + 
						"         score,\n" + 
						"         complete_date\n" + 
						"    from training_course_student tcs\n" + 
						"   where tcs.student_id = '"+cssoid+"'\n" + 
						"     and tcs.course_id in (select t.course_id\n" + 
						"                             from training_course_student t\n" + 
						"                            where t.student_id = '"+fssoid+"')";
					this.getGeneralDao().executeBySQL(sql7_bak);
					
					//删除冲突学习记录
					String sql7_del = 
						"delete from training_course_student tcs\n" +
						" where tcs.student_id = '"+cssoid+"'\n" + 
						"   and tcs.course_id in (select t.course_id\n" + 
						"                           from training_course_student t\n" + 
						"                          where t.student_id = '"+fssoid+"')";
					this.getGeneralDao().executeBySQL(sql7_del);
					
					//training_course_student---学习记录 --sso_userID
					String sql7="update training_course_student tcs set tcs.student_id='"+cssoid+"' where tcs.student_id='"+fssoid+"'";

					this.getGeneralDao().executeBySQL(sql7);
					//tcs_history---学习记录删除历史 --sso_userID
					String sql8="update tcs_history th set th.student_id='"+cssoid+"' where th.student_id='"+fssoid+"'" +
								" and th.id not in (select tcs.id\n" +
								"  from tcs_history tcs, tcs_history tcs1\n" + 
								" where tcs.course_id = tcs1.course_id\n" + 
								"   and tcs.student_id = '"+fssoid+"'\n" + 
								"   and tcs1.student_id = '"+cssoid+"')";
					this.getGeneralDao().executeBySQL(sql8);
					//scorm_stu_course---scorm 记录 --sso_userID
					String sql9="update scorm_stu_course ssc set ssc.student_id='"+cssoid+"' where ssc.student_id='"+fssoid+"'" +
								" and ssc.id not in (select tcs.id\n" +
								"  from scorm_stu_course tcs, scorm_stu_course tcs1\n" + 
								" where tcs.course_id = tcs1.course_id\n" + 
								"   and tcs.student_id = '"+fssoid+"'\n" + 
								"   and tcs1.student_id = '"+cssoid+"')";
					this.getGeneralDao().executeBySQL(sql9);
					//scorm_stu_sco---scorm 记录 --sso_userId
					String sql10="update scorm_stu_sco sss set sss.student_id='"+cssoid+"' where sss.student_id='"+fssoid+"'";
					this.getGeneralDao().executeBySQL(sql10);
					//pe_bzz_refund_info --退费--学生ID
					//stu_batch  --学生专项  --学生ID
					String sql11="update stu_batch sb set sb.stu_id='"+cid+"' where sb.stu_id='"+fid+"'";
 
					this.getGeneralDao().executeBySQL(sql11);
					//course_period_student --学生ID
					String sql12="update course_period_student cps set cps.student_id='"+cid+"' where cps.student_id='"+fid+"'";
					this.getGeneralDao().executeBySQL(sql12);
					//--sso_userId pe_bzz_order_info
					String sql13="update pe_bzz_order_info pboi set pboi.create_user='"+cssoid+"' where pboi.create_user='"+fssoid+"'";
					this.getGeneralDao().executeBySQL(sql13);
					//sso_user --用户 --sso_userId
					String sql14="update sso_user su set su.amount='"+amount+"',su.sum_amount='"+sum+"' where su.id='"+cssoid+"'";
					this.getGeneralDao().executeBySQL(sql14);
					String sql15="update sso_user suu set suu.flag_isvalid='3',suu.amount=0,suu.sum_amount=0 where suu.id='"+fssoid+"'";
					this.getGeneralDao().executeBySQL(sql15); 
					
					//批量修改elective完成时间，触发中间表
					m++;
					System.out.println("作用数据--"+m+":"+card);
				}
			}catch (Exception e) {
				msg+="</br>("+card+")异常---"+e.getMessage();
				continue;
			}
			//如果2个账号都是同一个类型
			
			//如果2个都有数据 那么把非从业的学习数据更新成从业的学生ID
	    
			// 就随机更新1个,余额迁移,最后设置无学习记录的账号为不可用
			System.out.println("执行进度:"+(i+1)+"/"+pageSize);
			msg+="</br>"+m+":"+card;
		}
		return msg;
	}

	@Override
	public void check(List idList) {
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		//审核通过
		EnumConst enumConstByFlagRecordAssignMethod = this.getByNamespaceCode("FlagRecordAssignMethod", "0");   //学时剥离记录
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzEditStudent.class);
		dc.createCriteria("oldPeEnterprise", "oldPeEnterprise");
		dc.add(Restrictions.in("id", idList));
		List stuList = this.generalDao.getList(dc);
		Iterator<PeBzzEditStudent> iterator = stuList.iterator();
		try{
			while(iterator.hasNext()){
				PeBzzEditStudent peBzzEditStudent = iterator.next();
				peBzzEditStudent.setEnumConstByFlagEditCheck("ccb2880a91dadc115011dadfcf26b0005x");
				peBzzEditStudent.setAlterDate(new Date());
				peBzzEditStudent.setNote(us.getLoginId());
				peBzzEditStudent = (PeBzzEditStudent)this.generalDao.save(peBzzEditStudent);;
				/*剥离学时开始*/
				
				/*剥离学生学时开始*/
				DetachedCriteria stuSsodc = DetachedCriteria.forClass(SsoUser.class);
				stuSsodc.add(Restrictions.eq("loginId", peBzzEditStudent.getPeBzzStudent().getRegNo()));
				List suList = this.generalDao.getList(stuSsodc);
				SsoUser ssoUser = new SsoUser();
				if(suList!=null&&suList.size()>0){
					ssoUser = (SsoUser) suList.get(0);
				}
				
				/*计算剩余学时开始*/
				String sumAmount = (ssoUser.getSumAmount()==null || ssoUser.getSumAmount().equals("")) ? "0.0" : ssoUser.getSumAmount();
				String amount = (ssoUser.getAmount()==null || ssoUser.getAmount().equals("")) ? "0.0" : ssoUser.getAmount();
				BigDecimal bdSumAmount = new BigDecimal(sumAmount);
				BigDecimal bdAmount = new BigDecimal(amount);
				BigDecimal bdSy = bdSumAmount.subtract(bdAmount);
				/*计算剩余学时结束*/
				
				ssoUser.setSumAmount(ssoUser.getAmount());
				ssoUser = (SsoUser)this.generalDao.save(ssoUser);
				/*剥离学生学时结束*/
				
				/*把多余的学时添加到机构管理员上开始*/
				DetachedCriteria pemdc = DetachedCriteria.forClass(PeEnterpriseManager.class);
				pemdc.createCriteria("ssoUser", "ssoUser", DetachedCriteria.LEFT_JOIN);
				pemdc.createCriteria("peEnterprise", "peEnterprise");
				pemdc.createCriteria("peEnterprise.peEnterprise", "peEnterprise1", DetachedCriteria.LEFT_JOIN);
				if (peBzzEditStudent.getOldPeEnterprise().getPeEnterprise() == null) {// 一级机构的学员
					pemdc.add(Restrictions.and(Restrictions.eq("peEnterprise",
							peBzzEditStudent.getOldPeEnterprise()), Restrictions
							.isNull("oldPeEnterprise.peEnterprise"))
	
					);
				} else {// 二级机构的学员
					pemdc.add(Restrictions.and(Restrictions.eq("peEnterprise",
							peBzzEditStudent.getOldPeEnterprise().getPeEnterprise()),
							Restrictions.isNull("oldPeEnterprise.peEnterprise"))
	
					);
				}
				
				List pemList = this.generalDao.getList(pemdc);
				PeEnterpriseManager manager = new PeEnterpriseManager();
				if(pemList!=null&&pemList.size()>0){
					manager = (PeEnterpriseManager) pemList.get(0);
				}
				SsoUser ssoUser2 = new SsoUser();
				ssoUser2 = manager.getSsoUser();
				
				ssoUser2.setSumAmount(new BigDecimal(ssoUser2.getSumAmount()).add(bdSy).setScale(1, BigDecimal.ROUND_HALF_UP).toString());
				this.generalDao.save(ssoUser2);
				/*把多余的学时添加到机构管理员上结束*/
				
				
				/*分配日志信息开始*/
				AssignRecord assignRecord = new AssignRecord();
				assignRecord.setSsoUser(ssoUser2);
				assignRecord.setAssignStyle("1");
				assignRecord.setAssignDate(new Date());
				assignRecord.setEnumConstByFlagRecordAssignMethod(enumConstByFlagRecordAssignMethod);
				this.generalDao.save(assignRecord);
				AssignRecordStudent ars = new AssignRecordStudent();
				ars.setAssignRecord(assignRecord);
				ars.setStudent(peBzzEditStudent.getPeBzzStudent());
				ars.setClassNum(bdSy.doubleValue()+"");
				this.generalDao.save(ars);
				/*分配日志信息结束*/
				/*剥离学时结束*/
				
				/*修改学生信息开始*/
				PeBzzStudent pbs = peBzzEditStudent.getPeBzzStudent();
				if("02".equals(peBzzEditStudent.getStatus())){//修改机构
					pbs.setPeEnterprise(peBzzEditStudent.getNewPeEnterprise());
				}else{//离职
					pbs.setEnumConstByIsEmployee(this.getByNamespaceCode("IsEmployee","0"));
					pbs.setPeEnterprise(this.getEnterprise("0000"));
				}
				this.generalDao.save(pbs);
				/*修改学生信息结束*/
			}			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public EnumConst getByNamespaceCode(String namespace,String code) {
			
			EnumConst enumConst = null;
			String hql = "from EnumConst where code='"+ code +"' and namespace='" + namespace + "'";
			
			List list = this.generalDao.getByHQL(hql);
			
			if (list.size() == 1)
				enumConst = (EnumConst)list.get(0);
			
			return enumConst;
		}
	/**
	 * 根据code获得机构实体
	 * @param code
	 * @return
	 */
	public PeEnterprise getEnterprise(String code){
		PeEnterprise enterprise = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeEnterprise.class);
		dc.add(Restrictions.eq("code",code));
		List<PeEnterprise> pelist = this.getGeneralDao().getList(dc);
		if(pelist!=null && pelist.size()>0){
			enterprise = pelist.get(0);
		}
		return enterprise;
	}

	@Override
	public void savePiciStudent(List list) {
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			PeBzzStudent student = (PeBzzStudent)map.get("student");
			PeBzzPici bzzPici = (PeBzzPici)map.get("pici");
			PeBzzPiciStudent bzzPiciStudent = new PeBzzPiciStudent();
			bzzPiciStudent.setPeBzzStudent(student);
			bzzPiciStudent.setPeBzzPici(bzzPici);
			this.generalDao.save(bzzPiciStudent);
		}
	}

}

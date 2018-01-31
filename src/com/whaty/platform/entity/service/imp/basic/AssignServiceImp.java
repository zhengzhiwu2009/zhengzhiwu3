package com.whaty.platform.entity.service.imp.basic;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.AssignRecord;
import com.whaty.platform.entity.bean.AssignRecordStudent;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.AssignDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.AssignService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

@Service
@Transactional
public class AssignServiceImp<T extends AbstractBean> implements AssignService<T>, Serializable {
	private AssignDao<T> assignDao;

	public AssignDao<T> getAssignDao() {
		return assignDao;
	}

	public void setAssignDao(AssignDao<T> assignDao) {
		this.assignDao = assignDao;
	}

	/**
	 * 分配学员学时
	 */
	@Transactional(rollbackFor = Exception.class)
	public String assignHour() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] ids = request.getParameter("ids").split(",");
		String style = request.getParameter("style");
		String s = ServletActionContext.getRequest().getParameter("score");
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
		dc.add(Restrictions.eq("loginId", us.getLoginId()));
		List<SsoUser> listUser = this.getAssignDao().getList(dc);
		// 分配给的学员S
		DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);
		stuDc.add(Restrictions.in("id", ids));
		stuDc.createCriteria("ssoUser", "ssoUser");
		List<PeBzzStudent> listStu = this.getAssignDao().getList(stuDc);
		// 金额分配记录
		DetachedCriteria dc1 = DetachedCriteria.forClass(EnumConst.class);
		dc1.add(Restrictions.eq("namespace", "FlagRecordAssignMethod"));
		dc1.add(Restrictions.eq("code", "1"));
		List<EnumConst> list1 = this.getAssignDao().getList(dc1);
		EnumConst enumConstByFlagRecordAssignMethod = list1.get(0);

		// 分配-学员
		DetachedCriteria dc2 = DetachedCriteria.forClass(EnumConst.class);
		dc2.add(Restrictions.eq("namespace", "FlagOperateType"));
		dc2.add(Restrictions.eq("code", "1"));
		List<EnumConst> list2 = this.getAssignDao().getList(dc2);
		EnumConst enumConstByFlagOperateType = list2.get(0);

		List list = new ArrayList();
		if (s == null || s.length() < 1) {
			return "请正确输入学时数！";
		}
		/***********************************************************************
		 * !!!!!!! 要计算修改后回写数据库的 不能读session中数据 !!!!!!!
		 **********************************************************************/
		Double totalCount = ids.length * Double.parseDouble(s);
		SsoUser ssoUser = listUser.get(0);
		String amount1 = "0";
		String sumAmount = "0";
		if (ssoUser.getAmount() != null && !"".equals(ssoUser.getAmount())) {
			amount1 = ssoUser.getAmount();
		}
		if (ssoUser.getSumAmount() != null && !"".equals(ssoUser.getSumAmount())) {
			sumAmount = ssoUser.getSumAmount();
		}
		if (totalCount > (Double.parseDouble(sumAmount) - Double.parseDouble(amount1))) {
			return "学时余额不足";
		}
		String amount = (ssoUser.getAmount() == null || ssoUser.getAmount().equalsIgnoreCase("")) ? "0.00" : ssoUser.getAmount();
		BigDecimal bdAmount = new BigDecimal(amount); // 用户原来已经使用的金额

		BigDecimal bdUsedAmount = new BigDecimal(s).multiply(new BigDecimal(ids.length));

		ssoUser.setAmount(bdAmount.add(bdUsedAmount).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		/**start 
		 * 新增用户余额表
		 * 2016-09-06
		 * */
		String usersSql = "insert into sso_user_balance (id ,fk_user_id ,operator ,create_date ,amount ,sum_amount) values (sys_guid()," +
				"'"+ ssoUser.getId() +"','"+ ssoUser.getId()+"', sysdate,'"+ bdAmount.add(bdUsedAmount).setScale(2, BigDecimal.ROUND_HALF_UP).toString() +"','"+ ssoUser.getSumAmount()+"')";
		int ii = this.getAssignDao().executeBySQL(usersSql);
		if(ii != ii){
			throw new EntityException("账户余额变动失败");
		}
		
		/**
		 * end
		 * */
		list.add(ssoUser);
		String acct_amount = new BigDecimal(ssoUser.getSumAmount()).subtract(new BigDecimal(ssoUser.getAmount())).toString();
		String operate_amount = bdUsedAmount.toString();

		list.add(ssoUser);
		// 分配日志信息
		AssignRecord assignRecord = new AssignRecord();
		assignRecord.setSsoUser(ssoUser);// 集体管理员
		assignRecord.setAssignStyle(style);
		assignRecord.setAssignDate(new Date());// 操作日期
		assignRecord.setEnumConstByFlagRecordAssignMethod(enumConstByFlagRecordAssignMethod);// 分配
		assignRecord.setAccountAmount(acct_amount);// 集体管理员余额
		assignRecord.setOperateAmount(operate_amount);// 操作学时数
		assignRecord.setEnumConstByFlagOperateType(enumConstByFlagOperateType);// 分配-学员
		list.add(assignRecord);
		for (PeBzzStudent stu : listStu) {
			SsoUser user = stu.getSsoUser();// 学员USER
			/**start 
			 * 新增用户余额表
			 * 2016-09-06
			 * */
			String userSql = "insert into sso_user_balance (id ,fk_user_id ,operator ,create_date ,amount ,sum_amount) values (sys_guid()," +
					"'"+ user.getId() +"','"+ us.getId()+"', sysdate,'"+ user.getAmount() +"','"+ (Double.parseDouble(user.getSumAmount()) + Double.parseDouble(s))+"')";
			int i = this.getAssignDao().executeBySQL(userSql);
			if(i != 1){
				throw new EntityException("账户余额变动失败");
			}
			
			/**
			 * end
			 * */
			user.setSumAmount(Double.parseDouble(user.getSumAmount()) + Double.parseDouble(s) + "");// 修改学员学时数(分配操作)
			stu.setSsoUser(user);
			AssignRecordStudent ars = new AssignRecordStudent();
			ars.setAssignRecord(assignRecord);
			ars.setStudent(stu);
			ars.setClassNum(Double.parseDouble(s) + "");// 操作(分配)学时数
			String subAmount = Double.parseDouble(user.getSumAmount()) - Double.parseDouble(user.getAmount()) + "";// 分配前学员余额
			ars.setSubAmount(subAmount);
			list.add(ars);
		}
		for (Object obj : list) {
			obj = this.getAssignDao().save(obj);
		}
		return null;
	}

	/**
	 * 剥离学员学时
	 */
	@Transactional(rollbackFor = Exception.class)
	public String stripHour() {
		List list = new ArrayList();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		/***********************************************************************
		 * !!!!!!! 要计算修改后回写数据库的 不能读session中数据 !!!!!!!
		 **********************************************************************/
		HttpServletRequest request = ServletActionContext.getRequest();
		String style = request.getParameter("style");
		//空判断，zgl
		String[] ids = null;
		if(request.getParameter("ids") != null){
			ids = request.getParameter("ids").split(",");
		}else{
			ids = new String[]{};
		}
		String s = ServletActionContext.getRequest().getParameter("score");
		if (s == null || s.length() < 1) {
			return "请正确输入学时数！";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
		dc.add(Restrictions.eq("loginId", us.getLoginId()));
		List<SsoUser> listUser = this.getAssignDao().getList(dc);
		SsoUser ssoUser = listUser.get(0);
		// 剥离学员S
		DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);
		stuDc.add(Restrictions.in("id", ids));
		stuDc.createCriteria("ssoUser", "ssoUser");
		List<PeBzzStudent> listStu = this.getAssignDao().getList(stuDc);
		boolean flag = true;
		for (PeBzzStudent stu : listStu) {
			SsoUser user = stu.getSsoUser();
			if (Double.parseDouble(user.getSumAmount()) < Double.parseDouble(s)) {
				flag = false;
			}
		}
		if (flag) {
			// 分配日志信息

			// 金额分配记录
			DetachedCriteria dc1 = DetachedCriteria.forClass(EnumConst.class);
			dc1.add(Restrictions.eq("namespace", "FlagRecordAssignMethod"));
			dc1.add(Restrictions.eq("code", "0"));
			List<EnumConst> list1 = this.getAssignDao().getList(dc1);
			EnumConst enumConstByFlagRecordAssignMethod = list1.get(0);

			// 剥离-学员
			DetachedCriteria dc2 = DetachedCriteria.forClass(EnumConst.class);
			dc2.add(Restrictions.eq("namespace", "FlagOperateType"));
			dc2.add(Restrictions.eq("code", "3"));
			List<EnumConst> list2 = this.getAssignDao().getList(dc2);
			EnumConst enumConstByFlagOperateType = list2.get(0);

			AssignRecord assignRecord = new AssignRecord();
			assignRecord.setSsoUser(ssoUser);// 集体管理员
			assignRecord.setAssignStyle(style);
			assignRecord.setAssignDate(new Date());// 操作日期
			assignRecord.setEnumConstByFlagRecordAssignMethod(enumConstByFlagRecordAssignMethod);// 剥离
			String sumAmount1 = (ssoUser.getSumAmount() == null || ssoUser.getSumAmount().equalsIgnoreCase("")) ? "0.0" : ssoUser
					.getSumAmount();
			BigDecimal bdSumAmount = new BigDecimal(sumAmount1);
			BigDecimal bdRecycle = new BigDecimal(s).multiply(new BigDecimal(ids.length));
			String accountAmount = bdSumAmount.add(bdRecycle).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(
					new BigDecimal(ssoUser.getAmount())).toString();
			String operateAmount = new BigDecimal(s).multiply(new BigDecimal(ids.length)).toString();
			assignRecord.setAccountAmount(accountAmount);// 集体管理员余额
			assignRecord.setOperateAmount(operateAmount);// 操作学时数
			assignRecord.setEnumConstByFlagOperateType(enumConstByFlagOperateType);
			list.add(assignRecord);
			for (PeBzzStudent stu : listStu) {
				SsoUser user = stu.getSsoUser();// 剥离学员USER
				String sumAmount = (user.getSumAmount() == null || user.getSumAmount().equals("")) ? "0.0" : user.getSumAmount();
				String amount = (user.getAmount() == null || user.getAmount().equals("")) ? "0.0" : user.getAmount();
				if (Double.parseDouble(sumAmount) - Double.parseDouble(s) - Double.parseDouble(amount) < 0) {
					return stu.getRegNo() + "学员剥离学时失败，剩余学时数不足!";
				}
				/**start 
				 * 新增用户余额表
				 * 2016-09-06
				 * */
				String userSql = "insert into sso_user_balance (id ,fk_user_id ,operator ,create_date ,amount ,sum_amount) values (sys_guid()," +
						"'"+ user.getId() +"','"+ us.getId()+"', sysdate,'"+ amount +"','"+ (Double.parseDouble(sumAmount) - Double.parseDouble(s))+"')";
				int i = this.getAssignDao().executeBySQL(userSql);
				if(i != i){
					return "账户余额变动失败";
				}
				
				/**
				 * end
				 * */
				user.setSumAmount(Double.parseDouble(sumAmount) - Double.parseDouble(s) + "");
				stu.setSsoUser(user);
				AssignRecordStudent ars = new AssignRecordStudent();
				ars.setAssignRecord(assignRecord);
				ars.setStudent(stu);
				ars.setClassNum(Double.parseDouble(s) + "");// 操作(剥离)学时数
				String subAmount = Double.parseDouble(user.getSumAmount()) - Double.parseDouble(user.getAmount()) + "";// 学员余额
				ars.setSubAmount(subAmount);
				list.add(ars);
			}
			ssoUser.setSumAmount(bdSumAmount.add(bdRecycle).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			list.add(ssoUser);
			/**start 
			 * 新增用户余额表
			 * 2016-09-06
			 * */
			String userSql = "insert into sso_user_balance (id ,fk_user_id ,operator ,create_date ,amount ,sum_amount) values (sys_guid()," +
					"'"+ ssoUser.getId() +"','"+ ssoUser.getId()+"', sysdate,'"+ ssoUser.getAmount() +"','"+ bdSumAmount.add(bdRecycle).setScale(2, BigDecimal.ROUND_HALF_UP).toString()+"')";
			int i = this.getAssignDao().executeBySQL(userSql);
			if(i != i){
				return "账户余额变动失败";
			}
			
			/**
			 * end
			 * */
			for (Object obj : list) {
				obj = this.getAssignDao().save(obj);
			}
		} else {
			return "要剥离的学时大于当前学生的剩余学时数";
		}
		return null;
	}

	/**
	 * 分配二级集体学时
	 */
	@Transactional(rollbackFor = Exception.class)
	public String assignEntHour() throws Exception {
		List list = new ArrayList();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] ids = request.getParameter("ids").split(",");
		String style = request.getParameter("style");
		String s = ServletActionContext.getRequest().getParameter("score");
		/***********************************************************************
		 * !!!!!!! 要计算修改后回写数据库的 不能读session中数据 !!!!!!!
		 **********************************************************************/
		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
		dc.add(Restrictions.eq("loginId", us.getLoginId()));
		List<SsoUser> listUser = this.getAssignDao().getList(dc);
		Double totalCount = ids.length * Double.parseDouble(s);
		SsoUser ssoUser = listUser.get(0);
		String amount1 = "0";
		String sumAmount = "0";
		if (ssoUser.getAmount() != null && !"".equals(ssoUser.getAmount())) {
			amount1 = ssoUser.getAmount();
		}
		if (ssoUser.getSumAmount() != null && !"".equals(ssoUser.getSumAmount())) {
			sumAmount = ssoUser.getSumAmount();
		}
		if (totalCount > (Double.parseDouble(sumAmount) - Double.parseDouble(amount1))) {
			return "剩余学时数不足";
		}
		String amount = (ssoUser.getAmount() == null || ssoUser.getAmount().equalsIgnoreCase("")) ? "0.0" : ssoUser.getAmount();
		BigDecimal bdAmount = new BigDecimal(amount); // 用户原来已经使用的学时数

		BigDecimal bdUsedAmount = new BigDecimal(s).multiply(new BigDecimal(ids.length));
		ssoUser.setAmount(bdAmount.add(bdUsedAmount).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
		list.add(ssoUser);
		String acct_amount = new BigDecimal(ssoUser.getSumAmount()).subtract(new BigDecimal(ssoUser.getAmount())).toString();
		String operate_amount = bdUsedAmount.toString();

		list.add(ssoUser);
		// 分配日志信息

		// 学时分配记录
		DetachedCriteria dc1 = DetachedCriteria.forClass(EnumConst.class);
		dc1.add(Restrictions.eq("namespace", "FlagRecordAssignMethod"));
		dc1.add(Restrictions.eq("code", "1"));
		List<EnumConst> list1 = this.getAssignDao().getList(dc1);
		EnumConst enumConstByFlagRecordAssignMethod = list1.get(0);

		// 分配-二级集体
		DetachedCriteria dc2 = DetachedCriteria.forClass(EnumConst.class);
		dc2.add(Restrictions.eq("namespace", "FlagOperateType"));
		dc2.add(Restrictions.eq("code", "2"));
		List<EnumConst> list2 = this.getAssignDao().getList(dc2);
		EnumConst enumConstByFlagOperateType = list2.get(0);

		AssignRecord assignRecord = new AssignRecord();
		assignRecord.setSsoUser(ssoUser);// 集体管理员
		assignRecord.setAssignStyle(style);
		assignRecord.setAssignDate(new Date());// 操作(分配)日期
		assignRecord.setEnumConstByFlagRecordAssignMethod(enumConstByFlagRecordAssignMethod);// 分配学时
		assignRecord.setAccountAmount(acct_amount);
		assignRecord.setOperateAmount(operate_amount);
		assignRecord.setEnumConstByFlagOperateType(enumConstByFlagOperateType);// 分配-二级集体
		list.add(assignRecord);
		// 分配给二级集体S
		DetachedCriteria pemDc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		pemDc.createCriteria("ssoUser", "ssoUser");
		pemDc.createCriteria("peEnterprise", "peEnterprise").add(Restrictions.in("id", ids));
		List<PeEnterpriseManager> listPem = this.getAssignDao().getList(pemDc);
		if (listPem == null || ids.length != listPem.size()) {
			return "操作失败，提交结果与执行结果不一致，请联系管理员 ！";
		}
		for (PeEnterpriseManager pem : listPem) {
			SsoUser user = pem.getSsoUser();// 二级集体USER
			user.setSumAmount(Double.parseDouble(user.getSumAmount() == null ? "0" : user.getSumAmount()) + Double.parseDouble(s) + "");// 修改二级集体学时数(分配操作)
			pem.setSsoUser(user);
			AssignRecordStudent ars = new AssignRecordStudent();
			ars.setAssignRecord(assignRecord);
			ars.setManager(pem);
			ars.setClassNum(Double.parseDouble(s) + "");// 操作(分配)学时数
			String subAmount = Double.parseDouble(user.getSumAmount()) - Double.parseDouble(user.getAmount()) + "";// 分配前二级集体余额
			ars.setSubAmount(subAmount);
			list.add(ars);
		}
		for (Object obj : list) {
			obj = this.getAssignDao().save(obj);
		}
		return null;
	}

	/**
	 * 剥离二级集体学时
	 */
	@Transactional(rollbackFor = Exception.class)
	public String stripEntHour() {
		List list = new ArrayList();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		/***********************************************************************
		 * !!!!!!! 要计算修改后回写数据库的 不能读session中数据 !!!!!!!
		 **********************************************************************/
		HttpServletRequest request = ServletActionContext.getRequest();
		String style = request.getParameter("style");
		String[] ids = request.getParameter("ids").split(",");
		String s = ServletActionContext.getRequest().getParameter("score");
		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
		dc.add(Restrictions.eq("loginId", us.getLoginId()));
		List<SsoUser> listUser = this.getAssignDao().getList(dc);
		SsoUser ssoUser = listUser.get(0);
		DetachedCriteria pemDc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		pemDc.createCriteria("peEnterprise", "peEnterprise").add(Restrictions.in("id", ids));
		//pemDc.add(Restrictions.in("id", ids));
		pemDc.createCriteria("ssoUser", "ssoUser");
		List<PeEnterpriseManager> listPem = this.getAssignDao().getList(pemDc);
		boolean flag = true;
		for (PeEnterpriseManager pem : listPem) {
			SsoUser user = pem.getSsoUser();
			String sumAmount = (ssoUser.getSumAmount() == null || ssoUser.getSumAmount().equalsIgnoreCase("")) ? "0.0" : ssoUser
					.getSumAmount();
			if (Double.parseDouble(sumAmount) < Double.parseDouble(s)) {
				flag = false;
			}
		}
		if (flag) {
			// 分配日志信息

			// 学时剥离记录
			DetachedCriteria dc1 = DetachedCriteria.forClass(EnumConst.class);
			dc1.add(Restrictions.eq("namespace", "FlagRecordAssignMethod"));
			dc1.add(Restrictions.eq("code", "0"));
			List<EnumConst> list1 = this.getAssignDao().getList(dc1);
			EnumConst enumConstByFlagRecordAssignMethod = list1.get(0);

			// 剥离二级集体
			DetachedCriteria dc2 = DetachedCriteria.forClass(EnumConst.class);
			dc2.add(Restrictions.eq("namespace", "FlagOperateType"));
			dc2.add(Restrictions.eq("code", "4"));
			List<EnumConst> list2 = this.getAssignDao().getList(dc2);
			EnumConst enumConstByFlagOperateType = list2.get(0);

			AssignRecord assignRecord = new AssignRecord();
			assignRecord.setSsoUser(ssoUser);// 集体管理员
			assignRecord.setAssignStyle(style);
			assignRecord.setAssignDate(new Date());// 操作日期
			String sumAmount1 = (ssoUser.getSumAmount() == null || ssoUser.getSumAmount().equalsIgnoreCase("")) ? "0.0" : ssoUser
					.getSumAmount();
			BigDecimal bdSumAmount = new BigDecimal(sumAmount1);
			BigDecimal bdRecycle = new BigDecimal(s).multiply(new BigDecimal(ids.length));
			String accountAmount = bdSumAmount.add(bdRecycle).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(
					new BigDecimal(ssoUser.getAmount())).toString();
			String operateAmount = new BigDecimal(s).multiply(new BigDecimal(ids.length)).toString();
			assignRecord.setAccountAmount(accountAmount);// 集体余额
			assignRecord.setOperateAmount(operateAmount);// 操作学时数
			assignRecord.setEnumConstByFlagRecordAssignMethod(enumConstByFlagRecordAssignMethod);// 剥离
			assignRecord.setEnumConstByFlagOperateType(enumConstByFlagOperateType);// 剥离-二级集体
			list.add(assignRecord);
			for (PeEnterpriseManager pem : listPem) {
				SsoUser user = pem.getSsoUser();// 二级集体USER
				String sumAmount = (user.getSumAmount() == null || user.getSumAmount().equals("")) ? "0.0" : user.getSumAmount();
				String amount = (user.getAmount() == null || user.getAmount().equals("")) ? "0.0" : user.getAmount();
				if (Double.parseDouble(sumAmount) - Double.parseDouble(s) - Double.parseDouble(amount) < 0) {
					return pem.getLoginId() + "二级集体剥离学时失败，剩余学时数不足!";
				}
				user.setSumAmount(Double.parseDouble(sumAmount) - Double.parseDouble(s) + "");
				pem.setSsoUser(user);
				AssignRecordStudent ars = new AssignRecordStudent();
				ars.setAssignRecord(assignRecord);
				ars.setManager(pem);
				ars.setClassNum(Double.parseDouble(s) + "");// 操作(剥离)学时数
				String subAmount = Double.parseDouble(user.getSumAmount()) - Double.parseDouble(user.getAmount()) + "";
				ars.setSubAmount(subAmount);// 二级余额
				list.add(ars);
			}
			String sumAmount = (ssoUser.getSumAmount() == null || ssoUser.getSumAmount().equalsIgnoreCase("")) ? "0.0" : ssoUser
					.getSumAmount();
			ssoUser.setSumAmount(bdSumAmount.add(bdRecycle).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			list.add(ssoUser);
			for (Object obj : list) {
				obj = this.getAssignDao().save(obj);
			}
		} else {
			return "要剥离的学时大于当前集体管理员的剩余学时数";
		}
		return null;
	}
}

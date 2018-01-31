package com.whaty.platform.entity.service.quartz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.sinoufc.platform.util.queue.Task;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.bean.AssignRecord;
import com.whaty.platform.entity.bean.AssignRecordStudent;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzEditStudent;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.WhatyuserLog4j;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.util.SpringUtil;

/**
 * Service实现类 - 用户管理
 * 
 * @author xuwenxue
 */

@SuppressWarnings("unchecked")
public class QuartzServiceImpl extends GeneralServiceImp implements QuartzService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EnumConstService enumConstService;
	private GeneralDao generalDao;

	public EnumConstService getEnumConstService() {
		if (enumConstService == null) {
			enumConstService = (EnumConstService) SpringUtil.getBean("enumConstService");
		}
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public GeneralDao getGeneralDao() {
		if (generalDao == null) {
			generalDao = (GeneralDao) SpringUtil.getBean("generalDao");
		}
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	@Override
	public void moveSiteTypeService(int size) {

		UserSession us = null;
		try {
			if (ServletActionContext.getRequest() != null) {
				us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			}
		} catch (Exception e) {/* 没作用，放空指针 */
		}
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select code,\n" + "       name,\n" + "       update_date,\n" + "       state,\n" + "       result,"
				+ "		rowid as rid\n" + "    from hzph_data_bridge.hr_site_type\n" + "		where result='0'\n" + " order by update_date desc";

		try {
			rs = db.execute_oracle_page(sql, 1, size);
			while (rs != null && rs.next()) {
				String msg = "";
				String state = rs.getString("state");// 01 新增 02 修改 03 删除
				String code = rs.getString("code");// 机构类别编号
				String name = rs.getString("name");// 机构类别名称
				String rowid = rs.getString("rid");// rowid 当做唯一标识
				// String result = rs.getString("result");
				try {
					EnumConst pex = this.getEnumConstService().getByNamespaceCode("FlagEnterpriseType", code);
					if ("01".equalsIgnoreCase(state)) {// 新增
						if (pex != null && pex.getId() != null) {
							String uSql = "update hzph_data_bridge.hr_site_type hs set hs.result='2',hs.note='机构类别已存在，不能添加' where hs.rowid='"
									+ rowid + "'";
							this.getGeneralDao().executeBySQL(uSql);
							continue;
						} else {
							EnumConst ec = new EnumConst();
							ec.setId(UUID.randomUUID().toString());
							ec.setCode(code);
							ec.setName(name);
							ec.setNamespace("FlagEnterpriseType");
							this.getGeneralDao().save(ec);
						}

					} else if ("02".equalsIgnoreCase(state)) {// 修改
						if (pex == null || pex.getId() == null) {
							String uSql = "update hzph_data_bridge.hr_site_type hs set hs.result='2',hs.note='机构类别不存在，不能修改' where hs.rowid='"
									+ rowid + "'";
							this.getGeneralDao().executeBySQL(uSql);
							continue;
						} else {
							pex.setName(name);
							this.getGeneralDao().save(pex);
						}
						// 修改机构类别
						this.getGeneralDao().save(pex);
					} else if ("03".equalsIgnoreCase(state)) {// 删除
						this.getGeneralDao().save(pex);
						String uSql = "update hzph_data_bridge.hr_site_type hs set hs.result='3',hs.note='删除机构类别，培训平台不做处理。' where hs.rowid='"
								+ rowid + "'";
						this.getGeneralDao().executeBySQL(uSql);
						continue;
					} else {// 未知状态
						continue;
					}
					String uSql = "update hzph_data_bridge.hr_site_type hs set hs.result='1' where hs.rowid='" + rowid + "'";
					this.getGeneralDao().executeBySQL(uSql);
					WhatyuserLog4j log = new WhatyuserLog4j();
					// log.setIp(ServletActionContext.getRequest().getRemoteAddr());
					log.setBehavior("QuartzServiceImp_moveSiteService"); // 动作
					log.setNotes("同步机构类别，类别CODE：" + code + "--" + msg);// 说明；
					log.setStatus("success");
					log.setOperateTime(new Date()); // 时间
					if (us == null) {
						log.setUserid("系统处理"); // SSO LOGINID
						log.setLogtype("系统处理");
					} else {
						log.setUserid(us.getLoginId()); // SSO LOGINID
						log.setLogtype(us.getUserLoginType());
					}
					try {
						this.getGeneralDao().save(log);
					} catch (Exception e) {
						System.out.println("同步机构类别保存日志失败--" + "同步机构类别，类别CODE：" + code + "--" + msg);
						// e.printStackTrace();
					}
				} catch (Exception ex) {

				}
			}
		} catch (SQLException e) {
			System.out.println("同步机构失败--" + new Date());
			// e.printStackTrace();
		} finally {
			db.close(rs);
			db = null;
		}
	}

	@Override
	public void moveSiteService(int size) {
		if (this.generalDao == null) {
			generalDao = (GeneralDao) SpringUtil.getBean("generalDao");
		}
		UserSession us = null;
		try {
			if (ServletActionContext.getRequest() != null) {
				us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			}
		} catch (Exception e) {/* 没作用，放空指针 */
		}
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select code,\n" + "       name,\n" + "       type,\n" + "       license_type,\n" + "       re_address,\n"
				+ "       office_address,\n" + "       website,\n" + "       update_date,\n" + "       hrs_state,\n"
				+ "       hrs_result,\n" + "       password,\n" + "		rowid as rid\n" + "  from hzph_data_bridge.hr_site hs\n"
				+ " where hs.hrs_result = '0'\n" + " order by hs.update_date desc";

		try {
			rs = db.execute_oracle_page(sql, 1, size);
			while (rs != null && rs.next()) {
				String msg = "";
				String hrs_state = rs.getString("hrs_state");// 01 新增 02 修改
				// 03 删除
				String code = rs.getString("code");// 机构编号
				String name = rs.getString("name");// 机构名称
				// String type = rs.getString("type");//机构类别 以逗号分隔
				String type = code.substring(0, 1);// 机构类别 t通过机构编号的首字母判断
				String license_type = rs.getString("license_type");// 证券业务许可类别
				// 以逗号分隔
				String re_address = rs.getString("re_address");// 注册地址
				String office_address = rs.getString("office_address");// 办公
				String website = rs.getString("website");
				String password = rs.getString("password");// MD5密码
				String rowid = rs.getString("rid");// rowid 当做唯一标识
				try {
					DetachedCriteria dcx = DetachedCriteria.forClass(PeEnterprise.class);
					dcx.add(Restrictions.eq("code", code).ignoreCase());
					List<PeEnterprise> peList = this.getGeneralDao().getList(dcx);
					PeEnterprise pex = null;
					if (peList != null && peList.size() > 0) {
						pex = peList.get(0);
					}
					if ("01".equalsIgnoreCase(hrs_state)) {// 新增
						if (pex != null) {
							String uSql = "update hzph_data_bridge.hr_site hs set hs.hrs_result='2',hs.note='机构已存在，不能添加' where hs.rowid='"
									+ rowid + "'";
							db.executeUpdate(uSql);
							continue;
						}
						EnumConst enumConstByFlagIsvalid = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "1");// 有效状态
						EnumConst enumConstByFlagEnterpriseState = this.getEnumConstService()
								.getByNamespaceCode("FlagEnterpriseState", "0");// 机构有效状态
						EnumConst enumConstByFlagEnterpriseType = null;
						DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
						dc.add(Restrictions.eq("namespace", "FlagEnterpriseType").ignoreCase());
						dc.add(Restrictions.eq("code", type).ignoreCase());
						PePriRole ppr = null;
						try {
							ppr = (PePriRole) this.getGeneralDao().getById(PePriRole.class, "2");
						} catch (EntityException e) {
							// e.printStackTrace();
							msg += "角色获取失败！";
						}
						List<EnumConst> ecList = this.getGeneralDao().getList(dc);
						if (ecList != null && ecList.size() > 0) {
							enumConstByFlagEnterpriseType = ecList.get(0);
						}
						try {
							// 创建机构
							PeEnterprise pe = new PeEnterprise();
							pe.setCode(code);// 机构编号
							pe.setName(name);// 机构名称
							pe.setAddress(office_address);// 办公地址
							pe.setEnrolAddress(re_address);// 注册地址
							pe.setEnumConstByFlagEnterpriseState(enumConstByFlagEnterpriseState);// 机构状态
							pe.setNetAddress(website);// 网址
							pe.setEnumConstByFlagEnterpriseType(enumConstByFlagEnterpriseType);// 机构类型
							pe = (PeEnterprise) this.getGeneralDao().save(pe);
							// 创建SSO_USER
							SsoUser user = new SsoUser();
							if (password == null || "".equals(password)) {
								password = Const.defaultPwd;
							}
							user.setPassword("没有密码");
							user.setPasswordBk(password);// MD5密码
							user.setEnumConstByFlagIsvalid(enumConstByFlagIsvalid);// 有效状态
							user.setLoginId(code);// 登陆ID
							user.setPePriRole(ppr);
							user = (SsoUser) this.getGeneralDao().save(user);
							// 创建一级管理员
							PeEnterpriseManager pem = new PeEnterpriseManager();
							pem.setPeEnterprise(pe);
							pem.setSsoUser(user);
							pem.setLoginId(code);
							pem.setEnumConstByFlagIsvalid(enumConstByFlagIsvalid);

							// 保存管理员
							this.getGeneralDao().save(pem);
						} catch (Exception e1) {
							msg += "管理员保存失败！";
							e1.printStackTrace();
							String uSql = "update hzph_data_bridge.hr_site hs set hs.hrs_result='2' where hs.rowid='" + rowid + "'";
							this.getGeneralDao().executeBySQL(uSql);
						}
						String uSql = "update hzph_data_bridge.hr_site hs set hs.hrs_result='1' where hs.rowid='" + rowid + "'";
						db.executeUpdate(uSql);
						System.out.println("Add an Ent:" + code);
					} else if ("02".equalsIgnoreCase(hrs_state)) {// 修改
						if (pex == null) {
							String uSql = "update hzph_data_bridge.hr_site hs set hs.hrs_result='2',hs.note='机构不存在，不能修改' where hs.rowid='"
									+ rowid + "'";
							db.executeUpdate(uSql);
							continue;
						}
						String oldName = pex.getName();
						// 修改机构
						if (type != null && type.length() > 0) {
							EnumConst enumConstByFlagEnterpriseType = null;
							DetachedCriteria dc = DetachedCriteria.forClass(EnumConst.class);
							dc.add(Restrictions.eq("namespace", "FlagEnterpriseType").ignoreCase());
							dc.add(Restrictions.eq("code", type).ignoreCase());
							List<EnumConst> ecList = this.getGeneralDao().getList(dc);
							if (ecList != null && ecList.size() > 0) {
								enumConstByFlagEnterpriseType = ecList.get(0);
							}
							pex.setEnumConstByFlagEnterpriseType(enumConstByFlagEnterpriseType);
						}
						if (name != null && name.length() > 0) {
							pex.setName(name);
						}
						if (re_address != null && re_address.length() > 0) {
							pex.setEnrolAddress(re_address);
						}
						if (office_address != null && office_address.length() > 0) {
							pex.setAddress(office_address);
						}
						if (website != null && website.length() > 0) {
							pex.setNetAddress(website);
						}
						/**
						 * 暂时改为不同步密码 if(password!=null && password.length()>0){
						 * //修改密码 if(!updatePwd(code,password)){ String uSql =
						 * "update hzph_data_bridge.hr_site hs set
						 * hs.hrs_result='2',hs.note='机构管理员不存在，不能修改密码' where
						 * hs.rowid='"+rowid+"'"; this.executeBySQL(uSql);
						 * continue; } }
						 */
						this.getGeneralDao().save(pex);
						if(name !=null && !name.equals(oldName)){
							String updateSql = " update pe_enterprise p set p.name = replace(p.name,'"+ oldName +"','"+ name +"') where p.FK_PARENT_ID ='"+ pex.getId()+"'  ";
							db.executeUpdate(updateSql);
						}
						String uSql = "update hzph_data_bridge.hr_site hs set hs.hrs_result='1' where hs.rowid='" + rowid + "'";
						db.executeUpdate(uSql);
						System.out.println("Modify an Ent:" + code);
					} else if ("03".equalsIgnoreCase(hrs_state)) {// 删除
						EnumConst enumConstByFlagEnterpriseState = this.getEnumConstService()
								.getByNamespaceCode("FlagEnterpriseState", "1");// 机构有效状态
						// 否
						pex.setEnumConstByFlagEnterpriseState(enumConstByFlagEnterpriseState);
						this.getGeneralDao().save(pex);
						String uSql = "update hzph_data_bridge.hr_site hs set hs.hrs_result='1',hs.note='删除机构，培训平台只记录，不做特殊处理。' where hs.rowid='"
								+ rowid + "'";
						db.executeUpdate(uSql);
						continue;
					} else {// 未知状态
						continue;
					}
					String uSql = "update hzph_data_bridge.hr_site hs set hs.hrs_result='1' where hs.rowid='" + rowid + "'";
					db.executeUpdate(uSql);
					WhatyuserLog4j log = new WhatyuserLog4j();
					// log.setIp(ServletActionContext.getRequest().getRemoteAddr());
					log.setBehavior("QuartzServiceImp_moveSiteService"); // 动作
					log.setNotes("同步站点，站点CODE：" + code + "--" + msg);// 说明；
					log.setStatus("success");
					log.setOperateTime(new Date()); // 时间
					if (us == null) {
						log.setUserid("系统处理"); // SSO LOGINID
						log.setLogtype("系统处理");
					} else {
						log.setUserid(us.getLoginId()); // SSO LOGINID
						log.setLogtype(us.getUserLoginType());
					}
					try {
						this.getGeneralDao().save(log);
					} catch (Exception e) {
						System.out.println("同步机构保存日志失败--" + "同步站点，站点CODE：" + code + "--" + msg);
						// e.printStackTrace();
					}
				} catch (Exception ex) {
					String uSql = "update hzph_data_bridge.hr_site hs set hs.hrs_result='2',hs.note='异常：" + ex.getMessage()
							+ "' where hs.rowid='" + rowid + "'";
					db.executeUpdate(uSql);
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println("同步机构失败--" + new Date());
			// e.printStackTrace();
		} finally {
			db.close(rs);
			db = null;
		}
	}

	public void moveStuService(int size) {
		long start = new Date().getTime();
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "";
		String sqlUpdate = "";
		String sqlInsert = "";
		String sqlInit = "update hzph_data_bridge.hr_result t set t.hrr_result = '" + start + "', t.note='等待执行中' where t.hrr_result = '0'"
				+ "	and rowid in\n" + "	       (select rid\n" + "   	       from (select rowid as rid, rownum as rnum\n"
				+ "       	           from hzph_data_bridge.hr_result hr\n"
				+ "           	      where hr.hrr_result = '0' and hrr_state <>'05'\n"
				+ "    	             order by hr.id,hr.update_date)\n" + "       	  where rnum <= " + size + ")";
		db.executeUpdate(sqlInit);
		sql = "select t.*,t.rowid as rid from hzph_data_bridge.hr_result t where t.hrr_result = '" + start
				+ "' order by t.id,t.update_date";
		try {
			rs = db.execute_oracle_page(sql, 1, size);
			while (rs.next()) {
				long end = new Date().getTime();
				if ((end - start) > 1200000) {// 执行超过20分钟自动停止并归还锁定的数据，防止重复执行或漏执行
					String sqlstat = "update hzph_data_bridge.hr_result hs set hs.hrr_result='0' where hs.hrr_result='" + start
							+ "', hs.note='执行超过20分钟自动停止并归还锁定的数据，防止重复执行或漏执行'";
					db.executeUpdate(sqlstat);
					break;
				}
				String stuId = strNew(rs.getString("id"));
				String siteid = rs.getString("siteid");
				String hrr_state = rs.getString("hrr_state");
//				String password = strNew(rs.getString("password"));
				String password = MyUtil.md5("sac123");
				String name = strNew(rs.getString("name"));
				String isEmployee = strNew(rs.getString("is_employee"));// 是否具有从业资格:0无1有
				String work_address = strNew(rs.getString("work_address"));//工作区域
				String work = strNew(rs.getString("work"));//从事业务
						
				if (null == isEmployee || "".equals(isEmployee))
					isEmployee = "0";
				try {
					if ("01".equals(hrr_state)) {
						if (!isExist(rs.getString("id"))) {// 如果不存学号,则插入该学员信息

							/* 插入到sso_user表 */
							sqlInsert = "insert into sso_user\n" + "  (id,\n" + "   login_id,\n" + "   password,\n" + "   fk_role_id,\n"
									+ "   flag_isvalid,\n" + "   login_num,\n" + "   password_bk,\n" + "   password_md5,\n"
									+ "   sum_amount,\n" + "   amount)\n" + "	values\n" + "  ('" + stuId + "'," + "	'" + stuId + "',"
									+ "	'字段弃用'," + "	'0'," + "	'2'," + "	'0'," + "	'" + password + "'," + "	'" + password + "'," + "	'0',"
									+ "	'0')";
							db.executeUpdate(sqlInsert);

							/* 插入到学员表 */
							sqlInsert = "insert into pe_bzz_student\n" + "  (id,\n" + "   name,\n" + "   reg_no,\n"
									+ "   fk_sso_user_id,\n" + "   fk_enterprise_id,\n" + "   gender,\n" + "   folk,\n" + "   gznx,\n"
									+ "   education,\n" + "   email,\n" + "   mobile_phone,\n" + "   position,\n" + "   true_name,\n"
									+ "   department,\n" + "   card_type,\n" + "   address,\n" + "   zige,\n" + "   phone,\n"
									+ "   zjlx,\n" + "   card_no,\n" + "	is_employee,ata_id,work_address,work)\n" + "	values\n" + "   ('"
									+ stuId
									+ "',"
									+ "   '"
									+ stuId
									+ "/"
									+ name
									+ "',"
									+ "	'"
									+ stuId
									+ "',"
									+ "	'"
									+ stuId
									+ "',"
									+ "	'"
									+ strNew(findIdBycode(strNew(rs.getString("siteid"))))
									+ "',"
									+ "	'"
									+ strNew(rs.getString("gender"))
									+ "',"
									+ "	'"
									+ strNew(rs.getString("mz"))
									+ "',"
									+ "	'"
									+ strNew(rs.getString("workyears"))
									+ "',"
									+ "	'"
									+ strNew(rs.getString("edutype"))
									+ "',"
									+ "	'"
									+ strNew(rs.getString("email"))
									+ "',"
									+ "	'"
									+ strNew(rs.getString("mobile_tel"))
									+ "',"
									+ "	'"
									+ strNew(rs.getString("post"))
									+ "',"
									+ "	'"
									+ name
									+ "',"
									+ "	'"
									+ strNew(rs.getString("section"))
									+ "',"
									+ "	'"
									+ strNew(rs.getString("nationality"))
									+ "',"
									+ "	'"
									+ strNew(rs.getString("address"))
									+ "',"
									+ "	'"
									+ strNew(zigeId(strNew(rs.getString("zige"))))
									+ "',"
									+ "	'"
									+ strNew(rs.getString("phone"))
									+ "',"
									+ "	'"
									+ strNew(rs.getString("cardtype"))
									+ "',\n"
									+ "	'"
									+ strNew(rs.getString("cardno_new"))
									+ "',\n"
									+ "	'" + this.getEnumConstService().getByNamespaceCode("IsEmployee", isEmployee).getId() + "','从业同步','" + work_address + "','" + work + "')";
							int num = db.executeUpdate(sqlInsert);
							System.out.println("Add a Student:" + stuId);
						} else {
							// 修改状态，改为异常
							String sqlstat = "update hzph_data_bridge.hr_result hs set hs.hrr_result='2', hs.ed_date=sysdate, hs.note='新增请求，但账号已存在，不处理。' where hs.hrr_result='"
									+ start + "' and hs.rowid='" + strNew(rs.getString("rid")) + "'";
							db.executeUpdate(sqlstat);
							continue;
						}
					} else if ("02".equals(hrr_state) || "04".equals(hrr_state)) { // 02信息修改
						// 04重新激活从业资格
						if (isExist(strNew(stuId)) && this.backupStu(stuId)) {// 备份学生

							DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
							dc.createAlias("peEnterprise", "peEnterprise", DetachedCriteria.LEFT_JOIN);
							dc.add(Restrictions.eq("regNo", stuId).ignoreCase());
							List<PeBzzStudent> stulist = this.getGeneralDao().getList(dc);
							if (stulist != null && stulist.size() > 0) {
								StringBuffer sb = new StringBuffer();
								PeBzzStudent peBzzStudent = stulist.get(0);
								PeBzzEditStudent peBzzEditStudent = new PeBzzEditStudent();
								peBzzEditStudent.setPeBzzStudent(peBzzStudent);
								peBzzEditStudent.setOldRegNo(peBzzStudent.getRegNo());
								peBzzEditStudent.setCreateDate(new Date());
								peBzzEditStudent.setNewRegNo(stuId);
								peBzzEditStudent.setEnumConstByFlagEditCheck("ccb2880a91dadc115011dadfcf26b0005x");
								peBzzEditStudent.setStatus("02");
								sb.append(" update pe_bzz_student t set t.fk_batch_id=t.fk_batch_id ,");
								sb.append(" t.is_employee = '"
										+ this.getEnumConstService().getByNamespaceCode("IsEmployee", isEmployee).getId() + "',");
								if (siteid != null && !"".equals(siteid.trim())) {
									// 机构变动判断语句，避免二级机构问题
									if ("0000".equals(peBzzStudent.getPeEnterprise().getId()) && "04".equals(hrr_state)) {// 无机构的学员激活从业资格,不需要审核
										sb.append(" t.fk_enterprise_id='" + this.getEnterprise(siteid.trim()).getId() + "',");
										// sb.append(" t.is_employee =
										// '"+this.getEnumConstService().getByNamespaceCode("IsEmployee","1").getId()+"',");
										peBzzEditStudent.setOldPeEnterprise(peBzzStudent.getPeEnterprise());
										peBzzEditStudent.setNewPeEnterprise(this.getEnterprise(siteid.trim()));
									} else if ("0000".equals(peBzzStudent.getPeEnterprise().getId())) {
										sb.append(" t.fk_enterprise_id='" + this.getEnterprise(siteid.trim()).getId() + "',");
										peBzzEditStudent.setOldPeEnterprise(peBzzStudent.getPeEnterprise());
										peBzzEditStudent.setNewPeEnterprise(this.getEnterprise(siteid.trim()));
									} else {// 普通机构变更判断
										// if("04".equals(hrr_state)){//有机构的学员激活从业资格，机构变动可能需要审核
										// sb.append(" t.is_employee =
										// '"+this.getEnumConstService().getByNamespaceCode("IsEmployee","1").getId()+"',");
										// }
										String checkSql = "select pbs.reg_no\n"
												+ "  from pe_bzz_student pbs, pe_enterprise pe1, pe_enterprise pe2\n"
												+ " where pbs.fk_enterprise_id = pe1.id\n" + "   and pe1.fk_parent_id = pe2.id(+)\n"
												+ "   and pbs.reg_no='" + peBzzStudent.getRegNo() + "'\n" + "   and (pe1.code='"
												+ siteid.trim() + "' or pe2.code='" + siteid.trim() + "')";
										if (db.countselect(checkSql) < 1) {// 机构变动时语句返回0
											peBzzEditStudent.setOldPeEnterprise(peBzzStudent.getPeEnterprise());
											peBzzEditStudent.setNewPeEnterprise(this.getEnterprise(siteid.trim()));
											if (this.check(peBzzStudent)) {// 自动剥离学时到一级管理员
												try {
													sb.append(" t.fk_enterprise_id='" + this.getEnterprise(siteid.trim()).getId() + "',");
												} catch (Exception e) {
													e.printStackTrace();
													String sqlstat = "update hzph_data_bridge.hr_result hs set hs.hrr_result='2', hs.ed_date=sysdate, hs.note='修改请求，但账号所在机构不存在，不处理。' where hs.hrr_result='"
															+ start + "' and hs.rowid='" + strNew(rs.getString("rid")) + "'";
													db.executeUpdate(sqlstat);
												}
											} else {
												peBzzEditStudent.setEnumConstByFlagEditCheck("ccb2880a91dadc115011dadfcf26b0007x");// 剥离失败
											}
										}
									}
								}
								if (rs.getString("edutype") != null && !"".equals(rs.getString("edutype"))) {
									sb.append(" t.education='" + rs.getString("edutype").trim() + "',");
									peBzzEditStudent.setOldEducation(peBzzStudent.getEducation());
									peBzzEditStudent.setNewEducation(rs.getString("edutype").trim());
								}
								if (rs.getString("post") != null && !"".equals(rs.getString("post"))) {
									sb.append(" t.position='" + rs.getString("post").trim() + "',");
								}
								if (rs.getString("gender") != null && !"".equals(rs.getString("gender"))) {
									sb.append(" t.gender='" + rs.getString("gender").trim() + "',");
									peBzzEditStudent.setOldGender(peBzzStudent.getGender());
									peBzzEditStudent.setNewGender(rs.getString("gender").trim());
								}
								if (rs.getString("mz") != null && !"".equals(rs.getString("mz"))) {
									sb.append(" t.folk='" + rs.getString("mz").trim() + "',");
									peBzzEditStudent.setOldFolk(peBzzStudent.getFolk());
									peBzzEditStudent.setNewFolk(rs.getString("mz").trim());
								}

								if (rs.getString("email") != null && !"".equals(rs.getString("email"))) {
									sb.append(" t.email='" + rs.getString("email").trim() + "',");
									peBzzEditStudent.setOldEmail(peBzzStudent.getEmail());
									peBzzEditStudent.setNewEmail(rs.getString("email").trim());
								}
								if (rs.getString("mobile_tel") != null && !"".equals(rs.getString("mobile_tel"))) {
									sb.append(" t.mobile_phone='" + rs.getString("mobile_tel").trim() + "',");
									peBzzEditStudent.setOldMobilePhone(peBzzStudent.getMobilePhone());
									peBzzEditStudent.setNewMobilePhone(rs.getString("mobile_tel").trim());
								}
								if (name != null && !"".equals(name)) {
									sb
											.append(" t.true_name='" + name.trim() + "',t.name='" + rs.getString("id").trim() + "/" + name
													+ "',");
									peBzzEditStudent.setOldTrueName(peBzzStudent.getTrueName());
									peBzzEditStudent.setNewTrueName(name);
								}
								if (rs.getString("section") != null && !"".equals(rs.getString("section"))) {
									sb.append(" t.department='" + rs.getString("section").trim() + "',");
								}
								if (rs.getString("address") != null && !"".equals(rs.getString("address"))) {
									sb.append(" t.address='" + rs.getString("address").trim() + "',");
									peBzzEditStudent.setOldAddress(peBzzStudent.getAddress());
									peBzzEditStudent.setNewAddress(rs.getString("address").trim());
								}
								if (rs.getString("zige") != null && !"".equals(rs.getString("zige"))) {
									sb.append(" t.zige='" + zigeId(rs.getString("zige").trim()) + "',");
								}
								if (rs.getString("nationality") != null && !"".equals(rs.getString("nationality"))) {
									sb.append(" t.card_type='" + rs.getString("nationality").trim() + "',");
									peBzzEditStudent.setOldCardType(peBzzStudent.getCardType());
									peBzzEditStudent.setNewCardType(rs.getString("nationality").trim());
								}
								if (rs.getString("cardtype") != null && !"".equals(rs.getString("cardtype"))) {
									sb.append(" t.zjlx='" + rs.getString("cardtype").trim() + "',");
									peBzzEditStudent.setNewZjlx(rs.getString("cardtype").trim());
								}
								if (rs.getString("workyears") != null && !"".equals(rs.getString("workyears"))) {
									sb.append(" t.gznx='" + rs.getString("workyears").trim() + "',");
								}
								if (rs.getString("cardno_new") != null && !"".equals(rs.getString("cardno_new"))) {
									sb.append(" t.card_no='" + rs.getString("cardno_new").trim() + "',");
									peBzzEditStudent.setOldCardNo(peBzzStudent.getCardNo());
									peBzzEditStudent.setNewCardNo(rs.getString("cardno_new").trim());
								}
								//工作区域
								if (rs.getString("work_address") != null && !"".equals(rs.getString("work_address"))) {
									sb.append(" t.work_address='" + rs.getString("work_address").trim() + "',");
								}
								//从事业务
								if (rs.getString("work") != null && !"".equals(rs.getString("work"))) {
									sb.append(" t.work='" + rs.getString("work").trim() + "',");
								}
								sb = sb.deleteCharAt(sb.length() - 1);// 去掉最后一位的逗号
								sb.append(" where upper(t.reg_no)=upper('" + stuId + "')");
								sqlUpdate = sb.toString();

								// db.executeUpdate(sqlUpdate);
								// System.out.println("updateSql:"+sqlUpdate);
								int re = db.executeUpdate(sqlUpdate);
								if (re < 1) {
									peBzzEditStudent.setEnumConstByFlagEditCheck("ccb2880a91dadc115011dadfcf26b0007x");// 执行失败，变为待审核
								}
								/**
								 * 同步密码已经改为单独处理
								 * if(password!=null&&!"".equals(password)){
								 * updatePwd(stuId,password.trim());//修改密码 }
								 */
								this.getGeneralDao().save(peBzzEditStudent);
								System.out.println("Modify a Student:" + stuId);
								// 同步学生选课信息
								// moveStuEleService(stuId);
								// System.out.println("Sync elective for Student:" + stuId);
							}
						} else {// 如果不存学号,记录错误信息
							String sqlstat = "update hzph_data_bridge.hr_result hs set hs.hrr_result='2', hs.ed_date=sysdate, hs.note='修改请求，但账号不存在，不处理。' where hs.hrr_result='"
									+ start + "' and hs.rowid='" + strNew(rs.getString("rid")) + "'";
							db.executeUpdate(sqlstat);
							continue;
						}
					} else if ("03".equals(hrr_state)) {
						if (isExist(rs.getString("id")) && this.backupStu(stuId)) {// 备份学生
							DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
							dc.createAlias("peEnterprise", "peEnterprise", DetachedCriteria.LEFT_JOIN);
							dc.add(Restrictions.eq("regNo", stuId).ignoreCase());
							List<PeBzzStudent> stulist = this.getGeneralDao().getList(dc);
							if (stulist != null && stulist.size() > 0) {
								PeBzzStudent peBzzStudent = stulist.get(0);
								PeBzzEditStudent peBzzEditStudent = new PeBzzEditStudent();
								peBzzEditStudent.setPeBzzStudent(peBzzStudent);
								peBzzEditStudent.setOldRegNo(peBzzStudent.getRegNo());
								peBzzEditStudent.setNewRegNo(peBzzStudent.getRegNo());
								peBzzEditStudent.setOldPeEnterprise(peBzzStudent.getPeEnterprise());
								peBzzEditStudent.setNewPeEnterprise(this.getEnterprise("0000"));
								peBzzEditStudent.setEnumConstByFlagEditCheck("ccb2880a91dadc115011dadfcf26b0005x");// 默认通过审核
								peBzzEditStudent.setStatus("03");
								peBzzEditStudent.setCreateDate(new Date());
								if (this.check(peBzzStudent)) { // 剥离学时
									// 修改学员数据
									StringBuffer sb = new StringBuffer();
									sb.append(" update pe_bzz_student t set t.fk_batch_id=t.fk_batch_id ,");
									sb.append(" t.is_employee = '"
											+ this.getEnumConstService().getByNamespaceCode("IsEmployee", "0").getId() + "',");
									sb.append(" t.fk_enterprise_id = '0000',");
									sb.append(" t.address = '',");
									sb.append(" t.department = '',");
									sb.append(" t.position = '',");
									sb.append(" t.work_address = '',");//工作区域
									sb.append(" t.work = '',");//从事业务
									sb.append(" t.zige = '4028808c1dbe60fe011dbe7866590093'");
									sb.append(" where upper(t.reg_no)=upper('" + stuId + "')");
									db.executeUpdate(sb.toString());
								} else {
									peBzzEditStudent.setEnumConstByFlagEditCheck("ccb2880a91dadc115011dadfcf26b0007x");// 出错的数据标记为为待审核
								}
								this.getGeneralDao().save(peBzzEditStudent);
								System.out.println("Delete a Student:" + stuId);
							} else {
								String sqlstat = "update hzph_data_bridge.hr_result hs set hs.hrr_result='2', hs.ed_date=sysdate, hs.note='账号不存在' where hs.hrr_result='"
										+ start + "' and hs.rowid='" + strNew(rs.getString("rid")) + "'";
								db.executeUpdate(sqlstat);
								continue;
							}
						} else {// 如果不存学号,记录错误信息
							String sqlstat = "update hzph_data_bridge.hr_result hs set hs.hrr_result='2', hs.ed_date=sysdate, hs.note='离职请求，但账号不存在，不处理。' where hs.hrr_result='"
									+ start + "' and hs.rowid='" + strNew(rs.getString("rid")) + "'";
							db.executeUpdate(sqlstat);
							continue;
						}
					}
					// 修改状态，改为已迁移
					String sqlstat = "update hzph_data_bridge.hr_result hs set hs.hrr_result='1', hs.ed_date=sysdate, hs.note='' where hs.hrr_result='"
							+ start + "' and hs.rowid='" + strNew(rs.getString("rid")) + "'";
					db.executeUpdate(sqlstat);

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					System.out.println("同步学员失败！！！" + e.getMessage());
					String sqlstat = "update hzph_data_bridge.hr_result hs set hs.hrr_result='2', hs.ed_date=sysdate, hs.note='同步学员失败，请检查后台日志。' where hs.hrr_result='"
							+ start + "' and hs.rowid='" + strNew(rs.getString("rid")) + "'";
					db.executeUpdate(sqlstat);
					continue;
				}
			}
			// 归还可能存在的没有处理的数据
			String sqlstat = "update hzph_data_bridge.hr_result hs set hs.hrr_result='0', hs.ed_date=sysdate, hs.note='' where hs.hrr_result='"
					+ start + "'";
			db.executeUpdate(sqlstat);
		} catch (Exception e) {
			System.out.println("同步学员失败！！！");
			// e.printStackTrace();
		} finally {
			db.close(rs);
			db = null;
		}
	}

	/**
	 * 迁移学员信息
	 * 
	 * @param size
	 * @return
	 * @author linjie
	 */
	private boolean backupStu(String regNo) {
		if (regNo == null || "".equals(regNo)) {
			return false;
		} else {
			try {
				String inSql = "insert into pe_bzz_student_copy\n" + "  (id,\n" + "   name,\n" + "   reg_no,\n" + "   fk_sso_user_id,\n"
						+ "   fk_enterprise_id,\n" + "   gender,\n" + "   folk,\n" + "   education,\n" + "   age,\n" + "   position,\n"
						+ "   title,\n" + "   department,\n" + "   address,\n" + "   zipcode,\n" + "   phone,\n" + "   mobile_phone,\n"
						+ "   email,\n" + "   fk_batch_id,\n" + "   true_name,\n" + "   birthday,\n" + "   export_state,\n"
						+ "   regist_people,\n" + "   regist_date,\n" + "   fk_recruit_id,\n" + "   export_people,\n" + "   export_date,\n"
						+ "   sub_enterprise_name,\n" + "   photo,\n" + "   flag_rank_state,\n" + "   data_date,\n"
						+ "   photo_confirm_date,\n" + "   photo_unconfirm_reason,\n" + "   photo_confirm,\n" + "   study_end_date,\n"
						+ "   is_goodstu,\n" + "   job_number,\n" + "   pick_user,\n" + "   fk_site_id,\n" + "   card_type,\n"
						+ "   card_no,\n" + "   groups,\n" + "   fk_grade_id,\n" + "   fk_exambatch_id,\n" + "   student_type,\n"
						+ "   is_employee,\n" + "   check_state,\n" + "   enterprise_id,\n" + "   zige,\n" + "   enterprise_name,\n"
						+ "   sub_groups,\n" + "   com_ten_hours_date,\n" + "   com_fifteen_hours_date,\n" + "   ata_id,\n"
						+ "   index_id,\n" + "   copy_carid,\n" + "   zjlx,\n" + "   gznx,\n" + "   modify_date)\n" + "  select id,\n"
						+ "         name,\n" + "         reg_no,\n" + "         fk_sso_user_id,\n" + "         fk_enterprise_id,\n"
						+ "         gender,\n" + "         folk,\n" + "         education,\n" + "         age,\n" + "         position,\n"
						+ "         title,\n" + "         department,\n" + "         address,\n" + "         zipcode,\n"
						+ "         phone,\n" + "         mobile_phone,\n" + "         email,\n" + "         fk_batch_id,\n"
						+ "         true_name,\n" + "         birthday,\n" + "         export_state,\n" + "         regist_people,\n"
						+ "         regist_date,\n" + "         fk_recruit_id,\n" + "         export_people,\n" + "         export_date,\n"
						+ "         sub_enterprise_name,\n" + "         photo,\n" + "         flag_rank_state,\n" + "         data_date,\n"
						+ "         photo_confirm_date,\n" + "         photo_unconfirm_reason,\n" + "         photo_confirm,\n"
						+ "         study_end_date,\n" + "         is_goodstu,\n" + "         job_number,\n" + "         pick_user,\n"
						+ "         fk_site_id,\n" + "         card_type,\n" + "         card_no,\n" + "         groups,\n"
						+ "         fk_grade_id,\n" + "         fk_exambatch_id,\n" + "         student_type,\n"
						+ "         is_employee,\n" + "         check_state,\n" + "         enterprise_id,\n" + "         zige,\n"
						+ "         enterprise_name,\n" + "         sub_groups,\n" + "         com_ten_hours_date,\n"
						+ "         com_fifteen_hours_date,\n" + "         ata_id,\n" + "         index_id,\n" + "         copy_carid,\n"
						+ "         zjlx,\n" + "         gznx,\n" + "         sysdate\n" + "    from pe_bzz_student pbs\n"
						+ "   where upper(pbs.reg_no) = upper('" + regNo + "')";
				this.getGeneralDao().executeBySQL(inSql);
				return true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}

	/**
	 * 判断是否存在该学员
	 * 
	 * @param string
	 * @return
	 * @author linjie
	 */
	private boolean isExist(String stuId) {
		// TODO Auto-generated method stub
		dbpool db = new dbpool();
		boolean flag = false;
		String sql = "select id from pe_bzz_student pbs where upper(pbs.reg_no)=upper('" + stuId + "')";
		try {
			int re = db.countselect(sql);
			if (re > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 根据code获得机构实体
	 * 
	 * @param code
	 * @return
	 * @author linjie
	 */
	public PeEnterprise getEnterprise(String code) {
		PeEnterprise enterprise = null;
		DetachedCriteria dc = DetachedCriteria.forClass(PeEnterprise.class);
		dc.add(Restrictions.eq("code", code).ignoreCase());
		List<PeEnterprise> pelist = this.getGeneralDao().getList(dc);
		if (pelist != null && pelist.size() > 0) {
			enterprise = pelist.get(0);
		}
		return enterprise;
	}

	/**
	 * 修改密码,暂时把密码存放到password_bk字段
	 * 
	 * @param cardno
	 * @param pwd
	 * @author linjie
	 */
	public boolean updatePwd(String stuId, String pwd) {
		// TODO Auto-generated method stub
		dbpool db = new dbpool();
		int re = 0;
		String sql = "update sso_user s set s.password_bk='" + pwd + "' where upper(s.login_id)=upper('" + stuId + "')";
		System.out.println(sql);
		try {
			re = db.executeUpdate(sql);
			System.out.println("修改密码成功！");
		} catch (Exception e) {
			System.out.println("修改密码失败！");
			e.printStackTrace();
		} finally {
			db = null;
		}
		return re > 0;
	}

	/**
	 * 根据机构代码code查找机构id
	 * 
	 * @param code
	 * @return
	 * @author linjie
	 */
	public String findIdBycode(String code) {
		String id = "0000";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id from pe_enterprise where code='" + code + "'";
		try {
			rs = db.executeQuery(sql);
			if (rs.next()) {
				id = rs.getString("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
		}
		return id;
	}

	/**
	 * 根据资格类型code查找id
	 * 
	 * @param zige
	 * @return
	 * @author linjie
	 */
	public String zigeId(String zige) {
		String id = "";
		dbpool db = new dbpool();
		MyResultSet rs = null;
		String sql = "select id from enum_const ec where ec.code='" + zige + "' and ec.namespace='FlagQualificationsType'";
		try {
			rs = db.executeQuery(sql);
			if (rs.next()) {
				id = rs.getString("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
		}
		return id;
	}

	/**
	 * 去掉空格
	 * 
	 * @param str
	 * @return
	 * @author linjie
	 */
	public String strNew(String str) {
		String strNew = "";
		if (str != null && !"".equals(str)) {
			strNew = str.trim();
		}
		return strNew;
	}

	/**
	 * 初始化statistic_enterprise表
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeInitEntry(String dateStr) {
		/* 插入机构id,年、月，如果存在则 */
		dbpool db = new dbpool();
		String sql = "insert into statistic_enterprise\n" + "  (id, year, month, fk_enterprise_id)\n"
				+ "  select SEQ_STATISTIC_ID.NEXTVAL,\n" + "         to_char(trunc(" + dateStr + "-1), 'yyyy'),\n"
				+ "         to_char(trunc(" + dateStr + "-1), 'MM'),\n" + "         id\n" + "    from pe_enterprise\n"
				+ "   where fk_parent_id is null\n" + "     and id not in (select se.fk_enterprise_id\n"
				+ "                      from statistic_enterprise se\n" + "                     where se.year = to_char(trunc(" + dateStr
				+ "-1), 'yyyy')\n" + "                       and se.month = to_char(trunc(" + dateStr + "-1), 'MM'))";

		try {
			int count = db.executeUpdate(sql);
			System.out.println("成功插入" + count + "条机构！");
		} catch (Exception e) {
			System.out.println("已存在所有机构，无需插入！");
		} finally {
			System.out.println("初始化机构完毕！");
			db = null;
		}
	}

	/**
	 * 更新机构类型
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatEntry(String dateStr) {
		System.out.println("正在更新机构类型。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.enterprise_type = (select pe.entype\n"
				+ "                               from pe_enterprise pe\n"
				+ "                              where ww.fk_enterprise_id = pe.id(+))\n" + " where ww.year = to_char(trunc(" + dateStr
				+ "-1), 'yyyy')\n" + "   and ww.month = to_char(trunc(" + dateStr + "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条机构类型完毕！");
		} catch (Exception e) {
			System.out.println("更新机构类型失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新注册人数累计
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatRegUserCount(String dateStr) {
		System.out.println("正在更新注册人数累计。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.reg_user_count =\n" + "       (select sum(id1)\n"
				+ "          from (select count(pbs.id) as id1, pe.id as enId\n"
				+ "                  from pe_bzz_student pbs, pe_enterprise pe\n" + "                 where pbs.fk_enterprise_id = pe.id\n"
				+ "                   and pe.fk_parent_id is null\n" + "                   and pbs.data_date <= trunc(" + dateStr
				+ " - 1)\n" + "                 group by pe.id\n" + "                union all\n"
				+ "                select count(pbs.id) as id1, pe.fk_parent_id as enId\n"
				+ "                  from pe_bzz_student pbs, pe_enterprise pe\n" + "                 where pbs.fk_enterprise_id = pe.id\n"
				+ "                   and pe.fk_parent_id is not null\n" + "                   and pbs.data_date <= trunc(" + dateStr
				+ " - 1)\n" + "                 group by pe.fk_parent_id)\n" + "         where enId = ww.fk_enterprise_id\n"
				+ "         group by enId)\n" + " where ww.year = to_char(trunc(" + dateStr + " - 1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc(" + dateStr + " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条注册人数累计完毕！");
		} catch (Exception e) {
			System.out.println("更新注册人数累计失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新注册人数新增
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatRegUserNum(String dateStr) {
		System.out.println("正在更新注册人数新增。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.reg_user_num = (select sum(bb.num)\n"
				+ "                            from (select b.num as num, b.enId as enId\n"
				+ "                                    from (select count(pbs.id) as num,\n"
				+ "                                                 pe.id as enId\n"
				+ "                                            from pe_bzz_student pbs,\n"
				+ "                                                 pe_enterprise  pe\n"
				+ "                                           where to_char(nvl(pbs.data_date,\n"
				+ "                                                             to_date('1900-01-01 00:00:00',\n"
				+ "                                                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                         'yyyy-MM') =\n"
				+ "                                                 to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                             and pbs.fk_enterprise_id = pe.id\n"
				+ "                                             and pe.fk_parent_id is null\n"
				+ "                                           group by pe.id) b\n"
				+ "                                  union all\n"
				+ "                                  select b.num as num, b.enId as enId\n"
				+ "                                    from (select count(pbs.id) as num,\n"
				+ "                                                 pe.fk_parent_id as enId\n"
				+ "                                            from pe_bzz_student pbs,\n"
				+ "                                                 pe_enterprise  pe\n"
				+ "                                           where to_char(nvl(pbs.data_date,\n"
				+ "                                                             to_date('1900-01-01 00:00:00',\n"
				+ "                                                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                         'yyyy-MM') =\n"
				+ "                                                 to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                             and pbs.fk_enterprise_id = pe.id\n"
				+ "                                             and pe.fk_parent_id is not null\n"
				+ "                                           group by pe.fk_parent_id) b) bb\n"
				+ "                           where bb.enId = ww.fk_enterprise_id\n"
				+ "                           group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc("
				+ dateStr
				+ "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条注册人数新增完毕！");
		} catch (Exception e) {
			System.out.println("更新注册人数新增失败！");
			e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新注册机构累计
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatRegEnCount(String dateStr) {
		System.out.println("正在更新注册机构累计。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql =

		"update statistic_enterprise ww\n" + "   set ww.reg_enterprise_count = (select b.num\n"
				+ "                                    from (select count(pe.id) as num,\n"
				+ "                                                 pe.entype as enType\n"
				+ "                                            from pe_enterprise pe\n"
				+ "                                           where pe.entype is not null\n"
				+ "                                             and pe.data_date <=\n"
				+ "                                                 trunc(" + dateStr + " - 1)\n"
				+ "                                           group by pe.entype) b\n"
				+ "                                   where ww.enterprise_type = b.enType)\n" + " where ww.year = to_char(trunc(" + dateStr
				+ " -1), 'yyyy')\n" + "   and ww.month = to_char(trunc(" + dateStr + " -1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条注册机构累计完毕！");
		} catch (Exception e) {
			System.out.println("更新注册机构累计失败！");
			e.printStackTrace();
		}
	}

	/**
	 * 更新注册机构新增
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatRegEnNum(String dateStr) {
		System.out.println("正在更新注册机构新增。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.reg_enterprise_num = (select b.num\n"
				+ "                                  from (select count(pe.id) num,\n"
				+ "                                               pe.entype as enType\n"
				+ "                                          from pe_enterprise pe\n"
				+ "                                         where to_char(nvl(pe.data_date,\n"
				+ "                                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                       'yyyy-MM') =\n"
				+ "                                               to_char(trunc(" + dateStr + "-1), 'yyyy-MM')\n"
				+ "                                         group by pe.entype) b\n"
				+ "                                 where ww.enterprise_type = b.enType)\n" + " where ww.year = to_char(trunc(" + dateStr
				+ "-1), 'yyyy')\n" + "   and ww.month = to_char(trunc(" + dateStr + "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条注册机构新增完毕！");
		} catch (Exception e) {
			System.out.println("更新注册机构新增失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新开始测验累计
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatBeginTesCount(String dateStr) {
		System.out.println("正在更新开始测验累计。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql =

		"update statistic_enterprise ww\n" + "   set ww.test_begin_times_count = (select sum(num)\n"
				+ "                                      from (select b.num  as num,\n"
				+ "                                                   b.enId as enId\n"
				+ "                                              from (select pe.id as enId,\n"
				+ "                                                           sum(case\n"
				+ "                                                                 when nvl(pbtse.exam_times,\n"
				+ "                                                                          0) > 0 then\n"
				+ "                                                                  1\n"
				+ "                                                                 else\n"
				+ "                                                                  0\n"
				+ "                                                               end) as num\n"
				+ "                                                      from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                           pe_bzz_student          pbs,\n"
				+ "                                                           pe_enterprise           pe\n"
				+ "                                                     where to_char(nvl(pbtse.elective_date,\n"
				+ "                                                                       to_date('1900-01-01 00:00:00',\n"
				+ "                                                                               'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                   'yyyy-MM') <=\n"
				+ "                                                           to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                                   'yyyy-MM')\n"
				+ "                                                       and to_char(nvl(pbtse.elective_date,\n"
				+ "                                                                       to_date('1900-01-01 00:00:00',\n"
				+ "                                                                               'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                   'yyyy') =\n"
				+ "                                                           to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                                   'yyyy')\n"
				+ "                                                       and pbtse.fk_stu_id =\n"
				+ "                                                           pbs.id\n"
				+ "                                                       and pbs.fk_enterprise_id =\n"
				+ "                                                           pe.id\n"
				+ "                                                       and pe.fk_parent_id is null\n"
				+ "                                                     group by pe.id) b\n"
				+ "                                            union all\n"
				+ "                                            select b.num  as num,\n"
				+ "                                                   b.enId as enId\n"
				+ "                                              from (select pe.fk_parent_id as enId,\n"
				+ "                                                           sum(case\n"
				+ "                                                                 when nvl(pbtse.exam_times,\n"
				+ "                                                                          0) > 0 then\n"
				+ "                                                                  1\n"
				+ "                                                                 else\n"
				+ "                                                                  0\n"
				+ "                                                               end) as num\n"
				+ "                                                      from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                           pe_bzz_student          pbs,\n"
				+ "                                                           pe_enterprise           pe\n"
				+ "                                                     where to_char(nvl(pbtse.elective_date,\n"
				+ "                                                                       to_date('1900-01-01 00:00:00',\n"
				+ "                                                                               'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                   'yyyy-MM') <=\n"
				+ "                                                           to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                                   'yyyy-MM')\n"
				+ "                                                       and to_char(nvl(pbtse.elective_date,\n"
				+ "                                                                       to_date('1900-01-01 00:00:00',\n"
				+ "                                                                               'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                   'yyyy') =\n"
				+ "                                                           to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                                   'yyyy')\n"
				+ "                                                       and pbtse.fk_stu_id =\n"
				+ "                                                           pbs.id\n"
				+ "                                                       and pbs.fk_enterprise_id =\n"
				+ "                                                           pe.id\n"
				+ "                                                       and pe.fk_parent_id is not null\n"
				+ "                                                     group by pe.fk_parent_id) b) bb\n"
				+ "                                     where bb.enId = ww.fk_enterprise_id\n"
				+ "                                     group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n" + "   and ww.month = to_char(trunc(" + dateStr + " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条开始测验累计完毕！");
		} catch (Exception e) {
			System.out.println("更新开始测验累计失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新完成测验累计
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatComTesCount(String dateStr) {
		System.out.println("正在更新完成测验累计。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql =

		"update statistic_enterprise ww\n" + "   set ww.test_complete_times_count = (select sum(num)\n"
				+ "                                         from (select b.num  as num,\n"
				+ "                                                      b.enId as enId\n"
				+ "                                                 from (select count(pbtse.id) as num,\n"
				+ "                                                              pe.id as enId\n"
				+ "                                                         from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                              pr_bzz_tch_opencourse   pbto,\n"
				+ "                                                              pe_bzz_tch_course       pbtc,\n"
				+ "                                                              pe_bzz_student          pbs,\n"
				+ "                                                              pe_enterprise           pe\n"
				+ "                                                        where to_char(nvl(pbtse.completed_time,\n"
				+ "                                                                          to_date('1900-01-01 00:00:00',\n"
				+ "                                                                                  'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                      'yyyy') =\n"
				+ "                                                              to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                                      'yyyy')\n"
				+ "                                                          and to_char(nvl(pbtse.completed_time,\n"
				+ "                                                                          to_date('1900-01-01 00:00:00',\n"
				+ "                                                                                  'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                      'yyyy-MM') <=\n"
				+ "                                                              to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                                      'yyyy-MM')\n"
				+ "                                                          and pe.fk_parent_id is null\n"
				+ "                                                          and pbtse.fk_stu_id =\n"
				+ "                                                              pbs.id\n"
				+ "                                                          and pbs.fk_enterprise_id =\n"
				+ "                                                              pe.id\n"
				+ "                                                          and pbtse.fk_tch_opencourse_id =\n"
				+ "                                                              pbto.id\n"
				+ "                                                          and pbto.fk_course_id =\n"
				+ "                                                              pbtc.id\n"
				+ "                                                          and to_number(nvl(pbtse.score_exam,\n"
				+ "                                                                            '0')) >=\n"
				+ "                                                              to_number(nvl(pbtc.passrole,\n"
				+ "                                                                            0))\n"
				+ "                                                          and nvl(pbtse.exam_times,\n"
				+ "                                                                  0) > 0\n"
				+ "                                                        group by pe.id) b\n"
				+ "                                               union all\n"
				+ "                                               select b.num  as num,\n"
				+ "                                                      b.enId as enId\n"
				+ "                                                 from (select count(pbtse.id) as num,\n"
				+ "                                                              pe.fk_parent_id as enId\n"
				+ "                                                         from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                              pr_bzz_tch_opencourse   pbto,\n"
				+ "                                                              pe_bzz_tch_course       pbtc,\n"
				+ "                                                              pe_bzz_student          pbs,\n"
				+ "                                                              pe_enterprise           pe\n"
				+ "                                                        where to_char(nvl(pbtse.completed_time,\n"
				+ "                                                                          to_date('1900-01-01 00:00:00',\n"
				+ "                                                                                  'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                      'yyyy') =\n"
				+ "                                                              to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                                      'yyyy')\n"
				+ "                                                          and to_char(nvl(pbtse.completed_time,\n"
				+ "                                                                          to_date('1900-01-01 00:00:00',\n"
				+ "                                                                                  'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                      'yyyy-MM') <=\n"
				+ "                                                              to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                                      'yyyy-MM')\n"
				+ "                                                          and pe.fk_parent_id is not null\n"
				+ "                                                          and pbtse.fk_stu_id =\n"
				+ "                                                              pbs.id\n"
				+ "                                                          and pbs.fk_enterprise_id =\n"
				+ "                                                              pe.id\n"
				+ "                                                          and pbtse.fk_tch_opencourse_id =\n"
				+ "                                                              pbto.id\n"
				+ "                                                          and pbto.fk_course_id =\n"
				+ "                                                              pbtc.id\n"
				+ "                                                          and to_number(nvl(pbtse.score_exam,\n"
				+ "                                                                            '0')) >=\n"
				+ "                                                              to_number(nvl(pbtc.passrole,\n"
				+ "                                                                            0))\n"
				+ "                                                          and nvl(pbtse.exam_times,\n"
				+ "                                                                  0) > 0\n"
				+ "                                                        group by pe.fk_parent_id) b) bb\n"
				+ "                                        where bb.enId = ww.fk_enterprise_id\n"
				+ "                                        group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n" + "   and ww.month = to_char(trunc(" + dateStr + " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条完成测验累计完毕！");
		} catch (Exception e) {
			System.out.println("更新完成测验累计失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新开始测验新增
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatBeginTesNum(String dateStr) {
		System.out.println("正在更新开始测验新增。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql =

		"update statistic_enterprise ww\n" + "   set ww.test_begin_times = (select sum(num)\n"
				+ "                                from (select b.num as num, b.enId as enId\n"
				+ "                                        from (select pe.id as enId,\n"
				+ "                                                     sum(case\n"
				+ "                                                           when nvl(pbtse.exam_times,\n"
				+ "                                                                    0) > 0 then\n"
				+ "                                                            1\n"
				+ "                                                           else\n"
				+ "                                                            0\n"
				+ "                                                         end) as num\n"
				+ "                                                from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                     pe_bzz_student          pbs,\n"
				+ "                                                     pe_enterprise           pe\n"
				+ "                                               where to_char(nvl(pbtse.elective_date,\n"
				+ "                                                                 to_date('1900-01-01 00:00:00',\n"
				+ "                                                                         'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                             'yyyy-MM') =\n"
				+ "                                                     to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                             'yyyy-MM')\n"
				+ "                                                 and pbtse.fk_stu_id = pbs.id\n"
				+ "                                                 and pbs.fk_enterprise_id =\n"
				+ "                                                     pe.id\n"
				+ "                                                 and pe.fk_parent_id is null\n"
				+ "                                               group by pe.id) b\n"
				+ "                                      union all\n"
				+ "                                      select b.num as num, b.enId as enId\n"
				+ "                                        from (select pe.fk_parent_id as enId,\n"
				+ "                                                     sum(case\n"
				+ "                                                           when nvl(pbtse.exam_times,\n"
				+ "                                                                    0) > 0 then\n"
				+ "                                                            1\n"
				+ "                                                           else\n"
				+ "                                                            0\n"
				+ "                                                         end) as num\n"
				+ "                                                from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                     pe_bzz_student          pbs,\n"
				+ "                                                     pe_enterprise           pe\n"
				+ "                                               where to_char(nvl(pbtse.elective_date,\n"
				+ "                                                                 to_date('1900-01-01 00:00:00',\n"
				+ "                                                                         'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                             'yyyy-MM') =\n"
				+ "                                                     to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                             'yyyy-MM')\n"
				+ "                                                 and pbtse.fk_stu_id = pbs.id\n"
				+ "                                                 and pbs.fk_enterprise_id =\n"
				+ "                                                     pe.id\n"
				+ "                                                 and pe.fk_parent_id is not null\n"
				+ "                                               group by pe.fk_parent_id) b) bb\n"
				+ "                               where bb.enId = ww.fk_enterprise_id\n"
				+ "                               group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc(" + dateStr + " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条开始测验新增完毕！");
		} catch (Exception e) {
			System.out.println("更新开始测验新增失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新完成测验新增
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatComTesNum(String dateStr) {
		System.out.println("正在更新完成测验新增。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql =

		"update statistic_enterprise ww\n" + "   set ww.test_complete_times = (select sum(num)\n"
				+ "                                   from (select b.num as num, b.enId as enId\n"
				+ "                                           from (select count(pbtse.id) as num,\n"
				+ "                                                        pe.id as enId\n"
				+ "                                                   from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                        pr_bzz_tch_opencourse   pbto,\n"
				+ "                                                        pe_bzz_tch_course       pbtc,\n"
				+ "                                                        pe_bzz_student          pbs,\n"
				+ "                                                        pe_enterprise           pe\n"
				+ "                                                  where to_char(nvl(pbtse.completed_time,\n"
				+ "                                                                    to_date('1900-01-01 00:00:00',\n"
				+ "                                                                            'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                'yyyy-MM') =\n"
				+ "                                                        to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                                'yyyy-MM')\n"
				+ "                                                    and pe.fk_parent_id is null\n"
				+ "                                                    and pbtse.fk_stu_id =\n"
				+ "                                                        pbs.id\n"
				+ "                                                    and pbs.fk_enterprise_id =\n"
				+ "                                                        pe.id\n"
				+ "                                                    and pbtse.fk_tch_opencourse_id =\n"
				+ "                                                        pbto.id\n"
				+ "                                                    and pbto.fk_course_id =\n"
				+ "                                                        pbtc.id\n"
				+ "                                                    and to_number(nvl(pbtse.score_exam,\n"
				+ "                                                                      '0')) >=\n"
				+ "                                                        to_number(nvl(pbtc.passrole,\n"
				+ "                                                                      0))\n"
				+ "                                                    and nvl(pbtse.exam_times, 0) > 0\n"
				+ "                                                  group by pe.id) b\n"
				+ "                                         union all\n"
				+ "                                         select b.num as num, b.enId as enId\n"
				+ "                                           from (select count(pbtse.id) as num,\n"
				+ "                                                        pe.fk_parent_id as enId\n"
				+ "                                                   from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                        pr_bzz_tch_opencourse   pbto,\n"
				+ "                                                        pe_bzz_tch_course       pbtc,\n"
				+ "                                                        pe_bzz_student          pbs,\n"
				+ "                                                        pe_enterprise           pe\n"
				+ "                                                  where to_char(nvl(pbtse.completed_time,\n"
				+ "                                                                    to_date('1900-01-01 00:00:00',\n"
				+ "                                                                            'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                'yyyy-MM') =\n"
				+ "                                                        to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                                'yyyy-MM')\n"
				+ "                                                    and pe.fk_parent_id is not null\n"
				+ "                                                    and pbtse.fk_stu_id =\n"
				+ "                                                        pbs.id\n"
				+ "                                                    and pbs.fk_enterprise_id =\n"
				+ "                                                        pe.id\n"
				+ "                                                    and pbtse.fk_tch_opencourse_id =\n"
				+ "                                                        pbto.id\n"
				+ "                                                    and pbto.fk_course_id =\n"
				+ "                                                        pbtc.id\n"
				+ "                                                    and to_number(nvl(pbtse.score_exam,\n"
				+ "                                                                      '0')) >=\n"
				+ "                                                        to_number(nvl(pbtc.passrole,\n"
				+ "                                                                      0))\n"
				+ "                                                    and nvl(pbtse.exam_times, 0) > 0\n"
				+ "                                                  group by pe.fk_parent_id) b) bb\n"
				+ "                                  where bb.enId = ww.fk_enterprise_id\n"
				+ "                                  group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc(" + dateStr + "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条完成测验新增完毕！");
		} catch (Exception e) {
			System.out.println("更新完成测验新增失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新报名人数
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatEntryNum(String dateStr) {
		System.out.println("正在更新报名人数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.entry_user_num = (select sum(num)\n"
				+ "                              from (select count(b.num) as num, b.enId as enId\n"
				+ "                                      from (select count(pbtse.id) as num,\n"
				+ "                                                   pe.id as enId,\n"
				+ "                                                   pbtse.fk_stu_id\n"
				+ "                                              from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                   pe_bzz_student          pbs,\n"
				+ "                                                   pe_enterprise           pe\n"
				+ "                                             where to_char(nvl(pbtse.elective_date,\n"
				+ "                                                               to_date('1900-01-01 00:00:00',\n"
				+ "                                                                       'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                           'yyyy-MM') =\n"
				+ "                                                   to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                               and pe.fk_parent_id is null\n"
				+ "                                               and pbtse.fk_stu_id = pbs.id\n"
				+ "                                               and pbtse.flag_elective_pay_status =\n"
				+ "                                                   '40288a7b3981661e01398186b0f50006'\n"
				+ "                                               and pbs.fk_enterprise_id = pe.id\n"
				+ "                                             group by pe.id, pbtse.fk_stu_id) b\n"
				+ "                                     group by enId\n"
				+ "                                    union all\n"
				+ "                                    select count(b.num) as num, b.enId as enId\n"
				+ "                                      from (select count(pbtse.id) as num,\n"
				+ "                                                   pe.fk_parent_id as enId,\n"
				+ "                                                   pbtse.fk_stu_id\n"
				+ "                                              from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                   pe_bzz_student          pbs,\n"
				+ "                                                   pe_enterprise           pe\n"
				+ "                                             where to_char(nvl(pbtse.elective_date,\n"
				+ "                                                               to_date('1900-01-01 00:00:00',\n"
				+ "                                                                       'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                           'yyyy-MM') =\n"
				+ "                                                   to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                               and pe.fk_parent_id is not null\n"
				+ "                                               and pbtse.flag_elective_pay_status =\n"
				+ "                                                   '40288a7b3981661e01398186b0f50006'\n"
				+ "                                               and pbtse.fk_stu_id = pbs.id\n"
				+ "                                               and pbs.fk_enterprise_id = pe.id\n"
				+ "                                             group by pe.fk_parent_id,\n"
				+ "                                                      pbtse.fk_stu_id) b\n"
				+ "                                     group by enId) bb\n"
				+ "                             where bb.enId = ww.fk_enterprise_id\n"
				+ "                             group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc("
				+ dateStr
				+ "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条报名人数完毕！");
		} catch (Exception e) {
			System.out.println("更新报名人数失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新报名人次
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatEntryTimes(String dateStr) {
		System.out.println("正在更新报名人次。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.entry_user_times = (select sum(num)\n"
				+ "                                from (select b.num as num, b.enId as enId\n"
				+ "                                        from (select count(pbtse.id) as num,\n"
				+ "                                                     pe.id as enId\n"
				+ "                                                from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                     pe_bzz_student          pbs,\n"
				+ "                                                     pe_enterprise           pe\n"
				+ "                                               where to_char(nvl(pbtse.elective_date,\n"
				+ "                                                                 to_date('1900-01-01 00:00:00',\n"
				+ "                                                                         'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                             'yyyy-MM') =\n"
				+ "                                                     to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                                 and pe.fk_parent_id is null\n"
				+ "                                                 and pbtse.fk_stu_id = pbs.id\n"
				+ "                                                 and pbtse.flag_elective_pay_status =\n"
				+ "                                                     '40288a7b3981661e01398186b0f50006'\n"
				+ "                                                 and pbs.fk_enterprise_id =\n"
				+ "                                                     pe.id\n"
				+ "                                               group by pe.id) b\n"
				+ "                                      union all\n"
				+ "                                      select b.num as num, b.enId as enId\n"
				+ "                                        from (select count(pbtse.id) as num,\n"
				+ "                                                     pe.fk_parent_id as enId\n"
				+ "                                                from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                     pe_bzz_student          pbs,\n"
				+ "                                                     pe_enterprise           pe\n"
				+ "                                               where to_char(nvl(pbtse.elective_date,\n"
				+ "                                                                 to_date('1900-01-01 00:00:00',\n"
				+ "                                                                         'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                             'yyyy-MM') =\n"
				+ "                                                     to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                                 and pe.fk_parent_id is not null\n"
				+ "                                                 and pbtse.flag_elective_pay_status =\n"
				+ "                                                     '40288a7b3981661e01398186b0f50006'\n"
				+ "                                                 and pbtse.fk_stu_id = pbs.id\n"
				+ "                                                 and pbs.fk_enterprise_id =\n"
				+ "                                                     pe.id\n"
				+ "                                               group by pe.fk_parent_id) b) bb\n"
				+ "                               where bb.enId = ww.fk_enterprise_id\n"
				+ "                               group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc(" + dateStr + "-1), 'MM')";
		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条报名人次完毕！");
		} catch (Exception e) {
			System.out.println("更新报名人次失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新报名学时
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatEntryHours(String dateStr) {
		System.out.println("正在更新报名学时数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.entry_courehours = (select sum(time)\n"
				+ "                                from (select b.time as time, b.enId as enId\n"
				+ "                                        from (select sum(to_number(pbtc.time)) as time,\n"
				+ "                                                     pe.id as enId\n"
				+ "                                                from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                     pe_bzz_student          pbs,\n"
				+ "                                                     pr_bzz_tch_opencourse   pbto,\n"
				+ "                                                     pe_bzz_tch_course       pbtc,\n"
				+ "                                                     pe_enterprise           pe\n"
				+ "                                               where to_char(nvl(pbtse.elective_date,\n"
				+ "                                                               to_date('1900-01-01 00:00:00',\n"
				+ "                                                                       'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                           'yyyy-MM') =\n"
				+ "                                                     to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                                 and pe.fk_parent_id is null\n"
				+ "                                                 and pbtse.fk_tch_opencourse_id =\n"
				+ "                                                     pbto.id\n"
				+ "                                                 and pbto.fk_course_id =\n"
				+ "                                                     pbtc.id\n"
				+ "                                                 and pbtse.fk_stu_id = pbs.id\n"
				+ "                                                 and pbtse.flag_elective_pay_status =\n"
				+ "                                                     '40288a7b3981661e01398186b0f50006'\n"
				+ "                                                 and pbs.fk_enterprise_id =\n"
				+ "                                                     pe.id\n"
				+ "                                               group by pe.id) b\n"
				+ "                                      union all\n"
				+ "                                      select b.time as time, b.enId as enId\n"
				+ "                                        from (select sum(to_number(pbtc.time)) as time,\n"
				+ "                                                     pe.fk_parent_id as enId\n"
				+ "                                                from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                     pr_bzz_tch_opencourse   pbto,\n"
				+ "                                                     pe_bzz_tch_course       pbtc,\n"
				+ "                                                     pe_bzz_student          pbs,\n"
				+ "                                                     pe_enterprise           pe\n"
				+ "                                               where to_char(nvl(pbtse.elective_date,\n"
				+ "                                                               to_date('1900-01-01 00:00:00',\n"
				+ "                                                                       'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                           'yyyy-MM') =\n"
				+ "                                                     to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                                 and pe.fk_parent_id is not null\n"
				+ "                                                 and pbtse.flag_elective_pay_status =\n"
				+ "                                                     '40288a7b3981661e01398186b0f50006'\n"
				+ "                                                 and pbtse.fk_tch_opencourse_id =\n"
				+ "                                                     pbto.id\n"
				+ "                                                 and pbto.fk_course_id =\n"
				+ "                                                     pbtc.id\n"
				+ "                                                 and pbtse.fk_stu_id = pbs.id\n"
				+ "                                                 and pbs.fk_enterprise_id =\n"
				+ "                                                     pe.id\n"
				+ "                                               group by pe.fk_parent_id) b) bb\n"
				+ "                               where bb.enId = ww.fk_enterprise_id\n"
				+ "                               group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc(" + dateStr + "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条报名学时完毕！");
		} catch (Exception e) {
			System.out.println("更新报名学时失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新付费金额
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatFeeAmount(String dateStr) {
		System.out.println("正在更新付费金额。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql =

		"update statistic_enterprise ww\n" + "   set ww.fee_amount =\n" + "       (select sum(num)\n"
				+ "          from (select b.num as num, b.enId as enId\n"
				+ "                  from (select sum(to_number(pboi.amount)) as num,\n" + "                               pe.id as enId\n"
				+ "                          from (select o.*\n" + "                                  FROM pe_bzz_order_info o\n"
				+ "                                 where o.flag_payment_state in\n"
				+ "                                       ('40288a7b394207de01394221a6ff000e',\n"
				+ "                                        '40288acf3965f52b0139662096020009')) pboi,\n"
				+ "                               pe_bzz_student pbs,\n" + "                               pe_enterprise pe\n"
				+ "                         where to_char(nvl(pboi.payment_date,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy-MM') =\n" + "                               to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                           and pboi.flag_payment_method <>\n"
				+ "                                               '40288a7b394207de0139421358110006'\n"
				+ "                           and pe.fk_parent_id is null\n"
				+ "                           and pboi.create_user = pbs.fk_sso_user_id\n"
				+ "                           and pbs.fk_enterprise_id = pe.id\n"
				+ "                         group by pe.id) b\n"
				+ "                union all\n"
				+ "                select b.num as num, b.enId as enId\n"
				+ "                  from (select sum(to_number(pboi.amount)) as num,\n"
				+ "                               pe.fk_parent_id as enId\n"
				+ "                          from (select o.*\n"
				+ "                                  FROM pe_bzz_order_info o\n"
				+ "                                 where o.flag_payment_state in\n"
				+ "                                       ('40288a7b394207de01394221a6ff000e',\n"
				+ "                                        '40288acf3965f52b0139662096020009')) pboi,\n"
				+ "                               pe_bzz_student pbs,\n"
				+ "                               pe_enterprise pe\n"
				+ "                         where to_char(nvl(pboi.payment_date,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy-MM') =\n"
				+ "                               to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                           and pboi.flag_payment_method <>\n"
				+ "                                               '40288a7b394207de0139421358110006'\n"
				+ "                           and pe.fk_parent_id is not null\n"
				+ "                           and pboi.create_user = pbs.fk_sso_user_id\n"
				+ "                           and pbs.fk_enterprise_id = pe.id\n"
				+ "                         group by pe.fk_parent_id) b\n"
				+ "                union all\n"
				+ "                select b.num as num, b.enId as enId\n"
				+ "                  from (select sum(to_number(pboi.amount)) as num,\n"
				+ "                               pe.id as enId\n"
				+ "                          from (select o.*\n"
				+ "                                  FROM pe_bzz_order_info o\n"
				+ "                                 where o.flag_payment_state in\n"
				+ "                                       ('40288a7b394207de01394221a6ff000e',\n"
				+ "                                        '40288acf3965f52b0139662096020009')) pboi,\n"
				+ "                               pe_enterprise_manager pem,\n"
				+ "                               pe_enterprise pe\n"
				+ "                         where to_char(nvl(pboi.payment_date,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy-MM') =\n"
				+ "                               to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                           and pboi.flag_payment_method <>\n"
				+ "                                               '40288a7b394207de0139421358110006'\n"
				+ "                           and pboi.create_user = pem.fk_sso_user_id\n"
				+ "                           and pem.fk_enterprise_id = pe.id\n"
				+ "                           and pe.fk_parent_id is null\n"
				+ "                         group by pe.id) b\n"
				+ "                union all\n"
				+ "                select b.num as num, b.enId as enId\n"
				+ "                  from (select sum(to_number(pboi.amount)) as num,\n"
				+ "                               pe.fk_parent_id as enId\n"
				+ "                          from (select o.*\n"
				+ "                                  FROM pe_bzz_order_info o\n"
				+ "                                 where o.flag_payment_state in\n"
				+ "                                       ('40288a7b394207de01394221a6ff000e',\n"
				+ "                                        '40288acf3965f52b0139662096020009')) pboi,\n"
				+ "                               pe_enterprise_manager pem,\n"
				+ "                               pe_enterprise pe\n"
				+ "                         where to_char(nvl(pboi.payment_date,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy-MM') =\n"
				+ "                               to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                           and pboi.flag_payment_method <>\n"
				+ "                                               '40288a7b394207de0139421358110006'\n"
				+ "                           and pboi.create_user = pem.fk_sso_user_id\n"
				+ "                           and pem.fk_enterprise_id = pe.id\n"
				+ "                           and pe.fk_parent_id is not null\n"
				+ "                         group by pe.fk_parent_id) b) bb\n"
				+ "         where bb.enId = ww.fk_enterprise_id\n"
				+ "         group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc(" + dateStr + " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条付费金额完毕！");
		} catch (Exception e) {
			System.out.println("更新付费金额失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新退费人数
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatRefundNum(String dateStr) {
		System.out.println("正在更新退费人数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.refund_num = (select count(num)\n"
				+ "                          from (select count(b.num) as num, b.enId as enId, id\n"
				+ "                                  from (select count(pbtse.fk_stu_id) as num,\n"
				+ "                                               pe.id as enId,\n"
				+ "                                               pbs.id as id\n"
				+ "                                          from elective_back_history pbtse,\n"
				+ "                                               pe_bzz_student        pbs,\n"
				+ "                                               pe_bzz_order_info     pboi,\n"
				+ "                                               pe_enterprise         pe\n"
				+ "                                         where to_char(nvl(pboi.refund_date,\n"
				+ "                                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                       'yyyy-MM') =\n"
				+ "                                               to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                           and pe.fk_parent_id is null\n"
				+ "                                           and pbtse.fk_order_id = pboi.id\n"
				+ "                                           and pbtse.fk_stu_id = pbs.id\n"
				+ "                                           and pboi.flag_refund_state =\n"
				+ "                                               '40288a7b394207de01394210f6f40003'\n"
				+ "                                           and pbs.fk_enterprise_id = pe.id\n"
				+ "                                         group by pe.id, pbs.id) b\n"
				+ "                                 group by enId, id\n"
				+ "                                union all\n"
				+ "                                select count(b.num) as num, b.enId as enId, id\n"
				+ "                                  from (select count(pbtse.fk_stu_id) as num,\n"
				+ "                                               pe.fk_parent_id as enId,\n"
				+ "                                               pbs.id as id\n"
				+ "                                          from elective_back_history pbtse,\n"
				+ "                                               pe_bzz_student        pbs,\n"
				+ "                                               pe_bzz_order_info     pboi,\n"
				+ "                                               pe_enterprise         pe\n"
				+ "                                         where to_char(nvl(pboi.refund_date,\n"
				+ "                                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                       'yyyy-MM') =\n"
				+ "                                               to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                           and pe.fk_parent_id is not null\n"
				+ "                                           and pbtse.fk_order_id = pboi.id\n"
				+ "                                           and pbtse.fk_stu_id = pbs.id\n"
				+ "                                           and pboi.flag_refund_state =\n"
				+ "                                               '40288a7b394207de01394210f6f40003'\n"
				+ "                                           and pbs.fk_enterprise_id = pe.id\n"
				+ "                                         group by pe.fk_parent_id, pbs.id) b\n"
				+ "                                 group by enId, id) bb\n"
				+ "                         where bb.enId = ww.fk_enterprise_id\n"
				+ "                         group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc("
				+ dateStr
				+ "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条退费人数完毕！");
		} catch (Exception e) {
			System.out.println("更新退费人数失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新退费人次
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatRefundTimes(String dateStr) {
		System.out.println("正在更新退费人次。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.refund_times = (select sum(num)\n"
				+ "                            from (select b.num as num, b.enId as enId\n"
				+ "                                    from (select count(pbtse.id) as num,\n"
				+ "                                                 pe.id as enId\n"
				+ "                                            from elective_back_history pbtse,\n"
				+ "                                                 pe_bzz_student        pbs,\n"
				+ "                                                 pe_bzz_order_info     pboi,\n"
				+ "                                                 pe_enterprise         pe\n"
				+ "                                           where to_char(nvl(pboi.refund_date,\n"
				+ "                                                             to_date('1900-01-01 00:00:00',\n"
				+ "                                                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                         'yyyy-MM') =\n"
				+ "                                                 to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                             and pe.fk_parent_id is null\n"
				+ "                                             and pbtse.fk_order_id = pboi.id\n"
				+ "                                             and pbtse.fk_stu_id = pbs.id\n"
				+ "                                             and pboi.flag_refund_state =\n"
				+ "                                                 '40288a7b394207de01394210f6f40003'\n"
				+ "                                             and pbs.fk_enterprise_id = pe.id\n"
				+ "                                           group by pe.id) b\n"
				+ "                                  union all\n"
				+ "                                  select b.num as num, b.enId as enId\n"
				+ "                                    from (select count(pbtse.id) as num,\n"
				+ "                                                 pe.fk_parent_id as enId\n"
				+ "                                            from elective_back_history pbtse,\n"
				+ "                                                 pe_bzz_student        pbs,\n"
				+ "                                                 pe_bzz_order_info     pboi,\n"
				+ "                                                 pe_enterprise         pe\n"
				+ "                                           where to_char(nvl(pboi.refund_date,\n"
				+ "                                                             to_date('1900-01-01 00:00:00',\n"
				+ "                                                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                         'yyyy-MM') =\n"
				+ "                                                 to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                             and pe.fk_parent_id is not null\n"
				+ "                                             and pbtse.fk_order_id = pboi.id\n"
				+ "                                             and pbtse.fk_stu_id = pbs.id\n"
				+ "                                             and pboi.flag_refund_state =\n"
				+ "                                                 '40288a7b394207de01394210f6f40003'\n"
				+ "                                             and pbs.fk_enterprise_id = pe.id\n"
				+ "                                           group by pe.fk_parent_id) b) bb\n"
				+ "                           where bb.enId = ww.fk_enterprise_id\n"
				+ "                           group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc("
				+ dateStr
				+ "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条退费人次完毕！");
		} catch (Exception e) {
			System.out.println("更新退费人次失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新退费学时
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatRefundHours(String dateStr) {
		System.out.println("正在更新退费学时。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.refund_hours = (select count(num)\n"
				+ "                            from (select b.num as num, b.enId as enId\n"
				+ "                                    from (select sum(to_number(pbtc.time)) as num,\n"
				+ "                                                 pe.id as enId\n"
				+ "                                            from elective_back_history pbtse,\n"
				+ "                                                 pr_bzz_tch_opencourse pbto,\n"
				+ "                                                 pe_bzz_tch_course     pbtc,\n"
				+ "                                                 pe_bzz_student        pbs,\n"
				+ "                                                 pe_bzz_order_info     pboi,\n"
				+ "                                                 pe_enterprise         pe\n"
				+ "                                           where to_char(nvl(pboi.refund_date,\n"
				+ "                                                             to_date('1900-01-01 00:00:00',\n"
				+ "                                                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                         'yyyy-MM') =\n"
				+ "                                                 to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                             and pbtse.fk_tch_opencourse_id =\n"
				+ "                                                 pbto.id\n"
				+ "                                             and pbto.fk_course_id = pbtc.id\n"
				+ "                                             and pe.fk_parent_id is null\n"
				+ "                                             and pbtse.fk_order_id = pboi.id\n"
				+ "                                             and pbtse.fk_stu_id = pbs.id\n"
				+ "                                             and pboi.flag_refund_state =\n"
				+ "                                                 '40288a7b394207de01394210f6f40003'\n"
				+ "                                             and pbs.fk_enterprise_id = pe.id\n"
				+ "                                           group by pe.id) b\n"
				+ "                                  union all\n"
				+ "                                  select b.num as num, b.enId as enId\n"
				+ "                                    from (select sum(to_number(pbtc.time)) as num,\n"
				+ "                                                 pe.fk_parent_id as enId\n"
				+ "                                            from elective_back_history pbtse,\n"
				+ "                                                 pr_bzz_tch_opencourse pbto,\n"
				+ "                                                 pe_bzz_tch_course     pbtc,\n"
				+ "                                                 pe_bzz_student        pbs,\n"
				+ "                                                 pe_bzz_order_info     pboi,\n"
				+ "                                                 pe_enterprise         pe\n"
				+ "                                           where to_char(nvl(pboi.refund_date,\n"
				+ "                                                             to_date('1900-01-01 00:00:00',\n"
				+ "                                                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                         'yyyy-MM') =\n"
				+ "                                                 to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                             and pbtse.fk_tch_opencourse_id =\n"
				+ "                                                 pbto.id\n"
				+ "                                             and pbto.fk_course_id = pbtc.id\n"
				+ "                                             and pe.fk_parent_id is not null\n"
				+ "                                             and pbtse.fk_order_id = pboi.id\n"
				+ "                                             and pbtse.fk_stu_id = pbs.id\n"
				+ "                                             and pboi.flag_refund_state =\n"
				+ "                                                 '40288a7b394207de01394210f6f40003'\n"
				+ "                                             and pbs.fk_enterprise_id = pe.id\n"
				+ "                                           group by pe.fk_parent_id) b) bb\n"
				+ "                           where bb.enId = ww.fk_enterprise_id\n"
				+ "                           group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc("
				+ dateStr
				+ "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条退费学时完毕！");
		} catch (Exception e) {
			System.out.println("更新退费学时失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新退费金额
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatRefundAmount(String dateStr) {
		System.out.println("正在更新退费金额。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.refund_amount = (select sum(num)\n"
				+ "                             from (select b.num as num, b.enId as enId\n"
				+ "                                     from (select sum(to_number(pboi.amount)) as num,\n"
				+ "                                                  pe.id as enId\n"
				+ "                                             from pe_bzz_order_info pboi,\n"
				+ "                                                  pe_bzz_student    pbs,\n"
				+ "                                                  pe_enterprise     pe\n"
				+ "                                            where to_char(nvl(pboi.refund_date,\n"
				+ "                                                              to_date('1900-01-01 00:00:00',\n"
				+ "                                                                      'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                          'yyyy-MM') =\n"
				+ "                                                  to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                              and pe.fk_parent_id is null\n"
				+ "                                              and pboi.create_user =\n"
				+ "                                                  pbs.fk_sso_user_id\n"
				+ "                                              and pboi.flag_refund_state =\n"
				+ "                                                  '40288a7b394207de01394210f6f40003'\n"
				+ "                                              and pbs.fk_enterprise_id = pe.id\n"
				+ "                                            group by pe.id) b\n"
				+ "                                   union all\n"
				+ "                                   select b.num as num, b.enId as enId\n"
				+ "                                     from (select sum(to_number(pboi.amount)) as num,\n"
				+ "                                                  pe.fk_parent_id as enId\n"
				+ "                                             from pe_bzz_order_info pboi,\n"
				+ "                                                  pe_bzz_student    pbs,\n"
				+ "                                                  pe_enterprise     pe\n"
				+ "                                            where to_char(nvl(pboi.refund_date,\n"
				+ "                                                              to_date('1900-01-01 00:00:00',\n"
				+ "                                                                      'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                          'yyyy-MM') =\n"
				+ "                                                  to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                              and pe.fk_parent_id is not null\n"
				+ "                                              and pboi.create_user =\n"
				+ "                                                  pbs.fk_sso_user_id\n"
				+ "                                              and pboi.flag_refund_state =\n"
				+ "                                                  '40288a7b394207de01394210f6f40003'\n"
				+ "                                              and pbs.fk_enterprise_id = pe.id\n"
				+ "                                            group by pe.fk_parent_id) b\n"
				+ "                                   union all\n"
				+ "                                   select b.num as num, b.enId as enId\n"
				+ "                                     from (select sum(to_number(pboi.amount)) as num,\n"
				+ "                                                  pe.id as enId\n"
				+ "                                             from pe_bzz_order_info     pboi,\n"
				+ "                                                  pe_enterprise_manager pem,\n"
				+ "                                                  pe_enterprise         pe\n"
				+ "                                            where to_char(nvl(pboi.refund_date,\n"
				+ "                                                              to_date('1900-01-01 00:00:00',\n"
				+ "                                                                      'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                          'yyyy-MM') =\n"
				+ "                                                  to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                              and pboi.create_user =\n"
				+ "                                                  pem.fk_sso_user_id\n"
				+ "                                              and pboi.flag_refund_state =\n"
				+ "                                                  '40288a7b394207de01394210f6f40003'\n"
				+ "                                              and pem.fk_enterprise_id = pe.id\n"
				+ "											   and pe.fk_parent_id is null\n"
				+ "                                            group by pe.id) b\n"
				+ "                                   union all\n"
				+ "                                   select b.num as num, b.enId as enId\n"
				+ "                                     from (select sum(to_number(pboi.amount)) as num,\n"
				+ "                                                  pe.fk_parent_id as enId\n"
				+ "                                             from pe_bzz_order_info     pboi,\n"
				+ "                                                  pe_enterprise_manager pem,\n"
				+ "                                                  pe_enterprise         pe\n"
				+ "                                            where to_char(nvl(pboi.refund_date,\n"
				+ "                                                              to_date('1900-01-01 00:00:00',\n"
				+ "                                                                      'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                          'yyyy-MM') =\n"
				+ "                                                  to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                              and pboi.create_user =\n"
				+ "                                                  pem.fk_sso_user_id\n"
				+ "                                              and pboi.flag_refund_state =\n"
				+ "                                                  '40288a7b394207de01394210f6f40003'\n"
				+ "                                              and pem.fk_enterprise_id =\n"
				+ "                                                  pe.id\n"
				+ "											   and pe.fk_parent_id is not null\n"
				+ "                                            group by pe.fk_parent_id) b\n"
				+ "                                   ) bb\n"
				+ "                            where bb.enId = ww.fk_enterprise_id\n"
				+ "                            group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc("
				+ dateStr
				+ "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条退费金额完毕！");
		} catch (Exception e) {
			System.out.println("更新退费金额失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新跨年退费人数
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatOverYearRefundNum(String dateStr) {
		System.out.println("正在更新跨年退费人数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "\n" + "update statistic_enterprise ww\n" + "   set ww.overyear_refund_num = (select sum(num)\n"
				+ "                                   from (select b.num as num,\n"
				+ "                                                b.enId as enId,\n"
				+ "                                                id\n"
				+ "                                           from (select count(pbtse.fk_stu_id) as num,\n"
				+ "                                                        pe.id as enId,\n"
				+ "                                                        pbs.id as id\n"
				+ "                                                   from elective_back_history pbtse,\n"
				+ "                                                        pe_bzz_student        pbs,\n"
				+ "                                                        pe_bzz_order_info     pboi,\n"
				+ "                                                        pe_enterprise         pe\n"
				+ "                                                  where to_char(nvl(pboi.refund_date,\n"
				+ "                                                                    to_date('1900-01-01 00:00:00',\n"
				+ "                                                                            'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                'yyyy-MM') =\n"
				+ "                                                        to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                                'yyyy-MM')\n"
				+ "                                                    and to_char(pboi.payment_date,\n"
				+ "                                                                'yyyy') =\n"
				+ "                                                        to_char((trunc("
				+ dateStr
				+ "-1) -\n"
				+ "                                                                interval '1' year),\n"
				+ "                                                                'yyyy')\n"
				+ "                                                    and pe.fk_parent_id is null\n"
				+ "                                                    and pbtse.fk_order_id =\n"
				+ "                                                        pboi.id\n"
				+ "                                                    and pbtse.fk_stu_id =\n"
				+ "                                                        pbs.id\n"
				+ "                                                    and pboi.flag_refund_state =\n"
				+ "                                                        '40288a7b394207de01394210f6f40003'\n"
				+ "                                                    and pbs.fk_enterprise_id =\n"
				+ "                                                        pe.id\n"
				+ "                                                  group by pe.id, pbs.id) b\n"
				+ "                                         union all\n"
				+ "                                         select b.num as num,\n"
				+ "                                                b.enId as enId,\n"
				+ "                                                id\n"
				+ "                                           from (select count(pbtse.fk_stu_id) as num,\n"
				+ "                                                        pe.fk_parent_id as enId,\n"
				+ "                                                        pbs.id as id\n"
				+ "                                                   from elective_back_history pbtse,\n"
				+ "                                                        pe_bzz_student        pbs,\n"
				+ "                                                        pe_bzz_order_info     pboi,\n"
				+ "                                                        pe_enterprise         pe\n"
				+ "                                                  where to_char(nvl(pboi.refund_date,\n"
				+ "                                                                    to_date('1900-01-01 00:00:00',\n"
				+ "                                                                            'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                'yyyy-MM') =\n"
				+ "                                                        to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                                'yyyy-MM')\n"
				+ "                                                    and to_char(pboi.payment_date,\n"
				+ "                                                                'yyyy') =\n"
				+ "                                                        to_char((trunc("
				+ dateStr
				+ "-1) -\n"
				+ "                                                                interval '1' year),\n"
				+ "                                                                'yyyy')\n"
				+ "                                                    and pe.fk_parent_id is not null\n"
				+ "                                                    and pbtse.fk_order_id =\n"
				+ "                                                        pboi.id\n"
				+ "                                                    and pbtse.fk_stu_id =\n"
				+ "                                                        pbs.id\n"
				+ "                                                    and pboi.flag_refund_state =\n"
				+ "                                                        '40288a7b394207de01394210f6f40003'\n"
				+ "                                                    and pbs.fk_enterprise_id =\n"
				+ "                                                        pe.id\n"
				+ "                                                  group by pe.fk_parent_id,\n"
				+ "                                                           pbs.id) b) bb\n"
				+ "                                  where bb.enId = ww.fk_enterprise_id\n"
				+ "                                  group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc(" + dateStr + "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条跨年退费人数完毕！");
		} catch (Exception e) {
			System.out.println("更新跨年退费人数失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新跨年退费人次
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatOverYearRefundTimes(String dateStr) {
		System.out.println("正在更新跨年退费人次。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.overyear_refund_times = (select sum(num)\n"
				+ "                                     from (select b.num as num, b.enId as enId\n"
				+ "                                             from (select count(pbtse.id) as num,\n"
				+ "                                                          pe.id as enId\n"
				+ "                                                     from elective_back_history pbtse,\n"
				+ "                                                          pe_bzz_student        pbs,\n"
				+ "                                                          pe_bzz_order_info     pboi,\n"
				+ "                                                          pe_enterprise         pe\n"
				+ "                                                    where to_char(nvl(pboi.refund_date,\n"
				+ "                                                                      to_date('1900-01-01 00:00:00',\n"
				+ "                                                                              'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                  'yyyy-MM') =\n"
				+ "                                                          to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                                  'yyyy-MM')\n"
				+ "                                                      and to_char(pboi.payment_date,\n"
				+ "                                                                  'yyyy') =\n"
				+ "                                                          to_char((trunc("
				+ dateStr
				+ "-1) -\n"
				+ "                                                                  interval '1' year),\n"
				+ "                                                                  'yyyy')\n"
				+ "                                                      and pe.fk_parent_id is null\n"
				+ "                                                      and pbtse.fk_order_id =\n"
				+ "                                                          pboi.id\n"
				+ "                                                      and pbtse.fk_stu_id =\n"
				+ "                                                          pbs.id\n"
				+ "                                                      and pboi.flag_refund_state =\n"
				+ "                                                          '40288a7b394207de01394210f6f40003'\n"
				+ "                                                      and pbs.fk_enterprise_id =\n"
				+ "                                                          pe.id\n"
				+ "                                                    group by pe.id) b\n"
				+ "                                           union all\n"
				+ "                                           select b.num as num, b.enId as enId\n"
				+ "                                             from (select count(pbtse.id) as num,\n"
				+ "                                                          pe.fk_parent_id as enId\n"
				+ "                                                     from elective_back_history pbtse,\n"
				+ "                                                          pe_bzz_student        pbs,\n"
				+ "                                                          pe_bzz_order_info     pboi,\n"
				+ "                                                          pe_enterprise         pe\n"
				+ "                                                    where to_char(nvl(pboi.refund_date,\n"
				+ "                                                                      to_date('1900-01-01 00:00:00',\n"
				+ "                                                                              'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                  'yyyy-MM') =\n"
				+ "                                                          to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                                  'yyyy-MM')\n"
				+ "                                                      and to_char(pboi.payment_date,\n"
				+ "                                                                  'yyyy') =\n"
				+ "                                                          to_char((trunc("
				+ dateStr
				+ "-1) -\n"
				+ "                                                                  interval '1' year),\n"
				+ "                                                                  'yyyy')\n"
				+ "                                                      and pe.fk_parent_id is not null\n"
				+ "                                                      and pbtse.fk_order_id =\n"
				+ "                                                          pboi.id\n"
				+ "                                                      and pbtse.fk_stu_id =\n"
				+ "                                                          pbs.id\n"
				+ "                                                      and pboi.flag_refund_state =\n"
				+ "                                                          '40288a7b394207de01394210f6f40003'\n"
				+ "                                                      and pbs.fk_enterprise_id =\n"
				+ "                                                          pe.id\n"
				+ "                                                    group by pe.fk_parent_id) b) bb\n"
				+ "                                    where bb.enId = ww.fk_enterprise_id\n"
				+ "                                    group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n" + "   and ww.month = to_char(trunc(" + dateStr + "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条跨年退费人次完毕！");
		} catch (Exception e) {
			System.out.println("更新跨年退费人次失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新跨年退费学时
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatOverYearRefundHours(String dateStr) {
		System.out.println("正在更新跨年退费学时。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.overyear_refund_hours = (select sum(num)\n"
				+ "                                     from (select b.num as num, b.enId as enId\n"
				+ "                                             from (select sum(to_number(pbtc.time)) as num,\n"
				+ "                                                          pe.id as enId\n"
				+ "                                                     from elective_back_history pbtse,\n"
				+ "                                                          pr_bzz_tch_opencourse pbto,\n"
				+ "                                                          pe_bzz_tch_course     pbtc,\n"
				+ "                                                          pe_bzz_student        pbs,\n"
				+ "                                                          pe_bzz_order_info     pboi,\n"
				+ "                                                          pe_enterprise         pe\n"
				+ "                                                    where to_char(nvl(pboi.refund_date,\n"
				+ "                                                                      to_date('1900-01-01 00:00:00',\n"
				+ "                                                                              'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                  'yyyy-MM') =\n"
				+ "                                                          to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                                  'yyyy-MM')\n"
				+ "                                                      and to_char(pboi.payment_date,\n"
				+ "                                                                  'yyyy') =\n"
				+ "                                                          to_char((trunc("
				+ dateStr
				+ "-1) -\n"
				+ "                                                                  interval '1' year),\n"
				+ "                                                                  'yyyy')\n"
				+ "                                                      and pboi.flag_refund_state =\n"
				+ "                                                          '40288a7b394207de01394210f6f40003'\n"
				+ "                                                      and pe.fk_parent_id is null\n"
				+ "                                                      and pbtse.fk_tch_opencourse_id =\n"
				+ "                                                          pbto.id\n"
				+ "                                                      and pbto.fk_course_id =\n"
				+ "                                                          pbtc.id\n"
				+ "                                                      and pbtse.fk_order_id =\n"
				+ "                                                          pboi.id\n"
				+ "                                                      and pbtse.fk_stu_id =\n"
				+ "                                                          pbs.id\n"
				+ "                                                      and pbs.fk_enterprise_id =\n"
				+ "                                                          pe.id\n"
				+ "                                                    group by pe.id) b\n"
				+ "                                           union all\n"
				+ "                                           select b.num as num, b.enId as enId\n"
				+ "                                             from (select sum(to_number(pbtc.time)) as num,\n"
				+ "                                                          pe.fk_parent_id as enId\n"
				+ "                                                     from elective_back_history pbtse,\n"
				+ "                                                          pr_bzz_tch_opencourse pbto,\n"
				+ "                                                          pe_bzz_tch_course     pbtc,\n"
				+ "                                                          pe_bzz_student        pbs,\n"
				+ "                                                          pe_bzz_order_info     pboi,\n"
				+ "                                                          pe_enterprise         pe\n"
				+ "                                                    where to_char(nvl(pboi.refund_date,\n"
				+ "                                                                      to_date('1900-01-01 00:00:00',\n"
				+ "                                                                              'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                  'yyyy-MM') =\n"
				+ "                                                          to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                                  'yyyy-MM')\n"
				+ "                                                      and to_char(pboi.payment_date,\n"
				+ "                                                                  'yyyy') =\n"
				+ "                                                          to_char((trunc("
				+ dateStr
				+ "-1) -\n"
				+ "                                                                  interval '1' year),\n"
				+ "                                                                  'yyyy')\n"
				+ "                                                      and pboi.flag_refund_state =\n"
				+ "                                                          '40288a7b394207de01394210f6f40003'\n"
				+ "                                                      and pe.fk_parent_id is not null\n"
				+ "                                                      and pbtse.fk_tch_opencourse_id =\n"
				+ "                                                          pbto.id\n"
				+ "                                                      and pbto.fk_course_id =\n"
				+ "                                                          pbtc.id\n"
				+ "                                                      and pbtse.fk_order_id =\n"
				+ "                                                          pboi.id\n"
				+ "                                                      and pbtse.fk_stu_id =\n"
				+ "                                                          pbs.id\n"
				+ "\n"
				+ "                                                      and pbs.fk_enterprise_id =\n"
				+ "                                                          pe.id\n"
				+ "                                                    group by pe.fk_parent_id) b) bb\n"
				+ "                                    where bb.enId = ww.fk_enterprise_id\n"
				+ "                                    group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n" + "   and ww.month = to_char(trunc(" + dateStr + "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条跨年退费学时完毕！");
		} catch (Exception e) {
			System.out.println("更新跨年退费学时失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新跨年退费金额
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatOverYearRefundAmount(String dateStr) {
		System.out.println("正在更新跨年退费金额。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.overyear_refund_amount = (select sum(num)\n"
				+ "                                      from (select b.num  as num,\n"
				+ "                                                   b.enId as enId\n"
				+ "                                              from (select sum(to_number(pboi.amount)) as num,\n"
				+ "                                                           pe.id as enId\n"
				+ "                                                      from pe_bzz_order_info pboi,\n"
				+ "                                                           pe_bzz_student    pbs,\n"
				+ "                                                           pe_enterprise     pe\n"
				+ "                                                     where to_char(nvl(pboi.refund_date,\n"
				+ "                                                                       to_date('1900-01-01 00:00:00',\n"
				+ "                                                                               'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                   'yyyy-MM') =\n"
				+ "                                                           to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                                   'yyyy-MM')\n"
				+ "                                                       and pe.fk_parent_id is null\n"
				+ "                                                       and to_char(pboi.payment_date,\n"
				+ "                                                                   'yyyy') =\n"
				+ "                                                           to_char((trunc("
				+ dateStr
				+ "-1) -\n"
				+ "                                                                   interval '1' year),\n"
				+ "                                                                   'yyyy')\n"
				+ "                                                       and pboi.create_user =\n"
				+ "                                                           pbs.fk_sso_user_id\n"
				+ "                                                       and pboi.flag_refund_state =\n"
				+ "                                                           '40288a7b394207de01394210f6f40003'\n"
				+ "                                                       and pbs.fk_enterprise_id =\n"
				+ "                                                           pe.id\n"
				+ "                                                     group by pe.id) b\n"
				+ "                                            union all\n"
				+ "                                            select b.num  as num,\n"
				+ "                                                   b.enId as enId\n"
				+ "                                              from (select sum(to_number(pboi.amount)) as num,\n"
				+ "                                                           pe.fk_parent_id as enId\n"
				+ "                                                      from pe_bzz_order_info pboi,\n"
				+ "                                                           pe_bzz_student    pbs,\n"
				+ "                                                           pe_enterprise     pe\n"
				+ "                                                     where to_char(nvl(pboi.refund_date,\n"
				+ "                                                                       to_date('1900-01-01 00:00:00',\n"
				+ "                                                                               'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                   'yyyy-MM') =\n"
				+ "                                                           to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                                   'yyyy-MM')\n"
				+ "                                                       and pe.fk_parent_id is not null\n"
				+ "                                                       and to_char(pboi.payment_date,\n"
				+ "                                                                   'yyyy') =\n"
				+ "                                                           to_char((trunc("
				+ dateStr
				+ "-1) -\n"
				+ "                                                                   interval '1' year),\n"
				+ "                                                                   'yyyy')\n"
				+ "                                                       and pboi.create_user =\n"
				+ "                                                           pbs.fk_sso_user_id\n"
				+ "                                                       and pboi.flag_refund_state =\n"
				+ "                                                           '40288a7b394207de01394210f6f40003'\n"
				+ "                                                       and pbs.fk_enterprise_id =\n"
				+ "                                                           pe.id\n"
				+ "                                                     group by pe.fk_parent_id) b\n"
				+ "                                            union all\n"
				+ "                                            select b.num  as num,\n"
				+ "                                                   b.enId as enId\n"
				+ "                                              from (select sum(to_number(pboi.amount)) as num,\n"
				+ "                                                           pe.id as enId\n"
				+ "                                                      from pe_bzz_order_info     pboi,\n"
				+ "                                                           pe_enterprise_manager pem,\n"
				+ "                                                           pe_enterprise         pe\n"
				+ "                                                     where to_char(nvl(pboi.refund_date,\n"
				+ "                                                                       to_date('1900-01-01 00:00:00',\n"
				+ "                                                                               'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                   'yyyy-MM') =\n"
				+ "                                                           to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                                   'yyyy-MM')\n"
				+ "                                                       and to_char(pboi.payment_date,\n"
				+ "                                                                   'yyyy') =\n"
				+ "                                                           to_char((trunc("
				+ dateStr
				+ "-1) -\n"
				+ "                                                                   interval '1' year),\n"
				+ "                                                                   'yyyy')\n"
				+ "                                                       and pboi.create_user =\n"
				+ "                                                           pem.fk_sso_user_id\n"
				+ "                                                       and pboi.flag_refund_state =\n"
				+ "                                                           '40288a7b394207de01394210f6f40003'\n"
				+ "                                                       and pem.fk_enterprise_id =\n"
				+ "                                                           pe.id\n"
				+ "                                                     group by pe.id) b\n"
				+ "                                            union all\n"
				+ "                                            select b.num  as num,\n"
				+ "                                                   b.enId as enId\n"
				+ "                                              from (select sum(to_number(pboi.amount)) as num,\n"
				+ "                                                           pe.fk_parent_id as enId\n"
				+ "                                                      from pe_bzz_order_info     pboi,\n"
				+ "                                                           pe_enterprise_manager pem,\n"
				+ "                                                           pe_enterprise         pe\n"
				+ "                                                     where to_char(nvl(pboi.refund_date,\n"
				+ "                                                                       to_date('1900-01-01 00:00:00',\n"
				+ "                                                                               'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                                   'yyyy-MM') =\n"
				+ "                                                           to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                                   'yyyy-MM')\n"
				+ "                                                       and to_char(pboi.payment_date,\n"
				+ "                                                                   'yyyy') =\n"
				+ "                                                           to_char((trunc("
				+ dateStr
				+ "-1) -\n"
				+ "                                                                   interval '1' year),\n"
				+ "                                                                   'yyyy')\n"
				+ "                                                       and pboi.create_user =\n"
				+ "                                                           pem.fk_sso_user_id\n"
				+ "                                                       and pboi.flag_refund_state =\n"
				+ "                                                           '40288a7b394207de01394210f6f40003'\n"
				+ "                                                       and pem.fk_enterprise_id =\n"
				+ "                                                           pe.fk_parent_id\n"
				+ "                                                     group by pe.fk_parent_id) b\n"
				+ "                                            ) bb\n"
				+ "                                     where bb.enId = ww.fk_enterprise_id\n"
				+ "                                     group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n" + "   and ww.month = to_char(trunc(" + dateStr + "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条跨年退费金额完毕！");
		} catch (Exception e) {
			System.out.println("更新跨年退费金额失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新完成学时数
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatComHours(String dateStr) {
		System.out.println("正在更新完成学时数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.complete_hours = (select sum(time)\n"
				+ "                              from (select b.time as time, b.enId as enId\n"
				+ "                                      from (select sum(to_number(pbtc.time)) as time,\n"
				+ "                                                   pe.id as enId\n"
				+ "                                              from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                   pe_bzz_student          pbs,\n"
				+ "                                                   pr_bzz_tch_opencourse   pbto,\n"
				+ "                                                   pe_bzz_tch_course       pbtc,\n"
				+ "                                                   pe_enterprise           pe\n"
				+ "                                             where to_char(nvl(pbtse.completed_time,\n"
				+ "                                                               to_date('1900-01-01 00:00:00',\n"
				+ "                                                                       'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                           'yyyy-MM') =\n"
				+ "                                                   to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                               and pbtse.flag_elective_pay_status =\n"
				+ "                                                   '40288a7b3981661e01398186b0f50006'\n"
				+ "                                               and pe.fk_parent_id is null\n"
				+ "                                               and pbtse.fk_tch_opencourse_id =\n"
				+ "                                                   pbto.id\n"
				+ "                                               and pbto.fk_course_id = pbtc.id\n"
				+ "                                               and pbtse.fk_stu_id = pbs.id\n"
				+ "                                               and pbs.fk_enterprise_id = pe.id\n"
				+ "                                             group by pe.id) b\n"
				+ "                                    union all\n"
				+ "                                    select b.time as time, b.enId as enId\n"
				+ "                                      from (select sum(to_number(pbtc.time)) as time,\n"
				+ "                                                   pe.fk_parent_id as enId\n"
				+ "                                              from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                                                   pr_bzz_tch_opencourse   pbto,\n"
				+ "                                                   pe_bzz_tch_course       pbtc,\n"
				+ "                                                   pe_bzz_student          pbs,\n"
				+ "                                                   pe_enterprise           pe\n"
				+ "                                             where to_char(nvl(pbtse.completed_time,\n"
				+ "                                                               to_date('1900-01-01 00:00:00',\n"
				+ "                                                                       'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                           'yyyy-MM') =\n"
				+ "                                                   to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy-MM')\n"
				+ "                                               and pbtse.flag_elective_pay_status =\n"
				+ "                                                   '40288a7b3981661e01398186b0f50006'\n"
				+ "                                               and pe.fk_parent_id is not null\n"
				+ "                                               and pbtse.fk_tch_opencourse_id =\n"
				+ "                                                   pbto.id\n"
				+ "                                               and pbto.fk_course_id = pbtc.id\n"
				+ "                                               and pbtse.fk_stu_id = pbs.id\n"
				+ "                                               and pbs.fk_enterprise_id = pe.id\n"
				+ "                                             group by pe.fk_parent_id) b) bb\n"
				+ "                             where bb.enId = ww.fk_enterprise_id\n"
				+ "                             group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc("
				+ dateStr
				+ "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条完成学时数完毕！");
		} catch (Exception e) {
			System.out.println("更新完成学时数失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新完成学时数累计
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatComHoursCount(String dateStr) {
		System.out.println("正在更新完成学时数累计。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.complete_hours_count =\n" + "       (select sum(time)\n"
				+ "          from (select b.time as time, b.enId as enId\n"
				+ "                  from (select sum(to_number(pbtc.time)) as time,\n" + "                               pe.id as enId\n"
				+ "                          from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                               pe_bzz_student          pbs,\n"
				+ "                               pr_bzz_tch_opencourse   pbto,\n"
				+ "                               pe_bzz_tch_course       pbtc,\n"
				+ "                               pe_enterprise           pe\n"
				+ "                         where to_char(nvl(pbtse.completed_time,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy') =\n" + "                               to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                           and to_char(nvl(pbtse.completed_time,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy-MM') <=\n"
				+ "                               to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                           and pbtse.score_exam >=\n"
				+ "                               to_number(nvl(pbtc.passrole, 0))\n"
				+ "                           and pbtse.flag_elective_pay_status =\n"
				+ "                               '40288a7b3981661e01398186b0f50006'\n"
				+ "                           and pe.fk_parent_id is null\n"
				+ "                           and pbtse.fk_tch_opencourse_id = pbto.id\n"
				+ "                           and pbto.fk_course_id = pbtc.id\n"
				+ "                           and pbtse.fk_stu_id = pbs.id\n"
				+ "                           and pbs.fk_enterprise_id = pe.id\n"
				+ "                         group by pe.id) b\n"
				+ "                union all\n"
				+ "                select b.time as time, b.enId as enId\n"
				+ "                  from (select sum(to_number(pbtc.time)) as time,\n"
				+ "                               pe.fk_parent_id as enId\n"
				+ "                          from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                               pr_bzz_tch_opencourse   pbto,\n"
				+ "                               pe_bzz_tch_course       pbtc,\n"
				+ "                               pe_bzz_student          pbs,\n"
				+ "                               pe_enterprise           pe\n"
				+ "                         where to_char(nvl(pbtse.completed_time,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy') =\n"
				+ "                               to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                           AND to_char(nvl(pbtse.completed_time,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy-MM') <=\n"
				+ "                               to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                           and pbtse.score_exam >=\n"
				+ "                               to_number(nvl(pbtc.passrole, 0))\n"
				+ "                           and pbtse.flag_elective_pay_status =\n"
				+ "                               '40288a7b3981661e01398186b0f50006'\n"
				+ "                           and pe.fk_parent_id is not null\n"
				+ "                           and pbtse.fk_tch_opencourse_id = pbto.id\n"
				+ "                           and pbto.fk_course_id = pbtc.id\n"
				+ "                           and pbtse.fk_stu_id = pbs.id\n"
				+ "                           and pbs.fk_enterprise_id = pe.id\n"
				+ "                         group by pe.fk_parent_id) b) bb\n"
				+ "         where bb.enId = ww.fk_enterprise_id\n"
				+ "         group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc(" + dateStr + " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条完成学时数累计完毕！");
		} catch (Exception e) {
			System.out.println("更新完成学时数累计失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新开始学习人次
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatBeginStudyNum(String dateStr) {
		System.out.println("正在更新开始学习人次。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.study_begin_times = (select sum(num)\n"
				+ "                                 from (select b.num as num, b.enId as enId\n"
				+ "                                         from (select count(tcs.id) as num,\n"
				+ "                                                      pe.id as enId\n"
				+ "                                                 from training_course_student tcs,\n"
				+ "                                                      pe_bzz_student          pbs,\n"
				+ "                                                      pe_enterprise           pe\n"
				+ "                                                where to_char(nvl(tcs.get_date,\n"
				+ "                                                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                                                         'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                             'yyyy-MM') =\n"
				+ "                                                      to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                              'yyyy-MM')\n"
				+ "                                                  and tcs.student_id =\n"
				+ "                                                      pbs.fk_sso_user_id\n"
				+ "                                                  and pe.fk_parent_id is null\n"
				+ "                                                  and pbs.fk_enterprise_id =\n"
				+ "                                                      pe.id\n"
				+ "                                                  and tcs.learn_status <>\n"
				+ "                                                      'UNCOMPLETE'\n"
				+ "                                                group by pe.id) b\n"
				+ "                                       union all\n"
				+ "                                       select b.num as num, b.enId as enId\n"
				+ "                                         from (select count(tcs.id) as num,\n"
				+ "                                                      pe.fk_parent_id as enId\n"
				+ "                                                 from training_course_student tcs,\n"
				+ "                                                      pe_bzz_student          pbs,\n"
				+ "                                                      pe_enterprise           pe\n"
				+ "                                                where to_char(nvl(tcs.get_date,\n"
				+ "                                                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                                                         'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                             'yyyy-MM') =\n"
				+ "                                                      to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                              'yyyy-MM')\n"
				+ "                                                  and tcs.student_id =\n"
				+ "                                                      pbs.fk_sso_user_id\n"
				+ "                                                  and pe.fk_parent_id is not null\n"
				+ "                                                  and pbs.fk_enterprise_id =\n"
				+ "                                                      pe.id\n"
				+ "                                                  and tcs.learn_status <>\n"
				+ "                                                      'UNCOMPLETE'\n"
				+ "                                                group by pe.fk_parent_id) b) bb\n"
				+ "                                where bb.enId = ww.fk_enterprise_id\n"
				+ "                                group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc(" + dateStr + "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条开始学习人次完毕！");
		} catch (Exception e) {
			System.out.println("更新开始学习人次失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新完成学习人次
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatComStudyNum(String dateStr) {
		System.out.println("正在更新完成学习人次。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.study_complete_times = (select sum(num)\n"
				+ "                                 from (select b.num as num, b.enId as enId\n"
				+ "                                         from (select count(tcs.id) as num,\n"
				+ "                                                      pe.id as enId\n"
				+ "                                                 from training_course_student tcs,\n"
				+ "                                                      pe_bzz_student          pbs,\n"
				+ "                                                      pe_enterprise           pe\n"
				+ "                                                where to_char(nvl(tcs.complete_date,\n"
				+ "                                                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                              'yyyy-MM') =\n"
				+ "                                                      to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                              'yyyy-MM')\n"
				+ "                                                  and tcs.student_id =\n"
				+ "                                                      pbs.fk_sso_user_id\n"
				+ "                                                  and tcs.learn_status =\n"
				+ "                                                      'COMPLETED'\n"
				+ "                                                  and pbs.fk_enterprise_id =\n"
				+ "                                                      pe.id\n"
				+ "                                                  and pe.fk_parent_id is null\n"
				+ "                                                group by pe.id) b\n"
				+ "                                       union all\n"
				+ "                                       select b.num as num, b.enId as enId\n"
				+ "                                         from (select count(tcs.id) as num,\n"
				+ "                                                      pe.fk_parent_id as enId\n"
				+ "                                                 from training_course_student tcs,\n"
				+ "                                                      pe_bzz_student          pbs,\n"
				+ "                                                      pe_enterprise           pe\n"
				+ "                                                where to_char(nvl(tcs.complete_date,\n"
				+ "                                                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                              'yyyy-MM') =\n"
				+ "                                                      to_char(trunc("
				+ dateStr
				+ "-1),\n"
				+ "                                                              'yyyy-MM')\n"
				+ "                                                  and tcs.student_id =\n"
				+ "                                                      pbs.fk_sso_user_id\n"
				+ "                                                  and pe.fk_parent_id is not null\n"
				+ "                                                  and pbs.fk_enterprise_id =\n"
				+ "                                                      pe.id\n"
				+ "                                                  and tcs.learn_status =\n"
				+ "                                                      'COMPLETED'\n"
				+ "                                                group by pe.fk_parent_id) b) bb\n"
				+ "                                where bb.enId = ww.fk_enterprise_id\n"
				+ "                                group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc(" + dateStr + "-1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条完成学习人次完毕！");
		} catch (Exception e) {
			System.out.println("更新完成学习人次失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新开始学习人次累计
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatBeginStudyCount(String dateStr) {
		System.out.println("正在更新开始学习累计。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql =

		"update statistic_enterprise ww\n" + "   set ww.study_begin_times_count = (select sum(num)\n"
				+ "                                 from (select b.num as num, b.enId as enId\n"
				+ "                                         from (select count(tcs.id) as num,\n"
				+ "                                                      pe.id as enId\n"
				+ "                                                 from training_course_student tcs,\n"
				+ "                                                      pe_bzz_student          pbs,\n"
				+ "                                                      pe_enterprise           pe\n"
				+ "                                                where to_char(nvl(tcs.get_date,\n"
				+ "                                                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                              'yyyy') =\n"
				+ "                                                      to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                              'yyyy')\n"
				+ "                                                  and to_char(nvl(tcs.get_date,\n"
				+ "                                                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                              'yyyy-MM') <=\n"
				+ "                                                      to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                              'yyyy-MM')\n"
				+ "                                                  and tcs.student_id =\n"
				+ "                                                      pbs.fk_sso_user_id\n"
				+ "                                                  and pe.fk_parent_id is null\n"
				+ "                                                  and pbs.fk_enterprise_id =\n"
				+ "                                                      pe.id\n"
				+ "                                                  and tcs.learn_status <>\n"
				+ "                                                      'UNCOMPLETE'\n"
				+ "                                                group by pe.id) b\n"
				+ "                                       union all\n"
				+ "                                       select b.num as num, b.enId as enId\n"
				+ "                                         from (select count(tcs.id) as num,\n"
				+ "                                                      pe.fk_parent_id as enId\n"
				+ "                                                 from training_course_student tcs,\n"
				+ "                                                      pe_bzz_student          pbs,\n"
				+ "                                                      pe_enterprise           pe\n"
				+ "                                                where to_char(nvl(tcs.get_date,\n"
				+ "                                                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                              'yyyy') =\n"
				+ "                                                      to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                              'yyyy')\n"
				+ "                                                  and to_char(nvl(tcs.get_date,\n"
				+ "                                                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                                              'yyyy-MM') <=\n"
				+ "                                                      to_char(trunc("
				+ dateStr
				+ " - 1),\n"
				+ "                                                              'yyyy-MM')\n"
				+ "                                                  and tcs.student_id =\n"
				+ "                                                      pbs.fk_sso_user_id\n"
				+ "                                                  and pe.fk_parent_id is not null\n"
				+ "                                                  and pbs.fk_enterprise_id =\n"
				+ "                                                      pe.id\n"
				+ "                                                  and tcs.learn_status <>\n"
				+ "                                                      'UNCOMPLETE'\n"
				+ "                                                group by pe.fk_parent_id) b) bb\n"
				+ "                                where bb.enId = ww.fk_enterprise_id\n"
				+ "                                group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc(" + dateStr + " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条开始学习人次累计完毕！");
		} catch (Exception e) {
			System.out.println("更新开始学习人次累计失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新完成学习人次累计
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatComStudyCount(String dateStr) {
		System.out.println("正在更新完成学习累计。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.study_complete_times_count =\n" + "       (select sum(num)\n"
				+ "          from (select b.num as num, b.enId as enId\n"
				+ "                  from (select count(tcs.id) as num, pe.id as enId\n"
				+ "                          from training_course_student tcs,\n"
				+ "                               pe_bzz_student          pbs,\n"
				+ "                               pe_enterprise           pe\n"
				+ "                         where to_char(nvl(tcs.complete_date,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy') =\n" + "                               to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                           and to_char(nvl(tcs.complete_date,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy-MM') <=\n"
				+ "                               to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                           and tcs.student_id = pbs.fk_sso_user_id\n"
				+ "                           and pe.fk_parent_id is null\n"
				+ "                           and pbs.fk_enterprise_id = pe.id\n"
				+ "                           and tcs.learn_status = 'COMPLETED'\n"
				+ "                         group by pe.id) b\n"
				+ "                union all\n"
				+ "                select b.num as num, b.enId as enId\n"
				+ "                  from (select count(tcs.id) as num, pe.fk_parent_id as enId\n"
				+ "                          from training_course_student tcs,\n"
				+ "                               pe_bzz_student          pbs,\n"
				+ "                               pe_enterprise           pe\n"
				+ "                         where to_char(nvl(tcs.complete_date,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy') =\n"
				+ "                               to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                           AND to_char(nvl(tcs.complete_date,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy-MM') <=\n"
				+ "                               to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                           and tcs.student_id = pbs.fk_sso_user_id\n"
				+ "                           and pe.fk_parent_id is not null\n"
				+ "                           and pbs.fk_enterprise_id = pe.id\n"
				+ "                           and tcs.learn_status = 'COMPLETED'\n"
				+ "                         group by pe.fk_parent_id) b) bb\n"
				+ "         where bb.enId = ww.fk_enterprise_id\n"
				+ "         group by bb.enId)\n"
				+ " where ww.year = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "   and ww.month = to_char(trunc(" + dateStr + " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条完成学习人次累计完毕！");
		} catch (Exception e) {
			System.out.println("更新完成学习人次累计失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新学员表本月完成10个学时时间
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeUpdateStudent(String dateStr) {
		System.out.println("正在更新修改学员表完成10学时时间。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update pe_bzz_student ww\n" + "   set ww.com_ten_hours_date = trunc(" + dateStr + "-1)\n" + " where ww.id in\n"
				+ "       (select b.suId as suId\n" + "          from (select pbtse.fk_stu_id as suId, sum(to_number(pbtc.time)) as time\n"
				+ "                  from pr_bzz_tch_stu_elective pbtse,\n" + "                       pr_bzz_tch_opencourse   pbto,\n"
				+ "                       pe_bzz_tch_course       pbtc\n" + "                 where to_char(nvl(pbtse.completed_time,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <= to_char(trunc(" + dateStr + "-1), 'yyyy-MM')\n"
				+ "                   and pbtse.flag_elective_pay_status =\n"
				+ "                       '40288a7b3981661e01398186b0f50006'\n"
				+ "                   and pbtse.fk_tch_opencourse_id = pbto.id\n" + "                   and pbto.fk_course_id = pbtc.id\n"
				+ "                   and pbtc.flag_coursetype =\n" + "                       '402880f32200c249012200c780c40001'\n"
				+ "                 group by pbtse.fk_stu_id) b\n" + "         where b.time >= 10) and ww.com_ten_hours_date is null";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条更新学员表本月完成10个学时时间完毕！");
		} catch (Exception e) {
			System.out.println("更新更新学员表本月完成10个学时时间失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新完成10个必修学时人数新增
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatComTenHours(String dateStr) {
		System.out.println("正在更新完成10个必修学时数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.complete_ten_hours =\n" + "       (select count(pbs.id) as num\n"
				+ "          from pe_bzz_student pbs, pe_enterprise pe\n" + "         where to_char(nvl(pbs.com_ten_hours_date,\n"
				+ "                           to_date('1900-01-01 00:00:00',\n"
				+ "                                   'yyyy-MM-dd hh24:mi:ss')),\n" + "                       'yyyy-MM') = to_char(trunc("
				+ dateStr + " - 1), 'yyyy-MM')\n" + "           and pbs.fk_enterprise_id = pe.id\n"
				+ "           and (ww.fk_enterprise_id = pe.id or\n" + "                pe.fk_parent_id = ww.fk_enterprise_id))\n"
				+ " where ww.year = to_char(trunc(" + dateStr + " - 1), 'yyyy')\n" + "   and ww.month = to_char(trunc(" + dateStr
				+ " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条完成10个必修学时人数新增完毕！");
		} catch (Exception e) {
			System.out.println("更新完成10个必修学时人数新增失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新完成10个必修学时数累计
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatComTenHoursCount(String dateStr) {
		System.out.println("正在更新完成10个必修学时人数累计。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.complete_ten_hours_count =\n"
				+ "       (select count(pbs.id) as num\n" + "          from pe_bzz_student pbs, pe_enterprise pe\n"
				+ "         where to_char(pbs.com_ten_hours_date, 'yyyy-MM') <=\n" + "               to_char(trunc(" + dateStr
				+ " - 1), 'yyyy-MM')\n" + "           AND to_char(pbs.com_ten_hours_date, 'yyyy') =\n" + "               to_char(trunc("
				+ dateStr + " - 1), 'yyyy')\n" + "           and pbs.com_ten_hours_date is not null\n"
				+ "           and pbs.fk_enterprise_id = pe.id\n" + "           and (ww.fk_enterprise_id = pe.id or\n"
				+ "                ww.fk_enterprise_id = pe.fk_parent_id))\n" + " where ww.year = to_char(trunc(" + dateStr
				+ " - 1), 'yyyy')\n" + "   and ww.month = to_char(trunc(" + dateStr + " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条完成10个必修学时人数累计完毕！");
		} catch (Exception e) {
			System.out.println("更新完成10个必修学时人数累计失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新学员表本月完成15个学时时间
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeUpdateFifStudent(String dateStr) {
		System.out.println("正在更新修改学员表完成15学时人数时间。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update pe_bzz_student ww\n" + "   set ww.com_fifteen_hours_date = trunc(" + dateStr + "-1)\n" + " where ww.id in\n"
				+ "       (select b.suId as suId\n" + "          from (select sum(time) as time, sum(ttime) as ttime, suId as suId\n"
				+ "                  from (select sum(to_number(pbtc.time)) as time,\n" + "                               0 as ttime,\n"
				+ "                               pbtse.fk_stu_id as suId\n"
				+ "                          from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                               pr_bzz_tch_opencourse   pbto,\n"
				+ "                               pe_bzz_tch_course       pbtc\n"
				+ "                         where to_char(nvl(pbtse.completed_time,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy-MM') <=\n" + "                               to_char(trunc(" + dateStr
				+ "-1), 'yyyy-MM')\n" + "                           and pbtse.flag_elective_pay_status =\n"
				+ "                               '40288a7b3981661e01398186b0f50006'\n"
				+ "                           and pbtse.fk_tch_opencourse_id = pbto.id\n"
				+ "                           and pbto.fk_course_id = pbtc.id\n"
				+ "                           and pbtc.flag_coursetype =\n"
				+ "                               '402880f32200c249012200c780c40001'\n"
				+ "                         group by pbtse.fk_stu_id\n" + "                        union all\n"
				+ "                        select 0 as time,\n" + "                               sum(to_number(pbtc.time)) as ttime,\n"
				+ "                               pbtse.fk_stu_id as suId\n"
				+ "                          from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                               pr_bzz_tch_opencourse   pbto,\n"
				+ "                               pe_bzz_tch_course       pbtc\n"
				+ "                         where to_char(nvl(pbtse.completed_time,\n"
				+ "                                           to_date('1900-01-01 00:00:00',\n"
				+ "                                                   'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                       'yyyy-MM') <=\n" + "                               to_char(trunc(" + dateStr
				+ "-1), 'yyyy-MM')\n" + "                           and pbtse.flag_elective_pay_status =\n"
				+ "                               '40288a7b3981661e01398186b0f50006'\n"
				+ "                           and pbtse.fk_tch_opencourse_id = pbto.id\n"
				+ "                           and pbto.fk_course_id = pbtc.id\n" + "                         group by pbtse.fk_stu_id)\n"
				+ "                 group by suId) b\n" + "         where b.time >= 10\n" + "           and b.ttime >= 15)"
				+ "		and ww.com_fifteen_hours_date is null";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条更新学员表本月完成15个学时时间完毕！");
		} catch (Exception e) {
			System.out.println("更新更新学员表本月完成15个学时时间失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新完成15个必修学时人数新增
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatComFifteenHours(String dateStr) {
		System.out.println("正在更新完成15个必修学时人数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.complete_fifteen_hours =\n" + "       (select count(pbs.id) as num\n"
				+ "          from pe_bzz_student pbs, pe_enterprise pe\n" + "         where to_char(nvl(pbs.com_fifteen_hours_date,\n"
				+ "                           to_date('1900-01-01 00:00:00',\n"
				+ "                                   'yyyy-MM-dd hh24:mi:ss')),\n" + "                       'yyyy-MM') = to_char(trunc("
				+ dateStr + " - 1), 'yyyy-MM')\n" + "           and pbs.fk_enterprise_id = pe.id\n"
				+ "           and pbs.com_fifteen_hours_date is not null\n" + "           and (ww.fk_enterprise_id = pe.id or\n"
				+ "                ww.fk_enterprise_id = pe.fk_parent_id))\n" + " where ww.year = to_char(trunc(" + dateStr
				+ " - 1), 'yyyy')\n" + "   and ww.month = to_char(trunc(" + dateStr + " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条完成15个必修学时人数新增完毕！");
		} catch (Exception e) {
			System.out.println("更新完成15个必修学时人数新增失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新完成15个必修学时人数累计
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatComFifteenHoursCount(String dateStr) {
		System.out.println("正在更新完成15个必修学时人数累计。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_enterprise ww\n" + "   set ww.complete_fifteen_hours_count =\n"
				+ "       (select count(pbs.id) as num\n" + "          from pe_bzz_student pbs, pe_enterprise pe\n"
				+ "         where to_char(nvl(pbs.com_fifteen_hours_date,\n"
				+ "                           to_date('1900-01-01 00:00:00',\n"
				+ "                                   'yyyy-MM-dd hh24:mi:ss')),\n" + "                       'yyyy-MM') <= to_char(trunc("
				+ dateStr + " - 1), 'yyyy-MM')\n" + "           AND to_char(nvl(pbs.com_fifteen_hours_date,\n"
				+ "                           to_date('1900-01-01 00:00:00',\n"
				+ "                                   'yyyy-MM-dd hh24:mi:ss')),\n" + "                       'yyyy') = to_char(trunc("
				+ dateStr + " - 1), 'yyyy')\n" + "           and pbs.com_fifteen_hours_date is not null\n"
				+ "           and pbs.fk_enterprise_id = pe.id\n" + "           and (ww.fk_enterprise_id = pe.id or\n"
				+ "                ww.fk_enterprise_id = pe.fk_parent_id))\n" + " where ww.year = to_char(trunc(" + dateStr
				+ " - 1), 'yyyy')\n" + "   and ww.month = to_char(trunc(" + dateStr + " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条完成15个必修学时人数新增累计！");
		} catch (Exception e) {
			System.out.println("更新完成15个必修学时人数新增累计！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 初始化Statistic_Year
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeInitStatisticYear(String dateStr) {
		dbpool db = new dbpool();
		String sql = " select count(*) aa from statistic_year where year=to_char(trunc(" + dateStr + "-1))";
		int ca = 0;
		MyResultSet rs = null;
		try {
			rs = db.executeQuery(sql);
			while (rs.next()) {
				ca = rs.getInt("aa");
			}
		} catch (Exception e) {

		} finally {
			db.close(rs);
		}
		if (ca == 0) {// 当无记录时插入年份，以便根据年份更新
			sql = "insert into statistic_year(id,year)select seq_stati_year.nextval,to_char(trunc(" + dateStr + "-1),'yyyy')from dual";
			try {
				int count = db.executeUpdate(sql);
				System.out.println("成功插入" + count + "个年份！");
			} catch (Exception e) {
				System.out.println("已存在今年记录，开始更新！");
			}
		}
	}

	/**
	 * 更新年注册机构数新增
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearRegEnNum(String dateStr) {
		System.out.println("正在更新年注册机构数新增。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "    set reg_enterprise_num = (select sum(reg_enterprise_num) as enNum\n"
				+ "                                from (select enterprise_type,\n"
				+ "                                             max(reg_enterprise_num) as reg_enterprise_num\n"
				+ "                                        from statistic_enterprise\n"
				+ "                                       where fk_enterprise_id <> '0000'\n"
				+ "                                         and year = to_char(trunc(" + dateStr + " - 1),\n"
				+ "                                                            'yyyy')\n"
				+ "                                       group by enterprise_type) se)\n" + "  where sy.year = to_char(trunc(" + dateStr
				+ " - 1), 'yyyy');";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年注册机构数新增完毕！");
		} catch (Exception e) {
			System.out.println("更新年注册机构数新增失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年注册机构数新增
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearRegEnCount(String dateStr) {
		System.out.println("正在更新年注册机构数累计。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "   set reg_enterprise_count = (select sum(enCount)\n"
				+ "                                 from (select max(reg_enterprise_count) as enCount,\n"
				+ "                                              year\n"
				+ "                                         from statistic_enterprise\n"
				+ "                                        where fk_enterprise_id <> '0000'\n"
				+ "                                        group by year, enterprise_type) bb\n"
				+ "                                where year = to_char(trunc(" + dateStr + "-1), 'yyyy'))\n"
				+ " where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年注册机构数累计完毕！");
		} catch (Exception e) {
			System.out.println("更新年注册机构数累计失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年注册人数新增
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearRegUserNum(String dateStr) {
		System.out.println("正在更新年注册人数新增。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "   set sy.reg_user_num = (select sum(userNum)\n"
				+ "                            from (select year, sum(reg_user_num) userNum\n"
				+ "                                    from statistic_enterprise\n"
				+ "                                   group by year, enterprise_type) bb\n"
				+ "                           where year = to_char(trunc(" + dateStr + "-1), 'yyyy'))\n"
				+ " where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年注册人数新增完毕！");
		} catch (Exception e) {
			System.out.println("更新年注册人数新增失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年注册人数累计
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearRegUserCount(String dateStr) {
		System.out.println("正在更新年注册人数累计。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "   set sy.reg_user_count =\n" + "       (select sum(userCount) as A5\n"
				+ "          from (select year, max(reg_user_count) as userCount\n" + "                  from (select enterprise_type,\n"
				+ "                               sum(reg_user_count) as reg_user_count,\n"
				+ "                               year as YEAR\n" + "                          from statistic_enterprise\n"
				+ "                         group by month, year, enterprise_type)\n"
				+ "                 group by year, enterprise_type) bb\n" + "         where year = to_char(trunc(" + dateStr
				+ " - 1), 'yyyy')\n" + "         group by year)\n" + " where sy.year = to_char(trunc(" + dateStr + " - 1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年注册人数累计完毕！");
		} catch (Exception e) {
			System.out.println("更新年注册人数累计失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年报名人数
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearEntryUserNum(String dateStr) {
		System.out.println("正在更新年报名人数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "   set sy.entry_user_num = (select sum(entry_user_num)\n"
				+ "                              from statistic_enterprise\n" + "                             where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + " where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年报名人数完毕！");
		} catch (Exception e) {
			System.out.println("更新年报名人数失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年报名人次
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearEntryUserTimes(String dateStr) {
		System.out.println("正在更新年报名人次。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "   set sy.entry_user_times = (select sum(entry_user_times)\n"
				+ "                              from statistic_enterprise\n" + "                             where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + " where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年报名人次完毕！");
		} catch (Exception e) {
			System.out.println("更新年报名人次失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年报名学时数
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearEntryHours(String dateStr) {
		System.out.println("正在更新年报名学时数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.entry_courehours= (select sum(entry_courehours)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年报名学时数完毕！");
		} catch (Exception e) {
			System.out.println("更新年报名学时数失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年付费金额
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearFeeAmount(String dateStr) {
		System.out.println("正在更新年付费金额。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "   set sy.fee_amount = (select sum(fee_amount)\n"
				+ "                          from statistic_enterprise\n" + "                         where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + " where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年付费金额完毕！");
		} catch (Exception e) {
			System.out.println("更新年付费金额失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年退费人数
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearRefundNum(String dateStr) {
		System.out.println("正在更新年退费人数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.refund_num= (select sum(refund_num)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年退费人数完毕！");
		} catch (Exception e) {
			System.out.println("更新年退费人数失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年退费人次
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearRefundTimes(String dateStr) {
		System.out.println("正在更新年退费人次。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.refund_times= (select sum(refund_times)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年退费人次完毕！");
		} catch (Exception e) {
			System.out.println("更新年退费人次失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年退费学时数
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearRefundHours(String dateStr) {
		System.out.println("正在更新年退费学时数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.refund_hours= (select sum(refund_hours)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年退费学时数完毕！");
		} catch (Exception e) {
			System.out.println("更新年退费学时数失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年退费金额
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearRefundAmount(String dateStr) {
		System.out.println("正在更新年退费金额。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.refund_amount= (select sum(refund_amount)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年退费金额完毕！");
		} catch (Exception e) {
			System.out.println("更新年退费金额失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年跨年退费人数
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearOverYearRefundNum(String dateStr) {
		System.out.println("正在更新年跨年退费人数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.overyear_refund_num= (select sum(overyear_refund_num)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年跨年退费人数完毕！");
		} catch (Exception e) {
			System.out.println("更新年跨年退费人数失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年跨年退费人次
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearOverYearRefundTimes(String dateStr) {
		System.out.println("正在更新年跨年退费人次。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "   set sy.overyear_refund_times= (select sum(overyear_refund_times)\n"
				+ "                              from statistic_enterprise\n" + "                             where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + " where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年跨年退费人次完毕！");
		} catch (Exception e) {
			System.out.println("更新年跨年退费人次失败！");
			// e.printStackTrace();
		}
	}

	/**
	 * 更新年跨年退费学时
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearOverYearRefundHours(String dateStr) {
		System.out.println("正在更新年跨年退费学时。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.overyear_refund_hours= (select sum(overyear_refund_hours)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年跨年退费学时完毕！");
		} catch (Exception e) {
			System.out.println("更新年跨年退费学时失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年跨年退费金额
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearOverYearRefundAmount(String dateStr) {
		System.out.println("正在更新年跨年退费金额。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.overyear_refund_amount= (select sum(overyear_refund_amount)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年跨年退费金额完毕！");
		} catch (Exception e) {
			System.out.println("更新年跨年退费金额失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年开始学习人次
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearBeginStudyTimes(String dateStr) {
		System.out.println("正在更新年开始学习人次。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.study_begin_times= (select sum(study_begin_times)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年开始学习人次完毕！");
		} catch (Exception e) {
			System.out.println("更新年开始学习人次失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年完成学习人次
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearComStudyTimes(String dateStr) {
		System.out.println("正在更新年完成学习人次。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.study_complete_times= (select sum(study_complete_times)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年完成学习人次完毕！");
		} catch (Exception e) {
			System.out.println("更新年完成学习人次失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年开始测验人次
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearBeginTestTimes(String dateStr) {
		System.out.println("正在更新年开始测验人次。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.test_begin_times= (select sum(test_begin_times)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年开始测验人次完毕！");
		} catch (Exception e) {
			System.out.println("更新年开始测验人次失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年完成测验人次
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearComTestTimes(String dateStr) {
		System.out.println("正在更新年完成测验人次。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.test_complete_times= (select sum(test_complete_times)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年完成测验人次完毕！");
		} catch (Exception e) {
			System.out.println("更新年完成测验人次失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年完成10学时人数
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearComTenHours(String dateStr) {
		System.out.println("正在更新年完成10学时人数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.com_ten_hours_num= (select sum(complete_ten_hours)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年完成10学时人数完毕！");
		} catch (Exception e) {
			System.out.println("更新年完成10学时人数失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年完成15学时人数
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearComFifteenHours(String dateStr) {
		System.out.println("正在更新年完成15学时人数。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "  set sy.com_fifteen_hours_num= (select sum(complete_fifteen_hours)\n"
				+ "                             from statistic_enterprise\n" + "                            where year = to_char(trunc("
				+ dateStr + "-1), 'yyyy'))\n" + "where sy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年完成15学时人数完毕！");
		} catch (Exception e) {
			System.out.println("更新年完成15学时人数失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年报名人数同比
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearEntryNumGrowth(String dateStr) {
		System.out.println("正在更新年报名人数同比。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "   set sy.entry_user_growth = (select decode(ss.enum,0,0,se.num / ss.enum)\n"
				+ "                                 from (select sum(nvl(entry_user_num,0)) num, year\n"
				+ "                                         from statistic_enterprise\n"
				+ "                                        where year = to_char(trunc(" + dateStr + "-1), 'yyyy')\n"
				+ "                                        group by year) se,\n"
				+ "                                      (select nvl(entry_user_num,0) as enum,year\n"
				+ "                                         from statistic_year\n"
				+ "                                        where year =\n"
				+ "                                              to_char(to_number(year) - 1)) ss\n"
				+ "                                where se.year = ss.year)\n" + " where sy.year = to_char(trunc(" + dateStr
				+ "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年报名人数同比完毕！");
		} catch (Exception e) {
			System.out.println("更新年报名人数同比失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年报名人次同比
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearEntryTimesGrowth(String dateStr) {
		System.out.println("正在更新年报名人次同比。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "   set sy.entry_user_times_growth = (select decode(ss.enum,0,0,se.num / ss.enum)\n"
				+ "                                 from (select sum(nvl(entry_user_times,0)) num, year\n"
				+ "                                         from statistic_enterprise\n"
				+ "                                        where year = to_char(trunc(" + dateStr + "-1), 'yyyy')\n"
				+ "                                        group by year) se,\n"
				+ "                                      (select nvl(entry_user_times,0) as enum,year\n"
				+ "                                         from statistic_year\n"
				+ "                                        where year =\n"
				+ "                                              to_char(to_number(year) - 1)) ss\n"
				+ "                                where se.year = ss.year)\n" + " where sy.year = to_char(trunc(" + dateStr
				+ "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年报名人次同比完毕！");
		} catch (Exception e) {
			System.out.println("更新年报名人次同比失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 更新年报名学时数同比
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatYearEntryHoursGrowth(String dateStr) {
		System.out.println("正在更新年报名学时数同比。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update statistic_year sy\n" + "   set sy.entry_courehours_growth = (select decode(ss.enum,0,0,se.num / ss.enum)\n"
				+ "                                 from (select sum(nvl(entry_courehours,0)) num, year\n"
				+ "                                         from statistic_enterprise\n"
				+ "                                        where year = to_char(trunc(" + dateStr + "-1), 'yyyy')\n"
				+ "                                        group by year) se,\n"
				+ "                                      (select nvl(entry_courehours,0) as enum,year\n"
				+ "                                         from statistic_year\n"
				+ "                                        where year =\n"
				+ "                                              to_char(to_number(to_char(trunc(" + dateStr + "-1), 'yyyy')) - 1)) ss\n"
				+ "                                where se.year = ss.year)\n" + " where sy.year = to_char(trunc(" + dateStr
				+ "-1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条年报名学时数同比完毕！");
		} catch (Exception e) {
			System.out.println("更新年报名人次同比失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	/**
	 * 20130109创建 此方法用于课程统计，向统计初始化表STATISTIC_COURSES中插入数据
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatCourseTableInsert(String dateStr) {

		System.out.println("正在向课程统计初始化表格中插入数据。。。。" + new Date());
		dbpool db = new dbpool();
		int count = 0;
		String sql =

		"insert into STATISTIC_COURSES\n" + "  (id,\n" + "   YEAR,\n" + "   MONTH,\n" + "   DAY,\n" + "   FK_COURSE_ID,\n"
				+ "   COURSE_END_DATE,\n" + "   STUDY_TOTAL_TIMES,\n" + "   STUDY_BEGIN_TIMES,\n" + "   STUDY_COMPLETE_TIMES,\n"
				+ "   TEST_BEGIN_TIMES,\n" + "   TEST_COMPLETE_TIMES,\n" + "   CREATE_YEAR,\n" + "   CREATE_MONTH,\n" + "   CREATE_DAY,\n"
				+ "   refund_count,\n" + "   OVERYEAR_REFUND_TIMES)\n" + "  select sys_guid(),\n" + "         f.year,\n"
				+ "         f.month,\n" + "         f.day,\n" + "         a.id,\n" + "         a.end_date,\n"
				+ "         nvl(b.total_times, 0),\n" + "         nvl(c.inc, 0) as inc,\n" + "         nvl(g.com, 0) as com,\n"
				+ "         nvl(d.start_times, 0),\n" + "         nvl(d.pass_times, 0),\n" + "         a.create_year,\n"
				+ "         a.create_month,\n" + "         a.create_day,\n" + "         h.refund_count,\n"
				+ "         i.OVERYEAR_REFUND_TIMES\n" + "    from (\n" + "          /*取出所有的课程*/\n" + "          select pbtc.id as id,\n"
				+ "                  pbtc.code as code,\n" + "                  pbtc.end_date as end_date,\n"
				+ "                  pbtc.flag_course_item_type as course_item,\n"
				+ "                  pbtc.flag_coursecategory as course_category,\n"
				+ "                  pbtc.flag_coursetype as course_type,\n"
				+ "                  to_char(pbtc.create_date, 'yyyy') as create_year,\n"
				+ "                  to_char(pbtc.create_date, 'mm') as create_month,\n"
				+ "                  to_char(pbtc.create_date, 'dd') as create_day\n" + "            from pe_bzz_tch_course pbtc\n"
				+ "          ) a\n" + "    left join (\n" + "               /*当前年课程备选次数*/\n"
				+ "               select pbtc.id as id, count(pbtse.id) as total_times\n"
				+ "                 from pr_bzz_tch_stu_elective pbtse\n"
				+ "                inner join pr_bzz_tch_opencourse pbto on pbtse.fk_tch_opencourse_id =\n"
				+ "                                                         pbto.id\n"
				+ "                inner join pe_bzz_tch_course pbtc on pbtc.id =\n"
				+ "                                                     pbto.fk_course_id\n"
				+ "                where to_char(nvl(pbtse.elective_date,\n"
				+ "                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                              'yyyy') = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "              and pbtse.flag_elective_pay_status = '40288a7b3981661e01398186b0f50006'\n"
				+ "                group by pbtc.id) b on a.id = b.id\n"
				+ "    left join (\n"
				+ "               /*当前年课程未学习和未完成**/\n"
				+ "               select pbtc.id as id,\n"
				+ "                       sum(case\n"
				+ "                             when tcs.learn_status <> 'UNCOMPLETE' then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as inc,\n"
				+ "                       sum(case\n"
				+ "                             when tcs.learn_status = 'UNCOMPLETE' then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as unc\n"
				+ "                 from training_course_student tcs\n"
				+ "                inner join pr_bzz_tch_opencourse pbto on tcs.course_id =\n"
				+ "                                                         pbto.id\n"
				+ "                inner join pe_bzz_tch_course pbtc on pbtc.id =\n"
				+ "                                                     pbto.fk_course_id\n"
				+ "                where to_char(nvl(tcs.get_date,\n"
				+ "                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                              'yyyy') = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "                group by pbtc.id\n"
				+ "               ) c on c.id = a.id\n"
				+ "    left join (\n"
				+ "               /*当前年课程考试次数和通过次数*/\n"
				+ "               select pbtc.id as id,\n"
				+ "                       count(tth.id) as start_times,\n"
				+ "                       sum(case\n"
				+ "                             when to_number(nvl(tth.score, 0)) >=\n"
				+ "                                  to_number(pbtc.passrole) then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as pass_times\n"
				+ "                 from test_testpaper_history tth\n"
				+ "                inner join test_testpaper_info tti on tth.testpaper_id =\n"
				+ "                                                      tti.id\n"
				+ "                inner join pe_bzz_tch_course pbtc on tti.group_id = pbtc.id\n"
				+ "                where to_char(tth.test_date, 'yyyy') =\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "                group by pbtc.id) d on d.id = a.id\n"
				+ "    left join (\n"
				+ "               /*取得当前时间*/\n"
				+ "               select to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy') as year,\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ "-1), 'mm') as month,\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ "-1), 'dd') as day\n"
				+ "                 from dual) f on 1 = 1\n"
				+ "    left join (\n"
				+ "               /*当前年课程被学习完成次数*/\n"
				+ "               select pbtc.id as id,\n"
				+ "                       sum(case\n"
				+ "                             when tcs.learn_status = 'COMPLETED' then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as com\n"
				+ "                 from training_course_student tcs\n"
				+ "                inner join pr_bzz_tch_opencourse pbto on tcs.course_id =\n"
				+ "                                                         pbto.id\n"
				+ "                inner join pe_bzz_tch_course pbtc on pbtc.id =\n"
				+ "                                                     pbto.fk_course_id\n"
				+ "                where to_char(nvl(tcs.complete_date,\n"
				+ "                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                              'yyyy') = to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "                group by pbtc.id\n"
				+ "               ) g on g.id = a.id\n"
				+ "    left join (select pbtc.id as id, count(ebh.id) as refund_count\n"
				+ "                 from elective_back_history ebh\n"
				+ "                inner join pe_bzz_order_info pboi on pboi.id =\n"
				+ "                                                     ebh.fk_order_id\n"
				+ "                inner join pr_bzz_tch_opencourse pbto on pbto.id =\n"
				+ "                                                         ebh.fk_tch_opencourse_id\n"
				+ "                inner join pe_bzz_tch_course pbtc on pbto.fk_course_id =\n"
				+ "                                                     pbtc.id\n"
				+ "                where pboi.flag_refund_state =\n"
				+ "                      '40288a7b394207de01394210f6f40003' --已退费\n"
				+ "                  and to_char(pboi.refund_date, 'yyyy') =\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "                group by pbtc.id\n"
				+ "               ) h on h.id = a.id\n"
				+ "    left join (select pbtc.id as id, count(ebh.id) as OVERYEAR_REFUND_TIMES\n"
				+ "                 from elective_back_history ebh\n"
				+ "                inner join pe_bzz_order_info pboi on pboi.id =\n"
				+ "                                                     ebh.fk_order_id\n"
				+ "                inner join pr_bzz_tch_opencourse pbto on pbto.id =\n"
				+ "                                                         ebh.fk_tch_opencourse_id\n"
				+ "                inner join pe_bzz_tch_course pbtc on pbto.fk_course_id =\n"
				+ "                                                     pbtc.id\n"
				+ "                where pboi.flag_refund_state =\n"
				+ "                      '40288a7b394207de01394210f6f40003' --跨退费\n"
				+ "                  and to_char(pboi.refund_date, 'yyyy') =\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')\n"
				+ "                  and to_char(pboi.payment_date, 'yyyy') =\n"
				+ "                      to_char((trunc("
				+ dateStr
				+ "-1) - interval '1' year), 'yyyy')\n"
				+ "                group by pbtc.id\n"
				+ "               ) i on i.id = a.id\n"
				+ "   where a.id not in (select sc.fk_course_id\n"
				+ "                        from STATISTIC_COURSES sc\n"
				+ "                       where sc.year = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                         and sc.month = to_char(trunc(" + dateStr + " - 1), 'mm'))";

		try {
			count = db.executeUpdate(sql);
			System.out.println("插入" + count + "条新增课程");
		} catch (Exception e) {
			System.out.println("插入数据失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
		System.out.println("课程统计初始化表格中插入数据结束" + new Date());
	}

	/**
	 * 20130109创建 此方法用于课程统计，更新统计初始化表STATISTIC_COURSES中数据
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatCourseTableUpdate(String dateStr) {
		System.out.println("正在更新课程统计初始化表格中数据。。。。" + new Date());
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update STATISTIC_COURSES sc set\n" + "  (day,\n" + "   COURSE_END_DATE,\n" + "   STUDY_TOTAL_TIMES,\n"
				+ "   STUDY_BEGIN_TIMES,\n" + "   STUDY_COMPLETE_TIMES,\n" + "   TEST_BEGIN_TIMES,\n" + "   TEST_BEGIN_COUNTS,\n"
				+ "   TEST_COMPLETE_TIMES,\n" + "   CREATE_YEAR,\n" + "   CREATE_MONTH,\n" + "   CREATE_DAY,\n" + "   refund_count,\n"
				+ "   OVERYEAR_REFUND_TIMES) =\n" + "  (select --sys_guid(),\n" + "   --f.year,\n" + "   -- f.month,\n" + "    f.day,\n"
				+ "    -- a.id,\n" + "    a.end_date,\n" + "    nvl(b.total_times, 0),\n" + "    nvl(c.inc, 0) as inc,\n"
				+ "    nvl(g.com, 0) as com,\n" + "    nvl(d.start_times, 0),\n" + "    nvl(b.start_counts, 0),\n"
				+ "    nvl(dd.pass_times, 0),\n" + "    a.create_year,\n" + "    a.create_month,\n" + "    a.create_day,\n"
				+ "    h.refund_count,\n" + "    i.OVERYEAR_REFUND_TIMES\n" + "     from (\n" + "           /*取出所有的课程*/\n"
				+ "           select pbtc.id as id,\n" + "                   pbtc.code as code,\n"
				+ "                   pbtc.end_date as end_date,\n" + "                   pbtc.flag_course_item_type as course_item,\n"
				+ "                   pbtc.flag_coursecategory as course_category,\n"
				+ "                   pbtc.flag_coursetype as course_type,\n"
				+ "                   to_char(pbtc.create_date, 'yyyy') as create_year,\n"
				+ "                   to_char(pbtc.create_date, 'mm') as create_month,\n"
				+ "                   to_char(pbtc.create_date, 'dd') as create_day\n" + "             from pe_bzz_tch_course pbtc) a\n"
				+ "     left join (\n" + "               /*当前年课程备选次数,开始考试人数*/\n" + "               select pbtc.id as id,\n"
				+ "                       count(pbtse.id) as total_times,\n" + "                       sum(case\n"
				+ "                             when nvl(pbtse.exam_times, 0) > 0 then\n" + "                              1\n"
				+ "                             else\n" + "                              0\n"
				+ "                           end) as start_counts\n" + "                 from pr_bzz_tch_stu_elective pbtse\n"
				+ "                inner join pr_bzz_tch_opencourse pbto on pbtse.fk_tch_opencourse_id =\n"
				+ "                                                         pbto.id\n"
				+ "                inner join pe_bzz_tch_course pbtc on pbtc.id =\n"
				+ "                                                     pbto.fk_course_id\n"
				+ "                where to_char(nvl(pbtse.elective_date,\n"
				+ "                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                              'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                  and to_char(nvl(pbtse.elective_date,\n"
				+ "                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                              'yyyy-MM') <=\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                group by pbtc.id) b on a.id = b.id\n"
				+ "     left join (\n"
				+ "               /*当前年课程未学习和未完成**/\n"
				+ "               select pbtc.id as id,\n"
				+ "                       sum(case\n"
				+ "                             when tcs.learn_status <> 'UNCOMPLETE' then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as inc,\n"
				+ "                       sum(case\n"
				+ "                             when tcs.learn_status = 'UNCOMPLETE' then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as unc\n"
				+ "                 from training_course_student tcs\n"
				+ "                inner join pr_bzz_tch_opencourse pbto on tcs.course_id =\n"
				+ "                                                         pbto.id\n"
				+ "                inner join pe_bzz_tch_course pbtc on pbtc.id =\n"
				+ "                                                     pbto.fk_course_id\n"
				+ "                where to_char(nvl(tcs.get_date,\n"
				+ "                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                              'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                  and to_char(nvl(tcs.get_date,\n"
				+ "                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                              'yyyy-MM') <=\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                group by pbtc.id) c on c.id = a.id\n"
				+ "     left join (\n"
				+ "               /*当前年课程考试次数和通过次数*/\n"
				+ "               select pbtc.id as id,\n"
				+ "                       count(tth.id) as start_times,\n"
				+ "                       sum(case\n"
				+ "                             when to_number(nvl(tth.score, 0)) >=\n"
				+ "                                  to_number(pbtc.passrole) then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as pass_times\n"
				+ "                 from test_testpaper_history tth\n"
				+ "                inner join test_testpaper_info tti on tth.testpaper_id =\n"
				+ "                                                      tti.id\n"
				+ "                inner join pe_bzz_tch_course pbtc on tti.group_id = pbtc.id\n"
				+ "                where to_char(tth.test_date, 'yyyy-MM') <=\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                  and to_char(tth.test_date, 'yyyy') =\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                group by pbtc.id) d on d.id = a.id\n"
				+ "     left join ( /*当前年课程考试通过次数*/\n"
				+ "               select sum(case\n"
				+ "                             when to_number(nvl(pbtse.score_exam, '0')) >=\n"
				+ "                                  to_number(nvl(pbtc.passrole, '0')) then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as pass_times,\n"
				+ "                       pbtc.id as id\n"
				+ "                 from pr_bzz_tch_stu_elective pbtse,\n"
				+ "                       pr_bzz_tch_opencourse   pbto,\n"
				+ "                       pe_bzz_tch_course       pbtc\n"
				+ "                where to_char(nvl(pbtse.completed_time,\n"
				+ "                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                              'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                  and to_char(nvl(pbtse.completed_time,\n"
				+ "                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                              'yyyy-MM') <=\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                  and pbtse.fk_tch_opencourse_id = pbto.id\n"
				+ "                  and pbto.fk_course_id = pbtc.id\n"
				+ "                  and nvl(pbtse.exam_times, 0) > 0\n"
				+ "                group by pbtc.id) dd on dd.id = a.id\n"
				+ "     left join (\n"
				+ "               /*取得当前时间*/\n"
				+ "               select to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy') as year,\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'mm') as month,\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'dd') as day\n"
				+ "                 from dual) f on 1 = 1\n"
				+ "     left join (\n"
				+ "               /*当前年课程被学习完成次数*/\n"
				+ "               select pbtc.id as id,\n"
				+ "                       sum(case\n"
				+ "                             when tcs.learn_status = 'COMPLETED' then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as com\n"
				+ "                 from training_course_student tcs\n"
				+ "                inner join pr_bzz_tch_opencourse pbto on tcs.course_id =\n"
				+ "                                                         pbto.id\n"
				+ "                inner join pe_bzz_tch_course pbtc on pbtc.id =\n"
				+ "                                                     pbto.fk_course_id\n"
				+ "                where to_char(nvl(tcs.complete_date,\n"
				+ "                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                              'yyyy-MM') <=\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                  and to_char(nvl(tcs.complete_date,\n"
				+ "                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                              'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                group by pbtc.id) g on g.id = a.id\n"
				+ "     left join (select pbtc.id as id, count(ebh.id) as refund_count\n"
				+ "                 from elective_back_history ebh\n"
				+ "                inner join pe_bzz_order_info pboi on pboi.id =\n"
				+ "                                                     ebh.fk_order_id\n"
				+ "                inner join pr_bzz_tch_opencourse pbto on pbto.id =\n"
				+ "                                                         ebh.fk_tch_opencourse_id\n"
				+ "                inner join pe_bzz_tch_course pbtc on pbto.fk_course_id =\n"
				+ "                                                     pbtc.id\n"
				+ "                where pboi.flag_refund_state =\n"
				+ "                      '40288a7b394207de01394210f6f40003' --已退费\n"
				+ "                  and to_char(pboi.refund_date, 'yyyy-MM') <=\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                  and to_char(pboi.refund_date, 'yyyy') =\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                group by pbtc.id) h on h.id = a.id\n"
				+ "     left join (select pbtc.id as id, count(ebh.id) as OVERYEAR_REFUND_TIMES\n"
				+ "                 from elective_back_history ebh\n"
				+ "                inner join pe_bzz_order_info pboi on pboi.id =\n"
				+ "                                                     ebh.fk_order_id\n"
				+ "                inner join pr_bzz_tch_opencourse pbto on pbto.id =\n"
				+ "                                                         ebh.fk_tch_opencourse_id\n"
				+ "                inner join pe_bzz_tch_course pbtc on pbto.fk_course_id =\n"
				+ "                                                     pbtc.id\n"
				+ "                where pboi.flag_refund_state =\n"
				+ "                      '40288a7b394207de01394210f6f40003' --跨年退费\n"
				+ "                  and to_char(pboi.refund_date, 'yyyy') =\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                  and to_char(pboi.payment_date, 'yyyy') =\n"
				+ "                      to_char((trunc("
				+ dateStr
				+ " - 1) - interval '1' year),\n"
				+ "                              'yyyy')\n"
				+ "                  and to_char(pboi.refund_date, 'yyyy-MM') <=\n"
				+ "                      to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                  and to_char(pboi.payment_date, 'yyyy-MM') <=\n"
				+ "                      to_char((trunc("
				+ dateStr
				+ " - 1) - interval '1' year),\n"
				+ "                              'yyyy-MM')\n"
				+ "                group by pbtc.id) i on i.id = a.id\n"
				+ "    where sc.FK_COURSE_ID = a.id) where sc.year = to_char\n"
				+ "  (trunc(" + dateStr + " - 1), 'yyyy') and sc.month = to_char\n" + "  (trunc(" + dateStr + " - 1), 'MM')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条课程数据");
		} catch (Exception e) {
			System.out.println("更新数据失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}

		System.out.println("更新课程统计初始化表格中数据结束" + new Date());
	}

	/**
	 * 20130109添加 课程统计，跨年统计数据项表STATISTIC_COURSE_YEAR中插入数据
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatAcrossInsert(String dateStr) {
		System.out.println("开始检测跨年表中是否存在当前年数据。。。" + new Date());
		dbpool db = new dbpool();
		int count = 0;
		MyResultSet rs = null;
		String sql_check = "select 1 from STATISTIC_COURSE_YEAR scy where scy.year = to_char(trunc(" + dateStr + "-1), 'yyyy')";
		try {
			rs = db.executeQuery(sql_check);
			if (rs.next()) {
				System.out.println("当前年记录存在，数据插入跳过");

				return;
			}
		} catch (Exception e) {
			System.out.println("查询数据失败！");
			// e.printStackTrace();
		} finally {
			db.close(rs);
		}
		System.out.println("开始向跨年表中插入数据。。。" + new Date());
		String sql = "insert into STATISTIC_COURSE_YEAR\n" + "  (year,\n" + "   ELECTIVE_PERSON_NUMBER,\n"
				+ "   START_STUDY_PERSON_NUMBER,\n" + "   COMPLETED_STUDY_PERSON_NUMBER,\n" + "   START_EXAM_PERSON_NUMBER,\n"
				+ "   PASS_EXAM_PERSON_NUMBER,\n" + "   PRE_START_STUDY_NUMBER,\n" + "   PRE_COMPLETED__STUDY_NUMBER,\n"
				+ "   PRE_START_EXAM_NUMBER,\n" + "   PRE_PASS_EXAM_NUMBER,\n" + "   PRE_COURSE_TIMES,\n" + "   PRE_PASS_TJMES,\n"
				+ "   UPDATE_TIME,\n" + "   PRE_PASS_COM_TEN_HOURS,\n" + "   PRE_PASS_COM_FIFTEEN_HOURS,\n" + "   PASS_COM_TEN_HOURS,\n"
				+ "   PASS_COM_FIFTEEN_HOURS,\n" + "   PASS_TJMES)\n" + "  select f.year,\n"
				+ "         nvl(a.total_number, 0), --当前年选课人数\n" + "         nvl(b.inc, 0), --当前年开始学习人数\n"
				+ "         nvl(g.com, 0), --当前年通过人数\n" + "         nvl(cc.exam_number, 0), --当前年测验人数\n"
				+ "         nvl(c.pass_times, 0), --当前年测验通过人数\n" + "         nvl(d.tcs_total_number, 0), --非当前年开始学习人数\n"
				+ "         nvl(d.pre_com, 0), --非当前年学习完成人数\n" + "         nvl(e.pre_exam_number, 0), --非当前年考试人数\n"
				+ "         nvl(e.pre_pass_times, 0), --非当前年测验通过人数\n" + "         nvl(i.pre_course_times, 0), --非当前年报名，今年考试学时总数\n"
				+ "         nvl(i.pre_pass_times, 0), --非当前年报名，今年考试通过学时总数\n" + "         f.update_time, --数据更新日期\n"
				+ "         nvl(j.PRE_PASS_COM_TEN_HOURS, 0), --跨年完成10学时\n" + "         nvl(k.PRE_PASS_COM_FIFTEEN_HOURS, 0), --跨年完成15学时\n"
				+ "         nvl(l.PASS_COM_TEN_HOURS, 0), --完成10学时人数\n" + "         nvl(m.PASS_COM_FIFTEEN_HOURS, 0), --完成15学时人数\n"
				+ "         nvl(n.pass_times, 0) --完成学时数\n" + "    from (select count(pbtse.id) as total_number\n"
				+ "            from pr_bzz_tch_stu_elective pbtse\n" + "           where to_char(nvl(pbtse.elective_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n" + "                         'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "             AND to_char(nvl(pbtse.elective_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy-MM') <=\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "             and pbtse.flag_elective_pay_status =\n"
				+ "                 '40288a7b3981661e01398186b0f50006') a,\n"
				+ "         (\n"
				+ "          select sum(case\n"
				+ "                        when tcs.learn_status <> 'UNCOMPLETE' then\n"
				+ "                         1\n"
				+ "                        else\n"
				+ "                         0\n"
				+ "                      end) as inc,\n"
				+ "                  sum(case\n"
				+ "                        when tcs.learn_status = 'UNCOMPLETE' then\n"
				+ "                         1\n"
				+ "                        else\n"
				+ "                         0\n"
				+ "                      end) as unc\n"
				+ "            from training_course_student tcs\n"
				+ "           where to_char(nvl(tcs.get_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "             AND to_char(nvl(tcs.get_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy-MM') <= to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM') /**今年课程学习情况*/\n"
				+ "          ) b,\n"
				+ "         (select sum(case\n"
				+ "                       when tcs.learn_status = 'COMPLETED' then\n"
				+ "                        1\n"
				+ "                       else\n"
				+ "                        0\n"
				+ "                     end) as com\n"
				+ "            from training_course_student tcs\n"
				+ "           where to_char(nvl(tcs.complete_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "             AND to_char(nvl(tcs.complete_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy-MM') <=\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM') /**今年课程学习完成情况*/\n"
				+ "          ) g,\n"
				+ "         ( /*开始考试人数*/\n"
				+ "          select sum(case\n"
				+ "                        when nvl(pbtse.exam_times, 0) > 0 then\n"
				+ "                         1\n"
				+ "                        else\n"
				+ "                         0\n"
				+ "                      end) as exam_number\n"
				+ "            from pr_bzz_tch_stu_elective pbtse\n"
				+ "           where to_char(nvl(pbtse.elective_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "             and to_char(nvl(pbtse.elective_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy-MM') <= to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')) cc,\n"
				+ "         (select count(tth.id) as exam_number, --测验人数\n"
				+ "                 sum(case\n"
				+ "                       when to_number(nvl(tth.score, 0)) >=\n"
				+ "                            to_number(pbtc.passrole) then\n"
				+ "                        1\n"
				+ "                       else\n"
				+ "                        0\n"
				+ "                     end) as pass_times --通过人数\n"
				+ "            from test_testpaper_history tth\n"
				+ "           inner join test_testpaper_info tti\n"
				+ "              on tth.testpaper_id = tti.id\n"
				+ "           inner join pe_bzz_tch_course pbtc\n"
				+ "              on tti.group_id = pbtc.id\n"
				+ "           where to_char(tth.test_date, 'yyyy') =\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "             AND to_char(tth.test_date, 'yyyy-MM') <=\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM') /**今年测验情况*/\n"
				+ "          ) c,\n"
				+ "         (select count(pbtse.id) as tcs_total_number,\n"
				+ "                 sum(case\n"
				+ "                       when tcs.learn_status = 'COMPLETED' then\n"
				+ "                        1\n"
				+ "                       else\n"
				+ "                        0\n"
				+ "                     end) as pre_com\n"
				+ "            from pr_bzz_tch_stu_elective pbtse\n"
				+ "           inner join training_course_student tcs\n"
				+ "              on pbtse.fk_training_id = tcs.id\n"
				+ "           where to_number(to_char(nvl(pbtse.elective_date,\n"
				+ "                                       to_date('1900-01-01 00:00:00',\n"
				+ "                                               'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                   'yyyy')) =\n"
				+ "                 (to_number(to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')) - 1)\n"
				+ "             and to_char(nvl(tcs.complete_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "             and to_char(nvl(tcs.complete_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy-MM') <=\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "             and pbtse.flag_elective_pay_status =\n"
				+ "                 '40288a7b3981661e01398186b0f50006'\n"
				+ "          /*非今年报名学习人数，学习完成人数**/\n"
				+ "          ) d,\n"
				+ "         (select count(tti.id) as pre_exam_number,\n"
				+ "                  sum(case\n"
				+ "                        when to_number(nvl(tth.score, 0)) >=\n"
				+ "                             to_number(pbtc.passrole) then\n"
				+ "                         1\n"
				+ "                        else\n"
				+ "                         0\n"
				+ "                      end) as pre_pass_times\n"
				+ "             from pr_bzz_tch_stu_elective pbtse\n"
				+ "            inner join pr_bzz_tch_opencourse pbto\n"
				+ "               on pbtse.fk_tch_opencourse_id = pbto.id\n"
				+ "            inner join pe_bzz_tch_course pbtc\n"
				+ "               on pbtc.id = pbto.fk_course_id\n"
				+ "            inner join test_testpaper_info tti\n"
				+ "               on tti.group_id = pbtc.id\n"
				+ "            inner join test_testpaper_history tth\n"
				+ "               on tth.testpaper_id = tti.id\n"
				+ "            where /*to_number(to_char(pbtse.elective_date, 'yyyy')) =\n"
				+ "                                                                                                           (to_number(to_char(trunc("
				+ dateStr
				+ "-1), 'yyyy')) - 1)*/\n"
				+ "            to_number(to_char(nvl(pbtse.elective_date,\n"
				+ "                                  to_date('1900-01-01 00:00:00',\n"
				+ "                                          'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                              'yyyy')) =\n"
				+ "            (to_number(to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')) - 1)\n"
				+ "         and pbtse.flag_elective_pay_status =\n"
				+ "            '40288a7b3981661e01398186b0f50006'\n"
				+ "         and to_char(nvl(tth.test_date,\n"
				+ "                        to_date('1900-01-01 00:00:00', 'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                    'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "         and to_char(nvl(tth.test_date,\n"
				+ "                        to_date('1900-01-01 00:00:00', 'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                    'yyyy-MM') <= to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')) e, /*去年报名，今年考试和通过的*/\n"
				+ "         (select to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy') as year,\n"
				+ "                 trunc("
				+ dateStr
				+ " - 1) as update_time\n"
				+ "            from dual --年份\n"
				+ "          ) f,\n"
				+ "         (select sum(to_number(pbtc.time)) as pre_course_times,\n"
				+ "                 sum(case\n"
				+ "                       when to_number(nvl(tth.score, 0)) >=\n"
				+ "                            to_number(pbtc.passrole) then\n"
				+ "                        to_number(pbtc.time)\n"
				+ "                       else\n"
				+ "                        0\n"
				+ "                     end) as pre_pass_times\n"
				+ "            from pr_bzz_tch_stu_elective pbtse\n"
				+ "           inner join training_course_student tcs\n"
				+ "              on pbtse.fk_training_id = tcs.id\n"
				+ "           inner join pr_bzz_tch_opencourse pbto\n"
				+ "              on pbtse.fk_tch_opencourse_id = pbto.id\n"
				+ "           inner join pe_bzz_tch_course pbtc\n"
				+ "              on pbto.fk_course_id = pbtc.id\n"
				+ "           inner join test_testpaper_info tti\n"
				+ "              on tti.group_id = pbtc.id\n"
				+ "           inner join test_testpaper_history tth\n"
				+ "              on tth.testpaper_id = tti.id\n"
				+ "           where to_number(to_char(nvl(pbtse.elective_date,\n"
				+ "                                       to_date('1900-01-01 00:00:00',\n"
				+ "                                               'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                   'yyyy')) =\n"
				+ "                 (to_number(to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')) - 1)\n"
				+ "             and to_char(nvl(tth.test_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "             and to_char(nvl(tth.test_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy-MM') <=\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "             and pbtse.flag_elective_pay_status =\n"
				+ "                 '40288a7b3981661e01398186b0f50006'\n"
				+ "          ) i, /*去年报名，今年测验总学时，通过总学时*/\n"
				+ "         (select count(distinct pbs.id) as PRE_PASS_COM_TEN_HOURS\n"
				+ "            from pr_bzz_tch_stu_elective pbtse, pe_bzz_student pbs\n"
				+ "           where to_char(nvl(pbtse.elective_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy') =\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1) - interval '1' year, 'yyyy')\n"
				+ "             and to_char(nvl(pbs.com_ten_hours_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "             and to_char(nvl(pbs.com_ten_hours_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy-MM') <=\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "             and pbtse.fk_stu_id = pbs.id\n"
				+ "             and pbtse.flag_elective_pay_status =\n"
				+ "                 '40288a7b3981661e01398186b0f50006'\n"
				+ "          ) j, /*跨年完成10学时人数*/\n"
				+ "         (select count(distinct pbs.id) as PRE_PASS_COM_FIFTEEN_HOURS\n"
				+ "            from pr_bzz_tch_stu_elective pbtse, pe_bzz_student pbs\n"
				+ "           where to_char(nvl(pbtse.elective_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy') =\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1) - interval '1' year, 'yyyy')\n"
				+ "             and to_char(nvl(pbs.com_fifteen_hours_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "             and to_char(nvl(pbs.com_fifteen_hours_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy-MM') <=\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "             and pbtse.fk_stu_id = pbs.id\n"
				+ "             and pbtse.flag_elective_pay_status =\n"
				+ "                 '40288a7b3981661e01398186b0f50006'\n"
				+ "          ) k, /*跨年完成15学时人数*/\n"
				+ "         (select count(distinct pbs.id) as PASS_COM_TEN_HOURS\n"
				+ "            from pe_bzz_student pbs\n"
				+ "           where to_char(nvl(pbs.com_ten_hours_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "             and to_char(nvl(pbs.com_ten_hours_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy-MM') <=\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "          ) l, /*完成10学时人数*/\n"
				+ "         (select count(distinct pbs.id) as PASS_COM_FIFTEEN_HOURS\n"
				+ "            from pe_bzz_student pbs\n"
				+ "           where to_char(nvl(pbs.com_fifteen_hours_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "             AND to_char(nvl(pbs.com_fifteen_hours_date,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy-MM') <=\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "          ) m, /*完成15学时人数*/\n"
				+ "         (select sum(case\n"
				+ "                       when pbtse.score_exam >= to_number(pbtc.passrole) then\n"
				+ "                        to_number(pbtc.time)\n"
				+ "                       else\n"
				+ "                        0\n"
				+ "                     end) as pass_times\n"
				+ "            from pr_bzz_tch_stu_elective pbtse\n"
				+ "           inner join pr_bzz_tch_opencourse pbto\n"
				+ "              on pbtse.fk_tch_opencourse_id = pbto.id\n"
				+ "           inner join pe_bzz_tch_course pbtc\n"
				+ "              on pbto.fk_course_id = pbtc.id\n"
				+ "           where to_char(nvl(pbtse.completed_time,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "             AND to_char(nvl(pbtse.completed_time,\n"
				+ "                             to_date('1900-01-01 00:00:00',\n"
				+ "                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                         'yyyy-MM') <=\n"
				+ "                 to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "             and pbtse.flag_elective_pay_status =\n"
				+ "                 '40288a7b3981661e01398186b0f50006'\n"
				+ "          ) n /*完成学时数*/";

		try {
			count = db.executeUpdate(sql);
			System.out.println("插入" + count + "条数据");
		} catch (Exception e) {
			System.out.println("数据插入失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
		System.out.println("跨年表中插入数据完成" + new Date());
	}

	/**
	 * 20130109添加 课程统计，跨年统计数据项表STATISTIC_COURSE_YEAR中更新数据
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatAcrossUpdate(String dateStr) {
		System.out.println("开始更新跨年表中数据。。。" + new Date());
		dbpool db = new dbpool();
		int count = 0;
		String sql =

		"update STATISTIC_COURSE_YEAR scy\n" + "   set ( --year,\n" + "        ELECTIVE_PERSON_NUMBER,\n"
				+ "        START_STUDY_PERSON_NUMBER,\n" + "        COMPLETED_STUDY_PERSON_NUMBER,\n"
				+ "        START_EXAM_PERSON_NUMBER,\n" + "        PASS_EXAM_PERSON_NUMBER,\n" + "        PRE_START_STUDY_NUMBER,\n"
				+ "        PRE_COMPLETED__STUDY_NUMBER,\n" + "        PRE_START_EXAM_NUMBER,\n" + "        PRE_PASS_EXAM_NUMBER,\n"
				+ "        PRE_COURSE_TIMES,\n" + "        PRE_PASS_TJMES,\n" + "        UPDATE_TIME,\n"
				+ "        PRE_PASS_COM_TEN_HOURS,\n" + "        PRE_PASS_COM_FIFTEEN_HOURS,\n" + "        PASS_COM_TEN_HOURS,\n"
				+ "        PASS_COM_FIFTEEN_HOURS,\n" + "        PASS_TJMES) =\n" + "       (select --f.year,\n"
				+ "         nvl(a.total_number, 0), --当前年选课人数\n" + "         nvl(b.inc, 0), --当前年开始学习人数\n"
				+ "         nvl(g.com, 0), --当前年通过人数\n" + "         nvl(cc.exam_number, 0), --当前年测验人数\n"
				+ "         nvl(c.pass_times, 0), --当前年测验通过人数\n" + "         nvl(d.tcs_total_number, 0), --非当前年开始学习人数\n"
				+ "         nvl(d.pre_com, 0), --非当前年学习完成人数\n" + "         nvl(e.pre_exam_number, 0), --非当前年考试人数\n"
				+ "         nvl(e.pre_pass_times, 0), --非当前年测验通过人数\n" + "         nvl(i.pre_course_times, 0), --非当前年报名，今年考试学时总数\n"
				+ "         nvl(i.pre_pass_times, 0), --非当前年报名，今年考试通过学时总数\n" + "         f.update_time, --数据更新日期\n"
				+ "         nvl(j.PRE_PASS_COM_TEN_HOURS, 0), --跨年完成10学时\n" + "         nvl(k.PRE_PASS_COM_FIFTEEN_HOURS, 0), --跨年完成15学时\n"
				+ "         nvl(l.PASS_COM_TEN_HOURS, 0), --完成10学时人数\n" + "         nvl(m.PASS_COM_FIFTEEN_HOURS, 0), --完成15学时人数\n"
				+ "         nvl(n.pass_times, 0) --完成学时数\n" + "          from (select count(pbtse.id) as total_number\n"
				+ "                  from pr_bzz_tch_stu_elective pbtse\n" + "                 where to_char(nvl(pbtse.elective_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   AND to_char(nvl(pbtse.elective_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                   and pbtse.flag_elective_pay_status =\n"
				+ "                       '40288a7b3981661e01398186b0f50006') a,\n"
				+ "               (select sum(case\n"
				+ "                             when tcs.learn_status <> 'UNCOMPLETE' then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as inc,\n"
				+ "                       sum(case\n"
				+ "                             when tcs.learn_status = 'UNCOMPLETE' then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as unc\n"
				+ "                  from training_course_student tcs\n"
				+ "                 where to_char(nvl(tcs.get_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   AND to_char(nvl(tcs.get_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM') /**今年课程学习情况*/\n"
				+ "                ) b,\n"
				+ "               (select sum(case\n"
				+ "                             when tcs.learn_status = 'COMPLETED' then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as com\n"
				+ "                  from training_course_student tcs\n"
				+ "                 where to_char(nvl(tcs.complete_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   AND to_char(nvl(tcs.complete_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM') /**今年课程学习完成情况*/\n"
				+ "                ) g,\n"
				+ "               ( /*开始考试人数*/\n"
				+ "                select sum(case\n"
				+ "                              when nvl(pbtse.exam_times, 0) > 0 then\n"
				+ "                               1\n"
				+ "                              else\n"
				+ "                               0\n"
				+ "                            end) as exam_number\n"
				+ "                  from pr_bzz_tch_stu_elective pbtse\n"
				+ "                 where to_char(nvl(pbtse.elective_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   and to_char(nvl(pbtse.elective_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')) cc,\n"
				+ "               (select count(tth.id) as exam_number, --测验人数\n"
				+ "                       sum(case\n"
				+ "                             when to_number(nvl(tth.score, 0)) >=\n"
				+ "                                  to_number(pbtc.passrole) then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as pass_times --通过人数\n"
				+ "                  from test_testpaper_history tth\n"
				+ "                 inner join test_testpaper_info tti\n"
				+ "                    on tth.testpaper_id = tti.id\n"
				+ "                 inner join pe_bzz_tch_course pbtc\n"
				+ "                    on tti.group_id = pbtc.id\n"
				+ "                 where to_char(tth.test_date, 'yyyy') =\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   AND to_char(tth.test_date, 'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM') /**今年测验情况*/\n"
				+ "                ) c,\n"
				+ "               (select count(pbtse.id) as tcs_total_number,\n"
				+ "                       sum(case\n"
				+ "                             when tcs.learn_status = 'COMPLETED' then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as pre_com\n"
				+ "                  from pr_bzz_tch_stu_elective pbtse\n"
				+ "                 inner join training_course_student tcs\n"
				+ "                    on pbtse.fk_training_id = tcs.id\n"
				+ "                 where to_number(to_char(nvl(pbtse.elective_date,\n"
				+ "                                             to_date('1900-01-01 00:00:00',\n"
				+ "                                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                         'yyyy')) =\n"
				+ "                       (to_number(to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')) - 1)\n"
				+ "                   and to_char(nvl(tcs.complete_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   and to_char(nvl(tcs.complete_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                   and pbtse.flag_elective_pay_status =\n"
				+ "                       '40288a7b3981661e01398186b0f50006'\n"
				+ "                /*非今年报名学习人数，学习完成人数**/\n"
				+ "                ) d,\n"
				+ "               (select count(tti.id) as pre_exam_number,\n"
				+ "                       sum(case\n"
				+ "                             when to_number(nvl(tth.score, 0)) >=\n"
				+ "                                  to_number(pbtc.passrole) then\n"
				+ "                              1\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as pre_pass_times\n"
				+ "                  from pr_bzz_tch_stu_elective pbtse\n"
				+ "                 inner join pr_bzz_tch_opencourse pbto\n"
				+ "                    on pbtse.fk_tch_opencourse_id = pbto.id\n"
				+ "                 inner join pe_bzz_tch_course pbtc\n"
				+ "                    on pbtc.id = pbto.fk_course_id\n"
				+ "                 inner join test_testpaper_info tti\n"
				+ "                    on tti.group_id = pbtc.id\n"
				+ "                 inner join test_testpaper_history tth\n"
				+ "                    on tth.testpaper_id = tti.id\n"
				+ "                 where to_number(to_char(nvl(pbtse.elective_date,\n"
				+ "                                             to_date('1900-01-01 00:00:00',\n"
				+ "                                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                         'yyyy')) =\n"
				+ "                       (to_number(to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')) - 1)\n"
				+ "                   and pbtse.flag_elective_pay_status =\n"
				+ "                       '40288a7b3981661e01398186b0f50006'\n"
				+ "                   and to_char(nvl(tth.test_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   and to_char(nvl(tth.test_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')) e, /*去年报名，今年考试和通过的*/\n"
				+ "               (select to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy') as year,\n"
				+ "                       trunc("
				+ dateStr
				+ " - 1) as update_time\n"
				+ "                  from dual --年份\n"
				+ "                ) f,\n"
				+ "               (select sum(to_number(pbtc.time)) as pre_course_times,\n"
				+ "                       sum(case\n"
				+ "                             when to_number(nvl(tth.score, 0)) >=\n"
				+ "                                  to_number(pbtc.passrole) then\n"
				+ "                              to_number(pbtc.time)\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as pre_pass_times\n"
				+ "                  from pr_bzz_tch_stu_elective pbtse\n"
				+ "                 inner join training_course_student tcs\n"
				+ "                    on pbtse.fk_training_id = tcs.id\n"
				+ "                 inner join pr_bzz_tch_opencourse pbto\n"
				+ "                    on pbtse.fk_tch_opencourse_id = pbto.id\n"
				+ "                 inner join pe_bzz_tch_course pbtc\n"
				+ "                    on pbto.fk_course_id = pbtc.id\n"
				+ "                 inner join test_testpaper_info tti\n"
				+ "                    on tti.group_id = pbtc.id\n"
				+ "                 inner join test_testpaper_history tth\n"
				+ "                    on tth.testpaper_id = tti.id\n"
				+ "                 where to_number(to_char(nvl(pbtse.elective_date,\n"
				+ "                                             to_date('1900-01-01 00:00:00',\n"
				+ "                                                     'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                                         'yyyy')) =\n"
				+ "                       (to_number(to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')) - 1)\n"
				+ "                   and to_char(nvl(tth.test_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   and to_char(nvl(tth.test_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                   and pbtse.flag_elective_pay_status =\n"
				+ "                       '40288a7b3981661e01398186b0f50006'\n"
				+ "                ) i, /*去年报名，今年测验总学时，通过总学时*/\n"
				+ "               (select count(distinct pbs.id) as PRE_PASS_COM_TEN_HOURS\n"
				+ "                  from pr_bzz_tch_stu_elective pbtse, pe_bzz_student pbs\n"
				+ "                 where to_char(nvl(pbtse.elective_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') =\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1) - interval '1' year, 'yyyy')\n"
				+ "                   and to_char(nvl(pbs.com_ten_hours_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   and to_char(nvl(pbs.com_ten_hours_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                   and pbtse.fk_stu_id = pbs.id\n"
				+ "                   and pbtse.flag_elective_pay_status =\n"
				+ "                       '40288a7b3981661e01398186b0f50006'\n"
				+ "                ) j, /*跨年完成10学时人数*/\n"
				+ "               (select count(distinct pbs.id) as PRE_PASS_COM_FIFTEEN_HOURS\n"
				+ "                  from pr_bzz_tch_stu_elective pbtse, pe_bzz_student pbs\n"
				+ "                 where to_char(nvl(pbtse.elective_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') =\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1) - interval '1' year, 'yyyy')\n"
				+ "                   and to_char(nvl(pbs.com_fifteen_hours_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   and to_char(nvl(pbs.com_fifteen_hours_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                   and pbtse.fk_stu_id = pbs.id\n"
				+ "                   and pbtse.flag_elective_pay_status =\n"
				+ "                       '40288a7b3981661e01398186b0f50006'\n"
				+ "                ) k, /*跨年完成15学时人数*/\n"
				+ "               (select count(distinct pbs.id) as PASS_COM_TEN_HOURS\n"
				+ "                  from pe_bzz_student pbs\n"
				+ "                 where to_char(nvl(pbs.com_ten_hours_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   and to_char(nvl(pbs.com_ten_hours_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                ) l, /*完成10学时人数*/\n"
				+ "               (select count(distinct pbs.id) as PASS_COM_FIFTEEN_HOURS\n"
				+ "                  from pe_bzz_student pbs\n"
				+ "                 where to_char(nvl(pbs.com_fifteen_hours_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   AND to_char(nvl(pbs.com_fifteen_hours_date,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                ) m, /*完成15学时人数*/\n"
				+ "               (select sum(case\n"
				+ "                             when pbtse.score_exam >= to_number(pbtc.passrole) then\n"
				+ "                              to_number(pbtc.time)\n"
				+ "                             else\n"
				+ "                              0\n"
				+ "                           end) as pass_times\n"
				+ "                  from pr_bzz_tch_stu_elective pbtse\n"
				+ "                 inner join pr_bzz_tch_opencourse pbto\n"
				+ "                    on pbtse.fk_tch_opencourse_id = pbto.id\n"
				+ "                 inner join pe_bzz_tch_course pbtc\n"
				+ "                    on pbto.fk_course_id = pbtc.id\n"
				+ "                 where to_char(nvl(pbtse.completed_time,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy') = to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy')\n"
				+ "                   AND to_char(nvl(pbtse.completed_time,\n"
				+ "                                   to_date('1900-01-01 00:00:00',\n"
				+ "                                           'yyyy-MM-dd hh24:mi:ss')),\n"
				+ "                               'yyyy-MM') <=\n"
				+ "                       to_char(trunc("
				+ dateStr
				+ " - 1), 'yyyy-MM')\n"
				+ "                   and pbtse.flag_elective_pay_status =\n"
				+ "                       '40288a7b3981661e01398186b0f50006'\n"
				+ "                ) n /*完成学时数*/\n"
				+ "         where f.year = scy.year)\n" + " where scy.year = to_char(trunc(" + dateStr + " - 1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条数据");
		} catch (Exception e) {
			System.out.println("数据更新失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
		System.out.println("更新跨年表中当前年数据完毕" + new Date());
	}

	/**
	 * 初始化集体报名概况
	 */
	public void exeInitTeamEntry(String dateStr) {
		/* 插入管理员id,年如果存在则 */
		dbpool db = new dbpool();
		String sql = "insert into teamentry\n" + "  (year, manager_id)\n" + "  select to_char(" + dateStr + ", 'yyyy'), su.id\n"
				+ "    from sso_user su\n" + "   where su.fk_role_id = '2'\n" + "     and su.id not in (select manager_id\n"
				+ "                         from teamentry\n" + "                        where year = to_char(" + dateStr + ", 'yyyy'))";

		try {
			int count = db.executeUpdate(sql);
			System.out.println("成功插入" + count + "条管理员记录！");
		} catch (Exception e) {
			System.out.println("已存在所有管理员，无需插入！");
		} finally {
			System.out.println("初始化管理员完毕！");
		}
	}

	/**
	 * 更新集体报名概况
	 * 
	 * @param dateStr
	 * @return
	 * @author linjie
	 */
	public void exeStatTeamEntry(String dateStr) {
		System.out.println("正在更新集体报名概况。。。。");
		dbpool db = new dbpool();
		int count = 0;
		String sql = "update teamentry tt\n" + "   set (sumentry, --报名单总数\n" + "       payedentry, --已支付报名单\n"
				+ "       payedcourse, --已支付报名课程\n" + "       payedstudent, --已支付学员数\n" + "       payedamount) --已支付金额\n"
				+ "       = (select nvl(amount, 0),\n" + "                 nvl(amounted, 0),\n" + "                 nvl(coursenum, 0),\n"
				+ "                 nvl(stunum, 0),\n" + "                 nvl(money, 0)\n" + "            from (select a.years,\n"
				+ "                         a.createUser,\n" + "                         a.amount,\n"
				+ "                         a.amounted,\n" + "                         b.coursenum,\n"
				+ "                         b.stunum,\n" + "                         a.money\n" + "                    from (\n" + "\n"
				+ "                          select a.years,\n" + "                                  su.id as createUser,\n"
				+ "                                  a.amount,\n" + "                                  a.amounted,\n"
				+ "                                  a.money\n"
				+ "                            from (select to_char(p.create_date, 'yyyy') as years,\n"
				+ "                                          substr(su.login_id, 0, 4) as createUser,\n"
				+ "                                          count(p.id) as amount,\n"
				+ "                                          sum(case\n"
				+ "                                                when p.flag_payment_state =\n"
				+ "                                                     '40288a7b394207de01394221a6ff000e' then\n"
				+ "                                                 1\n" + "                                                else\n"
				+ "                                                 0\n"
				+ "                                              end) as amounted,\n"
				+ "                                          sum(case\n"
				+ "                                                when p.flag_payment_state =\n"
				+ "                                                     '40288a7b394207de01394221a6ff000e' then\n"
				+ "                                                 to_number(p.AMOUNT)\n"
				+ "                                                else\n" + "                                                 0\n"
				+ "                                              end) as money\n"
				+ "                                     from pe_bzz_order_info p\n"
				+ "                                    inner join sso_user su on p.create_user =\n"
				+ "                                                              su.id\n"
				+ "                                    where su.fk_role_id in ('2', '4')\n"
				+ "                                    group by to_char(p.CREATE_DATE, 'yyyy'),\n"
				+ "                                             substr(su.login_id, 0, 4)) a\n"
				+ "                           inner join sso_user su on su.login_id = a.createUser\n" + "                          ) a,\n"
				+ "                         (select to_char(p.create_date, 'yyyy') as years,\n"
				+ "                                 p.create_user as createUser,\n"
				+ "                                 count(distinct ele.fk_stu_id) as stunum,\n"
				+ "                                 count(distinct opencourse.fk_course_id) as coursenum\n"
				+ "                            from pr_bzz_tch_stu_elective ele,\n"
				+ "                                 pr_bzz_tch_opencourse   opencourse,\n"
				+ "                                 pe_bzz_order_info       p\n"
				+ "                           inner join sso_user su on p.create_user = su.id\n"
				+ "                           where ele.fk_tch_opencourse_id = opencourse.id\n"
				+ "                             and ele.fk_order_id = p.id\n" + "                             and su.fk_role_id = '2'\n"
				+ "                           group by to_char(p.create_date, 'yyyy'),\n"
				+ "                                    p.create_user) b\n" + "                   where a.years = b.years(+)\n"
				+ "                     and a.years = to_char(trunc(" + dateStr + " - 1), 'yyyy')\n"
				+ "                     and a.createUser = b.createUser) aa\n" + "           where aa.createUser = tt.manager_id)\n"
				+ " where year = to_char(trunc(" + dateStr + " - 1), 'yyyy')";

		try {
			count = db.executeUpdate(sql);
			System.out.println("更新" + count + "条集体报名概况完毕！");
		} catch (Exception e) {
			System.out.println("更新集体报名概况失败！");
			// e.printStackTrace();
		} finally {
			db = null;
		}
	}

	private boolean isContains(HashSet<String> typeSet, String cStr) {
		if (typeSet.contains("all") || typeSet.contains(cStr)) {
			return true;
		}
		return false;
	}

	@Override
	public void exeAllStat(String dateStr, HashSet<String> typeSet) {
		/*-------------更新statistic_enterprise开始-----------------------*/
		// System.out.println("+++++++++正在更新statistic_enterprise表+++++++++");
		// if(!"sysdate".equals(dateStr)){
		// dateStr = "to_date('"+dateStr+"','yyyy-MM-dd')";
		// }
		// if(isContains(typeSet,"1")){
		// this.exeInitEntry(dateStr);//初始化statistic_enterprise表
		// }
		// if(isContains(typeSet,"2")){
		// this.exeStatEntry(dateStr);//机构类型
		// }
		// if(isContains(typeSet,"3")){
		// this.exeStatRegEnNum(dateStr);//注册机构新增
		// }
		// if(isContains(typeSet,"4")){
		// this.exeStatRegEnCount(dateStr);//注册机构累计
		// }
		// if(isContains(typeSet,"5")){
		// this.exeStatRegUserCount(dateStr);//注册人数累计
		// }
		// if(isContains(typeSet,"6")){
		// this.exeStatRegUserNum(dateStr);//注册人数新增
		// }
		// if(isContains(typeSet,"7")){
		// this.exeStatBeginTesCount(dateStr);//开始测验累计
		// }
		// if(isContains(typeSet,"8")){
		// this.exeStatComTesCount(dateStr);//完成测验累计
		// }
		// if(isContains(typeSet,"9")){
		// this.exeStatBeginTesNum(dateStr);//开始测验新增
		// }
		// if(isContains(typeSet,"10")){
		// this.exeStatComTesNum(dateStr);//完成测验新增
		// }
		// if(isContains(typeSet,"11")){
		// this.exeStatEntryNum(dateStr);//报名人数
		// }
		// if(isContains(typeSet,"12")){
		// this.exeStatEntryTimes(dateStr);//报名人次
		// }
		// if(isContains(typeSet,"13")){
		// this.exeStatEntryHours(dateStr);//报名学时数
		// }
		// if(isContains(typeSet,"14")){
		// this.exeStatFeeAmount(dateStr);//付费金额
		// }
		// if(isContains(typeSet,"15")){
		// this.exeStatComHours(dateStr);//完成学时数
		// }
		// if(isContains(typeSet,"16")){
		// this.exeStatRefundNum(dateStr);//退费人数
		// }
		// if(isContains(typeSet,"17")){
		// this.exeStatRefundTimes(dateStr);//退费人次
		// }
		// if(isContains(typeSet,"18")){
		// this.exeStatRefundHours(dateStr);//退费学时
		// }
		// if(isContains(typeSet,"19")){
		// this.exeStatRefundAmount(dateStr);//退费金额
		// }
		// if(isContains(typeSet,"20")){
		// this.exeStatOverYearRefundNum(dateStr);//跨年退费人数
		// }
		// if(isContains(typeSet,"21")){
		// this.exeStatOverYearRefundTimes(dateStr);//跨年退费人次
		// }
		// if(isContains(typeSet,"22")){
		// this.exeStatOverYearRefundHours(dateStr);//跨年退费学时
		// }
		// if(isContains(typeSet,"23")){
		// this.exeStatOverYearRefundAmount(dateStr);//跨年退费金额
		// }
		// if(isContains(typeSet,"24")){
		// this.exeStatBeginStudyNum(dateStr);//开始学习人次
		// }
		// if(isContains(typeSet,"25")){
		// this.exeStatComStudyNum(dateStr);//完成学习人次
		// }
		// if(isContains(typeSet,"26")){
		// this.exeStatBeginStudyCount(dateStr);//开始学习累计
		// }
		// if(isContains(typeSet,"27")){
		// this.exeStatComStudyCount(dateStr);//完成学习累计
		// }
		// if(isContains(typeSet,"28")){
		// this.exeStatComHoursCount(dateStr);//完成学时数累计
		// }
		// if(isContains(typeSet,"29")){
		// this.exeUpdateStudent(dateStr);//修改学员表完成10学时时间
		// }
		// if(isContains(typeSet,"30")){
		// this.exeStatComTenHours(dateStr);//完成10个必修学时数
		// }
		// if(isContains(typeSet,"31")){
		// this.exeStatComTenHoursCount(dateStr);//完成10个必修学时人数累计
		// }
		// if(isContains(typeSet,"32")){
		// this.exeUpdateFifStudent(dateStr);//修改学员表完成15学时人数时间
		// }
		// if(isContains(typeSet,"33")){
		// this.exeStatComFifteenHours(dateStr);//完成15个必修学时人数
		// }
		// if(isContains(typeSet,"34")){
		// this.exeStatComFifteenHoursCount(dateStr);//完成15个必修学时人数累计
		// }
		// //System.out.println("+++++++++更新statistic_enterprise表完毕！+++++++++");
		// /*-------------更新statistic_enterprise结束-----------------------*/
		//		
		//		
		// /*-------------------更新statistic_year开始-----------------------*/
		// //System.out.println("+++++++++正在更新statistic_year表+++++++++");
		// /*查询是否存在当年记录，如果存在则直接进行更新操作，否则插入年份*/
		// if(isContains(typeSet,"35")){
		// this.exeInitStatisticYear(dateStr);//初始化statistic_year表
		// }
		// if(isContains(typeSet,"36")){
		// this.exeStatYearRegEnNum(dateStr);//年注册机构新增
		// }
		// if(isContains(typeSet,"37")){
		// this.exeStatYearRegEnCount(dateStr);//年注册机构累计
		// }
		// if(isContains(typeSet,"38")){
		// this.exeStatYearRegUserNum(dateStr);//年注册人数新增
		// }
		// if(isContains(typeSet,"39")){
		// this.exeStatYearRegUserCount(dateStr);//年注册人数累计
		// }
		// if(isContains(typeSet,"40")){
		// this.exeStatYearEntryUserNum(dateStr);//年报名人数
		// }
		// if(isContains(typeSet,"41")){
		// this.exeStatYearEntryUserTimes(dateStr);//年报名人次
		// }
		// if(isContains(typeSet,"42")){
		// this.exeStatYearEntryHours(dateStr);//年报名学时数
		// }
		// if(isContains(typeSet,"43")){
		// this.exeStatYearFeeAmount(dateStr);//年付费金额
		// }
		// if(isContains(typeSet,"44")){
		// this.exeStatYearBeginStudyTimes(dateStr);//年开始学习人次
		// }
		// if(isContains(typeSet,"45")){
		// this.exeStatYearComStudyTimes(dateStr);//年完成学习人次
		// }
		// if(isContains(typeSet,"46")){
		// this.exeStatYearBeginTestTimes(dateStr);//年开始测验人次
		// }
		// if(isContains(typeSet,"47")){
		// this.exeStatYearComTestTimes(dateStr);//年完成测验人次
		// }
		// if(isContains(typeSet,"48")){
		// this.exeStatYearRefundNum(dateStr);//年退费人数
		// }
		// if(isContains(typeSet,"49")){
		// this.exeStatYearRefundTimes(dateStr);//年退费人次
		// }
		// if(isContains(typeSet,"50")){
		// this.exeStatYearRefundHours(dateStr);//年退费学时数
		// }
		// if(isContains(typeSet,"51")){
		// this.exeStatYearRefundAmount(dateStr);//年退费金额
		// }
		// if(isContains(typeSet,"52")){
		// this.exeStatYearOverYearRefundNum(dateStr);//年跨年退费人数
		// }
		// if(isContains(typeSet,"53")){
		// this.exeStatYearOverYearRefundTimes(dateStr);//年跨年退费人次
		// }
		// if(isContains(typeSet,"54")){
		// this.exeStatYearOverYearRefundHours(dateStr);//年跨年退费学时数
		// }
		// if(isContains(typeSet,"55")){
		// this.exeStatYearOverYearRefundAmount(dateStr);//年跨年退费金额
		// }
		// if(isContains(typeSet,"56")){
		// this.exeStatYearComTenHours(dateStr);//年完成10学时人数
		// }
		// if(isContains(typeSet,"57")){
		// this.exeStatYearComFifteenHours(dateStr);//年完成15学时人数
		// }
		// if(isContains(typeSet,"58")){
		// this.exeStatYearEntryNumGrowth(dateStr);//年报名人数同比
		// }
		// if(isContains(typeSet,"59")){
		// this.exeStatYearEntryTimesGrowth(dateStr);//年报名人次同比
		// }
		// if(isContains(typeSet,"60")){
		// this.exeStatYearEntryHoursGrowth(dateStr);//年报名学时数同比
		// }
		// //System.out.println("+++++++++更新statistic_year表完毕+++++++++");
		// /*-------------------更新statistic_year结束-----------------------*/
		//		
		//		
		// //System.out.println("****************************课程统计数据测试*************************************");
		// if(isContains(typeSet,"61")){
		// this.exeStatCourseTableInsert(dateStr);//插入course表
		// }
		// if(isContains(typeSet,"62")){
		// this.exeStatCourseTableUpdate(dateStr);//更新course表
		// }
		// if(isContains(typeSet,"63")){
		// this.exeStatAcrossInsert(dateStr);//插入跨年表
		// }
		// if(isContains(typeSet,"64")){
		// this.exeStatAcrossUpdate(dateStr);//更新跨年表
		// }
		// //System.out.println("****************************课程统计测试完毕***************************************************");
		// //System.out.println("****************************集体报名概况统计开始***************************************************");
		//		
		// if(isContains(typeSet,"65")){
		// this.exeInitTeamEntry(dateStr);
		// }
		// if(isContains(typeSet,"66")){
		// this.exeStatTeamEntry(dateStr);//更新集体报名概况
		// }
		// //System.out.println("****************************集体报名概况统计完毕***************************************************");
		//		
	}

	public boolean check(PeBzzStudent peBzzStudent) throws EntityException {
		// 审核通过
		EnumConst enumConstByFlagRecordAssignMethod = this.getEnumConstService().getByNamespaceCode("FlagRecordAssignMethod", "0"); // 学时剥离记录
		try {
			/* 剥离学生学时开始 */
			DetachedCriteria stuSsodc = DetachedCriteria.forClass(SsoUser.class);
			stuSsodc.add(Restrictions.eq("loginId", peBzzStudent.getRegNo()).ignoreCase());
			List suList = this.getGeneralDao().getList(stuSsodc);
			SsoUser ssoUser = new SsoUser();
			if (suList != null && suList.size() > 0) {
				ssoUser = (SsoUser) suList.get(0);
				/* 计算剩余学时开始 */
				// 总充值金额
				String sumAmount = (ssoUser.getSumAmount() == null || ssoUser.getSumAmount().equals("")) ? "0.0" : ssoUser.getSumAmount();

				// 总消费金额
				String amount = (ssoUser.getAmount() == null || ssoUser.getAmount().equals("")) ? "0.0" : ssoUser.getAmount();

				BigDecimal bdSumAmount = new BigDecimal(sumAmount);
				BigDecimal bdAmount = new BigDecimal(amount);
				// 余额
				BigDecimal bdSy = bdSumAmount.subtract(bdAmount);
				if (bdSy.compareTo(new BigDecimal(0)) > 0) {
					// 剥离-学员
					EnumConst enumConstByFlagOperateType = this.getEnumConstService().getByNamespaceCode("FlagOperateType", "3");
					/* 把多余的学时添加到机构管理员上开始 */
					DetachedCriteria pemdc = DetachedCriteria.forClass(PeEnterpriseManager.class);
					pemdc.createCriteria("ssoUser", "ssoUser", DetachedCriteria.LEFT_JOIN);
					pemdc.createCriteria("peEnterprise", "peEnterprise");
					pemdc.createCriteria("peEnterprise.peEnterprise", "peEnterprise1", DetachedCriteria.LEFT_JOIN);
					if (peBzzStudent.getPeEnterprise().getPeEnterprise() == null) {// 一级机构的学员
						pemdc.add(Restrictions.and(Restrictions.eq("peEnterprise", peBzzStudent.getPeEnterprise()), Restrictions
								.isNull("peEnterprise.peEnterprise")));
					} else {// 二级机构的学员
						pemdc.add(Restrictions.and(Restrictions.eq("peEnterprise", peBzzStudent.getPeEnterprise().getPeEnterprise()),
								Restrictions.isNull("peEnterprise.peEnterprise")));
					}
					// 集体管理员SSOUSER
					SsoUser ssoUser2 = new SsoUser();
					List pemList = this.getGeneralDao().getList(pemdc);
					PeEnterpriseManager manager = new PeEnterpriseManager();
					if (pemList != null && pemList.size() > 0) {
						manager = (PeEnterpriseManager) pemList.get(0);
						ssoUser2 = manager.getSsoUser();
						/* 把多余的学时添加到机构管理员上结束 */
					} else {// 没有找到管理员，返回出错信息
						return false;
					}

					AssignRecord assignRecord = new AssignRecord();
					assignRecord.setSsoUser(ssoUser2);// 集体管理员
					assignRecord.setAssignStyle("1");// 剥离
					assignRecord.setAssignDate(new Date());// 操作日期
					assignRecord.setEnumConstByFlagRecordAssignMethod(enumConstByFlagRecordAssignMethod);// 剥离
					// 管理员总充值
					String sumAmount1 = (ssoUser2.getSumAmount() == null || ssoUser2.getSumAmount().equalsIgnoreCase("")) ? "0.0"
							: ssoUser2.getSumAmount();
					BigDecimal bdSumAmount1 = new BigDecimal(sumAmount1);
					// 回收学时数=学员余额
					BigDecimal bdRecycle = bdSy;
					// 管理员余额
					String accountAmount = bdSumAmount1.add(bdRecycle).setScale(2, BigDecimal.ROUND_HALF_UP).subtract(
							new BigDecimal(ssoUser2.getAmount())).toString();
					// 操作金额=学员余额
					String operateAmount = bdSy.toString();
					assignRecord.setAccountAmount(accountAmount);// 集体管理员余额
					assignRecord.setOperateAmount(operateAmount);// 操作学时数
					assignRecord.setEnumConstByFlagOperateType(enumConstByFlagOperateType);
					this.getGeneralDao().save(assignRecord);
					if (Double.parseDouble(sumAmount) - Double.parseDouble(bdSy.toString()) - Double.parseDouble(amount) < 0) {
						System.out.println(peBzzStudent.getRegNo() + "学员剥离学时失败，剩余学时数不足!");
						return false;
					}
					ssoUser.setSumAmount(Double.parseDouble(sumAmount) - Double.parseDouble(bdSy.toString()) + "");
					peBzzStudent.setSsoUser(ssoUser);
					AssignRecordStudent ars = new AssignRecordStudent();
					ars.setAssignRecord(assignRecord);
					ars.setStudent(peBzzStudent);
					ars.setClassNum(Double.parseDouble(bdSy.toString()) + "");// 操作(剥离)学时数
					String subAmount = Double.parseDouble(ssoUser.getSumAmount()) - Double.parseDouble(ssoUser.getAmount()) + "";// 学员余额
					ars.setSubAmount(subAmount);
					this.getGeneralDao().save(ars);
					/* 计算剩余学时结束 */
					ssoUser2.setSumAmount(new BigDecimal(ssoUser2.getSumAmount()).add(bdSy).setScale(1, BigDecimal.ROUND_HALF_UP)
							.toString());
					this.getGeneralDao().save(ssoUser2);
					ssoUser.setSumAmount(ssoUser.getAmount());
					ssoUser = (SsoUser) this.getGeneralDao().save(ssoUser);
					/*
					 * 记录剥离学时用-没多大用处 String sql = "INSERT INTO
					 * AAA(EXE_DATE,STU_ID,STU_TIMES,STU_PE_ID,MANAGER_TIMES)
					 * VALUES(SYSDATE,'" + peBzzStudent.getId() + "','" +
					 * bdSy.toString() + "','" +
					 * peBzzStudent.getPeEnterprise().getId() + "','" +
					 * manager.getSsoUser().getSumAmount()+"-" +
					 * manager.getSsoUser().getAmount() + "')";
					 * this.getGeneralDao().executeBySQL(sql);
					 */
					/* 剥离学生学时结束 */
				} else {
					return true;// 没有学时的不执行剥离步骤
				}
			} else {// 没有找到学员账户，返回出错信息
				return false;
			}
		} catch (Exception e) {
			System.out.println("check失败，请检查学员、管理员学时记录是否完整。");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void moveStuEleService(String stuRegNo) {
		List paramList = new ArrayList();
		paramList.add(stuRegNo);
		// 异步队列式执行
		Task.setThreadMaxNum(10);
		new Task() {

			@Override
			public String obtainData(Task task, Object parameter) throws Exception {
				GeneralDao gd = (GeneralDao) SpringUtil.getBean("generalDao");
				gd.executeCall("ADD_REMOTE_ELECTIVE_BYSTU", (List) parameter);
				return "success";
			}

		}.setParameter(paramList).start();

	}
}

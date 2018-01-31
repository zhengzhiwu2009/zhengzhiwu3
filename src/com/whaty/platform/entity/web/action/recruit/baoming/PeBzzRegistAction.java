package com.whaty.platform.entity.web.action.recruit.baoming;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzRecruit;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.PrBzzPriManagerEnterprise;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

public class PeBzzRegistAction extends MyBaseAction<PeBzzRecruit> {

	private String e_id;
	private String select_batch_id;		//所选择的学期

	private PeBzzstudentbacthService peBzzstudentbacthService;

	public PeBzzstudentbacthService getPeBzzstudentbacthService() {
		return peBzzstudentbacthService;
	}

	public void setPeBzzstudentbacthService(
			PeBzzstudentbacthService peBzzstudentbacthService) {
		this.peBzzstudentbacthService = peBzzstudentbacthService;
	}

	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		this.getGridConfig().setCapability(false, false, true);
		this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location.href='/entity/recruit/peRegister.action?backParam=true';}");
//		 this.getGridConfig().setCanBack(true);
//		 this.getGridConfig().setBackUrl(this.getBackUrl(ServletActionContext.getRequest()));

		this.getGridConfig().setTitle("注册学生列表");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("姓名"), "name");
		this.getGridConfig().addColumn(this.getText("性别"), "gender", false,
				true, true, Const.sex_for_extjs);
		this.getGridConfig().addColumn(this.getText("民族"), "folk", false, true,
				false, "TextField", false, 50);
		this.getGridConfig().addColumn(this.getText("学历"), "education", false,
				true, true, Const.edu_for_extjs);
		this.getGridConfig().addColumn(this.getText("出生日期"), "birthdayDate",
				false);
		this.getGridConfig().addColumn(this.getText("手机"), "mobilePhone", true,
				true, true, "TextField", false, 50, "");
		this.getGridConfig().addColumn(this.getText("办公电话"), "phone", false,
				true, false, "TextField", true, 50,
				Const.phone_number_for_extjs);
		this.getGridConfig().addColumn(this.getText("电子邮件"), "email", false,
				true, false, "TextField", true, 150, Const.email_for_extjs);

		this.getGridConfig().addColumn(this.getText("所在学期"), "peBzzBatch.name",
				false, false, true, "");

		this.getGridConfig().addColumn(this.getText("操作时间"), "luruDate");

		this.getGridConfig().addColumn(this.getText("注册状态"),
				"enumConstByFlagRegistState.name");

		this.getGridConfig().addColumn(this.getText("所在企业"),
				"peEnterprise.name");

		this.getGridConfig().addColumn(this.getText("所在集团"),
				"peEnterprise.peEnterprise.name");

		if (us.getUserLoginType().equals("3")) {
			this.getGridConfig().addMenuFunction(this.getText("注册"),
					"FlagRegistState.true");

			this.getGridConfig().addMenuFunction(this.getText("取消注册"),
					"FlagRegistState.false");
			
			this.getGridConfig().addMenuScript(this.getText("导出制卡"),"{window.location='/entity/recruit/peRegister_exportZk.action?eId="+this.getE_id()+"&select_batch_id="+this.getSelect_batch_id()+"';}");
		}
		// this.getGridConfig().addMenuScript(this.getText("返回"),
		// "{window.location='/entity/recruit/peRegister.action'}");
	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/recruit/peBzzRegist";
	}

	public PeBzzRecruit getBean() {
		return (PeBzzRecruit) super.superGetBean();
	}

	public void setBean(PeBzzRecruit peBzzRecruit) {
		super.superSetBean(peBzzRecruit);
	}

	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRecruit.class);
		dc.createCriteria("peEnterprise", "peEnterprise");
		dc.createCriteria("peEnterprise.peEnterprise",
				DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peBzzBatch", "peBzzBatch");
		dc.createCriteria("enumConstByFlagRegistState","enumConstByFlagRegistState",
				DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("peEnterprise.id", this.getE_id()));
		//增加学习处理
		if(null != this.getSelect_batch_id() && !"".equals(this.getSelect_batch_id())){
			dc.add(Restrictions.eq("peBzzBatch.id", this.getSelect_batch_id()));
		}
		return dc;
	}

	public Map updateColumn() {
		Map map = new HashMap();
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		int countSucc = 0;
		int countFail = 0;
		try {
			String action = this.getColumn();
			List<String> idList = Arrays.asList(this.getIds().split(","));
			
			String ids =this.getIds();
			ids = ids.replace(",", "','");
			if(ids.endsWith(",'")) {
				ids = ids.substring(0, ids.length() - 2);
			} else if(!ids.endsWith("'")) {
				ids += "'";
			}
			ids = "'" + ids;

			if (action.equals("FlagRegistState.true")) {
				
				//查询所有要注册的报名信息
				DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRecruit.class);
				dc.createCriteria("peBzzBatch");
				dc.createCriteria("peEnterprise");
				dc.createCriteria("enumConstByFlagRegistState","registState",DetachedCriteria.LEFT_JOIN);
				dc.add(Restrictions.or(
						Restrictions.and(Restrictions.eq("registState.namespace", "FlagRegistState"), Restrictions.eq("registState.name", "未注册")), 
						Restrictions.isNull("enumConstByFlagRegistState")));
				dc.add(Restrictions.in("id", idList));
				
				List<PeBzzRecruit> list = this.getGeneralService().getList(dc);
				int i = 0;
				
				dc = DetachedCriteria.forClass(EnumConst.class);
				dc.add(Restrictions.eq("namespace", "FlagRankState"));
				dc.add(Restrictions.eq("isDefault", "1"));
				EnumConst rankEc = (EnumConst) this.getGeneralService().getList(dc).get(0);
				
				//保存学员，生成学号及密码
				String recId = "";
				for(PeBzzRecruit rec : list) {
					PeBzzStudent stu = this.recruitToStudent(rec, i);
					if(stu != null) {
						stu.setEnumConstByFlagRankState(rankEc);
						this.getPeBzzstudentbacthService().save(stu);	
					}
					recId += "'" + rec.getId() + "',";
					i++;
				}
				
				//更新报名信息为"已注册"
				if(recId.length() > 0) {
					recId = recId.substring(0, recId.length()-1);
				} else {
					recId = "'noreceds'";
				}
				
				dc = DetachedCriteria.forClass(EnumConst.class);
				dc.add(Restrictions.eq("namespace", "FlagRegistState"));
				dc.add(Restrictions.eq("name", "已注册"));
				EnumConst ec = (EnumConst)this.getGeneralService().getList(dc).get(0);
				
				String sql = "update pe_bzz_recruit r " +
						"set r.flag_regist_state='" + ec.getId() + "'" +
						"where r.id in (" + recId + ")";
				countSucc = this.getGeneralService().executeBySQL(sql);
				
				String enterpriseManagerInfo = "";
				if(countSucc > 10){	//为注册人数超过10人的企业自动创建管理员
					String loginId = this.createEnterpriseManager();
					if(null != loginId && !"".equals(loginId)){
						enterpriseManagerInfo = "自动生成管理员账号及密码为：" + loginId + "<br/>";
					}
				}
				
				countFail = idList.size() - list.size();
				
				String info = this.getText(String.valueOf(countSucc) + "条记录注册成功 <br />" + enterpriseManagerInfo);
				if(countFail > 0) {
					info += this.getText(String.valueOf(countFail) + "条记录已注册过<br />"
								+ "共" + String.valueOf(idList.size()) + "条记录");
				}
				map.put("info", info);
				
			} else if (action.equals("FlagRegistState.false")) {
				
				//查询所有已选课学生id,已选课学生不能删除
				DetachedCriteria criteria = DetachedCriteria.forClass(PrBzzTchStuElective.class);
				criteria.createCriteria("peBzzStudent", "peBzzStudent");
				criteria.createCriteria("peBzzStudent.peBzzRecruit", "sturec");
				criteria.setProjection(Projections.distinct(Property.forName("peBzzStudent.id")));
				criteria.add(Restrictions.in("sturec.id", idList));
				
				List ctDelStuIds = this.getGeneralService().getList(criteria);
				
				//查询所有可以删除的学生id
				criteria = DetachedCriteria.forClass(PeBzzStudent.class);
				criteria.createCriteria("peBzzRecruit");
				criteria.setProjection(Projections.projectionList().add(Property.forName("id")).add(Property.forName("peBzzRecruit.id")));
				criteria.add(Restrictions.in("peBzzRecruit.id", idList));
				if(ctDelStuIds.size() > 0) {
					criteria.add(Restrictions.not(Restrictions.in("id", ctDelStuIds)));
				}
				
				List<String> delStuIds = new ArrayList<String>();
				List<Object[]> tempList = this.getGeneralService().getList(criteria);
				ids = "";
				for(Object[] temp : tempList) {
					if(temp.length > 0 && temp[0] != null) {
						delStuIds.add(temp[0].toString());
					}
					if(temp.length > 1 && temp[1]!=null) {
						ids += "'" + temp[1].toString() + "',";
					}
				}
				if(ids.length() > 0) {
					ids = ids.substring(0, ids.length() - 1);
				} else {
					ids = "'norecruitinfo'";
				}
				
				//删除所有可删除的数据
				countSucc = this.getPeBzzstudentbacthService().deleteByIds(delStuIds);
				
				
				//更新报名信息为"未注册"
				criteria = DetachedCriteria.forClass(EnumConst.class);
				criteria.add(Restrictions.eq("namespace", "FlagRegistState"));
				criteria.add(Restrictions.eq("name", "未注册"));
				
				EnumConst ec = (EnumConst)this.getGeneralService().getList(criteria).get(0);
				

				String sql = "update pe_bzz_recruit r " +
					"set r.flag_regist_state='" + ec.getId() + "'" +
					"where r.id in (" + ids + ")";				
				this.getGeneralService().executeBySQL(sql);
				
				countFail = ctDelStuIds.size();
				
				String info = this.getText(String.valueOf(countSucc) + "条记录取消注册成功<br />" );
				if(countFail > 0) {
					info += this.getText(String.valueOf(countFail) + "条记录取消注册失败，用户已选课，不能取消注册<br />"
						+ "共" + String.valueOf(idList.size()) + "条记录");
				}
				map.put("info", info);
			}

		} catch (EntityException e) {
			e.printStackTrace();
			map.clear();
			map.put("success", "false");
			map.put("info", "操作失败");
			return map;
		}
		map.put("success", "true");

		return map;
	}

	private PeBzzStudent recruitToStudent(PeBzzRecruit recruit, int i)
			throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		if (us == null) {
			throw new EntityException("无操作权限");
		}
		
		//避免重复小注册
		PeBzzStudent student = new PeBzzStudent();
		
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("peBzzRecruit");
		dc.add(Restrictions.eq("peBzzRecruit.id", recruit.getId()));
		
		List list = this.getGeneralService().getList(dc);
		
		if(list.size() > 0) {
			return null;
		}
			
		//信息复制
		student.setAddress(recruit.getAddress());
		student.setAge(recruit.getAge());
		student.setBirthdayDate(recruit.getBirthdayDate());
		student.setDepartment(recruit.getDepartment());
		student.setEducation(recruit.getEducation());
		student.setEmail(recruit.getEmail());
		student.setFolk(recruit.getFolk());
		student.setGender(recruit.getGender());
		student.setMobilePhone(recruit.getMobilePhone());
		
		student.setPeBzzBatch(recruit.getPeBzzBatch());
		student.setPeBzzRecruit(recruit);
		student.setPeEnterprise(recruit.getPeEnterprise());
		
		student.setPhone(recruit.getPhone());
		student.setPosition(recruit.getPosition());
		student.setRegistDate(this.getCurSqlDate());
		student.setRegistPeople(us.getLoginId());
		student.setTitle(recruit.getTitle());
		student.setTrueName(recruit.getName());
		student.setZipcode(recruit.getZipcode());
		student.setEnumConstByFlagPhotoConfirm(getDefaultFlagPhotoConfirm());
		return student;
	}
	private EnumConst getDefaultFlagPhotoConfirm() {
		EnumConst ec = new EnumConst();
		ec.setId("4028809c2d925bcf011d66fd0dda8006");
		return ec;
	}

	public String getE_id() {
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (e_id == null) {
			e_id = (String) session.getAttribute("e_id");
		}
		return e_id;
	}

	public void setE_id(String e_id) {
		this.e_id = e_id;
		HttpSession session = ServletActionContext.getRequest().getSession();

		session.setAttribute("e_id", e_id);
	}


	private java.sql.Date getCurSqlDate() {
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

	/**
	 * 为注册人数超过10人的企业创建企业管理员
	 * @return 管理员账号
	 */
	public String createEnterpriseManager(){
		String loginId = "";
		if(null != this.getE_id() && !"".equals(this.getE_id())){
			DetachedCriteria dc = null;
			dc = DetachedCriteria.forClass(PeEnterprise.class);		//获取企业信息
			dc.add(Restrictions.eq("id", this.e_id));
			PeEnterprise peEnterprise = null;
			try {
				List<PeEnterprise> peEnterpriseList = this.getGeneralService().getList(dc);
				if(null != peEnterpriseList && peEnterpriseList.size() > 0){
					peEnterprise = peEnterpriseList.get(0);	//企业
					//创建企业管理员
					loginId = getLoginId(peEnterprise.getCode());	//登录账号
					//SsoUser start
					//保存ssoUser信息
					SsoUser ssoUser = new SsoUser();	//SsoUser
					ssoUser.setLoginNum(0L);	//登录次数
					ssoUser.setLoginId(loginId);	//登录账号
					ssoUser.setPassword(loginId);	//密码
					ssoUser.setPasswordMd5(MyUtil.md5(loginId));	//加密后密码
					
					DetachedCriteria dcd = DetachedCriteria.forClass(PePriRole.class);
					dcd.add(Restrictions.eq("id", "402880f322736b760122737a60c40008"));
					PePriRole pePriRole = (PePriRole) this.getGeneralService().getList(dcd).get(0);
					ssoUser.setPePriRole(pePriRole);	//设置权限组
					
					DetachedCriteria dcd2 = DetachedCriteria.forClass(EnumConst.class);
					dcd2.add(Restrictions.eq("id", "2"));
					EnumConst enumConstByFlagIsvalid = (EnumConst) this.getGeneralService().getList(dcd2).get(0);
					ssoUser.setEnumConstByFlagIsvalid(enumConstByFlagIsvalid);	//设为有效
					List<SsoUser> ssoUserList = new ArrayList<SsoUser>();
					ssoUserList.add(ssoUser);
					SsoUser ssoUserInstance = (SsoUser) this.getGeneralService().saveList(ssoUserList).get(0);
//					System.out.println("创建SsoUser信息成功=======================");
					//SsoUser end
					
					//PeEnterpriseManager start
					//保存企业管理员信息
					PeEnterpriseManager peEnterpriseManager = new PeEnterpriseManager();
					String name = (null == peEnterprise.getLxrName() || "".equals(peEnterprise.getLxrName())) ? loginId : peEnterprise.getLxrName();	//姓名
					peEnterpriseManager.setName(name);	//管理员姓名
					peEnterpriseManager.setLoginId(loginId);	//管理员账号
					peEnterpriseManager.setSsoUser(ssoUserInstance);	//设置SsoUser
					peEnterpriseManager.setPeEnterprise(peEnterprise);	//设置企业
					String gender = peEnterprise.getLxrXb();
					if("男".equals(gender) || "女".equals(gender)){
						DetachedCriteria dcd3 = DetachedCriteria.forClass(EnumConst.class);
						dcd3.add(Restrictions.eq("namespace", "Gender"));
						dcd3.add(Restrictions.eq("name", gender));
						EnumConst enumConstByGender = (EnumConst) this.getGeneralService().getList(dcd3).get(0);
						peEnterpriseManager.setEnumConstByGender(enumConstByGender);	//设置性别
					}
					peEnterpriseManager.setEnumConstByFlagIsvalid(enumConstByFlagIsvalid);	//设置为有效
					peEnterpriseManager.setPosition(peEnterprise.getLxrPosition());	//设置职务
					peEnterpriseManager.setPhone(peEnterprise.getLxrPhone());		//设置电话
					peEnterpriseManager.setMobilePhone(peEnterprise.getLxrMobile());	//设置手机
					peEnterpriseManager.setEmail(peEnterprise.getLxrEmail());		//设置邮箱
					List<PeEnterpriseManager> peEnterpriseManagerList = new ArrayList<PeEnterpriseManager>();
					peEnterpriseManagerList.add(peEnterpriseManager);
					PeEnterpriseManager peEnterpriseManagerInstance = (PeEnterpriseManager) this.getGeneralService().saveList(peEnterpriseManagerList).get(0);
//					System.out.println("创建管理员PeEnterpriseManager成功===================");
					//PeEnterpriseManager end
					
					//设置企业管理员默认的管理范围为其所在企业
					PrBzzPriManagerEnterprise managerSite = new PrBzzPriManagerEnterprise();
					managerSite.setPeEnterprise(peEnterprise);
					managerSite.setSsoUser(ssoUserInstance);
					List<PrBzzPriManagerEnterprise> prBzzPriManagerEnterpriseList = new ArrayList<PrBzzPriManagerEnterprise>();
					prBzzPriManagerEnterpriseList.add(managerSite);
					this.getGeneralService().saveList(prBzzPriManagerEnterpriseList);
//					System.out.println("设置管理范围成功PrBzzPriManagerEnterprise=====================");
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return loginId;
	}
	
	/**
	 * 获取要创建管理员应该分配的登录账号
	 * @param entpriseCode	企业编号
	 * @return 登录账号=企业编号+2位流水号
	 */
	public String getLoginId(String entpriseCode){
		String loginId = entpriseCode + "01";
		String sql = "select su.login_id from sso_user su where su.login_id like '"+entpriseCode+"%' and length(su.login_id)=8 order by su.login_id desc";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if(null != list && list.size() > 0){	//该企业下已经有管理员
				String loginIdInDb = (String) list.get(0);	//获取最大的管理员登录账号
				loginId = setLoginId(loginIdInDb, entpriseCode);	//获取管理员登录账号
			}else{		//该企业下还没有管理员
				loginId = entpriseCode + "01";
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginId;
	}
	
	/**
	 * 根据数据库中的登录账号确定当前管理员登录账号
	 * @param loginIdInDb	数据库中存在的最大账号
	 * @param entpriseCode	企业编号
	 * @return
	 */
	public String setLoginId(String loginIdInDb, String entpriseCode){
		String loginId = entpriseCode + "01";
		int i = Integer.parseInt(loginIdInDb.substring(loginIdInDb.length() - 2)) + 1;	//获取最后两位的值，并转化成整数，然后加1
		if(i < 10){
			loginId = entpriseCode + "0" + i;
		}else{
			loginId = entpriseCode + i;
		}
		return loginId;
	}
}

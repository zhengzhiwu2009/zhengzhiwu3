package com.whaty.platform.entity.web.action.email;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.SiteEmail;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

public class EditEmailAction extends MyBaseAction {
	private List studentList;
	private String start; // 列表开始的位置
	private String limit; // 每页要显示的信息个数
	private int curPage; // 当前页
	private int nextPage; // 下一页
	private int prePage; // 上一页
	private int totalPage; // 总页数
	private int pageNo; // 从输入框中获取的页码数
	private int isLimit;
	private String[] idss;
	private String logId;
	private String sendIds;
	private String content;
	private String title;
	private String flag;
	private String errorid;
	private String reid;
	private String sendId;
	private String sendName;
	private List<PePriRole> roleTypeList;// 账号类别List
	private String roleType;
	private List<PeEnterprise> peEnterpriseList;// 企业List
	private List<EnumConst> zgList;// 企业List
	private String enterpriseId;
	private String zgId;
	private String etId;
	private List<EnumConst> etList;// 机构类别

	public String getEtId() {
		return etId;
	}

	public void setEtId(String etId) {
		this.etId = etId;
	}

	public List<EnumConst> getEtList() {
		return etList;
	}

	public void setEtList(List<EnumConst> etList) {
		this.etList = etList;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getZgId() {
		return zgId;
	}

	public void setZgId(String zgId) {
		this.zgId = zgId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public List<PeEnterprise> getPeEnterpriseList() {
		return peEnterpriseList;
	}

	public void setPeEnterpriseList(List<PeEnterprise> peEnterpriseList) {
		this.peEnterpriseList = peEnterpriseList;
	}

	public List<PePriRole> getRoleTypeList() {
		return roleTypeList;
	}

	public void setRoleTypeList(List<PePriRole> roleTypeList) {
		this.roleTypeList = roleTypeList;
	}

	public String getSendId() {
		return sendId;
	}

	public void setSendId(String sendId) {
		this.sendId = sendId;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public void setReid(String reid) {
		this.reid = reid;
	}

	public String getErrorid() {
		return errorid;
	}

	public void setErrorid(String errorid) {
		this.errorid = errorid;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSendIds() {
		return sendIds;
	}

	public void setSendIds(String sendIds) {
		this.sendIds = sendIds;
	}

	public String[] getIdss() {
		return idss;
	}

	public void setIdss(String[] idss) {
		this.idss = idss;
	}

	public void setIsLimit(int isLimit) {
		this.isLimit = isLimit;
	}

	public List getStudentList() {
		return studentList;
	}

	public void setStudentList(List studentList) {
		this.studentList = studentList;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub

	}

	public String index() {
		return "index";
	}

	/**
	 * 账号类别List
	 */
	public void initRoleTypeList() {

		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String codes = "'0', '4', '2', '3'";
		if (us.getUserLoginType().equals("4")) {
			codes = "'0', '4'";
		} else if (us.getUserLoginType().equals("2")) {
			codes = "'0', '4', '2'";
		}

		String sql = "select t.id, t.name from pe_pri_role t\n" + "inner join enum_const ec on t.flag_role_type = ec.id\n"
				+ "where ec.code in (" + codes + ") order by t.id desc";

		try {
			this.roleTypeList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册页机构列表List
	 */
	public void initPeEnterpriseList() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String eps = "";
		String sql = "";
		if (us.getUserLoginType().equalsIgnoreCase("2") || us.getUserLoginType().equalsIgnoreCase("4")) { // 一级集体管理员

			if (this.enterpriseId == null || "".equals(this.enterpriseId)) {
				String enSql = "select pe.id\n" + "  from pe_enterprise pe\n"
						+ " inner join pe_enterprise_manager pem on pem.fk_enterprise_id =\n"
						+ "                                         pe.id\n" + " where pem.login_id = '" + us.getLoginId() + "'\n" + "\n"
						+ "union\n" + "\n" + "select pe.id\n" + "  from pe_enterprise         pe,\n"
						+ "       pe_enterprise         pePar,\n" + "       pe_enterprise_manager pem\n"
						+ " where pe.fk_parent_id = pePar.Id\n" + "   and pePar.Id = pem.fk_enterprise_id\n" + "   and pem.login_id = '"
						+ us.getLoginId() + "'";

				List list = null;
				try {
					list = this.getGeneralService().getBySQL(enSql);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i < list.size(); i++) {
					eps += "'" + list.get(i).toString() + "',";
				}
				if (list.size() > 0) {
					eps = eps.substring(0, eps.length() - 1);
				}
			}
			sql = "select t.id,t.name from pe_enterprise t  where t.id in (" + eps + ") order by t.name";
		} else {
			sql = "select t.id,t.name from pe_enterprise t  order by t.name";
		}
		try {
			this.peEnterpriseList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 资格类型列表
	 */
	public void initZgList() {
		String sql = "select * from enum_const t  where t.namespace='FlagQualificationsType' and t.code not in ('9','90','91') order by t.code ";
		try {
			this.zgList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 机构类别列表
	 */
	public void initETList() {
		String sql = "select * from enum_const t  where t.namespace='FlagEnterpriseType'  order by t.id ";
		try {
			this.etList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	public String addStudent() {
		String enterpriseIdBack = this.enterpriseId;
		this.enterpriseId = "";
		this.initRoleTypeList();
		this.initPeEnterpriseList();
		this.initZgList();
		this.initETList();
		ActionContext axt = ActionContext.getContext();

		if (this.getIsLimit() == 1) {
			this.setStart("0");
			this.setCurPage(1);

			/**
			 * 将limit放到session里
			 */
			axt.getSession().put("limit", this.getLimit());
		}
		if (axt.getSession().get("limit") != null) {
			this.setLimit((String) axt.getSession().get("limit"));
		} else {
			this.setLimit(super.getLimit());
		}

		/**
		 * 由输入框中输入页码后点击提交进行页面跳转（所跳转页面的合法性校验在页面进行）
		 */
		if (this.getPageNo() != 0) {
			this.setCurPage(this.getPageNo());
		}

		/**
		 * 首先设定默认开始页为第一页
		 */
		if (this.getCurPage() == 0) {
			this.setCurPage(1);
		}
		/**
		 * 设定start
		 */
		if (this.getCurPage() > 1) {
			Integer temp = new Integer(0);
			temp = (this.getCurPage() - 1) * Integer.parseInt(this.getLimit());
			this.setStart(temp.toString());
		} else
			this.setStart("0");
		Page page = null;
		UserSession us = (UserSession) axt.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (us.getUserLoginType().equalsIgnoreCase("2") || us.getUserLoginType().equalsIgnoreCase("4")
				|| us.getUserLoginType().equalsIgnoreCase("5") || us.getUserLoginType().equalsIgnoreCase("6")) { // 一级(监管)集体管理员
			this.enterpriseId = enterpriseIdBack;
			if (this.enterpriseId == null || "".equals(this.enterpriseId)) {
				String enSql = "select pe.id\n" + "  from pe_enterprise pe\n"
						+ " inner join pe_enterprise_manager pem on pem.fk_enterprise_id =\n"
						+ "                                         pe.id\n" + " where pem.login_id = '" + us.getLoginId() + "'\n" + "\n"
						+ "union\n" + "\n" + "select pe.id\n" + "  from pe_enterprise         pe,\n"
						+ "       pe_enterprise         pePar,\n" + "       pe_enterprise_manager pem\n"
						+ " where pe.fk_parent_id = pePar.Id\n" + "   and pePar.Id = pem.fk_enterprise_id\n" + "   and pem.login_id = '"
						+ us.getLoginId() + "'";

				List list = null;
				try {
					list = this.getGeneralService().getBySQL(enSql);
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				this.enterpriseId = "";
				for (int i = 0; i < list.size(); i++) {
					this.enterpriseId += "'" + list.get(i).toString() + "',";
				}
				if (list.size() > 0) {
					this.enterpriseId = this.enterpriseId.substring(0, this.enterpriseId.length() - 1);
				}
			} else {
				this.enterpriseId = "'" + this.enterpriseId + "'";
			}

		} else {
			this.enterpriseId = enterpriseIdBack;
			if (this.enterpriseId != null && !"".equals(this.enterpriseId)) {
				this.enterpriseId = "'" + this.enterpriseId + "'";
			}
		}

		/**
		 * 收件人列表sql 1按当前登陆人分三类； 2结果集包括：协会管理员； 集体管理员--1、2级； 学员：一级组织机构下 和 二级组织机构下；
		 * 3发信人只能为同级或低一级； 4if为搜索条件
		 */
		StringBuffer sql = new StringBuffer();
		sql.append("select info.login_id,info.name, ");
		sql.append("        info.enterpriseName,          ");
		sql.append("        info.enType,             "); // 机构类别
		sql.append("        info.roleType,                "); // 账号类别
		sql.append("        info.typeName	             "); // 资格类型

		sql.append(" from (");
		if (this.getEtId() != null && !"".equals(this.getEtId())) {// 机构类别不为空
			if (us.getUserLoginType().equalsIgnoreCase("2") || us.getUserLoginType().equalsIgnoreCase("5")) {// 一、当前登陆人为：一级(监管)集体管理员
				/* 1查询：集体管理员 */
				sql.append("select login_id, true_name as name ,enterpriseName,enType, roleType, typeName from (");
				/* 集体一级 */
				if ("".equals(this.getZgId()) || this.getZgId() == null) {
					sql
							.append(" select pem.login_id, pem.name as true_name,pe.name as enterpriseName, en.name as enType,pr.name as roleType, '' as typeName	");
					sql.append("   from pe_enterprise_manager pem, sso_user u, pe_pri_role pr, pe_enterprise pe	,enum_const  en");
					sql
							.append(" where 1=1 and pem.fk_sso_user_id=u.id(+) and u.fk_role_id=pr.id and pem.fk_enterprise_id=pe.id and pe.entype=en.id");
					if (getSendIds() != null && !("".equals(getSendIds()))) {
						sql.append(" and pem.login_id not in('" + getSendIds().replace(";", "','") + "')");
					}
					if (this.getEnterpriseId() != null && !"".equals(this.getEnterpriseId())) {
						sql.append(" and pem.fk_enterprise_id in (" + this.getEnterpriseId() + ")");
					}
					if (this.getEtId() != null && !"".equals(this.getEtId())) {
						sql.append(" and pe.entype ='" + this.getEtId() + "'");
					}
					sql.append(" union all ");
				}
				// sql.append(" and pem.fk_enterprise_id =
				// '"+this.getEnterpriseId()+"'");
				/* 集体二级 */
				// if("".equals(this.getZgId()) ||this.getZgId()==null){
				// sql.append(" select pem.login_id, pem.name as name ,pe.name
				// as enterpriseName, pr.name as roleType,'' as typeName ");
				// sql.append(" from pe_enterprise_manager pem1 inner join
				// pe_enterprise pe on pem1.fk_enterprise_id=pe.fk_parent_id");
				// sql.append(" inner join pe_enterprise_manager pem on
				// pem.fk_enterprise_id=pe.id ");
				// sql.append(" inner join sso_user u on pem.fk_sso_user_id=u.id
				// ");
				// sql.append(" inner join pe_pri_role pr on u.fk_role_id=pr.id
				// ");
				// if(getSendIds()!=null&&!("".equals(getSendIds()))){
				// sql.append(" where pem1.login_id not
				// in('"+getSendIds().replace(";", "','")+"')");
				// }
				// if(this.getEnterpriseId()!=null&&!"".equals(this.getEnterpriseId())){
				// sql.append(" and pem1.fk_enterprise_id in
				// ("+this.getEnterpriseId()+")");
				// }
				// sql.append(" union");
				// }
				/* 2查询：机构学员 */
				sql
						.append(" select distinct(pbs.reg_no) as login_id, pbs.true_name, pe.name as enterpriseName,en.name as enType, pr.name as roleType, ec.name as typeName ");
				sql.append(" from pe_bzz_student pbs, enum_const ec, sso_user u, pe_pri_role pr, pe_enterprise pe ,enum_const  en");
				sql
						.append(" where pbs.zige = ec.id(+) and pbs.fk_sso_user_id = u.id and u.fk_role_id = pr.id and pbs.fk_enterprise_id=pe.id and pe.entype=en.id");
				if (this.getEnterpriseId() != null && !"".equals(this.getEnterpriseId())) {
					sql.append(" and pe.id in (" + this.getEnterpriseId() + ")");
				}
				if (getSendIds() != null && !("".equals(getSendIds()))) {
					sql.append(" and pbs.reg_no not in('" + getSendIds().replace(";", "','") + "')");
				}
				if (this.getZgId() != null && !"".equals(this.getZgId())) {
					if ("4028808c1dbe60fe011dbe7866590093".equals(this.getZgId())) {
						sql.append(" and (pbs.zige = '" + this.getZgId() + "' or pbs.zige is null )");
					} else {

						sql.append(" and pbs.zige = '" + this.getZgId() + "'");
					}
				}
				if (this.getEtId() != null && !"".equals(this.getEtId())) {
					sql.append(" and pe.entype ='" + this.getEtId() + "'");
				}
				sql.append(" ) t order by t.login_id");
			} else if (us.getUserLoginType().equalsIgnoreCase("3")) { // 二、当前登陆人为：协会管理员
				/* 1查询：协会管理员 */
				sql.append("select login_id, true_name as name ,enterpriseName,enType, roleType, typeName from (");
				if ((this.getEnterpriseId() == null || "".equals(this.getEnterpriseId()))) {
					sql
							.append(" select distinct(pm.login_id), pm.true_name ,''as enterpriseName,''as enType ,pr.name as roleType,'' as typeName                ");
					sql.append("   from pe_manager pm, sso_user u, pe_pri_role pr		");
					sql.append("  where pm.FK_SSO_USER_ID=u.id(+) and u.fk_role_id=pr.id and pm.login_id not in('" + us.getLoginId());
					if (getSendIds() != null && !("".equals(getSendIds()))) {
						sql.append("','" + getSendIds().replace(";", "','") + "')");
					} else {
						sql.append("')");
					}
					if (this.getZgId() != null && !"".equals(this.getZgId())) {
						sql.append(" and pm.login_id is null ");
					}
					// if(this.getEtId()!=null&&!"".equals(this.getEtId())){
					// sql.append(" and pe.entype ='"+this.getEtId()+"'");
					// }
					sql.append(" union	all						");
				}
				/* 1查询：集体管理员 */
				/* 集体一级 */
				if ("".equals(this.getZgId()) || this.getZgId() == null) {
					sql
							.append(" select pem.login_id, pem.name as true_name,pe.name as enterpriseName,en.name as enType, pr.name as roleType, '' as typeName	");
					sql.append("   from pe_enterprise_manager pem, sso_user u, pe_pri_role pr, pe_enterprise pe	,enum_const  en");
					sql
							.append(" where 1=1 and pem.fk_sso_user_id=u.id(+) and u.fk_role_id=pr.id and pem.fk_enterprise_id=pe.id and pe.entype=en.id");
					if (getSendIds() != null && !("".equals(getSendIds()))) {
						sql.append(" and pem.login_id not in('" + getSendIds().replace(";", "','") + "')");
					}
					if (this.getEnterpriseId() != null && !"".equals(this.getEnterpriseId())) {
						sql.append(" and pem.fk_enterprise_id in (" + this.getEnterpriseId() + ")");
					}
					if (this.getEtId() != null && !"".equals(this.getEtId())) {
						sql.append(" and pe.entype ='" + this.getEtId() + "'");
					}
					sql.append(" union all ");
				}
				/* 集体二级 */
				// if("".equals(this.getZgId()) ||this.getZgId()==null){
				// sql.append(" select pem.login_id, pem.name as name ,pe.name
				// as enterpriseName, pr.name as roleType,'' as typeName ");
				// sql.append(" from pe_enterprise_manager pem1 inner join
				// pe_enterprise pe on pem1.fk_enterprise_id=pe.fk_parent_id");
				// sql.append(" inner join pe_enterprise_manager pem on
				// pem.fk_enterprise_id=pe.id ");
				// sql.append(" inner join sso_user u on pem.fk_sso_user_id=u.id
				// ");
				// sql.append(" inner join pe_pri_role pr on u.fk_role_id=pr.id
				// ");
				// if(getSendIds()!=null&&!("".equals(getSendIds()))){
				// sql.append(" where pem1.login_id not
				// in('"+getSendIds().replace(";", "','")+"')");
				// }
				// if(this.getEnterpriseId()!=null&&!"".equals(this.getEnterpriseId())){
				// sql.append(" and pem1.fk_enterprise_id =
				// ("+this.getEnterpriseId()+")");
				// }
				// sql.append(" union");
				// }
				/* 2查询：学员 */
				sql
						.append(" select pbs.reg_no as login_id, pbs.true_name, pe.name as enterpriseName,en.name as enType, pr.name as roleType, ec.name as typeName ");
				sql.append(" from pe_bzz_student pbs, enum_const ec, sso_user u, pe_pri_role pr, pe_enterprise pe ,enum_const  en");
				sql
						.append(" where pbs.zige = ec.id(+) and pbs.fk_sso_user_id = u.id and u.fk_role_id = pr.id and pbs.fk_enterprise_id=pe.id and pe.entype=en.id");
				if (this.getEnterpriseId() != null && !"".equals(this.getEnterpriseId())) {
					sql.append(" and pe.id = " + this.getEnterpriseId() + "");
				}
				if (this.getEtId() != null && !"".equals(this.getEtId())) {
					sql.append(" and pe.entype ='" + this.getEtId() + "'");
				}
				if (getSendIds() != null && !("".equals(getSendIds()))) {
					sql.append(" and pbs.reg_no not in('" + getSendIds().replace(";", "','") + "')");
				}
				if (this.getZgId() != null && !"".equals(this.getZgId())) {
					// sql.append(" and pbs.zige = '"+this.getZgId()+"'");
					if ("4028808c1dbe60fe011dbe7866590093".equals(this.getZgId())) {
						sql.append(" and (pbs.zige = '" + this.getZgId() + "' or pbs.zige is null )");
					} else {

						sql.append(" and pbs.zige = '" + this.getZgId() + "'");
					}
				}
				sql.append(" ) t order by t.login_id");
			} else if (us.getUserLoginType().equalsIgnoreCase("4") || us.getUserLoginType().equalsIgnoreCase("6")) { // 三、当前登陆人为：二级(监管)集体管理员
				sql.append("select login_id, true_name as name ,enterpriseName, enType,roleType, typeName from (");
				/* 集体二级 */
				if ("".equals(this.getZgId()) || this.getZgId() == null) {
					sql
							.append(" select pem.login_id, pem.name as true_name	,pe.name as enterpriseName,'' as enType, pr.name as roleType,'' as typeName	");
					sql.append(" from pe_enterprise_manager pem1 inner join pe_enterprise pe on pem1.fk_enterprise_id=pe.fk_parent_id ");
					sql.append(" inner join pe_enterprise_manager pem on pem.fk_enterprise_id=pe.id ");
					sql.append(" inner join sso_user u on pem.fk_sso_user_id=u.id ");
					sql.append(" inner join pe_pri_role pr on u.fk_role_id=pr.id ");
					if (getSendIds() != null && !("".equals(getSendIds()))) {
						sql.append(" where pem1.login_id not in('" + getSendIds().replace(";", "','") + "')");
					}
					if (this.getEnterpriseId() != null && !"".equals(this.getEnterpriseId())) {
						sql.append(" and pem1.fk_enterprise_id in (" + this.getEnterpriseId() + ")");
					}
					if (this.getEtId() != null && !"".equals(this.getEtId())) {
						sql.append(" and pe.entype ='" + this.getEtId() + "'");
					}
					sql.append(" union all ");
				}
				/* 2查询：学员 */
				sql
						.append(" select distinct(pbs.reg_no) as login_id, pbs.true_name, pe.name as enterpriseName, ec.name as enType,pr.name as roleType, ec.name as typeName ");
				sql.append(" from pe_bzz_student pbs, enum_const ec, sso_user u, pe_pri_role pr, pe_enterprise pe ");
				sql
						.append(" where pbs.zige = ec.id(+) and pbs.fk_sso_user_id = u.id and u.fk_role_id = pr.id and pbs.fk_enterprise_id=pe.id and pe.entype=ec.id");
				if (this.getEnterpriseId() != null && !"".equals(this.getEnterpriseId())) {
					sql.append(" and pe.id = " + this.getEnterpriseId() + "");
				}
				if (this.getEtId() != null && !"".equals(this.getEtId())) {
					sql.append(" and pe.entype ='" + this.getEtId() + "'");
				}
				if (getSendIds() != null && !("".equals(getSendIds()))) {
					sql.append(" and pbs.reg_no not in('" + getSendIds().replace(";", "','") + "')");
				}
				if (this.getZgId() != null && !"".equals(this.getZgId())) {

					if ("4028808c1dbe60fe011dbe7866590093".equals(this.getZgId())) {
						sql.append(" and (pbs.zige = '" + this.getZgId() + "' or pbs.zige is null )");
					} else {

						sql.append(" and pbs.zige = '" + this.getZgId() + "'");
					}

				}
				sql.append(" ) t order by t.login_id");
			}
		} else {// 机构类别为空
			if (us.getUserLoginType().equalsIgnoreCase("2") || us.getUserLoginType().equalsIgnoreCase("5")) {// 一、当前登陆人为：一级(监管)集体管理员
				/* 1查询：集体管理员 */
				sql.append("select login_id, true_name as name ,enterpriseName,enType, roleType, typeName from (");
				/* 集体一级 */
				if ("".equals(this.getZgId()) || this.getZgId() == null) {
					sql
							.append(" select pem.login_id, pem.name as true_name,pe.name as enterpriseName, '' as enType,pr.name as roleType, '' as typeName	");
					sql.append("   from pe_enterprise_manager pem, sso_user u, pe_pri_role pr, pe_enterprise pe	");
					sql.append(" where 1=1 and pem.fk_sso_user_id=u.id(+) and u.fk_role_id=pr.id and pem.fk_enterprise_id=pe.id ");
					if (getSendIds() != null && !("".equals(getSendIds()))) {
						sql.append(" and pem.login_id not in('" + getSendIds().replace(";", "','") + "')");
					}
					if (this.getEnterpriseId() != null && !"".equals(this.getEnterpriseId())) {
						sql.append(" and pem.fk_enterprise_id in (" + this.getEnterpriseId() + ")");
					}
					sql.append(" union all ");
				}
				// sql.append(" and pem.fk_enterprise_id =
				// '"+this.getEnterpriseId()+"'");
				/* 集体二级 */
				// if("".equals(this.getZgId()) ||this.getZgId()==null){
				// sql.append(" select pem.login_id, pem.name as name ,pe.name
				// as enterpriseName, pr.name as roleType,'' as typeName ");
				// sql.append(" from pe_enterprise_manager pem1 inner join
				// pe_enterprise pe on pem1.fk_enterprise_id=pe.fk_parent_id");
				// sql.append(" inner join pe_enterprise_manager pem on
				// pem.fk_enterprise_id=pe.id ");
				// sql.append(" inner join sso_user u on pem.fk_sso_user_id=u.id
				// ");
				// sql.append(" inner join pe_pri_role pr on u.fk_role_id=pr.id
				// ");
				// if(getSendIds()!=null&&!("".equals(getSendIds()))){
				// sql.append(" where pem1.login_id not
				// in('"+getSendIds().replace(";", "','")+"')");
				// }
				// if(this.getEnterpriseId()!=null&&!"".equals(this.getEnterpriseId())){
				// sql.append(" and pem1.fk_enterprise_id in
				// ("+this.getEnterpriseId()+")");
				// }
				// sql.append(" union");
				// }
				/* 2查询：机构学员 */
				sql
						.append(" select distinct(pbs.reg_no) as login_id, pbs.true_name, pe.name as enterpriseName,'' as enType, pr.name as roleType, ec.name as typeName ");
				sql.append(" from pe_bzz_student pbs, enum_const ec, sso_user u, pe_pri_role pr, pe_enterprise pe ");
				sql
						.append(" where pbs.zige = ec.id(+) and pbs.fk_sso_user_id = u.id and u.fk_role_id = pr.id and pbs.fk_enterprise_id=pe.id ");
				if (this.getEnterpriseId() != null && !"".equals(this.getEnterpriseId())) {
					sql.append(" and pe.id in (" + this.getEnterpriseId() + ")");
				}
				if (getSendIds() != null && !("".equals(getSendIds()))) {
					sql.append(" and pbs.reg_no not in('" + getSendIds().replace(";", "','") + "')");
				}
				if (this.getZgId() != null && !"".equals(this.getZgId())) {
					if ("4028808c1dbe60fe011dbe7866590093".equals(this.getZgId())) {
						sql.append(" and (pbs.zige = '" + this.getZgId() + "' or pbs.zige is null )");
					} else {

						sql.append(" and pbs.zige = '" + this.getZgId() + "'");
					}
				}
				sql.append(" ) t order by t.login_id");
			} else if (us.getUserLoginType().equalsIgnoreCase("3")) { // 二、当前登陆人为：协会管理员
				/* 1查询：协会管理员 */
				sql.append("select login_id, true_name as name ,enterpriseName,enType, roleType, typeName from (");
				if ((this.getEnterpriseId() == null || "".equals(this.getEnterpriseId()))) {
					sql
							.append(" select distinct(pm.login_id), pm.true_name ,''as enterpriseName,''as enType ,pr.name as roleType,'' as typeName                ");
					sql.append("   from pe_manager pm, sso_user u, pe_pri_role pr		");
					sql.append("  where pm.FK_SSO_USER_ID=u.id(+) and u.fk_role_id=pr.id and pm.login_id not in('" + us.getLoginId());
					if (getSendIds() != null && !("".equals(getSendIds()))) {
						sql.append("','" + getSendIds().replace(";", "','") + "')");
					} else {
						sql.append("')");
					}
					if (this.getZgId() != null && !"".equals(this.getZgId())) {
						sql.append(" and pm.login_id is null ");
					}
					sql.append(" union	all						");
				}
				/* 1查询：集体管理员 */
				/* 集体一级 */
				if ("".equals(this.getZgId()) || this.getZgId() == null) {
					sql
							.append(" select pem.login_id, pem.name as true_name,pe.name as enterpriseName,'' as enType, pr.name as roleType, '' as typeName	");
					sql.append("   from pe_enterprise_manager pem, sso_user u, pe_pri_role pr, pe_enterprise pe	");
					sql.append(" where 1=1 and pem.fk_sso_user_id=u.id(+) and u.fk_role_id=pr.id and pem.fk_enterprise_id=pe.id ");
					if (getSendIds() != null && !("".equals(getSendIds()))) {
						sql.append(" and pem.login_id not in('" + getSendIds().replace(";", "','") + "')");
					}
					if (this.getEnterpriseId() != null && !"".equals(this.getEnterpriseId())) {
						sql.append(" and pem.fk_enterprise_id in (" + this.getEnterpriseId() + ")");
					}
					sql.append(" union all ");
				}
				/* 集体二级 */
				// if("".equals(this.getZgId()) ||this.getZgId()==null){
				// sql.append(" select pem.login_id, pem.name as name ,pe.name
				// as enterpriseName, pr.name as roleType,'' as typeName ");
				// sql.append(" from pe_enterprise_manager pem1 inner join
				// pe_enterprise pe on pem1.fk_enterprise_id=pe.fk_parent_id");
				// sql.append(" inner join pe_enterprise_manager pem on
				// pem.fk_enterprise_id=pe.id ");
				// sql.append(" inner join sso_user u on pem.fk_sso_user_id=u.id
				// ");
				// sql.append(" inner join pe_pri_role pr on u.fk_role_id=pr.id
				// ");
				// if(getSendIds()!=null&&!("".equals(getSendIds()))){
				// sql.append(" where pem1.login_id not
				// in('"+getSendIds().replace(";", "','")+"')");
				// }
				// if(this.getEnterpriseId()!=null&&!"".equals(this.getEnterpriseId())){
				// sql.append(" and pem1.fk_enterprise_id =
				// ("+this.getEnterpriseId()+")");
				// }
				// sql.append(" union");
				// }
				/* 2查询：学员 */
				sql
						.append(" select pbs.reg_no as login_id, pbs.true_name, pe.name as enterpriseName,'' as enType, pr.name as roleType, ec.name as typeName ");
				sql.append(" from pe_bzz_student pbs, enum_const ec, sso_user u, pe_pri_role pr, pe_enterprise pe ");
				sql
						.append(" where pbs.zige = ec.id(+) and pbs.fk_sso_user_id = u.id and u.fk_role_id = pr.id and pbs.fk_enterprise_id=pe.id ");
				if (this.getEnterpriseId() != null && !"".equals(this.getEnterpriseId())) {
					sql.append(" and pe.id = " + this.getEnterpriseId() + "");
				}
				if (this.getEtId() != null && !"".equals(this.getEtId())) {
					sql.append(" and pe.entype ='" + this.getEtId() + "'");
				}
				if (getSendIds() != null && !("".equals(getSendIds()))) {
					sql.append(" and pbs.reg_no not in('" + getSendIds().replace(";", "','") + "')");
				}
				if (this.getZgId() != null && !"".equals(this.getZgId())) {
					// sql.append(" and pbs.zige = '"+this.getZgId()+"'");
					if ("4028808c1dbe60fe011dbe7866590093".equals(this.getZgId())) {
						sql.append(" and (pbs.zige = '" + this.getZgId() + "' or pbs.zige is null )");
					} else {

						sql.append(" and pbs.zige = '" + this.getZgId() + "'");
					}
				}
				sql.append(" ) t order by t.login_id");
			} else if (us.getUserLoginType().equalsIgnoreCase("4") || us.getUserLoginType().equalsIgnoreCase("6")) { // 三、当前登陆人为：二级(监管)集体管理员
				sql.append("select login_id, true_name as name ,enterpriseName, enType,roleType, typeName from (");
				/* 集体二级 */
				if ("".equals(this.getZgId()) || this.getZgId() == null) {
					sql
							.append(" select pem.login_id, pem.name as true_name	,pe.name as enterpriseName,'' as enType, pr.name as roleType,'' as typeName	");
					sql.append(" from pe_enterprise_manager pem1 inner join pe_enterprise pe on pem1.fk_enterprise_id=pe.fk_parent_id ");
					sql.append(" inner join pe_enterprise_manager pem on pem.fk_enterprise_id=pe.id ");
					sql.append(" inner join sso_user u on pem.fk_sso_user_id=u.id ");
					sql.append(" inner join pe_pri_role pr on u.fk_role_id=pr.id ");
					if (getSendIds() != null && !("".equals(getSendIds()))) {
						sql.append(" where pem1.login_id not in('" + getSendIds().replace(";", "','") + "')");
					}
					if (this.getEnterpriseId() != null && !"".equals(this.getEnterpriseId())) {
						sql.append(" and pem1.fk_enterprise_id in (" + this.getEnterpriseId() + ")");
					}
					sql.append(" union all ");
				}
				/* 2查询：学员 */
				sql
						.append(" select distinct(pbs.reg_no) as login_id, pbs.true_name, pe.name as enterpriseName, '' as enType,pr.name as roleType, ec.name as typeName ");
				sql.append(" from pe_bzz_student pbs, enum_const ec, sso_user u, pe_pri_role pr, pe_enterprise pe ");
				sql
						.append(" where pbs.zige = ec.id(+) and pbs.fk_sso_user_id = u.id and u.fk_role_id = pr.id and pbs.fk_enterprise_id=pe.id ");
				if (this.getEnterpriseId() != null && !"".equals(this.getEnterpriseId())) {
					sql.append(" and pe.id = " + this.getEnterpriseId() + "");
				}
				if (getSendIds() != null && !("".equals(getSendIds()))) {
					sql.append(" and pbs.reg_no not in('" + getSendIds().replace(";", "','") + "')");
				}
				if (this.getZgId() != null && !"".equals(this.getZgId())) {

					if ("4028808c1dbe60fe011dbe7866590093".equals(this.getZgId())) {
						sql.append(" and (pbs.zige = '" + this.getZgId() + "' or pbs.zige is null )");
					} else {

						sql.append(" and pbs.zige = '" + this.getZgId() + "'");
					}

				}
				sql.append(" ) t order by t.login_id");
			}
		}
		sql
				.append(") info left outer join pe_enterprise pe on pe.name=info.enterpriseName left outer join  pe_enterprise fpe on pe.fk_parent_id=fpe.id");

		sql.append(" where 1=1");
		if (null != this.getSendId() && !"".equals(this.getSendId())) {
			sql.append(" and login_id like '%" + this.getSendId() + "%' ");
		}
		if (this.getRoleType() != null && !"".equals(this.getRoleType())) {
			String temptype;
			try {
				temptype = java.net.URLDecoder.decode(this.getRoleType(), "UTF-8");
				sql.append(" and roleType = '" + temptype + "'");
				this.setRoleType(temptype);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (null != this.getSendName() && !"".equals(this.getSendName())) {
			String tempName;
			try {
				tempName = java.net.URLDecoder.decode(this.getSendName(), "UTF-8");
				sql.append(" and info.name like '%" + tempName + "%' ");
				this.setSendName(tempName);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			page = this.getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
			studentList = page.getItems();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**
		 * 求出总页数
		 */
		double tempTotalPage = page.getTotalCount() / Double.parseDouble(this.getLimit());

		/**
		 * 设置总页数
		 */
		if (tempTotalPage <= 1.0) {
			this.setTotalPage(1);
		} else if (tempTotalPage > 1.0 && tempTotalPage < 2.0) {
			this.setTotalPage(2);
		} else {
			if (page.getTotalCount() % Integer.parseInt(this.getLimit()) > 0) {
				this.setTotalPage(page.getTotalCount() / Integer.parseInt(this.getLimit()) + 1);
			} else
				this.setTotalPage(page.getTotalCount() / Integer.parseInt(this.getLimit()));
		}

		if (this.getCurPage() < 2) {
			this.setPrePage(-1); // 当前页面为1时，页面不再显示上一页和首页

			if (this.getTotalPage() < 2) {
				this.setTotalPage(1);
				this.setNextPage(-1); // 当总页数为1的时候，不再显示下一页和末页
			} else
				this.setNextPage(this.getCurPage() + 1);
		} else {
			this.setPrePage(this.getCurPage() - 1);
			if (this.getCurPage() >= this.getTotalPage()) {
				this.setNextPage(-1); // 当前页如果是最大页码数，不再显示下一页和末页
			} else
				this.setNextPage(this.getCurPage() + 1);
		}
		this.enterpriseId = enterpriseIdBack;
		return "add";
	}

	// 拼接ID已;隔开
	public String addEnd() {
		if (flag == null) {
			if (!("".equals(idss)) && idss != null) {
				if (idss.length == 1) {
					logId = idss[0];
				} else {
					StringBuffer temp = new StringBuffer();
					for (int i = 0; i < idss.length; i++) {
						if (i == 0) {
							temp.append(idss[i]);
						} else {
							temp.append(";" + idss[i]);
						}
					}
					logId = temp.toString();
				}
			}
			if (getSendIds() != null && !("".equals(getSendIds()))) {
				logId = logId + ";" + getSendIds();
			}
			return "index";
		} else {
			if (getSendIds() != null && !("".equals(getSendIds()))) {
				logId = getSendIds();
			}
			return "index";
		}
	}

	/**
	 * 发送邮件
	 * 
	 * @return
	 */
	public String sendexe() {
		List listId;

		ActionContext ac = ActionContext.getContext();
		UserSession us = (UserSession) ac.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (sendIds != null) {
			if (sendIds.indexOf(';') == -1) {
				// 校验看收件人是否存在
				try {
					StringBuffer sql = new StringBuffer();
					sql.append(" select p.login_id                           ");
					sql.append("   from Pe_Manager p			 ");
					sql.append("  where p.login_id = '" + sendIds + "'	 ");
					sql.append(" union					 ");
					sql.append(" select pem.login_id			 ");
					sql.append("   from pe_enterprise_manager pem		 ");
					sql.append("  where pem.login_id = '" + sendIds + "'	 ");
					sql.append(" union					 ");
					sql.append(" select pbs.reg_no as login_id		 ");
					sql.append("   from pe_bzz_student pbs			 ");
					sql.append("  where pbs.reg_no = '" + sendIds + "'");
					listId = this.getGeneralService().getBySQL(sql.toString());
				} catch (EntityException e1) {
					errorid = sendIds;
					flag = "false";
					return "status";
				}
				if (listId == null || listId.size() == 0) {
					errorid = sendIds;
					flag = "false";
					return "status";
				}
				SiteEmail siteEmail = new SiteEmail();
				siteEmail.setAddresseeId(sendIds);
				siteEmail.setContent(this.getContent());
				siteEmail.setSenderId(us.getLoginId());
				// 阅读状态 默认0未读
				siteEmail.setStatus("0");
				siteEmail.setTitle(this.getTitle());
				try {
					this.getGeneralService().save(siteEmail);
				} catch (EntityException e) {
					flag = "false";
					return "status";
				}
			} else {

				String[] str = sendIds.split(";");
				List<SiteEmail> list = new ArrayList();
				for (int i = 0; i < str.length; i++) {
					// 校验看收件人是否存在
					StringBuffer sql = new StringBuffer();
					sql.append(" select p.login_id                           ");
					sql.append("   from Pe_Manager p			 ");
					sql.append("  where p.login_id = '" + str[i] + "'	 ");
					sql.append(" union					 ");
					sql.append(" select pem.login_id			 ");
					sql.append("   from pe_enterprise_manager pem		 ");
					sql.append("  where pem.login_id = '" + str[i] + "'	 ");
					sql.append(" union					 ");
					sql.append(" select pbs.reg_no as login_id		 ");
					sql.append("   from pe_bzz_student pbs			 ");
					sql.append("  where pbs.reg_no = '" + str[i] + "'");
					try {
						listId = this.getGeneralService().getBySQL(sql.toString());
					} catch (EntityException e1) {
						errorid = str[i];
						flag = "false";
						return "status";
					}
					if (listId == null || listId.size() == 0) {
						errorid = str[i];
						flag = "false";
						return "status";
					}
					SiteEmail siteEmail = new SiteEmail();
					siteEmail.setAddresseeId(str[i]);
					siteEmail.setContent(this.getContent());
					siteEmail.setSenderId(us.getLoginId());
					// 阅读状态 默认0未读
					siteEmail.setStatus("0");
					siteEmail.setTitle(this.getTitle());
					list.add(siteEmail);
				}
				try {
					this.getGeneralService().saveList(list);
				} catch (EntityException e) {
					flag = "false";
					return "status";
				}
			}
		} else {
			flag = "false";
			return "status";
		}
		flag = "true";
		return "status";
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public int getPrePage() {
		return prePage;
	}

	public void setPrePage(int prePage) {
		this.prePage = prePage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getIsLimit() {
		return isLimit;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	/**
	 * 收信
	 * 
	 * @author shangbingcang
	 * @return
	 */
	public String recipient() {
		List listId;
		ActionContext ac = ActionContext.getContext();
		UserSession us = (UserSession) ac.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		// 校验看收件人是否存在
		StringBuffer sql = new StringBuffer();
		sql.append(" select p.login_id                           ");
		sql.append("   from Pe_Manager p			 ");
		sql.append("  where p.login_id = '" + this.getReid() + "'	 ");
		sql.append(" union					 ");
		sql.append(" select pem.login_id			 ");
		sql.append("   from pe_enterprise_manager pem		 ");
		sql.append("  where pem.login_id = '" + this.getReid() + "'	 ");
		sql.append(" union					 ");
		sql.append(" select pbs.reg_no as login_id		 ");
		sql.append("   from pe_bzz_student pbs			 ");
		sql.append("  where pbs.reg_no = '" + this.getReid() + "'");

		try {
			listId = this.getGeneralService().getBySQL(sql.toString());
		} catch (EntityException e1) {
			errorid = sendIds;
			flag = "false";
			return "status";
		}
		if (listId == null || listId.size() == 0) {
			errorid = sendIds;
			flag = "false";
			return "status";
		}
		SiteEmail site = new SiteEmail();
		site.setAddresseeId(this.getReid());
		site.setContent(this.getContent());
		site.setSenderId(us.getLoginId());
		// 阅读状态 默认0未读
		site.setStatus("0");
		site.setTitle(this.getTitle());
		try {
			this.getGeneralService().save(site);
		} catch (EntityException e) {
			flag = "false";
			return "errorr";
		}
		flag = "true";
		return "errorr";
	}

	public String getReid() {
		return reid;
	}

	public List<EnumConst> getZgList() {
		return zgList;
	}

	public void setZgList(List<EnumConst> zgList) {
		this.zgList = zgList;
	}

}

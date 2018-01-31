package com.whaty.platform.entity.web.action.basic;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import javax.imageio.ImageIO;
import jxl.Cell;
import jxl.CellType;
import jxl.LabelCell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzRefundInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.hibernate.GeneralHibernateDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PeBzzGoodStuService;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.util.file.FileManage;
import com.whaty.util.file.FileManageFactory;

/**
 * @param
 * @version 创建时间：2009-6-22 下午03:29:27
 * @return
 * @throws PlatformException
 *             类说明
 */
public class PeBzzStudentAction extends MyBaseAction<PeBzzStudent> {

	// 上传所用字段
	private File upload; // 文件
	private String uploadFileName; // 文件名属性
	private String uploadContentType; // 文件类型属性
	private String _uploadContentType; // 文件类型属性
	private String savePath; // 文件存储位置
	private String batchid;
	private List<PeBzzBatch> peBzzBatch;
	private List<PeBzzStudent> plist = new ArrayList<PeBzzStudent>();
	private String mngPri;
	private List groupList;
	private List<PeEnterprise> peEnterpriseList;
	private int count;
	private String studentId;
	private String reason;
	private String flag;
	private PeBzzStudent peBzzStudent;
	private StringBuffer uploadErrors = new StringBuffer();
	private String enterPage;
	private String loginCode;
	private String courseCode;

	private PeBzzstudentbacthService peBzzstudentbacthService;

	private PeBzzGoodStuService peBzzGoodStuService;

	private GeneralHibernateDao generalHibernateDao;

	private EnumConstService enumConstService;

	public PeBzzGoodStuService getPeBzzGoodStuService() {
		return peBzzGoodStuService;
	}

	public void setPeBzzGoodStuService(PeBzzGoodStuService peBzzGoodStuService) {
		this.peBzzGoodStuService = peBzzGoodStuService;
	}

	public PeBzzstudentbacthService getPeBzzstudentbacthService() {
		return peBzzstudentbacthService;
	}

	public void setPeBzzstudentbacthService(PeBzzstudentbacthService peBzzstudentbacthService) {
		this.peBzzstudentbacthService = peBzzstudentbacthService;
	}

	public File getUpload() {
		return upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public String getSavePath() {
		return ServletActionContext.getServletContext().getRealPath(savePath);
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	/**
	 * 初始化列表
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.setCanProjections(true);
		boolean canUpdate = true;
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.getGridConfig().setCapability(false, false, false, true, false);
		this.getGridConfig().setTitle(this.getText("学员信息列表"));
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("系统编号"), "regNo", true, true, true, "TextField", false, 100, true);
		this.getGridConfig().addColumn(this.getText("姓名"), "trueName", true, canUpdate, true, "TextField", true, 100);
		ColumnConfig c_name1 = new ColumnConfig(this.getText("所在机构"), "peEnterprise.name", true, canUpdate, true, "TextField", true, 50, "");
		if (us.getUserLoginType().equals("2") || us.getUserLoginType().equals("4")) {
			String sql = "select t.id, t.name as p_name\n" + "  from pe_enterprise t\n" + " where t.fk_parent_id in\n"
					+ "       (select s.id\n" + "          from pe_enterprise s\n" + "         inner join pe_enterprise_manager t\n"
					+ "            on s.id = t.fk_enterprise_id\n" + "           and t.login_id = '" + us.getLoginId() + "')\n"
					+ "    or t.id in (select s.id\n" + "                  from pe_enterprise s\n"
					+ "                 inner join pe_enterprise_manager t\n" + "                    on s.id = t.fk_enterprise_id\n"
					+ "                   and t.login_id = '" + us.getLoginId() + "')\n"
					+ " order by nlssort(t.name,'NLS_SORT=SCHINESE_PINYIN_M')";

			c_name1.setComboSQL(sql);
		} else {
			c_name1.setComboSQL("select t.id,t.name as p_name from pe_enterprise t order by nlssort(t.name,'NLS_SORT=SCHINESE_PINYIN_M') ");
		}
		this.getGridConfig().addColumn(c_name1);
		// 身份证号，改为,证件号码hdg
		if (null != us.getUserLoginType() && "56".indexOf(us.getUserLoginType()) >= 0) {
			this.getGridConfig().addColumn(this.getText("电子邮件"), "email", true, canUpdate, true, "TextField", true, 100);
			this.getGridConfig().addColumn(this.getText("从事业务"), "work", true, canUpdate, true, "TextField", true, 100);
			this.getGridConfig().addColumn(this.getText("职务"), "position", true, canUpdate, true, "TextField", true, 100);
			this.getGridConfig().addColumn(this.getText("办公电话"), "phone", true, canUpdate, true, "TextField", true, 100);
		} else {
			this.getGridConfig().addColumn(this.getText("证件号码"), "cardNo", true, canUpdate, true, "TextField", true, 100);
		}
		if (!"131AF5EC87836928E0530100007F9F54".equals(us.getSsoUser().getPePriRole().getId())) {
			ColumnConfig c_name = new ColumnConfig(this.getText("资格类型"), "enumConstByFlagQualificationsType.name");
			c_name.setAdd(true);
			c_name.setSearch(true);
			c_name.setList(true);
			c_name.setComboSQL("SELECT * FROM ENUM_CONST t WHERE NAMESPACE = 'FlagQualificationsType' and t.code not in('9','90','91')");
			this.getGridConfig().addColumn(c_name);
		}
		// 非监管机构
		if (null != us.getUserLoginType() && "56".indexOf(us.getUserLoginType()) == -1) {
			this.getGridConfig().addColumn(this.getText("从事业务"), "work", true, false, true, "TextField", true, 100);
			this.getGridConfig().addColumn(this.getText("职务"), "position", true, false, true, "TextField", true, 100);
		}
		// this.getGridConfig().addColumn(this.getText("资格类型"),"enumConstByFlagQualificationsType.name",true,false,true,"TextField",false,100,true);
		if (!"131AF5EC87836928E0530100007F9F54".equals(us.getSsoUser().getPePriRole().getId())) {
			this.getGridConfig().addColumn(this.getText("具有从业资格"), "enumConstByIsEmployee.name", true, false, true, "TextField", false,
					100, true);
			this.getGridConfig().addColumn(this.getText("是否有效"), "ssoUser.enumConstByFlagIsvalid.name", true, false, true, "TextField",
					true, 50);
		}
		this.getGridConfig().addColumn(this.getText("工作部门"), "department", true, canUpdate, true, "TextField", true, 50);
		ColumnConfig c_name2 = null;
		if (us.getUserLoginType().equals("4")) {
			c_name2 = new ColumnConfig(this.getText("组别"), "subGroups", true, canUpdate, true, "TextField", true, 100, "");
		} else {
			c_name2 = new ColumnConfig(this.getText("组别"), "groups", true, canUpdate, true, "TextField", true, 100, "");
		}
		String peEnterpriseCode = (String) ServletActionContext.getRequest().getSession().getAttribute("peEnterprisemanager");
		StringBuffer sql = new StringBuffer();

		if (us.getUserLoginType().equals("4")) {
			sql.append("  SELECT DISTINCT t.SUB_GROUPS\n ");
		} else {
			sql.append("  SELECT DISTINCT t.GROUPS\n ");
		}

		sql.append("  FROM   PE_BZZ_STUDENT t\n ");
		sql.append("  LEFT JOIN pe_enterprise s\n");
		sql.append("       ON t.fk_enterprise_id = s.id\n ");
		sql.append("          LEFT JOIN pe_enterprise a\n");
		sql.append("         ON s.fk_parent_id = a.id\n");
		sql.append("            AND a.code = '" + peEnterpriseCode + "'\n");
		if (us.getUserLoginType().equals("4")) {
			sql.append("  where t.SUB_GROUPS is not null\n ");
			sql.append("  order by t.SUB_GROUPS\n ");
		} else {
			sql.append("  where t.GROUPS is not null\n ");
			sql.append("  order by t.GROUPS");
		}
		c_name2.setComboSQL(sql.toString());
		this.getGridConfig().addColumn(c_name2);// 组别
		this.getGridConfig().addColumn(this.getText("性别"), "gender", false, canUpdate, false, "TextField", true, 10, true);
		this.getGridConfig().addColumn(this.getText("民族"), "folk", false, canUpdate, false, "TextField", true, 40);
		this.getGridConfig().addColumn(this.getText("学历"), "education", false, canUpdate, false, "TextField", true, 10, true);
		this.getGridConfig().addColumn(this.getText("出生日期"), "birthdayDate", false, canUpdate, false, "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("办公电话"), "phone", false, canUpdate, false, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("移动电话"), "mobilePhone", false, canUpdate, false, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("电子邮件"), "email", false, canUpdate, false, "TextField", true, 50);
		this.getGridConfig().addColumn(this.getText("学员照片"), "photo", false, false, false, "File", true, 50);
		// this.getGridConfig().addColumn(this.getText("国籍"),"cardType",false,canUpdate,true,"TextField",true,100);
		UserSession userSession = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(
				SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = userSession.getUserPriority().keySet();
		boolean password = false;
		boolean FlagIsvalid = false;
		// Lee 2015年1月7日 客服hjfw账号添加重置密码权限 ||
		// "hjfw".equalsIgnoreCase(us.getSsoUser().getLoginId())
		if (capabilitySet.contains(this.servletPath + "_password.action") || "hjfw".equalsIgnoreCase(us.getSsoUser().getLoginId())) {
			password = true;
		}
		if (capabilitySet.contains(this.servletPath + "_FlagIsvalid.action")) {
			FlagIsvalid = true;
		}
		if (null == enterPage || "".endsWith(enterPage)) {
			if (us.getUserLoginType().equals("3")) {
				if (password) {
					this.getGridConfig().addMenuFunction("重置密码(" + Const.defaultPwd + ")", "pwsd"); // 只给总站管理员提供此功能
				}
				if (FlagIsvalid) {
					this.getGridConfig().addMenuFunction(this.getText("设为有效"), "FlagIsvalid.true");
					this.getGridConfig().addMenuFunction(this.getText("设为无效/监管学员离职"), "FlagIsvalid.false");
				}
				// Lee 2014年9月20日 批量学员调入
				this.getGridConfig().addMenuScript(this.getText("无资格学员调入"),
						"{window.location='/entity/basic/peBzzStudent_stuCheckId.action';}");
				this.getGridConfig().addMenuScript(this.getText("课件卡/学习记录不上"),
						"{window.location='/entity/basic/peBzzStudent_studyRecord.action';}");
				// this.getGridConfig().addMenuScript(this.getText("批量学员学习进度修改"),
				// "{window.location='/entity/basic/peBzzStudent_stuLearnStatus.action';}");
			} else if (us.getUserLoginType().equals("2") || "5".equals(us.getUserLoginType())) {
				this.getGridConfig().addMenuFunction("重置密码(" + Const.defaultPwd + ")", "pwsd");
				this.getGridConfig().addMenuFunction(this.getText("分组"),
						"/entity/basic/peBzzStudent_toGroup.action?flag=" + this.flag + "", false, false);
				// this.getGridConfig().addMenuFunction(this.getText("修改分组名称"),"/entity/basic/peBzzStudent_modifyGroup.action?flag="+this.flag+"",true,false);
				this.getGridConfig().addMenuScript(this.getText("修改分组名称"),
						"{window.location='/entity/basic/peBzzStudent_modifyGroup.action';}");
				this.getGridConfig().addMenuFunction(this.getText("撤销分组"), "clearGroup");
				if (!"self".equals(flag)) {
					this.getGridConfig().addMenuScript(this.getText("学员导入"),
							"{window.location='/entity/basic/peBzzStudent_toUpload.action?flag=" + this.flag + "';}");
					this.getGridConfig().addMenuFunction(this.getText("学员调配"),
							"/entity/basic/peBzzStudent_toCheckIn.action?flag=" + this.flag + "", false, false);
					this.getGridConfig().addMenuScript(this.getText("Excel导入学员调配"),
							"{window.location='/entity/basic/peBzzStudent_toExcelCheckIn.action?flag=" + this.flag + "';}");
					this.getGridConfig().addMenuScript(this.getText("Excel批量导入分组"),
							"{window.location='/entity/basic/peBzzStudent_batchExcelGroup.action?flag=" + this.flag + "';}");
					// Lee 2014年9月20日 批量学员调出
					if (!"5".equals(us.getUserLoginType())) {
						this.getGridConfig().addMenuFunction(this.getText("无资格学员调出"), "stuCheckOut");
					} else {
						this.getGridConfig().addMenuFunction(this.getText("学员离职"), "FlagIsvalid.false");
					}
				}
			} else if (us.getUserLoginType().equals("4") || "6".equals(us.getUserLoginType())) {
				this.getGridConfig().addMenuFunction("重置密码(" + Const.defaultPwd + ")", "pwsd");
				this.getGridConfig().addMenuFunction(this.getText("分组"),
						"/entity/basic/peBzzStudent_toGroup.action?flag=" + this.flag + "", false, false);
				this.getGridConfig().addMenuScript(this.getText("修改分组名称"),
						"{window.location='/entity/basic/peBzzStudent_modifyGroup.action';}");
				this.getGridConfig().addMenuFunction(this.getText("撤销分组"), "clearGroup");
				this.getGridConfig().addMenuFunction(this.getText("学员调配"),
						"/entity/basic/peBzzStudent_toCheckIn.action?flag=" + this.flag + "", false, false);
				this.getGridConfig().addMenuScript(this.getText("Excel导入学员调配"),
						"{window.location='/entity/basic/peBzzStudent_toExcelCheckIn.action?flag=" + this.flag + "';}");
				// Lee 2014年9月20日 学员调出
				if (!"6".equals(us.getUserLoginType())) {
					this.getGridConfig().addMenuFunction(this.getText("无资格学员调出"), "stuCheckOut");
				} else {
					this.getGridConfig().addMenuFunction(this.getText("学员离职"), "FlagIsvalid.false");
				}
			}
			ServletActionContext.getRequest().getSession().setAttribute("flag_x", "stu");
			// 把"详细信息"，修改为学员信息 ，"查看学习详情"改为"学习详情"hdg
			this.getGridConfig().addRenderFunction(this.getText("学员信息"),
					"<a target=\"_blank\" href=\"peDetail_stuviewDetail.action?id=${value}\">学员信息</a>", "id");
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("学习详情"),
							"{return '<a target=\"_blank\" href=\"/entity/teaching/searchAnyStudent_electivedCourse.action?flag=stu&stuId='+record.data['id']+'\">学习详情</a>';}",
							"id");
		} else {
			this.getGridConfig().addMenuScript(this.getText("返回"), "{window.location='/entity/basic/enterpriseManager.action';}");
		}
		/*
		 * this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		 * 
		 * this.getGridConfig().addColumn(this.getText("系统编号"),"regNo",true,true,
		 * true,"TextField",false,100,true);
		 * this.getGridConfig().addColumn(this.
		 * getText("具有从业资格"),"combobox_isEmployee"
		 * ,false,false,true,"TextField",false,100,true);
		 * this.getGridConfig().addColumn
		 * (this.getText("资格类型"),"combobox_zige",false
		 * ,false,true,"TextField",false,100,true);
		 * 
		 * this.getGridConfig().addColumn(this.getText("姓名"),"trueName",true,
		 * canUpdate,true,"TextField",true,100);
		 * this.getGridConfig().addColumn(this
		 * .getText("性别"),"gender",false,canUpdate
		 * ,false,"TextField",true,10,true);
		 * this.getGridConfig().addColumn(this.
		 * getText("民族"),"folk",false,canUpdate,false,"TextField",true,40);
		 * this.
		 * getGridConfig().addColumn(this.getText("学历"),"education",false,canUpdate
		 * ,false,"TextField",true,10,true);
		 * this.getGridConfig().addColumn(this.getText("出生日期"),"birthday",false,
		 * canUpdate, false, "TextField", true,10);
		 * //if("self".equals(flag)){//本机构 ColumnConfig c_name1 = new
		 * ColumnConfig(this.getText("所在机构"),
		 * "combobox_peName",false,canUpdate,true,"TextField",true,50,""); if
		 * (us.getUserLoginType().equals("2")
		 * ||us.getUserLoginType().equals("4")) { String sql = "select t.id,
		 * t.name as p_name\n" + " from pe_enterprise t\n" + " where
		 * t.fk_parent_id in\n" + " (select s.id\n" + " from pe_enterprise s\n" + "
		 * inner join pe_enterprise_manager t\n" + " on s.id =
		 * t.fk_enterprise_id\n" + " and t.login_id = '"+us.getLoginId()+"')\n" + "
		 * or t.id in (select s.id\n" + " from pe_enterprise s\n" + " inner join
		 * pe_enterprise_manager t\n" + " on s.id = t.fk_enterprise_id\n" + "
		 * and t.login_id = '"+us.getLoginId()+"')\n" + " order by t.code";
		 * 
		 * c_name1 .setComboSQL(sql); } else { c_name1.setComboSQL("select
		 * t.id,t.name as p_name from pe_enterprise t order by t.code "); }
		 * this.getGridConfig().addColumn(c_name1);
		 * 
		 * 
		 * this.getGridConfig().addColumn(this.getText("是否有效"),
		 * "combobox_isValid",false,false,true,"TextField",true,50);
		 * 
		 * this.getGridConfig().addColumn(this.getText("办公电话"),"phone",false,
		 * canUpdate,false,"TextField",true,50);
		 * this.getGridConfig().addColumn(this
		 * .getText("移动电话"),"mobilePhone",false
		 * ,canUpdate,false,"TextField",true,50);
		 * this.getGridConfig().addColumn(
		 * this.getText("电子邮件"),"email",false,canUpdate
		 * ,false,"TextField",true,50);
		 * 
		 * 
		 * 
		 * this.getGridConfig().addColumn(this.getText("国籍"),"cardType",false,
		 * canUpdate,true,"TextField",true,100);
		 * this.getGridConfig().addColumn(this
		 * .getText("身份证号"),"cardNo",true,canUpdate,true,"TextField",true,100);
		 * ColumnConfig c_name2 = new ColumnConfig(this.getText("组别"),
		 * "combobox_groups",false,canUpdate,true,"TextField",true,100,"");
		 * String peEnterpriseCode = (String)ServletActionContext.getRequest()
		 * .getSession().getAttribute("peEnterprisemanager"); String sql =
		 * "SELECT DISTINCT t.GROUPS\n" + " FROM PE_BZZ_STUDENT t" + " LEFT JOIN
		 * pe_enterprise s" + " ON t.fk_enterprise_id = s.id " + " LEFT JOIN
		 * pe_enterprise a" + " ON s.fk_parent_id = a.id" + " AND a.code =
		 * '"+peEnterpriseCode+"'" + " order by t.GROUPS";
		 * c_name2.setComboSQL(sql); this.getGridConfig().addColumn(c_name2);
		 * //this
		 * .getGridConfig().addColumn(this.getText("组别"),"groups",true,canUpdate
		 * ,true,"TextField",true,100); if (us.getUserLoginType().equals("3")) {
		 * this.getGridConfig().addMenuFunction("重置密码","pwsd"); //只给总站管理员提供此功能
		 * this.getGridConfig().addMenuFunction(
		 * this.getText("设为有效"),"FlagIsvalid.true");
		 * this.getGridConfig().addMenuFunction
		 * (this.getText("设为无效"),"FlagIsvalid.false");
		 * 
		 * }else if(us.getUserLoginType().equals("2")){
		 * if("self".equalsIgnoreCase(flag)){
		 * this.getGridConfig().addMenuFunction
		 * (this.getText("分组"),"/entity/basic/peBzzStudent_toGroup.action?flag="
		 * +this.flag+"",false,false);
		 * this.getGridConfig().addMenuFunction(this.
		 * getText("撤销分组"),"clearGroup"); }else{
		 * this.getGridConfig().addMenuScript(this.getText("学员导入"),
		 * "{window.location='/entity/basic/peBzzStudent_toUpload.action?flag="
		 * +this.flag+"';}");
		 * this.getGridConfig().addMenuFunction(this.getText("学员调配"
		 * ),"/entity/basic/peBzzStudent_toCheckIn.action?flag="
		 * +this.flag+"",false,false);
		 * this.getGridConfig().addMenuScript(this.getText("Excel导入学员调配"),
		 * "{window.location='/entity/basic/peBzzStudent_toExcelCheckIn.action?flag="
		 * +this.flag+"';}"); } }else if(us.getUserLoginType().equals("4")){
		 * this.getGridConfig().addMenuFunction(this.getText("分组"),
		 * "/entity/basic/peBzzStudent_toGroup.action?flag="
		 * +this.flag+"",false,false);
		 * this.getGridConfig().addMenuFunction(this.
		 * getText("撤销分组"),"clearGroup"); }
		 * this.getGridConfig().addRenderFunction(this.getText("详细信息"), "<a
		 * href=\"peDetail_stuviewDetail.action?id=${value}\">查看详细信息</a>",
		 * "id"); this.getGridConfig().addRenderScript(this.getText("查看学习详情"),
		 * "{return '<a href=\
		 * "/entity/teaching/searchAnyStudent_electivedCourse.action?flag=stu&stuId='+record.data['id']+'\">查看学习详情</a>';}" ,
		 * "id");
		 */
	}

	/**
	 * 初始化分组信息
	 * 
	 * @author linjie
	 * @return
	 */
	public void initGroupList() {
		String peEnterpriseCode = (String) ServletActionContext.getRequest().getSession().getAttribute("peEnterprisemanager");
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sqlBuffer = new StringBuffer();
		if (us.getUserLoginType().equals("4")) {
			sqlBuffer.append("SELECT DISTINCT t.SUB_GROUPS ");
			sqlBuffer.append("FROM   PE_BZZ_STUDENT t ");
			sqlBuffer.append("       LEFT JOIN pe_enterprise s ");
			sqlBuffer.append("         ON t.fk_enterprise_id = s.id ");
			sqlBuffer.append("            where s.code = '");
			sqlBuffer.append(peEnterpriseCode);
			sqlBuffer.append("' and t.sub_groups is not null");
		} else {
			sqlBuffer.append("SELECT DISTINCT t.GROUPS ");
			sqlBuffer.append("FROM   PE_BZZ_STUDENT t ");
			sqlBuffer.append("       LEFT JOIN pe_enterprise s ");
			sqlBuffer.append("         ON t.fk_enterprise_id = s.id ");
			sqlBuffer.append("         inner join pe_enterprise pe2 on s.fk_parent_id = pe2.id ");
			sqlBuffer.append("            where pe2.code = '");
			sqlBuffer.append(peEnterpriseCode);
			sqlBuffer.append("' and t.groups is not null");
			sqlBuffer.append("	   union	");
			sqlBuffer.append("SELECT DISTINCT t.GROUPS ");
			sqlBuffer.append("FROM   PE_BZZ_STUDENT t ");
			sqlBuffer.append("       LEFT JOIN pe_enterprise s ");
			sqlBuffer.append("         ON t.fk_enterprise_id = s.id ");
			sqlBuffer.append("            where s.code = '");
			sqlBuffer.append(peEnterpriseCode);
			sqlBuffer.append("' and t.groups is not null");
		}
		try {
			this.groupList = this.getGeneralService().getBySQL(sqlBuffer.toString());
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始机构化列表
	 * 
	 * @author linjie
	 * @return
	 */
	public void initPeEnterpriseList() throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		try {
			this.checkBeforeCheckIn(this.getIds(), us);

		} catch (EntityException e) {
			// e.printStackTrace();
			throw new EntityException("机构验证失败--" + e.getMessage());
		}
		DetachedCriteria dcTemp = DetachedCriteria.forClass(PeEnterprise.class);
		String peEnterpriseCode = (String) ServletActionContext.getRequest().getSession().getAttribute("peEnterprisemanager");
		if (us.getUserLoginType().equals("4") || "6".equals(us.getUserLoginType())) {
			DetachedCriteria dc2 = DetachedCriteria.forClass(PeEnterprise.class);
			dc2.add(Restrictions.eq("code", peEnterpriseCode));
			dc2.setProjection(Property.forName("peEnterprise.id"));
			dcTemp.add(Restrictions.or(Restrictions.eq("code", peEnterpriseCode), Property.forName("id").in(dc2)));
		} else if (us.getUserLoginType().equals("2") || "5".equals(us.getUserLoginType())) {
			dcTemp.createAlias("peEnterprise", "peEnterprise", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria dc2 = DetachedCriteria.forClass(PeEnterprise.class);
			dc2.add(Restrictions.eq("code", peEnterpriseCode));
			dc2.setProjection(Property.forName("id"));
			// 一级管理员不能往自己机构迁移暂时不读取自身机构
			// dcTemp.add(Restrictions.or(Restrictions.eq("code",
			// peEnterpriseCode), Property.forName("peEnterprise.id").in(dc2)));
			dcTemp.add(Property.forName("peEnterprise.id").in(dc2));
		}
		dcTemp.addOrder(Order.asc("name"));
		try {
			this.peEnterpriseList = this.getGeneralService().getDetachList(dcTemp);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获得当前登录用户的机构信息
	 * 
	 * @author linjie
	 * @return
	 */
	private List<String> getMyEnterprise(UserSession us) throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc.createCriteria("peEnterprise");
		dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
		List<PeEnterpriseManager> pemlist = this.getGeneralService().getList(dc);
		List<String> myList = new ArrayList<String>();

		if (pemlist != null && pemlist.size() > 0) {
			for (PeEnterpriseManager pem : pemlist) {
				if (us.getUserLoginType().equals("2")) {// 一级管理员,迁移学员是自己机构的
					myList.add(pem.getPeEnterprise().getId());
				} else if (us.getUserLoginType().equals("4")) {// 二级管理员，迁移学员是自己机构与上级机构的
					myList.add(pem.getPeEnterprise().getId());
					myList.add(pem.getPeEnterprise().getPeEnterprise().getId());
				}
			}
			return myList;
		} else {
			return null;
		}
	}

	public void initPeEnterpriseList1() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dcTemp = DetachedCriteria.forClass(PeEnterprise.class);
		String peEnterpriseCode = (String) ServletActionContext.getRequest().getSession().getAttribute("peEnterprisemanager");
		if (us.getUserLoginType().equals("4")) {
			dcTemp.add(Restrictions.eq("code", peEnterpriseCode));
		} else if (us.getUserLoginType().equals("2")) {
			dcTemp.createAlias("peEnterprise", "peEnterprise", DetachedCriteria.LEFT_JOIN);
			DetachedCriteria dc2 = DetachedCriteria.forClass(PeEnterprise.class);
			dc2.add(Restrictions.eq("code", peEnterpriseCode));
			dc2.setProjection(Property.forName("id"));
			dcTemp.add(Restrictions.or(Restrictions.eq("code", peEnterpriseCode), Property.forName("peEnterprise.id").in(dc2)));
		}
		try {
			this.peEnterpriseList = this.getGeneralService().getDetachList(dcTemp);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void putIdsToSession() {
		ServletActionContext.getRequest().getSession().setAttribute("idStr", this.getIds());
	}

	/**
	 * 学员分组
	 * 
	 * @author linjie
	 * @return
	 */
	public String toGroup() {
		initGroupList();
		putIdsToSession();
		return "toGroup";
	}

	/**
	 * 学员签入
	 * 
	 * @author linjie
	 * @return
	 */
	public String toCheckIn() {
		try {
			initPeEnterpriseList();
			putIdsToSession();
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
			this.setTogo("back");
			return "m_msg";
		}
		return "toCheckIn";
	}

	/**
	 * excel学员调配
	 * 
	 * @author linjie
	 * @return
	 */
	public String toExcelCheckIn() {// excel学员调配
		try {
			initPeEnterpriseList();
			putIdsToSession();
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
			this.setTogo("back");
			return "m_msg";
		}
		return "toExcelCheckIn";
	}

	/**
	 * 批量导入分组
	 * 
	 * @author hdg
	 * @return
	 */
	public String batchExcelGroup() {
		initGroupList();
		return "batchExcelGroup";
	}

	/**
	 * 读取批量导入分组
	 * 
	 * @author hdg
	 * @return
	 */
	public String doExcelGroup() {
		String group = ServletActionContext.getRequest().getParameter("selectGroup");
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer msg = new StringBuffer();
		Workbook work = null;
		try {
			work = Workbook.getWorkbook(this.get_upload());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp1 = "";
		int success = 0;
		for (int i = 1; i < rows; i++) {
			try {
				temp1 = sheet.getCell(0, i).getContents().trim();
				if (temp1 == null || "".equals(temp1)) {
					msg.append("第" + (i + 1) + "行数据，系统编号不能为空！<br/>");
					continue;
				}
				String sql = "UPDATE pe_bzz_student t ";
				if (us.getUserLoginType().equals("4")) {
					sql += "SET    t.sub_groups = '";
				} else {
					sql += "SET    t.groups = '";
				}
				sql += group
						+ "' WHERE  upper(t.reg_no) = '"
						+ temp1.toUpperCase()
						+ "' AND T.FK_ENTERPRISE_ID IN (SELECT ID FROM PE_ENTERPRISE PE WHERE PE.ID = (SELECT PEM.FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER PEM WHERE PEM.FK_SSO_USER_ID = '"
						+ us.getSsoUser().getId()
						+ "') OR PE.FK_PARENT_ID = (SELECT PEM.FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER PEM WHERE PEM.FK_SSO_USER_ID = '"
						+ us.getSsoUser().getId() + "'))";
				try {
					int res = this.getGeneralService().executeBySQL(sql);
					if (res > 0) {
						success++;
					} else {
						msg.append("第" + (i + 1) + "行数据导入失败！<br/>");
					}
				} catch (EntityException e) {
					e.printStackTrace();
					this.setMsg("操作失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				// msg.append("第" + (i + 1) + "行数据导入失败！<br/>");
				continue;
			}
		}
		// StringBuffer sqlBuffer = new StringBuffer();
		// sqlBuffer.append("UPDATE pe_bzz_student t ");
		// if (us.getUserLoginType().equals("4")) {
		// sqlBuffer.append("SET t.sub_groups = '");
		// } else {
		// sqlBuffer.append("SET t.groups = '");
		// }
		// sqlBuffer.append(group);
		// sqlBuffer.append("' ");
		// sqlBuffer.append("WHERE upper(t.id) IN (");
		// sqlBuffer.append(idStr.toUpperCase());
		// sqlBuffer.append("'') ");
		// int num = 0;
		// try {
		// num = this.getGeneralService().executeBySQL(sqlBuffer.toString());
		// this.setMsg("成功分组" + num + "名学员！");
		// } catch (EntityException e) {
		// e.printStackTrace();
		// this.setMsg("操作失败！");
		// }
		this.setMsg("成功分组" + success + "名学生<br />" + msg.toString());
		this.setTogo(this.getServletPath() + ".action?flag=" + this.flag + "");
		return "m_msg";
	}

	/**
	 * excel导入的验证方法
	 * 
	 * @author linjie
	 * @return
	 */
	public String exeCheck() {
		try {
			int num = this.saveExcelCheck(this.get_upload());
			if (this.getMsg() == null) {
				this.setMsg("共有" + num + "条数据导入成功！");
			} else {
				this.setMsg(this.getMsg() + "共有" + num + "条数据导入成功！");
			}
			if (this.count == 0) {
				this.setTogo(this.getServletPath() + "_toExcelCheckIn.action?flag=" + this.flag + "");
			} else {
				this.setTogo(this.getServletPath() + ".action?flag=" + this.flag + "");
			}
		} catch (EntityException e) {
			this.setMsg(e.getMessage());
			this.setTogo(this.getServletPath() + "_toExcelCheckIn.action?flag=" + this.flag + "");
		}
		return "m_msg";
	}

	/**
	 * excel导入的excel数据校验
	 * 
	 * @author linjie
	 * @return
	 */
	public int saveExcelCheck(File file) throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String peEnterpriseId = ServletActionContext.getRequest().getParameter("peEnterpriseId");
		StringBuffer msg = new StringBuffer();
		Workbook work = null;
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量更新失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp1 = "";
		String sql = "";
		String idStr = "";
		for (int i = 1; i < rows; i++) {
			try {
				temp1 = sheet.getCell(0, i).getContents().trim();
				if (temp1 == null || "".equals(temp1)) {
					msg.append("第" + (i + 1) + "行数据，系统编号不能为空！<br/>");
					continue;
				} else {
					idStr += "'" + temp1 + "',";
				}
				// sql = "update pe_bzz_student set fk_enterprise_id
				// ='"+peEnterpriseId+"' where reg_no='"+temp1+"'";
				// this.count += this.getGeneralService().executeBySQL(sql);
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
				continue;
			}
		}
		if (idStr.length() > 2) {
			idStr = idStr.substring(0, idStr.length() - 1);
		}
		this.checkBeforeCheckIn(idStr, us);
		int num = saveCheckIn(peEnterpriseId, idStr);
		if (msg.length() > 0) {
			msg.append("批量调配学员失败，请修改以上错误之后重新上传！<br/>");
			this.setMsg(msg.toString());
		}
		return num;
	}

	public String toUpload() {// 学员导入
		return "toUpload";
	}

	/**
	 * excel导入的保存方法
	 * 
	 * @author linjie
	 * @return
	 */
	public String exeUpload() {
		try {

			this.saveUpload(this.get_upload());
			if (this.getMsg() == null) {
				this.setMsg("共有" + this.count + "条数据导入成功！");
			} else {
				this.setMsg(this.getMsg() + "共有" + this.count + "条数据导入成功！");
			}
			if (this.count == 0) {
				this.setTogo(this.getServletPath() + "_toUpload.action?flag=" + this.flag + "");
			} else {
				this.setTogo(this.getServletPath() + ".action?flag=" + this.flag + "");
			}
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo(this.getServletPath() + "_toUpload.action?flag=" + this.flag + "");
		}
		return "m_msg";
	}

	/**
	 * excel导入的保存方法
	 * 
	 * @author linjie
	 * @return
	 */
	public void saveUpload(File file) throws EntityException {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String loginType = ServletActionContext.getRequest().getParameter("loginType");
		StringBuffer msg = new StringBuffer();
		Workbook work = null;
		PeBzzStudent student = new PeBzzStudent();
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		List<PeBzzStudent> studentList = new ArrayList<PeBzzStudent>();
		for (int i = 1; i < rows; i++) {
			student = new PeBzzStudent();
			try {
				if (null != us.getUserLoginType() && "56".indexOf(us.getUserLoginType()) >= 0) {// 监管学员导入
					// 0：*姓名
					temp = sheet.getCell(0, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，姓名不能为空！<br/>");
						continue;
					} else {
						student.setTrueName(temp);
					}

					// 1：*Email
					temp = sheet.getCell(1, i).getContents().trim();
					if (null == temp || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，电子邮件不能为空！<br/>");
						continue;
					} else {
						if (checkMail(temp)) {// 邮件已存在对应账号
							msg.append("第" + (i + 1) + "行数据，电子邮件已存在对应账号，请重新输入。<br/>");
							continue;
						} else {
							student.setEmail(temp);
						}
					}

					// 2：*部门
					temp = sheet.getCell(2, i).getContents().trim();
					if (null == temp || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，工作部门不能为空！<br/>");
						continue;
					} else {
						student.setDepartment(temp);
					}

					// 3：*职务
					/*
					 * temp = sheet.getCell(3, i).getContents().trim(); if (null ==
					 * temp || "".equals(temp)) { msg.append("第" + (i + 1) +
					 * "行数据，职务不能为空！<br/>"); continue; } else { String temp2 =
					 * sheet.getCell(4, i).getContents().trim(); if (null !=
					 * temp2 && !"".equals(temp2)) temp2 = temp2 + "-";
					 * student.setPosition(temp2 + temp); }
					 */
					temp = sheet.getCell(3, i).getContents().trim();
					if (null == temp || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，职务不能为空！<br/>");
						continue;
					} else {
						student.setPosition(temp);
					}
					// 从事事务
					temp = sheet.getCell(4, i).getContents().trim();
					student.setWork(temp);

					// 5：*办公电话
					temp = sheet.getCell(5, i).getContents().trim();
					if (null == temp || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，办公电话不能为空！<br/>");
						continue;
					} else {
						student.setPhone(temp);
					}

					// 6：手机
					temp = sheet.getCell(6, i).getContents().trim();
					student.setMobilePhone(temp);

					if (null != loginType && "mail".equalsIgnoreCase(loginType)) {// 邮件当做账号
						// 判断账号是否存在
						student.setRegNo(student.getEmail());
					} else {// 系统生成账号
						student.setRegNo(this.codenum());
					}

					student.setName(student.getRegNo() + "/" + student.getTrueName());
					student.setEnumConstByFlagPhotoConfirm(this.getEnumConstService().getDefaultByNamespace("FlagPhotoConfirm"));
					student.setEnumConstByFlagIsGoodStu(this.getEnumConstService().getDefaultByNamespace("FlagIsGoodStu"));
					student.setEnumConstByCheckState(this.getEnumConstService().getByNamespaceCode("CheckState", "1"));
					student.setEnumConstByIsEmployee(this.getEnumConstService().getDefaultByNamespace("IsEmployee"));
					student.setEnumConstByFlagStudentType(this.getEnumConstService().getDefaultByNamespace("FlagStudentType"));
					student.setEnumConstByFlagQualificationsType(this.getEnumConstService().getByNamespaceCode("FlagQualificationsType",
							"100"));
					SsoUser ssoUser = new SsoUser();
					ssoUser.setLoginId(student.getRegNo());
					ssoUser.setLoginNum((long) 0);
					ssoUser.setAmount("0");
					ssoUser.setSumAmount("0");
					PePriRole pePriRole = new PePriRole();
					pePriRole.setId("0");
					ssoUser.setPePriRole(pePriRole);
					ssoUser.setEnumConstByFlagIsvalid(this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "1"));
					ssoUser.setPasswordMd5(MyUtil.md5(Const.defaultPwd));
					ssoUser.setPasswordBk(MyUtil.md5(Const.defaultPwd));
					student.setSsoUser(ssoUser);
					student.setPeEnterprise(this.getPeEnterpriseByUs());
					studentList.add(student);
				} else {// 普通学员导入
					// 0：姓名
					temp = sheet.getCell(0, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						msg.append("第" + (i + 1) + "行数据，姓名不能为空！<br/>");
						continue;
					} else {
						student.setTrueName(temp);
					}

					// 4：证件号码
					temp = sheet.getCell(4, i).getContents().trim();
					if ((temp == null || "".equals(temp)) && !"131AF5EC87836928E0530100007F9F54".equals(us.getRoleId())) {
						msg.append("第" + (i + 1) + "行数据，身份证号不能为空！<br/>");
						continue;
					} else if (!temp.matches("(^\\d{17}([0-9]|X|x)$)")) {
						msg.append("第" + (i + 1) + "行数据，身份证号格式错误！<br/>");
						continue;
					} else {
						DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
						dc.add(Restrictions.eq("cardNo", temp).ignoreCase()); // 不区分大小写
						List<PeBzzStudent> peList = this.getGeneralService().getList(dc);
						if (peList != null && !peList.isEmpty()) {
							msg.append("第" + (i + 1) + "行数据，身份证号重复！<br/>");
							continue;
						} else {
							student.setCardNo(temp.toUpperCase());// 默认存储大写
						}
					}

					// 3：学历
					temp = sheet.getCell(3, i).getContents().trim();
					if (temp == null || "".equals(temp)) {
						// msg.append("第" + (i + 1) + "行数据，学历不能为空！<br/>");
						// continue;
					} else if (!temp
							.matches("^[\u521D\u4E2D|\u9AD8\u4E2D|\u804C\u9AD8|\u4E2D\u4E13|\u6280\u6821|\u5927\u4E13|\u672c\u79d1|\u7855\u58EB|\u535A\u58EB]{1,}$")) {
						msg.append("第" + (i + 1) + "行数据，学历只能填写：初中、高中、职高、中专、技校、大专、本科、硕士、博士！<br/>");
						continue;
					} else {
						student.setEducation(temp);
					}

					// 6：Email
					temp = sheet.getCell(5, i).getContents().trim();
					student.setEmail(temp);
					/*
					 * if (temp == null || "".equals(temp)) { msg.append("第" +
					 * (i + 1) + "行数据，邮箱不能为空！<br/>"); continue; }else
					 * if(!temp.matches
					 * ("^\\w+([-+.]\\w+)*@\\w+([-]\\w+)*(\\.\\w+([-]\\w+)*){1,3}$"
					 * )){ msg.append("第" + (i + 1) + "行数据，邮箱格式不正确！<br/>");
					 * continue; }else{ student.setEmail(temp); }
					 */

					// 7：手机
					temp = sheet.getCell(6, i).getContents().trim();
					student.setMobilePhone(temp);
					/*
					 * if (temp == null || "".equals(temp)) { msg.append("第" +
					 * (i + 1) + "行数据，手机不能为空！<br/>"); continue; }else
					 * if(!temp.matches("^(\\+86)?0?1[3|5|8]\\d{9}$")){
					 * msg.append("第" + (i + 1) + "行数据，请输入正确的移动电话！<br/>");
					 * continue; }else{ student.setMobilePhone(temp); }
					 */

					// 1：国籍
					temp = sheet.getCell(1, i).getContents().trim();
					student.setCardType(temp);
					/*
					 * if (temp == null || "".equals(temp)) { msg.append("第" +
					 * (i + 1) + "行数据，国籍不能为空！<br/>"); continue; }else{
					 * student.setCardType(temp); }
					 */

					// 12：办公电话
					temp = sheet.getCell(11, i).getContents().trim();
					student.setPhone(temp);

					// 11：职称
					temp = sheet.getCell(10, i).getContents().trim();
					student.setTitle(temp);

					// 9：部门
					temp = sheet.getCell(8, i).getContents().trim();
					student.setDepartment(temp);

					// 10：职务
					temp = sheet.getCell(9, i).getContents().trim();
					student.setPosition(temp);
					/*
					 * temp = sheet.getCell(10, i).getContents().trim();
					 * student.setAge(temp);
					 */

					// 8：邮编
					temp = sheet.getCell(7, i).getContents().trim();
					student.setZipcode(temp);

					// 13：通讯地址
					temp = sheet.getCell(12, i).getContents().trim();
					student.setAddress(temp);
					/*
					 * temp = sheet.getCell(13, i).getContents().trim();
					 * student.setFolk(temp);
					 */

					// 2：性别
					temp = sheet.getCell(2, i).getContents().trim();
					student.setGender(temp);

					// 5：出生日期
					// temp = sheet.getCell(5, i).getContents().trim();

					// if (!"".equals(temp)) {
					// SimpleDateFormat sdf = new
					// SimpleDateFormat("dd/MM/yyyy");
					// Date date = sdf.parse(temp);
					// student.setBirthdayDate(date);
					// }
					student.setRegNo(this.codenum());
					student.setName(student.getRegNo() + "/" + student.getTrueName());
					student.setEnumConstByFlagPhotoConfirm(this.getEnumConstService().getDefaultByNamespace("FlagPhotoConfirm"));
					student.setEnumConstByFlagIsGoodStu(this.getEnumConstService().getDefaultByNamespace("FlagIsGoodStu"));
					student.setEnumConstByCheckState(this.getEnumConstService().getByNamespaceCode("CheckState", "1"));
					student.setEnumConstByIsEmployee(this.getEnumConstService().getDefaultByNamespace("IsEmployee"));
					student.setEnumConstByFlagStudentType(this.getEnumConstService().getDefaultByNamespace("FlagStudentType"));
					student
							.setEnumConstByFlagQualificationsType(this.getEnumConstService()
									.getDefaultByNamespace("FlagQualificationsType"));
					SsoUser ssoUser = new SsoUser();
					ssoUser.setLoginId(student.getRegNo());
					// ssoUser.setPassword(MyUtil.md5("1111"));
					// ssoUser.setPassword("1111");
					ssoUser.setLoginNum((long) 0);
					ssoUser.setAmount("0");
					ssoUser.setSumAmount("0");
					PePriRole pePriRole = new PePriRole();
					pePriRole.setId("0");
					ssoUser.setPePriRole(pePriRole);
					ssoUser.setEnumConstByFlagIsvalid(this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "1"));
					ssoUser.setPasswordMd5(MyUtil.md5(Const.defaultPwd));
					ssoUser.setPasswordBk(MyUtil.md5(Const.defaultPwd));
					student.setSsoUser(ssoUser);
					student.setPeEnterprise(this.getPeEnterpriseByUs());
					studentList.add(student);
					// this.peBzzStudent=this.getGeneralService().save(student);
					// if(this.peBzzStudent!=null){
					// this.count++;
					// }
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
				continue;
			}
		}
		if (null != us.getUserLoginType() && "56".indexOf(us.getUserLoginType()) >= 0) {// 监管判断邮件是否重复
			List<String> mailList = new ArrayList<String>();
			for (int j = 0; j < studentList.size(); j++) {
				if (mailList.contains(studentList.get(j).getEmail())) {
					msg.append("第" + (j + 2) + "行电子邮件重复！<br/>");
					break;
				} else {
					mailList.add(studentList.get(j).getEmail());
				}
			}
		} else {// 普通学员判断身份证号是否重复
			List<String> cardNoList = new ArrayList<String>();
			for (int j = 0; j < studentList.size(); j++) {
				if (cardNoList.contains(studentList.get(j).getCardNo())) {
					msg.append("第" + (j + 2) + "行身份证号重复！<br/>");
					break;
				} else {
					cardNoList.add(studentList.get(j).getCardNo());
				}
			}
		}
		if (msg.length() > 0) {
			msg.append("批量导入学员失败，请修改以上错误之后重新上传！<br/>");
			this.setMsg(msg.toString());
		} else {
			for (int j = 0; j < studentList.size(); j++) {
				this.getGeneralService().saveList(studentList);
				this.count++;
			}
		}
	}

	/**
	 * 自动生成系统编号
	 * 
	 * @author linjie
	 * @return
	 */
	private String codenum() {

		String tempcode = "";
		List temList = new ArrayList();
		try {
			temList = this.getGeneralService().getBySQL("SELECT S_PE_BZZ_STUDENT.NEXTVAL FROM DUAL");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DecimalFormat df = new DecimalFormat("0000000");
		tempcode = "sace" + df.format(temList.get(0)).toString();
		return tempcode;

		// String tempcode = null;
		// DetachedCriteria codedc
		// =DetachedCriteria.forClass(PeBzzStudent.class);
		// codedc.setProjection(Projections.max("regNo"));
		// codedc.add(Restrictions.ilike("regNo", "sace", MatchMode.START));
		// List list;
		// try {
		// list = this.getGeneralService().getList(codedc);
		// if(list.size()==0||list.get(0)==null){
		// tempcode = "sace"+"000000"+1;
		// }else{
		// String temp = "1"+ list.get(0).toString().substring(4,11);
		// temp = Integer.parseInt(temp)+1+"";
		// tempcode = "sace"+temp.trim().substring(1, temp.length());
		// }
		// } catch (EntityException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * 获得当前登录的机构信息
	 * 
	 * @author linjie
	 * @return
	 */
	private PeEnterprise getPeEnterpriseByUs() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = null;
		dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc.createCriteria("peEnterprise");
		dc.createCriteria("ssoUser", "ssoUser");
		dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
		List<PeEnterpriseManager> list = null;
		PeEnterpriseManager peEnterpriseManager = null;
		try {
			list = this.getGeneralService().getList(dc);
			if (null != list && list.size() > 0) {
				peEnterpriseManager = list.get(0);
			} else {
				peEnterpriseManager = new PeEnterpriseManager();
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return peEnterpriseManager.getPeEnterprise();
	}

	/**
	 * 学员签入校验
	 * 
	 * @author linjie
	 * @return
	 */
	public String checkIn() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String peEnterpriseId = ServletActionContext.getRequest().getParameter("peEnterpriseId");
		String idStr = (String) ServletActionContext.getRequest().getSession().getAttribute("idStr");
		// idStr = idStr.replace(",", "','");
		int num;
		try {
			this.checkBeforeCheckIn(idStr, us);// 检查有效性
			num = saveCheckIn(peEnterpriseId, idStr);
		} catch (EntityException e) {
			this.setTogo("back");
			this.setMsg(e.getMessage());
			return "m_msg";
		}
		this.setTogo(this.servletPath + ".action?flag=" + this.flag + "");
		this.setMsg("成功签入" + num + "名学员！");
		return "m_msg";
	}

	/**
	 * 学员嵌入保存
	 * 
	 * @author linjie
	 * @return
	 */
	private int saveCheckIn(String peEnterpriseId, String idStr) throws EntityException {
		if (idStr.indexOf('\'') < 0) {
			idStr = "'" + idStr.replaceAll(",", "','") + "'";
		}
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("UPDATE pe_bzz_student t ");
		sqlBuffer.append("SET    t.fk_enterprise_id = '");
		sqlBuffer.append(peEnterpriseId);
		sqlBuffer.append("' ");
		sqlBuffer.append("WHERE  upper(t.reg_no) IN (");
		sqlBuffer.append(idStr.toUpperCase());
		sqlBuffer.append(") ");
		sqlBuffer.append("or  upper(t.id) IN (");
		sqlBuffer.append(idStr.toUpperCase());
		sqlBuffer.append(") ");

		int num = this.getGeneralService().executeBySQL(sqlBuffer.toString());
		return num;
	}

	/**
	 * 机构调配的校验方法
	 * 
	 * @author linjie
	 * @return
	 */
	public void checkBeforeCheckIn(String idStr, UserSession us) throws EntityException {
		List<String> myList = getMyEnterprise(us);

		// 遍历选中学员id，并查处所属机构放进list
		// 一级管理员只能将一级学员调配至下属二级机构，不能逆向操作。
		boolean change = false;
		if (idStr != null && !"".equals(idStr)) {
			// String[] idss = idStr.replaceAll("'", "").split(",");
			if (idStr.indexOf('\'') < 0) {
				idStr = "'" + idStr.replaceAll(",", "','") + "'";
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
			dc.add(Restrictions.sqlRestriction(" upper(id) in (" + idStr.toUpperCase() + ")"));
			List<PeBzzStudent> list = this.getGeneralService().getList(dc);
			String enterpriseIds = "";
			for (int i = 0; i < list.size(); i++) {
				PeBzzStudent student = list.get(i);
				String eid = student.getPeEnterprise().getId();// 目标学生所在机构ID
				if (us.getUserLoginType().equals("2")) {
					// 一级管理员只能将一级学员调配至下属二级机构，不能逆向操作。

					if (!change && !myList.contains(eid)) {
						throw new EntityException("一级管理员只能将一级学员调配至下属二级机构，不能逆向操作。</br>“" + student.getRegNo() + "”学员不属于权限范围");
					}
				} else if (us.getUserLoginType().equals("4")) {
					// 二级管理员可以操作自己和上机机构中学员，可双向操作。
					if (!change && !myList.contains(eid)) {
						throw new EntityException("二级集体管理员可以在本机构与上级机构之间调配学员，不能才做其他机构。");
					}
				} else {
					enterpriseIds += eid + ",";
					ServletActionContext.getRequest().setAttribute("enterpriseIds", enterpriseIds);
				}
			}
		}
		if (idStr != null) {
			/* 判断账号是否有效 */
			String querySql = "select login_id from sso_user s inner join pe_bzz_student t on s.id=t.fk_sso_user_id where s.flag_isvalid<>2 and upper(t.id) in ("
					+ idStr.toUpperCase() + ")";
			/* 判断账号是否有余额 */
			String queryAmountSql = "select login_id from sso_user s inner join pe_bzz_student t on s.id=t.fk_sso_user_id where s.sum_amount-s.amount > 0 and upper(t.id) in ("
					+ idStr.toUpperCase() + ")";
			/* 学生作用范围验证 */

			/* 站点作用范围验证 */

			List list = this.getGeneralService().getBySQL(querySql);
			List listAmount = this.getGeneralService().getBySQL(queryAmountSql);
			String re = "";
			if (list != null && list.size() > 0) {
				for (Object s : list) {
					re += s.toString() + ",";
				}
				throw new EntityException("所选学生中存在无效账号:</br>(" + re + "),</br>操作失败！");
			} else if (listAmount != null && listAmount.size() > 0) {
				for (Object s : listAmount) {
					re += s.toString() + ",";
				}
				throw new EntityException("所选学生中账号中有带余额的账号:</br>(" + re + ")</br>操作失败,请剥离后调配！");
			}
		}
	}

	/**
	 * 学员分组
	 * 
	 * @author linjie
	 * @return
	 */
	public String doGroup() {
		// 已有分組和新建分組取一个
		String group = "";
		String group1 = ServletActionContext.getRequest().getParameter("group");
		String group2 = ServletActionContext.getRequest().getParameter("selectGroup");
		if (!"".equals(group1)) {
			group = group1;
		} else {
			group = group2;
		}
		String idStr = (String) ServletActionContext.getRequest().getSession().getAttribute("idStr");
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		idStr = idStr.replace(",", "','");
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("UPDATE pe_bzz_student t ");
		if (us.getUserLoginType().equals("4")) {
			sqlBuffer.append("SET    t.sub_groups = '");
		} else {
			sqlBuffer.append("SET    t.groups = '");
		}
		sqlBuffer.append(group);
		sqlBuffer.append("' ");
		sqlBuffer.append("WHERE  upper(t.id) IN ('");
		sqlBuffer.append(idStr.toUpperCase());
		sqlBuffer.append("') ");
		int num = 0;
		try {
			num = this.getGeneralService().executeBySQL(sqlBuffer.toString());
			this.setMsg("成功分组" + num + "名学员！");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("操作失败！");
		}
		this.setTogo(this.getServletPath() + ".action?flag=" + this.flag + "");
		return "m_msg";
	}

	/**
	 * 重写框架方法--列更新
	 * 
	 * @author linjie
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			String idStr = getIds().replace(",", "','");
			if (action.equals("clearGroup")) {
				StringBuffer sqlBuffer = new StringBuffer();
				sqlBuffer.append("UPDATE pe_bzz_student t ");
				if (us.getUserLoginType().equals("4")) {
					sqlBuffer.append("SET    t.sub_groups = '");
				} else {
					sqlBuffer.append("SET    t.groups = '");
				}
				sqlBuffer.append("' ");
				sqlBuffer.append("WHERE  upper(t.id) IN ('");
				sqlBuffer.append(idStr.toUpperCase());
				sqlBuffer.append("') ");
				int num = 0;
				try {
					num = this.getGeneralService().executeBySQL(sqlBuffer.toString());
				} catch (EntityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					this.setMsg("操作失败！");
				}
			}
			try {
				/**
				 * DetachedCriteria tempdc =
				 * DetachedCriteria.forClass(PeBzzStudent.class);
				 * if(action.equals("pwsd")){
				 * tempdc.setProjection(Projections.property("ssoUser"));
				 * tempdc.add(Restrictions.in("id", ids)); List<SsoUser> sslist =
				 * new ArrayList<SsoUser>(); sslist
				 * =this.getGeneralService().getList(tempdc); Iterator<SsoUser>
				 * iterator = sslist.iterator(); while(iterator.hasNext()){
				 * SsoUser ssoUser = iterator.next(); //String pswd =
				 * RandomString.getString(8); //密码重置操作2011-12-31修改，加密 // String
				 * pswd = RandomString.generatePassStr(); //
				 * ssoUser.setPassword(pswd); //密码重置功能，把学员的密码重置为自己的登录账号，即学号 //
				 * ssoUser.setPassword(ssoUser.getLoginId());
				 * //ssoUser.setPassword("111111");
				 * //ssoUser.setPassword(MyUtil.md5("111111"));
				 * ssoUser.setPasswordBk(MyUtil.md5(Const.defaultPwd));
				 * ssoUser.setPasswordMd5(MyUtil.md5(Const.defaultPwd));
				 * this.peBzzstudentbacthService.updateSsoUser(ssoUser); } msg =
				 * "重置密码"; }
				 */
				// modify by wangjt at 2013-12-31
				DetachedCriteria tempdc = DetachedCriteria.forClass(PeBzzStudent.class);
				Boolean isEmplyee = false;
				if (action.equals("pwsd")) {
					// 从业人员不能重置密码
					tempdc.add(Restrictions.in("id", ids));
					List<PeBzzStudent> eclist = new ArrayList<PeBzzStudent>();
					eclist = this.getGeneralService().getList(tempdc);
					// Iterator<PeBzzStudent> peIterator = eclist.iterator();
					// while (peIterator.hasNext()) {
					// PeBzzStudent peBzzStudent = peIterator.next();
					// if
					// (peBzzStudent.getEnumConstByIsEmployee().getName().equals("是"))
					// {
					// isEmplyee = true;
					// msg = "所选学员中存在有资格学员，请重新选择再重置！";
					// break;
					// }
					// }
					// if (isEmplyee == false) {
					Iterator<PeBzzStudent> itr = eclist.iterator();
					while (itr.hasNext()) {
						PeBzzStudent pbs = itr.next();
						SsoUser ssoUser = pbs.getSsoUser();
						ssoUser.setPasswordBk(MyUtil.md5(Const.defaultPwd));
						ssoUser.setPasswordMd5(MyUtil.md5(Const.defaultPwd));
						this.peBzzstudentbacthService.updateSsoUser(ssoUser);
					}
					msg = "重置密码";

					// }
				}
				// modify end
				if (action.equals("FlagIsvalid.true")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");

					tempdc.setProjection(Projections.property("ssoUser"));
					tempdc.add(Restrictions.in("id", ids));
					List<SsoUser> sslist = new ArrayList<SsoUser>();
					sslist = this.getGeneralService().getList(tempdc);
					Iterator<SsoUser> iterator = sslist.iterator();
					while (iterator.hasNext()) {
						SsoUser ssoUser = iterator.next();
						ssoUser.setEnumConstByFlagIsvalid(enumConst);
						this.peBzzstudentbacthService.updateSsoUser(ssoUser);
					}
					msg = "账户启用";
				}
				if (action.equals("FlagIsvalid.false")) {
					EnumConst enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");

					tempdc.setProjection(Projections.property("ssoUser"));
					tempdc.add(Restrictions.in("id", ids));
					List<SsoUser> sslist = new ArrayList<SsoUser>();
					sslist = this.getGeneralService().getList(tempdc);
					Iterator<SsoUser> iterator = sslist.iterator();
					while (iterator.hasNext()) {
						SsoUser ssoUser = iterator.next();
						ssoUser.setEnumConstByFlagIsvalid(enumConst);
						this.peBzzstudentbacthService.updateSsoUser(ssoUser);
					}
					if ("3".equals(us.getUserLoginType()))
						msg = "账户停用";
					else
						msg = "学员离职";
				}
				if ("Dimission.false".equals(action)) {
					// 检查是否离职操作的人员都是监管的学员
					String sql = "SELECT 1 FROM PE_BZZ_STUDENT PBS INNER JOIN PE_ENTERPRISE PE ON PBS.FK_ENTERPRISE_ID = PE.ID INNER JOIN ENUM_CONST EC ON PE.ENTYPE = EC.ID WHERE EC.CODE = 'V' AND PBS.FK_SSO_USER_ID IN ("
							+ ids + ")";
					List list = this.getGeneralService().getBySQL(sql);
					if (null != list || list.size() > 0) {
						map.clear();
						map.put("success", "false");
						map.put("info", "操作失败，学员离职只适用于监管学员，请检查所选学员后重试");
						return map;
					}
					String uptSql = "UPDATE SSO_USER SU SET SU.FLAG_ISVALID = '3' WHERE SU.ID IN (" + ids + ")";
					int uptResult = 0;
					uptResult = this.getGeneralService().executeBySQL(uptSql);
					if (uptResult > 0) {
						map.clear();
						map.put("success", "true");
						map.put("info", "共有" + uptResult + "名学员离职操作成功");
						return map;
					} else {
						map.clear();
						map.put("success", "true");
						map.put("info", "共有0名学员离职操作成功");
						return map;
					}
				}
				// Lee 2014年9月20日 学员调出
				if (action.equals("stuCheckOut")) {
					// 选中项非空验证
					if (null != ids && ids.length > 0) {
						StringBuffer sbIds = new StringBuffer();
						// 拼接更新条件'id1','id2','id3'....
						for (String checkOutId : ids) {
							sbIds.append("'" + checkOutId + "',");
						}
						String idsString = sbIds.substring(0, sbIds.length() - 1);
						// 更新SQL 将机构ID设为空机构的机构ID
						String checkSql = "UPDATE PE_BZZ_STUDENT SET FK_ENTERPRISE_ID = (SELECT ID FROM PE_ENTERPRISE WHERE NAME = '空') WHERE ID IN ("
								+ idsString + ") AND IS_EMPLOYEE = '40288ccf3ac50e95013ac5148fde0003'";
						int uptResult = 0;
						try {
							uptResult = this.getGeneralService().executeBySQL(checkSql);
							if (uptResult > 0) {
								map.clear();
								map.put("success", "true");
								map.put("info", "共有" + uptResult + "名学员调出成功");
								return map;
							} else {
								map.clear();
								map.put("success", "true");
								map.put("info", "共有0名学员调出成功");
								return map;
							}
						} catch (Exception e) {
							map.clear();
							map.put("success", "false");
							map.put("info", "学员调出操作失败");
							return map;
						}
					}
				}
				if (action.equals("info")) {
					tempdc.createCriteria("ssoUser", "ssoUser");
					tempdc.add(Restrictions.in("id", ids));
					this.setPlist(this.getGeneralService().getList(tempdc));
				}
				if (isEmplyee == true) {
					map.clear();
					map.put("success", "false");
					map.put("info", msg);
				} else {
					map.put("success", "true");
					map.put("info", msg + "共有" + ids.length + "条记录操作成功");
				}

			} catch (Exception e) {
				map.clear();
				map.put("success", "false");
				map.put("info", msg + "操作失败");
				return map;
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少有一条记录被选择");
			return map;
		}
		return map;
	}

	public void setEntityClass() {
		this.entityClass = PeBzzStudent.class;

	}

	public void setServletPath() {
		this.servletPath = "/entity/basic/peBzzStudent";
	}

	public void setBean(PeBzzStudent instance) {
		super.superSetBean(instance);
	}

	public PeBzzStudent getBean() {
		return super.superGetBean();
	}

	public String batch() {
		/**
		 * 取得管理员类型.总站管理员不限制
		 */
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";// 保存管理员类型
		if (us != null) {
			userLoginType = us.getUserLoginType();
		}
		if (userLoginType.equals("3") || userLoginType.equals("2")) {
			DetachedCriteria bathdc = DetachedCriteria.forClass(PeBzzBatch.class);
			bathdc.addOrder(Order.asc("startDate"));
			try {
				this.setPeBzzBatch(this.getGeneralService().getList(bathdc));
			} catch (EntityException e) {
				e.printStackTrace();
			}
			return "batch";
		} else {
			this.setMsg("您的权限不够，无法进行此项操作!!!!");
		}
		return "m_msg";
	}

	/**
	 * excel上传
	 * 
	 * @author linjie
	 * @return
	 */
	public String uploadExcel() {
		int count = 0;
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
			count = this.peBzzstudentbacthService.Bacthsave(filetest, batchid);
		} catch (Exception e) {
			this.setMsg(e.getMessage());
			return "uploadStudent_result";
		}
		this.setMsg("共" + count + "条数据上传成功！");
		this.setTogo("back");
		return "uploadStudent_result";
	}

	/**
	 * 框架方法：或者查询列表所需数据
	 * 
	 * @author linjie
	 * @return
	 */
	public DetachedCriteria initDetachedCriteria() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzStudent.class);
		dc.createCriteria("ssoUser", "ssoUser").createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN);
		;
		dc.createAlias("peEnterprise", "peEnterprise", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("enumConstByFlagQualificationsType", "enumConstByFlagQualificationsType", DetachedCriteria.LEFT_JOIN);
		String enumConstByFlagIsvalid = (String) ServletActionContext.getRequest().getParameter(
				"search__ssoUser.enumConstByFlagIsvalid.name");
		// if (enumConstByFlagIsvalid == null ||
		// enumConstByFlagIsvalid.equals("")) {
		// dc.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
		// }
		if ("56".indexOf(us.getUserLoginType()) >= 0) {
			dc.add(Restrictions.eq("enumConstByFlagIsvalid.code", "1"));
		}
		if (null != enterPage) {
			String sqlManager = "SELECT FK_ENTERPRISE_ID FROM PE_ENTERPRISE_MANAGER WHERE ID = '" + enterPage + "'";
			List listManager;
			try {
				listManager = this.getGeneralService().getBySQL(sqlManager);
				if (null != listManager && listManager.size() > 0)
					dc.add(Restrictions.in("peEnterprise.id", listManager.toArray(new String[listManager.size()])));
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			if (!us.getUserLoginType().equals("3")) {
				String peEnterpriseCode = (String) ServletActionContext.getRequest().getSession().getAttribute("peEnterprisemanager");

				DetachedCriteria dc2 = DetachedCriteria.forClass(PeEnterprise.class);
				dc2.add(Restrictions.eq("code", peEnterpriseCode));
				dc2.setProjection(Property.forName("id"));
				if ("self".equalsIgnoreCase(flag)) {
					dc.add(Restrictions.eq("peEnterprise.code", peEnterpriseCode));
				} else {
					String sql = "\n" + "select pe.id\n" + "  from pe_enterprise pe\n"
							+ " inner join pe_enterprise_manager pem on pem.fk_enterprise_id = pe.id\n" + " where pem.login_id = '"
							+ us.getLoginId() + "'\n" + "\n" + "union\n" + "\n" + "select pe.id\n"
							+ "  from pe_enterprise pe, pe_enterprise pePar, pe_enterprise_manager pem\n"
							+ " where pe.fk_parent_id = pePar.Id\n" + "   and pePar.Id = pem.fk_enterprise_id\n"
							+ "   and pem.login_id = '" + us.getLoginId() + "'";

					List list = null;
					try {
						list = this.getGeneralService().getBySQL(sql);
					} catch (EntityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// dc.add(Restrictions.or(Restrictions.eq("peEnterprise.code",
					// peEnterpriseCode),
					// Property.forName("peEnterprise.peEnterprise.id").in(dc2)));
					dc.add(Restrictions.in("peEnterprise.id", list.toArray(new String[list.size()])));
				}

			}
		}
		/*
		 * if("self".equals(flag)){
		 * dc.createCriteria("ssoUser","ssoUser").createAlias
		 * ("enumConstByFlagIsvalid", "enumConstByFlagIsvalid",
		 * DetachedCriteria.LEFT_JOIN);; dc.createAlias("peEnterprise",
		 * "peEnterprise",DetachedCriteria.LEFT_JOIN);
		 * dc.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee",
		 * DetachedCriteria.LEFT_JOIN); if (!us.getUserLoginType().equals("3")) {
		 * String peEnterpriseCode = (String)ServletActionContext.getRequest()
		 * .getSession().getAttribute("peEnterprisemanager");
		 * dc.add(Restrictions.eq("peEnterprise.code", peEnterpriseCode)); }
		 * }else{ dc.createCriteria("ssoUser","ssoUser").createAlias(
		 * "enumConstByFlagIsvalid", "enumConstByFlagIsvalid",
		 * DetachedCriteria.LEFT_JOIN);; dc.createCriteria("peEnterprise",
		 * "peEnterprise"
		 * ,DetachedCriteria.LEFT_JOIN).createAlias("peEnterprise",
		 * "subPeEnterprise",DetachedCriteria.LEFT_JOIN);
		 * dc.createCriteria("enumConstByIsEmployee", "enumConstByIsEmployee",
		 * DetachedCriteria.LEFT_JOIN); if (!us.getUserLoginType().equals("3")) {
		 * String peEnterpriseCode = (String)ServletActionContext.getRequest()
		 * .getSession().getAttribute("peEnterprisemanager");
		 * dc.add(Restrictions.eq("subPeEnterprise.code", peEnterpriseCode)); } }
		 */
		return dc;
	}

	/**
	 * 获得学生、照片信息
	 * 
	 * @author linjie
	 * @return
	 */
	private PeBzzStudent getPhotoedStudent(PeBzzStudent student) throws IOException {
		if (this.get_uploadFileName() == null)
			return student;

		String entCode = "";
		String batchCode = "";
		try {
			String sql = "select nvl(ppe.code,pe.code) as code,pb.batch_code from pe_bzz_student ps inner join pe_bzz_batch pb on ps.fk_batch_id=pb.id inner join pe_enterprise pe on ps.fk_enterprise_id=pe.id left outer join pe_enterprise ppe on pe.fk_parent_id=ppe.id \n"
					+ " where ps.id='" + student.getId() + "' ";
			Object temp[] = (Object[]) this.getGeneralService().getBySQL(sql).get(0);
			entCode = temp[0].toString();
			batchCode = temp[1].toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		String fileName = student.getRegNo() + ".jpg";

		BufferedImage bufferedImage = ImageIO.read(this.get_upload());

		String realPath = ServletActionContext.getRequest().getRealPath("/incoming/student-photo");
		File dir = new File(realPath + "/" + batchCode + "/" + entCode);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(dir.getAbsolutePath() + "/" + fileName);
		ImageIO.write(createResizedCopy(bufferedImage, 390, 567), "jpg", fos);
		fos.flush();
		fos.close();

		student.setPhoto(fileName);
		return student;
	}

	BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight) {
		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = scaledBI.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
		g.dispose();
		return scaledBI;
	}

	/**
	 * 重写框架方法：添加数据前的校验
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void checkBeforeAdd() throws EntityException {

		/*
		 * if(this.get_upload() != null &&
		 * this.get_uploadContentType().toLowerCase().indexOf("image/") == -1) {
		 * throw new EntityException("照片不是图片文件，请在学员照片处选择一个图片文件！"); }
		 */
		this.getBean().setEnumConstByFlagPhotoConfirm(getDefaultFlagPhotoConfirm());
		// 针对学员不同身份，体验账号的学员其领用人不能为空
		/*
		 * if("体验".equals(this.getBean().getEnumConstByFlagRankState().getName()) ){
		 * if(null == this.getBean().getPickUser() ||
		 * "".equals(this.getBean().getPickUser())){ throw new
		 * EntityException("对于体验账号学员其领用人不能为空！"); } }
		 */
		String cardNo = this.getBean().getCardNo();
		if (cardNo != null && !"".equals(cardNo)) {
			this.getBean().setCardNo(cardNo.trim().toUpperCase());
		}
	}

	private EnumConst getDefaultFlagPhotoConfirm() {
		EnumConst ec = new EnumConst();
		ec.setId("4028809c2d925bcf011d66fd0dda8006");
		return ec;
	}

	/**
	 * 重写框架方法：添加数据
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public Map add() {
		Map map = new HashMap();
		this.setBean((PeBzzStudent) super.setSubIds(this.getBean()));
		PeBzzStudent instance = null;
		boolean flag = false;
		try {
			checkBeforeAdd();
		} catch (EntityException e1) {
			try {
				this.getGeneralService().saveError();
			} catch (EntityException e2) {
			}
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}
		try {
			/*
			 * String mobile=this.getBean().getMobilePhone(); String
			 * phone=this.getBean().getPhone(); String
			 * email=this.getBean().getEmail();
			 * if((mobile==null||mobile.equals("")) &&
			 * (phone==null||phone.equals(""))) { map.clear();
			 * map.put("success", "false"); map.put("info",
			 * "固定电话和移动电话不能都为空，请至少填写一项！"); return map; } else { if(phone!=null &&
			 * !phone.equals("")) { if(!phone.matches(Const.telephone)) {
			 * map.clear(); map.put("success", "false"); map.put("info",
			 * "固定电话格式不正确！输入格式：3至4位区号-7至8位直播号码-1至4位分机号。"); return map; } }
			 * if(mobile!=null && !mobile.equals("")) {
			 * if(!mobile.matches(Const.mobile)) { map.clear();
			 * map.put("success", "false"); map.put("info",
			 * "移动电话格式不正确！请输入正确的移动电话。"); return map; } } } if(email!=null &&
			 * !email.equals("")) { if(!email.matches(Const.email)) {
			 * map.clear(); map.put("success", "false"); map.put("info",
			 * "电子邮箱格式不正确！请输入正确的电子邮箱。"); return map; } }
			 */

			instance = this.peBzzstudentbacthService.save(this.getBean());

			// 上
			instance = getPhotoedStudent(instance);
			if (instance.getPhoto() != null) {
				instance = this.peBzzstudentbacthService.save(instance);
			}
			map.put("success", "true");
			map.put("info", "添加成功");
			logger.info("添加成功! id= " + instance.getId());
		} catch (Exception e) {
			return super.checkAlternateKey(e, "添加");
		}
		return map;
	}

	/**
	 * 重写框架方法：删除数据
	 * 
	 * @author linjie
	 * @return
	 */
	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List idList = new ArrayList();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}

				// 新增强制删除功能，这块约束不再需要
				/*
				 * DetachedCriteria criteria =
				 * DetachedCriteria.forClass(PrBzzTchStuElective.class);
				 * criteria.createCriteria("peBzzStudent", "peBzzStudent");
				 * criteria.add(Restrictions.in("peBzzStudent.id", ids));
				 * 
				 * try { List<PrBzzTchStuElective> plist =
				 * this.getGeneralService().getList(criteria);
				 * if(plist.size()>0){ map.clear(); map.put("success", "false");
				 * map.put("info", "所选中学员已经选课,无法删除!"); return map; } } catch
				 * (EntityException e) { e.printStackTrace(); }
				 */
				map.put("success", "true");
				map.put("info", "删除成功");
				try {
					// this.peBzzstudentbacthService.deleteByIds(idList);
					UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(
							SsoConstant.SSO_USER_SESSION_KEY);
					if ("3".equals(us.getRoleId())) { // 总管理员
						this.peBzzstudentbacthService.deleteByIds(idList);
					} else {
						List<PeEnterprise> priList = us.getPriEnterprises();
						List siteList = new ArrayList();
						for (int i = 0; i < priList.size(); i++) {
							siteList.add(priList.get(i).getId());
						}
						int i = this.peBzzstudentbacthService.deleteByIds(this.getEntityClass(), siteList, idList);
						if (0 == i) {
							map.clear();
							map.put("success", "false");
							map.put("info", "对不起，您的没有删除的权限");
						}
					}
				} catch (Exception e) {
					return super.checkForeignKey(e);
				}

			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}

	/**
	 * 重写框架方法：更新数据前的校验
	 * 
	 * @author linjie
	 * @return
	 */
	@Override
	public void checkBeforeUpdate() throws EntityException {

		if (this.get_upload() != null && this.get_uploadContentType().toLowerCase().indexOf("image/") == -1) {
			throw new EntityException("照片不是图片文件，请在学员照片处选择一个图片文件！");
		}

		// 针对学员不同身份，体验账号的学员其领用人不能为空
		if ("体验".equals(this.getBean().getEnumConstByFlagRankState().getName())) {
			if (null == this.getBean().getPickUser() || "".equals(this.getBean().getPickUser())) {
				throw new EntityException("对于体验账号学员其领用人不能为空！");
			}
		}
	}

	/**
	 * 重写框架方法：更新数据
	 * 
	 * @author linjie
	 * @return
	 */
	public Map update() {

		Map map = new HashMap();

		String linkUrl = null;

		PeBzzStudent dbInstance = null;

		// 此处修改过，原来放在this.superSetBean((T)setSubIds(dbInstance,this.bean));下方
		// gaoyuan 09.11.23
		try {
			checkBeforeUpdate();
		} catch (EntityException e1) {
			try {
				this.getGeneralService().saveError();
			} catch (EntityException e2) {
			}
			map.put("success", "false");
			map.put("info", e1.getMessage());
			return map;
		}

		try {
			dbInstance = this.getGeneralService().getById(this.getBean().getId());
		} catch (EntityException e1) {
			map.put("success", "false");
			map.put("info", "更新失败");
			return map;
		}
		this.superSetBean((PeBzzStudent) setSubIds(dbInstance, this.getBean()));

		PeBzzStudent instance = null;

		try {
			this.getBean().setName(this.getBean().getRegNo() + "/" + this.getBean().getTrueName());

			PeBzzStudent pbs = getPhotoedStudent(this.getBean());
			instance = this.getGeneralService().save(pbs);

			String sql = "update test_homeworkpaper_history h " + " set h.user_id='(" + instance.getId() + ")" + instance.getTrueName()
					+ "' \n" + "where  h.t_user_id='" + instance.getId() + "'";

			int n = this.getGeneralService().executeBySQL(sql);
			// System.out.println(n);

			sql = "update test_testpaper_history h " + " set h.user_id='(" + instance.getId() + ")" + instance.getTrueName() + "' \n"
					+ "where h.user_id like '(" + instance.getId() + "%'";
			n = this.getGeneralService().executeBySQL(sql);
			// System.out.println(n);

			map.put("success", "true");
			map.put("info", "更新成功");

		} catch (Exception e) {
			return this.checkAlternateKey(e, "更新");

		}

		return map;
	}

	public String preUploadImage() {
		return "uploadImage";
	}

	/**
	 * 上传图片
	 * 
	 * @author linjie
	 * @return
	 */
	public String uploadImage() {

		// 定义临时目录
		String realPath = ServletActionContext.getRequest().getRealPath("/WEB-INF/tmp");
		String uploadDir = realPath + File.separator + "tempUploadImage" + File.separator + UUID.randomUUID();
		File theRTEUploadDir = new File(uploadDir);
		if (!theRTEUploadDir.isDirectory()) {
			theRTEUploadDir.mkdirs();
		}

		try {
			String zipFile = uploadDir + File.separator + uploadFileName;
			// 复制上传的文件到临时目录中
			if (upload != null) {
				FileInputStream fis = new FileInputStream(upload);
				FileOutputStream fos = new FileOutputStream(zipFile);
				byte[] buf = new byte[1024];
				int j = 0;
				while ((j = fis.read(buf)) != -1) {
					fos.write(buf, 0, j);
				}
				fis.close();
				fos.close();
			}

			// 解压文件
			FileManage fm = FileManageFactory.creat();
			String destPath = uploadDir + File.separator + "images";
			new File(destPath).mkdirs();
			fm.unZip(zipFile, destPath);

			// 导入图片
			File uploadImage[] = new File(destPath).listFiles();
			for (int i = 0; i < uploadImage.length; i++) {
				importStudentImage(uploadImage[i]);
				uploadImage[i].deleteOnExit();
			}

			// 删除上传及解压后的文件
			File uploadFiles[] = theRTEUploadDir.listFiles();
			for (int i = 0; i < uploadFiles.length; i++) {
				uploadFiles[i].deleteOnExit();
			}

			theRTEUploadDir.deleteOnExit();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "uploadImageRes";
	}

	/**
	 * 导入学生图片
	 * 
	 * @author linjie
	 * @return
	 */
	private void importStudentImage(File file) throws Exception {

		// 获得文件名及学号
		String filePath = file.getAbsolutePath();
		String fileName = filePath.substring(filePath.lastIndexOf(File.separatorChar) + 1, filePath.length());
		String fileRegNo = fileName.substring(0, fileName.lastIndexOf('.'));
		// System.out.println(fileName + "-" + fileRegNo);
		if (!isImageFile(fileName)) {
			uploadErrors.append("zip文件中的" + fileName + "不是图片文件!<br>");
			return;
		}

		// 根据学员获取学员信息
		String sql = "select ps.id,nvl(ppe.code,pe.code) as code,pb.batch_code from pe_bzz_student ps inner join pe_bzz_batch pb on ps.fk_batch_id=pb.id inner join pe_enterprise pe on ps.fk_enterprise_id=pe.id left outer join pe_enterprise ppe on pe.fk_parent_id=ppe.id \n"
				+ " where ps.reg_no='" + fileRegNo + "' " + this.getMngPri();
		List list = this.getGeneralService().getBySQL(sql);
		if (list != null && list.size() > 0) {
			Object[] temp = (Object[]) list.get(0);
			String id = temp[0].toString();
			String entCode = temp[1].toString();
			String batchCode = temp[2].toString();

			// 输出文件到目的目录，并对图片进行处理
			BufferedImage bufferedImage = ImageIO.read(file);

			String realPath = ServletActionContext.getRequest().getRealPath("/incoming/student-photo");
			File dir = new File(realPath + "/" + batchCode + "/" + entCode);
			if (!dir.isDirectory()) {
				dir.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(dir.getAbsolutePath() + "/" + fileRegNo + ".jpg");
			ImageIO.write(createResizedCopy(bufferedImage, 390, 567), "jpg", fos);
			fos.flush();
			fos.close();

			// 更新学员信息
			sql = "update pe_bzz_student set photo='" + fileRegNo + ".jpg' where id='" + id + "'";
			// System.out.println(sql);
			int n = this.getGeneralService().executeBySQL(sql);
			if (n == 0) {
				uploadErrors.append("学员" + fileRegNo + "照片保存失败，请稍后重试!<br />");
			}
		} else {
			uploadErrors.append("系统编号为" + fileRegNo + "的学员不存在!<br />");
		}

	}

	/**
	 * 判断是否图片
	 * 
	 * @author linjie
	 * @return
	 */
	private boolean isImageFile(String fileName) {
		if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".gif") || fileName.toLowerCase().endsWith(".png")
				|| fileName.toLowerCase().endsWith(".bmp") || fileName.toLowerCase().endsWith(".jpeg")
				|| fileName.toLowerCase().endsWith(".tiff")) {
			return true;
		}
		return false;
	}

	public List<PeBzzStudent> getPlist() {
		return plist;
	}

	public void setPlist(List<PeBzzStudent> plist) {
		this.plist = plist;
	}

	public List<PeBzzBatch> getPeBzzBatch() {
		return peBzzBatch;
	}

	public void setPeBzzBatch(List<PeBzzBatch> peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}

	public String getBatchid() {
		return batchid;
	}

	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}

	public String get_uploadContentType() {
		return _uploadContentType;
	}

	public void set_uploadContentType(String contentType) {
		_uploadContentType = contentType;
	}

	public StringBuffer getUploadErrors() {
		return uploadErrors;
	}

	public void setUploadErrors(StringBuffer uploadErrors) {
		this.uploadErrors = uploadErrors;
	}

	public String getMngPri() {
		if (this.mngPri == null) {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			mngPri = "";
			if (us.getUserLoginType().equals("3")) {
				// mngPri = "";
			} else {
				List entList = us.getPriEnterprises();
				for (int i = 0; i < entList.size(); i++) {
					PeEnterprise e = (PeEnterprise) entList.get(i);
					mngPri += "'" + e.getId() + "',";
				}
				if (mngPri.endsWith(",")) {
					mngPri = mngPri.substring(0, mngPri.length() - 1);
				}
				mngPri = " and ps.fk_enterprise_id in (" + mngPri + ")";
			}
		}
		return mngPri;
	}

	public String unConfirmPhoto() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PeBzzStudent.class);
		detachedCriteria.add(Restrictions.eq("id", studentId));
		try {
			PeBzzStudent student = this.getGeneralService().getStudentInfo(detachedCriteria);
			EnumConst enumConstByFlagPhotoConfirm = this.getMyListService().getEnumConstByNamespaceCode("FlagPhotoConfirm", "0");
			student.setPhotoUnconfirmReason(reason);
			student.setEnumConstByFlagPhotoConfirm(enumConstByFlagPhotoConfirm);
			this.getGeneralService().save(student);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "unConfirmPhoto";
	}

	/**
	 * 给二级集体机构学员分组
	 * 
	 * @return
	 */
	public String toSubGroup() {
		initGroupList();

		return "";
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List getGroupList() {
		return groupList;
	}

	public void setGroupList(List groupList) {
		this.groupList = groupList;
	}

	public List<PeEnterprise> getPeEnterpriseList() {
		return peEnterpriseList;
	}

	public void setPeEnterpriseList(List<PeEnterprise> peEnterpriseList) {
		this.peEnterpriseList = peEnterpriseList;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public Page list111111() {
		// TODO Auto-generated method stub
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String peId = "";
		String peEnterpriseCode = (String) ServletActionContext.getRequest().getSession().getAttribute("peEnterprisemanager");
		if (!us.getUserLoginType().equals("3")) {
			String sqlpe = "select pe.id\n" + "  from pe_enterprise pe\n" + " where pe.fk_parent_id =\n"
					+ "       (select id from pe_enterprise pes where pes.code = '" + peEnterpriseCode + "')";

			List list = null;
			try {
				list = this.getGeneralService().getBySQL(sqlpe);
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			peId = list.get(0).toString();
		}
		String sql1 =

		"select this_.ID           as id,\n" + "       this_.REG_NO       as regNo,\n"
				+ "       enumconstb4_.NAME  as combobox_isEmployee,\n" + "       enumconstb5_.NAME  as combobox_zige,\n"
				+ "       this_.TRUE_NAME    as trueName,\n" + "       this_.GENDER       as gender,\n"
				+ "       this_.FOLK         as folk,\n" + "       this_.EDUCATION    as education,\n"
				+ "       this_.BIRTHDAY     as birthday,\n" + "       peenterpri3_.NAME  as combobox_peName,\n"
				+ "       enumconstb2_.NAME  as combobox_isValid,\n" + "       this_.PHONE        as phone,\n"
				+ "       this_.MOBILE_PHONE as mobilePhone,\n" + "       this_.EMAIL        as email,\n"
				+ "       this_.CARD_TYPE    as cardType,\n" + "       this_.CARD_NO      as cardNo,\n"
				+ "       this_.GROUPS       as groups\n" + "  from PE_BZZ_STUDENT this_\n"
				+ " inner join SSO_USER ssouser1_ on this_.FK_SSO_USER_ID = ssouser1_.ID\n"
				+ "  left outer join ENUM_CONST enumconstb2_ on ssouser1_.FLAG_ISVALID =\n"
				+ "                                             enumconstb2_.ID\n"
				+ "  left outer join PE_ENTERPRISE peenterpri3_ on this_.FK_ENTERPRISE_ID =\n"
				+ "                                                peenterpri3_.ID\n"
				+ "  left outer join ENUM_CONST enumconstb4_ on this_.IS_EMPLOYEE =\n"
				+ "                                             enumconstb4_.ID\n"
				+ "  left outer join ENUM_CONST enumconstb5_ on this_.ZIGE = enumconstb5_.ID\n";
		String sql2 = " where peenterpri3_.CODE = '" + peEnterpriseCode + "'\n";
		String sql3 = "\n" + "union\n" + "\n" + "select this_.ID           as id,\n" + "       this_.REG_NO       as regNo,\n"
				+ "       enumconstb4_.NAME  as combobox_isEmployee,\n" + "       enumconstb5_.NAME  as combobox_zige,\n"
				+ "       this_.TRUE_NAME    as trueName,\n" + "       this_.GENDER       as gender,\n"
				+ "       this_.FOLK         as folk,\n" + "       this_.EDUCATION    as education,\n"
				+ "       this_.BIRTHDAY     as birthday,\n" + "       peenterpri3_.NAME  as combobox_peName,\n"
				+ "       enumconstb2_.NAME  as combobox_isValid,\n" + "       this_.PHONE        as phone,\n"
				+ "       this_.MOBILE_PHONE as mobilePhone,\n" + "       this_.EMAIL        as email,\n"
				+ "       this_.CARD_TYPE    as cardType,\n" + "       this_.CARD_NO      as cardNo,\n"
				+ "       this_.GROUPS       as groups\n" + "  from PE_BZZ_STUDENT this_\n"
				+ " inner join SSO_USER ssouser1_ on this_.FK_SSO_USER_ID = ssouser1_.ID\n"
				+ "  left outer join ENUM_CONST enumconstb2_ on ssouser1_.FLAG_ISVALID =\n"
				+ "                                             enumconstb2_.ID\n"
				+ "  left outer join PE_ENTERPRISE peenterpri3_ on this_.FK_ENTERPRISE_ID =\n"
				+ "                                                peenterpri3_.ID\n"
				+ "  left outer join ENUM_CONST enumconstb4_ on this_.IS_EMPLOYEE =\n"
				+ "                                             enumconstb4_.ID\n"
				+ "  left outer join ENUM_CONST enumconstb5_ on this_.ZIGE = enumconstb5_.ID\n" + " where peenterpri3_.FK_PARENT_ID = '"
				+ peId + "'";

		String sql = "select * from (" + sql1;
		if (!us.getUserLoginType().equals("3")) {
			if ("self".equalsIgnoreCase(flag)) {
				sql = (sql1 + sql2);

			} else {
				sql = (sql1 + sql2 + sql3);
			}
		}
		sql += ") where 1=1 ";
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer(sql);
		this.setSqlCondition(sqlBuffer);
		try {
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}

	public GeneralHibernateDao getGeneralHibernateDao() {
		return generalHibernateDao;
	}

	public void setGeneralHibernateDao(GeneralHibernateDao generalHibernateDao) {
		this.generalHibernateDao = generalHibernateDao;
	}

	public String fixStudent() {
		String cardNo = this.getIds();
		if (cardNo == null || "".equals(cardNo)) {
			cardNo = "1";
		}
		String m = this.peBzzstudentbacthService.updatestu(cardNo, this.flag, this.getCount());
		// HttpServletResponse response = ServletActionContext.getResponse();
		// response.setHeader("Content-type","text/html;charset=UTF-8");
		// response.setCharacterEncoding("UTF-8");
		// PrintWriter out = null;
		// try {
		// out = response.getWriter();
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// out.print(m);
		this.setMsg(m);
		return "msg";
	}

	public String refundOrder() {
		String refundSeq = ServletActionContext.getRequest().getParameter("refundOrder");
		if (null != refundSeq && !"".equals(refundSeq)) {
			try {
				DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
				dc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState");
				dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
				dc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentMethod", "enumConstByFlagPaymentMethod");
				dc.createCriteria("peBzzOrderInfo.ssoUser", "ssoUser");
				dc.add(Restrictions.eq("peBzzOrderInfo.seq", refundSeq));
				PeBzzRefundInfo peBzzRefundInfo = (PeBzzRefundInfo) this.getGeneralService().getList(dc).get(0);
				this.getGeneralService().executeRefund(peBzzRefundInfo);
				this.setMsg("处理完成");
			} catch (EntityException e) {
				this.setMsg("执行失败");
				e.printStackTrace();
			}
		} else {
			this.setMsg("参数获取失败");
		}
		return "msg";
	}

	/**
	 * 修改分组--小组信息
	 * 
	 * @author linjie
	 * @return
	 */
	public String modifyGroup() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sss = this.getEnterpriseId();
		String sql = "";
		if ("2".equals(us.getUserLoginType())) {
			sql = "select distinct pbs.groups from pe_bzz_student pbs where pbs.groups is not null and pbs.fk_enterprise_id in (" + sss
					+ ")";

		} else if ("4".equals(us.getUserLoginType())) {
			sql = "select distinct pbs.sub_groups from pe_bzz_student pbs where pbs.sub_groups is not null and pbs.fk_enterprise_id in ("
					+ sss + ")";
		}
		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list != null && list.size() > 0) {
			ServletActionContext.getRequest().setAttribute("groupList", list);
		} else {
			this.setMsg("您所在的机构下暂时没有任何分组信息!");
			this.setTogo("/entity/basic/peBzzStudent.action");
			return "msg";
		}
		return "groupList";

	}

	/**
	 * 分组
	 * 
	 * @author linjie
	 * @return
	 */
	public String go() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sss = this.getEnterpriseId();
		String selectGroup = ServletActionContext.getRequest().getParameter("selectGroup");
		String group = ServletActionContext.getRequest().getParameter("group");
		String sql = "";
		if ("2".equals(us.getUserLoginType())) {
			sql = "update pe_bzz_student pbs set pbs.groups='" + group + "' where pbs.groups = '" + selectGroup
					+ "' and pbs.fk_enterprise_id in (" + sss + ")";

		} else if ("4".equals(us.getUserLoginType())) {
			sql = "update pe_bzz_student pbs set pbs.sub_groups='" + group + "' where pbs.sub_groups = '" + selectGroup
					+ "' and pbs.fk_enterprise_id in (" + sss + ")";

		}
		try {
			this.getGeneralService().executeBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setMsg("分组信息修改成功!");
		this.setTogo("/entity/basic/peBzzStudent.action");
		return "msg";
	}

	/**
	 * 获得机构id
	 * 
	 * @author linjie
	 * @return
	 */
	public String getEnterpriseId() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql = "\n" + "select pe.id\n" + "  from pe_enterprise pe\n"
				+ " inner join pe_enterprise_manager pem on pem.fk_enterprise_id = pe.id\n" + " where pem.login_id = '" + us.getLoginId()
				+ "'\n" + "\n" + "union\n" + "\n" + "select pe.id\n"
				+ "  from pe_enterprise pe, pe_enterprise pePar, pe_enterprise_manager pem\n" + " where pe.fk_parent_id = pePar.Id\n"
				+ "   and pePar.Id = pem.fk_enterprise_id\n" + "   and pem.login_id = '" + us.getLoginId() + "'";
		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				sb.append("'");
				sb.append(list.get(i).toString());
				sb.append("',");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * 批量学员调入
	 * 
	 * @author Lee 2014年9月20日11:15:33
	 * @return 跳转页面
	 */
	public String stuCheckId() {
		return "stuCheckId";
	}

	/***************************************************************************
	 * Lee 课件卡、学习信息记录不上
	 * 
	 * @return 跳转页面
	 */
	public String studyRecord() {

		return "studyRecord";
	}

	public String updateStudyRecord() {
		StringBuffer sb = new StringBuffer();
		StringBuffer sbQuery = new StringBuffer();
		int sum = 0;
		String courseStr = "";
		String tishi ="" ;
		List list = null ;
		String[] str = this.courseCode.split(",");
		for (int i = 0; i < str.length; i++) {
			courseStr += "'" + str[i] + "',";
		}
		courseStr = courseStr.substring(0, courseStr.length() - 1);
		sb.append("  UPDATE  TRAINING_COURSE_STUDENT TS   ");
		sb.append("   SET TS.LEARN_STATUS  = 'COMPLETED',  ");
		sb.append("     TS.PERCENT         = '100', ");
		sb.append("     TS.COMPLETE_DATE = TS.GET_DATE + 1  ");
		sb.append("    WHERE ID IN (SELECT TCS.ID ");
		sb.append("     FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE  ");
		sb.append("      INNER JOIN TRAINING_COURSE_STUDENT TCS");
		sb.append("   ON PBTSE.FK_TRAINING_ID = TCS.ID  ");
		sb.append("    INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO  ");
		sb.append("      ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
		sb.append("   INNER JOIN PE_BZZ_TCH_COURSE PBTC  ");
		sb.append("    ON PBTO.FK_COURSE_ID = PBTC.ID  ");
		sb.append("      INNER JOIN PE_BZZ_STUDENT PBS ");
		sb.append("       ON PBTSE.FK_STU_ID = PBS.ID ");
		sb.append("     WHERE UPPER(PBS.REG_NO) = UPPER('"+ this.loginCode +"') ");
		sb.append("     AND PBTC.CODE IN  ("+ courseStr +") ");
		sb.append("      AND TCS.GET_DATE IS NOT NULL)  ");
		try {
			 sum = this.getGeneralService().executeBySQL(sb.toString());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("操作失败");
		}
		if(sum < 1){
			this.setMsg("操作失败,请核对登陆账号或课程编号");
		}else{
			sbQuery.append("  SELECT pc.code  FROM TRAINING_COURSE_STUDENT TS   ");
			sbQuery.append("   INNER JOIN  PR_BZZ_TCH_OPENCOURSE po ON ts.course_id = po.id   ");
			sbQuery.append("   INNER JOIN PE_BZZ_TCH_COURSE PC ON po.fk_course_id =pc.id   ");
			sbQuery.append("     INNER JOIN PE_BZZ_STUDENT PBS                 ");
			sbQuery.append("        ON TS.STUDENT_ID = PBS.FK_SSO_USER_ID               ");
			sbQuery.append("      WHERE PC.CODE IN ("+ courseStr +") ");
			sbQuery.append("    AND TS.LEARN_STATUS = 'COMPLETED'  ");
			sbQuery.append("    AND TS.COMPLETE_DATE IS NOT NULL ");
			sbQuery.append("    AND  upper(pbs.reg_no) = UPPER('"+ this.loginCode +"')  ");
			try {
				 list = this.getGeneralService().getBySQL(sbQuery.toString());
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(list !=null && list.size() >0){
				for(int j=0 ; j<list.size() ;j++){
					tishi += list.get(j)+"," ;
				}
				tishi = tishi.substring(0, tishi.length()-1);
				this.setMsg(tishi +" 操作成功 ");
				return "m_msg";
			}
			
		}
		this.setMsg("操作失败,请核对登陆账号或课程编号");
		return "m_msg";
	}

	/**
	 * 批量修改学员学习进度
	 * 
	 * @author Lee 2014年9月20日14:23:28
	 * @return 跳转页面
	 */
	public String stuLearnStatus() {
		return "stuLearnStatus";
	}

	/**
	 * 解析调入Excel执行调入操作
	 * 
	 * @author Lee 2014年9月20日11:50:31
	 * @return 操作提示
	 */
	public String impStuCheckIn() {
		this.setDoLog(false);// 不记录日志
		StringBuffer msg = new StringBuffer();
		Workbook work = null;
		try {
			File file = this.get_upload();
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			// e.printStackTrace();
			msg.append("Excel表格读取异常！导入学员失败！<br />");// 解析Excel异常
			this.setMsg(msg.toString());
			return "msg";
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("Excel表格异常，批量调入学员失败！请检查Excel文件！<br />");// 无数据
			this.setMsg(msg.toString());
			return "m_msg";
		}
		String cellCardNo = "";// 存储证件号码
		String cellEnterName = "";// 存储将要调入的机构名称
		int chkInCount = 0;// 调入成功计数器
		int errRowIdx = -1;// 错误行数
		if (rows > 21)
			rows = 21;
		try {
			for (int i = 1; i < rows; i++) {
				errRowIdx = i;
				// 证件号码单元格
				Cell cell2 = sheet.getCell(2, i);
				// 机构名称单元格
				Cell cell3 = sheet.getCell(3, i);
				if (cell2.getType() == CellType.LABEL) {
					LabelCell lc = (LabelCell) cell2;
					cellCardNo = lc.getString().trim();
				} else {
					cellCardNo = sheet.getCell(2, i).getContents().trim();
				}
				if (cell3.getType() == CellType.LABEL) {
					LabelCell lc = (LabelCell) cell3;
					cellEnterName = lc.getString().trim();
				} else {
					cellEnterName = sheet.getCell(3, i).getContents().trim();
				}
				// 证件号码、机构名称非空才执行后续调入操作
				if (null != cellCardNo && cellCardNo.length() > 0 && null != cellEnterName && cellEnterName.length() > 0) {
					// 调入操作：调入条件为无从业资格
					String uptEnterSql = "UPDATE PE_BZZ_STUDENT SET FK_ENTERPRISE_ID = (SELECT ID FROM PE_ENTERPRISE WHERE NAME = '"
							+ cellEnterName + "') WHERE UPPER(CARD_NO) = '" + cellCardNo.toUpperCase()
							+ "' AND (SELECT ID FROM PE_ENTERPRISE WHERE NAME = '" + cellEnterName
							+ "') IS NOT NULL AND IS_EMPLOYEE = (SELECT ID FROM ENUM_CONST WHERE NAMESPACE = 'IsEmployee' AND CODE = '0')";
					int chkInResult = this.getGeneralService().executeBySQL(uptEnterSql);
					// 调入失败
					if (chkInResult <= 0) {
						msg.append("<font color='red'>第" + (i + 1) + "行调入失败，证件号码或机构名称错误，或学员有从业资格。</font><br />");
					} else {
						msg.append("<font color='green'>第" + (i + 1) + "行调入成功。</font><br />");
						// 调入成功计数器
						chkInCount++;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("<font color='red'>调入异常，请检查第" + (errRowIdx + 1) + "行。</font><br />");
		}
		msg.append("<font color='green'>成功调入" + chkInCount + "名学员。</font>");
		this.setMsg(msg.toString());
		return "m_msg";
	}

	/**
	 * 解析Excel执行批量修改学员学习进度操作
	 * 
	 * @author Lee 2014年9月20日14:29:01
	 * @return 操作提示
	 */
	public String impStuLearnStatus() {
		this.setDoLog(false);// 不记录日志
		StringBuffer msg = new StringBuffer();
		Workbook work = null;
		try {
			File file = this.get_upload();
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			// e.printStackTrace();
			msg.append("Excel表格读取异常！导入学员失败！<br />");// 解析Excel异常
			this.setMsg(msg.toString());
			return "msg";
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("Excel表格异常，批量调入学员失败！请检查Excel文件！<br />");// 无数据
			this.setMsg(msg.toString());
			return "m_msg";
		}
		String cellLoginId = "";// 存储系统编号
		String[] cellCourseCodes = null;// 存储将课程编号集合
		int errRowIdx = -1;// 错误行数
		try {
			for (int i = 1; i < 21; i++) {
				errRowIdx = i;
				// 系统编号单元格
				Cell cell0 = sheet.getCell(0, i);
				// 机构名称单元格
				Cell cell1 = sheet.getCell(1, i);
				if (cell0.getType() == CellType.LABEL) {
					LabelCell lc = (LabelCell) cell0;
					cellLoginId = lc.getString().trim();
				} else {
					cellLoginId = sheet.getCell(0, i).getContents().trim();
				}
				if (cell1.getType() == CellType.LABEL) {
					LabelCell lc = (LabelCell) cell1;
					cellCourseCodes = (null == lc.getString().trim() || lc.getString().trim().length() == 0) ? null : lc.getString().trim()
							.split("-");
				} else {
					cellCourseCodes = (null == sheet.getCell(1, i).getContents().trim() || sheet.getCell(1, i).getContents().trim()
							.length() == 0) ? null : sheet.getCell(1, i).getContents().trim().split("-");
				}
				// 系统编号、课程编号非空才执行后续调入操作
				if (null != cellLoginId && cellLoginId.length() > 0 && null != cellCourseCodes && cellCourseCodes.length > 0) {
					// 检查系统编号是否存在
					String loginIdSql = "SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = (SELECT ID FROM SSO_USER WHERE UPPER(LOGIN_ID) = '"
							+ cellLoginId.toUpperCase() + "')";
					int loginIdResult = this.getGeneralService().executeBySQL(loginIdSql);
					// 学员存在，执行后续操作
					if (loginIdResult > 0) {
						// 循环课程集合进行修改操作
						for (String courseCode : cellCourseCodes) {
							String uptSql = "UPDATE TRAINING_COURSE_STUDENT SET PERCENT = 100,LEARN_STATUS='COMPLETED',COMPLETE_DATE=SYSDATE WHERE STUDENT_ID=(SELECT ID FROM SSO_USER WHERE UPPER(LOGIN_ID) = '"
									+ cellLoginId.toUpperCase()
									+ "') AND COURSE_ID = (SELECT ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_COURSE_ID = (SELECT ID FROM PE_BZZ_TCH_COURSE WHERE CODE IN ('"
									+ courseCode.toUpperCase() + "')))";
							int uptResult = this.getGeneralService().executeBySQL(uptSql);
							if (uptResult > 0) {
								// 免考课程需要填写完满意度调查方可获得学时
								msg.append("<font color='green'>第" + (i + 1) + "行系统编号：" + cellLoginId + " 课程编号：" + courseCode
										+ "操作成功。</font><br />");
							} else {
								msg.append("<font color='red'>第" + (i + 1) + "行操作失败，请检查课程编号：" + courseCode + "是否正确。</font><br />");
							}
						}
					} else {
						msg.append("<font color='red'>第" + (i + 1) + "行操作失败，系统编号不存在。</font><br />");
					}
				}
			}
		} catch (Exception e) {
			msg.append("操作异常，请检查第" + (errRowIdx + 1) + "行</font><br />");
		}
		this.setMsg(msg.toString());
		return "m_msg";
	}

	public String getEnterPage() {
		return enterPage;
	}

	public void setEnterPage(String enterPage) {
		this.enterPage = enterPage;
	}

	private boolean checkMail(String mail) {
		String sql = "SELECT 1 FROM SSO_USER WHERE UPPER(LOGIN_ID) = '" + mail.trim().toUpperCase() + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (null != list && list.size() > 0)
				return true;
		} catch (EntityException e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
}

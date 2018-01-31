package com.whaty.platform.entity.web.action.basic;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzEditStudent;
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
import com.whaty.util.file.FileManage;
import com.whaty.util.file.FileManageFactory;

/**
 * 
 * @author linjie
 *
 */
public class PeBzzStudentEnterpriseAction extends MyBaseAction<PeBzzEditStudent> {
	
	private List<PeBzzStudent> plist = new ArrayList<PeBzzStudent>();
	private List<PeEnterprise> peEnterpriseList;
	private PeBzzStudent peBzzStudent;
	private StringBuffer uploadErrors = new StringBuffer();
	
	private PeBzzstudentbacthService peBzzstudentbacthService;
	
	private EnumConstService enumConstService;
	
	public PeBzzstudentbacthService getPeBzzstudentbacthService() {
		return peBzzstudentbacthService;
	}

	public void setPeBzzstudentbacthService(
			PeBzzstudentbacthService peBzzstudentbacthService) {
		this.peBzzstudentbacthService = peBzzstudentbacthService;
	}

	/**
	 * 初始化列表
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		this.setCanProjections(true);
		boolean canUpdate = false;

		this.getGridConfig().setCapability(false, false, false,true,false);
		this.getGridConfig().setTitle(this.getText("学员机构变动列表"));
		
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		this.getGridConfig().addColumn(this.getText("系统编号"),"regNo",true,true,true,"TextField",false,100,true);
		this.getGridConfig().addColumn(this.getText("姓名"),"trueName",true,true,true,"TextField",false,100,true);
		this.getGridConfig().addColumn(this.getText("证件号"),"cardNo",true,true,true,"TextField",false,100,true);
		this.getGridConfig().addColumn(this.getText("账户剩余学时"),"balance",true,true,true,"TextField",false,100,true);
		this.getGridConfig().addColumn(this.getText("原机构编号"),"oldCode",true,true,true,"TextField",false,100,true);
		this.getGridConfig().addColumn(this.getText("原机构名称"),"oldName",true,true,true,"TextField",false,100,true);
		this.getGridConfig().addColumn(this.getText("新机构编号"),"newCode",true,true,true,"TextField",false,100,true);
		this.getGridConfig().addColumn(this.getText("新机构名称"),"newName",true,true,true,"TextField",false,100,true);
		this.getGridConfig().addColumn(this.getText("审核状态"),"checkState",false,false,false,"TextField",false,100,true);
		this.getGridConfig().addColumn(this.getText("变动状态"),"status",false,false,true,"TextField",false,100,true);
		this.getGridConfig().addColumn(this.getText("发起时间"),"createDate",true,true,true,"TextField",false,100,true);
		this.getGridConfig().addMenuFunction( this.getText("审核通过"),"FlagIsvalid.true");
		//this.getGridConfig().addMenuFunction(this.getText("审核不通过"),"FlagIsvalid.false");
	}
	
	/**
	 * 重写框架方法：学院机构信息（带sql条件）
	 * @author linjie
	 * @return
	 */
	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
			.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);

		StringBuffer sqlSB = new StringBuffer();
			sqlSB.append("select * from (select pbe.id as id,\n");
			sqlSB.append("       pbs.reg_no as regNo,\n"); 
			sqlSB.append("       pbs.true_name as trueName,\n"); 
			sqlSB.append("       pbs.card_no as cardNo,\n"); 
			sqlSB.append("       (su.sum_amount - su.amount) as balance,\n"); 
			sqlSB.append("       pe.code as oldCode,\n"); 
			sqlSB.append("       pe.name as oldName,\n"); 
			sqlSB.append("       pe1.code as newCode,\n"); 
			sqlSB.append("       pe1.name as newName,\n"); 
			sqlSB.append("       ec.name as checkState,\n"); 
			sqlSB.append("       decode(pbe.status,'02','机构变动','03','离职','') as status,\n"); 
			sqlSB.append("       pbe.createdate as createDate\n");
			sqlSB.append("  from pe_bzz_editstudent    pbe,\n"); 
			sqlSB.append("       pe_bzz_student        pbs,\n"); 
			sqlSB.append("       pe_enterprise         pe2,\n"); 
			sqlSB.append("       pe_enterprise         pe,\n"); 
			sqlSB.append("       pe_enterprise         pe1,\n"); 
			sqlSB.append("       pe_enterprise_manager pem,\n"); 
			sqlSB.append("       enum_const            ec,\n"); 
			sqlSB.append("       sso_user              su\n"); 
			sqlSB.append(" where pbe.old_fk_enterprise_id = pe2.id \n"); 
			sqlSB.append("   and (pe2.id = pe.id or\n"); 
			sqlSB.append("       pe2.fk_parent_id = pe.id)\n"); 
			sqlSB.append("   and pbs.id = pbe.student_id\n"); 
			sqlSB.append("   and su.id = pbs.fk_sso_user_id\n"); 
			sqlSB.append("   and pe.id = pem.fk_enterprise_id\n"); 
			sqlSB.append("   and pbe.flag_edit_check = ec.id\n");
			sqlSB.append("   and pbe.new_fk_enterprise_id = pe1.id(+) \n"); 
			sqlSB.append("   and ec.name='未审核' \n");
			sqlSB.append("   and pem.fk_sso_user_id = '"+us.getSsoUser().getId()+"') where 1=1");
			
			Page page = null;
			StringBuffer sqlBuffer = new StringBuffer(sqlSB);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if(temp.equals("id")){
					temp = "createdate";
				}
			} else {
				sqlBuffer.append(" order by createdate");
			}
		
		this.setSqlCondition(sqlBuffer);
		try {
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 获取当前用户机构信息
	 * @author linjie
	 * @return
	 */
	private List<String> getMyEnterprise(UserSession us) throws EntityException {
		DetachedCriteria dc = DetachedCriteria.forClass(PeEnterpriseManager.class);
		dc.createCriteria("peEnterprise");
		dc.add(Restrictions.eq("ssoUser",us.getSsoUser()));
		List<PeEnterpriseManager> pemlist = this.getGeneralService().getList(dc);
		List<String> myList = new ArrayList<String>();
		
		if(pemlist!=null && pemlist.size()>0){
			for(PeEnterpriseManager pem:pemlist){
				if(us.getUserLoginType().equals("2")){//一级管理员,迁移学员是自己机构的
					myList.add(pem.getPeEnterprise().getId());
				}else if(us.getUserLoginType().equals("4")){//二级管理员，迁移学员是自己机构与上级机构的
					myList.add(pem.getPeEnterprise().getId());
					myList.add(pem.getPeEnterprise().getPeEnterprise().getId());
				}
			}
			return myList;
		}else{
			return null;
		}
	}
	
	/**
	 * 重写框架方法--列更新
	 * @author linjie
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		Map map = new HashMap();
		String msg = "";
		String action = this.getColumn();
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			String idStr = getIds().replace(",", "','");
			if(action.equals("FlagIsvalid.true"))
			{
				try{
					List idList = new ArrayList();
					for (int i = 0; i < ids.length; i++) {
						idList.add(ids[i]);
					}
					this.peBzzstudentbacthService.check(idList);
					msg = "审核成功";
					map.put("success", "true");
					map.put("info", msg);
				}catch (Exception e) {
					msg = "审核失败";
					map.put("success", "false");
					map.put("info", msg);
					return map;
				}
				
			}
			
		}else{
			map.clear();
			map.put("success", "false");
			map.put("info", "至少有一条记录被选择");
			return map;
		}
		return map;
	}

	public void setEntityClass() {
		this.entityClass=PeBzzEditStudent.class;

	}

	public void setServletPath() {
		this.servletPath="/entity/basic/peBzzStudentEnterprise";
	}
	
	public void setBean(PeBzzEditStudent instance){
		super.superSetBean(instance);
	}
	
	public PeBzzEditStudent getBean(){
		return super.superGetBean();
	}
	
	/**
	 * 重写框架方法：更新数据前的校验
	 * @author linjie
	 * @return
	 */
	@Override
	public void checkBeforeUpdate() throws EntityException {
		throw new EntityException("不可修改！");
	}
	
	/**
	 * 重写框架方法：添加数据前的校验
	 * @author linjie
	 * @return
	 */
	@Override
	public void checkBeforeAdd()throws EntityException {
		throw new EntityException("不可！");
	}

	public List<PeBzzStudent> getPlist() {
		return plist;
	}

	public void setPlist(List<PeBzzStudent> plist) {
		this.plist = plist;
	}

	public StringBuffer getUploadErrors() {
		return uploadErrors;
	}

	public void setUploadErrors(StringBuffer uploadErrors) {
		this.uploadErrors = uploadErrors;
	}

	public List<PeEnterprise> getPeEnterpriseList() {
		return peEnterpriseList;
	}

	public void setPeEnterpriseList(List<PeEnterprise> peEnterpriseList) {
		this.peEnterpriseList = peEnterpriseList;
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
	
	/**
	 * 获得企业id
	 * @author linjie
	 * @return
	 */
	public String getEnterpriseId() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql = 
			"\n" +
			"select pe.id\n" + 
			"  from pe_enterprise pe\n" + 
			" inner join pe_enterprise_manager pem on pem.fk_enterprise_id = pe.id\n" + 
			" where pem.login_id = '"+us.getLoginId()+"'\n" + 
			"\n" + 
			"union\n" + 
			"\n" + 
			"select pe.id\n" + 
			"  from pe_enterprise pe, pe_enterprise pePar, pe_enterprise_manager pem\n" + 
			" where pe.fk_parent_id = pePar.Id\n" + 
			"   and pePar.Id = pem.fk_enterprise_id\n" + 
			"   and pem.login_id = '"+us.getLoginId()+"'";
		List list = null;
		try {
			list = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		if(list!=null && list.size()>0) {
			for(int i=0; i<list.size(); i++) {
				sb.append("'");
				sb.append(list.get(i).toString());
				sb.append("',");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}

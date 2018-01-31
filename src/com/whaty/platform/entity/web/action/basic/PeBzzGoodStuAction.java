package com.whaty.platform.entity.web.action.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzGoodStu;
import com.whaty.platform.entity.bean.PeBzzParty;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeInfoNews;
import com.whaty.platform.entity.bean.PeInfoNewsType;
import com.whaty.platform.entity.bean.PeManager;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.PeBzzGoodStuService;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;

/**
 * @param
 * @version 创建时间：2009-6-22 下午03:29:27
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PeBzzGoodStuAction extends MyBaseAction<PeBzzGoodStu> {
	
	private PeBzzGoodStu goodStu;
	private String batchid ;
	private List<PeBzzBatch> peBzzBatch;
	private List<PeBzzGoodStu> plist = new ArrayList<PeBzzGoodStu>();
	private String mngPri;
	
	private String studentId;
	private String reason;
	
	//添加/修改 优秀学员 上报信息
	private String goodStu_id;
	private String party_id;
	private String work_age;
	private String duty;
	private String professional;
	private String work_space;
	private String advanced_story;
	private String stats;
	private String certificate;
	private String feeling_words;
	
	//上传文件
	private List<File> upload;
	private List<String> uploadFileName;
	private String photo_one_flag;
	private String photo_two_flag;
	
	private StringBuffer uploadErrors = new StringBuffer();
	
	private PeBzzstudentbacthService peBzzstudentbacthService;
	
	private PeBzzGoodStuService peBzzGoodStuService ;
	
	public PeBzzGoodStu getGoodStu() {
		return goodStu;
	}

	public void setGoodStu(PeBzzGoodStu goodStu) {
		this.goodStu = goodStu;
	}

	public PeBzzGoodStuService getPeBzzGoodStuService() {
		return peBzzGoodStuService;
	}

	public void setPeBzzGoodStuService(PeBzzGoodStuService peBzzGoodStuService) {
		this.peBzzGoodStuService = peBzzGoodStuService;
	}

	public PeBzzstudentbacthService getPeBzzstudentbacthService() {
		return peBzzstudentbacthService;
	}

	public void setPeBzzstudentbacthService(
			PeBzzstudentbacthService peBzzstudentbacthService) {
		this.peBzzstudentbacthService = peBzzstudentbacthService;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		this.setCanProjections(true);
		boolean canUpdate = false;
		UserSession us = (UserSession) ServletActionContext.getRequest()
		.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		this.getGridConfig().setCapability(false, false, false, true);
		this.getGridConfig().setTitle(this.getText("优秀学员列表"));
		this.getGridConfig().addColumn(this.getText("ID"),"id",false);
		this.getGridConfig().addColumn(this.getText("学号"),"peBzzStudent.regNo",true,false,true,"TextField");
		
		this.getGridConfig().addColumn(this.getText("姓名"),"peBzzStudent.trueName",true,canUpdate,true,"TextField",true,100);
		
		this.getGridConfig().addColumn(this.getText("性别"),"peBzzStudent.gender",false,canUpdate,true,"TextField",true,10,Const.sex_for_extjs);
		this.getGridConfig().addColumn(this.getText("民族"),"peBzzStudent.folk",false,canUpdate,true,"TextField",true,40);
		this.getGridConfig().addColumn(this.getText("学历"),"peBzzStudent.education",false,canUpdate,true,"TextField",true,10,Const.edu_for_extjs);
		this.getGridConfig().addColumn(this.getText("所在学期"),"peBzzStudent.peBzzBatch.name",true,canUpdate,true,"TextField",false,50);
		
		List entList=us.getPriEnterprises();
		String sql_ent = "'no_ent'";
		for(int i=0;i<entList.size();i++)
		{
			PeEnterprise e=(PeEnterprise)entList.get(i);
			sql_ent+=",'"+e.getId()+"'";
		}

		sql_ent="("+sql_ent+")";
		ColumnConfig c_name1 = new ColumnConfig(this.getText("所在企业"),
				"peBzzStudent.peEnterprise.name",true,canUpdate,true,"TextField",false,50,"");
		if (us.getUserLoginType().equals("2") ||us.getRoleId().equals("402880a92137be1c012137db62100006")) {			
			c_name1
					.setComboSQL("select t.id,t.name as p_name from pe_enterprise t where t.fk_parent_id is not null and t.id in " + sql_ent);
		}
		else {
			c_name1.setComboSQL("select t.id,t.name as p_name from pe_enterprise t where t.fk_parent_id is not null");
			
		}
		this.getGridConfig().addColumn(c_name1);
		
		this.getGridConfig().addColumn(this.getText("是否有效"), "peBzzStudent.ssoUser.enumConstByFlagIsvalid.name",true,canUpdate,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("是否通过"), "enumConstByFlaggoodState.name",true,canUpdate,true,"TextField",false,50);
		this.getGridConfig().addColumn(this.getText("学员身份"), "peBzzStudent.enumConstByFlagRankState.name",true,canUpdate,true,"TextField",false,50);
		
		this.getGridConfig().addColumn(this.getText("办公电话"),"peBzzStudent.phone",false,canUpdate,false,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("移动电话"),"peBzzStudent.mobilePhone",true,canUpdate,true,"TextField",true,50);
		this.getGridConfig().addColumn(this.getText("电子邮件"),"peBzzStudent.email",false,canUpdate,false,"TextField",true,50);
		
		if (us.getUserLoginType().equals("3")) {                   
			
			this.getGridConfig().addMenuFunction( this.getText("通过审核"),"enumConstByFlaggoodState.id",
					this.getMyListService().getEnumConstByNamespaceCode("FlaggoodState", "002").getId());
			
			this.getGridConfig().addMenuFunction( this.getText("不通过审核"),"enumConstByFlaggoodState.id",
					this.getMyListService().getEnumConstByNamespaceCode("FlaggoodState", "001").getId());
			
		}
		this.getGridConfig().addRenderFunction(this.getText("上报信息"), "<a href=\"peBzzGoodStu_viewDetail.action?bean.id=${value}\">上报信息</a>", "id");
	}
	
	public String viewDetail() {
		try {
			this.setGoodStu((PeBzzGoodStu)this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "detail";
	}
	
	String fixNull(String str){
		if(str==null || str.equalsIgnoreCase("null")) str = "";
		return str.trim();
	}
	
	public String addGoodStu(){
		UserSession userSession = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = userSession.getUserLoginType();
		SsoUser ssoUser = userSession.getSsoUser();
		
		DetachedCriteria pemdc;
		
		try{
			PeBzzGoodStu new_GoodStu = this.getGeneralService().getById(this.getGoodStu_id());
			String stu_reg_no = "";
			stu_reg_no = fixNull(new_GoodStu.getPeBzzStudent().getRegNo());
			
			if ((userSession == null)||(!userLoginType.equals("3"))) {
				stats="nostats";
				return "addGoodStu";
			}
			
			if(!this.validateToken(this.getFormToken())) {
				stats="formInvildate";
				return "addGoodStu";
			}
			
			if(!"".equals(this.getParty_id())){
				PeBzzParty new_party = new PeBzzParty();
				pemdc = DetachedCriteria.forClass(PeBzzParty.class);
				pemdc.add(Restrictions.eq("id", this.getParty_id()));
				if(!"".equals(this.getParty_id())){
					new_party = (PeBzzParty)this.getGeneralService().getList(pemdc).get(0);
				}
				new_GoodStu.setPeBzzParty(new_party);
			}
			
			if(!"".equals(this.getWork_age())){
				new_GoodStu.setWork_age(this.getWork_age());
			}
			
			if(!"".equals(this.getDuty())){
				new_GoodStu.setDuty(this.getDuty());
			}
			
			if(!"".equals(this.getProfessional())){
				new_GoodStu.setProfessional(this.getProfessional());
			}
			
			if(!"".equals(this.getWork_space())){
				new_GoodStu.setWork_space(this.getWork_space());
			}
			
			if(!"".equals(this.certificate)){
				new_GoodStu.setCertificate(this.certificate);
			}
			
			if(!"".equals(this.feeling_words)){
				new_GoodStu.setFeeling_words(this.feeling_words);
			}
			
			String pic ="";
			
			File one_File;
			File two_File;
			
			String one_filename;
			String tow_filename;
			int current_num = 0;
			
			if( upload != null ){
				
				if(upload.size() == 1){
					if(photo_one_flag.equals("true")){
						one_File = upload.get(0);
						one_filename = "photo_one_"+stu_reg_no+".jpg";
						
						pic= this.uploadexe(one_File, one_filename);
						new_GoodStu.setPhoto_one(one_filename);
					}else if(photo_two_flag.equals("true")){
						two_File = upload.get(0);
						tow_filename = "photo_two_"+stu_reg_no+".jpg";
						
						pic= this.uploadexe(two_File, tow_filename);
						new_GoodStu.setPhoto_two(tow_filename);
					}
				}else if(upload.size() == 3){
					one_File = upload.get(0);
					one_filename = "photo_one_"+stu_reg_no+".jpg";
					
					pic= this.uploadexe(one_File, one_filename);
					new_GoodStu.setPhoto_one(one_filename);
					
					two_File = upload.get(1);
					tow_filename = "photo_two_"+stu_reg_no+".jpg";
					
					pic= this.uploadexe(two_File, tow_filename);
					new_GoodStu.setPhoto_two(tow_filename);
				}
				
			}
			
			if(!"".equals(this.getAdvanced_story())){
				new_GoodStu.setAdvanced_story(this.getAdvanced_story());
			}
			
			this.getGeneralService().update(new_GoodStu);
			ServletActionContext.getRequest().getSession().setAttribute(
					"newGoodStu_id", new_GoodStu.getId());
			stats ="success";
		} catch (EntityException e) {
			stats="failuer";
			e.printStackTrace();
		} 
		
		return "addGoodStu";
	}
	
	protected String uploadexe(File file,String filename){
		String link ="";
		try{
		String savePath = "/entity/manager/basic/goodStuMagImage/goodStu/";
		link = savePath+filename;
		String linkTemp = link;
		int afterFileName = 0;
		
		String theWebPath = ServletActionContext.getServletContext().getRealPath("/");
		theWebPath = theWebPath+"entity/manager/basic/goodStuMagImage/goodStu/"+filename ;
		File file_temp = new File(theWebPath);
		if(file_temp.exists()){
			file_temp.delete();
		}
		
		while(true){
			if(new File(ServletActionContext.getRequest().getRealPath(linkTemp)).isFile()){
				int point = (link.lastIndexOf(".")>0 ? link.lastIndexOf("."):link.length());
				linkTemp = link.substring(0, point)+"["+String.valueOf(afterFileName)+"]"+link.substring(point);
				afterFileName++;
				continue;
			}
			break;
		}
		
		FileOutputStream fos=new FileOutputStream(ServletActionContext.getRequest().getRealPath(linkTemp));
		FileInputStream fis=new FileInputStream(file);
		byte[] buffer=new byte[1024];
		int len=0;
		while((len=fis.read(buffer))>0){
			fos.write(buffer, 0, len);
		}
		fos.close();
		fis.close();
		}catch (Exception e) {
			
		}
		return link;
		
	}
	
	@SuppressWarnings({ "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		Map map = new HashMap();
		String action = this.getColumn();
		
		if (this.getIds() != null && this.getIds().length() > 0) {
			
			if(action.equals("enumConstByFlaggoodState.id")){
				return super.updateColumn();
			}
			
		}else{
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}

	public void setEntityClass() {
		this.entityClass=PeBzzGoodStu.class;

	}

	public void setServletPath() {
		this.servletPath="/entity/basic/peBzzGoodStu";
	}
	
	public void setBean(PeBzzGoodStu instance){
		super.superSetBean(instance);
	}
	
	public PeBzzGoodStu getBean(){
		return super.superGetBean();
	}
	
	public String batch(){
		/**
		 * 取得管理员类型.总站管理员不限制
		 */
		UserSession us = (UserSession)ServletActionContext
			.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String userLoginType = "";//保存管理员类型
		if (us!=null) {
			userLoginType = us.getUserLoginType();
		}
		if(userLoginType.equals("3")||userLoginType.equals("2")){
			DetachedCriteria bathdc = DetachedCriteria.forClass(PeBzzBatch.class);
			bathdc.addOrder(Order.asc("startDate"));
			try {
			this.setPeBzzBatch(this.getGeneralService().getList(bathdc));
			} catch (EntityException e) {
				e.printStackTrace();
			}
			return "batch";
		}else{
			this.setMsg("您的权限不够，无法进行此项操作!!!!");
		}
		return "msg";
	}
	
	public DetachedCriteria initDetachedCriteria() {
		
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzGoodStu.class);
		
		DetachedCriteria dc2 = dc.createCriteria("peBzzStudent", "peBzzStudent");
		dc2.createCriteria("ssoUser","ssoUser").createAlias("enumConstByFlagIsvalid", "enumConstByFlagIsvalid", DetachedCriteria.LEFT_JOIN);
		dc2.createCriteria("peBzzBatch", "peBzzBatch");
		dc2.createCriteria("enumConstByFlagRankState", "enumConstByFlagRankState", DetachedCriteria.LEFT_JOIN);
		dc2.createAlias("peEnterprise", "peEnterprise",DetachedCriteria.LEFT_JOIN);
		
		dc.createAlias("enumConstByFlaggoodState", "enumConstByFlaggoodState",DetachedCriteria.LEFT_JOIN);

		return dc;
	}
	
	public List<PeBzzGoodStu> getPlist() {
		return plist;
	}

	public void setPlist(List<PeBzzGoodStu> plist) {
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

	public StringBuffer getUploadErrors() {
		return uploadErrors;
	}

	public void setUploadErrors(StringBuffer uploadErrors) {
		this.uploadErrors = uploadErrors;
	}

	public String getMngPri() {
		if(this.mngPri == null) {
			UserSession us = (UserSession) ServletActionContext.getRequest()
			.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			mngPri = "";
			if(us.getUserLoginType().equals("3")) {
				//mngPri = "";
			} else {
				List entList=us.getPriEnterprises();
				for(int i=0;i<entList.size();i++)
				{
					PeEnterprise e=(PeEnterprise)entList.get(i);
					mngPri+="'"+e.getId()+"',";
				}
				if(mngPri.endsWith(",")) {
					mngPri = mngPri.substring(0,mngPri.length()-1);
				}
				mngPri = " and ps.fk_enterprise_id in ("+mngPri+")";
			}
		}
		return mngPri;
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

	public String getParty_id() {
		return party_id;
	}

	public void setParty_id(String party_id) {
		this.party_id = party_id;
	}

	public String getWork_age() {
		return work_age;
	}

	public void setWork_age(String work_age) {
		this.work_age = work_age;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getProfessional() {
		return professional;
	}

	public void setProfessional(String professional) {
		this.professional = professional;
	}

	public String getWork_space() {
		return work_space;
	}

	public void setWork_space(String work_space) {
		this.work_space = work_space;
	}

	public String getAdvanced_story() {
		return advanced_story;
	}

	public void setAdvanced_story(String advanced_story) {
		this.advanced_story = advanced_story;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public List<File> getUpload() {
		return upload;
	}

	public void setUpload(List<File> upload) {
		this.upload = upload;
	}

	public List<String> getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(List<String> uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getGoodStu_id() {
		return goodStu_id;
	}

	public void setGoodStu_id(String goodStu_id) {
		this.goodStu_id = goodStu_id;
	}

	public String getPhoto_one_flag() {
		return photo_one_flag;
	}

	public void setPhoto_one_flag(String photo_one_flag) {
		this.photo_one_flag = photo_one_flag;
	}

	public String getPhoto_two_flag() {
		return photo_two_flag;
	}

	public void setPhoto_two_flag(String photo_two_flag) {
		this.photo_two_flag = photo_two_flag;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getFeeling_words() {
		return feeling_words;
	}

	public void setFeeling_words(String feeling_words) {
		this.feeling_words = feeling_words;
	}
	
}

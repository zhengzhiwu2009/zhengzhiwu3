package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeResource;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.JsonUtil;

/**
 * 
 * 
 * @author Lzh
 * 
 */
public class PeResourcejitiAction extends MyBaseAction<PeResource> {
	private List resourceTagList;
	private List<EnumConst> resourceTypeList;
	private String resourceName;
	private String tagNames;
	private String describe;
	private String resetype;
	private String tagIds;// 资料标签
	private String content;
	private String replyDate;
	private String fabuunit;
	private String isOpen;
	private List resourceIsOpen;
	private List<File> file;
	private List<String> fileFileName;
	private List<String> fileContentType;
	
	private InputStream inputStream;
	
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		String filePath = "/UserFiles/File";
		InputStream is = ServletActionContext.getServletContext().getResourceAsStream(filePath + "/" + fileName);
		System.out.println(is);

		if(is == null) {
			is = new ByteArrayInputStream("文件不存在".getBytes());
		}
		return is;
	}

	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public List<String> getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public List getResourceIsOpen() {
		return resourceIsOpen;
	}

	public void setResourceIsOpen(List resourceIsOpen) {
		this.resourceIsOpen = resourceIsOpen;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}

	public String getFabuunit() {
		return fabuunit;
	}

	public void setFabuunit(String fabuunit) {
		this.fabuunit = fabuunit;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getTagNames() {
		return tagNames;
	}

	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getResetype() {
		return resetype;
	}

	public void setResetype(String resetype) {
		this.resetype = resetype;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List getResourceTagList() {
		return resourceTagList;
	}

	public void setResourceTagList(List resourceTagList) {
		this.resourceTagList = resourceTagList;
	}

	public List<EnumConst> getResourceTypeList() {
		return resourceTypeList;
	}

	public void setResourceTypeList(List<EnumConst> resourceTypeList) {
		this.resourceTypeList = resourceTypeList;
	}

	public void setBean(PeResource instance) {
		super.superSetBean(instance);

	}

	public PeResource getBean() {
		return super.superGetBean();
	}

	/**
	 * 初始化列表
	 * 
	 * @author Lzh
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();

		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("资料库");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);

		this.getGridConfig().addColumn(this.getText("资料名称"), "name",
				true, true, true, "TextField", false, 200, "");
//		this.getGridConfig().addColumn(this.getText("资料分类"), "resetype", true, true, true, "TextField", false, 200, "");
		ColumnConfig columnConfigFlagResourceType = new ColumnConfig(this.getText("资料分类"), "enumConstByFlagResourceType.name", true, true, true, "TextField", false, 100, "");
		String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagDocumentType'";
		columnConfigFlagResourceType.setComboSQL(sql1);
		this.getGridConfig().addColumn(columnConfigFlagResourceType); 
		
		ColumnConfig columnConfigResourceTag = new ColumnConfig(this.getText("资料标签"), "enumConstByFlagResourceTag.name", true, true, false, "TextField", true, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagResourceTag'";
		columnConfigResourceTag.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigResourceTag);
		
//		this.getGridConfig().addColumn(this.getText("资料标签"), "resourceTags", true, true, false, "TextField", false, 100, "");

		this.getGridConfig().addColumn(this.getText("颁布/发布时间"), "replydate",
				false, false, true, "textField");
		this.getGridConfig().addColumn(this.getText("颁布/发布单位"),"fabuunit" ,true, true,true,"TextField",false,100,"");
		
		this.getGridConfig().addColumn(this.getText("课程名称"), "kname", true, false, false, "textField", true, 500);
		this.getGridConfig().addColumn(this.getText("课程编号"), "kbianhao", true, false, false, "textField", true, 500);
		this.getGridConfig().addColumn(this.getText("浏览次数"),"views" ,false, false, false,"TextField",false,100,"");
			this
					.getGridConfig()
					.addRenderScript(
							this.getText("查看"),
							"{return '<a target=blank href=\"/cms/doumentDetail.htm?myId='+${value}+'&uid=" + us.getId() + "\" >查看</a>';}",
							"id");
		this.getGridConfig().addRenderFunction(this.getText("查看相关课程"),
					"<center><a href=\"/entity/teaching/kechengPerResource.action?id=${value}&typeflag=viewkecheng\" >查看课程</a></center>", "id");
		Map<String, String> defaultValueMap = new HashMap<String, String>();
		this.setDefaultValueMap(defaultValueMap);
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass = PeResource.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/entity/teaching/peResourcejiti";
	}

	/**
	 * 资料管理列表
	 * 
	 * @author Lzh
	 * @return
	 */
	public Page list() {
		UserSession us = (UserSession) ServletActionContext.getRequest()
			.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer
					.append("SELECT * FROM (select zl.id,zl.name,  e5.name resourceType, zl.resourcetags, nvl(to_char(zl.replydate, 'yyyy-MM-dd') ,'-')replydate ,zl.Fabuunit,  '' as kname,   '' as kbianhao , zl.views, zl.flag_top from PE_RESOURCE zl inner join enum_const e1 on zl.flag_isvalid=e1.id  and e1.code='1' " +
							" inner join enum_const e2 on zl.flag_offline = e2.id and e2.code = '0' inner join enum_const e5 on zl.flag_resourceType = e5.id ");
			sqlBuffer.append(" where (zl.flag_isaudit = '2' or zl.flag_isaudit is null) ");
			sqlBuffer.append(" and zl.id not in(SELECT PR.ID FROM PE_RESOURCE PR JOIN INTERACTION_TEACHCLASS_INFO ITI ON ITI.FK_ZILIAO = PR.ID AND ITI.TYPE != 'JSJJ' JOIN PE_BZZ_TCH_COURSE PBTC ON ITI.TEACHCLASS_ID = PBTC.ID");
			sqlBuffer.append(" JOIN ENUM_CONST EC ON PBTC.FLAG_COURSEAREA = EC.ID AND EC.CODE = '0')");
			if("2".equals(us.getUserLoginType()) || "4".equals(us.getUserLoginType())) {
				sqlBuffer.append(" AND (ZL.SHOWUSERS LIKE '%jtmanager%' OR ZL.SHOWUSERS IS NULL)");
			} else {
				sqlBuffer.append(" AND (ZL.SHOWUSERS LIKE '%jgmanager%' OR ZL.SHOWUSERS IS NULL)");
			}
			sqlBuffer.append(") WHERE 1 = 1 ");
			/* 拼接查询条件 */
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator
						.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1
						&& name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name
							.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}
				if (name.equals("name")) {
					name = "name";
				}
				/* 资料分类 */
				if (name.equals("enumConstByFlagResourceType.name")) {
					name = "resourceType";
				}
				/* 资料标签 */
				if (name.equals("enumConstByFlagResourceTag.name")) {
					name = "resourceTags";
				}
				/*课程编号*/
				if(name.equals("kbianhao")){
					sqlBuffer.append(" and id in (  select zl.id  from PE_RESOURCE zl " +
							"  inner join interaction_teachclass_info ii on ii.fk_ziliao = zl.id     and ii.type = 'KCZL' " +
							"   inner join pe_bzz_tch_course pbtc on pbtc.id = ii.teachclass_id and pbtc.code like '%"+value+"%') ");
				}
				/*课程名称*/
				if(name.equals("kname")){
					sqlBuffer.append(" and id in (  select zl.id  from PE_RESOURCE zl " +
							"  inner join interaction_teachclass_info ii on ii.fk_ziliao = zl.id     and ii.type = 'KCZL' " +
							"   inner join pe_bzz_tch_course pbtc on pbtc.id = ii.teachclass_id and pbtc.name like'%"+value+"%') ");
				}
				if("name".equals(name)){
					sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");
					
				}else{			
					if(!name.equals("kbianhao")&&!name.equals("kname") || "resourceTags".equals(name)){
						sqlBuffer.append(" and UPPER(" + name + ") like UPPER('%" + value + "%')");	
					} 
					
				}

			} while (true);
			String temp = this.getSort();
			if (temp != null && temp.indexOf(".") > 1) {
				if (temp.toLowerCase().startsWith("combobox_")) {
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				/* 资料分类 */
				if (temp.equals("enumConstByFlagResourceType.name")) {
					temp = "resourceType";
				}
				/* 资料标签 */
				if (temp.equals("enumConstByFlagResourceTag.name")) {
					temp = "resourceTags";
				}
				if (temp.equals("id")) {
					temp = "flag_top";
				}

				if (this.getDir() != null
						&& this.getDir().equalsIgnoreCase("desc")) {
					sqlBuffer.append(" order by " + temp + " asc,replydate desc ");

				} else {
					sqlBuffer.append(" order by " + temp + " desc ");
				}

			} else {
				sqlBuffer.append(" order by flag_top asc,replydate desc");
			}
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
		 * 资料查看功能 lzh
		 * @return
		 */
	public String courseInfo(){
		String id = ServletActionContext.getRequest().getParameter("id");
		String sql1="select zl.name,zl.RESETYPE ,zl.DESCRIBE,zl.fabuunit,nvl(to_char(zl.REPLYDATE, 'yyyy-MM-dd') ,'-')replydate from PE_RESOURCE zl inner join enum_const e1 on zl.flag_isvalid=e1.id "
			+" where zl.id='"+id+"'";
		String filelink=" select zl.FILE_LINK  from PE_RESOURCE zl "+
		" where zl.id='"+id+"' and zl.FILE_LINK is not null ";
		String resourcec=	
			"select *\n" +
			"  from (select a.*, rownum rownum_\n" + 
			"          from (SELECT *\n" + 
			"                  FROM (select id, name,  RESETYPE, fabuunit,nvl(to_char(REPLYDATE, 'yyyy-MM-dd') ,'-')replydate\n" + 
			"                          from pe_resource pr\n" + 
			"                         where pr.id in (\n" + 
			"\n" + 
			"                                         select iti.fk_ziliao\n" + 
			"                                           from interaction_teachclass_info iti\n" + 
			"                                          where iti.teachclass_id in\n" + 
			"                                                (select pbtc.id\n" + 
			"                                                   from pe_bzz_tch_course pbtc\n" + 
			"                                                  inner join enum_const ec1 on pbtc.flag_coursecategory =\n" + 
			"                                                                               ec1.id\n" + 
			"                                                  inner join enum_const ec2 on pbtc.flag_isvalid =\n" + 
			"                                                                               ec2.id\n" + 
			"                                                                           and ec2.code = '1'\n" + 
			"                                                  inner join enum_const ec3 on pbtc.flag_offline =\n" + 
			"                                                                               ec3.id\n" + 
			"                                                                           and ec3.code = '0'\n" + 
			"                                                  where pbtc.id in\n" + 
			"                                                        (select ii.teachclass_id\n" + 
			"                                                           from interaction_teachclass_info ii\n" + 
			"                                                          inner join PE_RESOURCE zl on zl.id =\n" + 
			"                                                                                       ii.fk_ziliao\n" + 
			"                                                          where zl.id =\n" + 
			"                                                                '"+id+"')\n" + 
			"                                                    and pbtc.FLAG_COURSEAREA <>\n" + 
			"                                                        'Coursearea0')) and pr.id<> '"+id+"'  and pr.flag_isvalid='2' and pr.flag_offline='3' )\n" + //pr.flag_isvalid='2'[表示资料有效] and pr.flag_offline='3'[表示资料未下线]
			"\n" + 
			"                ) a\n" + 
			"         where rownum <= 5) b\n" + 
			" where rownum_ > 0";
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			try {
			
			List filelinkb=new ArrayList();
			List courseZl=this.getGeneralService().getBySQL(resourcec);
			List list=	this.getGeneralService().getBySQL(sql1);
			List filelinkList= this.getGeneralService().getBySQL(filelink);
			if(null==filelinkList || filelinkList.size()<1){
				
			}else {
				String fklint=filelinkList.get(0).toString();
				String[] file = fklint.split(",");
				for(int i = 0; i < file.length; i++) {
					List filelinka=new ArrayList();
					String link = file[i];
					String fileName = file[i].substring(link.lastIndexOf("/")+1);
					filelinka.add(link);
					filelinka.add(fileName);
					filelinkb.add(filelinka);
				}
			}
			if(list!=null && list.size()>0){
				ServletActionContext.getRequest().setAttribute("list", list);	
			}
			ServletActionContext.getRequest().setAttribute("filelinkList", filelinkb);	
			if(courseZl!=null && courseZl.size()>0){
				ServletActionContext.getRequest().setAttribute("courseZl", courseZl);	
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute("usType", us.getUserLoginType());
		return "courseInfo"	;
	}
		
	public String toUploadResource() {
		String resourceTag_hql = "from EnumConst ec where ec.namespace = 'FlagResourceTag'";
		String resourceTypeHql = "from EnumConst ec where ec.namespace = 'FlagDocumentType' order by ec.code";
		String isOpen_hql = "from EnumConst ec where ec.namespace = 'FlagIsvalid' and ec.code != '2'";
		try {
			resourceTagList = this.getGeneralService().getByHQL(resourceTag_hql);
			resourceTypeList = this.getGeneralService().getByHQL(resourceTypeHql);
			resourceIsOpen = this.getGeneralService().getByHQL(isOpen_hql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "toUploadResource";
	}
	
	public String uploadResource() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String hql = "from EnumConst ec where ec.id = '" + resetype + "'";
		String open_hql = "from EnumConst ec where ec.id = '" + isOpen + "'";
		EnumConst isAudit = new EnumConst();
		EnumConst isOffLine = new EnumConst();
		EnumConst informationResource = this.getEnumConstService().getByNamespaceCode("InformationResource", "1");
		String path = "/UserFiles/File";
		String realPath = ServletActionContext.getServletContext().getRealPath(path);
		String filePath = "";
		
		InputStream is = null;
		OutputStream os = null;
		try {
			//文件上传
			if(file != null && !"".equals(file)) {
				for(int i = 0; i < file.size(); i++) {
					long ts = new Date().getTime();
					is = new FileInputStream(file.get(i));
					os = new FileOutputStream(new File(realPath, ts + "_" + fileFileName.get(i)));
					byte[] buffer = new byte[500];
					
					int length = 0;
					
					while(-1 != (length = is.read(buffer, 0, buffer.length))) {
						os.write(buffer);
					}
					filePath += path + "/" + ts + "_" + fileFileName.get(i) + ",";
				}
				filePath = filePath.substring(0, filePath.length() - 1);
			}
		
			Date reDate = null;
			EnumConst flagResourceType = (EnumConst) this.getGeneralService().getByHQL(hql).get(0);
			EnumConst flagIsOpen = (EnumConst) this.getGeneralService().getByHQL(open_hql).get(0);
			isAudit.setId("3");
			isOffLine.setId("3");
			
			if(("".equals(replyDate) || replyDate == null) && "0".equals(flagIsOpen.getCode())) {
				reDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			} else if(!"".equals(replyDate) && replyDate != null) {
				reDate = new SimpleDateFormat("yyyy-MM-dd").parse(replyDate);
			}
			PeResource pr = new PeResource();
			pr.setName(resourceName);
			pr.setResetype(flagResourceType.getName());
//			pr.setResourceTagIds(tagIds);
//			pr.setResourceTagNames(tagNames);
			pr.setDescribe(describe);
			pr.setEnumConstByFlagResourceType(flagResourceType);
			// 截取多个上传附件路径并以","分隔
//			int startIdx = content.toLowerCase().indexOf("<");
//			int endIdx = content.toLowerCase().lastIndexOf(">");
//			String fileLink = content.substring(startIdx, endIdx + 1).replaceAll("<a href=\"", "").replaceAll("\\\">(.*?)\\/U", ",/U").replaceAll("\\\">(.*?)\\</a>", "");
			pr.setFilelink(filePath);
			pr.setCreater(us.getId());
			pr.setCreationdate(new Date());
			pr.setFabuunit(fabuunit);
			pr.setReplyDate(reDate);
			
			pr.setEnumConstByFlagIsvalid(isAudit);
			pr.setEnumConstByFlagIsvalidSPXiaXian(isOffLine);
			pr.setEnumConstByFlagIsvalidSPshouye(isOffLine);
			pr.setEnumConstByFlagIsvalidSPxianzai(isOffLine);
			pr.setEnumConstByFlagIsAudit(isAudit);
			pr.setEnumConstByFlagIsOpen(flagIsOpen);
			pr.setEnumConstByInformationResource(informationResource);//资料来源
			
			pr.setViews(0);
			this.getGeneralService().save(pr);
			this.setMsg("提交成功，公开资料需经协会管理员审核后方能发布。您可在\"我的资料\"中查看资料的审核状态!");
			this.setTogo("/entity/teaching/myResource.action");	
		} catch (EntityException e) {
			this.setMsg("添加失败,请重新添加!");
			this.setTogo("back");
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			this.setMsg("添加失败,请重新添加!");
			this.setTogo("back");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			this.setMsg("添加失败,请重新添加!");
			this.setTogo("back");
		} catch (IOException e) {
			e.printStackTrace();
			this.setMsg("添加失败,请重新添加!");
			this.setTogo("back");
		} finally {
			try {
				if(os != null) 
					os.close();
				if(is != null) 
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "msg";
	}
	
	/**
	 * 批量添加到课程
	 * 
	 * @author Lwq
	 * @return
	 */
	public String addCourse() {
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			/* 存储选中ids便于后续操作使用 */
			ServletActionContext.getRequest().getSession().setAttribute("resourceIds", ids);
		}
		return "addCourse";
	}
}

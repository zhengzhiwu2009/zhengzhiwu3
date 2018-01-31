package com.whaty.platform.sso.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.struts2.ServletActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeResource;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.servlet.UserSession;

public class PeResourceInteractionAction extends MyBaseAction<PeResource> {

	private PeResourceService peResourceService;// 引用service层
	private List knowledeList;// 资料List
	private List knowledeListxq;// 资料详情list
	private String ziliaoId;// 资料ID
	private String fkziliao;// 与interaction_teachclass_info表相关联的的外键
	private String teachclassId;// 课程ID
	private PeResource peResource;// 资料实体类
	private EnumConst enumconstByflagasfasdf;
	private String type;// 课程资料类型
	private String coursename;// 做为资料中资料类型
	private String ziLiaoname;// 用于检索资料名
	private String privilege;// 权限
	private List<EnumConst> resourceTypeList;
	private String resetype;
	private List<File> file;
	private List<String> fileFileName;
	private List<String> fileContentType;

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

	public String getResetype() {
		return resetype;
	}

	public void setResetype(String resetype) {
		this.resetype = resetype;
	}

	public List<EnumConst> getResourceTypeList() {
		return resourceTypeList;
	}

	public void setResourceTypeList(List<EnumConst> resourceTypeList) {
		this.resourceTypeList = resourceTypeList;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getZiLiaoname() {
		return ziLiaoname;
	}

	public void setZiLiaoname(String ziLiaoname) {
		this.ziLiaoname = ziLiaoname;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/* 查看资料详情 */
	public String listpeResourcexq() {
		try {
			this.knowledeListxq = peResourceService.listdetails(ziliaoId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "peResourcexq";
	}

	/* 删除课程资料 */
	public String delpeResource() {
		try {
			int i = peResourceService.delete(fkziliao, teachclassId);
			if (i < 1) {
				this.setMsg("删除失败，无记录！");
			} else {
				this.setMsg("删除成功！");
			}
		} catch (Exception e) {
			this.setMsg("删除失败，异常！");
		}
		this.setTogo("/sso/peResourceInteraction_listpeResource.action?teachclassId=" + this.getTeachclassId() + "&coursename=" + this.coursename);
		return "msg";
	}

	/* 课程资料列表 */
	public String listpeResource() {
		try {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			// us.getRoleId().equals("3") || us.getRoleId().equals("R004")
			if (null == us) {
				this.setMsg("页面加载失败，请返回刷新重试");
				this.setTogo("back");
				return "msg";
			}
			this.privilege = us.getRoleId();
			this.knowledeList = peResourceService.listresource(teachclassId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "peResourcelist";

	}

	/* 课程资料修改前数据 */
	public String edit() {
		try {
			this.knowledeList = peResourceService.edit(ziliaoId, teachclassId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "listedit";
	}

	/* 课程资料修改 */
	public String editexe() {
		try {
			PeResource zl = this.getGeneralService().getById(ziliaoId);
			String sql = " select  id from pe_resource where name='" + peResource.getName() + "' and id <> '" + ziliaoId + "'";
			List resNameList = this.getGeneralService().getBySQL(sql);
			if (resNameList != null && resNameList.size() > 0) {
				this.setMsg("资料名不能重复");
				return "msg";
			}

			if (peResource.getContent().length() > 0 && peResource.getContent().toLowerCase().indexOf("</a>") != -1) {
				int startIdx = peResource.getContent().toLowerCase().indexOf("<");
				int endIdx = peResource.getContent().toLowerCase().lastIndexOf(">");
				// 截取多个上传附件路径并以","分隔
				String fileLink = peResource.getContent().substring(startIdx, endIdx + 1).replaceAll("<a href=\"", "").replaceAll("\\\">(.*?)\\/U", ",/U").replaceAll("\\\">(.*?)\\</a>", "");
				peResource.setFilelink(fileLink);
			}
			zl.setContent(peResource.getContent());
			zl.setName(peResource.getName());
			zl.setDescribe(peResource.getDescribe());
			zl.setFilelink(peResource.getFilelink());
			zl.setFabuunit(peResource.getFabuunit());
			zl.setResetype(peResource.getResetype());
			zl.setReplyDate(peResource.getReplyDate());

			peResourceService.save(zl);

			this.setMsg("修改成功！");
		} catch (Exception e) {
			this.setMsg("修改失败，异常！");
		}
		this.setTogo("/sso/peResourceInteraction_listpeResource.action?teachclassId=" + this.getTeachclassId() + "&coursename=" + this.coursename);
		return "msg";
	}

	/* 课程资料添加功能 */
	public String addziLiao() {
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
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			EnumConst ec1 = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "0");
//			EnumConst ec2 = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "1");
			String hql = "from EnumConst where id = '" + resetype + "'";
			EnumConst ec4 = (EnumConst) this.getGeneralService().getByHQL(hql).get(0);
			peResource.setEnumConstByFlagIsvalid(ec1);
			peResource.setEnumConstByFlagIsvalidSPxianzai(ec1);
			peResource.setEnumConstByFlagIsvalidSPshouye(ec1);
			peResource.setEnumConstByFlagIsvalidSPXiaXian(ec1);
			peResource.setEnumConstByFlagResourceType(ec4);
			peResource.setResetype(ec4.getName());
			// peResource.setResetype(coursename);
			peResource.setCreationdate(new Date());
			peResource.setCreater(us.getId());
			// peResource.setReplydate(new Date());
//			if (peResource.getContent().length() > 0 && peResource.getContent().toLowerCase().indexOf("</a>") != -1) {
//				int startIdx = peResource.getContent().toLowerCase().indexOf("<");
//				int endIdx = peResource.getContent().toLowerCase().lastIndexOf(">");
//				// 截取多个上传附件路径并以","分隔
//				String fileLink = peResource.getContent().substring(startIdx, endIdx + 1).replaceAll("<a href=\"", "").replaceAll("\\\">(.*?)\\/U", ",/U").replaceAll("\\\">(.*?)\\</a>", "");
//			}
			peResource.setFilelink(filePath);
			UUID uuid = UUID.randomUUID();
			String ida = uuid.toString();
			String id = ida.substring(0, 8) + ida.substring(9, 13) + ida.substring(14, 18) + ida.substring(19, 23) + ida.substring(24);
//			peResource.setId(id);
			String sqlname = " select  id from pe_resource where name='" + peResource.getName() + "'";
			List resNameList = this.getGeneralService().getBySQL(sqlname);
			if (resNameList != null && resNameList.size() > 0) {
				this.setMsg("资料名不能重复");
				return "msg";
			}
			PeResource pr = (PeResource) peResourceService.save(peResource);
			
			String sql = "insert into interaction_teachclass_info (id,teachclass_id,type,fk_ziliao) values (s_interaction_teachclass_id.nextval,'" + this.teachclassId + "','" + ec4.getName() + "','" + pr.getId() + "')";

			this.peResourceService.executeBySQL(sql);
			
			this.setMsg("添加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("添加失败，异常！");
		}
		this.setTogo("/sso/peResourceInteraction_listpeResource.action?teachclassId=" + this.getTeachclassId() + "&coursename=" + this.coursename);
		return "msg";

	}

	/* 添加课程资料中转 */
	public String addGoTo() {
		String resourceTypeHql = "from EnumConst ec where ec.namespace = 'FlagDocumentType' order by ec.code";
		try {
			resourceTypeList = this.getGeneralService().getByHQL(resourceTypeHql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "goto";

	}

	/* 课程检索功能 */
	public String peResourcejs() {
		try {
			this.knowledeList = this.peResourceService.peResourcejs(teachclassId, ziLiaoname);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "peResourcejs";
	}

	/**
	 * 下载资料列表
	 * 
	 * @author lenovo
	 * @return
	 */
	public String loadFileLink() {

		this.knowledeListxq = new ArrayList();
		try {
			List list = peResourceService.listdownload(ziliaoId);
			if (null == list || list.size() < 1) {
				this.setMsg("下载出现异常！");
			} else {
				String fklint = list.get(0).toString();
				String[] file = fklint.split(",");
				for (int i = 0; i < file.length; i++) {
					this.knowledeList = new ArrayList();
					String link = file[i];
					String fileName = file[i].substring(link.lastIndexOf("/") + 1);
					knowledeList.add(link);
					knowledeList.add(fileName);
					knowledeListxq.add(knowledeList);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "loadFileLink";

	}

	public PeResource getBean() {
		return (PeResource) super.superGetBean();
	}

	/*
	 * public void setBean(ZiLiao bean) { super.superSetBean(bean); }
	 */

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setEntityClass() {
		this.entityClass = PeResource.class;
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath = "/sso/peResourceInteraction";

	}

	public String getZiliaoId() {
		return ziliaoId;
	}

	public void setZiliaoId(String ziliaoId) {
		this.ziliaoId = ziliaoId;
	}

	public String getFkziliao() {
		return fkziliao;
	}

	public List getKnowledeList() {
		return knowledeList;
	}

	public void setKnowledeList(List knowledeList) {
		this.knowledeList = knowledeList;
	}

	public List getKnowledeListxq() {
		return knowledeListxq;
	}

	public void setKnowledeListxq(List knowledeListxq) {
		this.knowledeListxq = knowledeListxq;
	}

	public String getTeachclassId() {
		return teachclassId;
	}

	public void setTeachclassId(String teachclassId) {
		this.teachclassId = teachclassId;
	}

	public PeResourceService getPeResourceService() {
		return peResourceService;
	}

	public void setPeResourceService(PeResourceService peResourceService) {
		this.peResourceService = peResourceService;
	}

	public void setFkziliao(String fkziliao) {
		this.fkziliao = fkziliao;
	}

	public PeResource getPeResource() {
		return peResource;
	}

	public void setPeResource(PeResource peResource) {
		this.peResource = peResource;
	}

}

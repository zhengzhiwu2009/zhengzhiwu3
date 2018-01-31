package com.whaty.platform.entity.web.action.teaching.basicInfo;

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
import java.util.UUID;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
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
public class PeResourceAction extends MyBaseAction<PeResource> {
	
	private List<EnumConst> resourceTagList;
	private List<EnumConst> isValidList;
	private List<EnumConst> resourceTypeList;
	
	private PeResource peResource;
	
	private String id;
	private String name;
	private String reseType;
	private String tagIds;
	private String tagNames;
	private String show;
	private String down;
	private String replyDate;
	private String fabuunit;
	private String describe; 
	private String content;
	private String msg;
	private String togo;
	private String showusers;
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

	public String getShowusers() {
		return showusers;
	}

	public void setShowusers(String showusers) {
		this.showusers = showusers;
	}

	public List<EnumConst> getResourceTypeList() {
		return resourceTypeList;
	}

	public void setResourceTypeList(List<EnumConst> resourceTypeList) {
		this.resourceTypeList = resourceTypeList;
	}

	public List<EnumConst> getResourceTagList() {
		return resourceTagList;
	}

	public void setResourceTagList(List<EnumConst> resourceTagList) {
		this.resourceTagList = resourceTagList;
	}

	public List<EnumConst> getIsValidList() {
		return isValidList;
	}

	public void setIsValidList(List<EnumConst> isValidList) {
		this.isValidList = isValidList;
	}
	
	public void setBean(PeResource instance) {
		super.superSetBean(instance);

	}

	public PeResource getBean() {
		return super.superGetBean();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReseType() {
		return reseType;
	}

	public void setReseType(String reseType) {
		this.reseType = reseType;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getTagNames() {
		return tagNames;
	}

	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getDown() {
		return down;
	}

	public void setDown(String down) {
		this.down = down;
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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTogo() {
		return togo;
	}

	public void setTogo(String togo) {
		this.togo = togo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PeResource getPeResource() {
		return peResource;
	}

	public void setPeResource(PeResource peResource) {
		this.peResource = peResource;
	}

	/**
	 * 初始化列表
	 * 
	 * @author Lzh
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Set capabilitySet = us.getUserPriority().keySet();
		boolean canAdd = false;
		boolean canUpdate = false;
		boolean canDelete = false;
		/* 添加按钮 */
		if (capabilitySet.contains(this.servletPath + "_toAdd.action")) {
			this.getGridConfig().addMenuScript(this.getText("添加"), "{window.location='/entity/teaching/peResource_toAdd.action';}");
		}
		/* 删除按钮 */
		if (capabilitySet.contains(this.servletPath + "_delete.action")) {
			canDelete = true;
		}
		this.getGridConfig().setCapability(canAdd, canDelete, canUpdate);

		this.getGridConfig().setTitle("资料库");

		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("资料名称"), "name", true, true, true, "TextField", false, 200, "");
		
//		this.getGridConfig().addColumn(this.getText("资料分类"), "resetype", true, true, true, "TextField", false, 100, "");
		
		ColumnConfig columnConfigFlagResourceType = new ColumnConfig(this.getText("资料分类"), "enumConstByFlagResourceType.name", true, true, true, "TextField", false, 100, "");
		String sql1 = "select a.id ,a.name from enum_const a where a.namespace='FlagDocumentType'";
		columnConfigFlagResourceType.setComboSQL(sql1);
		
		this.getGridConfig().addColumn(columnConfigFlagResourceType);
		
		ColumnConfig columnConfigResourceTag = new ColumnConfig(this.getText("资料标签"), "enumConstByFlagResourceTag.name", true, true, false, "TextField", false, 100, "");
		String sql2 = "select a.id ,a.name from enum_const a where a.namespace='FlagResourceTag'";
		columnConfigResourceTag.setComboSQL(sql2);
		this.getGridConfig().addColumn(columnConfigResourceTag);
		
//		this.getGridConfig().addColumn(this.getText("资料标签"), "resourceTags", true, true, false, "TextField", false, 100, "");
		
		ColumnConfig columnConfigIsvalid = new ColumnConfig(this.getText("是否发布"), "enumConstByFlagIsvalid.name", true, false, true, "TextField", false, 100, "");
		String sql3 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid' and a.id<>'3x' ";
		columnConfigIsvalid.setComboSQL(sql3);
		
		ColumnConfig columnConfigoffline = new ColumnConfig(this.getText("是否下线"), "enumConstByFlagIsvalidSPXiaXian.name", true, false, true, "TextField", false, 100, "");
		String sql7 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid' and a.id<>'3x' ";
		columnConfigoffline.setComboSQL(sql7);
		
		ColumnConfig columnConfigShouYe = new ColumnConfig(this.getText("是否首页显示"), "enumConstByFlagIsvalidSPshouye.name", true, true, true, "TextField", false, 100, "");
		String sql4 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid' and a.id<>'3x' ";
		columnConfigShouYe.setComboSQL(sql4);
		
		
		ColumnConfig columnConfigXiaZai = new ColumnConfig(this.getText("是否首页可下载"), "enumConstByFlagIsvalidSPxianzai.name", true, true, true, "TextField", false, 100, "");
		String sql5 = "select a.id ,a.name from enum_const a where a.namespace='FlagIsvalid' and a.id<>'3x' ";
		columnConfigXiaZai.setComboSQL(sql5);
		
		this.getGridConfig().addColumn(columnConfigIsvalid);
		this.getGridConfig().addColumn(columnConfigoffline);
		this.getGridConfig().addColumn(columnConfigShouYe);
		this.getGridConfig().addColumn(columnConfigXiaZai);
		
		
		
		this.getGridConfig().addColumn(this.getText("是否置顶"), "flagtop", false, false, true,  "textField");
		this.getGridConfig().addColumn(this.getText("颁布/发布时间"), "replyDate", false, true, true,  "TextField", true, 10);
		this.getGridConfig().addColumn(this.getText("颁布/发布单位"),"fabuunit" ,true, true, true,"TextField",true,100,"");
		
		//Lwq 2016-04-26
		ColumnConfig columnConfigInformationSource = new ColumnConfig(this.getText("资料来源"), "enumConstByInformationResource.name", true, false, true, "TextField", true, 100, "");
		String sql6 = "select a.id ,a.name from enum_const a where a.namespace='InformationResource'";
		columnConfigInformationSource.setComboSQL(sql6);
		this.getGridConfig().addColumn(columnConfigInformationSource);
		this.getGridConfig().addColumn(this.getText("上传人"),"creater" ,true, false, true, "TextField", false, 200, "");
		
		this.getGridConfig().addColumn(this.getText("收藏人数"),"sc_cou" ,false, false, true,"TextField",true,100,"");
		this.getGridConfig().addColumn(this.getText("点赞人数"),"dz_cou" ,false, false, true,"TextField",true,100,"");
		this.getGridConfig().addColumn(this.getText("浏览次数"),"views" ,false, false, true,"TextField",true,100,"");
		
		this.getGridConfig().addColumn(this.getText("资料描述"), "describe", false, true, false, "TextArea", true, 500);
		this.getGridConfig().addColumn(this.getText("资料附件"), "content", false, true, false, "TextEditor", true, 2000);
		this.getGridConfig().addColumn(this.getText("课程名称"), "kname", true, false, false, "textField", true, 500);
		this.getGridConfig().addColumn(this.getText("课程编号"), "kbianhao", true, false, false, "textField", true, 500);
		
		
		this.getGridConfig().addRenderFunction(this.getText("查看相关课程"),
				"<center><a href=\"/entity/teaching/kechengPerResource.action?id=${value}&typeflag=viewkecheng\" >查看课程</a></center>", "id");
//		this.getGridConfig().addRenderFunction(this.getText("添加相关课程"),
//				"<center><a href=\"/entity/teaching/kechengPerResource.action?id=${value}&typeflag=addkecheng\"  >添加课程</a></center>", "id");
		/* 双击修改 */
		if (capabilitySet.contains(this.servletPath + "_update.action")) {
			this.getGridConfig().addRenderFunction(this.getText("修改"), 
					"<center><a href=\"/entity/teaching/peResource_toEdit.action?id=${value}&typeflag=viewkecheng\" >修改资料</a></center>", "id");
		}
		/* 查看资料详情 */
		if (capabilitySet.contains(this.servletPath + "_resourceDetail.action")) {
			this.getGridConfig().addRenderFunction(this.getText("查看资料详情"), 
					"<center><a href=\"/cms/doumentDetail.htm?myId=${value}&uid=3\" target=_blank >查看资料详情</a></center>", "id");
		}

		if (us.getUserLoginType().equals("3")) {
			if (capabilitySet.contains(this.servletPath + "_tagManager.action")) {
				this.getGridConfig().addMenuScript(this.getText("标签管理"), "{window.location='/entity/basic/tagManager.action';}");
			}
			if (capabilitySet.contains(this.servletPath + "_fabu.action")) {
				this.getGridConfig().addMenuFunction(this.getText("设置发布"), "FlagIsvalid.true");
			}
			if (capabilitySet.contains(this.servletPath + "_tingyong.action")) {
				this.getGridConfig().addMenuFunction(this.getText("取消发布"), "FlagIsvalid.false");
			}
			if (capabilitySet.contains(this.servletPath + "_xiaxian.action")) {
				this.getGridConfig().addMenuFunction(this.getText("设置下线"), "FlagXiaXian.true");
			}
			if (capabilitySet.contains(this.servletPath + "_tingyong.action")) {
				this.getGridConfig().addMenuFunction(this.getText("取消下线"), "FlagXiaXian.false");
			}
			if (capabilitySet.contains(this.servletPath + "_shouye.action")) {
				this.getGridConfig().addMenuFunction(this.getText("设置首页显示"), "FlagShouYe.true");
			}
			if (capabilitySet.contains(this.servletPath + "_tingyong.action")) {
				this.getGridConfig().addMenuFunction(this.getText("取消首页显示"), "FlagShouYe.false");
			}
			if (capabilitySet.contains(this.servletPath + "_zhiding.action")) {
				this.getGridConfig().addMenuFunction(this.getText("设为置顶"), "FlagZhiDing.true");
			}
			if (capabilitySet.contains(this.servletPath + "_tingyong.action")) {
				this.getGridConfig().addMenuFunction(this.getText("取消置顶"), "FlagZhiDing.false");
			}
			if (capabilitySet.contains(this.servletPath + "_xiazai.action")) {
				this.getGridConfig().addMenuFunction(this.getText("设置首页可下载"), "FlagXiaZai.true");
			}
			if (capabilitySet.contains(this.servletPath + "_tingyong.action")) {
				this.getGridConfig().addMenuFunction(this.getText("取消首页可下载"), "FlagXiaZai.false");
			}
			this.getGridConfig().addMenuFunction(this.getText("批量添加至课程"), "/entity/teaching/peResource_addToCourse.action", false, false);
//			this.getGridConfig().addMenuFunction(this.getText("批量添加至课程"), "/entity/teaching/kechengPerResource.action?typeflag=addkecheng", false, false);
			
		}
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
		this.servletPath = "/entity/teaching/peResource";
	}
	
	/**
	 * 重写框架方法：添加数据
	 * 
	 * @author Lzh
	 * @return
	 * @throws EntityException 
	 * @throws EntityException 
	 */
	public void checkBeforeAdd() throws EntityException {
		checkBeforeUpdate();
		UUID uuid = UUID.randomUUID();
		String ida=uuid.toString();
		String id=ida.substring(0,8)+ida.substring(9,13)+ida.substring(14,18)+ida.substring(19,23)+ida.substring(24); 
		this.getBean().setId(id);
		EnumConst ec = this.getEnumConstService().getByNamespaceCode("FlagIsvalid", "0");
		this.getBean().setEnumConstByFlagIsvalid(ec);
		this.getBean().setEnumConstByFlagIsvalidSPXiaXian(ec);
		this.getBean().setCreationdate(new Date());
	}
	
	public void checkBeforeUpdate() throws EntityException {
		String resname=this.getBean().getName();
		String resId=this.getBean().getId();
		String sql=" select  id from pe_resource where name='"+resname+"' and id <> '"+resId+"'";
		List resNameList=this.getGeneralService().getBySQL(sql);
		if(resNameList !=null && resNameList.size()>0	){
			
				throw new EntityException("资料名不能重复");
				
		}
		if(this.getBean().getResetype().length()>100)
		{
			throw new EntityException("资料分类只能为100以内的字符");
		}
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		this.superSetBean((PeResource) setSubIds(this.getBean()));
		String content=this.getBean().getContent();
		
		String fileLink = "";
		if (content.length() > 0 && content.toLowerCase().indexOf("</a>") != -1) {
			int startIdx = content.toLowerCase().indexOf("<");
			int endIdx = content.toLowerCase().lastIndexOf(">");
			//截取多个上传附件路径并以","分隔
			fileLink = content.substring(startIdx, endIdx + 1).replaceAll("<a href=\"", "").replaceAll("\\\">(.*?)\\/U", ",/U").replaceAll("\\\">(.*?)\\</a>", "");
		}
		this.getBean().setCreater(us.getId());
		this.getBean().setFilelink(fileLink);
	}
	
	/**
	 * 资料管理列表
	 * 
	 * @author Lzh
	 * @return
	 */
	public Page list() {
		Page page = null;
		StringBuffer sqlBuffer = new StringBuffer();
		try {
			sqlBuffer.append("SELECT * FROM (select zl.id,zl.name,e5.name resourceType, zl.resourcetags, e1.name as youxiao, e4.name as xiaxian,e2.name as shouye , e3.name as xiazai, CASE  WHEN zl.FLAG_TOP= '1' THEN  '是'   ELSE  '否' END AS zhiding " +
					" , zl.REPLYDATE as replyDate ,zl.Fabuunit, e6.name INFORMATIONSOURCE, su.login_id creater, nvl(src.sc_count, 0) sc_count, nvl(srd.dz_count, 0) dz_count, zl.views, '' as DESCRIBE ,'' as kname,'' as kbianhao , zl.creationdate from PE_RESOURCE zl inner join enum_const e1 on zl.flag_isvalid=e1.id " +
					" inner join enum_const e2 on zl.FLAG_INDEX=e2.id  " +
					" inner join enum_const e3 on zl.FLAG_DOWNLOAD=e3.id " +
					" inner join enum_const e4 on zl.flag_offline=e4.id " + 
					" inner join enum_const e5 on zl.flag_resourceType = e5.id " + 
					" INNER JOIN ENUM_CONST E6 ON ZL.INFORMATION_RESOURCE = E6.ID " +
					" inner join sso_user su on zl.creater = su.id " +
					" LEFT JOIN (SELECT COUNT(USERID) DZ_COUNT, SR.RESOURCEID FROM SC_RESOURCE SR WHERE SR.TYPE = '2' GROUP BY SR.RESOURCEID) SRD " +
					" ON SRD.RESOURCEID = zl.ID LEFT JOIN (SELECT COUNT(USERID) SC_COUNT, SR.RESOURCEID FROM SC_RESOURCE SR WHERE SR.TYPE = '1' GROUP BY SR.RESOURCEID) SRC " +
					" ON SRC.RESOURCEID = zl.ID " +
					" where (zl.flag_isaudit = '2' or zl.flag_isaudit is null)" +
					" and (zl.flag_isopen = '2' or zl.flag_isopen is null)");
			sqlBuffer.append(" ) WHERE 1 = 1 ");
			/* 拼接查询条件 */
			Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do {
				if (!iterator.hasNext())
					break;
				java.util.Map.Entry entry = (java.util.Map.Entry) iterator.next();
				String name = entry.getKey().toString();
				String value = ((String[]) entry.getValue())[0].toString();
				if (!name.startsWith("search__"))
					continue;
				if ("".equals(value))
					continue;
				if (name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")) {
					name = name.substring(name.lastIndexOf(".", name.lastIndexOf(".") - 1) + 1);
				} else {
					name = name.substring(8);
				}
				/* 资料分类 */
				if (name.equals("enumConstByFlagResourceType.name")) {
					name = "resourceType";
				}
				/* 资料标签 */
				if (name.equals("enumConstByFlagResourceTag.name")) {
					name = "resourceTags";
				}
				/* 是否有效-发布 */
				if (name.equals("enumConstByFlagIsvalid.name")) {
					name = "youxiao";
				}
				/* 是否有效-下线*/
				if (name.equals("enumConstByFlagIsvalidSPXiaXian.name")) {
					name = "xiaxian";
				}
				/* 是否首页 */
				if (name.equals("enumConstByFlagIsvalidSPshouye.name")) {
					name = "shouye";
				}
				/* 是否下载 */
				if (name.equals("enumConstByFlagIsvalidSPxianzai.name")) {
					name = "xiazai";
				}
				/* 资料来源 */
				if (name.equals("enumConstByInformationResource.name")) {
					name = "INFORMATIONSOURCE";
				}
				if (name.equals("name")) {
					name = "name";
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
				if("youxiao".equals(name)|| "shouye".equals(name)|| "xiazai".equals(name)){
					sqlBuffer.append(" and UPPER(" + name + ") = UPPER('" + value + "')");
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
				/* 是否有效-发布 */
				if (temp.equals("enumConstByFlagIsvalid.name")) {
					temp = "youxiao";
				}
				/* 是否下线 */
				if (temp.equals("enumConstByFlagIsvalidSPXiaXian.name")) {
					temp = "xiaxian";
				}
				
				/* 是否首页 */
				if (temp.equals("enumConstByFlagIsvalidSPshouye.name")) {
					temp = "shouye";
				}
				/* 是否下载 */
				if (temp.equals("enumConstByFlagIsvalidSPxianzai.name")) {
					temp = "xiazai";
				}
				/* 资料来源 */
				if (temp.equals("enumConstByInformationResource.name")) {
					temp = "INFORMATIONSOURCE";
				}
				/* 是否置顶 */
				if (temp.equals("flagtop")) {
					temp = "zhiding";
				}
				if (temp.equals("name")) {
					temp = "name";
				}
				if (temp.equals("id")) {
					temp = "creationdate";
				}
				
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc")) {					
					sqlBuffer.append(" order by zhiding desc, " + temp + " desc ");
					
				}else{
					sqlBuffer.append(" order by zhiding desc, " + temp + " asc ");
				}
				
			} else {
				    sqlBuffer.append(" order by zhiding desc, creationdate desc");
			}
			page = this.getGeneralService().getByPageSQL(sqlBuffer.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	/**
	 * 加载资料信息
	 * 
	 * @author Lzh
	 * @return
	 */
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(PeResource.class);
		dc.createCriteria("enumConstByFlagIsvalid", "enumConstByFlagIsvalid");
		dc.createCriteria("enumConstByFlagIsvalidSPshouye", "enumConstByFlagIsvalidSPshouye");
		dc.createCriteria("enumConstByFlagIsvalidSPxianzai", "enumConstByFlagIsvalidSPxianzai");
		dc.createCriteria("enumConstByFlagIsvalidSPXiaXian", "enumConstByFlagIsvalidSPXiaXian");
		dc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(dc, Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
			String id = this.getBean().getId();

			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this.getGridConfig().getListColumnConfig()));
				Container o = new Container();
				o.setBean(page.getItems().get(0));
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount", 1);
				map.put("models", list);
			}
			JsonUtil.setDateformat("yyyy-MM-dd");
			this.setJsonString(JsonUtil.toJSONString(map));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}
	/**
	 * 重写框架方法：删除数据
	 * 
	 * @author Lzh
	 * @return
	 */
	public Map delete() {
		Map map = new HashMap();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String str = this.getIds();
			if (str != null && str.length() > 0) {
				String[] ids = str.split(",");
				List<String> idList = new ArrayList<String>();
				for (int i = 0; i < ids.length; i++) {
					idList.add(ids[i]);
				}

				// 执行删除
				try {
					String idss = "";
					for (int j = 0; j < idList.size(); j++) {
						idss += "'" + idList.get(j).toString() + "'";
					}
					if(ids.length!=1){
						map.clear();
						map.put("success", "false");
						map.put("info", "只能操作一条记录");
						return map;
					}
					String delziLiao = "delete from pe_resource sc where sc.id = " + idss + "";
					String delFkZiLiao="delete from interaction_teachclass_info ii where ii.fk_ziliao=" + idss + "";
					this.getGeneralService().executeBySQL(delziLiao);
					this.getGeneralService().executeBySQL(delFkZiLiao);
					map.put("success", "true");
					map.put("info", "删除成功");
				} catch (RuntimeException e) {
					return this.checkForeignKey(e);
				} catch (Exception e1) {
					map.clear();
					map.put("success", "false");
					map.put("info", "删除失败");
				}
			} else {
				map.put("success", "false");
				map.put("info", "请选择操作项");
			}
		}
		return map;
	}
	

	/**
	 * 重写框架方法：更新字段
	 * 
	 * @author Lzh
	 * @return
	 */
	@SuppressWarnings( { "unchecked", "unchecked", "unchecked" })
	public Map updateColumn() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		Map map = new HashMap();
		String msg = "";
		int existNum = 0;
		String action = this.getColumn();
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");

			List idList = new ArrayList();
			for (int i = 0; i < ids.length; i++) {
				idList.add(ids[i]);
			}
			try {
				List<PeResource> plist = new ArrayList<PeResource>();
				DetachedCriteria pubdc = DetachedCriteria.forClass(PeResource.class);
				
				pubdc.add(Restrictions.in("id", ids));
				plist = this.getGeneralService().getList(pubdc);
				EnumConst enumConst = null;
				String zhiding=null;
				Date curDate = new Date();
				if (action.equals("FlagIsvalid.true")) {
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
					msg = "发布";
				}else if (action.equals("FlagIsvalid.false")) {
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
					msg = "取消发布";
				}
				if (action.equals("FlagXiaXian.true")) {
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
					msg = "下线";
				}else if (action.equals("FlagXiaXian.false")) {
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
					msg = "取消下线";
				}
				
				if (action.equals("FlagShouYe.true")) {
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
					msg = "首页显示";
				}else if (action.equals("FlagShouYe.false")) {
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
					msg = "取消首页";
				}
				if (action.equals("FlagXiaZai.true")) {
					enumConst= this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "1");
					msg = "下载";
				}else if (action.equals("FlagXiaZai.false")) {
					enumConst = this.getMyListService().getEnumConstByNamespaceCode("FlagIsvalid", "0");
					msg = "取消下载";
				}				
				if (action.equals("FlagZhiDing.true")) {
					zhiding="1";
					msg = "置顶";
				}else if (action.equals("FlagZhiDing.false")) {
					zhiding="2";
					msg = "取消置顶";
				}
				if(action !=null && !"".equals(action)){
				for(int i=0;i<plist.size();i++){
					PeResource peResource = plist.get(i);
					if(enumConst==null && zhiding==null){
						msg = "参数无效";
					}else if (null !=enumConst  && action.indexOf("FlagIsvalid")!=-1){
						peResource.setEnumConstByFlagIsvalid(enumConst);
					
					}else if (null !=enumConst  && action.indexOf("FlagShouYe")!=-1){
						peResource.setEnumConstByFlagIsvalidSPshouye(enumConst);
					
					}else if (null !=enumConst  && action.indexOf("FlagXiaZai")!=-1){
						peResource.setEnumConstByFlagIsvalidSPxianzai(enumConst);
					
					}
					else if (null !=enumConst  && action.indexOf("FlagXiaXian")!=-1){
						peResource.setEnumConstByFlagIsvalidSPXiaXian(enumConst);
					
					}
					
					else if(action.indexOf("FlagZhiDing")!=-1 ){
						peResource.setFlagtop(zhiding);
					}
					
					if("FlagIsvalid.true".equals(action)){
					}
					plist.set(i, peResource);
					}
				}else {
					msg = "参数无效";
				}
				
			
				this.getGeneralService().saveList(plist);
				map.put("success", "true");
				map.put("info", msg + "共有" + ids.length + "操作成功");
				
			} catch (Exception e) {
				e.printStackTrace();
				map.clear();
				map.put("success", "false");
				map.put("info", msg + "操作失败");
				return map;
			}
		} else {
			map.clear();
			map.put("success", "false");
			map.put("info", "至少一条数据被选择");
			return map;
		}
		return map;
	}
	/**
	 * 资料查看功能 lzh
	 * @return
	 */
	public String courseInfo(){
		String id = ServletActionContext.getRequest().getParameter("id");
		String sql1="select zl.name,zl.RESETYPE ,zl.DESCRIBE,zl.fabuunit, to_char(zl.REPLYDATE,'yyyy-MM-dd') as replyDate from PE_RESOURCE zl inner join enum_const e1 on zl.flag_isvalid=e1.id "
		+" where zl.id='"+id+"'";
		String filelink=" select zl.FILE_LINK  from PE_RESOURCE zl "+
		" where zl.id='"+id+"' and zl.FILE_LINK is not null ";
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		
		String resourcec=	
			"select *\n" +
			"  from (select a.*, rownum rownum_\n" + 
			"          from (SELECT *\n" + 
			"                  FROM (select id, name,  RESETYPE, fabuunit,  to_char(replyDate,'yyyy-MM-dd') \n" + 
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
			
		ServletActionContext.getRequest().setAttribute("usType", us.getUserLoginType());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "courseInfo"	;
	}
	
	public String toAdd() {
		String resourceTag_hql = "from EnumConst ec where ec.namespace = 'FlagResourceTag'";
		String isValid_hql = "from EnumConst ec where ec.namespace = 'FlagIsvalid' and ec.id<>'3x' ";
		String resourceTypeHql = "from EnumConst ec where ec.namespace = 'FlagDocumentType' order by ec.code";
		try {
			resourceTagList = this.getGeneralService().getByHQL(resourceTag_hql);
			isValidList = this.getGeneralService().getByHQL(isValid_hql);
			resourceTypeList = this.getGeneralService().getByHQL(resourceTypeHql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "toAdd";
	}
	
	public String savePeResource() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String hql1 = "from EnumConst ec where ec.id = '" + show + "'";
		String hql2 = "from EnumConst ec where ec.id = '" + down + "'";
		String hql3 = "from EnumConst ec where ec.id = '" + reseType + "'";
		EnumConst enumShow;
		EnumConst enumDown;
		EnumConst isValid = new EnumConst();
		EnumConst isOffLine = new EnumConst();
		EnumConst isOpen = new EnumConst();
		EnumConst flagResourceType = new EnumConst();
		EnumConst informationResource = this.getEnumConstService().getByNamespaceCode("InformationResource", "2");
		String path = "/UserFiles/File";
		String realPath = ServletActionContext.getServletContext().getRealPath(path);
		String filePath = "";
		
		InputStream is = null;
		OutputStream os = null;
		try {
			if(file != null && !"".equals(file)) {
				//文件上传
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
		
			enumShow = (EnumConst) this.getGeneralService().getByHQL(hql1).get(0);
			enumDown = (EnumConst) this.getGeneralService().getByHQL(hql2).get(0);
			flagResourceType = (EnumConst) this.getGeneralService().getByHQL(hql3).get(0);
			isValid.setId("3");
			isOffLine.setId("3");
			isOpen.setId("2");
			
			PeResource pr = new PeResource();
			pr.setName(name);
			pr.setResetype(flagResourceType.getName());
			pr.setResourceTagIds(tagIds);
			pr.setResourceTagNames(tagNames);
			pr.setEnumConstByFlagIsvalidSPshouye(enumShow);
			pr.setEnumConstByFlagIsvalidSPxianzai(enumDown);
			pr.setEnumConstByFlagIsvalid(isValid);
			pr.setEnumConstByFlagIsvalidSPXiaXian(isOffLine);
			pr.setEnumConstByFlagIsOpen(isOpen);
			pr.setEnumConstByFlagIsAudit(isOpen);
			if(replyDate != null && !"".equals(replyDate)) {
				Date reDate = new SimpleDateFormat("yyyy-MM-dd").parse(replyDate);
				pr.setReplyDate(reDate);
			}
			pr.setFabuunit(fabuunit);
			pr.setDescribe(describe);
			pr.setContent(content);
			pr.setEnumConstByFlagResourceType(flagResourceType);
//			// 截取多个上传附件路径并以","分隔
//			int startIdx = content.toLowerCase().indexOf("<");
//			int endIdx = content.toLowerCase().lastIndexOf(">");
//			String fileLink = content.substring(startIdx, endIdx + 1).replaceAll("<a href=\"", "").replaceAll("\\\">(.*?)\\/U", ",/U").replaceAll("\\\">(.*?)\\</a>", "");
			pr.setFilelink(filePath);
			pr.setCreater(us.getId());
			pr.setCreationdate(new Date());
			pr.setEnumConstByInformationResource(informationResource);//资料来源
			
			pr.setViews(0);
			pr.setShowUsers(showusers);
			this.getGeneralService().save(pr);
			msg = "添加成功!";
			this.togo = "/entity/teaching/peResource.action";
		} catch (EntityException e) {
			msg = "添加失败,请重新添加!";
			this.togo = "/entity/teaching/peResource_toAdd.action";
			e.printStackTrace();
		} catch (ParseException e) {
			msg = "添加失败,请重新添加!";
			this.togo = "/entity/teaching/peResource_toAdd.action";
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			msg = "添加失败,请重新添加!";
			this.togo = "/entity/teaching/peResource_toAdd.action";
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			msg = "添加失败,请重新添加!";
			this.togo = "/entity/teaching/peResource_toAdd.action";
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
		return "m_msg";
	}
	
	public String toEdit() {
		String hql = "from PeResource where id ='" + id + "'";
		String resourceTag_sql = "from EnumConst ec where ec.namespace='FlagResourceTag'";
		String isValid_sql = "from EnumConst ec where ec.namespace='FlagIsvalid' and ec.id<>'3x' ";
		String resourceTypeHql = "from EnumConst ec where ec.namespace = 'FlagDocumentType' order by ec.code";
		try {
			peResource = (PeResource)this.getGeneralService().getByHQL(hql).get(0);
			resourceTagList = this.getGeneralService().getByHQL(resourceTag_sql);
			isValidList = this.getGeneralService().getByHQL(isValid_sql);
			resourceTypeList = this.getGeneralService().getByHQL(resourceTypeHql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		
		return "toEdit";
	}
	
	public String editPeResource() {
		String hql1 = "from EnumConst ec where ec.id = '" + show + "'";
		String hql2 = "from EnumConst ec where ec.id = '" + down + "'";
		String hql3 = "from EnumConst ec where ec.id = '" + reseType + "'";
		EnumConst enumShow;
		EnumConst enumDown;
		EnumConst isValid = new EnumConst();
		EnumConst isOffLine = new EnumConst();
		EnumConst flagResourceType;
		String path = "/UserFiles/File";
		String realPath = ServletActionContext.getServletContext().getRealPath(path);
		String filePath = "";
		
		InputStream is = null;
		OutputStream os = null;
		System.out.println("==============" + file);
		try {
			if(file != null && !"".equals(file)) {
				//文件上传
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
			if(replyDate != null && !"".equals(replyDate)) {
				reDate = new SimpleDateFormat("yyyy-MM-dd").parse(replyDate);
			}
			
			enumShow = (EnumConst) this.getGeneralService().getByHQL(hql1).get(0);
			enumDown = (EnumConst) this.getGeneralService().getByHQL(hql2).get(0);
			flagResourceType = (EnumConst) this.getGeneralService().getByHQL(hql3).get(0);
			isValid.setId("2");
			isOffLine.setId("3");
			
			flagResourceType.setId(reseType);
			
			PeResource pr = this.getGeneralService().getById(id);
			pr.setName(name);
			pr.setResetype(flagResourceType.getName());
			pr.setResourceTagIds(tagIds);
			pr.setResourceTagNames(tagNames);
			pr.setEnumConstByFlagIsvalidSPshouye(enumShow);
			pr.setEnumConstByFlagIsvalidSPxianzai(enumDown);
			pr.setEnumConstByFlagIsvalid(isValid);
			pr.setEnumConstByFlagIsvalidSPXiaXian(isOffLine);
			pr.setReplyDate(reDate);
			pr.setFabuunit(fabuunit);
			pr.setDescribe(describe);
			pr.setContent(content);
			pr.setShowUsers(showusers);
			pr.setEnumConstByFlagResourceType(flagResourceType);
			// 截取多个上传附件路径并以","分隔
//			int startIdx = content.toLowerCase().indexOf("<");
//			int endIdx = content.toLowerCase().lastIndexOf(">");
//			String fileLink = content.substring(startIdx, endIdx + 1).replaceAll("<a href=\"", "").replaceAll("\\\">(.*?)\\/U", ",/U").replaceAll("\\\">(.*?)\\</a>", "");
			pr.setFilelink(filePath);
			this.getGeneralService().update(pr);
			msg = "修改成功!";
			this.togo = "/entity/teaching/peResource.action";
		} catch (EntityException e) {
			msg = "修改失败,请重新添加!";
			this.togo = "/entity/teaching/peResource_toEdit.action";
			e.printStackTrace();
		} catch (ParseException e) {
			msg = "修改失败,请重新添加!";
			this.togo = "/entity/teaching/peResource_toEdit.action";
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			msg = "修改失败,请重新添加!";
			this.togo = "/entity/teaching/peResource_toEdit.action";
		} catch (IOException e) {
			e.printStackTrace();
			msg = "修改失败,请重新添加!";
			this.togo = "/entity/teaching/peResource_toEdit.action";
		} finally {
			
		}
		return "m_msg";
	}
	
	
	/**
	 * 批量添加到课程
	 * 
	 * @author Lwq
	 * @return
	 */
	public String addToCourse() {
		if (this.getIds() != null && this.getIds().length() > 0) {
			String[] ids = getIds().split(",");
			/* 存储选中ids便于后续操作使用 */
			ServletActionContext.getRequest().getSession().setAttribute("resourceIds", ids);
		}
		return "addToCourse";
	}
	
}


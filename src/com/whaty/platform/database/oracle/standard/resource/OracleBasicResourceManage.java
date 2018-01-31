/**
 * 
 */
package com.whaty.platform.database.oracle.standard.resource;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.resource.basic.OracleBasicResourceList;
import com.whaty.platform.database.oracle.standard.resource.basic.OracleResource;
import com.whaty.platform.database.oracle.standard.resource.basic.OracleResourceDir;
import com.whaty.platform.database.oracle.standard.resource.basic.OracleResourceType;
import com.whaty.platform.resource.BasicResourceManage;
import com.whaty.platform.resource.basic.Resource;
import com.whaty.platform.resource.basic.ResourceDir;
import com.whaty.platform.resource.basic.ResourceType;
import com.whaty.platform.util.SearchProperty;
import com.whaty.platform.util.XMLParserUtil;

/**
 * @author wq
 * 
 */
public class OracleBasicResourceManage extends BasicResourceManage {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.resource.BasicResourceManage#getResourceType(java.lang.String)
	 */
	public ResourceType getResourceType(String id) throws NoRightException {
		return new OracleResourceType(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.resource.BasicResourceManage#getResource(java.lang.String)
	 */
	public Resource getResource(String id) throws NoRightException {
		return new OracleResource(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.resource.BasicResourceManage#addResourceType()
	 */
	public int addResourceType(String name, String note, String status,
			String xml) throws NoRightException, PlatformException {
		OracleResourceType type = new OracleResourceType();
		type.setName(name);
		type.setNote(note);
		type.setStatus(status);
		type.setResourceFieldList(XMLParserUtil.getResourceFieldList(xml));

		return type.add();
	}

	public int addResource(String title, String language, String description,
			String keywords, String creatUser, String typeId, String dirId,
			String xml) throws NoRightException, PlatformException {
		OracleResource res = new OracleResource();
		res.setTitle(title);
		res.setLanguage(language);
		res.setDiscription(description);
		res.setKeyWords(keywords);
		res.setCreatUser(creatUser);
		res.setResourceType(new OracleResourceType(typeId));
		res.setResourceDir(new OracleResourceDir(dirId));
		res.setResourceContentList(XMLParserUtil.getResourceContentList(xml));
		
		return res.add();
	}
	
	public int addResource(String title, String language, String description,
			String keywords, String creatUser, String typeId, String dirId,
			String xml, String is_index_show) throws NoRightException, PlatformException {
		OracleResource res = new OracleResource();
		res.setTitle(title);
		res.setLanguage(language);
		res.setDiscription(description);
		res.setKeyWords(keywords);
		res.setCreatUser(creatUser);
		res.setResourceType(new OracleResourceType(typeId));
		res.setResourceDir(new OracleResourceDir(dirId));
		res.setResourceContentList(XMLParserUtil.getResourceContentList(xml));
		res.setIs_index_show(is_index_show);
		return res.add();
	}

	public int getResourcesNum() throws NoRightException {
		OracleBasicResourceList basicList = new OracleBasicResourceList();
		return basicList.getResourcesNum(null);
	}

	public List getResourceList() throws NoRightException {
		OracleBasicResourceList basicList = new OracleBasicResourceList();
		return basicList.getResources(null, null);
	}

	public int getResourceTypesNum() throws NoRightException {
		OracleBasicResourceList basicList = new OracleBasicResourceList();
		return basicList.getResourceTypesNum(null);
	}

	public List getResourceTypeList() throws NoRightException {
		OracleBasicResourceList basicList = new OracleBasicResourceList();
		return basicList.getResourceTypes(null, null);
	}

	public int deleteResource(String id) throws NoRightException,
			PlatformException {
		OracleResource res = new OracleResource(id);
		return res.delete();
	}

	public int deleteResourceType(String id) throws NoRightException,
			PlatformException {
		OracleResourceType type = new OracleResourceType(id);

		return type.delete();
	}

	public int updateResourceType(String id, String name, String note,
			String status, String xml) throws NoRightException,
			PlatformException {
		OracleResourceType type = new OracleResourceType(id);
		type.setName(name);
		type.setNote(note);
		type.setStatus(status);
		type.setResourceFieldList(XMLParserUtil.getResourceFieldList(xml));

		return type.update();
	}

	public int updateResource(String id, String title, String language,
			String description, String keywords, String creatUser,
			String typeId, String dirId) throws NoRightException,
			PlatformException {
		OracleResource res = new OracleResource(id);
		res.setTitle(title);
		res.setLanguage(language);
		res.setDiscription(description);
		res.setKeyWords(keywords);
		res.setCreatUser(creatUser);
		res.setResourceType(new OracleResourceType(typeId));
		res.setResourceDir(new OracleResourceDir(dirId));

		return res.update();
	}

	public int updateResource(String id, String title, String language,
			String description, String keywords, String creatUser,
			String typeId, String dirId, String xml) throws NoRightException,
			PlatformException {
		OracleResource res = new OracleResource();
		res.setId(id);
		res.setTitle(title);
		res.setLanguage(language);
		res.setDiscription(description);
		res.setKeyWords(keywords);
		res.setCreatUser(creatUser);
		res.setResourceType(new OracleResourceType(typeId));
		res.setResourceDir(new OracleResourceDir(dirId));
		res.setResourceContentList(XMLParserUtil.getResourceContentList(xml));
		res.setStatus("1");

		return res.update();
	}

	public ResourceDir getResourceDir(String id) throws NoRightException,
			PlatformException {
		return new OracleResourceDir(id);
	}

	public List getResourceDirs(String id, String name, String parent,
			String note, String status, String isinherit)
			throws NoRightException, PlatformException {
		OracleBasicResourceList basicList = new OracleBasicResourceList();
		List searchList = new ArrayList();
		if (id != null)
			searchList.add(new SearchProperty("id", id, "="));
		if (name != null)
			searchList.add(new SearchProperty("name", name, "like"));
		if (parent != null)
			searchList.add(new SearchProperty("parent", parent, "="));
		if (note != null)
			searchList.add(new SearchProperty("note", note, "like"));
		if (status != null)
			searchList.add(new SearchProperty("status", status, "="));
		if (isinherit != null)
			searchList.add(new SearchProperty("isinherit", isinherit, "="));
		return basicList.getResourceDirs(searchList, null);
	}

	public List getResources(String id, String title, String language,
			String description, String keywords, String creatUser,
			String type_id, String dir_id) throws NoRightException,
			PlatformException {
		OracleBasicResourceList basicList = new OracleBasicResourceList();
		List searchList = new ArrayList();
		if (id != null)
			searchList.add(new SearchProperty("id", id, "="));
		if (title != null)
			searchList.add(new SearchProperty("title", title, "like"));
		if (language != null)
			searchList.add(new SearchProperty("language", language, "="));
		if (description != null)
			searchList.add(new SearchProperty("description", description,
					"like"));
		if (keywords != null)
			searchList.add(new SearchProperty("keywords", keywords, "="));
		if (creatUser != null)
			searchList.add(new SearchProperty("creatUser", creatUser, "="));
		if (type_id != null)
			searchList.add(new SearchProperty("type_id", type_id, "="));
		if (dir_id != null)
			searchList.add(new SearchProperty("dir_id", dir_id, "="));
		return basicList.getResources(searchList, null);
	}

	public int updateResourceDir(String id, String name, String parent,
			String note, String status, String isInherit)
			throws NoRightException, PlatformException {
		OracleResourceDir dir = new OracleResourceDir(id);
		dir.setName(name);
		dir.setParent(parent);
		dir.setNote(note);
		dir.setStatus(status);
		dir.setIsInherit(isInherit);

		return dir.update();
	}

	public int deleteResourceDir(String id) throws NoRightException,
			PlatformException {
		OracleResourceDir dir = new OracleResourceDir(id);
		return dir.delete();
	}

	public int addResourceDir(String name, String parent, String note,
			String status, String isInherit) throws NoRightException,
			PlatformException {
		OracleResourceDir dir = new OracleResourceDir();
		dir.setName(name);
		dir.setParent(parent);
		dir.setNote(note);
		dir.setStatus(status);
		dir.setIsInherit(isInherit);

		return dir.add();
	}

	public int addResourceDir(String name, String parent, String note,
			String status, String isInherit, String keyId)
			throws NoRightException, PlatformException {
		OracleResourceDir dir = new OracleResourceDir();
		dir.setName(name);
		dir.setParent(parent);
		dir.setNote(note);
		dir.setStatus(status);
		dir.setIsInherit(isInherit);
		dir.setKeyId(keyId);

		return dir.add();
	}

	public int setResourceRight(List userList, List resourceDirList) {
		OracleBasicResourceList basicList = new OracleBasicResourceList();
		return basicList.setResourceRight(userList, resourceDirList);
	}

	public List getResourceDirListByUserId(String userId)
			throws NoRightException, PlatformException {
		OracleBasicResourceList basicList = new OracleBasicResourceList();
		return basicList.searchResourceDirListByUserId(userId);
	}

	public ResourceDir getResourceDirByKeyId(String keyId)
			throws NoRightException, PlatformException {
		OracleBasicResourceList basicList = new OracleBasicResourceList();
		return basicList.getResourceDirByKeyId(keyId);
	}

	public boolean hasRight(String userId, String dirId) {
		try {
			ResourceDir curDir = this.getResourceDir(dirId);
			
			while(curDir.getIsInherit().equals("1") && !curDir.getParent().equals("0")) {
				curDir = this.getResourceDir(curDir.getParent());
			}
			
			List resDirList = new ArrayList();
			if (curDir.getIsInherit().equals("0")) {	//目录权限是独立的
				resDirList = this.getResourceDirListByUserId(userId);
				for (int i = 0; i < resDirList.size(); i++) {
					ResourceDir dir = (ResourceDir) resDirList.get(i);
					if (dir!=null && dir.getId()!=null && dir.getId().equals(dirId))
						return true;
				}
			} else {
				
			}
		} catch (NoRightException e) {
			
		} catch (PlatformException e) {
			
		}

		return false;
	}

//	public WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
//			throws PlatformException {
//		ServletContext application = session.getServletContext();
//		ResourceConfig config = (ResourceConfig) application
//				.getAttribute("resourceConfig");
//		PlatformConfig platformConfig = (PlatformConfig) application
//				.getAttribute("platformConfig");
//		WhatyEditorConfig editorConfig = new WhatyEditorConfig();
//		editorConfig.setAppRootURI(platformConfig.getPlatformWebAppUriPath());
//		editorConfig.setEditorRefURI("WhatyEditor/");
//		editorConfig.setEditorURI(platformConfig.getPlatformWebAppUriPath()
//				+ "WhatyEditor/");
//		editorConfig.setUploadAbsPath(config.getResourceWebIncomingAbsPath()
//				+ File.separator);
//		editorConfig.setUploadURI(config.getResourceWebIncomingUriPath());
//		return editorConfig;
//	}

}

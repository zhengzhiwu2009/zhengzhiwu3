package com.whaty.platform.resource;

import java.util.List;

import com.whaty.platform.Exception.NoRightException;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.resource.basic.Resource;
import com.whaty.platform.resource.basic.ResourceDir;
import com.whaty.platform.resource.basic.ResourceType;

public abstract class BasicResourceManage {

	/** Creates a new instance of ResourceManage */
	public BasicResourceManage() {
	}

	/**
	 * 锟斤拷取锟斤拷源锟斤拷锟斤拷锟斤拷目
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract int getResourceTypesNum() throws NoRightException;

	/**
	 * @param id
	 * @return 锟斤拷源锟斤拷锟酵讹拷锟斤拷
	 * @throws NoRightException
	 */
	public abstract ResourceType getResourceType(String id)
			throws NoRightException;

	/**
	 * 锟斤拷取锟斤拷源锟斤拷锟斤拷锟叫憋拷
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract List getResourceTypeList() throws NoRightException;

	/**
	 * @param id
	 * @return 锟斤拷源锟斤拷锟酵讹拷锟斤拷
	 * @throws NoRightException
	 */
	public abstract int addResourceType(String name, String note,
			String status, String xml) throws NoRightException,
			PlatformException;

	/**
	 * 锟斤拷锟斤拷锟皆茨柯�
	 * 
	 * @param name
	 * @param parent
	 * @param note
	 * @param status
	 * @param isInherit
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addResourceDir(String name, String parent, String note,
			String status, String isInherit) throws NoRightException,
			PlatformException;

	/**
	 * @param name
	 * @param parent
	 * @param note
	 * @param status
	 * @param isInherit
	 * @param keyId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addResourceDir(String name, String parent, String note,
			String status, String isInherit, String keyId)
			throws NoRightException, PlatformException;

	/**
	 * 锟斤拷取锟斤拷源锟斤拷
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract int getResourcesNum() throws NoRightException;

	/**
	 * @param id
	 * @return 锟斤拷源锟斤拷锟斤拷
	 * @throws NoRightException
	 */
	public abstract Resource getResource(String id) throws NoRightException;

	/**
	 * 锟斤拷取锟斤拷源锟叫憋拷
	 * 
	 * @return
	 * @throws NoRightException
	 */
	public abstract List getResourceList() throws NoRightException;

	/**
	 * @param title
	 * @param language
	 * @param description
	 * @param keywords
	 * @param creatUser
	 * @param typeId
	 * @param dirId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int addResource(String title, String language,
			String description, String keywords, String creatUser,
			String typeId, String dirId, String xml) throws NoRightException,
			PlatformException;
	
	public abstract int addResource(String title, String language,
			String description, String keywords, String creatUser,
			String typeId, String dirId, String xml, String is_index_show) throws NoRightException,
			PlatformException;

	/**
	 * 删锟斤拷锟斤拷源锟斤拷锟斤拷
	 * 
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteResource(String id) throws NoRightException,
			PlatformException;

	/**
	 * 删锟斤拷锟斤拷源锟斤拷锟酵讹拷锟斤拷
	 * 
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteResourceType(String id) throws NoRightException,
			PlatformException;

	/**
	 * @param id
	 * @param name
	 * @param note
	 * @param status
	 * @param xml
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateResourceType(String id, String name, String note,
			String status, String xml) throws NoRightException,
			PlatformException;

	/**
	 * @param id
	 * @param title
	 * @param language
	 * @param description
	 * @param keywords
	 * @param creatUser
	 * @param typeId
	 * @param dir_id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateResource(String id, String title,
			String language, String description, String keywords,
			String creatUser, String typeId, String dir_id)
			throws NoRightException, PlatformException;

	/**
	 * 锟斤拷锟斤拷锟斤拷源
	 * 
	 * @param id
	 * @param title
	 * @param language
	 * @param description
	 * @param keywords
	 * @param creatUser
	 * @param typeId
	 * @param dir_id
	 * @param xml
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateResource(String id, String title,
			String language, String description, String keywords,
			String creatUser, String typeId, String dir_id, String xml)
			throws NoRightException, PlatformException;

	/**
	 * @param id
	 * @param name
	 * @param parent
	 * @param note
	 * @param status
	 * @param isInherit
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int updateResourceDir(String id, String name,
			String parent, String note, String status, String isInherit)
			throws NoRightException, PlatformException;

	/**
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract int deleteResourceDir(String id) throws NoRightException,
			PlatformException;

	/**
	 * @param id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract ResourceDir getResourceDir(String id)
			throws NoRightException, PlatformException;

	/**
	 * @param id
	 * @param name
	 * @param parent
	 * @param note
	 * @param status
	 * @param isinherit
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getResourceDirs(String id, String name, String parent,
			String note, String status, String isinherit)
			throws NoRightException, PlatformException;

	/**
	 * @param id
	 * @param title
	 * @param language
	 * @param description
	 * @param keywords
	 * @param creatUser
	 * @param type_id
	 * @param dir_id
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getResources(String id, String title, String language,
			String description, String keywords, String creatUser,
			String type_id, String dir_id) throws NoRightException,
			PlatformException;

	/**
	 * @param userList
	 * @param resourceDirList
	 * @return
	 */
	public abstract int setResourceRight(List userList, List resourceDirList);

	/**
	 * @param userId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract List getResourceDirListByUserId(String userId)
			throws NoRightException, PlatformException;

	/**
	 * 锟矫伙拷userId锟角凤拷锟叫查看锟斤拷源目录dirId锟斤拷权f
	 * 
	 * @param userId
	 * @param dirId
	 * @return
	 */
	public abstract boolean hasRight(String userId, String dirId);

	/**
	 * @param session
	 * @return
	 * @throws PlatformException
	 */
//	public abstract WhatyEditorConfig getWhatyEditorConfig(HttpSession session)
//			throws PlatformException;

	/**
	 * 锟斤拷锟絢eyId锟斤拷取锟斤拷应锟斤拷锟斤拷源目录
	 * @param keyId
	 * @return
	 * @throws NoRightException
	 * @throws PlatformException
	 */
	public abstract ResourceDir getResourceDirByKeyId(String keyId)
			throws NoRightException, PlatformException;
}

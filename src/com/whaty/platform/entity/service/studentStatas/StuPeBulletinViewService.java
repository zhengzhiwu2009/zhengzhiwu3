package com.whaty.platform.entity.service.studentStatas;

import java.util.List;

import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.sso.web.servlet.UserSession;

public interface StuPeBulletinViewService {

	/**
	 * 获取登录用户信息
	 * @param userId
	 * @return PeBzzStudent对象
	 * @author cailei
	 */
	public PeBzzStudent getLoginStudent(String userId);
	/**
	 * 获取首页公告
	 * @param userId
	 * @return Page对象
	 * @author cailei
	 */
	public Page firstBulletinInfo(String loginId);
	/**
	 * 获取首页公告
	 * @param loginId 登录ID
	 * @param pageSize 每页条数
	 * @param startIndex 开始条数
	 * @return Page对象
	 * @author cailei
	 */
	public Page firstBulletinInfoByPage(String loginId, int pageSize, int startIndex);
	public List serviceAllPebulletins(String loginId);
	/**
	 * 学生工作室，学生的所有的公告，用于加缓存使用
	 * @param loginId
	 * @return
	 * @author weihuining
	 * @throws EntityException
	 */
	public List initAllPebulletins(String loginId) throws EntityException;
}

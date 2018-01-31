package com.whaty.platform.entity.service.basic;

import com.whaty.platform.entity.bean.AbstractBean;

public interface AssignService<T extends AbstractBean> {

	/**
	 * 分配学员学时
	 * 
	 * @return
	 * @throws Exception
	 */
	public String assignHour() throws Exception;

	/**
	 * 分配二级集体学时
	 * 
	 * @return
	 * @throws Exception
	 */
	public String assignEntHour() throws Exception;

	/**
	 * 剥离学员学时
	 * 
	 * @return
	 * @throws Exception
	 */
	public String stripHour() throws Exception;

	/**
	 * 剥离二级集体学时
	 * 
	 * @return
	 * @throws Exception
	 */
	public String stripEntHour() throws Exception;
}

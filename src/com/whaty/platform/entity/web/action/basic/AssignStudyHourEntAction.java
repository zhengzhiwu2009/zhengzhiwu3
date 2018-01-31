package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.Map;
import com.whaty.platform.entity.service.basic.AssignService;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

/**
 * 给二级机构分配学时
 * 
 * @author linjie
 * 
 * 
 */
public class AssignStudyHourEntAction extends MyBaseAction {
	private AssignService assignService;

	@Override
	public void setEntityClass() {
		// this.entityClass = PeBzzStudent.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/assignStudyHourEnt";
	}

	/**
	 * 统一分配二级集体学时
	 * 
	 * @return
	 */
	public String assignHour() {
		Map map = new HashMap();
		try {
			String result = this.getAssignService().assignEntHour();
			if (null == result || "".equals(result)) {
				result = "操作成功！";
				map.put("success", "true");
				map.put("info", result);
			} else {
				map.put("success", "false");
				map.put("info", result);
			}
		} catch (Exception e) {
			map.put("success", "false");
			map.put("info", "操作失败！");
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	/**
	 * 统一剥离二级集体学时
	 * 
	 * @return
	 */
	public String stripHour() {
		Map map = new HashMap();
		try {
			String result = this.getAssignService().stripEntHour();
			if (null == result || "".equals(result)) {
				result = "操作成功！";
				map.put("success", "true");
				map.put("info", result);
			} else {
				map.put("success", "false");
				map.put("info", result);
			}
		} catch (Exception e) {
			map.put("success", "false");
			map.put("info", "操作失败！");
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub

	}

	public AssignService getAssignService() {
		return assignService;
	}

	public void setAssignService(AssignService assignService) {
		this.assignService = assignService;
	}

}

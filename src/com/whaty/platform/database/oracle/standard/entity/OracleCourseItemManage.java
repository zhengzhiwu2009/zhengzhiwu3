package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.basic.OracleCourseItem;
import com.whaty.platform.entity.CourseItemManage;
import com.whaty.platform.logmanage.LogPriority;
import com.whaty.platform.logmanage.LogType;
import com.whaty.platform.util.log.UserLog;

public class OracleCourseItemManage extends CourseItemManage {

	@Override
	public int updateCourseItemStatus(String item, String status, String id)
			throws PlatformException {
		OracleCourseItem teachItem = new OracleCourseItem();
		teachItem.setId(id);
		if(!(teachItem.isIdExist() > 0)) {
			teachItem.setDayi("1");
			teachItem.setGonggao("1");
			teachItem.setTaolun("1");
			teachItem.setKaoshi("0");
			teachItem.setZuoye("1");
			teachItem.setZfx("1");
			teachItem.setZice("0");
			teachItem.setBoke("1");
			teachItem.setDaohang("1");
			teachItem.setDaoxue("1");
			teachItem.setSmzuoye("0");
			teachItem.setPingjia("1");
			teachItem.setShiyan("0");
			teachItem.setZiyuan("1");
			teachItem.setZxdayi("1");
			teachItem.add();
		}
		int sub = teachItem.updateItemStatus(item, status, id);
		return sub;
	}

	@Override
	public List getTheachItem(String id) {
		List itemList = new ArrayList();
		OracleCourseItem teachItem = new OracleCourseItem();
		teachItem.setId(id);
		if (teachItem.isIdExist() > 0) {
			itemList = teachItem.getItemById(id);
		} else {
			try {
				teachItem.setDayi("1");
				teachItem.setGonggao("1");
				teachItem.setTaolun("1");
				teachItem.setKaoshi("0");
				teachItem.setZuoye("1");
				teachItem.setZfx("1");
				teachItem.setZice("0");
				teachItem.setBoke("1");
				teachItem.setDaohang("1");
				teachItem.setDaoxue("1");
				teachItem.setSmzuoye("0");
				teachItem.setPingjia("1");
				teachItem.setShiyan("0");
				teachItem.setZiyuan("1");
				teachItem.setZxdayi("1");
				
				teachItem.add();

				itemList = teachItem.getItemById(id);

			} catch (Exception e) {
				
			}
		}
		return itemList;
	}

}

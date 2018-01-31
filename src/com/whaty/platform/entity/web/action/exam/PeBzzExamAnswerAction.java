package com.whaty.platform.entity.web.action.exam;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import javax.servlet.http.HttpSession;

import com.whaty.platform.entity.web.action.MyBaseAction;


public class PeBzzExamAnswerAction extends MyBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public void initGrid() {
	}

	public void setEntityClass() {
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/exam/peBzzExamAnswer";
	}
	
	
	public String questionInfo(){
	
		return "questioninfo";
	}
}

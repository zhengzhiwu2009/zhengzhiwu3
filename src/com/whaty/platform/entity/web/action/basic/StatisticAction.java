package com.whaty.platform.entity.web.action.basic;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeEnterpriseManager;
import com.whaty.platform.entity.bean.PePriRole;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.hibernate.GeneralHibernateDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.basic.PeBzzGoodStuService;
import com.whaty.platform.entity.service.basic.PeBzzstudentbacthService;
import com.whaty.platform.entity.service.quartz.QuartzService;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.util.file.FileManage;
import com.whaty.util.file.FileManageFactory;

/**
 * @param
 * @version 创建时间：2009-6-22 下午03:29:27
 * @return
 * @throws PlatformException
 * 类说明
 */
public class StatisticAction extends MyBaseAction<PeBzzStudent> {

	private QuartzService quartzService;
	private String date;
	private String[] types;
	
	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		
	}

	public String run(){
		Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\-\\s]?((((0?" +"[13578])|(1[02]))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))" +"|(((0?[469])|(11))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|" +"(0?2[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12" +"35679])|([13579][01345789]))[\\-\\-\\s]?((((0?[13578])|(1[02]))" +"[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))" +"[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\-\\s]?((0?[" +"1-9])|(1[0-9])|(2[0-8]))))))");
		if(p.matcher(date).matches()){
			if(types!=null && types.length>0){
				HashSet<String> typeSet = new HashSet<String>();
				for(int i=0; i<types.length; i++){
					typeSet.add(types[i]);
				}
				this.getQuartzService().exeAllStat(date,typeSet);
			}else{
				this.setMsg("请选择！");
			}
		}else{
			this.setMsg("日期格式错误！");
		}
		this.setTogo("back");
		return "msg";
	}
	
	
	public QuartzService getQuartzService() {
		return quartzService;
	}

	public void setQuartzService(QuartzService quartzService) {
		this.quartzService = quartzService;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String[] types) {
		this.types = types;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
}

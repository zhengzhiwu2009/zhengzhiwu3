package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeBzzCoursePubCourseAction extends MyBaseAction<PeBzzTchCourse>{

	private String indexFile;	
	private double price; // 课程的价格	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzTchCourse.class;

	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/teaching/peBzzCoursePubCourse";
	}
	/**
	 * 课程详细信息
	 * 
	 * @author Lzh
	 * @return
	 */
	public String courseInfo() {
		String id = ServletActionContext.getRequest().getParameter("id");
		EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
		price = Double.parseDouble(ec.getName());
		this.indexFile = ServletActionContext.getRequest().getParameter("indexFile");
		if ("undefined".equals(this.indexFile.trim()))
			this.indexFile = "未设置";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT A.CODE,A.NAME,A.TEACHER,A.PRICE,A.STUDYDATES,A.END_DATE, ");
			sb.append(" A.ANSWER_BEGINDATE,A.ANSWER_ENDDATE,EC1.NAME FLAGCOURSETYPE,A.TIME,A.PASSROLE,A.EXAMTIMES_ALLOW, ");
			sb.append(" EC2.NAME FLAGISVISITAFTERPASS,EC3.NAME FLAGISEXAM,EC4.NAME FLAGCOURSECATEGORY,EC5.NAME FLAGCOURSEITEMTYPE, ");
			sb.append(" EC6.NAME FLAGCONTENTPROPERTY,LISTAGG(C.NAME, ',') WITHIN GROUP(ORDER BY C.NAME) WC,EC7.NAME FLAGISRECOMMEND, ");
			sb.append(" EC8.NAME FLAGISFREE,A.PHOTO_LINK,A.TEA_IMG,A.PASSROLE_NOTE,A.SUGGESTION ");
			sb.append(" FROM (SELECT * FROM PE_BZZ_TCH_COURSE WHERE ID = '" + id + "') A ");
			sb.append(" INNER JOIN ENUM_CONST EC1 ON A.FLAG_COURSETYPE = EC1.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC2 ON A.FLAG_ISVISITAFTERPASS = EC2.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC3 ON A.FLAG_IS_EXAM = EC3.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC4 ON A.FLAG_COURSECATEGORY = EC4.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC5 ON A.FLAG_COURSE_ITEM_TYPE = EC5.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC6 ON A.FLAG_CONTENT_PROPERTY = EC6.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC7 ON A.FLAG_ISRECOMMEND = EC7.ID ");
			sb.append(" INNER JOIN ENUM_CONST EC8 ON A.FLAG_ISFREE = EC8.ID ");
			sb.append(" LEFT JOIN (SELECT * FROM PE_BZZ_TCH_COURSE_SUGGEST WHERE TABLE_NAME = 'PE_BZZ_TCH_COURSE') B ");
			sb.append(" ON A.ID = B.FK_COURSE_ID ");
			sb.append(" LEFT JOIN ENUM_CONST C ON B.FK_ENUM_CONST_ID = C.ID ");
			sb.append(" GROUP BY A.CODE,A.NAME,A.TEACHER,A.PRICE,A.STUDYDATES,A.END_DATE, ");
			sb.append(" A.ANSWER_BEGINDATE,A.ANSWER_ENDDATE,EC1.NAME,A.TIME,A.PASSROLE, ");
			sb.append(" A.EXAMTIMES_ALLOW,EC2.NAME,EC3.NAME,EC4.NAME,EC5.NAME,EC6.NAME, ");
			sb.append(" EC7.NAME,EC8.NAME,A.PHOTO_LINK,A.TEA_IMG,A.PASSROLE_NOTE,A.SUGGESTION,B.TABLE_NAME ");
			List detailList = this.getGeneralService().getBySQL(sb.toString());
			ServletActionContext.getRequest().setAttribute("courseDetail", detailList);
			this.indexFile = ServletActionContext.getRequest().getParameter("indexFile");
			// this.setBean(this.getGeneralService().getById(id));
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "courseInfo";
	}

	public String getIndexFile() {
		return indexFile;
	}

	public void setIndexFile(String indexFile) {
		this.indexFile = indexFile;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}  
}

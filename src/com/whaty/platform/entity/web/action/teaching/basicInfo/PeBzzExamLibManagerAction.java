package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

/**
 * @param
 * @version 创建时间：2009-6-21 上午10:40:06
 * @return
 * @throws PlatformException
 * 类说明
 */
public class PeBzzExamLibManagerAction extends MyBaseAction<PeBzzTchCourse> {
	
	private List peBzzTchCourses;
	private String tempFlag;
	
	public String getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	/**
	 * 初始化列表
	 * @author linjie
	 * @return
	 */
	@Override
	public void initGrid() {
		UserSession us = (UserSession)ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			this.getGridConfig().setTitle("题库管理");
			this.getGridConfig().setCapability(false, false, false);
			
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("课程编号"), "code",true,true,true,Const.coursecode_for_extjs);
			this.getGridConfig().addColumn(this.getText("课程名称"), "name");
			this.getGridConfig().addColumn(this.getText("课程所属区域"), "quyu",false);
			//this.getGridConfig().addColumn(this.getText("随堂练习题"), "num1",false,false,true,"");
			this.getGridConfig().addColumn(this.getText("课后测验题"), "num2",false,false,true,"");
			this.getGridConfig().addRenderFunction(this.getText("题库管理"), "<a href=\"/sso/bzzinteraction_InteractiontkManage.action?course_id=${value}&teacher_id=teacher1\"  target='_blank'>题库管理</a>","id");
		}
	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass=PeBzzTchCourse.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath="/entity/teaching/peBzzExamLibManager";
	}
	
	public void setBean(PeBzzTchCourse instance) {
		super.superSetBean(instance);
		
	}
	
	public PeBzzTchCourse getBean(){
		return super.superGetBean();
	}
	
	/**
	 * 重写框架方法：题库信息
	 * @author linjie
	 * @return
	 */
	public String abstractDetail() {
		Page page = null;
		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria
				.forClass(PeBzzTchCourse.class);
		dc.createCriteria("enumConstByFlagIsvalid","enumConstByFlagIsvalid");
		dc.createCriteria("enumConstByFlagCourseType","enumConstByFlagCourseType");
		dc.createCriteria("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory",DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("id", this.getBean().getId()));
		try {
			page = this.getGeneralService().getByPage(dc,
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
			String id = this.getBean().getId();

			if (page != null) {
				page.setItems(JsonUtil.ArrayToJsonObjects(page.getItems(), this
						.getGridConfig().getListColumnConfig()));
				Container o = new Container();
				o.setBean(page.getItems().get(0));
				List list = new ArrayList();
				list.add(o);
				map.put("totalCount", 1);
				map.put("models", list);
			}
			this.setJsonString(JsonUtil.toJSONString(map));
			JsonUtil.setDateformat("yyyy-MM-dd");
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return json();
	}
	
	/**
	 * 重写框架方法：题库信息（带sql条件）
	 * @author linjie
	 * @return
	 */
	public Page list() {
		DetachedCriteria dc = initDetachedCriteria();
		dc = setDetachedCriteria(dc, this.getBean());
		Page page = null;
		String sql="";
		UserSession us = (UserSession) ServletActionContext.getRequest()
				.getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		/*sql =
			"select *\n" +
			"  from (select a.id,\n" + 
			"               a.code,\n" + 
			"               a.name,\n" + 
			"               --b.num            as num1,\n" + 
			"               nvl(c.num, 0)            as num2,\n" + 
			"               a.categoryName,\n" + 
			"               a.courseTypeName,\n" + 
			"               a.time\n" + 
			"          from (select ptc.id,\n" + 
			"                       ptc.code,\n" + 
			"                       ptc.name,\n" + 
			"                       ptc.time,\n" + 
			"                       ec1.name as categoryName,\n" + 
			"                       ec2.name as courseTypeName\n" + 
			"                  from pe_bzz_tch_course ptc, enum_const ec1, enum_const ec2\n" + 
			"                 where ptc.flag_coursecategory = ec1.id\n" + 
			"                   and ptc.flag_coursetype = ec2.id) a\n" + 
			"          left join (select thi.group_id as course_id,\n" + 
			"                           count(distinct tpi.id) as num\n" + 
			"                      from test_paperquestion_info tpi,\n" + 
			"                           test_homeworkpaper_info thi\n" + 
			"                     where tpi.testpaper_id = thi.id\n" + 
			"                     group by thi.group_id) b\n" + 
			"            on a.id = b.course_id\n   " + 
			"          left join (select tti.group_id as course_id,\n" + 
			"                           count(distinct tpi.id) as num\n" + 
			"                      from test_paperquestion_info tpi,\n" + 
			"                           test_testpaper_info     tti\n" + 
			"                     where tpi.testpaper_id = tti.id\n" + 
			"                     group by tti.group_id) c\n" + 
			"            on c.course_id = a.id)\n" + 
			" where 1 = 1";
*/
		sql = 
			" select * from (select pbtc.id, pbtc.code, pbtc.name, e.name quyu,count(b.tsid) as num2\n" +
			"  from pe_bzz_tch_course pbtc\n" + 
			"  left join (select tld.group_id, tsi.id as tsid\n" + 
			"               from test_lore_dir           tld,\n" + 
			"                    test_lore_info          tli,\n" + 
			"                    test_storequestion_info tsi\n" + 
			"              where tsi.lore = tli.id\n" + 
			"                and tli.loredir = tld.id\n" + 
			"\n" + 
			"             ) b on b.group_id = pbtc.id\n" + 
			"  join enum_const e on pbtc.flag_coursearea=e.id   "+
			" group by pbtc.id, pbtc.code, pbtc.name,e.name) where 1=1";


		try {
			StringBuffer sql_temp = new StringBuffer(sql);
			/*Map params = this.getParamsMap();
			Iterator iterator = params.entrySet().iterator();
			do
	        {
	            if(!iterator.hasNext())
	                break;
	            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
	            String name = entry.getKey().toString();
	            String value = ((String[])entry.getValue())[0].toString();
	            if(!name.startsWith("search__"))
	            	continue;
	            if("".equals(value))
	            	continue;
	            if(name.indexOf(".") > 1 && name.indexOf(".") != name.lastIndexOf(".")){
	            	name=name.substring(name.lastIndexOf(".", name.lastIndexOf(".")-1)+1);
				}else{
					name=name.substring(8);
				}
	            
	            if (name.equals("enumConstByFlagIsvalid.name")) {
					name = "validName";
				}
				if (name.equals("enumConstByFlagCourseCategory.name")) {
					name = "categoryName";
				}
				if (name.equals("enumConstByFlagCourseType.name")) {
					name = "courseTypeName";
				}
				sql_temp.append(" and " + name + " like '%" + value + "%'");
				
	        }while(true);
			String temp = this.getSort();
			if(temp != null && temp.indexOf(".") > 1){
				if(temp.toLowerCase().startsWith("combobox_")){
					temp = temp.substring(temp.indexOf(".") + 1);
				}
			}
			if (this.getSort() != null && temp != null) {
				if (temp.equals("enumConstByFlagIsvalid.name")) {
					temp = "validName";
				}
				if (temp.equals("enumConstByFlagCourseCategory.name")) {
					temp = "categoryName";
				}
				if (temp.equals("enumConstByFlagCourseType.name")) {
					temp = "courseTypeName";
				}
				if (this.getDir() != null && this.getDir().equalsIgnoreCase("desc"))
					{sql_temp.append(" order by " +temp+ " desc ");
					}
				else{
					sql_temp.append(" order by " +temp+ " asc ");
				}
			} else {
				sql_temp.append(" order by id desc");
			}**/
			//sql_temp.append(" order by id desc");
			this.setSqlCondition(sql_temp);
			page = this.getGeneralService().getByPageSQL(sql_temp.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 重写框架方法：初始化课程信息
	 * @author linjie
	 * @return
	 */
	@Override
	public DetachedCriteria initDetachedCriteria() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourse.class);
		dc.createCriteria("enumConstByFlagIsvalid","enumConstByFlagIsvalid");
		dc.createCriteria("enumConstByFlagCourseType","enumConstByFlagCourseType");
		dc.createCriteria("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory",DetachedCriteria.LEFT_JOIN);
		//dc.addOrder(Order.asc("suqNum"));
		return dc;
	}
	public List getPeBzzTchCourses() {
		return peBzzTchCourses;
	}

	public void setPeBzzTchCourses(List peBzzTchCourses) {
		this.peBzzTchCourses = peBzzTchCourses;
	}
}

package com.whaty.platform.entity.web.action.statistics;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;
import com.whaty.platform.util.Struts2Helper;

/**
 * @param
 * @version 创建时间：2009-7-2 下午08:40:28
 * @return
 * @throws PlatformException
 * 类说明
 */
/**
 * @author gy
 *
 */
public class PrBzzStatisticsAction extends MyBaseAction {
	private String year;

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
		this.servletPath = "/entity/statistics/prBzzStatistics";
	}
	
	/**
	 * 转向学生统计页面
	 * @return
	 */
	public String turntoStu() {
		return "stu";
	}
	/**
	 * 转向考试统计页面
	 * @return
	 */
	public String turntoExam() {
		return "exam";
	}
	
	/**
	 * 转向资源统计页面
	 * @return
	 */
	public String turntoRes(){
		return "res";
	}
	
	/**
	 * 服务器人数统计页面
	 * @return
	 */
	public String viewServerCount() {
		return "serverCount";
	}
	
	/**
	 *每天最大在线人数、 每天学习总时长等 页面
	 * @return
	 */
	public String onlineDateCount(){
		return  "onlineDateCount";
	}
	
	
	/**
	 * 转向选课统计页面
	 * @return
	 */
	public String turntoEle(){
		return "ele";
	}
	
	/**
	 * 转向学习进度统计页面
	 * @return
	 */
	public String turntoPro(){
		return "pro";
	}
	
	/**
	 * 转向学习相关统计页面
	 * @return
	 */
	public String studyStatus(){
		return "studyStatus";
	}
	
	/**
	 * 转向报名信息统计页面
	 * @return
	 */
	public String recruitStatus(){
		return "recruitStatus";
	}
	
	/**
	 * 转向账号激活状况统计页面
	 * @return
	 */
	public String accountActiveStatus() {
		return "accountActiveStatus";
	}
	
	/**
	 * 转向直接统计页面
	 * @return
	 */
	public String zhiVoteStatus() {
		return "zhiVoteStatus";
	}
	
	
	/**
	 * 注册统计列表
	 * 
	 */
	
	public String registStat(){
		List list = new ArrayList();
		List dataIndexList = new ArrayList();
		//dataIndexList.add("id");
		dataIndexList.add("year");
		dataIndexList.add("dept_grown");
		dataIndexList.add("dept_sum");
		dataIndexList.add("dept_num_growth");
		dataIndexList.add("user_grown");
		dataIndexList.add("user_sum");
		dataIndexList.add("user_num_growth");
		String sql = 
			"select t.year as year,\n" +
			"       t.reg_enterprise_num,\n" + 
			"       t.reg_enterprise_count,\n" + 
			"       t.reg_enterprise_growth,\n" + 
			"       t.reg_user_num,\n" + 
			"       t.reg_user_count,\n" + 
			"       t.reg_user_growth\n" + 
			"  from statistic_year t";

		try {
			list = this.getGeneralService().getBySQL(sql);
			list = ArrayToJsonObjects(list,dataIndexList);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(list));
		return "json";
	}
	
	/**
	 * 综合统计，注册统计-按月度
	 * @param items
	 * @param list
	 * @return
	 */
	public String regStatByMonth(){
		List list = new ArrayList();
		List dataIndexList = new ArrayList();
		//dataIndexList.add("id");
		dataIndexList.add("year");
		dataIndexList.add("month");
		dataIndexList.add("dept_grown");
		dataIndexList.add("dept_sum");
		dataIndexList.add("user_grown");
		dataIndexList.add("user_sum");
		String sql = 
			"select t.year,\n" +
			"       t.month,\n" + 
			"       sum(t.reg_enterprise_num),\n" + 
			"       max(t.reg_enterprise_count),\n" + 
			"       sum(t.reg_user_num),\n" + 
			"       max(t.reg_user_count)\n" + 
			"  from statisticsum t\n" + 
			" where t.year = '"+getExtExtraParamValue("year")+"'\n" + 
			" group by t.year, t.month order by t.month";

		try {
			list = this.getGeneralService().getBySQL(sql);
			if(list!=null && list.size()>0){
				String[] obj = new String [6];
				obj[0]="总计";
				obj[1]=" ";
				obj[2]="9";
				obj[3]="--";
				obj[4]="53575";
				obj[5]="--";
				list.add(obj);
			}
			
			list = ArrayToJsonObjects(list,dataIndexList);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(list));
		return "json";
	}
	
	/**
	 * 综合统计，注册统计-注册类别
	 * @param items
	 * @param list
	 * @return
	 */
	public String regStatByRegtype(){
		List list = new ArrayList();
		List dataIndexList = new ArrayList();
		//dataIndexList.add("id");
		dataIndexList.add("year");
		dataIndexList.add("reg_type");
		dataIndexList.add("dept_grown");
		dataIndexList.add("dept_sum");
		dataIndexList.add("user_grown");
		dataIndexList.add("user_sum");
		String sql = 
			"select t.year,\n" +
			"       t.fk_regtype_id,\n" + 
			"       sum(t.reg_enterprise_num),\n" + 
			"       max(t.reg_enterprise_count),\n" + 
			"       sum(t.reg_user_num),\n" + 
			"       max(t.reg_user_count)\n" + 
			"  from statisticsum t\n" + 
			" where t.year = '"+getExtExtraParamValue("year")+"'\n" + 
			" group by t.year, t.fk_regtype_id";

		try {
			list = this.getGeneralService().getBySQL(sql);
			if(list!=null && list.size()>0){
				String[] obj = new String [6];
				obj[0]="总计";
				obj[1]=" ";
				obj[2]="--";
				obj[3]="--";
				obj[4]="53575";
				obj[5]="296949";
				list.add(obj);
			}
			list = ArrayToJsonObjects(list,dataIndexList);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(list));
		SimpleDateFormat sdf = new SimpleDateFormat("");
		sdf.format(new Date());
		return "json";
	}
	
	/**
	 * 综合统计，注册统计-机构类别
	 * @param items
	 * @param list
	 * @return
	 */
	public String regStatByDepttype(){
		List list = new ArrayList();
		List dataIndexList = new ArrayList();
		//dataIndexList.add("id");
		dataIndexList.add("year");
		dataIndexList.add("dept_type");
		dataIndexList.add("dept_grown");
		dataIndexList.add("dept_sum");
		dataIndexList.add("user_grown");
		dataIndexList.add("user_sum");
		String sql = 

			"select et.year,\n" +
			"       nvl(ec.name, '个人用户'),\n" + 
			"       sum(et.reg_enterprise_num),\n" + 
			"       max(et.reg_enterprise_count),\n" + 
			"       sum(et.reg_user_num),\n" + 
			"       max(et.reg_user_count),\n" + 
			"       ec.id as ecid\n" + 
			"  from statistic_enterprise_type et, enum_const ec\n" + 
			" where et.fk_enterprisetype_id = ec.id(+)\n" + 
			"   and et.year = '"+getExtExtraParamValue("year")+"'\n" + 
			" group by ec.name,ec.id,ec.code,et.year order by ec.code";


		try {
			list = this.getGeneralService().getBySQL(sql);
			if(list!=null && list.size()>0){
				String[] obj = new String [6];
				obj[0]="合计";
				obj[1]=" ";
				obj[2]="9";
				obj[3]="284";
				obj[4]="53575";
				obj[5]="296949";
				list.add(obj);
			}
			list = ArrayToJsonObjects(list,dataIndexList);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(list));
		
		return "json";
	}
	
	/**
	 * 获取ext的  extraParam 参数值
	 * @param key
	 * @return
	 */
	public static String getExtExtraParamValue(String key){
		
		return com.whaty.platform.util.Struts2Helper.getExtExtraParamValue(key);
	}
	

	
	public static List ArrayToJsonObjects(List items, List list) {
		try{
		List jsonObjectItems=new ArrayList();
		String[] columnString = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			columnString[i] = list.get(i).toString();
		}
		for (Object object : items) { 
			if(object.getClass().isArray()){
				JSONObject jsonObject = new JSONObject();
				Object[] valueObject = (Object[])object;
				for (int i = 0; i < columnString.length; i++) {
					String valueString = "";
					if(valueObject[i]!=null){
						valueString = valueObject[i].toString();
					}
					if(valueString==null || "0".endsWith(valueString)){
						valueString = "-";
					}
					if(valueString.indexOf("%")>0){
						valueString = "<font color=red>"+ valueString +"</font>";
					}
					jsonObject = getJSONObject(jsonObject, columnString[i], valueString);
				}
				jsonObjectItems.add(jsonObject);
			}else{
				break;
			}
		}
		if(jsonObjectItems.size() > 0){
			return jsonObjectItems;
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return items;
	}
	
	private static JSONObject getJSONObject(JSONObject jsonObject, String columnString,
			String valueString) {
		if(columnString.indexOf(".") < 0){
			jsonObject.put(columnString, valueString);
		}else{
			JSONObject subJsonObject = new JSONObject();
			String key = columnString.substring(0, columnString.indexOf("."));
			if(!jsonObject.containsKey(key)){
				jsonObject.put(key, subJsonObject);
			}
			jsonObject.put(key,  getJSONObject(jsonObject.getJSONObject(key), columnString.substring(columnString.indexOf(".")+1), valueString));

		}
		return jsonObject;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
}

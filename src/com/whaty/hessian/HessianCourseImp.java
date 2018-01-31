package com.whaty.hessian;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;

public class HessianCourseImp implements HessianCourse {

	/**
	 * 读取推荐课程
	 * @param pageInt
	 * @param pageSize
	 * @return Map
	 * @author linjie
	 */
	@Override
	public Map recommendCourse(int pageInt, int pageSize) {
		return coursesByType("","",pageInt,pageSize,true,false,"","","");
	}

	/**
	 * 通过课程类型，是否推荐课程来获取课程列表，如果type为“all”默认读所有课程，为null读未分类课程
	 * @param type//大纲分类
	 * @param type1//业务分类
	 * @param pageInt
	 * @param pageSize
	 * @param isRecommended
	 * @param isFree
	 * @param courseName
	 * @param courseTime  
	 * @param courseCategory 
	 * @return Map
	 * @author linjie
	 */
	@Override
	public Map coursesByType(String type,String type1,int pageInt, int pageSize,
			boolean isRecommended,boolean isFree,String courseName,String courseTime,String courseCategory) {
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List courses = new ArrayList();
		Map re = new HashMap();
		int count = 0;
		String sql=
				"select pbtc.id as id,\n" +
						"       pbtc.code as code,\n" + 
						"       pbtc.name as name,\n" + 
						"       pbtc.time as time,\n" + 
						"       pbtc.photo_link as photo_link,\n" + 
						"       ec2.name as course_category,\n" + 
						"       ec.name as course_type,\n" + 
						"       pbtc.announce_date as announce_date,\n" +
						"		pbtc.teacher as teacher,\n" +
						"       ec3.name as course_isfree,\n" + 
						"       '' as edit_date\n" + 
						"  from pe_bzz_tch_course pbtc, enum_const ec,enum_const ec1,enum_const ec2,enum_const ec3\n" + 
						" where pbtc.flag_course_item_type = ec.id" +//业务分类改为大纲分类
						"	and pbtc.flag_coursecategory = ec1.id\n" +
						"	and pbtc.flag_coursetype = ec2.id\n" +
						"   and pbtc.flag_isfree=ec3.id\n" +
						"	and (pbtc.flag_isvalid='2' \n" +
						"		/*or (pbtc.flag_isvalid='3' and pbtc.flag_canjoinbatch='40288acf3aaa56d5013aaa5b8ccc0001')*/" +
						"	) ";//有效的课程
		if(type!=null && "all".equals(type)){
			//sql +=" and pbtc.code not like 'B%'";
			//sql +=" and pbtc.code not like 'M%'";
		}else if(type!=null && !"".equals(type)){
			sql += "	and ec.id ='"+type+"'";//大纲分类
		}
		if(type1!=null && !"".equals(type1)){
			sql += "	and  ec1.id ='"+type1+"'";//业务分类
		}
		if(courseName!=null && !"".equals(courseName) && !"".equals(courseName.trim())){
			sql += "	and  (pbtc.name like '%"+courseName+"%' or pbtc.code like '%"+courseName+"%')";//课程名称或code搜索
		}
		if(courseTime!=null && !"".equals(courseTime)){
			sql += "	and  pbtc.time ='"+courseTime+"'";//课程学时
		}
		if(courseCategory!=null && !"".equals(courseCategory)){
			sql += "	and  pbtc.flag_coursetype ='"+courseCategory+"'";//性质分类
		}
		
		if(isRecommended){
			sql += "	and  pbtc.flag_isrecommend ='40288a7b399a357801399a4ff9ef0006'";//推荐课程
		}
		if(isFree){
			sql += "	and  ec2.code ='1'";//免费课程
		}
		sql+= " order by pbtc.announce_date desc";
		try {
			rs = db.execute_oracle_page(sql, pageInt, pageSize);
			count = db.countselect(sql);
			while(rs!=null && rs.next()){
				Map course = new HashMap();
				course.put("id",rs.getString(1));
				course.put("code",rs.getString(2));
				course.put("name",rs.getString(3));
				course.put("time",rs.getString(4));
				course.put("photo_link",rs.getString(5));
				course.put("course_category",rs.getString(6));
				course.put("course_type",rs.getString(7));
				course.put("announce_date",rs.getDate(8));
				course.put("course_isfree",rs.getString(10));
				course.put("edit_date",rs.getDate(11));
				course.put("teacher",rs.getString(9));
				courses.add(course);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.close(rs);
			db=null;
		}
		re.put("list", courses);
		re.put("count", count);
		return re;
	}

	/**
	 * 读取课程大纲分类 
	 * @param count
	 * @return List
	 * @author linjie
	 */
	@Override
	public List courseTypes(int count) {
		String sql = 
				"select distinct ec.id as id, ec.code as code, ec.name as name\n" +
						"  from enum_const ec\n" + 
						" where ec.namespace = 'FlagCourseItemType' order by ec.code\n" ;//业务分类改为大纲分类 FlagCourseCategory
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List types = new ArrayList();
		try {
			rs = db.execute_oracle_page(sql, 1, count);
			while(rs!=null && rs.next()){
				Map type = new HashMap();
				type.put("id",rs.getString(1));
				type.put("code",rs.getString(2));
				type.put("name",rs.getString(3));
				types.add(type);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.close(rs);
			db=null;
		}
		return types;
	}

	/**
	 * 根据课程id获得信息
	 * @param courseId
	 * @return Map
	 * @author linjie
	 */
	@Override
	public Map courseKCJJ(String courseId) {
		String sql = 
			"select t.id        	 as id,\n" +
			"       t.content    	 as content,\n" + 
			"       t.title     	 as title,\n" + 
			"       p.name       	 as courseName,\n" + 
			"       p.suggestion 	 as suggestion,\n" + 
			"       p.passrole_note  as passroleNote,\n" + 
			"       p.code      	 as code,\n" + 
			"       p.time      	 as time,\n" + 
			"       (select round(nvl(p.time,0)  * nvl(name,0),2) from enum_const ec where ec.namespace = 'ClassHourRate' and code = '0') as price,\n" + 
			"       p.end_date       as endDate,\n" +
			"       p.studydates     as studydates,\n" +
			"       p.photo_link as photoLink,\n" +
			"		ec.name 		 as course_isfree" + 
			"  from (select * from interaction_teachclass_info  where type = 'KCJJ') t, pe_bzz_tch_course p,enum_const ec\n" + 
			" where t.teachclass_id(+) = p.id\n" +
			"	and p.flag_isfree=ec.id" + 
			"   and p.id = '"+courseId+"'" ;
		//out.println(sql);
		dbpool db = new dbpool();
		MyResultSet rs = null;
		Map courseInfo = new HashMap();
		try {
			rs = db.executeQuery(sql);
			if(rs!=null && rs.next()){
				String content = rs.getString("content");
				courseInfo.put("id", fixnull(rs.getString("id")));
				courseInfo.put("title", fixnull(rs.getString("title")));
				courseInfo.put("content", fixnull(content));
				courseInfo.put("courseName", fixnull(rs.getString("courseName")));
				courseInfo.put("photoLink", fixnull(rs.getString("photoLink")));
				courseInfo.put("suggestion", fixnull(rs.getString("suggestion")));
				courseInfo.put("passroleNote", fixnull(rs.getString("passroleNote")));
				courseInfo.put("code", fixnull(rs.getString("code")));
				courseInfo.put("time", fixnull(rs.getString("time")));
				courseInfo.put("price", rs.getDouble("price"));
				courseInfo.put("endDate", fixnull(rs.getString("endDate")));
				courseInfo.put("studydates", fixnull(rs.getString("studydates")));
				courseInfo.put("course_isfree", fixnull(rs.getString("course_isfree")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.close(rs);
			db=null;
		}
		return courseInfo;
	}
	
	/**
	 * 查询分类
	 * ‘FlagCourseItemType’ 大纲分类
	 * ‘FlagCourseCategory’业务分类
	 * ‘FlagCourseType’性质分类
	 * @param nameSpace
	 * @param code
	 * @return List
	 * @author linjie
	 */
	@Override
	public List getEc(String nameSpace,String code){
		String sql = 
			"select distinct ec.id as id, ec.code as code, ec.name as name\n" +
					"  from enum_const ec\n" + 
					" where ec.namespace = '"+nameSpace+"'  order by ec.code\n" ;
		dbpool db = new dbpool();
		MyResultSet rs = null;
		List ecs = new ArrayList();
		try {
			rs = db.executeQuery(sql);
			while(rs!=null && rs.next()){
				Map ec = new HashMap();
				ec.put("id",rs.getString(1));
				ec.put("code",rs.getString(2));
				ec.put("name",rs.getString(3));
				ecs.add(ec);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			db.close(rs);
			db=null;
		}
		return ecs;
	}

	/**
	 * 字符串若不存在则附为空
	 * @param str
	 * @return String
	 * @author linjie
	 */
	String fixnull(String str){ 
   		if(str==null || str.equals("null") || str.equals("")){
   			str= "";
   			return str;
   		}
   		return str;
   }
}

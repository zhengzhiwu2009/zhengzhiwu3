/*
 * OracleCourseDataSelect.java
 *
 * Created on 2005��5��7��, ����4:37
 */

package com.whaty.platform.database.oracle.standard.entity.basic;
import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleTeachClass;
import com.whaty.platform.entity.basic.CourseDataSelect;
import com.whaty.platform.util.Page;
import com.whaty.util.log.Log;
/**
 *
 * @author Administrator
 */
public class OracleCourseDataSelect implements CourseDataSelect{
    
    private String SQLCOURSE = "select id,course_name,credit,course_time,course_status,standard_fee,text_book,ref_book,note from lrn_course_info c,lrn_course_relation r where c.id = r.course_id";

    private String SQLCOURSEWARE = "select id,title,author,publisher,note,link,founder,type from lrn_PE_TCH_COURSEWARE";

    private String condition = "";

    private String SQLCOURSE_ORDERBY = " order by id desc";

    private String SQLCWARE_ORDERBY = " order by reg_date desc";
    
    private String SQLCOURSETYPE = "";

    private String SQLCTYPE_ORDERBY = "";
    
    private String SQLTEACHCLASS = "select id,name,open_course_id from lrn_teach_class";
    
    private String SQLTEACHCLASS_ORDERBY = " order by id asc";
    /** Creates a new instance of OracleCourseDataSelect */
    public OracleCourseDataSelect() {
    }

    public List getCourseItemsByPage(Page page) {
        ArrayList courseItem = new ArrayList();
        return courseItem;
    }

    public int getCourseItemsNum() {
        return 0;
    }

    public List getCourseTypesByPage(Page page) {
        ArrayList courseType = new ArrayList();
        return courseType;
    }

    public int getCourseTypesNum() {
        return 0;
    }

    public List getCoursesByPage(Page page) {
        dbpool db = new dbpool();
        String sql = "";
        sql = SQLCOURSE+condition+SQLCOURSE_ORDERBY;
        MyResultSet rs = null;
        ArrayList courselist = new ArrayList();
        try
        {
            rs=db.execute_oracle_page(sql,page.getPageInt(),page.getPageSize());
            while(rs!=null && rs.next())
            {
                OracleCourse course = new OracleCourse();
                course.setId(rs.getString("id"));
                course.setName(rs.getString("course_name"));
                course.setCredit(rs.getFloat("credit"));
                course.setCourse_time(rs.getFloat("course_time"));
                course.setCourse_status(rs.getString("course_status"));
                course.setStandard_fee(rs.getString("standard_fee"));
                course.setText_book(rs.getString("text_book"));
                course.setRef_book(rs.getString("ref_book"));
                course.setNote(rs.getString("note"));
                courselist.add(course);
            }
        }
        catch(java.sql.SQLException e)
        {
            Log.setError("course.getCourseByPage(Page page) error" + sql);
        }
        finally
        {
            db.close(rs);
            db = null;
        }
        return courselist;
    }

    public int getCoursesNum() {
        dbpool db = new dbpool();
        String sql = "";
        sql = SQLCOURSE+condition+SQLCOURSE_ORDERBY;
        int i = db.countselect(sql);
        return i;
    }

    public List searchCoursesByPage(Page page, String search_type ,String search_value,String major_id) {
        boolean isHas = false;
        if(search_value !=null && !search_value.equals(""))
        {
            condition = " where '"+search_type+"' like '%"+search_value+"%' ";
            isHas = true;
        }
        if(major_id != null && !major_id.equals(""))
        {
            if(isHas)
            {
                condition = condition + " and major_id = '"+major_id+"'";
            }
            else
            {
                condition = " where major_id = '"+major_id+"'";
            }
        }
        return getCoursesByPage(page);
    }

    public int searchCoursesNum( String search_type ,String search_value,String major_id) {
        boolean isHas = false;
        if(search_value !=null && !search_value.equals(""))
        {
            condition = " where '"+search_type+"' like '%"+search_value+"%' ";
            isHas = true;
        }
        if(major_id != null && !major_id.equals(""))
        {
            if(isHas)
            {
                condition = condition + " and major_id = '"+major_id+"'";
            }
            else
            {
                condition = " where major_id = '"+major_id+"'";
            }
        }
        return getCoursesNum();
    }

    public List getTeachClassesByPage(Page page) {
        dbpool db = new dbpool();
        MyResultSet rs = null;
        String sql = "";
        ArrayList teachclasslist = new ArrayList();
        sql = SQLTEACHCLASS + condition + SQLTEACHCLASS_ORDERBY;
        try
        {
            rs = db.execute_oracle_page(sql, page.getPageInt(),page.getPageSize());
            while(rs!=null && rs.next())
            {
                OracleTeachClass teachclass = new OracleTeachClass();
                teachclass.setId(rs.getString("id"));
                teachclass.setName(rs.getString("name"));
                OracleOpenCourse openCourse = new OracleOpenCourse();
				openCourse.setId(rs.getString("open_course_id"));
				teachclass.setOpenCourse(openCourse);
                teachclasslist.add(teachclass);
            }
        }
        catch(java.sql.SQLException e)
        {
            Log.setError("CourseDataSelect.getTeachClassesByPage() error "+ sql);
        }
        finally
        {
            db.close(rs);
            db = null;
        }
        return teachclasslist;
    }

    public int getTeachClassesNum() {
        dbpool db = new dbpool();
        String sql = "";
        sql = SQLTEACHCLASS + condition+ SQLTEACHCLASS_ORDERBY;
        int i = db.countselect(sql);
        return i;
    }
   
    public  List getTeachClassesBySemester(Page page, String semester_id){
        SQLTEACHCLASS = "select id,name,open_course_id from lrn_teach_class t,lrn_course_active a"
                +" where t.open_course_id = a.id and a.semester_id = '"+semester_id+"'";
        return getTeachClassesByPage(page);
    }
    
    
    public  int getTeachClassesBySemesterNum(String semester_id){
        SQLTEACHCLASS = "select id,name,open_course_id from lrn_teach_class t,lrn_course_active a"
                +" where t.open_course_id = a.id and a.semester_id = '"+semester_id+"'";
        return getTeachClassesNum();
    }

    public List getTeachClassesByCourse(Page page,String open_course_id) {
        condition = " where open_course_id = '"+open_course_id+"'";
        return getTeachClassesByPage(page);
    }

    public int getTeachClassesByCourseNum(String open_course_id) {
        condition = " where open_course_id = '"+open_course_id+"'";
        return getTeachClassesNum();
    }

    public List getCoursesByMajorEdu(Page page, String major_id, String edutype_id) {
        if(major_id !=null && !major_id.equals("") && !major_id.equals("null"))
        {
            condition = condition + " and r.major_id = '"+major_id+"'";
        }
        if(edutype_id !=null && !edutype_id.equals("") && !edutype_id.equals("null"))
        {
                condition = condition + " and r.edu_type_id = '"+edutype_id+"'";
        }
        return getCoursesByPage(page);
    }

    public int getCoursesByMajorEduNum(String major_id, String edutype_id) {
        if(major_id !=null && !major_id.equals("") && !major_id.equals("null"))
        {
            condition = " and r.major_id = '"+major_id+"'";
        }
        if(edutype_id !=null && !edutype_id.equals("") && !edutype_id.equals("null"))
        {
                condition = condition + " and r.edu_type_id = '"+edutype_id+"'";
        }
        return getCoursesNum();
    }
}

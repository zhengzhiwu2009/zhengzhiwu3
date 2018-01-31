/*
 * OracleCourseType.java
 *
 * Created on 2005年5月9日, 下午12:28
 */

package com.whaty.platform.database.oracle.standard.entity.basic;
import java.util.Date;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.CourseType;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserDeleteLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

/**
 *
 * @author Administrator
 */
public class OracleCourseType extends CourseType{
    
    /** Creates a new instance of OracleCourseType */
    public OracleCourseType() {
    }
    
    public OracleCourseType(String aid) {
    	dbpool dbCourse=new dbpool();
        String sql="";
        MyResultSet rs=null;
        try
        {
           sql = "select name from entity_course_type where id='"+aid+"'";
           rs = dbCourse.executeQuery(sql);
           if(rs!=null && rs.next())
           {
               this.setId(aid);
               this.setName(rs.getString("name"));
           }
        }
        catch (java.sql.SQLException e)
        {
           Log.setError("");
        }
        finally
        {
           dbCourse.close(rs);
           dbCourse = null;
        }
    }

    public int add() throws PlatformException {
    	if(isIdExist()!=0)
    		throw new PlatformException("coursetype id has exist");
    	dbpool dbcourse = new dbpool();
        String sql = "";
        sql="insert into entity_course_type (id,name) values ('"+this.getId()+"','"+this.getName()+"')";
        int i = dbcourse.executeUpdate(sql);
        UserAddLog.setDebug("OracleCourseType.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
        return i;
    }

    public int delete() throws PlatformException {
    	if(hasCourse()!=0)
    		throw new PlatformException("this coursetype has course");
    	dbpool dbcourse = new dbpool();
        String sql = "";
        sql="delete entity_course_type where id='"+this.getId()+"'";
        int i = dbcourse.executeUpdate(sql);
        UserDeleteLog.setDebug("OracleCourseType.delete() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
        return i;
    }

    public int update() {
    	dbpool dbcourse = new dbpool();
        String sql = "";
        sql="update entity_course_type set name='"+this.getName()+"' where id='"+this.getId()+"'";
        int i = dbcourse.executeUpdate(sql);
        UserUpdateLog.setDebug("OracleCourseType.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
        return i;
    }

    public int isIdExist() {
        dbpool dbcourse = new dbpool();
        String sql = "";
        sql = "select id from entity_course_type where id='"+this.getId()+"'";
        int i = dbcourse.countselect(sql);
        return i;
    }
    
    public int hasCourse() {
        dbpool dbcourse = new dbpool();
        String sql = "";
        sql = "select id from entity_course_info where course_type='"+this.getId()+"'";
        int i = dbcourse.countselect(sql);
        return i;
    }
}

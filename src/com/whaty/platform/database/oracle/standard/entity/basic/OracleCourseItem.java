package com.whaty.platform.database.oracle.standard.entity.basic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.entity.basic.CourseItem;
import com.whaty.platform.util.log.UserAddLog;
import com.whaty.platform.util.log.UserUpdateLog;
import com.whaty.util.log.Log;

public class OracleCourseItem extends CourseItem{
	
	public OracleCourseItem(){}

	public void setAndUnsetItem(String itemId) {
		// TODO Auto-generated method stub
		
	}

	public List getItemById(String id) {
		 dbpool dbCourse=new dbpool();
         String sql="";
         MyResultSet rs=null;
         List itemList=new ArrayList();
         try
         {
            sql = "select id,dayi,gonggao,taolun,kaoshi,zuoye,ziyuan,ZXDAYI,SMZUOYE,ZICE,PINGJIA,DAOHANG,DAOXUE,SHIYAN,ZFX,boke  from entity_course_item where id='"+id+"'";
            rs = dbCourse.executeQuery(sql);
            if(rs!=null && rs.next())
            {
            	CourseItem courseItem=new OracleCourseItem();
            	courseItem.setId(id);
                courseItem.setDayi(rs.getString("dayi"));
                courseItem.setGonggao(rs.getString("gonggao"));
                courseItem.setTaolun(rs.getString("taolun"));
                courseItem.setKaoshi(rs.getString("kaoshi"));
                courseItem.setZuoye(rs.getString("zuoye"));
                courseItem.setZiyuan(rs.getString("ziyuan"));
                courseItem.setZxdayi(rs.getString("ZXDAYI"));
                courseItem.setSmzuoye(rs.getString("SMZUOYE"));
                courseItem.setZice(rs.getString("ZICE"));
                courseItem.setPingjia(rs.getString("PINGJIA"));
                courseItem.setDaohang(rs.getString("DAOHANG"));
                courseItem.setDaoxue(rs.getString("DAOXUE"));
                courseItem.setShiyan(rs.getString("SHIYAN"));
                courseItem.setZfx(rs.getString("ZFX"));
                courseItem.setBoke(rs.getString("boke"));
                itemList.add(courseItem);
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
         return itemList;
    }

	
	 public int add() throws PlatformException {
		 if(isIdExist()!=0)
	    		throw new PlatformException("courseItem id has exist");
	        dbpool dbcourseItem = new dbpool();
	        String sql = "";
	        sql="insert into entity_course_item (id,gonggao,daohang,daoxue,dayi,smzuoye,zuoye,zice,kaoshi,shiyan,zfx,taolun,ziyuan,zxdayi) values ('"+this.getId()+"','"+this.getGonggao()+"','"+this.getDaohang()+"',"+this.getDaoxue()+",'"+this.getDayi()+"','"+this.getSmzuoye()+"','"+this.getZuoye()+"','"+this.getZice()+"','"+this.getKaoshi()+"','"+this.getShiyan()+"','"+this.getZfx()+"','"+this.getTaolun()+"','" + this.getZiyuan()+"','" + this.getZxdayi() + "')";
	        int i = dbcourseItem.executeUpdate(sql);
	        UserAddLog.setDebug("OracleCourseItem.add() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	        return i;
	    }
	 
	 public int update() throws PlatformException {
	    	
	        dbpool dbcourseItem = new dbpool();
	        String sql = "";
	        sql="update entity_course_item set dayi='"+this.getDayi()+"', gonggao='"+this.getGonggao()+"', taolun='"+this.getTaolun()+"',kaoshi='"+this.getKaoshi()+"',zuoye='"+this.getZuoye()+"', ziyuan='"+this.getZiyuan()+"' where id='"+this.getId()+"'";
	        int i = dbcourseItem.executeUpdate(sql);
	        UserUpdateLog.setDebug("OracleCourseItem.update() SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	        return i;
	    }
	 
	 
	 public int updateItemStatus(String item,String status,String id) throws PlatformException {
	    	
	        dbpool dbcourseItem = new dbpool();
	        String sql = "";
	        sql="update entity_course_item set "+item+"='"+status +"' where id='"+id+"'";
	        int i = dbcourseItem.executeUpdate(sql);
	        UserUpdateLog.setDebug("OracleCourseItem.updateItemStatus(String item,String status,String id) SQL=" + sql + " COUNT=" + i + " DATE=" + new Date());
	        return i;
	    }
	 
	 
	 
	 /**�жϸÿγ�ID�Ƿ���ڣ�0Ϊ�����ڣ�����0Ϊ���� */
	    public int isIdExist() {
	        dbpool dbcourse = new dbpool();
	        String sql = "";
	        sql = "select id from entity_course_item where id='"+this.getId()+"'";
	        int i = dbcourse.countselect(sql);
	        return i;
	    }
}

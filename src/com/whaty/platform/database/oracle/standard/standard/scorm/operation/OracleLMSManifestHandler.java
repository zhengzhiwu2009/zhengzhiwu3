/**
 * 
 */
package com.whaty.platform.database.oracle.standard.standard.scorm.operation;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.adl.parsers.dom.ADLItem;

import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.scorm.LMSManifestHandler;
import com.whaty.platform.standard.scorm.Exception.ScormException;
import com.whaty.platform.standard.scorm.util.ScormLog;

/**
 * @author Administrator
 *
 */
public class OracleLMSManifestHandler extends LMSManifestHandler {

	public OracleLMSManifestHandler() {
		super();
		this.tempItemList = new Vector();
		this.items = new Vector();
		this.courseTitle = "";
      	this.courseDir = "";
      	this.courseID = "";
      	this.control = "";

	}

	/* (non-Javadoc)
	 * @see com.whaty.platform.standard.scorm.LMSManifestHandler#updateDB()
	 */
	public void updateDB() throws ScormException
	{
			ScormLog.setDebug("******  LMSManifestHandler:updateDB()  *********");
			dbpool db=new dbpool();
			  
			//用于检验课件是否存在   huze
			boolean isExist = false;
			//用于检验课件SCO是否存在   huze
			boolean isSCO = false;
			
			//判断课程是否存在  huze
			String sqlQueryCourse1 = "select 1 from SCORM_COURSE_INFO a where a.id = '"+this.getCourseID()+"'" ;
			if(db.countselect(sqlQueryCourse1)>0) {
				isExist = true;
			}
			
			  //判断课程是否存在SCO结点  huze
			String sqlQueryCourse2 = "select 1 from SCORM_COURSE_ITEM a where a.course_id = '"+this.getCourseID()+"'" ;
			if(db.countselect(sqlQueryCourse2)>0) {
				isSCO = true;
			}
			  
			//判断是否存在同课程同类型的课件  huze
			String sqlQueryCourse = "select 1 from SCORM_COURSE_INFO a, SCORM_SCO_LAUNCH b where a.id = b.course_id and a.id = '"+this.getCourseID()+"' and b.scorm_type = '"+this.getScormType()+"'";
			MyResultSet rs = null;
			try{
				rs = db.executeQuery(sqlQueryCourse);
				if(rs != null && rs.next())
					throw new ScormException("编号为："+this.courseID+"已存在同类型课件");
			}catch(ScormException e)
			{
				throw e;
			}
			catch (Exception e) {
				throw new ScormException("未知错误");
			} finally {
				db.close(rs);
			}
		
		  //如果不存在课程
		  if(!isExist) {
		      String sqlInsertCourse= "INSERT INTO SCORM_COURSE_INFO (ID,TITLE,CONTROL_TYPE,VERSION," +
		    	 		"DESCRIPTION,NAVIGATE) VALUES('"+this.courseID+"'," +
		    	 		"'"+this.courseTitle+"','"+this.control+"','','','"+this.getNavigate()+"')";
		      ScormLog.setDebug(sqlInsertCourse);
	    	  int j=db.executeUpdate(sqlInsertCourse);
	    	  if(j<1)
	    	  {
	    		  throw new ScormException("导入课件信息失败!");
	    	  }
		  }
    	  List sqlList=new ArrayList();
    	  
    	  //用于判断SCOID是否一致  huze
    	  StringBuilder sqlCheck = new  StringBuilder();
    	  ADLItem tempItem = new ADLItem();
                
    	  // Loop through each item in the course adding it to the database
          for ( int i = 0; i < items.size(); i++ )
          {
        	   tempItem = (org.adl.parsers.dom.ADLItem)items.elementAt(i);
                        
               // Decode the URL before inserting into the database
               URLDecoder urlDecoder = new URLDecoder();
               String alteredLocation = new String();

               //  If its external, don't concatenate to the local Web root.
               if ( (tempItem.getLaunchLocation().startsWith("http://")) ||
                    (tempItem.getLaunchLocation().startsWith("https://")) )
               {
                  alteredLocation = 
                           urlDecoder.decode( (String) tempItem.getLaunchLocation() );                             
               }
               else
               {
                  // Create the altered location (with decoded url)
            	  //use relative path   
                  alteredLocation =/*CoursewareConfig.getSCoursewareConfigAbsPath() + "/whatymanager/CourseImports/" + */
                	 "/"+ courseID + "/" + this.getScormType() + "/" 
                               + urlDecoder.decode( (String) tempItem.getLaunchLocation() );
               }

               // Insert into the database
               String tmp_title="";
               tmp_title=tempItem.getTitle();
               tmp_title=tmp_title.replace("'", "''");
               String sql = null ;
               
               //如果不存在则添加SCO结点  huze
               if(!isSCO) {
                   sql="insert into scorm_course_item(COURSE_ID,ID,TYPE,TITLE,LAUNCH," +
              		"PARAMETERSTRING,DATAFROMLMS,PREREQUISITES,MASTERYSCORE,MAXTIMEALLOWED," +
              		"TIMELIMITACTION,SEQUENCE,THELEVEL) values('"+courseID+"'," +
              		"'"+tempItem.getIdentifier()+"'," +
              		"'"+tempItem.getScormType()+"'," +
              		"'"+tmp_title+"'," +
              		"'"+alteredLocation+"'," +
              		"'"+tempItem.getParameterString()+"'," +
              		"'"+tempItem.getDataFromLMS()+"'," +
              		"'"+tempItem.getPrerequisites()+"'," +
              		"'"+tempItem.getMasteryScore()+"'," +
              		"'"+tempItem.getMaxTimeAllowed()+"'," +
              		"'"+tempItem.getTimeLimitAction()+"',"+i+","+tempItem.getLevel()+")";
                   	ScormLog.setDebug(sql);
                   	sqlList.add(sql);
               }
               
               //添加sco的launch  huze
               String sqlLaunch = "insert into SCORM_SCO_LAUNCH(ID,COURSE_ID,SCO_ID,SCORM_TYPE,LAUNCH,INDEXFILE) " 
            	   + "values(to_char(S_SCORM_LAUNCH_ID.nextVal),'"+this.getCourseID()+"','"+tempItem.getIdentifier()+"','"
            	   + this.getScormType()+"','"+alteredLocation+"','"+this.getIndexfile()+"')";
               sqlList.add(sqlLaunch);
               
               //拼检查SCO是否对应的sql  huze
               sqlCheck.append(" select '"+tempItem.getIdentifier()+"',"+i+","+tempItem.getLevel()+" from dual union");
          }  // Ends looping throught the item list
          
          //判断SCO与课件已有的SCO冲突
          if(isSCO) {
        	  if(sqlCheck.length()>0) {	
        		  String tmpSql = "(" + sqlCheck.substring(0, sqlCheck.length()-5) + ")";
        		  //已经存在SCO结点-导入SCO结点
        		  int i1 = db.countselect( "(select ID,SEQUENCE,THELEVEL from SCORM_COURSE_ITEM where COURSE_ID = "+this.getCourseID()+") minus " + tmpSql) ;
        		  if(i1!=0) {
        			  throw new  ScormException("此类型SCORM课件与平台已存在的其他类型SCORM课件信息冲突，导入失败!");
        		  }
        		  //已经存在导入SCO结点-SCO结点
        		  i1 = db.countselect(tmpSql + " minus (select ID,SEQUENCE,THELEVEL from SCORM_COURSE_ITEM where COURSE_ID = "+this.getCourseID()+")") ;
        		  if(i1!=0) {
        			  throw new  ScormException("此类型SCORM课件与平台已存在的其他类型SCORM课件信息冲突，导入失败!");
        		  }
        	  } else {
        		  throw new  ScormException("此类型SCORM课件与平台已存在的其他类型SCORM课件信息冲突，导入失败!");
        	  }
        	  //sqlCheck.delete(sql., end);
          }
          
          int k=db.executeUpdateBatch(sqlList);
          if(k<1)
          {
        	  throw new ScormException("导入课件信息失败!");
          }
          ScormLog.setDebug("Update DB finish!");
	}

}

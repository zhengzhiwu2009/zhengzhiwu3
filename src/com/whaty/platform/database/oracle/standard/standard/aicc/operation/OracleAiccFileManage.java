package com.whaty.platform.database.oracle.standard.standard.aicc.operation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.standard.aicc.Exception.AiccException;
import com.whaty.platform.standard.aicc.file.AUData;
import com.whaty.platform.standard.aicc.file.CMPData;
import com.whaty.platform.standard.aicc.file.CRSData;
import com.whaty.platform.standard.aicc.file.CSTData;
import com.whaty.platform.standard.aicc.file.DESData;
import com.whaty.platform.standard.aicc.file.ORTData;
import com.whaty.platform.standard.aicc.file.PREData;
import com.whaty.platform.standard.aicc.operation.AiccFileManage;
import com.whaty.platform.util.log.UserAddLog;

public class OracleAiccFileManage extends AiccFileManage {

	
	public OracleAiccFileManage() {
	}


	public void putAiccFileToDB(CRSData crsData, List desList, List auList, List cstList, List ortList, List preList, List cmpList) throws AiccException {
		dbpool db = new dbpool();
        List sqllist=new ArrayList();
        CSTData cstData=null;
        AUData auData=null;
        DESData desData=null;
        ORTData ortData=null;
        PREData preData=null;
        CMPData cmpData=null;
                
        //CRS data
        String sqlcrs = "insert into aicc_course_info(ID,TITLE,SYSTEM_VENDOR,CREATER," +
		"ALEVEL,CST_MAXFIELDS,ORT_MAXFIELDS,TOTAL_AUS,TOTAL_BLOCKS," +
		"TOTAL_OBJECTIVES,TOTAL_COMPLEX_OBJECTIVES,VERSION," +
		"MAX_NORMAL,DESCRIPTION) values('" +
		""+this.getCourse_id()+"'" +
		",'"+crsData.getCourse().getCourseTitle()+"'" +
		",'"+crsData.getCourse().getCourseSystem()+"'" +
		",'"+crsData.getCourse().getCourseCreator()+"'" +
		",'"+crsData.getCourse().getLevel()+"'" +
		","+crsData.getCourse().getMaxFieldsCST() +
		","+crsData.getCourse().getMaxFieldsORT() +
		","+crsData.getCourse().getTotalAus()+
		","+crsData.getCourse().getTotalBlocks() +
		","+crsData.getCourse().getTotalObjectives() +
		","+crsData.getCourse().getTotalComplexObjectives() +
		",'"+crsData.getCourse().getVersion()+"'" +
		",'"+crsData.getCourseBehavior().getMaxNormal()+"'" +
		",'"+crsData.getCourseDes().getCourseDescription()+"')";
        UserAddLog.setDebug("OracleAiccFileManage.putAiccFileToDB(CRSData crsData, List desList, List auList, List cstList, List ortList, List preList, List cmpList) SQL=" + sqlcrs + " DATE=" + new Date());
        sqllist.add(sqlcrs);
        
        //CST Data
        List blockList=new ArrayList();
        for(int i=0;i<cstList.size();i++)
        	blockList.add(((CSTData)cstList.get(i)).getBlock());
        for(int i=0;i<cstList.size();i++)
        {
        	cstData=(CSTData)cstList.get(i);
        	String type="AU";
        	String member="";
        	for(int j=0;j<cstData.getMembers().size();j++)
        	{
        		member=(String)cstData.getMembers().get(j);
        		if(blockList.contains(member))
        			type="BLOCK";
        		String sqlcst = "insert into aicc_course_cst(PARENT_ID,SYSTEM_ID," +
        					"COURSE_ID,SYSTEM_TYPE) values('"+ cstData.getBlock()+"'," +
        					"'"+member+"','"+this.getCourse_id()+"','"+ type + "'" +
        					")";
        		UserAddLog.setDebug("OracleAiccFileManage.putAiccFileToDB(CRSData crsData, List desList, List auList, List cstList, List ortList, List preList, List cmpList) SQL=" + sqlcst + " DATE=" + new Date());
        		sqllist.add(sqlcst);
        	}
        }
        
        //AU Data
        
        for(int i=0;i<auList.size();i++)
        {
        	auData=(AUData)auList.get(i);
        	String sqlau = "insert into aicc_course_au(SYSTEM_ID,TYPE,COMMAND,AFILE,MASTERY_SCORE," +
        			"MAX_SCORE,MAX_TIMES,TIME_LIMIT_ACTION,SYSTEM_VENDOR,CORE_VENDOR," +
        			"WEB_LAUNCH,AU_PASSWORD,COURSE_ID) values(" +
        			"'" +auData.getSystemId()+"'," +
        			"'"+auData.getType()+"'," +
        			"'"+auData.getCommandLine()+"'," +
        			"'"+auData.getFileName()+"'," +
        			"'"+auData.getMasteryScore()+"'," +
        			"'"+auData.getMaxScore()+"'," +
        			"'"+auData.getMaxTimeAllowed()+"'," +
        			"'"+auData.getTimeLimitAction()+"'," +
        			"'"+auData.getSystemVendor()+"'," +
        			"'"+auData.getCoreVendor()+"'," +
        			"'"+auData.getWebLaunch()+"'," +
        			"'"+auData.getAuPassword()+"'," +
        			"'"+this.getCourse_id()+"')";
        	UserAddLog.setDebug("OracleAiccFileManage.putAiccFileToDB(CRSData crsData, List desList, List auList, List cstList, List ortList, List preList, List cmpList) SQL=" + sqlau + " DATE=" + new Date());
        	sqllist.add(sqlau);
        }
        
        //DES Data
        for(int i=0;i<desList.size();i++)
        {
        	desData=(DESData)desList.get(i);
        	String sqldes = "insert into aicc_course_des(SYSTEM_ID,DEVELOP_ID,TITLE," +
        			"DESCRIPTION,COURSE_ID) values(" +
        			"'" +desData.getSystemId()+"'," +
        			"'"+desData.getDeveloperId()+"'," +
        			"'"+desData.getTitle()+"'," +
        			"'"+desData.getDescription()+"'," +
        			"'"+this.getCourse_id()+"')";
        	UserAddLog.setDebug("OracleAiccFileManage.putAiccFileToDB(CRSData crsData, List desList, List auList, List cstList, List ortList, List preList, List cmpList) SQL=" + sqldes + " DATE=" + new Date());
        	sqllist.add(sqldes);
        }
        
        //ORT Data
        for(int i=0;i<ortList.size();i++)
        {
        	ortData=(ORTData)ortList.get(i);
        	for(int j=0;j<ortData.getMembers().size();j++)
        	{
        		String sqlort = "insert into aicc_course_ort(SYSTEM_ID,CHILD_ID,COURSE_ID) " +
        		"values(" +
        			"'" +ortData.getCourseElement()+"'," +
        			"'"+(String)ortData.getMembers().get(j)+"'," +
        			"'"+this.getCourse_id()+"')";
        		UserAddLog.setDebug("OracleAiccFileManage.putAiccFileToDB(CRSData crsData, List desList, List auList, List cstList, List ortList, List preList, List cmpList) SQL=" + sqlort + " DATE=" + new Date());
        		sqllist.add(sqlort);
        	}
    		
        }
        
        
        int i = db.executeUpdateBatch(sqllist);
        if(i<1)
        {
        	throw new AiccException("error in put aicc files in DB");
        }
		
	}


	public boolean courseIsExist() {
		OracleAiccCourse aiccCourse=new OracleAiccCourse();
		aiccCourse.getCourse().setCourseID(this.getCourse_id());
		return aiccCourse.isExist();
	}

}

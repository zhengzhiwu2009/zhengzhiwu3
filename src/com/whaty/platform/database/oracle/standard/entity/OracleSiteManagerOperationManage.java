package com.whaty.platform.database.oracle.standard.entity;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.standard.entity.user.OracleBasicUserList;
import com.whaty.platform.entity.SiteManagerOperationManage;
import com.whaty.platform.entity.user.SiteManagerPriv;
import com.whaty.platform.util.Page;
import com.whaty.platform.util.SearchProperty;

public class OracleSiteManagerOperationManage extends SiteManagerOperationManage{

	private SiteManagerPriv sitemanagerPriv;
	
	public OracleSiteManagerOperationManage(SiteManagerPriv siteManagerPriv){
		this.sitemanagerPriv = siteManagerPriv;
	}

	public List getStudents(Page page, String id, String reg_no, String name, String id_card, String phone, String major_id, String edu_type_id, String grade_id) throws PlatformException {
		List searchProperties = new ArrayList();
		if(id!=null && !id.equals("") && !id.equals("null"))
		{
			searchProperties .add(new SearchProperty("id",id,"="));
		}
		if(reg_no!=null && !reg_no.equals("") && !reg_no.equals("null"))
		{
			searchProperties .add(new SearchProperty("reg_no",reg_no,"like"));
		}
		if(name!=null && !name.equals("") && !name.equals("null"))
		{
			searchProperties .add(new SearchProperty("name",name,"like"));
		}
		if(id_card!=null && !id_card.equals("") && !id_card.equals("null"))
		{
			searchProperties .add(new SearchProperty("id_card",id_card,"like"));
		}
		if(phone!=null && !phone.equals("") && !phone.equals("null"))
		{
			searchProperties .add(new SearchProperty("phone",phone,"like"));
		}
		if(grade_id!=null && !grade_id.equals("") && !grade_id.equals("null"))
		{
			searchProperties .add(new SearchProperty("grade_id",grade_id,"="));
		}
		if(edu_type_id!=null && !edu_type_id.equals("") && !edu_type_id.equals("null"))
		{
			searchProperties .add(new SearchProperty("edu_type_id",edu_type_id,"="));
		}
		if(major_id!=null && !major_id.equals("") && !major_id.equals("null"))
		{
			searchProperties .add(new SearchProperty("major_id",major_id,"="));
		}
		searchProperties .add(new SearchProperty("site_id",sitemanagerPriv.getSite_id(),"="));
		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		if(page!=null)
		{
			return oracleBasicUserList.getStudents(page,searchProperties,null);
		}
		else
		{
			return oracleBasicUserList.getStudents(searchProperties,null);
		}
	}

	public int getStudentsNum(String id, String reg_no, String name, String id_card, String phone,String major_id, String edu_type_id, String grade_id) throws PlatformException {
		List searchProperties = new ArrayList();
		if(id!=null && !id.equals("") && !id.equals("null"))
		{
			searchProperties .add(new SearchProperty("id",id,"="));
		}
		if(reg_no!=null && !reg_no.equals("") && !reg_no.equals("null"))
		{
			searchProperties .add(new SearchProperty("reg_no",reg_no,"like"));
		}
		if(name!=null && !name.equals("") && !name.equals("null"))
		{
			searchProperties .add(new SearchProperty("name",name,"like"));
		}
		if(id_card!=null && !id_card.equals("") && !id_card.equals("null"))
		{
			searchProperties .add(new SearchProperty("id_card",id_card,"like"));
		}
		if(phone!=null && !phone.equals("") && !phone.equals("null"))
		{
			searchProperties .add(new SearchProperty("phone",phone,"like"));
		}
		if(grade_id!=null && !grade_id.equals("") && !grade_id.equals("null"))
		{
			searchProperties .add(new SearchProperty("grade_id",grade_id,"="));
		}
		if(edu_type_id!=null && !edu_type_id.equals("") && !edu_type_id.equals("null"))
		{
			searchProperties .add(new SearchProperty("edu_type_id",edu_type_id,"="));
		}
		if(major_id!=null && !major_id.equals("") && !major_id.equals("null"))
		{
			searchProperties .add(new SearchProperty("major_id",major_id,"="));
		}
		searchProperties .add(new SearchProperty("site_id",sitemanagerPriv.getSite_id(),"="));
		OracleBasicUserList oracleBasicUserList = new OracleBasicUserList();
		return oracleBasicUserList.getStudentsNum(searchProperties);
	}
	
}

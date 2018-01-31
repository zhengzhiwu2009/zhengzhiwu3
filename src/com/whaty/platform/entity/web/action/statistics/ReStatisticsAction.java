package com.whaty.platform.entity.web.action.statistics;

import java.util.ArrayList;
import java.util.List;

import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class ReStatisticsAction extends MyBaseAction{
    private String type;
	@Override
	public void initGrid() {
		
		this.getGridConfig().setCapability(false, false, false);
		this.getGridConfig().setTitle("年度远程培训注册情况汇总表");
		this.getGridConfig().addColumn(this.getText("id"), "id",false);
		this.getGridConfig().addColumn(this.getText("年度"), "Annual", true);
		this.getGridConfig().addColumn(this.getText("注册类别"), "itype", true);
		//this.getGridConfig().addRenderScript(this.getText("注册类别"), "{if (${value}=='collective') return '集体用户';else if (${value}=='personal') return '个人用户'}", "itype");
		this.getGridConfig().addColumn(this.getText("新增注册数"), "aumont", false);
		this.getGridConfig().addColumn(this.getText("累计注册数"), "total", false);
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		this.servletPath="/entity/statistics/reStatistics";
		
	}
	
	public String index(){
		
		return "start";
	}
   @Override
public Page list() {
	Page page=null;
	StringBuffer sql=new StringBuffer();
	sql.append("select id,Annual,itype,aumont,total from (" );
	sql.append(" select max(pe.id) as id,to_char(pe.data_date, 'yyyy') as Annual,cast('集体用户' as varchar(8)) as itype,          ");
	sql.append("        count(pe.id) as aumont,		count(pe.id) as total		 ");
	sql.append("   from pe_enterprise pe					 ");
	sql.append("  group by to_char(pe.data_date, 'yyyy')			 ");
	sql.append(" union all							 ");
	sql.append(" select max(pbs.id) as id,to_char(pbs.data_date, 'yyyy') as Annual,cast('个人用户' as varchar(8)) as itype,		 ");
	sql.append("        count(pbs.id) as aumont,	count(pbs.id) as total			 ");
	sql.append("   from pe_bzz_student pbs					 ");
	sql.append("  where pbs.student_type = '402880a91da9032f011da905da070003'");
	sql.append("  group by to_char(pbs.data_date, 'yyyy')	)where 1=1		 ");
	this.setDir("desc");
	this.setSort("Annual");
	this.setSqlCondition(sql);
    try {
		page=this.getGeneralService().getByPageSQL(sql.toString(), Integer.parseInt(this.getLimit()), Integer.parseInt(this.getStart()));
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (EntityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	page.setItems(getAnnualTotal(getPersonal(),getCollective(),page.getItems()));
	
	return page;
 }
   public List<Object[]> getCollective(){
	   List<Object[]> list=new ArrayList<Object[]>();
	   StringBuffer sql=new StringBuffer();
	   sql.append(" select to_char(pe.data_date, 'yyyy') as dataDate,  ");
	   sql.append("        count(pe.id) as aumont			 ");
	   sql.append("   from pe_enterprise pe					 ");
	   sql.append("  group by to_char(pe.data_date, 'yyyy')");
	   try {
		list=this.getGeneralService().getBySQL(sql.toString());
	} catch (EntityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return list;
   }
   public List<Object[]> getPersonal(){
	   List<Object[]> list=new ArrayList<Object[]>();
	   StringBuffer sql=new StringBuffer();
	   sql.append(" select to_char(pbs.data_date, 'yyyy') as dataDate,		 ");
	   sql.append("        count(pbs.id) as aumont			 ");
	   sql.append("   from pe_bzz_student pbs					 ");
	   sql.append("  where pbs.student_type = '402880a91da9032f011da905da070003'");
	   sql.append("  group by to_char(pbs.data_date, 'yyyy')			 ");
	   try {
		list=this.getGeneralService().getBySQL(sql.toString());
	} catch (EntityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return list;
   }
   
   public List getAnnualTotal(List<Object[]> personalList,List<Object[]> collectiveList,List<Object[]> pageList ){
	    if(null!=pageList&&!"".equals(pageList)){
	        try {
				for(int i=0;i<pageList.size();i++){
					
					String pageYear=pageList.get(i)[1].toString();
					String pageSum=pageList.get(i)[4].toString();
					String pageType=pageList.get(i)[2].toString();
					int sum=Integer.parseInt(pageSum);
				    if("个人用户".equals(pageType)&&null!=personalList){
				    	for(int j=0;j<personalList.size();j++){
				    		String plyear=personalList.get(j)[0].toString();
				    		String plaumont=personalList.get(j)[1].toString();
				    		int year=Integer.parseInt(plyear);
				    		int year1=Integer.parseInt(pageYear);
				    		int aumont=Integer.parseInt(plaumont);
				    		if(year<year1){
				    	     sum+=aumont;
				    		}
				    	}
				    	
				    }
				    else if("集体用户".equals(pageType)&&null!=collectiveList){
				    	for(int j=0;j<collectiveList.size();j++){
				    		String plyear=collectiveList.get(j)[0].toString();
				    		String plaumont=collectiveList.get(j)[1].toString();
				    		int year=Integer.parseInt(plyear);
				    		int year1=Integer.parseInt(pageYear);
				    		int aumont=Integer.parseInt(plaumont);
				    		if(year<year1){
				    	     sum+=aumont;
				    		}
				    	}
				    }
				    pageList.get(i)[4]=sum+"";
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	            return pageList;
   }
               
   
}

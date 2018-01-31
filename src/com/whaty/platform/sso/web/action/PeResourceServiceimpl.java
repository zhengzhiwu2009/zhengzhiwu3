package com.whaty.platform.sso.web.action;

import java.util.List;

import com.whaty.platform.entity.bean.AbstractBean;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeResource;
import com.whaty.platform.entity.service.imp.GeneralServiceImp;
import com.whaty.platform.entity.web.action.MyBaseAction;

public class PeResourceServiceimpl extends GeneralServiceImp  implements PeResourceService {



	@Override//课程资料删除
	public int delete(String id,String teachclassid) throws Exception {
		// TODO Auto-generated method stub
		String del="delete from interaction_teachclass_info ii where ii.fk_ziliao='"+id+"' and ii.teachclass_id='"+teachclassid+"'";
			int i = this.getGeneralDao().executeBySQL(del);
		return i;
	}

	@Override//课程资料列表
	public List listresource(String id) throws Exception{
		// TODO Auto-generated method stub
		String sql=" select zl.id,zl.name,ii.type,zl.file_link,ii.teachclass_id,ii.fk_ziliao  from PE_RESOURCE zl inner join interaction_teachclass_info ii on ii.fk_ziliao=zl.id "+
//		" inner join enum_const ec1 on zl.flag_isvalid=ec1.id and ec1.code='0' "+
//		"  inner join enum_const ec2 on zl.flag_offline=ec2.id and ec2.code='0' "+
		" where ii.teachclass_id='"+id+"' ";
		List list= this.getGeneralDao().getBySQL(sql);
			
		return list;
	}

	@Override//课程资料详情
	public List listdetails(String id) throws Exception{
		// TODO Auto-generated method stub
		String sql="select zl.name,zl.DESCRIBE,zl.content from PE_RESOURCE zl  where zl.id='"+id+"' ";
		List list=null;
			list = this.getGeneralDao().getBySQL(sql);
		return list;
	}

	@Override//课程资料修改
	public List edit(String id,String cid) throws Exception {
		// TODO Auto-generated method stub
		String sql=" select zl.id,zl.name, zl.content,zl.DESCRIBE,ii.teachclass_id, "+
		" zl.flag_isvalid ,zl.FLAG_DOWNLOAD,zl.FLAG_TOP,zl.FLAG_INDEX,to_char(zl.replyDate,'yyyy-MM-dd') as replydate,zl.fabuunit,zl.RESETYPE "+
		" from PE_RESOURCE zl inner join interaction_teachclass_info ii on ii.fk_ziliao=zl.id and ii.teachclass_id='"+cid+"' "+
		" where zl.id='"+id+"' ";
		List list= this.getGeneralDao().getBySQL(sql);
		return list;
	}

	@Override//课程资料添加
	public int add() throws Exception {
		// TODO Auto-generated method stub
		
		return 0;
	}

	@Override//课程资料下载列表 
	public List listdownload(String id) throws Exception {
		// TODO Auto-generated method stub
		
		String sql=" select zl.FILE_LINK  from PE_RESOURCE zl "+
		" where zl.id='"+id+"' and zl.FILE_LINK is not null  ";
		List list= this.getGeneralDao().getBySQL(sql);
		
		return list;
	}

	@Override//课表资料检索
	public List peResourcejs(String id, String name) throws Exception {
		// TODO Auto-generated method stub
		String sql=" select zl.id,zl.name,ii.type,zl.file_link,ii.teachclass_id,ii.fk_ziliao  from PE_RESOURCE zl inner join interaction_teachclass_info ii on ii.fk_ziliao=zl.id "+
		" where ii.teachclass_id='"+id+"' and zl.name like '%"+name+"%'";
		List list= this.getGeneralDao().getBySQL(sql);
		return list;
	}


}

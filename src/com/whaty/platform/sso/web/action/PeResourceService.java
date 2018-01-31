package com.whaty.platform.sso.web.action;

import java.util.List;

import com.whaty.platform.entity.bean.PeResource;
import com.whaty.platform.entity.service.GeneralService;




public interface PeResourceService extends GeneralService{
	public List listresource(String id)throws Exception;//课程资料列表
	public List listdetails(String id) throws Exception;//课程资详情
	public List listdownload(String id) throws Exception;//课程资料下载
	public int  add()throws Exception;//课程资料添加
	public int delete(String id,String teachclassid) throws Exception;//课程资料删除
	public List edit(String id,String cid) throws Exception;//课程资修改
	public List peResourcejs(String id,String name)throws Exception; //检索
}

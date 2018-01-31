package com.whaty.platform.sso.web.action;

import java.util.List;

import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

public class JsonListAction  extends MyBaseAction<PeVotePaper> {
	private String id;

	public String jsonListSatisf(){
		String sql=
			"select t.id as id,t.question_note as name " +
			"  from pr_vote_question t " + 
			" inner join enum_const e on t.flag_question_type = e.id " + 
			" where  " +
			" t.fk_vote_paper_id = '"+this.id+"'  and   " + 
			"   e.code='3' ";

		List jsonList = null;
		try {
			jsonList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(jsonList));
		return "json";
	}
	public String jsonVoteList(){
		String sql=" select id, title  from pe_vote_paper   where fk_parent_id is null ";

		List jsonList = null;
		try {
			jsonList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(jsonList));
		return "json";
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void initGrid() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		
	}
	

}

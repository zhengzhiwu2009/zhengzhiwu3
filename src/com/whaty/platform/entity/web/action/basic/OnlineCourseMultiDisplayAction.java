package com.whaty.platform.entity.web.action.basic;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.whaty.platform.entity.bean.PeBzzTchCourseware;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.util.JsonUtil;

/**
 * 多表现形式课件管理
 * @author Huze
 */
public class OnlineCourseMultiDisplayAction extends MyBaseAction<PeBzzTchCourseware> {

	//课件code
	private String code;
	//课件类型
	private String id;
	//课件首页
	private String indexFile;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIndexFile() {
		return indexFile;
	}

	public void setIndexFile(String indexFile) {
		this.indexFile = indexFile;
	}

	@Override
	public void initGrid() {
		
		this.getGridConfig().setTitle(this.getText("课件各形式列表"));
		
		
		this.getGridConfig().setCapability(false,false, false);
		this.getGridConfig().setShowCheckBox(false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("首页"),"indexfile");
		this.getGridConfig().addColumn(this.getText("类型名称"), "name");
		
		this.getGridConfig().addRenderFunction(this.getText("点击查看"),
				"<a href='/entity/manager/basic/jumpOnlineCourse.jsp?id=${value}' target='_blank'>查看</a>",
				"id");
		this.getGridConfig().addRenderFunction(this.getText("修改首页"),
				"<a href='#' onclick=\""+this.getScript().toString()+"\">修改</a>",
				"id");
		this.getGridConfig().addRenderFunction(this.getText("操作"),
				"<a href=\"/entity/manager/basic/deleteCourse.jsp?id=${value}&code="+this.getCode()+"\") >删除</a>",
				"id");
	}

	public Page list() {
		StringBuffer sb = new StringBuffer();
		sb.append("select ssl.id,ssl.indexfile,st.name from scorm_sco_launch ssl inner join scorm_type st on ssl.scorm_type=st.code where ssl.course_id='");
		sb.append(this.getCode());
		sb.append("'");
		this.setSqlCondition(sb);
		Page page = null;
		try {
			page = getGeneralService().getByPageSQL(sb.toString(),
					Integer.parseInt(this.getLimit()),
					Integer.parseInt(this.getStart()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
	
	@Override
	public void setEntityClass() {
		this.entityClass = PeBzzTchCourseware.class;
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/basic/onlineCourseMutiDisplay";
	}
	
	private StringBuffer getScript() {
			StringBuffer script = new StringBuffer();
			script.append("	var indexInput = new Ext.form.TextField({     ");
			script.append("                  fieldLabel: '课件首页',allowBlank:false,");
			script.append("                  name:'indexFile',blankText: ''});    ");
			script.append("	var fid = new Ext.form.Hidden({name:'code',value:'"+this.getCode()+"'}); ");
			script.append("	var sid = new Ext.form.Hidden({name:'id',value:'${value}'}); ");
			script.append("	var formPanel = new Ext.form.FormPanel({                                                                                                      ");
			script.append("	        frame:true,   ");
			script.append("		    labelWidth: 100,     ");
			script.append("		    defaultType: 'textfield',");
			script.append("		  	autoScroll:true,     ");
			script.append("		    items: [indexInput,fid,sid]   ");
			script.append("	});                ");
			script.append("	var addModelWin = new Ext.Window({  ");
			script.append("	        title: '课件首页',   ");
			script.append("		    width: 450,               ");
			script.append("		    height: 225,              ");
			script.append("		    minWidth: 300,            ");
			script.append("		    minHeight: 250,           ");
			script.append("		    layout: 'fit',            ");
			script.append("		    plain:true,               ");
			script.append("		    bodyStyle:'padding:5px;', ");
			script.append("		    buttonAlign:'center',    ");
			script.append("		    items: formPanel,        ");
			script.append("		    buttons: [{               ");
			script.append("		  	  text: '提交',           ");
			script.append("		  	  handler: function() {    "); 
			script.append("		  	     if (formPanel.form.isValid()) {  ");
			script.append("                 formPanel.getForm().submit({          ");
			script.append("	                 url:'/entity/basic/onlineCourseMutiDisplay_updateIndex.action',   ");
			script.append("	                 waitMsg:'更新中...',     ");
			script.append("	                 success: function(form, action) {  ");
			script.append("		               var responseArray = action.result; ");
			script.append("		               if(responseArray.success=='true'){  addModelWin.close();storePageForReload.reload();   ");
			script.append("			             Ext.MessageBox.alert('成功', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');");
			script.append("		               } else { addModelWin.show();          ");
			script.append("			             Ext.MessageBox.alert('失败', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');");
			script.append("		               }                        ");
			script.append("	                 },");
			script.append("                  failure:function(form, action){");
			script.append("		               var responseArray = action.result; ");
			script.append("		               if(responseArray.success=='true'){  addModelWin.close();storePageForReload.reload();   ");
			script.append("			             Ext.MessageBox.alert('成功', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');");
			script.append("		               } else { addModelWin.show();          ");
			script.append("			             Ext.MessageBox.alert('失败', responseArray.info + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');");
			script.append("		               }                        ");
			script.append("                  }        ");
			script.append("                 });            ");
			script.append("		  	     }else{   ");
			script.append("		  	       Ext.MessageBox.alert('错误', '输入错误，请先修正提示的错误');  ");
			script.append("		  	     }                        ");
			script.append("		  	  }                            ");
			script.append("		  	 },{                              ");
			script.append("		  	    text: '取消',                 ");
			script.append("		  	    handler: function(){addModelWin.close();}  ");
			script.append("		  	 }]                               ");
			script.append("	});                                       ");
			script.append("	addModelWin.show();                       ");
			return script;
		
	}
	
	public String updateIndex() {
		Map map = new HashMap();
		this.getCode();
		String sql = "update scorm_sco_launch set indexfile='"+this.getIndexFile()+"',launch=substr(launch,0,INSTR(launch, '/', -1, 1))||'"+this.getIndexFile()+"' where id='"+this.getId()+"'" ;
		try {
			int i = this.getGeneralService().executeBySQL(sql);
			if(i>0) {
				map.put("info", "修改成功");
				map.put("success", "true");
			} else {
				
			}
		} catch (EntityException e) {
			map.put("info", "修改失败");
			map.put("success", "fasle");
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return json();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

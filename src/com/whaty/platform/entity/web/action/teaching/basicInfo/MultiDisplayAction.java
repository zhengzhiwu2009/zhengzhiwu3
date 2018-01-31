package com.whaty.platform.entity.web.action.teaching.basicInfo;

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
public class MultiDisplayAction extends MyBaseAction<PeBzzTchCourseware> {

	//课件code
	private String code;
	//课件类型
	private String scormType;
	//课件首页
	private String indexFile;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getScormType() {
		return scormType;
	}

	public void setScormType(String scormType) {
		this.scormType = scormType;
	}

	public String getIndexFile() {
		return indexFile;
	}

	public void setIndexFile(String indexFile) {
		this.indexFile = indexFile;
	}

	@Override
	public void initGrid() {
		PeBzzTchCourseware courseware = null;
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzTchCourseware.class).add(Restrictions.eq("code", this.getCode()));
			courseware = (PeBzzTchCourseware)(this.getGeneralService().getList(dc).get(0));
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(courseware==null) {
			this.getGridConfig().setTitle(this.getText("课件各形式列表"));
		} else {
			this.getGridConfig().setTitle(this.getText(courseware.getName()+"课件各形式列表"));
		}
		
		this.getGridConfig().setCapability(false,false, false);
		this.getGridConfig().setShowCheckBox(false);
		this.getGridConfig().addColumn(this.getText("ID"), "id", false);
		this.getGridConfig().addColumn(this.getText("类型名称"), "name");
		this.getGridConfig().addColumn(this.getText("首页"),"indexfile");
		this.getGridConfig().addRenderScript(this.getText("点击查看"),
				"{return '<a href=/CourseImports/"+StringUtils.defaultIfEmpty(this.getCode(), "")+"/'+record.data['id']+'/'+record.data['indexfile']+' target=\"_blank\">查看</a>';}",
				"");
		this.getGridConfig().addRenderFunction(this.getText("修改首页"),
				"<a href='#' onclick=\""+this.getScript().toString()+"\">修改</a>",
				"id");
		this.getGridConfig().addRenderScript(this.getText("操作"),
				"{return '<a href=\"#\" onclick=window.open(\"/entity/manager/course/courseware/scorm12/delete_scorm_info.jsp?courseware_id="+StringUtils.defaultIfEmpty(this.getCode(), "")+"&scormType='+record.data['id']+'\") >删除</a>';}",
				"id");
	}

	public Page list() {
		StringBuffer sb = new StringBuffer();
		sb.append("select id,name,indexfile from (select distinct b.code as id,b.name,indexfile from scorm_sco_launch a , scorm_type b where a.scorm_type = b.code and a.course_id = '");
		sb.append(this.getCode());
		sb.append("')");
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
		this.servletPath = "/entity/teaching/multidisplay";
	}
	
	private StringBuffer getScript() {
		StringBuffer script = new StringBuffer();
		script.append("	var indexInput = new Ext.form.TextField({     ");
		script.append("                  fieldLabel: '课件首页',allowBlank:false,");
		script.append("                  name:'indexFile',blankText: ''});    ");
		script.append("	var fid = new Ext.form.Hidden({name:'code',value:'"+this.getCode()+"'}); ");
		script.append("	var sid = new Ext.form.Hidden({name:'scormType',value:'${value}'}); ");
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
		script.append("	                 url:'/entity/teaching/multidisplay_updateIndex.action',   ");
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
		String sql = "update scorm_sco_launch t set indexfile='"+this.getIndexFile()+"' where course_id='"+this.getCode()+"' and scorm_type='"+this.getScormType()+"'" ;
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
}

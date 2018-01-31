package com.whaty.platform.entity.web.action.teaching.basicInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeEnterprise;
import com.whaty.platform.entity.bean.PeTchCourse;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.SatisfactionQuestionInfo;
import com.whaty.platform.entity.bean.SatisfactionSurveyPaperInfo;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.Container;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.util.Const;
import com.whaty.platform.util.JsonUtil;

import java.io.File;
import java.net.URLEncoder;

import jxl.Sheet;
import jxl.Workbook;

import com.whaty.util.Exception.WhatyUtilException;
import com.whaty.util.string.encode.*;
import com.whaty.platform.util.*;

/**
 * @param
 * @version 创建时间：2009-6-21 上午10:40:06
 * @return
 * @throws PlatformException
 * 类说明
 */
public class SatisfactionQuestionManagerAction extends MyBaseAction<SatisfactionQuestionInfo> {
	
	private String tempFlag;
	private String body;
	private String title;
	private String[] options ;
	private String answer ;
	private int count;
	private SatisfactionQuestionInfo questionInfo;
	private EnumConstService enumConstService;
	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	@Override
	public void initGrid() {
			this.getGridConfig().setTitle("满意度调查题目列表");
			this.getGridConfig().setCapability(false, true, false);
			
			this.getGridConfig().addColumn(this.getText("ID"), "id", false);
			this.getGridConfig().addColumn(this.getText("题目名称"), "title");
			this.getGridConfig().addRenderFunction(this.getText("查看题目"), "<a href=\"/entity/teaching/satisfactionQuestionManager_viewInfo.action?bean.id=${value}\"  target='_blank'>查看</a>","id");
			this.getGridConfig().addRenderFunction(this.getText("修改题目"), "<a href=\"/entity/teaching/satisfactionQuestionManager_toEdit.action?bean.id=${value}\" >修改</a>","id");
			this.getGridConfig().addMenuScript(this.getText("添加"),"{window.location='/entity/teaching/satisfactionQuestionManager_toAdd.action';}");	
			this.getGridConfig().addMenuScript(this.getText("批量导入题目"),"{window.location='/entity/teaching/satisfactionQuestionManager_toUpload.action';}");
	}

	@Override
	public void setEntityClass() {
		// TODO Auto-generated method stub
		this.entityClass=SatisfactionQuestionInfo.class;

	}

	@Override
	public void setServletPath() {
		// TODO Auto-generated method stub
		this.servletPath="/entity/teaching/satisfactionQuestionManager";
	}
	
	public void setBean(SatisfactionQuestionInfo instance) {
		super.superSetBean(instance);
		
	}
	
	public SatisfactionQuestionInfo getBean(){
		return super.superGetBean();
	}
	public String addQuestion(){
		questionInfo.setQuestioncore(this.getQuestionXml());
		try {
			questionInfo = this.getGeneralService().save(questionInfo);
			this.setMsg("一条满意度调查题目添加成功！");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("添加失败！");
		}
		this.setTogo("/entity/teaching/satisfactionQuestionManager_toAdd.action");
		return "msg";
	}
	
	public String updateQuestion(){
		try {
			questionInfo = this.getGeneralService().getById(this.getBean().getId());
			questionInfo.setQuestioncore(this.getQuestionXml());
			questionInfo.setTitle(this.getTitle());
			questionInfo = this.getGeneralService().save(questionInfo);
			this.setMsg("修改成功！");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("修改失败！");
		}
		this.setTogo("/entity/teaching/satisfactionQuestionManager_toEdit.action?bean.id="+this.getBean().getId());
		return "msg";
	}
	
	public String getQuestionXml(){
		String xml = "<question><body>" + HtmlEncoder.encode(body) + "</body><select>";
		for(int i=0; i<options.length; i++) {
			int charCode = i + 65;		//选项字母的ASCII码
			String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
			xml = xml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(options[i]) + "</content></item>";
		}
//		xml = xml + "</select><answer>" + HtmlEncoder.encode(answer) + "</answer></question>";
		xml = xml + "</select></question>";
		return xml;
	}
	public String getQuestion(){
		String questionCore = "";
		try {
			this.setBean(this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		com.whaty.util.string.WhatyStrManage strManage = new com.whaty.util.string.WhatyStrManage();
			questionCore = XMLParserUtil.getSingleMultiContentNoAn(this.getBean().getQuestioncore());
		strManage.setString(questionCore);
//		try {
//			questionCore = strManage.htmlDecode();
//		} catch (WhatyUtilException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return questionCore;
	}
	
	public String viewInfo(){
		ServletActionContext.getRequest()
		.getSession().setAttribute("questionInfo", this.getQuestion());
		return "questionInfo";
	}
	public Map getQuestionMap(){
		Map questionMap = new HashMap();
		try {
			this.setBean(this.getGeneralService().getById(this.getBean().getId()));
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		List coreList = XMLParserUtil.parserSingleMultiNoAn(this.getBean().getQuestioncore());
		
		questionMap.put("id", this.getBean().getId());
		questionMap.put("title", this.getBean().getTitle());
		questionMap.put("body",coreList.get(0));
		questionMap.put("answer",coreList.get(coreList.size()-1));
		questionMap.put("coreList",coreList);
		questionMap.put("charCode",(coreList.size()-1)/2);
		return questionMap;
	}
	
	
	public String toEdit(){
		ServletActionContext.getRequest()
		.getSession().setAttribute("questionMap", this.getQuestionMap());
		return "questionEdit";
	}
	public String toAdd(){
		ServletActionContext.getRequest()
		.getSession().setAttribute("satisfactionQuestion", this.getBean());
		return "questionAdd";
	}
	public String toUpload(){
		ServletActionContext.getRequest()
		.getSession().setAttribute("satisfactionQuestion", this.getBean());
		return "questionUpload";
	}
	
	public String exeUpload(){
		try {
			this.saveUpload(this.get_upload());
			if(this.getMsg()==null){
				this.setMsg("共有"+this.count+"条数据导入成功！");
			}else{
				this.setMsg(this.getMsg()+"共有"+this.count+"条数据导入成功！");
			}		
			this.setTogo("/entity/teaching/satisfactionQuestionManager.action");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo("/entity/teaching/satisfactionQuestionManager_toUpload.action");
		}
		return "msg";
	}
	public void saveUpload(File file) throws EntityException{
		StringBuffer msg = new StringBuffer();
		Workbook work = null;
		SatisfactionQuestionInfo info = new SatisfactionQuestionInfo();
		try {
			work = Workbook.getWorkbook(file);
		} catch (Exception e) {
			e.printStackTrace();
			msg.append("Excel表格读取异常！批量添加失败！<br/>");
			throw new EntityException(msg.toString());
		}
		Sheet sheet = work.getSheet(0);
		int rows = sheet.getRows();
		if (rows < 2) {
			msg.append("表格为空！<br/>");
		}
		String temp = "";
		String xml = "";
		for (int i = 1; i < rows; i++) {
			info = new SatisfactionQuestionInfo();
			try {
				temp = sheet.getCell(0, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，题目名称不能为空！<br/>");
					continue;
				}else{	
					DetachedCriteria dc = DetachedCriteria.forClass(SatisfactionQuestionInfo.class);
					dc.add(Restrictions.eq("title", temp));				
					List<SatisfactionQuestionInfo> satisfactionQuestionInfo = this.getGeneralService().getList(dc);				
					if (satisfactionQuestionInfo!=null &&!satisfactionQuestionInfo.isEmpty()) {
						msg.append("第" + (i + 1) + "行数据，题目名称重复！<br/>");
						continue;
					}else{
						info.setTitle(temp);	
					}						
				}			
				temp = sheet.getCell(1, i).getContents().trim();
				if (temp == null || "".equals(temp)) {
					msg.append("第" + (i + 1) + "行数据，题目内容不能为空！<br/>");
					continue;
				}else{
					xml = "<question><body>" + HtmlEncoder.encode(temp) + "</body><select>";
					for(int j=0; j<=5; j++) {
						temp = sheet.getCell(j+2, i).getContents().trim();
						if(temp!=null&&!"".equals(temp)){
							int charCode = j + 65;		//选项字母的ASCII码
							String index = String.valueOf((char)charCode);	//将ASCII码转换成字母
							xml = xml + "<item><index>" + HtmlEncoder.encode(index) + "</index><content>" + HtmlEncoder.encode(temp) + "</content></item>";	
						}
					}
					xml = xml + "</select></question>";
				}	
				info.setQuestioncore(xml);
				this.questionInfo=this.getGeneralService().save(info);
				if(this.questionInfo!=null){
					this.count++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				msg.append("第" + (i + 1) + "行数据添加失败！<br/>");
				continue;
			}
		}

		if (msg.length() > 0) {
			msg.append("批量导入试题失败，请修改以上错误之后重新上传！<br/>");
			this.setMsg(msg.toString());
		}
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String[] getOptions() {
		return options;
	}

	public void setOptions(String[] options) {
		this.options = options;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getTempFlag() {
		return tempFlag;
	}

	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	public SatisfactionQuestionInfo getQuestionInfo() {
		return questionInfo;
	}

	public void setQuestionInfo(SatisfactionQuestionInfo questionInfo) {
		this.questionInfo = questionInfo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}

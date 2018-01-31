package com.whaty.platform.entity.web.action.workspaceStudent;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.database.oracle.MyResultSet;
import com.whaty.platform.database.oracle.dbpool;
import com.whaty.platform.database.oracle.standard.entity.activity.OracleOpenCourse;
import com.whaty.platform.entity.PlatformFactory;
import com.whaty.platform.entity.PlatformManage;
import com.whaty.platform.entity.activity.OpenCourse;
import com.whaty.platform.entity.bean.AssignRecord;
import com.whaty.platform.entity.bean.CoursePeriodStudent;
import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.FrequentlyAskedQuestions;
import com.whaty.platform.entity.bean.PeBzzBatch;
import com.whaty.platform.entity.bean.PeBzzExamLate;
import com.whaty.platform.entity.bean.PeBzzExamScore;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOnlineCourse;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzPici;
import com.whaty.platform.entity.bean.PeBzzPiciStudent;
import com.whaty.platform.entity.bean.PeBzzRefundInfo;
import com.whaty.platform.entity.bean.PeBzzReplyInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeBzzTeachingLiveroom;
import com.whaty.platform.entity.bean.PeElectiveCoursePeriod;
import com.whaty.platform.entity.bean.PeResource;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrBzzTchOpencourse;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.PrBzzTchStuElectiveBack;
import com.whaty.platform.entity.bean.SiteEmail;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.bean.SystemApply;
import com.whaty.platform.entity.bean.TrainingCourseStudent;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.EnumConstService;
import com.whaty.platform.entity.service.studentStatas.StudentWorkspaceService;
import com.whaty.platform.entity.user.EntityUser;
import com.whaty.platform.entity.user.StudentPriv;
import com.whaty.platform.entity.util.MyUtil;
import com.whaty.platform.entity.util.Page;
import com.whaty.platform.entity.web.action.MyBaseAction;
import com.whaty.platform.entity.web.util.Base64Util;
import com.whaty.platform.entity.web.util.WebServiceInvoker;
import com.whaty.platform.entity.web.util.XMLFileUtil;
import com.whaty.platform.sso.service.SsoUserService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;
import com.whaty.platform.training.basic.StudyProgress;
import com.whaty.platform.util.JsonUtil;

public class StudentWorkspaceAction extends MyBaseAction {

	private Date dateString;
	private String operateresult;
	private String ziliaoname;// 资料名
	private String ziliaotypes;// 资料类型
	private List flagResourceType;
	private String fabuunit;// 资料发布单位
	private String kname;// 课程名称
	private String kbianhao;// 课程编号
	private String perid;// 资料ID
	private List filelink;// 资料附件
	private List resourceList;// 资料详情
	private List resourceListc;// 资料课程相关资料
	private String password_old;
	private String password_new;
	private String opencourseid;
	private String opencoursename;
	private List learningCourses;
	private List learntCourses;
	private List notLearnCourses;
	private List<PrBzzTchStuElectiveBack> electiveBackList;
	private String sql = "";
	private List<StudentBatch> listSb;
	private PeBzzBatch peBzzBatch;
	private PeBzzStudent peBzzStudent;
	private SsoUser ssoUser;
	private File photo;
	private String photoFileName;
	private List<PeBzzTeachingLiveroom> liveroomlist;
	private List<PeBzzOnlineCourse> peBzzOnlineCourses;
	private String reason;
	private String latereason;
	private List<PeVotePaper> peVotePaperList; // 调查问卷
	private List applyList;

	private List<PeBzzReplyInfo> peBzzReplyInfoList = new ArrayList<PeBzzReplyInfo>();
	private List<PeElectiveCoursePeriod> cpList = new ArrayList<PeElectiveCoursePeriod>();

	private int totalCount; // 总条数
	private int startIndex = 0; // 开始数
	private int pageSize = 10; // 页面显示数
	private Page page;

	/** 学员段首页 */
	private List recommendCourseList;// 热门推荐课程
	private List seriesCourseList; // 专题系列课程
	private List publishCourseList; // 新发布课程
	private List informationList; // 资料
	private List noticeList;// 公告列表
	private String notice;

	private String optamount;// 操作金额
	private String opttype;// 操作类型
	private String cname;
	private List ziLiaotype;
	private List courseTypeList;// 课程类别列表
	private List courseCategoryList;// 课程性质列表
	// Lee start 2014年03月12日 添加内容属性查询条件
	private List courseContentList;// 内容属性列表
	private List suggestrenList;// 建议学习人群
	private String suggestren;// 建议学习人群
	private String coursearea;// 课程所属区域
	private List courseareaList;// 课程所属区域
	private String courseContent;// 内容属性ID
	private String endDate;// Lee 选课期结束日期
	private List seriesList; // 系列课程列表

	private String ktimestart; // 开始时间开始
	private String ktimeend;// 开始时间结束
	private String etimestart;// 线束时间开始
	private String etimeend;// 线束时间开始

	private String mybatchid;

	private String orderno;
	private String orderprice;
	private String fpstatus;

	private String zhanneixinNum;// 个人主页未读站内信数量
	private String tongzhigonggaoNum;// 个人主页通知公告数量
	private String diaochawenjuanNum;// 个人主页可填写调查问卷数量
	private String weizhifudingdanNum;// 个人主页未支付订单数量
	private String xuankeqiNum;// 个人主页选课期数量
	private BigDecimal yufufeizhanghuyueNum;// 个人主页预付费账户余额
	private String zhengzaixuexikechengNum;// 个人主页正在学习课程数量
	private BigDecimal zhengzaixuexikechengTime;// 个人主页正在学习课程学时
	private String daikaoshikechengNum;// 个人主页待考试课程数量
	private BigDecimal daikaoshikechengTime;// 个人主页待考试课程学时
	private String zhuanxiangpeixunNum;// 个人主页专项培训数量
	private String zaixianzhibokechengNum;// 个人主页在线直播课程数量
	private String zaixiankaoshiNum;// 个人主页在线考试数量

	private List yufeihislist;// 预付费历史

	private String title;// 问题标题
	private String type;
	private String keywords;// 关键字
	private List typeList;// 问题类型列表
	private String qid;// 常见问题ID
	private FrequentlyAskedQuestions question;
	private String ids;
	private List issueDetail;// 问题及建议问题详细
	private List replyList;// 问题及建议回复
	private List issueType;// 问题及建议分类

	// Lee start 2014年04月03日
	private String yxkcxss;// 已选课程学时数

	private String types;

	// lwq 2015-12-22
	private String tagIds;// 资料标签

	private List resourceTagList;
	private List resourceIsOpen;

	private String pass;// lwq 2016-01-18
	private String passEmail;
	private String choosePassEmail;
	private String content;
	private List fileList;
	private List<EnumConst> resourceTypeList;
	private String resetype;
	private String resourceName;
	private String tagNames;
	private String describe;

	private List<File> file;
	private List<String> fileFileName;
	private List<String> fileContentType;

	private InputStream inputStream;

	private String fileName;

	private String replyDate;

	private String isOpen;

	private String uid;

	private List courseDocList;

	private SsoUserService ssoUserService;

	private String orderInfo;
	private String orderType;// 课程学习各菜单中排序方式
	
	private String invoiceType;//发票类型
	private String orders;
	private String seq;
	private String remark;//发票冲红原因
	private String merge_order;
	private String paramter ;
	private List<PeBzzPici> piciList ;//开放式考试

	

	public List<PeBzzPici> getPiciList() {
		return piciList;
	}

	public void setPiciList(List<PeBzzPici> piciList) {
		this.piciList = piciList;
	}

	public String getMerge_order() {
		return merge_order;
	}

	public void setMerge_order(String merge_order) {
		this.merge_order = merge_order;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}

	public SsoUserService getSsoUserService() {
		return ssoUserService;
	}

	public void setSsoUserService(SsoUserService ssoUserService) {
		this.ssoUserService = ssoUserService;
	}

	public List getCourseDocList() {
		return courseDocList;
	}

	public void setCourseDocList(List courseDocList) {
		this.courseDocList = courseDocList;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public List getResourceIsOpen() {
		return resourceIsOpen;
	}

	public void setResourceIsOpen(List resourceIsOpen) {
		this.resourceIsOpen = resourceIsOpen;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		String filePath = "/UserFiles/File";
		InputStream is = ServletActionContext.getServletContext().getResourceAsStream(filePath + "/" + fileName);
		System.out.println(is);

		if (is == null) {
			is = new ByteArrayInputStream("文件不存在".getBytes());
		}
		return is;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public List<File> getFile() {
		return file;
	}

	public void setFile(List<File> file) {
		this.file = file;
	}

	public List<String> getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(List<String> fileFileName) {
		this.fileFileName = fileFileName;
	}

	public List<String> getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(List<String> fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getTagNames() {
		return tagNames;
	}

	public void setTagNames(String tagNames) {
		this.tagNames = tagNames;
	}

	public String getResetype() {
		return resetype;
	}

	public void setResetype(String resetype) {
		this.resetype = resetype;
	}

	public List<EnumConst> getResourceTypeList() {
		return resourceTypeList;
	}

	public void setResourceTypeList(List<EnumConst> resourceTypeList) {
		this.resourceTypeList = resourceTypeList;
	}

	public List getFileList() {
		return fileList;
	}

	public void setFileList(List fileList) {
		this.fileList = fileList;
	}

	public String getChoosePassEmail() {
		return choosePassEmail;
	}

	public void setChoosePassEmail(String choosePassEmail) {
		this.choosePassEmail = choosePassEmail;
	}

	public String getPassEmail() {
		return passEmail;
	}

	public void setPassEmail(String passEmail) {
		this.passEmail = passEmail;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public List getFlagResourceType() {
		return flagResourceType;
	}

	public void setFlagResourceType(List flagResourceType) {
		this.flagResourceType = flagResourceType;
	}

	public List getResourceTagList() {
		return resourceTagList;
	}

	public void setResourceTagList(List resourceTagList) {
		this.resourceTagList = resourceTagList;
	}

	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public String getYxkcxss() {
		return yxkcxss;
	}

	public void setYxkcxss(String yxkcxss) {
		this.yxkcxss = yxkcxss;
	}

	// Lee end

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List getCourseContentList() {
		return courseContentList;
	}

	public void setCourseContentList(List courseContentList) {
		this.courseContentList = courseContentList;
	}

	public String getCourseContent() {
		return courseContent;
	}

	public void setCourseContent(String courseContent) {
		this.courseContent = courseContent;
	}

	// Lee end

	// 课程列表检索使用
	private String courseType;// 课程类别id
	private String courseCategory;// 课程性质id
	private String classHour;// 课程学时
	private String classHour_all;
	private String paymentDate;
	private String electiveId;
	private String periodId;
	private List<PrBzzTchOpencourse> opencourseList;
	private List<PeBzzTchCourse> freeCourseList;

	private PeBzzOrderInfo peBzzOrderInfo;
	private PeBzzInvoiceInfo peBzzInvoiceInfo;
	private String selTitle;
	private String selSendName;
	private String selSendDate;
	private String flag;
	private String[] delId;
	private String unReadnum;
	private String unChooseCourseNum;// Lee 2013年12月26日 选课期数量

	private String peBulletinnum;// Lee 2013年12月26日 通知公告
	private BigDecimal balancenum = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);

	private String amount;// 订单金额
	private String batchId;// 专项id
	private int classHourRate;// 1学时兑换金钱数

	private StudentWorkspaceService studentWorkspaceService;

	private BigDecimal price = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP); // 课时价格

	private String id; // 用于退费申请的订单id

	private String merorderid; // 第三方支付完成后返回的订单号

	private String courseCode; // 20121128添加，用于选课单搜索

	private String tchprice;// 20140620 dgh 价格

	private String teacher;// 20140620 dgh 讲师

	private String time;// 20140620 dgh 学时

	private List<EnumConst> courseItemTypeList; // 20121128, 按大纲分类

	private String courseItemType; // 20121128, 大纲id

	private String courseName; // 课程名称

	private String seriseCourse; // 系列课程

	private String totalLearningHours;

	private String opids; // 课程报名传递过来的opencourse的id

	private String examStatus;

	private String paymentMethod;

	private String isInvoice;

	private EnumConstService enumConstService;

	private String batchName;

	private String totalhourbx;
	private String totalhourxx;
	private String totalprice;

	private String paymentDateStart; // 用于我的报名历史查询开始时间，时间段查询
	private String paymentDateEnd; // 用于我的报名历史查询结束时间，时间段查询
	private String selSendDateStart; // 用于站内信箱时间查询开始时间
	private String selSendDateEnd; // 用于站内信箱时间产寻结束时间
	private PeBzzRefundInfo peBzzRefundInfo;
	private PeBzzInvoiceInfo lastInvoiceInfo;
	private String paymentType;// 支付方式：null=宝易互通, 99bill=快钱

	/**
	 * ************************************* getter & setter Start
	 * **************************************************
	 */
	public PeBzzRefundInfo getPeBzzRefundInfo() {
		return peBzzRefundInfo;
	}

	public void setPeBzzRefundInfo(PeBzzRefundInfo peBzzRefundInfo) {
		this.peBzzRefundInfo = peBzzRefundInfo;
	}

	public PeBzzOrderInfo getPeBzzOrderInfo() {
		return peBzzOrderInfo;
	}

	public void setPeBzzOrderInfo(PeBzzOrderInfo peBzzOrderInfo) {
		this.peBzzOrderInfo = peBzzOrderInfo;
	}

	public PeBzzInvoiceInfo getPeBzzInvoiceInfo() {
		return peBzzInvoiceInfo;
	}

	public void setPeBzzInvoiceInfo(PeBzzInvoiceInfo peBzzInvoiceInfo) {
		this.peBzzInvoiceInfo = peBzzInvoiceInfo;
	}

	public StudentWorkspaceService getStudentWorkspaceService() {
		return studentWorkspaceService;
	}

	public void setStudentWorkspaceService(StudentWorkspaceService studentWorkspaceService) {
		this.studentWorkspaceService = studentWorkspaceService;
	}

	public String getPeriodId() {
		return periodId;
	}

	public void setPeriodId(String periodId) {
		this.periodId = periodId;
	}

	public String getSelTitle() {
		return selTitle;
	}

	public void setSelTitle(String selTitle) {
		this.selTitle = selTitle;
	}

	public String getSelSendName() {
		return selSendName;
	}

	public void setSelSendName(String selSendName) {
		this.selSendName = selSendName;
	}

	public String getSelSendDate() {
		return selSendDate;
	}

	public void setSelSendDate(String selSendDate) {
		this.selSendDate = selSendDate;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String[] getDelId() {
		return delId;
	}

	public void setDelId(String[] delId) {
		this.delId = delId;
	}

	public String getUnReadnum() {
		return unReadnum;
	}

	public void setUnReadnum(String unReadnum) {
		this.unReadnum = unReadnum;
	}

	public BigDecimal getBalancenum() {
		return balancenum;
	}

	public void setBalancenum(BigDecimal balancenum) {
		this.balancenum = balancenum;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public String getPassword_old() {
		return password_old;
	}

	public void setPassword_old(String password_old) {
		this.password_old = password_old;
	}

	public String getPassword_new() {
		return password_new;
	}

	public void setPassword_new(String password_new) {
		this.password_new = password_new;
	}

	public String getOpencourseid() {
		return opencourseid;
	}

	public void setOpencourseid(String opencourseid) {
		this.opencourseid = opencourseid;
	}

	public String getOpencoursename() {
		return opencoursename;
	}

	public void setOpencoursename(String opencoursename) {
		this.opencoursename = opencoursename;
	}

	public List getLearningCourses() {
		return learningCourses;
	}

	public void setLearningCourses(List learningCourses) {
		this.learningCourses = learningCourses;
	}

	public List getLearntCourses() {
		return learntCourses;
	}

	public void setLearntCourses(List learntCourses) {
		this.learntCourses = learntCourses;
	}

	public List getNotLearnCourses() {
		return notLearnCourses;
	}

	public void setNotLearnCourses(List notLearnCourses) {
		this.notLearnCourses = notLearnCourses;
	}

	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}

	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public List<PeBzzTeachingLiveroom> getLiveroomlist() {
		return liveroomlist;
	}

	public void setLiveroomlist(List<PeBzzTeachingLiveroom> liveroomlist) {
		this.liveroomlist = liveroomlist;
	}

	public List<PeBzzOnlineCourse> getPeBzzOnlineCourses() {
		return peBzzOnlineCourses;
	}

	public void setPeBzzOnlineCourses(List<PeBzzOnlineCourse> peBzzOnlineCourses) {
		this.peBzzOnlineCourses = peBzzOnlineCourses;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getLatereason() {
		return latereason;
	}

	public void setLatereason(String latereason) {
		this.latereason = latereason;
	}

	public List<PeVotePaper> getPeVotePaperList() {
		return peVotePaperList;
	}

	public void setPeVotePaperList(List<PeVotePaper> peVotePaperList) {
		this.peVotePaperList = peVotePaperList;
	}

	public List getApplyList() {
		return applyList;
	}

	public void setApplyList(List applyList) {
		this.applyList = applyList;
	}

	public List<PeBzzReplyInfo> getPeBzzReplyInfoList() {
		return peBzzReplyInfoList;
	}

	public void setPeBzzReplyInfoList(List<PeBzzReplyInfo> peBzzReplyInfoList) {
		this.peBzzReplyInfoList = peBzzReplyInfoList;
	}

	public List<PeElectiveCoursePeriod> getCpList() {
		return cpList;
	}

	public void setCpList(List<PeElectiveCoursePeriod> cpList) {
		this.cpList = cpList;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOperateresult() {
		return operateresult;
	}

	public void setOperateresult(String operateresult) {
		this.operateresult = operateresult;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List getCourseTypeList() {
		return courseTypeList;
	}

	public void setCourseTypeList(List courseTypeList) {
		this.courseTypeList = courseTypeList;
	}

	public List getCourseCategoryList() {
		return courseCategoryList;
	}

	public void setCourseCategoryList(List courseCategoryList) {
		this.courseCategoryList = courseCategoryList;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getCourseCategory() {
		return courseCategory;
	}

	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}

	public String getClassHour() {
		return classHour;
	}

	public void setClassHour(String classHour) {
		this.classHour = classHour;
	}

	public String getElectiveId() {
		return electiveId;
	}

	public void setElectiveId(String electiveId) {
		this.electiveId = electiveId;
	}

	public List<PrBzzTchOpencourse> getOpencourseList() {
		return opencourseList;
	}

	public void setOpencourseList(List<PrBzzTchOpencourse> opencourseList) {
		this.opencourseList = opencourseList;
	}

	public EnumConstService getEnumConstService() {
		return enumConstService;
	}

	public void setEnumConstService(EnumConstService enumConstService) {
		this.enumConstService = enumConstService;
	}

	public List<PeBzzTchCourse> getFreeCourseList() {
		return freeCourseList;
	}

	public void setFreeCourseList(List<PeBzzTchCourse> freeCourseList) {
		this.freeCourseList = freeCourseList;
	}

	public int getClassHourRate() {
		return classHourRate;
	}

	public void setClassHourRate(int classHourRate) {
		this.classHourRate = classHourRate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getMerorderid() {
		return merorderid;
	}

	public void setMerorderid(String merorderid) {
		this.merorderid = merorderid;
	}

	public String getSelSendDateStart() {
		return selSendDateStart;
	}

	public void setSelSendDateStart(String selSendDateStart) {
		this.selSendDateStart = selSendDateStart;
	}

	public String getSelSendDateEnd() {
		return selSendDateEnd;
	}

	public void setSelSendDateEnd(String selSendDateEnd) {
		this.selSendDateEnd = selSendDateEnd;
	}

	public String getPaymentDateStart() {
		return paymentDateStart;
	}

	public void setPaymentDateStart(String paymentDateStart) {
		this.paymentDateStart = paymentDateStart;
	}

	public String getPaymentDateEnd() {
		return paymentDateEnd;
	}

	public void setPaymentDateEnd(String paymentDateEnd) {
		this.paymentDateEnd = paymentDateEnd;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getOpids() {
		return opids;
	}

	public void setOpids(String opids) {
		this.opids = opids;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Date getDateString() {
		return dateString;
	}

	public void setDateString(Date dateString) {
		this.dateString = dateString;
	}

	public String getClassHour_all() {
		return classHour_all;
	}

	public void setClassHour_all(String classHour_all) {
		this.classHour_all = classHour_all;
	}

	public List<PrBzzTchStuElectiveBack> getElectiveBackList() {
		return electiveBackList;
	}

	public void setElectiveBackList(List<PrBzzTchStuElectiveBack> electiveBackList) {
		this.electiveBackList = electiveBackList;
	}

	public List getListSb() {
		return listSb;
	}

	public void setListSb(List<StudentBatch> listSb) {
		this.listSb = listSb;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(String isInvoice) {
		this.isInvoice = isInvoice;
	}

	public PeBzzBatch getPeBzzBatch() {
		return peBzzBatch;
	}

	public void setPeBzzBatch(PeBzzBatch peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public List getCourseItemTypeList() {
		return courseItemTypeList;
	}

	public void setCourseItemTypeList(List courseItemTypeList) {
		this.courseItemTypeList = courseItemTypeList;
	}

	public String getCourseItemType() {
		return courseItemType;
	}

	public void setCourseItemType(String courseItemType) {
		this.courseItemType = courseItemType;
	}

	/**
	 * ************************************* getter & setter End
	 * **************************************************
	 */

	@Override
	public void initGrid() {
	}

	@Override
	public void setEntityClass() {
	}

	@Override
	public void setServletPath() {
		this.servletPath = "/entity/workspaceStudent/studentWorkspace";
	}

	public void getClassHourPrice() {
		EnumConst ec = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");
		this.price = new BigDecimal(ec.getName()).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 初始化学员信息
	 * 
	 * @author linjie
	 */
	private void initStudent() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria peStudentDC = DetachedCriteria.forClass(PeBzzStudent.class);
		peStudentDC.createAlias("ssoUser", "ssoUser");
		peStudentDC.add(Restrictions.eq("ssoUser", ssoUser));
		List peStudentList = new ArrayList();
		try {
			peStudentList = this.getGeneralService().getList(peStudentDC);
			// 空判断zgl
			if (peStudentList != null && peStudentList.size() > 0) {
				this.setPeBzzStudent((PeBzzStudent) peStudentList.get(0));
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		userSession.setSsoUser(this.peBzzStudent.getSsoUser());
	}

	/**
	 * 跳转至首页
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toIndex() {
		this.initStudent();
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		String sql = "SELECT COUNT(*) NUM FROM PR_BZZ_TCH_STU_ELECTIVE A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID AND A.ISPASS <> '1' INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID AND C.FLAG_COURSEAREA != 'Coursearea0' LEFT JOIN TRAINING_COURSE_STUDENT D ON A.FK_TRAINING_ID = D.ID AND D.LEARN_STATUS <> 'COMPLETED' INNER JOIN ENUM_CONST EC4 ON C.FLAG_ISFREE = EC4.ID INNER JOIN PE_BZZ_STUDENT PBS ON A.FK_STU_ID = PBS.ID AND PBS.FK_SSO_USER_ID = '"
				+ ssoUser.getId() + "'";
		String num = "0";
		try {
			List zzxxList = this.getGeneralService().getBySQL(sql);
			if (zzxxList != null && zzxxList.size() > 0)
				num = zzxxList.get(0).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute("num", num);
		return "index";
	}

	/**
	 * 学生基本信息
	 * 
	 * @return String
	 * @author linjie
	 */
	public String viewInfo() {
		this.initStudent();
		return "viewInfo";
	}

	/**
	 * 跳转至学员修改页面
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toEdit() {
		this.initStudent();
		return "toEdit";
	}

	/**
	 * 修改学员信息
	 * 
	 * @return String
	 * @author linjie
	 */
	public String editexe() {

		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria peStudentDC = DetachedCriteria.forClass(PeBzzStudent.class);
		peStudentDC.add(Restrictions.eq("ssoUser", ssoUser));
		List peStudentList = new ArrayList();
		try {
			peStudentList = this.getGeneralService().getList(peStudentDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeBzzStudent instance = (PeBzzStudent) peStudentList.get(0);

		try {
			this.getGeneralService().save(instance);
			this.setOperateresult("学员信息修改成功。");
		} catch (EntityException e) {
			e.printStackTrace();
			this.setOperateresult("学员信息修改失败。");
		}
		this.setPeBzzStudent(instance);
		return "editexe";
	}

	/**
	 * 重新设置密码
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toPassword() {
		this.StudentInfo();
		return "toPassword";
	}

	/**
	 * 保存修改的新密码
	 * 
	 * @return String
	 * @author linjie
	 */
	public String passwordexe() {
		Map map = new HashMap();
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		DetachedCriteria peStudentDC = DetachedCriteria.forClass(PeBzzStudent.class);
		peStudentDC.createCriteria("ssoUser", "ssoUser");
		peStudentDC.add(Restrictions.eq("ssoUser.id", ssoUser.getId()));
		List peStudentList = new ArrayList();
		try {
			peStudentList = this.getGeneralService().getList(peStudentDC);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeBzzStudent instance = (PeBzzStudent) peStudentList.get(0);

		String email = instance.getEmail();
		if ((email == null || "".equals(email)) && (instance.getPassEmail() == null || "".equals(instance.getPassEmail()))) {
			this.setMsg("您的邮箱为空，邮箱可用于找回密码，请您先在修改信息页面中将邮箱完善再进行密码修改");
			this.setTogo("/entity/workspaceStudent/studentWorkspace_StudentInfo.action");
			return "stu_msg";
		}
		/*
		 * if
		 * (!instance.getSsoUser().getPassword().equals(this.getPassword_old())) {
		 * this.setOperateresult("原始密码不正确。"); return "editexe"; } else {
		 * instance.getSsoUser().setPassword(this.getPassword_new()); try {
		 * this.getGeneralService().save(instance);
		 * this.setOperateresult("修改密码成功。"); } catch (EntityException e) {
		 * e.printStackTrace(); this.setOperateresult("修改密码失败。"); }
		 * this.setPeStudent(instance); //
		 * System.out.println("this.getSsoid()"+this.getSsoid()); return
		 * "editexe"; }
		 */

		// 修改密码，修改后的密码加密2011-12-31
		// if(
		// (!instance.getSsoUser().getPasswordMd5().equals(MyUtil.md5(this.getPassword_old())))
		// &&
		if (!instance.getSsoUser().getPasswordBk().equals(MyUtil.md5(this.getPassword_old()))) {
			this.setOperateresult("1");
			return "editexe";
		} else {
			instance.getSsoUser().setPassword("字段弃用");
			instance.getSsoUser().setPasswordBk(MyUtil.md5(this.getPassword_new()));
			instance.getSsoUser().setPasswordMd5(MyUtil.md5(this.getPassword_new()));
			try {
				this.getGeneralService().save(instance);
				this.setOperateresult("2");
			} catch (EntityException e) {
				e.printStackTrace();
				this.setOperateresult("3");
			}
			this.setPeBzzStudent(instance);
			// System.out.println("this.getSsoid()"+this.getSsoid());
			return "editexe";
		}

	}

	/**
	 * 进入学员工作室的登陆界面
	 * 
	 * @return String
	 * @author linjie
	 */
	public String loginInfo() {
		this.initStudent();
		return "loginInfo";
	}

	/**
	 * 初始化检索中课程类别、性质列表
	 * 
	 * @author dgh
	 */
	public void initCouresTypeAndCourseTypeList() {

		try {
			this.courseTypeList = this.studentWorkspaceService.initCouresType("FlagCourseType");// 初始化课程性质

			this.courseCategoryList = this.studentWorkspaceService.initCourseCategory("FlagCourseCategory"); // 初始业务分类
			this.courseItemTypeList = this.studentWorkspaceService.initCourseItemType("FlagCourseItemType");// 初始大纲分类
			// Lee start 2014年3月12日 初始化内容属性分类列表
			this.courseContentList = this.studentWorkspaceService.initCourseContent("FlagContentProperty");// 初始按内容属性
			// Lee end
			// dgh 20140620 建议学习人群
			this.suggestrenList = this.studentWorkspaceService.initSuggestRen("FlagSuggest");// 初始按内容属性

			this.courseareaList = this.studentWorkspaceService.initCourseArea("FlagCoursearea");// 课程所属区域

		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化待选课程列表
	 * 
	 * @author dgh
	 */
	public void initUnelectivedCourses_old() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String opids = "";
		ServletActionContext.getRequest().getSession().setAttribute("endDate", this.endDate);// Lee
		// 2014年04月04日
		// 添加结束日期
		ServletActionContext.getRequest().getSession().setAttribute("periodId", this.periodId);// Lee
		// 2014年04月04日
		// 添加主键
		List opencourseIdList;// 选课单
		if (ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList") != null) {
			opencourseIdList = (List) ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList");
			for (int i = 0; i < opencourseIdList.size(); i++) {
				opids += "'" + opencourseIdList.get(i) + "',";
			}
		}
		if (opids.length() > 0) {
			opids = opids.substring(0, opids.length() - 1);
		}
		EnumConst enumConstByFlagOrderIsValid = this.getEnumConstService().getByNamespaceCode("FlagOrderIsValid", "1");
		StringBuffer sqlBuffer = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT PBTO.ID         AS ID, ");
		sql.append("        PBTC.ID         AS COURSEID, ");
		sql.append("        PBTC.CODE       AS CODE, ");
		sql.append("        PBTC.NAME       AS COURSENAME, ");
		sql.append("        EC1.NAME        AS COURSETYPENAME, ");
		sql.append("        EC2.NAME        AS CATEGORYNAME, ");
		sql.append("        PBTC.TEACHER    AS TEACHER, ");
		sql.append("        EC3.NAME        AS ITEMNAME, ");
		sql.append("        PBTC.TIME       AS TIME, ");
		sql.append("        EC4.NAME        AS COURSECONTENT, ");
		sql.append("        PBTC.STUDYDATES AS STUDYDATES, ");
		sql.append("        PBTC.PRICE      AS TCHPRICE, ");
		sql.append("        EC6.CODE        AS PRETEST, ");
		sql.append("        PBTC.PASSROLE        AS PASSROLE ");
		sql.append("   FROM PR_BZZ_TCH_OPENCOURSE PBTO ");
		sql.append("  INNER JOIN PE_BZZ_TCH_COURSE PBTC ");
		sql.append("     ON PBTO.FK_COURSE_ID = PBTC.ID ");
		sql.append("    AND PBTC.FLAG_OFFLINE = '22' ");
		sql.append("    AND PBTC.FLAG_COURSEAREA = 'Coursearea1' ");
		sql.append("  INNER JOIN ENUM_CONST VD ");
		sql.append("     ON PBTC.FLAG_ISVALID = VD.ID ");
		sql.append("    AND VD.NAMESPACE = 'FlagIsvalid' ");
		sql.append("    AND VD.CODE = '1' ");
		sql.append("  INNER JOIN ENUM_CONST EC1 ");
		sql.append("     ON PBTC.FLAG_COURSETYPE = EC1.ID ");
		sql.append("  INNER JOIN ENUM_CONST EC2 ");
		sql.append("     ON PBTC.FLAG_COURSECATEGORY = EC2.ID ");
		sql.append("  INNER JOIN ENUM_CONST EC3 ");
		sql.append("     ON PBTC.FLAG_COURSE_ITEM_TYPE = EC3.ID ");
		sql.append("   LEFT JOIN (SELECT WM_CONCAT(EC.NAME) NAMES, FK_COURSE_ID ");
		sql.append("                FROM PE_BZZ_TCH_COURSE_SUGGEST A ");
		sql.append("               INNER JOIN ENUM_CONST EC ");
		sql.append("                  ON A.FK_ENUM_CONST_ID = EC.ID ");
		sql.append("               GROUP BY FK_COURSE_ID, TABLE_NAME ");
		sql.append("              HAVING TABLE_NAME = 'PE_BZZ_TCH_COURSE') PE ");
		sql.append("     ON PBTO.FK_COURSE_ID = PE.FK_COURSE_ID ");
		sql.append("  INNER JOIN ENUM_CONST EC4 ");
		sql.append("     ON PBTC.FLAG_CONTENT_PROPERTY = EC4.ID ");
		sql.append("  INNER JOIN PE_BZZ_BATCH PBZ ");
		sql.append("     ON PBTO.FK_BATCH_ID = PBZ.ID ");
		sql.append("  INNER JOIN ENUM_CONST EC5 ");
		sql.append("     ON PBZ.FLAG_BATCH_TYPE = EC5.ID ");
		sql.append("    AND EC5.CODE = '1' ");
		sql.append("  LEFT JOIN ENUM_CONST EC6 ");
		sql.append("     ON PBTC.FLAG_PRE_TEST = EC6.ID ");
		sql.append("  WHERE PBTC.ID NOT IN ( ");
		// sql.append(" SELECT PBTO2.FK_COURSE_ID ");
		// sql.append(" FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		// sql.append(" INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO2 ");
		// sql.append(" ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO2.ID ");
		// sql.append(" WHERE PBTSE.FK_STU_ID = '" + this.peBzzStudent.getId() +
		// "' ");
		// sql.append(" AND PBTO2.ID IS NOT NULL ");
		// sql.append(" UNION ALL ");
		sql.append("                        SELECT PBTO3.FK_COURSE_ID ");
		sql.append("                          FROM PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSEB ");
		sql.append("                         INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO3 ");
		sql.append("                            ON PBTO3.ID = PBTSEB.FK_TCH_OPENCOURSE_ID ");
		sql.append("                         WHERE PBTSEB.FK_STU_ID = '" + this.peBzzStudent.getId() + "' ");
		sql.append("                           AND PBTO3.ID IS NOT NULL ");
		sql.append("                        UNION ALL ");
		sql.append("                        SELECT PBTO.ID ");
		sql.append("                          FROM PE_BZZ_TCH_COURSE PBTO ");
		sql.append("                         INNER JOIN ENUM_CONST EC2 ");
		sql.append("                            ON EC2.NAMESPACE = 'FlagIsFree' ");
		sql.append("                           AND EC2.CODE = '1' ");
		sql.append("                         WHERE PBTO.FLAG_ISFREE = EC2.ID ");
		sql.append("                           AND PBTO.ID IS NOT NULL) ");
		sql.append("    AND (PBTC.END_DATE > SYSDATE OR PBTC.END_DATE IS NULL) ");
		String sql2 = "	and PBTO.id not in (" + opids + ")";
		String sql3 = " order by PBTC.ANNOUNCE_DATE DESC";

		if (this.courseCategory != null && !"".equalsIgnoreCase(this.courseCategory)) {
			sqlBuffer.append(" and EC2.ID = '" + this.courseCategory + "'\n");
		}
		if (this.courseType != null && !"".equalsIgnoreCase(this.courseType)) {
			sqlBuffer.append(" and EC1.ID = '" + this.courseType + "'\n");
		}
		if (this.courseCode != null && !"".equalsIgnoreCase(this.courseCode)) {
			sqlBuffer.append(" and PBTC.CODE like '%" + this.courseCode + "%'\n");
		}
		if (this.courseName != null && !"".equalsIgnoreCase(this.courseName)) {
			sqlBuffer.append(" and PBTC.NAME like '%" + this.courseName + "%'\n");
		}
		if (this.courseItemType != null && !"".equalsIgnoreCase(this.courseItemType)) {
			sqlBuffer.append(" and PBTC.FLAG_COURSE_ITEM_TYPE = '" + this.courseItemType + "'\n");
		}
		// dgh 20140620 start
		if (this.tchprice != null && !"".equalsIgnoreCase(this.tchprice)) {
			sqlBuffer.append(" and PBTC.price = '" + this.tchprice + "'\n");
		}
		if (this.teacher != null && !"".equalsIgnoreCase(this.teacher)) {
			sqlBuffer.append(" and PBTC.teacher = '" + this.teacher + "'\n");
		}
		if (this.time != null && !"".equalsIgnoreCase(this.time)) {
			sqlBuffer.append(" and PBTC.time = '" + this.time + "'\n");
		}
		// 建议学习人群
		if (this.suggestren != null && !"".equalsIgnoreCase(this.suggestren)) {
			sqlBuffer.append(" and pe.names like '%" + this.suggestren + "%'\n");
		}
		// dgh end
		// Lee start 2014年03月12日 添加内容属性查询条件
		if (this.courseContent != null && !"".equalsIgnoreCase(this.courseContent)) {
			sqlBuffer.append(" and PBTC.FLAG_CONTENT_PROPERTY = '" + this.courseContent + "'\n");
		}
		if (seriseCourse != null && !"".equalsIgnoreCase(seriseCourse)) {
			sqlBuffer
					.append(" AND PBTC.ID IN (SELECT r.Pk_Course_Id FROM RECOMMEND_COURSE R WHERE r.Pk_Series_Id ='" + seriseCourse + "')");
		}
		// Lee end
		if (opids.length() > 0) {
			sql = sql.append(sql2 + sqlBuffer.toString() + sql3);
		} else {
			sql = sql.append(sqlBuffer.toString() + sql3);
		}
		try {
			this.page = this.getGeneralService().getByPageSQL(sql.toString(), pageSize, startIndex);
			// BigDecimal price = new
			// BigDecimal(this.getEnumConstService().getByNamespaceCode("ClassHourRate",
			// "0").getName()).setScale(2, BigDecimal.ROUND_HALF_UP);
			// ServletActionContext.getRequest().setAttribute("price", price);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化待选课程列表
	 * 
	 * @author dgh
	 */
	public void initUnelectivedCourses() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String opids = "";
		ServletActionContext.getRequest().getSession().setAttribute("endDate", this.endDate);// Lee
		// 2014年04月04日
		// 添加结束日期
		ServletActionContext.getRequest().getSession().setAttribute("periodId", this.periodId);// Lee
		// 2014年04月04日
		// 添加主键
		List opencourseIdList = null;// 选课单
		String shoppingSql = " select fk_opencourse_id  from shopping_course where fk_stu_id ='" + this.peBzzStudent.getId() + "' ";
		try {
			opencourseIdList = this.getGeneralService().getBySQL(shoppingSql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (opencourseIdList != null && opencourseIdList.size() > 0) {

			for (int i = 0; i < opencourseIdList.size(); i++) {
				opids += "'" + opencourseIdList.get(i) + "',";
			}
		}
		if (opids.length() > 0) {
			opids = opids.substring(0, opids.length() - 1);
		}
		EnumConst enumConstByFlagOrderIsValid = this.getEnumConstService().getByNamespaceCode("FlagOrderIsValid", "1");
		StringBuffer sqlBuffer = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT PBTO.ID         AS ID, ");
		sql.append("        PBTC.ID         AS COURSEID, ");
		sql.append("        PBTC.CODE       AS CODE, ");
		sql.append("        PBTC.NAME       AS COURSENAME, ");
		sql.append("        EC1.NAME        AS COURSETYPENAME, ");
		sql.append("        EC2.NAME        AS CATEGORYNAME, ");
		sql.append("        PBTC.TEACHER    AS TEACHER, ");
		sql.append("        EC3.NAME        AS ITEMNAME, ");
		sql.append("        PBTC.TIME       AS TIME, ");
		sql.append("        EC4.NAME        AS COURSECONTENT, ");
		sql.append("        PBTC.STUDYDATES AS STUDYDATES, ");
		sql.append("        PBTC.PRICE      AS TCHPRICE, ");
		sql.append("        EC6.CODE        AS PRETEST, ");
		sql.append("        PBTC.PASSROLE        AS PASSROLE ");
		sql.append("   FROM PR_BZZ_TCH_OPENCOURSE PBTO ");
		sql.append("  INNER JOIN PE_BZZ_TCH_COURSE PBTC ");
		sql.append("     ON PBTO.FK_COURSE_ID = PBTC.ID ");
		sql.append("    AND PBTC.FLAG_OFFLINE = '22' ");
		sql.append("    AND PBTC.FLAG_COURSEAREA = 'Coursearea1' ");
		sql.append("  INNER JOIN ENUM_CONST VD ");
		sql.append("     ON PBTC.FLAG_ISVALID = VD.ID ");
		sql.append("    AND VD.NAMESPACE = 'FlagIsvalid' ");
		sql.append("    AND VD.CODE = '1' ");
		sql.append("  INNER JOIN ENUM_CONST EC1 ");
		sql.append("     ON PBTC.FLAG_COURSETYPE = EC1.ID ");
		sql.append("  INNER JOIN ENUM_CONST EC2 ");
		sql.append("     ON PBTC.FLAG_COURSECATEGORY = EC2.ID ");
		sql.append("  INNER JOIN ENUM_CONST EC3 ");
		sql.append("     ON PBTC.FLAG_COURSE_ITEM_TYPE = EC3.ID ");
		sql.append("   LEFT JOIN (SELECT WM_CONCAT(EC.NAME) NAMES, FK_COURSE_ID ");
		sql.append("                FROM PE_BZZ_TCH_COURSE_SUGGEST A ");
		sql.append("               INNER JOIN ENUM_CONST EC ");
		sql.append("                  ON A.FK_ENUM_CONST_ID = EC.ID ");
		sql.append("               GROUP BY FK_COURSE_ID, TABLE_NAME ");
		sql.append("              HAVING TABLE_NAME = 'PE_BZZ_TCH_COURSE') PE ");
		sql.append("     ON PBTO.FK_COURSE_ID = PE.FK_COURSE_ID ");
		sql.append("  INNER JOIN ENUM_CONST EC4 ");
		sql.append("     ON PBTC.FLAG_CONTENT_PROPERTY = EC4.ID ");
		sql.append("  INNER JOIN PE_BZZ_BATCH PBZ ");
		sql.append("     ON PBTO.FK_BATCH_ID = PBZ.ID ");
		sql.append("  INNER JOIN ENUM_CONST EC5 ");
		sql.append("     ON PBZ.FLAG_BATCH_TYPE = EC5.ID ");
		sql.append("    AND EC5.CODE = '1' ");
		sql.append("  LEFT JOIN ENUM_CONST EC6 ");
		sql.append("     ON PBTC.FLAG_PRE_TEST = EC6.ID ");
		sql.append("  WHERE PBTC.ID NOT IN ( ");
		// sql.append(" SELECT PBTO2.FK_COURSE_ID ");
		// sql.append(" FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE ");
		// sql.append(" INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO2 ");
		// sql.append(" ON PBTSE.FK_TCH_OPENCOURSE_ID = PBTO2.ID ");
		// sql.append(" WHERE PBTSE.FK_STU_ID = '" + this.peBzzStudent.getId() +
		// "' ");
		// sql.append(" AND PBTO2.ID IS NOT NULL ");
		// sql.append(" UNION ALL ");
		sql.append("                        SELECT PBTO3.FK_COURSE_ID ");
		sql.append("                          FROM PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSEB ");
		sql.append("                         INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO3 ");
		sql.append("                            ON PBTO3.ID = PBTSEB.FK_TCH_OPENCOURSE_ID ");
		sql.append("                         WHERE PBTSEB.FK_STU_ID = '" + this.peBzzStudent.getId() + "' ");
		sql.append("                           AND PBTO3.ID IS NOT NULL ");
		sql.append("                        UNION ALL ");
		sql.append("                        SELECT PBTO.ID ");
		sql.append("                          FROM PE_BZZ_TCH_COURSE PBTO ");
		sql.append("                         INNER JOIN ENUM_CONST EC2 ");
		sql.append("                            ON EC2.NAMESPACE = 'FlagIsFree' ");
		sql.append("                           AND EC2.CODE = '1' ");
		sql.append("                         WHERE PBTO.FLAG_ISFREE = EC2.ID ");
		sql.append("                           AND PBTO.ID IS NOT NULL) ");
		// sql
		// .append(" AND (PBTC.END_DATE > SYSDATE OR PBTC.END_DATE IS NULL) ");
		String sql2 = "	and PBTO.id not in (" + opids + ")";
		String sql3 = " order by PBTC.ANNOUNCE_DATE DESC";

		if (this.courseCategory != null && !"".equalsIgnoreCase(this.courseCategory)) {
			sqlBuffer.append(" and EC2.ID = '" + this.courseCategory + "'\n");
		}
		if (this.courseType != null && !"".equalsIgnoreCase(this.courseType)) {
			sqlBuffer.append(" and EC1.ID = '" + this.courseType + "'\n");
		}
		if (this.courseCode != null && !"".equalsIgnoreCase(this.courseCode)) {
			sqlBuffer.append(" and PBTC.CODE like '%" + this.courseCode + "%'\n");
		}
		if (this.courseName != null && !"".equalsIgnoreCase(this.courseName)) {
			sqlBuffer.append(" and PBTC.NAME like '%" + this.courseName + "%'\n");
		}
		if (this.courseItemType != null && !"".equalsIgnoreCase(this.courseItemType)) {
			sqlBuffer.append(" and PBTC.FLAG_COURSE_ITEM_TYPE = '" + this.courseItemType + "'\n");
		}
		// dgh 20140620 start
		if (this.tchprice != null && !"".equalsIgnoreCase(this.tchprice)) {
			sqlBuffer.append(" and PBTC.price = '" + this.tchprice + "'\n");
		}
		if (this.teacher != null && !"".equalsIgnoreCase(this.teacher)) {
			sqlBuffer.append(" and PBTC.teacher = '" + this.teacher + "'\n");
		}
		if (this.time != null && !"".equalsIgnoreCase(this.time)) {
			sqlBuffer.append(" and PBTC.time = '" + this.time + "'\n");
		}
		// 建议学习人群
		if (this.suggestren != null && !"".equalsIgnoreCase(this.suggestren)) {
			sqlBuffer.append(" and pe.names like '%" + this.suggestren + "%'\n");
		}
		// dgh end
		// Lee start 2014年03月12日 添加内容属性查询条件
		if (this.courseContent != null && !"".equalsIgnoreCase(this.courseContent)) {
			sqlBuffer.append(" and PBTC.FLAG_CONTENT_PROPERTY = '" + this.courseContent + "'\n");
		}
		if (seriseCourse != null && !"".equalsIgnoreCase(seriseCourse)) {
			sqlBuffer
					.append(" AND PBTC.ID IN (SELECT r.Pk_Course_Id FROM RECOMMEND_COURSE R WHERE r.Pk_Series_Id ='" + seriseCourse + "')");
		}
		// Lee end
		if (opids.length() > 0) {
			sql = sql.append(sql2 + sqlBuffer.toString() + sql3);
		} else {
			sql = sql.append(sqlBuffer.toString() + sql3);
		}
		try {
			this.page = this.getGeneralService().getByPageSQL(sql.toString(), pageSize, startIndex);
			// BigDecimal price = new
			// BigDecimal(this.getEnumConstService().getByNamespaceCode("ClassHourRate",
			// "0").getName()).setScale(2, BigDecimal.ROUND_HALF_UP);
			// ServletActionContext.getRequest().setAttribute("price", price);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查看已选课程列表(专项)
	 * 
	 * @author dgh
	 */
	public void viewBitchSelectivedCourses() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		List opencourseIdList;// 选课单

		StringBuffer sqlBuffer = new StringBuffer();
		// String batchStateSql = "SELECT DISTINCT C.ID,EC.CODE FROM
		// PR_BZZ_TCH_STU_ELECTIVE_BACK A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON
		// A.FK_TCH_OPENCOURSE_ID = B.ID INNER JOIN PE_BZZ_ORDER_INFO C ON
		// A.FK_ORDER_ID = C.ID INNER JOIN ENUM_CONST EC ON C.FLAG_PAYMENT_STATE
		// = EC.ID WHERE B.FK_BATCH_ID = '"
		// + this.mybatchid + "' AND C.CREATE_USER = '" + us.getId() + "'";
		String batchStateSql = "SELECT D.ID, EC.CODE FROM STU_BATCH A INNER JOIN PE_BZZ_STUDENT B ON A.STU_ID = B.ID INNER JOIN SSO_USER C ON B.FK_SSO_USER_ID = C.ID INNER JOIN PE_BZZ_ORDER_INFO D ON A.FK_ORDER_ID = D.ID INNER JOIN ENUM_CONST EC ON D.FLAG_PAYMENT_STATE = EC.ID WHERE A.BATCH_ID = '"
				+ this.mybatchid + "' AND C.ID = '" + us.getId() + "'";
		List batchList = null;
		try {
			batchList = this.getGeneralService().getBySQL(batchStateSql);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql = "";
		if (batchList != null && batchList.size() > 0) {
			String eleTable = "PR_BZZ_TCH_STU_ELECTIVE";
			try {
				Object[] listArr = (Object[]) batchList.get(0);
				if ("0".equals(listArr[1]) || "2".equals(listArr[1])) {// 未到账、已退费
					eleTable += "_BACK";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			sql = " SELECT THIS_.ID AS ID, PEBZZTCHCO1_.ID AS COURSEID, PEBZZTCHCO1_.CODE AS CODE, PEBZZTCHCO1_.NAME AS COURSENAME, ENUMCONSTB2_.NAME AS COURSETYPENAME, ENUMCONSTB3_.NAME AS CATEGORYNAME, "
					+ " PEBZZTCHCO1_.TEACHER AS TEACHER, ENUMCONSTB4_.NAME AS ITEMNAME, PEBZZTCHCO1_.TIME AS TIME, ENUMCONSTB5_.NAME AS COURSECONTENT, PEBZZTCHCO1_.STUDYDATES AS STUDYDATES, PEBZZTCHCO1_.PRICE AS TCHPRICE, "
					+ " THIS_.FLAG_CHOOSE AS FC, THIS_.FK_BATCH_ID AS BID, NVL(PBTSEB.FK_ORDER_ID, 0) AS FOID,EC6.NAME EC6NAME FROM PR_BZZ_TCH_OPENCOURSE THIS_ inner join ENUM_CONST EC6 on this_.FLAG_CHOOSE = ec6.ID INNER JOIN PE_BZZ_BATCH PEBZZBATCH5_ "
					+ " ON THIS_.FK_BATCH_ID = PEBZZBATCH5_.ID INNER JOIN PE_BZZ_TCH_COURSE PEBZZTCHCO1_ ON THIS_.FK_COURSE_ID = PEBZZTCHCO1_.ID " // AND
					// PEBZZTCHCO1_.FLAG_OFFLINE
					// =
					// '22'
					// "//已选课程不需要判断是否下线是否有效
					+ " INNER JOIN ENUM_CONST ENUMCONSTB2_ ON PEBZZTCHCO1_.FLAG_COURSETYPE = ENUMCONSTB2_.ID INNER JOIN ENUM_CONST ENUMCONSTB3_ ON PEBZZTCHCO1_.FLAG_COURSECATEGORY = ENUMCONSTB3_.ID INNER JOIN ENUM_CONST ENUMCONSTB4_ "
					+ " ON PEBZZTCHCO1_.FLAG_COURSE_ITEM_TYPE = ENUMCONSTB4_.ID INNER JOIN ENUM_CONST ENUMCONSTB5_ ON PEBZZTCHCO1_.FLAG_CONTENT_PROPERTY = ENUMCONSTB5_.ID "
					// + " INNER JOIN ENUM_CONST VD ON PEBZZTCHCO1_.FLAG_ISVALID
					// = VD.ID AND VD.NAMESPACE = 'FlagIsvalid' AND VD.CODE =
					// '1' "//已选课程不需要判断是否下线是否有效
					+ " INNER JOIN "
					+ eleTable
					+ " PBTSEB ON THIS_.ID = PBTSEB.FK_TCH_OPENCOURSE_ID AND PBTSEB.FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '"
					+ us.getId() + "') AND THIS_.FK_BATCH_ID = '" + this.mybatchid + "'";
		} else {
			sql = "select this_.id as id,\n"
					+ "  pebzztchco1_.id as courseid,\n"
					+ "  pebzztchco1_.CODE as code,\n"
					+ "  pebzztchco1_.NAME as courseName,\n"
					+ "  enumconstb2_.NAME as courseTypeName,\n"
					+ "  enumconstb3_.NAME as categoryName,\n"
					+ "  pebzztchco1_.teacher as teacher, \n"
					+ "  enumconstb4_.NAME as itemName,\n"
					+ "  pebzztchco1_.TIME as time,\n"
					+ "  enumconstb5_.NAME AS courseContent,\n"
					+ "  pebzztchco1_.STUDYDATES as studyDates,pebzztchco1_.price as tchprice ,THIS_.FLAG_CHOOSE AS FC, THIS_.FK_BATCH_ID as bid , NVL(PBTSEB.FK_ORDER_ID, 0) AS FOID,EC6.NAME EC6NAME "
					+ "  from PR_BZZ_TCH_OPENCOURSE this_\n"
					+ "  inner join PE_BZZ_BATCH pebzzbatch5_ on this_.FK_BATCH_ID = pebzzbatch5_.ID \n"
					+ "  inner join PE_BZZ_TCH_COURSE pebzztchco1_ on this_.FK_COURSE_ID =pebzztchco1_.ID \n"
					// + " AND pebzztchco1_.FLAG_OFFLINE = '22'
					// "//查看已选不需要考虑是否下线等
					+ "  inner join ENUM_CONST enumconstb2_ on pebzztchco1_.FLAG_COURSETYPE =enumconstb2_.ID\n"
					+ "  inner join ENUM_CONST enumconstb3_ on pebzztchco1_.FLAG_COURSECATEGORY =enumconstb3_.ID\n"
					+ "  inner join ENUM_CONST enumconstb4_ on pebzztchco1_.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  inner join ENUM_CONST EC6 on this_.FLAG_CHOOSE = ec6.ID\n"
					+ "INNER JOIN ENUM_CONST enumconstb5_ ON pebzztchco1_.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					// + " inner join enum_const vd on pebzztchco1_.FLAG_ISVALID
					// = vd.id and vd.namespace='FlagIsvalid' and vd.code = '1'
					// "//查看已选不需要考虑是否下线等
					+ "   and  pebzztchco1_.ID not in   "
					+ " (select prbzztchop1_.FK_COURSE_ID as y0_   from PR_BZZ_TCH_STU_ELECTIVE this_    inner join PR_BZZ_TCH_OPENCOURSE prbzztchop1_ on this_.FK_TCH_OPENCOURSE_ID =   prbzztchop1_.ID"
					+ "   where this_.FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '"
					+ us.getId()
					+ "'))   "
					+ "  and pebzztchco1_.ID not in  "
					+ "   (select this_.ID as y0_    from PE_BZZ_TCH_COURSE this_  inner join enum_const ec2 on ec2.namespace = 'FlagIsFree'  and ec2.code = '1'    where this_.FLAG_ISFREE = ec2.id) "
					+ " INNER JOIN PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSEB " + " ON THIS_.ID = PBTSEB.FK_TCH_OPENCOURSE_ID "
					+ " AND PBTSEB.FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '" + us.getId() + "') "
					+ " AND THIS_.FK_BATCH_ID = '" + this.mybatchid + "'";
		}
		// + " where pebzztchco1_.id in\n"
		// + " (select pbto.fk_course_id from pr_bzz_tch_stu_elective_back
		// pbtseb inner
		// join pr_bzz_tch_opencourse pbto on pbto.id =
		// pbtseb.fk_tch_opencourse_id\n"
		// + " where pbtseb.fk_stu_id='" + us.getLoginId() + "'\n )" + " and
		// this_.FK_BATCH_ID='" + this.mybatchid + "'";

		if (this.courseCategory != null && !"".equalsIgnoreCase(this.courseCategory)) {
			sqlBuffer.append(" and enumconstb3_.ID = '" + this.courseCategory + "'\n");
		}
		if (this.courseType != null && !"".equalsIgnoreCase(this.courseType)) {
			sqlBuffer.append(" and enumconstb2_.ID = '" + this.courseType + "'\n");
		}
		if (this.courseCode != null && !"".equalsIgnoreCase(this.courseCode)) {
			sqlBuffer.append(" and pebzztchco1_.CODE like '%" + this.courseCode + "%'\n");
		}
		if (this.courseName != null && !"".equalsIgnoreCase(this.courseName)) {
			sqlBuffer.append(" and pebzztchco1_.NAME like '%" + this.courseName + "%'\n");
		}
		if (this.courseItemType != null && !"".equalsIgnoreCase(this.courseItemType)) {
			sqlBuffer.append(" and pebzztchco1_.FLAG_COURSE_ITEM_TYPE = '" + this.courseItemType + "'\n");
		}
		// dgh 20140620 start
		if (this.tchprice != null && !"".equalsIgnoreCase(this.tchprice)) {
			sqlBuffer.append(" and pebzztchco1_.price = '" + this.tchprice + "'\n");
		}
		if (this.teacher != null && !"".equalsIgnoreCase(this.teacher)) {
			sqlBuffer.append(" and pebzztchco1_.teacher = '" + this.teacher + "'\n");
		}
		if (this.time != null && !"".equalsIgnoreCase(this.time)) {
			sqlBuffer.append(" and pebzztchco1_.time = '" + this.time + "'\n");
		}
		// dgh end
		// Lee start 2014年03月12日 添加内容属性查询条件
		if (this.courseContent != null && !"".equalsIgnoreCase(this.courseContent)) {
			sqlBuffer.append(" and pebzztchco1_.FLAG_CONTENT_PROPERTY = '" + this.courseContent + "'\n");
		}
		sql = sql + sqlBuffer.toString() + " ORDER BY THIS_.FLAG_CHOOSE ,PEBZZTCHCO1_.CODE ASC";
		try {
			this.page = this.getGeneralService().getByPageSQL(sql, pageSize, startIndex);
			BigDecimal price = new BigDecimal(this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0").getName()).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			ServletActionContext.getRequest().setAttribute("price", price);
			ServletActionContext.getRequest().getSession().setAttribute("mybatchid", this.mybatchid);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化待选课程列表
	 * 
	 * @author dgh
	 */
	public void initBitchUnelectivedCourses() {
		StringBuffer sqlBuffer = new StringBuffer();
		String sql = "select this_.id as id,\n"
				+ "  pebzztchco1_.id as courseid,\n"
				+ "  pebzztchco1_.CODE as code,\n"
				+ "  pebzztchco1_.NAME as courseName,\n"
				+ "  enumconstb2_.NAME as courseTypeName,\n"
				+ "  enumconstb3_.NAME as categoryName,\n"
				+ "  pebzztchco1_.teacher as teacher, \n"
				+ "  enumconstb4_.NAME as itemName,\n"
				+ "  pebzztchco1_.TIME as time,\n"
				// Lee start 2014年3月14日 添加内容属性字段(查询)
				+ "  enumconstb5_.NAME AS courseContent,\n"
				// Lee end
				+ "  pebzztchco1_.STUDYDATES as studyDates,pebzztchco1_.price as tchprice"
				+ "  from PR_BZZ_TCH_OPENCOURSE this_\n"
				+ "  inner join PE_BZZ_BATCH pebzzbatch5_ on this_.FK_BATCH_ID = pebzzbatch5_.ID \n"
				+ "  inner join PE_BZZ_TCH_COURSE pebzztchco1_ on this_.FK_COURSE_ID =pebzztchco1_.ID \n"
				// Lee start 2014年1月24日
				+ " AND pebzztchco1_.FLAG_OFFLINE = '22' "
				// Lee end
				+ " inner join ENUM_CONST enumconstb2_ on pebzztchco1_.FLAG_COURSETYPE =enumconstb2_.ID\n"
				+ " inner join ENUM_CONST enumconstb3_ on pebzztchco1_.FLAG_COURSECATEGORY =enumconstb3_.ID\n"
				+ " inner join ENUM_CONST enumconstb4_ on pebzztchco1_.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
				// Lee start 2014年3月14日 添加内容属性字段(查询)
				+ "INNER JOIN ENUM_CONST enumconstb5_ ON pebzztchco1_.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
				// Lee end
				// + " inner join enum_const ec3 on ec3.code = '1' and
				// ec3.namespace = 'FlagBatchType'\n"
				+ " inner join enum_const vd on pebzztchco1_.FLAG_ISVALID = vd.id and vd.namespace='FlagIsvalid' and vd.code = '1' INNER JOIN ENUM_CONST EC ON PEBZZTCHCO1_.FLAG_OFFLINE = EC.ID AND EC.CODE = '0' "
				+ " where THIS_.FLAG_CHOOSE = 'choose0' "
				+ "  and pebzztchco1_.id not in\n"
				+ "       (select prbzztchop1_.FK_COURSE_ID as y0_ from PR_BZZ_TCH_STU_ELECTIVE this_\n"
				+ "         inner join PR_BZZ_TCH_OPENCOURSE prbzztchop1_  on this_.FK_TCH_OPENCOURSE_ID =  prbzztchop1_.ID where this_.FK_STU_ID = '"
				+ this.peBzzStudent.getId()
				+ "')\n"
				+ "  and pebzztchco1_.id not in\n"
				+ "      (select pbto.fk_course_id  from pr_bzz_tch_stu_elective_back pbtseb inner join pr_bzz_tch_opencourse pbto on pbto.id = pbtseb.fk_tch_opencourse_id\n"
				+ "        where pbtseb.fk_stu_id='"
				+ this.peBzzStudent.getId()
				+ "'\n )"
				+ "   and pebzztchco1_.ID not in\n"
				+ "       (select this_.ID as y0_ from PE_BZZ_TCH_COURSE this_ inner join enum_const ec2 on ec2.namespace = 'FlagIsFree' and ec2.code = '1' where this_.FLAG_ISFREE = ec2.id)\n"
				+ "   and (pebzztchco1_.end_date>sysdate or pebzztchco1_.end_date is null)\n" + "  and this_.FK_BATCH_ID='"
				+ this.mybatchid + "'";
		String sql2 = "	and this_.id not in (" + opids + ")";
		String sql3 = " order by pebzztchco1_.CODE desc";
		if (this.courseCategory != null && !"".equalsIgnoreCase(this.courseCategory)) {
			sqlBuffer.append(" and enumconstb3_.ID = '" + this.courseCategory + "'\n");
		}
		if (this.courseType != null && !"".equalsIgnoreCase(this.courseType)) {
			sqlBuffer.append(" and enumconstb2_.ID = '" + this.courseType + "'\n");
		}
		if (this.courseCode != null && !"".equalsIgnoreCase(this.courseCode)) {
			sqlBuffer.append(" and pebzztchco1_.CODE like '%" + this.courseCode + "%'\n");
		}
		if (this.courseName != null && !"".equalsIgnoreCase(this.courseName)) {
			sqlBuffer.append(" and pebzztchco1_.NAME like '%" + this.courseName + "%'\n");
		}
		if (this.courseItemType != null && !"".equalsIgnoreCase(this.courseItemType)) {
			sqlBuffer.append(" and pebzztchco1_.FLAG_COURSE_ITEM_TYPE = '" + this.courseItemType + "'\n");
		}
		// dgh 20140620 start
		if (this.tchprice != null && !"".equalsIgnoreCase(this.tchprice)) {
			sqlBuffer.append(" and pebzztchco1_.price = '" + this.tchprice + "'\n");
		}
		if (this.teacher != null && !"".equalsIgnoreCase(this.teacher)) {
			sqlBuffer.append(" and pebzztchco1_.teacher = '" + this.teacher + "'\n");
		}
		if (this.time != null && !"".equalsIgnoreCase(this.time)) {
			sqlBuffer.append(" and pebzztchco1_.time = '" + this.time + "'\n");
		}
		// }
		// dgh end
		// Lee start 2014年03月12日 添加内容属性查询条件
		if (this.courseContent != null && !"".equalsIgnoreCase(this.courseContent)) {
			sqlBuffer.append(" and pebzztchco1_.FLAG_CONTENT_PROPERTY = '" + this.courseContent + "'\n");
		}
		// Lee end
		sqlBuffer.append(sql3);
		if (null != opids && opids.length() > 0 && !"null".equals(opids)) {
			sql = sql + sql2 + sqlBuffer.toString();
		} else {
			sql = sql + sqlBuffer.toString();
		}
		try {
			this.page = this.getGeneralService().getByPageSQL(sql, pageSize, startIndex);
			BigDecimal price = new BigDecimal(this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0").getName()).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			ServletActionContext.getRequest().setAttribute("price", price);
			ServletActionContext.getRequest().getSession().setAttribute("mybatchid", this.mybatchid);
			ServletActionContext.getRequest().getSession().setAttribute("endDate", this.endDate);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取选课期已选学时数
	 * 
	 * @param peElectiveCoursePeriod
	 * @return BigDecimal
	 * @author linjie
	 */
	public BigDecimal getElectivedCourseTime(PeElectiveCoursePeriod peElectiveCoursePeriod) {

		/*
		 * DetachedCriteria dc =
		 * DetachedCriteria.forClass(PrBzzTchStuElective.class);
		 * dc.createCriteria
		 * ("prBzzTchOpencourse","prBzzTchOpencourse").createAlias
		 * ("peBzzTchCourse", "peBzzTchCourse"); dc.createAlias("peBzzStudent",
		 * "peBzzStudent"); dc.add(Restrictions.eq("peBzzStudent",
		 * this.getPeBzzStudent()));
		 * dc.add(Restrictions.eq("peElectiveCoursePeriod",
		 * peElectiveCoursePeriod)); BigDecimal totalClass = new BigDecimal(0);
		 * try { List<PrBzzTchStuElective> opList =
		 * this.getGeneralService().getList(dc); Iterator<PrBzzTchStuElective>
		 * it = opList.iterator(); while(it.hasNext()) { PrBzzTchStuElective ele =
		 * it.next(); String time =
		 * ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getTime()==null ?
		 * "0.0" : ele.getPrBzzTchOpencourse().getPeBzzTchCourse().getTime();
		 * //totalClass = new BigDecimal(time).setScale(2); totalClass =
		 * totalClass.add(new BigDecimal(time)); } } catch (EntityException e) { //
		 * TODO Auto-generated catch block e.printStackTrace(); }
		 */
		/**
		 * 查询选课期中现在已经选课的学时数
		 */
		String sql = "select nvl(sum(c.time), 0)\n" + "  from pr_bzz_tch_stu_elective_back ele\n"
				+ " inner join pr_bzz_tch_opencourse op on op.id = ele.fk_tch_opencourse_id\n"
				+ " inner join pe_bzz_tch_course c on c.id = op.fk_course_id\n" + " inner join pe_elective_course_period p on p.id =\n"
				+ "                                           ele.fk_ele_course_period_id\n" + " where p.id = '"
				+ peElectiveCoursePeriod.getId() + "'  and ele.fk_stu_id='" + this.peBzzStudent.getId() + "' ";
		List timeList = null;
		try {
			timeList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String totalTime = "";
		if (timeList.size() == 0) {
			totalTime = "0";
		} else {
			totalTime = timeList.get(0).toString();
		}
		BigDecimal bdTotalTime = null;
		if (totalTime != null) {
			if (!totalTime.equals("")) {
				bdTotalTime = new BigDecimal(totalTime);
			} else {
				bdTotalTime = new BigDecimal(0);
			}
		} else {
			bdTotalTime = new BigDecimal(0);
		}
		return bdTotalTime;

	}

	/**
	 * 查询已选课程金额
	 * 
	 * @param peElectiveCoursePeriod
	 * @return
	 */
	public BigDecimal getElectivedAmount(PeElectiveCoursePeriod peElectiveCoursePeriod) {
		String classMoney_sql = "SELECT SUM(C.PRICE) FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID INNER JOIN ENUM_CONST D ON C.FLAG_COURSECATEGORY = D.ID INNER JOIN PE_ELECTIVE_COURSE_PERIOD E ON A.FK_ELE_COURSE_PERIOD_ID = E.ID WHERE E.ID = '"
				+ this.periodId + "' AND A.FK_STU_ID = '" + this.peBzzStudent.getId() + "'";

		List money_res = null;
		BigDecimal selectAmount = null;
		try {
			money_res = this.getGeneralService().getBySQL(classMoney_sql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		String money_all_ = "0";
		if (money_res != null && !"".equals(money_res))
			money_all_ = money_res.get(0) + "";

		if ("null".equals(money_all_)) {
			selectAmount = new BigDecimal(0);
		} else {
			selectAmount = new BigDecimal(money_all_).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
		return selectAmount;
	}

	/**
	 * 公共课程报名列表
	 * 
	 * @return String
	 * @author dgh
	 */
	public String tonewcourse() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.peBzzStudent = this.studentWorkspaceService.initStudentInfo(ssoUser.getId());
		try {
			seriesList = this.getGeneralService().getBySQL(" SELECT t.ID id ,t.NAME name FROM RECOMMEND_SERIES t ");
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.initCouresTypeAndCourseTypeList();
		this.initUnelectivedCourses();
		this.orderOpenCourse();
		this.getNum();
		return "tonewcourse";
	}

	/**
	 * 统计已选课程学时数
	 * 
	 * @author linjie
	 */
	public void getNum() {
		// BigDecimal price = new
		// BigDecimal(this.getEnumConstService().getByNamespaceCode("ClassHourRate",
		// "0").getName()).setScale(2, BigDecimal.ROUND_HALF_UP);

		// BigDecimal price = new BigDecimal("20");
		BigDecimal xuanNum = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);

		BigDecimal biNum = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal t = new BigDecimal(0);
		BigDecimal totalMoney = new BigDecimal(0.0).setScale(2, BigDecimal.ROUND_HALF_UP);
		if (this.opencourseList != null && this.opencourseList.size() != 0) {
			Iterator<PrBzzTchOpencourse> it = this.opencourseList.iterator();
			while (it.hasNext()) {
				PrBzzTchOpencourse op = it.next();
				if (op.getPeBzzTchCourse().getEnumConstByFlagCourseType().getCode().equals("0")) {
					biNum = biNum.add(new BigDecimal(op.getPeBzzTchCourse().getTime() == null ? "0" : op.getPeBzzTchCourse().getTime()))
							.setScale(2, BigDecimal.ROUND_HALF_UP);
				} else if (op.getPeBzzTchCourse().getEnumConstByFlagCourseType().getCode().equals("1")) {
					xuanNum = xuanNum
							.add(new BigDecimal(op.getPeBzzTchCourse().getTime() == null ? "0" : op.getPeBzzTchCourse().getTime()))
							.setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				totalMoney = totalMoney.add(
						(new BigDecimal(op.getPeBzzTchCourse().getPrice() == null ? "0" : op.getPeBzzTchCourse().getPrice()))).setScale(2,
						BigDecimal.ROUND_HALF_UP);
			}
		}
		t = t.add(biNum).add(xuanNum).setScale(1, BigDecimal.ROUND_HALF_UP);
		ServletActionContext.getRequest().setAttribute("biNum", biNum.setScale(1, BigDecimal.ROUND_HALF_UP));
		ServletActionContext.getRequest().setAttribute("xuanNum", xuanNum.setScale(1, BigDecimal.ROUND_HALF_UP));
		ServletActionContext.getRequest().setAttribute("t", t.setScale(1, BigDecimal.ROUND_HALF_UP));
		ServletActionContext.getRequest().setAttribute("totalMoney", totalMoney);
	}

	/**
	 * 获取已选课程
	 * 
	 * @author linjie
	 */
	public void orderOpenCourse() {
		List opencourseIdList = null;
		if (ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList") != null) {
			opencourseIdList = (List) ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList");
		}
		if (opencourseIdList == null || opencourseIdList.size() < 1) {
			this.opencourseList = new ArrayList();
			return;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		dc.createCriteria("peBzzTchCourse", "peBzzTchCourse").createAlias("enumConstByFlagCourseType", "enumConstByFlagCourseType")
				.createAlias("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory");
		dc.add(Restrictions.in("id", opencourseIdList.toArray(new String[opencourseIdList.size()])));
		try {
			this.opencourseList = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加课程到选课单viewElectiveOrder
	 * 
	 * @return String
	 * @author linjie
	 */
	public String addCourseToOrder_old() {
		this.initStudent();
		Map map = new HashMap();
		List opencourseIdList;
		if (ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList") != null) {
			opencourseIdList = (List) ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList");
		} else {
			opencourseIdList = new ArrayList();
		}
		DetachedCriteria dceletived = DetachedCriteria.forClass(PrBzzTchStuElective.class);
		dceletived.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
		dceletived.createAlias("peBzzStudent", "peBzzStudent");
		dceletived.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
		dceletived.add(Restrictions.eq("prBzzTchOpencourse.id", this.electiveId));

		DetachedCriteria dceletiveback = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
		dceletiveback.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
		dceletiveback.createAlias("peBzzStudent", "peBzzStudent");
		dceletiveback.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
		dceletiveback.add(Restrictions.eq("prBzzTchOpencourse.id", this.electiveId));
		try {
			List tempList = this.getGeneralService().getList(dceletived);
			List tempBackList = this.getGeneralService().getList(dceletiveback);
			if (tempList != null && tempList.size() > 0) {
				map.put("success", "该课程已选");
			} else if (tempBackList != null && tempBackList.size() > 0) {
				map.put("success", "该课程已选");
			} else {
				opencourseIdList.add(this.electiveId);
				ServletActionContext.getRequest().getSession().setAttribute("opencourseIdList", opencourseIdList);
				map.put("success", "成功添加到选课单");
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return "json";
	}

	/**
	 * 添加课程到选课单viewElectiveOrder 把选课信息保存到数据库表中
	 * 
	 * @return String
	 * @author linjie
	 */
	public String addCourseToOrder() {
		this.initStudent();
		Map map = new HashMap();
		DetachedCriteria dceletived = DetachedCriteria.forClass(PrBzzTchStuElective.class);
		dceletived.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
		dceletived.createAlias("peBzzStudent", "peBzzStudent");
		dceletived.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
		dceletived.add(Restrictions.eq("prBzzTchOpencourse.id", this.electiveId));

		DetachedCriteria dceletiveback = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
		dceletiveback.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
		dceletiveback.createAlias("peBzzStudent", "peBzzStudent");
		dceletiveback.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
		dceletiveback.add(Restrictions.eq("prBzzTchOpencourse.id", this.electiveId));

		try {
			List tempList = this.getGeneralService().getList(dceletived);
			List tempBackList = this.getGeneralService().getList(dceletiveback);
			List list = this.getGeneralService().getBySQL(
					" select * from shopping_course where FK_OPENCOURSE_ID ='" + this.electiveId + "' and FK_STU_ID ='"
							+ this.getPeBzzStudent().getId() + "' and status ='0'  ");
			if (tempList != null && tempList.size() > 0) {
				map.put("success", "该课程已选");
			} else if (tempBackList != null && tempBackList.size() > 0) {
				map.put("success", "该课程已选");
			} else if (list != null && list.size() > 0) {
				map.put("success", "该课程已加入购物车");
			} else {
				// opencourseIdList.add(this.electiveId);
				// ServletActionContext.getRequest().getSession().setAttribute("opencourseIdList",
				// opencourseIdList);
				this.studentWorkspaceService.addHoppingCourse(this.peBzzStudent.getId(), this.electiveId);
				map.put("success", "成功添加到选课单");
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return "json";
	}

	public String goShoppingCourse() {
		HttpServletRequest request = ServletActionContext.getRequest();
		this.initStudent();
		Map map = new HashMap();
		List openList = null;
		List courseList = null;
		String price = "";
		String courseType = "";
		String courseId = request.getParameter("courseId");
		String courseSql = " select price ,flag_coursearea from pe_bzz_tch_course where id ='" + courseId + "' ";
		String openCourseSql = " select pbto.id  from pr_bzz_tch_opencourse pbto where pbto.fk_course_id ='" + courseId
				+ "' and pbto.fk_batch_id ='40288a7b394d676d01394dad824c003b' ";
		try {
			openList = this.getGeneralService().getBySQL(openCourseSql);
			courseList = this.getGeneralService().getBySQL(courseSql);
			if (courseList != null && courseList.size() > 0) {
				Object[] obj = (Object[]) courseList.get(0);
				price = obj[0].toString();
				courseType = obj[1].toString();
			}
			if (openList != null && openList.size() > 0) {
				this.electiveId = openList.get(0).toString();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		DetachedCriteria dceletived = DetachedCriteria.forClass(PrBzzTchStuElective.class);
		dceletived.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
		dceletived.createAlias("peBzzStudent", "peBzzStudent");
		dceletived.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
		dceletived.add(Restrictions.eq("prBzzTchOpencourse.id", this.electiveId));

		DetachedCriteria dceletiveback = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
		dceletiveback.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
		dceletiveback.createAlias("peBzzStudent", "peBzzStudent");
		dceletiveback.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
		dceletiveback.add(Restrictions.eq("prBzzTchOpencourse.id", this.electiveId));

		try {
			List tempList = this.getGeneralService().getList(dceletived);
			List tempBackList = this.getGeneralService().getList(dceletiveback);
			List list = this.getGeneralService().getBySQL(
					" select * from shopping_course where FK_OPENCOURSE_ID ='" + this.electiveId + "' and FK_STU_ID ='"
							+ this.getPeBzzStudent().getId() + "' and status ='0'  ");
			if (tempList != null && tempList.size() > 0) {
				map.put("success", "您已报名或已将该课程加入了选课单，请不要重复操作。");
			} else if (tempBackList != null && tempBackList.size() > 0) {
				map.put("success", "该课程已报名但未支付，请到【我的订单】页面完成支付");
			} else if (list != null && list.size() > 0) {
				map.put("success", "您已报名或已将该课程加入了选课单，请不要重复操作。");
			} else if (price != null && price.equals("0")) {
				map.put("success", "该课程是免费课程，请到免费公共课程中直接学习");
			} else if (courseType != null && courseType.equals("Coursearea0")) {
				map.put("success", "该课程为视频直播课程，请到【报名付费】-【在线直播报名】中完成报名操作");
			} else {
				// opencourseIdList.add(this.electiveId);
				// ServletActionContext.getRequest().getSession().setAttribute("opencourseIdList",
				// opencourseIdList);
				this.studentWorkspaceService.addHoppingCourse(this.peBzzStudent.getId(), this.electiveId);
				map.put("success", "您已将该课程加入了选课单，请到【公共课程报名】-【选课单】中查看并完成报名支付流程。");
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setJsonString(JsonUtil.toJSONString(map));
		return "json";
	}

	/**
	 * 添加课程到选课单viewElectiveOrder 把选课信息保存到数据库表中
	 * 
	 * @return String
	 * @author linjie
	 */
	public String addCourseToShopping(String courseId) {
		ServletActionContext.getRequest().getSession().setAttribute("courseId", null);
		this.initStudent();
		Map map = new HashMap();
		List<String> openList = null;
		String price = "";
		String courseType = "";
		String openCourseId = "";
		List courseList = null;
		//String courseSql = " select price from pe_bzz_tch_course where id ='" + courseId + "' ";
		String courseSql = " select price ,flag_coursearea from pe_bzz_tch_course where id ='" + courseId + "' ";
		String openCourseSql = " select pbto.id  from pr_bzz_tch_opencourse pbto where pbto.fk_course_id ='" + courseId
				+ "' and pbto.fk_batch_id ='40288a7b394d676d01394dad824c003b' ";
		try {
			openList = this.getGeneralService().getBySQL(openCourseSql);
			courseList =this.getGeneralService().getBySQL(courseSql);
			if (courseList != null && courseList.size() > 0) {
				Object[] obj = (Object[]) courseList.get(0);
				price = obj[0].toString();
				courseType = obj[1].toString();
			}
			if(courseType !=null && courseType.equals("Coursearea0")){
				return "ZBKC";
			}
			if (price != null && "0".equals(price)) {
				return "MFKC";
			}
			if (openList != null && openList.size() > 0) {
				openCourseId = openList.get(0).toString();
			} else {
				return "MYKC";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (openCourseId != null && !"".equals("openCourseId")) {
			DetachedCriteria dceletived = DetachedCriteria.forClass(PrBzzTchStuElective.class);
			dceletived.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
			dceletived.createAlias("peBzzStudent", "peBzzStudent");
			dceletived.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
			dceletived.add(Restrictions.eq("prBzzTchOpencourse.id", openCourseId));

			DetachedCriteria dceletiveback = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
			dceletiveback.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
			dceletiveback.createAlias("peBzzStudent", "peBzzStudent");
			dceletiveback.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
			dceletiveback.add(Restrictions.eq("prBzzTchOpencourse.id", openCourseId));

			try {
				List tempList = this.getGeneralService().getList(dceletived);
				List tempBackList = this.getGeneralService().getList(dceletiveback);
				List list = this.getGeneralService().getBySQL(
						" select * from shopping_course where FK_OPENCOURSE_ID ='" + openCourseId + "' and FK_STU_ID ='"
								+ this.getPeBzzStudent().getId() + "' and status ='0'  ");
				if (tempList != null && tempList.size() > 0) {
					return "BMKC";
				} else if (tempBackList != null && tempBackList.size() > 0) {
					return "BMWZFKC";
				} else if (list != null && list.size() > 0) {
					return "YXKC";
				} else {
					this.studentWorkspaceService.addHoppingCourse(this.peBzzStudent.getId(), openCourseId);
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}

		return "";
	}

	/**
	 * 从选课单中删除课程
	 * 
	 * @return String
	 * @author linjie
	 */
	public String delCourseFromOrder_old() {
		this.initStudent();
		List opencourseIdList;
		if (ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList") != null) {
			opencourseIdList = (List) ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList");
			for (int i = 0; i < opencourseIdList.size(); i++) {
				if (null != opencourseIdList.get(i) && null != this.electiveId) {
					if (this.electiveId.equalsIgnoreCase(opencourseIdList.get(i).toString())) {
						opencourseIdList.remove(i);
						break;
					}
				} else {
					this.setMsg("操作失败，请返回并刷新重试");
					return "msg";
				}
			}
			ServletActionContext.getRequest().getSession().setAttribute("opencourseIdList", opencourseIdList);
		}
		return this.viewElectiveOrder();
	}

	/**
	 * 从选课单中删除课程
	 * 
	 * @return String
	 * @author linjie
	 */
	public String delCourseFromOrder() {
		this.initStudent();
		dbpool db = new dbpool();
		MyResultSet rs = null;

		String sql = " delete from shopping_course where  FK_OPENCOURSE_ID ='" + this.electiveId + "' and FK_STU_ID ='"
				+ this.getPeBzzStudent().getId() + "' ";
		try {
			rs = db.executeQuery(sql);

		} catch (Exception e) {

		} finally {
			db.close(rs);
			db = null;
		}
		return this.viewElectiveOrder();
	}

	private BigDecimal getPriceOfCourse() {
		BigDecimal price = new BigDecimal(this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0").getName());
		return price.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 查看选课单
	 * 
	 * @return String
	 * @author linjie
	 */
	public String viewElectiveOrder_old() {
		this.initCouresTypeAndCourseTypeList();
		List opencourseIdList = (List) ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList");
		if (opencourseIdList != null && opencourseIdList.size() > 0) {
			// opencourseIdList =
			// (List)ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList");
		} else {
			return "viewElectiveOrder";
		}

		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		dc.createCriteria("peBzzTchCourse", "peBzzTchCourse").createAlias("enumConstByFlagCourseType", "enumConstByFlagCourseType")
				.createAlias("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory");
		dc.add(Restrictions.in("id", opencourseIdList));

		// 课程编号
		if (this.courseCode != null && !"".equalsIgnoreCase(this.courseCode)) {
			dc.add(Restrictions.like("peBzzTchCourse.code", this.courseCode, MatchMode.ANYWHERE).ignoreCase());
		}
		// 课程名称
		if (this.courseName != null && !"".equalsIgnoreCase(this.courseName)) {
			dc.add(Restrictions.like("peBzzTchCourse.name", this.courseName, MatchMode.ANYWHERE).ignoreCase());
		}
		// 课程学时
		if (this.time != null && !"".equalsIgnoreCase(this.time)) {
			dc.add(Restrictions.like("peBzzTchCourse.time", this.time, MatchMode.ANYWHERE).ignoreCase());
		}
		// 课程价格
		if (this.tchprice != null && !"".equalsIgnoreCase(this.tchprice)) {
			dc.add(Restrictions.like("peBzzTchCourse.price", this.tchprice, MatchMode.ANYWHERE).ignoreCase());
		}
		// 主讲人
		if (this.teacher != null && !"".equalsIgnoreCase(this.teacher)) {
			dc.add(Restrictions.like("peBzzTchCourse.teacher", this.teacher, MatchMode.ANYWHERE).ignoreCase());
		}
		// 大纲分类
		if (this.courseItemType != null && !"".equalsIgnoreCase(this.courseItemType)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCourseItemType.id", this.courseCategory));
		}
		// 业务分类
		if (this.courseCategory != null && !"".equalsIgnoreCase(this.courseCategory)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCourseCategory.id", this.courseCategory));
		}
		// 按内容属性
		if (this.courseContent != null && !"".equalsIgnoreCase(this.courseContent)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagContentProperty.id", this.courseContent));
		}
		// 按内容属性
		if (this.courseType != null && !"".equalsIgnoreCase(this.courseType)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCourseType.id", this.courseType));
		}
		dc.addOrder(Order.desc("peBzzTchCourse.code"));
		try {
			this.opencourseList = this.getGeneralService().getList(dc);
			BigDecimal price = this.getPriceOfCourse();
			ServletActionContext.getRequest().setAttribute("price", price);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.getNum();
		return "viewElectiveOrder";
	}

	/**
	 * 新查看选课单 ，不从session中取选课信息，直接从中间表 ***中去选课信息。
	 * 
	 * @return String
	 * @author linjie
	 */
	public String viewElectiveOrder() {
		Map map = new HashMap();
		this.initCouresTypeAndCourseTypeList();
		String courseId = (String) ServletActionContext.getRequest().getSession().getAttribute("courseId");
		this.initStudent();

		if (courseId != null && !"".equals("courseId")) {
			String str = this.addCourseToShopping(courseId);
			if (str !=null && "ZBKC".equals(str)){
				this.setMsg("该课程为视频直播课程，请到【报名付费】-【在线直播报名】中完成报名操作");
			}
			if (str != null && "MYKC".equals(str)) {
				this.setMsg("该课程不在公共课程报名中");
			}
			if (str != null && "MFKC".equals(str)) {
				this.setMsg("免费课程，请到免费公共课程报名中直接学习");
			}
			if (str != null && "YXKC".equals(str)) {
				this.setMsg("该课程已在选课单列表中");
			}
			if (str != null && "BMKC".equals(str)) {
				this.setMsg("该课程已报名");
			}
			if (str != null && "BMWZFKC".equals(str)) {
				this.setMsg("课程已报名但未支付，请到【我的订单】页面完成支付 ");
			}
		}

		StringBuffer sb = new StringBuffer();
		sb.append("      SELECT PBTO.ID                                                                   ");
		sb.append("      	      FROM PR_BZZ_TCH_OPENCOURSE PBTO                                         ");
		sb.append("                inner join pe_bzz_tch_course pbtc on pbto.fk_course_id = pbtc.id     ");
		sb.append("      	     INNER JOIN SHOPPING_COURSE SC                                            ");
		sb.append("      	        ON PBTO.ID = SC.FK_OPENCOURSE_ID                                      ");
		sb.append("      	       AND PBTO.ID NOT IN                                                     ");
		sb.append("      	           (SELECT PBTSE.FK_TCH_OPENCOURSE_ID                                 ");
		sb.append("      	              FROM (SELECT FK_TCH_OPENCOURSE_ID, FK_STU_ID                    ");
		sb.append("      	                      FROM PR_BZZ_TCH_STU_ELECTIVE                            ");
		sb.append("      	                    UNION ALL                                                 ");
		sb.append("      	                    SELECT FK_TCH_OPENCOURSE_ID, FK_STU_ID                    ");
		sb.append("      	                      FROM PR_BZZ_TCH_STU_ELECTIVE_BACK) PBTSE                ");
		sb.append("      	             INNER JOIN PE_BZZ_STUDENT PBS                                    ");
		sb.append("      	                ON PBTSE.FK_STU_ID = PBS.ID                                   ");
		sb.append("      	             WHERE PBS.id = '" + this.peBzzStudent.getId() + "'                ");
		sb.append("      	               AND PBTSE.FK_TCH_OPENCOURSE_ID IS NOT NULL)                    ");
		sb.append("      	     WHERE PBTC.FLAG_OFFLINE != '11'                                          ");
		sb.append("      	       AND PBTC.FLAG_ISVALID = '2'                                            ");
		sb.append("      	       AND SC.FK_STU_ID = '" + this.peBzzStudent.getId() + "'                  ");

		List opencourseIdList = null;
		try {
			opencourseIdList = this.getGeneralService().getBySQL(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (opencourseIdList == null || opencourseIdList.size() == 0) {
			return "viewElectiveOrder";
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		dc.createCriteria("peBzzTchCourse", "peBzzTchCourse").createAlias("enumConstByFlagCourseType", "enumConstByFlagCourseType")
				.createAlias("enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory");
		dc.add(Restrictions.in("id", opencourseIdList));
		dc.add(Restrictions.ne("peBzzTchCourse.enumConstByFlagOffline.id", "11"));

		// 课程编号
		if (this.courseCode != null && !"".equalsIgnoreCase(this.courseCode)) {
			dc.add(Restrictions.like("peBzzTchCourse.code", this.courseCode, MatchMode.ANYWHERE).ignoreCase());
		}
		// 课程名称
		if (this.courseName != null && !"".equalsIgnoreCase(this.courseName)) {
			dc.add(Restrictions.like("peBzzTchCourse.name", this.courseName, MatchMode.ANYWHERE).ignoreCase());
		}
		// 课程学时
		if (this.time != null && !"".equalsIgnoreCase(this.time)) {
			dc.add(Restrictions.like("peBzzTchCourse.time", this.time, MatchMode.ANYWHERE).ignoreCase());
		}
		// 课程价格
		if (this.tchprice != null && !"".equalsIgnoreCase(this.tchprice)) {
			dc.add(Restrictions.like("peBzzTchCourse.price", this.tchprice, MatchMode.ANYWHERE).ignoreCase());
		}
		// 主讲人
		if (this.teacher != null && !"".equalsIgnoreCase(this.teacher)) {
			dc.add(Restrictions.like("peBzzTchCourse.teacher", this.teacher, MatchMode.ANYWHERE).ignoreCase());
		}
		// 大纲分类
		if (this.courseItemType != null && !"".equalsIgnoreCase(this.courseItemType)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCourseItemType.id", this.courseCategory));
		}
		// 业务分类
		if (this.courseCategory != null && !"".equalsIgnoreCase(this.courseCategory)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCourseCategory.id", this.courseCategory));
		}
		// 按内容属性
		if (this.courseContent != null && !"".equalsIgnoreCase(this.courseContent)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagContentProperty.id", this.courseContent));
		}
		// 按内容属性
		if (this.courseType != null && !"".equalsIgnoreCase(this.courseType)) {
			dc.add(Restrictions.eq("peBzzTchCourse.enumConstByFlagCourseType.id", this.courseType));
		}
		dc.addOrder(Order.desc("peBzzTchCourse.code"));
		try {
			this.opencourseList = this.getGeneralService().getList(dc);
			BigDecimal price = this.getPriceOfCourse();
			ServletActionContext.getRequest().setAttribute("price", price);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.getNum();
		return "viewElectiveOrder";
	}

	/**
	 * 清除选课单
	 * 
	 * @author linjie
	 */
	public void destoryElectiveOrder() {
		if (ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("opencourseIdList");
		}
		this.destorySession();
	}

	/**
	 * 清除选课单
	 * 
	 * @author linjie
	 */
	public void destorySession() {
		if (ServletActionContext.getRequest().getSession().getAttribute("eleList") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("eleList");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("electiveBackList") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("electiveBackList");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("peBzzOrderInfo") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("peBzzOrderInfo");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("peBzzInvoiceInfo") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("peBzzInvoiceInfo");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("isInvoice") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("isInvoice");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("stuBatchList") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("stuBatchList");
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("peBzzBatch") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("peBzzBatch");
		}
	}

	/**
	 * 正在学习课程
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toLearningCourses() {

		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.peBzzStudent = this.studentWorkspaceService.initStudentInfo(ssoUser.getId());
		this.initCouresTypeAndCourseTypeList();
		try {
			this.page = this.studentWorkspaceService.initLearingCourse(this.peBzzStudent.getId(), courseCategory, courseType, courseCode,
					courseName, time, courseItemType, courseContent, suggestren, teacher, coursearea, orderInfo, orderType, pageSize,
					startIndex);
			List tongjiList = this.studentWorkspaceService.tongjiLearningCourse(this.peBzzStudent.getId(), courseCategory, courseType,
					courseCode, courseName, time, courseItemType, courseContent, suggestren, teacher, coursearea);
			BigDecimal totalNum = new BigDecimal("0.0");
			BigDecimal incomNum = new BigDecimal("0.0");
			BigDecimal uncomNum = new BigDecimal("0.0");
			if (tongjiList.size() > 0) {
				String[] tongjiArr = tongjiList.get(0).toString().split(",");
				totalNum = new BigDecimal(tongjiArr[0]).setScale(1, BigDecimal.ROUND_HALF_UP);
				incomNum = new BigDecimal(tongjiArr[1]).setScale(1, BigDecimal.ROUND_HALF_UP);
				uncomNum = new BigDecimal(tongjiArr[2]).setScale(1, BigDecimal.ROUND_HALF_UP);
			}
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("totalNum", totalNum);
			request.setAttribute("incomNum", incomNum);
			request.setAttribute("uncomNum", uncomNum);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			this.setM4Session(this.getPeBzzStudent(), "0");
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		return "learningCourses";
	}

	/**
	 * 已完成课程
	 * 
	 * @author Lee
	 */
	public String toCompletedCourses() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.peBzzStudent = this.studentWorkspaceService.initStudentInfo(ssoUser.getId());
		this.initCouresTypeAndCourseTypeList();
		try {
			this.page = this.studentWorkspaceService.initCompletedCourses(this.peBzzStudent.getId(), courseType, courseCode, courseName,
					time, teacher, coursearea, orderInfo, orderType, pageSize, startIndex);
			List list = this.studentWorkspaceService.tongjiCompletedCourse(this.peBzzStudent.getId(), courseType, courseCode, courseName,
					time, teacher, coursearea);
			BigDecimal bdList = new BigDecimal("0.0");
			if (list.size() > 0) {
				bdList = new BigDecimal(list.get(0).toString()).setScale(1, BigDecimal.ROUND_HALF_UP);
			}
			ServletActionContext.getRequest().setAttribute("list", bdList.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return "completedCourses";
	}

	/**
	 * 免费学习课程
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toFreeCourses() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = us.getSsoUser();
		this.peBzzStudent = this.studentWorkspaceService.initStudentInfo(ssoUser.getId());
		// this.initStudent();
		this.initCouresTypeAndCourseTypeList();

		try {
			seriesList = this.getGeneralService().getBySQL(" SELECT t.ID id ,t.NAME name FROM RECOMMEND_SERIES t ");

			this.page = this.studentWorkspaceService.initFreeCourse(ssoUser.getId(), courseCode, courseName, courseType, courseItemType,
					courseCategory, courseContent, time, teacher, suggestren, pageSize, startIndex, seriseCourse);
			List freeTimesList = this.studentWorkspaceService.tongjiFreeCourse(this.peBzzStudent.getId(), courseCode, courseName,
					courseType, courseItemType, courseCategory, courseContent, time, teacher, suggestren);
			BigDecimal num = new BigDecimal(0);
			if (null != freeTimesList && freeTimesList.size() > 0) {
				num = new BigDecimal(freeTimesList.get(0).toString()).setScale(1, BigDecimal.ROUND_HALF_UP);
			}
			this.classHour_all = num.setScale(1, BigDecimal.ROUND_HALF_UP).toString();
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		return "freeCourses";
	}

	/**
	 * 公司内训课程学习
	 * 
	 * @return String
	 * @author Lzh
	 */
	public String toInternal() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = us.getSsoUser();
		this.peBzzStudent = this.studentWorkspaceService.initStudentInfo(ssoUser.getId());
		this.initCouresTypeAndCourseTypeList();
		try {
			this.page = this.studentWorkspaceService.initInternal(ssoUser.getId(), courseCode, courseType, courseName, courseCategory,
					courseItemType, pageSize, startIndex, time, teacher, courseContent, suggestren, coursearea);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}

		return "internal";

	}

	public String toresourceList() {
		try {
			this.filelink = this.studentWorkspaceService.resourcefilelink(perid);
			this.resourceList = this.studentWorkspaceService.resourceList(perid);
			this.resourceListc = this.studentWorkspaceService.resourceListc(perid);
			HttpServletRequest request = ServletActionContext.getRequest();
			this.perid = request.getParameter("perid");
			this.kbianhao = request.getParameter("kbianhao");
			this.kname = request.getParameter("kname");
			try {
				this.page = this.studentWorkspaceService.initPeResourceList(kname, kbianhao, perid, pageSize, startIndex);
			} catch (EntityException e1) {
				e1.printStackTrace();
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "courseInfo";

	}

	/**
	 * 资料库
	 * 
	 * @return String
	 * @author Lzh
	 */

	public String toZiliao() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		uid = us.getId();
		this.initCouresTypeAndCourseTypeList();
		HttpServletRequest request = ServletActionContext.getRequest();
		String ktimestart = request.getParameter("ktimestart");
		String ktimeend = request.getParameter("ktimeend");
		String resourceTag_sql = "from EnumConst ec where ec.namespace='FlagResourceTag'";
		this.kbianhao = request.getParameter("kbianhao");
		this.kname = request.getParameter("kname");
		try {
			this.page = this.studentWorkspaceService.initZiLiaolist(ziliaoname, ziliaotypes, fabuunit, ktimestart, ktimeend, kname,
					kbianhao, tagIds, pageSize, startIndex);
			// 没声明新变量存储select列表值，使用的课程性质变量存储
			this.courseCategoryList = this.studentWorkspaceService.initEnumConst("FlagDocumentType");
			resourceTagList = this.getGeneralService().getByHQL(resourceTag_sql);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return "ziliao";
	}

	/**
	 * 资料库
	 * 
	 * @return String
	 * @author Lzh
	 */
	public String toPeResourcecourse() {
		String view_sql = "UPDATE PE_RESOURCE SET VIEWS = VIEWS + 1 WHERE ID = '" + perid + "'";

		HttpServletRequest request = ServletActionContext.getRequest();
		this.perid = request.getParameter("perid");
		this.kbianhao = request.getParameter("kbianhao");
		this.kname = request.getParameter("kname");
		try {
			this.getGeneralService().executeBySQL(view_sql);
			this.page = this.studentWorkspaceService.initPeResourceList(kname, kbianhao, perid, pageSize, startIndex);
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		return "toPeResourcecourse";
	}

	/**
	 * 学员上传资料 lwq 2016-02-29
	 * 
	 * @return
	 */
	public String toUploadResource() {
		String resourceTag_hql = "from EnumConst ec where ec.namespace = 'FlagResourceTag'";
		String resourceTypeHql = "from EnumConst ec where ec.namespace = 'FlagDocumentType' order by ec.code";
		String isOpen_hql = "from EnumConst ec where ec.namespace = 'FlagIsvalid' and ec.code != '2'";
		try {
			resourceTagList = this.getGeneralService().getByHQL(resourceTag_hql);
			resourceTypeList = this.getGeneralService().getByHQL(resourceTypeHql);
			resourceIsOpen = this.getGeneralService().getByHQL(isOpen_hql);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "toUploadResource";
	}

	public String uploadResource() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String hql = "from EnumConst ec where ec.id = '" + resetype + "'";
		String open_hql = "from EnumConst ec where ec.id = '" + isOpen + "'";
		EnumConst informationResource = this.getEnumConstService().getByNamespaceCode("InformationResource", "0");
		EnumConst isAudit = new EnumConst();
		EnumConst isOffLine = new EnumConst();
		String path = "/UserFiles/File";
		String realPath = ServletActionContext.getServletContext().getRealPath(path);
		String filePath = "";

		InputStream is = null;
		OutputStream os = null;
		try {
			// 文件上传
			if (file != null && !"".equals(file)) {
				for (int i = 0; i < file.size(); i++) {
					long ts = new Date().getTime();
					is = new FileInputStream(file.get(i));
					os = new FileOutputStream(new File(realPath, ts + "_" + fileFileName.get(i)));
					byte[] buffer = new byte[500];

					int length = 0;

					while (-1 != (length = is.read(buffer, 0, buffer.length))) {
						os.write(buffer);
					}
					filePath += path + "/" + ts + "_" + fileFileName.get(i) + ",";
				}
				filePath = filePath.substring(0, filePath.length() - 1);
			}

			Date reDate = null;
			EnumConst flagResourceType = (EnumConst) this.getGeneralService().getByHQL(hql).get(0);
			EnumConst flagIsOpen = (EnumConst) this.getGeneralService().getByHQL(open_hql).get(0);
			isAudit.setId("3");
			isOffLine.setId("3");
			if (("".equals(replyDate) || replyDate == null) && "0".equals(flagIsOpen.getCode())) {
				reDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			} else if (!"".equals(replyDate) && replyDate != null) {
				reDate = new SimpleDateFormat("yyyy-MM-dd").parse(replyDate);
			}

			PeResource pr = new PeResource();
			pr.setName(resourceName);
			pr.setResetype(flagResourceType.getName());
			// pr.setResourceTagIds(tagIds);
			// pr.setResourceTagNames(tagNames);
			pr.setDescribe(describe);
			pr.setEnumConstByFlagResourceType(flagResourceType);
			// 截取多个上传附件路径并以","分隔
			// int startIdx = content.toLowerCase().indexOf("<");
			// int endIdx = content.toLowerCase().lastIndexOf(">");
			// String fileLink = content.substring(startIdx, endIdx +
			// 1).replaceAll("<a href=\"", "").replaceAll("\\\">(.*?)\\/U",
			// ",/U").replaceAll("\\\">(.*?)\\</a>", "");
			pr.setFilelink(filePath);
			pr.setCreater(us.getId());
			pr.setCreationdate(new Date());
			pr.setFabuunit(fabuunit);
			pr.setReplyDate(reDate);

			pr.setEnumConstByFlagIsvalid(isAudit);
			pr.setEnumConstByFlagIsvalidSPXiaXian(isOffLine);
			pr.setEnumConstByFlagIsvalidSPshouye(isOffLine);
			pr.setEnumConstByFlagIsvalidSPxianzai(isOffLine);
			pr.setEnumConstByFlagIsAudit(isAudit);
			pr.setEnumConstByFlagIsOpen(flagIsOpen);

			pr.setEnumConstByInformationResource(informationResource);// 资料来源

			pr.setViews(0);
			this.getGeneralService().save(pr);
			this.setMsg("提交成功，公开资料需经协会管理员审核后方能发布。您可在\"我的资料\"中查看资料的审核状态!");
			this.setTogo("/entity/workspaceStudent/studentWorkspace_myResource.action");
		} catch (EntityException e) {
			this.setMsg("添加失败,请重新添加!");
			this.setTogo("back");
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
			this.setMsg("添加失败,请重新添加!");
			this.setTogo("back");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			this.setMsg("添加失败,请重新添加!");
			this.setTogo("back");
		} catch (IOException e) {
			e.printStackTrace();
			this.setMsg("添加失败,请重新添加!");
			this.setTogo("back");
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "stu_msg";
	}

	@Override
	public void addActionError(String anErrorMessage) {
		System.out.println("===========" + anErrorMessage);
		if (anErrorMessage.startsWith("the request was rejected because its size")) {
			super.addActionError(getText("struts.messages.error.file.too.large"));
		} else {
			super.addActionError(getText("struts.messages.error.content.type.not.allowed"));
		}
	}

	public String myResource() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		uid = us.getId();
		HttpServletRequest request = ServletActionContext.getRequest();
		String ktimestart = request.getParameter("ktimestart");
		String ktimeend = request.getParameter("ktimeend");
		String resourceTag_sql = "from EnumConst ec where ec.namespace='FlagDocumentType'";
		this.kbianhao = request.getParameter("kbianhao");
		this.kname = request.getParameter("kname");
		try {
			this.page = this.studentWorkspaceService.getMyResource(ziliaoname, ziliaotypes, fabuunit, ktimestart, ktimeend, kname, tagIds,
					pageSize, startIndex);
			resourceTagList = this.getGeneralService().getByHQL(resourceTag_sql);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return "showResource";
	}

	public String cancleAudit() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String sql = "DELETE FROM SC_RESOURCE SR WHERE SR.USERID = '" + us.getId() + "' AND SR.RESOURCEID = '" + perid + "' AND TYPE = '1'";
		try {
			this.getGeneralService().executeBySQL(sql);
			this.setMsg("删除收藏资料成功!");
			this.setTogo("/entity/workspaceStudent/studentWorkspace_myResource.action");
		} catch (EntityException e) {
			this.setMsg("删除收藏资料失败，请检查后重新操作!");
			this.setTogo("/entity/workspaceStudent/studentWorkspace_myResource.action");
			e.printStackTrace();
		}
		return "stu_msg";
	}

	public String deleteResource() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String checkSql = "SELECT EC.NAME FROM PE_RESOURCE PR JOIN ENUM_CONST EC ON PR.FLAG_ISAUDIT = EC.ID WHERE PR.ID = '" + perid + "'";
		List list;
		try {
			list = this.getGeneralService().getBySQL(checkSql);
			if(list != null && list.size() > 0 && ((String)list.get(0)).equals("否")) {
				String sql = "DELETE FROM PE_RESOURCE PR WHERE PR.ID = '" + perid + "'";
				this.getGeneralService().executeBySQL(sql);
				this.setMsg("删除资料成功!");
				this.setTogo("/entity/workspaceStudent/studentWorkspace_myResource.action");
			} else {
				this.setMsg("删除资料失败!");
				this.setTogo("/entity/workspaceStudent/studentWorkspace_myResource.action");
			}
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("删除收藏资料失败，请检查后重新操作!");
			this.setTogo("/entity/workspaceStudent/studentWorkspace_myResource.action");
		}
		return "stu_msg";
	}

	/**
	 * 学员端选课期报名 选课期已选课程列表
	 * 
	 * @return String
	 * @author linjie
	 */
	public String selectcourselist() {
		this.initStudent();
		this.getClassHourPrice();
		ServletActionContext.getRequest().getSession().setAttribute("periodId", this.periodId);
		// String endDate_sql = "SELECT END_DATE FROM ... WHERE ID = '" +
		// this.periodId + "'";
		// try {
		// List res_ = this.getGeneralService().getBySQL(endDate_sql);
		// if (res_ != null)
		// if (res_.get(0) != null)
		// this.endDate = (String) res_.get(0);
		// } catch (Exception e) {
		// System.out.println(e);
		// }
		ServletActionContext.getRequest().getSession().setAttribute("endDate", this.endDate);// Lee
		// 2014年04月04日
		// 添加结束日期
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
		dc.createCriteria("peElectiveCoursePeriod", "peElectiveCoursePeriod");
		dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
		dc.createCriteria("prBzzTchOpencourse.peBzzTchCourse", "pc");
		dc.createCriteria("pc.enumConstByFlagCourseCategory", "enumConstByFlagCourseCategory");
		// dc.createCriteria("peElectiveCoursePeriod.enumConstByFlagElePeriodPayStatus",
		// "enumConstByFlagElePeriodPayStatus");
		dc.add(Restrictions.eq("peElectiveCoursePeriod.id", this.periodId));
		dc.add(Restrictions.eq("peBzzStudent", this.peBzzStudent));
		try {
			page = this.getGeneralService().getByPage(dc, pageSize, startIndex);
			DetachedCriteria perDc = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
			perDc.add(Restrictions.eq("id", this.periodId));
			perDc.createCriteria("enumConstByFlagElePeriodPayStatus", "enumConstByFlagElePeriodPayStatus", DetachedCriteria.LEFT_JOIN);
			PeElectiveCoursePeriod per = (PeElectiveCoursePeriod) this.getGeneralService().getList(perDc).get(0);
			if (per.getBeginDatetime().before(new Date()) && per.getEndDatetime().after(new Date())
					&& per.getEnumConstByFlagElePeriodPayStatus().getCode().equals("0")) {
				ServletActionContext.getRequest().setAttribute("isRightDate", 1);
			} else {
				ServletActionContext.getRequest().setAttribute("isRightDate", 0);
			}
			BigDecimal num = new BigDecimal(0);
			List list = page.getItems();
			for (int i = 0; i < list.size(); i++) {
				PrBzzTchStuElectiveBack e = (PrBzzTchStuElectiveBack) list.get(i);
				PeBzzTchCourse pc = e.getPrBzzTchOpencourse().getPeBzzTchCourse();
				num = num.add(new BigDecimal(pc.getTime()));
			}
			String classSum_sql = "SELECT SUM(C.TIME) FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID INNER JOIN ENUM_CONST D ON C.FLAG_COURSECATEGORY = D.ID INNER JOIN PE_ELECTIVE_COURSE_PERIOD E ON A.FK_ELE_COURSE_PERIOD_ID = E.ID WHERE E.ID = '"
					+ this.periodId + "' AND A.FK_STU_ID = '" + this.peBzzStudent.getId() + "'";

			String classMoney_sql = "SELECT SUM(C.PRICE) FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID INNER JOIN ENUM_CONST D ON C.FLAG_COURSECATEGORY = D.ID INNER JOIN PE_ELECTIVE_COURSE_PERIOD E ON A.FK_ELE_COURSE_PERIOD_ID = E.ID WHERE E.ID = '"
					+ this.periodId + "' AND A.FK_STU_ID = '" + this.peBzzStudent.getId() + "'";

			int endnum = startIndex + pageSize;
			String pageClassSum_sql = "SELECT sum(TIME) FROM (SELECT INFO.TIME,ROWNUM RNUM FROM (SELECT C.TIME FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID INNER JOIN ENUM_CONST D ON C.FLAG_COURSECATEGORY = D.ID INNER JOIN PE_ELECTIVE_COURSE_PERIOD E ON A.FK_ELE_COURSE_PERIOD_ID = E.ID WHERE E.ID = '"
					+ this.periodId
					+ "' AND A.FK_STU_ID = '"
					+ this.peBzzStudent.getId()
					+ "' ) INFO WHERE ROWNUM <= "
					+ endnum
					+ ") WHERE RNUM >  " + startIndex + "";

			String pageMoneySum_sql = "SELECT sum(PRICE) FROM (SELECT INFO.PRICE,ROWNUM RNUM FROM (SELECT C.PRICE FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID INNER JOIN ENUM_CONST D ON C.FLAG_COURSECATEGORY = D.ID INNER JOIN PE_ELECTIVE_COURSE_PERIOD E ON A.FK_ELE_COURSE_PERIOD_ID = E.ID WHERE E.ID = '"
					+ this.periodId
					+ "' AND A.FK_STU_ID = '"
					+ this.peBzzStudent.getId()
					+ "' ) INFO WHERE ROWNUM <= "
					+ endnum
					+ ") WHERE RNUM >  " + startIndex + "";

			String c_sql = "SELECT SUM(C.TIME) " + "FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A " + "INNER JOIN PR_BZZ_TCH_OPENCOURSE B "
					+ "ON A.FK_TCH_OPENCOURSE_ID = B.ID " + "INNER JOIN PE_BZZ_TCH_COURSE C " + "ON B.FK_COURSE_ID = C.ID "
					+ "INNER JOIN ENUM_CONST D " + "ON C.FLAG_COURSECATEGORY = D.ID " + "INNER JOIN PE_ELECTIVE_COURSE_PERIOD E "
					+ "ON A.FK_ELE_COURSE_PERIOD_ID = E.ID " + "INNER JOIN ENUM_CONST F " + "ON C.FLAG_COURSETYPE = F.ID "
					+ "WHERE E.ID = '"
					+ this.periodId
					+ "' AND A.FK_STU_ID = '"
					+ this.peBzzStudent.getId()
					+ "' AND F.CODE = '0' "
					+ "UNION ALL "
					// 学时求和 zgl
					+ "SELECT SUM(C.TIME) "
					+ "FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A "
					+ "INNER JOIN PR_BZZ_TCH_OPENCOURSE B "
					+ "ON A.FK_TCH_OPENCOURSE_ID = B.ID "
					+ "INNER JOIN PE_BZZ_TCH_COURSE C "
					+ "ON B.FK_COURSE_ID = C.ID "
					+ "INNER JOIN ENUM_CONST D "
					+ "ON C.FLAG_COURSECATEGORY = D.ID "
					+ "INNER JOIN PE_ELECTIVE_COURSE_PERIOD E "
					+ "ON A.FK_ELE_COURSE_PERIOD_ID = E.ID "
					+ "INNER JOIN ENUM_CONST F "
					+ "ON C.FLAG_COURSETYPE = F.ID "
					+ "WHERE E.ID = '" + this.periodId + "' AND A.FK_STU_ID = '" + this.peBzzStudent.getId() + "' AND F.CODE = '1'";
			List res_ = this.getGeneralService().getBySQL(classSum_sql);
			List pageRes_ = this.getGeneralService().getBySQL(pageClassSum_sql);
			List pageMoney_ = this.getGeneralService().getBySQL(pageMoneySum_sql);
			List c_res = this.getGeneralService().getBySQL(c_sql);
			List money_res = this.getGeneralService().getBySQL(classMoney_sql);
			String classHourSum = "0";
			String bixiu = "0";
			String xuanxiu = "0";
			String pageClassHourSum = "0";
			String pageMon_ = "0";
			String money_all_ = "0";
			if (money_res != null && !"".equals(money_res))
				money_all_ = money_res.get(0) + "";
			if (pageRes_ != null && !"".equals(pageRes_))
				pageClassHourSum = pageRes_.get(0) + "";
			if (pageMoney_ != null && !"".equals(pageMoney_))
				pageMon_ = pageMoney_.get(0) + "";
			if (res_ != null && !"".equals(res_))
				classHourSum = res_.get(0) + "";
			if (c_res != null && !"".equals(c_res)) {
				bixiu = c_res.get(0) == null ? "0" : c_res.get(0).toString();
				xuanxiu = c_res.get(1) == null ? "0" : c_res.get(1).toString();
			}
			if ("null".equals(classHourSum))
				classHourSum = "0";
			if ("null".equals(bixiu))
				bixiu = "0";
			if ("null".equals(xuanxiu))
				xuanxiu = "0";

			if ("null".equals(pageClassHourSum)) {
				pageClassHourSum = "0";
			}
			if ("null".equals(money_all_)) {
				money_all_ = "0";
			}
			if ("null".equals(pageMon_)) {
				pageMon_ = "0";
			}
			Double pageChs = Double.parseDouble(pageClassHourSum);
			Double m_all = Double.parseDouble(money_all_);
			Double page_M = Double.parseDouble(pageMon_);

			ServletActionContext.getRequest().setAttribute("pageClassHourSum", pageChs);// 本页课成总学时
			ServletActionContext.getRequest().setAttribute("moneySum", m_all);// 本页课成总金额
			ServletActionContext.getRequest().setAttribute("pageMoneyHourSum", page_M);// 本页课成金额
			ServletActionContext.getRequest().setAttribute("classHourSum", classHourSum);// 已选课成总学时
			ServletActionContext.getRequest().setAttribute("bixiu", bixiu);// 必修课程学时总数
			ServletActionContext.getRequest().setAttribute("xuanxiu", xuanxiu);// 选修课程学时总数
			ServletActionContext.getRequest().setAttribute("endDate", this.endDate);// 选课期结束时间
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "selectcourselist";
	}

	/**
	 * 选课期待选课程列表
	 * 
	 * @return String
	 * @author linjie
	 */
	public String searchCourse() {
		this.initCouresTypeAndCourseTypeList();// Lee 2014年04月04日 初始化查询中的列表信息
		this.initStudent();
		this.initUnelectivedCourses();
		this.getClassHourPrice();
		this.initSelectCourseInfo();
		return "elecourse";
	}

	/**
	 * 增加选课期选课时课时信息提示
	 */
	public void initSelectCourseInfo() {
		try {
			DetachedCriteria perDc = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
			perDc.add(Restrictions.eq("id", this.periodId));
			PeElectiveCoursePeriod peElectiveCoursePeriod = (PeElectiveCoursePeriod) this.getGeneralService().getList(perDc).get(0);
			BigDecimal periodTime = new BigDecimal(peElectiveCoursePeriod.getStuTime() == null ? "0.0" : peElectiveCoursePeriod
					.getStuTime());
			BigDecimal totalHour = new BigDecimal(peElectiveCoursePeriod.getTotalhour() == null ? "0.0" : peElectiveCoursePeriod
					.getTotalhour());
			BigDecimal compulsoryHour = new BigDecimal(peElectiveCoursePeriod.getCompulsoryhour() == null ? "0.0" : peElectiveCoursePeriod
					.getCompulsoryhour());
			BigDecimal amountUpLine = new BigDecimal(peElectiveCoursePeriod.getAmountuplimit() == null ? "0.0" : peElectiveCoursePeriod
					.getAmountuplimit());
			String classSum_sql = "SELECT SUM(C.TIME) FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID INNER JOIN ENUM_CONST D ON C.FLAG_COURSECATEGORY = D.ID INNER JOIN PE_ELECTIVE_COURSE_PERIOD E ON A.FK_ELE_COURSE_PERIOD_ID = E.ID WHERE E.ID = '"
					+ this.periodId + "' AND A.FK_STU_ID = '" + this.peBzzStudent.getId() + "'";

			String classMoney_sql = "SELECT SUM(C.PRICE) FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A INNER JOIN PR_BZZ_TCH_OPENCOURSE B ON A.FK_TCH_OPENCOURSE_ID = B.ID INNER JOIN PE_BZZ_TCH_COURSE C ON B.FK_COURSE_ID = C.ID INNER JOIN ENUM_CONST D ON C.FLAG_COURSECATEGORY = D.ID INNER JOIN PE_ELECTIVE_COURSE_PERIOD E ON A.FK_ELE_COURSE_PERIOD_ID = E.ID WHERE E.ID = '"
					+ this.periodId + "' AND A.FK_STU_ID = '" + this.peBzzStudent.getId() + "'";

			String c_sql = "SELECT SUM(C.TIME) " + "FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A " + "INNER JOIN PR_BZZ_TCH_OPENCOURSE B "
					+ "ON A.FK_TCH_OPENCOURSE_ID = B.ID " + "INNER JOIN PE_BZZ_TCH_COURSE C " + "ON B.FK_COURSE_ID = C.ID "
					+ "INNER JOIN ENUM_CONST D " + "ON C.FLAG_COURSECATEGORY = D.ID " + "INNER JOIN PE_ELECTIVE_COURSE_PERIOD E "
					+ "ON A.FK_ELE_COURSE_PERIOD_ID = E.ID " + "INNER JOIN ENUM_CONST F " + "ON C.FLAG_COURSETYPE = F.ID "
					+ "WHERE E.ID = '"
					+ this.periodId
					+ "' AND A.FK_STU_ID = '"
					+ this.peBzzStudent.getId()
					+ "' AND F.CODE = '0' "
					+ "UNION ALL "
					+ "SELECT sum(c.time) "
					+ "FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A "
					+ "INNER JOIN PR_BZZ_TCH_OPENCOURSE B "
					+ "ON A.FK_TCH_OPENCOURSE_ID = B.ID "
					+ "INNER JOIN PE_BZZ_TCH_COURSE C "
					+ "ON B.FK_COURSE_ID = C.ID "
					+ "INNER JOIN ENUM_CONST D "
					+ "ON C.FLAG_COURSECATEGORY = D.ID "
					+ "INNER JOIN PE_ELECTIVE_COURSE_PERIOD E "
					+ "ON A.FK_ELE_COURSE_PERIOD_ID = E.ID "
					+ "INNER JOIN ENUM_CONST F "
					+ "ON C.FLAG_COURSETYPE = F.ID "
					+ "WHERE E.ID = '" + this.periodId + "' AND A.FK_STU_ID = '" + this.peBzzStudent.getId() + "' AND F.CODE = '1'";
			List res_ = this.getGeneralService().getBySQL(classSum_sql);
			List c_res = this.getGeneralService().getBySQL(c_sql);
			List money_res = this.getGeneralService().getBySQL(classMoney_sql);
			String classHourSum = "0";
			String bixiu = "0";
			String xuanxiu = "0";
			String money_all_ = "0";
			if (money_res != null && !"".equals(money_res))
				money_all_ = money_res.get(0) + "";
			if (res_ != null && !"".equals(res_))
				classHourSum = res_.get(0) + "";
			if (c_res != null && !"".equals(c_res)) {
				bixiu = c_res.get(0) + "";
				xuanxiu = c_res.get(1) + "";
			}

			if (c_res != null && !"".equals(c_res)) {
				bixiu = c_res.get(0) + "";
				xuanxiu = c_res.get(1) + "";
			}
			if ("null".equals(classHourSum))
				classHourSum = "0";
			if ("null".equals(bixiu))
				bixiu = "0";
			if ("null".equals(xuanxiu))
				xuanxiu = "0";

			if ("null".equals(money_all_)) {
				money_all_ = "0";
			}
			Double m_all = Double.parseDouble(money_all_);

			ServletActionContext.getRequest().setAttribute("moneySum", m_all);// 已选课程金额
			ServletActionContext.getRequest().setAttribute("classHourSum", classHourSum);// 已选课成总学时
			ServletActionContext.getRequest().setAttribute("bixiu", bixiu);// 必修课程学时总数
			ServletActionContext.getRequest().setAttribute("xuanxiu", xuanxiu);// 选修课程学时总数
			ServletActionContext.getRequest().setAttribute("periodTime", periodTime);// 学时数上线
			ServletActionContext.getRequest().setAttribute("totalHour", totalHour);// 学时数下线
			ServletActionContext.getRequest().setAttribute("compulsoryHour", compulsoryHour);// 必修课学时数下线
			ServletActionContext.getRequest().setAttribute("amountUpLine", amountUpLine);// 课程金额上线
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 专项培训选课
	 * 
	 * @return
	 */
	public String searchBatchCourse() {
		this.initCouresTypeAndCourseTypeList();// Lee 2014年04月04日 初始化查询中的列表信息
		this.initStudent();
		this.initBitchUnelectivedCourses();
		this.getClassHourPrice();
		return "batchcourse";
	}

	/**
	 * 专项培训查看选课
	 * 
	 * @return
	 */
	public String searchViewBatchCourse() {
		this.initCouresTypeAndCourseTypeList();// Lee 2014年04月04日 初始化查询中的列表信息
		this.initStudent();
		this.viewBitchSelectivedCourses();
		this.getClassHourPrice();
		return "viewbatchcourse";
	}

	public String batchPbtcs() {
		List batchPbtcList;// 专项课程列表(专项中的必选、自选课程列表，不去除学员已经报名的课程，仅显示用)
		Object batchid = ServletActionContext.getRequest().getParameter("mybatchid");
		batchid = this.mybatchid;
		batchid = ServletActionContext.getRequest().getSession().getAttribute("mybatchid");
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT PBTC.CODE, ");
		sb.append("        PBTC.NAME, ");
		sb.append("        EC1.NAME NAME1, ");
		sb.append("        EC2.NAME NAME2, ");
		sb.append("        EC3.NAME NAME3, ");
		sb.append("        EC4.NAME NAME4, ");
		sb.append("        PBTC.TEACHER, ");
		sb.append("        PBTC.TIME, ");
		sb.append("        PBTC.STUDYDATES, ");
		sb.append("        PBTC.PRICE, ");
		sb.append("        EC5.NAME NAME5, ");
		sb.append("        PBTC.ID ");
		sb.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
		sb.append("  INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ");
		sb.append("     ON PBTC.ID = PBTO.FK_COURSE_ID ");
		sb.append("  INNER JOIN PE_BZZ_BATCH PBB ");
		sb.append("     ON PBTO.FK_BATCH_ID = PBB.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC1 ");
		sb.append("     ON PBTC.FLAG_COURSETYPE = EC1.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC2 ");
		sb.append("     ON PBTC.FLAG_COURSECATEGORY = EC2.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC3 ");
		sb.append("     ON PBTC.FLAG_COURSE_ITEM_TYPE = EC3.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC4 ");
		sb.append("     ON PBTC.FLAG_CONTENT_PROPERTY = EC4.ID ");
		sb.append("  INNER JOIN ENUM_CONST EC5 ");
		sb.append("     ON PBTO.FLAG_CHOOSE = EC5.ID ");
		sb.append("  WHERE PBB.ID = '" + batchid + "' ORDER BY EC5.CODE ASC ");
		try {
			batchPbtcList = this.getGeneralService().getBySQL(sb.toString());
			ServletActionContext.getRequest().setAttribute("batchPbtcList", batchPbtcList);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "batchPbtcs";
	}

	/**
	 * 选课期选课
	 * 
	 * @return String
	 * @author linjie
	 */
	public String addCourseToPeriod() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		Map map = new HashMap();
		this.initStudent();
		EnumConst enumConstByFlagElectivePayStatus = this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus", "0");// 选课未支付状态

		HttpServletRequest request = ServletActionContext.getRequest();
		if (this.periodId == null || this.periodId.trim().equals("")) {
			this.periodId = (String) request.getSession().getAttribute("periodId");
			// String endDate_sql = "SELECT END_DATE FROM ... WHERE ID = '" +
			// this.periodId + "'";
			// try {
			// List res_ = this.getGeneralService().getBySQL(endDate_sql);
			// if (res_ != null)
			// if (res_.get(0) != null)
			// this.endDate = (String) res_.get(0);
			// } catch (Exception e) {
			// System.out.println(e);
			// }
			// this.endDate = (String) request.getSession()
			// .getAttribute("endDate");// Lee 选课期结束日期
		}
		String opId = request.getParameter("opId");
		DetachedCriteria perDc = DetachedCriteria.forClass(PeElectiveCoursePeriod.class);
		perDc.add(Restrictions.eq("id", this.periodId));
		DetachedCriteria opDc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		opDc.add(Restrictions.eq("id", opId));
		EnumConst enumConstByFlagOrderIsValid = this.getEnumConstService().getByNamespaceCode("FlagOrderIsValid", "1");
		DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		orderDc.add(Restrictions.eq("enumConstByFlagOrderIsValid", enumConstByFlagOrderIsValid));
		orderDc.add(Restrictions.eq("peElectiveCoursePeriod.id", this.periodId));
		try {
			List<PeBzzOrderInfo> orderList = this.getGeneralService().getList(orderDc);
			if (orderList != null && orderList.size() > 0) {
				// this.setMsg("管理员已经提交订单，此选课期课程不能再修改！");
				// this.setTogo("/entity/workspaceStudent/studentWorkspace_searchCourse.action");
				// return "stu_msg";
				// Lee start 由下代替
				// map.put("success", "管理员已经提交订单，此选课期课程不能再修改！");
				// this.setJsonString(JsonUtil.toJSONString(map));
				// return "json";
				// Lee end
				// Lee start
				this.setMsg("管理员已经提交订单，此选课期课程不能再修改！");
				this.setTogo("javascript:history.go(-1);");
				return "stu_msg";
				// Lee end
			}
			DetachedCriteria dceletived = DetachedCriteria.forClass(PrBzzTchStuElective.class);
			dceletived.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
			dceletived.createAlias("peBzzStudent", "peBzzStudent");
			dceletived.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
			dceletived.add(Restrictions.eq("prBzzTchOpencourse.id", opId));

			DetachedCriteria dceletiveback = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
			dceletiveback.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
			dceletiveback.createAlias("peBzzStudent", "peBzzStudent");
			dceletiveback.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
			dceletiveback.add(Restrictions.eq("prBzzTchOpencourse.id", opId));
			try {
				List tempList = this.getGeneralService().getList(dceletived);
				List tempBackList = this.getGeneralService().getList(dceletiveback);
				if (tempList != null && tempList.size() > 0) {
					// Lee start 由下代替
					// map.put("false", "该课程已选");
					// return "json";
					// Lee end
					// Lee start
					this.setMsg("该课程已选");
					this.setTogo("javascript:history.go(-1);");
					return "stu_msg";
					// Lee end
				} else if (tempBackList != null && tempBackList.size() > 0) {
					// Lee start 由下代替
					// map.put("false", "该课程已选");
					// return "json";
					// Lee end
					// Lee start
					this.setMsg("该课程已选");
					this.setTogo("javascript:history.go(-1);");
					return "stu_msg";
					// Lee end
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			PeElectiveCoursePeriod peElectiveCoursePeriod = (PeElectiveCoursePeriod) this.getGeneralService().getList(perDc).get(0);
			PrBzzTchOpencourse prBzzTchOpencourse = (PrBzzTchOpencourse) this.getGeneralService().getList(opDc).get(0);
			BigDecimal electivedTime = this.getElectivedCourseTime(peElectiveCoursePeriod).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal periodTime = new BigDecimal(peElectiveCoursePeriod.getStuTime() == null ? "0.0" : peElectiveCoursePeriod
					.getStuTime()).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal courseTime = new BigDecimal(prBzzTchOpencourse.getPeBzzTchCourse().getTime() == null ? "0.0" : prBzzTchOpencourse
					.getPeBzzTchCourse().getTime()).setScale(2, BigDecimal.ROUND_HALF_UP);

			if (electivedTime.add(courseTime).compareTo(periodTime) == 1) {
				// if((electivedTime+courseTime)>periodTime) {
				// this.setMsg("总课时数快满，剩余不足"+courseTime+"学时，请选择课时数较小的课程");
				// this.setTogo("/entity/workspaceStudent/studentWorkspace_searchCourse.action");
				// return "stu_msg";
				// Lee start 由下代替
				// map.put("success", "总课时数快满，剩余不足" + courseTime
				// + "学时，请选择课时数较小的课程");
				// this.setJsonString(JsonUtil.toJSONString(map));
				// return "json";
				// Lee end
				// Lee start
				// this.setMsg("总课时数快满，剩余不足" + courseTime + "学时，请选择课时数较小的课程");
				this.setMsg("所选课程学时数已超过选课期总学时上限，添加失败");
				this.setTogo("javascript:history.go(-1);");
				return "stu_msg";
				// Lee end
			}

			// 选课金额上线校验 lwq
			BigDecimal selectAmount = this.getElectivedAmount(peElectiveCoursePeriod).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal sumAmount = new BigDecimal(peElectiveCoursePeriod.getAmountuplimit() == null ? "0.0" : peElectiveCoursePeriod
					.getAmountuplimit()).setScale(1, BigDecimal.ROUND_HALF_UP);
			BigDecimal courseAmount = new BigDecimal(prBzzTchOpencourse.getPeBzzTchCourse().getPrice() == null ? "0.0" : prBzzTchOpencourse
					.getPeBzzTchCourse().getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
			if ((selectAmount.add(courseAmount)).compareTo(sumAmount) == 1) {
				// this.setMsg("总金额快满，剩余不足" + courseAmount + "元，请选择金额较小的课程");
				this.setMsg("所选课程金额数已超过选课期总金额上限，添加失败");
				this.setTogo("javascript:history.go(-1);");
				return "stu_msg";
			}

			PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
			eleBack.setPrBzzTchOpencourse(prBzzTchOpencourse);
			eleBack.setPeElectiveCoursePeriod(peElectiveCoursePeriod);
			eleBack.setPeBzzStudent(this.peBzzStudent);
			// 检验是否存在选课back记录
			String eleCheck = this.getGeneralService().checkElective(eleBack);
			if (eleCheck != null && eleCheck.length() > 0) {
				// this.setMsg(eleCheck);
				this.setTogo("javascript:window.close();");
				// return "stu_msg";
				// Lee start 由下代替
				// map.put("success", eleCheck);
				// this.setJsonString(JsonUtil.toJSONString(map));
				// return "json";
				// Lee end
				// Lee start
				this.setMsg(eleCheck);
				return "stu_msg";
				// Lee end
			}
			this.getGeneralService().save(eleBack);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		map.put("success", "成功添加到选课期");
		return this.searchCourse();// Lee
		// Lee start 由上代替
		// this.setJsonString(JsonUtil.toJSONString(map));
		// return "json";
		// return searchCourse();
		// Lee end
	}

	/**
	 * 专项选课期选课
	 * 
	 * @return String
	 * @author linjie
	 */
	public String addCourseToBatch() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		Map map = new HashMap();
		this.initStudent();

		HttpServletRequest request = ServletActionContext.getRequest();
		if (this.mybatchid == null || this.mybatchid.trim().equals("")) {
			this.mybatchid = (String) request.getSession().getAttribute("mybatchid");
		}
		String opId = request.getParameter("opId");
		DetachedCriteria opDc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		opDc.add(Restrictions.eq("id", opId));
		try {
			StringBuffer sbub = new StringBuffer();
			sbub.append(" SELECT SB.* FROM STU_BATCH SB ");
			sbub.append("   JOIN PE_BZZ_STUDENT PS ON SB.STU_ID=PS.ID ");
			sbub.append("   JOIN SSO_USER SU ON  PS.FK_SSO_USER_ID=SU.ID ");
			sbub.append("    WHERE FK_ORDER_ID IS NOT NULL AND SU.LOGIN_ID='" + userSession.getLoginId() + "' AND SB.BATCH_ID='"
					+ this.mybatchid + "'");

			/* 1. 验证管理员是否已提交 */
			List orderList = this.getGeneralService().getBySQL(sbub.toString());
			if (orderList != null && orderList.size() > 0) {
				this.setMsg("此次专项报名已经生成订单，不能选课！");
				this.setTogo("javascript:history.go(-1);");
				return "stu_msg";
			}

			/* 2. 验证课程是否已被选 */
			DetachedCriteria dceletived = DetachedCriteria.forClass(PrBzzTchStuElective.class);
			dceletived.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
			dceletived.createAlias("peBzzStudent", "peBzzStudent");
			dceletived.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
			dceletived.add(Restrictions.eq("prBzzTchOpencourse.id", opId));

			DetachedCriteria dceletiveback = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
			dceletiveback.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzTchCourse", "peBzzTchCourse");
			dceletiveback.createAlias("peBzzStudent", "peBzzStudent");
			dceletiveback.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
			dceletiveback.add(Restrictions.eq("prBzzTchOpencourse.id", opId));
			try {
				List tempList = this.getGeneralService().getList(dceletived);
				List tempBackList = this.getGeneralService().getList(dceletiveback);
				if (tempList != null && tempList.size() > 0) {
					this.setMsg("该课程已选");
					this.setTogo("javascript:history.go(-1);");
					return "stu_msg";
				} else if (tempBackList != null && tempBackList.size() > 0) {
					this.setMsg("该课程已选");
					this.setTogo("javascript:history.go(-1);");
					return "stu_msg";
					// Lee end
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
			DetachedCriteria perDc = DetachedCriteria.forClass(PeBzzBatch.class);
			perDc.add(Restrictions.eq("id", this.mybatchid));
			PeBzzBatch peBzzBatch = (PeBzzBatch) this.getGeneralService().getList(perDc).get(0);
			PrBzzTchOpencourse prBzzTchOpencourse = (PrBzzTchOpencourse) this.getGeneralService().getList(opDc).get(0);

			PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
			eleBack.setPrBzzTchOpencourse(prBzzTchOpencourse);
			eleBack.setPeBzzStudent(this.peBzzStudent);
			// 3.检验是否存在选课back记录
			String eleCheck = this.getGeneralService().checkElective(eleBack);
			if (eleCheck != null && eleCheck.length() > 0) {
				this.setTogo("javascript:window.close();");
				this.setMsg(eleCheck);
				return "stu_msg";
			}
			this.getGeneralService().save(eleBack);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		map.put("success", "添加成功");
		return this.searchBatchCourse();// Lee
	}

	/**
	 * 选课期选课删除
	 * 
	 * @return String
	 * @author linjie
	 */
	public String deletePeriodCourse() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String eleId = request.getParameter("eleId");
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
		dc.createAlias("peBzzOrderInfo", "peBzzOrderInfo", dc.LEFT_JOIN);
		// dc.createAlias("trainingCourseStudent", "trainingCourseStudent");
		dc.add(Restrictions.eq("id", eleId));
		try {
			List<PrBzzTchStuElectiveBack> eleList = this.getGeneralService().getList(dc);
			if (eleList != null && eleList.size() > 0) {
				PrBzzTchStuElectiveBack eleBack = eleList.get(0);
				if (eleBack.getPeBzzOrderInfo() != null) {
					this.setMsg("管理员已经提交订单，此选课期课程不能再修改！");
					this.setTogo("javascript:history.go(-1);");
					return "stu_msg";
				}
				// this.getStudentWorkspaceService().deleteElective(eleList.get(0));
				this.getGeneralService().delete(eleBack);
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("删除失败");
			this.setTogo("javascript:history.go(-1);");
			return "stu_msg";
		}
		return selectcourselist();
	}

	/**
	 * 判断当前时间是否正确
	 * 
	 * @param startDate
	 * @param endDate
	 * @author linjie
	 */
	public boolean isRightDate(Date startDate, Date endDate) {
		Date now = new Date();
		if (now.after(endDate)) {
			return false;
		}
		if (now.before(startDate)) {
			return false;
		}
		return true;
	}

	/**
	 * 调查问卷
	 * 
	 * @return String
	 * @author longyinsong
	 */
	public String getVoteList() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.peBzzStudent = this.studentWorkspaceService.initStudentInfo(ssoUser.getId());
		this.initStudent();
		try {
			this.page = this.studentWorkspaceService.initVoteListByQfType(pageSize, startIndex, this.peBzzStudent
					.getEnumConstByFlagQualificationsType(), this.peBzzStudent);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// dateString = formatter.format(date);
		try {
			dateString = formatter.parse(dateFormat.format(date) + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "voteList";
	}

	/**
	 * 学生变更申请
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toChangeStudent() {

		this.initStudent();
		DetachedCriteria dc = DetachedCriteria.forClass(SystemApply.class);
		dc.createCriteria("enumConstByApplyType", "enumConstByApplyType");
		dc.add(Restrictions.eq("PeBzzStudent", this.getPeBzzStudent()));
		dc.add(Restrictions.or(Restrictions.eq("enumConstByApplyType.code", "12"), Restrictions.or(Restrictions.eq(
				"enumConstByApplyType.code", "13"), Restrictions.eq("enumConstByApplyType.code", "14"))));
		try {
			this.setApplyList(this.getGeneralService().getList(dc));
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "toChangeStudent";
	}

	/**
	 * 开课课程
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toCourseforum() {
		this.initStudent();

		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
		dc.add(Restrictions.eq("id", this.getOpencourseid()));
		List opencourse = new ArrayList();
		try {
			opencourse = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (opencourse.size() == 1) {
			this.setOpencoursename(((PrBzzTchOpencourse) opencourse.get(0)).getPeBzzTchCourse().getName());
		}
		return "toCourseforum";
	}

	public String toStuForum() {
		this.initStudent();
		return "toStuForum";
	}

	/**
	 * 学员端选课期报名 dgh
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toElectiveCoursePeriod() {
		// this.initStudent();
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.peBzzStudent = this.studentWorkspaceService.initStudentInfo(ssoUser.getId());
		HttpServletRequest request = ServletActionContext.getRequest();
		String coursename = request.getParameter("coursePeriodName");
		String ktimestart = request.getParameter("ktimestart");
		String ktimeend = request.getParameter("ktimeend");
		String etimestart = request.getParameter("etimestart");
		String etimeend = request.getParameter("etimeend");
		request.getSession().setAttribute("coursePeriodName", coursename);// Lee
		request.getSession().setAttribute("ktimestart", ktimestart);// Lee
		request.getSession().setAttribute("ktimeend", ktimeend);// Lee
		request.getSession().setAttribute("etimestart", etimestart);// Lee
		request.getSession().setAttribute("etimeend", etimeend);// Lee
		// 查询条件回显
		try {
			this.page = this.studentWorkspaceService.initCoursePeriodList(this.peBzzStudent.getId(), coursename, pageSize, startIndex,
					ktimestart, ktimeend, etimestart, etimeend);
			// Lee start 2014年04月03日 已选课程学时总数
			this.yxkcxss = this.studentWorkspaceService.getTimes(this.peBzzStudent.getId(), coursename, ktimestart, ktimeend, etimestart,
					etimeend);
			// Lee end
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("yxkcxss", yxkcxss);
		return "electiveCoursePeriod";
	}

	/**
	 * 选课期
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toElectiveCoursePeriod1() {
		this.initStudent();
		HttpServletRequest request = ServletActionContext.getRequest();
		String coursename = request.getParameter("coursePeriodName");

		try {
			DetachedCriteria dc = DetachedCriteria.forClass(CoursePeriodStudent.class);
			dc.add(Restrictions.eq("peBzzStudent", this.peBzzStudent));
			dc.createAlias("peElectiveCoursePeriod", "peElectiveCoursePeriod");
			dc.add(Restrictions.like("peElectiveCoursePeriod.name", coursename, MatchMode.ANYWHERE));
			page = this.getGeneralService().getByPage(dc, this.pageSize, this.startIndex);

		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "electiveCoursePeriod";
	}

	/**
	 * 用于判断传入的set是否为空
	 * 
	 * @param set
	 * @return String
	 * @author linjie
	 */
	public boolean setIsNull(String orderId) {
		String sql = "select * from pe_bzz_refund_info r where r.FK_ORDER_ID = '" + orderId + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list == null || list.size() == 0) {
				return true;
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 历史订单
	 * 
	 * @return String
	 * @author linjie
	 */
	public String tohistoryrecord() {

		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			/**
			 * 列表中显示的订单
			 */
			this.page = this.studentWorkspaceService.initHistoryRecord(us.getSsoUser().getId(), paymentDateStart, paymentDateEnd, pageSize,
					startIndex, orderno, tchprice, cname);
			/**
			 * 新增，最后一行订单数统计
			 */
			Map params = new HashMap();
			params.put("userId", us.getSsoUser().getId());
			// params.put("payDate", paymentDate);
			params.put("paymentDateStart", paymentDateStart);
			params.put("paymentDateEnd", paymentDateEnd);
			params.put("orderno", orderno);
			params.put("orderprice", orderprice);
			params.put("cname", cname);
			params.put("tchprice", tchprice);
			params.put("fpstatus", fpstatus);
			List list = this.studentWorkspaceService.initHistoryRecordStatistics(params);
			ServletActionContext.getRequest().setAttribute("tongjiList", list.get(0));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * try { EnumConst enumConstByFlagOrderType =
		 * this.getEnumConstService().getByNamespaceCode("FlagOrderType", "0");
		 * 
		 * DetachedCriteria dc =
		 * DetachedCriteria.forClass(PeBzzOrderInfo.class);
		 * dc.createCriteria("enumConstByFlagOrderIsValid",
		 * "enumConstByFlagOrderIsValid"); //订单是否有效
		 * dc.add(Restrictions.eq("enumConstByFlagOrderIsValid.code", "1"));
		 * dc.createCriteria("enumConstByFlagPaymentState",
		 * "enumConstByFlagPaymentState");
		 * dc.createCriteria("enumConstByFlagPaymentMethod",
		 * "enumConstByFlagPaymentMethod");
		 * dc.createCriteria("enumConstByFlagRefundState",
		 * "enumConstByFlagRefundState", DetachedCriteria.LEFT_JOIN);
		 * dc.add(Restrictions.eq("enumConstByFlagOrderType",
		 * enumConstByFlagOrderType)); dc.add(Restrictions.eq("ssoUser",
		 * us.getSsoUser())); dc.addOrder(Order.desc("seq")); if
		 * (this.paymentDate != null && !this.paymentDate.trim().equals("")) {
		 * SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); Date
		 * date1 = format.parse(this.paymentDate); Date date2 = new Date();
		 * date2.setDate(date1.getDate()+1);
		 * dc.add(Restrictions.between("paymentDate", date1, date2)); } page =
		 * this.getGeneralService().getByPage(dc, pageSize, startIndex); } catch
		 * (Exception e) { e.printStackTrace(); }
		 */
		return "tohistoryrecord";
	}

	/**
	 * 学生退费申请
	 * 
	 * @return String
	 * @author linjie
	 */
	public String applyRefund() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.createCriteria("enumConstByFlagPaymentState", "enumConstByFlagPaymentState");
		dc.add(Restrictions.eq("id", id));
		try {
			PeBzzOrderInfo order = (PeBzzOrderInfo) this.getGeneralService().getList(dc).get(0);
			this.getGeneralService().checkRefundPermission(order);
		} catch (EntityException e) {
			// e.printStackTrace();
			this.setMsg(e.getMessage());
			this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
			return "stu_msg";
		}
		return "refundApply";
	}

	public boolean strIsNull(String str) {
		if (str == null || str.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否满足退费申请条件，未学习，未开发票
	 * 
	 * @param orderId
	 * @return String
	 * @author linjie
	 */
	public boolean canRefund(String orderId) {
		String sql = "(select pbtse.id\n" + "          from pr_bzz_tch_stu_elective pbtse\n" + "         where pbtse.fk_order_id = '"
				+ orderId + "'\n" + "           and pbtse.online_time > '0')\n"
				+ "           union (select pbii.id from pe_bzz_invoice_info pbii where pbii.fk_order_id = '" + orderId + "')";
		List tempList = null;

		try {
			tempList = this.getGeneralService().getBySQL(sql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (tempList != null && tempList.size() > 0) {
			return false;
		}
		return true;
	}

	/**
	 * 改变note、
	 * 
	 * @param peBzzTeachingLiveroom
	 * @param length
	 * @param endDate
	 * @author linjie
	 */
	private void changetile(PeBzzTeachingLiveroom peBzzTeachingLiveroom, int length) {
		int templength = peBzzTeachingLiveroom.getNote().length();
		if (templength > length) {
			peBzzTeachingLiveroom.setNote(peBzzTeachingLiveroom.getNote().substring(0, length) + "...");
		}
	}

	/**
	 * 设置属性
	 * 
	 * @param user
	 * @param loginType
	 * @param endDate
	 * @author linjie
	 * @throws PlatformException
	 */
	public void setM4Session(PeBzzStudent user, String loginType) throws PlatformException {
		HttpServletRequest request = ServletActionContext.getRequest();
		if (SsoConstant.SSO_STUDENT.equals(loginType)) {

			PlatformFactory factory = PlatformFactory.getInstance();
			PlatformManage platformManage = factory.createPlatformManage();
			EntityUser euser = platformManage.getEntityUser(user.getId(), "student");
			request.getSession().setAttribute("eduplatform_user", euser);
			StudentPriv studentPriv = factory.getStudentPriv(user.getId());
			request.getSession().setAttribute("eduplatform_priv", studentPriv);
		}
	}

	/**
	 * 学生个人信息
	 * 
	 * @return String
	 * @author linjie
	 */
	public String StudentInfo() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);

		this.peBzzStudent = this.studentWorkspaceService.initStudentInfo(us.getSsoUser().getId());
		/**
		 * 可能未使用，注释掉
		 */
		// 判断其是否考试报名
		/*
		 * DetachedCriteria score = DetachedCriteria
		 * .forClass(PeBzzExamScore.class); score.createCriteria("peBzzStudent",
		 * "peBzzStudent"); score.add(Restrictions.eq("peBzzStudent.id",
		 * peBzzStudent.getId())); List scoreList = new ArrayList(); try {
		 * scoreList = this.getGeneralService().getExamScores(score); } catch
		 * (EntityException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		return "studentinfo";
	}

	/**
	 * 上传照片
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String confirmPhoto() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String ssoUserid = userSession.getSsoUser().getId();
		DetachedCriteria studc = DetachedCriteria.forClass(PeBzzStudent.class);
		studc.createCriteria("peEnterprise", "peEnterprise");
		studc.add(Restrictions.eq("regNo", userSession.getLoginId()));
		PeBzzStudent bzzStudent = null;
		Date today = new Date();
		try {
			bzzStudent = this.getGeneralService().getStudentInfo(studc);
			EnumConst enumConstByFlagPhotoConfirm = this.getMyListService().getEnumConstByNamespaceCode("FlagPhotoConfirm", "1");
			bzzStudent.setEnumConstByFlagPhotoConfirm(enumConstByFlagPhotoConfirm);
			bzzStudent.setPhotoConfirmDate(today);
			try {
				this.getGeneralService().save(bzzStudent);
			} catch (EntityException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "confirmPhoto_success";
	}

	/**
	 * 取消报名
	 * 
	 * @return String
	 * @author linjie
	 */
	public String cancelBaoming() {
		this.StudentInfo();
		PeBzzExamScore student = null;
		DetachedCriteria score = DetachedCriteria.forClass(PeBzzExamScore.class);
		score.add(Restrictions.eq("peBzzStudent.id", peBzzStudent.getId()));
		DetachedCriteria prdc = score.createCriteria("peBzzExamBatch", "peBzzExamBatch");
		prdc.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
		prdc.add(Restrictions.eq("enumConstByFlagExamBatch.id", "402880a91dadc115011dadfcf26b00aa"));
		try {
			student = (PeBzzExamScore) this.getGeneralService().getExamScore(score);
			this.getGeneralService().delete(student);
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "cancel_Baoming";
	}

	/**
	 * 取消报名
	 * 
	 * @return String
	 * @author linjie
	 */
	public String cancelBao() {
		this.StudentInfo();
		PeBzzExamScore student = null;
		DetachedCriteria score = DetachedCriteria.forClass(PeBzzExamScore.class);
		score.add(Restrictions.eq("peBzzStudent.id", peBzzStudent.getId()));
		DetachedCriteria prdc = score.createCriteria("peBzzExamBatch", "peBzzExamBatch");
		prdc.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
		prdc.add(Restrictions.eq("enumConstByFlagExamBatch.id", "402880a91dadc115011dadfcf26b00aa"));
		try {
			student = (PeBzzExamScore) this.getGeneralService().getExamScore(score);
			if (student != null) {
				this.getGeneralService().delete(student);
			}
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "cancel_Bao";
	}

	/**
	 * 报名
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toBaoMing() {
		this.StudentInfo();
		PeBzzExamScore student = null;
		PeBzzExamLate studentlate = null;

		DetachedCriteria score = DetachedCriteria.forClass(PeBzzExamScore.class);
		score.add(Restrictions.eq("peBzzStudent.id", peBzzStudent.getId()));

		// 是否在本批次申请缓考,如果有缓考信息，则删除
		DetachedCriteria late = DetachedCriteria.forClass(PeBzzExamLate.class);
		DetachedCriteria latec = late.createCriteria("peBzzExamBatch", "peBzzExamBatch");
		latec.createCriteria("enumConstByFlagExamBatch", "enumConstByFlagExamBatch");
		latec.add(Restrictions.eq("enumConstByFlagExamBatch.id", "402880a91dadc115011dadfcf26b00aa"));
		late.createCriteria("peBzzStudent", "peBzzStudent");
		late.add(Restrictions.eq("peBzzStudent.id", peBzzStudent.getId()));

		try {
			student = (PeBzzExamScore) this.getGeneralService().getExamScore(score);

			studentlate = (PeBzzExamLate) this.getGeneralService().getExamLate(late);
			if (studentlate != null) {
				this.getGeneralService().delete(studentlate);
			}
		} catch (EntityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "baoming_success";
	}

	/**
	 * 跳转修改信息
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toModifyInfo() {
		this.StudentInfo();
		return "tomodifyinfo";
	}

	/**
	 * 从业人员修改邮箱
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toModifyEmail() {
		this.StudentInfo();
		return "tomodifyEmail";
	}

	public String modifyPassEmail() {
		try {

			DetachedCriteria PeBzzStudentCriteria = DetachedCriteria.forClass(PeBzzStudent.class);
			PeBzzStudentCriteria.add(Restrictions.idEq(peBzzStudent.getId()));
			// PeBzzStudentCriteria.createCriteria("peBzzBatch", "peBzzBatch");
			PeBzzStudentCriteria.createCriteria("peEnterprise", "peEnterprise");
			PeBzzStudentCriteria.createCriteria("peEnterprise.peEnterprise", DetachedCriteria.LEFT_JOIN);
			PeBzzStudent student = (PeBzzStudent) this.getGeneralService().getList(PeBzzStudentCriteria).get(0);
			if (choosePassEmail != null && "regEmail".equals(choosePassEmail)) {
				student.setPassEmail("");
			} else if (choosePassEmail != null && "passEmail".equals(choosePassEmail)) {
				student.setPassEmail(passEmail);
			}
			this.getGeneralService().update(student);
			operateresult = "1";
		} catch (Exception e) {
			operateresult = "2";
			e.printStackTrace();
		}
		return "modifyPassEmail";
	}

	/**
	 * 跳转修改照片
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toUpdatePhoto() {
		this.StudentInfo();
		return "toUpdatePhoto";
	}

	/**
	 * 修改信息
	 * 
	 * @return String
	 * @author linjie
	 */
	@SuppressWarnings("unchecked")
	public String modifyInfo() {
		try {
			DetachedCriteria PeBzzStudentCriteria = DetachedCriteria.forClass(PeBzzStudent.class);
			PeBzzStudentCriteria.add(Restrictions.idEq(peBzzStudent.getId()));
			// PeBzzStudentCriteria.createCriteria("peBzzBatch", "peBzzBatch");
			PeBzzStudentCriteria.createCriteria("peEnterprise", "peEnterprise");
			PeBzzStudentCriteria.createCriteria("peEnterprise.peEnterprise", DetachedCriteria.LEFT_JOIN);
			PeBzzStudent student = (PeBzzStudent) this.getGeneralService().getList(PeBzzStudentCriteria).get(0);

			student.setTrueName(peBzzStudent.getTrueName()); // 姓名*
			student.setCardType(peBzzStudent.getCardType()); // 国籍*
			// student.setCardNo(peBzzStudent.getCardNo()); //身份证号*
			student.setEducation(peBzzStudent.getEducation()); // 学历*
			student.setGender(peBzzStudent.getGender()); // 性别*
			student.setFolk(peBzzStudent.getFolk()); // 民族*
			student.setMobilePhone(peBzzStudent.getMobilePhone()); // 手机*
			student.setBirthdayDate(peBzzStudent.getBirthdayDate()); // 出生日期*
			student.setDepartment(peBzzStudent.getDepartment()); // 部门*
			// student.setTitle(this.peBzzStudent.getTitle()); //职称
			student.setPosition(peBzzStudent.getPosition()); // 职务
			student.setEmail(peBzzStudent.getEmail()); // 电子邮箱*
			// student.setZipcode(peBzzStudent.getZipcode()); //邮编
			student.setAddress(peBzzStudent.getAddress()); // 工作所在地区*
			// student.setPhone(peBzzStudent.getPhone()); //办公电话
			student.setWork(peBzzStudent.getWork()); // 从事业务
			student.setWorkAddress(peBzzStudent.getWorkAddress()); // 工作区域

			PeBzzStudent bzzStudent = (PeBzzStudent) this.getGeneralService().save(student);
			if (bzzStudent != null) {
				operateresult = "1";
			} else {
				operateresult = "2";
			}
		} catch (Exception e) {
			e.printStackTrace();
			operateresult = "2";
			return "modifyInfo";
		}
		return "modifyInfo";
	}

	/**
	 * 修改照片
	 * 
	 * @return String
	 * @author linjie
	 */
	public String modifyPhoto() {
		try {
			DetachedCriteria PeBzzStudentCriteria = DetachedCriteria.forClass(PeBzzStudent.class);
			PeBzzStudentCriteria.add(Restrictions.idEq(peBzzStudent.getId()));
			PeBzzStudentCriteria.createCriteria("peBzzBatch", "peBzzBatch");
			PeBzzStudentCriteria.createCriteria("peEnterprise", "peEnterprise");
			PeBzzStudentCriteria.createCriteria("peEnterprise.peEnterprise", DetachedCriteria.LEFT_JOIN);
			PeBzzStudent student = (PeBzzStudent) this.getGeneralService().getList(PeBzzStudentCriteria).get(0);
			student.setPhoto(getPhotoName(student));

			PeBzzStudent bzzStudent = (PeBzzStudent) this.getGeneralService().save(student);
			if (bzzStudent != null) {
				operateresult = "1";
			} else {
				operateresult = "2";
			}
		} catch (Exception e) {
			e.printStackTrace();
			operateresult = "2";
			return "modifyInfo";
		}
		return "modifyInfo";
	}

	/**
	 * 获得照片名称
	 * 
	 * @param student
	 * @return String
	 * @author linjie
	 * @throws IOException
	 */
	private String getPhotoName(PeBzzStudent student) throws IOException {
		if (this.getPhoto() == null)
			return peBzzStudent.getPhoto();
		String fileName = student.getRegNo() + ".jpg";

		BufferedImage bufferedImage = ImageIO.read(this.getPhoto());

		String entCode = "";
		if (student.getPeEnterprise().getPeEnterprise() == null) {
			// 用户在一级企业下
			entCode = student.getPeEnterprise().getCode();
		} else {
			entCode = student.getPeEnterprise().getPeEnterprise().getCode();
		}

		String realPath = ServletActionContext.getRequest().getRealPath("/incoming/student-photo");
		File dir = new File(realPath + "/" + student.getPeBzzBatch().getBatchCode() + "/" + entCode);
		if (!dir.isDirectory()) {
			dir.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(dir.getAbsolutePath() + "/" + fileName);
		ImageIO.write(createResizedCopy(bufferedImage, 390, 567), "jpg", fos);
		fos.flush();
		fos.close();

		// System.out.println(fileName);

		// FileInputStream fis=new FileInputStream(this.getPhoto());
		// byte[] buffer=new byte[1024];
		// int len=0;
		// while((len=fis.read(buffer))>0){
		// fos.write(buffer, 0, len);
		// }

		return fileName;
	}

	BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight) {
		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = scaledBI.createGraphics();
		g.setComposite(AlphaComposite.Src);
		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
		g.dispose();
		return scaledBI;
	}

	public String totrain() {
		return "totrain";
	}

	/**
	 * 注册邮箱
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String tositeemail() {

		ActionContext ac = ActionContext.getContext();
		UserSession us = (UserSession) ac.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		StringBuffer sql = new StringBuffer();

		try {
			page = this.studentWorkspaceService.initSiteemail(us.getLoginId(), selTitle, selSendDateStart, selSendDateEnd, selSendName,
					pageSize, startIndex);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "tositeemail";
	}

	/**
	 * 删除邮箱
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String delEmail() {
		if (this.getDelId() != null && this.getDelId().length > 0) {
			String[] ids = this.getDelId();
			try {

				DetachedCriteria dc = DetachedCriteria.forClass(SiteEmail.class);
				dc.add(Restrictions.in("id", ids));
				List<SiteEmail> periodList = this.getGeneralService().getList(dc);
				for (SiteEmail siteEmail : periodList) {

					siteEmail.setAddresseeDel((long) 1);
					this.getGeneralService().save(siteEmail);
				}

			} catch (EntityException e) {
				flag = "delfalse";
				return "status";
			}

		} else {
			flag = "delfalse";
			return "status";
		}
		flag = "deltrue";
		return "status";
	}

	/**
	 * 学员端 预付费余额查询
	 * 
	 * @param endDate
	 * @author linjie dgh
	 */
	public String toaccountbalance() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String ktimestart = request.getParameter("ktimestart");
		String ktimeend = request.getParameter("ktimeend");
		String optamount = request.getParameter("optamount");
		String opttype = request.getParameter("opttype");
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser user = us.getSsoUser();
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
			dc.add(Restrictions.eq("id", user.getId()));
			List tmpList = this.getGeneralService().getList(dc);
			if (tmpList.size() > 0) {
				user = (SsoUser) tmpList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletActionContext.getRequest().setAttribute("myUser", user);
		try {
			BigDecimal b1 = new BigDecimal(user.getSumAmount());
			BigDecimal b2 = new BigDecimal(user.getAmount());
			BigDecimal subAmount = b1.subtract(b2); // new
			ServletActionContext.getRequest().setAttribute("sumAmount", b1.setScale(2, BigDecimal.ROUND_HALF_UP));
			ServletActionContext.getRequest().setAttribute("amount", b2.setScale(2, BigDecimal.ROUND_HALF_UP));
			ServletActionContext.getRequest().setAttribute("subAmount", subAmount.setScale(2, BigDecimal.ROUND_HALF_UP));

			StringBuffer sqlBuffer = new StringBuffer();
			// sqlBuffer.append("SELECT * FROM ( ");
			// sqlBuffer.append(" SELECT TO_CHAR(ASSIGN_DATE, 'yyyy-MM-dd
			// hh24:mi:ss') AS
			// ASSIGN_DATE,OPERATE_AMOUNT,EC_TYPE.NAME,ACCOUNT_AMOUNT");
			// sqlBuffer.append(" FROM ASSIGN_RECORD AR JOIN SSO_USER SU ON
			// SU.ID=AR.FK_USER_ID ");
			// sqlBuffer.append(" JOIN ENUM_CONST EC_TYPE ON
			// AR.FLAG_OPERATE_TYPE = EC_TYPE.ID AND NAMESPACE =
			// 'FlagOperateType'");
			// sqlBuffer.append(" LEFT JOIN PE_BZZ_STUDENT PBS ON
			// PBS.FK_SSO_USER_ID=SU.ID ");
			// sqlBuffer.append(" LEFT JOIN PE_ENTERPRISE pe ON
			// PBS.FK_ENTERPRISE_ID=PE.ID ");
			// sqlBuffer.append(" WHERE SU.LOGIN_ID = '" + us.getLoginId() + "'
			// ");
			// sqlBuffer.append(") WHERE 1=1 ");
			sqlBuffer.append(" SELECT * ");
			sqlBuffer.append(" FROM ((SELECT TO_CHAR(ASSIGN_DATE, 'yyyy-MM-dd hh24:mi:ss') AS ASSIGN_DATE, ");
			sqlBuffer.append(" OPERATE_AMOUNT, ");
			sqlBuffer.append(" EC_TYPE.NAME, ");
			sqlBuffer.append(" ACCOUNT_AMOUNT ");
			sqlBuffer.append(" FROM ASSIGN_RECORD AR ");
			sqlBuffer.append(" JOIN SSO_USER SU ");
			sqlBuffer.append(" ON SU.ID = AR.FK_USER_ID ");
			sqlBuffer.append(" JOIN ENUM_CONST EC_TYPE ");
			sqlBuffer.append(" ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID ");
			sqlBuffer.append(" AND NAMESPACE = 'FlagOperateType' ");
			sqlBuffer.append(" LEFT JOIN PE_BZZ_STUDENT PBS ");
			sqlBuffer.append(" ON PBS.FK_SSO_USER_ID = SU.ID ");
			sqlBuffer.append(" LEFT JOIN PE_ENTERPRISE PE ");
			sqlBuffer.append(" ON PBS.FK_ENTERPRISE_ID = PE.ID ");
			sqlBuffer.append(" WHERE SU.LOGIN_ID = '" + us.getLoginId() + "') UNION ALL ");
			sqlBuffer.append(" (SELECT TO_CHAR(ASSIGN_DATE, 'yyyy-MM-dd hh24:mi:ss') AS ASSIGN_DATE, ");
			sqlBuffer.append(" CLASS_NUM OPERATE_AMOUNT, ");
			sqlBuffer.append(" EC_TYPE.NAME, ");
			sqlBuffer.append(" SUBAMOUNT ACCOUNT_AMOUNT ");
			sqlBuffer.append(" FROM ASSIGN_RECORD AR ");
			sqlBuffer.append(" INNER JOIN ASSIGN_RECORD_STUDENT ARS ");
			sqlBuffer.append(" ON AR.ID = ARS.FK_RECORD_ID ");
			sqlBuffer.append(" JOIN ENUM_CONST EC_TYPE ");
			sqlBuffer.append(" ON AR.FLAG_OPERATE_TYPE = EC_TYPE.ID ");
			sqlBuffer.append(" AND NAMESPACE = 'FlagOperateType' ");
			sqlBuffer.append(" INNER JOIN PE_BZZ_STUDENT PBS ");
			sqlBuffer.append(" ON ARS.FK_STUDENT_ID = PBS.ID ");
			sqlBuffer.append("  INNER JOIN SSO_USER SU ");
			sqlBuffer.append("  ON PBS.FK_SSO_USER_ID = SU.ID ");
			sqlBuffer.append(" WHERE SU.LOGIN_ID = '" + us.getLoginId() + "' ");
			sqlBuffer.append(" ))WHERE 1 = 1 ");
			if (null != ktimestart && !ktimestart.equals("")) {
				sqlBuffer.append(" AND ASSIGN_DATE >= '" + ktimestart + "' ");
			}
			if (null != ktimeend && !ktimeend.equals("")) {
				// 修改时间格式
				ktimeend = ktimeend + " 23:59:59";
				sqlBuffer.append(" AND ASSIGN_DATE <='" + ktimeend + "'");
			}
			if (null != optamount && !optamount.equals("")) {
				sqlBuffer.append(" AND OPERATE_AMOUNT = " + optamount);
			}
			if (null != opttype && !opttype.equals("")) {
				sqlBuffer.append(" AND NAME='" + opttype + "'");
			}
			sqlBuffer.append(" ORDER BY ASSIGN_DATE DESC ");
			yufeihislist = this.getGeneralService().getBySQL(sqlBuffer.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "toaccountbalance";
	}

	/**
	 * 查看专项培训列表
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String tospecialenrolment() {
		HttpServletRequest request = ServletActionContext.getRequest();
		// batchName = request.getParameter("batchName");
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		try {
			this.page = this.studentWorkspaceService.initBatchList(us.getLoginId(), batchName, pageSize, startIndex);
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return "tospecialenrolment";
	}

	/**
	 * 确认订单是否已支付，等待确认状态
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public int isPayemntOrder(String batchId, int count) {
		if (count > 0) {
			return 1;
		}
		this.initStudent();
		String sql = "select o.id as id\n" + "  from pe_bzz_order_info o\n" + "  inner join enum_const e on e.id = o.flag_payment_state\n"
				+ "                        and e.namespace = 'FlagPaymentState'\n" + "                        and e.code = '0'\n"
				+ " inner join (select ele.fk_order_id as oid\n" + "               from pr_bzz_tch_stu_elective ele\n"
				+ "              where ele.fk_tch_opencourse_id in\n" + "                    (select op.id\n"
				+ "                       from pr_bzz_tch_opencourse op\n" + "                      where op.fk_batch_id = '" + batchId
				+ "')\n" + "                and ele.fk_stu_id = '" + this.peBzzStudent.getId() + "') t on o.id = t.oid";
		try {
			List list = this.getGeneralService().getBySQL(sql);

			if (list.size() == 0) {
				return 2;
			}

		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 3;
	}

	public String trainview() {

		try {

			this.page = this.studentWorkspaceService.initTrainview(this.id, pageSize, startIndex);
			this.classHour = this.studentWorkspaceService.initTrainviewClassNum(this.id);
			// BigDecimal price =
			// this.studentWorkspaceService.initCoursePrice("ClassHourRate").setScale(2,
			// BigDecimal.ROUND_HALF_UP);
			// ServletActionContext.getRequest().setAttribute("price", price);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "trainview";
	}

	/**
	 * 学生查看订单详情
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String viewOrder() {
		try {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			HttpServletRequest request = ServletActionContext.getRequest();
			String id = request.getParameter("id");
			/**
			 * 首先根据订单id来查找对应的订单
			 * 
			 */
			String hql = "from PeBzzOrderInfo p where p.id='" + id + "'";
			peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getByHQL(hql).get(0);
			this.ssoUser = peBzzOrderInfo.getSsoUser();
			DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);
			stuDc.add(Restrictions.eq("ssoUser", this.ssoUser));
			List stuList = this.getGeneralService().getList(stuDc);
			if (stuList != null && stuList.size() > 0) {
				this.peBzzStudent = (PeBzzStudent) stuList.get(0);
			}

			DetachedCriteria dcInvoice = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
			dcInvoice.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));
			List peBzzInvoiceInfoList = this.getGeneralService().getList(dcInvoice);
			if (peBzzInvoiceInfoList != null && peBzzInvoiceInfoList.size() > 0) {
				this.peBzzInvoiceInfo = (PeBzzInvoiceInfo) peBzzInvoiceInfoList.get(0);
			}
			DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			orderDc.createCriteria("enumConstByFlagPaymentState");
			orderDc.createCriteria("enumConstByFlagPaymentMethod");
			orderDc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState", orderDc.LEFT_JOIN);
			orderDc.add(Restrictions.eq("id", id));
			List orderList = this.getGeneralService().getList(orderDc);
			PeBzzOrderInfo order = (PeBzzOrderInfo) orderList.get(0);
			// /**
			// * 找到订单对应的支付状态和支付方式
			// */
			// EnumConst enumConstByFlagPaymentState =
			// order.getEnumConstByFlagPaymentState();
			// EnumConst enumConstByFlagPaymentMethod =
			// order.getEnumConstByFlagPaymentMethod();
			//
			// DetachedCriteria dc = null;
			// /**
			// * 如果订单为网银支付和支付状态为未支付,进入选课记录备份表中进行查找记录
			// */
			// EnumConst refundState = order.getEnumConstByFlagRefundState();
			// if(refundState != null && (refundState.getCode().equals("0") ||
			// refundState.getCode().equals("2"))){//待审核和已拒绝
			// dc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
			// dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
			// dc.createCriteria("prBzzTchOpencourse.peBzzTchCourse",
			// "peBzzTchCourse");
			// dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
			// dc.createCriteria("trainingCourseStudentHistory",
			// "trainingCourseStudentHistory",dc.LEFT_JOIN);
			// dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
			// dc.add(Restrictions.eq("peBzzOrderInfo", order));
			// }else if(refundState != null &&
			// refundState.getCode().equals("1")){//已退费
			// dc = DetachedCriteria.forClass(PrBzzTchStuElectiveHistory.class);
			// dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
			// dc.createCriteria("prBzzTchOpencourse.peBzzTchCourse",
			// "peBzzTchCourse");
			// dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
			// dc.createCriteria("trainingCourseStudentHistory",
			// "trainingCourseStudentHistory",dc.LEFT_JOIN);
			// dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
			// dc.add(Restrictions.eq("peBzzOrderInfo", order));
			// }else if(enumConstByFlagPaymentMethod.getCode().equals("0") &&
			// enumConstByFlagPaymentState.getCode().equals("0")) {
			// dc = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
			// dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
			// dc.createCriteria("prBzzTchOpencourse.peBzzTchCourse",
			// "peBzzTchCourse");
			// dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
			// dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
			// dc.add(Restrictions.eq("peBzzOrderInfo", order));
			// }else{
			// dc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
			// dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
			// dc.createCriteria("prBzzTchOpencourse.peBzzTchCourse",
			// "peBzzTchCourse");
			// dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
			// dc.createCriteria("trainingCourseStudent",
			// "trainingCourseStudent",dc.LEFT_JOIN);
			// dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
			// dc.add(Restrictions.eq("peBzzOrderInfo", order));
			// }

			page = this.getGeneralService().getOrderDetail(order, us.getSsoUser(), pageSize, startIndex);
			
			if(peBzzOrderInfo.getMergeSeq() != null && !"".equals(peBzzOrderInfo.getMergeSeq())) {
				String sql = "SELECT SEQ FROM PE_BZZ_ORDER_INFO WHERE MERGE_SEQ = '" + peBzzOrderInfo.getMergeSeq() + "'";
				List list = this.getGeneralService().getBySQL(sql);
				merge_order = "";
				for(int i = 0; i < list.size(); i++) {
					merge_order += list.get(0) + ",";
				}
				merge_order = merge_order.substring(0, merge_order.length() - 1);
			}
			// dgh 20140709 获取选修学时
			this.totalhourxx = this.getTotalXxHours(order, us.getSsoUser());
			// dgh 20140709 获取必修学时
			this.totalhourbx = this.getTotalBxHours(order, us.getSsoUser());
			// dgh 20140709 金额合计
			this.tchprice = this.getTotalPrice(order, us.getSsoUser());
			BigDecimal num = new BigDecimal(0);
			List list = page.getItems();
			for (int i = 0; i < list.size(); i++) {
				Object[] o = (Object[]) list.get(i);
				if (null != o[5])
					num = num.add(new BigDecimal(o[5].toString()));
				else
					num = num.add(new BigDecimal("0"));
			}
			this.classHour = num.setScale(1, BigDecimal.ROUND_HALF_UP).toString();
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "viewOrder";
	}

	private String getTotalPrice(PeBzzOrderInfo order, SsoUser ssoUser) {
		String sql = "";
		String userSql = "";
		if (ssoUser != null) {
			userSql = "   and pboi.create_user = '" + ssoUser.getId() + "'\n";
		}
		if (!order.getEnumConstByFlagPaymentState().getCode().equalsIgnoreCase("0")
				&& !(order.getEnumConstByFlagRefundState() != null && "1".equalsIgnoreCase(order.getEnumConstByFlagRefundState().getCode()))) {
			sql = " select sum(price)  from (select pbtc.price from pr_bzz_tch_stu_elective ele\n"
					+ "  left join training_course_student tcs\n" + "    on tcs.id = ele.fk_training_id\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n" + "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ " left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end

					"  left join pe_bzz_student pbs\n" + "    on ele.fk_stu_id = pbs.id\n" + "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n" + " where  pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ "union all\n"
					+ "select pbtc.price from elective_history ele\n"
					+ "  left join tcs_history tcs\n"
					+ "    on tcs.id = ele.fk_training_id\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype "
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n"
					+ "    on ele.fk_stu_id = pbs.id\n"
					+ "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n" + " where  pboi.id = '" + order.getId() + "'\n" + userSql + ")";
		} else {
			sql = "select sum(price) from  (select  pbtc.price from pr_bzz_tch_stu_elective_back ele\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n" + "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n" + "    on ele.fk_stu_id = pbs.id\n" + "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n" + " where  pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ "union all\n"
					+ "select pbtc.price from elective_back_history ele\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ "  LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n"
					+ "    on ele.fk_stu_id = pbs.id\n"
					+ "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n" + " where   pboi.id = '" + order.getId() + "'\n" + userSql + ")";
		}
		List tempList;
		try {
			tempList = this.getGeneralService().getBySQL(sql);
			if (null != tempList && tempList.size() > 0 && null != tempList.get(0)) {
				return tempList.get(0).toString();
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return "0";
	}

	private String getTotalXxHours(PeBzzOrderInfo order, SsoUser ssoUser) {
		String sql = "";
		String userSql = "";
		if (ssoUser != null) {
			userSql = "   and pboi.create_user = '" + ssoUser.getId() + "'\n";
		}
		if (!order.getEnumConstByFlagPaymentState().getCode().equalsIgnoreCase("0")
				&& !(order.getEnumConstByFlagRefundState() != null && "1".equalsIgnoreCase(order.getEnumConstByFlagRefundState().getCode()))) {
			sql = " select sum(time) from  (select SUM(PBTC.TIME) TIME from pr_bzz_tch_stu_elective ele\n"
					+ "  left join training_course_student tcs\n"
					+ "    on tcs.id = ele.fk_training_id\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ " left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n" + "    on ele.fk_stu_id = pbs.id\n" + "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n"
					+ " where pbtc.FLAG_COURSETYPE='402880f32200c249012200c7f8b30002' and pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ "union\n"
					+ "select SUM(PBTC.TIME) TIME from elective_history ele\n"
					+ "  left join tcs_history tcs\n"
					+ "    on tcs.id = ele.fk_training_id\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n"
					+ "    on ele.fk_stu_id = pbs.id\n"
					+ "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n"
					+ " where pbtc.FLAG_COURSETYPE='402880f32200c249012200c7f8b30002' and pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ ")";
		} else {
			sql = "select sum(time) from  (select  SUM(PBTC.TIME) TIME from pr_bzz_tch_stu_elective_back ele\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n" + "    on ele.fk_stu_id = pbs.id\n" + "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n"
					+ " where pbtc.FLAG_COURSETYPE='402880f32200c249012200c7f8b30002' and pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ "union\n"
					+ "select SUM(PBTC.TIME) TIME from elective_back_history ele\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ "  LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n"
					+ "    on ele.fk_stu_id = pbs.id\n"
					+ "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n"
					+ " where pbtc.FLAG_COURSETYPE='402880f32200c249012200c7f8b30002' and  pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ ")";
		}
		List tempList;
		try {
			tempList = this.getGeneralService().getBySQL(sql);
			if (null != tempList && tempList.size() > 0 && null != tempList.get(0)) {
				return tempList.get(0).toString();
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return "0";
	}

	private String getTotalBxHours(PeBzzOrderInfo order, SsoUser ssoUser) {
		String sql = "";
		String userSql = "";
		if (ssoUser != null) {
			userSql = "   and pboi.create_user = '" + ssoUser.getId() + "'\n";
		}
		if (!order.getEnumConstByFlagPaymentState().getCode().equalsIgnoreCase("0")
				&& !(order.getEnumConstByFlagRefundState() != null && "1".equalsIgnoreCase(order.getEnumConstByFlagRefundState().getCode()))) {
			sql = " select sum(time) from (select SUM(PBTC.TIME) TIME from pr_bzz_tch_stu_elective ele\n"
					+ "  left join training_course_student tcs\n"
					+ "    on tcs.id = ele.fk_training_id\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ " left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end

					"  left join pe_bzz_student pbs\n" + "    on ele.fk_stu_id = pbs.id\n" + "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n"
					+ " where pbtc.FLAG_COURSETYPE='402880f32200c249012200c780c40001' and pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ "union\n"
					+ "select SUM(PBTC.TIME) TIME from elective_history ele\n"
					+ "  left join tcs_history tcs\n"
					+ "    on tcs.id = ele.fk_training_id\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n"
					+ "    on ele.fk_stu_id = pbs.id\n"
					+ "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n"
					+ " where pbtc.FLAG_COURSETYPE='402880f32200c249012200c780c40001' and pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ ")";
		} else {
			sql = "select sum(time)  from (select  SUM(PBTC.TIME) TIME from pr_bzz_tch_stu_elective_back ele\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ " LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n" + "    on ele.fk_stu_id = pbs.id\n" + "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n"
					+ " where pbtc.FLAG_COURSETYPE='402880f32200c249012200c780c40001' and pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ "union\n"
					+ "select SUM(PBTC.TIME) TIME from elective_back_history ele\n"
					+ "  left join pr_bzz_tch_opencourse pbto\n"
					+ "    on ele.fk_tch_opencourse_id = pbto.id\n"
					+ "  left join pe_bzz_tch_course pbtc\n"
					+ "    on pbto.fk_course_id = pbtc.id\n"
					+ "  left join enum_const ec\n"
					+ "    on ec.id = pbtc.flag_coursetype"
					// **dgh start
					+ "  LEFT JOIN ENUM_CONST ENUMCONSTB3_ ON PBTC.FLAG_COURSECATEGORY =ENUMCONSTB3_.ID\n"
					+ "  left join ENUM_CONST enumconstb4_ on pbtc.FLAG_COURSE_ITEM_TYPE =enumconstb4_.ID\n"
					+ "  left JOIN ENUM_CONST enumconstb5_ ON pbtc.FLAG_CONTENT_PROPERTY = enumconstb5_.ID"
					+
					// **dgh end
					"  left join pe_bzz_student pbs\n"
					+ "    on ele.fk_stu_id = pbs.id\n"
					+ "  left join pe_bzz_order_info pboi\n"
					+ "    on ele.fk_order_id = pboi.id\n"
					+ " where pbtc.FLAG_COURSETYPE='402880f32200c249012200c780c40001' and  pboi.id = '"
					+ order.getId()
					+ "'\n"
					+ userSql
					+ ")";
		}
		List tempList;
		try {
			tempList = this.getGeneralService().getBySQL(sql);
			if (null != tempList && tempList.size() > 0 && null != tempList.get(0)) {
				return tempList.get(0).toString();
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}

		return "0";
	}

	/**
	 * 学生课程报名支付
	 * 
	 * @author NaN
	 * @rewrite linjie 2012-12-25
	 */
	public String paymentCourse_old() {
		this.destorySession();// 清空session信息
		this.initStudent();
		toApplyInvoice();// 初始化发票信息
		try {
			UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);

			// 1.获取选中课程list
			List opencourseIdList = (List) ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList");
			if (opencourseIdList == null || opencourseIdList.size() < 1) {
				this.setMsg("请不要重复提交，如果未支付成功请到“我的订单”中继续操作！");
				this.setTogo("javascript:window.close();");
				return "stu_msg";
			}
			// 2.通过反射拿到开课的实体对象的集合；
			DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
			dc.createCriteria("peBzzTchCourse");
			dc.add(Restrictions.in("id", opencourseIdList.toArray(new String[opencourseIdList.size()])));
			List<PrBzzTchOpencourse> list = this.getGeneralService().getList(dc);
			// 4.得到预付费余额；
			DetachedCriteria sdc = DetachedCriteria.forClass(SsoUser.class);
			sdc.add(Restrictions.eq("id", us.getSsoUser().getId()));
			BigDecimal sumamount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal amount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal subAmount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
			try {
				SsoUser ssoUser = (SsoUser) this.getGeneralService().getList(sdc).get(0);
				amount = new BigDecimal(ssoUser.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
				sumamount = new BigDecimal(ssoUser.getSumAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
				subAmount = sumamount.subtract(amount);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			ServletActionContext.getRequest().setAttribute("sumAmount", sumamount);
			ServletActionContext.getRequest().setAttribute("amount", amount);
			ServletActionContext.getRequest().setAttribute("subAmount", subAmount);

			// 5.往 选课表 和 课程记录表 进行存储；
			// 并且计算出 学时 和 总金额；
			BigDecimal temAmount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal classhour = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
			List<PrBzzTchStuElective> eleList = new ArrayList<PrBzzTchStuElective>();
			this.electiveBackList = new ArrayList();
			for (PrBzzTchOpencourse p : list) {
				PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
				eleBack.setPeBzzStudent(this.peBzzStudent);
				eleBack.setPrBzzTchOpencourse(p);
				eleBack.setSsoUser(us.getSsoUser());
				this.electiveBackList.add(eleBack);
				String eleCheck = this.getGeneralService().checkElective(eleBack);
				if (eleCheck != null && eleCheck.length() > 0) {
					this.setMsg(eleCheck);
					this.setTogo("javascript:window.close();");
					return "stu_msg";
				}
				// BigDecimal price = new
				// BigDecimal(this.getEnumConstService().getByNamespaceCode("ClassHourRate",
				// "0").getName()).setScale(2, BigDecimal.ROUND_HALF_UP);
				temAmount = (temAmount
						.add(new BigDecimal(p.getPeBzzTchCourse().getPrice() == null ? "0" : p.getPeBzzTchCourse().getPrice())).setScale(2,
						BigDecimal.ROUND_HALF_UP));
				classhour = classhour.add(new BigDecimal(p.getPeBzzTchCourse().getTime() == null ? "0" : p.getPeBzzTchCourse().getTime()))
						.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			EnumConst enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "0");// 订单类型，选课订单
			this.amount = String.valueOf(temAmount);
			this.classHour = String.valueOf(classhour);

			this.peBzzOrderInfo = new PeBzzOrderInfo();
			this.peBzzOrderInfo.setSeq(this.getGeneralService().getOrderSeq());
			this.peBzzOrderInfo.setAmount(this.amount);
			this.peBzzOrderInfo.setClassHour(this.classHour);
			this.peBzzOrderInfo.setEnumConstByFlagOrderType(enumConstByFlagOrderType);
			;// 订单类型，选课订单
			/**
			 * 未通过课程在购买，订单标识
			 */
			EnumConst enumConstByFlagOrderIsIncomplete = this.enumConstService.getByNamespaceCode("FlagOrderIsIncomplete", "1");
			if (flag != null) {
				if (flag.equals("incompleteCoursePayment")) {
					peBzzOrderInfo.setEnumConstByFlagOrderIsIncomplete(enumConstByFlagOrderIsIncomplete);
					enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "6");// 订单类型，未通过课程继续购买
					peBzzOrderInfo.setEnumConstByFlagOrderType(enumConstByFlagOrderType); // 覆盖上面的选课订单类型
				}
			}

			ServletActionContext.getRequest().getSession().setAttribute("electiveBackList", electiveBackList);
			ServletActionContext.getRequest().getSession().setAttribute("peBzzOrderInfo", peBzzOrderInfo);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "confirmOrder";
	}

	public String paymentCourse() {
		this.destorySession();// 清空session信息
		this.initStudent();
		toApplyInvoice();// 初始化发票信息
		try {
			UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			StringBuffer sb = new StringBuffer();
			sb.append("    SELECT PBTO.ID   ");
			sb.append("      FROM PR_BZZ_TCH_OPENCOURSE PBTO  ");
			sb.append("      INNER JOIN PE_BZZ_TCH_COURSE PBTC");
			sb.append("     ON PBTO.FK_COURSE_ID = PBTC.ID ");
			sb.append("      INNER JOIN SHOPPING_COURSE SC ON PBTO.ID = SC.FK_OPENCOURSE_ID  ");
			sb.append("      AND PBTO.ID NOT IN ");
			sb
					.append("       (SELECT PBTSE.FK_TCH_OPENCOURSE_ID   FROM (SELECT FK_TCH_OPENCOURSE_ID, FK_STU_ID  FROM PR_BZZ_TCH_STU_ELECTIVE ");
			sb
					.append("     UNION ALL               SELECT FK_TCH_OPENCOURSE_ID, FK_STU_ID                 FROM PR_BZZ_TCH_STU_ELECTIVE_BACK) PBTSE ");
			sb.append("     INNER JOIN PE_BZZ_STUDENT PBS            ON PBTSE.FK_STU_ID = PBS.ID  ");
			sb.append("        WHERE PBS.id  = '" + this.getPeBzzStudent().getId()
					+ "'         AND PBTSE.FK_TCH_OPENCOURSE_ID IS NOT NULL) ");
			sb.append("       WHERE PBTC.FLAG_OFFLINE != '11'  AND PBTC.FLAG_ISVALID = '2'  AND SC.FK_STU_ID ='"
					+ this.getPeBzzStudent().getId() + "' ");
			sb.append("      ");

			// 1.获取选中课程list

			List opencourseIdList = this.getGeneralService().getBySQL(sb.toString());
			if (opencourseIdList == null || opencourseIdList.size() < 1) {
				this.setMsg("请不要重复提交，如果未支付成功请到“我的订单”中继续操作！");
				this.setTogo("javascript:window.close();");
				return "stu_msg";
			}
			// 2.通过反射拿到开课的实体对象的集合；
			DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
			dc.createCriteria("peBzzTchCourse");
			dc.add(Restrictions.in("id", opencourseIdList.toArray(new String[opencourseIdList.size()])));
			List<PrBzzTchOpencourse> list = this.getGeneralService().getList(dc);
			// 4.得到预付费余额；
			DetachedCriteria sdc = DetachedCriteria.forClass(SsoUser.class);
			sdc.add(Restrictions.eq("id", us.getSsoUser().getId()));
			BigDecimal sumamount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal amount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal subAmount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
			try {
				SsoUser ssoUser = (SsoUser) this.getGeneralService().getList(sdc).get(0);
				amount = new BigDecimal(ssoUser.getAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
				sumamount = new BigDecimal(ssoUser.getSumAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
				subAmount = sumamount.subtract(amount);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			ServletActionContext.getRequest().setAttribute("sumAmount", sumamount);
			ServletActionContext.getRequest().setAttribute("amount", amount);
			ServletActionContext.getRequest().setAttribute("subAmount", subAmount);

			// 5.往 选课表 和 课程记录表 进行存储；
			// 并且计算出 学时 和 总金额；
			BigDecimal temAmount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
			BigDecimal classhour = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
			List<PrBzzTchStuElective> eleList = new ArrayList<PrBzzTchStuElective>();
			this.electiveBackList = new ArrayList();
			for (PrBzzTchOpencourse p : list) {
				PrBzzTchStuElectiveBack eleBack = new PrBzzTchStuElectiveBack();
				eleBack.setPeBzzStudent(this.peBzzStudent);
				eleBack.setPrBzzTchOpencourse(p);
				eleBack.setSsoUser(us.getSsoUser());
				this.electiveBackList.add(eleBack);
				String eleCheck = this.getGeneralService().checkElective(eleBack);
				if (eleCheck != null && eleCheck.length() > 0) {
					this.setMsg(eleCheck);
					this.setTogo("javascript:window.close();");
					return "stu_msg";
				}
				// BigDecimal price = new
				// BigDecimal(this.getEnumConstService().getByNamespaceCode("ClassHourRate",
				// "0").getName()).setScale(2, BigDecimal.ROUND_HALF_UP);
				temAmount = (temAmount
						.add(new BigDecimal(p.getPeBzzTchCourse().getPrice() == null ? "0" : p.getPeBzzTchCourse().getPrice())).setScale(2,
						BigDecimal.ROUND_HALF_UP));
				classhour = classhour.add(new BigDecimal(p.getPeBzzTchCourse().getTime() == null ? "0" : p.getPeBzzTchCourse().getTime()))
						.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			EnumConst enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "0");// 订单类型，选课订单
			this.amount = String.valueOf(temAmount);
			this.classHour = String.valueOf(classhour);

			this.peBzzOrderInfo = new PeBzzOrderInfo();
			this.peBzzOrderInfo.setSeq(this.getGeneralService().getOrderSeq());
			this.peBzzOrderInfo.setAmount(this.amount);
			this.peBzzOrderInfo.setClassHour(this.classHour);
			this.peBzzOrderInfo.setEnumConstByFlagOrderType(enumConstByFlagOrderType);
			;// 订单类型，选课订单
			/**
			 * 未通过课程在购买，订单标识
			 */
			EnumConst enumConstByFlagOrderIsIncomplete = this.enumConstService.getByNamespaceCode("FlagOrderIsIncomplete", "1");
			if (flag != null) {
				if (flag.equals("incompleteCoursePayment")) {
					peBzzOrderInfo.setEnumConstByFlagOrderIsIncomplete(enumConstByFlagOrderIsIncomplete);
					enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "6");// 订单类型，未通过课程继续购买
					peBzzOrderInfo.setEnumConstByFlagOrderType(enumConstByFlagOrderType); // 覆盖上面的选课订单类型
				}
			}

			ServletActionContext.getRequest().getSession().setAttribute("electiveBackList", electiveBackList);
			ServletActionContext.getRequest().getSession().setAttribute("peBzzOrderInfo", peBzzOrderInfo);
			ServletActionContext.getRequest().getSession().setAttribute("shoppingIdList", opencourseIdList);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "confirmOrder";
	}

	/**
	 * 专项支付
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String paymentSpecialTraining() {
		this.destorySession();// 清空session信息
		this.initStudent();
		toApplyInvoice();// 初始化发票信息
		try {
			UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
			SsoUser user = us.getSsoUser();
			try {
				DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
				dc.add(Restrictions.eq("id", user.getId()));
				List tmpList = this.getGeneralService().getList(dc);
				if (tmpList.size() > 0) {
					user = (SsoUser) tmpList.get(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 订单验证
			DetachedCriteria sbDc = DetachedCriteria.forClass(StudentBatch.class);
			sbDc.add(Restrictions.eq("peBzzBatch.id", this.getBatchId()));
			sbDc.add(Restrictions.eq("peStudent", this.peBzzStudent));
			sbDc.add(Restrictions.isNotNull("peBzzOrderInfo"));
			List sbListTemp = this.getGeneralService().getList(sbDc);
			if (sbListTemp != null && sbListTemp.size() > 0) {
				this.setMsg("当前专项/直播存在订单，不要重新提交，如果是您本人提交的订单请到“我的订单”中完成支付。");
				return "success";
			}
			// 专项实体
			DetachedCriteria dcBatch = DetachedCriteria.forClass(PeBzzBatch.class);
			dcBatch.add(Restrictions.eq("id", this.getBatchId()));
			PeBzzBatch peBzzBatch = (PeBzzBatch) this.getGeneralService().getList(dcBatch).get(0);

			// 不在报名期才可以支付
			if (!isRightDate(peBzzBatch.getStartDate(), peBzzBatch.getEndDate())) {
				this.setMsg("当前时间不在支付时间段内");
				return "success";
			}

			// 专项价格
			String sql = "SELECT  PBTC.price                         "
					+ "  FROM PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSEB                         "
					+ "  JOIN PR_BZZ_TCH_OPENCOURSE PBTO    "
					+ "    ON PBTSEB.FK_TCH_OPENCOURSE_ID = PBTO.ID                       "
					+ "  JOIN PE_BZZ_TCH_COURSE PBTC                                      "
					+ "    ON PBTO.FK_COURSE_ID = PBTC.ID                                 "
					+ "   JOIN PE_BZZ_STUDENT s on s.id = FK_STU_ID "
					+ " JOIN SSO_USER SU ON SU.ID = s.fk_sso_user_id "
					+ "inner join enum_const vd on PBTC.FLAG_ISVALID =  vd.id and vd.namespace = 'FlagIsvalid' and vd.code = '1'  "
					+ " and PBTC.ID not in    (select PBTC.FK_COURSE_ID as y0_   from PR_BZZ_TCH_STU_ELECTIVE this_   inner join PR_BZZ_TCH_OPENCOURSE PBTC on this_.FK_TCH_OPENCOURSE_ID ="
					+ " PBTC.ID INNER JOIN PE_BZZ_STUDENT PBS ON this_.FK_STU_ID = PBS.ID  where PBS.REG_NO = '"
					+ us.getLoginId()
					+ "')"
					+ " and PBTC.ID not in    (select this_.ID as y0_  from PE_BZZ_TCH_COURSE this_    inner join enum_const ec2 on ec2.namespace ='FlagIsFree'  and ec2.code = '1'   where this_.FLAG_ISFREE = ec2.id)       "
					+ " WHERE PBTO.FK_BATCH_ID = '" + this.getBatchId() + "'" + "   AND SU.LOGIN_ID='" + us.getLoginId() + "'";

			List studentCourselist = this.getGeneralService().getBySQL(sql);
			BigDecimal sumPrice = new BigDecimal("0.00");

			Iterator<String> it = studentCourselist.iterator();
			while (it.hasNext()) {
				sumPrice = sumPrice.add(new BigDecimal(it.next().toString()));
			}
			if (sumPrice.compareTo(BigDecimal.ZERO) <= 0) {
				this.setMsg("订单金额异常，请确认后重试");
				this.setTogo("close");
				return "msg";
			}
			this.amount = sumPrice.toString();

			// 专项学时数
			String sqlTime = "SELECT  TIME                         "
					+ "  FROM PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSEB                         "
					+ "  JOIN PR_BZZ_TCH_OPENCOURSE PBTO                                  "
					+ "    ON PBTSEB.FK_TCH_OPENCOURSE_ID = PBTO.ID                       "
					+ "  JOIN PE_BZZ_TCH_COURSE PBTC                                      "
					+ "    ON PBTO.FK_COURSE_ID = PBTC.ID                                 "
					+ " join PE_BZZ_STUDENT s on s.id = FK_STU_ID  " + " JOIN SSO_USER SU ON SU.ID = s.fk_sso_user_id "
					+ " WHERE PBTO.FK_BATCH_ID = '" + this.getBatchId() + "'" + "   AND SU.LOGIN_ID='" + us.getLoginId() + "'";
			List studentTimelist = this.getGeneralService().getBySQL(sqlTime);
			BigDecimal times = new BigDecimal("0.00");

			Iterator<String> itime = studentTimelist.iterator();
			while (itime.hasNext()) {
				times = times.add(new BigDecimal(itime.next().toString()));
			}
			this.classHour = times.toString();

			// 获取专项选课列表
			DetachedCriteria dcElectiveBack = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
			dcElectiveBack.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse").createAlias("peBzzBatch", "peBzzBatch");
			dcElectiveBack.add(Restrictions.eq("peBzzBatch.id", this.getBatchId()));
			dcElectiveBack.add(Restrictions.eq("peBzzStudent", this.getPeBzzStudent()));
			this.electiveBackList = this.getGeneralService().getDetachList(dcElectiveBack);

			// 获取专项学生对照表
			DetachedCriteria dcStuBatch = DetachedCriteria.forClass(StudentBatch.class);
			dcStuBatch.createAlias("peBzzBatch", "peBzzBatch");
			dcStuBatch.add(Restrictions.eq("peStudent", this.getPeBzzStudent()));
			dcStuBatch.add(Restrictions.eq("peBzzBatch.id", this.getBatchId()));
			List<StudentBatch> stuBatchList = this.getGeneralService().getDetachList(dcStuBatch);

			try {
				EnumConst enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "2");// 订单类型，专项订单
				Object orderTypeString = ServletActionContext.getRequest().getAttribute("orderTypeString");
				if (null != orderTypeString && !"".equals(orderTypeString)) {
					enumConstByFlagOrderType = this.getEnumConstService().getByNamespaceCode("FlagOrderType", "7");// 订单类型，视频直播订单
				}
				this.peBzzOrderInfo = new PeBzzOrderInfo();
				this.peBzzOrderInfo.setSeq(this.getGeneralService().getOrderSeq());
				this.peBzzOrderInfo.setAmount(this.amount);
				this.peBzzOrderInfo.setPeBzzBatch(peBzzBatch);
				this.peBzzOrderInfo.setEnumConstByFlagOrderType(enumConstByFlagOrderType); // 订单类型，专项订单、视频直播订单
			} catch (EntityException e) {
				e.printStackTrace();
			}

			BigDecimal b1 = new BigDecimal(user.getSumAmount());
			BigDecimal b2 = new BigDecimal(user.getAmount());
			BigDecimal subAmount = b1.subtract(b2); // new
			ServletActionContext.getRequest().setAttribute("subAmount", subAmount.setScale(2, BigDecimal.ROUND_HALF_UP));

			ServletActionContext.getRequest().getSession().setAttribute("peBzzOrderInfo", peBzzOrderInfo);
			ServletActionContext.getRequest().getSession().setAttribute("electiveBackList", electiveBackList);
			ServletActionContext.getRequest().getSession().setAttribute("stuBatchList", stuBatchList);
			ServletActionContext.getRequest().getSession().setAttribute("peBzzBatch", peBzzBatch);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "confirmOrder";
	}

	/**
	 * 支付订单
	 * 
	 * @param endDate
	 * @author linjie dgh
	 */
	public String confirmOrder() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		this.initStudent();
		// 把购物课程表中信息改为已支付
		studentWorkspaceService.updateShoppingCourse(this.peBzzStudent.getId());
		String message = "";
		SsoUser ssoUser = null;
		this.initStudent();
		if (this.paymentMethod == null || "".equalsIgnoreCase(this.paymentMethod) || "0,1,2,3".indexOf(this.paymentMethod) < 0) {
			this.setMsg("请正确选择支付方式！");
			this.setTogo("close");
			return "stuPreAccountPaied";
		}
		// 获得托管状态SsoUser
		try {
			ssoUser = this.getGeneralService().initSsoUser(us);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		PeBzzOrderInfo _peBzzOrderInfo = (PeBzzOrderInfo) ActionContext.getContext().getSession().get("peBzzOrderInfo");
		if (null != _peBzzOrderInfo && null != _peBzzOrderInfo.getEnumConstByFlagOrderType()
				&& null != _peBzzOrderInfo.getEnumConstByFlagOrderType().getCode()
				&& "7".equalsIgnoreCase(_peBzzOrderInfo.getEnumConstByFlagOrderType().getCode())) {
			// 收费直播订单检查报名人数上限
			String bid = _peBzzOrderInfo.getPeBzzBatch().getId();
			String maxSql = "SELECT 1 FROM PE_BZZ_TCH_COURSE PBTC INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID AND PBTO.FK_BATCH_ID = '"
					+ bid
					+ "' INNER JOIN PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSE ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID AND PBTSE.FK_ORDER_ID IS NOT NULL GROUP BY PBTC.MAXSTUS, PBTC.ID HAVING PBTC.MAXSTUS - NVL(COUNT(PBTSE.ID), 0) < 1";
			try {
				List maxList = this.getGeneralService().getBySQL(maxSql);
				if (null != maxList && maxList.size() > 0) {
					this.setMsg("报名人数已达上限，谢谢关注！");
					this.setTogo("javascript:window.close();");
					return "msg";
				}
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 初始化订单
		this.initOrderAndElective();
		EnumConst enumConstByClassHourRate = this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");//

		if (paymentMethod.equals("0")) { // 在线支付
			// 实例化订单
			try {
				this.generateOrder(ssoUser);
			} catch (EntityException e) {
				e.printStackTrace();
				this.destoryElectiveOrder();
				this.setMsg("订单生成失败，请重试。");
				return "stuPreAccountPaied";
			}
			ServletActionContext.getRequest().getSession().setAttribute(this.peBzzOrderInfo.getSeq(), "stu");
			// Lee 新增快钱支付
			String paymentType = ServletActionContext.getRequest().getParameter("paymentType");
			ServletActionContext.getRequest().getSession().setAttribute("paymentType", paymentType);
			return "onlinePayment";
		} else if (paymentMethod.equals("1")) {// 预付费支付
			BigDecimal subAmount = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
			String orderType = "0";
			if (this.peBzzOrderInfo.getEnumConstByFlagOrderType() != null) {
				orderType = this.peBzzOrderInfo.getEnumConstByFlagOrderType().getCode();
			}
			// 空指针异常 zgl
			String am = (peBzzOrderInfo.getAmount() == null || "".equals(peBzzOrderInfo.getAmount())) ? "0" : peBzzOrderInfo.getAmount();
			subAmount = new BigDecimal(am);// 2014-07-08
			BigDecimal classhour = new BigDecimal(peBzzOrderInfo.getClassHour());
			// 金额为订单金额，无需按比例算，暂时注释
			// 改成学时
			BigDecimal amount = new BigDecimal(ssoUser.getAmount()).add(classhour).setScale(2, BigDecimal.ROUND_HALF_UP);
			if (new BigDecimal(ssoUser.getSumAmount()).compareTo(amount) == -1) {
				this.setMsg("预付费账户余额不足");
				return "msg";
			} else {
				String so_amount = (ssoUser.getAmount() == null || ssoUser.getAmount().equals("")) ? "0" : ssoUser.getAmount();
				// 改成学时
				ssoUser.setAmount(new BigDecimal(so_amount).add(classhour).toString());
				// 实例化订单
				try {
					this.generateOrder(ssoUser);
					this.getGeneralService().updatePeBzzOrderInfo(peBzzOrderInfo, "confirm", ssoUser);

					BigDecimal zhyAmountnew = new BigDecimal(ssoUser.getSumAmount()).subtract(new BigDecimal(ssoUser.getAmount()));// 账户余额

					// 分别插入其他两张表 dgh
					EnumConst enumConstByFlagOperateType = this.getEnumConstService().getByNamespaceCode("FlagOperateType", "5"); // 金额分配记录
					List saveList = new ArrayList();
					AssignRecord assignRecord = new AssignRecord();
					assignRecord.setSsoUser(ssoUser);
					assignRecord.setAssignStyle("operate5");
					assignRecord.setAssignDate(new Date());
					// assignRecord.setEnumConstByFlagRecordAssignMethod(enumConstByFlagRecordAssignMethod);
					// 空值
					assignRecord.setAccountAmount(zhyAmountnew.toString());
					// 改成学时
					assignRecord.setOperateAmount(classhour.toString());
					assignRecord.setEnumConstByFlagOperateType(enumConstByFlagOperateType);
					assignRecord.setPeBzzOrderInfo(peBzzOrderInfo);
					saveList.add(assignRecord);

					this.getGeneralService().saveList(saveList);

				} catch (EntityException e) {
					e.printStackTrace();
					this.destoryElectiveOrder();
					this.setMsg("订单生成失败，请重试。");
					return "stuPreAccountPaied";
				}
				message = "支付成功";

			}
		}

		// /**
		// * 未通过课程在购买，订单标识
		// */
		// EnumConst enumConstByFlagOrderIsIncomplete =
		// this.enumConstService.getByNamespaceCode("FlagOrderIsIncomplete",
		// "1");
		// if(ServletActionContext.getRequest().getSession().getAttribute("incompleteCoursePayment")
		// != null) {
		// peBzzOrderInfo.setEnumConstByFlagOrderIsIncomplete(enumConstByFlagOrderIsIncomplete);
		// }

		// /**
		// * 20130102添加，用于标识是否为购买未完成课程订单
		// */
		// String flag = "";
		// if(ServletActionContext.getRequest().getSession().getAttribute("incompleteCoursePayment")!=null)
		// {
		// flag =
		// (String)ServletActionContext.getRequest().getSession().getAttribute("incompleteCoursePayment");
		// ServletActionContext.getRequest().getSession().removeAttribute("incompleteCoursePayment");
		// }
		// try {
		// if(isInvoice == null || "0".equals(isInvoice)) {
		// this.studentWorkspaceService.saveOrderInvoiceEletive(peBzzOrderInfo,
		// null, eleList,stuBatchList, flag);
		// } else {
		// this.studentWorkspaceService.saveOrderInvoiceEletive(peBzzOrderInfo,
		// peBzzInvoiceInfo, eleList,stuBatchList, flag);
		// }
		// } catch (EntityException e) {
		// e.printStackTrace();
		// this.setMsg("支付失败");
		// return "stuPreAccountPaied";
		// }
		this.destoryElectiveOrder();
		this.setMsg(message);
		this.setTogo("newClose");
		return "stuPreAccountPaied";
	}

	/**
	 * 初始化要保存的订单和选课信息
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public void initOrderAndElective() {
		this.initStudent();
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);

		EnumConst enumConstByFlagPaymentMethod = this.getEnumConstService().getByNamespaceCode("FlagPaymentMethod", this.paymentMethod);// 支付方式
		EnumConst enumConstByFlagPaymentType = this.getEnumConstService().getByNamespaceCode("FlagPaymentType", "0");// 支付类型，个人支付
		EnumConst enumConstByFlagOrderState = this.getEnumConstService().getByNamespaceCode("FlagOrderState", "1");// 订单状态，正常
		EnumConst enumConstByFlagPaymentState = this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");// 未支付;
		EnumConst enumConstByFlagBatchPayState = this.getEnumConstService().getByNamespaceCode("FlagBatchPayState", "0");// 专项，未支付
		EnumConst enumConstByFlagOrderIsValid = this.enumConstService.getByNamespaceCode("FlagOrderIsValid", "1"); // 订单有效状态，初始为有效
//		EnumConst enumConstByFlagFpOpenState = this.getEnumConstService().getByNamespaceCode("FlagFpOpenState", "0");// 待开发票
		EnumConst enumConstByFlagCheckState = this.getEnumConstService().getByNamespaceCode("FlagCheckState", "0");// 对账状态
		// 是否申请发票
		/**
		 * 如果直接从session中取peBzzInvoiceInfo或peBzzOrderInfo 会将本次传参覆盖掉，
		 */
//		PeBzzInvoiceInfo _peBzzInvoiceInfo = (PeBzzInvoiceInfo) ActionContext.getContext().getSession().get("peBzzInvoiceInfo");
		PeBzzOrderInfo _peBzzOrderInfo = (PeBzzOrderInfo) ActionContext.getContext().getSession().get("peBzzOrderInfo");
		if (_peBzzOrderInfo != null) {
			_peBzzOrderInfo.setCname(peBzzOrderInfo.getCname());
			_peBzzOrderInfo.setNum(peBzzOrderInfo.getNum());
			peBzzOrderInfo = _peBzzOrderInfo;
		}
//		if ("1".equals(this.isInvoice) && !"1".equalsIgnoreCase(this.paymentMethod)) {// 选中开发票，并且不是预付费支付
//			if (_peBzzInvoiceInfo != null) {
//				_peBzzInvoiceInfo.setAddress(peBzzInvoiceInfo.getAddress());
//				_peBzzInvoiceInfo.setAddressee(peBzzInvoiceInfo.getAddressee());
//				_peBzzInvoiceInfo.setNum(peBzzInvoiceInfo.getNum());
//				_peBzzInvoiceInfo.setPhone(peBzzInvoiceInfo.getPhone());
//				_peBzzInvoiceInfo.setTitle(peBzzInvoiceInfo.getTitle());
//				_peBzzInvoiceInfo.setZipCode(peBzzInvoiceInfo.getZipCode());
//				peBzzInvoiceInfo = _peBzzInvoiceInfo;
//			}
//			peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);// 待开发票
//			peBzzInvoiceInfo.setPeBzzOrderInfo(peBzzOrderInfo);
//		} else {
//			peBzzInvoiceInfo = null;
//		}

		this.peBzzOrderInfo.setEnumConstByFlagOrderIsValid(enumConstByFlagOrderIsValid); // 设为有效，初始化订单
		this.peBzzOrderInfo.setEnumConstByFlagPaymentMethod(enumConstByFlagPaymentMethod);
		this.peBzzOrderInfo.setEnumConstByFlagPaymentType(enumConstByFlagPaymentType);
		this.peBzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
		this.peBzzOrderInfo.setEnumConstByFlagOrderState(enumConstByFlagOrderState);
		this.peBzzOrderInfo.setEnumConstByFlagCheckState(enumConstByFlagCheckState);
		this.peBzzOrderInfo.setTel(this.peBzzStudent.getPhone());
		this.peBzzOrderInfo.setCreateDate(new Date());
		this.peBzzOrderInfo.setPayer(this.peBzzStudent.getTrueName());

		String orderType = "0";
		if (this.peBzzOrderInfo.getEnumConstByFlagOrderType() != null) {
			orderType = this.peBzzOrderInfo.getEnumConstByFlagOrderType().getCode();
		}

		// 课程表名
		if ("0".equals(orderType) || "6".equals(orderType)) { // 20130105修改，未通过课程购买走课程报名一样流程
			electiveBackList = (List<PrBzzTchStuElectiveBack>) ServletActionContext.getRequest().getSession().getAttribute(
					"electiveBackList");
			// 专项支付、视频直播支付
		} else if ("2".equals(orderType) || "7".equals(orderType)) {
			peBzzBatch = (PeBzzBatch) ServletActionContext.getRequest().getSession().getAttribute("peBzzBatch");

			DetachedCriteria dcElective = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
			dcElective.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
			dcElective.add(Restrictions.eq("prBzzTchOpencourse.peBzzBatch", peBzzBatch));
			dcElective.add(Restrictions.eq("peBzzStudent", this.peBzzStudent));

			try {
				electiveBackList = this.getGeneralService().getDetachList(dcElective);
			} catch (EntityException e) {
				e.printStackTrace();
			}

			DetachedCriteria sbDc = DetachedCriteria.forClass(StudentBatch.class);
			sbDc.createCriteria("peStudent", "peStudent");
			sbDc.createCriteria("peBzzBatch", "peBzzBatch");
			sbDc.add(Restrictions.eq("peStudent", peBzzStudent));
			sbDc.add(Restrictions.eq("peBzzBatch", peBzzBatch));

			try {
				listSb = this.getGeneralService().getDetachList(sbDc);
				for (int i = 0; i < listSb.size(); i++) {
					StudentBatch sb = listSb.get(i);
					sb.setPeBzzOrderInfo(peBzzOrderInfo);
					sb.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);
					listSb.set(i, sb);
				}
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 实例化订单
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public void generateOrder(SsoUser user) throws EntityException {
		// 邮寄方式
		String postType = ServletActionContext.getRequest().getParameter("postType");
		EnumConst enumConstByFlagPostType = this.getEnumConstService().getByNamespaceCode("FlagPostType", postType);
		if(peBzzInvoiceInfo != null && !"".equals(peBzzInvoiceInfo)) {
			this.peBzzInvoiceInfo.setEnumConstByFlagPostType(enumConstByFlagPostType);
		}
		this.peBzzOrderInfo.setSsoUser(user);
		try {
			this.electiveBackList = this.getGeneralService().saveElectiveBackList(this.electiveBackList, peBzzOrderInfo, peBzzInvoiceInfo,
					null, null, this.listSb);
		} catch (EntityException e) {
			e.printStackTrace();
		}
	}

	// /**
	// * 第三方支付确认
	// * @return
	// */
	// public String confirmOnlinePayment(){
	// this.initStudent();
	// PeBzzOrderInfo order = null;
	//
	// DetachedCriteria orderDc =
	// DetachedCriteria.forClass(PeBzzOrderInfo.class);
	// orderDc.createCriteria("enumConstByFlagPaymentState",
	// "enumConstByFlagPaymentState");
	// orderDc.createCriteria("enumConstByFlagOrderIsIncomplete",
	// DetachedCriteria.LEFT_JOIN);
	// orderDc.add(Restrictions.eq("seq", this.merorderid));
	//
	// try {
	// order = (PeBzzOrderInfo)this.getGeneralService().getList(orderDc).get(0);
	// this.getGeneralService().updatePeBzzOrderInfo(order, "confirm", null);
	// //this.studentWorkspaceService.saveOrderInvoiceEletive(order, null,
	// eleList, stuBatchList, flag);
	// } catch (EntityException e) {
	// e.printStackTrace();
	// this.setMsg("支付失败");
	// return "stuOnlinePaied";
	// }
	// ServletActionContext.getRequest().setAttribute("flag", "onlinePayment");
	// this.destoryElectiveOrder();
	// this.setMsg("支付成功");
	// return "stuOnlinePaied";
	// }
	// /**
	// * 课程报名提交订单
	// * @return
	// */
	// public String submitOrder() {
	// this.initStudent();
	// String message ="";
	// UserSession us = (UserSession) ActionContext.getContext()
	// .getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
	// HttpServletRequest request = ServletActionContext.getRequest();
	// //1.获得支付方式
	// String paymentMethod = request.getParameter("paymentMethod");
	// String orderSeq = "";
	// try {
	// orderSeq = this.getGeneralService().getOrderSeq();
	// } catch (EntityException e1) {
	// e1.printStackTrace();
	// }
	// EnumConst enumConstByFlagPaymentMethod =
	// this.getEnumConstService().getByNamespaceCode("FlagPaymentMethod",
	// paymentMethod);//支付方式
	// EnumConst enumConstByFlagPaymentType =
	// this.getEnumConstService().getByNamespaceCode("FlagPaymentType",
	// "0");//支付类型，个人支付
	// EnumConst enumConstByFlagOrderState =
	// this.getEnumConstService().getByNamespaceCode("FlagOrderState",
	// "1");//订单状态，正常
	// EnumConst enumConstByClassHourRate =
	// this.getEnumConstService().getByNamespaceCode("ClassHourRate", "0");//
	// EnumConst enumConstByFlagPaymentState = null;//支付状态
	// EnumConst enumConstByFlagElectivePayStatus = null;//选课支付状态
	// EnumConst enumConstByFlagOrderType = null;
	// EnumConst enumConstByFlagFpOpenState =
	// this.getEnumConstService().getByNamespaceCode("FlagFpOpenState",
	// "0");//待开
	//
	// String orderType =
	// (String)ServletActionContext.getRequest().getSession().getAttribute("orderType");
	// if(orderType!=null && orderType.equals("2")) {
	// enumConstByFlagOrderType =
	// this.getEnumConstService().getByNamespaceCode("FlagOrderType",
	// "2");//订单类型，专项支付
	// ServletActionContext.getRequest().getSession().setAttribute(orderSeq,
	// "batchPayment");
	// } else {
	// enumConstByFlagOrderType =
	// this.getEnumConstService().getByNamespaceCode("FlagOrderType",
	// "0");//订单类型，选课订单
	// ServletActionContext.getRequest().getSession().setAttribute(orderSeq,
	// "stuElePayment");
	// }
	// EnumConst enumConstByFlagBatchPayState = null;//学生专项培训支付状态
	// peBzzOrderInfo =
	// (PeBzzOrderInfo)request.getSession().getAttribute("peBzzOrderInfo");
	// peBzzOrderInfo.setCreateDate(new Date());
	// DetachedCriteria dcSsouser = DetachedCriteria.forClass(SsoUser.class);
	// dcSsouser.add(Restrictions.eq("id", us.getSsoUser().getId()));
	// SsoUser ssoUser = us.getSsoUser();
	// try {
	// ssoUser = (SsoUser)
	// this.getGeneralService().getDetachList(dcSsouser).get(0);
	// } catch (EntityException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// if(paymentMethod.equals("1")) {//预付费支付
	// enumConstByFlagPaymentState =
	// this.getEnumConstService().getByNamespaceCode("FlagPaymentState",
	// "1");//到账情况,已到账
	// enumConstByFlagElectivePayStatus =
	// this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus",
	// "1"); //选课支付状态,已支付
	// enumConstByFlagBatchPayState =
	// this.getEnumConstService().getByNamespaceCode("FlagBatchPayState",
	// "1");//专项培训支付情况,已支付
	//
	// BigDecimal subAmount = new BigDecimal(0).setScale(2,
	// BigDecimal.ROUND_HALF_UP);
	//
	// if(orderType!=null && orderType.equals("2")){
	// subAmount = new BigDecimal(peBzzOrderInfo.getClassHour()).setScale(2,
	// BigDecimal.ROUND_HALF_UP);
	// }else{
	// subAmount = new BigDecimal(peBzzOrderInfo.getAmount()).divide(new
	// BigDecimal(enumConstByClassHourRate.getName())).setScale(2,
	// BigDecimal.ROUND_HALF_UP);
	// }
	// BigDecimal amount = new
	// BigDecimal(ssoUser.getAmount()).add(subAmount).setScale(2,
	// BigDecimal.ROUND_HALF_UP);
	// if(new BigDecimal(ssoUser.getSumAmount()).compareTo(amount)==-1) {
	// // if (Double.parseDouble(ssoUser.getSumAmount()) < amount) {
	// this.setMsg("预付费账户余额不足");
	// return "success";
	// }
	// String so_amount = (ssoUser.getAmount()==null ||
	// ssoUser.getAmount().equals("")) ? "0" : ssoUser.getAmount();
	// ssoUser.setAmount(new BigDecimal(so_amount).add(subAmount).toString());
	// // ssoUser.setAmount(amount+"");
	// peBzzOrderInfo.setSsoUser(ssoUser);
	// peBzzOrderInfo.setPaymentDate(new Date());
	// message = "支付成功";
	// } else { //网银支付
	// enumConstByFlagPaymentState =
	// this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");
	// //到账情况 0未到账
	// enumConstByFlagElectivePayStatus =
	// this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus",
	// "0"); //选课支付状态 0未到账
	// enumConstByFlagBatchPayState =
	// this.getEnumConstService().getByNamespaceCode("FlagBatchPayState", "0");
	// //专项支付情况 //0未到账
	// peBzzOrderInfo.setSsoUser(ssoUser);
	// message = "支付成功";
	// }
	//
	// /**
	// * 支付时，添加订单的有效状态，默认为有效
	// */
	// EnumConst enumConstByFlagOrderIsValid =
	// this.enumConstService.getByNamespaceCode("FlagOrderIsValid", "1");
	// //订单有效状态，有效
	// peBzzOrderInfo.setEnumConstByFlagOrderIsValid(enumConstByFlagOrderIsValid);
	// //后来添加,订单的有效状态
	// peBzzOrderInfo.setEnumConstByFlagPaymentMethod(enumConstByFlagPaymentMethod);
	// peBzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
	// peBzzOrderInfo.setEnumConstByFlagPaymentType(enumConstByFlagPaymentType);
	// peBzzOrderInfo.setEnumConstByFlagOrderState(enumConstByFlagOrderState);
	// peBzzOrderInfo.setEnumConstByFlagOrderType(enumConstByFlagOrderType);
	// peBzzOrderInfo.setPayer(this.peBzzStudent.getTrueName());
	// peBzzOrderInfo.setSeq(orderSeq);
	// peBzzInvoiceInfo = (PeBzzInvoiceInfo)
	// request.getSession().getAttribute("peBzzInvoiceInfo");
	// peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(enumConstByFlagFpOpenState);
	// List<PrBzzTchStuElective> eleList =
	// (List<PrBzzTchStuElective>)request.getSession().getAttribute("eleList");
	// String isInvoice = (String)
	// request.getSession().getAttribute("isInvoice");
	//
	// //循环设置选课支付状态
	// for(PrBzzTchStuElective ele : eleList) {
	// ele.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
	// }
	// List<StudentBatch> stuBatchList = null;
	// if(ServletActionContext.getRequest().getSession().getAttribute("stuBatchList")!=null){
	// stuBatchList = (List<StudentBatch>)
	// ServletActionContext.getRequest().getSession().getAttribute("stuBatchList");
	// for(StudentBatch stuBatch : stuBatchList) {
	// stuBatch.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);
	// stuBatch.setPeBzzOrderInfo(peBzzOrderInfo);
	// }
	// }
	// ServletActionContext.getRequest().getSession().setAttribute("peBzzOrderInfo",
	// this.peBzzOrderInfo);
	// ServletActionContext.getRequest().getSession().setAttribute("peBzzInvoiceInfo",
	// this.peBzzInvoiceInfo);
	// ServletActionContext.getRequest().getSession().setAttribute("eleList",
	// eleList);
	// if (paymentMethod.equals("0")) {
	// if(orderType!=null && orderType.equals("2")) { //专项支付
	// this.studentWorkspaceService.saveElectiveCourseAndPebzzOrderInfo(eleList,
	// peBzzOrderInfo, peBzzInvoiceInfo, stuBatchList);
	// } else {
	// this.generateOrder(); //学生选课支付
	// }
	// if
	// (ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList")
	// != null) {
	// ServletActionContext.getRequest().getSession().removeAttribute("opencourseIdList");
	// }
	// return "onlinePayment";
	// }
	// /**
	// * 20130102添加，用于标识是否为购买未完成课程订单
	// */
	// String flag = "";
	// if(ServletActionContext.getRequest().getSession().getAttribute("incompleteCoursePayment")!=null)
	// {
	// flag =
	// (String)ServletActionContext.getRequest().getSession().getAttribute("incompleteCoursePayment");
	// ServletActionContext.getRequest().getSession().removeAttribute("incompleteCoursePayment");
	// }
	// try {
	// if(isInvoice.equals("0") && !paymentMethod.equals("1")) {
	// this.studentWorkspaceService.saveOrderInvoiceEletive(peBzzOrderInfo,
	// null, eleList,stuBatchList, flag);
	// } else {
	// this.studentWorkspaceService.saveOrderInvoiceEletive(peBzzOrderInfo,
	// peBzzInvoiceInfo, eleList,stuBatchList, flag);
	// }
	// } catch (EntityException e) {
	// e.printStackTrace();
	// this.setMsg("支付失败");
	// return "stuPreAccountPaied";
	// }
	// this.destoryElectiveOrder();
	// this.setMsg(message);
	// return "stuPreAccountPaied";
	// }
	// /**
	// * 第三方支付，生成选课临时记录
	// */
	// public void generateOrder() {
	// String isInvoice = (String)
	// ServletActionContext.getRequest().getSession().getAttribute("isInvoice");
	// peBzzOrderInfo =
	// (PeBzzOrderInfo)ServletActionContext.getRequest().getSession().getAttribute("peBzzOrderInfo");
	// peBzzInvoiceInfo = (PeBzzInvoiceInfo)
	// ServletActionContext.getRequest().getSession().getAttribute("peBzzInvoiceInfo");
	// List<PrBzzTchStuElective> eleList =
	// (List<PrBzzTchStuElective>)ServletActionContext.getRequest().getSession().getAttribute("eleList");
	// List<StudentBatch> stuBatchList =
	// (List<StudentBatch>)ServletActionContext.getRequest().getSession().getAttribute("stuBatchList");
	// //EnumConst enumConstByFlagPaymentState =
	// this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "0");
	// //EnumConst enumConstByFlagElectivePayStatus =
	// this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus",
	// "0");
	// //EnumConst enumConstByFlagBatchPayState =
	// this.getEnumConstService().getByNamespaceCode("FlagBatchPayState", "0");
	// //
	// this.peBzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
	// this.peBzzOrderInfo.setNum(this.getPeBzzOrderInfo().getSeq());
	// /*if(ServletActionContext.getRequest().getSession().getAttribute("stuBatchList")!=null){
	// stuBatchList = (List<StudentBatch>)
	// ServletActionContext.getRequest().getSession().getAttribute("stuBatchList");
	// for(StudentBatch sb : stuBatchList) {
	// sb.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);
	// }
	// }*/
	// try {
	// if(isInvoice.equals("0")) {
	// this.studentWorkspaceService.saveOrderInvoiceEletiveBack(peBzzOrderInfo,
	// null, eleList,stuBatchList);
	// } else {
	// this.studentWorkspaceService.saveOrderInvoiceEletiveBack(peBzzOrderInfo,
	// peBzzInvoiceInfo, eleList,stuBatchList);
	// }
	// } catch (EntityException e) {
	// e.printStackTrace();
	// }
	// }
	// /**
	// * 第三方支付确认
	// * @return
	// */
	// public String confirmOnlinePayment(){
	// this.initStudent();
	//
	// EnumConst enumConstByFlagPaymentState =
	// this.getEnumConstService().getByNamespaceCode("FlagPaymentState", "1");
	// //订单支付装填，已支付
	// EnumConst enumConstByFlagElectivePayStatus =
	// this.getEnumConstService().getByNamespaceCode("FlagElectivePayStatus",
	// "1");//选课支付状态，已支付
	// EnumConst enumConstByFlagBatchPayState =
	// this.getEnumConstService().getByNamespaceCode("FlagBatchPayState",
	// "1");//专项支付状态，已支付
	// /**
	// * 根据易宝返回的订单id号来查询订单
	// */
	// DetachedCriteria orderDc =
	// DetachedCriteria.forClass(PeBzzOrderInfo.class);
	// orderDc.createCriteria("enumConstByFlagPaymentState",
	// "enumConstByFlagPaymentState");
	// orderDc.createCriteria("enumConstByFlagOrderIsIncomplete",
	// DetachedCriteria.LEFT_JOIN);
	// orderDc.add(Restrictions.eq("seq", this.merorderid));
	// PeBzzOrderInfo order = null;
	// try {
	// order = (PeBzzOrderInfo)this.getGeneralService().getList(orderDc).get(0);
	// } catch (EntityException e2) {
	// // TODO Auto-generated catch block
	// e2.printStackTrace();
	// }
	// order.setPaymentDate(new Date());
	// order.setNum(order.getSeq());
	// order.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
	// /**
	// * 根据查询的订单来查询备份的选课信息
	// */
	// DetachedCriteria eleBackDc =
	// DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
	// eleBackDc.createCriteria("ssoUser", "ssoUser");
	// eleBackDc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
	// eleBackDc.createCriteria("peBzzStudent", "peBzzStudent");
	// eleBackDc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
	// eleBackDc.add(Restrictions.eq("peBzzOrderInfo", order));
	// List<PrBzzTchStuElectiveBack> eleBackList = null;
	// try {
	// eleBackList = this.getGeneralService().getList(eleBackDc);
	// } catch (EntityException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// /**
	// * 根据订单来查找专项关联信息
	// */
	// DetachedCriteria stuBatchDc =
	// DetachedCriteria.forClass(StudentBatch.class);
	// stuBatchDc.add(Restrictions.eq("peBzzOrderInfo", order));
	// stuBatchDc.createCriteria("enumConstByFlagBatchPayState",
	// "enumConstByFlagBatchPayState");
	// List<StudentBatch> stuBatchList = null;
	// try {
	// stuBatchList = this.getGeneralService().getList(stuBatchDc);
	// } catch (EntityException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// for(StudentBatch sb : stuBatchList) {
	// sb.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);
	// }
	// /**
	// * 恢复选课信息
	// */
	// List eleList = new ArrayList();
	// for(PrBzzTchStuElectiveBack back : eleBackList) {
	// /**
	// * 将备份表中的数据导入到选课正式表中
	// */
	// PrBzzTchStuElective ele = new PrBzzTchStuElective();
	// ele.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
	// ele.setElectiveDate(new Date());
	// ele.setPeBzzOrderInfo(order);
	// ele.setPeBzzStudent(back.getPeBzzStudent());
	// ele.setPrBzzTchOpencourse(back.getPrBzzTchOpencourse());
	// ele.setSsoUser(back.getSsoUser());
	// /**
	// * 课程状态记录
	// */
	// TrainingCourseStudent trainingCourseStudent = new
	// TrainingCourseStudent();
	// trainingCourseStudent.setPrBzzTchOpencourse(back.getPrBzzTchOpencourse());
	// trainingCourseStudent.setSsoUser(peBzzStudent.getSsoUser());
	// trainingCourseStudent.setPercent(0.00);
	// trainingCourseStudent.setLearnStatus(StudyProgress.UNCOMPLETE);
	// ele.setTrainingCourseStudent(trainingCourseStudent);
	//
	// eleList.add(ele);
	// /*
	// * 去重复
	//
	// List eListBack = new ArrayList();
	// eListBack.addAll(eleList);
	// for(Object o : eListBack) {
	// PrBzzTchStuElective e = (PrBzzTchStuElective)o;
	// if(!(e.getPeBzzStudent().getId().equals(ele.getPeBzzStudent().getId()) &&
	// e.getPrBzzTchOpencourse().getId().equals(ele.getPrBzzTchOpencourse().getId())))
	// {
	// eleList.add(ele);
	// }
	// }
	// */ }
	//
	// Set<String> set = new HashSet<String>();
	// List resultList = new ArrayList();
	// for(int i=0;i<eleList.size();i++){
	// PrBzzTchStuElective temp = (PrBzzTchStuElective)eleList.get(i);
	// String stuId = temp.getPeBzzStudent().getId() +
	// temp.getPrBzzTchOpencourse().getId();
	// if(!set.contains(stuId)){
	// set.add(stuId);
	// resultList.add(temp);
	// }
	// }
	// eleList = resultList;
	// String flag = "";
	// if(order.getEnumConstByFlagOrderIsIncomplete()!=null) {
	// flag = "incompleteCoursePayment";
	// }
	// try {
	// this.studentWorkspaceService.saveOrderInvoiceEletive(order, null,
	// eleList, stuBatchList, flag);
	// } catch (EntityException e) {
	// e.printStackTrace();
	// this.setMsg("支付失败");
	// return "stuOnlinePaied";
	// }
	// ServletActionContext.getRequest().setAttribute("flag", "onlinePayment");
	// this.destoryElectiveOrder();
	// this.setMsg("支付成功");
	// return "stuOnlinePaied";
	// }

	/**
	 * 欢迎界面
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public void welcome_old() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.peBzzStudent = this.studentWorkspaceService.initStudentInfo(ssoUser.getId());
		try {
			List znxList = this.studentWorkspaceService.getNum("zhanneixin", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.zhanneixinNum = (znxList == null || znxList.size() == 0) ? "0" : znxList.get(0).toString();
			List tzggList = this.studentWorkspaceService.getNum("tongzhigonggao", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.tongzhigonggaoNum = (tzggList == null || tzggList.size() == 0) ? "0" : tzggList.get(0).toString();
			List dcwjList = this.studentWorkspaceService.getNum("diaochawenjuan", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.diaochawenjuanNum = (dcwjList == null || dcwjList.size() == 0) ? "0" : dcwjList.get(0).toString();
			List wzfddList = this.studentWorkspaceService.getNum("weizhifudingdan", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.weizhifudingdanNum = (wzfddList == null || wzfddList.size() == 0) ? "0" : wzfddList.get(0).toString();
			List xkqList = this.studentWorkspaceService.getNum("xuankeqi", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.xuankeqiNum = (xkqList == null || xkqList.size() == 0) ? "0" : xkqList.get(0).toString();
			List yffzhyeList = this.studentWorkspaceService.getNum("yufufeizhanghuyue", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.yufufeizhanghuyueNum = (yffzhyeList == null || yffzhyeList.size() == 0) ? new BigDecimal(0) : new BigDecimal(yffzhyeList
					.get(0).toString());
			List zzxxkcList = this.studentWorkspaceService.getNum("zhengzaixuexikecheng", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.zhengzaixuexikechengNum = (zzxxkcList == null || zzxxkcList.size() == 0 || ((Object[]) zzxxkcList.get(0))[0] == null) ? "0"
					: ((Object[]) zzxxkcList.get(0))[0].toString();
			this.zhengzaixuexikechengTime = (zzxxkcList == null || zzxxkcList.size() == 0 || ((Object[]) zzxxkcList.get(0))[1] == null) ? new BigDecimal(
					0)
					: new BigDecimal(((Object[]) zzxxkcList.get(0))[1].toString());
			List dkskcList = this.studentWorkspaceService.getNum("daikaoshikecheng", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.daikaoshikechengNum = (dkskcList == null || dkskcList.size() == 0 || ((Object[]) dkskcList.get(0))[0] == null) ? "0"
					: ((Object[]) dkskcList.get(0))[0].toString();
			this.daikaoshikechengTime = (dkskcList == null || dkskcList.size() == 0 || ((Object[]) dkskcList.get(0))[1] == null) ? new BigDecimal(
					0)
					: new BigDecimal(((Object[]) dkskcList.get(0))[1].toString());
			List zhuanxiangpeixunList = this.studentWorkspaceService.getNum("zhuanxiangpeixun", ssoUser.getLoginId(), this.peBzzStudent
					.getId());
			this.zhuanxiangpeixunNum = (zhuanxiangpeixunList == null || zhuanxiangpeixunList.size() == 0) ? "0" : zhuanxiangpeixunList.get(
					0).toString();
			List zaixianzhibokechengList = this.studentWorkspaceService.getNum("zaixianzhibokecheng", ssoUser.getLoginId(),
					this.peBzzStudent.getId());
			this.zaixianzhibokechengNum = (zaixianzhibokechengList == null || zaixianzhibokechengList.size() == 0) ? "0"
					: zaixianzhibokechengList.get(0).toString();
			List zaixiankaoshiList = this.studentWorkspaceService.getNum("zaixiankaoshi", ssoUser.getLoginId(), this.peBzzStudent.getId());
			this.zaixiankaoshiNum = (zaixiankaoshiList == null || zaixiankaoshiList.size() == 0) ? "0" : zaixiankaoshiList.get(0)
					.toString();
			HttpServletRequest request = ServletActionContext.getRequest();
			request.setAttribute("zhanneixinNum", zhanneixinNum);
			request.setAttribute("tongzhigonggaoNum", tongzhigonggaoNum);
			request.setAttribute("diaochawenjuanNum", diaochawenjuanNum);
			request.setAttribute("weizhifudingdanNum", weizhifudingdanNum);
			request.setAttribute("xuankeqiNum", xuankeqiNum);
			request.setAttribute("yufufeizhanghuyueNum", yufufeizhanghuyueNum.setScale(2, BigDecimal.ROUND_DOWN));
			request.setAttribute("zhengzaixuexikechengNum", zhengzaixuexikechengNum);
			request.setAttribute("zhengzaixuexikechengTime", zhengzaixuexikechengTime.setScale(1, BigDecimal.ROUND_HALF_UP));
			request.setAttribute("daikaoshikechengNum", daikaoshikechengNum);
			request.setAttribute("daikaoshikechengTime", daikaoshikechengTime.setScale(1, BigDecimal.ROUND_HALF_UP));
			request.setAttribute("zhuanxiangpeixunNum", zhuanxiangpeixunNum);
			request.setAttribute("zaixianzhibokechengNum", zaixianzhibokechengNum);
			request.setAttribute("zaixiankaoshiNum", zaixiankaoshiNum);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return "welcome";

	}

	/**
	 * begin 2016-03-08 学员端首页 yangcl
	 */
	public String welcome() {
		this.welcome_old();
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String courseId = (String) ServletActionContext.getRequest().getSession().getAttribute("courseId");
		if (courseId != null && !"".equals("courseId")) {
			return "toViewElective";
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		this.initHomePageCourse(); // 初始化首页课程信息
		this.notice = getNoticeData();
		request.setAttribute("userId", userSession.getSsoUser().getId());
		request.setAttribute("notice", notice);// 通知公告
		return "new_welcome";
	}

	public void initHomePageCourse() {
		this.recommendCourseList = this.getRecommendC();// 推荐课程
		//this.seriesCourseList = this.getSeriseCourse_();// 系列课程
		this.publishCourseList = this.getPublicCourse();// 新发布课程
		this.informationList = this.getInformation();// 资料

	}

	public List getRecommendC() {
		List list = null;
		StringBuffer sb = new StringBuffer();
		/** 实时查询数据 */
		/*
		 * sb.append(" select * "); sb.append(" from (select pbtc.id, ");
		 * sb.append(" pbtc.name, "); sb.append(" pbtc.code, "); sb.append("
		 * pbtc.price, "); sb.append(" pbtc.teacher, "); sb.append(" pbtc.time,
		 * "); sb.append(" pbtc.announce_date, "); sb.append(" pbtc.photo_link,
		 * "); sb.append(" nvl(hot.count, 0) cou "); sb.append(" from
		 * pe_bzz_tch_course pbtc "); sb.append(" join enum_const ec ");
		 * sb.append(" on pbtc.flag_course_item_type = ec.id "); sb.append("
		 * join enum_const ec1 "); sb.append(" on pbtc.flag_coursecategory =
		 * ec1.id "); sb.append(" join enum_const ec2 "); sb.append(" on
		 * pbtc.flag_coursetype = ec2.id "); sb.append(" join enum_const ec3 ");
		 * sb.append(" on pbtc.flag_isfree = ec3.id "); sb.append(" left join
		 * interaction_teachclass_info iti "); sb.append(" on iti.TEACHCLASS_ID =
		 * pbtc.id "); sb.append(" and iti.TYPE = 'KCJJ' "); sb.append(" left
		 * join (select pbtc.id, count(pbtse.fk_stu_id) count "); sb.append("
		 * from pe_bzz_tch_course pbtc "); sb.append(" join
		 * pr_bzz_tch_opencourse pbto "); sb.append(" on pbtc.id =
		 * pbto.fk_course_id "); sb.append(" join pr_bzz_tch_stu_elective pbtse
		 * "); sb.append(" on pbtse.fk_tch_opencourse_id = pbto.id ");
		 * sb.append(" join enum_const ec1 "); sb.append(" on
		 * pbtc.flag_coursearea = ec1.id "); sb.append(" and ((ec1.code = '0'
		 * and pbtc.flag_isvalid = '2') or "); sb.append(" (ec1.code = '1' and
		 * pbtc.flag_isvalid = '2' and "); sb.append(" pbtc.flag_offline =
		 * '22')) "); sb.append(" group by pbtc.id) hot "); sb.append(" on
		 * hot.id = pbtc.id "); sb.append(" "); sb.append(" where
		 * (pbtc.flag_isvalid = '2' and pbtc.flag_offline = '22') ");
		 * sb.append(" AND (PBTC.FLAG_COURSEAREA = 'Coursearea1' or ");
		 * sb.append(" PBTC.FLAG_COURSEAREA = 'Coursearea0') "); sb.append("
		 * order by cou desc) "); sb.append(" where rownum < 16 ");
		 */
		sb
				.append("  SELECT * FROM (SELECT PBTC.ID, PBTC.NAME, PBTC.CODE, PBTC.PRICE, PBTC.TEACHER,  PBTC.TIME,  PBTC.ANNOUNCE_DATE,PBTC.PHOTO_LINK    ");
		sb.append("   FROM STATISTIC_COURSES_YEAR SY    ");
		sb.append("  INNER JOIN PE_BZZ_TCH_COURSE PBTC    ");
		sb.append("     ON SY.FK_COURSE_ID = PBTC.ID    ");
		sb.append("     WHERE to_char(pbtc.announce_date,'yyyy') ='2014'  "); // 生产时去掉
																				// 年份
																				// 用当年信息
		sb.append("      ORDER BY nvl(sy.study_entry_times,0) DESC)   ");
		sb.append("   WHERE ROWNUM <16  ");

		try {
			list = this.getGeneralService().getBySQL(sb.toString());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List getSeriseCourse_() {
		this.initStudent();
		List list = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer allsb = new StringBuffer();
		sb.append("        SELECT RS.ID, RS.NAME, RS.DESCS ,photo_link                                        ");
		sb.append("    FROM RECOMMEND_SERIES RS                                                   ");
		sb.append("   INNER JOIN (SELECT PBTCS.ID,                                                 ");
		sb.append("                      PBTCS.FK_COURSE_ID FK_COURSE_ID,                          ");
		sb.append("                      EC.NAME            SUGGESTNAME                            ");
		sb.append("                 FROM PE_BZZ_TCH_COURSE_SUGGEST PBTCS, ENUM_CONST EC            ");
		sb.append("                WHERE PBTCS.FK_ENUM_CONST_ID = EC.ID) AA                        ");
		sb.append("      ON RS.ID = AA.FK_COURSE_ID                                               ");
		sb.append("   INNER JOIN (SELECT PBS.ID id,                                                  ");
		sb.append("                      PBS.FK_SSO_USER_ID FK_SSO_USER_ID,                        ");
		sb.append("                      EC1.NAME           ZIGENAME                              ");
		sb.append("                 FROM PE_BZZ_STUDENT PBS, ENUM_CONST EC1                       ");
		sb.append("                WHERE PBS.ZIGE = EC1.ID) BB                                    ");
		sb.append("      ON AA.SUGGESTNAME = BB.ZIGENAME                                           ");
		// sb.append(" INNER JOIN SSO_USER SU ");
		// sb.append(" ON BB.FK_SSO_USER_ID = SU.ID ");
		// sb.append(" WHERE bb.id = '"+ this.getPeBzzStudent().getId()+"' ");
		sb.append("   GROUP BY RS.ID, RS.NAME, RS.DESCS ,   photo_link                                        ");
		try {
			list = this.getGeneralService().getBySQL(sb.toString());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (list == null || list.size() == 0) {

			allsb.append("        SELECT RS.ID, RS.NAME, RS.DESCS  ,photo_link                                      ");
			allsb.append("    FROM RECOMMEND_SERIES RS                                                   ");
			allsb.append("   INNER JOIN (SELECT PBTCS.ID,                                                 ");
			allsb.append("                      PBTCS.FK_COURSE_ID FK_COURSE_ID,                          ");
			allsb.append("                      EC.NAME            SUGGESTNAME                            ");
			allsb.append("                 FROM PE_BZZ_TCH_COURSE_SUGGEST PBTCS, ENUM_CONST EC            ");
			allsb.append("                WHERE PBTCS.FK_ENUM_CONST_ID = EC.ID) AA                        ");
			allsb.append("      ON RS.ID = AA.FK_COURSE_ID                                               ");
			allsb.append("   INNER JOIN (SELECT PBS.ID id,                                                  ");
			allsb.append("                      PBS.FK_SSO_USER_ID FK_SSO_USER_ID,                        ");
			allsb.append("                      EC1.NAME           ZIGENAME                              ");
			allsb.append("                 FROM PE_BZZ_STUDENT PBS, ENUM_CONST EC1                       ");
			allsb.append("                WHERE PBS.ZIGE = EC1.ID) BB                                    ");
			allsb.append("      ON AA.SUGGESTNAME = BB.ZIGENAME                                           ");
			allsb.append("   GROUP BY RS.ID, RS.NAME, RS.DESCS   ,photo_link                      ");
			try {
				list = this.getGeneralService().getBySQL(allsb.toString());
			} catch (EntityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return list;
	}

	public List getPublicCourse() {
		this.initStudent();
		List list = null;
		StringBuffer sb = new StringBuffer();
		sb.append("  SELECT *                                                                                                                                                           ");
		sb.append("   FROM (SELECT PBTC.ID,                                                                                                                                             ");
		sb.append("                PBTC.NAME,                                                                                                                                           ");
		sb.append("                PBTC.CODE,                                                                                                                                           ");
		sb.append("                PBTC.PRICE,                                                                                                                                          ");
		sb.append("                PBTC.TEACHER,                                                                                                                                        ");
		sb.append("                PBTC.TIME,                                                                                                                                           ");
		sb.append("                PBTC.ANNOUNCE_DATE,                                                                                                                                  ");
		sb.append("                PBTC.PHOTO_LINK                                                                                                                                      ");
		sb.append("                ,aa.enames                                                                                                                                           ");
		sb.append("           FROM PE_BZZ_TCH_COURSE PBTC                                                                                                                               ");
		sb.append("           LEFT JOIN  (SELECT btc.id  , ec.name enames  FROM PE_BZZ_TCH_COURSE btc INNER JOIN   PE_BZZ_TCH_COURSE_SUGGEST ps ON  btc.id = ps.fk_course_id            ");
		sb.append("           INNER JOIN ENUM_CONST ec ON ps.fk_enum_const_id = ec.id WHERE  ec.name IN                                                                                 ");
		sb.append("           (SELECT   EC1.NAME           ZIGENAME                                                                                                                     ");
		sb.append(" 	                 FROM PE_BZZ_STUDENT PBS, ENUM_CONST EC1                                                                                                          ");
		sb.append(" 	               WHERE PBS.ZIGE = EC1.ID AND  pbs.id ='"+this.getPeBzzStudent().getId()+"' ))aa   ON pbtc.id = aa.id                                                                        ");
		sb.append("                                                                                                                                                                     ");
		sb.append("           JOIN ENUM_CONST EC                                                                                                                                        ");
		sb.append("             ON PBTC.FLAG_COURSE_ITEM_TYPE = EC.ID                                                                                                                   ");
		sb.append("           JOIN ENUM_CONST EC1                                                                                                                                       ");
		sb.append("             ON PBTC.FLAG_COURSECATEGORY = EC1.ID                                                                                                                    ");
		sb.append("           JOIN ENUM_CONST EC2                                                                                                                                       ");
		sb.append("             ON PBTC.FLAG_COURSETYPE = EC2.ID                                                                                                                        ");
		sb.append("           JOIN ENUM_CONST EC3                                                                                                                                       ");
		sb.append("             ON PBTC.FLAG_ISFREE = EC3.ID                                                                                                                            ");
		sb.append("          WHERE (PBTC.FLAG_ISVALID = '2' AND PBTC.FLAG_OFFLINE = '22')                                                                                               ");
		sb.append("            AND (PBTC.FLAG_COURSEAREA = 'Coursearea1' OR                                                                                                             ");
		sb.append("                PBTC.FLAG_COURSEAREA = 'Coursearea0')                                                                                                                ");
		sb.append("            AND TO_CHAR(PBTC.ANNOUNCE_DATE, 'yyyy')  <= to_char(SYSDATE ,'yyyy')                                                                                     ");
		sb.append("          ORDER BY aa.enames ,PBTC.ANNOUNCE_DATE DESC)                                                                                                               ");
		sb.append("  WHERE ROWNUM < 16                                                                                                                                                  ");
		/*sb
				.append(" select * from (                                                                                                         ");
		sb
				.append("  SELECT  pbtc.id ,pbtc.name ,pbtc.code , pbtc.price ,pbtc.teacher ,pbtc.time ,pbtc.announce_date, pbtc.photo_link       ");
		sb
				.append("    FROM PE_BZZ_TCH_COURSE PBTC                                                                                          ");
		sb
				.append("    JOIN ENUM_CONST EC                                                                                                   ");
		sb
				.append("      ON PBTC.FLAG_COURSE_ITEM_TYPE = EC.ID                                                                              ");
		sb
				.append("    JOIN ENUM_CONST EC1                                                                                                  ");
		sb
				.append("      ON PBTC.FLAG_COURSECATEGORY = EC1.ID                                                                               ");
		sb
				.append("    JOIN ENUM_CONST EC2                                                                                                  ");
		sb
				.append("      ON PBTC.FLAG_COURSETYPE = EC2.ID                                                                                   ");
		sb
				.append("    JOIN ENUM_CONST EC3                                                                                                  ");
		sb
				.append("      ON PBTC.FLAG_ISFREE = EC3.ID                                                                                       ");
		sb
				.append("   WHERE (PBTC.FLAG_ISVALID = '2' AND PBTC.FLAG_OFFLINE = '22')                                                          ");
		sb
				.append("     AND (PBTC.FLAG_COURSEAREA = 'Coursearea1' OR                                                                        ");
		sb
				.append("         PBTC.FLAG_COURSEAREA = 'Coursearea0' )                                                                            ");
		sb.append("and  to_char(pbtc.announce_date,'yyyy') ='2014'  "); // 测试课程图片
																		// 生产时去掉
		sb
				.append("   ORDER BY PBTC.ANNOUNCE_DATE DESC                                                                                      ");
		sb
				.append("   ) where rownum< 16                                                                                                    "*/
		try {
			list = this.getGeneralService().getBySQL(sb.toString());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public List getInformation() {
		List list = null;
		StringBuffer sb = new StringBuffer();
		sb
				.append(" SELECT * FROM (SELECT pr.id ,pr.name ,pr.fabuunit ,pr.RESETYPE ,pr.creationdate ,pr.views  FROM PE_RESOURCE pr ORDER BY pr.views desc) WHERE ROWNUM <6");
		try {
			list = this.getGeneralService().getBySQL(sb.toString());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public String getNoticeData() {
		Reader inStream = null;
		List list = null;
		String data = "";
		Object title = null;
		StringBuffer sb = new StringBuffer();
		sb
				.append(" select p.title ,p.note as note  FROM PE_BULLETIN P WHERE p.flag_isvalid ='2' AND p.SCOPE_STRING LIKE '%stuhomepage%' order by p.publish_date desc ");
		try {
			list = this.getGeneralService().getBySQL(sb.toString());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list != null && list.size() > 0) {
			try {

				Object[] question = (Object[]) list.get(0);
				Clob clob = (Clob) question[1];
				title = question[0];
				if (clob != null && !"".equals(clob)) {
					inStream = clob.getCharacterStream();
					char[] c = new char[(int) clob.length()];
					inStream.read(c);
					data = new String(c);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServletActionContext.getRequest().setAttribute("title", title);
		return data;

	}

	public void firstBulletinInfo() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String sql = "select count(*)\n" + "  from pe_bulletin pb, sso_user so, pe_manager pe\n" + " where pe.id = pb.fk_manager_id\n"
				+ "   and so.login_id = '" + us.getLoginId() + "'\n" + "   and pb.scope_string like '%students%'";

		try {
			page = this.getGeneralService().getByPageSQL(sql, 10, 0);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 未读邮件数量
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public void UnreadNum() {
		DetachedCriteria dc = DetachedCriteria.forClass(SiteEmail.class).add(
				Restrictions.eq("addresseeId", this.getPeBzzStudent().getRegNo())).add(Restrictions.eq("status", "0")).add(
				Restrictions.eq("addresseeDel", (long) 0));
		try {
			List list = this.getGeneralService().getDetachList(dc);
			if (list != null) {
				this.unReadnum = "" + list.size();
			} else {
				this.unReadnum = "0";
			}
		} catch (EntityException e) {
			this.unReadnum = "0";
		}

	}

	/**
	 * 余额
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public void Balance() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(SsoUser.class);
		dc.add(Restrictions.eq("loginId", us.getLoginId()));
		try {
			SsoUser user = (SsoUser) this.getGeneralService().getList(dc).get(0);
			if (user.getSumAmount() != null && !("".equals(user.getSumAmount())) && !("".equals(user.getAmount()))
					&& user.getAmount() != null) {
				balancenum = new BigDecimal(user.getSumAmount()).subtract(new BigDecimal(user.getAmount())).setScale(2,
						BigDecimal.ROUND_HALF_UP);
				BigDecimal mData = new BigDecimal(balancenum.toString()).setScale(1, BigDecimal.ROUND_HALF_UP);
				balancenum = mData.setScale(2, BigDecimal.ROUND_HALF_UP);
			} else {
				balancenum = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
			}
		} catch (EntityException e) {
			balancenum = new BigDecimal(0).setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}

	/**
	 * 跳转到退费申请页面
	 * 
	 * @return
	 */
	public String toRefundApply() {

		return "refundApply";
	}

	/**
	 * 提交退费申请
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String toSubmitRefundApply() {
		String id = ServletActionContext.getRequest().getParameter("id");
		String refundReason = ServletActionContext.getRequest().getParameter("refundReason");
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.eq("id", id));
		try {
			List<PeBzzOrderInfo> orderList = this.getGeneralService().getList(dc);
			EnumConst enumConstByFlagRefundState = this.getEnumConstService().getByNamespaceCode("FlagRefundState", "0");
			peBzzOrderInfo = orderList.get(0);
			peBzzOrderInfo.setEnumConstByFlagRefundState(enumConstByFlagRefundState);
			PeBzzRefundInfo peBzzRefundInfo = new PeBzzRefundInfo();
			peBzzRefundInfo.setApplyDate(new Date());
			peBzzRefundInfo.setPeBzzOrderInfo(peBzzOrderInfo);
			peBzzRefundInfo.setReason(refundReason);
			peBzzRefundInfo.setEnumConstByFlagRefundState(enumConstByFlagRefundState);
			this.getGeneralService().save(peBzzRefundInfo);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMsg("申请失败！");
			this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
			return "stu_msg";
		}
		this.setMsg("申请成功，请等待协会管理员审核！");
		this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
		return "stu_msg";
	}

	/**
	 * 查看订单退费详细情况
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String viewReason() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzRefundInfo.class);
		dc.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState");
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.add(Restrictions.eq("peBzzOrderInfo.id", this.id));
		try {
			List list = this.getGeneralService().getList(dc);
			if (list.size() > 0) {
				this.peBzzRefundInfo = (PeBzzRefundInfo) list.get(0);
			} else {
				this.peBzzRefundInfo = new PeBzzRefundInfo();
			}
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "viewReason";
	}

	/**
	 * 网银支付产生的订单，首次没支付后，点击支付时在支付，查看订单详情
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String continuePaymentOnline() {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			orderDc.createCriteria("enumConstByFlagPaymentState", "enumConstByFlagPaymentState");
			orderDc.createCriteria("enumConstByFlagOrderType", "enumConstByFlagOrderType");
			orderDc.add(Restrictions.eq("id", id));
			PeBzzOrderInfo order = (PeBzzOrderInfo) this.getGeneralService().getList(orderDc).get(0);

			request.getSession().setAttribute("peBzzOrderInfo", order); // 将查找出来的订单放入
			// DetachedCriteria invoiceDc =
			// DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
			// invoiceDc.add(Restrictions.eq("peBzzOrderInfo", order));
			// List list = this.getGeneralService().getList(invoiceDc);
			// if(list.size()>0) {
			// peBzzInvoiceInfo = (PeBzzInvoiceInfo)list.get(0);
			// }
			// request.getSession().setAttribute("peBzzInvoiceInfo",
			// peBzzInvoiceInfo);
			// if(order.getEnumConstByFlagOrderType().getCode().equals("2")) {
			// //专项支付
			// ServletActionContext.getRequest().getSession().setAttribute(order.getSeq(),
			// "batchPayment");
			// } else { //选课订单支付
			// ServletActionContext.getRequest().getSession().setAttribute(order.getSeq(),
			// "stuElePayment");
			// }
		} catch (EntityException e) {
			e.printStackTrace();
		}
		// return "viewOrderInfo";
		return "onlinePayment";
	}

	/**
	 * 跳转到网银支付页面
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String onlinePayment() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			DetachedCriteria orderDc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			orderDc.createCriteria("enumConstByFlagPaymentState", "enumConstByFlagPaymentState");
			orderDc.createCriteria("enumConstByFlagOrderType", "enumConstByFlagOrderType");
			orderDc.add(Restrictions.eq("id", id));
			PeBzzOrderInfo order = (PeBzzOrderInfo) this.getGeneralService().getList(orderDc).get(0);
			// Lee 新增快钱支付
			String paymentType = ServletActionContext.getRequest().getParameter("paymentType");
			ServletActionContext.getRequest().getSession().setAttribute("paymentType", paymentType);

			request.getSession().setAttribute("peBzzOrderInfo", order); // 将查找出来的订单放入
			request.getSession().setAttribute(order.getSeq(), "stu"); // 标识学生提交的订单
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "onlinePayment";
	}

	// /**
	// * 专项支付，网银支付确认
	// * @return
	// */
	// public String onlinePaymentOfBatch() {
	// /**
	// * 根据易宝返回的order号来查找对应的订单
	// */
	// DetachedCriteria orderDc =
	// DetachedCriteria.forClass(PeBzzOrderInfo.class);
	// orderDc.createCriteria("enumConstByFlagPaymentState",
	// "enumConstByFlagPaymentState");
	// orderDc.add(Restrictions.eq("seq", this.merorderid));
	// PeBzzOrderInfo peBzzOrderInfo = null;
	// try {
	// peBzzOrderInfo = (PeBzzOrderInfo)
	// this.getGeneralService().getList(orderDc).get(0);
	// this.getGeneralService().updatePeBzzOrderInfo(peBzzOrderInfo, "confirm",
	// null);
	// } catch (EntityException e) {
	// e.printStackTrace();
	// }
	// /**
	// * 订单的支付状态，改为已支付
	// */
	// EnumConst enumConstByFlagPaymentState =
	// this.enumConstService.getByNamespaceCode("FlagPaymentState", "1");
	// peBzzOrderInfo.setEnumConstByFlagPaymentState(enumConstByFlagPaymentState);
	// /**
	// * 根据订单来查找对应的学员专项信息
	// */
	// DetachedCriteria sbDc = DetachedCriteria.forClass(StudentBatch.class);
	// sbDc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
	// sbDc.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));
	// sbDc.createCriteria("enumConstByFlagBatchPayState",
	// "enumConstByFlagBatchPayState");
	// List<StudentBatch> sbList = null;
	// try {
	// sbList = this.getGeneralService().getList(sbDc);
	// } catch (EntityException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// /**
	// * 专项的支付状态，已支付
	// */
	// EnumConst enumConstByFlagBatchPayState =
	// this.enumConstService.getByNamespaceCode("FlagBatchPayState", "1");
	// for(StudentBatch sb : sbList) {
	// sb.setEnumConstByFlagBatchPayState(enumConstByFlagBatchPayState);
	// }
	// /**
	// * 根据订单来查询对应的选课信息
	// */
	// DetachedCriteria electiveDc =
	// DetachedCriteria.forClass(PrBzzTchStuElective.class);
	// electiveDc.createCriteria("enumConstByFlagElectivePayStatus",
	// "enumConstByFlagElectivePayStatus");
	// electiveDc.add(Restrictions.eq("peBzzOrderInfo", peBzzOrderInfo));
	// List<PrBzzTchStuElective> electiveList = null;
	// try {
	// electiveList = this.getGeneralService().getList(electiveDc);
	// } catch (EntityException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// /**
	// * 选课支付状态
	// */
	// EnumConst enumConstByFlagElectivePayStatus =
	// this.enumConstService.getByNamespaceCode("FlagElectivePayStatus", "1");
	// for(PrBzzTchStuElective ele : electiveList) {
	// ele.setEnumConstByFlagElectivePayStatus(enumConstByFlagElectivePayStatus);
	// }
	// this.studentWorkspaceService.saveElectiveCourseAndPebzzOrderInfo(electiveList,
	// peBzzOrderInfo, peBzzInvoiceInfo, sbList);
	// this.setMsg("支付成功");
	// return "success";
	// }
	/**
	 * 此action用来关闭订单
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String closeOrder() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		if (id != null && !"".equals(id)) {
			try {
				List orderIdList = new ArrayList();
				orderIdList.add(id);
				// 关闭订单
				this.getGeneralService().closeOrderList(orderIdList, us.getSsoUser());
			} catch (EntityException e) {
				// e.printStackTrace();
				this.setMsg(e.getMessage());
				this.setTogo("back");
				return "stu_msg";
			} catch (Exception e) {
				e.printStackTrace();
				this.setMsg("关闭订单时出现异常！");
				this.setTogo("back");
				return "stu_msg";
			}
		} else {
			this.setMsg("参数错误！");
			this.setTogo("back");
			return "stu_msg";
		}
		return tohistoryrecord();
	}

	/**
	 * 此action用于学员来申请订单发票,作为中转action
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String toApplyInvoice() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		HttpServletRequest request = ServletActionContext.getRequest();
		String[] ids = null;
		Double amount = 0.00;
		String orderSeqs = "";
		if(id !=null ){
		 ids = id.split(",");
		}
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.in("id", ids));
		List<PeBzzOrderInfo> list = null;
		try {
			list = this.getGeneralService().getList(dc);
			for(PeBzzOrderInfo order : list) {
				
				if(order.getEnumConstByFlagPaymentState() != null && "0".equals(order.getEnumConstByFlagPaymentState().getCode())) {
					this.setMsg(order.getSeq() + "订单未支付不能申请发票");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				Date paymentDate = order.getPaymentDate();
				Calendar c = Calendar.getInstance();
				c.setTime(paymentDate);
				c.add(Calendar.DATE, 7);
				if(new Date().getTime() < c.getTimeInMillis()) {
					this.setMsg(order.getSeq() + "订单支付7天后才可以申请发票");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				if(order.getPaymentDate() != null){
					if(order.getPaymentDate().getYear() != new Date().getYear()){
						this.setMsg("订单跨年不能申请发票，如有疑问请联系咨询中心");
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
				}
				if(order.getEnumConstByFlagRefundState() != null 
						&& ("已退费".equals(order.getEnumConstByFlagRefundState().getName()) 
						|| "退费中".equals(order.getEnumConstByFlagRefundState().getName())
						|| "待审核".equals(order.getEnumConstByFlagRefundState().getName()))) {
					this.setMsg(order.getSeq() + "已申请退费，不能开发票");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				if(order.getEnumConstByFlagPaymentMethod() != null && "1".equals(order.getEnumConstByFlagPaymentMethod().getCode())) {
					this.setMsg(order.getSeq() + "为预付费账户支付订单不能开发票");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				if(order.getPeBzzInvoiceInfos().size() > 0) {
					this.setMsg(order.getSeq() + "订单已申请发票，请勿重复操作");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				if(order.getMergeSeq() != null) {
					this.setMsg(order.getSeq() + "订单已合并申请发票，请取消合并后再操作");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				amount += Double.parseDouble(order.getAmount());
				orderSeqs += order.getSeq() + "|";
			}
		} catch (EntityException e1) {
			this.setMsg("获取订单信息失败");
			this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
			return "stu_msg";
		}
		BigDecimal b = new BigDecimal(amount);
		amount = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		request.setAttribute("amount", amount);
		request.setAttribute("orderSeqs", orderSeqs);
		try {
			// 读取最后一次发票信息
			this.setLastInvoiceInfo(this.getGeneralService().getLastInvoice(us.getSsoUser().getId()));
		} catch (EntityException e) {
			this.setLastInvoiceInfo(new PeBzzInvoiceInfo());
		}
		return "toApplyInvoice";
	}

	/**
	 * 保存发票信息
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String saveInvoiceInfo() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String err_msg ="";
		Map result = null ;
		String[] ids = id.split(",");
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.in("id", ids));
		List<PeBzzOrderInfo> list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e1) {
			this.setMsg("获取订单信息失败");
			this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
			return "stu_msg";
		}
		String x_seq = "";
		Double x_amount = 0.00;
		EnumConst enumConstByInvoiceType = this.getEnumConstService().getByNamespaceCode("InvoiceType", invoiceType);
		this.peBzzInvoiceInfo.setEnumConstByInvoiceType(enumConstByInvoiceType);
		if(list.size() == 1) {
			this.peBzzOrderInfo = list.get(0);
			// 邮寄方式
//			if("4".equals(invoiceType)) {
//				String postType = ServletActionContext.getRequest().getParameter("postType");
//				EnumConst enumConstByFlagPostType = this.getEnumConstService().getByNamespaceCode("FlagPostType", postType);
//				this.peBzzInvoiceInfo.setEnumConstByFlagPostType(enumConstByFlagPostType);
//			}
			this.peBzzInvoiceInfo.setPeBzzOrderInfo(peBzzOrderInfo);
			this.peBzzInvoiceInfo.setEnumConstByFlagFpOpenState(this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode(
					"FlagFpOpenState", "0"));
			
			x_seq = peBzzOrderInfo.getSeq();
			x_amount = Double.parseDouble(peBzzOrderInfo.getAmount());
			BigDecimal b = new BigDecimal(x_amount);
			x_amount = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			Map param = new HashMap();
			param.put("business", XMLFileUtil.SAVE_BUSSINESS);
			param.put("fplx", "0");
			param.put("amount", x_amount.toString());
			param.put("seq", x_seq);
			param.put("invoice", peBzzInvoiceInfo);
			String xml = XMLFileUtil.getXMLFile(param);
			try{
				WebServiceInvoker wst = new WebServiceInvoker();
			 	result = XMLFileUtil.getResult(wst.client(xml));
			}catch(Exception e){
				err_msg ="申请失败，请稍候再试";
			}finally{
				this.setMsg(err_msg);
				if(!"".equals(err_msg)){
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
			}
			if("0000".equals(result.get("returnCode"))) {
				List<Map> returnInfo = (List)result.get("returnInfo");
				if(returnInfo != null && returnInfo.size() > 0) {
					Map<String, String> map = returnInfo.get(0);
					String seq = map.get("seq");
					String returnCode = map.get("returnCode");
					String returnMsg = map.get("returnMsg");
					if("0000".equals(returnCode)) {
						this.setMsg("申请成功！");
						peBzzInvoiceInfo.setInvoiceNum(map.get("fplsh"));
						peBzzInvoiceInfo.setCount("0");
						peBzzInvoiceInfo.setCreateDate(new Date());
					} else {
						this.setMsg(seq + "订单申请失败," + returnMsg);
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
				} else {
					this.setMsg("申请失败!");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
			} else {
				//try {
					//this.setMsg(new String((Base64Util.getFromBASE64((String)result.get("returnMsg"))).getBytes(), "UTF-8"));
					this.setMsg("申请失败");
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
				this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
				return "stu_msg";
			}
			try {
				this.getGeneralService().save(peBzzInvoiceInfo);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
			return "stu_msg";
		} else if(list.size() > 1) {
			Double a = 0.00;
			String seqSql = "SELECT S_PE_BZZ_MERGE_SEQ.NEXTVAL FROM DUAL";
			Object obj = null;
			try {
				obj = this.getGeneralService().getBySQL(seqSql).get(0);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			String seqFormat = "000000000";
			DecimalFormat df = new DecimalFormat(seqFormat);
			seq = "H" + df.format(obj);
			x_seq = seq;
			for(PeBzzOrderInfo order : list) {
				if(order.getEnumConstByFlagPaymentState() != null && "0".equals(order.getEnumConstByFlagPaymentState().getCode())) {
					this.setMsg(order.getSeq() + "订单未支付不能申请发票");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				Date paymentDate = order.getPaymentDate();
				Calendar c = Calendar.getInstance();
				c.setTime(paymentDate);
				c.add(Calendar.DATE, 7);
				if(new Date().getTime() < c.getTimeInMillis()) {
					this.setMsg(order.getSeq() + "订单支付7天后才可以申请发票");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				if(order.getEnumConstByFlagRefundState() != null 
						&& ("已退费".equals(order.getEnumConstByFlagRefundState().getName()) 
						|| "退费中".equals(order.getEnumConstByFlagRefundState().getName())
						|| "待审核".equals(order.getEnumConstByFlagRefundState().getName()))) {
					this.setMsg(order.getSeq() + "已申请退费，不能开发票");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				if(order.getEnumConstByFlagPaymentMethod() != null && "1".equals(order.getEnumConstByFlagPaymentMethod().getCode())) {
					this.setMsg(order.getSeq() + "为预付费账户支付订单不能开发票");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				if(order.getPeBzzInvoiceInfos().size() > 0) {
					this.setMsg(order.getSeq() + "订单已申请发票，请勿重复操作");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				if(order.getMergeSeq() != null) {
					this.setMsg(order.getSeq() + "订单已合并申请发票，请取消合并后再操作");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				x_amount += Double.parseDouble(order.getAmount());
			}
			BigDecimal b = new BigDecimal(x_amount);
			x_amount = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			Map param = new HashMap();
			param.put("business", XMLFileUtil.SAVE_BUSSINESS);
			param.put("fplx", "0");
			param.put("amount", x_amount.toString());
			param.put("seq", x_seq);
			param.put("invoice", peBzzInvoiceInfo);
			String xml = XMLFileUtil.getXMLFile(param);
			try{
				WebServiceInvoker wst = new WebServiceInvoker();
			 	result = XMLFileUtil.getResult(wst.client(xml));
			}catch(Exception e){
				err_msg ="申请失败，请稍候再试";
			}finally{
				this.setMsg(err_msg);
				if(!"".equals(err_msg)){
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
			}
			if("0000".equals(result.get("returnCode"))) {
				List<Map> returnInfo = (List)result.get("returnInfo");
				if(returnInfo != null && returnInfo.size() > 0) {
					Map<String, String> map = returnInfo.get(0);
					String seq = map.get("seq");
					String returnCode = map.get("returnCode");
					String returnMsg = map.get("returnMsg");
					if("0000".equals(returnCode)) {
						this.setMsg("申请成功！");
						peBzzInvoiceInfo.setInvoiceNum(map.get("fplsh"));
					} else {
						this.setMsg(seq + "订单申请失败," + returnMsg);
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
				} else {
					this.setMsg("申请失败!");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
			} else {
//				try {
//					this.setMsg(new String((Base64Util.getFromBASE64((String)result.get("returnMsg"))).getBytes(), "UTF-8"));
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
				this.setMsg("申请失败");
				this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
				return "stu_msg";
			}
			for(PeBzzOrderInfo order : list) {
				PeBzzInvoiceInfo invoice = new PeBzzInvoiceInfo();
				order.setMergeSeq(seq);
				try {
					this.getGeneralService().save(order);
				} catch (EntityException e) {
					e.printStackTrace();
				}
				// 邮寄方式
				if("4".equals(invoiceType)) {
//					String postType = ServletActionContext.getRequest().getParameter("postType");
//					EnumConst enumConstByFlagPostType = this.getEnumConstService().getByNamespaceCode("FlagPostType", postType);
////					this.peBzzInvoiceInfo.setEnumConstByFlagPostType(enumConstByFlagPostType);
//					invoice.setEnumConstByFlagPostType(enumConstByFlagPostType);
					invoice.setGmfnsrsbh(peBzzInvoiceInfo.getGmfnsrsbh());
					invoice.setGmfdz(peBzzInvoiceInfo.getGmfdz());
					invoice.setGmfkhyh(peBzzInvoiceInfo.getGmfkhyh());
					invoice.setGmfyhzh(peBzzInvoiceInfo.getGmfkhyh());
				}
				invoice.setGmfsjhm(peBzzInvoiceInfo.getGmfsjhm());
				invoice.setTitle(peBzzInvoiceInfo.getTitle());
				invoice.setAddress(peBzzInvoiceInfo.getAddress());
				invoice.setProvince(peBzzInvoiceInfo.getProvince());
				invoice.setCity(peBzzInvoiceInfo.getCity());
				invoice.setZipCode(peBzzInvoiceInfo.getZipCode());
				invoice.setAddressee(peBzzInvoiceInfo.getAddressee());
				invoice.setPhone(peBzzInvoiceInfo.getPhone());
				invoice.setPeBzzOrderInfo(order);
				invoice.setEnumConstByFlagFpOpenState(this.getGeneralService().getGeneralDao().getEnumConstByNamespaceCode(
						"FlagFpOpenState", "0"));
				invoice.setEnumConstByInvoiceType(enumConstByInvoiceType);
				invoice.setGmfdh(peBzzInvoiceInfo.getGmfsjhm());
				invoice.setEmail(peBzzInvoiceInfo.getEmail());
				invoice.setInvoiceRemark(peBzzInvoiceInfo.getInvoiceRemark());
				invoice.setCreateDate(new Date());
				invoice.setInvoiceNum(peBzzInvoiceInfo.getInvoiceNum());
				invoice.setCount("0");
				try {
					this.getGeneralService().save(invoice);
				} catch (EntityException e) {
					e.printStackTrace();
				}
			}
			UUID uuid = UUID.randomUUID();
			String add_sql = "INSERT INTO PE_BZZ_ORDER_MERGE(ID, SEQ, OPERATOR, CREATE_DATE) VALUES('" + uuid + "','" + seq + "','" + us.getId() + "',sysdate)";
			try {
				this.getGeneralService().executeBySQL(add_sql);
				this.id = (String)this.getGeneralService().getBySQL("SELECT ID FROM PE_BZZ_ORDER_MERGE WHERE SEQ = '" + seq + "'").get(0);
			} catch (EntityException e) {
				e.printStackTrace();
			}
		}
		this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
		return "stu_msg";
	}
	
	/**
	 * 用于发票修改时，检索出原有的发票信息
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String seekInvoice() {
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("enumConstByInvoiceType", "enumConstByInvoiceType", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.add(Restrictions.eq("peBzzOrderInfo.id", this.id));
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		if (list.size() < 1) {
			this.setMsg("发票信息获取失败！");
			this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
			return "stu_msg";
		} else {
			this.peBzzInvoiceInfo = (PeBzzInvoiceInfo) list.get(0);
		}
		return "seekInvoice";
	}

	/**
	 * 修改发票信息
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String modifyInvoiceInfo() {
		try {
			String err_msg = "";
			Map result = null;
			String x_seq = "";
			Double x_amount = 0.00;
			toApplyInvoice();// 初始化发票信息
			DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzOrderInfo.class);
			dc1.add(Restrictions.eq("id", this.id));
			List orderList = this.getGeneralService().getList(dc1);
			String[] ids = null;
			if (orderList.size() < 1) {
				this.setMsg("发票信息获取失败！");
				this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
				return "stu_msg";
			} else {
				PeBzzOrderInfo order = (PeBzzOrderInfo) orderList.get(0);
				if(order.getMergeSeq() != null && !"".equals(order.getMergeSeq())) {
					x_seq = order.getMergeSeq();
					String hql = "from PeBzzOrderInfo where mergeSeq = '" + order.getMergeSeq() + "'";
					List<PeBzzOrderInfo> idList = this.getGeneralService().getByHQL(hql);
					ids = new String[idList.size()];
					for(int i = 0; i < idList.size(); i++) {
						ids[i] = idList.get(i).getId();
						x_amount += Double.parseDouble(idList.get(i).getAmount());
					}
				} else {
					ids = new String[1];
					ids[0] = id;
					x_amount = Double.parseDouble(order.getAmount());
					x_seq = order.getSeq();
				}
			}
			DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
			dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
			dc.add(Restrictions.in("peBzzOrderInfo.id", ids));
			List list = this.getGeneralService().getList(dc);
			if (list.size() < 1) {
				this.setMsg("发票信息修改失败！");
				this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
				return "stu_msg";
			}
			for(int i = 0; i < list.size(); i++) {
				PeBzzInvoiceInfo invoice = (PeBzzInvoiceInfo) list.get(i);
				if("1".equals(invoice.getEnumConstByFlagFpOpenState().getCode())) {
					this.setMsg(invoice.getPeBzzOrderInfo().getSeq() + "订单发票已打印，修改失败！");
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
			}
			BigDecimal b = new BigDecimal(x_amount);
			x_amount = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			String postType = ServletActionContext.getRequest().getParameter("postType");
			EnumConst enumConstByFlagPostType = this.getEnumConstService().getByNamespaceCode("FlagPostType", postType);
			EnumConst enumConstByInvoiceType = this.getEnumConstService().getByNamespaceCode("InvoiceType", "4");
			peBzzInvoiceInfo.setEnumConstByFlagPostType(enumConstByFlagPostType);
			peBzzInvoiceInfo.setEnumConstByInvoiceType(enumConstByInvoiceType);
			Map param = new HashMap();
			param.put("business", XMLFileUtil.UPDATE_BUSSINESS);
			param.put("fplx", "0");
			param.put("amount", x_amount.toString());
			param.put("seq", x_seq);
			param.put("invoice", peBzzInvoiceInfo);
			String xml = XMLFileUtil.getXMLFile(param);
			try{
				WebServiceInvoker wst = new WebServiceInvoker();
			 	result = XMLFileUtil.getResult(wst.client(xml));
			}catch(Exception e){
				err_msg ="申请失败，请稍候再试";
			}finally{
				this.setMsg(err_msg);
				if(!"".equals(err_msg)){
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
			}
			if("0000".equals(result.get("returnCode"))) {
				this.setMsg("修改成功!");
			} else {
//				try {
//					this.setMsg(new String(Base64Util.getFromBASE64(((String)result.get("returnMsg"))).getBytes(), "UTF-8"));
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
				this.setMsg("修改失败");
				this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
				return "stu_msg";
			}
			for(int i = 0; i < list.size(); i++) {
				PeBzzInvoiceInfo invoice = (PeBzzInvoiceInfo) list.get(i);
				invoice.setAddress(this.peBzzInvoiceInfo.getAddress());
				invoice.setAddressee(this.peBzzInvoiceInfo.getAddressee());
				invoice.setPhone(this.peBzzInvoiceInfo.getPhone());
				invoice.setTitle(this.peBzzInvoiceInfo.getTitle());
				invoice.setZipCode(this.peBzzInvoiceInfo.getZipCode());
				invoice.setCity(this.peBzzInvoiceInfo.getCity());
				invoice.setProvince(this.peBzzInvoiceInfo.getProvince());
				invoice.setEnumConstByFlagPostType(enumConstByFlagPostType);
				invoice.setGmfnsrsbh(peBzzInvoiceInfo.getGmfnsrsbh());
				invoice.setGmfdz(peBzzInvoiceInfo.getGmfdz());
				invoice.setGmfkhyh(peBzzInvoiceInfo.getGmfkhyh());
				invoice.setGmfyhzh(peBzzInvoiceInfo.getGmfyhzh());
				invoice.setGmfsjhm(peBzzInvoiceInfo.getGmfsjhm());
				invoice.setGmfdh(peBzzInvoiceInfo.getGmfdh());
				invoice.setEmail(peBzzInvoiceInfo.getEmail());
				invoice.setInvoiceRemark(peBzzInvoiceInfo.getInvoiceRemark());
				this.getGeneralService().save(invoice);
			}
		} catch (EntityException e) {
			this.setMsg("发票信息修改失败，请重新执行此操作！");
			this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
			return "stu_msg";
		}
		this.setMsg("发票信息修改成功！");
		this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
		return "stu_msg";
	}

	/**
	 * 比较时间
	 * 
	 * @param startDate
	 * @param days
	 * @param endDate
	 * @author linjie
	 */
	public boolean CompareTime(Date startDate, String days) {
		boolean flag = true;
		if (days == null || "".equals(days) || startDate == null || "".equals(startDate)) {
			flag = true;
		} else {
			Date date = new Date();

			long time1 = startDate.getTime();
			long time2 = date.getTime();
			// Lee 2015年2月10日 以分钟为单位进行比较
			long between_days = (time2 - time1) / (1000 * 60);
			if (between_days > Long.parseLong(days) * 24 * 60) {
				flag = false;
			} else {
				flag = true;
			}
		}
		return flag;
	}

	public boolean CompareTime(String sd, String days) {
		boolean flag = true;
		if (days == null || "".equals(days) || sd == null || "".equals(sd)) {
			flag = true;
		} else {
			Date date = new Date();
			try {
				Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(sd);
				long time1 = startDate.getTime();
				long time2 = date.getTime();
				// Lee 2015年2月10日 以分钟为单位进行比较
				long between_days = (time2 - time1) / (1000 * 60);
				if (between_days > Long.parseLong(days) * 24 * 60) {
					flag = false;
				} else {
					flag = true;
				}
			} catch (ParseException e) {
				e.printStackTrace();
				flag = false;
			}
		}
		return flag;
	}

	public boolean CompareLiveTime(String startDate, String endDate) {
		boolean flag = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = new Date();
		try {
			long time1 = sdf.parse(startDate).getTime();
			long time3 = sdf.parse(endDate).getTime();
			long time2 = date.getTime();
			long between_days = (time2 - time1) / (1000 * 60);
			long between_days2 = (time3 - time2) / (1000 * 60);
			if (between_days < 0 || between_days2 < 0)
				flag = true;
		} catch (ParseException e) {
			e.printStackTrace();
			flag = true;
		}
		return flag;
	}

	public boolean isHaveResource(String id) {
		boolean flag = false;
		String sql = "SELECT PR.ID FROM PE_RESOURCE PR JOIN INTERACTION_TEACHCLASS_INFO ITI ON ITI.FK_ZILIAO = PR.ID "
				+ "JOIN PE_BZZ_TCH_COURSE PBTC ON PBTC.ID = ITI.TEACHCLASS_ID WHERE PBTC.ID = '" + id + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list.size() > 0)
				flag = true;
		} catch (EntityException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 课程价格
	 * 
	 * @param time
	 * @param price
	 * @return String
	 * @author linjie
	 */
	public BigDecimal coursePrice(String time, BigDecimal price) {
		BigDecimal bigtime = new BigDecimal(time).setScale(2, BigDecimal.ROUND_HALF_UP);
		return bigtime.multiply(price).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 获得订单状态
	 * 
	 * @param invoices
	 * @return String
	 * @author linjie
	 */
	public String getOrderInvoiceState(Set invoices) {
		// Set set =
		// ((PeBzzOrderInfo)page.getItems().get(0)).getPeBzzInvoiceInfos();
		// System.out.println("**************************"+invoices.size());
		String name = null;
		if (invoices.size() == 0) {
			name = "未开";
		} else {
			Iterator<PeBzzInvoiceInfo> it = invoices.iterator();
			PeBzzInvoiceInfo i = it.next();
			name = i.getEnumConstByFlagFpOpenState().getName();
			// System.out.println("*********************************"+name);
		}
		return name;
	}
	
	/**
	 * 获得发票类型
	 * 
	 * @param invoices
	 * @return String
	 * @author linjie
	 */
	public String getOrderInvoiceType(Set invoices) {
		// Set set =
		// ((PeBzzOrderInfo)page.getItems().get(0)).getPeBzzInvoiceInfos();
		// System.out.println("**************************"+invoices.size());
		String code = null;
		if (invoices.size() == 0) {
			code = "999";
		} else {
			Iterator<PeBzzInvoiceInfo> it = invoices.iterator();
			PeBzzInvoiceInfo i = it.next();
			code = i.getEnumConstByInvoiceType().getCode();
//			System.out.println("*********************************"+code);
		}
		return code;
	}

	public String getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(String examStatus) {
		this.examStatus = examStatus;
	}

	public String getTotalLearningHours() {
		return totalLearningHours;
	}

	public void setTotalLearningHours(String totalLearningHours) {
		this.totalLearningHours = totalLearningHours;
	}

	/**
	 * 设置属性
	 * 
	 * @return String
	 * @author linjie
	 * @throws PlatformException
	 * @throws UnsupportedEncodingException
	 */
	public String InteractionStuManage() throws PlatformException, UnsupportedEncodingException {

		HttpServletRequest request = ServletActionContext.getRequest();

		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String userId = userSession.getSsoUser().getId();

		OpenCourse openCourse = new OracleOpenCourse();

		try {
			openCourse.setBzzCourse(this.getPeBzzCourse(course_id));
		} catch (EntityException e) {
			e.printStackTrace();

		}
		request.getSession().removeAttribute("courseId");
		request.getSession().setAttribute("courseId", course_id);
		request.getSession().setAttribute("userId", userId);

		request.getSession().removeAttribute("openCourse");
		request.getSession().setAttribute("openCourse", openCourse);

		return "teacherInfo";
	}

	/**
	 * 获得所选课程
	 * 
	 * @param courseId
	 * @return String
	 * @author linjie
	 * @throws EntityException
	 */
	private PeBzzTchCourse getPeBzzCourse(String courseId) throws EntityException {

		DetachedCriteria peCourseDC = DetachedCriteria.forClass(PeBzzTchCourse.class);
		peCourseDC.add(Restrictions.eq("id", courseId));
		try {
			if (this.getGeneralService().getList(peCourseDC).size() > 0) {
				return (PeBzzTchCourse) this.getGeneralService().getList(peCourseDC).get(0);
			} else {
				return new PeBzzTchCourse();
			}
		} catch (EntityException e) {
			e.printStackTrace();
			throw new EntityException("没有所选的课程");
		}
	}

	/**
	 * 此方法后来添加，为了校验进入学时是，选课是否已经申请退费
	 * 
	 * @return String
	 * @author linjie
	 */
	public String checkGoStudy() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);

		Map map = new HashMap();
		DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peBzzOrderInfo.enumConstByFlagRefundState", DetachedCriteria.LEFT_JOIN);
		dc.add(Restrictions.eq("id", this.electiveId));
		List list = null;
		try {
			list = this.getGeneralService().getList(dc);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrBzzTchStuElective ele = null;
		if (list.size() > 0) {
			ele = (PrBzzTchStuElective) list.get(0);
			if (ele.getPeBzzOrderInfo() != null) {// 正常购买的课程
				EnumConst ec = ele.getPeBzzOrderInfo().getEnumConstByFlagRefundState();
				if (ec != null) {
					String code = ec.getCode();
					if (!code.equals("2")) { // 已申请获已退费
						map.put("info", "true"); // 此时不可以学习
					} else {
						map.put("info", "false"); // 可学习
					}
				} else {
					map.put("info", "false");
				}
			} else {// 免费课程或者迁移来的课程，无法判断是否退费
				map.put("info", "false");
			}
		} else { // 已审核
			map.put("info", "true"); // 不可学习
		}
		String result = JsonUtil.toJSONString(map);
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.getWriter().write(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return NONE;
	}

	private String course_id;

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	/**
	 * 已通过课程
	 * 
	 * @return String
	 * @author linjie
	 */
	public String toPassedCourses() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		this.peBzzStudent = this.studentWorkspaceService.initStudentInfo(ssoUser.getId());
		this.initCouresTypeAndCourseTypeList();
		Map params = new HashMap();
		params.put("studentId", this.peBzzStudent.getId());
		params.put("courseType", courseType);
		params.put("courseCode", this.courseCode);
		params.put("courseName", this.courseName);// 添加课程名称/编号查询条件
		params.put("examStatus", StudyProgress.PASSED);
		params.put("classHour", classHour);
		params.put("pageSize", pageSize);
		params.put("startIndex", startIndex);
		// Lee start 2014年3月5日 添加获得学时时间查询条件
		params.put("startDate", selSendDateStart);
		params.put("endDate", selSendDateEnd);
		// Lee end
		try {
			// this.page =
			// this.studentWorkspaceService.initPassedCourses(params);
			this.page = this.studentWorkspaceService.initPassedCourses(this.peBzzStudent.getId(), courseType, time, teacher, coursearea,
					selSendDateStart, selSendDateEnd, courseCode, courseName, orderInfo, orderType, pageSize, startIndex);
			List tongjiList = this.studentWorkspaceService.statisPassedCourse(this.peBzzStudent.getId(), courseType, time, teacher,
					coursearea, selSendDateStart, selSendDateEnd, courseCode, courseName);
			BigDecimal totalNum = new BigDecimal("0.0");
			BigDecimal bNum = new BigDecimal("0.0");
			BigDecimal xNum = new BigDecimal("0.0");
			if (tongjiList.size() > 0) {
				String[] tongjiArr = tongjiList.get(0).toString().split(",");
				totalNum = new BigDecimal(tongjiArr[0]).setScale(1, BigDecimal.ROUND_HALF_UP);
				bNum = new BigDecimal(tongjiArr[1]).setScale(1, BigDecimal.ROUND_HALF_UP);
				xNum = new BigDecimal(tongjiArr[2]).setScale(1, BigDecimal.ROUND_HALF_UP);
			}
			ServletActionContext.getRequest().setAttribute("a_list", totalNum);
			ServletActionContext.getRequest().setAttribute("b_list", bNum);
			ServletActionContext.getRequest().setAttribute("c_list", xNum);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return "toPassedCourses";
	}

	/**
	 * 已通过课程
	 * 
	 * @return String
	 * @author linjie
	 */
	// public String toPassedCourses() {
	// UserSession userSession = (UserSession) ActionContext.getContext()
	// .getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
	// SsoUser ssoUser = userSession.getSsoUser();
	// this.peBzzStudent = this.studentWorkspaceService
	// .initStudentInfo(ssoUser.getId());
	// this.initCouresTypeAndCourseTypeList();
	// Map params = new HashMap();
	// params.put("studentId", this.peBzzStudent.getId());
	// params.put("courseType", courseType);
	// params.put("courseCode", this.courseCode);
	// params.put("courseName", this.courseName);// 添加课程名称/编号查询条件
	// params.put("examStatus", StudyProgress.PASSED);
	// params.put("classHour", classHour);
	// params.put("pageSize", pageSize);
	// params.put("startIndex", startIndex);
	// // Lee start 2014年3月5日 添加获得学时时间查询条件
	// params.put("startDate", selSendDateStart);
	// params.put("endDate", selSendDateEnd);
	// // Lee end
	// try {
	// // this.page =
	// // this.studentWorkspaceService.initPassedCourses(params);
	// this.page = this.studentWorkspaceService.initPassedCourses(
	// this.peBzzStudent.getId(), courseType, time, teacher,
	// coursearea, selSendDateStart, selSendDateEnd, courseCode,
	// courseName, pageSize, startIndex);
	// List tongjiList = this.studentWorkspaceService.statisPassedCourse(
	// this.peBzzStudent.getId(), courseType, time, teacher,
	// coursearea, selSendDateStart, selSendDateEnd, courseCode,
	// courseName);
	// BigDecimal totalNum = new BigDecimal("0.0");
	// BigDecimal bNum = new BigDecimal("0.0");
	// BigDecimal xNum = new BigDecimal("0.0");
	// if (tongjiList.size() > 0) {
	// String[] tongjiArr = tongjiList.get(0).toString().split(",");
	// totalNum = new BigDecimal(tongjiArr[0]).setScale(1,
	// BigDecimal.ROUND_HALF_UP);
	// bNum = new BigDecimal(tongjiArr[1]).setScale(1,
	// BigDecimal.ROUND_HALF_UP);
	// xNum = new BigDecimal(tongjiArr[2]).setScale(1,
	// BigDecimal.ROUND_HALF_UP);
	// }
	// ServletActionContext.getRequest().setAttribute("a_list", totalNum);
	// ServletActionContext.getRequest().setAttribute("b_list", bNum);
	// ServletActionContext.getRequest().setAttribute("c_list", xNum);
	// } catch (Exception e1) {
	// e1.printStackTrace();
	// }
	// return "toPassedCourses";
	// }
	/**
	 * 此方法用于未通过课程再去支付功能
	 * 
	 * @return String
	 * @author cailei
	 */
	public String incompleteCoursePayment() {
		this.initStudent();
		String opid = ServletActionContext.getRequest().getParameter("opencourseId");
		// Lee start 2014年1月24日
		// 查看课程是否下线
		String err_course_sql = "SELECT B.CODE FROM PR_BZZ_TCH_OPENCOURSE A INNER JOIN PE_BZZ_TCH_COURSE B ON A.FK_COURSE_ID = B.ID WHERE B.FLAG_OFFLINE = '22' AND A.ID = '"
				+ opid + "'";
		String err_msg = "";
		try {
			List err_course_list = this.getGeneralService().getBySQL(err_course_sql);
			if (err_course_list == null || err_course_list.size() == 0)
				err_msg = "课程已下线！请购买其他课程！";
		} catch (Exception e) {
			err_msg = "课程验证失败！";
		} finally {
			this.setMsg(err_msg);
			if (!"".equals(err_msg)) {
				this.setTogo("close");
				return "msg";
			}
		}
		// Lee end
		// 查看是否为完成学习课程
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria tcsDc = DetachedCriteria.forClass(TrainingCourseStudent.class);
		tcsDc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
		tcsDc.add(Restrictions.eq("learnStatus", "COMPLETED"));
		tcsDc.add(Restrictions.eq("prBzzTchOpencourse.id", opid));
		tcsDc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
		List tcsList = null;
		try {
			tcsList = this.getGeneralService().getList(tcsDc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		// 未完成学习
		if (tcsList == null || tcsList.size() < 1) {
			DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchStuElectiveBack.class);
			dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
			dc.createCriteria("prBzzTchOpencourse", "prBzzTchOpencourse");
			dc.createCriteria("peBzzOrderInfo.enumConstByFlagOrderIsValid", "enumConstByFlagOrderIsValid");
			dc.createCriteria("peBzzOrderInfo.enumConstByFlagOrderIsIncomplete", "enumConstByFlagOrderIsIncomplete");
			// 添加enumConstByFlagPaymentState zgl
			dc.createCriteria("peBzzOrderInfo.enumConstByFlagPaymentState", "enumConstByFlagPaymentState");
			dc.add(Restrictions.eq("enumConstByFlagOrderIsValid.code", "1"));// 有效订单
			dc.add(Restrictions.eq("enumConstByFlagOrderIsIncomplete.code", "1"));// 重新购买订单
			dc.add(Restrictions.eq("enumConstByFlagPaymentState.code", "0"));// 2015年4月18日
			// 增加
			// 未到账
			// 条件
			dc.add(Restrictions.eq("prBzzTchOpencourse.id", opid));
			dc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
			List bList = null;
			try {
				bList = this.getGeneralService().getList(dc);
			} catch (EntityException e) {
				e.printStackTrace();
			}
			if (bList != null) {
				if (bList.size() > 0) {
					this.setMsg("此门课程已经生成订单，请您去我的订单里面继续支付未完成的订单！");
					return "stu_msg";
				}
			}
		}
		if (ServletActionContext.getRequest().getSession().getAttribute("opencourseIdList") != null) {
			ServletActionContext.getRequest().getSession().removeAttribute("opencourseIdList");
		}
		try {
			this.getGeneralService().transferIncompleteRecord(this.peBzzStudent.getId(), opid);// 将elective和back迁移到备份表，删除原数据。
		} catch (EntityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		List list = new ArrayList();
		list.add(opid);
		ServletActionContext.getRequest().getSession().setAttribute("opencourseIdList", list);
		this.flag = "incompleteCoursePayment";
		// ServletActionContext.getRequest().getSession().setAttribute("incompleteCoursePayment",
		// "incompleteCoursePayment");
		return "incompleteCoursePayment";
	}

	public PeBzzInvoiceInfo getLastInvoiceInfo() {

		return lastInvoiceInfo;
	}

	public void setLastInvoiceInfo(PeBzzInvoiceInfo lastInvoiceInfo) {
		this.lastInvoiceInfo = lastInvoiceInfo;
	}

	/**
	 * 进入在线考试
	 * 
	 * @return String
	 * @author qiaoshijia
	 */
	public String firstOnlineExam() {
		HttpServletRequest request = ServletActionContext.getRequest();
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzPiciStudent.class);
		dc.createCriteria("peBzzPici", "peBzzPici").addOrder(Order.desc("startDatetime")).createCriteria("enumConstByFlagPiciStatus",
				"enumConstByFlagPiciStatus").add(Restrictions.eq("namespace", "FlagPiciStatus")).add(Restrictions.eq("code", "0"));
		dc.createCriteria("peBzzStudent", "peBzzStudent").add(Restrictions.eq("ssoUser", us.getSsoUser()));
		try {
			this.page = this.getGeneralService().getByPage(dc, pageSize, startIndex);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		sb.append("   SELECT pici.id,pici.name,pici.START_TIME ,pici.END_TIME,pici.PASSSCORE ,pici.EXAM_TIMES  ");
		sb.append(" FROM PE_BZZ_PICI PICI ");
		sb.append(" INNER JOIN ENUM_CONST EC ");
		sb.append("   ON PICI.EXAM_TYPE = EC.ID ");
		sb.append(" INNER JOIN ENUM_CONST ec1 ");
		sb.append("   ON pici.status = ec1.id ");
		sb.append("WHERE EC.CODE = '1' ");
		sb.append("  AND ec1.code = '0'  ");
		sb.append(" AND PICI.ID NOT IN ");
		sb.append(" (SELECT PI.ID ");
		sb.append("  FROM PE_BZZ_PICI PI ");
		sb.append(" INNER JOIN PE_BZZ_PICI_STUDENT PBPS ");
		sb.append("ON PI.ID = PBPS.PC_ID ");
		sb.append("INNER JOIN PE_BZZ_STUDENT PBS ");
		sb.append("   ON PBPS.STU_ID = PBS.ID ");
		sb.append(" AND PBS.FK_SSO_USER_ID = '"+us.getId()+"') ");
		try {
			this.piciList = this.getGeneralService().getBySQL(sb.toString());
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "firstOnlineExam";
	}
	/***************************************************************************
	 * 
	 * @return
	 */
	public String openOnlineExam(){
		HttpServletRequest request = ServletActionContext.getRequest();
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzPici.class);
		dc.createCriteria("enumConstByFlagExamTypes", "enumConstByFlagExamTypes");
		dc.add(Restrictions.eq("enumConstByFlagExamTypes.code", "1"));
		try {
			this.page = this.getGeneralService().getByPage(dc, pageSize, startIndex);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.paramter = "open";
		return "firstOnlineExam";
	}
	public String piciEnter(){
		HttpServletRequest request = ServletActionContext.getRequest();
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String pc_id = request.getParameter("batch_id");
		String userId = us.getId();
		String hql = " from PeBzzStudent where fk_sso_user_id = '"+userId+"'";
		String piciHql = " from PeBzzPici where id = '"+pc_id+"'";
		List<PeBzzStudent> list = null ;
		try {
			list = this.getGeneralService().getByHQL(hql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<PeBzzPici> piciList =null ;
		try {
			piciList = this.getGeneralService().getByHQL(piciHql);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PeBzzPiciStudent peBzzPiciStudent = new PeBzzPiciStudent();
		peBzzPiciStudent.setPeBzzPici(piciList.get(0));
		peBzzPiciStudent.setPeBzzStudent(list.get(0));
		try {
			this.getGeneralService().save(peBzzPiciStudent);
		} catch (EntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.firstOnlineExam();
		return "firstOnlineExam";
	}

	/**
	 * 判断当前时间是否在指定的时间内
	 * 
	 * @param startDate
	 * @param endDate
	 * @author linjie
	 */
	public boolean compareTime(long startDate, long endDate) {
		Date date = new Date();
		if (startDate < date.getTime() && endDate > date.getTime()) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前时间是否大于指定时间
	 * 
	 * @param startDate
	 * @param endDate
	 * @author linjie
	 */
	public boolean compareTime(long startDate) {
		Date date = new Date();
		if (startDate < date.getTime()) {
			return true;
		}
		return false;
	}

	public boolean compareTime(String endDate) {
		try {
			Date ed = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endDate);
			Date date = new Date();
			if (ed.getTime() > date.getTime()) {
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public boolean compareTime(Date endDate) {
		Date date = new Date();
		if (endDate.getTime() > date.getTime()) {
			return true;
		}
		return false;
	}

	/**
	 * 修改utf-8编码格式
	 * 
	 * @param str
	 * @return String
	 * @author linjie
	 */
	public String transcoding(String str) {
		try {
			return URLEncoder.encode(URLEncoder.encode(str, "utf-8"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public String getUnChooseCourseNum() {
		return unChooseCourseNum;
	}

	public void setUnChooseCourseNum(String unChooseCourseNum) {
		this.unChooseCourseNum = unChooseCourseNum;
	}

	public String getPeBulletinnum() {
		return peBulletinnum;
	}

	public void setPeBulletinnum(String peBulletinnum) {
		this.peBulletinnum = peBulletinnum;
	}

	/**
	 * 计算过期时间
	 * 
	 * @param startDate
	 *            开始学习时间
	 * @param days
	 *            有效天数
	 * @return 过期日期或-
	 * @author Lee 2014年3月11日
	 */
	public String CalcDate(Date startTime, String days) {
		if (days == null || "".equals(days) || "null".equals(days) || "0".equals(days) || startTime == null)
			return "-";
		startTime.setDate(startTime.getDate() + Integer.parseInt(days));
		// String return_ = startTime.toString();
		String return_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime);
		startTime.setDate(startTime.getDate() - Integer.parseInt(days));
		return return_;
	}

	public String CalcDate(String sd, String days) {
		if (days == null || "".equals(days) || "null".equals(days) || "0".equals(days) || sd == null)
			return "-";
		try {
			Date startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sd);
			startTime.setDate(startTime.getDate() + Integer.parseInt(days));
			// String return_ = startTime.toString();
			String return_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startTime);
			startTime.setDate(startTime.getDate() - Integer.parseInt(days));
			return return_;
		} catch (ParseException e) {
			e.printStackTrace();
			return "-";
		}
	}

	public List getSuggestrenList() {
		return suggestrenList;
	}

	public void setSuggestrenList(List suggestrenList) {
		this.suggestrenList = suggestrenList;
	}

	public String getTchprice() {
		return tchprice;
	}

	public void setTchprice(String tchprice) {
		this.tchprice = tchprice;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	/**
	 * 在线直播支付列表
	 * 
	 * @return
	 */
	public String toSacLive() {
		this.page = this.studentWorkspaceService.initSacLive(courseCode, courseName, selSendDateStart, selSendDateEnd, teacher, pageSize,
				startIndex);
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		PeBzzStudent pbs = this.studentWorkspaceService.initStudentInfo(us.getSsoUser().getId());
		String entype = "";
		try {
			entype = pbs.getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode();
		} catch (Exception e) {
			entype = "";
		}
		ServletActionContext.getRequest().setAttribute("ptp", entype);
		return "toSacLive";
	}

	/**
	 * 在线直播学习列表
	 * 
	 * @return
	 */
	public String toSacLiveStudy() {
		this.page = this.studentWorkspaceService.initSacLiveStudy(courseCode, courseName, selSendDateStart, selSendDateEnd, teacher,
				orderInfo, orderType, pageSize, startIndex);
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		PeBzzStudent pbs = this.studentWorkspaceService.initStudentInfo(us.getSsoUser().getId());
		String entype = "";
		try {
			entype = pbs.getPeEnterprise().getEnumConstByFlagEnterpriseType().getCode();
		} catch (Exception e) {
			entype = "";
		}
		ServletActionContext.getRequest().setAttribute("ptp", entype);
		return "toSacLiveStudy";
	}

	/**
	 * 直播学习详情
	 * 
	 * @return
	 */
	public String showDetail() {
		UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
		String pbtcId = ServletActionContext.getRequest().getParameter("id");
		StringBuffer sbPbtc = new StringBuffer();
		// 课程信息
		sbPbtc.append("SELECT CODE, NAME FROM PE_BZZ_TCH_COURSE WHERE ID = '" + pbtcId + "'");
		// 登陆登出记录
		StringBuffer sbWeHistory = new StringBuffer();
		sbWeHistory.append(" SELECT DISTINCT * FROM ( ");
		sbWeHistory.append(" SELECT TO_CHAR(TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sbWeHistory.append("        (WH_JOINTIME + 8 * 1000 * 3600) / 1000 / 24 / 60 / 60,'yyyy-MM-dd hh24:mi:ss') D1, ");
		sbWeHistory.append("        TO_CHAR(TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sbWeHistory.append("        (WH_LEAVETIME + 8 * 1000 * 3600) / 1000 / 24 / 60 / 60,'yyyy-MM-dd hh24:mi:ss') D2 ");
		sbWeHistory.append("   FROM WE_HISTORY WH, LIVE_SEQ_STU LSS ");
		sbWeHistory.append("  WHERE WH.WH_UID = LSS.ID ");
		sbWeHistory.append("    AND WH.WH_WEBCASTID = (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + pbtcId + "') ");
		sbWeHistory.append("    AND LSS.LOGIN_ID = '" + us.getSsoUser().getId() + "' ");
		sbWeHistory.append("    )ORDER BY TO_DATE(D1,'yyyy-MM-dd hh24:mi:ss') ASC ");
		;
		// 点名记录
		StringBuffer sbWeRollCall = new StringBuffer();
		sbWeRollCall.append(" SELECT DISTINCT * FROM ( ");
		sbWeRollCall.append(" SELECT TO_CHAR(TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sbWeRollCall.append("        (WR_STARTTIME + 8 * 1000 * 3600) / 1000 / 24 / 60 / 60,'yyyy-MM-dd hh24:mi:ss') D1, ");
		sbWeRollCall.append("        TO_CHAR(TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sbWeRollCall.append("        (WR_ENDTIME + 8 * 1000 * 3600) / 1000 / 24 / 60 / 60,'yyyy-MM-dd hh24:mi:ss') D2, ");
		sbWeRollCall.append("        DECODE(WRU.WRU_PRESENT,'true',1,0) ");
		sbWeRollCall.append("   FROM WE_ROLLCALL WR, WE_ROLLCALL_USERS WRU, LIVE_SEQ_STU LSS ");
		sbWeRollCall.append("  WHERE WR.WR_ID = WRU.WRU_WR_ID ");
		sbWeRollCall.append("    AND WRU.WRU_UID = LSS.ID ");
		sbWeRollCall.append("    AND WR.WR_WEBCASTID = (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + pbtcId + "') ");
		sbWeRollCall.append("    AND LSS.LOGIN_ID = '" + us.getSsoUser().getId() + "'");
		sbWeRollCall.append("    ORDER BY TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') +  ");
		sbWeRollCall.append("        WR_STARTTIME / 1000 / 24 / 60 / 60 ASC) ");
		// 答疑记录
		StringBuffer sbWeQa = new StringBuffer();
		sbWeQa.append(" SELECT DISTINCT * FROM ( ");
		sbWeQa.append(" SELECT TO_CHAR(TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sbWeQa.append("        (WQ.WQ_SUBMITTIME + 8 * 1000 * 3600) / 1000 / 24 / 60 / 60,'yyyy-MM-dd hh24:mi:ss') D1, ");
		sbWeQa.append("        WQ.WQ_QUESTION, ");
		sbWeQa.append("        NVL(WQ.WQ_RESPONSE,'-') WQ_RESPONSE, ");
		sbWeQa.append("        WQ.WQ_NAME ");
		sbWeQa.append("   FROM WE_QA WQ, LIVE_SEQ_STU LSS ");
		sbWeQa.append("  WHERE WQ.WQ_SUBMITTER = LSS.ID ");
		sbWeQa.append("    AND WQ.WQ_WEBCASTID = (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '" + pbtcId + "') ");
		sbWeQa.append("    AND LSS.LOGIN_ID = '" + us.getSsoUser().getId() + "' ");
		sbWeQa.append("    ORDER BY TO_DATE('1970-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') + ");
		sbWeQa.append("    WQ.WQ_SUBMITTIME / 1000 / 24 / 60 / 60 ASC) ");
		StringBuffer sbMinSumSb = new StringBuffer();
		sbMinSumSb.append(" SELECT MINSUM, ");
		sbMinSumSb.append("        TRUE_NAME, ");
		sbMinSumSb.append("        COURSE_LEN LIVESUM, ");
		sbMinSumSb.append("        TRUNC(MINSUM * 100 / COURSE_LEN, 2) LIVEPERC ");
		sbMinSumSb.append("   FROM (SELECT TRUNC(SUM((WH.WH_LEAVETIME - WH.WH_JOINTIME) / 60 / 1000), 2) MINSUM, ");
		sbMinSumSb.append("                PBS.TRUE_NAME, ");
		sbMinSumSb.append("                PBTC.COURSE_LEN ");
		sbMinSumSb.append("           FROM WE_HISTORY        WH, ");
		sbMinSumSb.append("                PE_BZZ_STUDENT    PBS, ");
		sbMinSumSb.append("                LIVE_SEQ_STU      LSS, ");
		sbMinSumSb.append("                PE_BZZ_TCH_COURSE PBTC ");
		sbMinSumSb.append("          WHERE WH.WH_UID = LSS.ID ");
		sbMinSumSb.append("            AND LSS.LOGIN_ID = PBS.FK_SSO_USER_ID ");
		sbMinSumSb.append("            AND LSS.LIVE_ID = PBTC.LIVEID ");
		sbMinSumSb.append("            AND PBS.FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "' ");
		sbMinSumSb.append("            AND WH.WH_WEBCASTID = ");
		sbMinSumSb.append("                (SELECT LIVEID ");
		sbMinSumSb.append("                   FROM PE_BZZ_TCH_COURSE ");
		sbMinSumSb.append("                  WHERE ID = '" + pbtcId + "') ");
		sbMinSumSb.append("          GROUP BY WH.WH_NICKNAME, ");
		sbMinSumSb.append("                   WH.WH_WEBCASTID, ");
		sbMinSumSb.append("                   PBS.TRUE_NAME, ");
		sbMinSumSb.append("                   PBTC.COURSE_LEN) ");
		try {
			// 小统计
			List minSumList = this.getGeneralService().getBySQL(sbMinSumSb.toString());
			if (null == minSumList || minSumList.size() <= 0) {
				this.setMsg("暂无直播数据");
				this.setTogo("close");
				return "stu_msg";
			}
			// 互动调查记录
			StringBuffer sbWeVote = new StringBuffer();
			sbWeVote
					.append(" SELECT WVV_ID,WVV_SUBJECT FROM WE_VOTE_VOTELIST WHERE WVV_WEBCASTID = (SELECT LIVEID FROM PE_BZZ_TCH_COURSE WHERE ID = '"
							+ pbtcId + "') ");
			// 课程信息
			List listPbtc = this.getGeneralService().getBySQL(sbPbtc.toString());
			// 互动调查记录
			List listVote = this.getGeneralService().getBySQL(sbWeVote.toString());
			// 问题内容
			Map<Object, List> mapVoteQuestion = new HashMap<Object, List>();
			// 问题选项
			Map<Object, List> mapVoteOption = new HashMap<Object, List>();
			// 问题回答
			Map<Object, List> mapVoteResult = new HashMap<Object, List>();
			// 循环调查内容
			for (int i = 0; i < listVote.size(); i++) {
				Object voteId = ((Object[]) listVote.get(i))[0];
				StringBuffer sbWeVoteQuestion = new StringBuffer();
				sbWeVoteQuestion.append(" SELECT WVQ_ID,WVQ_CONTENT FROM WE_VOTE_QUESTIONS WHERE WVQ_WVV_ID = '" + voteId + "'");
				List listVoteQuestion = this.getGeneralService().getBySQL(sbWeVoteQuestion.toString());
				mapVoteQuestion.put(voteId, listVoteQuestion);
				/* 说明：如果内容选项value为空，调查回答的value不为空，则为问题题，其他为选择题 */
				// 循环调查内容选项
				for (int ii = 0; ii < listVoteQuestion.size(); ii++) {
					Object voteQuestionId = ((Object[]) listVoteQuestion.get(ii))[0];
					StringBuffer sbWeVoteOption = new StringBuffer();
					sbWeVoteOption.append(" SELECT WVO_ID,WVO_VALUE FROM WE_VOTE_OPTIONS WHERE WVO_WVQ_ID = '" + voteQuestionId + "' ");
					List listVoteOption = this.getGeneralService().getBySQL(sbWeVoteOption.toString());
					mapVoteOption.put(voteQuestionId, listVoteOption);
					// 循环调查回答
					for (int iii = 0; iii < listVoteOption.size(); iii++) {
						Object voteOptionId = ((Object[]) listVoteOption.get(iii))[0];
						StringBuffer sbWeVoteResult = new StringBuffer();
						sbWeVoteResult
								.append(" SELECT WVR_WVO_ID,WVR_ANSWER, WVR_WVO_ID WWI FROM WE_VOTE_RESULTS WVR,LIVE_SEQ_STU LSS WHERE LSS.LOGIN_ID = '"
										+ us.getSsoUser().getId() + "' AND WVR.WVR_UID = LSS.ID AND WVR_WVO_ID = '" + voteOptionId + "'");
						List listVoteResult = this.getGeneralService().getBySQL(sbWeVoteResult.toString());
						mapVoteResult.put(voteOptionId, listVoteResult);
					}
				}
			}
			// 课程信息
			ServletActionContext.getRequest().setAttribute("listPbtc", listPbtc);
			// 登陆登出记录
			List listWeHistory = this.getGeneralService().getBySQL(sbWeHistory.toString());
			// 点名记录
			List listWeRollCall = this.getGeneralService().getBySQL(sbWeRollCall.toString());
			// 答疑记录
			List listWeQa = this.getGeneralService().getBySQL(sbWeQa.toString());
			// 登陆登出记录
			ServletActionContext.getRequest().setAttribute("listWeHistory", listWeHistory);
			// 点名记录
			ServletActionContext.getRequest().setAttribute("listWeRollCall", listWeRollCall);
			// 问卷调查记录
			ServletActionContext.getRequest().setAttribute("listVote", listVote);
			// 问卷调查问题内容
			ServletActionContext.getRequest().setAttribute("mapVoteQuestion", mapVoteQuestion);
			// 问卷调查问题选项
			ServletActionContext.getRequest().setAttribute("mapVoteOption", mapVoteOption);
			// 问卷调查问题回答
			ServletActionContext.getRequest().setAttribute("mapVoteResult", mapVoteResult);
			// 答疑记录
			ServletActionContext.getRequest().setAttribute("listWeQa", listWeQa);
			ServletActionContext.getRequest().setAttribute("minSum", ((Object[]) minSumList.get(0))[0]);
			ServletActionContext.getRequest().setAttribute("stuName", ((Object[]) minSumList.get(0))[1]);
			ServletActionContext.getRequest().setAttribute("liveSum", ((Object[]) minSumList.get(0))[2]);
			ServletActionContext.getRequest().setAttribute("livePerc", ((Object[]) minSumList.get(0))[3]);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "showDetail";
	}

	public String getKtimestart() {
		return ktimestart;
	}

	public void setKtimestart(String ktimestart) {
		this.ktimestart = ktimestart;
	}

	public String getKtimeend() {
		return ktimeend;
	}

	public void setKtimeend(String ktimeend) {
		this.ktimeend = ktimeend;
	}

	public String getEtimestart() {
		return etimestart;
	}

	public void setEtimestart(String etimestart) {
		this.etimestart = etimestart;
	}

	public String getEtimeend() {
		return etimeend;
	}

	public void setEtimeend(String etimeend) {
		this.etimeend = etimeend;
	}

	public String getMybatchid() {
		return mybatchid;
	}

	public void setMybatchid(String mybatchid) {
		this.mybatchid = mybatchid;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getFpstatus() {
		return fpstatus;
	}

	public void setFpstatus(String fpstatus) {
		this.fpstatus = fpstatus;
	}

	public String getOrderprice() {
		return orderprice;
	}

	public void setOrderprice(String orderprice) {
		this.orderprice = orderprice;
	}

	public String getTotalhourbx() {
		return totalhourbx;
	}

	public void setTotalhourbx(String totalhourbx) {
		this.totalhourbx = totalhourbx;
	}

	public String getTotalhourxx() {
		return totalhourxx;
	}

	public void setTotalhourxx(String totalhourxx) {
		this.totalhourxx = totalhourxx;
	}

	public String getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}

	public List getCourseareaList() {
		return courseareaList;
	}

	public void setCourseareaList(List courseareaList) {
		this.courseareaList = courseareaList;
	}

	public String getSuggestren() {
		return suggestren;
	}

	public void setSuggestren(String suggestren) {
		this.suggestren = suggestren;
	}

	public String getCoursearea() {
		return coursearea;
	}

	public void setCoursearea(String coursearea) {
		this.coursearea = coursearea;
	}

	public String getZhanneixinNum() {
		return zhanneixinNum;
	}

	public void setZhanneixinNum(String zhanneixinNum) {
		this.zhanneixinNum = zhanneixinNum;
	}

	public String getTongzhigonggaoNum() {
		return tongzhigonggaoNum;
	}

	public void setTongzhigonggaoNum(String tongzhigonggaoNum) {
		this.tongzhigonggaoNum = tongzhigonggaoNum;
	}

	public String getDiaochawenjuanNum() {
		return diaochawenjuanNum;
	}

	public void setDiaochawenjuanNum(String diaochawenjuanNum) {
		this.diaochawenjuanNum = diaochawenjuanNum;
	}

	public String getWeizhifudingdanNum() {
		return weizhifudingdanNum;
	}

	public void setWeizhifudingdanNum(String weizhifudingdanNum) {
		this.weizhifudingdanNum = weizhifudingdanNum;
	}

	public String getXuankeqiNum() {
		return xuankeqiNum;
	}

	public void setXuankeqiNum(String xuankeqiNum) {
		this.xuankeqiNum = xuankeqiNum;
	}

	public BigDecimal getYufufeizhanghuyueNum() {
		return yufufeizhanghuyueNum;
	}

	public void setYufufeizhanghuyueNum(BigDecimal yufufeizhanghuyueNum) {
		this.yufufeizhanghuyueNum = yufufeizhanghuyueNum;
	}

	public String getZhengzaixuexikechengNum() {
		return zhengzaixuexikechengNum;
	}

	public void setZhengzaixuexikechengNum(String zhengzaixuexikechengNum) {
		this.zhengzaixuexikechengNum = zhengzaixuexikechengNum;
	}

	public String getDaikaoshikechengNum() {
		return daikaoshikechengNum;
	}

	public void setDaikaoshikechengNum(String daikaoshikechengNum) {
		this.daikaoshikechengNum = daikaoshikechengNum;
	}

	public String getZhuanxiangpeixunNum() {
		return zhuanxiangpeixunNum;
	}

	public void setZhuanxiangpeixunNum(String zhuanxiangpeixunNum) {
		this.zhuanxiangpeixunNum = zhuanxiangpeixunNum;
	}

	public String getZaixianzhibokechengNum() {
		return zaixianzhibokechengNum;
	}

	public void setZaixianzhibokechengNum(String zaixianzhibokechengNum) {
		this.zaixianzhibokechengNum = zaixianzhibokechengNum;
	}

	public String getZaixiankaoshiNum() {
		return zaixiankaoshiNum;
	}

	public void setZaixiankaoshiNum(String zaixiankaoshiNum) {
		this.zaixiankaoshiNum = zaixiankaoshiNum;
	}

	public BigDecimal getZhengzaixuexikechengTime() {
		return zhengzaixuexikechengTime;
	}

	public void setZhengzaixuexikechengTime(BigDecimal zhengzaixuexikechengTime) {
		this.zhengzaixuexikechengTime = zhengzaixuexikechengTime;
	}

	public BigDecimal getDaikaoshikechengTime() {
		return daikaoshikechengTime;
	}

	public void setDaikaoshikechengTime(BigDecimal daikaoshikechengTime) {
		this.daikaoshikechengTime = daikaoshikechengTime;
	}

	public void setYufeihislist(List yufeihislist) {
		this.yufeihislist = yufeihislist;
	}

	public List getYufeihislist() {
		return yufeihislist;
	}

	public String getOptamount() {
		return optamount;
	}

	public void setOptamount(String optamount) {
		this.optamount = optamount;
	}

	public String getOpttype() {
		return opttype;
	}

	public void setOpttype(String opttype) {
		this.opttype = opttype;
	}

	/**
	 * 删除专项中已选的自选课程
	 * 
	 * @return String
	 * @author Lee
	 */
	public String delCourseToBatch() {
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		Map map = new HashMap();
		String uid = userSession.getSsoUser().getId();

		HttpServletRequest request = ServletActionContext.getRequest();
		String openCourseId = "";
		String myBatchId = "";
		openCourseId = request.getParameter("openCourseId");
		myBatchId = request.getParameter("myBatchId");

		try {
			/* 1. 验证订单是否已经支付 */
			String checkOrderSql = "SELECT A.ID FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A INNER JOIN PE_BZZ_ORDER_INFO B ON A.FK_ORDER_ID = B.ID INNER JOIN ENUM_CONST C "
					+ " ON B.FLAG_PAYMENT_STATE = C.ID WHERE (C.CODE = '1' OR C.CODE = '2' OR B.NUM IS NOT NULL) AND A.FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '"
					+ uid + "') AND A.FK_TCH_OPENCOURSE_ID = '" + openCourseId + "'";
			List checkOrderList = this.getGeneralService().getBySQL(checkOrderSql);
			if (null == checkOrderList || checkOrderList.size() == 0) {
				String delCourseSql = "DELETE FROM PR_BZZ_TCH_STU_ELECTIVE_BACK A WHERE A.FK_TCH_OPENCOURSE_ID = '" + openCourseId
						+ "' AND A.FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '" + uid
						+ "') AND A.FK_ELE_COURSE_PERIOD_ID IS NULL";
				int exe_res = this.getGeneralService().executeBySQL(delCourseSql);
				if (exe_res == 0) {
					this.setMsg("删除失败！");
					this.setTogo("javascript:history.go(-1);");
					return "stu_msg";
				}
			} else {
				this.setMsg("管理员已经提交订单，此选课期课程不能再修改！");
				this.setTogo("javascript:history.go(-1);");
				return "stu_msg";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setMybatchid(myBatchId);
		return this.searchViewBatchCourse();// Lee
	}

	/**
	 * 问题及建议
	 * 
	 * @return
	 */
	public String questionAdvice() {
		try {
			// topic:title
			// type:type
			this.page = this.studentWorkspaceService.initQuestionAdvice(title, type, this.getPageSize(), this.getStartIndex());
			this.issueType = this.studentWorkspaceService.initIssueType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "questionAdvice";
	}

	public String addQuestionAdvice() {
		try {
			this.issueType = this.studentWorkspaceService.initIssueType();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "addQuestionAdvice";
	}

	public String saveQuestionAdvice() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		HttpServletRequest request = ServletActionContext.getRequest();
		String topic = request.getParameter("topic");
		String type = request.getParameter("type");
		String desc = request.getParameter("desc");
		String realPath = ServletActionContext.getServletContext().getRealPath("/UserFiles/File");
		String filePath = "";

		InputStream is = null;
		OutputStream os = null;
		try {
			// 文件上传
			if (file != null && !"".equals(file)) {
				for (int i = 0; i < file.size(); i++) {
					long ts = new Date().getTime();
					is = new FileInputStream(file.get(i));
					os = new FileOutputStream(new File(realPath, ts + "_" + fileFileName.get(i)));
					byte[] buffer = new byte[500];

					int length = 0;

					while (-1 != (length = is.read(buffer, 0, buffer.length))) {
						os.write(buffer);
					}
					filePath += ts + "_" + fileFileName.get(i) + ",";
				}
				filePath = filePath.substring(0, filePath.length() - 1);
			}

			String sql = "INSERT INTO USER_ISSUE (ID,TOPIC,UITYPE, FILELINK,FK_SSO_USER_ID,PHONE,WHODATE,ISSUEDESCRIBE) SELECT SYS_GUID(),'"
					+ topic
					+ "','"
					+ type
					+ "','"
					+ filePath
					+ "',PBS.FK_SSO_USER_ID,PBS.MOBILE_PHONE,SYSDATE,'"
					+ desc
					+ "' FROM PE_BZZ_STUDENT PBS WHERE PBS.FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'";

			int res = this.getGeneralService().executeBySQL(sql);
			if (res > 0) {
				this.setMsg("您的问题/建议已经提交成功，谢谢您的支持！");
				this.setTogo("/entity/workspaceStudent/studentWorkspace_questionAdvice.action");
			} else {
				this.setMsg("提问/建议保存失败");
				this.setTogo("back");
			}
		} catch (EntityException e) {
			// e.printStackTrace();
			this.setMsg("提问/建议保存失败");
			this.setTogo("back");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			this.setMsg("附件不存在");
			this.setTogo("back");
		} catch (IOException e) {
			this.setMsg("附件上传失败");
			this.setTogo("back");
			e.printStackTrace();
		} finally {
			try {
				if (os != null)
					os.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "stu_msg";
	}

	public String downloadFile() {
		return SUCCESS;
	}

	public String showQuestionAdvice() {
		try {
			this.issueDetail = this.studentWorkspaceService.showQuestionAdvice(qid);
			this.replyList = this.studentWorkspaceService.showQuestionAdviceReplys(qid);

			// 附件
			fileList = new ArrayList();
			Object[] fklint = (Object[]) issueDetail.get(0);
			String[] file = ((String) fklint[5]).split(",");
			for (int i = 0; i < file.length; i++) {
				List fileInfoList = new ArrayList();
				String link = file[i];
				String fileName = file[i].substring(link.lastIndexOf("/") + 1);
				fileInfoList.add(link);
				fileInfoList.add(fileName);
				fileList.add(fileInfoList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showQuestionAdvice";
	}

	/**
	 * 2014-10-26 学员端常见问题库
	 * 
	 * @return
	 */
	public String frequentlyQuestion() {
		try {
			this.typeList = this.studentWorkspaceService.initQuestionType();
			this.page = this.studentWorkspaceService.initQuestion(title, types, keywords, this.getPageSize(), this.getStartIndex());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "frequentlyQuestion";
	}

	/**
	 * 2014-10-26 常见问题详情
	 * 
	 * @return
	 */
	public String showQuestion() {
		try {
			String sql = "UPDATE FREQUENTLY_ASKED_QUESTIONS SET VIEWS = VIEWS + 1 WHERE ID = '" + qid + "'";
			this.getGeneralService().executeBySQL(sql);
			this.question = this.studentWorkspaceService.showQuestion(qid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "showQuestion";
	}

	/**
	 * 导出问卷调查结果
	 * 
	 * @return
	 */
	public String resultSetToExcel() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		try {
			HSSFSheet sheet = wb.createSheet("常见问题列表");
			HSSFRow subRow = sheet.createRow((int) 0);
			HSSFCell subCell = subRow.createCell((short) 0);
			subCell.setCellValue("编号");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 1);
			subCell.setCellValue("问题标题");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 2);
			subCell.setCellValue("问题分类");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 3);
			subCell.setCellValue("关键词");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 4);
			subCell.setCellValue("提问者角色");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 5);
			subCell.setCellValue("问题描述");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 6);
			subCell.setCellValue("问题解决方案");
			subCell.setCellStyle(style);
			subCell = subRow.createCell((short) 7);
			subCell.setCellValue("备注");
			subCell.setCellStyle(style);

			if (ids != null && !"".equals(ids)) {
				String[] _ids = this.ids.split(",");

				String sql = "select faq.title,faq.types questionType,faq.keywords,faq.roles,faq.description,faq.solution,faq.remarks from Frequently_Asked_Questions faq "
						+ " where faq.id in (";
				for (int i = 0; i < _ids.length; i++) {
					sql += "'" + _ids[i] + "',";
				}
				sql += "'') order by faq.create_date desc";
				List questions = new ArrayList();
				questions = this.getGeneralService().getBySQL(sql);
				Reader inStream = null;
				for (int k = 0; k < questions.size(); k++) {
					inStream = null;
					String data = "";
					subRow = sheet.createRow((int) k + 1);
					Object[] question = (Object[]) questions.get(k);
					Clob clob = (Clob) question[5];
					if (clob != null && !"".equals(clob)) {
						inStream = clob.getCharacterStream();
						char[] c = new char[(int) clob.length()];
						inStream.read(c);
						data = new String(c);
					}
					subRow = sheet.createRow((int) k + 1);
					subRow.createCell((short) 0).setCellValue(k + 1);
					subRow.createCell((short) 1).setCellValue((String) question[0]);
					subRow.createCell((short) 2).setCellValue((String) question[1]);
					subRow.createCell((short) 3).setCellValue((String) question[2]);
					subRow.createCell((short) 4).setCellValue((String) question[3]);
					subRow.createCell((short) 5).setCellValue((String) question[4]);
					subRow.createCell((short) 6).setCellValue(data);
					subRow.createCell((short) 7).setCellValue((String) question[6]);
				}
				if (inStream != null) {
					inStream.close();
				}
				autoWidth(sheet, 8);
				Calendar c = Calendar.getInstance();
				String tm = c.get(Calendar.YEAR) + "年" + (c.get(Calendar.MONTH) + 1) + "月" + c.get(Calendar.DAY_OF_MONTH) + "日"
						+ c.get(Calendar.HOUR_OF_DAY) + "时" + c.get(Calendar.MINUTE) + "分" + c.get(Calendar.SECOND) + "秒" + "";
				String excelName = "常见问题(" + tm + ").xls";
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader("Content-Disposition", "attachment;filename=" + toUtf8String(excelName));
				ServletOutputStream out = response.getOutputStream();
				wb.write(out);
				if (out != null) {
					out.flush();
					out.close();
				}
				this.setMsg("导出成功！");
			}
			// this.setTogo(this.getServletPath()+".action");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("导出失败！");
			// this.setTogo("close");
			this.setTogo(this.getServletPath() + ".action");
		}
		return "msg";
	}

	/**
	 * 设置sheet列宽
	 * 
	 * @param sheet
	 * @param maxColNum
	 */
	public void autoWidth(HSSFSheet sheet, int maxColNum) {
		for (int columnNum = 0; columnNum < maxColNum; columnNum++) {
			sheet.autoSizeColumn((short) columnNum);
		}
	}

	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex + "|pebzzcoursezixuanmanager6070");
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public List getTypeList() {
		return typeList;
	}

	public void setTypeList(List typeList) {
		this.typeList = typeList;
	}

	public String getQid() {
		return qid;
	}

	public void setQid(String qid) {
		this.qid = qid;
	}

	public void setQuestion(FrequentlyAskedQuestions question) {
		this.question = question;
	}

	public FrequentlyAskedQuestions getQuestion() {
		return question;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List getZiLiaotype() {
		return ziLiaotype;
	}

	public void setZiLiaotype(List ziLiaotype) {
		this.ziLiaotype = ziLiaotype;
	}

	public String getZiliaoname() {
		return ziliaoname;
	}

	public void setZiliaoname(String ziliaoname) {
		this.ziliaoname = ziliaoname;
	}

	public String getZiliaotypes() {
		return ziliaotypes;
	}

	public void setZiliaotypes(String ziliaotypes) {
		this.ziliaotypes = ziliaotypes;
	}

	public String getFabuunit() {
		return fabuunit;
	}

	public void setFabuunit(String fabuunit) {
		this.fabuunit = fabuunit;
	}

	/**
	 * 在线直播查看详情
	 * 
	 * @return
	 */
	public String sacLiveView() {
		String id = ServletActionContext.getRequest().getParameter("id");
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT NVL(PBTC.CODE,'-') CODE, ");
			sb.append("        NVL(PBTC.NAME,'-') NAME1, ");
			sb.append("        NVL(TO_CHAR(PBTC.SIGNSTARTDATE,'yyyy-MM-dd hh24:mi'),'-') SSDATE, ");
			sb.append("        NVL(TO_CHAR(PBTC.SIGNENDDATE,'yyyy-MM-dd hh24:mi'),'-') SEDATE, ");
			sb.append("        NVL(PBTC.MAXSTUS,'-') MAXSTUS, ");
			sb.append("        NVL(ECSIGNTYPE.NAME,'-') NAME2, ");
			sb.append("        NVL(PBTC.ESTIMATETIME,'-') ETIME, ");
			sb.append("        NVL(PBTC.TIME,'-') TIME, ");
			sb.append("        NVL(PBTC.PRICE,'-') PRICE, ");
			sb.append("        NVL(PBTC.LIVEURL,'-') LIVEURL, ");
			sb.append("        NVL(TO_CHAR(PBTC.ESTIMATESTARTDATE,'yyyy-MM-dd hh24:mi'),'-') ESDATE, ");
			sb.append("        NVL(TO_CHAR(PBTC.ESTIMATEENDDATE,'yyyy-MM-dd hh24:mi'),'-') EEDATE, ");
			sb.append("        NVL(ECCOURSETYPE.NAME,'-') NAME3, ");
			sb.append("        NVL(ECCOURSECATEGORY.NAME,'-') NAME4, ");
			sb.append("        NVL(ECCIT.NAME,'-') NAME5, ");
			sb.append("        NVL(ECCONTENTPROPERTY.NAME,'-') NAME6, ");
			sb.append("        NVL(TO_CHAR(PBTC.ANNOUNCE_DATE,'yyyy-MM-dd hh24:mi:ss'),'-') ANDATE, ");
			sb.append("        NVL(PBTC.SUGGESTION,'-') SUGG, ");
			sb.append("        NVL(TO_CHAR(WM_CONCAT(ECSUGGEST.NAME)),'-') NAME7, ");
			sb.append("        NVL(PBTC.PHOTO_LINK,'-') PHOTOLINK, ");
			sb.append("        NVL(PBTC.LIVEDESC,'-') LIVEDESC, ");
			sb.append("        NVL(PBTC.TEACHER,'-') TEACHER, ");
			sb.append("        NVL(PBTC.TEA_IMG,'-') TEAIMG, ");
			sb.append("        NVL(PBTC.TEACHER_NOTE,'-') TEANOTE, ");
			sb.append("        NVL(PBTC.PASSROLE_NOTE,'-') PASSROLENOTE ");
			sb.append("   FROM PE_BZZ_TCH_COURSE PBTC ");
			sb.append("  INNER JOIN ENUM_CONST ECSIGNTYPE ");
			sb.append("     ON PBTC.FLAG_SIGNTYPE = ECSIGNTYPE.ID ");
			sb.append("  INNER JOIN ENUM_CONST ECCOURSETYPE ");
			sb.append("     ON PBTC.FLAG_COURSETYPE = ECCOURSETYPE.ID ");
			sb.append("  INNER JOIN ENUM_CONST ECCOURSECATEGORY ");
			sb.append("     ON PBTC.FLAG_COURSECATEGORY = ECCOURSECATEGORY.ID ");
			sb.append("  INNER JOIN ENUM_CONST ECCIT ");
			sb.append("     ON PBTC.FLAG_COURSE_ITEM_TYPE = ECCIT.ID ");
			sb.append("  INNER JOIN ENUM_CONST ECCONTENTPROPERTY ");
			sb.append("     ON PBTC.FLAG_CONTENT_PROPERTY = ECCONTENTPROPERTY.ID ");
			sb.append("   LEFT JOIN PE_BZZ_TCH_COURSE_SUGGEST PBTCS ");
			sb.append("     ON PBTC.ID = PBTCS.FK_COURSE_ID ");
			sb.append("  LEFT JOIN ENUM_CONST ECSUGGEST ");
			sb.append("     ON PBTCS.FK_ENUM_CONST_ID = ECSUGGEST.ID ");
			sb.append("     WHERE PBTC.ID = '" + id + "' ");
			sb.append("     GROUP BY PBTC.CODE, ");
			sb.append("        PBTC.NAME, ");
			sb.append("        PBTC.SIGNSTARTDATE, ");
			sb.append("        PBTC.SIGNENDDATE, ");
			sb.append("        PBTC.MAXSTUS, ");
			sb.append("        ECSIGNTYPE.NAME, ");
			sb.append("        PBTC.ESTIMATETIME, ");
			sb.append("        PBTC.TIME, ");
			sb.append("        PBTC.PRICE, ");
			sb.append("        PBTC.LIVEURL, ");
			sb.append("        PBTC.ESTIMATESTARTDATE, ");
			sb.append("        PBTC.ESTIMATEENDDATE, ");
			sb.append("        ECCOURSETYPE.NAME, ");
			sb.append("        ECCOURSECATEGORY.NAME, ");
			sb.append("        ECCIT.NAME, ");
			sb.append("        ECCONTENTPROPERTY.NAME, ");
			sb.append("        PBTC.ANNOUNCE_DATE, ");
			sb.append("        PBTC.SUGGESTION, ");
			sb.append("        PBTC.PHOTO_LINK, ");
			sb.append("        PBTC.LIVEDESC, ");
			sb.append("        PBTC.TEACHER, ");
			sb.append("        PBTC.TEA_IMG, ");
			sb.append("        PBTC.TEACHER_NOTE, ");
			sb.append("        PBTC.PASSROLE_NOTE ");
			List sacLiveList = this.getGeneralService().getBySQL(sb.toString());
			Object sacLiveView = sacLiveList.get(0);
			ServletActionContext.getRequest().setAttribute("sacLiveView", sacLiveView);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "sacLiveView";
	}

	public String liveStudy() {
		String batchId = ServletActionContext.getRequest().getParameter("id");
		UserSession userSession = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		// 是否已经付费
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT PBTC.LIVEURL, PBTC.LIVESTUPWD, PBTC.LIVEID ");
		sb.append("   FROM PR_BZZ_TCH_STU_ELECTIVE PBTSE, ");
		sb.append("        PR_BZZ_TCH_OPENCOURSE   PBTO, ");
		sb.append("        PE_BZZ_TCH_COURSE       PBTC, ");
		sb.append("        PE_BZZ_STUDENT          PBS ");
		sb.append("  WHERE PBTSE.FK_TCH_OPENCOURSE_ID = PBTO.ID ");
		sb.append("    AND PBTO.FK_BATCH_ID = '" + batchId + "' ");
		sb.append("    AND PBTO.FK_COURSE_ID = PBTC.ID ");
		sb.append("    AND PBTSE.FK_STU_ID = PBS.ID ");
		sb.append("    AND PBTC.FLAG_COURSEAREA = 'Coursearea0' ");
		sb.append("    AND PBS.REG_NO = '" + userSession.getLoginId() + "' ");
		List liveList = null;
		try {
			liveList = this.getGeneralService().getBySQL(sb.toString());
			if (null == liveList || liveList.size() == 0) {
				this.setMsg("直播未付费，请付费后重试");
				return "msg";
			} else {// 拼接直播链接
				Object[] liveArr = (Object[]) liveList.get(0);
				Object liveUrl = liveArr[0];
				Object livePwd = liveArr[1];
				Object liveId = liveArr[2];
				// 查询学员序列值
				String sql = "SELECT ID FROM LIVE_SEQ_STU WHERE LIVE_ID = '" + liveId + "' AND LOGIN_ID = '"
						+ userSession.getSsoUser().getId() + "'";
				List seqList = this.getGeneralService().getBySQL(sql);
				if (null == seqList || seqList.size() == 0) {
					String insert = "INSERT INTO LIVE_SEQ_STU (ID, LOGIN_ID, LIVE_ID) VALUES (SEQ_LIVE_SEQ_STU.NEXTVAL,'"
							+ userSession.getSsoUser().getId() + "','" + liveId + "')";
					int intRes = this.getGeneralService().executeBySQL(insert);
					seqList = null;
					seqList = this.getGeneralService().getBySQL(sql);
					if (intRes <= 0 || (null == seqList || seqList.size() == 0)) {
						this.setMsg("直播连接失败，请重试！");
						return "msg";
					}
				}
				Long timeLong = (new Date()).getTime();
				Object lssId = seqList.get(0);
				Object md5K = this.encodePassword(lssId + timeLong.toString());
				String uptSql = "UPDATE LIVE_SEQ_STU SET K_PARAM = '" + md5K.toString() + "' WHERE LIVE_ID = '" + liveId
						+ "' AND LOGIN_ID = '" + userSession.getSsoUser().getId() + "'";
				int upt = this.getGeneralService().executeBySQL(uptSql);
				if (upt == 1) {
					// 直播用户seq格式
					Object seq = seqList.get(0);
					String toLive = liveUrl + "?nickName=" + userSession.getUserName() + "-" + userSession.getLoginId() + "&token="
							+ livePwd + "&sd=0&uid=" + seq + "&k=" + md5K.toString();
					ServletActionContext.getRequest().setAttribute("liveStudy", toLive);
					return "liveStudy";
				} else {
					System.out.println("直播连接失败请联系管理员" + uptSql);
					this.setMsg("直播连接失败，请联系管理员！");
					this.setTogo("javascript:window.close();");
					return "msg";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("直播连接失败，请联系管理员！");
			this.setTogo("javascript:window.close();");
			return "msg";
		}
	}

	/**
	 * 在线直播支付
	 * 
	 * @return
	 */
	public String paymentLive() {
		String batchId = ServletActionContext.getRequest().getParameter("batchId");
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		// 直播专项实体
		DetachedCriteria dcBatch = DetachedCriteria.forClass(PeBzzBatch.class);
		dcBatch.add(Restrictions.eq("id", batchId));
		PeBzzBatch peBzzBatch;
		try {
			peBzzBatch = (PeBzzBatch) this.getGeneralService().getList(dcBatch).get(0);
			// 不在报名时间内
			if (!isRightDate(peBzzBatch.getStartDate(), peBzzBatch.getEndDate())) {
				this.setMsg("当前时间不在支付时间段内");
				this.setTogo("javascript:window.close();");
				return "msg";
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		// 检查学员是否在专项报名表中 不在的话添加
		String sql = "SELECT 1 FROM STU_BATCH WHERE STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '"
				+ us.getSsoUser().getId() + "') AND BATCH_ID = '" + batchId + "'";
		List list;
		try {
			list = this.getGeneralService().getBySQL(sql);
			int res, resback = 0;
			if (null == list || list.size() == 0) {
				// 检查是否达到报名上限
				String maxSql = "SELECT 1 FROM PE_BZZ_TCH_COURSE PBTC INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID AND PBTO.FK_BATCH_ID = '"
						+ batchId
						+ "' INNER JOIN PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSE ON PBTO.ID = PBTSE.FK_TCH_OPENCOURSE_ID AND PBTSE.FK_ORDER_ID IS NOT NULL GROUP BY PBTC.MAXSTUS, PBTC.ID HAVING PBTC.MAXSTUS - NVL(COUNT(PBTSE.ID), 0) < 1";
				List maxList = this.getGeneralService().getBySQL(maxSql);
				if (null != maxList && maxList.size() > 0) {
					this.setMsg("报名人数已达上限，谢谢关注！");
					this.setTogo("javascript:window.close();");
					return "msg";
				}
				// 添加到专项报名表：40288a7b39c3ac650139c3f216870005未支付
				String insertString = "INSERT INTO STU_BATCH (ID, STU_ID, BATCH_ID, FLAG_BATCHPAYSTATE) SELECT SYS_GUID(),ID,'" + batchId
						+ "','40288a7b39c3ac650139c3f216870005' FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId()
						+ "'";
				res = this.getGeneralService().executeBySQL(insertString);
				// 添加到报名表
				if (res > 0) {
					insertString = "INSERT INTO PR_BZZ_TCH_STU_ELECTIVE_BACK (ID, FK_OPERATOR_ID, FK_TCH_OPENCOURSE_ID, FK_STU_ID) SELECT SYS_GUID(),FK_SSO_USER_ID,(SELECT ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_BATCH_ID = '"
							+ batchId + "'),ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'";
					resback = this.getGeneralService().executeBySQL(insertString);
				}
			}
			// 跳转到支付方法
			if (resback > 0 || (null != list && list.size() > 0)) {
				ServletActionContext.getRequest().setAttribute("orderTypeString", "7");// 订单类型：视频直播订单
				return this.paymentSpecialTraining();
			}
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("直播支付失败，请联系管理员");
			this.setTogo("javascript:window.close();");
			return "msg";
		}
		this.setMsg("直播支付失败，请联系管理员");
		this.setTogo("javascript:window.close();");
		return "msg";
	}

	/**
	 * 免费直播报名
	 * 
	 * @return
	 */
	public String paymentFreeLive() {
		String batchId = ServletActionContext.getRequest().getParameter("batchId");
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		// 直播专项实体
		DetachedCriteria dcBatch = DetachedCriteria.forClass(PeBzzBatch.class);
		dcBatch.add(Restrictions.eq("id", batchId));
		PeBzzBatch peBzzBatch;
		try {
			peBzzBatch = (PeBzzBatch) this.getGeneralService().getList(dcBatch).get(0);
			// 不在报名时间内
			if (!isRightDate(peBzzBatch.getStartDate(), peBzzBatch.getEndDate())) {
				this.setMsg("当前时间不在报名时间段内");
				this.setTogo("javascript:window.close();");
				return "msg";
			}
		} catch (EntityException e1) {
			e1.printStackTrace();
		}
		String sqlStubatch = "";
		String sqlPbtseb = "";
		// 检查学员是否在专项报名表中 不在的话添加
		String sql = "SELECT 1 FROM STU_BATCH WHERE STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '"
				+ us.getSsoUser().getId() + "') AND BATCH_ID = '" + batchId + "'";
		List list;
		try {
			list = this.getGeneralService().getBySQL(sql);
			int res, resback = 0;
			if (null == list || list.size() == 0) {// STU_BATCH中无该学员
				// 检查是否达到报名上限
				String maxSql = "SELECT 1 FROM PE_BZZ_TCH_COURSE PBTC INNER JOIN PR_BZZ_TCH_OPENCOURSE PBTO ON PBTC.ID = PBTO.FK_COURSE_ID AND PBTO.FK_BATCH_ID = '"
						+ batchId
						+ "' AND TO_NUMBER(PBTC.MAXSTUS) - (SELECT COUNT(STU_ID) FROM STU_BATCH SB WHERE SB.BATCH_ID = '"
						+ batchId + "') < 1";
				List maxList = this.getGeneralService().getBySQL(maxSql);
				if (null != maxList && maxList.size() > 0) {
					this.setMsg("报名人数已达上限，谢谢关注！");
					this.setTogo("javascript:window.close();");
					return "msg";
				}
				sqlStubatch = "INSERT INTO STU_BATCH (ID, STU_ID, BATCH_ID, FLAG_BATCHPAYSTATE) SELECT SYS_GUID(),ID,'" + batchId
						+ "',null FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'";
				sqlPbtseb = "INSERT INTO PR_BZZ_TCH_STU_ELECTIVE_BACK (ID, FK_OPERATOR_ID, FK_TCH_OPENCOURSE_ID, FK_STU_ID) SELECT SYS_GUID(),FK_SSO_USER_ID,(SELECT ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_BATCH_ID = '"
						+ batchId + "'),ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'";
				// 添加报名数据：插入STU_BATCH和报名表
				List<String> batchSql = new ArrayList<String>();
				batchSql.add(sqlStubatch);
				batchSql.add(sqlPbtseb);
				try {
					this.getGeneralService().executeBySQL(sqlStubatch);
					this.getGeneralService().executeBySQL(sqlPbtseb);
					// this.getGeneralService().executeBatchSql(batchSql, null);
				} catch (Exception e) {
					e.printStackTrace();
					this.setMsg("直播报名失败，请联系管理员");
					this.setTogo("javascript:window.close();");
					return "msg";
				}
				// 1.通过拿到开课的实体对象的PrBzzTchOpencourse
				DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
				dc.createCriteria("peBzzBatch", "peBzzBatch");
				dc.add(Restrictions.eq("peBzzBatch.id", batchId));
				List<PrBzzTchOpencourse> list1;
				try {
					list1 = this.getGeneralService().getList(dc);
					PrBzzTchOpencourse bzzTchOpencourse = null;
					if (list1.size() > 0) {
						bzzTchOpencourse = list1.get(0);
					}
					// 2.拿到当前登陆人对应的学员实体对象
					DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);
					stuDc.createCriteria("ssoUser", "ssoUser");
					stuDc.add(Restrictions.eq("ssoUser", us.getSsoUser()));

					PeBzzStudent stu = (PeBzzStudent) this.getGeneralService().getList(stuDc).get(0);
					stuDc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
					try {
						this.peBzzStudent = (PeBzzStudent) this.getGeneralService().getList(stuDc).get(0);
					} catch (EntityException e) {
						e.printStackTrace();
					}

					DetachedCriteria eleDc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
					eleDc.add(Restrictions.eq("prBzzTchOpencourse", bzzTchOpencourse));
					eleDc.add(Restrictions.eq("peBzzStudent", this.peBzzStudent));
					List eleList = this.getGeneralService().getList(eleDc);
					if (eleList.size() < 1) {// 当选课表无记录时插入选课表和training表
						// 3.插入training表
						List saveList = new ArrayList();
						TrainingCourseStudent trainingCourseStudent = new TrainingCourseStudent();
						trainingCourseStudent.setPrBzzTchOpencourse(bzzTchOpencourse);
						trainingCourseStudent.setSsoUser(peBzzStudent.getSsoUser());
						trainingCourseStudent.setPercent(0.00);
						trainingCourseStudent.setLearnStatus(StudyProgress.UNCOMPLETE);
						saveList.add(trainingCourseStudent);
						// 4.插入选课表
						PrBzzTchStuElective ele = new PrBzzTchStuElective();
						ele.setPrBzzTchOpencourse(bzzTchOpencourse);
						ele.setElectiveDate(new Date());
						ele.setPeBzzStudent(stu);
						ele.setSsoUser(us.getSsoUser());
						ele.setTrainingCourseStudent(trainingCourseStudent);
						saveList.add(ele);
						this.getGeneralService().saveList(saveList);
					} else {
						this.setMsg("您已经报名，请返回刷新直播列表");
						this.setTogo("javascript:window.close();");
						return "msg";
					}
				} catch (EntityException e2) {
					e2.printStackTrace();
					this.setMsg("直播报名失败，请联系管理员");
					this.setTogo("javascript:window.close();");
					return "msg";
				}
			} else {// STU_BATCH中有该学员
				// 检查pbtseb中是否有该学员
				String pbtsebSql = "SELECT 1 FROM PR_BZZ_TCH_STU_ELECTIVE_BACK PBTSEB WHERE PBTSEB.FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '"
						+ us.getSsoUser().getId()
						+ "') AND PBTSEB.FK_TCH_OPENCOURSE_ID IN (SELECT ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_BATCH_ID = '"
						+ batchId
						+ "')";
				List pbtsebList = null;
				pbtsebList = this.getGeneralService().getBySQL(pbtsebSql);
				// 没有则添加
				if (pbtsebList == null || pbtsebList.size() == 0) {
					String pbtsebAddSql = "INSERT INTO PR_BZZ_TCH_STU_ELECTIVE_BACK (ID, FK_OPERATOR_ID, FK_TCH_OPENCOURSE_ID, FK_STU_ID) SELECT SYS_GUID(),FK_SSO_USER_ID,(SELECT ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_BATCH_ID = '"
							+ batchId + "'),ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '" + us.getSsoUser().getId() + "'";
					int add = this.getGeneralService().executeBySQL(pbtsebAddSql);
					if (add <= 0) {
						this.setMsg("直播报名选课失败，请联系管理员。");
						this.setTogo("javascript:window.close();");
						return "msg";
					}
				}
				// 检查pbtse中是否有该学员
				String pbtseSql = "SELECT 1 FROM PR_BZZ_TCH_STU_ELECTIVE PBTSEB WHERE PBTSEB.FK_STU_ID = (SELECT ID FROM PE_BZZ_STUDENT WHERE FK_SSO_USER_ID = '"
						+ us.getSsoUser().getId()
						+ "') AND PBTSEB.FK_TCH_OPENCOURSE_ID IN (SELECT ID FROM PR_BZZ_TCH_OPENCOURSE WHERE FK_BATCH_ID = '"
						+ batchId
						+ "')";
				List pbtseList = null;
				pbtseList = this.getGeneralService().getBySQL(pbtseSql);
				// 没有则添加，有泽提示
				if (pbtseList == null || pbtseList.size() == 0) {
					// 1.通过拿到开课的实体对象的PrBzzTchOpencourse
					DetachedCriteria dc = DetachedCriteria.forClass(PrBzzTchOpencourse.class);
					dc.createCriteria("peBzzBatch", "peBzzBatch");
					dc.add(Restrictions.eq("peBzzBatch.id", batchId));
					List<PrBzzTchOpencourse> list1;
					try {
						list1 = this.getGeneralService().getList(dc);
						PrBzzTchOpencourse bzzTchOpencourse = null;
						if (list1.size() > 0) {
							bzzTchOpencourse = list1.get(0);
						}
						// 2.拿到当前登陆人对应的学员实体对象
						DetachedCriteria stuDc = DetachedCriteria.forClass(PeBzzStudent.class);
						stuDc.createCriteria("ssoUser", "ssoUser");
						stuDc.add(Restrictions.eq("ssoUser", us.getSsoUser()));

						PeBzzStudent stu = (PeBzzStudent) this.getGeneralService().getList(stuDc).get(0);
						stuDc.add(Restrictions.eq("ssoUser", us.getSsoUser()));
						try {
							this.peBzzStudent = (PeBzzStudent) this.getGeneralService().getList(stuDc).get(0);
						} catch (EntityException e) {
							e.printStackTrace();
						}

						DetachedCriteria eleDc = DetachedCriteria.forClass(PrBzzTchStuElective.class);
						eleDc.add(Restrictions.eq("prBzzTchOpencourse", bzzTchOpencourse));
						eleDc.add(Restrictions.eq("peBzzStudent", this.peBzzStudent));
						List eleList = this.getGeneralService().getList(eleDc);
						if (eleList.size() < 1) {// 当选课表无记录时插入选课表和training表
							// 3.插入training表
							List saveList = new ArrayList();
							TrainingCourseStudent trainingCourseStudent = new TrainingCourseStudent();
							trainingCourseStudent.setPrBzzTchOpencourse(bzzTchOpencourse);
							trainingCourseStudent.setSsoUser(peBzzStudent.getSsoUser());
							trainingCourseStudent.setPercent(0.00);
							trainingCourseStudent.setLearnStatus(StudyProgress.UNCOMPLETE);
							saveList.add(trainingCourseStudent);
							// 4.插入选课表
							PrBzzTchStuElective ele = new PrBzzTchStuElective();
							ele.setPrBzzTchOpencourse(bzzTchOpencourse);
							ele.setElectiveDate(new Date());
							ele.setPeBzzStudent(stu);
							ele.setSsoUser(us.getSsoUser());
							ele.setTrainingCourseStudent(trainingCourseStudent);
							saveList.add(ele);
							this.getGeneralService().saveList(saveList);
						} else {
							this.setMsg("您已经报名，请返回刷新直播列表");
							this.setTogo("javascript:window.close();");
							return "msg";
						}
					} catch (EntityException e2) {
						e2.printStackTrace();
						this.setMsg("直播报名失败，请联系管理员");
						this.setTogo("javascript:window.close();");
						return "msg";
					}
				}
			}
			this.setMsg("恭喜您已经报名成功，请在规定时间内参加直播课程培训。");
			this.setTogo("javascript:window.close();");
			return "msg";
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("直播报名失败，请联系管理员");
			this.setTogo("javascript:window.close();");
			return "msg";
		}
	}

	public String getKname() {
		return kname;
	}

	public void setKname(String kname) {
		this.kname = kname;
	}

	public String getKbianhao() {
		return kbianhao;
	}

	public void setKbianhao(String kbianhao) {
		this.kbianhao = kbianhao;
	}

	public String getPerid() {
		return perid;
	}

	public void setPerid(String perid) {
		this.perid = perid;
	}

	public String encodePassword(String rawPass) {
		String saltedPass = mergePasswordAndSalt(rawPass, "PONY", false);
		MessageDigest messageDigest = getMessageDigest();
		byte[] digest;
		try {
			digest = messageDigest.digest(saltedPass.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 not supported!");
		}
		return new String(Hex.encodeHex(digest));
	}

	private String mergePasswordAndSalt(String password, Object salt, boolean strict) {
		if (password == null) {
			password = "";
		}
		if (strict && (salt != null)) {
			if ((salt.toString().lastIndexOf("{") != -1) || (salt.toString().lastIndexOf("}") != -1)) {
				throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
			}
		}
		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}

	private MessageDigest getMessageDigest() {
		String algorithm = "MD5";
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
		}
	}

	public List getFilelink() {
		return filelink;
	}

	public void setFilelink(List filelink) {
		this.filelink = filelink;
	}

	public List getResourceList() {
		return resourceList;
	}

	public void setResourceList(List resourceList) {
		this.resourceList = resourceList;
	}

	public List getResourceListc() {
		return resourceListc;
	}

	public void setResourceListc(List resourceListc) {
		this.resourceListc = resourceListc;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public List getIssueDetail() {
		return issueDetail;
	}

	public void setIssueDetail(List issueDetail) {
		this.issueDetail = issueDetail;
	}

	public List getReplyList() {
		return replyList;
	}

	public void setReplyList(List replyList) {
		this.replyList = replyList;
	}

	public List getIssueType() {
		return issueType;
	}

	public void setIssueType(List issueType) {
		this.issueType = issueType;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public List getSeriesList() {
		return seriesList;
	}

	public void setSeriesList(List seriesList) {
		this.seriesList = seriesList;
	}

	public String getSeriseCourse() {
		return seriseCourse;
	}

	public void setSeriseCourse(String seriseCourse) {
		this.seriseCourse = seriseCourse;
	}

	public String viewlive() {
		String sql = "SELECT PBTC.CODE, SCL.SCORM_TYPE, SCL.INDEXFILE " + "  FROM PE_BZZ_TCH_COURSE PBTC "
				+ "  JOIN SCORM_SCO_LAUNCH SCL ON PBTC.CODE = SCL.COURSE_ID WHERE PBTC.CODE='" + course_id + "'";
		try {
			List list = this.getGeneralService().getBySQL(sql);
			if (list != null && list.size() > 0) {
				Object[] obj = (Object[]) list.get(0);
				Object courseCode = (String) obj[0];
				Object scormType = (String) obj[1];
				Object indexFile = (String) obj[2];
				Object myDate = new Date().getTime();
				ServletActionContext.getRequest().setAttribute("cid", course_id);
				ServletActionContext.getRequest().setAttribute("courseCode", courseCode);
				ServletActionContext.getRequest().setAttribute("scormType", scormType);
				ServletActionContext.getRequest().setAttribute("indexFile", indexFile);
				ServletActionContext.getRequest().setAttribute("myDate", myDate);
			} else {
				this.setMsg("课程不能预览!");
				return "viewFailed";
			}
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "viewlive";
	}
	
	
	
	/**
	 * 删除发票
	 * lwq
	 * 2016-06-02
	 * @return
	 */
	public String toCancleMergeOrder() {
		String err_msg = "";
		Map result = null;
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		String[] orderIds = orders.split(",");
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.add(Restrictions.in("id", orderIds));
		dc.createAlias("peBzzInvoiceInfos", "peBzzInvoiceInfos", DetachedCriteria.LEFT_JOIN)
			.createAlias("peBzzInvoiceInfos.enumConstByInvoiceType", "enumConstByInvoiceType", DetachedCriteria.LEFT_JOIN)
			.createAlias("peBzzInvoiceInfos.enumConstByFlagFpOpenState", "enumConstByFlagFpOpenState", DetachedCriteria.LEFT_JOIN);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		PeBzzOrderInfo orderInfo = null;
		String seq = "";
		Double x_amount = 0.00;
		try {
			List<PeBzzOrderInfo> orderList = this.getGeneralService().getDetachList(dc);
			List<PeBzzOrderInfo> order_list = null;
				if(orderList != null && orderList.size() > 0) {
				if(orderList.get(0).getMergeSeq() != null && !"".equals(orderList.get(0).getMergeSeq())) {
					seq = orderList.get(0).getMergeSeq();
					String hql = "from PeBzzOrderInfo where mergeSeq = '" + seq + "'";
					order_list = this.getGeneralService().getByHQL(hql);
				} else {
					seq = orderList.get(0).getSeq();
					order_list = orderList;
				}
				for(PeBzzOrderInfo order : order_list) {
					if(order.getPeBzzInvoiceInfos().size() == 0) {
						this.setMsg(order.getSeq() + "订单未申请发票，请确认后再操作");
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
					if("已开".equals(getOrderInvoiceState(order.getPeBzzInvoiceInfos()))) {
						this.setMsg(order.getSeq() + "订单发票已开，不能删除");
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
					if(!"待开".equals(getOrderInvoiceState(order.getPeBzzInvoiceInfos()))) {
						this.setMsg(order.getSeq() + "订单发票非待开状态，不能删除");
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
					if(((PeBzzInvoiceInfo)order.getPeBzzInvoiceInfos().iterator().next()).getEnumConstByInvoiceType() != null 
								&& "3".equals(((PeBzzInvoiceInfo)order.getPeBzzInvoiceInfos().iterator().next()).getEnumConstByInvoiceType().getCode())) {
						this.setMsg(order.getSeq() + "订单为电子发票申请，不能删除");
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
					x_amount += Double.parseDouble(order.getAmount());
				}
				
				BigDecimal b = new BigDecimal(x_amount);
				x_amount = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				Map param = new HashMap();
				param.put("business", XMLFileUtil.DELETE_BUSSINESS);
				param.put("amount", x_amount.toString());
				param.put("seq", seq);
				param.put("fplx", "0");
				param.put("invoice", orderList.get(0).getPeBzzInvoiceInfos().iterator().next());
				String xml = XMLFileUtil.getXMLFile(param);
				try{
					WebServiceInvoker wst = new WebServiceInvoker();
				 	result = XMLFileUtil.getResult(wst.client(xml));
				}catch(Exception e){
					err_msg ="申请失败，请稍候再试";
				}finally{
					this.setMsg(err_msg);
					if(!"".equals(err_msg)){
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
				}
				if("0000".equals(result.get("returnCode"))) {
					this.setMsg("删除发票成功!");
				} else {
//					try {
//						this.setMsg(new String(Base64Util.getFromBASE64((String)result.get("returnMsg")).getBytes(), "UTF-8"));
						this.setMsg("删除失败");
//					} catch (UnsupportedEncodingException e) {
//						e.printStackTrace();
//					}
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				for(PeBzzOrderInfo order : order_list) {
					String dele_sql = "DELETE FROM PE_BZZ_INVOICE_INFO WHERE FK_ORDER_ID = '" + order.getId() + "'";
					this.getGeneralService().executeBySQL(dele_sql);
				}
				if(orderList != null && orderList.size() > 0 && orderList.get(0).getMergeSeq() != null && !"".equals(orderList.get(0).getMergeSeq())) {
					String sql = "DELETE FROM PE_BZZ_ORDER_MERGE WHERE SEQ = '" + seq + "'";
					this.getGeneralService().executeBySQL(sql);
					String update_sql = "UPDATE PE_BZZ_ORDER_INFO SET MERGE_SEQ = '' WHERE MERGE_SEQ = '" + seq + "'";
					this.getGeneralService().executeBySQL(update_sql);
				}
			}
		} catch (EntityException e) {
			e.printStackTrace();
			this.setMsg("操作失败");
			this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
			return "stu_msg";
		}
		this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
		return "stu_msg";
	}
	
	/*
	public String toCreditNote() {
		getInvoice(this.id);

		/*DetachedCriteria dc = DetachedCriteria.forClass(PeBzzInvoiceInfo.class);
		dc.createCriteria("peBzzOrderInfo", "peBzzOrderInfo");
		dc.add(Restrictions.eq("peBzzOrderInfo.id", this.id));
		List list = null;
		try {
			list = this.getGeneralService().getDetachList(dc);
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.peBzzInvoiceInfo = (PeBzzInvoiceInfo)list.get(0);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("amount", this.getLastInvoiceInfo().getPeBzzOrderInfo().getAmount());
		return "toCreditNote";
	}
	 */
	/**
	 * 发票冲红
	 * lwq
	 * 2016-06-03
	 * @return
	 */
	/*
	public String creditNote() {
		UserSession us = (UserSession) ActionContext.getContext().getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		//根据订单ID取得订单
		DetachedCriteria dc = DetachedCriteria.forClass(PeBzzOrderInfo.class);
		dc.createAlias("enumConstByFlagRefundState", "enumConstByFlagRefundState", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFlagPaymentMethod", "enumConstByFlagPaymentMethod", DetachedCriteria.LEFT_JOIN);
		dc.createAlias("enumConstByFlagPaymentState", "enumConstByFlagPaymentState", DetachedCriteria.LEFT_JOIN);
		dc.createCriteria("peBzzInvoiceInfos", "peBzzInvoiceInfos", JoinType.LEFT_OUTER_JOIN)
			.createCriteria("peBzzInvoiceInfos.enumConstByInvoiceType", "enumConstByInvoiceType", JoinType.LEFT_OUTER_JOIN)
			.createCriteria("peBzzInvoiceInfos.enumConstByFlagFpOpenState", "enumConstByFlagFpOpenState", JoinType.LEFT_OUTER_JOIN);
		dc.add(Restrictions.eq("id", id));
		PeBzzOrderInfo order = null;
		String seq = "";
		Double x_amount = 0.00;
		try {
			this.getGeneralService().getOrderSeq();
			List<PeBzzOrderInfo> orderList = this.getGeneralService().getDetachList(dc);
			if(orderList != null && orderList.size() > 0) {
				order = orderList.get(0);
				List<PeBzzOrderInfo> orders = null;
				if(order.getMergeSeq() != null && !"".equals(order.getMergeSeq())) {
					//根据合并订单号取得合并订单中所有订单
					DetachedCriteria dc1 = DetachedCriteria.forClass(PeBzzOrderInfo.class);
					dc1.createCriteria("enumConstByFlagRefundState", "enumConstByFlagRefundState", JoinType.LEFT_OUTER_JOIN);
					dc1.createCriteria("enumConstByFlagPaymentMethod", "enumConstByFlagPaymentMethod", JoinType.LEFT_OUTER_JOIN);
					dc1.createCriteria("enumConstByFlagPaymentState", "enumConstByFlagPaymentState", JoinType.LEFT_OUTER_JOIN);
					dc1.createCriteria("peBzzInvoiceInfos", "peBzzInvoiceInfos", JoinType.LEFT_OUTER_JOIN)
						.createCriteria("peBzzInvoiceInfos.enumConstByInvoiceType", "enumConstByInvoiceType", JoinType.LEFT_OUTER_JOIN)
						.createCriteria("peBzzInvoiceInfos.enumConstByFlagFpOpenState", "enumConstByFlagFpOpenState", JoinType.LEFT_OUTER_JOIN);
					dc1.add(Restrictions.eq("mergeSeq", order.getMergeSeq()));
					orders = this.getGeneralService().getDetachList(dc1);
					seq = order.getMergeSeq();
				} else {
					orders = orderList;
					seq = order.getSeq();
				}
				for(PeBzzOrderInfo order1 : orders) {
					if(order1.getPeBzzInvoiceInfos().size() == 0) {
						this.setMsg(order1.getSeq() + "订单未申请发票");
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
					if("4".equals(((PeBzzInvoiceInfo)order1.getPeBzzInvoiceInfos().iterator().next()).getEnumConstByInvoiceType().getCode())) {
						this.setMsg(order1.getSeq() + "订单为纸质发票，不能做冲红操作");
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
					if(!"已开".equals(getOrderInvoiceState(order1.getPeBzzInvoiceInfos()))) {
						this.setMsg(order1.getSeq() + "订单的发票尚未开具，不能冲红");
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
					String check_sql = "SELECT id FROM PE_BZZ_INVOICE_RESET WHERE FK_INVOICE_ID = '" + ((PeBzzInvoiceInfo)order1.getPeBzzInvoiceInfos().iterator().next()).getId() + "'";
					List l = this.getGeneralService().getBySQL(check_sql);
					if(l != null && l.size() > 0) {
						this.setMsg(order1.getSeq() + "订单已冲红,请不要重复操作");
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
					x_amount += Double.parseDouble(order.getAmount());
				}
				
				BigDecimal b = new BigDecimal(x_amount);
				x_amount = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				Map param = new HashMap();
				param.put("business", XMLFileUtil.SAVE_BUSSINESS);
				param.put("fplx", "1");
				param.put("amount", x_amount.toString());
				param.put("seq", seq);
				param.put("invoice", (PeBzzInvoiceInfo)orderList.get(0).getPeBzzInvoiceInfos().iterator().next());
				String xml = XMLFileUtil.getXMLFile(param);
				WebServiceInvoker wst = new WebServiceInvoker();
				Map result = XMLFileUtil.getResult(wst.client(xml));
				if("0000".equals(result.get("returnCode"))) {
					List returnInfo = (List)result.get("returnInfo");
					if(returnInfo != null && returnInfo.size() > 0) {
						Map<String, String> map = (Map)returnInfo.get(0);
						if("0000".equals(map.get("returnCode"))) {
							this.setMsg("冲红成功!");
						} else {
							this.setMsg((String)map.get("returnMsg"));
							this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
							return "stu_msg";
						}
					} else {
						this.setMsg("冲红失败!");
						this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
						return "stu_msg";
					}
				} else {
					try {
						this.setMsg(new String(Base64Util.getFromBASE64(((String)result.get("returnMsg"))).getBytes(), "UTF-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
					return "stu_msg";
				}
				for(PeBzzOrderInfo order1 : orders) {
					String sql = "UPDATE PE_BZZ_INVOICE_INFO SET ";
					// 邮寄方式
					if("4".equals(invoiceType)) {
						String postType = ServletActionContext.getRequest().getParameter("postType");
						EnumConst enumConstByFlagPostType = this.getEnumConstService().getByNamespaceCode("FlagPostType", postType);
						sql += " FLAG_POST_TYPE = '" + enumConstByFlagPostType.getId() + "', ";
					}
					EnumConst enumConstByInvoiceType = this.getEnumConstService().getByNamespaceCode("InvoiceType", invoiceType);
					EnumConst enumConstByFlagOpenState = this.getEnumConstService().getByNamespaceCode("", "");//冲红修改发票状态
					sql += " TITLE = '" + peBzzInvoiceInfo.getTitle() + "', "
						 + " ADDRESS = '" + peBzzInvoiceInfo.getAddress() + "', "
						 + " PROVINCE = '" + peBzzInvoiceInfo.getProvince() + "', "
						 + " CITY = '" + peBzzInvoiceInfo.getCity() + "', "
						 + " ZIP_CODE = '" + peBzzInvoiceInfo.getZipCode() + "', "
						 + " ADDRESSEE = '" + peBzzInvoiceInfo.getAddressee() + "', "
						 + " PHONE = '" + peBzzInvoiceInfo.getPhone() + "', "
						 + " INVOICE_TYPE = '" + enumConstByInvoiceType.getId() + "', "
						 + " CREATE_DATE = SYSDATE WHERE FK_ORDER_ID = '" + order1.getId() + "'";
					try {
						this.getGeneralService().executeBySQL(sql);
					} catch (EntityException e) {
						e.printStackTrace();
					}
					UUID uuid = UUID.randomUUID();
					String sql1 = "INSERT INTO PE_BZZ_INVOICE_RESET(ID, FK_INVOICE_ID, OPERATOR, CREATE_DATE, STATUS, REMARK) " +
							" VALUES('" + uuid + "','" + ((PeBzzInvoiceInfo)order1.getPeBzzInvoiceInfos().iterator().next()).getId() + "','" + us.getId() + "',sysdate,'01','" + remark + "')";
					this.getGeneralService().executeBySQL(sql1);
				}
			}
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		this.setMsg("发票冲红操作成功！");
		this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
		return "stu_msg";
	}
	*/
	/**
	 * 学生查看发票详情
	 * 
	 * @param endDate
	 * @author linjie
	 */
	public String viewInvoiceDetail() {
		try {
			UserSession us = (UserSession) ServletActionContext.getRequest().getSession().getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
			HttpServletRequest request = ServletActionContext.getRequest();
			String id = request.getParameter("id");
			String hql = "from PeBzzOrderInfo p where p.id='" + id + "'";
			PeBzzOrderInfo peBzzOrderInfo = (PeBzzOrderInfo) this.getGeneralService().getByHQL(hql).get(0);
			if(peBzzOrderInfo.getPeBzzInvoiceInfos() != null && peBzzOrderInfo.getPeBzzInvoiceInfos().size() > 0) {
				peBzzInvoiceInfo = (PeBzzInvoiceInfo) peBzzOrderInfo.getPeBzzInvoiceInfos().iterator().next();
			} else {
				this.setMsg("发票信息获取失败");
				this.setTogo("/entity/workspaceStudent/studentWorkspace_tohistoryrecord.action");
				return "stu_msg";
			}
			
		} catch (EntityException e) {
			e.printStackTrace();
		}
		return "viewInvoiceInfo";
	}
	
	public SsoUser getSsoUser() {
		return ssoUser;
	}

	public void setSsoUser(SsoUser ssoUser) {
		this.ssoUser = ssoUser;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public void voidMethod() {
		System.out.println(new Date());
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List getRecommendCourseList() {
		return recommendCourseList;
	}

	public void setRecommendCourseList(List recommendCourseList) {
		this.recommendCourseList = recommendCourseList;
	}

	public List getSeriesCourseList() {
		return seriesCourseList;
	}

	public void setSeriesCourseList(List seriesCourseList) {
		this.seriesCourseList = seriesCourseList;
	}

	public List getPublishCourseList() {
		return publishCourseList;
	}

	public void setPublishCourseList(List publishCourseList) {
		this.publishCourseList = publishCourseList;
	}

	public List getInformationList() {
		return informationList;
	}

	public void setInformationList(List informationList) {
		this.informationList = informationList;
	}

	public List getNoticeList() {
		return noticeList;
	}

	public void setNoticeList(List noticeList) {
		this.noticeList = noticeList;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getParamter() {
		return paramter;
	}

	public void setParamter(String paramter) {
		this.paramter = paramter;
	}

}

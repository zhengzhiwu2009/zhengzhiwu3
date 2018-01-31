package com.whaty.platform.entity.service.studentStatas;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.whaty.platform.entity.bean.EnumConst;
import com.whaty.platform.entity.bean.FrequentlyAskedQuestions;
import com.whaty.platform.entity.bean.PeBzzInvoiceInfo;
import com.whaty.platform.entity.bean.PeBzzOrderInfo;
import com.whaty.platform.entity.bean.PeBzzStudent;
import com.whaty.platform.entity.bean.PrBzzTchStuElective;
import com.whaty.platform.entity.bean.StudentBatch;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.util.Page;

public interface StudentWorkspaceService {

	/**
	 * 用于自主选课支付
	 * 
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 * @param eleList
	 * @return
	 * @author cailei
	 */
	public void saveOrderInvoiceEletive(PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo, List<PrBzzTchStuElective> eleList)
			throws EntityException;

	/**
	 * 用于专项培训支付
	 * 
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 * @param eleList
	 * @param stuBatchList
	 * @return
	 * @author cailei
	 */
	public void saveOrderInvoiceEletive(PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo,
			List<PrBzzTchStuElective> eleList, List<StudentBatch> stuBatchList, String flag) throws EntityException;

	/**
	 * 删除选课
	 * 
	 * @param prBzzTchStuElective
	 * @return
	 * @author cailei
	 */
	public void deleteElective(PrBzzTchStuElective prBzzTchStuElective) throws EntityException;

	/**
	 * 此方法为后来添加，学员第三方支付时，首先产生的订单存放在一个选课记录备份表中，等待以后恢复使用
	 * 
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 * @param eleList
	 * @param stuBatchList
	 * @return
	 * @author cailei
	 * @throws EntityException
	 */

	public void saveOrderInvoiceEletiveBack(PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo,
			List<PrBzzTchStuElective> eleList, List<StudentBatch> stuBatchList) throws EntityException;

	/**
	 * 用于学员网银支付前期数据的保存
	 * 
	 * @param electiveList
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 * @author cailei
	 * @return
	 */
	public void saveElectiveCourseAndPebzzOrderInfo(List<PrBzzTchStuElective> electiveList, PeBzzOrderInfo peBzzOrderInfo,
			PeBzzInvoiceInfo peBzzInvoiceInfo, List<StudentBatch> sbList);

	/**
	 * 用于初始化student
	 * 
	 * @param userId
	 *            登陆人员的userid
	 * @return
	 * @author cailei
	 */
	public PeBzzStudent initStudentInfo(String userId);

	/**
	 * 用于获取登陆人员的未阅读邮件数量
	 * 
	 * @param regNo
	 * @return
	 * @author cailei
	 */
	public String UnreadNum(String regNo);

	/**
	 * 选课期数量
	 * 
	 * @param regNo
	 * @return
	 * @author Lee
	 */
	public String unChooseCourseNum(String id);

	/**
	 * 通知公告数量
	 * 
	 * @param regNo
	 * @return
	 * @author Lee
	 */
	public String peBulletinnum(String id);

	/**
	 * 获取预付费账户余额
	 * 
	 * @param loginId
	 *            登陆人员的登陆id
	 * @return
	 * @author cailei
	 */
	public BigDecimal balance(String loginId);

	/**
	 * 获取首页列表显示信息
	 * 
	 * @param stuId
	 *            学生id
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public Page paymentCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName,
			String courseItemType, int pageSize, int startIndex) throws EntityException;

	/**
	 * 初始化选课表
	 * 
	 * @param stuId
	 *            学生id
	 * @return
	 * @throws EntityException
	 */
	public Page initCoursePeriodList(String stuId, String coursename, int pageSize, int startIndex, String ktimestart, String ktimeend,
			String etimestart, String etimeend) throws EntityException;

	/**
	 * 初始化专项列表
	 * 
	 * @param loginId
	 *            登陆id
	 * @return
	 * @throws EntityException
	 */
	public Page initBatchList(String loginId, String batchName, int pageSize, int startIndex) throws EntityException;

	/**
	 * 正在学习的课程
	 * 
	 * @param stuId
	 * @param courseCategory
	 * @param courseType
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws EntityException
	 */
	public Page initLearingCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName,
			String courseItemType, int pageSize, int startIndex) throws EntityException;

	public Page initLearingCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName,
			String time, String courseItemType, String courseContent, String suggestren, String teacher, String coursearea, String orderInfo, String orderType, int pageSize,
			int startIndex) throws EntityException;

	public List tongjiLearningCourse(String stuId, String courseCategory, String courseType, String courseCode, String courseName,
			String time, String courseItemType, String courseContent, String suggestren, String teacher, String coursearea);

	public List tongjiFreeCourse(String stuId, String courseCode, String courseName, String courseType, String courseItemType,
			String courseCategory, String courseContent, String time, String teacher, String suggestren);

	/**
	 * 免费课程列表
	 * 
	 * @param userId
	 * @param courseCategory
	 * @param courseType
	 * @param classHour
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws EntityException
	 */
	public Page initFreeCourse(String userId, String courseCode, String courseName, String courseCategory, int pageSize, int startIndex,
			String time, String teacher) throws EntityException;

	public Page initFreeCourse(String userId, String courseCode, String courseName, String courseType, String courseItemType,
			String courseCategory, String courseContent, String time, String teacher, String suggestren, int pageSize, int startIndex,
			String seriseCourse) throws EntityException;

	/**
	 * 公司内训课程列表
	 * 
	 * @param userId
	 * @param courseCategory
	 * @param courseItemType
	 * @param courseType
	 * @param classHour
	 * @param pageSize
	 * @param startIndex
	 * @param coursearea
	 * @param suggestren
	 * @param courseContent
	 * @return
	 * @throws EntityException
	 */
	public Page initInternal(String userId, String courseCode, String courseName, String courseCategory, String courseType,
			String courseItemType, int pageSize, int startIndex, String time, String teacher, String courseContent, String suggestren,
			String coursearea) throws EntityException;

	/**
	 * 资料库 lzh
	 * 
	 * @param ktimeend
	 * @param ktimestart
	 * 
	 */
	public Page initZiLiaolist(String name, String ziliaotype, String fabuunit, String ktimestart, String ktimeend, String kname,
			String kbianhao, String tagIds, int pageSize, int startIndex) throws EntityException;
	
	
	public Page getMyResource(String name, String ziliaotype, String fabuunit, String ktimestart, String ktimeend, String kname,
			String tagIds, int pageSize, int startIndex) throws EntityException;

	/**
	 * 资料库查看相关课程 lzh
	 * 
	 * @param name
	 *            课程名
	 * @param code
	 *            课程编号
	 * @param id
	 */
	public Page initPeResourceList(String name, String code, String id, int pageSize, int startIndex) throws EntityException;

	/**
	 * 学生端查看相关详情页面数据 lzh
	 * 
	 * @param id
	 * @return
	 */
	public List resourceList(String id) throws EntityException;

	/**
	 * 学生端查看相关课程资料页面 lzh
	 * 
	 * @param id
	 * @return
	 * @throws EntityException
	 */
	public List resourceListc(String id) throws EntityException;

	/**
	 * 资料库附件 lzh
	 * 
	 * @param id
	 * @return
	 * @throws EntityException
	 */
	public List resourcefilelink(String id) throws EntityException;

	/**
	 * 已完成课程
	 * 
	 * @param stuId
	 * @param courseType
	 * @param classHour
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws EntityException
	 */
	public Page initCompletedCourses(String stuId, String courseType, String classHour, int pageSize, int startIndex)
			throws EntityException;

	/**
	 * 站内信箱
	 * 
	 * @param loginId
	 * @param selTitle
	 * @param selSendDate
	 * @param selSendName
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws EntityException
	 */
	public Page initSiteemail(String loginId, String selTitle, String selSendDateStart, String selSendDateEnd, String selSendName,
			int pageSize, int startIndex) throws EntityException;

	/**
	 * 调查问卷
	 * 
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws EntityException
	 */
	public Page initVoteList(int pageSize, int startIndex) throws EntityException;

	/**
	 * 调查问卷
	 * 
	 * @param pageSize
	 * @param startIndex
	 * @param startIndex
	 * @return enumConstByFlagQualificationsType
	 * @throws EntityException
	 */
	public Page initVoteListByQfType(int pageSize, int startIndex, EnumConst enumConstByFlagQualificationsType) throws EntityException;

	public Page initVoteListByQfType(int pageSize, int startIndex, EnumConst enumConstByFlagQualificationsType, PeBzzStudent pbs)
			throws EntityException;

	/**
	 * 课程性质
	 * 
	 * @param namespace
	 * @return
	 * @throws EntityException
	 */
	public List<EnumConst> initCouresType(String namespace) throws EntityException;

	/**
	 * 课程类别
	 * 
	 * @param namespace
	 * @return
	 * @throws EntityException
	 */
	public List<EnumConst> initCourseCategory(String namespace) throws EntityException;

	/**
	 * 按大纲分类
	 * 
	 * @param namespace
	 * @return
	 * @throws EntityException
	 */
	public List<EnumConst> initCourseItemType(String namespace) throws EntityException;

	/**
	 * 学生购买课程，显示列表
	 * 
	 * @param stuId
	 * @param courseCategory
	 * @param courseType
	 * @param classHour
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws EntityException
	 */
	public Page initUnelectivedCourses(String stuId, String courseCategory, String courseType, String classHour, int pageSize,
			int startIndex) throws EntityException;

	/**
	 * 课程价格
	 * 
	 * @return
	 * @throws EntityException
	 */
	public BigDecimal initCoursePrice(String namesapce) throws EntityException;

	/**
	 * 专项课程查看
	 * 
	 * @return
	 * @throws EntityException
	 */
	public Page initTrainview(String batchId, int pageSize, int startIndex) throws EntityException;

	/**
	 * 订单再支付时更新信息
	 * 
	 * @param peBzzOrderInfo
	 * @param peBzzInvoiceInfo
	 * @throws EntityException
	 */
	public void updateOrderAndInvoice(PeBzzOrderInfo peBzzOrderInfo, PeBzzInvoiceInfo peBzzInvoiceInfo) throws EntityException;

	/**
	 * 报名历史缓存添加
	 * 
	 * @param userId
	 * @param paymentDate
	 *            按时间搜索传递的时间
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public Page initHistoryRecord(String userId, String paymentDateStart, String paymentDateEnd, int pageSize, int startIndex,
			String orderno, String price, String cname) throws ParseException;

	/**
	 * 报名历史，最后一行订单数统计
	 * 
	 * @param userId
	 * @return
	 */
	public List initHistoryRecordStatistics(String userId);

	/**
	 * 获取学员的正在学习的总时长
	 */
	public String countStudentTotalLearningHours(Map<String, String> params);

	/**
	 * 已完成课程
	 * 
	 * @param stuId
	 * @param courseType
	 * @param classHour
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws EntityException
	 */
	public Page initCompletedCourses(String stuId, String courseType, String examStatus, String classHour, int pageSize, int startIndex)
			throws EntityException;

	/**
	 * 报名历史，最后一行订单数统计
	 * 
	 * @param userId
	 * @return
	 */
	public List initHistoryRecordStatistics(Map params);

	/**
	 * 专项学时统计
	 * 
	 * @param id
	 * @return
	 */
	public String initTrainviewClassNum(String id);

	/**
	 * 用于统计已完成课程的学时
	 * 
	 * @param stuId
	 * @param courseType
	 * @param examStatus
	 * @param classHour
	 * @return
	 */
	public List tongjiCompletedCourse(String stuId, String courseType, String examStatus, String classHour) throws EntityException;

	public List tongjiCompletedCourse(String stuId, String courseType, String courseCode, String courseName, String time, String teacher,
			String coursearea) throws EntityException;

	/**
	 * 已通过课程
	 * 
	 * @param stuId
	 * @param courseType
	 * @param classHour
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws EntityException
	 */
	public Page initPassedCourses(Map params) throws EntityException;

	/**
	 * 用于统计已通过课程的学时
	 * 
	 * @param stuId
	 * @param courseType
	 * @param examStatus
	 * @param classHour
	 * @return
	 */
	public List statisPassedCourse(Map params) throws EntityException;

	/**
	 * 初始化内容属性列表 Lee 2014年03月12日
	 */
	public List initCourseContent(String params);

	/**
	 * 初始化内容属性列表 lzh
	 */
	public List iniziLiao(String params);

	/**
	 * 初始化内容属性列表 dgh 2014年06月20日
	 */
	public List initSuggestRen(String params);

	/**
	 * 初始化课程所属区域
	 */
	public List initCourseArea(String params);

	/**
	 * 获取已选课程学时数 Lee 2014年04月03日
	 * 
	 * @param stuId
	 * @param coursename
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws EntityException
	 */
	public String getTimes(String stuId, String coursename, String ktimestart, String ktimeend, String etimestart, String etimeend)
			throws EntityException;

	/**
	 * 在线直播报名列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page initSacLive(String courseCode, String courseName, String selSendDateStart, String selSendDateEnd, String teacher,
			int pageSize, int startIndex);

	/**
	 * 在线直播学习列表
	 * 
	 * @author Lee
	 * @return
	 */
	public Page initSacLiveStudy(String courseCode, String courseName, String selSendDateStart, String selSendDateEnd, String teacher, String orderInfo, String orderType,
			int pageSize, int startIndex);

	/**
	 * 获取个人首页条目数量等
	 * 
	 * @return
	 */
	public List getNum(String method, String loginId, String studentId) throws Exception;

	/**
	 * 待考试课程
	 * 
	 * @author Lee
	 * @param stuId学员ID
	 * @param courseType课程性质
	 * @param courseCode课程编号
	 * @param courseName课程名称
	 * @param time学时
	 * @param teacher主讲人
	 * @param coursearea所属区域
	 * @return
	 * @throws Exception
	 */
	public Page initCompletedCourses(String stuId, String courseType, String courseCode, String courseName, String time, String teacher,
			String coursearea, String orderInfo, String orderType, int pageSize, int startIndex) throws Exception;

	/**
	 * 已通过课程
	 * 
	 * @author Lee
	 * @param stuId学员ID
	 * @param courseType课程性质
	 * @param time学时
	 * @param teacher主讲人
	 * @param coursearea所属区域
	 * @param selSendDateStart获得学时时间起
	 * @param selSendDateEnd获得学时时间止
	 * @return
	 */
	public Page initPassedCourses(String stuId, String courseType, String time, String teacher, String coursearea, String selSendDateStart,
			String selSendDateEnd, String courseCode, String courseName, String orderInfo, String orderType, int pageSize, int startIndex) throws Exception;

	public List statisPassedCourse(String stuId, String courseType, String time, String teacher, String coursearea,
			String selSendDateStart, String selSendDateEnd, String courseCode, String courseName);

	/**
	 * 问题及建议
	 * 
	 * @param pageSize
	 * @param startIndex
	 * @return
	 * @throws Exception
	 */
	public Page initQuestionAdvice(String topic, String type, int pageSize, int startIndex) throws Exception;

	/**
	 * 2014-10-26 李文强 常见问题库
	 * 
	 * @param title问题标题
	 * @param type问题类型
	 * @param keywords关键字
	 */
	public Page initQuestion(String title, String type, String keywords, int pageSize, int startIndex) throws Exception;

	/**
	 * 2014-10-26 李文强 初始化常见问题类型
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<EnumConst> initQuestionType() throws Exception;

	public List<EnumConst> initIssueType() throws Exception;

	/**
	 * 根据namespace加载码值
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<EnumConst> initEnumConst(String namespace) throws Exception;

	/**
	 * 2014-10-26 李文强 常见问题详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public FrequentlyAskedQuestions showQuestion(String qid) throws Exception;

	public List showQuestionAdvice(String qid) throws Exception;

	public List showQuestionAdviceReplys(String qid) throws Exception;
	
	public void addHoppingCourse(String stuId ,String opencourseId); 
	
	public void updateShoppingCourse(String stuId ); 
}

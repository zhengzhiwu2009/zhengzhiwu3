package com.whaty.platform.entity.bean;

import java.util.Date;
import java.util.Set;

public class PeBzzExamBatch extends AbstractBean implements java.io.Serializable{

	// Fields
	
	private String id;    			//考试批次ID
	private String name;			//考试批次名称NAME
	private Date startEntryDate;	//开始报名时间START_ENTRY
	private Date endEntryDate;		//结束报名时间END_ENTRY
	private Date againStartTime; 	//补考申请开始时间
	private Date againEndTime;  	//补考申请结束时间
	private Date startDelayDate;	//申请缓考开始时间START_DELAY
	private Date endDelayDate;		//申请缓考结束时间END_DELAY
	private String condition;		//报名条件CONDITION
	//private String selected;		//是否为当前考试批次
	private Date startExamDate;		//开始考试时间STARTEXAM
	private Date ednExamDate;		//结束考试时间ENDEXAM
	private PeBzzBatch peBzzBatch; // 所在批次
	private EnumConst enumConstByFlagExamBatch;  // 是否为当前考试批次
	
	private Long time; //基础课学时必须完成百分之几
	private EnumConst enumConstByFlagExamConditionHomework;  //作业是否必须完成
	private EnumConst enumConstByFlagExamConditionTest;  //在线自测是否必须完成
	private EnumConst enumConstByFlagExamConditionEvaluate;  //课程评估是否必须完成
	
	private String exam_explain;//考试说明
	private String exam_notice;//考试通知
	private String late_explain;//缓考说明
	private String answer_explain;//辅导答疑公告
	
	private Long examScale;//考试成绩所占比例
	private Long testScale;//自测成绩所站比例
	
	private PeEnterprise peSite;	//管理员所在的企业

	public Long getExamScale() {
		return examScale;
	}

	public void setExamScale(Long examScale) {
		this.examScale = examScale;
	}

	public Long getTestScale() {
		return testScale;
	}

	public void setTestScale(Long testScale) {
		this.testScale = testScale;
	}

	public String getExam_explain() {
		return exam_explain;
	}

	public void setExam_explain(String exam_explain) {
		this.exam_explain = exam_explain;
	}

	public String getExam_notice() {
		return exam_notice;
	}

	public void setExam_notice(String exam_notice) {
		this.exam_notice = exam_notice;
	}

	public String getLate_explain() {
		return late_explain;
	}

	public void setLate_explain(String late_explain) {
		this.late_explain = late_explain;
	}

	/** default constructor */
	public PeBzzExamBatch() {
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartEntryDate() {
		return startEntryDate;
	}
	public void setStartEntryDate(Date startEntryDate) {
		this.startEntryDate = startEntryDate;
	}
	public Date getEndEntryDate() {
		return endEntryDate;
	}
	public void setEndEntryDate(Date endEntryDate) {
		this.endEntryDate = endEntryDate;
	}
	public Date getStartDelayDate() {
		return startDelayDate;
	}
	public void setStartDelayDate(Date startDelayDate) {
		this.startDelayDate = startDelayDate;
	}
	public Date getEndDelayDate() {
		return endDelayDate;
	}
	public void setEndDelayDate(Date endDelayDate) {
		this.endDelayDate = endDelayDate;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public Date getStartExamDate() {
		return startExamDate;
	}
	public void setStartExamDate(Date startExamDate) {
		this.startExamDate = startExamDate;
	}
	public Date getEdnExamDate() {
		return ednExamDate;
	}
	public void setEdnExamDate(Date ednExamDate) {
		this.ednExamDate = ednExamDate;
	}

	public PeBzzBatch getPeBzzBatch() {
		return peBzzBatch;
	}

	public void setPeBzzBatch(PeBzzBatch peBzzBatch) {
		this.peBzzBatch = peBzzBatch;
	}

	public EnumConst getEnumConstByFlagExamBatch() {
		return enumConstByFlagExamBatch;
	}

	public void setEnumConstByFlagExamBatch(EnumConst enumConstByFlagExamBatch) {
		this.enumConstByFlagExamBatch = enumConstByFlagExamBatch;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public EnumConst getEnumConstByFlagExamConditionHomework() {
		return enumConstByFlagExamConditionHomework;
	}

	public void setEnumConstByFlagExamConditionHomework(
			EnumConst enumConstByFlagExamConditionHomework) {
		this.enumConstByFlagExamConditionHomework = enumConstByFlagExamConditionHomework;
	}

	public EnumConst getEnumConstByFlagExamConditionTest() {
		return enumConstByFlagExamConditionTest;
	}

	public void setEnumConstByFlagExamConditionTest(
			EnumConst enumConstByFlagExamConditionTest) {
		this.enumConstByFlagExamConditionTest = enumConstByFlagExamConditionTest;
	}

	public EnumConst getEnumConstByFlagExamConditionEvaluate() {
		return enumConstByFlagExamConditionEvaluate;
	}

	public void setEnumConstByFlagExamConditionEvaluate(
			EnumConst enumConstByFlagExamConditionEvaluate) {
		this.enumConstByFlagExamConditionEvaluate = enumConstByFlagExamConditionEvaluate;
	}

	public String getAnswer_explain() {
		return answer_explain;
	}

	public void setAnswer_explain(String answer_explain) {
		this.answer_explain = answer_explain;
	}

	public Date getAgainStartTime() {
		return againStartTime;
	}

	public void setAgainStartTime(Date againStartTime) {
		this.againStartTime = againStartTime;
	}

	public Date getAgainEndTime() {
		return againEndTime;
	}

	public void setAgainEndTime(Date againEndTime) {
		this.againEndTime = againEndTime;
	}

	public PeEnterprise getPeSite() {
		return peSite;
	}

	public void setPeSite(PeEnterprise peSite) {
		this.peSite = peSite;
	}
	
}

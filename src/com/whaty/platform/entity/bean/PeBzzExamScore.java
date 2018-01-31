package com.whaty.platform.entity.bean;

public class PeBzzExamScore extends AbstractBean implements java.io.Serializable{

	private String id;		//ID
	private PeBzzStudent peBzzStudent;	//学生
	private PeBzzExamBatch peBzzExamBatch;
	private PeBzzExamBatchSite peBzzExamBatchSite;
	private String score;//考试成绩
	private String subScore;//主观题成绩
	private String objScore;//客观题成绩
	private String test_score;//自测成绩
	private String total_score;//总评成绩
	private String total_grade;//总评等级
	private EnumConst enumConstByFlagExamScoreRelease;//考试成绩是否发布
	private EnumConst enumConstByFlagExamTotalScoreRelease;//总评成绩是否发布
	private EnumConst enumConstByFlagExamTotalGradeRelease;//总评等级是否发布
	private EnumConst enumConstByFlagExamTestScoreRelease;//自测成绩是否发布
	private EnumConst	enumConstByFlagExamScoreStatus; //状态常量：考试报名审核状态
	private EnumConst	enumConstByFlagExamStatus; //状态常量：考试状态
	private EnumConst enumConstByFlagExamType;	//类型：正常、缓考、补考
	
	private PeEnterprise peSite;	//管理员所在的企业
	
	//为准考证而设
	private String testcard_id ;//准考证号
	private String room_id;//考场名称
	private String desk_no;//座位号
	private String address;//考试地址
	private String note;//乘车路线
	private String phone; // 联系电话
	private String exam_time;//考试时间20110222添加  为了区分不同场次
	//private String status;//报名是否审核通过
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}
	public PeBzzStudent getPeBzzStudent() {
		return peBzzStudent;
	}
	public void setPeBzzStudent(PeBzzStudent peBzzStudent) {
		this.peBzzStudent = peBzzStudent;
	}
	public PeBzzExamBatch getPeBzzExamBatch() {
		return peBzzExamBatch;
	}
	public void setPeBzzExamBatch(PeBzzExamBatch peBzzExamBatch) {
		this.peBzzExamBatch = peBzzExamBatch;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public EnumConst getEnumConstByFlagExamScoreStatus() {
		return enumConstByFlagExamScoreStatus;
	}
	public void setEnumConstByFlagExamScoreStatus(
			EnumConst enumConstByFlagExamScoreStatus) {
		this.enumConstByFlagExamScoreStatus = enumConstByFlagExamScoreStatus;
	}
	public PeBzzExamBatchSite getPeBzzExamBatchSite() {
		return peBzzExamBatchSite;
	}
	public void setPeBzzExamBatchSite(PeBzzExamBatchSite peBzzExamBatchSite) {
		this.peBzzExamBatchSite = peBzzExamBatchSite;
	}
	public String getTestcard_id() {
		return testcard_id;
	}
	public void setTestcard_id(String testcard_id) {
		this.testcard_id = testcard_id;
	}
	public String getDesk_no() {
		return desk_no;
	}
	public void setDesk_no(String desk_no) {
		this.desk_no = desk_no;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getExam_time() {
		return exam_time;
	}
	public void setExam_time(String exam_time) {
		this.exam_time = exam_time;
	}
	public String getSubScore() {
		return subScore;
	}
	public void setSubScore(String subScore) {
		this.subScore = subScore;
	}
	public String getObjScore() {
		return objScore;
	}
	public void setObjScore(String objScore) {
		this.objScore = objScore;
	}
	public String getTest_score() {
		return test_score;
	}
	public void setTest_score(String test_score) {
		this.test_score = test_score;
	}
	public String getTotal_score() {
		return total_score;
	}
	public void setTotal_score(String total_score) {
		this.total_score = total_score;
	}
	public String getTotal_grade() {
		return total_grade;
	}
	public void setTotal_grade(String total_grade) {
		this.total_grade = total_grade;
	}
	public EnumConst getEnumConstByFlagExamScoreRelease() {
		return enumConstByFlagExamScoreRelease;
	}
	public void setEnumConstByFlagExamScoreRelease(
			EnumConst enumConstByFlagExamScoreRelease) {
		this.enumConstByFlagExamScoreRelease = enumConstByFlagExamScoreRelease;
	}
	public EnumConst getEnumConstByFlagExamTotalScoreRelease() {
		return enumConstByFlagExamTotalScoreRelease;
	}
	public void setEnumConstByFlagExamTotalScoreRelease(
			EnumConst enumConstByFlagExamTotalScoreRelease) {
		this.enumConstByFlagExamTotalScoreRelease = enumConstByFlagExamTotalScoreRelease;
	}
	public EnumConst getEnumConstByFlagExamTotalGradeRelease() {
		return enumConstByFlagExamTotalGradeRelease;
	}
	public void setEnumConstByFlagExamTotalGradeRelease(
			EnumConst enumConstByFlagExamTotalGradeRelease) {
		this.enumConstByFlagExamTotalGradeRelease = enumConstByFlagExamTotalGradeRelease;
	}
	public EnumConst getEnumConstByFlagExamTestScoreRelease() {
		return enumConstByFlagExamTestScoreRelease;
	}
	public void setEnumConstByFlagExamTestScoreRelease(
			EnumConst enumConstByFlagExamTestScoreRelease) {
		this.enumConstByFlagExamTestScoreRelease = enumConstByFlagExamTestScoreRelease;
	}
	public EnumConst getEnumConstByFlagExamStatus() {
		return enumConstByFlagExamStatus;
	}
	public void setEnumConstByFlagExamStatus(EnumConst enumConstByFlagExamStatus) {
		this.enumConstByFlagExamStatus = enumConstByFlagExamStatus;
	}
	public EnumConst getEnumConstByFlagExamType() {
		return enumConstByFlagExamType;
	}
	public void setEnumConstByFlagExamType(EnumConst enumConstByFlagExamType) {
		this.enumConstByFlagExamType = enumConstByFlagExamType;
	}
	public PeEnterprise getPeSite() {
		return peSite;
	}
	public void setPeSite(PeEnterprise peSite) {
		this.peSite = peSite;
	}
	
}

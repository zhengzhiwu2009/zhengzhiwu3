package com.whaty.platform.entity.service.information;
/**
 * 投票
 * @author 李冰
 *
 */
public interface PeVotePaperService {

	/**
	 * 对于投票的记录
	 * @param vote
	 */
	public void saveVoteNumber(String vote);
	/**
	 * 保存主观题记录
	 * @param vote
	 */
	public void saveOrUpdateSubVoteResult(String subQuestion);
	/**
	 * 保存或更新用户选择题投票记录
	 * @param vote
	 */
	public void saveOrUpdateUserVoteResult(String voteResult);
	/**
	 * 给课程添加满意度调查
	 * @param FvoteId
	 * @param courseId
	 * @return
	 */
	public int addCourseVote(String FvoteId,String courseId);
	/**课程重复添加满意度
	 * 需要删除已添加过的满意度和问题
	 * */
	public boolean getFlag(String FvoteId,String courseId);
}	

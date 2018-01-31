package com.whaty.platform.entity.service.imp.information;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.opensymphony.xwork2.ActionContext;
import com.whaty.platform.entity.bean.PeBzzTchCourse;
import com.whaty.platform.entity.bean.PeVotePaper;
import com.whaty.platform.entity.bean.PrVoteQuestion;
import com.whaty.platform.entity.bean.PrVoteRecord;
import com.whaty.platform.entity.bean.PrVoteSubQuestion;
import com.whaty.platform.entity.bean.PrVoteUserQuestion;
import com.whaty.platform.entity.bean.SsoUser;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.service.information.PeVotePaperService;
import com.whaty.platform.sso.web.action.SsoConstant;
import com.whaty.platform.sso.web.servlet.UserSession;

/**
 * 投票
 * 
 * @author 李冰
 * 
 */
public class PeVotePaperServiceImp implements PeVotePaperService {
	private GeneralDao generalDao;

	/**
	 * 保存或更新主观题
	 */
	public void saveOrUpdateSubVoteResult(String subQuestion) {
		String[] theItem = subQuestion.split("#");
		for (int i = 0; i < theItem.length; i++) {
			String[] question = theItem[i].split("@");
			if (question.length == 2) {
				String questionId = "";
				String custom_type = "";
				if (question[0].startsWith("custom_")) {
					custom_type = "1";
					questionId = question[0].substring(7).toString().trim();
				} else {
					custom_type = "0";
					questionId = question[0].trim();
				}
				this.getGeneralDao().setEntityClass(PrVoteQuestion.class);
				PrVoteQuestion prVoteQuestion = (PrVoteQuestion) this
						.getGeneralDao().getById(questionId);
				this.getGeneralDao().setEntityClass(PrVoteSubQuestion.class);
				UserSession userSession = (UserSession) ActionContext
						.getContext().getSession().get(
								SsoConstant.SSO_USER_SESSION_KEY);
				SsoUser ssoUser = userSession.getSsoUser();
				List subs = this.getGeneralDao().getBySQL(
						"select * from PR_VOTE_SUB_QUESTION t where t.student_id='"
								+ ssoUser.getId()
								+ "' and t.fk_vote_question_id='"
								+ prVoteQuestion.getId() + "'");
				if (subs != null && subs.size() > 0) {
					Date now = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String rightNow = sdf.format(now);
					String sql = "update PR_VOTE_SUB_QUESTION t set t.item_content='"
							+ question[1]
							+ "' ,iscustom ='"
							+ custom_type
							+ "' , t.create_date=to_date('"
							+ rightNow
							+ "', 'yyyy-mm-dd hh24:mi:ss') where t.student_id='"
							+ ssoUser.getId()
							+ "' and t.fk_vote_question_id='"
							+ prVoteQuestion.getId()
							+ "' and t.iscustom ='"
							+ custom_type + "' ";
					this.getGeneralDao().executeBySQL(sql);
				} else {
					PrVoteSubQuestion prVoteSubQuestion = new PrVoteSubQuestion();
					prVoteSubQuestion.setCreateDate(new Date());
					prVoteSubQuestion.setIsCustom(custom_type);
					prVoteSubQuestion.setItemContent(question[1]);
					prVoteSubQuestion.setPrVoteQuestion(prVoteQuestion);
					prVoteSubQuestion.setSsoUser(ssoUser);
					this.getGeneralDao().save(prVoteSubQuestion);
				}
			} else {
				continue;
			}
		}
	}

	/**
	 * 对于投票的记录
	 */
	public void saveVoteNumber(String vote) {
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		String[] answers = this.getAnswer(vote);
		String[] qeIds = new String[answers.length];
		for (int i = 0; i < answers.length; i++) {
			String[] qe = answers[i].split(":");
			qeIds[i] = qe[0];
			this.getGeneralDao().setEntityClass(PrVoteQuestion.class);
			PrVoteQuestion prVoteQuestion = (PrVoteQuestion) this
					.getGeneralDao().getById(qe[0].trim());
			// 查询用户曾经投过的票
			String lastVoteResult = "";

			DetachedCriteria userQ = DetachedCriteria
					.forClass(PrVoteUserQuestion.class);
			userQ.createCriteria("prVoteQuestion", "prVoteQuestion");
			userQ.add(Restrictions.eq("prVoteQuestion.id", prVoteQuestion
					.getId()));
			userQ.createCriteria("ssoUser", "ssoUser");
			userQ.add(Restrictions.eq("ssoUser", ssoUser));
			List userVotes = this.getGeneralDao().getList(userQ);
			if (userVotes != null && userVotes.size() > 0) {
				PrVoteUserQuestion pvuq = (PrVoteUserQuestion) userVotes.get(0);
				lastVoteResult = pvuq.getVoteResult().trim();
			}

			// 将用户上次投过但此次未投的选项票数减1
			if (!lastVoteResult.trim().equals("")) {
				String[] lr = lastVoteResult.split(",");
				for (int k = 0; k < lr.length; k++) {
					if (!lr[k].trim().equals("")
							&& strContains(qe[1].trim(), lr[k].trim())) {
						if (lr[k].trim().equals("1")) {
							if (prVoteQuestion.getItemResult1() != null
									&& prVoteQuestion.getItemResult1() > 1) {
								prVoteQuestion.setItemResult1(prVoteQuestion
										.getItemResult1() - 1);
							} else {
								prVoteQuestion.setItemResult1(null);
							}

						} else if (lr[k].trim().equals("2")) {
							if (prVoteQuestion.getItemResult2() != null
									&& prVoteQuestion.getItemResult2() > 1) {
								prVoteQuestion.setItemResult2(prVoteQuestion
										.getItemResult2() - 1);
							} else {
								prVoteQuestion.setItemResult2(null);
							}
						} else if (lr[k].trim().equals("3")) {
							if (prVoteQuestion.getItemResult3() != null
									&& prVoteQuestion.getItemResult3() > 1) {
								prVoteQuestion.setItemResult3(prVoteQuestion
										.getItemResult3() - 1);
							} else {
								prVoteQuestion.setItemResult3(null);
							}
						} else if (lr[k].trim().equals("4")) {
							if (prVoteQuestion.getItemResult4() != null
									&& prVoteQuestion.getItemResult4() > 1) {
								prVoteQuestion.setItemResult4(prVoteQuestion
										.getItemResult4() - 1);
							} else {
								prVoteQuestion.setItemResult4(null);
							}
						} else if (lr[k].trim().equals("5")) {
							if (prVoteQuestion.getItemResult5() != null
									&& prVoteQuestion.getItemResult5() > 1) {
								prVoteQuestion.setItemResult5(prVoteQuestion
										.getItemResult5() - 1);
							} else {
								prVoteQuestion.setItemResult5(null);
							}
						} else if (lr[k].trim().equals("6")) {
							if (prVoteQuestion.getItemResult6() != null
									&& prVoteQuestion.getItemResult6() > 1) {
								prVoteQuestion.setItemResult6(prVoteQuestion
										.getItemResult6() - 1);
							} else {
								prVoteQuestion.setItemResult6(null);
							}
						} else if (lr[k].trim().equals("7")) {
							if (prVoteQuestion.getItemResult7() != null
									&& prVoteQuestion.getItemResult7() > 1) {
								prVoteQuestion.setItemResult7(prVoteQuestion
										.getItemResult7() - 1);
							} else {
								prVoteQuestion.setItemResult7(null);
							}
						} else if (lr[k].trim().equals("8")) {
							if (prVoteQuestion.getItemResult8() != null
									&& prVoteQuestion.getItemResult8() > 1) {
								prVoteQuestion.setItemResult8(prVoteQuestion
										.getItemResult8() - 1);
							} else {
								prVoteQuestion.setItemResult8(null);
							}
						} else if (lr[k].trim().equals("9")) {
							if (prVoteQuestion.getItemResult9() != null
									&& prVoteQuestion.getItemResult9() > 1) {
								prVoteQuestion.setItemResult9(prVoteQuestion
										.getItemResult9() - 1);
							} else {
								prVoteQuestion.setItemResult9(null);
							}
						} else if (lr[k].trim().equals("10")) {
							if (prVoteQuestion.getItemResult10() != null
									&& prVoteQuestion.getItemResult10() > 1) {
								prVoteQuestion.setItemResult10(prVoteQuestion
										.getItemResult10() - 1);
							} else {
								prVoteQuestion.setItemResult10(null);
							}
						} else if (lr[k].trim().equals("11")) {
							if (prVoteQuestion.getItemResult11() != null
									&& prVoteQuestion.getItemResult11() > 1) {
								prVoteQuestion.setItemResult11(prVoteQuestion
										.getItemResult11() - 1);
							} else {
								prVoteQuestion.setItemResult11(null);
							}
						} else if (lr[k].trim().equals("12")) {
							if (prVoteQuestion.getItemResult12() != null
									&& prVoteQuestion.getItemResult12() > 1) {
								prVoteQuestion.setItemResult12(prVoteQuestion
										.getItemResult12() - 1);
							} else {
								prVoteQuestion.setItemResult12(null);
							}
						} else if (lr[k].trim().equals("13")) {
							if (prVoteQuestion.getItemResult13() != null
									&& prVoteQuestion.getItemResult13() > 1) {
								prVoteQuestion.setItemResult13(prVoteQuestion
										.getItemResult13() - 1);
							} else {
								prVoteQuestion.setItemResult13(null);
							}
						} else if (lr[k].trim().equals("14")) {
							if (prVoteQuestion.getItemResult14() != null
									&& prVoteQuestion.getItemResult14() > 1) {
								prVoteQuestion.setItemResult14(prVoteQuestion
										.getItemResult14() - 1);
							} else {
								prVoteQuestion.setItemResult14(null);
							}
						} else if (lr[k].trim().equals("15")) {
							if (prVoteQuestion.getItemResult15() != null
									&& prVoteQuestion.getItemResult15() > 1) {
								prVoteQuestion.setItemResult15(prVoteQuestion
										.getItemResult15() - 1);
							} else {
								prVoteQuestion.setItemResult15(null);
							}
						}
					}

				}
			}
			// 针对用户选项调整投票数
			String[] nowVote = qe[1].trim().split(",");
			for (int j = 0; j < nowVote.length; j++) {
				if (!nowVote[j].trim().equals("")) {
					if (nowVote[j].trim().equals("1")) {
						if (prVoteQuestion.getItemResult1() != null
								&& prVoteQuestion.getItemResult1() > 0) {
							// 用户未投过此题票或者上次投过但未投此选项，票数加1
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "1"))) {
								prVoteQuestion.setItemResult1(prVoteQuestion
										.getItemResult1() + 1);
							}
						} else {// 此选项被投过票投后设为1
							prVoteQuestion.setItemResult1(1L);
						}
					} else if (nowVote[j].trim().equals("2")) {
						if (prVoteQuestion.getItemResult2() != null
								&& prVoteQuestion.getItemResult2() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "2"))) {
								prVoteQuestion.setItemResult2(prVoteQuestion
										.getItemResult2() + 1);
							}
						} else {
							prVoteQuestion.setItemResult2(1L);
						}
					} else if (nowVote[j].trim().equals("3")) {
						if (prVoteQuestion.getItemResult3() != null
								&& prVoteQuestion.getItemResult3() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "3"))) {
								prVoteQuestion.setItemResult3(prVoteQuestion
										.getItemResult3() + 1);
							}
						} else {
							prVoteQuestion.setItemResult3(1L);
						}
					} else if (nowVote[j].trim().equals("4")) {
						if (prVoteQuestion.getItemResult4() != null
								&& prVoteQuestion.getItemResult4() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "4"))) {
								prVoteQuestion.setItemResult4(prVoteQuestion
										.getItemResult4() + 1);
							}
						} else {
							prVoteQuestion.setItemResult4(1L);
						}
					} else if (nowVote[j].trim().equals("5")) {
						if (prVoteQuestion.getItemResult5() != null
								&& prVoteQuestion.getItemResult5() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "5"))) {
								prVoteQuestion.setItemResult5(prVoteQuestion
										.getItemResult5() + 1);
							}
						} else {
							prVoteQuestion.setItemResult5(1L);
						}
					} else if (nowVote[j].trim().equals("6")) {
						if (prVoteQuestion.getItemResult6() != null
								&& prVoteQuestion.getItemResult6() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "6"))) {
								prVoteQuestion.setItemResult6(prVoteQuestion
										.getItemResult6() + 1);
							}
						} else {
							prVoteQuestion.setItemResult6(1L);
						}
					} else if (nowVote[j].trim().equals("7")) {
						if (prVoteQuestion.getItemResult7() != null
								&& prVoteQuestion.getItemResult7() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "7"))) {
								prVoteQuestion.setItemResult7(prVoteQuestion
										.getItemResult7() + 1);
							}
						} else {
							prVoteQuestion.setItemResult7(1L);
						}
					} else if (nowVote[j].trim().equals("8")) {
						if (prVoteQuestion.getItemResult8() != null
								&& prVoteQuestion.getItemResult8() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "8"))) {
								prVoteQuestion.setItemResult8(prVoteQuestion
										.getItemResult8() + 1);
							}
						} else {
							prVoteQuestion.setItemResult8(1L);
						}
					} else if (nowVote[j].trim().equals("9")) {
						if (prVoteQuestion.getItemResult9() != null
								&& prVoteQuestion.getItemResult9() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "9"))) {
								prVoteQuestion.setItemResult9(prVoteQuestion
										.getItemResult9() + 1);
							}
						} else {
							prVoteQuestion.setItemResult9(1L);
						}
					} else if (nowVote[j].trim().equals("10")) {
						if (prVoteQuestion.getItemResult10() != null
								&& prVoteQuestion.getItemResult10() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "10"))) {
								prVoteQuestion.setItemResult10(prVoteQuestion
										.getItemResult10() + 1);
							}
						} else {
							prVoteQuestion.setItemResult10(1L);
						}
					} else if (nowVote[j].trim().equals("11")) {
						if (prVoteQuestion.getItemResult11() != null
								&& prVoteQuestion.getItemResult11() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "11"))) {
								prVoteQuestion.setItemResult11(prVoteQuestion
										.getItemResult11() + 1);
							}
						} else {
							prVoteQuestion.setItemResult11(1L);
						}
					} else if (nowVote[j].trim().equals("12")) {
						if (prVoteQuestion.getItemResult12() != null
								&& prVoteQuestion.getItemResult12() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "12"))) {
								prVoteQuestion.setItemResult12(prVoteQuestion
										.getItemResult12() + 1);
							}
						} else {
							prVoteQuestion.setItemResult12(1L);
						}
					} else if (nowVote[j].trim().equals("13")) {
						if (prVoteQuestion.getItemResult13() != null
								&& prVoteQuestion.getItemResult13() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "13"))) {
								prVoteQuestion.setItemResult13(prVoteQuestion
										.getItemResult13() + 1);
							}
						} else {
							prVoteQuestion.setItemResult13(1L);
						}
					} else if (nowVote[j].trim().equals("14")) {
						if (prVoteQuestion.getItemResult14() != null
								&& prVoteQuestion.getItemResult14() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "14"))) {
								prVoteQuestion.setItemResult14(prVoteQuestion
										.getItemResult14() + 1);
							}
						} else {
							prVoteQuestion.setItemResult14(1L);
						}
					} else if (nowVote[j].trim().equals("15")) {
						if (prVoteQuestion.getItemResult15() != null
								&& prVoteQuestion.getItemResult15() > 0) {
							if (lastVoteResult.equals("")
									|| (!lastVoteResult.equals("") && strContains(
											lastVoteResult, "15"))) {
								prVoteQuestion.setItemResult15(prVoteQuestion
										.getItemResult15() + 1);
							}
						} else {
							prVoteQuestion.setItemResult15(1L);
						}
					}
				}

			}
			this.getGeneralDao().setEntityClass(PrVoteQuestion.class);
			this.getGeneralDao().save(prVoteQuestion);
			if (!qe[1].trim().equals("")) {// 更新用户投票记录
				this.getGeneralDao().setEntityClass(PrVoteUserQuestion.class);
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String rightNow = sdf.format(now);
				this
						.getGeneralDao()
						.executeBySQL(
								"update PR_VOTE_USER_QUESTION t set t.VOTE_RESULT='"
										+ qe[1]
										+ "' , t.create_date=to_date('"
										+ rightNow
										+ "', 'yyyy-mm-dd hh24:mi:ss') where t.student_id='"
										+ ssoUser.getId()
										+ "' and t.fk_vote_question_id='"
										+ qe[0] + "'");
			}
		}
		// 此问卷中，记录用户曾经投过现在没投问题的选项票数
		this.getGeneralDao().setEntityClass(PrVoteQuestion.class);
		PrVoteQuestion prvq = (PrVoteQuestion) this.getGeneralDao().getById(
				qeIds[0].trim());
		PeVotePaper peVotePaper = prvq.getPeVotePaper();
		DetachedCriteria prUQ = DetachedCriteria
				.forClass(PrVoteUserQuestion.class);
		prUQ.createCriteria("prVoteQuestion", "prVoteQuestion");
		prUQ.add(Restrictions.eq("prVoteQuestion.peVotePaper.id", peVotePaper
				.getId()));
		prUQ.add(Restrictions.not(Restrictions.in("prVoteQuestion.id", qeIds)));
		prUQ.createCriteria("ssoUser", "ssoUser");
		prUQ.add(Restrictions.eq("ssoUser", ssoUser));
		List prUQs = this.getGeneralDao().getList(prUQ);
		for (int n = 0; n < prUQs.size(); n++) {
			PrVoteUserQuestion prvuq = (PrVoteUserQuestion) prUQs.get(n);
			DetachedCriteria qes = DetachedCriteria
					.forClass(PrVoteQuestion.class);
			qes.add(Restrictions.eq("id", prvuq.getPrVoteQuestion().getId()));
			List qesss = this.getGeneralDao().getList(qes);
			if (qesss != null && qesss.size() > 0) {
				PrVoteQuestion qess = (PrVoteQuestion) qesss.get(0);
				String rl = prvuq.getVoteResult();
				String[] an = rl.split(",");
				for (int p = 0; p < an.length; p++) {
					if (an[p].trim().equals("1")) {
						if (qess.getItemResult1() != null
								&& qess.getItemResult1() > 1) {
							qess.setItemResult1(qess.getItemResult1() - 1);
						} else {
							qess.setItemResult1(null);
						}

					} else if (an[p].trim().equals("2")) {
						if (qess.getItemResult2() != null
								&& qess.getItemResult2() > 1) {
							qess.setItemResult2(qess.getItemResult2() - 1);
						} else {
							qess.setItemResult2(null);
						}
					} else if (an[p].trim().equals("3")) {
						if (qess.getItemResult3() != null
								&& qess.getItemResult3() > 1) {
							qess.setItemResult3(qess.getItemResult3() - 1);
						} else {
							qess.setItemResult3(null);
						}
					} else if (an[p].trim().equals("4")) {
						if (qess.getItemResult4() != null
								&& qess.getItemResult4() > 1) {
							qess.setItemResult4(qess.getItemResult4() - 1);
						} else {
							qess.setItemResult4(null);
						}
					} else if (an[p].trim().equals("5")) {
						if (qess.getItemResult5() != null
								&& qess.getItemResult5() > 1) {
							qess.setItemResult5(qess.getItemResult5() - 1);
						} else {
							qess.setItemResult5(null);
						}
					} else if (an[p].trim().equals("6")) {
						if (qess.getItemResult6() != null
								&& qess.getItemResult6() > 1) {
							qess.setItemResult6(qess.getItemResult6() - 1);
						} else {
							qess.setItemResult6(null);
						}
					} else if (an[p].trim().equals("7")) {
						if (qess.getItemResult7() != null
								&& qess.getItemResult7() > 1) {
							qess.setItemResult7(qess.getItemResult7() - 1);
						} else {
							qess.setItemResult7(null);
						}
					} else if (an[p].trim().equals("8")) {
						if (qess.getItemResult8() != null
								&& qess.getItemResult8() > 1) {
							qess.setItemResult8(qess.getItemResult8() - 1);
						} else {
							qess.setItemResult8(null);
						}
					} else if (an[p].trim().equals("9")) {
						if (qess.getItemResult9() != null
								&& qess.getItemResult9() > 1) {
							qess.setItemResult9(qess.getItemResult9() - 1);
						} else {
							qess.setItemResult9(null);
						}
					} else if (an[p].trim().equals("10")) {
						if (qess.getItemResult10() != null
								&& qess.getItemResult10() > 1) {
							qess.setItemResult10(qess.getItemResult10() - 1);
						} else {
							qess.setItemResult10(null);
						}
					} else if (an[p].trim().equals("11")) {
						if (qess.getItemResult11() != null
								&& qess.getItemResult11() > 1) {
							qess.setItemResult11(qess.getItemResult11() - 1);
						} else {
							qess.setItemResult11(null);
						}
					} else if (an[p].trim().equals("12")) {
						if (qess.getItemResult12() != null
								&& qess.getItemResult12() > 1) {
							qess.setItemResult12(qess.getItemResult12() - 1);
						} else {
							qess.setItemResult12(null);
						}
					} else if (an[p].trim().equals("13")) {
						if (qess.getItemResult13() != null
								&& qess.getItemResult13() > 1) {
							qess.setItemResult13(qess.getItemResult13() - 1);
						} else {
							qess.setItemResult13(null);
						}
					} else if (an[p].trim().equals("14")) {
						if (qess.getItemResult14() != null
								&& qess.getItemResult14() > 1) {
							qess.setItemResult14(qess.getItemResult14() - 1);
						} else {
							qess.setItemResult14(null);
						}
					} else if (an[p].trim().equals("15")) {
						if (qess.getItemResult15() != null
								&& qess.getItemResult15() > 1) {
							qess.setItemResult15(qess.getItemResult15() - 1);
						} else {
							qess.setItemResult15(null);
						}
					}
				}
				this.getGeneralDao().setEntityClass(PrVoteQuestion.class);
				this.getGeneralDao().save(qess);
				this.getGeneralDao().setEntityClass(PrVoteUserQuestion.class);
				this.getGeneralDao().delete(prvuq);

			}
		}

	}

	public Boolean strContains(String str, String num) {
		if (str.indexOf("," + num + ",") < 0 && str.indexOf(num + ",") < 0) {
			return true;
		}
		return false;
	}

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public int addCourseVote(String FvoteId, String courseId) {
		int i;
		String paperId;
		String sql = "select id from Pe_Bzz_Tch_Course  p  where  p.id='"
				+ courseId + "'";
		List<String> courselist = this.generalDao.getBySQL(sql);
		if (null == courselist || courselist.size() == 0) {
			return 0;
		}
		i = 0;
		// 判断课程是否已经用过该调查问卷
		String paperSql = " SELECT p.id FROM PE_VOTE_PAPER P WHERE p.COURSEID ='"
				+ courseId + "' AND p.FK_PARENT_ID ='" + FvoteId + "' ";
		List<String> peVotePaperList = this.generalDao.getBySQL(paperSql);
		if (peVotePaperList != null && peVotePaperList.size() > 0) {
			paperId = peVotePaperList.get(0);
			sql = "update Pe_Bzz_Tch_Course p set p.SATISFACTION_ID='"
				+paperId+ "' where p.id='"
				+ courselist.get(0) + "'";
		i = this.generalDao.executeBySQL(sql);

		} else {
			this.getGeneralDao().setEntityClass(PeVotePaper.class);
			PeVotePaper peVotePaper = (PeVotePaper) this.getGeneralDao()
					.getById(FvoteId);
			PeVotePaper newPeVotePaper = new PeVotePaper();
			newPeVotePaper.setCreateUser(peVotePaper.getCreateUser());
			newPeVotePaper.setStartDate(new Date());
			newPeVotePaper.setEndDate(peVotePaper.getEndDate());
			newPeVotePaper.setEnumConstByFlagCanSuggest(peVotePaper
					.getEnumConstByFlagCanSuggest());
			newPeVotePaper.setEnumConstByFlagIsvalid(peVotePaper
					.getEnumConstByFlagIsvalid());
			newPeVotePaper.setEnumConstByFlagLimitDiffip(peVotePaper
					.getEnumConstByFlagLimitDiffip());
			newPeVotePaper.setEnumConstByFlagLimitDiffsession(peVotePaper
					.getEnumConstByFlagLimitDiffsession());
			newPeVotePaper.setEnumConstByFlagQualificationsType(peVotePaper
					.getEnumConstByFlagQualificationsType());
			newPeVotePaper.setEnumConstByFlagViewSuggest(peVotePaper
					.getEnumConstByFlagViewSuggest());
			newPeVotePaper.setNote(peVotePaper.getNote());
			newPeVotePaper.setTitle(peVotePaper.getTitle());
			newPeVotePaper.setCourseId(courseId);
			newPeVotePaper.setType(peVotePaper.getType());
			newPeVotePaper.setFkPapentId(peVotePaper.getId());
			newPeVotePaper = (PeVotePaper) this.generalDao.save(newPeVotePaper);
			if (null != newPeVotePaper) {
				DetachedCriteria dc = DetachedCriteria.forClass(
						PrVoteQuestion.class).add(
						Restrictions.eq("peVotePaper.id", FvoteId));
				List<PrVoteQuestion> list = this.getGeneralDao().getDetachList(
						dc);
				if (!"".equals(list) && null != list) {
					for (int j = 0; j < list.size(); j++) {
						PrVoteQuestion pvq = list.get(j);
						PrVoteQuestion p = new PrVoteQuestion();
						
						p.setQuestionNote(pvq.getQuestionNote());
						p.setCreateDate(pvq.getCreateDate());
						p.setEnumConstByFlagBak(pvq.getEnumConstByFlagBak());
						p.setEnumConstByFlagQuestionType(pvq
								.getEnumConstByFlagQuestionType());
						p.setItem1(pvq.getItem1());
						p.setItem2(pvq.getItem2());
						p.setItem3(pvq.getItem3());
						p.setItem4(pvq.getItem4());
						p.setItem5(pvq.getItem5());
						p.setItem6(pvq.getItem6());
						p.setItem7(pvq.getItem7());
						p.setItem8(pvq.getItem8());
						p.setItem9(pvq.getItem9());
						p.setItem10(pvq.getItem10());
						p.setItem11(pvq.getItem11());
						p.setItem12(pvq.getItem12());
						p.setItem13(pvq.getItem13());
						p.setItem14(pvq.getItem14());
						p.setItem15(pvq.getItem15());
						p.setItemNum(pvq.getItemNum());
						p.setItemResult1(pvq.getItemResult1());
						p.setItemResult2(pvq.getItemResult2());
						p.setItemResult3(pvq.getItemResult3());
						p.setItemResult4(pvq.getItemResult4());
						p.setItemResult5(pvq.getItemResult5());
						p.setItemResult6(pvq.getItemResult6());
						p.setItemResult7(pvq.getItemResult7());
						p.setItemResult8(pvq.getItemResult8());
						p.setItemResult9(pvq.getItemResult9());
						p.setItemResult10(pvq.getItemResult10());
						p.setItemResult11(pvq.getItemResult11());
						p.setItemResult12(pvq.getItemResult12());
						p.setItemResult13(pvq.getItemResult13());
						p.setItemResult14(pvq.getItemResult14());
						p.setItemResult15(pvq.getItemResult15());
						p.setPeVotePaper(newPeVotePaper);
						p.setItemScore1(pvq.getItemScore1());
						p.setItemScore2(pvq.getItemScore2());
						p.setItemScore3(pvq.getItemScore3());
						p.setItemScore4(pvq.getItemScore4());
						p.setItemScore5(pvq.getItemScore5());
						p.setItemScore6(pvq.getItemScore6());
						p.setItemScore7(pvq.getItemScore7());
						p.setItemScore8(pvq.getItemScore8());
						p.setItemScore9(pvq.getItemScore9());
						p.setItemScore10(pvq.getItemScore10());
						p.setItemScore11(pvq.getItemScore11());
						p.setItemScore12(pvq.getItemScore12());
						p.setItemScore13(pvq.getItemScore13());
						p.setItemScore14(pvq.getItemScore14());
						p.setItemScore15(pvq.getItemScore15());
						p.setWeight(pvq.getWeight());
						p.setIsCustom(pvq.getIsCustom());
						p.setIsRequirde(pvq.getIsRequirde());
						p.setQuestionOrder(pvq.getQuestionOrder());
						p.setFkParentId(pvq.getId());
						this.generalDao.save(p);
					}
				}
				sql = "update Pe_Bzz_Tch_Course p set p.SATISFACTION_ID='"
						+ newPeVotePaper.getId() + "' where p.id='"
						+ courselist.get(0) + "'";
				i = this.generalDao.executeBySQL(sql);

			}
		}
		return i;
	}

	public boolean getFlag(String FvoteId, String courseId) {
		List list = this.generalDao
				.getBySQL(" SELECT * FROM PR_VOTE_RECORD PVR WHERE PVR.FK_VOTE_PAPER_ID IN (SELECT P.ID FROM PE_VOTE_PAPER P WHERE  P.COURSEID = '"
						+ courseId + "')");
		if (list != null && list.size() > 0) {
			return false;
		}

		List<PeVotePaper> peVotePaperList = this.generalDao
				.getByHQL(" from PeVotePaper where courseId ='" + courseId
						+ "' ");
		if (peVotePaperList != null && peVotePaperList.size() > 0)
			for (PeVotePaper pe : peVotePaperList) {
				this.generalDao
						.executeBySQL(" delete from pr_vote_question where fk_vote_paper_id ='"
								+ pe.getId() + "' ");
				this.generalDao
						.executeBySQL(" delete from pe_vote_paper where id = '"
								+ pe.getId() + "' ");
			}

		return true;
	}

	public String[] getAnswer(String voteResult) {
		// TODO Auto-generated method stub
		String[] questions = voteResult.split(",");
		Set set = new HashSet();
		for (int i = 0; i < questions.length; i++) {
			String[] question = questions[i].split("@");
			if (question.length == 2) {
				set.add(question[0].trim());
			}
		}
		String[] result = new String[set.size()];
		Iterator it = set.iterator();
		int k = 0;
		while (it.hasNext()) {
			String re = (String) it.next();
			result[k] = re + ":";
			k++;
		}
		for (int j = 0; j < result.length; j++) {
			for (int i = 0; i < questions.length; i++) {
				String[] question = questions[i].split("@");
				if (question.length == 2) {
					int index = result[j].indexOf(":");
					if (result[j].trim().substring(0, index).equals(
							question[0].trim())) {
						result[j] = result[j] + question[1] + ",";
					}
				}
			}
		}
		return result;
	}

	@Override
	public void saveOrUpdateUserVoteResult(String voteResult) {
		// TODO Auto-generated method stub
		UserSession userSession = (UserSession) ActionContext.getContext()
				.getSession().get(SsoConstant.SSO_USER_SESSION_KEY);
		SsoUser ssoUser = userSession.getSsoUser();
		String[] answers = getAnswer(voteResult);
		for (int i = 0; i < answers.length; i++) {
			String[] question = answers[i].trim().split(":");
			// int lastIndex=question[1].trim().lastIndexOf(",");
			// question[1]=question[1].trim().substring(0, lastIndex);
			this.getGeneralDao().setEntityClass(PrVoteQuestion.class);
			PrVoteQuestion prVoteQuestion = (PrVoteQuestion) this
					.getGeneralDao().getById(question[0].trim());

			this.getGeneralDao().setEntityClass(PrVoteUserQuestion.class);
			List userVotes = this.getGeneralDao().getBySQL(
					"select * from PR_VOTE_USER_QUESTION t where t.student_id='"
							+ ssoUser.getId() + "' and t.fk_vote_question_id='"
							+ prVoteQuestion.getId() + "'");
			if (userVotes != null && userVotes.size() > 0) {
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String rightNow = sdf.format(now);
				this
						.getGeneralDao()
						.executeBySQL(
								"update PR_VOTE_USER_QUESTION t set t.VOTE_RESULT='"
										+ question[1]
										+ "' , t.create_date=to_date('"
										+ rightNow
										+ "', 'yyyy-mm-dd hh24:mi:ss') where t.student_id='"
										+ ssoUser.getId()
										+ "' and t.fk_vote_question_id='"
										+ question[0] + "'");
			} else {
				PrVoteUserQuestion pvuq = new PrVoteUserQuestion();
				pvuq.setCreateDate(new Date());
				pvuq.setPrVoteQuestion(prVoteQuestion);
				pvuq.setSsoUser(ssoUser);
				pvuq.setVoteResult(question[1]);
				this.getGeneralDao().save(pvuq);
			}

		}
	}

}

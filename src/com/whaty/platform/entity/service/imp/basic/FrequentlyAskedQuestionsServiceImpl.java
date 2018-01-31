package com.whaty.platform.entity.service.imp.basic;

import com.whaty.platform.entity.bean.FrequentlyAskedQuestions;
import com.whaty.platform.entity.dao.GeneralDao;
import com.whaty.platform.entity.exception.EntityException;
import com.whaty.platform.entity.service.basic.FrequentlyAskedQuestionsService;

public class FrequentlyAskedQuestionsServiceImpl implements FrequentlyAskedQuestionsService {
	private GeneralDao generalDao;

	public GeneralDao getGeneralDao() {
		return generalDao;
	}

	public void setGeneralDao(GeneralDao generalDao) {
		this.generalDao = generalDao;
	}

	public FrequentlyAskedQuestions add(
			FrequentlyAskedQuestions frequentlyAskedQuestions)
			throws EntityException {
		frequentlyAskedQuestions = (FrequentlyAskedQuestions) this.getGeneralDao().save(frequentlyAskedQuestions);
		return frequentlyAskedQuestions;
	}
	
}

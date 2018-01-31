package com.whaty.platform.entity.service.basic;

import com.whaty.platform.entity.bean.FrequentlyAskedQuestions;
import com.whaty.platform.entity.exception.EntityException;

public interface FrequentlyAskedQuestionsService {
	public FrequentlyAskedQuestions add(FrequentlyAskedQuestions frequentlyAskedQuestions) throws EntityException;
}

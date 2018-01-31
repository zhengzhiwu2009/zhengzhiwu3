package com.whaty.platform.quartz;

import java.io.Serializable;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzTrainingJob extends QuartzJobBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
	}
}

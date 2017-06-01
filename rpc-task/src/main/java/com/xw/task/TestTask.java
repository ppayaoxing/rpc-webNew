package com.xw.task;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xw.util.DateUtils;


/**
 * 测试任务
 * @author Administrator
 */
public class TestTask implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {	
		System.out.println("biz_code:"+context.getJobDetail().getJobDataMap().get("biz_code")+"---TestTask---"+DateUtils.getCurrentDate());
	}

}

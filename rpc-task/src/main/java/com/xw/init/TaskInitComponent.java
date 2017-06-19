package com.xw.init;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.InitializingBean;

import com.xw.common.QuartzScheduleMgr;
import com.xw.model.TaskData;
import com.xw.server.TaskDataService;

/**
 * 任务初始化 
 * @author Administrator
 */
@SuppressWarnings("unchecked")
public class TaskInitComponent implements InitializingBean {

	@Resource
	private TaskDataService taskDataService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		List<TaskData> list = taskDataService.list();
		Scheduler scheduler = QuartzScheduleMgr.getInstanceScheduler();  
		for(TaskData task:list){
			Class <? extends Job> jobClass = (Class<? extends Job>) Class.forName(task.getFullclass());
			 JobDetail job = JobBuilder.newJob(jobClass).withIdentity(task.getName(), task.getGroupname())
					 .usingJobData("biz_code",task.getBizCode()).build();  
			 Trigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getTriggername(), task.getGroupname())
					 	.startNow()  
			            .withSchedule(CronScheduleBuilder.cronSchedule(task.getCorn()))  
			            .build(); 			 
			 scheduler.scheduleJob(job, trigger);//设置调度相关的Job  			  
		}
        scheduler.start();             
	} 

}

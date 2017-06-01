//package com.xw.task;
//
//import java.util.concurrent.TimeUnit;
//
//import org.quartz.CronScheduleBuilder;
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerFactory;
//import org.quartz.Trigger;
//import org.quartz.TriggerBuilder;
//import org.quartz.impl.StdSchedulerFactory;
//
//import com.xw.base.TestBase;
//
//public class TaskTest extends TestBase {
//	
//	public static void main(String[] args) throws Exception {
//		/* Scheduler scheduler = QuartzScheduleMgr.getInstanceScheduler();  
//		 JobDetail job = JobBuilder.newJob(TestTask.class).withIdentity("test", "group").build();  
//		 Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger", "group")
//				 	.startNow()  
//		            .withSchedule(CronScheduleBuilder.cronSchedule("0/5 0 0 * * ?"))  
//		            .build(); 			 
//		 scheduler.scheduleJob(job, trigger);//设置调度相关的Job  			  
//         scheduler.start();
//         System.out.println("---task init--end");
//         TimeUnit.MINUTES.sleep(1000);*/
//		
//		
//		//通过schedulerFactory获取一个调度器  
//	       SchedulerFactory schedulerfactory=new StdSchedulerFactory();  
//	       Scheduler scheduler=null;  
//	       try{  
////	      通过schedulerFactory获取一个调度器  
//	           scheduler=schedulerfactory.getScheduler();  
//	             
////	       创建jobDetail实例，绑定Job实现类  
////	       指明job的名称，所在组的名称，以及绑定job类  
//	           JobDetail job=JobBuilder.newJob(TestTask.class).withIdentity("job1", "jgroup1").build();  
//	           
//	             
////	       定义调度触发规则  
//	             
////	      使用simpleTrigger规则  
////	        Trigger trigger=TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "triggerGroup")  
////	                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withRepeatCount(8))  
////	                        .startNow().build();  
////	      使用cornTrigger规则  每天10点42分  
//	              Trigger trigger=TriggerBuilder.newTrigger().withIdentity("simpleTrigger", "triggerGroup")  
//	              .withSchedule(CronScheduleBuilder.cronSchedule("0/3 * * * * ?"))  
//	              .startNow().build();   
//	             
////	       把作业和触发器注册到任务调度中  
//	           scheduler.scheduleJob(job, trigger);  
//	             
////	       启动调度  
//	           scheduler.start();  
//	           
//	           System.out.println("---task init--end");
//	           TimeUnit.MINUTES.sleep(1000);
//	             
//	             
//	       }catch(Exception e){  
//	           e.printStackTrace();  
//	       }  
//		
//		
//	}
//
//}

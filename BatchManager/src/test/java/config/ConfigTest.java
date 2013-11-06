package config;

import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigTest {

  @Test
  public void configLoadingTest() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BatchConfiguration.class);
    String[] beanNames = ctx.getBeanDefinitionNames();
    for (String beanName : beanNames) {
      System.out.println("====================> " + beanName);
    }
    JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
    Job importUser = ctx.getBean("importUserJob", Job.class);
    JobParameters params = new JobParametersBuilder().toJobParameters();
    try {
      jobLauncher.run(importUser, params);
    }
    catch (JobExecutionAlreadyRunningException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (JobRestartException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (JobInstanceAlreadyCompleteException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (JobParametersInvalidException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}

package config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=BatchConfiguration.class)
public class ConfigTest {
  
  @Autowired
  ApplicationContext ctx;

  @Test
  public void configLoadingTest() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BatchConfiguration.class);
    printBeans(ctx);
    launchJob(ctx);
  }
  
  @Test
  public void xmlConfigLoading() {
    ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("/sample/config/batchConfig.xml");
    printBeans(ctx);
    launchJob(ctx);
  }

  private void printBeans(ApplicationContext ctx) {
    String[] beanNames = ctx.getBeanDefinitionNames();
    for (String beanName : beanNames) {
      System.out.println("====================> " + beanName);
    }
  }
  
  @Test
  public void hibernateJpaConfigLoading() {
    //AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BatchConfiguration.class);
    printBeans(ctx);
    launchJob(ctx);
  }

  private void launchJob(ApplicationContext ctx) {
    JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
    Job importUser = ctx.getBean("importUserJob", Job.class);
    JobParameters params = new JobParametersBuilder().addString("test", "testParam").toJobParameters();
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

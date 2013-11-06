package sample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import sample.item.Person;
import sample.repository.PersonRepository;
import config.BatchConfiguration;

public class SampleApplication {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BatchConfiguration.class);
    PersonRepository repo = ctx.getBean(PersonRepository.class);
    Iterable<Person> allPerson = repo.findAll();
    for (Person person : allPerson) {
      System.out.println("Firstname : "+person.getFirstname()+", Lastname : "+person.getLastname());
    }
    
    JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
    Job importUserJob = ctx.getBean("importUserJob", Job.class);
    JobParameters params = new JobParametersBuilder().toJobParameters();
    try {
      jobLauncher.run(importUserJob, params);
    } catch (JobExecutionAlreadyRunningException e) {
      e.printStackTrace();
    } catch (JobRestartException e) {
      e.printStackTrace();
    } catch (JobInstanceAlreadyCompleteException e) {
      e.printStackTrace();
    } catch (JobParametersInvalidException e) {
      e.printStackTrace();
    }
    
    allPerson = repo.findAll();
    for (Person person : allPerson) {
      System.out.println("Firstname : "+person.getFirstname()+", Lastname : "+person.getLastname());
    }
  }
}

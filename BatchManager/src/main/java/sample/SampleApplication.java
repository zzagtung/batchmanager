package sample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import sample.item.Person;
import sample.repository.PersonRepository;
import config.BatchConfiguration;
import config.CommonConfiguration;

public class SampleApplication {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CommonConfiguration.class, BatchConfiguration.class);
    PersonRepository repo = ctx.getBean(PersonRepository.class);
    Iterable<Person> allPerson = repo.findAll();
    for (Person person : allPerson) {
      System.out.println("Firstname : "+person.getFirstname()+", Lastname : "+person.getLastname());
    }
    
    Job importUserJob = ctx.getBean("importUserJob", Job.class);
    JobLauncher launcher = ctx.getBean(JobLauncher.class);
    try {
      launcher.run(importUserJob, null);
    } catch (JobExecutionAlreadyRunningException e) {
      e.printStackTrace();
    } catch (JobRestartException e) {
      e.printStackTrace();
    } catch (JobInstanceAlreadyCompleteException e) {
      e.printStackTrace();
    } catch (JobParametersInvalidException e) {
      e.printStackTrace();
    }
    
    JobRepository repository = ctx.getBean(JobRepository.class);
    //repository.getLastJobExecution("양평", "");
    
    allPerson = repo.findAll();
    for (Person person : allPerson) {
      System.out.println("Firstname : "+person.getFirstname()+", Lastname : "+person.getLastname());
    }
  }
}

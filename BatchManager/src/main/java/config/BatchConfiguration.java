package config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.MapExecutionContextDao;
import org.springframework.batch.core.repository.dao.MapJobExecutionDao;
import org.springframework.batch.core.repository.dao.MapJobInstanceDao;
import org.springframework.batch.core.repository.dao.MapStepExecutionDao;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import sample.item.Person;
import sample.processor.SampleItemProcessor;
import sample.reader.SampleReader;

@Configurable
@EnableBatchProcessing
public class BatchConfiguration {

  @Bean
  public ItemReader<Person> reader() {
    SampleReader reader = new SampleReader();
    return reader.getReader();
  }
  
  @Bean
  public ItemWriter<Person> writer(EntityManagerFactory entityManagerFactory) {
    JpaItemWriter<Person> writer = new JpaItemWriter<Person>();
    writer.setEntityManagerFactory(entityManagerFactory);
    //writer.write(items);
    return writer;
  }
  
  @Bean
  public Job importUserJob(JobBuilderFactory jobs, Step s1) {
    return jobs.get("importUserJob").incrementer(new RunIdIncrementer()).flow(s1).end().build();
  }

  @Bean
  public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader, ItemWriter<Person> writer,
      ItemProcessor<Person, Person> processor) {
    return stepBuilderFactory.get("step1").<Person, Person> chunk(10).reader(reader).processor(processor)
        .writer(writer).build();
  } 
  
  @Bean
  public ItemProcessor<Person, Person> processor() {
    return new SampleItemProcessor();
  }
  
  @Bean
  public JobLauncher lancher() {
    return new SimpleJobLauncher();
  }
  
  @Bean
  public JobRepository repository() {
    return new SimpleJobRepository(new MapJobInstanceDao(), new MapJobExecutionDao(), new MapStepExecutionDao(), new MapExecutionContextDao());
  }
}

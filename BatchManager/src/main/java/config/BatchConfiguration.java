package config;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import sample.item.Person;
import sample.processor.SampleItemProcessor;
import sample.reader.SampleReader;

@Configurable
@EnableBatchProcessing
@Import(CommonConfiguration.class)
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
  public Job importUserJob(JobBuilderFactory jobs, Step step1) {
    return jobs.get("importUserJob").incrementer(new RunIdIncrementer()).flow(step1).end().build();
  }

  @Bean
  public Step step1(StepBuilderFactory stepBuilderFactory, ItemWriter<Person> writer) {
    return stepBuilderFactory.get("step1").
        <Person, Person> chunk(10).
        reader(reader()).
        processor(processor())
        .writer(writer).build(); 
  } 
  
  @Bean
  public ItemProcessor<Person, Person> processor() {
    return new SampleItemProcessor();
  }
  
}

package sample.writer;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;

import sample.item.Person;

public class SampleWriter {

  public ItemWriter<Person> getWriter(EntityManagerFactory entityManagerFactory) {
    JpaItemWriter<Person> writer = new JpaItemWriter<Person>();
    writer.setEntityManagerFactory(entityManagerFactory);
    return writer;
  }
}

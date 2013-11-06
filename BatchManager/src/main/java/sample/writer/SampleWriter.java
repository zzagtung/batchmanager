package sample.writer;

import java.util.List;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.transaction.annotation.Transactional;

import sample.item.Person;

public class SampleWriter<T> extends JpaItemWriter<Person> {

  @Transactional
  public void write(List<? extends Person> items) {
    super.write(items);
  }
}

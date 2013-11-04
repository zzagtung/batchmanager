package sample.processor;

import org.springframework.batch.item.ItemProcessor;

import sample.item.Person;

public class SampleItemProcessor implements ItemProcessor<Person, Person> {

  @Override
  public Person process(Person item) throws Exception {
    Person transedPerson = new Person();
    transedPerson.setFirstname(item.getFirstname().toUpperCase());
    transedPerson.setLastname(item.getLastname().toUpperCase());
    return transedPerson;
  }

}

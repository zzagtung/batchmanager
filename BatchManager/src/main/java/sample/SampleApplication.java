package sample;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import config.BatchConfiguration;
import config.CommonConfiguration;
import sample.item.Person;
import sample.repository.PersonRepository;

public class SampleApplication {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CommonConfiguration.class, BatchConfiguration.class);
    PersonRepository repo = ctx.getBean(PersonRepository.class);
    Iterable<Person> allPerson = repo.findAll();
    for (Person person : allPerson) {
      System.out.println("Firstname : "+person.getFirstname()+", Lastname : "+person.getLastname());
    }
  }
}

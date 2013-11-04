package sample.repository;

import org.springframework.data.repository.CrudRepository;

import sample.item.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{
}

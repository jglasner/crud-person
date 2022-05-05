package com.radarsaude.crudperson.service;

import java.util.List;
import java.util.Optional;

import com.radarsaude.crudperson.entity.Person;

public interface PersonService {

	Person save(Person person);

	Person update(Person person);

	void delete(Person person);

	List<Person> find(Person personFiltro);

	void validate(Person person);

	Optional<Person> findById(Long id);

}

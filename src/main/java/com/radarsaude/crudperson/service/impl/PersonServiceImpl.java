package com.radarsaude.crudperson.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radarsaude.crudperson.entity.Person;
import com.radarsaude.crudperson.exception.RegraNegocioException;
import com.radarsaude.crudperson.repository.PersonRepository;
import com.radarsaude.crudperson.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {

	private PersonRepository repository;

	public PersonServiceImpl(PersonRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public Person save(Person person) {
		validate(person);
		return repository.save(person);
	}

	@Override
	@Transactional
	public Person update(Person person) {
		Objects.requireNonNull(person.getId());
		validate(person);
		return repository.save(person);
	}

	@Override
	@Transactional
	public void delete(Person person) {
		Objects.requireNonNull(person.getId());
		repository.delete(person);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Person> find(Person personFiltro) {
		Example<Person> example = Example.of(personFiltro,
				ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
		return repository.findAll(example);
	}

	@Override
	public void validate(Person person) {
		if (person.getName() == null || person.getName().trim().equals("")) {
			throw new RegraNegocioException("Informe um nome válido.");
		}

		if (person.getEmail() == null || person.getEmail().trim().equals("")) {
			throw new RegraNegocioException("Informe um e-mail válido.");
		}
	}

	@Override
	public Optional<Person> findById(Long id) {
		return repository.findById(id);
	}

}

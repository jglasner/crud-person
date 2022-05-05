package com.radarsaude.crudperson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radarsaude.crudperson.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}

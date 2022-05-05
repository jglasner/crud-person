package com.radarsaude.crudperson.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.radarsaude.crudperson.dto.PersonDTO;
import com.radarsaude.crudperson.entity.Person;
import com.radarsaude.crudperson.exception.RegraNegocioException;
import com.radarsaude.crudperson.service.PersonService;

@RestController
@RequestMapping("/api/person")
public class PersonController {

	@Autowired
	private PersonService service;

	@GetMapping
	public ResponseEntity find(@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "email", required = false) String email) {

		Person personFiltro = new Person();
		personFiltro.setName(name);
		personFiltro.setEmail(email);

		List<Person> persons = service.find(personFiltro);
		return ResponseEntity.ok(persons);
	}

	@GetMapping("{id}")
	public ResponseEntity findById(@PathVariable Long id) {
		return service.findById(id).map(person -> {
			return new ResponseEntity(converter(person), HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity("Pessoa não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}

	@PostMapping
	public ResponseEntity save(@RequestBody PersonDTO dto) {
		try {
			Person entidade = converter(dto);
			entidade = service.save(entidade);
			return new ResponseEntity<>(entidade, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity update(@PathVariable Long id, @RequestBody PersonDTO dto) {
		return service.findById(id).map(entity -> {
			try {
				Person person = converter(dto);
				person.setId(entity.getId());
				service.update(person);
				return ResponseEntity.ok(person);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> new ResponseEntity("Pessoa não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}

	@DeleteMapping("{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		return service.findById(id).map(entidade -> {
			service.delete(entidade);
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}).orElseGet(() -> new ResponseEntity("Pessoa não encontrado na base de dados.", HttpStatus.BAD_REQUEST));
	}

	private PersonDTO converter(Person person) {

		PersonDTO personDTO = new PersonDTO();

		personDTO.setId(person.getId());
		personDTO.setName(person.getName());
		personDTO.setSex(person.getSex());
		personDTO.setBirthday(person.getBirthday());
		personDTO.setPhone(person.getPhone());
		personDTO.setEmail(person.getEmail());

		return personDTO;

	}

	private Person converter(PersonDTO dto) {
		Person person = new Person();

		person.setId(dto.getId());
		person.setName(dto.getName());
		person.setSex(dto.getSex());
		person.setBirthday(dto.getBirthday());
		person.setPhone(dto.getPhone());
		person.setEmail(dto.getEmail());

		return person;
	}

}

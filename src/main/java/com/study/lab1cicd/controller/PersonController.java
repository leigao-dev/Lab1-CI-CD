package com.study.lab1cicd.controller;

import com.study.lab1cicd.entity.Person;
import com.study.lab1cicd.service.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/persons")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // 获取所有人的信息
    @GetMapping
    public List<Person> getAllPersons() {
        return personService.getAllPersons();
    }

    // 获取指定ID的人员信息
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable("id") Long id) {
        Optional<Person> person = personService.getPersonById(id);
        if (person.isPresent()) {
            return ResponseEntity.ok(person.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 创建新记录
    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody Person person) {
        Person createdPerson = personService.createPerson(person);
        return ResponseEntity
                .created(URI.create("/api/v1/persons/" + createdPerson.getId()))
                .build();
    }

    // 更新现有记录
    @PatchMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody Person person) {
        Optional<Person> updatedPerson = personService.updatePerson(id, person);
        return updatedPerson.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 删除指定ID的记录
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable("id") Long id) {
        boolean isDeleted = personService.deletePerson(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
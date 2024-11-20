package com.study.lab1cicd.service;

import com.study.lab1cicd.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
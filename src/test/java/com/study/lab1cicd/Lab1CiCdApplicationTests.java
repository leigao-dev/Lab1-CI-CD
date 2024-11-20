package com.study.lab1cicd;


import com.study.lab1cicd.controller.PersonController;
import com.study.lab1cicd.entity.Person;
import com.study.lab1cicd.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.hamcrest.Matchers.matchesRegex;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class Lab1CiCdApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private PersonService personService;

	@InjectMocks
	private PersonController personController;

	private Person person;

	@BeforeEach
	void setUp() {
		person = new Person();
		person.setId(1L);
		person.setName("John Doe");
		person.setAge(30);

	}

	@Test
	void testGetPersonById_NotFound() throws Exception {
		when(personService.getPersonById(1L)).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/v1/persons/{id}", 1L))
				.andExpect(status().isNotFound());
	}

	@Test
	void testCreatePerson() throws Exception {


		// 执行请求并验证
		mockMvc.perform(post("/api/v1/persons")
						.contentType("application/json")
						.content("{ \"name\": \"John Doe\", \"age\": 30}"))
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", matchesRegex("/api/v1/persons/\\d+")));  // 使用正则匹配 Location 头部的 URL
	}

	@Test
	void testUpdatePerson_NotFound() throws Exception {
		when(personService.updatePerson(1L, person)).thenReturn(Optional.empty());

		mockMvc.perform(patch("/api/v1/persons/{id}", 1L)
						.contentType("application/json")
						.content("{\"name\": \"Updated Name\", \"age\": 35}"))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeletePerson_NotFound() throws Exception {
		when(personService.deletePerson(1L)).thenReturn(false);

		mockMvc.perform(delete("/api/v1/persons/{id}", 1L))
				.andExpect(status().isNotFound());
	}

	@Test
	void testGetAllPersons() throws Exception {

		when(personService.getAllPersons()).thenReturn(List.of(person));


		mockMvc.perform(get("/api/v1/persons"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("John Doe"));
	}
}

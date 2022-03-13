package com.training;

import com.training.controller.CrudController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TodoAppTests {

	@Autowired
	private CrudController crudController;

	@Autowired
	private MockMvc mvc;

	@Test
	void contextLoads() {
		assertThat(crudController).isNotNull();
	}

	@Test
	public void getTasksView() throws Exception
	{
		mvc.perform( MockMvcRequestBuilders
				.get("/tasks")
				.accept(MediaType.TEXT_HTML))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.model().attribute("tasks", new ArrayList<>()))
				.andExpect(MockMvcResultMatchers.view().name("tasks"));
	}

	@Test
	public void getAllTasks() throws Exception
	{
		mvc.perform( MockMvcRequestBuilders
				.get("/tasks/all")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("[]"));
	}

}

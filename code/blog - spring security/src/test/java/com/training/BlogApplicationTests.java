package com.training;

import com.training.controller.BlogController;
import org.json.JSONObject;
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
class BlogApplicationTests {

    @Autowired
    private BlogController blogController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertThat(blogController).isNotNull();
    }

    @Test
    public void getBlogPosts() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/posts")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.content().json("[]"));


    }


    @Test
    public void getHomePosts() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/")
                        .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
//                .andExpect(MockMvcResultMatchers.model().attribute("posts", new ArrayList<>()))
                .andExpect(MockMvcResultMatchers.view().name("home")
        );


    }


}

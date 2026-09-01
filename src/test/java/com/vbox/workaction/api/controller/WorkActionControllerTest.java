package com.vbox.workaction.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vbox.workaction.api.dto.CreateWorkActionRequest;
import com.vbox.workaction.domain.model.WorkAction.Priority;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class WorkActionControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createsAndSearchesWorkAction() throws Exception {
        var body = new CreateWorkActionRequest("DISC-1001", "Review disclosure", "Validate fields", Priority.HIGH, LocalDate.now().plusDays(3), "team-a");
        mvc.perform(post("/api/work-actions/v1").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(body))).andExpect(status().isOk()).andExpect(jsonPath("$.data.status").value("OPEN"));
        mvc.perform(get("/api/work-actions/v1/search").param("refId", "DISC-1001")).andExpect(status().isOk()).andExpect(jsonPath("$.data.content[0].title").value("Review disclosure"));
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        ObjectMapper objectMapper() {
            return JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
        }

    }
}

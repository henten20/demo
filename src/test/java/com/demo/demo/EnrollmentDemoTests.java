package com.demo.demo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * User Enrollment tests
 * 
 * @author Henry Ton
 * @since 0.0.1
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class EnrollmentDemoTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private List<String> row1;
    private List<String> row2;
    private List<List<String>> file;

    @BeforeEach
    void init() {
        row1 = new ArrayList<>();
        row1.add("25b28034-2b7b-4012-8f58-201252944713");
        row1.add("John Doe");
        row1.add("23");
        row1.add("Geico");

        row2 = new ArrayList<>();
        row2.add("25b28034-2b7b-4012-8f58-201252944714");
        row2.add("Jason Flake");
        row2.add("19");
        row2.add("State Farm");

        file = new ArrayList<>();
        file.add(row1);
        file.add(row2);
    }
    
	@Test
	void contextLoads() {

	}

    @Test
    public void enrollWithfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
			.post("/enroll")
			.contentType("application/json")
            .content(objectMapper.writeValueAsString(file)))
			.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].insurance").value("Geico"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].customers[0].firstAndLastName").value("John Doe"))
            .andExpect(MockMvcResultMatchers.jsonPath("$[1].insurance").value("State Farm"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].customers[0].firstAndLastName").value("Jason Flake"));
    }
}

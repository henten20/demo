package com.demo.demo;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class ParserDemoTests {
	@Autowired
	LispParser lispParser;
	@Autowired
	MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;

    private String lisp1 = "()";
    private String lisp2 = "())";
    private String lisp3 = "(defun csg-intersection-intersect-all (obj-a obj-b) " 
        + "(lambda (ray) " 
        + "(flet ((inside-p (obj) (lambda (d) (inside-p obj (ray-point ray d)))))" 
        + "(merge 'fvector" 
        + "     (remove-if-not (inside-p obj-b) (intersect-all obj-a ray))" 
        + "       (remove-if-not (inside-p obj-a) (intersect-all obj-b ray)) "
        + "      #'<))))";

    private String lisp4 = "(defun csg-intersection-intersect-all (obj-a obj-b) " 
        + "(lambda (ray) " 
        + "(flet ((inside-p (obj) (lambda (d) (inside-p obj (ray-point ray d)))))" 
        + "(merge 'fvector" 
        + "     (remove-if-not (inside-p obj-b) (intersect-all obj-a ray))" 
        + "       (remove-if-not (inside-p obj-a) (intersect-all obj-b ray)) "
        + "      #'<)))";
        
	@Test
    void parserPassesTest1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
			.post("/parse")
			.contentType("application/json")
			.param("lisp", lisp1))
			.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    void parserPassesTest2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
			.post("/parse")
			.contentType("application/json")
			.param("lisp", lisp2))
			.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    void parserPassesTest3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
			.post("/parse")
			.contentType("application/json")
			.param("lisp", lisp3))
			.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    void parserPassesTest4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
			.post("/parse")
			.contentType("application/json")
			.param("lisp", lisp4))
			.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("false"));
    }
}

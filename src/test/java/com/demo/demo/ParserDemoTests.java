package com.demo.demo;

import com.demo.demo.rest.LispParserService;

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
 * LISP Parser tests
 * 
 * @author Henry Ton
 * @since 0.0.1
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class ParserDemoTests {
	@Autowired
	private MockMvc mockMvc;

    private String lisp0 = "";
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
	void contextLoads() {

	}

    @Test
    void parserTest0() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
			.post("/parse")
			.contentType("application/json")
			.param("lisp", lisp0))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    
	@Test
    void parserTest1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
			.post("/parse")
			.contentType("application/json")
			.param("lisp", lisp1))
			.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    void parserTest2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
			.post("/parse")
			.contentType("application/json")
			.param("lisp", lisp2))
			.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("false"));
    }

    @Test
    void parserTest3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
			.post("/parse")
			.contentType("application/json")
			.param("lisp", lisp3))
			.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    void parserTest4() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
			.post("/parse")
			.contentType("application/json")
			.param("lisp", lisp4))
			.andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("false"));
    }
}

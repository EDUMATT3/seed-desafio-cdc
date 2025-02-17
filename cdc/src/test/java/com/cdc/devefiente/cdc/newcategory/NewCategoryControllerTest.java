package com.cdc.devefiente.cdc.newcategory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.cdc.devefiente.cdc.common.CustomMockMvc;

import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
public class NewCategoryControllerTest {
    
    @Autowired
    private CustomMockMvc mvc;
    private  Set<String> generatedNames = new HashSet<>();

    @Property(tries = 10)
    @Label("fluxo de cadastro de nova categoria")
    void teste1(@ForAll @AlphaChars @StringLength(min = 1, max = 255) String name) throws Exception {
        Assumptions.assumeTrue(generatedNames.add(name));

        mvc.post("/categories", Map.of("name", name))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        mvc.post("/categories", Map.of("name", name))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}

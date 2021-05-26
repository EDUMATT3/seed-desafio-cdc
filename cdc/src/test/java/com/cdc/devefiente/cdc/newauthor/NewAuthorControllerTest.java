package com.cdc.devefiente.cdc.newauthor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
public class NewAuthorControllerTest {

    @Autowired
    private MockMvc mvc;
    //o jqwik n pode gerar valores repetidos, oque pode nos dar um falso negativo
    private static Set<String> generatedEmails = new HashSet<>();

    @Property(tries = 10)
    @Label("fluxo de cadastro de novo autor")
    void test1(@ForAll @AlphaChars @StringLength(min = 1, max = 255) String name,
        @ForAll @AlphaChars @StringLength(min = 1, max = 50) String email,
        @ForAll @AlphaChars @StringLength(min = 1, max = 255) String description) throws Exception {

            //para n repetir emails
            Assumptions.assumeTrue(generatedEmails.add(email));

            String payload = new ObjectMapper()
                .writeValueAsString(
                    Map.of("name", name, "email", email+"@gmail.com", "description", description)
                );
            
            MockHttpServletRequestBuilder content = MockMvcRequestBuilders.post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);
            mvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
            
            mvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        }
}

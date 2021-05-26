package com.cdc.devefiente.cdc.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.cdc.devefiente.cdc.common.CustomMockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class BookDetailsController {

    @Autowired
    private CustomMockMvc mvc;

    @Test
    @DisplayName("exibe detalhes do livro corretamente")
    void test1() throws Exception  {

        mvc.post("/authors", Map.of("name", "eduardo", "email", "teste@gmail.com", "description", "anydescription")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());;
        mvc.post("/categories", Map.of("name", "category")).andExpect(MockMvcResultMatchers.status().is2xxSuccessful());;

        String title = "title";
        String resume = "resume";
        String summary = "summary";
        BigDecimal price = new BigDecimal("20");
        int pageNumber = 101;
        String isbn = "23524325t34";
        String publicationDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        mvc.post("/books", Map.of(
            "title", title,
            "resume", resume,
            "summary", summary,
            "price", price.toString(),
            "pageNumber", String.valueOf(pageNumber),
            "isbn", isbn,
            "publicationDate", publicationDate,
            "categoryId", "1",
            "authorId", "1"))
            .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
        
        
        ResultActions result = mvc.get("/books/1");

        Map<String, Object> author =  Map.of("name", "eduardo", "description", "anydescription");

        Map<String, Object> bookDetails = Map.of("title", title, "resume", resume, "summary", summary, "price", price.setScale(2),
        "pageNumber", String.valueOf(pageNumber), "isbn", isbn, "publicationDate", publicationDate, "author", author);

        String expectedJson = new ObjectMapper().writeValueAsString(bookDetails);

        result.andExpect(MockMvcResultMatchers.content().json(expectedJson));
    }

}

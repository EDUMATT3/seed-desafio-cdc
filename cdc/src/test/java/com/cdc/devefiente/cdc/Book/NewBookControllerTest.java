package com.cdc.devefiente.cdc.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.cdc.devefiente.cdc.common.CustomMockMvc;

import org.junit.jupiter.api.Assumptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.constraints.AlphaChars;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.NumericChars;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.spring.JqwikSpringSupport;
import net.jqwik.time.api.Dates;

@JqwikSpringSupport
@SpringBootTest
@AutoConfigureMockMvc
public class NewBookControllerTest {

	@Autowired
	private CustomMockMvc mvc;
	private Set<String> uniqueValues = new HashSet<>();
	
	@Property(tries = 20)
	@Label("fluxo de cadastro de novo livro")
	@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
	public void test(@ForAll @AlphaChars @StringLength(min = 1, max = 255) String title,
        @ForAll @AlphaChars @StringLength(min = 1, max = 500) String resume,
        @ForAll @AlphaChars @StringLength(min = 1, max = 255) String summary,
        @ForAll @BigRange(min = "20",max = "100") BigDecimal price,
        @ForAll @IntRange(min = 100) int pageNumber,
        @ForAll @NumericChars @StringLength(min = 10, max = 10) String isbn,
        @ForAll("futureDates") LocalDate publiblicationDate) throws Exception {
		
		Assumptions.assumeTrue(uniqueValues.add(title));
		Assumptions.assumeTrue(uniqueValues.add(isbn));
		
		//vai salvar a primeira vez e depois bloquear por conta da validacao de unicidade
		mvc.post("/authors", Map.of("name","eduardo","email","email@email.com","description","test"));
		mvc.post("/categories", Map.of("name","test"));
		
		String formattedPubliblicationDate = publiblicationDate
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		
		mvc.post("/books",Map.of("title",title,
            "resume",resume,
            "summary",summary,
            "price",price.toString(),
            "pageNumber",String.valueOf(pageNumber),
            "isbn",isbn,
            "publicationDate",formattedPubliblicationDate,
            "categoryId","1",
            "authorId","1"))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
		mvc.post("/books",Map.of("title",title,
            "resume",resume,
            "summary",summary,
            "price",price.toString(),
            "pageNumber",String.valueOf(pageNumber),
            "isbn",isbn,
            "publiblicationDate",formattedPubliblicationDate,
            "categoryId","1",
            "authorId","1"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Provide
	Arbitrary<LocalDate> futureDates() {
	    return Dates.dates().atTheEarliest(LocalDate.now().plusDays(1));
	}

}
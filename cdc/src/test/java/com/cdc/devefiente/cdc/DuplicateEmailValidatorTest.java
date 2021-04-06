// package com.cdc.devefiente.cdc;

// import static org.mockito.Mockito.doThrow;

// import com.cdc.devefiente.cdc.newAuthor.AuthorRepository;
// import com.cdc.devefiente.cdc.newAuthor.DuplicateEmailValidator;
// import com.cdc.devefiente.cdc.newAuthor.NewAuthorForm;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.validation.Errors;
// import org.springframework.web.bind.MethodArgumentNotValidException;

// @SpringBootTest
// public class DuplicateEmailValidatorTest {

//     @MockBean
//     AuthorRepository AuthorRepository;

//     @InjectMocks
//     DuplicateEmailValidator duplicateEmailValidator;

//     @Test
//     @DisplayName("dado um formulario de novo autor deve validar se o email está duplicado")
//     public void deveriaValidarEmail(){
//         //Arrange
//         NewAuthorForm newAuthorForm = new NewAuthorForm("George Orwell", "contato@orwell", "some description...");

//         duplicateEmailValidator.validate(newAuthorForm, Errors.);

//         Assert
//     }
// }

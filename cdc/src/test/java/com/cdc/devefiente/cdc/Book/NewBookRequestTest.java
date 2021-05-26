package com.cdc.devefiente.cdc.Book;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import com.cdc.devefiente.cdc.book.NewBookRequest;
import com.cdc.devefiente.cdc.category.Category;
import com.cdc.devefiente.cdc.newAuthor.Author;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NewBookRequestTest {
    
    private NewBookRequest request = new NewBookRequest("", "", "", BigDecimal.TEN, 100, "", LocalDate.now(), 1l, 1l);

    @Test
    @DisplayName("Cria o livro quando categoria e autor estão cadastrados")
    void test1() throws Exception{

        //parece que java tem um bangui para mock do manager
        EntityManager manager = mock(EntityManager.class);

        when(manager.find(Category.class, 1l))
            .thenReturn(new Category(""));
        
        when(manager.find(Author.class, 1l))
            .thenReturn(new Author("", "", ""));

        Assertions.assertNotNull(request.toModel(manager));
    }

    @Test
    @DisplayName("Não cria o livro caso o autor não exista")
    void test2() throws Exception{

        EntityManager manager = mock(EntityManager.class);

        when(manager.find(Category.class, 1l))
            .thenReturn(new Category(""));
        
        when(manager.find(Author.class, 1l))
            .thenReturn(null);

        Assertions.assertThrows(IllegalStateException.class, () -> {
            request.toModel(manager);
        });
    }

    @Test
    @DisplayName("Não cria o livro caso a categoria não exista")
    void test3() throws Exception{

        EntityManager manager = mock(EntityManager.class);

        when(manager.find(Category.class, 1l))
            .thenReturn(null);
        
        when(manager.find(Author.class, 1l))
            .thenReturn(new Author("", "", ""));

        Assertions.assertThrows(IllegalStateException.class, () -> {
            request.toModel(manager);
        });
    }
}

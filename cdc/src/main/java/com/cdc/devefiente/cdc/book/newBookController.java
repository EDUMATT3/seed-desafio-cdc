package com.cdc.devefiente.cdc.book;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import com.cdc.devefiente.cdc.common.ExistsId;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//5
@RestController
@RequestMapping("/books")
@Validated
public class newBookController {

    @PersistenceContext
    EntityManager entityManager;

    @GetMapping
    public List<BookResponse> list(){
        return entityManager.createQuery("from Book", Book.class)
            .getResultList()
            .stream()
            .map(BookResponse::new)
            .collect(Collectors.toList());
    }

    //refactor Response dto
    //status 404 ConstraintViolationException
    @GetMapping("/{id}")
    public BookDetailsResponse details(@PathVariable @ExistsId(domainClass = Book.class, message = "Livro não encontrado") Long id){
        Book book = entityManager.find(Book.class, id);
        return new BookDetailsResponse(book);
    }
    
    @PostMapping
    @Transactional
    public ResponseEntity<?> createBook(@Valid @RequestBody NewBookRequest form){
        Book newBook = form.toModel(entityManager);
        entityManager.persist(newBook);
        return ResponseEntity.created(URI.create("/books/"+newBook.getId())).build();
    }
}

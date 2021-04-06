package com.cdc.devefiente.cdc.newAuthor;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//3
@RestController
@RequestMapping("/authors")
public class AuthorController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    DuplicateEmailValidator duplicateEmailValidator;

    @InitBinder
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(duplicateEmailValidator);
    }

    @GetMapping
    public List<Author> listAuthors(){
        return entityManager.createQuery("from Author").getResultList();
    }
    
    @Transactional
    @PostMapping
    public ResponseEntity<?> newAuthor(@Valid @RequestBody NewAuthorRequest newAuthorForm){
        Author newAuthor = newAuthorForm.toModel();
        entityManager.persist(newAuthor);
        return ResponseEntity.created(URI.create("/authors/"+newAuthor.getId())).build();
    }
}

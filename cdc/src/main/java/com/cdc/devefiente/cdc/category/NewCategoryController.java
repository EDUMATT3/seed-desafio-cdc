package com.cdc.devefiente.cdc.category;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//2
@RestController
@RequestMapping("/categories")
public class NewCategoryController {
    
    @PersistenceContext
    EntityManager entityManager;
    
    @PostMapping
    @Transactional
    public ResponseEntity<?> createCategory(@Valid @RequestBody NewCategoryRequest form){
        Category newCategory = form.toModel();
        entityManager.persist(newCategory);
        return ResponseEntity.created(URI.create("/categories/"+ newCategory.getId())).build();
    }
}

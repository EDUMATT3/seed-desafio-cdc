package com.cdc.devefiente.cdc.newPais;

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

@RestController
@RequestMapping("/countries")
public class NewCountryController {
    
    @PersistenceContext
    EntityManager entityManager;

    @PostMapping
    @Transactional
    public ResponseEntity<?> newPais(@Valid @RequestBody NewPaisRequest newPaisRequest){
        Country newCountry = newPaisRequest.toModel();
        entityManager.persist(newCountry);
        return ResponseEntity.created(URI.create("/countries/"+newCountry.getId())).build();
    }
}

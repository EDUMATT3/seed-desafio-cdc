package com.cdc.devefiente.cdc.newState;

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
@RequestMapping("/states")
public class NewStateController {
    
    @PersistenceContext
    EntityManager entityManager;

    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody newStateRequest newStateRequest){
        State newCountry = newStateRequest.toModel(entityManager);
        entityManager.persist(newCountry);
        return ResponseEntity.created(URI.create("/states/"+newCountry.getId())).build();
    }

}

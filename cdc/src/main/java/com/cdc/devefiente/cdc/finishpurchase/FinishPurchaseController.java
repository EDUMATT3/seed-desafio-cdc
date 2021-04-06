package com.cdc.devefiente.cdc.finishpurchase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/purchases")
public class FinishPurchaseController {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    StateBelongsToCountryValidator stateBelongsToCountryValidator;

    @InitBinder
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(stateBelongsToCountryValidator);
    }
    
    @PostMapping
    @Transactional
    public String finish(@Valid @RequestBody FinishPurchaseRequest request) {
        //se eu coloquei um país que tem estado então estado deve ser preenchido
        Purchase newPurchase = request.toModel(entityManager);
        entityManager.persist(newPurchase);
        return newPurchase.toString();
    }
}

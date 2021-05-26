package com.cdc.devefiente.cdc.newCoupon;

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
@RequestMapping("/coupons")
public class NewCouponController {

    @PersistenceContext
    EntityManager entityManager;
    
    @PostMapping
    @Transactional
    public ResponseEntity<?> save(@Valid @RequestBody NewCouponRequest request){
        Coupon newCoupon = request.toModel();
        entityManager.persist(newCoupon);
        return ResponseEntity.created(URI.create("/coupons/"+newCoupon.getId())).build();
    }

}

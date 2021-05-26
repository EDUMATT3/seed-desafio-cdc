package com.cdc.devefiente.cdc.finishpurchase;

import java.util.Optional;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cdc.devefiente.cdc.common.ExistsId;
import com.cdc.devefiente.cdc.newCoupon.Coupon;
import com.cdc.devefiente.cdc.newPais.Country;
import com.cdc.devefiente.cdc.newState.State;

import org.springframework.util.StringUtils;
public class FinishPurchaseRequest {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String lastName;
    @NotBlank
    @Document
    private String document;
    @NotBlank
    private String address;
    @NotBlank
    private String complement;
    @NotBlank
    private String city;
    @NotNull
    @ExistsId(domainClass = Country.class)
    private Long countryId;
    @ExistsId(domainClass = State.class)
    private Long stateId;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String cep;
    @Valid
    @NotNull
    private NewOrderRequest order;

    @ExistsId(domainClass = Coupon.class,fieldName = "code")
    private String couponCode;

    public FinishPurchaseRequest(@Email @NotBlank String email, @NotBlank String name, @NotBlank String lastName,
            @NotBlank @Document String document, @NotBlank String address, @NotBlank String complement,
            @NotBlank String city, @NotNull Long countryId, @NotBlank String phoneNumber,
            @NotBlank String cep, @Valid @NotNull NewOrderRequest order) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.document = document;
        this.address = address;
        this.complement = complement;
        this.city = city;
        this.countryId = countryId;
        this.phoneNumber = phoneNumber;
        this.cep = cep;
        this.order = order;
    }

    //não esta no construtor pois não é obrigatório
    public void setStateId(Long stateId){
        this.stateId = stateId;
    }

    public void setCouponCode(String couponCode){
        this.couponCode = couponCode;
    }

    public NewOrderRequest getOrder(){
        return order;
    }

    public Purchase toModel(EntityManager entityManager, CouponRepository couponRepository) {

        @NotNull Country country = entityManager.find(Country.class, countryId);
        
        Function<Purchase, Order> orderCreatFunction = order.toModel(entityManager);

        Purchase purchase = new Purchase(email, name, lastName, document, address, complement, country, phoneNumber, cep, orderCreatFunction);

        if(stateId != null){
            purchase.setState(entityManager.find(State.class, stateId));
        }

        if(StringUtils.hasText(couponCode)){
            Coupon coupon = couponRepository.getByCode(couponCode);
            purchase.applyCoupon(coupon);
        }

        return purchase;
    }  

    public boolean hasState() {
        return Optional.ofNullable(stateId).isPresent();
    }

    public Long getCountryId() {
        return countryId;
    }

    public Long getStateId() {
        return stateId;
    }

    public Optional<String> getCouponCode() {
        return Optional.ofNullable(couponCode);
    }  
}

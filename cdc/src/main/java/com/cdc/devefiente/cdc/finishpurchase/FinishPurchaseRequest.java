package com.cdc.devefiente.cdc.finishpurchase;

import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cdc.devefiente.cdc.common.ExistsId;
import com.cdc.devefiente.cdc.newPais.Country;
import com.cdc.devefiente.cdc.newState.State;

import lombok.Getter;

@Getter
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

    public FinishPurchaseRequest(@Email @NotBlank String email, @NotBlank String name, @NotBlank String lastName,
            @NotBlank @Document String document, @NotBlank String address, @NotBlank String complement,
            @NotBlank String city, @NotNull Long countryId, @NotNull Long stateId, @NotBlank String phoneNumber,
            @NotBlank String cep, @Valid @NotNull NewOrderRequest order) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.document = document;
        this.address = address;
        this.complement = complement;
        this.city = city;
        this.countryId = countryId;
        this.stateId = stateId;
        this.phoneNumber = phoneNumber;
        this.cep = cep;
        this.order = order;
    }

    public NewOrderRequest getOrder(){
        return order;
    }

    public Purchase toModel(EntityManager entityManager) {

        @NotNull Country country = entityManager.find(Country.class, countryId);
        
        Function<Purchase, Order> orderCreatFunction = order.toModel(entityManager);

        Purchase purchase = new Purchase(email, name, lastName, document, address, complement, country, phoneNumber, cep, orderCreatFunction);

        if(stateId != null){
            purchase.setState(entityManager.find(State.class, stateId));
        }

        return purchase;
    }  

    @Override
    public String toString() {
        return "{" +
            " email='" + getEmail() + "'" +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", document='" + getDocument() + "'" +
            ", address='" + getAddress() + "'" +
            ", complement='" + getComplement() + "'" +
            ", city='" + getCity() + "'" +
            ", countryId='" + getCountryId() + "'" +
            ", stateId='" + getStateId() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", cep='" + getCep() + "'" +
            ", order='" + getOrder() + "'" +
            "}";
    }

    public boolean hasState() {
        return stateId!=null;
    }  
}

package com.cdc.devefiente.cdc.newState;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cdc.devefiente.cdc.common.ExistsId;
import com.cdc.devefiente.cdc.common.UniqueValue;
import com.cdc.devefiente.cdc.newPais.Country;

import org.springframework.util.Assert;

import lombok.Getter;

@Getter
public class newStateRequest {

    @NotBlank
    @UniqueValue(domainClass = State.class, fieldName = "name")
    private String name;

    @NotNull
    @ExistsId(domainClass = Country.class)
    private Long countryId;

    public State toModel(EntityManager entityManager) {
        
        Country country = entityManager.find(Country.class, countryId);
        Assert.state(Objects.nonNull(country), "País não encontrado.");

        return new State(this.name, country);
    }
}

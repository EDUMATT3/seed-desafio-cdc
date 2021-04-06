package com.cdc.devefiente.cdc.newPais;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

import com.cdc.devefiente.cdc.common.UniqueValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.util.Assert;

import lombok.Getter;

@Getter
public class NewPaisRequest {

    @NotBlank
    @UniqueValue(domainClass = Country.class, fieldName = "name")
    private String name;

    @JsonCreator(mode = Mode.PROPERTIES)
    public NewPaisRequest(@NotBlank String name) {
        this.name = name;
    }

    public Country toModel() {
        Assert.state(Objects.nonNull(this.name), "Nome inválido!");

        return new Country(this.name);
    }

}

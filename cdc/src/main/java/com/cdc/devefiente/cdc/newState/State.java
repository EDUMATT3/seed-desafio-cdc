package com.cdc.devefiente.cdc.newState;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cdc.devefiente.cdc.newPais.Country;


@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @ManyToOne
    private Country country;

    @Deprecated
    public State(){}
    
    public State(@NotBlank String name, @NotNull Country country) {
        this.name = name;
        this.country = country;
    }

    public Long getId() {
        return this.id;
    }

    public boolean belongsToCountry(Country country) {
        return this.country.equals(country);
    }
}

package com.cdc.devefiente.cdc.finishpurchase;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.cdc.devefiente.cdc.newPais.Country;
import com.cdc.devefiente.cdc.newState.State;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class StateBelongsToCountryValidator implements Validator {

    private EntityManager entityManager;

    public StateBelongsToCountryValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FinishPurchaseRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        FinishPurchaseRequest request = (FinishPurchaseRequest) target;

        if(request.hasState()){
            Country country = entityManager.find(Country.class, request.getCountryId());
            State state = entityManager.find(State.class, request.getStateId());

            if (!state.belongsToCountry(country)) {
                errors.rejectValue("stateId", null, "O statusId: "+request.getStateId()+" não pertence ao pais de id: "+ request.getCountryId());
            }
        }
    }
}

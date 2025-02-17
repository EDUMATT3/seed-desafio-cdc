package com.cdc.devefiente.cdc.common;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistsIdValidator implements ConstraintValidator<ExistsId, Object>{

    private String domainAttribute;
    private Class<?> klass;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(ExistsId params){
        domainAttribute = params.fieldName();
        klass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context){
        Query query = entityManager.createQuery("select 1 from "+klass.getName()+" where "+domainAttribute+"=:value");

        query.setParameter("value", value);
        List<?> list = query.getResultList();

        //Assert.state(list, "Foi encontrado mais de um "+klass+" com o atributo "+domainAttribute+" = "+value);
        
        return !list.isEmpty();
    }
}
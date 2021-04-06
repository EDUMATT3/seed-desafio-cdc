package com.cdc.devefiente.cdc.newAuthor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//2
@Component
public class DuplicateEmailValidator implements Validator{

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public boolean supports(Class<?> clazz){
        return NewAuthorRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        if(errors.hasErrors()){
            return;
        }

        NewAuthorRequest form = (NewAuthorRequest) target;
        Optional<Author> optionalAuthor = authorRepository.findByEmail(form.getEmail());

        if(optionalAuthor.isPresent()){
            errors.rejectValue("email", null, "Already exists an Author with the email "+form.getEmail());
        }
    }
}

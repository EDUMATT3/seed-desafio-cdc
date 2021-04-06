package com.cdc.devefiente.cdc.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationErrorHandler {

    @Autowired
    private MessageSource messageSource;
    
    /** 
     * Para aplicar uma validação para toda a aplicação
     * @InitBinder
     * public void initBinder(WebDataBinder binder)
    */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorsResponseDTO handleValidationError(MethodArgumentNotValidException exception){
        List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        return buildValidationErrors(globalErrors, fieldErrors);
    }

    // @ExceptionHandler(ConstraintViolationException.class)
    // public ValidationErrorsResponseDTO handleValidationError(ConstraintViolationException exception){
    //     exception.getMessage();
    //     List<ObjectError> globalErrors = exception.getBindingResult().getGlobalErrors();
    //     List<FieldError> fieldErrors = exception.g;

    //     return buildValidationErrors(globalErrors, fieldErrors);
    // }

    private ValidationErrorsResponseDTO buildValidationErrors(List<ObjectError> globalErrors,
            List<FieldError> fieldErrors) {
        ValidationErrorsResponseDTO validationErrors = new ValidationErrorsResponseDTO();
        globalErrors.forEach(error -> validationErrors.addError(getErrorMessage(error)));

        fieldErrors.forEach(error -> {
            String errorMessage = getErrorMessage(error);
            validationErrors.addFieldError(error.getField(), errorMessage);
        });

        return validationErrors;
    }

    private String getErrorMessage(ObjectError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }
}

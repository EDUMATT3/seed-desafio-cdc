package com.cdc.devefiente.cdc.common;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationErrorsResponseDTO {
    private List<String> globalErrorMessages = new ArrayList<>();
    private List<FieldErrorResponseDTO> fieldErrors = new ArrayList<>();

    public void addError(String Message){
        globalErrorMessages.add(Message);
    }

    public void addFieldError(String field, String message){
        fieldErrors.add(new FieldErrorResponseDTO(field, message));
    }
}

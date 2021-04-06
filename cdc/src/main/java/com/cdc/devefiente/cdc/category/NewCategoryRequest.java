package com.cdc.devefiente.cdc.category;

import javax.validation.constraints.NotBlank;

import com.cdc.devefiente.cdc.common.UniqueValue;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewCategoryRequest {
    
    @NotBlank
    @UniqueValue(domainClass = Category.class, fieldName = "name")
    private String name;

    public NewCategoryRequest(@NotBlank String name) {
        this.name = name;
    }
    
    public Category toModel(){
        return new Category(name);
    }
}

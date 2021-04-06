package com.cdc.devefiente.cdc.newAuthor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class NewAuthorRequest {
    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank 
    @Size(max = 400)
    private String description;

    public NewAuthorRequest(@NotBlank String name, @NotBlank @Email String email, 
        @NotBlank @Size(max = 400) String description) {
        this.name = name;
        this.email = email;
        this.description = description;
    }

    public Author toModel(){
        return new Author(this.name, this.email, this.description);
    }
}

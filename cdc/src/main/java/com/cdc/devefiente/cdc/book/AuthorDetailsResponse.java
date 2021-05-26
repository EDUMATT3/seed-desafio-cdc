package com.cdc.devefiente.cdc.book;

import com.cdc.devefiente.cdc.newAuthor.Author;

public class AuthorDetailsResponse {
    private String name;
	private String description;

	public AuthorDetailsResponse(Author author) {
        this.name = author.getName();
        this.description = author.getDescription();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

}

package com.cdc.devefiente.cdc.book;

import lombok.Getter;

@Getter
public class BookResponse {
    
    private Long id;
    private String title;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
    }
}

package com.cdc.devefiente.cdc.book;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookDetailsResponse {

    private AuthorDetailsResponse author;
    private String title;
    private String resume;
    private String summary;
    private BigDecimal price;
    private int pageNumber;
    private String isbn;
    private LocalDate publicationDate;

    public BookDetailsResponse(@NotNull Book book) {
        this.author = new AuthorDetailsResponse(book.getAuthor());
        this.title = book.getTitle();
        this.resume = book.getResume();
        this.summary = book.getSummary();
        this.price = book.getPrice();
        this.pageNumber = book.getPageNumber();
        this.isbn = book.getIsbn();
        this.publicationDate = book.getPublicationDate();
    }
}

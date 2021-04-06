package com.cdc.devefiente.cdc.book;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.cdc.devefiente.cdc.newAuthor.Author;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookDetailsResponse {
    private Long id;
    private String title;
    private String resume;
    private String summary;
    private BigDecimal price;
    private int pageNumber;
    private String isbn;
    private LocalDate publicationDate;
    private Long authorId;
    private String authorName;
    private String authorDescription;
    private Long categoryId;
    private String categoryName;

    public BookDetailsResponse(@NotNull Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.resume = book.getResume();
        this.summary = book.getSummary();
        this.price = book.getPrice();
        this.pageNumber = book.getPageNumber();
        this.isbn = book.getIsbn();
        this.publicationDate = book.getPublicationDate();

        Author author = book.getAuthor();
        this.authorId = author.getId();
        this.authorName = author.getName();
        this.authorDescription = author.getDescription();
        this.categoryId = book.getCategory().getId();
        this.categoryName = book.getCategory().getName();
    }
}

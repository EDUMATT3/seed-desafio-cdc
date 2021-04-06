package com.cdc.devefiente.cdc.book;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cdc.devefiente.cdc.category.Category;
import com.cdc.devefiente.cdc.newAuthor.Author;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BookBuilder {
    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 500)
    private String resume;

    private String summary;

    @NotNull
    @Min(value = 20)
    private BigDecimal price;

    @NotNull
    @Min(value = 100)
    private int pageNumber;

    @NotBlank
    private String isbn;

    @Future
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate publicationDate;

    @NotNull
    @ManyToOne 
    private Category category;

    @NotNull
    @ManyToOne
    private Author author;

     public BookBuilder() {}

    public BookBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder setResume(String resume) {
        this.resume = resume;
        return this;
    }

    public BookBuilder setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public BookBuilder setPrice(@NotNull @Min(20) BigDecimal price) {
        this.price = price;
        return this;
    }

    public BookBuilder setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public BookBuilder setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public BookBuilder setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public BookBuilder setCategory(Category category) {
        this.category = category;
        return this;
    }

    public BookBuilder setAuthor(Author author) {
        this.author = author;
        return this;
    }

    public Book build(){
        return new Book(title, resume, summary, price, pageNumber, isbn, publicationDate, category, author);
    }
    
}

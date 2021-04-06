package com.cdc.devefiente.cdc.book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cdc.devefiente.cdc.category.Category;
import com.cdc.devefiente.cdc.newAuthor.Author;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Deprecated
    public Book() {}

    public Book(String title, String resume, String summary, BigDecimal price, int pageNumber, String isbn, LocalDate publicationDate, Category category, Author author) {
        this.title = title;
        this.resume = resume;
        this.summary = summary;
        this.price = price;
        this.pageNumber = pageNumber;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.category = category;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Book)) {
            return false;
        }
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }
}

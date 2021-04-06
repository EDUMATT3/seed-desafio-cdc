package com.cdc.devefiente.cdc.book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.cdc.devefiente.cdc.category.Category;
import com.cdc.devefiente.cdc.common.ExistsId;
import com.cdc.devefiente.cdc.common.UniqueValue;
import com.cdc.devefiente.cdc.newAuthor.Author;
import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.util.Assert;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewBookRequest {

    @NotBlank
    @UniqueValue(domainClass = Book.class, fieldName = "title")
    private String title;

    @NotBlank
    @Size(max = 500)
    private String resume;

    private String summary;

    @NotNull
    @Min(20)
    private BigDecimal price;

    @NotNull
    @Min(100)
    private int pageNumber;

    @NotBlank
    @UniqueValue(domainClass = Book.class, fieldName = "isbn")
    private String isbn;

    @Future
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate publicationDate;

    @NotNull 
    @ExistsId(domainClass = Category.class)
    private Long categoryId;

    @NotNull
    @ExistsId(domainClass = Author.class)
    private Long authorId;

    public Book toModel(EntityManager entityManager) {

        Category category = entityManager.find(Category.class, this.categoryId);
        Assert.state(Objects.nonNull(category), "Categoria não encontrada com o id: "+ categoryId);

        Author author = entityManager.find(Author.class, authorId);
        Assert.state(Objects.nonNull(author), "Autor(a) não encontrado(a) com o id: "+ authorId);

        return new BookBuilder()
            .setTitle(title)
            .setResume(resume)
            .setSummary(summary)
            .setPrice(price)
            .setPageNumber(pageNumber)
            .setIsbn(isbn)
            .setPublicationDate(publicationDate)
            .setCategory(category)
            .setAuthor(author)
            .build();
    }

}

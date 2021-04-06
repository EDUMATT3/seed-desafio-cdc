package com.cdc.devefiente.cdc.finishpurchase;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.cdc.devefiente.cdc.book.Book;
import com.cdc.devefiente.cdc.common.ExistsId;

import lombok.Getter;

@Getter
public class NewOrderItemRequest {

    @NotNull
    @ExistsId(domainClass = Book.class)
    private Long bookId;

    @Positive
    private int quantity;

    public NewOrderItemRequest(@NotNull Long bookId, @Positive int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public Long getBookId(){
        return bookId;
    }

    public OrderItem toModel(EntityManager entityManager){
        @NotNull Book book = entityManager.find(Book.class, bookId);
        return new OrderItem(book, quantity);
    }

    @Override
    public String toString() {
        return "{" +
            " bookId='" + getBookId() + "'" +
            ", quantity='" + getQuantity() + "'" +
            "}";
    }
}

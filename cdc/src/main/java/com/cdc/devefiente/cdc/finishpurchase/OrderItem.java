package com.cdc.devefiente.cdc.finishpurchase;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.cdc.devefiente.cdc.book.Book;

@Embeddable
public class OrderItem {

    @ManyToOne
    private @NotNull Book book;
    private @Positive int quantity;
    private @Positive BigDecimal momentPrice;

    @Deprecated
    public OrderItem(){}

    public OrderItem(@NotNull Book book, @Positive int quantity) {
        this.book = book;
        this.quantity = quantity;
        this.momentPrice = book.getPrice();
    }

    public BigDecimal total(){
        return momentPrice.multiply(new BigDecimal(quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof OrderItem)) {
            return false;
        }
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(book, orderItem.book);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(book);
    }
}

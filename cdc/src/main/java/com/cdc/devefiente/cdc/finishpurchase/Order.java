package com.cdc.devefiente.cdc.finishpurchase;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.util.Assert;

import lombok.Getter;
import lombok.NonNull;

@Getter
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private @NonNull @Valid Purchase purchase;
    @ElementCollection
    private @Size(min = 1) Set<OrderItem> items = new HashSet<>();

    public Order(@NonNull @Valid Purchase purchase, @Size(min = 1) Set<OrderItem> items) {
        Assert.isTrue(!items.isEmpty(), "Every order should have at least one item");

        this.purchase = purchase;
        this.items.addAll(items);
    }

    public boolean totalEqualTo(@NotNull @Positive BigDecimal total) {
        BigDecimal orderTotal = items.stream()
            .map(OrderItem::total).reduce(BigDecimal.ZERO, 
                (actual, next) -> actual.add(next)
            );   
        return orderTotal.doubleValue() == total.doubleValue();
    }

    @Override
    public String toString() {
        return "{" +
            " purchase='" + getPurchase() + "'" +
            ", items='" + getItems() + "'" +
            "}";
    }
}

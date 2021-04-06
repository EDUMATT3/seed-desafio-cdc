package com.cdc.devefiente.cdc.finishpurchase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.util.Assert;

import lombok.Getter;

@Getter
public class NewOrderRequest {

    @NotNull
    @Positive
    private BigDecimal total;

    @Valid
    @Size(min = 1)
    private List<NewOrderItemRequest> items = new ArrayList<>();

    public NewOrderRequest(@NotNull @Positive BigDecimal total, @Valid @Size(min = 1) List<NewOrderItemRequest> items) {
        this.total = total;
        this.items = items;
    }

    public List<NewOrderItemRequest> getItems(){
        return items;
    }

    @Override
    public String toString() {
        return "{" +
            " total='" + getTotal() + "'" +
            ", items='" + getItems() + "'" +
            "}";
    }

    public Function<Purchase, Order> toModel(EntityManager entityManager) {

        Set<OrderItem> calculatedItems = items
            .stream()
            .map(item -> item.toModel(entityManager))
            .collect(Collectors.toSet());

        return (purchase) -> {
            Order order = new Order(purchase, calculatedItems);
            Assert.isTrue(order.totalEqualTo(total), "The total sent is not equal to the actual total");
            return order;
        };
    }
}

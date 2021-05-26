package com.cdc.devefiente.cdc.newCoupon;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;

@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private @NotNull String code;
    private @NotNull @DecimalMin("0.1") @DecimalMax("100.0") BigDecimal discountPercentage;
    private @Future LocalDate expirationDate;

    public Coupon(@NotNull String code, @NotNull @DecimalMin("0.1") @DecimalMax("100.0") BigDecimal discountPercentage,
            @Future LocalDate expirationDate) {
                Assert.isTrue(expirationDate.compareTo(LocalDate.now()) >=0, "A validade deve ser uma data futura");
                this.code = code;
                this.discountPercentage = discountPercentage;
                this.expirationDate = expirationDate;
    }

    @Deprecated
    public Coupon(){}

    public Long getId() {
        return id;
    }

    public boolean isValid() {
        return LocalDate.now().compareTo(this.expirationDate) <=0;
    }

    public BigDecimal getDiscountPercentage() {
        return discountPercentage;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

}

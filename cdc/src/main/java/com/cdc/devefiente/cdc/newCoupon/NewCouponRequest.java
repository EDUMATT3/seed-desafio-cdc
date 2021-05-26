package com.cdc.devefiente.cdc.newCoupon;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.cdc.devefiente.cdc.common.UniqueValue;
import com.fasterxml.jackson.annotation.JsonFormat;

public class NewCouponRequest {

    @NotNull
    @UniqueValue(domainClass = Coupon.class, fieldName = "code")
    private String code;

    @NotNull
    @DecimalMin("0.1")
    @DecimalMax("100.0")
    private BigDecimal discountPercentage;

    @Future
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expirationDate;

    public NewCouponRequest(@NotNull String code,
            @NotNull @DecimalMin("0.1") @DecimalMax("100.0") BigDecimal discountPercentage,
            @Future LocalDate expirationDate) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.expirationDate = expirationDate;
    }

    public Coupon toModel() {
        return new Coupon(code, discountPercentage, expirationDate);
    }
}

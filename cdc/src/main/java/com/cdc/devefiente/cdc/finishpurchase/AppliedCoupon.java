package com.cdc.devefiente.cdc.finishpurchase;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.cdc.devefiente.cdc.newCoupon.Coupon;

@Embeddable
public class AppliedCoupon {

    @ManyToOne
    private Coupon coupon;
    @Positive
    @NotNull
    private BigDecimal discountPercentage;
    @Future
    @NotNull
    private LocalDate momentExpirationDate;

    @Deprecated
    public AppliedCoupon(){}

    public AppliedCoupon(Coupon coupon) {
        this.coupon = coupon;
        this.discountPercentage = coupon.getDiscountPercentage();
        this.momentExpirationDate = coupon.getExpirationDate();
    }

}

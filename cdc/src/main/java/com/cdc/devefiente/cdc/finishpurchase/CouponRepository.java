package com.cdc.devefiente.cdc.finishpurchase;

import com.cdc.devefiente.cdc.newCoupon.Coupon;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    public Coupon getByCode(String code);
}

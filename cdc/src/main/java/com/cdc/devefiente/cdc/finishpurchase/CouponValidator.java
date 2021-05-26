package com.cdc.devefiente.cdc.finishpurchase;

import java.util.Optional;

import com.cdc.devefiente.cdc.newCoupon.Coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CouponValidator implements Validator{

    CouponRepository couponRepository;
    
    @Autowired
    public CouponValidator(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public boolean supports(Class<?> clazz){
        return FinishPurchaseRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors){
        if(errors.hasErrors()){
            return;
        }

        FinishPurchaseRequest request = (FinishPurchaseRequest) target;

        Optional<String> optionalCode = request.getCouponCode();

        if(optionalCode.isPresent()){
            Coupon coupon = couponRepository.getByCode(optionalCode.get());
            if(!coupon.isValid()){
                errors.rejectValue("couponCode", null, "This coupon is not valid");
            }
        }
    }

}

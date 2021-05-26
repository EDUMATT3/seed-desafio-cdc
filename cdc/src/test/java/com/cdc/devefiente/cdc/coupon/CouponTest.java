package com.cdc.devefiente.cdc.coupon;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.cdc.devefiente.cdc.newCoupon.Coupon;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.util.ReflectionTestUtils;

public class CouponTest {
    
    @ParameterizedTest
    @CsvSource({
        "0, true",
        "1, true"
    })
    @DisplayName("Cria cupom com data válida")
    void test1(long value, boolean result) throws Exception{
        Coupon coupon = new Coupon("code", BigDecimal.TEN, LocalDate.now().plusDays(value));
        Assertions.assertEquals(result, coupon.isValid());
    }

    @Test
    @DisplayName("Cupon com data passada não é mais válido")
    void test2() throws Exception{
        Coupon coupon = new Coupon("code", BigDecimal.TEN, LocalDate.now().plusDays(1));
        //muda um valor de um campo em um objeto já criado
        ReflectionTestUtils.setField(coupon, "expirationDate", LocalDate.now().minusDays(1));
        Assertions.assertFalse(coupon.isValid());
    }

    @Test
    @DisplayName("Não pode criar um cupom com data no passado")
    void test3() throws Exception{
        
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Coupon("code", BigDecimal.TEN, LocalDate.now().minusDays(1));
        });
    }
}

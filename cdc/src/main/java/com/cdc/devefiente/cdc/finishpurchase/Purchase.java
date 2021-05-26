package com.cdc.devefiente.cdc.finishpurchase;

import java.util.function.Function;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cdc.devefiente.cdc.newCoupon.Coupon;
import com.cdc.devefiente.cdc.newPais.Country;
import com.cdc.devefiente.cdc.newState.State;

import org.springframework.util.Assert;

import lombok.ToString;

@ToString
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private @Email @NotBlank String email;
    private @NotBlank String name;
    private @NotBlank String lastName;
    private @NotBlank @Document String document;
    private @NotBlank String address;
    private @NotBlank String complement;
    @ManyToOne
    private @NotNull Country country;
    @ManyToOne
    private State state;
    private @NotBlank String phoneNumber;
    private @NotBlank String cep;
    @OneToOne(mappedBy = "purchase", cascade = CascadeType.PERSIST)
    private Order order;

    @Embedded
    private AppliedCoupon appliedCoupon;

    public Purchase(@Email @NotBlank String email, @NotBlank String name, @NotBlank String lastName,
            @NotBlank @Document String document, @NotBlank String address, @NotBlank String complement,
            @NotNull Country country, @NotBlank String phoneNumber, @NotBlank String cep, Function<Purchase, Order> orderCreatFunction) {
                this.email = email;
                this.name = name;
                this.lastName = lastName;
                this.document = document;
                this.address = address;
                this.complement = complement;
                this.country = country;
                this.phoneNumber = phoneNumber;
                this.cep = cep;
                this.order = orderCreatFunction.apply(this);
    }

    public void setState(@NotNull @Valid State state) {
        Assert.notNull(country, "Country cannot be null");
        Assert.isTrue(state.belongsToCountry(country), "This state does not belong to this Country");
        this.state = state;
    }

    public void applyCoupon(Coupon coupon) {
        Assert.isTrue(coupon.isValid(), "The coupon to be applied is not valid");
        Assert.isNull(appliedCoupon, "You cannot change a purchase coupon");
        this.appliedCoupon = new AppliedCoupon(coupon);
    }
}

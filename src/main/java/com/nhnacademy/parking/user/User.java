package com.nhnacademy.parking.user;

import com.nhnacademy.parking.exception.InsufficientCashException;
import java.math.BigDecimal;

public class User {

    private final Long id;
    private BigDecimal money;
    Voucher voucher;

    public User(Long id, BigDecimal money) {
        this.id = id;
        this.money = money;
    }

    public void pay(BigDecimal fee) {

        System.out.println(this.id + "님 결제금액: " + fee + "₩");

        if (this.money.compareTo(fee) < 0) {
            throw new InsufficientCashException();
        }

        this.money = this.money.subtract(fee);
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public Voucher getVoucher() {
        return voucher;
    }

    public void takeVoucher(Voucher voucher) {
        this.voucher = voucher;
    }
}

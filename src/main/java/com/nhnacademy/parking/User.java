package com.nhnacademy.parking;

import com.nhnacademy.parking.exception.InsufficientCashException;
import java.math.BigDecimal;

public class User {

    private final Long id;
    private BigDecimal money;

    public User(Long id, BigDecimal money) {
        this.id = id;
        this.money = money;
    }

    public void pay(BigDecimal fee) {
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

}

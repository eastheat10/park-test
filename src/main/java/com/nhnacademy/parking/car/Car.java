package com.nhnacademy.parking.car;

import com.nhnacademy.parking.User;
import com.nhnacademy.parking.exception.InsufficientCashException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Car {

    public Long getNumber() {
        return number;
    }

    protected final User user;

    private final Long number;
    private final LocalDateTime enterTime;

    public Car(User user, Long number, LocalDateTime enterTime) {
        this.user = user;
        this.number = number;
        this.enterTime = enterTime;
    }

    public void pay(BigDecimal fee) {
        user.pay(fee);
    }

    public LocalDateTime getEnterTime() {
        return this.enterTime;
    }

    public BigDecimal getMoney() {
        return user.getMoney();
    }

    public Long getUserId() {
        return user.getId();
    }
}

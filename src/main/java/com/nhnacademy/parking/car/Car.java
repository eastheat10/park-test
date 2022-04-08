package com.nhnacademy.parking.car;

import com.nhnacademy.parking.exception.InsufficientCashException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Car {

    public Long getNumber() {
        return number;
    }

    private final Long number;
    private final BigDecimal money;
    private final LocalDateTime enterTime;

    public Car(Long number, BigDecimal money, LocalDateTime enterTime) {
        this.number = number;
        this.money = money;
        this.enterTime = enterTime;
    }

    public void pay(BigDecimal fee) {
        if (this.money.compareTo(fee) < 0) {
            throw new InsufficientCashException();
        }
        money.subtract(fee);
    }

    public LocalDateTime getEnterTime() {
        return this.enterTime;
    }

}

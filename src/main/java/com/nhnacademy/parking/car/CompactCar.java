package com.nhnacademy.parking.car;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CompactCar extends Car {
    public CompactCar(Long number, BigDecimal money, LocalDateTime enterTime) {
        super(number, money, enterTime);
    }

    @Override
    public void pay(BigDecimal fee) {

    }
}

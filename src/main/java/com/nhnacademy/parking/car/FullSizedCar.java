package com.nhnacademy.parking.car;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FullSizedCar extends Car {
    public FullSizedCar(Long number, BigDecimal money, LocalDateTime enterTime) {
        super(number, money, enterTime);
    }
}

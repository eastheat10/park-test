package com.nhnacademy.parking.car;

import com.nhnacademy.parking.user.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CompactCar extends Car {

    public CompactCar(User user, Long number, LocalDateTime enterTime) {
        super(user, number, enterTime);
    }

    @Override
    public void pay(BigDecimal fee) {
        user.pay(fee.multiply(BigDecimal.valueOf(0.5)));
    }
}

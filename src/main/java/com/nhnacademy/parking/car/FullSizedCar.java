package com.nhnacademy.parking.car;

import com.nhnacademy.parking.user.User;
import java.time.LocalDateTime;

public class FullSizedCar extends Car {

    public FullSizedCar(User user, Long number, LocalDateTime enterTime) {
        super(user, number, enterTime);
    }
}

package com.nhnacademy.parking.car;

import com.nhnacademy.parking.user.User;
import com.nhnacademy.parking.user.Voucher;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

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
        System.out.println(this.getUserId() + "님 입차 시간: " + this.enterTime);
        System.out.println(this.getUserId() + "님 출차 시간: " + LocalDateTime.now());
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

    public Optional<Voucher> getVoucher() {
        return Optional.ofNullable(user.getVoucher());
    }
}

package com.nhnacademy.parking.user;

public class Voucher {

    private final long discountTime;

    public Voucher(long discountTime) {
        this.discountTime = discountTime;
    }

    public long getDiscountTime() {
        return discountTime;
    }
}

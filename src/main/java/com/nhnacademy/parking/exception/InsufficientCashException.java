package com.nhnacademy.parking.exception;

public class InsufficientCashException extends IllegalStateException {
    public InsufficientCashException() {
        super("고객님의 잔액이 부족합니다");
    }
}

package com.nhnacademy.parking.exception;

public class CapacityOverflowException extends IllegalStateException {
    public CapacityOverflowException() {
        super("주차 대수 초과");
    }
}

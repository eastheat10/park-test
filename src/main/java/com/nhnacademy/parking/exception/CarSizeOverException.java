package com.nhnacademy.parking.exception;

public class CarSizeOverException extends IllegalArgumentException {

    public CarSizeOverException() {
        super("대형차는 진입불가합니다.");
    }
}

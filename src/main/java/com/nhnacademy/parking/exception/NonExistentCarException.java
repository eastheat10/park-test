package com.nhnacademy.parking.exception;

import java.util.NoSuchElementException;

public class NonExistentCarException extends NoSuchElementException {

    public NonExistentCarException(String lotCode) {
        super(lotCode + "번에 주차된 차량이 없습니다.");
    }
}

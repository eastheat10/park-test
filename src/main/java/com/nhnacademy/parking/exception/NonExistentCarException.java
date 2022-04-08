package com.nhnacademy.parking.exception;

import java.util.NoSuchElementException;

public class NonExistentCarException extends NoSuchElementException {

    public NonExistentCarException(Long carNumber) {
        super(carNumber + "번에 해당하는 차량이 없습니다.");
    }
}

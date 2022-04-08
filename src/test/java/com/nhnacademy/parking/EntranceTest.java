package com.nhnacademy.parking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.exception.CapacityOverflowException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EntranceTest {

    Entrance entrance;
    ParkingSystem ps;

    @BeforeEach
    void setUp() {
        ps = mock(ParkingSystem.class);
        entrance = new Entrance(ps);
    }

    @DisplayName("차가 들어오면 번호판 인식")
    @Test
    void enterCar() {
        Car car = mock(Car.class);

        when(car.getNumber()).thenReturn(1L);

        assertThat(entrance.scan(car)).isTrue();
        verify(car, times(1)).getNumber();
    }

    @DisplayName("번호판 인식 실패")
    @Test
    void enterCar_fail() {
        Car car = mock(Car.class);

        when(car.getNumber()).thenReturn(null);

        assertThat(entrance.scan(car)).isFalse();
        verify(car, times(1)).getNumber();
    }

    @DisplayName("주차")
    @Test
    void park() {
        Car car = mock(Car.class);

        when(ps.park(car)).thenReturn(1);

        assertThat(entrance.park(car)).isNotNull();
        verify(ps, times(1)).park(car);
    }

    @DisplayName("주차 실패")
    @Test
    void park_fail() {
        Car car = mock(Car.class);

        when(ps.park(car)).thenThrow(CapacityOverflowException.class);

        assertThatThrownBy(() -> entrance.park(car))
            .isInstanceOf(CapacityOverflowException.class);
    }

}
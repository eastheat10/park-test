package com.nhnacademy.parking;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.parkingsystem.ParkingSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ExitTest {

    Exit exit;
    ParkingSystem parkingSystem;

    @BeforeEach
    void setUp() {
        parkingSystem = mock(ParkingSystem.class);
        exit = new Exit(parkingSystem);
    }

    @DisplayName("주차장에서 차가 나갈 때 결제")
    @Test
    void exit() {
        Car car = mock(Car.class);
        String id = "A - 1";

        when(parkingSystem.exit(id)).thenReturn(car);

        assertThat(exit.exit(id)).isNotNull();
        verify(parkingSystem, times(1)).exit(id);
    }
}
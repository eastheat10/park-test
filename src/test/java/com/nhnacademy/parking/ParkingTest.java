package com.nhnacademy.parking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.car.FullSizedCar;
import com.nhnacademy.parking.exception.CapacityOverflowException;
import com.nhnacademy.parking.exception.CarSizeOverException;
import com.nhnacademy.parking.policy.FeePolicy1;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingTest {

    ParkingSystem ps;
    Car car;

    @BeforeEach
    void setUp() {
        ps = new ParkingSystem(new FeePolicy1());
        car = mock(Car.class);
    }

    @DisplayName("차가 들어오면 주차")
    @Test
    void park() {

        assertThat(ps.park(car)).isNotNull();
    }

    @DisplayName("자리가 없어 주차 실패")
    @Test
    void park_fail() {

        for (int i = 0; i < 10; i++) {
            ps.park(car);
        }

        assertThatThrownBy(() -> ps.park(car))
            .isInstanceOf(CapacityOverflowException.class);
    }

    @DisplayName("대형차는 주차 불가")
    @Test
    void bigCarCantPark() {
        User user = mock(User.class);
        Long carNumber = 1234L;
        LocalDateTime enterTime = LocalDateTime.now();

        Car car = new FullSizedCar(user, carNumber, enterTime);

        assertThatThrownBy(() -> ps.park(car))
            .isInstanceOf(CarSizeOverException.class);
    }

}

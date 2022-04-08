package com.nhnacademy.parking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.exception.CapacityOverflowException;
import com.nhnacademy.parking.exception.InsufficientCashException;
import com.nhnacademy.parking.policy.FeePolicy1;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingSystemTest {

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

    @DisplayName("차가 나가면 결제 - 30분 주차, 차주 1,000원 소지")
    @Test
    void payWhenExit30m() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(1_000);
        LocalDateTime before30m = LocalDateTime.now().minusMinutes(30);

        Car parkingCar = new Car(carNumber, money, before30m);
        ps.park(parkingCar);

        assertThat(ps.exit(carNumber)).isEqualTo(parkingCar);
    }

    @DisplayName("차가 나가면 결제실패 - 30분 주차, 차주 999원 소지")
    @Test
    void payWhenExit30m_fail() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(999);
        LocalDateTime before30m = LocalDateTime.now().minusMinutes(30);

        Car parkingCar = new Car(carNumber, money, before30m);
        ps.park(parkingCar);

        assertThatThrownBy(() -> ps.exit(carNumber))
            .isInstanceOf(InsufficientCashException.class);
    }

    @DisplayName("차가 나가면 결제 - 31분 주차, 차주 1,500원 소지")
    @Test
    void payWhenExit31m() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(1_500);
        LocalDateTime before31m = LocalDateTime.now().minusMinutes(31);

        Car parkingCar = new Car(carNumber, money, before31m);
        ps.park(parkingCar);

        assertThat(ps.exit(carNumber)).isEqualTo(parkingCar);
    }

    @DisplayName("차가 나가면 결제실패 - 31분 주차, 차주 1,499원 소지")
    @Test
    void payWhenExit31m_fail() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(1_499);
        LocalDateTime before31m = LocalDateTime.now().minusMinutes(31);

        Car parkingCar = new Car(carNumber, money, before31m);
        ps.park(parkingCar);

        assertThatThrownBy(() -> ps.exit(carNumber))
            .isInstanceOf(InsufficientCashException.class);
    }

    @DisplayName("차가 나가면 결제 - 61분 주차, 차주 3,000원 소지")
    @Test
    void payWhenExit61m() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(3_000);
        LocalDateTime before61m = LocalDateTime.now().minusMinutes(61);

        Car parkingCar = new Car(carNumber, money, before61m);
        ps.park(parkingCar);

        assertThat(ps.exit(carNumber)).isEqualTo(parkingCar);
    }

    @DisplayName("차가 나가면 결제실패 - 61분 주차, 차주 2,999원 소지")
    @Test
    void payWhenExit61m_fail() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(2_999);
        LocalDateTime before61m = LocalDateTime.now().minusMinutes(61);

        Car parkingCar = new Car(carNumber, money, before61m);
        ps.park(parkingCar);

        assertThatThrownBy(() -> ps.exit(carNumber))
            .isInstanceOf(InsufficientCashException.class);
    }

    @DisplayName("차가 나가면 결제 - 6시간 주차, 차주 10,000원 소지")
    @Test
    void payWhenExit6h() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(10_000);
        LocalDateTime before6h = LocalDateTime.now().minusHours(6);

        Car parkingCar = new Car(carNumber, money, before6h);
        ps.park(parkingCar);

        assertThat(ps.exit(carNumber)).isEqualTo(parkingCar);
    }

    @DisplayName("차가 나가면 결제실패 - 6시간 주차, 차주 9,999원 소지")
    @Test
    void payWhenExit6h_fail() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(9_999);
        LocalDateTime before6h = LocalDateTime.now().minusHours(6);

        Car parkingCar = new Car(carNumber, money, before6h);
        ps.park(parkingCar);

        assertThatThrownBy(() -> ps.exit(carNumber))
            .isInstanceOf(InsufficientCashException.class);
    }

}
package com.nhnacademy.parking.parkingsystem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.policy.FeePolicy1;
import com.nhnacademy.parking.user.User;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PayTest1 {

    ParkingSystem ps;
    Car car;

    @BeforeEach
    void setUp() {
        ps = new ParkingSystem(new FeePolicy1());
        car = mock(Car.class);
    }

    @DisplayName("차가 나가면 결제 - 30분 주차, 차주 1,000원 소지")
    @Test
    void payWhenExit30m() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(1_000);
        LocalDateTime before30m = LocalDateTime.now().minusMinutes(30);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before30m);
        Long lotCode = ps.park(parkingCar);

        assertThat(ps.exit(lotCode)).isEqualTo(parkingCar);
    }

    @DisplayName("차가 나가면 결제실패 - 30분 주차, 차주 999원 소지")
    @Test
    void payWhenExit30m_fail() {


        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(999);
        LocalDateTime before30m = LocalDateTime.now().minusMinutes(30);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before30m);
        Long lotCode = ps.park(parkingCar);

        assertThat(ps.exit(lotCode))
            .isNull();
    }

    @DisplayName("차가 나가면 결제 - 31분 주차, 차주 1,500원 소지")
    @Test
    void payWhenExit31m() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(1_500);
        LocalDateTime before31m = LocalDateTime.now().minusMinutes(31);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before31m);
        Long lotCode = ps.park(parkingCar);

        assertThat(ps.exit(lotCode)).isEqualTo(parkingCar);
    }

    @DisplayName("차가 나가면 결제실패 - 31분 주차, 차주 1,499원 소지")
    @Test
    void payWhenExit31m_fail() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(1_499);
        LocalDateTime before31m = LocalDateTime.now().minusMinutes(31);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before31m);
        Long lotCode = ps.park(parkingCar);

        assertThat(ps.exit(lotCode)).isNull();
    }

    @DisplayName("차가 나가면 결제 - 61분 주차, 차주 3,000원 소지")
    @Test
    void payWhenExit61m() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(3_000);
        LocalDateTime before61m = LocalDateTime.now().minusMinutes(61);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before61m);
        Long lotCode = ps.park(parkingCar);

        assertThat(ps.exit(lotCode)).isEqualTo(parkingCar);
    }

    @DisplayName("차가 나가면 결제실패 - 61분 주차, 차주 2,999원 소지")
    @Test
    void payWhenExit61m_fail() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(2_999);
        LocalDateTime before61m = LocalDateTime.now().minusMinutes(61);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before61m);
        Long lotCode = ps.park(parkingCar);

        assertThat(ps.exit(lotCode)).isNull();

    }

    @DisplayName("차가 나가면 결제 - 6시간 주차, 차주 10,000원 소지")
    @Test
    void payWhenExit6h() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(10_000);
        LocalDateTime before6h = LocalDateTime.now().minusHours(6);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before6h);
        Long lotCode = ps.park(parkingCar);

        assertThat(ps.exit(lotCode)).isEqualTo(parkingCar);
    }

    @DisplayName("차가 나가면 결제실패 - 6시간 주차, 차주 9,999원 소지")
    @Test
    void payWhenExit6h_fail() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(9_999);
        LocalDateTime before6h = LocalDateTime.now().minusHours(6);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before6h);
        Long lotCode = ps.park(parkingCar);

        assertThat(ps.exit(lotCode)).isNull();
    }

}
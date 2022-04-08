package com.nhnacademy.parking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.exception.CapacityOverflowException;
import com.nhnacademy.parking.exception.InsufficientCashException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.function.BiFunction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingRepositoryImplTest {

    ParkingRepository parkingRepository;
    Car car;
    BiFunction<LocalDateTime, LocalDateTime, Long> policy;

    @BeforeEach
    void setUp() {
        policy = (enterTime, exitTime) -> {
            long betweenMin = ChronoUnit.MINUTES.between(enterTime, exitTime);
            if (exitTime.getDayOfMonth() <= enterTime.getDayOfMonth()) {    // 주차 하루 미만
                long additionalTime = betweenMin - 30L;
                long times = additionalTime > 0 ? (additionalTime / 10) + 1 : 0;
                long totalFee =  1_000L + (500 * times);
                return Math.min(totalFee, 10_000L);
            }
            betweenMin =
                ChronoUnit.MINUTES.between(enterTime.plusDays(1).with(LocalTime.MIN), exitTime);
            long parkingDays = betweenMin / 1440L;
            System.out.println("parkingDays = " + parkingDays);
            long additionalFee = (betweenMin % 1440L) > 200 ? 10_000 : (betweenMin - 1440L) / 10;
            return (parkingDays * 10_000L) + additionalFee + 10_000L;
        };
        parkingRepository = new ParkingRepositoryImpl(policy);
        car = mock(Car.class);
    }

    @DisplayName("주차장에 차가 들아오면 번호판을 인식한다")
    @Test
    void enterCar() {
        parkingRepository.enter(car);

        when(car.getNumber()).thenReturn(1L);

        verify(car, times(1)).getNumber();
    }

    @DisplayName("차를 특정 구역에 주차")
    @Test
    void parking() {

        assertThat(parkingRepository.park(car)).isEqualTo(1);
    }

    @DisplayName("주차 실패")
    @Test
    void fail_parking() {
        parkingRepository.park(car);
        parkingRepository.park(car);
        parkingRepository.park(car);
        parkingRepository.park(car);
        parkingRepository.park(car);

        assertThatThrownBy(() -> parkingRepository.park(car))
            .isInstanceOf(CapacityOverflowException.class);
    }

    @DisplayName("출차시 주차 요금 결제")
    @Test
    void payParkingFee() {
        assertThat(parkingRepository.exit(car)).isTrue();
    }

    @DisplayName("주차요금 결제 실패")
    @Test
    void payFee_Fail() {
        Car car1 = new Car(1234L, BigDecimal.valueOf(0), LocalDateTime.now());
        assertThatThrownBy(() -> parkingRepository.exit(car1))
            .isInstanceOf(InsufficientCashException.class);
    }

    @Test
    void datTest1() {
        LocalDateTime enterTime = LocalDateTime.of(2022, 04, 06, 12, 00, 00);
        LocalDateTime 다음날자정 = enterTime.plusDays(1).with(LocalTime.MIN);
        System.out.println("다음날자정 = " + 다음날자정);
    }


}

package com.nhnacademy.parking.car;

import static org.assertj.core.api.Assertions.assertThat;

import com.nhnacademy.parking.User;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CarTest {

    User user;

    @BeforeEach
    void setUp() {
        user = new User(1234L, BigDecimal.valueOf(100_000));
    }

    @DisplayName("경차의 경우 요금이 50% 감면")
    @Test
    void compactCarDiscount50percent() {
        Long carNumber = 1234L;
        LocalDateTime enterTime = LocalDateTime.now();
        BigDecimal fee = BigDecimal.valueOf(10_000);
        BigDecimal discountedFee = fee.divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN);

        Car car = new CompactCar(user, carNumber, enterTime);
        BigDecimal money = car.getMoney();

        car.pay(fee);

        assertThat(car.getMoney())
            .isEqualTo(money.subtract(discountedFee));
    }

    @DisplayName("일반 차는 할인 없음")
    @Test
    void justFee() {
        Long carNumber = 1234L;
        LocalDateTime enterTime = LocalDateTime.now();
        BigDecimal fee = BigDecimal.valueOf(10_000);

        Car car = new Car(user, carNumber, enterTime);
        BigDecimal money = car.getMoney();

        car.pay(fee);

        assertThat(car.getMoney()).isEqualTo(money.subtract(fee));
    }

}
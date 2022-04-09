package com.nhnacademy.parking.policy;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FeePolicy1Test {

    FeePolicy policy;

    @BeforeEach
    void setUp() {
        policy = new FeePolicy1();
    }

    @DisplayName("30분 주차시 주차요금 1000원")
    @Test
    void parking_30m() {
        LocalDateTime enterTime = LocalDateTime.of(2022, 4, 6, 12, 0, 0);
        LocalDateTime exitTime = LocalDateTime.of(2022, 4, 6, 12, 30, 0);

        assertThat(policy.calculateFee(enterTime, exitTime, Optional.empty())).isEqualTo(BigDecimal.valueOf(1_000));
    }

    @DisplayName("31분 주차시 주차요금 1500원")
    @Test
    void parking_31m() {
        LocalDateTime enterTime = LocalDateTime.of(2022, 4, 6, 12, 0, 0);
        LocalDateTime exitTime = LocalDateTime.of(2022, 4, 6, 12, 31, 0);

        assertThat(policy.calculateFee(enterTime, exitTime, Optional.empty())).isEqualTo(BigDecimal.valueOf(1_500));
    }

    @DisplayName("61분 주차시 주차요금 3000원")
    @Test
    void parking_61m() {
        LocalDateTime enterTime = LocalDateTime.of(2022, 4, 6, 12, 0, 0);
        LocalDateTime exitTime = LocalDateTime.of(2022, 4, 6, 13, 1, 0);

        assertThat(policy.calculateFee(enterTime, exitTime, Optional.empty())).isEqualTo(BigDecimal.valueOf(3_000));
    }

    @DisplayName("6시간 주차시 주차요금 10,000원")
    @Test
    void parking_6h() {
        LocalDateTime enterTime = LocalDateTime.of(2022, 4, 6, 12, 0, 0);
        LocalDateTime exitTime = LocalDateTime.of(2022, 4, 6, 18, 0, 0);

        assertThat(policy.calculateFee(enterTime, exitTime, Optional.empty())).isEqualTo(BigDecimal.valueOf(10_000));
    }

    @DisplayName("2일 연속 주차시 20,000원")
    @Test
    void parking_2days() {
        LocalDateTime enterTime = LocalDateTime.of(2022, 4, 6, 12, 0, 0);
        LocalDateTime exitTime = LocalDateTime.of(2022, 4, 7, 12, 0, 0);

        assertThat(policy.calculateFee(enterTime, exitTime, Optional.empty())).isEqualTo(BigDecimal.valueOf(20_000));
    }

    @DisplayName("3일 연속 주차시 30,000원")
    @Test
    void parking_3days() {
        LocalDateTime enterTime = LocalDateTime.of(2022, 4, 6, 12, 0, 0);
        LocalDateTime exitTime = LocalDateTime.of(2022, 4, 8, 12, 0, 0);

        assertThat(policy.calculateFee(enterTime, exitTime, Optional.empty())).isEqualTo(BigDecimal.valueOf(30_000));
    }

}
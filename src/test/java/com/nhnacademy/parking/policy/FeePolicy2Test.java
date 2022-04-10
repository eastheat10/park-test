package com.nhnacademy.parking.policy;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FeePolicy2Test {

    FeePolicy policy;

    @BeforeEach
    void setUP() {
        policy = new FeePolicy2();
    }

    @DisplayName("최초 30분 무료")
    @Test
    void free30m() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before30m = now.minusMinutes(30);

        assertThat(policy.calculateFee(before30m, now, 0)).isEqualTo(BigDecimal.valueOf(0));
    }

    @DisplayName("31분 ~ 60분 1,000원")
    @Test
    void parking_31m_to_60m() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before31m = now.minusMinutes(31);
        LocalDateTime before60m = now.minusMinutes(60);

        assertThat(policy.calculateFee(before31m, now, 0)).isEqualTo(BigDecimal.valueOf(1_000));
        assertThat(policy.calculateFee(before60m, now, 0)).isEqualTo(BigDecimal.valueOf(1_000));
    }

    @DisplayName("60분 부터 10분당 500원 추가")
    @Test
    void parking_500_per_10m() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime before61m = now.minusMinutes(61);
        LocalDateTime before71m = now.minusMinutes(71);

        assertThat(policy.calculateFee(before61m, now, 0)).isEqualTo(BigDecimal.valueOf(1_500));
        assertThat(policy.calculateFee(before71m, now, 0)).isEqualTo(BigDecimal.valueOf(2_000));
    }

    @DisplayName("하루 최대 요금 15,000원")
    @Test
    void parking_max_fee_15_000() {
        LocalDateTime enterTime = LocalDateTime.of(2022, 4, 9, 0, 0, 0);
        LocalDateTime exitTime = LocalDateTime.of(2022, 4, 9, 11, 59, 59);

        assertThat(policy.calculateFee(enterTime, exitTime, 0)).isEqualTo(
            BigDecimal.valueOf(15_000));
    }

    @DisplayName("2일 연속 주차 30,000원")
    @Test
    void parking_2days() {
        LocalDateTime enterTime = LocalDateTime.of(2022, 4, 9, 0, 0, 0);
        LocalDateTime exitTime = LocalDateTime.of(2022, 4, 10, 23, 59, 59);

        assertThat(policy.calculateFee(enterTime, exitTime, 0)).isEqualTo(
            BigDecimal.valueOf(30_000));
    }

}
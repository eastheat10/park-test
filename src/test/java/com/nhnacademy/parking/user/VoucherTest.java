package com.nhnacademy.parking.user;

import static org.assertj.core.api.Assertions.*;

import com.nhnacademy.parking.policy.FeePolicy;
import com.nhnacademy.parking.policy.FeePolicy2;
import com.nhnacademy.parking.user.Voucher;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class VoucherTest {

    FeePolicy policy;

    @BeforeEach
    void setUp() {
        policy = new FeePolicy2();
    }

    @DisplayName("3시간 주차 후 2시간 주차권을 제시하면, 1시간 요금만 정산")
    @Test
    void useVoucher() {
        LocalDateTime enterTime = LocalDateTime.now();
        LocalDateTime exitTime = enterTime.plusHours(3);
        LocalDateTime discountTime = enterTime.plusHours(1);

        Optional<Voucher> voucher = Optional.of(new Voucher(2));

        BigDecimal threeHourWithVoucher = policy.calculateFee(enterTime, exitTime, voucher);
        BigDecimal oneHourFee = policy.calculateFee(enterTime, discountTime, Optional.empty());

        assertThat(threeHourWithVoucher).isEqualTo(oneHourFee);
    }

    @DisplayName("59분 주차 후 1시간 주차권을 제시하면, 무료")
    @Test
    void isFree() {
        LocalDateTime enterTime = LocalDateTime.now();
        LocalDateTime exitTime = enterTime.plusMinutes(59);

        Optional<Voucher> voucher = Optional.of(new Voucher(1));

        BigDecimal fee = policy.calculateFee(enterTime, exitTime, voucher);

        assertThat(fee).isEqualTo(BigDecimal.ZERO);
    }
}
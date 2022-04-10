package com.nhnacademy.parking.policy;

import static java.time.temporal.ChronoUnit.MINUTES;

import com.nhnacademy.parking.user.Voucher;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

public class FeePolicy1 implements FeePolicy {

    private final static long MAX_FEE_OF_DAY = 10_000L;
    private final static long MINUTE_OF_DAY = 1440L;
    private final static long ADDITIONAL_FEE_PER_10MIN = 500L;

    @Override
    public BigDecimal calculateFee(LocalDateTime enterTime, LocalDateTime exitTime, Optional<Voucher> voucher) {

        long betweenMin = MINUTES.between(enterTime, exitTime);

        if (enterTime.getDayOfMonth() == exitTime.getDayOfMonth()) {    // 주차 하루 미만
            long additionalTime = betweenMin - 30L;
            long times = additionalTime > 0 ? (additionalTime / 10) + 1 : 0;
            long totalFee = 1_000L + (ADDITIONAL_FEE_PER_10MIN * times);
            return BigDecimal.valueOf(Math.min(totalFee, MAX_FEE_OF_DAY));
        }

        // 주차 시간이 하루가 넘을 시 며칠간 주차 했는지 계산
        betweenMin = MINUTES.between(exitTime.with(LocalTime.MIN), exitTime);
        long parkingDays = exitTime.getDayOfMonth() - enterTime.getDayOfMonth();
        long additionalFee = betweenMin % MINUTE_OF_DAY > 200 ? MAX_FEE_OF_DAY
            : ((betweenMin % MINUTE_OF_DAY) / 10 + 1) * ADDITIONAL_FEE_PER_10MIN;

        return BigDecimal.valueOf((parkingDays * MAX_FEE_OF_DAY) + additionalFee);
    }
}

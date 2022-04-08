package com.nhnacademy.parking.policy;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FeePolicy1 implements FeePolicy {
    @Override
    public BigDecimal calculateFee(LocalDateTime enterTime, LocalDateTime exitTime) {
        long betweenMin = MINUTES.between(enterTime, exitTime);

        if (DAYS.between(enterTime, exitTime) < 1) {    // 주차 하루 미만
            long additionalTime = betweenMin - 30L;
            long times = additionalTime > 0 ? (additionalTime / 10) + 1 : 0;
            long totalFee = 1_000L + (500 * times);
            return BigDecimal.valueOf(Math.min(totalFee, 10_000L));
        }

        betweenMin = MINUTES.between(enterTime.plusDays(1).with(LocalTime.MIN), exitTime);
        System.out.println("betweenMin = " + betweenMin);
        long parkingDays = betweenMin / 1440L;
        long additionalFee = (betweenMin % 1440L) > 200 ? 10_000 : (betweenMin - 1440L) / 10;
        return BigDecimal.valueOf((parkingDays * 10_000L) + additionalFee + 10_000L);
    }
}

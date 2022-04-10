package com.nhnacademy.parking.policy;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class FeePolicy2 implements FeePolicy {

    private final static long MAX_FEE_OF_DAY = 15_000L;
    private final static long MINUTE_OF_DAY = 1440L;
    private final static long ADDITIONAL_FEE_PER_10MIN = 500L;

    @Override
    public BigDecimal calculateFee(LocalDateTime enterTime, LocalDateTime exitTime, long voucher) {
        if (voucher > 0) {
            System.out.println(voucher + "시간권을 사용합니다.");
            exitTime = exitTime.minusHours(voucher);
        }

        long betweenMin = MINUTES.between(enterTime, exitTime);

        if (enterTime.getDayOfMonth() >= exitTime.getDayOfMonth()) {
            if (betweenMin <= 30) {
                return BigDecimal.valueOf(0);
            }

            if (betweenMin <= 60) {
                return BigDecimal.valueOf(1_000);
            }

            betweenMin -= 60;
            long times = betweenMin / 10 + 1;
            long result = 1_000 + (ADDITIONAL_FEE_PER_10MIN * times);
            return BigDecimal.valueOf(Math.min(result, MAX_FEE_OF_DAY));
        }

        // 주차 시간이 하루가 넘을 시 며칠간 주차 했는지 계산
        betweenMin = MINUTES.between(exitTime.with(LocalTime.MIN), exitTime);
        long parkingDays = exitTime.getDayOfMonth() - enterTime.getDayOfMonth();
        long additionalFee = betweenMin % MINUTE_OF_DAY > 300 ? MAX_FEE_OF_DAY
            : ((betweenMin % MINUTE_OF_DAY) / 10 + 1) * ADDITIONAL_FEE_PER_10MIN;

        return BigDecimal.valueOf((parkingDays * MAX_FEE_OF_DAY) + additionalFee);
    }

}

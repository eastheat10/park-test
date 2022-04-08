package com.nhnacademy.parking.policy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface FeePolicy {

    BigDecimal calculateFee(LocalDateTime enterTime, LocalDateTime exitTime);

}

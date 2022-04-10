package com.nhnacademy.parking.policy;

import com.nhnacademy.parking.user.Voucher;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public interface FeePolicy {

    BigDecimal calculateFee(LocalDateTime enterTime, LocalDateTime exitTime, Optional<Voucher> voucher);

}

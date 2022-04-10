package com.nhnacademy.parking.parkingsystem;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.exception.CapacityOverflowException;
import com.nhnacademy.parking.exception.InsufficientCashException;
import com.nhnacademy.parking.policy.FeePolicy;
import com.nhnacademy.parking.user.PaycoServer;
import com.nhnacademy.parking.user.User;
import com.nhnacademy.parking.user.Voucher;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingSystem {

    private final PaycoServer server;
    private final FeePolicy feePolicy;
    private final ParkingLot parkingLot;

    public ParkingSystem(FeePolicy feePolicy) {
        server = new PaycoServer();
        this.feePolicy = feePolicy;
        parkingLot = new ParkingLot();
    }

    public Long park(Car car) {
        Long lotCode = null;

        try {
            lotCode = parkingLot.park(car);
        } catch (CapacityOverflowException e) {
            System.out.println(e.getMessage());
        }

        return lotCode;
    }

    public Car exit(Long lotCode) {

        Car exitCar = parkingLot.exit(lotCode);

        BigDecimal fee = feePolicy.calculateFee(
            exitCar.getEnterTime(), LocalDateTime.now(), exitCar.getVoucher()
                                                                .map(Voucher::getDiscountTime)
                                                                .orElse(0L));

        if (server.isExist(exitCar.getUserId())) {
            fee = fee.multiply(BigDecimal.valueOf(0.9));
        }

        try {
            exitCar.pay(fee);
        } catch (InsufficientCashException e) {
            System.out.println(exitCar.getUserId() + "님 잔액부족");
            return null;
        }
        return exitCar;
    }


    public void addUserToServer(User user) {
        server.join(user);
    }

}

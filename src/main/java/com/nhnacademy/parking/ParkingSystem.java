package com.nhnacademy.parking;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.car.FullSizedCar;
import com.nhnacademy.parking.exception.CapacityOverflowException;
import com.nhnacademy.parking.exception.CarSizeOverException;
import com.nhnacademy.parking.exception.NonExistentCarException;
import com.nhnacademy.parking.policy.FeePolicy;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ParkingSystem {

    private final static int MAX_PARKING_AREA = 10;

    private final PaycoServer server;
    private final FeePolicy feePolicy;
    private final Map<Integer, Car> parkingLot;
    private final Set<Integer> areaNumber;

    public ParkingSystem(FeePolicy feePolicy) {
        server = new PaycoServer();
        this.feePolicy = feePolicy;
        parkingLot = new HashMap<>();
        areaNumber = new HashSet<>();
        for (int i = 1; i <= MAX_PARKING_AREA; i++) {
            // 주차 공간 번호 추가
            areaNumber.add(i);
        }
    }

    public synchronized Integer park(Car car) {
        if (MAX_PARKING_AREA <= parkingLot.size()) {
            throw new CapacityOverflowException();
        }

        if (car instanceof FullSizedCar) {
            throw new CarSizeOverException();
        }

        Integer number = areaNumber.stream()
                                   .findFirst()
                                   .orElseThrow(CapacityOverflowException::new);
        areaNumber.remove(number);
        parkingLot.put(number, car);

        return number;
    }

    public synchronized Car exit(Long number) {
        Integer findAreaNumber = findAreaByNumber(number);
        Car parkedCar = parkingLot.get(findAreaNumber);

        areaNumber.add(findAreaNumber);
        BigDecimal fee = feePolicy.calculateFee(
            parkedCar.getEnterTime(), LocalDateTime.now(), parkedCar.getVoucher());

        if (server.isExist(parkedCar.getUserId())) {
            fee = fee.multiply(BigDecimal.valueOf(0.9));
        }

        parkedCar.pay(fee);
        return parkedCar;
    }

    private Integer findAreaByNumber(Long carNumber) {
        return parkingLot.keySet().stream()
                         .filter(areaNum ->
                             Objects.equals(parkingLot.get(areaNum).getNumber(), carNumber))
                         .findFirst()
                         .orElseThrow(() -> new NonExistentCarException(carNumber));
    }

    public void addUserToServer(User user) {
        server.join(user);
    }

}

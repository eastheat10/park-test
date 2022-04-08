package com.nhnacademy.parking;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.exception.CapacityOverflowException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class ParkingRepositoryImpl implements ParkingRepository {

    private final static int MAX_PARKING_AREA = 5;

    private final Map<Integer, Car> parkingLot;
    private final BiFunction<LocalDateTime, LocalDateTime, Long> parkingPolicy1;

    public ParkingRepositoryImpl(BiFunction<LocalDateTime, LocalDateTime, Long> parkingPolicy1) {
        this.parkingLot = new HashMap<>();
        this.parkingPolicy1 = parkingPolicy1;
    }

    @Override
    public boolean enter(Car car) {
        Long carNumber = car.getNumber();
        int parkNumber = park(car);
        return false;
    }

    @Override
    public int park(Car car) {
        if (parkingLot.size() >= MAX_PARKING_AREA) {
            throw new CapacityOverflowException();
        }

        parkingLot.put(parkingLot.size() + 1, car);
        return parkingLot.size();
    }

    @Override
    public boolean exit(Car car) {
        payFee(car);
        return true;
    }

    private void payFee(Car car) {
        int fee = 20000;
        car.pay(null);
    }

}

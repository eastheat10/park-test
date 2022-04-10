package com.nhnacademy.parking.parkingsystem;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.car.FullSizedCar;
import com.nhnacademy.parking.exception.CapacityOverflowException;
import com.nhnacademy.parking.exception.CarSizeOverException;
import com.nhnacademy.parking.exception.NonExistentCarException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParkingLot {

    private final static int MAX_PARKING_AREA = 10;

    private final Map<Long, Car> parkingLot;
    private final Set<Long> areaNumber;

    public ParkingLot() {
        this.parkingLot = new HashMap<>();
        this.areaNumber = new HashSet<>();
        for (int i = 0; i < MAX_PARKING_AREA; i++) {
            areaNumber.add((long) i + 1);
        }
    }

    public Long park(Car car) {
        if (MAX_PARKING_AREA <= parkingLot.size()) {
            throw new CapacityOverflowException();
        }

        if (car instanceof FullSizedCar) {
            throw new CarSizeOverException();
        }

        Long lotCode = areaNumber.stream()
                                .findAny()
                                .orElseThrow(CapacityOverflowException::new);

        areaNumber.remove(lotCode);
        parkingLot.put(lotCode, car);

        return lotCode;
    }

    public Car exit(Long lotCode) {
        Car parkedCar = parkingLot.get(lotCode);

        if (parkedCar == null) {
            throw new NonExistentCarException(lotCode);
        }

        return parkedCar;
    }

}

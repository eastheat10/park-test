package com.nhnacademy.parking.parkingsystem;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.car.FullSizedCar;
import com.nhnacademy.parking.exception.CapacityOverflowException;
import com.nhnacademy.parking.exception.CarSizeOverException;
import com.nhnacademy.parking.exception.NonExistentCarException;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingLot {

    private final static int MAX_PARKING_AREA = 10;

    private final Map<String, Car> parkingLot;
    private final Set<String> areaNumber;

    private static ParkingLot parkingLotClass;

    public ParkingLot() {
        this.parkingLot = new ConcurrentHashMap<>();
        this.areaNumber = new HashSet<>();
        while (areaNumber.size() < MAX_PARKING_AREA){
            areaNumber.add("A-" + (new SecureRandom().nextInt(99) + 1));
        }
    }

    public synchronized String park(Car car) {
        if (MAX_PARKING_AREA <= parkingLot.size()) {
            throw new CapacityOverflowException();
        }

        if (car instanceof FullSizedCar) {
            throw new CarSizeOverException();
        }

        String lotCode = areaNumber.parallelStream()
                                 .filter(lot -> parkingLot.get(lot) == null)
                                 .findFirst()
                                 .orElseThrow(CapacityOverflowException::new);

        areaNumber.remove(lotCode);
        parkingLot.put(lotCode, car);

        return lotCode;
    }

    public synchronized Car exit(String lotCode) {
        Car parkedCar = parkingLot.get(lotCode);

        if (parkedCar == null) {
            throw new NonExistentCarException(lotCode);
        }

        return parkedCar;
    }

    public static ParkingLot getParkingLot() {
        if (parkingLotClass == null) {
            parkingLotClass = new ParkingLot();
        }

        return parkingLotClass;
    }

}

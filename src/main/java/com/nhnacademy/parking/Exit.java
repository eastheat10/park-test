package com.nhnacademy.parking;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.parkingsystem.ParkingSystem;

public class Exit {

    private final ParkingSystem parkingSystem;

    public Exit(ParkingSystem parkingSystem) {
        this.parkingSystem = parkingSystem;
    }

    public synchronized Car exit(Long lotCode) {
        return parkingSystem.exit(lotCode);
    }
}

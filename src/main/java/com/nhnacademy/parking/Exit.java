package com.nhnacademy.parking;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.parkingsystem.ParkingSystem;

public class Exit {

    private final ParkingSystem parkingSystem;

    public Exit(ParkingSystem ps) {
        this.parkingSystem = ps;
    }

    public synchronized Car exit(String lotCode) {
        return parkingSystem.exit(lotCode);
    }
}

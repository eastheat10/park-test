package com.nhnacademy.parking;

import com.nhnacademy.parking.car.Car;

public class Exit {

    private final ParkingSystem parkingSystem;

    public Exit(ParkingSystem parkingSystem) {
        this.parkingSystem = parkingSystem;
    }

    public Car exit(Long id) {
        return parkingSystem.exit(id);
    }
}

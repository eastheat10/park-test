package com.nhnacademy.parking;

import com.nhnacademy.parking.car.Car;

public class Entrance {

    private final ParkingSystem parkingSystem;

    public Entrance(ParkingSystem parkingSystem) {
        this.parkingSystem = parkingSystem;
    }

    public boolean scan(Car car) {
        return car.getNumber() != null;
    }

    public Integer park(Car car) {
        return parkingSystem.park(car);
    }
}

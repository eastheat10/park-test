package com.nhnacademy.parking;

import com.nhnacademy.parking.car.Car;

public interface ParkingRepository {

    boolean enter(Car car);

    int park(Car car);

    boolean exit(Car car);
}

package com.nhnacademy.parking;

import com.nhnacademy.parking.car.Car;

public class Simulation extends Thread {

    private final Entrance entrance;
    private final Exit exit;
    private final Car car;

    public Simulation(Entrance entrance, Exit exit, Car car) {
        this.entrance = entrance;
        this.exit = exit;
        this.car = car;
    }

    @Override
    public void run() {
        entrance.scan(car);
        Long lotCode = entrance.park(car);

        System.out.println(car.getUserId() + "님 주차완료, 주차구역: " + lotCode);

        Car exitedCar = exit.exit(lotCode);

        if (exitedCar == null) {
            System.out.println(car.getUserId() + "님 출차 불가");
        } else {
            System.out.println(exitedCar.getUserId() + "님 출차 완료");
        }
    }
}

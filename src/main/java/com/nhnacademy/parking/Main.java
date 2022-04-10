package com.nhnacademy.parking;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.parkingsystem.ParkingSystem;
import com.nhnacademy.parking.policy.FeePolicy2;
import com.nhnacademy.parking.user.User;
import com.nhnacademy.parking.user.Voucher;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private final static int ENTRANCE_NUMBER = 3;

    public static void main(String[] args) {
        List<Simulation> list = new ArrayList<>();

        for (int i = 0; i < ENTRANCE_NUMBER; i++) {
            SecureRandom random = new SecureRandom();
            ParkingSystem ps = new ParkingSystem(new FeePolicy2());

            Long id = (long) (random.nextInt(9000) + 1000);
            BigDecimal money = BigDecimal.valueOf(random.nextInt(100_000));

            LocalDateTime today = LocalDateTime.now();
            LocalDateTime enterTime = today.minusHours(random.nextInt(today.getHour()))
                                           .minusMinutes(random.nextInt(60));

            User user = new User(id, money);
            user.takeVoucher(new Voucher(random.nextInt(3)));
            Car car = new Car(user, id, enterTime);

            list.add(new Simulation(new Entrance(ps), new Exit(ps), car));
        }

        list.forEach(Simulation::start);
    }

}

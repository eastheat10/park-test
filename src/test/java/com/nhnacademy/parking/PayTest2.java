package com.nhnacademy.parking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

import com.nhnacademy.parking.car.Car;
import com.nhnacademy.parking.exception.InsufficientCashException;
import com.nhnacademy.parking.policy.FeePolicy;
import com.nhnacademy.parking.policy.FeePolicy2;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PayTest2 {

    ParkingSystem ps;
    Car car;

    @BeforeEach
    void setUp() {
        ps = new ParkingSystem(new FeePolicy2());
        car = mock(Car.class);
    }

    @DisplayName("차가 나가면 결제 - 30분 주차, 차주 0원 소지")
    @Test
    void payWhenExit30m() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(0);
        LocalDateTime before30m = LocalDateTime.now().minusMinutes(30);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before30m);
        ps.park(parkingCar);

        assertThat(ps.exit(carNumber)).isEqualTo(parkingCar);
    }


    @DisplayName("차가 나가면 결제 - 31분 주차, 차주 1,000원 소지")
    @Test
    void payWhenExit31m() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(1_000);
        LocalDateTime before31m = LocalDateTime.now().minusMinutes(31);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before31m);
        ps.park(parkingCar);

        assertThat(ps.exit(carNumber)).isEqualTo(parkingCar);
    }

    @DisplayName("차가 나가면 결제실패 - 31분 주차, 차주 999원 소지")
    @Test
    void payWhenExit31m_fail() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(999);
        LocalDateTime before31m = LocalDateTime.now().minusMinutes(31);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before31m);
        ps.park(parkingCar);

        assertThatThrownBy(() -> ps.exit(carNumber))
            .isInstanceOf(InsufficientCashException.class);
    }

    @DisplayName("차가 나가면 결제 - 61분 주차, 차주 1,500원 소지")
    @Test
    void payWhenExit61m() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(1_500);
        LocalDateTime before61m = LocalDateTime.now().minusMinutes(61);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before61m);
        ps.park(parkingCar);

        assertThat(ps.exit(carNumber)).isEqualTo(parkingCar);
    }

    @DisplayName("차가 나가면 결제실패 - 61분 주차, 차주 1,499원 소지")
    @Test
    void payWhenExit61m_fail() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(1_499);
        LocalDateTime before61m = LocalDateTime.now().minusMinutes(61);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before61m);
        ps.park(parkingCar);

        assertThatThrownBy(() -> ps.exit(carNumber))
            .isInstanceOf(InsufficientCashException.class);
    }

    @DisplayName("차가 나가면 결제 - 6시간 주차, 차주 15,000원 소지")
    @Test
    void payWhenExit6h() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(15_000);
        LocalDateTime before6h = LocalDateTime.now().minusHours(6);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before6h);
        ps.park(parkingCar);

        assertThat(ps.exit(carNumber)).isEqualTo(parkingCar);
    }

    @DisplayName("차가 나가면 결제실패 - 6시간 주차, 차주 14,999원 소지")
    @Test
    void payWhenExit6h_fail() {

        Long carNumber = 1234L;
        BigDecimal money = BigDecimal.valueOf(14_999);
        LocalDateTime before6h = LocalDateTime.now().minusHours(6);

        User user = new User(carNumber, money);

        Car parkingCar = new Car(user, carNumber, before6h);
        ps.park(parkingCar);

        assertThatThrownBy(() -> ps.exit(carNumber))
            .isInstanceOf(InsufficientCashException.class);
    }

    @DisplayName("PAYCO 회원이면 10% 할인")
    @Test
    void paycoMemberDiscount() {

        Long id = 1111L;
        BigDecimal money = BigDecimal.valueOf(100_000);
        LocalDateTime enterTime = LocalDateTime.now().minusMinutes(10);

        User user = new User(id, money);
        ps.addUserToServer(user);

        FeePolicy policy = new FeePolicy2();

        BigDecimal fee = policy.calculateFee(enterTime, LocalDateTime.now(), Optional.empty());
        BigDecimal expectFee = fee.multiply(BigDecimal.valueOf(0.9));

        Car car = new Car(user, id, LocalDateTime.now().minusMinutes(10));

        ps.park(car);
        ps.exit(id);

        assertThat(car.getMoney()).isEqualTo(money.subtract(expectFee));
    }

    @DisplayName("3시간 주차 후 2시간 주차권을 제시하면, 1시간 요금만 정산")
    @Test
    void useVoucher() {
        LocalDateTime exitTime = LocalDateTime.now();
        LocalDateTime enterTime = exitTime.minusHours(3);
        LocalDateTime discountTime = exitTime.minusHours(1);

        long id = 4321L;
        BigDecimal money = BigDecimal.valueOf(100_000);

        // 1시간 주차, 주차권 없음
        User user = new User(id, money);
        Car car = new Car(user, id, discountTime);

        // 3시간주차, 2시간 주차권
        User voucherUser = new User(id + 1, money);
        Car voucherCar = new Car(voucherUser, id + 1, enterTime);
        voucherUser.takeVoucher(new Voucher(2));

        ps.park(car);
        ps.park(voucherCar);

        ps.exit(id);
        ps.exit(id + 1);

        assertThat(voucherUser.getMoney()).isEqualTo(user.getMoney());
    }

    @DisplayName("59분 주차 후 1시간 주차권을 제시하면, 무료")
    @Test
    void isFree() {
        LocalDateTime enterTime = LocalDateTime.now().minusMinutes(59);

        Long id = 4321L;
        BigDecimal money = BigDecimal.valueOf(100_000);

        // 3시간주차, 2시간 주차권
        User voucherUser = new User(id, money);
        Car voucherCar = new Car(voucherUser, id, enterTime);
        voucherUser.takeVoucher(new Voucher(1));

        ps.park(voucherCar);

        ps.exit(id);

        assertThat(voucherUser.getMoney()).isEqualTo(money);
    }

}
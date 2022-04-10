## 스펙 1. 
주차장에 차가 들어온다. 
* 차가 들어오면 번호판을 인식(scan)합니다.

A-1 에 주차한다. 
* 차를 특정 주차구역(ParkingSpaces)에 주차합니다.

주차장에서 차가 나간다. 
* 차가 나갈려면 주차 시간만큼 결제를 해야한다. 
* 만약 돈이 없으면 나갈 수 없습니다.

### 요금표
| 시간     | 요금      | 비고                                    |
|--------|---------|---------------------------------------|
| 최초 30분 | 1,000원  |
| 추가 10분 | 500원    | 1초라도 넘으면 부과됩니다.                       |
| 일일 주차  | 10,000원 | 일 최대 금액입니다. 24:00이 넘어가면 추가 요금이 부과됩니다. 2일 연속 주차 시 20,000원|

* 30분 1초 주차한 경우 요금은 1,500원입니다. 
* 50분 주차한 경우 요금은 2,000원입니다. 
* 61분 주차한 경우 요금은 3,000원입니다. 
* 6시간 주차한 경우 요금은 10,000원입니다. 

## 스펙 2. 
* 주차장 입구가 n개 입니다. 
* 주차장 출구도 n개 입니다. 

## 스펙 3. 
> 요금표가 변경 됐습니다. 

### 요금표

| 시간                                           | 요금| 비고|
|----------------------------------------------|----|----|
| 최초 30분                                       |무료||
| 최초 30초과 ~ 1시간                                |1,000원 이 후 추가 10분 500원 |1초라도 넘으면 부과됩니다. |
| 일일 주차| 15,000원 |일 최대 금액입니다. 24:00이 넘어가면 추가 요금이 부과됩니다. 2일 연속 주차 시 30,000원 |

* **경차의 경우 요금이 50% 감면됩니다.**
> 주차장에 대형차(트럭 등)는 주차할 수 없습니다.

## 스펙 4. 
* 사용자(User)가 Payco 회원인 경우에는 주차 요금이 10% 할인됩니다.
  * Payco 회원은 바코드를 통해서 Payco 인증 서버에 인증 후 할인됩니다. 
  * 만약 Payco 인증이 안되는 경우에는 익명 사용자로 간주됩니다. 
  * 그 외 사용자는 익명사용자로 간주하며, 요금할인 혜택이 없습니다.

* 시간 주차권이 존재합니다. 
  * 3시간 주차 후 2시간 주차권을 제시하면, 1시간 요금만 정산하면 됩니다. 
  * 59분 주차 후 1시간 주차권을 제시하면, 무료입니다
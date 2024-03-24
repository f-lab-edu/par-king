package com.parking.jpa.entity

import com.parking.domain.entity.Car
import com.parking.domain.entity.DibsOnParkingLotStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "car")
data class CarJpaEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "car_number")
    val carNumber: String,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "parking_lot_id")
    val parkingLotId: Long? = null,

    @Column(name = "dibs_on_parking_lot_id")
    val dibsOnParkingLotId: Long? = null,

    @Column(name = "dibs_on_parking_lot_status")
    @Enumerated(value = EnumType.STRING)
    val dibsOnParkingLotStatus: DibsOnParkingLotStatus? = null,

    @Column(name = "start_dibs_on_time")
    val startDibsOnTime: LocalDateTime? = null
) : BaseEntity() {
    fun to() = Car(
        carId = id,
        carNumber = carNumber,
        memberId = memberId,
        parkingLotId = parkingLotId,
        dibsOnParkingLotId = dibsOnParkingLotId,
        dibsOnParkingLotStatus = dibsOnParkingLotStatus
    )

    companion object {
        fun from(car: Car) = CarJpaEntity(
            id = car.carId,
            carNumber = car.carNumber,
            memberId = car.memberId,
            parkingLotId = car.parkingLotId,
            dibsOnParkingLotId = car.dibsOnParkingLotId,
            dibsOnParkingLotStatus = car.dibsOnParkingLotStatus,
            startDibsOnTime = car.startDibsOnTime
        )
    }
}

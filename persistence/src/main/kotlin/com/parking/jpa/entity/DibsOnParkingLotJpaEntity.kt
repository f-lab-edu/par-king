package com.parking.jpa.entity

import com.parking.domain.entity.DibsOnParkingLot
import com.parking.domain.entity.DibsOnParkingLotStatus
import jakarta.persistence.*

@Entity
@Table(name = "dibs_on_parking_lot")
data class DibsOnParkingLotJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(name = "member_id")
    val memberId: Long,

    @Column(name = "car_id")
    val carId: Long,

    @Column(name = "parking_lot_id")
    val parkingLotId: Long,

    @Column(name = "current_status")
    @Enumerated(value = EnumType.STRING)
    val currentStatus: DibsOnParkingLotStatus

) : BaseEntity() {
    fun to() = DibsOnParkingLot(
        dibsOnParkingLotId = id,
        memberId,
        parkingLotId,
        carId,
        currentStatus = currentStatus
    )
}

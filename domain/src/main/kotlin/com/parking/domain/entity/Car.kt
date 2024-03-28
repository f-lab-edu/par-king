package com.parking.domain.entity

import java.time.LocalDateTime

data class Car (
    val carId: Long? = null,
    var carNumber: String,
    val memberId: Long,
    var parkingLotId: Long? = null,
    var dibsOnParkingLotId: Long? = null,
    var dibsOnParkingLotStatus: DibsOnParkingLotStatus? = null,
    var startDibsOnTime: LocalDateTime? = null
) {
    fun dibsOnParkingLot(parkingLotId: Long, dibsOnParkingLotId: Long) {
        this.parkingLotId = parkingLotId
        this.dibsOnParkingLotId = dibsOnParkingLotId
        this.startDibsOnTime = LocalDateTime.now()
        this.dibsOnParkingLotStatus = DibsOnParkingLotStatus.DIBS_ON_PARKING_LOT
    }
}

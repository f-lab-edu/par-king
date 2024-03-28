package com.parking.api.application.vo

import com.parking.domain.entity.DibsOnParkingLot

data class DibsOnParkingLotVO(
    val memberId: String,
    val parkingLotId: Long,
    val carId: Long
) {
    fun to(memberId: Long) = DibsOnParkingLot(
        memberId = memberId,
        parkingLotId = this.parkingLotId,
        carId = this.carId
    )
}
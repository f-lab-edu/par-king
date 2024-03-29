package com.parking.api.adapter.`in`.dto

import com.parking.api.application.vo.DibsOnParkingLotVO

data class DibsOnParkingLotDTO(
    val memberId: String,
    val parkingLotId: Long,
    val carId: Long
) {
    fun to() = DibsOnParkingLotVO(
        memberId, parkingLotId, carId
    )
}

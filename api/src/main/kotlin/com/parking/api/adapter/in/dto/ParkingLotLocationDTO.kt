package com.parking.api.adapter.`in`.dto

import com.parking.api.application.vo.ParkingLotLocationVO

data class ParkingLotLocationDTO(
    val cityName: String? = null,
    val guName: String? = null
) {
    fun to() = ParkingLotLocationVO(
        cityName, guName
    )
}

package com.parking.api.adapter.`in`.dto

import com.parking.api.application.vo.CarInfoVO
import com.parking.domain.entity.DibsOnParkingLotStatus
import java.time.LocalDateTime

data class CarInfoDTO(
    val carId: Long? = null,
    var carNumber: String,
    val dibsOnParkingLotName: String? = null,
    var dibsOnParkingLotStatus: DibsOnParkingLotStatus? = null,
    var startDibsOnTime: LocalDateTime? = null
) {
    companion object {
        fun from(carInfo: CarInfoVO) = CarInfoDTO(
            carId = carInfo.carId,
            carNumber = carInfo.carNumber,
            dibsOnParkingLotName = carInfo.dibsOnParkingLotName,
            dibsOnParkingLotStatus = carInfo.dibsOnParkingLotStatus,
            startDibsOnTime = carInfo.startDibsOnTime
        )
    }
}

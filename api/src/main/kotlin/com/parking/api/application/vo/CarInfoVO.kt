package com.parking.api.application.vo

import com.parking.domain.entity.Car
import com.parking.domain.entity.DibsOnParkingLotStatus
import java.time.LocalDateTime

data class CarInfoVO(
    val carId: Long? = null,
    var carNumber: String,
    val dibsOnParkingLotName: String? = null,
    var dibsOnParkingLotStatus: DibsOnParkingLotStatus? = null,
    var startDibsOnTime: LocalDateTime? = null
) {
    companion object {
        fun from(car: Car, parkingLotName: String?) = CarInfoVO(
            carId = car.carId,
            carNumber = car.carNumber,
            dibsOnParkingLotName = parkingLotName,
            dibsOnParkingLotStatus = car.dibsOnParkingLotStatus,
            startDibsOnTime = car.startDibsOnTime
        )
    }
}

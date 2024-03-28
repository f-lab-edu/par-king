package com.parking.api.application.vo

import com.parking.domain.entity.Car

data class RegisterCarVO (
    val memberId: String,
    val carNumber: String
) {
    fun toCar(id: Long) = Car(
        carId = null,
        carNumber = carNumber,
        memberId = id
    )
}
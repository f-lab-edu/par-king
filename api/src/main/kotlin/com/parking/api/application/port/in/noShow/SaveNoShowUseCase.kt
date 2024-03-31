package com.parking.api.application.port.`in`.noShow

interface SaveNoShowUseCase {
    fun save(memberId: String, parkingLotId: Long, carId: Long)
}
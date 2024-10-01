package com.parking.api.application.port.`in`.noShow

import com.parking.domain.entity.DibsOnParkingLot

interface SaveNoShowUseCase {
    fun save(memberId: Long, dibsOnParkingLot: DibsOnParkingLot)
}
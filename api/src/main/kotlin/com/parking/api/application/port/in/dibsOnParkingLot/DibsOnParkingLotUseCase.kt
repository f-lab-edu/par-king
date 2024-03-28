package com.parking.api.application.port.`in`.dibsOnParkingLot

import com.parking.api.application.vo.DibsOnParkingLotVO

interface DibsOnParkingLotUseCase {
    fun dibsOnParkingLot(memberId: Long, parkingLotId: Long, dibsOnParkingLotVO: DibsOnParkingLotVO)
}
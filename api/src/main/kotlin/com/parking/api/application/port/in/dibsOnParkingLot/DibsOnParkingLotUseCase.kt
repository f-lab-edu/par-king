package com.parking.api.application.port.`in`.dibsOnParkingLot

import com.parking.api.application.vo.DibsOnParkingLotVO

interface DibsOnParkingLotUseCase {
    fun dibsOnParkingLot(parkingLotId: Long, dibsOnParkingLotVO: DibsOnParkingLotVO)
}
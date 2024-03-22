package com.parking.api.application.port.`in`.parkingLot

import com.parking.api.application.vo.ParkingLotInfoVO

interface CreateParkingLotUseCase {
    fun create(parkingLotInfoVO: ParkingLotInfoVO)
}
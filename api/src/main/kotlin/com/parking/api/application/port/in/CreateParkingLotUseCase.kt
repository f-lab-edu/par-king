package com.parking.api.application.port.`in`

import com.parking.api.application.vo.ParkingLotInfoVO

interface CreateParkingLotUseCase {
    fun create(parkingLotInfoVO: ParkingLotInfoVO)
}
package com.parking.api.application.port.`in`.parkingLot

import com.parking.api.application.vo.ParkingLotInfoVO

interface ModifyParkingLotUseCase {
    fun modify(parkingLotInfoVO: ParkingLotInfoVO): ParkingLotInfoVO
}
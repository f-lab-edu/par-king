package com.parking.api.application.port.out

import com.parking.domain.entity.ParkingLot

interface SaveParkingLotPort {
    fun save(parkingLot: ParkingLot)
}
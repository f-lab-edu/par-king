package com.parking.api.application.port.out

import com.parking.domain.entity.DibsOnParkingLot

interface SaveDibsOnParkingLotPort {
    fun save(dibsOnParkingLot: DibsOnParkingLot): DibsOnParkingLot
}
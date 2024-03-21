package com.parking.api.application.port.out

import com.parking.domain.entity.ParkingLot
import java.time.LocalDateTime

interface SaveParkingLotPort {
    fun save(parkingLot: ParkingLot)

    fun update(parkingLot: ParkingLot, deletedAt: LocalDateTime)
}
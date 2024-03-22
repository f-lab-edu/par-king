package com.parking.api.application.port.out

import com.parking.domain.entity.ParkingLot

interface FindParkingLotPort {
    fun findById(parkingLotId: Long): ParkingLot?

    fun findByMemberId(memberId: Long): List<ParkingLot>
}
package com.parking.api.application.port.out

import com.parking.api.application.vo.ParkingLotLocationVO
import com.parking.domain.entity.ParkingLot

interface FindParkingLotPort {
    fun findById(parkingLotId: Long): ParkingLot?

    fun findAllByMemberId(memberId: Long): List<ParkingLot>

    fun findAllByLocation(location: ParkingLotLocationVO): List<ParkingLot>
}
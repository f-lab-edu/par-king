package com.parking.api.application.port.`in`.parkingLot

import com.parking.api.application.vo.ParkingLotListInfoVO
import com.parking.api.application.vo.ParkingLotLocationVO

interface FindParkingLotUseCase {
    fun findAllByMemberId(currentUserName: String, memberId: String): List<ParkingLotListInfoVO>

    fun findAllByLocation(location: ParkingLotLocationVO): List<ParkingLotListInfoVO>
}
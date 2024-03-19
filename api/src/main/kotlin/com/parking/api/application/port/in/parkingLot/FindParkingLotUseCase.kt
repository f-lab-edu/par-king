package com.parking.api.application.port.`in`.parkingLot

import com.parking.api.application.vo.ParkingLotListInfoVO

interface FindParkingLotUseCase {
    fun findAllByMemberId(memberId: String): List<ParkingLotListInfoVO>
}
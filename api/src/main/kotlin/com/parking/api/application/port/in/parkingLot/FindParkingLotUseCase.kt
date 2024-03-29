package com.parking.api.application.port.`in`.parkingLot

import com.parking.api.application.vo.ParkingLotListInfoVO
import com.parking.api.application.vo.ParkingLotLocationVO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FindParkingLotUseCase {
    fun findAllByMemberId(currentUserName: String, memberId: String, page: Pageable): Page<ParkingLotListInfoVO>

    fun findAllByLocation(location: ParkingLotLocationVO, page: Pageable): Page<ParkingLotListInfoVO>
}
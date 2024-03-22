package com.parking.api.application.port.out

import com.parking.api.application.vo.ParkingLotLocationVO
import com.parking.domain.entity.ParkingLot
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface FindParkingLotPort {
    fun findById(parkingLotId: Long): ParkingLot?

    fun findAllByMemberId(memberId: Long, page: Pageable): Page<ParkingLot>

    fun findAllByLocation(location: ParkingLotLocationVO, page: Pageable): Page<ParkingLot>
}
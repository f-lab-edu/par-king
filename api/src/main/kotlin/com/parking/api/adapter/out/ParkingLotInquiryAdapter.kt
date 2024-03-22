package com.parking.api.adapter.out

import com.parking.api.application.port.out.FindParkingLotPort
import com.parking.domain.entity.ParkingLot
import com.parking.jpa.repositories.ParkingLotJpaRepository
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class ParkingLotInquiryAdapter(
    private val parkingLotJpaRepository: ParkingLotJpaRepository
): FindParkingLotPort {

    override fun findById(parkingLotId: Long): ParkingLot? {
        val parkingLotEntity = parkingLotJpaRepository.findById(parkingLotId).getOrNull()

        return parkingLotEntity?.to()
    }

    override fun findByMemberId(memberId: Long): List<ParkingLot> {
        return parkingLotJpaRepository.findAllByMemberId(memberId).map {
            it.to()
        }
    }
}
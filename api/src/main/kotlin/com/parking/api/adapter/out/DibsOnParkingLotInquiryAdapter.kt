package com.parking.api.adapter.out

import com.parking.api.application.port.out.FindDibsOnParkingLotPort
import com.parking.domain.entity.DibsOnParkingLot
import com.parking.domain.entity.DibsOnParkingLotStatus
import com.parking.jpa.repositories.DibsOnParkingLotRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DibsOnParkingLotInquiryAdapter(
    private val dibsOnParkingLotRepository: DibsOnParkingLotRepository
): FindDibsOnParkingLotPort {
    override fun findDibsOnDataByOverTimeAndStatus(overTime: LocalDateTime, status: DibsOnParkingLotStatus): List<DibsOnParkingLot> {

        return dibsOnParkingLotRepository.findDibsOnDataByOverTimeAndStatus(overTime, status).map { it.to() }
    }
}
package com.parking.api.application.port.out

import com.parking.domain.entity.DibsOnParkingLot
import com.parking.domain.entity.DibsOnParkingLotStatus
import java.time.LocalDateTime

interface FindDibsOnParkingLotPort {
    fun findDibsOnDataByOverTimeAndStatus(overTime: LocalDateTime, status: DibsOnParkingLotStatus): List<DibsOnParkingLot>
}
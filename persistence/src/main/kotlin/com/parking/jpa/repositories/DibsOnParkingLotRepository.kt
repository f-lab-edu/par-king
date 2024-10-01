package com.parking.jpa.repositories

import com.parking.domain.entity.DibsOnParkingLotStatus
import com.parking.jpa.entity.DibsOnParkingLotEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface DibsOnParkingLotRepository : JpaRepository<DibsOnParkingLotEntity, Long>, CustomDibsOnParkingLotRepository {
}

interface CustomDibsOnParkingLotRepository {
    fun findDibsOnDataByOverTimeAndStatus(
        overTime: LocalDateTime,
        status: DibsOnParkingLotStatus
    ): List<DibsOnParkingLotEntity>
}
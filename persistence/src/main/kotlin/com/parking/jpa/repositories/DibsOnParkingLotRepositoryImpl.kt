package com.parking.jpa.repositories

import com.parking.domain.entity.DibsOnParkingLotStatus
import com.parking.jpa.entity.DibsOnParkingLotEntity
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import com.parking.jpa.entity.QDibsOnParkingLotEntity.Companion.dibsOnParkingLotEntity

@Repository
class DibsOnParkingLotRepositoryImpl : QuerydslRepositorySupport(DibsOnParkingLotEntity::class.java), CustomDibsOnParkingLotRepository {
    override fun findDibsOnDataByOverTimeAndStatus(
        overTime: LocalDateTime,
        status: DibsOnParkingLotStatus
    ): List<DibsOnParkingLotEntity> {
        return from(dibsOnParkingLotEntity)
            .where(dibsOnParkingLotEntity.createdAt.lt(overTime)
                .and(dibsOnParkingLotEntity.currentStatus.eq(status)))
            .fetch()
    }
}
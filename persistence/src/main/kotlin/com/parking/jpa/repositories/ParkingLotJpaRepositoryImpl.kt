package com.parking.jpa.repositories

import com.parking.jpa.entity.ParkingLotJpaEntity
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import com.parking.jpa.entity.QParkingLotJpaEntity.Companion.parkingLotJpaEntity

@Repository
class ParkingLotJpaRepositoryImpl : QuerydslRepositorySupport(ParkingLotJpaEntity::class.java),
    ParkingLotJpaRepositoryCustom {
    override fun findAllByMemberId(memberId: Long): List<ParkingLotJpaEntity> {
        return from(parkingLotJpaEntity)
            .where(parkingLotJpaEntity.memberId.eq(memberId))
            .fetch()
    }
}
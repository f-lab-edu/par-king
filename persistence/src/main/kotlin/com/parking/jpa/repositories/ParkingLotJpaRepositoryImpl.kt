package com.parking.jpa.repositories

import com.parking.jpa.entity.ParkingLotJpaEntity
import com.parking.jpa.entity.ParkingLotJpaEntity.Companion.STANDARD_DELETED_AT_TIME
import com.parking.jpa.entity.QParkingLotJpaEntity.Companion.parkingLotJpaEntity
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ParkingLotJpaRepositoryImpl : QuerydslRepositorySupport(ParkingLotJpaEntity::class.java),
    ParkingLotJpaRepositoryCustom {
    override fun findAllByMemberId(memberId: Long): List<ParkingLotJpaEntity> {
        return from(parkingLotJpaEntity)
            .where(
                parkingLotJpaEntity.memberId.eq(memberId)
                    .and(parkingLotJpaEntity.deletedAt.lt(STANDARD_DELETED_AT_TIME))
            )
            .fetch()
    }

    override fun findAllByLocation(cityName: String?, guName: String?): List<ParkingLotJpaEntity> {
        return from(parkingLotJpaEntity)
            .where(
                parkingLotJpaEntity.deletedAt.lt(STANDARD_DELETED_AT_TIME)
                    .and(cityName?.let { parkingLotJpaEntity.cityName.eq(it) })
                    .and(guName?.let { parkingLotJpaEntity.guName.eq(it) })
            )
            .fetch()
    }
}
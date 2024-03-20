package com.parking.jpa.repositories

import com.parking.domain.entity.ParkingLot.Companion.STANDARD_DELETED_AT_TIME
import com.parking.jpa.entity.ParkingLotJpaEntity
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
}
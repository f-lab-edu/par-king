package com.parking.jpa.repositories

import com.parking.jpa.entity.ParkingLotJpaEntity
import com.parking.jpa.entity.ParkingLotJpaEntity.Companion.STANDARD_DELETED_AT_TIME
import com.parking.jpa.entity.QParkingLotJpaEntity.Companion.parkingLotJpaEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ParkingLotJpaRepositoryImpl : QuerydslRepositorySupport(ParkingLotJpaEntity::class.java),
    ParkingLotJpaRepositoryCustom {
    override fun findAllByMemberId(memberId: Long, pageable: Pageable): Page<ParkingLotJpaEntity> {
        return from(parkingLotJpaEntity)
            .where(
                parkingLotJpaEntity.memberId.eq(memberId)
                    .and(parkingLotJpaEntity.deletedAt.lt(STANDARD_DELETED_AT_TIME))
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
            .let {
                PageImpl(it.results, pageable, it.total)
            }
    }

    override fun findAllByLocation(cityName: String?, guName: String?, pageable: Pageable): Page<ParkingLotJpaEntity> {
        return from(parkingLotJpaEntity)
            .where(
                parkingLotJpaEntity.deletedAt.lt(STANDARD_DELETED_AT_TIME)
                    .and(cityName?.let { parkingLotJpaEntity.cityName.eq(it) })
                    .and(guName?.let { parkingLotJpaEntity.guName.eq(it) })
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
            .let {
                PageImpl(it.results, pageable, it.total)
            }
    }
}
package com.parking.jpa.repositories

import com.parking.jpa.entity.ParkingLotEntity
import com.parking.jpa.entity.ParkingLotEntity.Companion.STANDARD_DELETED_AT_TIME
import com.parking.jpa.entity.QParkingLotEntity.Companion.parkingLotEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class ParkingLotRepositoryImpl : QuerydslRepositorySupport(ParkingLotEntity::class.java),
    ParkingLotRepositoryCustom {
    override fun findAllByMemberId(memberId: Long, pageable: Pageable): Page<ParkingLotEntity> {
        return from(parkingLotEntity)
            .where(
                parkingLotEntity.memberId.eq(memberId)
                    .and(parkingLotEntity.deletedAt.lt(STANDARD_DELETED_AT_TIME))
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
            .let {
                PageImpl(it.results, pageable, it.total)
            }
    }

    override fun findAllByLocation(cityName: String?, guName: String?, pageable: Pageable): Page<ParkingLotEntity> {
        return from(parkingLotEntity)
            .where(
                parkingLotEntity.deletedAt.lt(STANDARD_DELETED_AT_TIME)
                    .and(cityName?.let { parkingLotEntity.cityName.eq(it) })
                    .and(guName?.let { parkingLotEntity.guName.eq(it) })
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetchResults()
            .let {
                PageImpl(it.results, pageable, it.total)
            }
    }
}
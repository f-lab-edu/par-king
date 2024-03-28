package com.parking.jpa.repositories

import com.parking.jpa.entity.CarEntity
import org.springframework.stereotype.Repository
import com.parking.jpa.entity.QCarEntity.Companion.carEntity
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

@Repository
class CarRepositoryImpl:  QuerydslRepositorySupport(CarEntity::class.java), CarRepositoryCustom {
    override fun findAllByMemberId(memberId: Long): List<CarEntity> {
        return from(carEntity)
            .where(carEntity.memberId.eq(memberId))
            .fetch()
    }
}